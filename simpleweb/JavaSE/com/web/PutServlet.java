package com.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PutServlet extends HttpServlet{
	private static final long serialVersionUID = 8210964834240904718L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//BufferedReader reader = req.getReader();
		//String line = null;
		//while((line = reader.readLine()) != null){
		//	System.out.println(line);
		//}
		
		String name = req.getParameter("name");
		String sex = req.getParameter("sex");
		String address = req.getParameter("address");
		String company = req.getParameter("company");
		String job = req.getParameter("job");
		
		String info = name + "," + sex + "," + address + "," + company + "," + job;
		System.out.println("server:" + info);
		
		resp.getOutputStream().print(info);
		resp.getOutputStream().close();
	}
}
