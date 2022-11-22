package com.ornn.payment.convert;

import com.ornn.payment.entity.PayNotify;
import com.ornn.payment.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author: CANHUI.WANG * @create: 2022-09-27
 */

@Mapper
public interface PayNotifyConvert {
    PayNotifyConvert INSTANCE = Mappers.getMapper(PayNotifyConvert.class);

    /**
     * 支付结果通知报文日志信息的转换方法
     * @param payOrder
     * @return
     */
    @Mappings({})
    PayNotify convertPayNotify(PayOrder payOrder);
}
