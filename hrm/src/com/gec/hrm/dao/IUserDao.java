package com.gec.hrm.dao;

import java.util.List;

import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.User;


public interface IUserDao {
	
	int edit(User user);

    User selectId(int id);
	
	int del(int[] ids);
	
	User selectByLoginName(String loginName);
	
	//User selectByLoginNameNoMe(String loginName);
	
	int add(User user);
	
	List<User> selectAll();

    User login(String name ,String pwd);

	PageBean<User> getOnePage(int dangQianPage, int pageSize, User user);
}