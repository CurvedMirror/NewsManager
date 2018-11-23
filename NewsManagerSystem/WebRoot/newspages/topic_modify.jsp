<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="../elements/head.jsp"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
<title>新闻中国</title>
   <script type="text/javascript">
		function check(){
			var tname = document.getElementById("tname");
	
			if(tname.value == ""){
				alert("请输入主题名称！！");
				tname.focus();
				return false;
			}		
			return true;
		}
	</script>
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
	    <h1 id="opt_type"> 修改主题： </h1>
	    <form action="<%=basePath%>/topic?opr=edit" method="post" onsubmit="return check()" >
	      <p>
	      
	        <label> 主题名称 </label>
	        
	        <input name="tname" id="tname" type="text" class="opt_input" value="<%=request.getParameter("tname") %>" />
	        <input name="tid" id="tid" type="hidden" value="<%=request.getParameter("tid") %>"/>
	      </p>
	      <input name="action" type="hidden" value="addtopic">
	      <input type="button" value="提交" class="opt_sub" id="updateTopicSubmit" />
	      <input type="reset" value="重置" class="opt_sub" />
	    </form>
	  </div>
	 	<%@include file="console_element/bottom.html" %>
  </div>
</body>
</html>	