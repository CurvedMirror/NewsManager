<%@page import="org.jbit.news.util.DatabaseUtil.DataBaseUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.jbit.news.entity.*"  %>
<%@ page import="org.jbit.news.dao.impl.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'control.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
		<!--判断登陆 -->
		<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("uname");
		String pwd = request.getParameter("upwd");
		User user = new UserDaoImpl(DataBaseUtil.getConnection()).login(name, pwd);
		if (user!=null) {
			session.setAttribute("loginuser", name);
			response.sendRedirect("newspages/admin.jsp");
		}else{
			request.setAttribute("mess", "用户名密码不正确");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		%>
		
  </body>
</html>
