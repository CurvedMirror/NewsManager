package org.jbit.news.dao;

import org.jbit.news.entity.User;

public interface UserDao {
	
	/**
	 * 登录
	 * @param name
	 * @param pwd
	 * @return
	 */
	public User login(String name,String pwd);
}
