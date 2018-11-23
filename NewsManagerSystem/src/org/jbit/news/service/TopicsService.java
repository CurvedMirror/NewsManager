package org.jbit.news.service;

import java.util.List;

import org.jbit.news.entity.Topics;

/**
 * 管理主题service接口
 * 
 * @author Administrator
 * 
 */
public interface TopicsService {
	/**
	 * 删除主题的方法
	 * 
	 * @param Tid
	 * @return
	 */
	public int deleteTopics(int tid) ;

	/**
	 * 修改主题的方法
	 * 
	 * @param topics
	 * @return
	 */
	public int updateTopic(Topics topics) ;

	/**
	 * 添加主题
	 * 
	 * @param name
	 * @return
	 */
	public int addTopic(String name) ; 
	
	/**
	 * 获取所有主题
	 * @param appendSql
	 * @return
	 */
	public List<Topics> getAllTopics(String appendSql) ;
}
