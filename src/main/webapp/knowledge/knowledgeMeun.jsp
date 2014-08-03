<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div id=knowledgeIndex_sidebar>
	<ul>

		<li id="categories"><c:forEach items="${sessionScope.menuSet}" var="menu">
				<h2>${menu.text}</h2>
				<ul>
					<c:forEach items="${menu.children}" var="children">
						<li><a href="#">${children.text}</a>(${children.topicTotal})</li>

					</c:forEach>

					<!-- 		<li><a href="#">解决问题分享 </a> (1)</li>
					<li><a href="#">操作系统.网络.硬件 </a>(4)</li>
					<li><a href="#">工具相关</a> (4)</li>
					<li><a href="#">编程开发</a> (4)</li> -->
				</ul>
			</c:forEach>
		</li>

	</ul>
	<div>

	</div>

</div>

