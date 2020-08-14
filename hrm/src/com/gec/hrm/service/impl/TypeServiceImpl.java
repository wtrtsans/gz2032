package com.gec.hrm.service.impl;

import java.util.List;

import com.gec.hrm.dao.ITypeDao;
import com.gec.hrm.dao.impl.TypeDaoImpl;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.Type;
import com.gec.hrm.service.ITypeService;

public class TypeServiceImpl implements ITypeService {
	
	private ITypeDao typeDao = new TypeDaoImpl();

	@Override
	public List<Type> selectAll() {
		return typeDao.selectAll();
	}

	@Override
	public int edit(Type type) {
		return typeDao.edit(type);
	}

	@Override
	public Type selectId(int id) {
		return typeDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return typeDao.del(ids);
	}

	@Override
	public int add(Type type) {
		return typeDao.add(type);
	}

	@Override
	public PageBean<Type> getOnePage(int dangQianPage, int pageSize, Type type) {
		return typeDao.getOnePage(dangQianPage, pageSize, type);
	}

}
