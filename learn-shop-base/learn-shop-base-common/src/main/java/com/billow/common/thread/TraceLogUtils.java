package com.billow.common.thread;

import com.billow.tools.generator.UUID;

public class TraceLogUtils {

    /**
     * 生成 tracedid
     *
     * @return {@link String}
     * @author xiaoy
     * @since 2021/2/4 11:11
     */
    public static String getTraceId() {
        return UUID.generate();
    }
}
