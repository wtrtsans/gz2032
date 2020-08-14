package com.gec.hrm.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.hrm.dao.IDocumentDao;
import com.gec.hrm.entity.Document;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.util.AliUtil;

public class DocumentDaoImpl extends AliUtil<Document> implements IDocumentDao {

	@Override
	public int edit(Document document) {
		String sql = "UPDATE `document_inf` SET `TITLE` = ?";
		if(document.getFilename()!=null&&document.getFilename()!="") {
			sql += ", `filename` = '"+document.getFilename()+"', `filetype` = '"+document.getFiletype()+"', `filebytes` = '"+document.getFilebytes()+"'";
		}
		sql += ", `REMARK` = ?, `USER_ID` = ? WHERE `ID` = ?";
		Object[] objects = {document.getTitle(), document.getRemark(), document.getUser().getId(), document.getId()};
		return executeUpdate(sql ,objects);
	}

	@Override
	public Document selectId(int id) {
		String sql = "SELECT * FROM `document_inf` WHERE `ID` = ?";
		Object[] objects = {id};
		List<Document> list = executeQuery(sql ,objects);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int del(int[] ids) {
		String sql = "DELETE FROM `document_inf` WHERE `ID` = ?";
		int flag = 1;
		for(int id : ids) {
			if(executeUpdate(sql ,(Object)id)==0) {
				flag = 0;
			}
		}
		return flag;
	}

	@Override
	public int add(Document document) {
		String sql = "INSERT INTO `document_inf`(`TITLE`, `filename`, `filetype`, `filebytes`, `REMARK`, `USER_ID`) VALUES(?, ?, ?, ?, ?, ?)";
		Object[] objects = {document.getTitle(), document.getFilename(), document.getFiletype(), document.getFilebytes(), document.getRemark(), document.getUser().getId()};
		return executeUpdate(sql ,objects);
	}
    
    public int getCount(Document document) {
		int count = 0;
		String sql = "select count(1) as count from `document_inf` where 1=1 ";
		if(document.getTitle()!=null&&document.getTitle()!="")
			sql += "and `TITLE` like '%" + document.getTitle() + "%'";
		count = getCount(sql);
		return count;
	}
	
	private List<Document> getOnePageInfo(int dangQianPage, int pageSize, Document document) {
		String sql = "select * from `document_inf` where 1=1 ";
		if(document.getTitle()!=null&&document.getTitle()!="")
			sql += "and `TITLE` like '%" + document.getTitle() + "%'";
		sql += " ORDER BY `ID` LIMIT ?,?";
		Object[] objects = {(dangQianPage-1)*pageSize,pageSize};
		List<Document> onePageList = executeQuery(sql, objects);
		return onePageList;
	}

	@Override
	public PageBean<Document> getOnePage(int dangQianPage, int pageSize, Document document) {
		List<Document> list = getOnePageInfo(dangQianPage, pageSize, document);
		PageBean<Document> pageBean = new PageBean<Document>(dangQianPage, getCount(document), pageSize, list);
		return pageBean;
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document document = new Document();
		document.setId(rs.getInt(1));
		document.setTitle(rs.getString(2));
		document.setFilename(rs.getString(3));
		document.setFiletype(rs.getString(4));
		document.setFilebytes(rs.getString(5));
		document.setRemark(rs.getString(6));
		document.setCreateDate(rs.getTimestamp(7));
		document.setUser(new UserDaoImpl().selectId(rs.getInt(8)));
		return document;
	}

	@Override
	public List<Document> selectAll(Document document) {
		String sql = "select * from `document_inf` where 1=1 ";
		if(document.getTitle()!=null&&document.getTitle()!="")
			sql += "and `TITLE` like '%" + document.getTitle() + "%'";
		List<Document> onePageList = executeQuery(sql);
		return onePageList;
	}

}
