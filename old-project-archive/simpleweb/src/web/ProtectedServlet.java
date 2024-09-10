package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProtectedServlet extends HttpServlet {
	private static final long serialVersionUID = -7233069774644617478L;

	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();
		String authType = req.getAuthType();
		out.println("You   are   authorized   to   view   this   page");
		out.println("You   were   authenticated   using:   " + authType + "   method   of   authentication");
		Principal princ = req.getUserPrincipal();
		out.println("The   user   is:   " + princ.getName());
	}
}
