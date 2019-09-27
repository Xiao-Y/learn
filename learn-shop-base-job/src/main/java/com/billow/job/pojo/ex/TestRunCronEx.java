package com.billow.job.pojo.ex;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-08-14 9:33
 */
@Data
public class TestRunCronEx {

    /**
     * cron 表达式
     *
     * @author LiuYongTao
     * @date 2019/8/14 9:34
     */
    private String cron;
    /**
     * 运行次数
     *
     * @author LiuYongTao
     * @date 2019/8/14 9:34
     */
    private int times;
}
