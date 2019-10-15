package com.xiaoxinkeji.interf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {
	/**
	 * ��ʼ������
	 */
	private static String pat="yyyy:MM:dd HH:mm:ss:SSS";
	
	/**
	 * ��ȡʱ���
	 */
	public static Long getTimeStamp(){
		return new Date().getTime();
	}
	
	/**
	 * ��ȡ��ǰ��ʱ�������
	 */
	public static String getDateTime(){
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateFormat.format(new Date());
		
	}
	
	/**
	 * ��ȡ��ǰʱ������ڼ�����
	 */
	public static String getDateTimeComepare(){
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		return sdf.format(new Date());
		
	}
	
	/**
	 * ��ȡ��ǰʱ����Date���͵�
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
