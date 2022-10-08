package com.ornn.schedule.wallet.service;

import com.ornn.schedule.wallet.entity.dto.AccountChargeDTO;
import com.ornn.schedule.wallet.entity.dto.PayNotifyDTO;
import com.ornn.schedule.wallet.entity.vo.AccountChargeVO;
import com.ornn.schedule.wallet.entity.vo.PayNotifyVO;

public interface UserAccountTradeService {

    AccountChargeVO chargeOrder(AccountChargeDTO accountChargeDTO) throws IllegalAccessException;

    PayNotifyVO receivePayNotify(PayNotifyDTO payNotifyDTO) throws IllegalAccessException;
}
