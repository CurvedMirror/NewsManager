package org.jbit.news.entity;

import java.util.List;

public class Page {
	// 当前页数
	private int currPageNo = 1;
	// 总页数
	private int totalPageCount = 0;
	// 页面大小
	private int pageSize = 10;
	// 记录总数
	private int totalCount;
	// 每页新闻集合
	private List<News> newsList;

	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if(totalCount>0){
			this.totalCount = totalCount;
			//计算总页数
			totalPageCount=this.totalCount%pageSize==0?
					(this.totalCount/pageSize):(this.totalCount/pageSize+1);
		}
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}
}
