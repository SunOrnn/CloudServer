package com.ornn.wallet.service.impl;

import com.ornn.wallet.entity.AddBalance;
import com.ornn.wallet.entity.UserBalance;
import com.ornn.wallet.entity.UserBalanceFlow;
import com.ornn.wallet.mapper.UserBalanceFlowMapper;
import com.ornn.wallet.mapper.UserBalanceMapper;
import com.ornn.wallet.service.UserBalanceService;
import com.ornn.wallet.util.DateUtils;
import com.ornn.wallet.util.IDutils;
import com.ornn.wallet.util.SnowFlakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserBalanceFlowMapper userBalanceFlowMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addBalance(AddBalance addBalance) throws IllegalAccessException {
        // 查询电子钱包账户余额
        Map param = new HashMap<>();
        param.put("user_id", addBalance.getUserId());
        param.put("acc_type", addBalance.getAccType());
        List<UserBalance> userBalanceList = userBalanceMapper.selectByMap(param);
        UserBalance userBalance = userBalanceList.get(0);
        userBalance.setBalance(userBalance.getBalance() + addBalance.getAmount());
        userBalance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userBalanceMapper.updateById(userBalance);
        // 生成电子钱包账户变动流水记录
        UserBalanceFlow userBalanceFlow = createUserBalanceFlow(addBalance, userBalance);
        // 持久层入库处理
        userBalanceFlowMapper.insert(userBalanceFlow);
        return true;
    }

    /**
     * 生成电子钱包账户变动流水记录
     * @param addBalance
     * @param userBalance
     * @return
     */
    private UserBalanceFlow createUserBalanceFlow(AddBalance addBalance, UserBalance userBalance) throws IllegalAccessException {
        UserBalanceFlow userBalanceFlow = new UserBalanceFlow();
        userBalanceFlow.setUserId(addBalance.getUserId());
        // 设置账户变动流水号
        userBalanceFlow.setFlowNo(getFlowId());
        // 记录账户编号
        userBalanceFlow.setAccNo(userBalance.getAccNo());
        // 记录业务类型
        userBalanceFlow.setBusiType(addBalance.getBusiType());
        // 记录变动金额
        userBalanceFlow.setAmount(addBalance.getAmount());
        // 币种
        userBalanceFlow.setCurrency(userBalanceFlow.getCurrency());
        // 记录账户变动前的金额
        userBalanceFlow.setBeginBalance(userBalance.getBalance() - addBalance.getAmount());
        // 记录账户变动后的金额
        userBalanceFlow.setEndBalance(userBalanceFlow.getEndBalance());
        // 借贷方向，借方账户
        userBalanceFlow.setFundDirect("00");
        // 设置创建时间
        userBalanceFlow.setCreateTime(new Timestamp((System.currentTimeMillis())));
        // 设置更新时间
        userBalanceFlow.setUpdateTime(new Timestamp((System.currentTimeMillis())));
        return  userBalanceFlow;
    }

    /**
     * 以特定的规则生成电子钱包账户变动流水号的私有方法
     * @return
     */
    private String getFlowId() throws IllegalAccessException {
        // “雪花生成器”ID生成器
        SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(IDutils.getWorkId(), 1);
        // 以“日期YYYYMMDDHHmmss + 随机生成ID”规则生成充值订单号
        return DateUtils.getStringByFormat(new Date(), DateUtils.sf3) + idGenerator.nextId();
    }
}
