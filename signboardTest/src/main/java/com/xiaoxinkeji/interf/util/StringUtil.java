package com.xiaoxinkeji.interf.util;

public class StringUtil {
	public static String replaceStr(String sourceStr,String matcherStr,String replaceStr){
	
		try {
			//�õ�ԭ�ַ���
			int index = sourceStr.indexOf(matcherStr);
			String beginStr = sourceStr.substring(0, index);
			
			//�õ������ַ���
			int matcherLen = matcherStr.length();
			int souceLen = sourceStr.length();
			String endStr = sourceStr.substring(index+matcherLen, souceLen);
			
			//ƴ���ַ���
			sourceStr = beginStr+replaceStr+endStr;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return sourceStr;
		
	}
}
