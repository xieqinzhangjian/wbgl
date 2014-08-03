<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>set

<style>
.title_td {
	
}

td {
	font-size: 12px;
}
</style>

<body>
	<!-- 查询 -->
	<div id="problem_list_layout" class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north'" style="height: 100px;">
			<form id="problem_list_searchForm">
				<table>
					<tr>
						<td>开始日期:</td>
						<td><input class="easyui-datetimebox" name="startCreateDate" style="width:150px"></td>
						<td class="title_td">截至日期:</td>
						<td><input class="easyui-datetimebox" name="endCreateDate" style="width:150px"></td>

					</tr>
					<tr>
						<td class="title_td">问题标题:</td>
						<td class="title_td"><input type="text" name="title"></td>
						<td class="title_td">来访网吧:</td>
						<td><input type="text" name="wbName"></td>
						<td class="title_td">问题状态:</td>
						<td><input id="problem_search_statusId" name="statusName" /></td>
						<!-- class="easyui-combobox" 	data-options="valueField:'id',textField:'text',url:'${xymz}/status_list.action'" -->
					</tr>
					<tr>
						<td class="title_td">所属分类:</td>
						<td><select id="classify_combotree" style="width:200px;" name="classifyName" ></select></td>
						<td class="title_td">线上受理人:</td>
						<td><input id="problem_search_userName" name="userName" /></td>
						<td class="title_td">线下受理人:</td>
						<td><input id="problem_search_appointName" name="appointName" /></td>
						<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
							onclick="problem_searchFun();">查询</a></td>
						<td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="problem_clearFun();">清空</a>
						</td>
					</tr>
				</table>
			</form>



		</div>
		<div data-options="region:'center'">
			<table id="problem_list_datagrid"></table>
		</div>
	</div>
	<!-- 添加 -->
	<div id="problem_add_dialog" title="新增事务">
		<form id="problem_add_form" action="${xymz}/problem_edit.action" method="post">
			<table border="1" cellpadding="3" cellspacing="0" style="width: 90%;margin:auto">
				<tr>
					<td colspan="2" style="background-color: #0081C2">
						<div style="color: white;font-size: 14px;font-weight:bold">网吧信息</div></td>
				</tr>
				<tr>
					<td style="width:150px">网吧名称:</td>
					<td style="width:200px"><input name="wbName" id="wbName" class="easyui-validatebox" data-options="required:true"
						validType="NoNull">
					</td>
				</tr>
				<tr>
					<td>联系人:</td>
					<td><input name="contact" type="text" id="wbContact">
					</td>
				</tr>
				<tr>
					<td>联系电话:</td>
					<td><input name="wbTel" id="wbTel" type="text" class="easyui-validatebox" data-options="required:true"
						validType="NoNull">
					</td>
				</tr>
				<tr>
					<td>网吧地址:</td>
					<td><input name="addr" id="wbAddr" type="text" class="easyui-validatebox" style="width: 300px"
						data-options="required:true" validType="NoNull">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="background-color: #0081C2">
						<div style="color: white;font-size: 14px;font-weight:bold">事务信息</div></td>
				</tr>
				<tr>
					<td>问题标题:</td>
					<td><input type="text" name="title" class="easyui-validatebox" data-options="required:true" validType="NoNull">
					</td>
				</tr>

				<tr>
					<td>所属分类:</td>
					<td><select id="classify_add_combotree" style="width:200px;" name="classifyName"></select></td>
				</tr>

				<tr>
					<td>来访时间:</td>
					<td><input class="easyui-datetimebox" name="createDate" data-options="showSeconds:false" style="width:150px">
						如果此处为空,来访时间将是提交事务时间</td>
				</tr>
				<tr>
					<td>初始状态:</td>
					<td><input id="add_status" name="statusName" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top">处理记录:</td>
					<td><script id="problem_add_editor" name="content" type="text/plain"></script> <!-- 	<textarea id="problem_add_content" name="content" class="easyui-validatebox" data-options="required:true" style="width:390px;height:100px;">	</textarea> -->
					</td>
				</tr>

			</table>


		</form>

	</div>

	<!-- 修改 -->
	<div id="problem_edit_dialog">
		<form id="problem_edit_form" action="" method="post">
			<input hidden="true" name="id">
			<table border="1" cellpadding="3" cellspacing="0" style="width: 90%;margin:auto">
				<tr>
					<td colspan="2" style="background-color: #0081C2">
						<div style="color: white;font-size: 14px;font-weight:bold">网吧信息</div></td>
				</tr>
				<tr>
					<td style="width:150px">网吧名称:</td>
					<td style="width:200px"><input name="wbName" id="edit_wbName" class="easyui-validatebox" data-options="required:true"
						validType="NoNull">
					</td>
				</tr>
				<tr>
					<td>联系人:</td>
					<td><input name="contact" type="text" id="edit_wbContact">
					</td>
				</tr>
				<tr>
					<td>联系电话:</td>
					<td><input name="wbTel" id="edit_wbTel" type="text" class="easyui-validatebox" data-options="required:true"
						validType="NoNull">
					</td>
				</tr>
				<tr>
					<td>网吧地址:</td>
					<td><input name="addr" id="edit_wbAddr" type="text" class="easyui-validatebox" style="width: 300px"
						data-options="required:true" validType="NoNull">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="background-color: #0081C2">
						<div style="color: white;font-size: 14px;font-weight:bold">事务信息</div></td>
				</tr>
				<tr>
					<td>问题标题:</td>
					<td><input type="text" name="title" class="easyui-validatebox" data-options="required:true" validType="NoNull">
					</td>
				</tr>

				<tr>
					<td>所属分类:</td>
					<td><select id="classify_edit_combotree" style="width:200px;" name="classifyName"></select></td>
				</tr>

				<tr>
					<td>来访时间:</td>
					<td><input class="easyui-datetimebox" name="createDate" id="problem_createDate" data-options="showSeconds:false"
						style="width:150px"> 如果此处为空,来访时间将是提交事务时间</td>
				</tr>
				<tr>
					<!-- name="statusName"  -->
					<td>初始状态:</td>
					<td><input id="easyui-combobox_statusName" name="statusName"  />
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top">处理记录:</td>
					<td><script id="problem_edit_editor" name="content" type="text/plain"></script>
					</td>
				</tr>
			</table>


		</form>
	</div>
	<!-- 详细信息 -->
	<div id="problem_show_dialog">
		<table id="problem_show_table" border="1" bordercolor="#0081C2" style="border-collapse:collapse;">
			<tr>
				<th>事务状态:</th>
				<td id="problem_show_statusName"></td>

				<th>网吧名称:</th>
				<td id="problem_show_wbName"></td>
			</tr>
			<tr>
				<th>所属分类:</th>
				<td id="problem_show_classifyName" style="width: 280px"></td>

				<th>联系人:</th>
				<td id="problem_show_contact"></td>


			</tr>
			<tr>
				<th>线上受理人:</th>
				<td id="problem_show_userName"></td>

				<th>网吧电话:</th>
				<td id="problem_show_wbTel"></td>

			</tr>
			<tr>
				<th>线下受理人:</th>
				<td id="problem_show_appointName"></td>
				<th>网吧地址</th>
				<td id="problem_show_wbAddr" style="width: 360px"></td>


			</tr>
		</table>

		<br />
		<!-- <fieldset style="height:150px;width:763px;overflow:auto;border:1px #0081C2 groove">
			<legend style="border:0px;background-color:white;"> 处理方法 </legend></fieldset> -->
		<div id="problem_show_content"></div>

	</div>

	<script type="text/javascript">
		$(function() {
			$('#problem_list_searchForm').bind('keyup', function(event) {
				if (event.keyCode == '13') {
					problem_searchFun();
					//	$('#index_login').submit();
				}
			});

			$('#problem_search_userName').combobox({
				url : '${xymz}/user_list.action',
				valueField : 'id',
				textField : 'name',
				onLoadSuccess : function(r) {
					$('#problem_search_appointName').combobox('loadData', r);
				}

			});
			$('#problem_search_appointName').combobox({
				//url : '${xymz}/user_list.action',
				valueField : 'id',
				textField : 'name',
			});

			$('#problem_search_statusId').combobox({
				url : '${xymz}/status_list.action',
				valueField : 'id',
				textField : 'text',
				onLoadSuccess : function(r) {
					$('#easyui-combobox_statusName').combobox('loadData', r);
					$('#add_status').combobox('loadData', r);
				}
			});
			$('#easyui-combobox_statusName').combobox({
				valueField : 'id',
				textField : 'text',
				 required:true,
			});
			$('#add_status').combobox({
				valueField : 'id',
				textField : 'text',
				 required:true,
			});
			
			
			
			$('#classify_combotree').combobox({
				url : '${xymz}/classify_list.action',
				valueField : 'id',
				textField : 'text',
				onLoadSuccess : function(r) {
					$('#classify_edit_combotree').combobox('loadData', r);
					$('#classify_add_combotree').combobox('loadData', r);
				}
			});
			$('#classify_edit_combotree').combobox({
				valueField : 'id',
				textField : 'text',
				required:true,
	
			});
			$('#classify_add_combotree').combobox({
				valueField : 'id',
				textField : 'text',
				 required:true,
			});



		});
		//查询网吧信息并写入表单
		$(function() {
			$('#wbName').change(function() {
				var wbName = $('#wbName').val();
				$.post('${xymz}/wb_queryByName.action', {
					'name' : wbName
				}, function(data) {
					if (data) {
						$('#wbName').val(data.name);
						$('#wbContact').val(data.contact);
						$('#wbTel').val(data.wbTel);
						$('#wbAddr').val(data.addr);

					}

				}, 'json');

			});

		});

		//编辑的时候
		$(function() {
			$('#edit_wbName').change(function() {
				var wbName = $('#edit_wbName').val();
				$.post('${xymz}/wb_queryByName.action', {
					'name' : wbName
				}, function(data) {
					if (data) {
						$('#edit_wbName').val(data.name);
						$('#edit_wbContact').val(data.contact);
						$('#edit_wbTel').val(data.wbTel);
						$('#edit_wbAddr').val(data.addr);

					}

				}, 'json');

			});

		});

		//右击
		function onRowContextMenu(e, rowIndex, rowData) {
			e.preventDefault(); //阻止浏览器右键事件
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#problem_onRowContextMenu').menu('show', {
				left : e.pageX,
				top : e.pageX
			});
		};

		//双击
		function onDblClickRow(rowIndex, rowData) {
			var url = '${xymz}/problem/problemShow.jsp';

			/* 		addTab({
			 title : rowData.title,
			 href : url,
			 closable : true,
			 }); */
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			if (clientWidth > 1000) {
				clientWidth = 800;
			}
			if (clientHeight > 700) {
				clientHeight = 600;
			}

			$('#problem_show_dialog').dialog({
				title : rowData.title,
				width : clientWidth,
				height : clientHeight,
				closed : true,
				cache : false,
				//	fit : true,
				//href : 'get_content.php',
				modal : true
			});

			$('#problem_show_dialog').dialog('open');
			//必须清空内容,不然会内容会累加
			$('#problem_show_table td').text('');
			$('#problem_show_content').text('');
			$('#problem_table_gzjl td').text('');

			//延迟查询和写入数据
			setTimeout(function() {
				$.post('${xymz}/problem_byProblemId.action', {
					'id' : rowData.id
				}, function(data) {
					var content = htmldecode(data.content);

					$('#problem_show_content').append(content);
					//网吧
					$('#problem_show_wbName').text(data.wbName);
					$('#problem_show_wbAddr').text(data.wbAddr);
					$('#problem_show_contact').text(data.contact);
					$('#problem_show_wbTel').text(data.wbTel);

					//问题
					$('#problem_show_statusName').text(data.statusName);
					$('#problem_show_classifyName').text(data.classifyName);
					$('#problem_show_userName').text(data.userName);
					$('#problem_show_appointName').text(data.appointName);

				}, 'json');

			}, 200);

		}
		function htmldecode(str) {
			str = str.replace(/&amp;/gi, '&');
			str = str.replace(/&nbsp;/gi, ' ');
			str = str.replace(/&quot;/gi, '"');
			str = str.replace(/&apos;/gi, "'");
			str = str.replace(/&lt;/gi, '<');
			str = str.replace(/&gt;/gi, '>');
			str = str.replace(/_$tag_]*>(?:(rn)|r|n)?/gi, 'n');
			return str;
		}

		function problem_searchFun() {
			$('#problem_list_datagrid').datagrid('load', serializeObject($('#problem_list_searchForm')));
		}
		function problem_clearFun() {
			$('#problem_list_searchForm input').val('');
			$('#problem_list_datagrid').datagrid('load', {});
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

		function getClassifyTree() {

			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			if (clientWidth > 1000) {
				clientWidth = 1000;
			}
			if (clientHeight > 700) {
				clientHeight = 650;
			}

			$('#problem_add_dialog').dialog({
				iconCls : 'icon-add',
				width : clientWidth,
				height : clientHeight,
				resizable : true,
				maximizable : true,
				//fit : true,
				closed : true,
				onClose : function() {

				},
				buttons : [ {
					text : '保存',
					iconCls : 'icon-add',
					handler : function(r) {
						save_problem();
					},
				} ]

			});

			$('#problem_add_form input').val("");
			$('#problem_add_content').val("");

		}

		function save_problem() {

			$('#problem_add_form').form('submit', {
				url : '${xymz}/problem_save.action',
				success : function(data) {

					$('#problem_list_datagrid').datagrid('load');
					$('#problem_add_dialog').dialog('close');

					var obj = jQuery.parseJSON(data);
					$.messager.show({
						title : '提示',
						msg : obj.msg,
					});
				}
			});
		}

		function edit_problem() {
			$('#problem_edit_form').form('submit', {
				url : '${xymz}/problem_edit.action',
				success : function(r) {
					var obj = jQuery.parseJSON(r);
					if (obj.success) {
						UE.getEditor('problem_edit_editor').execCommand('cleardoc');
						$.messager.show({
							title : '提示',
							msg : obj.msg,
						});

					} else {
						$.messager.alert('提示', obj.msg);
					}
					$('#problem_edit_dialog').dialog('close');
					$('#problem_list_datagrid').datagrid('reload');

				}
			});

		}

		function delProblem() {
			$.post('${xymz}/privilege_approve.action', {
				'url' : 'problem_delete.action'
			}, function(r) {
				if (r.success) {
					var row = $('#problem_list_datagrid').datagrid('getSelected');
					if (row) {
						$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
							if (r) {
								$.post('${xymz}/problem_delete.action', {
									'id' : row.id
								}, function(data) {

									$('#problem_list_datagrid').datagrid('load');
									$.messager.show({
										title : '提示',
										msg : data.msg
									});

								}, 'json');
							}
						});

					} else {
						$.messager.alert('提示', '请选择要删除的事务');

					}
				} else {
					$.messager.alert('提示', r.msg);
				}
			}, 'json');

		}

		$('#problem_list_datagrid').datagrid({
			url : '${xymz}/problem_dataGrid.action',
			border : false,
			fit : true,
			pagination : true,
			pageList : [ 20, 30, 40, 50 ],
			striped : true,//是否显示斑马线效果。
			//rownumbers:true, //编号
			pageSize : 20, //初始化现在多少条数据
			sortName : 'createDate',
			sortOrder : 'desc',// 默认是asc

			fitColumns : true, //真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
			checkOnSelect : false,
			selectOnCheck : true,
			idField : 'id',
			singleSelect : true,
			onDblClickRow : onDblClickRow,
			columns : [ [ {
				field : 'id',
				title : 'id',
				width : 100,
				hidden : true

			}, {
				field : 'title',
				title : '问题标题',
				width : 100,
			//可以点击title排序
			}, {
				field : 'wbName',
				title : '来访网吧',
				width : 60,

			},

			{
				field : 'userName',
				title : '受理人',
				width : 50,

			}, {
				field : 'statusName',
				title : '事务状态',
				width : 50,

			}, {
				field : 'createDateString',
				title : '创建日期',
				width : 50,
			}, {
				field : 'createDate',
				title : '创建日期',
				width : 50,
				hidden : true
			}, {
				field : 'appointName',
				title : '线下受理人',
				width : 50,
			}, {
				field : 'lastUpdateDateString',
				title : '最后更新日期',
				width : 50,
			}, {
				field : 'classifyId',
				title : '分类ID',
				width : 100,
				hidden : true
			}, {
				field : 'contact',
				title : '分类ID',
				width : 100,
				hidden : true
			}, {
				field : 'wbTel',
				title : '分类ID',
				width : 100,
				hidden : true
			}, {
				field : 'content',
				title : '处理记录',
				width : 100,
				hidden : true
			}, {
				field : 'createDate',
				title : '分类ID',
				width : 100,
				hidden : true
			}

			] ],
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {

					$.post('${xymz}/privilege_approve.action', {
						'url' : 'problem_save.action'
					}, function(r) {
						if (!r.success) {
							$.messager.alert('提示', r.msg);
						} else {

							UE.getEditor('problem_add_editor');

							getClassifyTree();

							$('#problem_add_dialog').dialog('open');
						}

					}, 'json');

				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					delProblem();
				}
			}, '-', {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					$.post('${xymz}/privilege_approve.action', {
						'url' : 'problem_edit.action'
					}, function(r) {
						if (!r.success) {
							$.messager.alert('提示', r.msg);
						} else {
							var row = $('#problem_list_datagrid').datagrid('getSelected');
							if (row) {
								$.post('${xymz}/problem_problemById.action', {
									'id' : row.id
								}, function(r) {
									var clientWidth = document.body.clientWidth;
									var clientHeight = document.body.clientHeight;
									if (clientWidth > 1000) {
										clientWidth = 1000;
									}
									if (clientHeight > 700) {
										clientHeight = 650;
									}

									$('#problem_edit_dialog').dialog({
										title : r.obj.title,
										iconCls : 'icon-edit',
										width : clientWidth,
										height : clientHeight,
										resizable : true,
										maximizable : true,
										closed : true,
										onClose : function() {

										},
										buttons : [ {
											text : '保存',
											iconCls : 'icon-edit',
											handler : function(r) {
												edit_problem();

											},
										} ]

									});

									$('#problem_edit_dialog').dialog('open');

									$('#problem_edit_form input[name=id]').val(r.obj.id);
									$('#problem_edit_form input[name=wbName]').val(r.obj.wb.name);
									$('#problem_edit_form input[name=contact]').val(r.obj.wb.contact);
									$('#problem_edit_form input[name=wbTel]').val(r.obj.wb.wbTel);
									$('#problem_edit_form input[name=addr]').val(r.obj.wb.addr);
									$('#problem_edit_form input[name=title]').val(r.obj.title);
									$('#problem_edit_form input[name=classifyName]').val(r.obj.classify.id);
									//分类回写
									$('#classify_edit_combotree').combotree('setText',r.obj.classify.text);
									//状态回显
									$('#easyui-combobox_statusName').combobox('setText',r.obj.status.text);

									//创建日期回写
									var formatCreateDate = getDate(row.createDate);
									$('#problem_createDate').datetimebox('setValue', formatCreateDate + "");

									UE.getEditor('problem_edit_editor');
									setTimeout(function() {
										UE.getEditor('problem_edit_editor').setContent(r.obj.content);

									}, 300);

								}, 'json');

							} else {
								$.messager.alert('提示', "请选择要修改的是事务");
							}

						}

					}, 'json');

				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					var tab = $('#layout_center_Tabs').tabs('getSelected');
					tab.panel('refresh');

				}
			}, '-', {
				text : '分类管理',
				iconCls : 'icon-building_go',
				handler : function() {
					$.post('${xymz}/privilege_approve.action', {
						'url' : 'classify_list.action'
					}, function(r) {
						if (!r.success) {
							$.messager.alert('提示', r.msg);
						} else {

							var url = '${xymz}/sysMgr/classify/classifyMgr.jsp';
							addTab({
								title : '分类管理',
								href : url,
								closable : true
							});

						}

					}, 'json');

				}
			} ],
		});

		function getDate(value) {

			if (value) {
				var newTime = new Date(value);
				var year = newTime.getFullYear();
				var month = newTime.getMonth() + 1;
				var date = newTime.getDate();
				var hours = newTime.getHours();
				var minutes = newTime.getMinutes();
				var fullDate = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes;

				return fullDate;

			}
		}
		function getwindowsdx() {
			var s = "";
			s += "\r\n网页可见区域宽：" + document.body.clientWidth;
			s += "\r\n网页可见区域高：" + document.body.clientHeight;
			s += "\r\n网页可见区域宽：" + document.body.offsetWidth + " (包括边线和滚动条的宽)";
			s += "\r\n网页可见区域高：" + document.body.offsetHeight + " (包括边线的宽)";
			s += "\r\n网页正文全文宽：" + document.body.scrollWidth;
			s += "\r\n网页正文全文高：" + document.body.scrollHeight;
			s += "\r\n网页被卷去的高：" + document.body.scrollTop;
			s += "\r\n网页被卷去的左：" + document.body.scrollLeft;
			s += "\r\n网页正文部分上：" + window.screenTop;
			s += "\r\n网页正文部分左：" + window.screenLeft;
			s += "\r\n屏幕分辨率的高：" + window.screen.height;
			s += "\r\n屏幕分辨率的宽：" + window.screen.width;
			s += "\r\n屏幕可用工作区高度：" + window.screen.availHeight;
			s += "\r\n屏幕可用工作区宽度：" + window.screen.availWidth;
			s += "\r\n你的屏幕设置是 " + window.screen.colorDepth + " 位彩色";
			s += "\r\n你的屏幕设置 " + window.screen.deviceXDPI + " 像素/英寸";
			alert(s);

		}

		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;

		$('#problem_show_dialog').dialog({
			title : '问题明细',
			width : clientWidth,
			height : clientHeight,
			closed : true,
			cache : false,
			maximizable : true,
			//	fit : true,
			//href : 'get_content.php',
			modal : true
		});
	</Script>