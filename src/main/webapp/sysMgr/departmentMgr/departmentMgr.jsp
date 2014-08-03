<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>

<style>
td {
	font-size: 12px;
}
</style>

<div id="sysMgr_departmentMgr_layout" class="easyui-layout" data-options="fit:true,border:false">

	<!-- 查询 -->
	<div id="sysMgr_departmentMgr_layout_from" data-options="region:'north'" style="height: 50px; ">
		<form id="sysMgr_departmentMgr_searchForm">
			检索部门名称(可模糊查询)：<input name="text" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="depart_SearchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="depart_ClearFun();">清空</a>
		</form>
	</div>

	<!-- 表格 -->
	<div data-options="region:'center'" style="height: 30px;width: 500px">
		<table id="sysMgr_departmentMgr_datagrid"></table>
	</div>

</div>

<!-- 添加 -->
<div id="sysMgr_department_add" class="easyui-dialog" title="新增部门" style="width:400px;height:250px;" data-options="iconCls:'icon-add',modal:true,closed:true,buttons:[{
				text:'保存',
				iconCls:'icon-add',
				handler:function(r){
					addDepart();
				}
			}]">

	<form id="sysMgr_departmentMgr_addDepart" action="" method="post">
		<table>
			<tr>
				<td style="width:150px">部门名称:</td>
				<td style="width:200px"><input name="text" class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>部门电话:</td>
				<td><input name="tel" type="text"></td>
			</tr>
			<tr>
				<td>部门传真:</td>
				<td><input name="fax" type="text"></td>
			</tr>
			<tr>
				<td>上级部门:</td>
				<td><input id="sysMgr_department_add_select" name="id" value="无上级部门">
				</td>
			</tr>

		</table>
	</form>

</div>

<!-- 编辑 -->
<div id="sysMgr_department_editDialog" class="easyui-dialog" title="编辑部门" style="width:400px;height:250px;" data-options="iconCls:'icon-edit',modal:true,closed:true,buttons:[{
				text:'保存',
				handler:function(r){
					editDepart();
				}
			}]">

	<form id="sysMgr_departmentMgr_editForm" action="" method="post">
		<input type="text" name="id" hidden="true">
		<table>


			<tr>
				<td style="width:150px">部门名称:</td>
				<td style="width:200px"><input name="text" type="text" class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>部门电话:</td>
				<td><input name="tel" type="text"></td>
			</tr>
			<tr>
				<td>部门传真:</td>
				<td><input name="fax" type="text"></td>
			</tr>
			<tr>
				<td>上级部门:</td>
				<td><input id="sysMgr_department_edit_select" name="parentId" value="" checked="checked" class="easyui-validatebox" data-options="required:true">
				</td>
			</tr>

		</table>

	</form>

</div>



<script type="text/javascript">
	function depart_SearchFun() {
		$('#sysMgr_departmentMgr_datagrid').datagrid('load', serializeObject($('#sysMgr_departmentMgr_searchForm')));
	}
	function depart_ClearFun() {
		$('#sysMgr_departmentMgr_searchForm input[name=name]').val('');
		$('#sysMgr_departmentMgr_datagrid').datagrid('load', {});
	}

	serializeObject = function(form) {
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		});
		return o;
	};

	function depart_DepartmentTree() {
		//当用户点击添加部门的时候,先判断是否有这个权限,如果没有给用户一个提示
		$.post('${xymz}/privilege_approve.action', {
			"url" : 'department_save.action'
		}, function(r) {
			if (r.success) {

				$('#sysMgr_departmentMgr_addDepart input').val('');
				$('#sysMgr_department_add_select').combotree({
					url : '${xymz}/department_departmentTree.action',

				});

				$('#sysMgr_department_add').dialog('open');
			} else {
				$.messager.alert('提示', r.msg);
			}

		}, 'json');

	}

	function addDepart() {
		$('#sysMgr_departmentMgr_addDepart').form('submit', {
			url : '${xymz}/department_save.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				$('#sysMgr_department_add').dialog('close');
				/* 		$('#sysMgr_departmentMgr_datagrid').datagrid('insertRow', {
							index : 0,
							row : obj.obj

						}); */
				$('#sysMgr_departmentMgr_datagrid').datagrid('load');
				$.messager.show({
					title : '提示',
					msg : obj.msg,
				});

			}
		});

		/* 	$.post('${xymz}/department_save.action', $(
					"#sysMgr_departmentMgr_addDepart").serialize(), function(r) {

				var obj = jQuery.parseJSON(r);

				$('#sysMgr_department_add').dialog('close');
				$('#sysMgr_departmentMgr_datagrid').datagrid('insertRow', {
					index : 0,
					row : obj.obj

				});
				$.messager.show({
					title : '提示',
					msg : obj.msg,
				});

			}); */
	}

	function delDepartment() {
		//判断是否有权限
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'department_delete.action'

		}, function(r) {

			if (r.success) {
				var departmentMgr = $('#sysMgr_departmentMgr_datagrid');
				//获取所选择的行
				var rows = departmentMgr.datagrid('getSelections');
				var ids0 = [];

				if (rows.length > 0) {
					$.messager.confirm('确认', '您确认要删除所选部门吗？', function(r) {
						if (r) {

							var ids = [];

							for ( var i = 0; i < rows.length; i++) {
								var id = rows[i].id;
								ids.push(id);
							}
							$.post('${xymz}/department_delete.action', {
								'ids' : ids.join(',')
							}, function(r) {

								departmentMgr.datagrid('reload');
								departmentMgr.datagrid('unselectAll');

								var obj = jQuery.parseJSON(r);
								if (obj.success) {
									$.messager.show({
										title : '提示',
										msg : obj.msg,
									});
								} else {
									$.messager.alert('提示', obj.msg);
								}

							});
						}
					});

				} else {
					$.messager.alert('提示', '请选择要删除的部门!');
				}
			} else {
				$.messager.alert('提示', r.msg);
			}

		}, 'json');

	}

	function editDepartment() {
		//判断是否有权限
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'department_edit.action'
		}, function(r) {
			if (r.success) {
				var depMgr = $('#sysMgr_departmentMgr_datagrid');

				var row = depMgr.datagrid('getSelected');

				if (row != null) {
					var editDialog = $('#sysMgr_department_editDialog');
					editDialog.dialog('open');

					$('#sysMgr_department_edit_select').combotree({
						url : '${xymz}/department_departmentTree.action',
						onChange : function() {

							var id = $('#sysMgr_departmentMgr_editForm input[name=id]').val();
							var parentId = $('#sysMgr_department_edit_select').combotree('getValue');

							if (id == parentId) {
								$.messager.alert('提示', '<font color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上级部门不能设置为本部门,请重新选择</font>');
								$('#sysMgr_department_edit_select').combotree('clear');
							}

							if (parentId) {
								$.post('${xymz}/department_isParentOk.action', {
									'id' : id,
									'parentId' : parentId
								}, function(r) {
									if (!r.success) {
										$.messager.alert('提示', r.msg);
										$('#sysMgr_department_edit_select').combotree('clear');
									}

								}, 'json');
							}

						}
					});

					$('#sysMgr_departmentMgr_editForm').form('load', row);

				} else {
					$.messager.alert('提示', '请选择要编辑的部门!');
				}

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	function editDepart() {

		var editDpt = $('#sysMgr_department_editDatagrid');

		var data = $('#sysMgr_departmentMgr_editForm').serialize();
		$.post('${xymz}/department_edit.action', data, function(r) {

			if (r.success) {
				$('#sysMgr_department_editDialog').dialog('close');

				//编辑保存好数据后,重新加载表格列表数据	
				$('#sysMgr_departmentMgr_datagrid').datagrid('load');
				$.messager.show({
					title : '提示',
					msg : r.msg,
				});

			} else {
				$.messager.alert('提示', r.msg);
			}

		}, 'json');

		/* 		
		 点击按钮没反映,没有提交
		 $('#sysMgr_departmentMgr_editForm').form('submit', {
		 url : '${xymz}/department_edit.action',
		 success : function(data) {
		 $('#sysMgr_department_editDialog').dialog('close');
		 $('#sysMgr_departmentMgr_datagrid').datagrid('load');
		 var obj = jQuery.parseJSON(data);
		 $.messager.show({
		 title : '提示',
		 msg : obj.msg,
		 }, 'json');

		 }

		 }); */

	}

	$('#sysMgr_departmentMgr_datagrid').datagrid({
		url : '${xymz}/department_dataGrid.action',
		border : false,
		fit : true,
		pagination : true,
		striped : true,
		pageSize : 20,
		idField : 'id',
		//fitColumns : true, 全屏 
		rownumbers : true,
		checkOnSelect : true,
		selectOnCheck : false,
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			//checkbox : true,
			hidden : true
		}, {
			field : 'text',
			title : '部门名称',
			width : 100
		}, {
			field : 'tel',
			title : '部门电话',
			width : 100,
		}, {
			field : 'fax',
			title : '传真',
			width : 100,
		}, {
			field : 'parent',
			title : '上级部门',
			width : 100,

		}, {
			field : 'parentId',
			title : '上级部门ID',
			width : 100,
			hidden : true,

		}

		] ],
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {

				depart_DepartmentTree();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				delDepartment();

			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				editDepartment();
			}
		} ]
	});

/* */
</script>
