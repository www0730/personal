<%@ page import="com.zzptc.twds.pojo.Scientific" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

	String username =  request.getAttribute("username").toString();
	String name =  request.getAttribute("name").toString();
	List<Scientific> sclist = (List<Scientific>) request.getAttribute("sclist");
	String sclistStr = JSONArray.toJSONString(sclist).toString();
%>
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
			margin-right: 10px;

		}
	</style>
</head>

<%--科研工作量添加与申报--%>
<body>
<div id="tb">
	<span style="margin-left: 40%;font-weight: bold;font-size: 18px;float:left;">科研工作量</span>
	<span class="editSpan">
		                 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="addScientific()">添加</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addTCourse()">申报</a>
						<%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="updateCourse()">申诉</a>--%>
				</span>
	<%--<span class="editSpan">

				<a href="#" class="easyui-menubutton" style="font-weight: bold;""  id="mb" data-options="menu:'#mm'">其它工作量详情</a>
		</span>--%>
</div>

<table id="dg" class="easyui-datagrid" border="false" data-options="onLoadSuccess: onLoadSuccess"  fitColumns="true" emptyMsg="暂无数据！" singleSelect="true">
	<thead>
	<th field="toid" width="50" align="center">申报编号</th>

	<th field="oType" width="50" align="center">科研类型</th>
	<th field="toName" width="50" align="center">科研名称</th>
	<th field="time" width="50" align="center">时间</th>
	<th field="totalWorkload" width="50" align="center">工作量</th>
	<th field="state" width="50" align="center">审核状态</th>
	<th field="content" width="50" align="center">审核意见</th>
	</thead>
	<c:if test="${list.size()==0}">
		<div class="easyui-window" title="提示" style="width:200px;height:130px;text-align: center;">
			<span>暂无科研工作量！</span></br>
		</div>
	</c:if>
	<c:if test="${list.size()!=0}">
		<c:forEach items="${list}" var="e">
			<tr>
				<td  >${e.toid}</td>

			<td>${e.scientific.scientificName }</td>
				<td>${e.toname}</td>


				<td>${e.time}年</td>
				<td>${e.totalWorkload}</td>

				<c:if test="${e.state==0}">
					<td>未申报</td>
				</c:if>
				<c:if test="${e.state==1}">
					<td>已提交</td>
				</c:if>
				<c:if test="${e.state==2}">
					<td>审核通过</td>
				</c:if>
				<c:if test="${e.state==3}">
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
<div class="easyui-window" id="ew" title="添加科研工作量" style="width:420px;height:280px; ">

</div>
<div  class="sss" id="${username}" title="添加科研工作量" style="width:420px;height:280px; ">

</div>
<div  class="ss" id="${name}" title="添加科研工作量" style="width:420px;height:280px; ">

</div>
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

	function addScientific(){
		var username='<%=username%>';
		var name='<%=name%>';
			$("#ew").window("open");
			var html='<div style="margin-top: 30px;" xmlns="http://www.w3.org/1999/html">'+
					'<div class="name"><span">工号:</span></div>'+
					'<input type="text" class="easyui-validatebox"  readonly="readonly"  style="text-align:center;font-weight: bold;" id="username" value=" '+(username)+'"/>'+
					'</div>'+
					'<div style="margin-top: 10px;">'+
					'<div class="name"><span >姓名:</span></div>'+
					'<input type="text"  class="easyui-validatebox"  readonly="readonly"  style="text-align:center;font-weight: bold;"  id="name" value="'+(name)+'"/>'+
					'</div>'+

					'<div style="margin-top: 10px;">'+
					'<div class="name"><span >科研类别:</span></div>'+
					'<select  id="select"   style="width:180px;height:30px;border-radius: 5px;"   />'+
					'</div>'+
					'<div style="margin-top: 10px;">'+
					'<div class="name"><span >科研名称:</span></div>'+
					'<input type="text"  style="text-align: center;"class="easyui-validatebox" data-options="required:true"  id="toName" value=""/>'+
					'</div>'+
					'<div style="margin-top: 10px;">'+
					'<div class="name"><span >时间:</span></div>'+
					'<select   id="year" style="width:180px;height:30px;border-radius: 5px;" /></select>'+
					'</div>'+
					'<div style="margin-top: 10px;text-align:center; ">'+
					'<a href="javascript:void(0)" onclick="examScientific()" ><button style="width:70px;"  type="sumbit">提交</button></a>'+
					'</div>';

			$("#ew").html(html);
		var reportList = JSON.parse('<%=sclistStr%>');
		for(var i=0;i<reportList.length;i++){
			var scientificId=reportList[i].scientificId;
			var scientificName=reportList[i].scientificName;
			$("#select").append("<option value="+scientificId+">"+scientificName+"</option>");

		}
		var date =new Date();
		var year=date.getFullYear();

        for(var i=year-1;i<=year+1;i++){
            $("#year").append("<option value="+i+">"+i+"年"+"</option>");

        }

	}

    function addTCourse(){
        var row=$("#dg").datagrid("getSelected");
        if(row==null||row==""){
            $("#warn").window("open");
        }else{
            //	$("#ew").window("open");
            var toid = row.toid;
            var state=row.state;
            if(state=="未申报"){
                var data={
                    toid:toid
                };
                $.ajax({
                    url:'${pageContext.request.contextPath}/tCourses/updateTScientific.action',
                    type:'post',
                    data:data,
                    success:function(e){
                        alert(e.msg);
                        window.location="${pageContext.request.contextPath}/tCourses/DeclareScientificView.action";
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

	function examScientific(){
		var username=$("#username").val();
		var name=$("#name").val();
		var scientificId=$("#select").val();
		var toName=$("#toName").val();
		var year=$("#year").val();
		var data={username:username,name:name,scientificId:scientificId,toName:toName,year:year}
		$.ajax({
			url:'${pageContext.request.contextPath}/examines/examScientific.action',
			type:'post',
			data:data,
			success:function(e){
				alert(e.msg);
				if(e.success){
					window.location="${pageContext.request.contextPath}/tCourses/DeclareScientificView.action";
				}
			}
		})

	}





</script>
</html>