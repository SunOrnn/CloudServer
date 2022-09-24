package com.ornn.wallet.service.impl;

import com.ornn.wallet.client.PaymentClient;
import com.ornn.wallet.convert.UserBalanceConvert;
import com.ornn.wallet.convert.UserBalanceOrderConvert;
import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.entity.UserBalanceOrder;
import com.ornn.wallet.entity.AddBalance;
import com.ornn.wallet.entity.contant.BusinessCodeEnum;
import com.ornn.wallet.entity.contant.GlobalCodeEnum;
import com.ornn.wallet.entity.contant.TradeType;
import com.ornn.wallet.entity.dto.UnifiedPayDTO;
import com.ornn.wallet.entity.vo.AccountChargeVO;
import com.ornn.wallet.entity.vo.PayNotifyVO;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.dto.PayNotifyDTO;
import com.ornn.wallet.entity.vo.UnifiedPayVO;
import com.ornn.wallet.exception.DaoException;
import com.ornn.wallet.exception.ServiceException;
import com.ornn.wallet.mapper.UserBalanceMapper;
import com.ornn.wallet.mapper.UserBalanceOrderMapper;
import com.ornn.wallet.service.UserAccountTradeService;
import com.ornn.wallet.service.UserBalanceService;
import com.ornn.wallet.util.DateUtils;
import com.ornn.wallet.util.IDutils;
import com.ornn.wallet.util.SnowFlakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserAccountTradeServiceImpl implements UserAccountTradeService {

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private UserBalanceService userBalanceService;

    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Autowired
    private UserBalanceOrderMapper userBalanceOrderMapper;

    /**
     *
     * @param accountChargeDTO
     * @return
     */
    @Override
    public AccountChargeVO chargeOrder(AccountChargeDTO accountChargeDTO) throws IllegalAccessException {
        // 生成电子钱包充值订单信息
        UserBalanceOrder userBalanceOrder = createChargeOrder(accountChargeDTO);
        try {
            userBalanceOrderMapper.insert(userBalanceOrder);
        } catch (Exception e) {
            throw new DaoException(BusinessCodeEnum.BUSI_CHARGE_FAIL_2000.getCode(), BusinessCodeEnum.BUSI_CHARGE_FAIL_2000.getDesc(), e);
        }
        // 调用支付系统接口
        // 构建支付请求参数
        UnifiedPayDTO unifiedPayDTO = buildUnifiedPayDTO(accountChargeDTO, userBalanceOrder);
        ResponseResult<UnifiedPayVO> responseResult = paymentClient.unifiedPay(unifiedPayDTO);
        if (responseResult.getCode().equals(GlobalCodeEnum.GL_SUCC_0000.getCode())) {
            // 支付失败的业务异常返回
            throw new ServiceException(responseResult.getCode(), responseResult.getMessage());
        }
        // 获取支付返回数据
        UnifiedPayVO unifiedPayVO = responseResult.getData();
        // 封装返回的电子钱包充值订单信息
        AccountChargeVO accountChargeVO = UserBalanceOrderConvert.INSTANCE.convertAccountChargeVo(unifiedPayVO);
        accountChargeVO.setUserId(accountChargeDTO.getUserId());

        return accountChargeVO;
    }

    /**
     * 生成电子钱包充值订单信息的私有方法
     * @param accountChargeDTO
     * @return
     */
    private UserBalanceOrder createChargeOrder(AccountChargeDTO accountChargeDTO) throws IllegalAccessException {
        UserBalanceOrder userBalanceOrder = UserBalanceConvert.INSTANCE.convertUserBalanceOrder(accountChargeDTO);
        // 生成电子钱包充值订单流水号
        String orderId = getOrderId();
        userBalanceOrder.setOrderId(orderId);
        // 设置交易类型为“充值”
        userBalanceOrder.setTradeType(TradeType.CHARGE.getCode());
        // 设置支付状态为”待支付“
        userBalanceOrder.setStatus("0");
        // 设置交易时间
        userBalanceOrder.setTradeTime(new Timestamp(System.currentTimeMillis()));
        // 设置订单创建时间
        userBalanceOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 设置订单初始更新时间
        userBalanceOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return userBalanceOrder;
    }

    /**
     * 以特定的规则生成电子钱包充值订单流水号的私有方法
     * @return
     */
    private String getOrderId() throws IllegalAccessException {
        // “雪花生成器”ID生成器
        SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(IDutils.getWorkId(), 1);
        // 以“日期YYYYMMDDHHmmss + 随机生成ID”规则生成充值订单号
        return DateUtils.getStringByFormat(new Date(), DateUtils.sf3) + idGenerator.nextId();
    }

    /**
     * 构建支付系统请求参数对象的私有方法
     * @param accountChargeDTO
     * @param userBalanceOrder
     * @return
     */
    private UnifiedPayDTO buildUnifiedPayDTO(AccountChargeDTO accountChargeDTO, UserBalanceOrder userBalanceOrder) {
        UnifiedPayDTO unifiedPayDTO = new UnifiedPayDTO();
        // 支付系统为接入方分配的应用ID
        unifiedPayDTO.setAppId("10001");
        // 支付业务系统订单号
        unifiedPayDTO.setOrderId(userBalanceOrder.getOrderId());
        // 充值交易类型-余额充值
        unifiedPayDTO.setTradeType("topup");
        // 支付渠道
        unifiedPayDTO.setChannel(accountChargeDTO.getPaymentType());
        // 具体的支付渠道方式
        unifiedPayDTO.setPayType("ALL_PAY_H5");
        // 支付金额
        unifiedPayDTO.setAmount(accountChargeDTO.getAmount());
        // 支付币种
        unifiedPayDTO.setCurrency(accountChargeDTO.getCurrency());
        // 商户用户标识
        unifiedPayDTO.setUserId(String.valueOf(accountChargeDTO.getUserId()));
        // 商品标题
        unifiedPayDTO.setSubject("huawei mate 50");
        // 商品详情
        unifiedPayDTO.setBody("huawei mate 50 test");
        // 支付回调通知地址
        unifiedPayDTO.setNotifyUrl("http://www.baidu.com");
        // 支付结果同步返回URL
        unifiedPayDTO.setReturnUrl("http://www.baidu.com");
        return unifiedPayDTO;
    }

    /**
     * ”电子钱包充值支付回调“接口的业务层方法的实现
     * @param payNotifyDTO
     * @return
     */
    @Override
    public PayNotifyVO receivePayNotify(PayNotifyDTO payNotifyDTO) throws IllegalAccessException {
        // 判断电子钱包充值订单支付状态是否为成功
        Map paramMap = new HashMap<>();
        paramMap.put("order_id", payNotifyDTO.getOrderId());
        List<UserBalanceOrder> userBalanceOrderList = userBalanceMapper.selectByMap(paramMap);

        // 如果电子钱包充值订单不存在，则返回失败结果
        if (CollectionUtils.isEmpty(userBalanceOrderList) && userBalanceOrderList.size() <= 0) {
            return PayNotifyVO.builder().result("fail").build();
        }
        UserBalanceOrder userBalanceOrder = userBalanceOrderList.get(0);

        // 判断电子钱包充值订单的支付状态，如果已经为成功状态，则说明已处理，返回成功结果
        if ("2".equals(userBalanceOrder.getStatus())) {
            return PayNotifyVO.builder().result("success").build();
        }

        // 更新电子钱包订单支付状态成功
        userBalanceOrder.setStatus(String.valueOf(payNotifyDTO.getPayStatus()));

        // 设置订单更新时间
        userBalanceOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        // 更新状态
        userBalanceOrderMapper.updateById(userBalanceOrder);

        // 如果是支付成功回调通知，则完成电子钱包账户余额的增加
        if (payNotifyDTO.getPayStatus() == 2) {
            AddBalance addBalanceBO = AddBalance.builder()
                    .userId(userBalanceOrder.getUserId())
                    .amount(userBalanceOrder.getAmount())
                    .busiType("charge")
                    .accType("0")
                    .currency(userBalanceOrder.getCurrency())
                    .build();
            // 调用电子钱包余额业务层方法增加余额
            userBalanceService.addBalance(addBalanceBO);
        }

        return PayNotifyVO.builder().result("success").build();
    }
}
