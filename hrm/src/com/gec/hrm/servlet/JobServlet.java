package com.gec.hrm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.Job;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IJobService;
import com.gec.hrm.service.impl.JobServiceImpl;

@WebServlet("/jobServlet")
public class JobServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IJobService jobService = new JobServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("jobn");
		Job job = new Job();
		job.setName(name);
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq=="")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<Job> page = jobService.getOnePage(dangQianPage, 10, job);
		if(page.getTotalPage()<page.getDangQianPage())
			page = jobService.getOnePage(page.getTotalPage(), 10, job);
		request.setAttribute("page", page);
		request.setAttribute("job", job);
		request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
	}
	
	public void toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
	}
	
	public void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		Job job = new Job();
		job.setName(name);
		job.setRemark(remark);
		if(jobService.add(job)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/jobServlet?action=list").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("jobIds");
		int[] ids = new int[strIds.length];
		System.out.println(strIds.length);
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		jobService.del(ids);
		request.getRequestDispatcher("/jobServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void toEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		Job job = jobService.selectId(id);
		request.setAttribute("ejob", job);
		request.setAttribute("dq", request.getParameter("dq"));
		request.getRequestDispatcher("/WEB-INF/jsp/job/jobedit.jsp").forward(request, response);
	}
	
	public void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		String name = request.getParameter("name");
		String remark = request.getParameter("remark");
		Job job = new Job();
		job.setId(id);
		job.setName(name);
		job.setRemark(remark);
		if(jobService.edit(job)==0) {
			response.getWriter().write("修改失败！！");
			return;
		}
		request.getRequestDispatcher("/jobServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
}
