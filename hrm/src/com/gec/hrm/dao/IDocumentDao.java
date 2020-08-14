package com.gec.hrm.dao;


import java.util.List;

import com.gec.hrm.entity.Document;
import com.gec.hrm.entity.PageBean;

public interface IDocumentDao {
	
	List<Document> selectAll(Document document);
	
	int edit(Document document);

	Document selectId(int id);
	
	int del(int[] ids);

	int add(Document document);

	PageBean<Document> getOnePage(int dangQianPage, int pageSize, Document document);
	
}