package com.ornn.wallet.entity.dto;

import lombok.Data;

@Data
public class AccountChargeDTO {
    private Long userId;

    private Integer amount;

    private String currency;

    private Integer paymentType;

    private Integer isRenew;
}
