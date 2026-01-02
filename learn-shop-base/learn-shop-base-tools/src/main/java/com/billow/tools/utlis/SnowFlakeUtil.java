package com.billow.tools.utlis;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>雪花算法 ID 生成器。
 *
 * <ul>
 *     <li>最高 1 位固定值 0，因为生成的 ID 是正整数；
 *     <li>接下来 41 位存储毫秒级时间戳，2 ^ 41 / ( 1000 * 60 * 60 * 24 * 365) = 69，大概可以使用 69 年；
 *     <li>再接下 10 位存储机器码，包括 5 位 dataCenterId 和 5 位 workerId，最多可以部署 2 ^ 10 = 1024 台机器；
 *     <li>最后 12 位存储序列号，同一毫秒时间戳时，通过这个递增的序列号来区分，即对于同一台机器而言，同一毫秒时间戳下，可以生成 2 ^ 12 = 4096 个不重复 ID。
 * </ul>
 *
 * <p>优化自开源项目：<a href="https://gitee.com/yu120/sequence">Sequence</a>
 *
 * @author liuyongtao
 * @Date 2021/1/10 16:49
 */
public class SnowFlakeUtil {
    private static Logger log = LoggerFactory.getLogger(SnowFlakeUtil.class);

    private static SnowFlakeUtil flowIdWorker = new SnowFlakeUtil();

    public static SnowFlakeUtil getFlowIdInstance() {
        return flowIdWorker;
    }

    /**
     * 工作机器 ID 占用的位数（5bit）。
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据中心 ID 占用的位数（5bit）。
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    /**
     * 序号占用的位数（12bit）。
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 工作机器 ID 占用 5bit 时的最大值 31。
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 数据中心 ID 占用 5bit 时的最大值 31。
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 序号掩码，用于与自增后的序列号进行位“与”操作，如果值为 0，则代表自增后的序列号超过了 4095。
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作机器 ID 位需要左移的位数（12bit）。
     */
    private static final long WORK_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据中心 ID 位需要左移的位数（12bit + 5bit）。
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间戳需要左移的位数（12bit + 5bit + 5bit）。
     */
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    /**
     * 时间起始标记点，一旦确定不能变动（2023-04-02 13:01:00）。
     */
    private static long twepoch = 1680411660000L;
    /**
     * 可容忍的时间偏移量。
     */
    private static long offsetPeriod = 5L;

    /**
     * 工作机器 ID。
     */
    private final long workerId;
    /**
     * 数据中心 ID。
     */
    private final long dataCenterId;

    /**
     * IP 地址信息，用来生成工作机器 ID 和数据中心 ID。
     */
    protected InetAddress address;
    /**
     * 同一毫秒内的最新序号，最大值可为（2^12 - 1 = 4095）。
     */
    private long sequence;
    /**
     * 上次生产 ID 时间戳。
     */
    private long lastTimeMillis = -1L;

    /**
     * 雪花算法 ID 生成器。
     */
    public SnowFlakeUtil() {
        this(null);
    }

    /**
     * 根据 IP 地址计算数据中心 ID 和工作机器 ID 生成数据库 ID。
     *
     * @param address IP 地址
     */
    public SnowFlakeUtil(InetAddress address) {
        this.address = address;
        this.dataCenterId = getDataCenterId(MAX_DATA_CENTER_ID);
        this.workerId = getWorkerId(dataCenterId, MAX_WORKER_ID);
    }

    /**
     * 根据数据中心 ID 和工作机器 ID 生成数据库 ID。
     *
     * @param workerId     工作机器 ID
     * @param dataCenterId 数据中心 ID
     */
    public SnowFlakeUtil(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("workerId must be greater than 0 and less than %d.", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("dataCenterId must be greater than 0 and less than %d.", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 根据 MAC + PID 的 hashCode 获取 16 个低位生成工作机器 ID。
     */
    protected long getWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder mpId = new StringBuilder();
        mpId.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StrUtil.isNotBlank(name)) {
            // GET jvmPid
            mpId.append(name.split("@")[0]);
        }
        // MAC + PID 的 hashCode 获取16个低位
        return (mpId.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 根据网卡 MAC 地址计算余数作为数据中心 ID。
     */
    protected long getDataCenterId(long maxDataCenterId) {
        long id = 1L;
        try {
            if (address == null) {
                address = InetAddress.getLocalHost();
            }
            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            if (null != network) {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 2]) | (0x0000FF00 & (((long) mac[mac.length - 1]) << 8))) >> 6;
                    id = id % (maxDataCenterId + 1);
                }
            }
        } catch (Exception e) {
        }
        return id;
    }

    /**
     * 获取下一个 ID。
     */
    public synchronized long nextId() {
        long currentTimeMillis = System.currentTimeMillis();
        // 当前时间小于上一次生成 ID 使用的时间，可能出现服务器时钟回拨问题。
        if (currentTimeMillis < lastTimeMillis) {
            long offset = lastTimeMillis - currentTimeMillis;
            // 在可容忍的时间差值之内等待时间恢复正常
            if (offset <= offsetPeriod) {
                try {
                    wait(offset << 1L);
                    currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis < lastTimeMillis) {
                        String error = "Clock moved backwards, please check the time. Current timestamp: %d, last used timestamp: %d";
                        throw new RuntimeException(String.format(error, currentTimeMillis, lastTimeMillis));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String error = "Clock moved backwards, please check the time. Current timestamp: %d, last used timestamp: %d";
                throw new RuntimeException(String.format(error, currentTimeMillis, lastTimeMillis));
            }
        }

        if (currentTimeMillis == lastTimeMillis) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大
                currentTimeMillis = tilNextMillis(lastTimeMillis);
            }
        } else {
            // 不同毫秒内，序列号置为 0。
            sequence = 0L;
        }

        // 记录最后一次使用的毫秒时间戳
        lastTimeMillis = currentTimeMillis;

        // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
        return ((currentTimeMillis - twepoch) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORK_ID_SHIFT)
                | sequence;
    }

    /**
     * 获取指定时间戳的接下来的时间戳。
     */
    private long tilNextMillis(long lastTimestamp) {
        long currentTimeMillis = System.currentTimeMillis();
        while (currentTimeMillis <= lastTimestamp) {
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis;
    }

    public static long getTwepoch() {
        return twepoch;
    }

    public static void setTwepoch(long twepoch) {
        SnowFlakeUtil.twepoch = twepoch;
    }

    public static long getOffsetPeriod() {
        return offsetPeriod;
    }

    public static void setOffsetPeriod(long offsetPeriod) {
        SnowFlakeUtil.offsetPeriod = offsetPeriod;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public long getLastTimeMillis() {
        return lastTimeMillis;
    }

    public void setLastTimeMillis(long lastTimeMillis) {
        this.lastTimeMillis = lastTimeMillis;
    }

    /**
     * 从雪花ID中提取时间戳（毫秒）
     */
    public static long extractTimestampFromId(long snowFlakeId) {
        // 1. 右移22位，分离出时间戳部分
        long timestampPart = snowFlakeId >> TIMESTAMP_SHIFT;
        // 2. 加上起始时间戳，得到实际的毫秒时间戳
        return timestampPart + twepoch;
    }

    /**
     * 从雪花ID中提取日期时间
     */
    public static LocalDateTime extractDateFromId(long snowFlakeId) {
        // 1. 提取时间戳
        long timestamp = extractTimestampFromId(snowFlakeId);
        // 2. 转换为LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                new Date(timestamp).toInstant(),
                ZoneId.systemDefault()
        );
        return dateTime;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            SnowFlakeUtil snowFlakeUtil = SnowFlakeUtil.getFlowIdInstance();
            System.out.println(snowFlakeUtil.nextId());
        }
    }
}