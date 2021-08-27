package com.billow.tools.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 雪花算法
 *
 * @author liuyongtao
 * @since 2021-8-26 8:42
 */
@Component
public class SnowFlakeService {

    @Value("${spring.cloud.client.ip-address}")
    private String ip;
    @Value("${server.port}")
    private Long port;

    // 机器标识（用 ip 地址计算而来） 和 数据标识（直接使用端口）
    private final long machineId = ipToNum(ip);
    private final long datacenterId = port;

    // 起始的时间戳，设置的尽量大，最好是项目创建的时间，确定之后禁止修改
    private final static long START_STMP = 1480166465631L;

    // 三个部分中每一部分占用的位数
    private final static long SEQUENCE_BIT = 12;    // 序列号占用的位数
    private final static long MACHINE_BIT = 5;      // 机器标识占用的位数
    private final static long DATACENTER_BIT = 5;   // 数据中心占用的位数

    // 每一部分最大值
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    // 每一部分向左的位移
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    // 每一毫秒中的序列号，和上一次执行时的时间戳
    private long sequence = 0L;
    private long lastStmp = -1L;

    /**
     * 生成主键的方法，请确保分布式不同节点中，方法两个参数的唯一性
     */
    public synchronized long nextId() {
        long currStmp = System.currentTimeMillis();
        if (currStmp == lastStmp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0L) {
                long mill = System.currentTimeMillis();
                while (mill <= lastStmp) {
                    mill = System.currentTimeMillis();
                }
                currStmp = mill;
            }
        } else {
            sequence = 0L;
        }
        lastStmp = currStmp;
        currStmp = (currStmp - START_STMP) << TIMESTMP_LEFT;     // 时间戳部分
        currStmp = currStmp | (datacenterId << DATACENTER_LEFT); // 数据中心部分
        currStmp = currStmp | (machineId << MACHINE_LEFT);       // 机器标识部分
        currStmp = currStmp | sequence;                          // 序列号部分
        return currStmp;                         // 转化为字符串
    }

    /**
     * 将 ipv4 的地址转化为 唯一数字
     *
     * @param ip 地址
     */
    private long ipToNum(String ip) {
        String[] ipArr = ip.split("\\.");
        long ipNum = Long.parseLong(ipArr[3]) & 0xFF;
        ipNum |= ((Long.parseLong(ipArr[2]) << 8) & 0xFF00);
        ipNum |= ((Long.parseLong(ipArr[1]) << 16) & 0xFF0000);
        ipNum |= ((Long.parseLong(ipArr[0]) << 24) & 0xFF000000);
        return ipNum;
    }
}