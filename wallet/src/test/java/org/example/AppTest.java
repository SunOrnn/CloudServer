package org.example;

import static org.junit.Assert.assertTrue;

import com.ornn.wallet.service.UserAccountService;
import com.ornn.wallet.service.UserBalanceService;
import com.ornn.wallet.service.impl.UserAccountServiceImpl;
import com.ornn.wallet.util.DateUtils;
import com.ornn.wallet.util.IDutils;
import com.ornn.wallet.util.SnowFlakeIdGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private String getOrderId() throws IllegalAccessException {
        // “雪花生成器”ID生成器
        SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator(IDutils.getWorkId(), 1);
        // 以“日期YYYYMMDDHHmmss + 随机生成ID”规则生成充值订单号
        return DateUtils.getStringByFormat(new Date(), DateUtils.sf3) + idGenerator.nextId();
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void getId() throws IllegalAccessException {
        System.out.println(getOrderId());
    }
}
