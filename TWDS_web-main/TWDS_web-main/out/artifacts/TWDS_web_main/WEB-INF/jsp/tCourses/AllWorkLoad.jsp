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
</style>
</head>
<body>
<div id="tb">
				<span class="editSpan1"  style="margin-top: 20px;">
					年份:&nbsp;&nbsp;<select id="selectyear" style="height:30px;border-radius: 5px;text-decoration: none;margin-top: 20px;" >
							<option selected="selected"  value="-1" >---全部---</option>
							<c:if test="${yearMap.size()!=0}">
								<c:forEach items="${yearMap}" var="content">
									<c:if test="${year==content.key}">
										<option  selected="selected" value="${content.key}" >${content.key}</option>

									</c:if>
									<c:if test="${year!=content.key}">
										<option   value="${content.key}" >${content.key}</option>

									</c:if>
								</c:forEach>
							</c:if>
				</select></span>
	<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="searchByresult()"  iconCls="icon-search">搜索</a>
</div>
<div id="tb">
		<span style="margin-left: 40%;font-weight: bold;font-size: 18px;float:left;">教师工作量汇总</span>
</div>

<table id="dg" class="easyui-datagrid" border="false" data-options="onLoadSuccess: onLoadSuccess"  fitColumns="true"  singleSelect="true">
	<thead>

	<th field="coName" width="50" align="center">工号</th>
	<th field="TWorkload" width="50" align="center">教学工作量</th>
	<th field="SWorkload" width="50" align="center">科研工作量</th>
	<th field="OWorkload" width="50" align="center">其他工作量</th>
	<th field="AWorkload" width="50" align="center">工作量</th>

	</thead>
	<c:if test="${list.size()==0}">
		<div class="easyui-window" title="提示" style="width:200px;height:130px;text-align: center;">
			<span>暂无工作量！</span></br>
		</div>
	</c:if>
	<c:if test="${list.size()!=0}">
		<c:forEach items="${list}" var="e">
			<tr>
				<td  >${e.username}</td>
				<td  >${e.totalTworkload}</td>
				<td  >${e.totalworkload}</td>
				<td  >${e.totalOworkload}</td>
				<td  >${e.total}</td>


			</tr>
		</c:forEach>
	</c:if>



</table>


</body>

<script type="text/javascript">
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
	/*	$('#dg').datagrid('mergeCells',{
				index:'0',                
				field:'OWorkload',                 	
				rowspan:data.rows.length
		　}); $('#dg').datagrid('mergeCells',{
			index:'0',
			field:'SWorkload',
			rowspan:data.rows.length
		});*/


	/*	$('#dg').datagrid('mergeCells',{
			index:'0',                
			field:'name',               	
			rowspan:data.rows.length 
		}); */
		/*$('#dg').datagrid('mergeCells',{
			index:'0',                
			field:'totalworkload',               	
			rowspan:data.rows.length 
		});*/

	};



	function searchByresult(){
		var year=$("#selectyear option:selected").val();
		window.location="${pageContext.request.contextPath}/examines/AllTechWorkLoad.action?year="+year;
	}




</script>
</html>