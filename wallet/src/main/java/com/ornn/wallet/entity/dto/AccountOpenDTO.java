package com.ornn.wallet.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AccountOpenDTO implements Serializable {
    @NotNull(message = "用户Id不能为空")
    private Long userId;

    private Integer accType;

    private String currency;
}
