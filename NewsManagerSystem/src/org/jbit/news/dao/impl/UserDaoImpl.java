package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.UserDao;
import org.jbit.news.entity.User;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class UserDaoImpl extends BaseDao implements UserDao {
	public UserDaoImpl(Connection conn) {
		super(conn);
	}

	public UserDaoImpl() {
	}

	public User login(String name, String pwd) {
		User user = null;
		String sql = "select * from news_users where uname=? and upwd=?";
		ResultSet rs = super.executeQuery(sql, name, pwd);
		try {

			while (rs.next()) {
				user = new User(rs.getInt("uid"), rs.getString("uname"),
						rs.getString("upwd"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, rs);
		}
		return user;
	}

}
