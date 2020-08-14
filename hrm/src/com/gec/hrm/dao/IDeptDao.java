package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.entity.Dept;
import com.gec.hrm.entity.PageBean;

public interface IDeptDao {
	
	List<Dept> selectAll();
	
	int edit(Dept dept);

	Dept selectId(int id);
	
	int del(int[] ids);

	int add(Dept dept);

	PageBean<Dept> getOnePage(int dangQianPage, int pageSize, Dept dept);
	
}