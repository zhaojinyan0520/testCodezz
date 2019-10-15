package com.xiaoxinkeji.interf.util;

public class StringUtil {
	public static String replaceStr(String sourceStr,String matcherStr,String replaceStr){
	
		try {
			//得到原字符串
			int index = sourceStr.indexOf(matcherStr);
			String beginStr = sourceStr.substring(0, index);
			
			//得到最后的字符串
			int matcherLen = matcherStr.length();
			int souceLen = sourceStr.length();
			String endStr = sourceStr.substring(index+matcherLen, souceLen);
			
			//拼接字符串
			sourceStr = beginStr+replaceStr+endStr;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return sourceStr;
		
	}
}
