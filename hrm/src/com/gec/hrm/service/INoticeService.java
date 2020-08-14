package com.gec.hrm.service;

import com.gec.hrm.entity.Notice;
import com.gec.hrm.entity.PageBean;

public interface INoticeService {
	
	int edit(Notice notice);

	Notice selectId(int id);
	
	int del(int[] ids);

	int add(Notice notice);

	PageBean<Notice> getOnePage(int dangQianPage, int pageSize, Notice notice);
	
}
