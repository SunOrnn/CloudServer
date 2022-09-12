package com.ornn.wallet.service.impl;

import com.ornn.wallet.entity.AddBalance;
import com.ornn.wallet.entity.UserBalance;
import com.ornn.wallet.entity.UserBalanceFlow;
import com.ornn.wallet.mapper.UserBalanceFlowMapper;
import com.ornn.wallet.mapper.UserBalanceMapper;
import com.ornn.wallet.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserBalanceServiceImpl implements UserBalanceService {

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserBalanceFlowMapper userBalanceFlowMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addBalance(AddBalance addBalance) {
        return false;
    }

    /**
     * 生成电子钱包账户变动流水记录
     * @param addBalance
     * @param userBalance
     * @return
     */
    private UserBalanceFlow createUserBalanceFlow(AddBalance addBalance, UserBalance userBalance) {
        return  null;
    }

    /**
     * 以特定的规则生成电子钱包账户变动流水号的私有方法
     * @return
     */
    private String getFlowId() {
        return null;
    }
}
