<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>人事管理系统 ——员工管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
	<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
	<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
	<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript">
	function toPage(pageIndex)
	{
		$("#pageIndex").attr("value",pageIndex);
	    $("#empform").attr("action", "${ctx}/employeeServlet?action=list&dq="+pageIndex);
		$("#empform").submit();
	}
	
	function pagerJump()
	{
		var page_size=$('#pager_jump_page_size').val();
			
 		var regu = /^[1-9]\d*$/;
 			
 		if (!regu.test(page_size)||page_size < 1 || page_size >'${page.totalPage}')
		{
			alert('请输入[1-'+ '${page.totalPage}' +']之间的页码！');	
		}else
 		{
	 		$("#pageIndex").attr("value",page_size);
 	        $("#empform").attr("action", "${ctx}/employeeServlet?action=list&dq="+page_size);
 	    	$("#empform").submit();
 		}
	}
	
	$(function(){
		/**
		if("${page.dangQianPage}">"${page.totalPage}"){
	        $("#empform").attr("action", "${ctx}/employeeServlet?action=list&dq=${page.totalPage}");
	    	$("#empform").submit();
		}
		*/
 	   /** 获取上一次选中的部门数据 */
 	   var boxs  = $("input[type='checkbox'][id^='box_']");
 	   
 	   /** 给全选按钮绑定点击事件  */
	   $("#checkAll").click(function(){
	       // this是checkAll  this.checked是true
	       // 所有数据行的选中状态与全选的状态一致
	       boxs.attr("checked",this.checked);
	   })
	   
	    /** 给数据行绑定鼠标覆盖以及鼠标移开事件  */
	    $("tr[id^='data_']").hover(function(){
	    	$(this).css("backgroundColor","#eeccff");
	    },function(){
	    	$(this).css("backgroundColor","#ffffff");
	    })

	    	
 	   /** 删除员工绑定点击事件 */
 	   $("#delete").click(function(){
 		   /** 获取到用户选中的复选框  */
 		   var checkedBoxs = boxs.filter(":checked");
 		   if(checkedBoxs.length < 1){
 			   alert("请选择一个需要删除的员工！");
 		   }else{
 			    $("#empform").attr("action", "${ctx}/employeeServlet?action=del&dq=${page.dangQianPage}");
 		 		$("#empform").submit();
 		   }
 	   })
 	   
 	   $("#query").click(function(){
 		  	$("#empform").attr("action", "${ctx}/employeeServlet?action=list");
 		 	$("#empform").submit();
 	   })
 	   
    })
	</script>
</head>
<body>
	<!-- 导航 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	  <tr><td height="10"></td></tr>
	  <tr>
	    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
		<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：员工管理 &gt; 员工查询</td>
		<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
	  </tr>
	</table>
	<form name="empform" method="post" id="empform" action="${ctx}/employeeServlet?action=list">
		<input type="hidden" name="pageIndex" value="${page.dangQianPage}"
			id="pageIndex" />
	<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
	  <!-- 查询区  -->
	  <tr valign="top">
	    <td height="30">
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr>
			  <td class="fftd">
				    <table width="100%" border="0" cellpadding="0" cellspacing="0">
					  <tr>
					    <td class="font3">
					    	职位：
							    <select name="j" style="width:143px;">
					    			<option value="0">--请选择职位--</option>
					    			<c:forEach items="${jobList}" var="job">
					    				<c:choose>
					    				<c:when test="${job.id==employee.job.id}">
					    					<option value="${job.id}" selected="selected">${job.name}</option>
					    				</c:when>
					    				<c:otherwise>
					    					<option value="${job.id}">${job.name}</option>
					    				</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
					    		</select>
					    	姓名：<input type="text" name="n" value="${employee.name}">
					    	身份证号码：<input type="text" name="c" maxlength="18" value="${employee.cardId}">
					    </td>
					  </tr>
					  <tr>
					    <td class="font3">
					    	性别：
					    		<select name="s" style="width:143px;">
					    			<option value="">--请选择性别--</option>
					    			<option value="1" <c:if test="${employee.sex eq 1}">selected="selected"</c:if> >男</option>
					    			<option value="2" <c:if test="${employee.sex eq 2}">selected="selected"</c:if> >女</option>
					    		</select>
					    	手机：<input type="text" name="p" value="${employee.phone}">
					    	所属部门：<select  name="d" style="width:100px;">
								   <option value="0">--部门选择--</option>
								   <c:forEach items="${deptList }" var="dept">
								   		<c:choose>
								   		<c:when test="${dept.id==employee.dept.id}">
					    					<option value="${dept.id}" selected="selected">${dept.name}</option>
					    				</c:when>
					    				<c:otherwise>
					    					<option value="${dept.id }">${dept.name }</option>
					    				</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
							</select>&nbsp;
					    	<input type="submit" value="搜索"/>
					    	<input id="delete" type="button" value="删除"/>
					    </td>
					  </tr>
					</table>
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	  
	  <!-- 数据展示区 -->
	  <tr valign="top">
	    <td height="20">
		  <table width="100%" border="1" cellpadding="5" cellspacing="0" style="border:#c2c6cc 1px solid; border-collapse:collapse;">
		    <tr class="main_trbg_tit" align="center">
			  <td><input type="checkbox" name="checkAll" id="checkAll"></td>
			  <td>姓名</td>
			  <td>性别</td>
			  <td>手机号码</td>
			  <td>邮箱</td>
			  <td>职位</td>
			  <td>学历</td>
			  <td>身份证号码</td>
			  <td>部门</td>
			  <td>联系地址</td>
			  <td>建档日期</td>
			  <td align="center">操作</td>
			</tr>
			<c:forEach items="${page.lists}" var="employee" varStatus="stat">
				<tr id="data_${stat.index}" class="main_trbg" align="center">
					<td><input type="checkbox" id="box_${stat.index}" 
						value="${employee.id}" name="empIds" /></td>
					 <td>${employee.name }</td>
					  <td>
					        <c:choose>
					        	<c:when test="${employee.sex == 1 }">男</c:when>
					        	<c:otherwise>女</c:otherwise>
					        </c:choose>
					  </td>
					  <td>${employee.phone }</td>
					  <td>${employee.email }</td>
					  <td>${employee.job.name }</td>
					  <td>${employee.education }</td>
					  <td>${employee.cardId }</td>
					  <td>${employee.dept.name }</td>
					  <td>${employee.address }</td>
					  <td>
					  	<f:formatDate value="${employee.createDate}" 
								type="date" dateStyle="long"/>
					  </td>
					  <td align="center" width="40px;"><a href="${ctx}/employeeServlet?action=toEdit&id=${employee.id}&dq=${page.dangQianPage}">
							<img title="修改" src="${ctx}/images/update.gif"/></a>
					  </td>
				</tr>
			</c:forEach>
		  </table>
		</td>
	  </tr>
	  <!-- 分页标签 -->
	  <tr valign="top"><td align="center" class="font3">
	  	<%@include file="/page/page.jsp"%>
	  </td></tr>
	</table>
	</form>
</body>
</html>