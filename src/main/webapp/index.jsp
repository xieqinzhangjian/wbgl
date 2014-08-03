<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="public/public.jspf"%>
<script type="text/javascript" src="${xymz}/jslib/jquery-easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="${xymz}/jslib/jquery-easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${xymz}/jslib/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${xymz}/jslib/jquery-easyui-1.3.6/themes/bootstrap/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="${xymz}/jslib/jquery-easyui-1.3.6/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="${xymz}/jslib/syUtil.js"></script>


<!-- ueditor1_3_6-utf8-jsp -->
<script type="text/javascript" charset="utf-8" src="${xymz}/jslib/ueditor1_3_6-utf8-jsp/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${xymz}/jslib/ueditor1_3_6-utf8-jsp/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${xymz}/jslib/ueditor1_3_6-utf8-jsp/lang/zh-cn/zh-cn.js"></script>

<!--highcharts  -->
<script type="text/javascript" src="${xymz}/jslib/Highcharts-3.0.10/js/highcharts.js"></script>
<script type="text/javascript" src="${xymz}/jslib/My97DatePicker/WdatePicker.js"></script>



<!-- validator -->

<script type="text/javascript" src="${xymz}/jslib/validator-0.2.1/jquery.validator.js"></script>
<script type="text/javascript" src="${xymz}/jslib/validator-0.2.1/local/zh_CN.js"></script>
<link rel="stylesheet" href="${xymz}/jslib/validator-0.2.1/jquery.validator.css" type="text/css"></link>


<!-- 知识库,和知识库分页 -->
<link rel="stylesheet" href="${xymz}/jslib/knowledge/knowledge.css" type="text/css"></link>
<link rel="stylesheet" href="${xymz}/jslib/knowledge/pageViercss.css" type="text/css"></link>




<script type="text/javascript" src="${xymz}/jslib/kindeditor-4.1.10/kindeditor.js"></script>
<script type="text/javascript" src="${xymz}/jslib/kindeditor-4.1.10/lang/zh_CN.js"></script>




<body class="easyui-layout" >

	<div data-options="region:'west'" style="width:150px">

	
		<%@ include file="layout/west.jsp"%>
	</div>

	<div data-options="region:'center'">

		<%@ include file="layout/center.jsp"%>
	</div>

	<%@ include file="/layout/login.jsp"%>

</body>
</html>
