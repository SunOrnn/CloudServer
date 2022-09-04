package com.ornn.wallet.service;

import com.ornn.wallet.entity.bo.AccountBO;
import com.ornn.wallet.entity.bo.AccountOpenBO;
import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.dto.AccountQueryDTO;

import java.util.List;

public interface UserAccountService {

    AccountOpenBO openAcc(AccountOpenDTO accountOpenDTO);

    List<AccountBO> queryAcc(AccountQueryDTO accountQueryDTO);
}
