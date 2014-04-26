<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	打印出所有的cookie.<br/>
	<a href="carts.jsp">下一页面</a>
	<%
	
		Cookie[] c =request.getCookies();
		boolean isCoookieExist = false;
		if(c != null){			
			for(Cookie cc : c){
				out.println("<br/>");
				out.println(cc.getName() + "=" + cc.getValue());
	
				out.println("<br/>");
				if(cc.getName().equals("lijia")){
					isCoookieExist = true;
				}
			}
		}
		
/**		if(! isCoookieExist){
			Cookie c1 = new Cookie("lijia","slieer");
			response.addCookie(c1);
		}
	*/	
		
	%>

</body>
</html>