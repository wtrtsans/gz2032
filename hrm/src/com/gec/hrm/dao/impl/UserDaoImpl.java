package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.IUserDao;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.User;
import com.gec.hrm.util.AliUtil;


public class UserDaoImpl extends AliUtil<User> implements IUserDao {

	@Override
	public int edit(User user) {
		String sql = "UPDATE `user_inf` SET `loginname` = ?, `PASSWORD` = ?, `username` = ?, `STATUS` = ? WHERE `id` = ?";
		Object[] objects = {user.getLoginname(),user.getPassword(),user.getUsername(),user.getStatus(),user.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public User selectId(int id) {
		String sql = "SELECT * FROM `user_inf` WHERE `id` = ?";
		Object[] objects = {id};
		List<User> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `user_inf` WHERE `id` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public User selectByLoginName(String loginName) {
		String sql = "SELECT * FROM `user_inf` WHERE `loginname` = ?";
		Object[] objects = {loginName};
		List<User> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int add(User user) {
		String sql = "INSERT INTO `user_inf`(`loginname`, `PASSWORD`, `STATUS`, `username`) VALUES(?,?,?,?)";
		Object[] objects = {user.getLoginname(),user.getPassword(),user.getStatus(),user.getUsername()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public User login(String name ,String pwd) {
		String sql = "SELECT * FROM `user_inf` WHERE `loginname` = ? AND `PASSWORD` = ?";
		Object[] objects = {name, pwd};
		List<User> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setLoginname(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(rs.getInt(4));
		user.setCreatedate(rs.getTimestamp(5));
		user.setUsername(rs.getString(6));
		return user;
	}

	@Override
	public List<User> selectAll() {
		String sql = "SELECT * FROM `user_inf`";
		return executeQuery(sql);
	}
	

    
    public int getCount(User user) {
		int count = 0;
		String sql = "select count(1) as count from `user_inf` where 1=1 ";
		if(user.getLoginname()!=null&&user.getLoginname()!="")
			sql += "and loginname like '%" + user.getLoginname() + "%'";
		if(user.getUsername()!=null&&user.getUsername()!="")
			sql += "and username like '%" + user.getUsername() + "%'";
		if(user.getStatus()!=-999)
			sql += "and STATUS = " + user.getStatus();
		count = getCount(sql);
		return count;
	}
	
	private List<User> getOnePageInfo(int dangQianPage, int pageSize, User user) {
		String sql = "select * from `user_inf` where 1=1 ";
		if(user.getLoginname()!=null&&user.getLoginname()!="")
			sql += "and loginname like '%" + user.getLoginname() + "%'";
		if(user.getUsername()!=null&&user.getUsername()!="")
			sql += "and username like '%" + user.getUsername() + "%'";
		if(user.getStatus()!=-999)
			sql += "and STATUS = " + user.getStatus();
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<User> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<User> getOnePage(int dangQianPage, int pageSize, User user) {
		List<User> list = getOnePageInfo(dangQianPage, pageSize, user);
		PageBean<User> pageBean = new PageBean<User>(dangQianPage, getCount(user), pageSize, list);
		return pageBean;
	}

}
