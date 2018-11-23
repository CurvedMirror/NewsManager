package org.jbit.news.entity;

public class Topics {
	private int tid;
	private String tname;
	
	public Topics() {
	}
	public Topics(int tid,String tname) {
		this.tid=tid;
		this.tname=tname;
	}
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
}
