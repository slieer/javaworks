<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a>test seesion!</a>
	<div>
	<br></br>
		这个页面什么都没做，被请求后出现,通过firefox可看到:<br/>
		Set-Cookie	JSESSIONID=625E46CDD7AC1105539C5579B1F7F304; Path=/simpel.web.test
	</div>
	<dir>说明当请求jsp时，server 会给client设置cookie.</dir>
	<a href="carts.jsp">carts.jsp这个页面有java httpSession操作.</a>
	<%
		String path = request.getRealPath("");
		String p = request.getContextPath();
		out.print(path);
		out.print(p);
	%>
</body>
</html>