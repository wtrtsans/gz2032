package com.gec.hrm.service.impl;

import com.gec.hrm.dao.IEmployeeDao;
import com.gec.hrm.dao.impl.EmployeeDaoImpl;
import com.gec.hrm.entity.Employee;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {
	
	private IEmployeeDao employeeDao = new EmployeeDaoImpl();

	@Override
	public Employee selectByCardId(String cardId) {
		return employeeDao.selectByCardId(cardId);
	}

	@Override
	public int edit(Employee employee) {
		return employeeDao.edit(employee);
	}

	@Override
	public Employee selectId(int id) {
		return employeeDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return employeeDao.del(ids);
	}

	@Override
	public int add(Employee employee) {
		return employeeDao.add(employee);
	}

	@Override
	public PageBean<Employee> getOnePage(int dangQianPage, int pageSize, Employee employee) {
		return employeeDao.getOnePage(dangQianPage, pageSize, employee);
	}

}
