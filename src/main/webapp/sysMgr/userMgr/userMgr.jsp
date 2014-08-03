<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>

<style>
td {
	font-size: 12px;
}
</style>
<script type="text/javascript">
	/**
	 * @author 孙宇
	 * 
	 * @requires jQuery
	 * 
	 * 将form表单元素的值序列化成对象
	 * 
	 * @returns object
	 */
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

	function user_searchFun() {
		/* 	$('#sysMgr_userMgr_datagrid').datagrid('load', {
				name : $('#sysMgr_userMgr_layout input[name=name]').val()
			}); */
		$('#sysMgr_userMgr_datagrid').datagrid('load', serializeObject($('#sysMgr_userMgr_searchForm')));
	}
	function user_clearFun() {
		$('#sysMgr_userMgr_layout input[name=name]').val('');
		$('#sysMgr_userMgr_datagrid').datagrid('load', {});
	}

	function saveUser() {

		$('#sysMgr_userMgr_addform').form('submit', {

			url : '${xymz}/user_save.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					$('#sysMgr_userMgr_addUserDialog').dialog('close');
					$('#sysMgr_userMgr_datagrid').datagrid('load');
					$.messager.alert('提示', "添加成功,默认密码1234");
				} else {
					$.messager.alert('提示', obj.msg);
				}

			}
		});

	}

	function delUser() {
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'user_delete.action'
		}, function(r) {
			if (r.success) {
				var row = $('#sysMgr_userMgr_datagrid').datagrid('getSelected');
				if (row) {
					$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
						if (r) {

							$.post('${xymz}/user_delete.action', {
								'id' : row.id
							}, function(data) {

								$.messager.show({
									title : '提示',
									msg : data.msg,
								});

								$('#sysMgr_userMgr_datagrid').datagrid('load');

							}, 'json');
						}

					});
				} else {
					$.messager.alert('提示', '请选择要删除的用户');
				}

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	function editUser() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'user_edit.action'
		}, function(r) {
			if (r.success) {
				var rows = $('#sysMgr_userMgr_datagrid').datagrid('getSelected');
				if (rows != null) {

					//初始化 部门
					$('#sysMgr_userMgr_editDeparSelect').combotree({
						url : '${xymz}/department_departmentTree.action',
						required : true,
						onChange : function() {

							console.info('还没点击就执行了???');
							//当选择某个部门时,查询此部门下所有的岗位
							var deparId = $('#sysMgr_userMgr_editDeparSelect').combotree('getValue');

							if (deparId) {
								$.post('${xymz}/role_roleListByDeperId.action', {
									'departmentId' : deparId
								}, function(r) {
									//如果有岗位,就初始化combobox
									if (r.length != 0) {
										$('#sysMgr_userMgr_editRoleSelect').combobox({
											url : '${xymz}/role_roleListByDeperId.action?departmentId=' + deparId,
											required : true,
											valueField : 'id',
											multiple : true,//支持多选
											textField : 'text',
										});
									}
								}, 'json');

							}

						}
					});

					//初始化岗位
					$('#sysMgr_userMgr_editRoleSelect').combobox({
						url : '${xymz}/role_roleListByDeperId.action?departmentId=' + rows.departmentId,
						required : true,
						valueField : 'id',
						multiple : true,//支持多选
						textField : 'text',

					});

					$('#sysMgr_userMgr_editUserform').form('load', rows);
					var editDialog = $('#sysMgr_userMgr_editUserDialog');
					editDialog.dialog('open');
				} else {
					$.messager.alert('提示', '请选择要编辑的用户!');
				}

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	function user_DepartmentTree() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'user_save.action'
		}, function(r) {
			if (r.success) {

				$('#sysMgr_userMgr_addUserDialog input').val('');
				$.post('${xymz}/role_roleList.action', {}, function(data) {
					if (data.success == false) {
						$.messager.alert('提示', data.msg);
					} else {
						//初始化部门选择框数据
						$('#sysMgr_userMgr_addDeparSelect').combotree({
							url : '${xymz}/department_departmentTree.action',
							required : true,
							onChange : function() {

								//当选择某个部门时,查询此部门下所有的岗位
								var deparId = $('#sysMgr_userMgr_addDeparSelect').combotree('getValue');

								if (deparId) {
									$.post('${xymz}/role_roleListByDeperId.action', {
										'departmentId' : deparId
									},

									function(r) {

										//如果有岗位,就初始化combobox
										if (r.length != 0) {
											$('#sysMgr_userMgr_addRoleSelect').combobox({
												url : '${xymz}/role_roleListByDeperId.action?departmentId=' + deparId,
												required : true,
												valueField : 'id',
												multiple : true,//支持多选
												textField : 'text',
											});
										} else {
											//如果该部门没有岗位,就提示用户
											$('#sysMgr_userMgr_addRoleSelect').combobox({
												url : '${xymz}/role_roleListByDeperId.action?departmentId=' + 0,
											/* 											required : true,
											 valueField : 'id',
											 multiple : true,//支持多选
											 textField:'text', */
											});
											//alert('抱歉! 此部门没有岗位,请先给该部门新增岗位!');

											//$('.combobox-item combobox-item-selected').remove();
										}

									}, 'json');

								}

							}
						});

						//不管如何,先初始化岗位,以防止岗位空白提交
						$('#sysMgr_userMgr_addRoleSelect').combobox({});
						//页面加载完毕后,焦点自动在username的input 表单上,获取焦点

						$('#sysMgr_userMgr_addUserDialog').dialog('open');

					}
				}, 'json');

			} else {
				$.messager.alert('提示', r.msg);
			}
		}, 'json');

	}

	$('#sysMgr_userMgr_datagrid').datagrid({
		url : '${xymz}/user_dataGrid.action',
		border : false,
		fit : true,
		pagination : true,
		striped : true,
		//rownumbers:true, 编号
		pageSize : 20, //初始化现在多少条数据
		//sortName:'email',
		//sortOrder:'desc', 默认是asc
		fitColumns : true,
		checkOnSelect : false,
		selectOnCheck : true,
		idField : 'id',
		singleSelect : true,
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			hidden : true
		}, {
			field : 'name',
			title : '姓名',
			width : 100,
			sortable : true
		//可以点击title排序
		}, {
			field : 'gender',
			title : '性别',
			width : 100,
			sortable : true
		},

		{
			field : 'phoneNumber',
			title : '电话',
			width : 100,
			sortable : true

		}, {
			field : 'email',
			title : '邮箱地址',
			width : 200,
			sortable : true,
			//鼠标焦点到此字段时,自动弹出信息
			formatter : function(value, row, index) {
				return '<span title="'+value+'">' + value + '</span>';
			}

		}, {
			field : 'department',
			title : '部门',
			width : 100,
			sortable : true
		}, {
			field : 'roles',
			title : '岗位',
			width : 100,
			sortable : true
		}, {
			field : 'departmentId',
			title : '部门ID',
			width : 100,
			hidden : true
		}, {
			field : 'roleIds',
			title : '岗位',
			width : 100,
			hidden : true
		}

		] ],
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				user_DepartmentTree();
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				delUser();
			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				editUser();
			}
		}, '-', {
			text : '设置密码',
			iconCls : 'icon-key_go',
			handler : function() {
				$.post('${xymz}/privilege_approve.action', {
					'url' : 'user_save.action'
				}, function(r) {

					if (!r.success) {
						$.messager.alert('提示', r.msg);
					} else {

						var rows = $('#sysMgr_userMgr_datagrid').datagrid('getSelected');
						if (rows) {
							$.messager.prompt('提示信息', '请输入要设置的密码 :', function(r) {
								if (r) {
									var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
									if (reg.test(r)) {
										$.messager.alert('警告', '<font color="red">注意:不能用汉字做密码</font>');
									} else {
										$.post('${xymz}/user_editPassword.action', {
											'id' : rows.id,
											'password' : r,
										}, function(data) {
											$.messager.show({
												title : '提示',
												msg : data.msg,
											});

										}, 'json');

									}

								}
							});

						} else {
							$.messager.alert('提示', '请先选择要修改密码的用户');

						}

					}

				}, 'json');

			}
		} ]

	});
</script>

<div id="sysMgr_userMgr_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north'" style="height: 50px;">
		<form id="sysMgr_userMgr_searchForm">
			检索用户名称(可模糊查询)：<input name="name" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
				onclick="user_searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="user_clearFun();">清空</a>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="sysMgr_userMgr_datagrid"></table>
	</div>
</div>


<!-- 新增 -->
<div id="sysMgr_userMgr_addUserDialog" class="easyui-dialog" title="添加用户" style="width:500px;height:300px;"
	data-options="closed:true,iconCls:'icon-add',modal:true,title:'添加用户',
       buttons:[{
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
				saveUser();
				}
					
			}]">

	<form id="sysMgr_userMgr_addform" action="" method="post">
		<table>
			<tr>
				<td>姓名:</td>
				<td><input name='name' class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>性别:</td>
				<td><select name="gender">
						<option value="男">男</option>
						<option value="女">女</option>
				</select></td>
			</tr>
			<tr>
				<td>电话号码:</td>
				<td><input name='phoneNumber' class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>邮箱:</td>
				<td><input name='email' class="easyui-validatebox" data-options="required:true,validType:'email'"></td>
			</tr>
			<tr>
				<td>部门:</td>
				<td><input id="sysMgr_userMgr_addDeparSelect"  name="departmentId"
					value="">
				</td>
				<td>岗位:</td>
				<td><input id="sysMgr_userMgr_addRoleSelect"  name="roleIds"
					value=" ">
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 编辑 -->

<div id="sysMgr_userMgr_editUserDialog" class="easyui-dialog" title="编辑用户" style="width:500px;height:300px;"
	data-options="closed:true,iconCls:'icon-edit',modal:true,title:'编辑用户',
       buttons:[{
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(){
							$('#sysMgr_userMgr_editUserform').form('submit', {

							url : '${xymz}/user_edit.action',
							success : function(data) {
								var obj = jQuery.parseJSON(data);
								if(obj.success){
									$('#sysMgr_userMgr_editUserDialog').dialog('close');
									$('#sysMgr_userMgr_datagrid').datagrid('load');
								
									$.messager.show({
										title : '提示',
										msg : obj.msg,
									});
								}else{
									$.messager.alert('提示',obj.msg);
								}
						
				
							}
						});
				}
					
			}]">

	<form id="sysMgr_userMgr_editUserform" action="" method="post">
		<input type="text" name="id" hidden="true">
		<table>
			<tr>

				<td>姓名:</td>
				<td><input name='name' class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>性别:</td>
				<td><select name="gender">
						<option value="男">男</option>
						<option value="女">女</option>
				</select></td>
			</tr>
			<tr>
				<td>电话号码:</td>
				<td><input name='phoneNumber' class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>邮箱:</td>
				<td><input name='email' class="easyui-validatebox" data-options="required:true,validType:'email'"></td>
			</tr>
			<tr>
				<td>部门:</td>
				<td><input id="sysMgr_userMgr_editDeparSelect" name="departmentId" value="" 
					>
				</td>
				<td>岗位:</td>
				<td><input id="sysMgr_userMgr_editRoleSelect" name="roleIds" value="">
				</td>
			</tr>
		</table>
	</form>
</div>