package org.jbit.news.dao;

import java.sql.SQLException;
import java.util.List;

import org.jbit.news.entity.Topics;

public interface TopicsDao {
	//获取所有主题
	public List<Topics> getAllTopics(String appendSql) throws SQLException;
	//更新主题
	public int updateTopic(Topics topics) throws SQLException;
	//根据名字查找主题
	public Topics findTopicByName(String name) throws SQLException;
	//添加主题
	public int addTopic(String name) throws SQLException;
	//通过tid删除主题
	public int deleteTopic(int tid) throws SQLException;
	
	
}
