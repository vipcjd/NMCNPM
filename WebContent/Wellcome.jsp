<%@page import="model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
<style>
.a {
	font-size: 40px;
	color: blue;
	margin-top: 50px;
	margin-left: 50px;
}
</style>
</head>
<body>

	<%
		Users users = (Users) session.getAttribute("user");
		if (users != null) {
	%>
	<div class="a">
		Đăng nhập thành công, xin chào
		<div class="s" style="color: blue;"><%=users.getUsername()%></div>
	</div>
	<a href="LogoutController" style="color: red;"><i
		class="fas fa-sign-out-alt"></i> Đăng Xuất</a>
	<%
		}
	%>
	Xin chào:
	<%
		String id = request.getAttribute("id").toString();
		String name = request.getAttribute("name").toString();
		out.print("Id: " + id);
		out.print("<br/>Name: " + name);
	%>


</body>
</html>