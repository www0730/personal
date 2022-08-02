<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>教师工作量核算统计系统</title>
	<link href="css/login.css" rel="stylesheet" type="text/css"></link>//引入css样式
	<script type="text/javascript" src="js/jquery.min.js"></script>//引入jquery
</head>

<body style="background:url(images/beijing.jpg) no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div style="width:100%;">
	<div id="title"><p>教师工作量核算统计系统</p></div>

	<div id="login-box" >
		<div style="color:red;font-size: 20px;text-align: center;height:20px;"></div>
		<form action="login.action" method="post"  onsubmit="return login()" >//传递参数post，或者get//通过点击按钮触发login()
			<div class=row>
				<label>工&nbsp;&nbsp;&nbsp;号:</label>
				<input type="text" name="username" id="username" ></input>
			</div>
			<div class="row">
				<label>密&nbsp;&nbsp;&nbsp;码:</label>
				<input type="password" name="password" id="password" ></input>
			</div>
			<div id="login-button" style=" margin-top: 40px;">
				<button type="submit">登录</button>//按钮
			</div>
		</form>

	</div>


</div>
</body>
<script type="text/javascript">
	function login() {
		var username=$("#username").val();//$("#username")是id选择器，获得ID为username的标签,通过.val()获得这个标签的value值，赋值给声明的变量。
		var password=$("#password").val();
		if(username==""){
			alert("账号不能为空");
			return false;
		}

		if(password==""){
			alert("密码不能为空");
			return false;
		}






	}


</script>


</html>