<%@page import="org.jbit.news.service.impl.TopicsServiceImpl"%>
<%@page import="org.jbit.news.service.TopicsService"%>
<%@page import="org.jbit.news.entity.Comments"%>
<%@page import="org.jbit.news.entity.News"%>
<%@page import="org.jbit.news.dao.TopicsDao"%>
<%@page import="org.jbit.news.dao.impl.TopicsDaoImpl"%>
<%@page import="org.jbit.news.entity.Topics"%>
<%@ include file="../elements/head.jsp"  %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<title>新闻中国</title>
   
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
	    <h1 id="opt_type"> 修改新闻： </h1>
		    <form action="<%=basePath %>/news?opr=editNews&nid=<%=request.getParameter("nid")%>"  enctype="multipart/form-data" method="post">
		      <p>
		        <label> 主题 </label>
		        
		        <%
		       TopicsService topicsDaoImpl=new TopicsServiceImpl(); 
		        List<Topics> topics=topicsDaoImpl.getAllTopics("");//获得主题
		        News news=(News)session.getAttribute("news");//当前的新闻
		        
		        %>
		        <select name="ntid">
		        	<%
		        	for(Topics topics2:topics){
		        		//显示对应的主题
		        		if(news.getNtid()==topics2.getTid()){
		        			
		        		
		        	%>	
		        		<option value='<%=topics2.getTid() %>' selected="selected"><%=topics2.getTname() %></option>
		        	<% 
		        		}else{
		        	%>
		        			<option value='<%=topics2.getTid() %>'><%=topics2.getTname() %></option>
		        	<%
		        		}
		        	}
		        	 %>
		        </select>
		        <input type="hidden" name="nid" value="" />
		      </p>
		      <p>
		        <label> 标题 </label>
		        <input name="ntitle" type="text" class="opt_input" value="<%=news.getNtitle()%>"/>
		      </p>
		      <p>
		        <label> 作者 </label>
		        <input name="nauthor" type="text" class="opt_input" value="<%=news.getNauthor()%>"/>
		      </p>
		      <p>
		        <label> 摘要 </label>
		        <textarea name="nsummary" cols="40" rows="3"><%=news.getNsummary() %></textarea>
		      </p>
		      <p>
		        <label> 内容 </label>
		        <textarea name="ncontent" cols="70" rows="10"><%=news.getNcontent() %> </textarea>
		      </p>
		      <p>
		        <label> 上传图片 </label>
		        <input name="file" type="file" class="opt_input"/>
		      </p>
		      <input type="submit" value="提交" class="opt_sub" />
		      <input type="reset" value="重置" class="opt_sub" />
		    </form>
		    <p></p>
		    <h1 id="opt_type">
				修改新闻评论：
			</h1>
		      <table width="80%" align="left">
		         <tr>
		           <td colspan="6"><hr />
		           </td>
		         </tr>
		   			
			      	<c:forEach var="comments" items="${ comments}">
			      			<tr>
							<td>留言人：</td>
							<td>${comments.cauthor }</td>
							<td>IP：</td>
							<td>${comments.cip }</td>
							<td>留言时间：</td>
							<td>${comments.cdate }</td>
							<td><a href="comments_control.jsp?opr=delComm&cid=${comments.cid }&nid=${comments.cnid}">删除</a>
							</td>
							<tr>
							 <tr><td colspan="6">${comments.ccontent }</td><tr/>
			      	</c:forEach>
			   
			        <tr>
			          <td colspan="6"><hr />
			          </td>
			        </tr>
		      </table>
		  </div>
	  </div>
	 <%@include file="console_element/bottom.html" %>
  </div>
</body>
</html>	