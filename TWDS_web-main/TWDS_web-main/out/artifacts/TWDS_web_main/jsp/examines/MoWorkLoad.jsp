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
		input{
			width:100%;
			height: 100%;
		
		}
		
			#tb{
		height:40px;
		background: #efefef;
		
		
	}
.editSpan{
	position: relative;
	text-align:center;
	top: 6px;
	right: 30px;

}
		
	</style>
</head>

<%--教学工作量汇总模块（学院管理员与教师）--%>
<body>
<div id="tb">
	<span style="margin-left: 40%;font-weight: bold;font-size: 18px;float:left;">教学工作量</span>
</div>
<div id="tb">
				<span class="editSpan1">
					年份:&nbsp;&nbsp;<select id="selectyear" style="height:30px;border-radius: 5px;text-decoration: none;" >
							<option selected="selected"  value="-1" >---全部---</option>
                    <c:if test="${resultMap.size()!=0}">
						<c:forEach items="${resultMap}" var="content">
                            <c:if test="${year==content.key}">
                                <option  selected="selected" value="${content.key}" >${content.key}</option><%--按年份显示--%>
                            </c:if>
                            <c:if test="${year!=content.key}">
                                <option   value="${content.key}" >${content.key}</option><%--显示全部--%>

                            </c:if>
                        </c:forEach>
                    </c:if>
					</select>
					审核状态:&nbsp;&nbsp;<select id="selectresult" style="height:30px;border-radius: 5px;text-decoration: none;" >
							<option   value="-1" >---全部---</option>
                                      <c:if test="${state==null||state=='-1'}">显示全部
										  <option value="0" >未申报</option>
										  <option value="1" >已提交</option>
										  <option value="2">审核通过</option>
										  <option value="3">审核不通过</option>
									  </c:if>
				     	 <c:if test="${state!=null}">
							 <c:if test="${state=='0'}">
								 <option value="0" selected="selected" >未申报</option>
								 <option value="1" >已提交</option>
								 <option value="2">审核通过</option>
								 <option value="3">审核不通过</option>
							 </c:if>
							 <c:if test="${state=='1'}">
								 <option value="0"  >未申报</option>
								 <option value="1" selected="selected">已提交</option>
								 <option value="2">审核通过</option>
								 <option value="3">审核不通过</option>
							 </c:if>
							 <c:if test="${state=='2'}">
								 <option value="0"  >未申报</option>
								 <option value="1" >已提交</option>
								 <option value="2" selected="selected">审核通过</option>
								 <option value="3">审核不通过</option>
							 </c:if>
							 <c:if test="${state=='3'}">
								 <option value="0"  >未申报</option>
								 <option value="1" >已提交</option>
								 <option value="2">审核成功</option>
								 <option value="3" selected="selected">审核失败</option>
							 </c:if>

					 </c:if>

					</select>
					<a href="javascript:void(0)"  class="easyui-linkbutton" onclick="searchByresult()"  iconCls="icon-search">搜索</a>
				 </span>
				
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
<div id="tb">
	<span style="margin-left: 40%;font-weight: bold;font-size: 18px;float:left;">年度统计</span>
</div>
<table id="dg" class="easyui-datagrid" border="false" data-options="onLoadSuccess: onLoadSuccess"  fitColumns="true" emptyMsg="暂无数据！" singleSelect="true">
	<thead>
	<th field="tid" width="50" align="center">年度</th>
	<th field="coName" width="50" align="center">工作量</th>

	</thead>
	<c:if test="${list.size()==0}">
		<div class="easyui-window" title="提示" style="width:200px;height:130px;text-align: center;">
			<span>暂无任教课程！</span></br>
		</div>
	</c:if>
	<c:if test="${resulut.size()!=0}">
		<c:forEach items="${resulut}" var="content">
			<tr>
                <c:if test="${year==content.key}">
                    <td  >${content.key}</td>
                    <td>${content.value}</td>
                </c:if>
                <c:if test="${year==null||year=='-1'}">
                    <td  >${content.key}</td>

                    <td>${content.value}</td>
                </c:if>


			</tr>
		</c:forEach>
	</c:if>



</table>
</body>
<script type="text/javascript">
	/*var date =new Date();
	var year=date.getFullYear();
	for(var i=year-1;i<=year+1;i++){
		$("#selectyear").append("<option value="+i+">"+i+"年"+"</option>");

	}*/
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
	function searchByresult(){
		var year=$("#selectyear option:selected").val();
		var state=$("#selectresult option:selected").val();
		window.location="${pageContext.request.contextPath}/examines/MTechWorkLoad.action?state="+state+"&&year="+year;
	}

</script>
</html>