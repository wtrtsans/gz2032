package com.gec.hrm.entity;

import java.util.List;

public class PageBean<T> {
	private int dangQianPage;
	private int totalPage;
	private int count;
	private int pageSize;
	private List<T> lists;
	
	
	public PageBean(int dangQianPage, int count, int pageSize, List<T> lists) {
		super();
		this.dangQianPage = dangQianPage;
		this.count = count;
		this.pageSize = pageSize;
		this.lists = lists;
		getTotalPage();
	}
	
	public PageBean() {
		super();
	}

	public int getDangQianPage() {
		return dangQianPage;
	}
	public void setDangQianPage(int dangQianPage) {
		this.dangQianPage = dangQianPage;
	}
	public int getTotalPage() {
		if (count%pageSize==0) {
			totalPage = count/pageSize;
		} else {
			totalPage = count/pageSize+1;
		}
		return totalPage;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	
	
}
