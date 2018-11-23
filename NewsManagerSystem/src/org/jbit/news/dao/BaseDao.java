package org.jbit.news.dao;

import java.sql.*;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jbit.news.util.DatabaseUtil.DataBaseUtil;



public class BaseDao {

	


	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	protected Connection conn = null;
	protected PreparedStatement pstmt = null;
	
	public BaseDao(Connection conn){
		this.conn=conn;
	}
	public BaseDao() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 执行增、删,改语句
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数列表
	 * @return
	 */
	public int executeUpdate(String sql, Object... params) {
		
		
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);

			// 赋值参数
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);// 下标与值意义一一对应
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(null, pstmt, null);
		}
		return result;
	}
	// 4.查询通用方法
		public ResultSet executeQuery(String sql,Object... params) {
			
			ResultSet rs=null;
			try {
				pstmt = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return rs;
		}
}