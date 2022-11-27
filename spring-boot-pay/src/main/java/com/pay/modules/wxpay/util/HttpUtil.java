package com.pay.modules.wxpay.util;

import com.pay.common.util.HttpUtils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 获取订单url
 */
public class HttpUtil {

    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;

	private final static String DEFAULT_ENCODING = "UTF-8";

	public static String postData(String urlStr, String data) {
		return postData(urlStr, data, null);
	}

	public static String postData(String urlStr, String data, String contentType) {
		BufferedReader reader = null;
		try {
            URLConnection conn = initHttps(urlStr);
			if (contentType != null) {
                conn.setRequestProperty("content-type", contentType);
            }
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
			if (data == null) {
                data = "";
            }
			writer.write(data);
			writer.flush();
			writer.close();

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
                    reader.close();
                }
			} catch (IOException e) {
			}
		}
		return null;
	}

    /**
     * 初始化http请求参数
     */
    private static HttpsURLConnection initHttps(String url) throws Exception {
        TrustManager[] tm = {new MyX509TrustManager()};
        System.setProperty("https.protocols", "TLSv1");
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL _url = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) _url.openConnection();
        // 设置域名校验
        http.setHostnameVerifier(new HttpUtils().new TrustAnyHostnameVerifier());
        // 连接超时
        http.setConnectTimeout(DEF_CONN_TIMEOUT);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(DEF_READ_TIMEOUT);
        http.setUseCaches(false);
        http.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        http.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        http.setSSLSocketFactory(ssf);
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        return http;
    }

    private static class MyX509TrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }
}
