package com.ornn.wallet.convert;

import com.ornn.wallet.entity.UserBalanceOrder;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.vo.AccountChargeVO;
import com.ornn.wallet.entity.vo.UnifiedPayVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserBalanceOrderConvert {

    UserBalanceOrderConvert INSTANCE = Mappers.getMapper(UserBalanceOrderConvert.class);

    /**
     * 充值订单数据生成转换方法
     *
     * @param accountChargeDTO
     * @return
     */
    @Mappings({})
    UserBalanceOrder convertUserBalanceOrder(AccountChargeDTO accountChargeDTO);

    /**
     * 充值订单业务层返回数据生成转换方法
     *
     * @param unifiedPayBO
     * @return
     */
    @Mappings({})
    AccountChargeVO convertAccountChargeVo(UnifiedPayVO unifiedPayVO);
}
