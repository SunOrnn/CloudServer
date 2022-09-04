package com.ornn.wallet.entity.dto;

import lombok.Data;

@Data
public class AccountQueryDTO {
    private Long ueerId;

    private Integer accType;

    private String currency;
}
