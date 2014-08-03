<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div id="sell_hardware_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north'" style="height: 140px;">
		<div style="color: #042E5D">
			说明:<br /> 1.默认只查询状态为"正常使用"的,如果要查其他状态的,可以自己在下面查询栏里选择查询<br /> 2.当到期日期离现在小于10天,那么会自动变为红色提醒 <br />
			3.当到期时间为今天之前的,那么状态会自动被修改为"已到期",无需人工修改,并且背景变为灰色
		</div>

		<form id="sell_hardware_searchForm">
			<table>
				<tr>
					<td>网吧名称:</td>
					<td><input name="wbName" />
					</td>

					<td>区域:</td>
					<td><input name="scope" />
					</td>

					<td>注册号:</td>
					<td><input name="regNumber" />
					</td>
				</tr>
				<tr>
					<td>收费金额:</td>
					<td><input name="cost" />
					</td>
					<td>状态:</td>
					<td><select class="easyui-combobox" name="state" style="width:100px;">
							<option value=""></option>
							<option value="正常使用">正常使用</option>
							<option value="已过期">已过期</option>
							<option value="已流失">已流失</option>
							<option value="查看全部">查看全部</option>
					</select>
					</td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="movie_searchFun();">查询</a>
					</td>
					<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="movie_clearFun();">清空</a>
					</td>
				</tr>

			</table>


		</form>
	</div>
	<div data-options="region:'center'">
		<table id="sell_hardware_datagrid"></table>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('#sell_hardware_searchForm').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				movie_searchFun();
			}
		});
	});

	function movie_searchFun() {
		$('#sell_hardware_datagrid').datagrid('load', serializeObject($('#sell_hardware_searchForm')));
	}
	function movie_clearFun() {
		$('#sell_hardware_searchForm input').val('');
		$('#sell_hardware_datagrid').datagrid('load', {});
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

	//查询网吧信息并写入表单
	$(function() {
		$('#movie_wbName').change(function() {
			var wbName = $('#movie_wbName').val();
			$.post('${xymz}/wb_queryByName.action', {
				'name' : wbName
			}, function(data) {
				if (data) {
					$('#movie_wbName').val(data.name);
					$('#movie_contact').val(data.contact);
					$('#movie_wbTel').val(data.wbTel);
					$('#movie_addr').val(data.addr);

				}

			}, 'json');

		});

	});

	function movie_add() {
		$('#sell_hardware_form input').val('');
		$('#sell_hardware_dialog').dialog('open');
	}

	$('#sell_hardware_datagrid').datagrid({
		url : '${xymz}/sellMovie_dataGrid.action',
		border : false,
		fit : true,
		pagination : true,
		striped : true,
		rownumbers : true,// 编号
		pageSize : 20, //初始化现在多少条数据
		sortName : 'endUseDate',
		//sortOrder:'desc',// 默认是asc
		fitColumns : true,
		//checkOnSelect : false,//复选框的东西
		//	selectOnCheck : true,//复选框的东西
		idField : 'id',
		singleSelect : true,
		rowStyler : function(index, row) {

			//实际到期日期
			var endDate = new Date(row.endUseDate);

			//现在的日期
			var nowDate = new Date();

			//判断是否过期
			if (endDate < nowDate) {
				row.state = '已过期';
				$.post('${xymz}/sellMovie_due.action', {
					'id' : row.id
				}, function() {
				}, 'json');
			}

			//用结束日期-10天,也就是提前10天提醒,判断是否大于现在日期,如果大于,就红色报警
			var lastDate = AddDays(endDate, 10);
			if (lastDate < nowDate) {
				if (endDate > nowDate) {
					return 'background-color:red;color:#fff;';
				} else {
					return 'background-color:#A3A3A3;color:#fff;';
				}
			}

		},

		columns : [ [ {
			field : 'id',
			title : 'id',
			width : 100,
			hidden : true
		}, {
			field : 'regNumber',
			title : '注册号',
			width : 60,
			sortable : true
		//可以点击title排序
		}, {
			field : 'wbName',
			title : '网吧名称',
			width : 100,
			sortable : true
		},

		{
			field : 'agent',
			title : '代理商',
			width : 60,
			sortable : true,
			halign : 'center',
			align : 'center'

		}, {
			field : 'startUseDate',
			title : '计费日期',
			width : 100,
			sortable : true,
			//鼠标焦点到此字段时,自动弹出信息
			formatter : function(value, row, index) {
				var newTime = new Date(value);
				var year = newTime.getFullYear();
				var month = newTime.getMonth() + 1;
				var date = newTime.getDate();
				var fullDate = year + '-' + month + '-' + date;
				return fullDate;
			}

		}, {
			field : 'endUseDate',
			title : '到期日期',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				var newTime = new Date(value);
				var year = newTime.getFullYear();
				var month = newTime.getMonth() + 1;
				var date = newTime.getDate();
				var fullDate = year + '-' + month + '-' + date;

				return fullDate;
			}
		}, {
			field : 'isCharge',
			title : '是否收费',
			width : 60,
			sortable : true,
			halign : 'center',
			align : 'center'
		}, {
			field : 'paymentDate',
			title : '收费日期',
			width : 60,
			sortable : true,
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
		}, {
			field : 'isMyGameSoft',
			title : '游戏更新是我们的',
			width : 60,
			sortable : true,
			halign : 'center',
			align : 'center'
		}, {
			field : 'cost',
			title : '费用',
			width : 60,
			sortable : true,
			halign : 'center',
			align : 'center'
		}, {
			field : 'contact',
			title : '联系人',
			width : 60,
			halign : 'center',
			align : 'center'
		}, {
			field : 'wbTel',
			title : '联系方式',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'scope',
			title : '区域',
			width : 100,
			halign : 'center',
			align : 'center'
		}, {
			field : 'addr',
			title : '详细地址',
			width : 200,
		}, {
			field : 'state',
			title : '状态',
			width : 80,
			sortable : true,
			halign : 'center',
			align : 'center'
		}

		] ],
		toolbar : [ {
			text : '增加',
			iconCls : 'icon-add',
			handler : function() {
				$.post('${xymz}/privilege_approve.action', {
					'url' : 'sellMovie_save.action'
				}, function(r) {
					if (r.success) {
						movie_add();
					} else {
						$.messager.alert('提示', r.msg);
					}
				}, 'json');

			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {

				$.post('${xymz}/privilege_approve.action', {
					'url' : 'sellMovie_delete.action'
				}, function(r) {
					if (r.success) {
						delMovie();
					} else {
						$.messager.alert('提示', r.msg);
					}
				}, 'json');

			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {

				$.post('${xymz}/privilege_approve.action', {
					'url' : 'sellMovie_edit.action'
				}, function(r) {
					if (r.success) {
						editMovie();
					} else {
						$.messager.alert('提示', r.msg);
					}
				}, 'json');

			}
		}, '-', {
			text : '续费',
			iconCls : 'icon-money_dollar',
			handler : function() {
				$.post('${xymz}/privilege_approve.action', {
					'url' : 'sellMovie_xuFei.action'
				}, function(r) {
					if (r.success) {
						xuFeiDialog();
					} else {
						$.messager.alert('提示', r.msg);
					}
				}, 'json');

			}
		}, '-', {
			text : '查看续费记录',
			iconCls : 'icon-shape_square_edit',
			handler : function() {
				xuFeiLog();
			}
		}, '-', {
			text : '查看所有',
			iconCls : 'icon-bricks',
			handler : function() {
				$('#sell_hardware_datagrid').datagrid('load', {
					state : '查看全部'
				});

			}
		} ]

	});

	function editMovie() {

		var row = $('#sell_hardware_datagrid').datagrid('getSelected');
		$('#sell_hardware_edit_form input').val("");
		if (row) {
			$('#sell_hardware_edit_dialog').dialog('open');
			/* 		var falseoption = $('#edit_isCharge option[value=否]');
					var trueoption = $('#edit_isCharge option[value=是]');
					falseoption.removeAttr('selected');
					trueoption.removeAttr('selected'); */

			$('#sell_hardware_edit_form input[name=addr]').val(row.addr);
			$('#sell_hardware_edit_form input[name=wbName]').val(row.wbName);
			$('#sell_hardware_edit_form input[name=agent]').val(row.agent);
			$('#sell_hardware_edit_form input[name=contact]').val(row.contact);
			$('#sell_hardware_edit_form input[name=cost]').val(row.cost);
			$('#sell_hardware_edit_form input[name=id]').val(row.id);

			//是否付钱了
			$('#edit_isCharge_combobox').combobox('select', row.isCharge);

			//是否是我们的游戏更新
			$('#edit_isMyGameSoft_combobox').combobox('select', row.isMyGameSoft);

			//状态
			$('#edit_state_combobox').combobox('select', row.state);

			$('#sell_hardware_edit_form input[name=regNumber]').val(row.regNumber);
			$('#sell_hardware_edit_form input[name=scope]').val(row.scope);

			//	回写开始计费日期
			var formatStartDate = getDate(row.startUseDate);
			$('#edit_startUseDate').datebox('setValue', '' + formatStartDate); // 设置日期输入框的值

			//回写到期时间
			var formatEndDate = getDate(row.endUseDate);
			$('#edit_endUseDate').datebox('setValue', '' + formatEndDate); // 设置日期输入框的值

			//回显收费日期  paymentDate
			var formatPaymentDate = getDate(row.paymentDate);
			$('#edit_paymentDate').datebox('setValue', '' + formatPaymentDate); // 设置日期输入框的值

			$('#sell_hardware_edit_form input[name=state]').val(row.state);
			$('#sell_hardware_edit_form input[name=wbTel]').val(row.wbTel);

		} else {
			$.messager.alert('提示', '请选择要编辑的网吧');
		}

	}
	function movieSellEditSave() {
		$('#sell_hardware_edit_form').form('submit', {
			url : '${xymz}/sellMovie_edit.action',
			success : function(r) {
				var obj = $.parseJSON(r);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
				$('#sell_hardware_edit_dialog').dialog('close');
				$('#sell_hardware_datagrid').datagrid('reload');

			},

		});

	}

	function movieSellSave() {
		$('#sell_hardware_form').form('submit', {
			url : '${xymz}/sellMovie_save.action',
			success : function(data) {
				var obj = $.parseJSON(data);
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
				$('#sell_hardware_dialog').dialog('close');
				$('#sell_hardware_datagrid').datagrid('load');
			}
		});

	}

	function delMovie() {

		var row = $('#sell_hardware_datagrid').datagrid('getSelected');

		if (row) {

			$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
				if (r) {
					$.post('${xymz}/sellMovie_delete.action', {
						'id' : row.id
					},

					function(r) {
						if (r.success) {
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
							$('#sell_hardware_datagrid').datagrid('load');

						} else {
							$.messager.alert('提示', '删除异常,请联系管理员');
						}

					}, 'json');

				}
			});

		} else {
			$.messager.alert('提示', '请选择要删除的网吧');
		}

	}

	function xuFeiDialog() {
		var row = $('#sell_hardware_datagrid').datagrid('getSelected');
		if (row) {
			$('#sell_hardware_xufei_form input').val("");
			$('#sell_hardware_xufei_dialog').dialog('open');
			$('#xuFeiwbName').val(row.wbName);
			$('#sell_hardware_xufei_id').val(row.id);

		} else {
			$.messager.alert('提示', '请选择要续费的网吧');
		}
	}

	function movieSellXuFeiSave() {
		$('#sell_hardware_xufei_form').form('submit', {
			url : '${xymz}/sellMovie_xuFei.action',
			success : function(r) {
				var obj = $.parseJSON(r);
				if (obj.success) {
					$.messager.show({
						title : '提示',
						msg : obj.msg
					});
					$('#sell_hardware_xufei_dialog').dialog('close');
					$('#sell_hardware_datagrid').datagrid('reload');
				}

			}
		});

	}

	function xuFeiLog() {
		var row = $('#sell_hardware_datagrid').datagrid('getSelected');
		if (row) {
			$('#sell_hardwareXuFei_log_datagrid').datagrid({
				url : '${xymz}/sellMovie_xuFeiLogBySellMovie.action?id=' + row.id,
				border : false,
				fit : true,
				striped : true,//斑马线
				rownumbers : true,// 编号
				//sortOrder:'desc', 默认是asc
				fitColumns : true,
				idField : 'id',
				singleSelect : true,
				columns : [ [ {
					field : 'payDate',
					title : '续费日期',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						var newTime = new Date(value);
						var year = newTime.getFullYear();
						var month = newTime.getMonth() + 1;
						var date = newTime.getDate();
						var fullDate = year + '年' + month + '月' + date + '日';

						return fullDate;
					},
					halign : 'center',
					align : 'center'
				}, {
					field : 'endUseDate',
					title : '到期日期',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						if (value) {
							var newTime = new Date(value);
							var year = newTime.getFullYear();
							var month = newTime.getMonth() + 1;
							var date = newTime.getDate();
							var fullDate = year + '年' + month + '月' + date + '日';

							return fullDate;
						}

					},
					halign : 'center',
					align : 'center'
				}, {
					field : 'cost',
					title : '续费金额',
					width : 60,
					sortable : true,
					halign : 'center',
					align : 'center'
				}, {
					field : 'userName',
					title : '操作人',
					width : 60,
					sortable : true,
					halign : 'center',
					align : 'center'
				}, {
					field : 'remarks',
					title : '备注',
					width : 60,
					sortable : true,
					halign : 'center',
					align : 'center'
				} ] ]

			});
			$('#sell_hardwareXuFei_log_dialog').dialog({
				width : 600,
				height : 450,
				title : row.wbName + '的续费记录',
				closed : false,
				modal : true,
				resizable : true,
				maximizable : true,

			});
		} else {
			$.messager.alert('提示', '请选择要查看续费记录的网吧');
		}

	}

	function AddDays(d, n) {
		var t = new Date(d);//复制并操作新对象，避免改动原对象
		t.setDate(t.getDate() - n);
		return t;
	}

	$('#edit_isCharge_combobox').combobox({
		valueField : 'label',
		textField : 'value',
		data : [ {
			label : '是',
			value : '是'
		}, {
			label : '否',
			value : '否'
		} ]

	});
	$('#edit_isMyGameSoft_combobox').combobox({
		valueField : 'label',
		textField : 'value',
		data : [ {
			label : '是',
			value : '是'
		}, {
			label : '否',
			value : '否'
		} ]

	});
	$('#edit_state_combobox').combobox({
		valueField : 'label',
		textField : 'value',
		data : [ {
			label : '正常使用',
			value : '正常使用'
		}, {
			label : '试用',
			value : '试用'
		}, {
			label : '已过期',
			value : '已过期'
		}, {
			label : '已流失',
			value : '已流失'
		} ]

	});

	function getDate(value) {
		if (value) {
			var newTime = new Date(value);
			var year = newTime.getFullYear();
			var month = newTime.getMonth() + 1;
			var date = newTime.getDate();
			var fullDate = year + '-' + month + '-' + date;

			return fullDate;

		}
	}
</script>
<!-- 续费记录 -->
<div id="sell_hardwareXuFei_log_dialog" style="padding:10px;">
	<table id="sell_hardwareXuFei_log_datagrid"></table>
</div>


<!--续费  -->
<div id="sell_hardware_xufei_dialog" class="easyui-dialog" title="续费"
	data-options="iconCls:'icon-money_dollar',
	closed: true,    
	width: 300,    
    height: 300,    
	modal:true,
	buttons:[{
				text:'确定',
				iconCls:'icon-money_dollar',
				handler:function(r){
					movieSellXuFeiSave();
				}
			}]">

	<form action="" id="sell_hardware_xufei_form" method="post">
		<input id="sell_hardware_xufei_id" name="id" hidden="true">
		<table>
			<tr>
				<td>网吧:</td>
				<td><input type="text" name="wbName" id="xuFeiwbName" class="easyui-validatebox" data-options="required:true"
					validType="NoNull"></td>
			</tr>
			<tr>
				<td>续费金额:</td>
				<td><input type="text" name="cost" class="easyui-validatebox" data-options="required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>续费日期:</td>
				<td><input type="text" name="payDate" class="easyui-datebox" class="easyui-validatebox"
					data-options="showSeconds:false,required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>到期日期:</td>
				<td><input type="text" name="endUseDate" class="easyui-datebox" class="easyui-validatebox"
					data-options="showSeconds:false,required:true" validType="NoNull"></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><select class="easyui-combobox" name="state" style="width:100px;">
						<option selected="selected" value="正常使用">正常使用</option>
						<option value="试用">试用</option>
						<option value="过期">过期</option>
						<option value="已流失">已流失</option>
				</select></td>
			</tr>

			<tr>
				<td style="vertical-align:top">备注:</td>
				<td><textarea id="movie_log_remarks" name="remarks" data-options="required:true" style="width:170px;height:70px;">	</textarea>
				</td>
			</tr>


		</table>
	</form>
</div>

<!-- 新增 -->
<div id="sell_hardware_dialog" class="easyui-dialog" title="新增电影销售" style="width:500px;height:520px;"
	data-options="iconCls:'icon-add',closed: true,modal:true,buttons:[{
				text:'保存',
				iconCls:'icon-add',
				handler:function(r){
					movieSellSave();
				}
			}]">

	<form id="sell_hardware_form" action="" method="post">

		<table>
			<tr>
				<td colspan="2" style="background-color: #0081C2">
					<div style="color: white;font-size: 14px;font-weight:bold">网吧信息</div></td>
			</tr>
			<tr>
				<td style="width:150px">网吧名称:</td>
				<td style="width:200px"><input name="wbName" id="movie_wbName" class="easyui-validatebox" data-options="required:true"
					validType="NoNull">
				</td>
			</tr>
			<tr>
				<td>联系人:</td>
				<td><input name="contact" id="movie_contact" type="text">
				</td>
			</tr>
			<tr>
				<td>联系电话:</td>
				<td><input name="wbTel" type="text" id="movie_wbTel" class="easyui-validatebox" data-options="required:true"
					validType="NoNull">
				</td>
			</tr>
			<tr>
				<td>区域:</td>
				<td><input name="scope" type="text" id="scope">
				</td>
			</tr>
			<tr>
				<td>网吧地址:</td>
				<td><input name="addr" type="text" id="movie_addr" class="easyui-validatebox" style="width: 300px"
					data-options="required:true" validType="NoNull">
				</td>
			</tr>
			<tr>
				<td colspan="2" style="background-color: #0081C2">
					<div style="color: white;font-size: 14px;font-weight:bold">销售信息</div></td>
			</tr>
			<tr>
				<td>注册号:</td>
				<td><input type="text" name="regNumber" class="easyui-validatebox" data-options="required:true" validType="NoNull">
				</td>
			</tr>

			<tr>
				<td>代理商:</td>
				<td><input type="text" name="agent" /></td>
			</tr>

			<tr>
				<td>计费日期:</td>
				<td><input class="easyui-datebox" class="easyui-validatebox" name="startUseDate"
					data-options="showSeconds:false,required:true" validType="NoNull" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>到期日期:</td>
				<td><input class="easyui-datebox" name="endUseDate" class="easyui-validatebox"
					data-options="showSeconds:false,required:true" validType="NoNull" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>是否收费:</td>
				<td><select class="easyui-combobox" name="isCharge" style="width:100px;">
						<option value="是">是</option>
						<option value="否">否</option>
				</select></td>
			</tr>
			<tr>
				<td>是否用我们的更新软件:</td>
				<td><select class="easyui-combobox" name="isMyGameSoft" style="width:100px;">
						<option value="是">是</option>
						<option value="否">否</option>
				</select></td>
			</tr>
			<tr>
				<td>费用:</td>
				<td><input type="text" name="cost" class="easyui-validatebox" data-options="required:true" validType="NoNull" />
				</td>
			</tr>
			<tr>
				<td>收费日期:</td>
				<td><input class="easyui-datebox" name="paymentDate" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><select class="easyui-combobox" name="state" style="width:100px;">
						<option selected="selected" value="正常使用">正常使用</option>
						<option value="试用">试用</option>
						<option value="过期">过期</option>
						<option value="已流失">已流失</option>
				</select>
				</td>
			</tr>

		</table>
	</form>
</div>
<!-- 编辑 -->
<div id="sell_hardware_edit_dialog" class="easyui-dialog" title="编辑" style="width:500px;height:520px;"
	data-options="iconCls:'icon-edit',closed: true,modal:true,buttons:[{
				text:'编辑',
				iconCls:'icon-edit',
				handler:function(r){
					movieSellEditSave();
				}
			}]">

	<form id="sell_hardware_edit_form" action="" method="post">
		<input id="sell_hardware_edit_id" name="id" hidden="true">
		<table>
			<tr>
				<td colspan="2" style="background-color: #0081C2">
					<div style="color: white;font-size: 14px;font-weight:bold">网吧信息</div></td>
			</tr>
			<tr>
				<td style="width:150px">网吧名称:</td>
				<td style="width:200px"><input name="wbName" id="movie_wbName" class="easyui-validatebox" data-options="required:true"
					validType="NoNull">
				</td>
			</tr>
			<tr>
				<td>联系人:</td>
				<td><input name="contact" id="movie_contact" type="text">
				</td>
			</tr>
			<tr>
				<td>联系电话:</td>
				<td><input name="wbTel" type="text" id="movie_wbTel" class="easyui-validatebox" data-options="required:true"
					validType="NoNull">
				</td>
			</tr>
			<tr>
				<td>区域:</td>
				<td><input name="scope" type="text" id="scope">
				</td>
			</tr>
			<tr>
				<td>网吧地址:</td>
				<td><input name="addr" type="text" id="movie_addr" class="easyui-validatebox" style="width: 300px"
					data-options="required:true" validType="NoNull">
				</td>
			</tr>
			<tr>
				<td colspan="2" style="background-color: #0081C2">
					<div style="color: white;font-size: 14px;font-weight:bold">销售信息</div></td>
			</tr>
			<tr>
				<td>注册号:</td>
				<td><input type="text" name="regNumber" class="easyui-validatebox" data-options="required:true" validType="NoNull">
				</td>
			</tr>

			<tr>
				<td>代理商:</td>
				<td><input type="text" name="agent" /></td>
			</tr>

			<tr>
				<td>计费日期:</td>
				<td><input class="easyui-datebox" id="edit_startUseDate" name="startUseDate" data-options="showSeconds:false"
					style="width:150px" class="easyui-validatebox" data-options="required:true" validType="NoNull" />
				</td>
			</tr>
			<tr>
				<td>到期日期:</td>
				<td><input class="easyui-datebox" name="endUseDate" id="edit_endUseDate" class="easyui-validatebox"
					data-options="required:true" validType="NoNull" data-options="showSeconds:false,required:true" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>是否收费:</td>
				<td><input id="edit_isCharge_combobox" name="isCharge" /></td>
			</tr>
			<tr>
				<td>是否用我们的更新软件:</td>
				<td><input id="edit_isMyGameSoft_combobox" name="isMyGameSoft" />
				</td>
			</tr>
			<tr>
				<td>费用:</td>
				<td><input type="text" name="cost" class="easyui-validatebox" data-options="required:true" validType="NoNull" />
				</td>
			</tr>
			<tr>
				<td>收费日期:</td>
				<td><input class="easyui-datebox" name="paymentDate" id="edit_paymentDate" style="width:150px" />
				</td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><input id="edit_state_combobox" name="state" />
				</td>
			</tr>

		</table>
	</form>
</div>