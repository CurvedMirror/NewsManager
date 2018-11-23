<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	String userinsession = (String)session.getAttribute("loginuser");
	request.setCharacterEncoding("GBK");
	if(userinsession == null){
%>
<script>
	alert("您没有登录或者已经退出，请重新登录!");
	open("../index.jsp","_self");	
</script>
<%
	}
%>

