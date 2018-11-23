<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.jbit.news.entity.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	request.setAttribute("ctx", path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新闻中国</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
 <base href="<%=basePath%>"/>
</head>
<%
	//获取验证信息
	request.setCharacterEncoding("UTF-8");
	String message = (String) request.getAttribute("mess");
%>

<body>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/index.js"></script>

	<div id="header">
		<form name="form1" method="post" action="control.jsp">
			<div id="top_login">
				<label> 登录名 </label> <input type="text" name="uname"
					value="<%=request.getParameter("uname") == null ? "" : request
					.getParameter("uname")%>"
					class="login_input" /> <label> 密&#160;&#160;码 </label> <input
					type="password" name="upwd"
					value="<%=request.getParameter("upwd") == null ? "" : request
					.getParameter("upwd")%>"
					class="login_input" /> <input type="submit" class="login_sub"
					value="登录" /> <label id="error" style="color:red"><%=message != null && !message.equals("") ? message : ""%>
				</label> <img src="images/friend_logo.gif" alt="Google" id="friend_logo" />
			</div>
		</form>
		<div id="nav">
			<div id="logo">
				<img src="images/logo.jpg" alt="新闻中国" />
			</div>
			<div id="a_b01">
				<img src="images/a_b01.gif" alt="" />
			</div>
			<!--mainnav end-->
		</div>
	</div>
	<div id="container">

		<%@ include file="index-elements/index_sidebar.jsp"%>

		<div class="main">
			<div class="class_type">
				<img src="images/class_type.gif" alt="新闻中心" />
			</div>
			<div class="content">
				<ul class="class_date">
					<c:forEach var="topic" items="${requestScope.list }" varStatus="i" begin="1">
						
						<c:if test="${i.count%11==1 }"> <li id='class_mouth'> </c:if>
							<a href="javascript:;"id="${topic.tid }">
							<b>${topic.tname}</b> </a>
						<c:if test="${i.count%11==0 }"> </li> </c:if>
						<c:set var="n" value="${i.count }"></c:set>
					</c:forEach>	
					<c:if test="${n%11!=0 }"></li></c:if>
				</ul>
				<ul class="classlist">
					
				
				</ul>
			</div>
		</div>
	</div>
	<div id="footer">
		<iframe src="index-elements/index_bottom.html" scrolling="no"
			frameborder="0" width="947px" height="190px"></iframe>
	</div>
</body>
</html>
