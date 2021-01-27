package com.billow.tools.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 唯一Id生成器基于雪花算法
 *
 * @author liuyongtao
 * @Date 2021/1/10 16:49
 **/
public class SnowFlakeUtil {
    private static Logger log = LoggerFactory.getLogger(SnowFlakeUtil.class);

    private static SnowFlakeUtil flowIdWorker = new SnowFlakeUtil(1);
    private final long id;
    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间
     */
    private final long epoch = 1524291141010L;
    /**
     * 机器标识位数
     */
    private final long workerIdBits = 10L;
    /**
     * 机器ID最大值: 1023
     */
    private final long maxWorkerId = -1L ^ -1L << this.workerIdBits;
    /**
     * 毫秒内自增位
     */
    private final long sequenceBits = 12L;

    /**
     * 12
     */
    private final long workerIdShift = this.sequenceBits;
    /**
     * 22
     */
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    /**
     * 4095,111111111111,12位
     */
    private final long sequenceMask = -1L ^ -1L << this.sequenceBits;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private SnowFlakeUtil(long id) {
        if (id > this.maxWorkerId || id < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.id = id;
    }

    public static SnowFlakeUtil getFlowIdInstance() {
        return flowIdWorker;
    }

    /**
     * 获得系统当前毫秒数
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (this.lastTimestamp == timestamp) {
            //如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                // 重新生成timestamp
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }

        if (timestamp < this.lastTimestamp) {
            log.error(String.format("clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
            return -1;
        }

        this.lastTimestamp = timestamp;
        return timestamp - this.epoch << this.timestampLeftShift | this.id << this.workerIdShift | this.sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            SnowFlakeUtil snowFlakeUtil = SnowFlakeUtil.getFlowIdInstance();
            System.out.println(snowFlakeUtil.nextId());
        }
    }
}