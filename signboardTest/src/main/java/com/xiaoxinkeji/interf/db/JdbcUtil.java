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
	 * 创建connection对象与数据库建立连接
	 */
	public static Connection getconn(){
		Connection connection = null;
		try {
			//与数据库建立驱动
			Class.forName("com.mysql.jdbc.driver");
			
			//与数据库建立连接得到connection对象
			 connection = DriverManager.getConnection("jdbc:mysql://192.168.10.120");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
		
	}
	
	/**
	 * 添加单个测试结果入库
	 *
	 */
	
	public static int jdbcUpdata(String sql,Autolog autolog ){
		int result =0;
		try {
			//与数据库建立连接
			Connection connection = getconn();
			
			//预编译
			PreparedStatement ps = connection.prepareStatement(sql);
			
			//设置值
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			
			//测试结果存放数据库
			result = ps.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * 批量添加测试结果存入数据库
	 * 
	 */
	
	public static int[] jdbcBathUpdate(String sql,List<Autolog> list){
		int[] result = null;
		try {
			//与数据库建立连接进行预编译
			Connection connection = getconn();
			PreparedStatement ps = connection.prepareStatement(sql);
			//遍历设置的数据
			for (Autolog autolog : list) {
				ps.setString(1, autolog.getReqType());
				ps.setString(2, autolog.getReqUrl());
				ps.setString(3, autolog.getReqData());
				ps.setString(4, autolog.getTestCase());
				ps.setString(5, autolog.getExpResult());
				ps.setString(6, autolog.getActResult());
				ps.setInt(7, autolog.getResult());
				ps.setString(8, autolog.getExecTime());
				//将实体类存放在集合中
				list.add(autolog);
			}
			//将测试数据批量存放在数据库
			result = ps.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * 查询测试结果
	 */
	
	public static List<Object> JdbcQuery(String sql , Autolog autolog){
		List<Object>  list = null;
		try {
			//与数据库建立连接
			Connection  conn = getconn();
			//进行预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			//设置参数
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			//获取到结果集，并将结果集中的数据映射到实体类中
			ResultSet rs = ps.executeQuery();
			list = handler(rs, Autolog.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		
	}
	/**
	 * 将测试结果映射到结果集的实体类中
	 * @param rs
	 * @param cls
	 * @return
	 */
	public static List<Object> handler(ResultSet rs,Class<?> cls){
		Object  result = null;
		List<Object>  list = new ArrayList<Object>();
		try {
			//实例化对象
			result = cls.newInstance();
			//根据结果集获取到总行数
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			//遍历行数
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
