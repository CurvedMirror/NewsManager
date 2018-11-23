package org.jbit.news.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jbit.news.entity.News;

public interface NewsDao {
	//获取某主题下的新闻数量
	int getNewsCountByTID(int tid) throws SQLException;
	//获取所有新闻
	List<News> getAllnews() throws SQLException;
	//获取某主题下的所有新闻
	List<News>  getAllnewsByTID(int tid) throws SQLException;
	//根据新闻ID读取新闻
	News getNewByNid(int nid) throws SQLException;
	//删除新闻
	int deleteNewByNid(int nid) throws SQLException;
	//查询新闻数目
	int getTotalCount(Integer tid) throws SQLException;
	//查询每页显示的新闻
	List<News> getPageNewsList(Integer tid,int pageNo,int pageSize) throws SQLException;
	//添加新闻
	int addNews(News news) throws SQLException;
	//修改新闻
	int editNews(News news) throws SQLException;
	//查询最新消息(左侧新闻)
	List<List<News>> findLatestNewsByTid(Map<Integer, Integer> topics) throws SQLException;
	
}
