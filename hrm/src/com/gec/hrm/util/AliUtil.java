package com.gec.hrm.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class AliUtil<T> {

	private static Properties p = new Properties();
	
	private static DataSource ds;
	
	static{
		try(InputStream in = AliUtil.class.getResourceAsStream("/db.properties")){
			//将文件流加载到p对象
			p.load(in);
			//创建数据库连接池
			ds = DruidDataSourceFactory.createDataSource(p);  //自动填充属性,创建出数据源来
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection(){
		Connection conn = null;
		try {
			conn = ds.getConnection();
			System.out.println("conn:"+conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public int executeUpdate(String sql,Object...objects) {
		int flag = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (objects!=null) {
				for (int i = 0; i < objects.length; i++) {
					ps.setObject((i+1), objects[i]);
				}
			}
			flag = ps.executeUpdate();
		}  catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn, ps, rs);
		}

		return flag;
	}
    
    public int queryCount(String sql , Object...obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
    	int row = 0;
    	try {
    		ps = getConnection().prepareStatement(sql);
			for(int i = 0 ; i<obj.length;i++) {
				ps.setObject(i+1, obj[i]);
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				row = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(conn, ps, rs);
		}
		return row;
    	
    }
    
    public List<T> executeQuery(String sql,Object...objects) {
		List<T> list =null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { 
			list = new ArrayList<T>();
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (objects!=null) {
				for (int i = 0; i < objects.length; i++) {
					ps.setObject((i+1), objects[i]);
				}
			}
			rs = ps.executeQuery();
			while(rs.next()) {
                //将重写方法获取的对象，放入集合
                list.add(getEntity(rs));
            }
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(sql);
		}
        close(conn, ps, rs);
		return list;
	}
    
    public int getCount(String sql) {
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try { 
			conn = getConnection();
			//String sql = "select count(1) as user_count from `"+tableName+"`";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next())
				count = rs.getInt("count");
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        close(conn, ps, rs);
		return count;
	}

    public abstract T getEntity(ResultSet rs) throws Exception;


    public void close(Connection conn,Statement stat,ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stat!=null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
