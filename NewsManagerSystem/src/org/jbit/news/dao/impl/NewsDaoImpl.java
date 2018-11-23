package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.NewsDao;
import org.jbit.news.entity.News;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class NewsDaoImpl extends BaseDao implements NewsDao {
	public NewsDaoImpl() {
	}

	public NewsDaoImpl(Connection connection) {
		super(connection);
	}

	public int getNewsCountByTID(int tid) throws SQLException {
		ResultSet rs = null;
		String sql = "select count(ntid) from news where ntid=?";
		int count = -1;

		rs = super.executeQuery(sql, tid);
		rs.next();
		count = rs.getInt(1);

		DataBaseUtil.closeAll(null, null, rs);

		return count;
	}

	// 获取所有新闻
	public List<News> getAllnews() throws SQLException {
		List<News> list = new ArrayList<News>();
		ResultSet rs = null;
		String sql = "SELECT  nid,ntid,ntitle,nauthor,ncreateDate,nsummary,tname FROM news,topic"
				+ " WHERE news.`ntid`=topic.`tid` "
				+ "ORDER BY ncreateDate DESC";

		rs = this.executeQuery(sql);
		News news = null;
		while (rs.next()) {
			news = new News();
			news.setNid(rs.getInt("nid"));
			news.setNtid(rs.getInt("ntid"));
			news.setNtitle(rs.getString("ntitle"));
			news.setNauthor(rs.getString("nauthor"));
			news.setNcreateDate(rs.getString("ncreateDate"));
			news.setNsummary(rs.getString("nsummary"));
			list.add(news);
		}

		DataBaseUtil.closeAll(null, null, rs);

		return list;
	}

	// 获取某主题下单所有新闻
	public List<News> getAllnewsByTID(int tid) throws SQLException {
		List<News> list = new ArrayList<News>();
		ResultSet rs = null;
		String sql = "SELECT  nid,ntid,ntitle,nauthor,ncreateDate,nsummary,tname FROM news,topic"
				+ " WHERE news.`ntid`=topic.`tid`  AND news.`ntid`=?"
				+ " ORDER BY ncreateDate DESC";

		rs = this.executeQuery(sql, tid);
		News news = null;
		while (rs.next()) {
			news = new News();
			news.setNid(rs.getInt("nid"));
			news.setNtid(rs.getInt("ntid"));
			news.setNtitle(rs.getString("ntitle"));
			news.setNauthor(rs.getString("nauthor"));
			news.setNcreateDate(rs.getString("ncreateDate"));
			news.setNsummary(rs.getString("nsummary"));
			list.add(news);
		}

		DataBaseUtil.closeAll(null, null, rs);
		return list;
	}

	// 根据新闻id读取新闻
	public News getNewByNid(int nid) throws SQLException {
		ResultSet rs = null;
		News news = null;
		String sql = "SELECT * FROM news WHERE nid=?";
		rs = executeQuery(sql, nid);
		while (rs.next()) {
			news = new News();
			news.setNid(rs.getInt("nid"));
			news.setNtid(rs.getInt("ntid"));
			news.setNtitle(rs.getString("ntitle"));
			news.setNauthor(rs.getString("nauthor"));
			news.setNcreateDate(rs.getString("ncreateDate"));
			news.setNsummary(rs.getString("nsummary"));
			news.setNcontent(rs.getString("ncontent"));
		}
		DataBaseUtil.closeAll(null, null, rs);

		return news;
	}

	// 删除新闻
	public int deleteNewByNid(int nid) throws SQLException {
		String sql = "DELETE  FROM news WHERE nid=? ";
		return super.executeUpdate(sql, nid);
	}

	public int getTotalCount(Integer tid) throws SQLException {
		ResultSet rs = null;
		List<Object> params = new ArrayList<Object>();
		String sql = "select count(ntid) from news";
		if (tid != null) {
			sql += " where ntid=?";
			params.add(tid);
		}
		int count = -1;
		rs = super.executeQuery(sql, params.toArray());
		rs.next();
		count = rs.getInt(1);
		DataBaseUtil.closeAll(null, null, rs);
		return count;
	}

	public List<News> getPageNewsList(Integer tid, int pageNo, int pageSize)
			throws SQLException {

		List<News> newsList = new ArrayList<News>();
		ResultSet rs = null;
		List<Object> params = new ArrayList<Object>();

		String sql = "select nid,ntid,ntitle,nauthor,ncreateDate,nsummary,tname "
				+ "from news,topic where news.ntid=topic.tid ";

		if (tid != null) {
			sql += " and news.ntid=?";
			params.add(tid);
		}
		sql += " order by ncreateDate desc limit ?,?";
		params.add((pageNo - 1) * pageSize);
		params.add(pageSize);
		rs = this.executeQuery(sql, params.toArray());
		while (rs.next()) {
			News news = new News();
			news.setNid(rs.getInt("nid"));
			news.setNtid(rs.getInt("ntid"));
			news.setNtitle(rs.getString("ntitle"));
			news.setNauthor(rs.getString("nauthor"));
			news.setNcreateDate(rs.getString("ncreateDate"));
			news.setNsummary(rs.getString("nsummary"));
			newsList.add(news);
		}
		DataBaseUtil.closeAll(null, null, rs);
		return newsList;
	}

	public int addNews(News news) throws SQLException {
		String sql = "INSERT INTO news(ntid,ntitle,nauthor,ncreateDate,npicPath,ncontent,nmodifyDate,nsummary)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		int result = this.executeUpdate(sql, news.getNtid(), news.getNtitle(),
				news.getNauthor(), news.getNcreateDate(), news.getNpicPath(),
				news.getNcontent(), news.getNmodifyDate(), news.getNsummary());
		return result;
	}

	// 修改新闻
	public int editNews(News news) throws SQLException {
		String sql = "UPDATE news SET ntid=?,ntitle=?,nauthor=?,npicPath=?,ncontent=?,nsummary=?"
				+ " WHERE nid=? ";
		int result = this.executeUpdate(sql, news.getNtid(), news.getNtitle(),
				news.getNauthor(), news.getNpicPath(), news.getNcontent(),
				news.getNsummary(), news.getNid());
		return result;
	}

	public List<List<News>> findLatestNewsByTid(Map<Integer, Integer> topics)
			throws SQLException {

		List<List<News>> list = new ArrayList<List<News>>();
		ResultSet rs = null;
		Set<Integer> keysIntegers = topics.keySet();// 取出所有key的集合
		Iterator<Integer> it = keysIntegers.iterator();// 获取Iterator对象

		while (it.hasNext()) {
			Integer tid = it.next();// key
			Integer limit = topics.get(tid);// 取出value
			String sql = "select nid,ntid,ntitle from news where"
					+ " ntid =? order by ncreateDate desc limit ?";
			rs = this.executeQuery(sql, tid, limit);
			List<News> list2 = new ArrayList<News>();

			News news = null;

			while (rs.next()) {
				news = new News();
				news.setNid(rs.getInt("nid"));
				news.setNtid(rs.getInt("ntid"));
				news.setNtitle(rs.getString("ntitle"));
				list2.add(news);
			}
			list.add(list2);// 装进大list里面

		}
		DataBaseUtil.closeAll(null, null, rs);
		return list;
	}
}
