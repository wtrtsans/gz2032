package com.gec.hrm.service.impl;

import java.util.List;

import com.gec.hrm.dao.IDeptDao;
import com.gec.hrm.dao.impl.DeptDaoImpl;
import com.gec.hrm.entity.Dept;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IDeptService;

public class DeptServiceImpl implements IDeptService {
	
	private IDeptDao deptDao = new DeptDaoImpl();

	@Override
	public int edit(Dept dept) {
		// TODO Auto-generated method stub
		return deptDao.edit(dept);
	}

	@Override
	public Dept selectId(int id) {
		// TODO Auto-generated method stub
		return deptDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		// TODO Auto-generated method stub
		return deptDao.del(ids);
	}

	@Override
	public int add(Dept dept) {
		// TODO Auto-generated method stub
		return deptDao.add(dept);
	}

	@Override
	public PageBean<Dept> getOnePage(int dangQianPage, int pageSize, Dept dept) {
		// TODO Auto-generated method stub
		return deptDao.getOnePage(dangQianPage, pageSize, dept);
	}

	@Override
	public List<Dept> selectAll() {
		// TODO Auto-generated method stub
		return deptDao.selectAll();
	}

}
