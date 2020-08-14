package com.gec.hrm.service.impl;


import java.util.List;

import com.gec.hrm.dao.IDocumentDao;
import com.gec.hrm.dao.impl.DocumentDaoImpl;
import com.gec.hrm.entity.Document;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IDocumentService;

public class DocumentServiceImpl implements IDocumentService {
	
	private IDocumentDao documentDao = new DocumentDaoImpl();

	@Override
	public int edit(Document document) {
		return documentDao.edit(document);
	}

	@Override
	public Document selectId(int id) {
		return documentDao.selectId(id);
	}

	@Override
	public int del(int[] ids) {
		return documentDao.del(ids);
	}

	@Override
	public int add(Document document) {
		return documentDao.add(document);
	}

	@Override
	public PageBean<Document> getOnePage(int dangQianPage, int pageSize, Document document) {
		return documentDao.getOnePage(dangQianPage, pageSize, document);
	}

	@Override
	public List<Document> selectAll(Document document) {
		return documentDao.selectAll(document);
	}

}
