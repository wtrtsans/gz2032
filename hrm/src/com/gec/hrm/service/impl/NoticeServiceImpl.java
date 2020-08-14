package com.gec.hrm.service.impl;

import com.gec.hrm.dao.INoticeDao;
import com.gec.hrm.dao.impl.NoticeDaoImpl;
import com.gec.hrm.entity.Notice;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.INoticeService;

public class NoticeServiceImpl implements INoticeService {
	
	private INoticeDao noticeDao = new NoticeDaoImpl();

	@Override
	public int edit(Notice notice) {
		return noticeDao.edit(notice);
	}

	@Override
	public Notice selectId(int id) {
		return noticeDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return noticeDao.del(ids);
	}

	@Override
	public int add(Notice notice) {
		return noticeDao.add(notice);
	}

	@Override
	public PageBean<Notice> getOnePage(int dangQianPage, int pageSize, Notice notice) {
		return noticeDao.getOnePage(dangQianPage, pageSize, notice);
	}

}
