<%@page import="org.jbit.news.service.impl.TopicsServiceImpl"%>
<%@page import="org.jbit.news.service.TopicsService"%>
<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@page import="org.jbit.news.dao.TopicsDao"%>
<%@ page import="org.jbit.news.dao.impl.*"%>
<%@ page import="org.jbit.news.entity.*"%>
<%@ include file="../elements/head.jsp"  %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>"/>
   <title>添加新闻--管理后台</title>    
    <link href="css/admin.css" rel="stylesheet" type="text/css" />

  </head>
  
  <body>

<div id="main">
  <div>
	   <%@ include file="console_element/top.jsp" %>
  </div>
  <div id="opt_list">
   	<%@ include file="console_element/left.jsp" %>
    
  </div>
  <div id="opt_area">
    <h1 id="opt_type"> 添加新闻： </h1>
    <form action="<%=basePath %>/news?opr=addNews" enctype="multipart/form-data" method="post">
      <p>
        <label> 主题 </label>
        <select name="ntid">
        <%
        	TopicsService topicsDao=new TopicsServiceImpl();
        	List<Topics> topics= topicsDao.getAllTopics("");
        	for(Topics topics2:topics){
        %>
        		 <option value="<%=topics2.getTid()%>"><%=topics2.getTname() %></option>
        <%	
        	}
         %>
        </select>
      </p>
      <p>
        <label> 标题 </label>
        <input name="ntitle" type="text" class="opt_input" />
      </p>
      <p>
        <label> 作者 </label>
        <input name="nauthor" type="text" class="opt_input" />
      </p>
      <p>
        <label> 摘要 </label>
        <textarea name="nsummary" cols="40" rows="3"></textarea>
      </p>
      <p>
        <label> 内容 </label>
        <textarea name="ncontent" cols="70" rows="10"></textarea>
      </p>
      <p>
        <label> 上传图片 </label>
        <input name="file" type="file" class="opt_input" />
      </p>
      <input name="action" type="hidden" value="addnews"/>
      <input type="submit" value="提交" class="opt_sub" />
      <input type="reset" value="重置" class="opt_sub" />
    </form>
  </div>  
  <%@include file="console_element/bottom.html" %>
  </div>
</body>
</html>
  