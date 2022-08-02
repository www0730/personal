<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>教师工作量核算统计系统</title>
	<link href="css/login.css" rel="stylesheet" type="text/css"></link>
	<script type="text/javascript" src="js/jquery.min.js"></script>
</head>

<body style="background:url(images/beijing.jpg) no-repeat;background-size:100% 100%;background-attachment: fixed;">
<div>
	<div id="title"><p>教师工作量核算统计系统</p></div>
	<div id="login-box" style="height: 290px;">
		<div style="color:red;font-size: 20px;text-align: center;height:20px;">${errorMsg}</div>
		<form action="login.action" method="post" onsubmit="return login()">
			<div class=row>
				<label>工&nbsp;&nbsp;&nbsp;号:</label>
				<input type="text" name="username" id="username"></input>
			</div>
			<div class="row">
				<label>密&nbsp;&nbsp;&nbsp;码:</label>
				<input type="password" name="password" id="password"></input>
			</div>
			<div id="login-button"  style=" margin-top: 40px;">
				<button type="submit">登录</button>
			</div>
		</form>

	</div>


</div>
</body>
<script type="text/javascript">
	function login() {
		var username=$("#username").val();
		var password=$("#password").val();
		var level=$("#select option:selected").val();
		if(username==""){
			alert("账号不能为空");
			return false;
		}
		if(username==""){
			alert("账号不能为空");
			return false;
		}

		if(password==""){
			alert("密码不能为空");
			return false;
		}

		//	window.location.href="http://localhost:8080/GDMS_web/login.action?username="+username+"&password"+password;


	}


</script>


</html>