package com.gec.hrm.dao;

import com.gec.hrm.entity.Employee;
import com.gec.hrm.entity.PageBean;

public interface IEmployeeDao {
	
	Employee selectByCardId(String cardId);
	
	int edit(Employee employee);

	Employee selectId(int id);
	
	int del(int[] ids);

	int add(Employee employee);

	PageBean<Employee> getOnePage(int dangQianPage, int pageSize, Employee employee);
	
}