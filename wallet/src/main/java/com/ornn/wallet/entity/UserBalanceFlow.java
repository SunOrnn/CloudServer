package com.ornn.wallet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * 余额账户流水记录实体类
 */
@Data
@TableName("user_balance_flow")
public class UserBalanceFlow {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 账户变动流水号
     */
    private String flowNo;

    /**
     * 账户编号
     */
    private String accNo;

    /**
     * 业务类型
     */
    private String busiType;

    /**
     * 变动金额
     */
    private Integer amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 变动前的金额
     */
    private Double beginBalance;

    /**
     * 变动后的金额
     */
    private Double endBalance;

    /**
     * 借贷方向
     */
    private String fundDirect;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 创建时间
     */
    private Timestamp createTime;
}
