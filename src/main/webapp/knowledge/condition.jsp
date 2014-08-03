<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<form id="conditionStrForm" action="${bjxy}/topic_queryByTitleOrUser.action" method="post" style="float: right;">
	<input type="text" style="width: 150px" name="str" value="${requestScope.str}"> <select style="width: 60px" name="type">
		<option value=2 <c:if test="${requestScope.type==2}">selected="selected"</c:if>>帖子</option>
		<option value=1 <c:if test="${requestScope.type==1}">selected="selected"</c:if>>用户</option>
	</select> <a onclick="condition();">搜索</a>
</form>


<script type="text/javascript">
	function condition() {

		var conditionStr = $('#conditionStrForm input[name=str]').val();
		if (conditionStr) {
			var conditionType = $('#conditionStrForm select[name=type]').val();
			$.post('${xymz}/topic_condition.action', {
				"conditionStr" : conditionStr,
				"conditionType" : conditionType
			}, function(r) {
				
				
				console.info(r);
				
			}, 'json');

			return;
		}
		var tab = $('#layout_center_Tabs').tabs('getTab', '知识库');
		if (tab) {
			tab.panel('refresh');
		}
	}
</script>