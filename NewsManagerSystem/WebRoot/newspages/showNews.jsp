<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <ul class="classlist">
<c:forEach items="${requestScope.list }" var="news" varStatus="i">
	<li>${ news.ntitle }<span>作者：${news.nauthor
			}&nbsp;&nbsp;&nbsp;&nbsp; <a
			href='news?opr=editNewsShow&nid=${news.nid }'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='javascript:void(0)' onclick='isDelete(${news.nid})'>删除</a> </span>
		<c:if test="${i.count%5==0 }">
			<li class="space"></li>
		</c:if></li>
</c:forEach>
</ul>