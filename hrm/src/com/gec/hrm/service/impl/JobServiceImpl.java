package com.gec.hrm.service.impl;

import java.util.List;

import com.gec.hrm.dao.IJobDao;
import com.gec.hrm.dao.impl.JobDaoImpl;
import com.gec.hrm.entity.Job;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IJobService;

public class JobServiceImpl implements IJobService {
	
	private IJobDao jobDao = new JobDaoImpl();

	@Override
	public int edit(Job job) {
		return jobDao.edit(job);
	}

	@Override
	public Job selectId(int id) {
		return jobDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return jobDao.del(ids);
	}

	@Override
	public int add(Job job) {
		return jobDao.add(job);
	}

	@Override
	public PageBean<Job> getOnePage(int dangQianPage, int pageSize, Job job) {
		return jobDao.getOnePage(dangQianPage, pageSize, job);
	}

	@Override
	public List<Job> selectAll() {
		// TODO Auto-generated method stub
		return jobDao.selectAll();
	}

}
