/**
 * Project Name:tphw-base-common
 * File Name:SequenceUtil.java
 * Package Name:com.cntaiping.fsc.common.util
 * Date:2017年9月18日下午2:10:30
 * Copyright (c) 2017-2022, CHINA TAIPING INSURANCE GROUP LTD. All Rights Reserved.
 */

package com.billow.tools.generator;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Random;

/**
 * ClassName:SequenceUtil <br/>
 * Description: 序列号生成工具. <br/>
 * Date:     2017年9月18日 下午2:10:30 <br/>
 *
 * @author billow
 */
public class SequenceUtil {

    private static Random randomNumberGenerator;

    /**
     * @methodName:createSequence
     * @Description: 日期+4位随机序号
     * @author billow
     * @date 2017年9月21日
     */
    public static String createSequence(String prefix, String suffix) {
        prefix = prefix == null ? "" : prefix;
        suffix = suffix == null ? "" : suffix;
        return prefix + DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS") + generateRandomSequenceNum(4) + suffix;
    }

    /**
     * @methodName:createSequence
     * @Description: 日期+4位随机序号
     * @author billow
     * @date 2017年9月21日
     */
    public static String createSequence() {
        return createSequence(null, null);
    }

    /**
     * @methodName:createSequenceByPrefix
     * @Description: 带前缀+日期+4位随机序号
     * @author billow
     * @date 2017年9月21日
     */
    public static String createSequenceByPrefix(String prefix) {
        return createSequence(prefix, null);
    }

    /**
     * @methodName:createSequenceBySuffix
     * @Description: 日期+4位随机序号+带后缀
     * @author billow
     * @date 2017年9月21日
     */
    public static String createSequenceBySuffix(String suffix) {
        return createSequence(null, suffix);
    }

    /**
     * @methodName:generateSequencNum
     * @Description: 获取指定长度的随机流水号，length不能大于8位
     * @author billow
     * @date 2017年9月18日
     */
    public static String generateRandomSequenceNum(int length) {

        Assert.isTrue(length > 0, "length must be greater than 0!");
        String sequencStr = "";
        synchronized (sequencStr) {
            for (int i = 0; i < length; i++) {
                sequencStr += initRNG().nextInt(9);
            }
        }
        return sequencStr;
    }

    private static Random initRNG() {
        Random rnd = randomNumberGenerator;
        return (rnd == null) ? (randomNumberGenerator = new Random()) : rnd;
    }

}

