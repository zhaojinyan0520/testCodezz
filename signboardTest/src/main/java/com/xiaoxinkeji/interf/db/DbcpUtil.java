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
	  * �������ӳ�
	  */
	static{ 
		if (dataSource==null) {
			dataSource = new BasicDataSource();
			try {
				//��������
				dataSource.setDriverClassName("com.jdbc.mysql.Driver");
				dataSource.setUrl("");
				dataSource.setUsername("");
				dataSource.setPassword("");
				
				//�������ӳش�С
				dataSource.setInitialSize(30);//���ó�ʼ��������
				dataSource.setMaxActive(30);//�������������
				dataSource.setMaxIdle(30);//������������
				dataSource.setMinIdle(30);//������С����������
				
				//���¼������
				dataSource.setTestOnBorrow(false);
				dataSource.setTestOnReturn(false);
				dataSource.setMaxWait(3000);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	/**
	 * �����ݿ⽨������
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
	 * �������������ݼ������ݿ�
	 */
	
	public static int jdbcupdate(String sql,Autolog autolog){
		int result = 0;
		try {
			//��������
			Connection conn = getconn();
			//Ԥ����
			PreparedStatement ps = conn.prepareStatement(sql);
			//���ò���
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	
	/**
	 * ����������������ŵ����ݿ�
	 */
	
	public static int[] jdbcBatchUpdate(String sql,List<Autolog> list){
		int[] result = null;
		try {
			//��������
			Connection conn = getconn();
			//Ԥ����
			PreparedStatement ps = conn.prepareStatement(sql);
			//���ò���
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
	 * �����ݿ��ѯ���Խ��
	 */
	
	public static List<Object> jdbcQuery(String sql,Autolog autolog){
		 List<Object> result = null;
		try {
			//��������
			Connection conn = getconn();
			//Ԥ����
			PreparedStatement ps = conn.prepareStatement(sql);
			//���ò���
			ps.setInt(1, autolog.getId());
			ps.setString(2, autolog.getTestCase());
			//��ʵ����ӳ�䵽�����
			ResultSet rs = ps.executeQuery();
			result = handler(rs, Autolog.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
		
	}
	/**
	 * ���ý�����е��ֶ�����ʵ�����е��ֶ������Ӧ
	 * @param rs
	 * @param cls
	 * @return
	 */
	public static List<Object> handler(ResultSet rs,Class<?> cls){
		Object data = null;
		List<Object> list = new ArrayList<Object>();
		try {
			//ʵ����ʵ����
			data = cls.newInstance();
			//�õ��ܵ�����
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int columnIndex = 0; columnIndex <= columnCount; columnIndex++) {
				String columnClassName = rsmd.getColumnClassName(columnIndex);
				
				// ��������Զ���˽�е�����Ҫ����ʱ������getDeclaredField(name)
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
