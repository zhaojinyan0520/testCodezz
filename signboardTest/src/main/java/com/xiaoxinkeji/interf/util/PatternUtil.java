package com.xiaoxinkeji.interf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import com.alibaba.fastjson.JSONPath;
public class PatternUtil {
	/**
	 * 创建表达式
	 */
	private static String CompareResultRegex="([//$//.a-zA-Z0-9]+)=([\\w])+)";
	private static String depKeyRegex="([//a-zA-Z0-9])+:([//$//.A-Za-z0-9])+";
	private static String ReqDataRegex="(//[a-zA-Z0-9]+:[//$//.A-Za-z0-9])";
	private static Map<String, String> map = new HashMap<String, String>();
	/**
	 * 建立正则规范
	 */
	public static Matcher getMatcher(String regex,String data){
		Matcher matcher = null;
		try {
			Pattern pattern = Pattern.compile(regex);
			matcher =  pattern.matcher(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return matcher;
		
	}
	
	/**
	 * 实际值和预期 值做对比，打印测试报告
	 */
	public static void CompareResultToReport(String expResult,String actResult){
		try {
			Matcher mathcer = getMatcher(CompareResultRegex, expResult);
			while (mathcer.find()) {
				Assert.assertEquals(expResult, JSONPath.read(actResult, mathcer.group(1).toString()),mathcer.group(2));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	/**
	 * 实际值和预期值做对比，测试结果入库
	 */
	public static int CompareResultToDb(String expResult,String actResult){
		int flag = 0;
		List<Integer> list =new  ArrayList<Integer>();
		try {
			Matcher matcher = getMatcher(CompareResultRegex, expResult);
			while (matcher.find()) {
				int status = JSONPath.read(actResult, matcher.group(1).toString()).equals(matcher.group(2))?1:0;
				list.add(status);
				if (!list.contains(0)) {
					flag=1;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
		
	}
	
	/**
	 * 存储服务器返回值的value值
	 */
	public static void storeResponseValue(String depKey,String actResult){
		try {
			Matcher matcher = getMatcher(depKeyRegex, depKey);
			while (matcher.find()) {
				String jsonPath = matcher.group(2);
				String value = JSONPath.read(actResult, jsonPath).toString();
				map.put(matcher.group(), value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	/**
	 *处理接口依赖 
	 */
	
	public static void handlerReqDateOfDepkey(String reqdate){
		try {
			Matcher matcher = getMatcher(ReqDataRegex, reqdate);
			while (matcher.find()) {
				String value = map.get(matcher.group());
				StringUtil.replaceStr(reqdate, matcher.group(2), value);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
