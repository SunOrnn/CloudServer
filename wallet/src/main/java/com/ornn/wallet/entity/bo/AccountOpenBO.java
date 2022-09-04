package com.ornn.wallet.entity.bo;

import lombok.Data;

@Data
public class AccountOpenBO {
    private Long userId;

    private String accNo;

    private String accType;

    private String currency;
}
