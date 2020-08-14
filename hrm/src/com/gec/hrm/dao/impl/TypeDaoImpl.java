package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.ITypeDao;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.Type;
import com.gec.hrm.util.AliUtil;

public class TypeDaoImpl extends AliUtil<Type> implements ITypeDao {

	@Override
	public List<Type> selectAll() {
		String sql = "SELECT * FROM `type_inf`";
		return executeQuery(sql);
	}

	@Override
	public int edit(Type type) {
		String sql = "UPDATE `type_inf` SET `name` = ?, `user_id` = ?, `modify_date` = ? WHERE `id` = ?";
		Object[] objects = {type.getName(), type.getUserId(), type.getModifyDate(), type.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Type selectId(int id) {
		String sql = "SELECT * FROM `type_inf` WHERE `id` = ?";
		Object[] objects = {id};
		List<Type> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `type_inf` WHERE `id` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Type type) {
		String sql = "INSERT INTO `type_inf`(`name`,`user_id`) VALUES(?,?)";
		Object[] objects = {type.getName(), type.getUserId()};
		return executeUpdate(sql ,objects);
	}
    
    public int getCount(Type type) {
		int count = 0;
		String sql = "select count(1) as count from `type_inf` where 1=1 ";
		if(type.getName()!=null&&type.getName()!="")
			sql += "and `name` like '%" + type.getName() + "%'";
		count = getCount(sql);
		return count;
	}
	
	private List<Type> getOnePageInfo(int dangQianPage, int pageSize, Type type) {
		String sql = "select * from `type_inf` where 1=1 ";
		if(type.getName()!=null&&type.getName()!="")
			sql += "and `name` like '%" + type.getName() + "%'";
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Type> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Type> getOnePage(int dangQianPage, int pageSize, Type type) {
		List<Type> list = getOnePageInfo(dangQianPage, pageSize, type);
		PageBean<Type> pageBean = new PageBean<Type>(dangQianPage, getCount(type), pageSize, list);
		return pageBean;
	}

	@Override
	public Type getEntity(ResultSet rs) throws Exception {
		Type type = new Type();
		type.setId(rs.getInt(1));
		type.setName(rs.getString(2));
		type.setCreateDate(rs.getDate(3));
		type.setState(rs.getInt(4));
		type.setUserId(rs.getInt(5));
		type.setModifyDate(rs.getDate(6));
		return type;
	}

}
