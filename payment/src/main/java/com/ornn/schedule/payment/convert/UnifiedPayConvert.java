package com.ornn.schedule.payment.convert;

import com.ornn.schedule.payment.entity.PayOrder;
import com.ornn.schedule.payment.entity.dto.UnifiedPayDTO;
import com.ornn.schedule.payment.entity.vo.UnifiedPayVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnifiedPayConvert {
    UnifiedPayConvert INSTANCE = Mappers.getMapper(UnifiedPayConvert.class);

    /**
     * 生成订单信息
     * @param unifiedPayDTO
     * @return
     */
    @Mappings({})
    UnifiedPayVO unifiedPayDTOConvertUnifiedPayVO(UnifiedPayDTO unifiedPayDTO);

    /**
     * 支付请求参数对象 到 支付订单持久层实体类对象的转换
     * @param unifiedPayDTO
     * @return
     */
    @Mappings({})
    PayOrder unifiedPayDTOConvertPayOrder(UnifiedPayDTO unifiedPayDTO);
}
