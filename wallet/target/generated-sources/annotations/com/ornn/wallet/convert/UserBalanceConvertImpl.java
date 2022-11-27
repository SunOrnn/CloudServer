package com.ornn.wallet.convert;

import com.ornn.wallet.entity.UserBalance;
import com.ornn.wallet.entity.UserBalanceOrder;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.vo.AccountOpenVO;
import com.ornn.wallet.entity.vo.AccountVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-27T19:00:36+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
public class UserBalanceConvertImpl implements UserBalanceConvert {

    @Override
    public UserBalance convertUserBalance(AccountOpenDTO accountOpenDTO) {
        if ( accountOpenDTO == null ) {
            return null;
        }

        UserBalance userBalance = new UserBalance();

        userBalance.setUserId( accountOpenDTO.getUserId() );
        if ( accountOpenDTO.getAccType() != null ) {
            userBalance.setAccType( String.valueOf( accountOpenDTO.getAccType() ) );
        }
        userBalance.setCurrency( accountOpenDTO.getCurrency() );

        return userBalance;
    }

    @Override
    public AccountOpenVO convertAccountOpenVO(UserBalance userBalance) {
        if ( userBalance == null ) {
            return null;
        }

        AccountOpenVO accountOpenVO = new AccountOpenVO();

        accountOpenVO.setUserId( userBalance.getUserId() );
        accountOpenVO.setAccNo( userBalance.getAccNo() );
        accountOpenVO.setAccType( userBalance.getAccType() );
        accountOpenVO.setCurrency( userBalance.getCurrency() );

        return accountOpenVO;
    }

    @Override
    public List<AccountVO> convertAccountVo(List<UserBalance> userBalanceList) {
        if ( userBalanceList == null ) {
            return null;
        }

        List<AccountVO> list = new ArrayList<AccountVO>( userBalanceList.size() );
        for ( UserBalance userBalance : userBalanceList ) {
            list.add( userBalanceToAccountVO( userBalance ) );
        }

        return list;
    }

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

    protected AccountVO userBalanceToAccountVO(UserBalance userBalance) {
        if ( userBalance == null ) {
            return null;
        }

        AccountVO accountVO = new AccountVO();

        accountVO.setId( userBalance.getId() );
        accountVO.setUserId( userBalance.getUserId() );
        accountVO.setAccNo( userBalance.getAccNo() );
        accountVO.setAccType( userBalance.getAccType() );
        accountVO.setCurrency( userBalance.getCurrency() );
        if ( userBalance.getBalance() != null ) {
            accountVO.setBalance( userBalance.getBalance().intValue() );
        }
        accountVO.setCreateTime( userBalance.getCreateTime() );
        accountVO.setUpdateTime( userBalance.getUpdateTime() );

        return accountVO;
    }
}
