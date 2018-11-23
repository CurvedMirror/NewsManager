<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <ul class="classlist" id="topicsList">
<c:forEach items="${requestScope.list }" var="topics" varStatus="i">
	<li>${topics.tname }&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='javascript:;' class="tpsMdfLink" id='${topics.tid }:${topics.tname}'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href='javascript:;' class="tpsDelLink" id='${topics.tid }'>删除</a></li>
</c:forEach>
</ul>