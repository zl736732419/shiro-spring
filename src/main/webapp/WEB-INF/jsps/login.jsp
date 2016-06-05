<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- css link-->

<!-- css link end -->


<!--js script -->

<!--js script end-->
<title></title>
</head>
<body>
	<h1>用户登录</h1>
	<form action="${pageContext.request.contextPath}/login" method="post">
		<p>
			用户名：<input type="text" name="username">
		</p>
		<p>
			密码：<input type="text" name="password">
		</p>
		<p>
			<input type="submit" value="登录">
		</p>
	</form>
	<p>
		<span style="color:red">${error }</span>
	</p>
</body>
</html>