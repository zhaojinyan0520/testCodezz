package com.xiaoxinkeji.interf.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import com.xiaoxinkeji.interf.autolog.Autolog;

public class JdbcUtil {
	
	/**
	 * ����connection���������ݿ⽨������
	 */
	public static Connection getconn(){
		Connection connection = null;
		try {
			//�����ݿ⽨������
			Class.forName("com.mysql.jdbc.driver");
			
			//�����ݿ⽨�����ӵõ�connection����
			 connection = DriverManager.getConnection("jdbc:mysql://192.168.10.120");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
		
	}
	
	/**
	 * ��ӵ������Խ�����
	 *
	 */
	
	public static int jdbcUpdata(String sql,Autolog autolog ){
		int result =0;
		try {
			//�����ݿ⽨������
			Connection connection = getconn();
			
			//Ԥ����
			PreparedStatement ps = connection.prepareStatement(sql);
			
			//����ֵ
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			
			//���Խ��������ݿ�
			result = ps.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * ������Ӳ��Խ���������ݿ�
	 * 
	 */
	
	public static int[] jdbcBathUpdate(String sql,List<Autolog> list){
		int[] result = null;
		try {
			//�����ݿ⽨�����ӽ���Ԥ����
			Connection connection = getconn();
			PreparedStatement ps = connection.prepareStatement(sql);
			//�������õ�����
			for (Autolog autolog : list) {
				ps.setString(1, autolog.getReqType());
				ps.setString(2, autolog.getReqUrl());
				ps.setString(3, autolog.getReqData());
				ps.setString(4, autolog.getTestCase());
				ps.setString(5, autolog.getExpResult());
				ps.setString(6, autolog.getActResult());
				ps.setInt(7, autolog.getResult());
				ps.setString(8, autolog.getExecTime());
				//��ʵ�������ڼ�����
				list.add(autolog);
			}
			//����������������������ݿ�
			result = ps.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * ��ѯ���Խ��
	 */
	
	public static List<Object> JdbcQuery(String sql , Autolog autolog){
		List<Object>  list = null;
		try {
			//�����ݿ⽨������
			Connection  conn = getconn();
			//����Ԥ����
			PreparedStatement ps = conn.prepareStatement(sql);
			//���ò���
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			//��ȡ�������������������е�����ӳ�䵽ʵ������
			ResultSet rs = ps.executeQuery();
			list = handler(rs, Autolog.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		
	}
	/**
	 * �����Խ��ӳ�䵽�������ʵ������
	 * @param rs
	 * @param cls
	 * @return
	 */
	public static List<Object> handler(ResultSet rs,Class<?> cls){
		Object  result = null;
		List<Object>  list = new ArrayList<Object>();
		try {
			//ʵ��������
			result = cls.newInstance();
			//���ݽ������ȡ��������
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			//��������
			for (int columnIndex = 0;columnIndex <= columnCount; columnIndex++) {
				String columnClassName = rsmd.getColumnClassName(columnIndex);
				Field field = Autolog.class.getDeclaredField(columnClassName);
				field.setAccessible(true);
				field.set(result, rs.getObject(columnClassName));
			}
			list.add(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		
	}
	
}
