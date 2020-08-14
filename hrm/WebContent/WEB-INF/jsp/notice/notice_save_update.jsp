<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>人事管理系统——添加公告</title>
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
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/ueditor.all.min.js">
	
</script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="${ctx}/js/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td width="15" height="32"><img
				src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
			<td class="main_locbg font2"><img
				src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：公告类型管理
				&gt; 添加公告类型</td>
			<td width="15" height="32"><img
				src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
		</tr>
	</table>
	<form action="${ctx}/noticeServlet?action=doAddOrEdit&dq=${dq}" id="noticeForm"
		method="post">
		<input type="hidden" name="id" id="id" value="${enotice.id }" />
		<input type="hidden" name="userId" value="${sessionScope.user_session.id}" />
		<input type="hidden" id="text" name="text" value="${enotice.content}" />
						<div>
							<script id="editor" type="text/plain"
								style="width:1024px;height:150px;"></script>
						</div>
		<table width="100%" border="0" cellpadding="5" cellspacing="0"
			class="main_tabbor">
			<tr>
				<td>${message}</td>
			</tr>
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="10"
						class="main_tab">
						<tr>
							<td class="font3 fftd">
								<table>
									<tr>
										<td class="font3 fftd">名称：<input type="text" name="name"
											id="name" value="${enotice.name }" size="20" /></td>
									</tr>
									<tr>
										<td class="font3 fftd">类型： <select name="typeId">
												<option value="0" >请选择类型</option>
												<c:forEach items="${types }" var="type">
													<option value="${type.id }"
														<c:if test="${type.id == enotice.type.id }">selected=selected</c:if>>${type.name }</option>
												</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="fftd"><input type="submit"
								name="submit" value="保存"> <input type="reset"
								value="取消 "></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>


	</form>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		$(function() {
			var ue = UE.getEditor('editor');
			//回显数据
			if (document.getElementById("id").value != null) {

				var ue = UE.getEditor('editor');

				var htmlStr = $("#text").val();

				ue.ready(function() {

					ue.setContent(htmlStr, false);

				});

			}

			$("#noticeForm").submit(
					function() {
						if (!UE.getEditor('editor').hasContents()) {
							alert('请先填写内容!');
						} else {
							var content = UE.getEditor('editor').getContent();
							content = content
									.replace(new RegExp("<", "g"), "<")
									.replace(new RegExp(">", "g"), ">")
									.replace(new RegExp("\"", "g"), "");
							document.getElementById("text").value = content;
							$("#noticeForm").submit();
						}
					})
		})
	</script>
</body>
</html>