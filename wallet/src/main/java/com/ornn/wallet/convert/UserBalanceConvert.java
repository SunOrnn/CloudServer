package com.ornn.wallet.convert;

import com.ornn.wallet.entity.UserBalance;
import com.ornn.wallet.entity.UserBalanceOrder;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.vo.AccountOpenVO;
import com.ornn.wallet.entity.vo.AccountVO;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface UserBalanceConvert {
    UserBalanceConvert INSTANCE = Mappers.getMapper(UserBalanceConvert.class);

    /**
     * 电子账户开通数据传输对象到数据库对象的转换映射
     *
     * @param accountOpenDTO
     * @return
     */
    @Mappings({})
    UserBalance convertUserBalance(AccountOpenDTO accountOpenDTO);

    /**
     * 根据电子账号信息持久层数据对象转换开户业务层返回参数对象
     */
    @Mappings({})
    AccountOpenVO convertAccountOpenVO(UserBalance userBalance);

    /**
     * 根据电子账户持久层数据库对象转换查询业务层返回参数对象
     */
    @Mappings({})
    List<AccountVO> convertAccountVo(List<UserBalance> userBalanceList);

    @Mappings({})
    UserBalanceOrder convertUserBalanceOrder(AccountChargeDTO accountChargeDTO);
}
