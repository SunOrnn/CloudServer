package com.ornn.payment.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UnifiedPayDTO implements Serializable {
    /**
     * 接入方应用ID
     */
    @NotNull(message = "应用ID不能为空")
    private String appId;
}
