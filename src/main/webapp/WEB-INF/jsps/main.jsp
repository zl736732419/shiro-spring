<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
	<h1 style="color:green">登录成功</h1>
	<shiro:guest>
		hello guest, more to <a href="${pageContext.request.contextPath }/login">login</a>
	</shiro:guest>
	<shiro:user>
		用户<shiro:principal/>欢迎登录!
	</shiro:user>
	
	<shiro:authenticated>
		用户<shiro:principal/> 已通过身份认证!
	</shiro:authenticated>
	
	<shiro:notAuthenticated>
		用户<shiro:principal/>还没有经过认证!
	</shiro:notAuthenticated>
	
	<shiro:hasRole name="admin">
		用户<shiro:principal/> 拥有admin访问权限!
	</shiro:hasRole>
	
	
	
	<p>欢迎 <span>${subject.principal}!</span><a href="${pageContext.request.contextPath }/logout">退出</a></p>

</body>
</html>