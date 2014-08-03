<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>

<div id="hardwareMgr" class="easyui-layout" style="width:100%;height:100%;">
	<div data-options="region:'north'" style="height:60px;">

		<form id="sysMgr_departmentMgr_searchForm">
			检索网吧名称(可模糊查询)：<input name="wbName" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
				onclick="hardware_SearchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
				onclick="hardware_ClearFun();">清空</a>
		</form>

		<div style="position:absolute;bottom:0px;background-color: #F0F0F0; width: 100%;float: right;">
			<a class="easyui-linkbutton" onclick="hardwareAdd()" data-options="iconCls:'icon-add',plain:true">新增</a> <a
				class="easyui-linkbutton" onclick="sellHardware_edit()" data-options="iconCls:'icon-edit',plain:true">修改</a> <a
				class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sellHardware_delete();">删除</a> <a
				class="easyui-linkbutton" data-options="iconCls:'icon-money',plain:true" onclick="openHardware_change_dialog();">硬件更换</a>
		</div>
	</div>

	<div data-options="region:'center',split:true,collapsible:true,border:false">

		<div id="hardware_detail_show"></div>
		<div id="hardware_change_show"></div>
		<div id="wb_info">
			网吧名称:<span id="hardware_hx_wbName"></span><br /> 联系人: <span id="hardware_hx_wbContact"></span><br /> 电话: <span
				id="hardware_hx_wbTel"></span><br /> 地址: <span id="hardware_hx_wbAddr"></span>
		</div>


	</div>

	<div data-options="region:'east',split:true" style="width:300px">
		<table id="hardware_datagrid"></table>
	</div>
</div>
<!-- 新增整机 -->
<div id="hardware_dialog_add">
	<form id="hardware_add_form" action="" method="post">
		网吧名称:<input name="wbName" id="hardware_wbName" class="easyui-validatebox" data-options="required:true" validType="NoNull">
		联系人:<input name="contact" type="text" id="hardware_wbContact"> 联系电话:<input name="wbTel" id="hardware_wbTel" type="text"
			class="easyui-validatebox" data-options="required:true" validType="NoNull"> 网吧地址:<input name="wbAddr"
			id="hardware_wbAddr" type="text" class="easyui-validatebox" data-options="required:true" validType="NoNull"
			style="width: 300px">
		<script id="hardware_editor" name="hardware_detail" type="text/plain"></script>
	</form>
</div>

<div id="hardware_change_dialog">
		<input name="id" id="hardware_change_id" hidden="true">
		<script id="hardware_change_editor" name="hardware_change" type="text/plain"></script>

</div>


<div id="hardware_edit_dialog">
	<form id="hardware_edit_form" action="" method="post">
		<input name="id" id="hardware_edit_id" hidden="true">
		<script id="hardware_edit_editor" name="hardware_detail" type="text/plain"></script>

	</form>

</div>


<script type="text/javascript">
	function hardware_SearchFun() {
		$('#hardware_datagrid').datagrid('load', serializeObject($('#sysMgr_departmentMgr_searchForm')));
	}
	function hardware_ClearFun() {
		$('#sysMgr_departmentMgr_searchForm input[name=name]').val('');
		$('#hardware_datagrid').datagrid('load', {});
	}

	$('#hardware_parts_search_userName').combobox({
		url : '${xymz}/user_list.action',
		valueField : 'id',
		textField : 'name',
	});
	$('#hardware_search_userName').combobox({
		url : '${xymz}/user_list.action',
		valueField : 'id',
		textField : 'name',
	});

	$('#hardware_datagrid').datagrid({
		url : '${xymz}/hardware_dataGrid.action',
		maximizable : true,
		resizable : true,
		pagination : true,
		fit : true,
		singleSelect : true,
		rownumbers : true,
		sortName : 'lastupdateDate',
		sortOrder : 'desc',
		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 50,
			hidden : true,
		}, {
			field : 'wbName',
			title : '网吧名称',
			width : 100
		}, {
			field : 'lastupdateDate',
			title : '最后更新时间',
			width : 100,

			formatter : function(value, row, index) {
				if (value) {
					var newTime = new Date(value);
					var year = newTime.getFullYear();
					var month = newTime.getMonth() + 1;
					var date = newTime.getDate();
					var fullDate = year + '-' + month + '-' + date;

					return fullDate;

				}

			}
		}, ] ],
		onClickRow : function(rowIndex, rowData) {

			showdata(rowData);

		},
		onLoadSuccess : function(r) {

			showdata(r.rows[0]);

		},

	});

	function showdata(rowData) {

		$('#hardware_detail_show').html("");
		$('#hardware_change_show').html("");

		$('#hardware_hx_wbName').text(rowData.wbName);
		$('#hardware_hx_wbContact').text(rowData.contact);
		$('#hardware_hx_wbTel').text(rowData.wbTel);
		$('#hardware_hx_wbAddr').text(rowData.wbAddr);
		$('#hardware_detail_show').html(rowData.hardware_detail);

		//判断硬件更换记录是否有.如果有,就显示
		if ($.trim(rowData.hardware_change) != "") {
			//保存后,清空编辑器内容
			$('#hardware_change_show').html(rowData.hardware_change);
		}

	}

	function hardwareAdd() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'hardware_save.action'
		}, function(r) {
			if (!r.success) {
				$.messager.alert('提示', r.msg);
				return;
			} else {
				$('#hardware_dialog_add').dialog('open');

				$('#hardware_add_form input').val("");

				$.post('${xymz}/hardwareInfo_queryBySellHardware.action', {
					'id' : 10000,
				}, function(r) {

					initUeditor("hardware_editor", UE.getEditor);

					setTimeout(function() {
						UE.getEditor('hardware_editor').setContent(r.obj.hardware_detail, false);
					}, 300);
				}, 'json');

			}
		}, 'json');

	}

	$('#hardware_dialog_add').dialog({
		title : '新增整机',
		width : 780,
		height : 600,
		closed : true,
		cache : false,
		modal : true,
		fit : true,

		onClose : function() {
			//UE.getEditor('hardware_detail_show_editor').execCommand('cleardoc');
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function(r) {

				var hardware_wbName = $.trim($('#hardware_wbName').val());
				var hardware_wbTel = $.trim($('#hardware_wbTel').val());
				var hardware_wbAddr = $.trim($('#hardware_wbAddr').val());

				if ("" == hardware_wbName || "" == hardware_wbTel || "" == hardware_wbAddr) {
					$.messager.alert('警告', '网吧信息不完整,请检查');

					return;
				}

				$('#hardware_add_form').form('submit', {
					url : '${xymz}/hardware_save.action',
					success : function(data) {
						$('#hardware_dialog_add').dialog('close');
						$('#hardware_datagrid').datagrid('load');
					}
				});

			},
		} ]

	});

	function hardware_edit_dialog(data) {

		$('#hardware_edit_dialog').dialog({
			title : data.wbName,
			width : 780,
			height : 600,
			cache : false,
			modal : true,
			fit : true,
			closed:true,
			onClose : function() {
				//	UE.getEditor('hardware_edit_editor').execCommand('cleardoc');
			},
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function(r) {
					$('#hardware_edit_form').form('submit', {
						url : '${xymz}/hardware_edit.action',
						success : function(data) {
							$('#hardware_edit_dialog').dialog('close');
							$('#hardware_datagrid').datagrid('load');
							
							showdata(r.obj);
							
						}
					});

				},
			} ]

		});

	}
	//查询网吧信息并写入表单,整机的
	$(function() {
		$('#hardware_wbName').change(function() {
			var wbName = $('#hardware_wbName').val();
			$.post('${xymz}/wb_queryByName.action', {
				'name' : wbName
			}, function(data) {
				if (data) {
					$('#hardware_wbName').val(data.name);
					$('#hardware_wbContact').val(data.contact);
					$('#hardware_wbTel').val(data.wbTel);
					$('#hardware_wbAddr').val(data.addr);

				}

			}, 'json');

		});

	});

	function initUeditor(id, ueditor) {

		return ueditor(id, {
			initialFrameWidth : 1300 //初始化编辑器宽度,默认1000
			,
			initialFrameHeight : 600, //初始化编辑器高度,默认320
			toolbars : [ [ 'source', //源代码
			'anchor', //锚点
			'undo', //撤销
			'redo', //重做
			'bold', //加粗

			'spechars', //特殊字符
			'forecolor', //字体颜色

			'print', //打印
			'preview', //预览
			'horizontal', //分隔线
			'removeformat', //清除格式
			'unlink', //取消链接
			'inserttable', //插入表格
			'insertrow', //前插入行
			'insertcol', //前插入列
			'mergeright', //右合并单元格
			'mergedown', //下合并单元格
			'deleterow', //删除行
			'deletecol', //删除列
			'splittorows', //拆分成行
			'splittocols', //拆分成列
			'splittocells', //完全拆分单元格
			'deletecaption', //删除表格标题
			'inserttitle', //插入标题
			'mergecells', //合并多个单元格
			'deletetable', //删除表格

			'insertparagraphbeforetable', //"表格前插入行"

			'fontfamily', //字体
			'fontsize', //字号
			'paragraph', //段落格式
			'simpleupload', //单图上传
			'edittable', //表格属性
			'edittd', //单元格属性
			'link', //超链接

			'fullscreen', //全屏
			'insertimage', //多图上传
			'attachment', //附件
			] ]

		});

	}

	function sellHardware_delete() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'hardware_delete.action'
		}, function(r) {
			if (!r.success) {
				$.messager.alert('提示', r.msg);
			} else {
				var row = $('#hardware_datagrid').datagrid('getSelected');
				if (row) {
					$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
						if (r) {
							$.post('${xymz}/hardware_delete.action', {
								'id' : row.id
							}, function(r) {
								if (r.success) {
									$('#hardware_datagrid').datagrid('load');
									$.messager.show({
										title : '提示',
										msg : r.msg,
									});
								} else {
									$.messager.alert('警告', r.msg);
								}

							}, 'json');
						}
					});
				} else {
					$.messager.alert('警告', '请选择要删除 的记录');
				}
			}
		}, 'json');

	}

	function sellHardware_edit() {

		$.post('${xymz}/privilege_approve.action', {
			'url' : 'hardware_edit.action'
		}, function(r) {
			if (!r.success) {
				$.messager.alert('提示', r.msg);
			} else {
				var row = $('#hardware_datagrid').datagrid('getSelected');
				if (row) {
					$('#hardware_edit_id').val(row.id);
					//设置内容
					hardware_edit_dialog(row);
					$('#hardware_edit_dialog').dialog('open');

					//销毁					
					UE.delEditor("hardware_edit_editor");

					var hardware_edit = UE.hardware_edit;
					var hardware_edit = initUeditor("hardware_edit_editor", hardware_edit);
					setTimeout(function() {
						hardware_edit.execCommand('cleardoc');
						hardware_edit.setContent(row.hardware_detail);
					}, 300);
				} else {
					$.messager.alert('警告', '请选择要编辑的记录');
				}

			}
		}, 'json');

	}

	function sellHardware_edit_save() {

		var hardware_detail = UE.getEditor('hardware_edit_editor').getContent();
		$.post('${xymz}/hardware_edit.action', {
			'id' : $('#hardware_edit_id').val(),
			'hardware_detail' : hardware_detail,
		}, function(r) {
			if (r.success) {
				$('#hardware_datagrid').datagrid('load');

				//	UE.getEditor('hardware_edit_editor').setContent("", false); //保存成功后,编辑框内容置空
				showdata(r.obj);

				$.messager.show({
					title : '提示',
					msg : r.msg,
				});
			} else {
				$.messager.alert('警告', r.msg);
			}

		}, 'json');

	}

	function openHardware_change_dialog() {
		$.post('${xymz}/privilege_approve.action', {
			'url' : 'hardware_edit.action'
		}, function(r) {
			if (!r.success) {
				$.messager.alert('提示', r.msg);

			} else {
				var row = $('#hardware_datagrid').datagrid('getSelected');
				if (row) {

					//初始化dialog
					$('#hardware_change_dialog').dialog({
						title : row.wbName + "硬件更换单",
						fit : true,
						modal : true,
						closed:true,
						buttons : [ {
							text : '保存',
							iconCls : 'icon-save',
							handler : function() {
								var id = $('#hardware_change_id').val();

							var hardware_change_content = hardware_change.getContent();

								$.post('${xymz}/hardware_saveChange.action', {
									'id' : id,
									'hardware_change' : hardware_change_content
								}, function(r) {
									$('#hardware_change_dialog').dialog('close');
									$('#hardware_datagrid').datagrid('load');
									
								
									showdata(r.obj);
								}, 'json');

							}
						} ]
					});



					
					
					$('#hardware_change_dialog').dialog('open');
					
					
					
					UE.delEditor("hardware_change_editor");
					  var hardware_change = UE.MyEditor;
						var hardware_change =initUeditor("hardware_change_editor",hardware_change);
					if (row.hardware_change != null) {
						$('#hardware_change_id').val(row.id);
						setTimeout(function() {
							hardware_change.setContent(row.hardware_change);

						}, 300);

					} else {
						//获取硬件更换表格
						$.post('${xymz}/hardwareInfo_queryBySellHardware.action', {
							'id' : 20000,
						}, function(r) {
							$('#hardware_change_id').val(row.id);
							setTimeout(function() {
								hardware_change.setContent(r.obj.hardware_detail);
							}, 300);
						}, 'json');

					}

				} else {
					$.messager.alert('警告', '请选择要更换的网吧');
				}

			}

		}, 'json');

	}
</script>
