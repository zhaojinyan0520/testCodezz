package com.xiaoxinkeji.interf.test;

import org.testng.annotations.Test;

import com.xiaoxinkeji.interf.autolog.Autolog;
import com.xiaoxinkeji.interf.util.DataTimeUtil;
import com.xiaoxinkeji.interf.util.ExcelUtil;
import com.xiaoxinkeji.interf.util.HttpReqUtil;
import com.xiaoxinkeji.interf.util.PatternUtil;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;


public class signboardTest {
	private static List<Autolog> list = new ArrayList<Autolog>();
	
	@Test(dataProvider = "dp")
	  public void f(String id,String isExec,String testCase,String reqType,String reqHost,String reqInterface,String reqData,String expResult,String isDep,String depKey) {
		 //ƴ��·������ʼ��ʵ�ʷ���ֵ
		  String reqUrl = reqHost + reqInterface;
		  String actResult = null;
		  
		  //��ӡ��־
		  Reporter.log("�ӿڵ�ַ��"+reqUrl);
		  Reporter.log("���������"+reqData);
		  
		  //�жϽӿ��Ƿ�ִ�У�����ִ�еķ���
		  if ("YES".equals(isExec)) {
			if ("GET".equals(reqType)) {
				actResult = HttpReqUtil.sendGet(reqUrl, reqData);
			}else if ("POST".equals(reqType)) {
				actResult = HttpReqUtil.sendPost(reqUrl, reqData);
			}
		}else {
			System.out.println("�ýӿڲ�ִ��");
		}
		  
		//�����Խ����ӡ�ڲ��Ա�����
		PatternUtil.CompareResultToDb(expResult, actResult); 
		//�����Խ��������ݿ�
		int result = PatternUtil.CompareResultToDb(expResult, actResult);
		list.add(new Autolog(testCase, reqType, reqUrl, reqData, expResult, actResult, result, DataTimeUtil.getDateTime()));
		
	  }
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }


  @DataProvider
  public Object[][] dp() {
	  ExcelUtil excelUtil = new ExcelUtil("D:\\Learing_Documents\\app_testcase1.xlsx");
	  Object[][] result = excelUtil.fromArrayValues(0);
    return result;
  }
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

}
