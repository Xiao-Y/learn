/**
 * Project Name:tphw-base-common
 * File Name:SequenceUtil.java
 * Package Name:com.cntaiping.fsc.common.util
 * Date:2017年9月18日下午2:10:30
 * Copyright (c) 2017-2022, CHINA TAIPING INSURANCE GROUP LTD. All Rights Reserved.
 *
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
 * @author   liebin.zheng	 
 */
public class SequenceUtil {
	
	private static volatile int sequencNum = 0;
	
	private static Random randomNumberGenerator;

    private static Random initRNG() {
        Random rnd = randomNumberGenerator;
        return (rnd == null) ? (randomNumberGenerator = new Random()) : rnd;
    }
    
    /**
     * 
     * @methodName:createSequenceByPrefix
     * @Description: 带前缀+日期+4位随机序号
     * @author liebin.zheng
     * @date 2017年9月21日
     *
     */
	public static String createSequenceByPrefix(String prefix){
		
		return prefix + DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS") + generateRandomSequenceNum(4);
	}
	
    /**
     * 
     * @methodName:createSequence
     * @Description: 日期+4位随机序号
     * @author liebin.zheng
     * @date 2017年9月21日
     *
     */
	public static String createSequence(){
		
		return DateFormatUtils.format(new Date(), "yyyyMMddhhmmssSSS") + generateRandomSequenceNum(4);
	}
	
	/**
	 * 
	 * @methodName:createRandomSeqNo
	 * @Description: 获取时间戳+百位顺序流水号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 *
	 */
	public static String  createRandomSeqNo(){
		return System.currentTimeMillis() + generateSequenceNum();
	}
	
	/**
	 * 
	 * @methodName:createRandomSeqNo2
	 * @Description:  获取时间戳+百位随机流水号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 *
	 */
	public static String createRandomSeqNo2(){
		return System.currentTimeMillis() + generateSequencNum2();
	}
	
	
	/**
	 * 
	 * @methodName:generateSequencNum2
	 * @Description: 获取百位随机流水号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 *
	 */
	public static String generateSequencNum2(){
		
		String sequencStr = "000";
		synchronized(sequencStr){
			int num = initRNG().nextInt(1000);
			if(num < 1000){
				
				if(num < 10){
					sequencStr = "00" + num;
				} else if(num < 100) {
					sequencStr = "0" + num;
				} else {
					sequencStr = "" + num;
				}
			} 
		}
		return sequencStr;
	}
	
	/**
	 * 
	 * @methodName:generateSequencNum
	 * @Description: 获取百位流水号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 */
	public static String generateSequenceNum(){
		String sequencStr = "000";
		synchronized(sequencStr){
			if(sequencNum < 1000){
				
				if(sequencNum < 10){
					sequencStr = "00" + sequencNum;
				} else if(sequencNum < 100) {
					sequencStr = "0" + sequencNum;
				} else {
					sequencStr = "" + sequencNum;
				}
				sequencNum++;
			} else {
				sequencNum = 0;
				sequencStr = "000";
			}
			
		}
		return sequencStr;
		
	}
	
	/**
	 * 
	 * @methodName:generateSequencNum
	 * @Description: 获取指定长度的随机流水号，length不能大于8位
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 * 
	 */
	public static String generateRandomSequenceNum(int length){
		
		Assert.isTrue(length > 0, "length must be greater than 0!");
		String sequencStr = "";
		synchronized(sequencStr){
			for (int i = 0; i < length; i++) {
				sequencStr += initRNG().nextInt(9);
			}
		}
		return sequencStr;
		
	}
	
	/**
	 * 
	 * @methodName:generateSequencNum
	 * @param currentMaxNo 当前最大编号
	 * @param length 流水号的长度
	 * @Description: 获取指定长度的顺序流水号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 * 
	 */
	public static String generateSequenceNum(int currentMaxNo, int length){
		Assert.isTrue(currentMaxNo > 0, "currentMaxNo must be greater than 0!");
		Assert.isTrue(length > 0, "length must be greater than 0!");
		String sequencStr = "";
		StringBuffer sb  = new StringBuffer();
		int prefixLength = 0;
		synchronized(sequencStr){
			sequencStr = String.valueOf(currentMaxNo);
			prefixLength = length - sequencStr.length();
			if(prefixLength > 0){
				for (int i = 0; i < prefixLength; i++) {
					sb.append("0");
				}
				sequencStr = sb.toString() + sequencStr;
			}
			
		}
		return sequencStr;
	}
	
	
	/**
	 * 
	 * @methodName:generateSequencNum2
	 * @param currentMaxNo 当前最大编号
	 * @param length 流水号的长度
	 * @Description: 获取指定长度的顺序流水号，前缀补0，最多12位顺序号
	 * @author liebin.zheng
	 * @date 2017年9月18日
	 * 
	 */
	public static String generateSequenceNum2(int currentMaxNo, int length){
		Assert.isTrue(currentMaxNo > 0, "currentMaxNo must be greater than 0!");
		Assert.isTrue(length > 0, "length must be greater than 0!");
		Assert.isTrue(length < 13, "length can not be greater than 12!");
		String sequencStr = "";
		StringBuffer sb  = new StringBuffer();
		sb.append("00000000000");
		synchronized(sequencStr){
			sb.append(String.valueOf(currentMaxNo));
			sequencStr = sb.toString();
		}
		return sequencStr.substring(sequencStr.length() - length, sequencStr.length());
	}

}

