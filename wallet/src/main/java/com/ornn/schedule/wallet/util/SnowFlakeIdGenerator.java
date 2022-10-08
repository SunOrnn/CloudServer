package com.ornn.schedule.wallet.util;

public class SnowFlakeIdGenerator {
    /**
     * 初始时间
     */
    private static final long INITIAL_TIME_STAMP = 1546272000000L;
    /**
     * 机器ID所占的位数
     */
    private static final long WORKER_ID_BITS = 10L;
    /**
     * 数据标识ID所占的位数
     */
    private static final long DATACENTER_ID_BITS = 5L;
    /**
     * 支持的最大机器ID，结果是31（这个位移算法可以很快计算出几位二进制数所能表示的最大十进制数）
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 支持的最大数据标识ID，结果是31
     */
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    /**
     * 序列在ID中占的位数
     */
    private final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID的偏移量（12）
     */
    private final long WORKERID_OFFSET = SEQUENCE_BITS;
    /**
     * 数据中心ID的偏移量（12+5）
     */
    private final long DATACENTERID_OFFSET = SEQUENCE_BITS + SEQUENCE_BITS;
    /**
     * 时间截的偏移量（12+5+5）
     */
    private final long TIMESTAMP_OFFSET = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    /**
     * 生成序列的掩码，这里为4095（0b111111111111=0xfff=4095）
     */
    private final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作节点ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID（0~31）
     */
    private long datacenterId;
    /**
     * 毫秒内序列（0~4095）
     */
    private long sequence = 0L;
    /**
     * 上一次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     * @param workerId
     * @param datacenterId
     */
    public SnowFlakeIdGenerator(long workerId, long datacenterId) throws IllegalAccessException {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalAccessException(String.format("WorkerID 不能大于%d或小于0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalAccessException(String.format("DataCenterID 不能大于%d或小于0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 用于获取下一个ID（用同步锁保证线程安全）
     * @return
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳，则说明系统时钟存在问题，应抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for %d milliseconds!");
        }
        // 如果同一时间生成的，则以ms为单位进行序列计算
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 如果sequence等于0，则说明毫秒内序列已经增长到最大值
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else{
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        // 上一次shengchengID的时间截
        lastTimestamp = timestamp;
        // 通过移位或运算，将结果拼到一起组成64位的ID
        return ((timestamp - INITIAL_TIME_STAMP) << TIMESTAMP_OFFSET) | (datacenterId << DATACENTERID_OFFSET) | (workerId << WORKERID_OFFSET) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上一次生成ID的时间截
     * @return 当前时间截
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
