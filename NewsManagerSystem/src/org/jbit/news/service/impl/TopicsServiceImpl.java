package org.jbit.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jbit.news.dao.NewsDao;
import org.jbit.news.dao.TopicsDao;
import org.jbit.news.dao.impl.NewsDaoImpl;
import org.jbit.news.dao.impl.TopicsDaoImpl;
import org.jbit.news.entity.Topics;
import org.jbit.news.service.TopicsService;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class TopicsServiceImpl implements TopicsService {

	Connection conn = DataBaseUtil.getConnection();// 连接
	NewsDao newsDao = new NewsDaoImpl(conn);// 新闻
	TopicsDao topicsDao = new TopicsDaoImpl(conn);// 标题

	/**
	 * 1.执行成功 0执行失败 -1新闻不存在
	 */
	public int deleteTopics(int tid) {
		int result = 0;

		try {
			conn.setAutoCommit(false);

			if (newsDao.getNewsCountByTID(tid) == 0) {
				if (topicsDao.deleteTopic(tid) > 0) {
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}

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

	/**
	 * -1代表新闻主题存在，1表示成功更新，0表示更新失败
	 */
	public int updateTopic(Topics topics) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			if (topicsDao.findTopicByName(topics.getTname()) == null) {// 如果新闻主题是否重复
				if (topicsDao.updateTopic(topics) > 0) {// 更新新闻主题
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
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

	public int addTopic(String name) {
		int result=0;
		try {
			conn.setAutoCommit(false);
			
			if (topicsDao.findTopicByName(name)==null) {
				if (topicsDao.addTopic(name) > 0) {
					result = 1;//添加主题成功
				}
			}else{
				result=-1;//主题存在
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseUtil.closeAll(conn, null, null);
		}
		return result;
	}
	
	public List<Topics> getAllTopics(String appendSql) {
		try {
			return topicsDao.getAllTopics("");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
		
	}
}
