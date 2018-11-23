package org.jbit.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jbit.news.dao.CommentsDao;
import org.jbit.news.dao.NewsDao;
import org.jbit.news.dao.impl.CommentsDaoImpl;
import org.jbit.news.dao.impl.NewsDaoImpl;
import org.jbit.news.entity.News;
import org.jbit.news.entity.Page;
import org.jbit.news.service.NewsService;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class NewsServiceImpl implements NewsService {
	Connection conn = DataBaseUtil.getConnection();// 连接
	CommentsDao commentsDao = new CommentsDaoImpl(conn);// 评论
	NewsDao newsDao = new NewsDaoImpl(conn);// 新闻

	/**
	 * 0代表该新闻下没有评论
	 */
	public int deleteNewByNid(int nid) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			if (commentsDao.deleteCommentsByCnid(nid) >= 0) {
				result = 1;
			}
			newsDao.deleteNewByNid(nid);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseUtil.closeAll(conn, null, null);
		}
		return result;
	}

	// 添加新闻
	public int addNews(News news) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			result = newsDao.addNews(news);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseUtil.closeAll(conn, null, null);
		}
		return result;
	}

	// 修改新闻
	public int editNews(News news) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			result = newsDao.editNews(news);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseUtil.closeAll(conn, null, null);
		}
		return result;
	}

	public void findPageNews(Integer tid, Page pageObj) {

		try {
			int totalCount = newsDao.getTotalCount(tid);
			pageObj.setTotalCount(totalCount);
			if (totalCount > 0) {
				// 对末页进行控制
				if (pageObj.getCurrPageNo() > pageObj.getTotalPageCount())
					pageObj.setCurrPageNo(pageObj.getTotalPageCount());
				// 进行分页查询
				List<News> newsList = newsDao.getPageNewsList(tid,
						pageObj.getCurrPageNo(), pageObj.getPageSize());
				pageObj.setNewsList(newsList);
			} else {
				pageObj.setCurrPageNo(0);
				pageObj.setNewsList(new ArrayList<News>());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
	}

	public List<List<News>> findLatestNewsByTid(Map<Integer, Integer> topics) {
		List<List<News>> list = null;
		try {
			list = newsDao.findLatestNewsByTid(topics);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return list;
	}

	public News getNewByNid(int nid) {
		try {
			return newsDao.getNewByNid(nid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
	}

	public int getNewsCountByTID(int tid) {
		try {
			return newsDao.getNewsCountByTID(tid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return 0;
	}

	public List<News> getAllnews() {
		try {
			return newsDao.getAllnews();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
	}

	public List<News> getAllnewsByTID(int tid) {
		try {
			return newsDao.getAllnewsByTID(tid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
	}

	public int getTotalCount(Integer tid) {
		try {
			return newsDao.getTotalCount(tid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return 0;
	}

	public List<News> getPageNewsList(Integer tid, int pageNo, int pageSize) {
		try {
			return newsDao.getPageNewsList(tid, pageNo, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
	}
}
