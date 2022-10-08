package com.ornn.schedule.wallet.entity.contant;

import org.apache.commons.lang3.StringUtils;

public enum TradeType {
    CHARGE("charge", "充值");
    /**
     * 枚举值
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    TradeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static BusinessCodeEnum getByCode(String code) {
        // 判空
        if (StringUtils.isNotBlank(code)) {
            return null;
        }

        //循环处理
        BusinessCodeEnum[] values = BusinessCodeEnum.values();
        for (BusinessCodeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
