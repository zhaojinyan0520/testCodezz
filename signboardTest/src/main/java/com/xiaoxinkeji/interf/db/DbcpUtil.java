package com.xiaoxinkeji.interf.db;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import com.xiaoxinkeji.interf.autolog.Autolog;

public class DbcpUtil {
	
	private static BasicDataSource dataSource = null;
	/**
	  * 建立连接池
	  */
	static{ 
		if (dataSource==null) {
			dataSource = new BasicDataSource();
			try {
				//建立连接
				dataSource.setDriverClassName("com.jdbc.mysql.Driver");
				dataSource.setUrl("");
				dataSource.setUsername("");
				dataSource.setPassword("");
				
				//设置连接池大小
				dataSource.setInitialSize(30);//设置初始化连接数
				dataSource.setMaxActive(30);//设置最大连接数
				dataSource.setMaxIdle(30);//设置最大空闲数
				dataSource.setMinIdle(30);//设置最小空闲连接数
				
				//重新检查连接
				dataSource.setTestOnBorrow(false);
				dataSource.setTestOnReturn(false);
				dataSource.setMaxWait(3000);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * 与数据库建立连接
	 */
	
	public static Connection getconn(){
		Connection conn =null;
		try {
			 conn = dataSource.getConnection();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conn;
		
	}
	
	
	/**
	 * 将单个测试数据加入数据库
	 */
	
	public static int jdbcupdate(String sql,Autolog autolog){
		int result = 0;
		try {
			//建立连接
			Connection conn = getconn();
			//预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			//设置参数
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * 将测试数据批量存放到数据库
	 */
	
	public static int[] jdbcBatchUpdate(String sql,List<Autolog> list){
		int[] result = null;
		try {
			//建立连接
			Connection conn = getconn();
			//预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			//设置参数
			for (Autolog autolog : list) {
				ps.setString(1,autolog.getTestCase() );
				ps.setString(2, autolog.getReqType());
				ps.setString(3, autolog.getReqData());
				ps.setString(4, autolog.getReqUrl());
				ps.setString(5, autolog.getExpResult());
				ps.setString(6, autolog.getActResult());
				ps.setString(7, autolog.getExecTime());
				ps.setInt(8, autolog.getResult());
				list.add(autolog);
			}
			result = ps.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * 从数据库查询测试结果
	 */
	
	public static List<Object> jdbcQuery(String sql,Autolog autolog){
		 List<Object> result = null;
		try {
			//建立连接
			Connection conn = getconn();
			//预编译
			PreparedStatement ps = conn.prepareStatement(sql);
			//设置参数
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			//将实体类映射到结果集
			ResultSet rs = ps.executeQuery();
			result = handler(rs, Autolog.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	/**
	 * 利用结果集中的字段名和实体类中的字段名相对应
	 * @param rs
	 * @param cls
	 * @return
	 */
	public static List<Object> handler(ResultSet rs,Class<?> cls){
		Object data = null;
		List<Object> list = new ArrayList<Object>();
		try {
			//实例化实体类
			data = cls.newInstance();
			//得到总的列数
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int columnIndex = 0; columnIndex <= columnCount; columnIndex++) {
				String columnClassName = rsmd.getColumnClassName(columnIndex);
				
				// 对象的属性都是私有的所以要想访问必须加上getDeclaredField(name)
                Field field = data.getClass().getDeclaredField(columnClassName);
                field.setAccessible(true);
                
				field.set(data, rs.getObject(columnClassName));
			}
			list.add(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
		
	}
}
