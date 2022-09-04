package com.ornn.wallet.entity.dto;

import lombok.Data;

@Data
public class PayNotifyDTO {

    private String orderId;

    private Integer amount;

    private String currency;

    private Integer payStatus;
}
