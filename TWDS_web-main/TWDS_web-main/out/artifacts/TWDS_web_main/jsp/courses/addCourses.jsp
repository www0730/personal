<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
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
	#tb{
		height:40px;
		background: #efefef;
		
		
	}
.editSpan{
	position: relative;
	float:right;
	top: 6px;
	right: 30px;

}
.name{
	width:100px;
	text-align: right;
	display: inline-block;
	margin-right: 20px;
	
}

input{
	width:250px;
	height:27px;
	border-radius:7px;
	outline:none;

}
</style>
</head>

<%--添加课程模块--%>
<body>
	<div class="easyui-panel" border="false"  style="text-align: center;">
			<div style="margin-top: 20px;">
				<div style="width:50%;float: left;text-align: center;" >
					<div style="margin-top: 30px;">
						<div class="name"><span>课程名称:</span></div>
						<input class="easyui-textbox" data-options="required:true" id="coName" prompt="如:Java课程设计"/><%--动态选择列，并让相应列内容必填--%>
					</div>
					
					<div style="margin-top: 10px;">
						<div class="name"><span>学分:</span></div>
						<input class="easyui-textbox" data-options="required:true" id="coWeek" prompt="如:8.0"/>
					</div>
					
					<div style="margin-top: 10px;">
						<div class="name"><span>开课周数:</span></div>
						<input class="easyui-textbox" data-options="required:true" id="coWenum" prompt="如:18"/>
					</div>
					
					<div style="margin-top: 10px;">
						<div class="name"><span>总课时:</span></div>
						<input class="easyui-textbox" data-options="required:true" id="coTotal" prompt="如:32.0"/>
					</div>
					
					<div style="margin-top: 10px;">
						<div class="name"><span>开课学期:</span></div>
						<input class="easyui-textbox" data-options="required:true" id="coTerm" prompt="如:2022年上学期"/>
					</div>
				</div>
				<div style="width:50%;float:right;text-align: left;margin-top:10px;">
					<div style="··margin-top:  110px;">
							<div class="name"><span>课程类型:</span></div>
							<select id="selectType" style="width:250px;height:30px;border-radius: 5px;" >
									<option value="研究生课程">研究生课程</option>
									<option value="本科生课程">本科生课程</option>
							</select>
					</div>
				
					<div style="margin-top:10px;">
						<div class="name" ><span >所属二级学院:</span></div>
						<select id="selectCollege" style="width:250px;height:30px;border-radius: 5px;" >
							<c:forEach items="${list}" var="c">
								<option value="${c.cId}">${c.cName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<a href="javascript:void(0)" onclick="addCourses()" class="easyui-linkbutton" style="width:70px;height:35px;outline: none;" iconCls="icon-add">添加</a>
			</div>
			
	</div>
	
	
</body>
<script type="text/javascript">

function addCourses(){
	var coType=$("#selectType option:selected").val();
	var cId=$("#selectCollege option:selected").val();
	var coName=$("#coName").val();
	var coWeek=$("#coWeek").val();
	var coWenum=$("#coWenum").val();
	var coTotal=$("#coTotal").val();
	var coTerm=$("#coTerm").val();
	
	if(coName==""){
		alert("课程名不能为空!");
		return false;
	}
	if(coWeek==""){
		alert("学分不能为空!");
		return false;
	}
	if(coWenum==""){
		alert("开课周数不能为空!");
		return false;
	}

	if(coTotal==""){
		alert("总课时不能为空!");
		return false;
	}
	if(coTerm==""){
		alert("开课学期不能为空!");
		return false;
	}
	if(coTerm.indexOf("年")==-1){
		alert("开课学期的格式为X年上/下学期!");
		return false;
	}
	if(coTerm.indexOf("学期")==-1){
		alert("开课学期的格式为X年上/下学期!");
		return false;
	}
	if(coTerm.indexOf("上")==-1&&coTerm.indexOf("下")==-1){
		alert("开课学期的格式为X年上/下学期!");
		return false;
	}

	var data={
			coType:coType,
			cId:cId,

			coName:coName,
			coWeek:coWeek,
			coWenum:coWenum,
			coTotal:coTotal,
			coTerm:coTerm
		};
	$.ajax({
		url:'${pageContext.request.contextPath}/courses/insert.action',
		type:'post',
		data:data,
		success:function(e){
			alert(e.msg);
			window.location="${pageContext.request.contextPath}/courses/addCoursesView.action";
		}
	})
	
	
}


</script>
</html>