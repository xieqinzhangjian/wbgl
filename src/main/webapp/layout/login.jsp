<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function userLogin() {
		$('#index_login').form('submit', {
			url : '${xymz}/user_login.action',
			success : function(r) {
				console.info(r);
				var data = eval("(" + r + ")");
				if (data.success) {
					location.replace(location.href);
					$.messager.show({
						title : '提示',
						msg : data.msg
					});
				} else {
					$.messager.alert('提示', data.msg);
				}
			}

		}

		);

	}

	$(function() {
		//增加回车功能
		$('#index_login input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				userLogin();
			//	$('#index_login').submit();
			}
		});
		//页面加载完毕后,焦点自动在username的input 表单上,获取焦点
		$('#index_login input[name=name]').focus();

	});
</script>


<c:if test="${sessionScope.user==null}">
	<div id="layout_login_dialog" class="easyui-dialog" data-options="title:'登录',modal:true,closable:false,buttons:[{
		text:'登录',
		iconCls:'',
		handler:function(){
			userLogin();
	
	}
	}]">
		<form id="index_login" method="post">
			<table>
				<tr>
					<td>姓名:</td>
					<td><input class="easyui-validatebox" data-options="required:true" validType="loginName" type="text" name="name" />
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" class="easyui-validatebox" data-options="required:true" validType="NoNull" name="password" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</c:if>

