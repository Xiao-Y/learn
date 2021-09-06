package com.billow.tools.generator;

import com.billow.tools.utlis.SpringContextUtil;

/**
 * 雪花算法
 *
 * @author liuyongtao
 * @since 2021-8-26 8:42
 */
public class SnowFlakeKit {

    private final static SnowFlakeService snowFlakeService = SpringContextUtil.getBean(SnowFlakeService.class);

    public static long gen() {
        return snowFlakeService.nextId();
    }

}