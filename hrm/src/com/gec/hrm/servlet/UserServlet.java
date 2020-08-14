package com.gec.hrm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.PageBean;
import com.gec.hrm.entity.User;
import com.gec.hrm.service.IUserService;
import com.gec.hrm.service.impl.UserServiceImpl;

@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IUserService userService = new UserServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ln = request.getParameter("l");
		String un = request.getParameter("u");
		String s = request.getParameter("s");
		int status;
		if(s==null||s=="")
			status = -999;
		else
			status = new Integer(s.trim());
		User user = new User();
		user.setLoginname(ln);
		user.setUsername(un);
		user.setStatus(status);
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq=="")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<User> page = userService.getOnePage(dangQianPage, 10, user);
		if(page.getTotalPage()<page.getDangQianPage())
			page = userService.getOnePage(page.getTotalPage(), 10, user);
		request.setAttribute("page", page);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);
	}
	
	public void toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
	}
	
	public void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ln = request.getParameter("loginname");
		if(userService.selectByLoginName(ln)!=null) {
			response.getWriter().write("该登录名已被注册！！");
			return;
		}
		String un = request.getParameter("username");
		String password = request.getParameter("password");
		String s = request.getParameter("status");
		int status;
		if(s==null||s=="")
			status = -999;
		else
			status = new Integer(s);
		User user = new User();
		user.setLoginname(ln);
		user.setUsername(un);
		user.setStatus(status);
		user.setCreatedate(new Date());
		user.setPassword(password);
		if(userService.add(user)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/userServlet?action=list").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("userIds");
		int[] ids = new int[strIds.length];
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		userService.del(ids);
		request.getRequestDispatcher("/userServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void toEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		User user = userService.selectId(id);
		request.setAttribute("euser", user);
		request.setAttribute("dq", request.getParameter("dq"));
		request.getRequestDispatcher("/WEB-INF/jsp/user/useredit.jsp").forward(request, response);
	}
	
	public void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ln = request.getParameter("loginname");
		String isMe = request.getParameter("isMe");
		if(!isMe.equals(ln)) {
			if(userService.selectByLoginName(ln)!=null) {
				response.getWriter().write("该登录名已被注册！！");
				return;
			}
		}
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		String un = request.getParameter("username");
		String password = request.getParameter("password");
		String s = request.getParameter("status");
		int status;
		if(s==null||s=="")
			status = -999;
		else
			status = new Integer(s);
		User user = new User();
		user.setId(id);
		user.setLoginname(ln);
		user.setUsername(un);
		user.setStatus(status);
		user.setPassword(password);
		if(userService.edit(user)==0) {
			response.getWriter().write("修改失败！！");
			return;
		}
		request.getRequestDispatcher("/userServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void getLoginname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cln = request.getParameter("cln");
		String isMe = request.getParameter("isMe");
		if(isMe!=null&&isMe!=""&&isMe!="null"&&isMe.equals(cln)) {
			out.print(0);
			return ;
		}
		User user = userService.selectByLoginName(cln);
		if (user != null)
			out.print(1);
		else
			out.print(0);
		out.close();
	}
}
