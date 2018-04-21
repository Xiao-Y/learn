package com.ft.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
	private static final String DAY_FORMAT_STRING = "yyyyMMdd";
	private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DAY_FORMAT_STRING);
	private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public static String getDayFormatString(Date date){
		if(date == null){
			throw new IllegalArgumentException("date can not be null");
		}
		return DAY_FORMAT.format(date).toString();
	}
	
	/**
	 * 字符串转化为日期格式
	 * @param strDate
	 * @param simpleDateFormat
	 * @return
	 * @throws Exception 
	 */
	public static Date convertStringToDate(String strDate,SimpleDateFormat simpleDateFormat) throws Exception{
		Date date = null;
		if(strDate == null || "".equals(strDate)){
			return date;
		}
		
		if(simpleDateFormat == null){
			simpleDateFormat = FORMAT;
		}
		try {
			date = FORMAT.parse(strDate);
		} catch (Exception e) {
			throw new Exception("日期格式转化异常:" + e.getMessage());
		}
		return date;
	}
	
	/**
	 * 日期格式转化为字符串
	 * @param date
	 * @param simpleDateFormat
	 * @return
	 */
	public static String convertDateToString(Date date,SimpleDateFormat simpleDateFormat){
		String strDate = null;
		if(date == null){
			return strDate;
		}
		if(simpleDateFormat == null){
			simpleDateFormat = FORMAT;
		}
		return FORMAT.format(date);
	}
}
