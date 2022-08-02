<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>教师工作量申报系统</title>
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
	</style>
</head>
<body>
<div id="tb">
	<span style="margin-left: 40%;font-weight: bold;font-size: 18px;float:left;">教学工作量</span>
	<span class="editSpan">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTCourse()">申报</a>
						<%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="updateCourse()">申诉</a>--%>
				</span>
	<%--<span class="editSpan">

				<a href="#" class="easyui-menubutton" style="font-weight: bold;""  id="mb" data-options="menu:'#mm'">其它工作量详情</a>
		</span>--%>
</div>

<table id="dg" class="easyui-datagrid" border="false" data-options="onLoadSuccess: onLoadSuccess"  fitColumns="true" emptyMsg="暂无数据！" singleSelect="true">
	<thead>
	<th field="tid" width="50" align="center">申报编号</th>
	<th field="coName" width="50" align="center">课程名</th>
	<th field="coType" width="50" align="center">课程类型</th>


	<th field="coTotal" width="50" align="center">总课时</th>
	<th field="clName" width="50" align="center">开课班级</th>
	<th field="coTerm" width="50" align="center">开课学期</th>

	<th field="TWorkload" width="50" align="center">教学工作量</th>

	<th field="state" width="50" align="center">审核状态</th>

	<th field="content" width="50" align="center">审核意见</th>
	</thead>
	<c:if test="${list.size()==0}">
		<div class="easyui-window" title="提示" style="width:200px;height:130px;text-align: center;">
			<span>暂无任教课程！</span></br>
		</div>
	</c:if>
	<c:if test="${list.size()!=0}">
		<c:forEach items="${list}" var="e">
			<tr>
				<td  >${e.tid}</td>

				<td>${e.courses.coName }</td>
				<td>${e.courses.coType }</td>


				<td>${e.courses.coTotal }</td>
				<td>${e.classes.clName}</td>
				<td>${e.courses.coTerm}</td>

				<td>${e.totalTworkload}</td>
				<c:if test="${e.state=='0'}">
					<td>未申报</td>
				</c:if>
				<c:if test="${e.state=='1'}">
					<td>已提交</td>
				</c:if>
				<c:if test="${e.state=='2'}">
					<td>审核通过</td>
				</c:if>
				<c:if test="${e.state=='3'}">
					<td>审核不通过</td>
				</c:if>
				<td>${e.content}</td>
			<%--	<td>${e.content}</td>
				<c:if test="${e.state=='0'}">
				<td><div   style="margin-top: 10px; "  >
					<a href="javascript:void(0)"   onclick="addTCourse()"  id="${e.tid}" class="easyui-linkbutton" style="width:70px;height:35px;outline: none;" iconCls="icon-add">申报	</a>
				</div></td>
				</c:if>
				<c:if test="${e.state=='3'}">
					<td><div style="margin-top: 10px;" >
						<a href="javascript:void(0)" onclick="addTCourses()" id="${e.tid}" class="easyui-linkbutton" style="width:70px;height:35px;outline: none;" iconCls="icon-remove">申诉</a>
					</div></td>
				</c:if>--%>
			</tr>
		</c:forEach>
	</c:if>



</table>
</div>
<div class="easyui-window" id="ew" title="修改" style="width:420px;height:250px; ">
<div class="easyui-window" id="warn" title="警告" style="width:200px;height:150px;text-align: center;">
	<p>请选择一行数据!</p>
	<div style="margin-top: 20px;">
		<a href="javascript:void(0)" onclick="warn()" class="easyui-linkbutton" >确定</a>
	</div>
</div>

</body>

<script type="text/javascript">

	$(function(){
		$("#ew").window("close");
		$("#warn").window("close");
		$("#warn1").window("close");
	});
	function cancel(){
		$("#warn1").window("close");
	}


	function warn(){
		$("#warn").window("close");
	}
	$(function(){
		$("#other").window("close");

	});


	function totalOworkload(){
		$("#other").window("open");
	}

	function onLoadSuccess(data){
		//datagrid的index，表示从第几行开始合并；紫色的内容需是最精髓的，就是记住最开始需要合并的位置
		//合并单元格的区域，就是clomun中的filed对应的列
		//纵向合并的格数，如果想要横向合并，就使用colspa
		console.info(data);
		$('#dg').datagrid('mergeCells',{
			index:'0',
			field:'OWorkload',
			rowspan:data.rows.length
		});

		$('#dg').datagrid('mergeCells',{
			index:'0',
			field:'name',
			rowspan:data.rows.length
		});
		$('#dg').datagrid('mergeCells',{
			index:'0',
			field:'totalworkload',
			rowspan:data.rows.length
		});

	};
	function updateCourse(){
		var row=$("#dg").datagrid("getSelected");
		var tid = row.tid;
		if(row==null||row==""){
			$("#warn").window("open");
		}else  if(row.length>1){
			alert("请选择一行数据")
		} else  if(row.state=='审核通过'){
			alert("已审核通过，无需申诉！")
		}else if(row.state=="已提交"){
			alert("审核中，无需申诉！");
		}else{
			var data={
				tid:tid
			};
			$.ajax({
				url:'${pageContext.request.contextPath}/tCourses/updateTCourse.action',
				type:'post',
				data:data,
				success:function(e){
					alert("提交申诉成功");
					window.location="${pageContext.request.contextPath}/tCourses/selectAllCourses.action";
				}
			});
		}

	}
	function addTCourse(){
	var row=$("#dg").datagrid("getSelected");
	if(row==null||row==""){
		$("#warn").window("open");
	}else{
	//	$("#ew").window("open");
		var tid = row.tid;
		var state=row.state;
	if(state=="未申报"){
		var data={
			tid:tid
		};
		$.ajax({
			url:'${pageContext.request.contextPath}/tCourses/updateTCourse.action',
			type:'post',
			data:data,
			success:function(e){
				alert(e.msg);
				window.location="${pageContext.request.contextPath}/tCourses/selectAllCourses.action";
			}
		});
	}else if(state=="已提交"){
		alert("已经提交，无须重复提交！");
	}else if(state=="审核通过"){
		alert("已经审核通过，无须重复提交！");
	}else{
		alert("审核不通过，如有问题，请提交申诉！");
	}

		}}


	function certain(){
		var row=$("#dg").datagrid("getSelected");
		$.ajax({
			url:'${pageContext.request.contextPath}/courses/delete.action',
			type:'post',
			data:{'coId':row.coId},
			success:function(e){
				window.location="${pageContext.request.contextPath}/courses/selectAll.action";
			}
		});
	}



	/*function addTCourse(){
		var tid = document.getElementsByTagName("a")[0].getAttribute('id');
		var data={
			tid:tid
		};
		$.ajax({
			url:'${pageContext.request.contextPath}/tCourses/updateTCourse.action',
			type:'post',
			data:data,
			success:function(e){
				alert(e.msg);
				window.location="${pageContext.request.contextPath}/tCourses/selectAllCourses.action";
			}
		})


	}*/







</script>
</html>