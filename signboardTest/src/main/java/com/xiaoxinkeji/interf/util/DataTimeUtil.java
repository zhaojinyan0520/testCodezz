package com.xiaoxinkeji.interf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {
	/**
	 * 初始化变量
	 */
	private static String pat="yyyy:MM:dd HH:mm:ss:SSS";
	
	/**
	 * 获取时间戳
	 */
	public static Long getTimeStamp(){
		return new Date().getTime();
	}
	
	/**
	 * 获取当前的时间和日期
	 */
	public static String getDateTime(){
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateFormat.format(new Date());
		
	}
	
	/**
	 * 获取当前时间和日期及毫秒
	 */
	public static String getDateTimeComepare(){
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		return sdf.format(new Date());
		
	}
	
	/**
	 * 获取当前时间是Date类型的
	 * @throws ParseException 
	 */
	public static Date getDateType(String datestr) throws ParseException{
		SimpleDateFormat sdf =  null;
		try {
			sdf = new SimpleDateFormat(pat);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sdf.parse(datestr);
		
	}
	public static void main(String[] args){
		System.out.println(DataTimeUtil.getDateTime());
	}
	
}
