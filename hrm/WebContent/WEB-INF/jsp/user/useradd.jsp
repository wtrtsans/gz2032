<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>人事管理系统——添加用户</title>
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
	
	$(function(){


    	var loginnameFlag = 0;
    	
    	$("[name=loginname]").blur(function(){
			var loginname = $("#loginname").val();
			if ($.trim(loginname) == ""){
				return;
			}
			$.get("${ctx}/userServlet?action=getLoginname",{"cln":loginname},function(data){
				if(data==1)
				   {
						loginnameFlag = 1;
						$("#loginnameFlag").html("<span style='color:red;'>该登录名已经存在，请重新输入！</span>");
				   }else if(data==0)
				   {
					   loginnameFlag = 0;
					   $("#loginnameFlag").html("<span style='color:green;'>该登录名可使用！</span>");
				   }
			})
		})
		
    	/** 员工表单提交 */
		$("#userForm").submit(function(){
			var username = $("#username");
			var status = $("#status");
			var loginname = $("#loginname");
			var password = $("#password");
			var msg = "";
			if ($.trim(username.val()) == ""){
				msg = "姓名不能为空！";
				username.focus();
			}else if ($.trim(status.val()) == ""){
				msg = "状态不能为空！";
				status.focus();
			}else if ($.trim(loginname.val()) == ""){
				msg = "登录名不能为空！";
				loginname.focus();
			}else if ($.trim(password.val()) == ""){
				msg = "密码不能为空！";
				password.focus();
			}else if(!/^\w{2,20}$/.test(loginname.val())){
			    msg = "登录名长度必须是2~20之间";
			}else if (loginnameFlag == 1){
				msg = "登录名不能重复！";
				loginname.focus();
			}else if(!/^\w{6,20}$/.test(password.val())){
				msg = "密码长度必须是6~20之间";
			}
			if (msg != ""){
				$.ligerDialog.error(msg);
				return false;
			}else{
				return true;
			}
			$("#userForm").submit();
		});
    });
		

	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：用户管理  &gt; 添加用户</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>
<form action="${ctx}/userServlet?action=doAdd" id="userForm" method="post">
<table width="100%"  border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr><td>${message}</td></tr>
  <tr valign="top">
    <td>
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		<tr>
		    			<td class="font3 fftd">姓&nbsp;名：<input type="text" name="username" id="username" size="20"/></td>
		    			<td class="font3 fftd">状&nbsp;态：<input type="text" name="status" id="status" size="20"/></td>
		    		</tr>
		    			
		    		<tr>
		    			<td class="font3 fftd">密&nbsp;码：<input name="password" id="password" size="20" /></td>
		    			<td class="font3 fftd">登录名：<input name="loginname" id="loginname" size="20" /><span id="loginnameFlag" ></span></td>
		    		</tr>
		    		
		    	</table>
		    </td></tr>
			<tr><td align="left" class="fftd"><input type="submit" value="添加">&nbsp;&nbsp;<input type="reset" value="取消 "></td></tr>
		  </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>