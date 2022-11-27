package com.pay.modules.ppay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.pay.common.model.Product;
import com.pay.common.model.Result;
import com.pay.modules.ppay.service.AliPayService;
import com.pay.modules.ppay.util.PayUtils;
import com.yungouos.pay.common.PayException;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService(group = "payCloud", retries = 1, timeout = 10000)
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private PayUtils payUtils;

    @Override
    public Result aliPay(Product product) {
        try {
            Long orderNo = IdUtil.getSnowflake(1,1).nextId();
            product.setOutTradeNo(orderNo+"");
            String result = payUtils.aliPay(product);
            /**
             * 数据库生成订单
             */
            return Result.ok(result);
        }catch (PayException e){
            return Result.error(e.getMessage());
        }
    }
}
