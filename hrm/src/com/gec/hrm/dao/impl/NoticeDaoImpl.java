package com.gec.hrm.dao.impl;


import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.INoticeDao;
import com.gec.hrm.entity.Notice;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.util.AliUtil;

public class NoticeDaoImpl extends AliUtil<Notice> implements INoticeDao {

	@Override
	public int edit(Notice notice) {
		String sql = "UPDATE `notice_inf` SET `name` = ?,`type_id` = ?, `content` = ?, `user_id` = ?, `modify_date` = ? WHERE `id` = ?";
		Object[] objects = {notice.getName(), notice.getType().getId(), notice.getContent(), notice.getUserId(), notice.getModifyDate(), notice.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Notice selectId(int id) {
		String sql = "SELECT * FROM `notice_inf` WHERE `id` = ?";
		Object[] objects = {id};
		List<Notice> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `notice_inf` WHERE `id` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Notice notice) {
		String sql = "INSERT INTO `notice_inf`(`name`,`type_id`,`content`,`user_id`) VALUES(?,?,?,?)";
		Object[] objects = {notice.getName(), notice.getType().getId(), notice.getContent(), notice.getUserId()};
		return executeUpdate(sql ,objects);
	}
    
    public int getCount(Notice notice) {
		int count = 0;
		String sql = "select count(1) as count from `notice_inf` where 1=1 ";
		if(notice.getName()!=null&&notice.getName()!="")
			sql += "and `name` like '%" + notice.getName() + "%'";
//		if(notice.getType().getId()!=0)
//			sql += "and `type_id` = '%" + notice.getType().getId() + "%'";
		count = getCount(sql);
		return count;
	}
	
	private List<Notice> getOnePageInfo(int dangQianPage, int pageSize, Notice notice) {
		String sql = "select * from `notice_inf` where 1=1 ";
		if(notice.getName()!=null&&notice.getName()!="")
			sql += "and `name` like '%" + notice.getName() + "%'";
//		if(notice.getType().getId()!=0)
//			sql += "and `type_id` = '%" + notice.getType().getId() + "%'";
		sql += " ORDER BY `id` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Notice> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Notice> getOnePage(int dangQianPage, int pageSize, Notice notice) {
		List<Notice> list = getOnePageInfo(dangQianPage, pageSize, notice);
		PageBean<Notice> pageBean = new PageBean<Notice>(dangQianPage, getCount(notice), pageSize, list);
		return pageBean;
	}

	@Override
	public Notice getEntity(ResultSet rs) throws Exception {
		Notice notice = new Notice();
		notice.setId(rs.getInt(1));
		notice.setName(rs.getString(2));
		notice.setCreateDate(rs.getDate(3));
		notice.setType(new TypeDaoImpl().selectId(rs.getInt(4)));
		notice.setContent(rs.getString(5));
		notice.setUserId(rs.getInt(6));
		notice.setModifyDate(rs.getDate(7));
		return notice;
	}

}
