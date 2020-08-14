<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>人事管理系统 ——用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css" />
<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js"
	type="text/javascript"></script>
<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js"
	type="text/javascript"></script>
<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.js"
	type="text/javascript"></script>
<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">

	function toPage(pageIndex)
	{
		$("#pageIndex").attr("value",pageIndex);
	    $("#userform").attr("action", "${ctx}/userServlet?action=list&dq="+pageIndex);
		$("#userform").submit();
	}
	
	function move(){}
	
	function out(){}
	
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
		        $("#userform").attr("action", "${ctx}/userServlet?action=list&dq="+page_size);
		    	$("#userform").submit();
			}
	}
	
	$(function(){
		/**
		if("${page.dangQianPage}">"${page.totalPage}"){
	        $("#userform").attr("action", "${ctx}/userServlet?action=list&dq=${page.totalPage}");
	    	$("#userform").submit();
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
 			   alert("请选择一个需要删除的用户！");
 		   }else{
 			   $("#userform").attr("action", "${ctx}/userServlet?action=del&dq=${page.dangQianPage}");
 			   $("#userform").submit();
 		   }
 	   })
 	   
 	   $("#query").click(function(){
 		  	$("#userform").attr("action", "${ctx}/userServlet?action=list");
 		 	$("#userform").submit();
 	   })
 	   
 	   
    })
	</script>
</head>
<body>
	<!-- 导航 -->
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td width="15" height="32"><img
				src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
			<td class="main_locbg font2"><img
				src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：用户管理
				&gt; 用户查询</td>
			<td width="15" height="32"><img
				src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
		</tr>
	</table>
	<form name="userform" method="post" id="userform"
		action="${ctx}/queryUser.action">
		
		<!-- 配置pageIndex的隐藏域 -->
		<input type="hidden" name="pageIndex" value="${page.dangQianPage}" id="pageIndex">
		<table width="100%" height="90%" border="0" cellpadding="5"
			cellspacing="0" class="main_tabbor">
			<!-- 查询区  -->
			<tr valign="top">
				<td height="30">
					<table width="100%" border="0" cellpadding="0" cellspacing="10"
						class="main_tab">
						<tr>
							<td class="fftd">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td class="font3">登录名：<input type="text" name="l"
											value="${user.loginname}" />&nbsp;&nbsp;用户名：<input
											type="text" name="u" value="${user.username}" />
											用户状态：<input type="text" name="s" value="${user.status==-999?"":user.status}"><input
											type="button" value="查询" id="query" /><input type="button"
											value="删除" id="delete" />
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
					<table width="100%" border="1" cellpadding="5" cellspacing="0"
						style="border: #c2c6cc 1px solid; border-collapse: collapse;">
						<tr class="main_trbg_tit" align="center">
							<td><input type="checkbox" name="checkAll" id="checkAll"></td>
							<td>登录名</td>
							<td>密码</td>
							<td>用户名</td>
							<td>状态</td>
							<td>创建时间</td>
							<td align="center">操作</td>
						</tr>
						<c:forEach items="${page.lists}" var="user"
							varStatus="stat">
							<tr id="data_${stat.index}" align="center" class="main_trbg"
								onMouseOver="move(this);" onMouseOut="out(this);">
								<td><input type="checkbox" id="box_${stat.index}"
									value="${user.id}" name="userIds" /></td>
								<td>${user.loginname }</td>
								<td>${user.password }</td>
								<td>${user.username }</td>
								<td>${user.status }</td>
								<td><f:formatDate value="${user.createdate}" type="date"
										dateStyle="long" /></td>
								<td align="center" width="40px;"><a
									href="${ctx}/userServlet?action=toEdit&id=${user.id}&dq=${page.dangQianPage}"> <img
										title="修改" src="${ctx}/images/update.gif" /></a></td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<!-- 分页标签 -->
			<tr valign="top">
				<td align="center" class="font3">
				<%@include file="/page/page.jsp"%>
				</td>
			</tr>
		</table>

	</form>
</body>
</html>