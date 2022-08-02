<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师工作量核算系统</title>
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
	margin-right: 10px;
	
}

input{
	width:250px;
	height:27px;
	border-radius:7px;
	outline:none;
	

}


</style>

<script type="text/javascript">

var workName="";
$(function(){
	$("#ew").window("close");
	$("#warn").window("close");
	$("#warn1").window("close");
	$("#delete").window("close");
});

	function deleteUser(){
		var row=$("#dg").datagrid("getSelected");
		
		if(row==null||row==""){
			$("#warn").window("open");
		}else{
			$("#warn1").window("open");
		}
		
		
	}
	
	function certain(){
		var row=$("#dg").datagrid("getSelected");
		$.ajax({
			url:'${pageContext.request.contextPath}/user/delete.action',
			type:'post',
			data:{'username':row.username},
			success:function(e){
				var html='<span>'+(e.msg)+'</span>';
				$("#delete").html(html);
				$("#delete").window("open");
				$("#warn1").window("close");
				window.location="${pageContext.request.contextPath}/user/selectAll.action";
			}
		})
	}
	
	
	function cancel(){
		$("#warn1").window("close");
	}


	function delHtmlTag(str){
		if(str==null||str.replace(/(^s*)|(s*$)/g, "").length ==0) {
			return "";
		}else {
			return str.toString().match(/[\u4e00-\u9fa5]/g).join("");//取其中的汉字}
		}
		//.*\/(?<room>.*)(?<shi>\d).*(?<ting>\d).*(?<wei>\d).*
	}
	
	function delHtmlTag1(str){
		if(str==null||str.replace(/(^s*)|(s*$)/g, "").length ==0) {
            return "";
		}else{
			return str.toString().match(/[0-9a-zA-Z\u4e00-\u9fa5]/g).join("");//取其中的汉字与数字
		}

	}
	
function updateUser(){
	var row=$("#dg").datagrid("getSelected");
	if(row==null||row==""){
		$("#warn").window("open");
	}else{
		$("#ew").window("open");
		var roleid=delHtmlTag(row.roleid);
		var collegeName=delHtmlTag1(row.cid);//获取所属学院
		var education=delHtmlTag(row.education);
		var title=delHtmlTag(row.title);
		console.log(education+title+${roleid});
		$.ajax({
			url:'${pageContext.request.contextPath}/college/selectAllCollege.action',
			type:'post',
			data:'',
			success:function(e){
				var html="";
				 html+='<div style="margin-top: 30px;">'+
					'<span class="name">工号:</span>'+
					'<input type="text" class="easyui-validatebox" readonly="readonly" style="width:70px;text-align:center;font-weight: bold;"  id="username" value="'+(row.username)+'" />'+
				'</div>'+
					 '<div style="margin-top: 10px;" >'+
					'<div class="name"><span>类型:</span></div>'+
					'<select id="selectLevel" style="width:250px;height:30px;border-radius: 5px;" >';
				<c:if test="${roleid==2}">
							html+='<option value="2">教务管理员</option>';
				</c:if>

				html+='<option value="3">学院管理员</option>'+
						'<option value="4">老师</option>'+
					'</select>'+
			'</div>'+
			'<div style="margin-top: 10px;">'+
				'<div class="name"><span>所属学院:</span></div>'+
				'<select id="selectwId" style="width:250px;height:30px;border-radius: 5px;"><div></div></select>'+
			'</div>';
			
			
			
		html+='<div style="margin-top: 10px;">'+
				'<span class="name">姓名:</span>'+
				'<input type="text" class="easyui-validatebox"  id="name" value="'+(row.name)+'" />'+
			'</div>'+
			/* '<div style="margin-top: 10px;">'+
			'<div class="name"><span>密码:</span></div>'+
				'<input class="easyui-validatebox"  data-options="required:true,validType:'+"length[6,12]"+'" type="password" id="password"  value="'+(row.password)+'" />'+
			'</div>'+
			'<div style="margin-top: 10px;">'+
				'<div class="name"><span>确认密码:</span></div>'+
				'<input class="easyui-validatebox" type="password"  data-options="required:true,validType:'+"length[6,12]"+'"   id="password1" value="'+(row.password)+'" />'+
			'</div>'+ */
			'<div style="margin-top: 10px;">'+
			'<span class="name">年龄:</span>'+
			'<input type="text" class="easyui-validatebox"  id="age" value="'+(row.age)+'" />'+
		'</div>'+

			'<div style="margin-top: 10px;">'+
				'<div class="name"><span>电话号码:</span></div>'+
				'<input class="easyui-validatebox" data-options="required:true,validType:'+"length[11,11]"+'"" id="phone"  value="'+(row.phone)+'"   />'+
			'</div>'+
				'<div style="margin-top: 10px;">'+
				'<div class="name"><span>邮箱:</span></div>'+
				'<input class="easyui-validatebox " data-options="required:true,validType:'+"email"+'" id="email" value="'+(row.email)+'"  />'+
				'</div>'+
				'<div style="margin-top: 10px;" >'+
				'<div class="name"><span>学历:</span></div>'+
				'<select id="selectEducation" style="width:250px;height:30px;border-radius: 5px;" >'+
				'<option value="1">本科</option>'+
				'<option value="2">硕士</option>'+
				'<option value="3">博士</option>'+
				'</select>'+
				'</div>'+
				'<div style="margin-top: 10px;" >'+
				'<div class="name"><span>职称:</span></div>'+
				'<select id="selectTitle" style="width:250px;height:30px;border-radius: 5px;" >'+
				'<option value="4">普通老师</option>'+
				'<option value="3">讲师</option>'+
				'<option value="2">副教授</option>'+
				'<option value="1">教授</option>'+
				'</select>'+
				'</div>'+
			'<div style="margin-top: 10px;text-align:center; ">'+
				'<a href="javascript:void(0)" onclick="update()" style="width:100px;"><button style="width:100px;" type="sumbit">提交</button></a>'+
			'</div>';
			
			$("#ew").html(html);
				console.log(education+title);
			 $.each(e,function(n,v){
                     if(collegeName==v.cName){
                         var html1='<option value="'+(v.cId)+'" selected="selected">'+(v.cName)+'</option>';
                         $("#selectwId").append(html1);
                     }else if(${roleid==2}){
                         var html1='<option value="'+(v.cId)+'">'+(v.cName)+'</option>';
                         $("#selectwId").append(html1);
                     }

				});

			 if(roleid=="教务管理员"){
				$("#selectLevel option[value='2']").attr("selected","selected");
			}else if(roleid=="学院管理员"){
				$("#selectLevel option[value='3']").attr("selected","selected");
			}else if(roleid=="老师"){
				$("#selectLevel option[value='4']").attr("selected","selected");
			}
				if(education=="本科"){
					$("#selectEducation option[value='1']").attr("selected","selected");
				}else if(education=="硕士"){
					$("#selectEducation option[value='2']").attr("selected","selected");
				}else if(education=="博士"){
					$("#selectEducation option[value='3']").attr("selected","selected");
				}
				if(title=="教授"){
					$("#selectTitle option[value='1']").attr("selected","selected");
				}else if(title=="副教授"){
					$("#selectTitle option[value='2']").attr("selected","selected");
				}else if(title=="讲师"){
					$("#selectTitle option[value='3']").attr("selected","selected");
				}else if(title=="普通老师"){
					$("#selectTitle option[value='4']").attr("selected","selected");
				}
				
			} 
		});
		
	
	}
}

function update(){
	var row=$("#dg").datagrid("getSelected");
	var level=$("#selectLevel option:selected").val();
	var cId=$("#selectwId option:selected").val();
	var age=$("#age").val();
	var userid=$("#userid").val();
	//alert(userid);
	var username=$("#username").val();
	/* var password=$("#password").val();
	var password1=$("#password1").val(); */
	var name=$("#name").val();
	var email=$("#email").val();
	var phone=$("#phone").val();
	var education=$("#selectEducation option:selected").val();
	var title=$("#selectTitle option:selected").val();

	if(level==""){
		alert("请选择用户类型!");
		return false;
	}
	if(cId==""){
		alert("请选择所属学院!");
		return false;
	}
	if(education==""){
		alert("请选择学历!");
		return false;
	}
	if(title==""){
		alert("请选择职称!");
		return false;
	}

	/* if(password==""||password1==""){
		alert("用户密码不能为空!");
		return false;
	}
	if(password!=password1){
		alert("输入密码不一致!");
		return false;
	} */
	if(name==""){
		alert("姓名不能为空!");
		return false;
	}
	if(age==""){
		alert("年龄不能为空!");
		return false;
	}
	if(email==""){
		alert("用户邮箱不能为空!");
		return false;
	}
	if(phone==""){
		alert("电话号码不能为空!");
		return false;
	}
	var data={
		username:username,
			roleid:	level,
			cId:cId,
			name:name,
			email:email,
			phone:phone,
			age:age,
		education:education,
		title:title
		};
	 $.ajax({
		url:'${pageContext.request.contextPath}/user/update.action',
		type:'post',
		data:data,
		success:function(e){
			if(e.success){
				window.location="${pageContext.request.contextPath}/user/selectAll.action";
			}else {
				alert(e.msg);
				window.location="${pageContext.request.contextPath}/user/selectAll.action";
			}
		}
	}); 
}
function warn(){
	$("#warn").window("close");
}








</script>
</head>
<body>
<div id="tb">
		<span class="editSpan">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteUser()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateUser()">修改</a>
		</span>
</div> 
<table id="dg" class="easyui-datagrid" border="false" order="false" data-options="pagination:true,fitColumns:true,rownumbers:true,singleSelect:true" emptyMsg="暂无数据！" >
		<thead>	
			<th field=username width="100" align="center">工号</th>
			<th field=roleid width="100" align="center">类型</th>
			<th field=cid width="200" align="center">所属学院</th>
			<th field=name width="100" align="center">姓名</th>
			<th field=age width="100" align="center">年龄</th>
			<th field=phone width="200" align="center">电话</th>
			<th field=email width="200" align="center">邮箱</th>
			<th field=education width="100" align="center">学历</th>
			<th field=title width="100" align="center">职称</th>
		</thead>
		<c:if test="${list.size()==0}">
				<div class="easyui-window" title="提示" style="width:200px;height:100px;text-align: center;">
					<span>暂无数据！</span>
				</div>
		</c:if>
		<c:if test="${list.size()!=0}">
		<c:forEach items="${list}" var="u">
			<tr>
				<td>${u.username }</td>
				<td>
					<c:if test="${u.roleid==2}">
						<span>教务管理员</span>
					</c:if>
					<c:if test="${u.roleid==3}">
						<span>学院管理员</span>
					</c:if>
					<c:if test="${u.roleid==4}">
						<span>老师</span>
					</c:if>
				</td>
				<td>
					<c:forEach items="${collegeList}" var="w">
						<c:if test="${w.cId==u.cid}">${w.cName}</c:if>
					</c:forEach>
				</td>
				<td>${u.name}</td>
				<td>${u.age}</td>
				<td>${u.phone}</td>
				<td>${u.email}</td>
				<td>
				<c:if test="${u.education==1}">
					<span>本科</span>
				</c:if>
				<c:if test="${u.education==2}">
					<span>硕士</span>
				</c:if>
				<c:if test="${u.education==3}">
					<span>博士</span>
				</c:if></td>
				<td>
					<c:if test="${u.title==1}">
						<span>教授</span>
					</c:if>
					<c:if test="${u.title==2}">
						<span>副教授</span>
					</c:if>
					<c:if test="${u.title==3}">
						<span>讲师</span>
					</c:if>
					<c:if test="${u.title==4}">
						<span>普通老师</span>
					</c:if>
				</td>

			</tr>
		</c:forEach>
	</c:if>
</table>

<div class="easyui-window" id="ew" title="修改数据" style="width:500px;height:500px; ">
	
</div>

<div class="easyui-window" id="delete" title="提示" style="width:200px;height:100px;text-align: center; ">
	
</div>

<div class="easyui-window" id="warn" title="警告" style="width:200px;height:150px;text-align: center;">
	<p>请选择一行数据!</p>
	<div style="margin-top: 20px;">
		<a href="javascript:void(0)" onclick="warn()" class="easyui-linkbutton" >确定</a>
	</div>
</div>

<div class="easyui-window" id="warn1" title="警告" style="width:200px;height:150px;text-align: center;">
	<p>确定删除数据!</p>
	<div style="margin-top: 20px;">
		<a href="javascript:void(0)" onclick="certain()" class="easyui-linkbutton" >确定</a>
		<a href="javascript:void(0)" onclick="cancel()" class="easyui-linkbutton" >取消</a>
	</div>
</div>

</body>
</html>