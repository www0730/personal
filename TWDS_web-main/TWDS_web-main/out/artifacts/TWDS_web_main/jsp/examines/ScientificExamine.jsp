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

.editSpan1{
	position: relative;
	float:left;
	top: 6px;
	left: 30px;

}
.name{
	width:100px;
	text-align: right;
	display: inline-block;
	margin-right: 10px;
	
}
</style>
</head>

<%--科研工作量审核--%>
<body>

	<div id="tb">

				 	<div class="editSpan">
						<a class="easyui-linkbutton"  style="background: #0969A2;color: white;" iconCls="icon-ok" onclick="examSuccess()">通过</a>
						<a class="easyui-linkbutton" style="background: #0969A2;color: white;" iconCls="icon-no" onclick="examFail()">不通过</a>
					</div>
				
			</div>




	<table id="dg" class="easyui-datagrid" border="false" data-options="onLoadSuccess: onLoadSuccess"  fitColumns="true" emptyMsg="暂无数据！" singleSelect="true">
						<thead>
						<th field="toid" width="50" align="center">申报编号</th>
						<th field="name" width="50" align="center">申报老师</th>
						<th field="oType" width="50" align="center">类型</th>
						<th field="toName" width="50" align="center">名称</th>
						<th field="toTime" width="50" align="center">时间</th>
						<th field="totalWorkload" width="50" align="center">工作量</th>
						<th field="state" width="50" align="center">审核状态</th>
						<th field="content" width="50" align="center">审核意见</th>
						</thead>
					<c:if test="${list.size()==0}">
								<div class="easyui-window" title="提示" style="width:200px;height:100px;text-align: center;">
									<span>暂无数据！</span>
								</div>
						</c:if>
		<c:if test="${list.size()!=0}">
			<c:forEach items="${list}" var="e">
				<tr>
					<td  >${e.toid}</td>
					<td  >${e.user.name}</td>
					<td>${e.scientific.scientificName }</td>
					<td>${e.toname}</td>


					<td>${e.time}</td>
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

				</tr>
			</c:forEach>
		</c:if>



	</table>


	<div class="easyui-window" id="ew" title="审核" style="width:420px;height:250px; ">

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


		function warn(){
			$("#warn").window("close");
		}
		$(function(){
			$("#other").window("close");

		});

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


		function onLoadSuccess(data){
		$('#dg').datagrid('mergeCells',{ 
			index:'0',                
			field:'examine',               	
			rowspan:data.rows.length 
		}); 
	}
	function examSuccess(){
		var row=$("#dg").datagrid("getSelected");

		if(row==null||row==""){
			$("#warn").window("open");
		}else  if(row.length>1){
			alert("请选择一行数据")
		} else  if(row.state=='审核通过'||row.state=='审核不通过'){
			alert("已审核，无需重复审核！")
		}else{
			$("#ew").window("open");
			var html='<div style="margin-top: 30px;">'+
					'<div class="name"><span">申报编号:</span></div>'+
					'<input type="text" class="easyui-validatebox"  readonly="readonly"  style="width:70px;text-align:center;font-weight: bold;" id="toid" value="'+(row.toid)+'"/>'+
					'</div>'+
					'<div style="margin-top: 10px;">'+
					'<div class="name"><span >审核意见:</span></div>'+
					'<input type="text"  style="width: 250px;height: 79px;text-align: center;"class="easyui-validatebox" data-options="required:true"  id="content" value=""/>'+
					'</div>'+

					'<div style="margin-top: 10px;text-align:center; ">'+
					'<a href="javascript:void(0)" onclick="examupdate1()" ><button style="width:150px;"  type="sumbit">提交</button></a>'+
					'</div>';

			$("#ew").html(html);
		}

	}
		function examFail(){
			var row=$("#dg").datagrid("getSelected");
			if(row==null||row==""){
				$("#warn").window("open");
			}else  if(row.length>1){
				alert("请选择一行数据")
			}else if(row.state=='审核通过'||row.state=='审核不通过'){
				alert("已审核，无需重复审核！")
			}else{
				$("#ew").window("open");

				var html='<div style="margin-top: 30px;">'+
						'<div class="name"><span">申报编号:</span></div>'+
						'<input type="text" class="easyui-validatebox"  readonly="readonly"  style="width:70px;text-align:center;font-weight: bold;" id="toid" value="'+(row.toid)+'"/>'+
						'</div>'+
						'<div style="margin-top: 10px;">'+
						'<div class="name"><span >审核意见:</span></div>'+
						'<input type="text"  style="width: 250px;height: 79px;text-align: center;"class="easyui-validatebox" data-options="required:true"  id="content" value=""/>'+
						'</div>'+

						'<div style="margin-top: 10px;text-align:center; ">'+
						'<a href="javascript:void(0)" onclick="examupdate2()" ><button style="width:150px;"  type="sumbit">提交</button></a>'+
						'</div>';

				$("#ew").html(html);
			}

		}


		function examupdate1(){
			var rows = $('#dg').datagrid('getSelections');
			var toid=$("#toid").val();
			var content=$("#content").val();
	    	var data={toid:toid,content:content,state:'2'}
			$.ajax({
				url:'${pageContext.request.contextPath}/examines/updateScientific.action',
				type:'post',
				data:data,
				success:function(e){
					alert(e.msg);
					if(e.success){
						window.location="${pageContext.request.contextPath}/examines/selectScientificWork.action";
					}
				}
			})

		}


	
	function examupdate2(){
		var rows = $('#dg').datagrid('getSelections');
		var toid=$("#toid").val();
		var content=$("#content").val();
		var data={toid:toid,content:content,state:'3'}
		$.ajax({
			url:'${pageContext.request.contextPath}/examines/updateScientific.action',
			type:'post',
			data:data,
			success:function(e){
				alert(e.msg);
				if(e.success){
					window.location="${pageContext.request.contextPath}/examines/selectScientificWork.action";
				}
			}
		})
	}
	
	 //通过学院搜索
	 
	function searchByCid(){
		var cId=$("#selectCollege option:selected").val();
		window.location="${pageContext.request.contextPath}/examines/selectAllTCoursesByCid.action?cid="+cId;
					
		
	 }



	</script>

</html>