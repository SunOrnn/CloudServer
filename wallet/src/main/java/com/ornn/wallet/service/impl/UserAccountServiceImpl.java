package com.ornn.wallet.service.impl;

import com.ornn.wallet.convert.UserBalanceConvert;
import com.ornn.wallet.entity.UserBalance;
import com.ornn.wallet.entity.contant.BusinessCodeEnum;
import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.dto.AccountQueryDTO;
import com.ornn.wallet.entity.vo.AccountOpenVO;
import com.ornn.wallet.entity.vo.AccountVO;
import com.ornn.wallet.exception.ServiceException;
import com.ornn.wallet.mapper.UserBalanceMapper;
import com.ornn.wallet.service.UserAccountService;
import com.ornn.wallet.util.DateUtils;
import com.ornn.wallet.util.IDutils;
import com.ornn.wallet.util.SnowFlakeIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Override
    public AccountOpenVO openAcc(AccountOpenDTO accountOpenDTO) throws IllegalAccessException {
        // 判断在同一个用户ID下是否存在同一种类型的账户
        Map parmaMap = new HashMap<>();
        parmaMap.put("user_id", accountOpenDTO.getUserId());
        parmaMap.put("acc_type", accountOpenDTO.getAccType());
        List<UserBalance> userBalanceList = userBalanceMapper.selectByMap(parmaMap);
        if (!CollectionUtils.isEmpty(userBalanceList) && userBalanceList.size() > 0) {
            throw new ServiceException(BusinessCodeEnum.BUSI_ACCOUNT_FAIL_1000.getCode(), BusinessCodeEnum.BUSI_ACCOUNT_FAIL_1000.getDesc());
        }

        // 将“业务层输出的数据对象”转换为“持久层数据对象”
        UserBalance userBalance = UserBalanceConvert.INSTANCE.convertUserBalance(accountOpenDTO);
        // 生成电子账户编号
        String accountNo = getAccountNo();
        userBalance.setAccNo(accountNo);
        // 设置电子钱包账户的初始余额
        userBalance.setBalance(0D);
        // 设置时间值
        userBalance.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userBalance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        // 通过持久层组件将电子钱包账户信息写入数据库
        userBalanceMapper.insert(userBalance);
        // 封装返回业务层输出的数据对象
        AccountOpenVO accountOpenVO = UserBalanceConvert.INSTANCE.convertAccountOpenVO(userBalance);
        return accountOpenVO;
    }

    @Override
    public List<AccountVO> queryAcc(AccountQueryDTO accountQueryDTO) {
        // 组合电子钱包账户查询条件
        Map paramMap = new HashMap<>();
        paramMap.put("user_id", accountQueryDTO.getUserId());
        if (accountQueryDTO.getAccType() != null) {
            paramMap.put("acc_type", accountQueryDTO.getAccType());
        }

        if (StringUtils.isNotBlank(accountQueryDTO.getCurrency())) {
            paramMap.put("currency", accountQueryDTO.getCurrency());
        }

        List<AccountVO> accountVOS = new ArrayList<>();
        List<UserBalance> userBalanceList = userBalanceMapper.selectByMap(paramMap);

        if (!CollectionUtils.isEmpty(userBalanceList) && userBalanceList.size() > 0) {
            accountVOS = UserBalanceConvert.INSTANCE.convertAccountVo(userBalanceList);
        }

        return accountVOS;
    }

    /**
     * 生成电子钱包账户编号
     */
    private String getAccountNo() throws IllegalAccessException {
        // “雪花生成器”ID生成器
        SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(IDutils.getWorkId(), 1);
        // 以“日期YYYYMMDDHHmmss + 随机生成ID”规则生成充值订单号
        return DateUtils.getStringByFormat(new Date(), DateUtils.sf3) + idGenerator.nextId();

    }
}
