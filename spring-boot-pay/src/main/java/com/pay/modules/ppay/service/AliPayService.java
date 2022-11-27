package com.pay.modules.ppay.service;

import com.pay.common.model.Product;
import com.pay.common.model.Result;

/**
 * 支付宝支付
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
public interface AliPayService {

    Result aliPay(Product product);

}
