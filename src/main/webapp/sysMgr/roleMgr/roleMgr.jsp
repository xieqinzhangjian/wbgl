<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>


<style>
td {
	font-size: 12px;
}
</style>

<div id="sysMgr_roleMgr_layout" class="easyui-layout" data-options="fit:true,border:false">

	<!-- 查询 -->
	<div id="sysMgr_role_layout_from" data-options="region:'north'" style="height: 50px; ">
		<form id="sysMgr_roleMgr_searchForm">
			检索岗位名称(可模糊查询)：<input name="text" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="role_searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="role_clearFun();">清空</a>
		</form>
	</div>

	<!-- 表格 -->
	<div data-options="region:'center'" style="height: 30px">
		<table id="sysMgr_roleMgr_datagrid"></table>
	</div>

</div>

<!-- 添加  -->
<div id="sysMgr_roleMgr_addRoleDialog" class="easyui-dialog" title="新增岗位" style="width:400px;height:250px;" data-options="iconCls:'icon-save',modal:true,closed:true,buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(r){
					addRole();
				}
			}]">

	<form id="sysMgr_roleMgr_addRoleForm" action="" method="post">
		<table>
			<tr>
				<td style="width:150px">岗位名称:</td>
				<td style="width:200px"><input name="text" class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>岗位职责:</td>
				<td><input name="description" type="text"></td>
			</tr>
			<tr>
				<td>所属部门:</td>
				<td><input id="sysMgr_roleMgr__addSelect" name="departmentId" value="" class="easyui-validatebox" data-options="required:true">
				</td>
			</tr>

		</table>
	</form>

</div>

<!-- 编辑 -->
<div id="sysMgr_roleMgr_editDialog" class="easyui-dialog" title="编辑岗位" style="width:400px;height:250px;" data-options="iconCls:'icon-edit',modal:true,closed:true,buttons:[{
				text:'保存',
				iconCls:'icon-edit',
				handler:function(r){
					editSave();
				}
			}]">


	<form id="sysMgr_roleMgr_editForm" method="post">
		<input type="text" name="id" hidden="true">
		<table>
			<tr>
				<td style="width:150px">岗位名称:</td>
				<td style="width:200px"><input name="text" class="easyui-validatebox" data-options="required:true"></td>
			</tr>
			<tr>
				<td>岗位职责:</td>
				<td><input name="description" type="text"></td>
			</tr>
			<tr>
				<td>所属部门:</td>
				<td><input id="sysMgr_roleMgr_editSelect" name="departmentId" value="" class="easyui-validatebox" data-options="required:true">
				</td>
			</tr>

		</table>
	</form>

</div>

<!-- 设置权限 -->
<div id="sysMgr_roleMgr_editPrivilegeDialog">
	<form id="sysMgr_roleMgr_editPrivilegeForm" method="post">
		<ul id="sysMgr_roleMgr_editPrivilegeTree"></ul>


	</form>

</div>



<script type="text/javascript">
	function role_searchFun() {
		$('#sysMgr_roleMgr_datagrid').datagrid('load', serializeObject($('#sysMgr_roleMgr_searchForm')));
	}
	function role_clearFun() {
		$('#sysMgr_roleMgr_searchForm input[name=name]').val('');
		$('#sysMgr_roleMgr_datagrid').datagrid('load', {});
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

	function addRole() {

		/* 	$('#sysMgr_roleMgr_addRoleForm').form('submit', {
		 url : '${xymz}/role_save.action',
		 success : function(data) {
		 var obj = jQuery.parseJSON(data);
		 $('#sysMgr_role_addRoleDialog').dialog('close');
		 $('#sysMgr_roleMgr_datagrid').datagrid('insertRow', {
		 index : 0,
		 row : obj.obj

		 });
		 $.messager.show({
		 title : '提示',
		 msg : obj.msg,
		 });

		 }
		 }); */

		$.post('${xymz}/role_save.action', $("#sysMgr_roleMgr_addRoleForm").serialize(), function(r) {

			if (r.success) {
				$('#sysMgr_roleMgr_addRoleDialog').dialog('close');
				role_clearFun();
				$.messager.show({
					title : '提示',
					msg : r.msg,
				});
			} else {
				$.messager.alert('提示', r.msg);
			}

		}, 'json');
	}

	function role_DepartmentTree() {
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'role_save.action'
		}, function(r) {
			if (r.success) {

				$('#sysMgr_roleMgr_addRoleForm input').val('');
				$.post('${xymz}/department_departmentList.action', {}, function(data) {

					if (data.success == false) {
						$.messager.alert('提示', data.msg);

					} else {
						$('#sysMgr_roleMgr__addSelect').combotree({
							url : '${xymz}/department_departmentTree.action',
						});

						$('#sysMgr_roleMgr_addRoleDialog').dialog('open');
					}
				}, 'json');

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	function delRole() {
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'role_delete.action'
		}, function(r) {
			if (r.success) {

				var roleMgr = $('#sysMgr_roleMgr_datagrid');
				//获取所选择的行
				var row = roleMgr.datagrid('getSelected');
				if (row) {
					$.messager.confirm('确认', '您确认要删除所选岗位吗？', function(r) {
						if (r) {

							var ids = [];
							$.post('${xymz}/role_delete.action', {
								'ids' : row.id
							}, function(r) {

								roleMgr.datagrid('reload');
								roleMgr.datagrid('unselectAll');

								var obj = jQuery.parseJSON(r);
								$.messager.show({
									title : '提示',
									msg : obj.msg,
								});

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

	function editRole() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'role_edit.action'
		}, function(r) {
			if (r.success) {
				var depMgr = $('#sysMgr_roleMgr_datagrid');

				var row = depMgr.datagrid('getSelected');

				if (row != null) {
					var editDialog = $('#sysMgr_roleMgr_editDialog');
					editDialog.dialog('open');

					$('#sysMgr_roleMgr_editSelect').combotree({
						url : '${xymz}/department_departmentTree.action',
					});

					$('#sysMgr_roleMgr_editForm').form('load', row);

				} else {
					$.messager.alert('提示', '请选择要编辑的岗位!');
				}
			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	function editSave() {

		var editDpt = $('#sysMgr_roleMgr_editDatagrid');
		var editForm = $('#sysMgr_roleMgr_editForm');
		var data = $('#sysMgr_roleMgr_editForm').serialize();

		$.post('${xymz}/role_edit.action', data, function(r) {

			if (r.success) {

				$('#sysMgr_roleMgr_editDialog').dialog('close');

				//编辑保存好数据后,重新加载表格列表数据	
				$('#sysMgr_roleMgr_datagrid').datagrid('load');
				$.messager.show({
					title : '提示',
					msg : r.msg,
				});

			} else {
				$.messager.alert('提示', r.msg);
			}

		}, 'json');

		/* 		 $('#sysMgr_roleMgr_editForm').form('submit','enableValidation' ,{
		 url : '${xymz}/role_edit.action',
		 success : function(r) {
		 console.debug('--------------');
		 $('#sysMgr_roleMgr_editDialog').dialog('close');

		 //编辑保存好数据后,重新加载表格列表数据	
		 $('#sysMgr_roleMgr_datagrid').datagrid('load');
		 var obj = jQuery.parseJSON(r);
		 $.messager.show({
		 title : '提示',
		 msg : obj.msg,
		 });
		 }

		 }); */

	}

	function privilege() {
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'privilege_edit.action'
		}, function(r) {
			if (r.success) {

				var depMgr = $('#sysMgr_roleMgr_datagrid');

				var row = depMgr.datagrid('getSelected');

				if (row != null) {

					var clientWidth = document.body.clientWidth;
					var clientHeight = document.body.clientHeight;

					if (clientWidth > 1000) {
						clientWidth = 400;
					}
					if (clientHeight > 700) {
						clientHeight = 600;
					}

					$('#sysMgr_roleMgr_editPrivilegeDialog').dialog({
					iconCls:'icon-database_key',
						title : row.text,
						width : clientWidth,
						height : clientHeight,
						closed : true,
						cache : false,
						maximizable : true,
						//	fit : true,
						//href : 'get_content.php',
						modal : true,

						buttons : [ {
							text : '保存',
							handler : function() {
								var trees = $('#sysMgr_roleMgr_editPrivilegeTree').tree('getChecked');
								var privilegeIds = '';
								if (trees) {
									for ( var i = 0; i < trees.length; i++) {
										if (i != trees.length - 1) {
											privilegeIds += trees[i].id + ',';
										} else {
											privilegeIds += trees[i].id;
										}
									}
								}

								$.post('${xymz}/role_editPrivilege.action', {
									'privilegeIds' : privilegeIds,
									'id' : row.id
								}, function(r) {
									if (r.success) {
										$('#sysMgr_roleMgr_editPrivilegeDialog').dialog('close');
										$.messager.show({
											title : '提示',
											msg : r.msg
										});

										console.info("r.ids===" + r.ids);
									}

								}, 'json');

							}
						} ],

					});

					//获取权限树
					$('#sysMgr_roleMgr_editPrivilegeTree').tree({
						url : '${xymz}/role_privilegeTree.action?id=' + row.id,
						lines : true,//虚线
						checkbox : true,//复选框
					//	onlyLeafCheck:true 如果为true根节点就没有复选框了
					//cascadeCheck:false

					});

					$('#sysMgr_roleMgr_editPrivilegeDialog').dialog('open');

				} else {
					$.messager.alert('提示', '请选择要编辑的岗位!');
				}

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	$('#sysMgr_roleMgr_datagrid').datagrid({
		url : '${xymz}/role_dataGrid.action',
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
		loadMsg : '正在加载..',
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 100,
			//checkbox : true,
			hidden : true
		}, {
			field : 'text',
			title : '岗位名称',
			width : 100
		}, {
			field : 'description',
			title : '岗位职责',
			width : 200
		},

		{
			field : 'department',
			title : '所属部门',
			width : 100,

		}

		] ],
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				$('#sysMgr_roleMgr_addForm input').val('');
				role_DepartmentTree();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				delRole();

			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				editRole();
			}
		}, '-', {
			text : '设置权限',
			iconCls : 'icon-database_key',
			handler : function() {
				privilege();
			}
		} ]
	});
</script>
