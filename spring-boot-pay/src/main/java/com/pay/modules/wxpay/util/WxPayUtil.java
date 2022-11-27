package com.pay.modules.wxpay.util;

import com.pay.common.constants.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 相关配置参数
 */
@Component
@Configuration
@EnableConfigurationProperties({CpWxPayProperties.class})
public class WxPayUtil {

    private CpWxPayProperties wxPay;

    public WxPayUtil(CpWxPayProperties wxPay) {
        this.wxPay = wxPay;
    }

    public CpWxPayProperties wxPay() {
        return wxPay;
    }

    /**
     * 基础参数
     * @param packageParams
     */
	public void commonParams(SortedMap<Object, Object> packageParams) {
		String appId = wxPay.getAppId();
		String mch_id = wxPay.getMchId();
		String currTime = PayCommonUtil.getCurrTime();
		String strTime = currTime.substring(8);
		String strRandom = PayCommonUtil.buildRandom(4) + "";
		String nonce_str = strTime + strRandom;
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
	}

    /**
     * 该接口主要用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)
     * 减小二维码数据量，提升扫描速度和精确度
     * @param urlCode
     * @return
     */
	public String shortUrl(String urlCode) {
		try {
			String key = wxPay.getApiKey();
			SortedMap<Object, Object> packageParams = new TreeMap<>();
			commonParams(packageParams);
			packageParams.put("long_url", urlCode);// URL链接
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(WxPayUrl.SHORT_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if (Constants.SUCCESS.equalsIgnoreCase(returnCode)) {
				String resultCode = (String) map.get("return_code");
				if (Constants.SUCCESS.equalsIgnoreCase(resultCode)) {
					urlCode = (String) map.get("short_url");
                    return urlCode;
				}else{
                    return Constants.FAIL;
                }
			}else{
                return Constants.FAIL;
            }
		} catch (Exception e) {
			e.printStackTrace();
            return Constants.FAIL;
		}
	}

    public String doRefund(String url,String data) throws Exception {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        File certfile = ResourceUtils.getFile("classpath:cert"+
                Constants.SF_FILE_SEPARATOR + wxPay.getCertPath());
        FileInputStream instream = new FileInputStream(certfile);
        try {
            keyStore.load(instream, wxPay.getMchId().toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, wxPay.getMchId().toCharArray())
                .build();
        SSLConnectionSocketFactory SSL = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(SSL)
                .build();
        try {
            HttpPost httPost = new HttpPost(url);
            httPost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httPost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}