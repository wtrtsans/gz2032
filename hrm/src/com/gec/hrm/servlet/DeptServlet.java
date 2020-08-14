package com.gec.hrm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.Dept;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IDeptService;
import com.gec.hrm.service.impl.DeptServiceImpl;

@WebServlet("/deptServlet")
public class DeptServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IDeptService deptService = new DeptServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("deptn");
		Dept dept = new Dept();
		dept.setName(name);
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq=="")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<Dept> page = deptService.getOnePage(dangQianPage, 10, dept);
		if(page.getTotalPage()<page.getDangQianPage())
			page = deptService.getOnePage(page.getTotalPage(), 10, dept);
		request.setAttribute("page", page);
		request.setAttribute("dept", dept);
		request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
	}
	
	public void toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
	}
	
	public void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		Dept dept = new Dept();
		dept.setName(name);
		dept.setRemark(remark);
		if(deptService.add(dept)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/deptServlet?action=list").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("deptIds");
		int[] ids = new int[strIds.length];
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		deptService.del(ids);
		request.getRequestDispatcher("/deptServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void toEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		Dept dept = deptService.selectId(id);
		request.setAttribute("edept", dept);
		request.setAttribute("dq", request.getParameter("dq"));
		request.getRequestDispatcher("/WEB-INF/jsp/dept/deptedit.jsp").forward(request, response);
	}
	
	public void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		Dept dept = new Dept();
		dept.setId(id);
		dept.setName(name);
		dept.setRemark(remark);
		if(deptService.edit(dept)==0) {
			response.getWriter().write("修改失败！！");
			return;
		}
		request.getRequestDispatcher("/deptServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
}
