package com.pay.modules.alipay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.pay.common.constants.Constants;
import com.pay.common.model.Product;
import com.pay.common.util.SslUtils;
import com.pay.common.util.ZxingUtils;
import com.pay.modules.alipay.service.CpAliPayService;
import com.pay.modules.alipay.util.CpPayUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
/**
 * 支付宝
 * 创建者 科帮网
 * 创建时间	2018年1月15日
 * ======================
 * 商户端私钥：
 * 由我们自己生成的RSA私钥（必须与商户端公钥是一对），生成后要保存在服务端，绝对不能保存在客户端，也绝对不能从服务端下发。
 * 用来对订单信息进行加签，加签过程一定要在服务端完成，绝对不能在客户端做加签工作，客户端只负责用加签后的订单信息调起支付宝来支付。
 * ======================
 * 商户端公钥：
 * 由我们自己生成的RSA公钥（必须与商户端私钥是一对），生成后需要填写在支付宝开放平台，
 * 用来给支付宝服务端验签经过我们加签后的订单信息，以确保订单信息确实是我们商户端发给支付宝的，并且确保订单信息在传输过程中未被篡改。
 * ======================
 * 支付宝私钥：
 * 支付宝自己生成的，他们自己保存，开发者是无法看到的，用来对支付结果进行加签。
 * ======================
 * 支付宝公钥：
 * 支付宝公钥和支付宝私钥是一对，也是支付宝生成的，当我们把商户端公钥填写在支付宝开放平台后，平台就会给我们生成一个支付宝公钥。
 * 我们可以复制下来保存在服务端，同样不要保存在客户端，并且不要下发，避免被反编译或截获，而被篡改支付结果。
 * 用来让服务端对支付宝服务端返给我们的同步或异步支付结果进行验签，以确保支付结果确实是由支付宝服务端返给我们服务端的，而且没有被篡改。
 * 对支付结果的验签工作也一定要在服务端完成，绝对不能在客户端验签，因为支付宝公钥一旦存储在客户端用来验签，那就可能被反编译，这样就谁都可以验签支付结果并篡改了。
 * ======================
 * 支付宝建议加签方式升级为RSA(SHA256)密钥，以为 SHA 貌似已经被破解了。
 * 
 */
@DubboService(group = "payCloud", retries = 1, timeout = 10000)
public class CpAliPayServiceImpl implements CpAliPayService {
	private static final Logger logger = LoggerFactory.getLogger(CpAliPayServiceImpl.class);
	
    @Value("${file.path}")
    private String filePath;
    @Value("${server.context.url}")
    private String projectUrl;
    @Autowired
    private CpPayUtils cpPayUtils;


	@Override
	public String aliPay(Product product) {
		logger.info("订单号：{}生成支付宝支付码",product.getOutTradeNo());
        try {
            SslUtils.ignoreSsl();
            String orderNo = IdUtil.getSnowflake(1,1).nextId()+"";
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .asyncNotify(cpPayUtils.getConfig().getNotifyUrl())
                    .preCreate(product.getSubject(), orderNo, product.getTotalFee());
            if (ResponseChecker.success(response)) {
                String imgName = orderNo+".png";
                String imgPath= filePath+ Constants.SF_FILE_SEPARATOR + imgName;
                ZxingUtils.createQRCodeImage(response.getQrCode(), imgPath);
                String imgUrl = projectUrl+"/file/"+imgName;
                return imgUrl;
            } else {
                logger.error("企业支付下单失败{}{}",response.msg,response.subMsg);
                return Constants.FAIL;
            }
        } catch (Exception e) {
            logger.error("企业支付下单失败");
            return Constants.FAIL;
        }
	}

	@Override
	public String aliRefund(Product product) {
		logger.info("订单号："+product.getOutTradeNo()+"支付宝退款");
        try {
                AlipayTradeRefundResponse response = Factory.Payment
                        .Common()
                        .refund(product.getOutTradeNo(), product.getTotalFee());
                if(ResponseChecker.success(response)){
                    return Constants.SUCCESS;
                }else{
                    logger.error("订单号：{}支付宝退款失败",product.getOutTradeNo());
                    return Constants.FAIL;
                }
        } catch (Exception e) {
            logger.error("支付宝支付退款失败{}",e.getMessage());
            return Constants.FAIL;
        }
	}
    /**
     * 如果你调用的是当面付预下单接口(aliPay.trade.precreate)，调用成功后订单实际上是没有生成，因为创建一笔订单要买家、卖家、金额三要素。
     * 预下单并没有创建订单，所以根据商户订单号操作订单，比如查询或者关闭，会报错订单不存在。
     * 当用户扫码后订单才会创建，用户扫码之前二维码有效期2小时，扫码之后有效期根据timeout_express时间指定。
     * =====只有支付成功后 调用此订单才可以=====
     */
	@Override
	public String aliCloseOrder(Product product) {
		logger.info("订单号："+product.getOutTradeNo()+"支付宝关闭订单");
        try {
            AlipayTradeCloseResponse response = Factory.Payment
                    .Common()
                    .close(product.getOutTradeNo());
            if(ResponseChecker.success(response)){
                return Constants.SUCCESS;
            }else{
                logger.error("订单号：{}支付宝关闭订单失败",product.getOutTradeNo());
                return Constants.FAIL;
            }
        } catch (Exception e) {
            logger.error("支付宝支付关闭订单失败{}",e.getMessage());
            return Constants.FAIL;
        }
	}
	@Override
	public String downloadBillUrl(String billDate,String billType) {
		logger.info("获取支付宝订单地址:"+billDate);
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse response = Factory.Payment
                    .Common()
                    .downloadBill(billType,billDate);
            if(ResponseChecker.success(response)){
                return Constants.SUCCESS;
            }else{
                return Constants.FAIL;
            }
        } catch (Exception e) {
            logger.error("支付宝支付关闭订单失败{}",e.getMessage());
            return Constants.FAIL;
        }
	}

	@Override
	public String aliPayMobile(Product product) {
        try {
            AlipayTradeWapPayResponse response = Factory.Payment
                    .Wap()
                    .pay(product.getSubject(), product.getOutTradeNo(), product.getTotalFee(),"","");
            if (ResponseChecker.success(response)) {
                return response.getBody();
            } else {
                logger.error("支付宝App支付(系统)失败:{}",response.body);
                return Constants.FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
	}
	@Override
	public String aliPayPc(Product product) {
		logger.info("支付宝PC支付下单");
        try {
            String orderNo = IdUtil.getSnowflake(1,1).nextId()+"";
            AlipayTradePagePayResponse response = Factory.Payment.Page()
                    .pay(product.getSubject(), orderNo, product.getTotalFee(),"");
            if (ResponseChecker.success(response)) {
                return Constants.FAIL;
            } else {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.error("企业支付下单失败");
            return Constants.FAIL;
        }
	}
	@Override
	public String appPay(Product product) {
        try {
            AlipayTradeAppPayResponse response = Factory.Payment
                    .App()
                    .pay(product.getSubject(), product.getOutTradeNo(), product.getTotalFee());
            if (ResponseChecker.success(response)) {
                return response.getBody();
            } else {
                logger.error("支付宝App支付(系统)失败:{}",response.body);
                return Constants.FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
	}
}
