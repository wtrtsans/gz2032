package com.gec.hrm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gec.hrm.entity.Dept;
import com.gec.hrm.entity.Employee;
import com.gec.hrm.entity.Job;
import com.gec.hrm.entity.PageBean;
import com.gec.hrm.service.IEmployeeService;
import com.gec.hrm.service.impl.DeptServiceImpl;
import com.gec.hrm.service.impl.EmployeeServiceImpl;
import com.gec.hrm.service.impl.JobServiceImpl;

@WebServlet("/employeeServlet")
public class EmployeeServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IEmployeeService employeeService = new EmployeeServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee employee = new Employee(); 
		employee.setDept(new Dept());
		employee.setJobId(new Job());

		employee.setName(request.getParameter("n"));
		employee.setPhone(request.getParameter("p"));
		employee.setCardId(request.getParameter("c"));

		String sexStr = request.getParameter("s");
		String deptStr = request.getParameter("d");
		String jobStr = request.getParameter("j");
		
		if(sexStr==null||sexStr=="")
			employee.setSex(3);
		else
			employee.setSex(new Integer(sexStr));	
		
		if(deptStr==null||deptStr=="")
			employee.getDept().setId(0);
		else
			employee.getDept().setId(new Integer(deptStr));
		
		if(jobStr==null||jobStr=="")
			employee.getJob().setId(0);
		else
			employee.getJob().setId(new Integer(jobStr));
		
		String dq = request.getParameter("dq");
		int dangQianPage;
		if(dq==null||dq=="")
			dangQianPage = 1;
		else
			dangQianPage = new Integer(dq);
		PageBean<Employee> page = employeeService.getOnePage(dangQianPage, 10, employee);
		if(page.getTotalPage()<page.getDangQianPage())
			page = employeeService.getOnePage(page.getTotalPage(), 10, employee);

		request.setAttribute("employee", employee);
		request.setAttribute("page", page);
		request.setAttribute("jobList", new JobServiceImpl().selectAll());
		request.setAttribute("deptList", new DeptServiceImpl().selectAll());
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
	}
	
	public void toAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("jobList", new JobServiceImpl().selectAll());
		request.setAttribute("deptList", new DeptServiceImpl().selectAll());
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
	}
	
	public void doAdd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee employee = new Employee(); 
		employee.setCardId(request.getParameter("cardId"));
		employee.setDept(new Dept());
		employee.setJobId(new Job());
		employee.setName(request.getParameter("name"));
		employee.setSex(new Integer(request.getParameter("sex")));
		employee.getJob().setId(new Integer(request.getParameter("job_id")));
		employee.setEducation(request.getParameter("education"));
		employee.setEmail(request.getParameter("email"));
		employee.setPhone(request.getParameter("phone"));
		employee.setTel(request.getParameter("tel"));
		employee.setParty(request.getParameter("party"));
		employee.setQqNum(request.getParameter("qqNum"));
		employee.setAddress(request.getParameter("address"));
		employee.setPostCode(request.getParameter("postCode"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = df.parse(request.getParameter("birthday"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		employee.setBirthday(new Date(birthday.getTime()));
		employee.setRace(request.getParameter("race"));
		employee.setSpeciality(request.getParameter("speciality"));
		employee.setHobby(request.getParameter("hobby"));
		employee.setRemark(request.getParameter("remark"));
		employee.getDept().setId(new Integer(request.getParameter("dept_id")));
		
		if(employeeService.add(employee)==0) {
			response.getWriter().write("添加失败！！");
			return;
		}
		request.getRequestDispatcher("/employeeServlet?action=list").forward(request, response);
	}
	
	public void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strIds[] = request.getParameterValues("empIds");
		int[] ids = new int[strIds.length];
		for(int i = 0; i < strIds.length; i++) {
			ids[i] = new Integer(strIds[i]);
		}
		employeeService.del(ids);
		request.getRequestDispatcher("/employeeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void toEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strId = request.getParameter("id");
		int id;
		id = new Integer(strId);
		Employee employee = employeeService.selectId(id);
		request.setAttribute("eemployee", employee);
		request.setAttribute("dq", request.getParameter("dq"));
		request.setAttribute("jobList", new JobServiceImpl().selectAll());
		request.setAttribute("deptList", new DeptServiceImpl().selectAll());
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeedit.jsp").forward(request, response);
	}
	
	public void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Employee employee = new Employee(); 
		employee.setCardId(request.getParameter("cardId"));
		
		if(!request.getParameter("cardId").equals(request.getParameter("isMe"))) {
			if(employeeService.selectByCardId(employee.getCardId())!=null) {
				response.getWriter().write("该身份证号码已存在！！");
				return;
			}
		}
		
		employee.setId(new Integer(request.getParameter("id")));
		employee.setDept(new Dept());
		employee.setJobId(new Job());
		employee.setName(request.getParameter("name"));
		employee.setSex(new Integer(request.getParameter("sex")));
		employee.getJob().setId(new Integer(request.getParameter("job_id")));
		employee.setEducation(request.getParameter("education"));
		employee.setEmail(request.getParameter("email"));
		employee.setPhone(request.getParameter("phone"));
		employee.setTel(request.getParameter("tel"));
		employee.setParty(request.getParameter("party"));
		employee.setQqNum(request.getParameter("qqNum"));
		employee.setAddress(request.getParameter("address"));
		employee.setPostCode(request.getParameter("postCode"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = df.parse(request.getParameter("birthday"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		employee.setBirthday(new Date(birthday.getTime()));
		employee.setRace(request.getParameter("race"));
		employee.setSpeciality(request.getParameter("speciality"));
		employee.setHobby(request.getParameter("hobby"));
		employee.setRemark(request.getParameter("remark"));
		employee.getDept().setId(new Integer(request.getParameter("dept_id")));
		
		if(employeeService.edit(employee)==0) {
			response.getWriter().write("修改失败！！");
			return;
		}
		request.getRequestDispatcher("/employeeServlet?action=list&dq="+request.getParameter("dq")).forward(request, response);
	}
	
	public void getcardid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cardId = request.getParameter("cardId");
		String isMe = request.getParameter("isMe");
		if(isMe!=null&&isMe!=""&&isMe!="null"&&isMe.equals(cardId)) {
			out.print(0);
			return ;
		}
		Employee employee = employeeService.selectByCardId(cardId);
		if (employee != null)
			out.print(1);
		else
			out.print(0);
		out.close();
	}
	
}
