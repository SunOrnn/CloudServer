package com.ornn.wallet.convert;

import com.ornn.wallet.entity.UserBalanceOrder;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.vo.AccountChargeVO;
import com.ornn.wallet.entity.vo.UnifiedPayVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-27T19:00:36+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
public class UserBalanceOrderConvertImpl implements UserBalanceOrderConvert {

    @Override
    public UserBalanceOrder convertUserBalanceOrder(AccountChargeDTO accountChargeDTO) {
        if ( accountChargeDTO == null ) {
            return null;
        }

        UserBalanceOrder userBalanceOrder = new UserBalanceOrder();

        if ( accountChargeDTO.getUserId() != null ) {
            userBalanceOrder.setUserId( String.valueOf( accountChargeDTO.getUserId() ) );
        }
        if ( accountChargeDTO.getAmount() != null ) {
            userBalanceOrder.setAmount( accountChargeDTO.getAmount().intValue() );
        }
        userBalanceOrder.setCurrency( accountChargeDTO.getCurrency() );
        userBalanceOrder.setIsRenew( accountChargeDTO.getIsRenew() );

        return userBalanceOrder;
    }

    @Override
    public AccountChargeVO convertAccountChargeVo(UnifiedPayVO unifiedPayVO) {
        if ( unifiedPayVO == null ) {
            return null;
        }

        AccountChargeVO accountChargeVO = new AccountChargeVO();

        if ( unifiedPayVO.getAmount() != null ) {
            accountChargeVO.setAmount( unifiedPayVO.getAmount().intValue() );
        }
        accountChargeVO.setCurrency( unifiedPayVO.getCurrency() );
        accountChargeVO.setOrderId( unifiedPayVO.getOrderId() );
        accountChargeVO.setTradeNo( unifiedPayVO.getTradeNo() );
        accountChargeVO.setExtraInfo( unifiedPayVO.getExtraInfo() );

        return accountChargeVO;
    }
}
