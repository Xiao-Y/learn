package com.billow.mq.retry;

import java.util.Date;

public interface NextRetryDate {

    /**
     * 获取下次重试的时间
     *
     * @param tryCount
     * @return java.util.Date
     * @author LiuYongTao
     * @date 2019/10/18 15:22
     */
    Date nextRetryDate(long tryCount);
}
