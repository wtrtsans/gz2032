package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.Type;

public interface ITypeDao {
	
	List<Type> selectAll();
	
	int edit(Type type);

	Type selectId(int id);
	
	int del(int[] ids);

	int add(Type type);

	PageBean<Type> getOnePage(int dangQianPage, int pageSize, Type type);
	
}