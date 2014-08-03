<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${xymz}/jslib/syUtil.js"></script>



<c:if test="${sessionScope.user!=null}">
	<div style="background-color:#F2F2F2;font-size: 14px;font-weight: 400 ">
		信佑名智 <a href="javascript:void(0)" class="easyui-splitbutton" data-options="menu:'#mm'" onclick="javascript:alert('ok')">
			${sessionScope.user.name} </a>

		<div id="mm" style="width:100px;">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-key_go',plain:true" onclick="resetPassword();">修改密码</a> <a
				href="#" class="easyui-linkbutton" data-options="iconCls:'icon-logout_user',plain:true" onclick="west_logout();">注销</a>
		</div>
	</div>
</c:if>


<div id="layout_west_accordion" class="easyui-accordion" data-options="fit:true,border:false,selected:true">


	<ul id="layout_west_tree" class="easyui-tree"
		data-options="url:'${xymz}/menu_allparent.action',lines:true,
				onClick:function(node){
					if(node.url){
						var url ='${xymz}'+node.url;
						addTab({title:node.text,href:url,closable:true});
					}

				},
				onDblClick:function(node){   //双击 展开菜单或者关闭菜单
				  if(node.state=='closed'){
				  	$(this).tree('expand',node.target);
				  }else{
				  	$(this).tree('collapse',node.target);
				  }
				
				},
				
				">
	</ul>



	<%-- 	<div title="知识库" data-options="iconCls:'icon-mini-book_open',selected:true">
		<ul id="layout_west_knowledgeMenuTree" class="easyui-tree"
			data-options="url:'${xymz}/knowledgeMenu_queryTree.action',lines:true,
				onClick:function(node){
					onclinkMenu(node);

				},
				onDblClick:function(node){   //双击 展开菜单或者关闭菜单
				  if(node.state=='closed'){
				  	$(this).tree('expand',node.target);
				  }else{
				  	$(this).tree('collapse',node.target);
				  }
				
				},
				
				">
		</ul>

	</div> --%>
	<!-- <div id="user_info" title="个人信息" data-options="iconCls:'icon-logout_user',">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-logout_user',plain:true" onclick="west_logout();">注销</a>

	</div> -->



</div>
<!-- 编辑 -->
<div id="resetPassword">
		<form id="resetPassword_Form" action='${xymz}/user_editPassword.action' method="post">
		<input name="id" hidden="true" id="user_id" value="${sessionScope.user.id}">
		<table>
			<tr>
				<td>新密码:</td>
				<td><input name="password" data-rule="密码:required;" id="user_password"  type="password" class="easyui-validatebox" data-options="required:true" validType="NoNull">
				</td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td><input name="rePassword"  data-rule="确认密码:required;match[password]" type="password" class="easyui-validatebox" data-options="required:true" validType="NoNull">
				</td>
			</tr>
		</table>
		
	
	</form>
</div>


<script type="text/javascript">
	$('#resetPassword').dialog({
		title : '修改密码',
		width : 350,
		height : 130,
		closed : true,
		cache : false,
		modal : true,
		buttons : [ {
			iconCls : 'icon-save',
			text : '保存',
			handler : function() {
				saveNewPassword();
			}
		} ]

	});

	function saveNewPassword() {
		
		var password= $('#resetPassword_Form input[name=password]').val();
		var rePassword = $('#resetPassword_Form input[name=rePassword]').val();
		
		console.info("password=="+password);
		console.info("rePassword=="+rePassword);
		if(password!=rePassword){
			$.messager.alert('提示', "新密码和确认密码不一致,请检查");
				return ;
		}else if(""==$.trim(password)){
			$.messager.alert('提示', "都提示了不能为空,没看到呀??");
			return ;
		}
		
	
		$.post('${xymz}/user_editPassword.action', {
			'id' : $('#user_id').val(),
			'password' : $('#user_password').val()
		}, function(r) {
			$('#resetPassword').dialog('close');

			$.messager.alert('提示', r.msg);
			setTimeout(function() {
				west_logout();

			}, 1000);

		}, 'json');

	}
	function resetPassword() {
		rePasswordhuiche();

		$('#resetPassword').dialog('open');
	}

	function onclinkMenu(node) {
		if (node.url) {
			var url = '${xymz}' + node.url + "?knowledgeMenuId=" + node.id;
			addTab({
				title : node.text,
				href : url,
				id : node.id,
				closable : true
			});
		}
	}

	function west_logout() {
		$.post('${xymz}/user_logout.action', {}, function() {
			//刷新页面
			location.replace(location.href);
		}, 'json');
	}

	function rePasswordhuiche() {
	//增加回车功能
		$('#resetPassword').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				saveNewPassword();
				//	$('#index_login').submit();
			}
		});
	}
</script>
