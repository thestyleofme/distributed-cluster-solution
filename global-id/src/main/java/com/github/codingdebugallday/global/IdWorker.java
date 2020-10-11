package com.github.codingdebugallday.global;

/**
 * <p>
 * 雪花算法实现
 * </p>
 *
 * @author isaac 2020/10/12 0:27
 * @since 1.0.0
 */
public class IdWorker {

    /**
     * 下面两个每个5位，加起来就是10位的工作机器id
     * 工作id
     * 数据id
     */
    private final long workerId;
    private final long datacenterId;
    /**
     * 12位的序列号
     */
    private long sequence;

    /**
     * 初始时间戳
     */
    private static final long INIT_EPOCH = 1288834974657L;
    /**
     * 序列号id长度
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 长度为5位
     */
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATACENTER_ID_BITS = 5L;

    /**
     * 序列号最大值
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 工作id需要左移的位数，12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据id需要左移位数 12+5=17位
     */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间戳需要左移位数 12+5+5=22位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /**
     * 上次时间戳，初始值为负数
     */
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId

        /*
         * 最大值
         */
        long maxWorkerId = ~(-1L << WORKER_ID_BITS);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << DATACENTER_ID_BITS);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                TIMESTAMP_LEFT_SHIFT, DATACENTER_ID_BITS, WORKER_ID_BITS, SEQUENCE_BITS, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 下一个ID生成算法
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 获取当前时间戳如果等于上次时间戳
        // 说明：还处在同一毫秒内，则在序列号加1；否则序列号赋值为0，从0开始。
        if (lastTimestamp == timestamp) {
            // 0  - 4095
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        // 将上次时间戳值刷新
        lastTimestamp = timestamp;

        /*
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - INIT_EPOCH) << TIMESTAMP_LEFT_SHIFT) |
                (datacenterId << DATACENTER_ID_SHIFT) |
                (workerId << WORKER_ID_SHIFT) |
                sequence;
    }

    /**
     * 获取时间戳，并与上次时间戳比较
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        // workerId datacenterId 0-2^5-1 -> 0 - 31
        // sequence 0-2^12-1 -> 0-4095
        IdWorker worker = new IdWorker(21, 10, 0);
        for (int i = 0; i < 100; i++) {
            System.out.println(worker.nextId());
        }
    }

}