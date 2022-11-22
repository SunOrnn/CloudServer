package com.ornn.wallet.service;

import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.dto.PayNotifyDTO;
import com.ornn.wallet.entity.vo.AccountChargeVO;
import com.ornn.wallet.entity.vo.PayNotifyVO;

public interface UserAccountTradeService {

    AccountChargeVO chargeOrder(AccountChargeDTO accountChargeDTO) throws IllegalAccessException;

    PayNotifyVO receivePayNotify(PayNotifyDTO payNotifyDTO) throws IllegalAccessException;
}
