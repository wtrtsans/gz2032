package com.gec.hrm.service.impl;

import java.util.List;

import com.gec.hrm.dao.IUserDao;
import com.gec.hrm.dao.impl.UserDaoImpl;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.User;
import com.gec.hrm.service.IUserService;



public class UserServiceImpl implements IUserService {

	private IUserDao userDao = new UserDaoImpl();

	@Override
	public int edit(User user) {
		return userDao.edit(user);
	}

	@Override
	public User selectId(int id) {
		return userDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return userDao.del(ids);
	}

	@Override
	public User selectByLoginName(String loginName) {
		return userDao.selectByLoginName(loginName);
	}
	
	@Override
	public int add(User user) {
		return userDao.add(user);
	}
	
	@Override
	public User login(String name ,String pwd) {
		return userDao.login(name ,pwd);
	}

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	@Override
	public PageBean<User> getOnePage(int dangQianPage, int pageSize, User user) {	
		return userDao.getOnePage(dangQianPage, pageSize, user);
	}

}
