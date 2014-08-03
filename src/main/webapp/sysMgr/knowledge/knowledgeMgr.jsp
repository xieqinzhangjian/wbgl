<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>

<div class="easyui-layout" style="width:100%;height:100%;">

	<div data-options="region:'north'" style="height:30px;">
		<a id="btn" href="#" class="easyui-linkbutton" onclick="knowledgeMenu_delete()" data-options="iconCls:'icon-no',plain:true">删除</a> <a id="btn" href="#" class="easyui-linkbutton" onclick="add_dialog()" data-options="iconCls:'icon-add',plain:true">添加</a>
	</div>
	<div data-options="region:'center'" style="padding:0px;">
		<ul style="float: left;" id="sysMgr_knowledgeMenuMgr_tree"></ul>
	</div>
</div>


<div id="sysMgr_knowledgeMenuMgr_dialog">
	<form method="post" id="knowledgeMenu_add_form">
		<table>
			<tr>
				<td width="200px">新分类名称</td>
				<td><input id="knowledgeMenu_newName" name="text" type="text">
				</td>
			</tr>
			<tr>
				<td>父节点:</td>
				<td><select id="easyui-combotree_parentName" name="parentId"></select> <%-- 				<td><select id="easyui-combotree_parentName" class="easyui-combotree" style="width:200px;" name="parentId" data-options="url:'${xymz}/knowledgeMenu_list.action',panelHeight:400"></select></td> --%>
			</tr>
			<tr>
				<td style="color: red" colspan="2">如果不选择父节点,那么添加的此节点为根节点</td>
			</tr>
		</table>
	</form>

</div>



<script type="text/javascript">
	$('#sysMgr_knowledgeMenuMgr_tree').tree({
		url : '${xymz}/knowledgeMenu_queryTree.action',
		onDblClick : function(node) {
			$('#sysMgr_knowledgeMenuMgr_tree').tree('beginEdit', node.target);
		},
		onAfterEdit : function(node) {
			$.post('${xymz}/knowledgeMenu_edit.action', {
				'id' : node.id,
				'text' : node.text,
			}, function(r) {
				if (r.success) {
					//	$.messager.alert('提示', r.msg);
				}

			}, 'json');
		}

	});

	$('#sysMgr_knowledgeMenuMgr_dialog').dialog({
		title : '新增',
		iconCls : 'icon-add',
		width : 300,
		height : 150,
		closed : true,
		cache : false,
		modal : true,
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				knowledgeMenu_insert();
			}
		} ]

	});

	function add_dialog() {

		$('#easyui-combotree_parentName').combotree({
			url : '${xymz}/knowledgeMenu_list.action',
			width : 155,
			height : 20,
			panelHeight : 400

		});

		$('#sysMgr_knowledgeMenuMgr_dialog').dialog('open');

	}

	function knowledgeMenu_insert() {

		$('#knowledgeMenu_add_form').form('submit', {
			url : '${xymz}/knowledgeMenu_save.action',
			success : function(r) {
				$('#sysMgr_knowledgeMenuMgr_tree').tree('reload');
				$('#easyui-combotree_parentName').combotree('reload');
				$('#sysMgr_knowledgeMenuMgr_dialog').dialog('close');

			}
		});

	}

	function knowledgeMenu_delete() {
		var row = $('#sysMgr_knowledgeMenuMgr_tree').tree('getSelected');
		if (row) {
			$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
				if (r) {

					$.post('${xymz}/knowledgeMenu_delete.action', {
						'id' : row.id
					}, function(r) {
						if (r.success) {
							$('#sysMgr_knowledgeMenuMgr_tree').tree('reload');
							$('#easyui-combotree_parentName').combotree('reload');
						}

					}, 'json');
				}
			});

		}
	}
</script>