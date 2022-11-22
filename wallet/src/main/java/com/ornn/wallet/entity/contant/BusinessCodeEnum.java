package com.ornn.wallet.entity.contant;

import org.apache.commons.lang3.StringUtils;

public enum BusinessCodeEnum {
    /**
     * 电子钱包信息管理返回码的定义
     */
    BUSI_ACCOUNT_FAIL_1000(1000, "该用户已开通该类型电子账户"),
    /**
     * 电子钱包交易相关返回码的定义
     */
    BUSI_CHARGE_FAIL_2000(2000, "充值失败"),
    /**
     * 支付服务熔断故障
     */
    BUSI_PAY_FALL_2001(2001, "支付系统故障，请稍后重试");
    /**
     * 编码
     */
    private Integer code;
    /**
     * 描述
     */
    private String desc;

    BusinessCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc =desc;
    }

    public static  BusinessCodeEnum getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        // 循环处理
        BusinessCodeEnum[] values = BusinessCodeEnum.values();

        for (BusinessCodeEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
