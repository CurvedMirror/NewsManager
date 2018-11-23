package org.jbit.news.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jbit.news.dao.CommentsDao;
import org.jbit.news.dao.impl.CommentsDaoImpl;
import org.jbit.news.entity.Comments;
import org.jbit.news.service.CommentsService;
import org.jbit.news.util.DatabaseUtil.DataBaseUtil;

public class CommentsServiceImpl implements CommentsService {
	Connection conn = DataBaseUtil.getConnection();
	CommentsDao commentsDao = new CommentsDaoImpl(conn);
	
	public int insertComment(Comments comments) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			if (comments.getCcontent() != "") {
				if (commentsDao.insertComment(comments) > 0) {
					result = 1;
				} else {
					result = 0;
				}
			}
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

	public int deleteCommentByCid(int cid) {
		int result = 0;
		try {
			conn.setAutoCommit(false);
			result = commentsDao.deleteCommentByCid(cid);
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

	public List<Comments> getAllComments(int nid) {
		try {
			return commentsDao.getAllComments(nid);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DataBaseUtil.closeAll(conn, null, null);
		}
		return null;
	}

	public int deleteCommentsByCnid(int cnid) {
		try {
			return commentsDao.deleteCommentByCid(cnid);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DataBaseUtil.closeAll(conn, null, null);
		}
		return 0;
	}
}
