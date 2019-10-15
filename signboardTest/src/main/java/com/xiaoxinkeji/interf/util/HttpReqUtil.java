package com.xiaoxinkeji.interf.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpReqUtil {
	/**
	 * 创建get请求方法
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendGet(String url,String param){
		String reqUrl = url +"?"+param;
		String result = null;
		try {
			//得到httpclient，httpget对象
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(reqUrl);
			
			//得到服务器的实际返回值
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				 result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param param
	 * @return
	 */
	
	public static String sendPost(String url,String param){
		String result = null;
		try {
			
			//得到httpclient，httpget对象
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			
			//设置消息体
			StringEntity entity = new StringEntity(param);
			httpPost.setEntity(entity);
			
			//得到服务器实际返回值
			CloseableHttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	
	/**
	 * put请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPut(String url,String param){
		String result = null;
		try {
			//得到httpclient，httpget对象
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(url);
			
			//设置消息体
			StringEntity entity = new StringEntity(param);
			httpPut.setEntity(entity);
			
			//得到服务器实际返回值
			CloseableHttpResponse response = httpClient.execute(httpPut);
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
		
	}
	
	public static void main(String[] args){
//		String result = HttpReqUtil.sendPost("http://47.93.37.191:60008/admin_management/adminOrder/getMainOrderListByCon", "limit=10&page=1&startTime=1570809600000&endTime=1570895999999&orderSn=null&status=null");
		String result = HttpReqUtil.sendGet("https://deal-admin.kuick.cn/api/v1.7/app/81b02eb1-72bc-4e4f-88be-49113bba815a/members", "keyword=&access_token=588f2168488460d45a2f4be7153f2784e85dd93a4f7f76e5b8c08d9b55800ee24d06af3350b26a126ebbd8c8e591f2a8618d5a2887bb2acf429373ee36e463ed&app_secret=bb1549b9-4981-4b38-a9a8-d4b95cb69427&https=1");
		System.out.println("post请求结果："+result);
	}
}
