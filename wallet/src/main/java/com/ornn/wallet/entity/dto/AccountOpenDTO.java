package com.ornn.wallet.entity.dto;

import com.ornn.wallet.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AccountOpenDTO implements Serializable {
    /**
     * 用户ID
     */
    @NotNull(message = "用户Id不能为空")
    private Long userId;
    /**
     * 账户类型：0-现金账户；1-赠送账户
     */
    private Integer accType;
    /**
     * 账户币种（仅支持人民币、美元两种账户）
     */
    @EnumValue(strValues = {"CNY", "USD"})
    private String currency;
}
