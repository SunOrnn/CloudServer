package com.ornn.schedule.wallet.service;

import com.ornn.schedule.wallet.entity.dto.AccountOpenDTO;
import com.ornn.schedule.wallet.entity.dto.AccountQueryDTO;
import com.ornn.schedule.wallet.entity.vo.AccountOpenVO;
import com.ornn.schedule.wallet.entity.vo.AccountVO;

import java.util.List;

public interface UserAccountService {
    /**
     * 定义”电子钱包开户“接口的业务层方法
     * @param accountOpenDTO
     * @return
     */
    AccountOpenVO openAcc(AccountOpenDTO accountOpenDTO) throws IllegalAccessException;

    /**
     * 定义”电子钱包“接口的业务层方法
     * @param accountQueryDTO
     * @return
     */
    List<AccountVO> queryAcc(AccountQueryDTO accountQueryDTO);
}
