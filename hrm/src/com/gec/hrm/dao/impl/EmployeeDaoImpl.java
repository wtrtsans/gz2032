package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.IEmployeeDao;
import com.gec.hrm.entity.Employee;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.util.AliUtil;

public class EmployeeDaoImpl extends AliUtil<Employee> implements IEmployeeDao {

	@Override
	public Employee selectByCardId(String cardId) {
		String sql = "SELECT * FROM `employee_inf` WHERE `CARD_ID` = ?";
		Object[] objects = {cardId};
		List<Employee> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int edit(Employee employee) {
		String sql = "UPDATE `employee_inf` SET `NAME` = ?, `CARD_ID` = ?, `ADDRESS` = ?"
				+" , `POST_CODE` = ?, `TEL` = ?, `PHONE` = ?, `QQ_NUM` = ?, `EMAIL` = ?"
				+" , `SEX` = ?, `PARTY` = ?, `BIRTHDAY` = ?, `RACE` = ?, `EDUCATION` = ?"
				+" , `SPECIALITY` = ?, `HOBBY` = ?, `REMARK` = ?, `dept_id` = ?, `job_id` = ? WHERE `ID` = ?";
		Object[] objects = {employee.getName(), employee.getCardId(), employee.getAddress(), employee.getPostCode(), employee.getTel(), employee.getPhone(), employee.getQqNum(), employee.getEmail(), employee.getSex(), employee.getParty(), employee.getBirthday(), employee.getRace(), employee.getEducation(), employee.getSpeciality(), employee.getHobby(), employee.getRemark(), employee.getDept().getId(), employee.getJob().getId(), employee.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Employee selectId(int id) {
		String sql = "SELECT * FROM `employee_inf` WHERE `ID` = ?";
		Object[] objects = {id};
		List<Employee> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `employee_inf` WHERE `ID` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Employee employee) {
		String sql = "INSERT INTO `employee_inf`(`NAME`, `CARD_ID`, `ADDRESS`, `POST_CODE`"
				+" , `TEL`, `PHONE`, `QQ_NUM`, `EMAIL`, `SEX`, `PARTY`, `BIRTHDAY`, `RACE`, `EDUCATION`"
				+" , `SPECIALITY`, `HOBBY`, `REMARK`, `dept_id`, `job_id`)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] objects = {employee.getName(), employee.getCardId(), employee.getAddress(), employee.getPostCode(), employee.getTel(), employee.getPhone(), employee.getQqNum(), employee.getEmail(), employee.getSex(), employee.getParty(), employee.getBirthday(), employee.getRace(), employee.getEducation(), employee.getSpeciality(), employee.getHobby(), employee.getRemark(), employee.getDept().getId(), employee.getJob().getId()};
		return executeUpdate(sql ,objects);
	}
    
    public int getCount(Employee employee) {
		int count = 0;
		String sql = "select count(1) as count from `employee_inf` where 1=1 ";
		if(employee.getName()!=null&&employee.getName()!="")
			sql += " and `NAME` like '%" + employee.getName() + "%'";
		if(employee.getCardId()!=null&&employee.getCardId()!="")
			sql += " and `CARD_ID` like '%" + employee.getCardId() + "%'";
		if(employee.getPhone()!=null&&employee.getPhone()!="")
			sql += " and `PHONE` like '%" + employee.getPhone() + "%'";
		if(employee.getSex()!=0&&employee.getSex()!=3)
			sql += " and `SEX` = " + employee.getSex();
		if(employee.getDept().getId()!=0)
			sql += " and `dept_id` = " + employee.getDept().getId();
		if(employee.getJob().getId()!=0)
			sql += " and `job_id` = " + employee.getJob().getId();
		count = getCount(sql);
		return count;
	}
	
	private List<Employee> getOnePageInfo(int dangQianPage, int pageSize, Employee employee) {
		String sql = "select * from `employee_inf` where 1=1 ";
		if(employee.getName()!=null&&employee.getName()!="")
			sql += " and `NAME` like '%" + employee.getName() + "%'";
		if(employee.getCardId()!=null&&employee.getCardId()!="")
			sql += " and `CARD_ID` like '%" + employee.getCardId() + "%'";
		if(employee.getPhone()!=null&&employee.getPhone()!="")
			sql += " and `PHONE` like '%" + employee.getPhone() + "%'";
		if(employee.getSex()!=0&&employee.getSex()!=3)
			sql += " and `SEX` = " + employee.getSex();
		if(employee.getDept().getId()!=0)
			sql += " and `dept_id` = " + employee.getDept().getId();
		if(employee.getJob().getId()!=0)
			sql += " and `job_id` = " + employee.getJob().getId();
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Employee> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Employee> getOnePage(int dangQianPage, int pageSize, Employee employee) {
		List<Employee> list = getOnePageInfo(dangQianPage, pageSize, employee);
		PageBean<Employee> pageBean = new PageBean<Employee>(dangQianPage, getCount(employee), pageSize, list);
		return pageBean;
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee employee = new Employee();
		employee.setId(rs.getInt(1));
		employee.setName(rs.getString(2));
		employee.setCardId(rs.getString(3));
		employee.setAddress(rs.getString(4));
		employee.setPostCode(rs.getString(5));
		employee.setTel(rs.getString(6));
		employee.setPhone(rs.getString(7));
		employee.setQqNum(rs.getString(8));
		employee.setEmail(rs.getString(9));
		employee.setSex(rs.getInt(10));
		employee.setParty(rs.getString(11));
		employee.setBirthday(rs.getTimestamp(12));
		employee.setRace(rs.getString(13));
		employee.setEducation(rs.getString(14));
		employee.setSpeciality(rs.getString(15));
		employee.setHobby(rs.getString(16));
		employee.setRemark(rs.getString(17));
		employee.setCreateDate(rs.getTimestamp(18));
		employee.setState(rs.getInt(19));
		employee.setDept(new DeptDaoImpl().selectId(rs.getInt(20)));
		employee.setJobId(new JobDaoImpl().selectId(rs.getInt(21)));
		return employee;
	}

}
