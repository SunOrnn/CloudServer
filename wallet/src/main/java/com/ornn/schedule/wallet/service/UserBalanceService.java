package com.ornn.schedule.wallet.service;

import com.ornn.schedule.wallet.entity.AddBalance;

public interface UserBalanceService {

    boolean addBalance(AddBalance addBalanceBo) throws IllegalAccessException;
}
