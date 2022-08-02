<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师工作量核算统计系统</title>
<link href="${pageContext.request.contextPath}/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">

span{
	width:100px;
	height:30px;
	margin-right: 20px;

}

input{
	width: 250px;
	height:30px;
	border-radius: 6px;
	text-align: left;
	outline: none;
}
.name{
	width:100px;
	text-align: right;
	display: inline-block;

}

</style>

</head>
<body>

	<div class="easyui-panel" border="false" style="text-align: center;">
		<div style="margin-top: 30px;" >
			<div class="name"><span>用户类型:</span></div>
			<select id="selectLevel" style="width:250px;height:30px;border-radius: 5px;" >
				<c:if test="${roleid==2}">
					<option value="2">教务管理员</option>
				</c:if>
				<option value="3">学院管理员</option>
				<option value="4">老师</option>
			</select>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>所属学院:</span></div>
			<c:if test="${roleid==2}">
			<select class="selectcId" style="width:250px;height:30px;border-radius: 5px;">
				<c:forEach items="${collegeList}" var="w">
					<option value="${w.cId}">${w.cName}</option>
				</c:forEach>
			</select>
			</c:if>
			<c:if test="${roleid==3}">
			<select class="selectcId" style="width:250px;height:30px;border-radius: 5px;">
				<c:forEach items="${collegeList}" var="w">
					<c:if test="${w.cId==cid}">
						<option value="${w.cId}"  selected>${w.cName}</option>
					</c:if>
				</c:forEach>
			</select>
			</c:if>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>工号:</span></div>
			<input class="easyui-validatebox" data-options="required:true" id="username"/>
		</div>
		<div style="margin-top: 10px;">
		<div class="name"><span>密码:</span></div>
			<input class="easyui-validatebox" data-options="required:true,validType:'length[6,12]'" type="password" id="password"/>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>姓名:</span></div>
			<input class="easyui-validatebox" data-options="required:true" id="name"/>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>年龄:</span></div>
			<input class="easyui-validatebox" data-options="required:true" id="age"/>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>邮箱:</span></div>
			<input class="easyui-validatebox " data-options="required:true,validType:'email'" id="email"/>
		</div>
		<div style="margin-top: 10px;">
			<div class="name"><span>电话号码:</span></div>
			<input class="easyui-validatebox" data-options="required:true,validType:'length[11,11]'" id="phone"/>
		</div>
		<div style="margin-top: 10px;">
		<div class="name"><span>学历:</span></div>
	<select id="selectEducation" style="width:250px;height:30px;border-radius: 5px;" >
		<option value="1" selected>本科</option>
		<option value="2">硕士</option>
		<option value="3">博士</option>
	</select>
	</div>
		<div style="margin-top: 10px;">
	<div class="name"><span>职称:</span></div>
	  <select id="selectTitle" style="width:250px;height:30px;border-radius: 5px;" >
		<option value="4" selected>普通老师</option>
		<option value="3">讲师</option>
		<option value="2">副教授</option>
		  <option value="1">教授</option>
	</select>
	</div>
		<div style="margin-top: 20px;">
			<a href="javascript:void(0)" onclick="insertUser()" class="easyui-linkbutton" style="width:100px;outline: none;" iconCls="icon-add">注册</a>
		</div>
	</div>



</body>

<script type="text/javascript">

	function insertUser(){
		var level=$("#selectLevel option:selected").val();
		var cId=$(".selectcId option:selected").val();
		console.log("da"+cId);
		var username=$("#username").val();
		var password=$("#password").val();
		var name=$("#name").val();
		var email=$("#email").val();
		var phone=$("#phone").val();
		var age=$("#age").val();
		var education=$("#selectEducation").val();
		var title=$("#selectTitle").val();
		if(level==""){
			alert("请选择用户类型!");
			return false;
		}
		if(cId==""){
			alert("请选择所属学院!");
			return false;
		}
		if(username==""){
			alert("工号不能为空!");
			return false;
		}


		if(password==""){
			alert("用户密码不能为空!");
			return false;
		}

		if(name==""){
			alert("姓名不能为空!");
			return false;
		}

		if(email==""){
			alert("邮箱不能为空!");
			return false;
		}
		if(phone==""){
			alert("电话号码不能为空!");
			return false;
		}
		var data={
				roleid:	level,
				cId:cId,
			   username:username,
				password:password,
				name:name,
				email:email,
				phone:phone,
				age:age,
				education:education,
				title:title
			};
		$.ajax({
			url:'${pageContext.request.contextPath}/user/insert.action',
			type:'post',
			data:data,
			success:function(e){
				alert(e.msg);
				window.location="${pageContext.request.contextPath}/user/insertUser.action";
			}
		})


	}

</script>
</html>