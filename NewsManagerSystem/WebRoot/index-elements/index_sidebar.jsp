<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.jbit.news.entity.*"%>


<div class="sidebar">
<%
	List<News> list1=(List<News>)request.getAttribute("list1");
	List<News> list2=(List<News>)request.getAttribute("list2");
	List<News> list3=(List<News>)request.getAttribute("list3");
	
	if(list1==null&&list2==null&&list3==null){
		request.getRequestDispatcher("/news?opr=topicLatest").forward(request, response);
				return;
	}
 %>
<h1> <img src="images/title_1.gif" alt="国内新闻" /> </h1>
    <div class="side_list">
      <ul>
      <%
      	for(News news:list1){
      %>	<li> <a href='#'><b><%=news.getNtitle() %> </b></a> </li>
      <%} %>
      </ul>
    </div>
    <h1> <img src="images/title_2.gif" alt="国际新闻" /> </h1>
     <div class="side_list">
      <ul>
      <%
      	for(News news:list2){
      %>	<li> <a href='#'><b><%=news.getNtitle() %> </b></a> </li>
      <%} %>
      </ul>
    </div>
    <h1> <img src="images/title_3.gif" alt="娱乐新闻" /> </h1>
     <div class="side_list">
      <ul>
      <%
      	for(News news:list3){
      %>	<li> <a href='#'><b><%=news.getNtitle() %> </b></a> </li>
      <%} %>
      </ul>
    </div>
</div>