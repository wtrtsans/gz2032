package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.IJobDao;
import com.gec.hrm.entity.Job;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.util.AliUtil;

public class JobDaoImpl extends AliUtil<Job> implements IJobDao {

	@Override
	public List<Job> selectAll() {
		String sql = "SELECT * FROM `job_inf`";
		return executeQuery(sql);
	}

	@Override
	public int edit(Job job) {
		String sql = "UPDATE `job_inf` SET `NAME` = ?, `REMARK` = ?, `state` = ? WHERE `ID` = ?";
		Object[] objects = {job.getName(), job.getRemark(), job.getState(), job.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Job selectId(int id) {
		String sql = "SELECT * FROM `job_inf` WHERE `ID` = ?";
		Object[] objects = {id};
		List<Job> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `job_inf` WHERE `ID` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Job job) {
		String sql = "INSERT INTO `job_inf`(`NAME`,`REMARK`,`state`) VALUES(?,?,?)";
		Object[] objects = {job.getName(), job.getRemark(), job.getState()};
		return executeUpdate(sql ,objects);
	}
    
    public int getCount(Job job) {
		int count = 0;
		String sql = "select count(1) as count from `job_inf` where 1=1 ";
		if(job.getName()!=null&&job.getName()!="")
			sql += "and `NAME` like '%" + job.getName() + "%'";
		count = getCount(sql);
		return count;
	}
	
	private List<Job> getOnePageInfo(int dangQianPage, int pageSize, Job job) {
		String sql = "select * from `job_inf` where 1=1 ";
		if(job.getName()!=null&&job.getName()!="")
			sql += "and `NAME` like '%" + job.getName() + "%'";
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Job> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Job> getOnePage(int dangQianPage, int pageSize, Job job) {
		List<Job> list = getOnePageInfo(dangQianPage, pageSize, job);
		PageBean<Job> pageBean = new PageBean<Job>(dangQianPage, getCount(job), pageSize, list);
		return pageBean;
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job job = new Job();
		job.setId(rs.getInt(1));
		job.setName(rs.getString(2));
		job.setRemark(rs.getString(3));
		job.setState(rs.getInt(4));
		return job;
	}

}
