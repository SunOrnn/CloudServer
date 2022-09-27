package com.ornn.payment.service.impl;

import com.ornn.payment.convert.UnifiedPayConvert;
import com.ornn.payment.entity.PayOrder;
import com.ornn.payment.entity.constant.BusinessCodeEnum;
import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.entity.vo.UnifiedPayVO;
import com.ornn.payment.exception.ServiceException;
import com.ornn.payment.mapper.PayOrderMapper;
import com.ornn.payment.service.PayChannelService;
import com.ornn.payment.service.PayChannelServiceFactory;
import com.ornn.payment.service.PayService;
import com.ornn.payment.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.unit.DataUnit;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    /**
     * 定义分布式锁Redis锁的前缀
     */
    public final String redisLockPrefix = "pay-order&";

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Override
    public UnifiedPayVO unifiedPay(UnifiedPayDTO unifiedPayDTO) {
        // 返回数据对象
        UnifiedPayVO unifiedPayVO = null;
        // 创建Redis分布式锁
        // 支付防并发安全逻辑-通过“前缀 + 接入方业务订单号”获取Redis分布式锁（同一笔订单，同一时刻只允许一个线程处理）
        Lock lock = redisLockRegistry.obtain(redisLockPrefix + unifiedPayDTO.getOrderId());
        // 持有锁，等待时间为1s
        boolean isLock = false;
        try {
            isLock = lock.tryLock(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (isLock) {
            // 数据库级别订单状态防重判断
            boolean isRepeatPayOrder = isSuccessPayOrder(unifiedPayDTO);
            if (isRepeatPayOrder) {
                throw new ServiceException(BusinessCodeEnum.BUSI_PAY_FAIL_1000.getCode(), BusinessCodeEnum.BUSI_PAY_FAIL_1000.getDesc());
            }
            // 支付订单入库
            String payId = this.payOrderSave(unifiedPayDTO);
            // 获取具体的支付渠道服务类的实例
            PayChannelService payChannelService = payChannelServiceFactory.createPayChannelService(unifiedPayDTO.getChannel());
            // 调用渠道支付方法设置支付平台订单流水号
            unifiedPayDTO.setOrderId(payId);
            unifiedPayVO = payChannelService.pay(unifiedPayDTO);
            // 释放分布式锁
            lock.unlock();
        } else {
            // 如果持有锁超时，则说明请求正在被处理，提示用户稍后重试
            throw new ServiceException(BusinessCodeEnum.BUSI_PAY_FAIL_1001.getCode(), BusinessCodeEnum.BUSI_PAY_FAIL_1001.getDesc());
        }

        return unifiedPayVO;
    }

    /**
     * 从数据库级别判断是否为成功支付订单的私有方法
     * @param unifiedPayDTO
     * @return
     */
    private boolean isSuccessPayOrder(UnifiedPayDTO unifiedPayDTO) {
        Map<String, Object> parm = new HashMap<>();
        parm.put("order_id", unifiedPayDTO.getOrderId());
        List<PayOrder> payOrderList = payOrderMapper.selectByMap(parm);
        if (!CollectionUtils.isEmpty(payOrderList) && payOrderList.size() > 0) {
            // 判断订单在支付订单中是否存在支付状态为“成功”的订单，若存在，则不处理新的支付请求
            List<PayOrder> successPayOrderList = payOrderList.stream().filter(o -> "2".equals(o.getStatus())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(successPayOrderList) && successPayOrderList.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 支付订单入库方法
     * @param unifiedPayDTO
     * @return
     */
    private String payOrderSave(UnifiedPayDTO unifiedPayDTO) {
        // MapStruct工具进行实体对象类型转换\
        PayOrder payOrder = UnifiedPayConvert.INSTANCE.unifiedPayDTOConvertPayOrder(unifiedPayDTO);
        // 设置支付状态为“待支付”
        payOrder.setStatus("0");
        // 生成支付平台流水号
        String payId = createPayId();
        payOrder.setPayId(payId);
        // 订单创建时间
        payOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 订单更新时间
        payOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        // 订单入库操作
        payOrderMapper.insert(payOrder);
        return payOrder.getPayId();
    }

    /**
     * 生成支付平台订单
     * @return
     */
    private String createPayId() {
        // 获取10000~99999的随机数
        Integer random = new Random().nextInt(99999) % (99999 - 10000 + 1) + 10000;

        // 时间戳 + 随机数
        String payId = DateUtils.getStringByFormat(new Date(), DateUtils.sf3) + String.valueOf(random);
        return  payId;
    }
}
