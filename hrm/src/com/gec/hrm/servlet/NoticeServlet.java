package com.gec.hrm.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.Notice;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.Type;
import com.gec.hrm.service.INoticeService;
import com.gec.hrm.service.impl.NoticeServiceImpl;
import com.gec.hrm.service.impl.TypeServiceImpl;

@WebServlet("/noticeServlet")
public class NoticeServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private INoticeService noticeService = new NoticeServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("n");
		Notice notice = new Notice();
		notice.setName(name);
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq==""||dq=="null")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<Notice> page = noticeService.getOnePage(dangQianPage, 10, notice);
		if(page.getTotalPage()<page.getDangQianPage())
			page = noticeService.getOnePage(page.getTotalPage(), 10, notice);
		request.setAttribute("page", page);
		request.setAttribute("notice", notice);
		request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
	}
	
	public void toAddOrEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {String strId = request.getParameter("id");
		if(strId!=null&&strId!="") {
			int id;
			id = new Integer(strId);
			Notice notice = noticeService.selectId(id);
			request.setAttribute("enotice", notice);
			request.setAttribute("dq", request.getParameter("dq"));
		}
		request.setAttribute("types", new TypeServiceImpl().selectAll());
		request.getRequestDispatcher("/WEB-INF/jsp/notice/notice_save_update.jsp").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("noticeIds");
		int[] ids = new int[strIds.length];
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		noticeService.del(ids);
		request.getRequestDispatcher("/noticeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void doAddOrEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Notice notice = new Notice();
		notice.setName(request.getParameter("name"));
		notice.setUserId(new Integer(request.getParameter("userId")));
		notice.setContent(request.getParameter("text"));
		notice.setType(new Type());
		notice.getType().setId(new Integer(request.getParameter("typeId")));
		String strId = request.getParameter("id");
		
		if(strId!=null&&strId!="") {
			notice.setId(new Integer(strId));
			notice.setModifyDate(new Date());
			if(noticeService.edit(notice)==0) {
				response.getWriter().write("修改失败！！");
				return;
			}
			request.getRequestDispatcher("/noticeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
		}else {
			if(noticeService.add(notice)==0) {
				response.getWriter().write("添加失败！！");
				return;
			}
			request.getRequestDispatcher("/noticeServlet?action=list").forward(request, response);
		}
	}
	
}
