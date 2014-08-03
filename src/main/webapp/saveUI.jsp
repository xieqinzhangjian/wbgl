<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../../../public/public.jspf"%>
<script type="text/javascript" src="${xymz}/jslib/kindeditor-4.1.10/kindeditor.js"></script>
<script type="text/javascript" src="${xymz}/jslib/kindeditor-4.1.10/lang/zh_CN.js"></script>
</head>
<body>


	<div id="problem_add_panel" class="easyui-panel" title="新增事务" style="width:900px;height:650px;padding:10px;background:#fafafa;" data-options="iconCls:'icon-save',closable:true, modal:true,  
                collapsible:true,minimizable:true,maximizable:true">
		<form id="problem_add_form" action="" method="post">


<textarea id="editor_id" name="content" style="width:700px;height:300px;">
								&lt;strong&gt;HTML内容&lt;/strong&gt;
						</textarea>
		</form>
	</div>
	<script type="text/javascript">
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_id');
		});
	</script>


</body>
</html>