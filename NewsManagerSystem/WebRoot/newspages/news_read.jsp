<%@page import="org.jbit.news.entity.Comments"%>
<%@page import="org.jbit.news.entity.News"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中国</title>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript">

	
	$(document).ready(function(){

		$("#submit").click(function(){
			var flag=check();
			if(flag){
				var array=$("#myform").serializeArray();
				var queryString=$.param(array);
				$.post("news?opr=addComment",queryString,function(data){
				if(data.result="success"){
						alert("success");
						var $tr=$("<tr><td>留言人:</td><td>cauthor</td>"
							+"<td>IP:</td><td>cip</td>"
							+"<td>留言时间:</td><td>cdate</td></tr>"
							+"<tr><td colspan=\"6\">ccontent</td></tr>"
							+"<tr><td colspan=\"12\"><hr/></td></tr>");
						$(array).each(function() {
							$tr.find("td:contains('"+this.name+"')").text(this.value);
						});
							$tr.find("td:contains('cdate')").text(data.date);
							$("#comments").prepend($tr);
						
					}else{
						alert("fail");
					}
					
				},"JSON");
			}
		});
	});


	function check(){
		var cauthor=document.getElementById("cauthor");
		var content=document.getElementById("ccontent");
		if(cauthor.value==""||content.value==""){
			alert("请填写完整信息!");
			return false;
		}
		return true;
	}
</script>
<link href="css/read.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<div id="container">

		<%
			News news = (News) session.getAttribute("myNews");
		%>
		<div class="main">
			<div class="class_type">
				<img src="images/class_type.gif" alt="新闻中心" />
			</div>
			<div class="content">
				<ul class="classlist">
					<table width="80%" align="center">
						<tr width="100%">
							<td colspan="2" align="center"><%=news.getNtitle()%></td>
						</tr>
						<tr>
							<td colspan="2"><hr /></td>
						</tr>
						<tr>
							<td align="center"><%=news.getNcreateDate()%></td>
							<td align="left"><%=news.getNauthor()%></td>
						</tr>
						<tr>
							<td colspan="2" align="center"></td>
						</tr>
						<tr>
							<td colspan="2"><%=news.getNcontent()%></td>
						</tr>
						<tr>
							<td colspan="2"><hr /></td>
						</tr>
					</table>
				</ul>
				<ul class="classlist">
					<table width="80%" align="center" id="comments">

						<c:choose>
							<c:when test="${empty commentsList }">
								<td colspan="6">暂无评论！</td>
							</c:when>
							<c:otherwise>
								<c:forEach var="comments" items="${ commentsList}">
									<tr>
										<td colspan="2">留言人：${comments.cauthor }</td>
										<td colspan="2">ip:${comments.cip }</td>
										<td colspan="2">留言时间：${comments.cdate }</td>
									</tr>
									<tr>
										<td colspan="12">${comments.ccontent }<hr /></td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>

					</table>
				</ul>
				<ul class="classlist">
					<form action="<%=basePath %>/news?opr=addComment" method="post" id="myform">
						<input type="hidden" value="<%=news.getNid()%>" name="nid"/>
						<table width="80%" align="center">
							<tr>
								<td>评 论</td>
							</tr>
							<tr>
								<td>用户名：</td>
								<td><input id="cauthor" name="cauthor" value="这家伙很懒什么也没留下" />
									IP： <input name="cip" value="127.0.0.1" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td colspan="2"><textarea onmouseover="tishi()"
										name="ccontent" id="ccontent" cols="70" rows="10"></textarea></td>
							</tr>
							<tr>
							<td><input name="submit" id="submit" value="发  表" type="button" /></td>
							</tr>
						</table>
					</form>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
