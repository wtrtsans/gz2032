package com.gec.hrm.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//得到客户端传入的action
		String action = request.getParameter("action");
		if (null==action||"".equals(action)) {
			response.getWriter().write("您提交的是非法数据！！");
			return;
		}
		//根据输入的动作得到本对象的相应的方法对象：反射机制：反射可以做未来不确定是事情。你知道我下一刻添加什么方法吗？
		try {
			//action能是字符串
			Method method = this.getClass().getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);//调用动作对应的方法
		} catch (Exception e) {
			response.getWriter().write("您提交的是非法数据！！");
			System.out.println(action);
			e.printStackTrace();
		}
	}
}
