package com.pay.modules.wxpay.service;

import com.pay.common.model.Product;
import com.pay.common.model.Result;

public interface CpWxPayService {
	/**
	 * 微信支付下单(模式二)
	 * 扫码支付 还有模式一 适合固定商品ID 有兴趣的同学可以自行研究
	 * @Author  科帮网
	 * @param product
	 * @return  String
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	Result wxPay2(Product product);
	/**
	 * 微信支付下单(模式一)
	 * @Author  科帮网
	 * @param product  void
	 * @Date	2017年9月5日
	 * 更新日志
	 * 2017年9月5日  科帮网 首次创建
	 *
	 */
	void wxPay1(Product product);
    /**
     * 微信支付退款
     * @Author  科帮网
     * @param product
     * @return  String
     * @Date	2017年7月31日
     * 更新日志
     * 2017年7月31日  科帮网 首次创建
     *
     */
	String wxRefund(Product product);
	/**
	 * 关闭订单
	 * @Author  科帮网
	 * @param product
	 * @return  String
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	String wxCloseOrder(Product product);
	/**
	 * 下载微信账单
	 * @Author  科帮网  void
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	void saveBill();
    /**
     * 微信公众号支付返回一个url地址
     * @Author  科帮网
     * @param product
     * @return  String
     * @Date	2017年7月31日
     * 更新日志
     * 2017年7月31日  科帮网 首次创建
     *
     */
	String wxPayMobile(Product product);
	/**
	 * H5支付 唤醒 微信APP 进行支付
	 * 申请入口：登录商户平台-->产品中心-->我的产品-->支付产品-->H5支付
	 * @Author  科帮网
	 * @param product
	 * @return  String
	 * @Date	2017年8月9日
	 * 更新日志
	 * 2017年8月9日  科帮网 首次创建
	 *
	 */
	String wxPayH5(Product product);
	
	/**
	 * 查询订单
	 * @param product
	 */
	void orderQuery(Product product);
}
