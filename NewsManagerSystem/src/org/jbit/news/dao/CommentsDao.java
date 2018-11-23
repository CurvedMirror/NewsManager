package org.jbit.news.dao;

import java.sql.SQLException;
import java.util.List;

import org.jbit.news.entity.Comments;

public interface CommentsDao {
	// 查询所有留言
	List<Comments> getAllComments(int nid) throws SQLException;
	// 插入评论到指定新闻下面
	int insertComment(Comments comments) throws SQLException;
	// 根据新闻id删除评论
	int deleteCommentsByCnid(int cnid) throws SQLException;
	//删除指定新闻下的单个评论
	int deleteCommentByCid(int cid) throws SQLException;
}
