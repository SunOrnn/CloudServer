package com.ornn.wallet.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class AccountVO implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 账号编号
     */
    private String accNo;
    /**
     * 账户类型
     */
    private String accType;
    /**
     * 币种
     */
    private String currency;
    /**
     * 账户余额
     */
    private Integer balance;
    /**
     * 格式化日期显示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
    /**
     * 格式化日期显示
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;
}
