package org.jbit.news.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jbit.news.entity.News;
import org.jbit.news.entity.Page;

public interface NewsService {
	// 获取某主题下的新闻数量
	int getNewsCountByTID(int tid);

	// 获取所有新闻
	List<News> getAllnews();

	// 获取某主题下的所有新闻
	List<News> getAllnewsByTID(int tid);

	// 根据新闻ID读取新闻
	News getNewByNid(int nid);

	// 删除新闻
	int deleteNewByNid(int nid);

	// 查询新闻数目
	int getTotalCount(Integer tid);

	// 查询每页显示的新闻
	List<News> getPageNewsList(Integer tid, int pageNo, int pageSize);

	// 添加新闻
	int addNews(News news);

	// 修改新闻
	int editNews(News news);

	// 查询最新消息(左侧新闻)
	List<List<News>> findLatestNewsByTid(Map<Integer, Integer> topics);

	public void findPageNews(Integer tid, Page pageObj);
}
