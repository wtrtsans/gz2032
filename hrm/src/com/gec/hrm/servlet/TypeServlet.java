package com.gec.hrm.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.Type;
import com.gec.hrm.service.ITypeService;
import com.gec.hrm.service.impl.TypeServiceImpl;

@WebServlet("/typeServlet")
public class TypeServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ITypeService typeService = new TypeServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("n");
		Type type = new Type();
		type.setName(name);
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq==""||dq=="null")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<Type> page = typeService.getOnePage(dangQianPage, 10, type);
		if(page.getTotalPage()<page.getDangQianPage())
			page = typeService.getOnePage(page.getTotalPage(), 10, type);
		request.setAttribute("page", page);
		request.setAttribute("type", type);
		request.getRequestDispatcher("/WEB-INF/jsp/notice/typelist.jsp").forward(request, response);
	}
	
	public void toAddOrEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		if(strId!=null&&strId!="") {
			int id;
			id = new Integer(strId);
			Type type = typeService.selectId(id);
			request.setAttribute("etype", type);
			request.setAttribute("dq", request.getParameter("dq"));
		}
		request.getRequestDispatcher("/WEB-INF/jsp/notice/type_save_update.jsp").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("typeIds");
		int[] ids = new int[strIds.length];
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		typeService.del(ids);
		request.getRequestDispatcher("/typeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void doAddOrEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Type type = new Type();
		type.setName(request.getParameter("name"));
		type.setUserId(new Integer(request.getParameter("userId")));
		String strId = request.getParameter("id");
		
		if(strId!=null&&strId!="") {
			type.setId(new Integer(strId));
			type.setModifyDate(new Date());
			if(typeService.edit(type)==0) {
				response.getWriter().write("修改失败！！");
				return;
			}
			request.getRequestDispatcher("/typeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
		}else {
			if(typeService.add(type)==0) {
				response.getWriter().write("添加失败！！");
				return;
			}
			request.getRequestDispatcher("/typeServlet?action=list").forward(request, response);
		}
	}
	
}
