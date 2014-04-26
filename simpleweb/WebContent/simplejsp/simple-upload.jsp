<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
//String fileName = "file";//request.getParameter("fileName");
	InputStream in = request.getInputStream();
	if(in != null){
		File f = new File("./upload");
		out.print(f.getCanonicalFile());
		System.out.print(f.getCanonicalFile());
		if(! f.exists()){
			f.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(f);
		int data = -1;
		while((data = in.read()) != -1){
			fout.write(data);
		}
		fout.flush();
		fout.close();
		//out.print("file path:" + f.getCanonicalPath());
	}
	out.print("no file");
%>
<form action="simple-upload.jsp" method="post" enctype="multipart/form-data">
	<input type="hidden" name="fileName" value="fileName">
	<input type="file" name="file" value="file">
	<input type="submit" name="submit">
</form>
</body>
</html>