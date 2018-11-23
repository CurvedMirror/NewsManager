package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.TopicsDao;
import org.jbit.news.entity.Topics;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class TopicsDaoImpl extends BaseDao implements TopicsDao {

	public TopicsDaoImpl(Connection connection) {
		super(connection);
	}

	public int addTopic(String name) throws SQLException {
		int result = 0;
		String sql = "INSERT  INTO topic(`tname`)  VALUES(?)";
		result = super.executeUpdate(sql, name);
		return result;
	}

	public List<Topics> getAllTopics(String appendSql) throws SQLException {
		ResultSet rs = null;
		List<Topics> list = new ArrayList<Topics>();
		try {
			String sql = "SELECT * FROM topic " + appendSql;
			rs = super.executeQuery(sql);
			while (rs.next()) {
				Topics topics = new Topics(rs.getInt("tid"),
						rs.getString("tname"));
				list.add(topics);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			DataBaseUtil.closeAll(null, null, rs);
		}
		return list;
	}

	public int updateTopic(Topics topics) throws SQLException {
		int result = 0;
		String sql = "update topic set tname=? where tid=?";
		return executeUpdate(sql, topics.getTname(), topics.getTid());
	}

	public Topics findTopicByName(String name) throws SQLException {
		Topics topics = null;
		String sql = " WHERE tname='" + name + "'";
		if (getAllTopics(sql).size() != 0) {
			topics = getAllTopics(sql).get(0);
		}
		return topics;
	}

	public int deleteTopic(int tid) throws SQLException {
		String sql = "delete from topic where tid=?";
		return executeUpdate(sql, tid);
	}
}
