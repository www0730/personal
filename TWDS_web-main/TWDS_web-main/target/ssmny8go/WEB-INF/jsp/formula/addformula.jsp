<%@ page import="com.zzptc.twds.pojo.Scientific" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.zzptc.twds.pojo.Oworkload" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	List<Scientific> audfList = (List<Scientific>) request.getAttribute("scientificLists");
	String scientificListsStr = JSONArray.toJSONString(audfList).toString();
	List<Oworkload> audfList1 = (List<Oworkload>) request.getAttribute("oworkloadLists");
	String oworkloadListsStr = JSONArray.toJSONString(audfList1).toString();
%>
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

.name{
	width:100px;
	text-align: left;
	display: inline-block;
	margin-left: 10px;
	
}

input{
	width:200px;
	height:27px;
	border-radius:7px;
	outline:none;

}


</style>
</head>
<body>
<p>说明：
	教师工作量的计算办法：教学工作量+科研工作量+其他工作量=课程类型（总课时*类型参数）+课程人数（总课时*人数参数）+学分（总课时*学分参数）+科研工作量（工作量值*科研类别参数）+其他工作量（工作量值*其他工作量参数）</p>
<div class="easyui-panel" border="false" >
			<p>教学工作量参数</p>
			<div style="border-top:1px solid #efefef;">
				<div style="margin-top: 5px;">
					<div style="float: left;width:30%">
						<div class="name"  style="width: 165px;"><span>本科课程:</span></div>
						 <input class="easyui-textbox" data-options="required:true"  id="bvalue"  value="${paras.bvalue}"/>
					</div>
					
					<div style="display:inline-block;width:30%">
						<div class="name" style="width: 165px;"><span>研究生课程:</span></div>
						<input class="easyui-textbox"  data-options="required:true" id="yvalue"  value="${paras.yvalue}"/>
					</div>
					
					<%--<div style="display:inline-block;width:30%">
						<div class="name"><span>p3参数:</span></div>
						<input class="easyui-textbox"  id="p3"  />
					</div>--%>
					
					
				</div>
				<div style="margin-top: 10px;">
					<div style="float: left;width:30%">
						<div class="name" style="width: 165px;"><span>人数小于等于40:</span></div>
						<input class="easyui-textbox" data-options="required:true"  id="rvalue1"  value="${paras.rvalue1}" />
					</div>
					<div style="display:inline-block;width:30%">
						<div class="name" style="width: 165px;"><span>人数大于40小于等于60:</span></div>
						<input class="easyui-textbox"  data-options="required:true"  id="rvalue2"  value="${paras.rvalue2}"/>
					</div>
					
				</div>
				<div style="margin-top: 10px;">
					<div style="float: left;width:30%">
						<div class="name" style="width: 165px;"><span>人数大于60小于等于100:</span></div>
						<input class="easyui-textbox" data-options="required:true"  id="rvalue3" value="${paras.rvalue3}" />
					</div>
					<div style="display:inline-block;width:30%">
						<div class="name"  style="width: 165px;"><span>人数大于100:</span></div>
						<input class="easyui-textbox"  data-options="required:true"  id="rvalue4"  value="${paras.rvalue4}"/>
					</div>

				</div>
				<div style="margin-top: 10px;">
					<div style="float: left;width:30%">
						<div class="name" style="width: 165px;"><span>学分小于等于4:</span></div>
						<input class="easyui-textbox" data-options="required:true"  id="xfvalue1"  value="${paras.xfvalue1}"/>
					</div>
					<div style="display:inline-block;width:30%">
						<div class="name"  style="width: 165px;"><span>学分大于4:</span></div>
						<input class="easyui-textbox"  data-options="required:true"  id="xfvalue2"  value="${paras.xfvalue2}"/>
					</div>

				</div>
			<%--	<div style="margin-top: 10px;">
					<div style="float: left;width:30%">
						<div class="name" style="width: 165px;"><span>课时数小于等于40:</span></div>
						<input class="easyui-textbox" data-options="required:true"  id="kvalue1"  value="${paras.kvalue1}"/>
					</div>
					<div style="display:inline-block;width:30%">
						<div class="name"  style="width: 165px;"><span>课时数大于40小于等于60:</span></div>
						<input class="easyui-textbox"   data-options="required:true"  id="kvalue2" value="${paras.kvalue2}" />
					</div>

				</div>--%>

			</div>
</div>

<div class="easyui-panel" border="false" >


<div style="margin-top: 10px">
   <p>科研工作量参数</p>
<div style="border-top:1px solid #efefef;">
	<div style="margin-top: 5px;">
		<c:forEach items="${scientificList}" var="sc">
			<div style="float: left;width:30%;margin-top:15px;">
				<div class="name" style="width: 165px;"><span>${sc.scientificName}:</span></div>
				<input class="easyui-textbox" data-options="required:true"  id="scientific${sc.scientificId}"  value="${sc.para}"/>
			</div>

		</c:forEach>

	</div>

</div>
	</div>

			
	</div>
<div class="easyui-panel" border="false" >
<div style="margin-top: 10px">
	<p>其他工作量参数</p>
	<div style="border-top:1px solid #efefef;">
		<div style="margin-top: 5px;">
			<c:forEach items="${oworkloadList}" var="sc">
				<div style="float: left;width:30%;margin-top:15px;">
					<div class="name" style="width: 165px;"><span>${sc.otype}:</span></div>
					<input class="easyui-textbox" data-options="required:true"  id="oworkload${sc.oid}"  value="${sc.para}"/>
				</div>

			</c:forEach>

		</div>

	</div>
</div>

</div>


<div style="margin-top: 20px;">
	<a href="javascript:void(0)" onclick="addFormula()" class="easyui-linkbutton" style="width:100px;outline: none;" iconCls="icon-add">修改</a>
</div>

</body>

<script type="text/javascript">
	function checkRate(input) {
		var re = /^[0-9]+.?[0-9]*/;//判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
		if (!re.test(input)) {
			alert("请输入数字");
			return false;
		}
	}
function addFormula(){
	
	var bvalue=$("#bvalue").val();
	var yvalue=$("#yvalue").val();
	var rvalue1=$("#rvalue1").val();
	var rvalue2=$("#rvalue2").val();
	var rvalue3=$("#rvalue3").val();
	var rvalue4=$("#rvalue4").val();
	var xfvalue1=$("#xfvalue1").val();
	var xfvalue2=$("#xfvalue2").val();
	
	/*var kvalue1=$("#kvalue1").val();
	var kvalue2=$("#kvalue2").val();*/
      var len=0;
	var re = /^[0-9]+.?[0-9]*/;//判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
	if (isNaN(bvalue)) {
		len=len+1;
	}
	if (isNaN(yvalue)) {
		len=len+1;
	}
	if (isNaN(rvalue1)) {
		len=len+1;
	}
	if (isNaN(rvalue2)) {
		len=len+1;
	}
	if (isNaN(rvalue3)) {
		len=len+1;
	}
	if (isNaN(rvalue4)) {
		len=len+1;
	}
	if (isNaN(xfvalue1)) {
		len=len+1;
	}
	if (isNaN(xfvalue2)) {
		len=len+1;
	}


	if(bvalue==""||yvalue==""||rvalue1==""||rvalue2==""||rvalue3==""||rvalue4==""||xfvalue1==""||xfvalue2==""){
		alert("教学工作量参数填写不完整!");
		return false;
	}
	var scientificMap =new Map();
	var oworkloadMap  =new Map();
	var reportList = JSON.parse('<%=scientificListsStr%>');
	for(var i=0;i<reportList.length;i++){
		//var scientificName = reportList[i].scientificName;
        var scientificId=reportList[i].scientificId;
		if($('#scientific'+scientificId).val()==""){
			alert("科研工作量参数填写不完整!");
			return false;
		}
		if (isNaN($('#scientific'+scientificId).val())) {
			len=len+1;
		}
		scientificMap.set(scientificId,$('#scientific'+scientificId).val());
	}
	var reportList1 = JSON.parse('<%=oworkloadListsStr%>');
	for(var i=0;i<reportList1.length;i++){
		//var oName = reportList1[i].otype;
		var oId=reportList1[i].oid;
		if($('#oworkload'+oId).val()==""){
			alert("其他工作量参数填写不完整!");
			return false;
		}

		if (isNaN($('#oworkload'+oId).val())) {
			len=len+1;
		}
		oworkloadMap.set(oId,$('#oworkload'+oId).val());

	}
	var obj1 = Object.create(null);
	var iterator1 = scientificMap.keys();
	for (var j = 0; j < scientificMap.size; j++) {
		var key1 = iterator1.next().value;
		obj1[key1] = scientificMap.get(key1);
	}
	var obj = Object.create(null);
	var iterator = oworkloadMap.keys();
	for (var i = 0; i < oworkloadMap.size; i++) {
		var key = iterator.next().value;
		obj[key] = oworkloadMap.get(key);
	}

	if(len>0){
		alert("参数填写不正确，参数均需为数字！")
	}
	var data={
		bvalue:bvalue,
		yvalue:yvalue,
		rvalue1:rvalue1,
		rvalue2:rvalue2,
		rvalue3:rvalue3,
		rvalue4:rvalue4,
		xfvalue1:xfvalue1,
		xfvalue2:xfvalue2,
		scientificMapStr:JSON.stringify(obj1),
		oworkloadMapStr:JSON.stringify(obj)
		};
	 $.ajax({
		url:'${pageContext.request.contextPath}/formula/insert.action',
		type:'post',
		data:data,
		success:function(e){
			alert(e.msg);
			window.location="${pageContext.request.contextPath}/formula/addFormulaView.action";
		}
	}) 
	
	
}


</script>
</html>