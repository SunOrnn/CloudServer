package com.ornn.schedule.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 余额账户信息实体类
 */
@Data
@TableName("user_balance")
public class UserBalance {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     *用户编号
     */
    private Long userId;

    /**
     * 账户编号
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
    private Double balance;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
