package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.IDeptDao;
import com.gec.hrm.entity.Dept;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.util.AliUtil;

public class DeptDaoImpl extends AliUtil<Dept> implements IDeptDao {

	@Override
	public List<Dept> selectAll() {
		String sql = "SELECT * FROM `dept_inf`";
		return executeQuery(sql);
	}

	@Override
	public int edit(Dept dept) {
		String sql = "UPDATE `dept_inf` SET `NAME` = ?, `REMARK` = ?, `state` = ? WHERE `ID` = ?";
		Object[] objects = {dept.getName(), dept.getRemark(), dept.getState(), dept.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Dept selectId(int id) {
		String sql = "SELECT * FROM `dept_inf` WHERE `ID` = ?";
		Object[] objects = {id};
		List<Dept> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `dept_inf` WHERE `ID` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Dept dept) {
		String sql = "INSERT INTO `dept_inf`(`NAME`,`REMARK`,`state`) VALUES(?,?,?)";
		Object[] objects = {dept.getName(), dept.getRemark(), dept.getState()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dept = new Dept();
		dept.setId(rs.getInt(1));
		dept.setName(rs.getString(2));
		dept.setRemark(rs.getString(3));
		dept.setState(rs.getInt(4));
		return dept;
	}
    
    public int getCount(Dept dept) {
		int count = 0;
		String sql = "select count(1) as count from `dept_inf` where 1=1 ";
		if(dept.getName()!=null&&dept.getName()!="")
			sql += "and `NAME` like '%" + dept.getName() + "%'";
		count = getCount(sql);
		return count;
	}
	
	private List<Dept> getOnePageInfo(int dangQianPage, int pageSize, Dept dept) {
		String sql = "select * from `dept_inf` where 1=1 ";
		if(dept.getName()!=null&&dept.getName()!="")
			sql += "and `NAME` like '%" + dept.getName() + "%'";
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Dept> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Dept> getOnePage(int dangQianPage, int pageSize, Dept dept) {
		List<Dept> list = getOnePageInfo(dangQianPage, pageSize, dept);
		PageBean<Dept> pageBean = new PageBean<Dept>(dangQianPage, getCount(dept), pageSize, list);
		return pageBean;
	}

}
