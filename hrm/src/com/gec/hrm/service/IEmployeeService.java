package com.gec.hrm.service;

import com.gec.hrm.entity.Employee;
import com.gec.hrm.entity.PageBean;

public interface IEmployeeService {
	
	Employee selectByCardId(String cardId);
	
	int edit(Employee employee);

	Employee selectId(int id);
	
	int del(int[] ids);

	int add(Employee employee);

	PageBean<Employee> getOnePage(int dangQianPage, int pageSize, Employee employee);
	
}
