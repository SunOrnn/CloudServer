package com.pay.modules.ppay.controller;

import com.pay.common.model.Product;
import com.pay.common.model.Result;
import com.pay.modules.ppay.service.AliPayService;
import com.pay.modules.ppay.util.PayUtils;
import com.yungouos.pay.util.PaySignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * 个人支付宝支付
 * 爪哇笔记 https://blog.52itstyle.vip
 * 个人也可申请，微信、支付宝官方直连结算。
 * 快来看看吧！https://mmbizurl.cn/s/hEludsCNs
 * @author 小柒2012
 */
@RestController
@RequestMapping("/ppay")
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private PayUtils payUtils;

    /**
     * 生成支付二维码
     * @return
     */
    @PostMapping(value="aliPay")
    public Result wxPay(Product product) {
        return aliPayService.aliPay(product);
    }

    /**
     * 支付宝支付异步回调
     * @param request
     * @return
     */
    @RequestMapping(value="aliNotify")
    public String aliNotify(HttpServletRequest request) {
        try {
            String key = payUtils.getAliPayProperties().getKey();
            boolean flag = PaySignUtil.checkNotifySign(request,key);
            if(flag){
                String outTradeNo = request.getParameter("outTradeNo");
                System.out.println("订单号为："+outTradeNo);
                /**
                 * 数据库订单相关操作
                 */
                return "SUCCESS";
            }else{
                return "ERROR";
            }
        } catch (Exception e) {
            return "ERROR";
        }
    }
}
