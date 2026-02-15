package com.billow.task.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

public class IdGeneratorUtil {
    private static final long WORKER_ID;
    private static final long DATA_CENTER_ID;
    private static final Snowflake SNOWFLAKE;

    static {
        WORKER_ID = Math.abs(NetUtil.ipv4ToLong(NetUtil.getLocalhostStr())) % 32;
        DATA_CENTER_ID = Math.abs(NetUtil.getLocalhostStr().hashCode()) % 32;
        SNOWFLAKE = IdUtil.createSnowflake(WORKER_ID, DATA_CENTER_ID);
    }

    public static String generateGroupNo() {
        return "TG" + SNOWFLAKE.nextIdStr();
    }
}