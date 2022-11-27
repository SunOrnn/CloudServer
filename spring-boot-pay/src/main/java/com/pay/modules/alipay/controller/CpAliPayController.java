package com.pay.modules.alipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.pay.common.constants.Constants;
import com.pay.common.model.Product;
import com.pay.modules.alipay.service.CpAliPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 支付宝支付
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 * 创建时间	2017年7月30日
 */
@Api(tags ="支付宝支付")
@Controller
@RequestMapping(value = "aliPay")
public class CpAliPayController {

	private static final Logger logger = LoggerFactory.getLogger(CpAliPayController.class);

	@Autowired
	private CpAliPayService aliPayService;

    /**
     * 电脑支付
     * @param product
     * @param map
     * @return
     */
	@ApiOperation(value="电脑支付")
	@PostMapping(value="pcPay")
    public String  pcPay(Product product,ModelMap map) {
		logger.info("电脑支付");
		String form  =  aliPayService.aliPayPc(product);
		map.addAttribute("form", form);
		return "aliPay/pay";
    }

    /**
     * 手机H5支付
     * @param product
     * @param map
     * @return
     */
	@ApiOperation(value="手机H5支付")
	@PostMapping(value="mobilePay")
    public String  mobilePay(Product product,ModelMap map) {
		logger.info("手机H5支付");
		String form  =  aliPayService.aliPayMobile(product);
		map.addAttribute("form", form);
		return "aliPay/pay";
    }

    /**
     * 扫码支付
     * @param product
     * @param map
     * @return
     */
	@ApiOperation(value="二维码支付")
	@PostMapping(value="qcPay")
    public String  qcPay(Product product,ModelMap map) {
		logger.info("二维码支付");
		String message  =  aliPayService.aliPay(product);
		if(!Constants.FAIL.equals(message)){
			map.addAttribute("img", message);
		}else{

		}
		return "aliPay/qcpay";
    }

	@ApiOperation(value="app支付服务端")
	@PostMapping(value="appPay")
    public String  appPay(Product product,ModelMap map) {
		logger.info("app支付服务端");
		String orderString  =  aliPayService.appPay(product);
		map.addAttribute("orderString", orderString);
		return "aliPay/pay";
    }

    /**
     * 支付宝异步回调
     * @param request
     * @return
     */
	@ApiOperation(value="支付宝支付回调(二维码、H5、网站)")
	@RequestMapping(value="notify",method=RequestMethod.POST)
	public String notify(HttpServletRequest request){
        String  message = "success";
        try {
            Map<String, String> params = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                params.put(parameterName, request.getParameter(parameterName));
            }
            String outTradeNo = request.getParameter("out_trade_no");
            Boolean flag = Factory.Payment.Common().verifyNotify(params);
            if(flag){
                /**
                 * 自行处理业务逻辑
                 */
                logger.info("商户订单号为：{}",outTradeNo);
            }else{
                logger.error("验证签名失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message =  "failed";
        }
        return message;
    }
	
	/**
	 * 支付宝支付PC端前台回调
	 * @Author  科帮网
	 * @param request
	 * @return  String
	 * @Date	2018年11月20日
	 * 更新日志
	 * 2018年11月20日  科帮网 首次创建
	 */
	@RequestMapping(value="/frontRcvResponse",method=RequestMethod.POST)
	public String  frontRcvResponse(HttpServletRequest request){
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = iter.next();
				String[] values = requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//商户订单号
			String orderNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//前台回调验证签名 v1 or v2
            Boolean flag = Factory.Payment.Common().verifyNotify(params);
			if(flag) {
				logger.info("订单号"+orderNo+"验证签名结果[成功].");
				//处理业务逻辑
			}else {
				logger.info("订单号"+orderNo+"验证签名结果[失败].");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//处理异常信息
		}
		//支付成功、跳转到成功页面
		return "success.html";
	}
}
