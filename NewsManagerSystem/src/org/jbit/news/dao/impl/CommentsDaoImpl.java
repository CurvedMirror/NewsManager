package org.jbit.news.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.CommentsDao;
import org.jbit.news.entity.Comments;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class CommentsDaoImpl extends BaseDao implements CommentsDao {
	public CommentsDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public CommentsDaoImpl(Connection conn) {
		super(conn);
	}

	// 查询所有留言
	public List<Comments> getAllComments(int nid) throws SQLException {
		List<Comments> list = new ArrayList<Comments>();
		ResultSet rs = null;
		Comments comments = null;
		String sql = "SELECT * FROM `comments`  WHERE cnid=? ORDER BY cdate DESC LIMIT 3";
		rs = super.executeQuery(sql, nid);
		while (rs.next()) {
			comments = new Comments();
			comments.setCnid(rs.getInt("cnid"));
			comments.setCid(rs.getInt("cid"));
			comments.setContent(rs.getString("ccontent"));
			comments.setCdate(rs.getString("cdate"));
			comments.setCip(rs.getString("cip"));
			comments.setCauthor(rs.getString("cauthor"));
			list.add(comments);
		}
		DataBaseUtil.closeAll(conn, null, rs);

		return list;
	}

	// 插入留言到指定新闻下面
	public int insertComment(Comments comments) throws SQLException {

		String sql = "INSERT INTO comments(cnid,ccontent,cdate,cip,cauthor) VALUES(?,?,?,?,? )";
		return super.executeUpdate(sql, comments.getCnid(),
				comments.getCcontent(), comments.getCdate(), comments.getCip(),
				comments.getCauthor());
	}

	// 根据新闻id删除评论
	public int deleteCommentsByCnid(int cnid) throws SQLException {
		String sql = "DELETE  FROM comments WHERE cnid=?";
		return super.executeUpdate(sql, cnid);
	}

	// 删除指定新闻下的单个评论
	public int deleteCommentByCid(int cid) throws SQLException {
		String sql = "DELETE FROM comments WHERE cid=?";
		return super.executeUpdate(sql, cid);
	}
}
