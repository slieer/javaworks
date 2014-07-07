<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		out.println("host:" +  request.getRemoteHost());
	    out.println("ip" +  request.getRemoteAddr());
	%>
	
	<form action="test-remote-address.jsp" method="post">
		<input type="text" name="name" value="2">
		<input type="password" name="pw" value="aa">
		<input type="submit" name="sub" value="sub"> 
	</form>
	
	<%
		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		if(name != null && pw != null){
			out.print("name:" + name + "pw:" + pw);
		}
	%>
	<%!
		public void get(){
			System.out.println("abc");
	    }
	%>
</body>
</html>