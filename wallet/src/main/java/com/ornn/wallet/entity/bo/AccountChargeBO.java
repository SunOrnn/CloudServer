package com.ornn.wallet.entity.bo;

import lombok.Data;

@Data
public class AccountChargeBO {
    private Long userId;

    private Integer amount;

    private String currency;

    private String orderId;

    private String tradeNo;

    private String extraInfo;
}
