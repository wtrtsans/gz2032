package com.gec.hrm.service;

import java.util.List;

import com.gec.hrm.entity.Job;
import com.gec.hrm.entity.PageBean;

public interface IJobService {
	
	List<Job> selectAll();
	
	int edit(Job job);

	Job selectId(int id);
	
	int del(int[] ids);

	int add(Job job);

	PageBean<Job> getOnePage(int dangQianPage, int pageSize, Job job);
	
}
