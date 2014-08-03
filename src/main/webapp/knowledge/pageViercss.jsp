<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div class="green-black" style="float: right;">
	<c:if test="${requestScope.pageBean.currentPage>1}">

		<a href="javascript: gotoPage(${requestScope.pageBean.currentPage-1},${requestScope.statusType})">上一页</a>
	</c:if>
	<%--<span class="current">1</span>--%>
	<c:forEach begin="${requestScope.pageBean.beginPageIndex}" end="${requestScope.pageBean.endPageIndex}" var="num">

		<c:if test="${requestScope.pageBean.currentPage!=num}">
			<a href="javascript: gotoPage(${num},${requestScope.statusType})">${num}</a>
		</c:if>

		<c:if test="${requestScope.pageBean.currentPage==num}">
			<span class="current">${num}</span>
		</c:if>

	</c:forEach>
	<c:if test="${requestScope.pageBean.currentPage<requestScope.pageBean.pageCount}">
		<a href="javascript: gotoPage(${requestScope.pageBean.currentPage+1},${requestScope.statusType})">下一页</a>

	</c:if>

	页次：${requestScope.pageBean.currentPage}/${requestScope.pageBean.pageCount}页 每页显示：${requestScope.pageBean.pageSize }条 &nbsp;
	总记录数：${requestScope.pageBean.recordCount}条
</div>
<script type="text/javascript">

function gotoPage(currentPage){
	


}


</script>