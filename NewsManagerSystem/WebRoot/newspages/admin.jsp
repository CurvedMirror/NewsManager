<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.jbit.news.dao.TopicsDao"%>
<%@ page import="org.jbit.news.entity.*"%>
<%@ include file="../elements/head.jsp"  %>
<%
	
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	request.setAttribute("ctx", path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
   <script type="text/javascript" src="${ctx }/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${ctx }/js/admin.js"></script>
	
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
 <base href="<%=basePath%>"/>
<title>添加主题--管理后台</title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript">
function isDelete(nid){
		if(confirm("此新闻的相关评论你也将删除，确定删除吗？")){
			location.href="news?opr=delNews&nid="+nid;
		}
	}
</script>
	<div id="main">
	   <div>
	     <%@ include file="console_element/top.jsp" %>
	  </div> 
	  <div id="opt_list"> 	
	 	<%@ include file="console_element/left.jsp" %>
	  </div>
	  
		<div id="msg"
		style="display:none;position:absolute;z-index:5;background-color:pink;
		font-size:16px;padding:5px 20px;"> </div>
		
	  <div id="opt_area"> 
	  
	      <p align="right"> 当前页数:[1/3]&nbsp; <a href="#">下一页</a> <a href="#">末页</a> </p>
	  </div>	  
	 <%@include file="console_element/bottom.html" %>
    </div>
</body>
</html>

	