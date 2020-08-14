package com.gec.hrm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.hrm.entity.User;
import com.gec.hrm.service.IUserService;
import com.gec.hrm.service.impl.UserServiceImpl;

@WebServlet("/loginServlet")
public class LoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IUserService userService = new UserServiceImpl();
	
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("loginname");
		String pwd = request.getParameter("password");
		User user = userService.login(name, pwd);
		if(user!=null){
			HttpSession session = request.getSession();
			session.setAttribute("user_session", user);
			//跳转到主界面
			request.getRequestDispatcher("/mainServlet?action=main").forward(request, response);
		}else{
			request.setAttribute("message", "用户名或密码错误");
			request.getRequestDispatcher("/loginServlet?action=loginForm").forward(request, response);
		}
	}
	
	public void loginForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("user_session", null);
		System.out.println("溜了溜了");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
}
