<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../../../public/public.jspf"%>
<style>
.texthidden {
	width: 550px;
	height: 100px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	/*    border:1px solid #ddd; */
}
</style>

<div id="wrapper">
	<div class="topicPage">
		<!-- start content -->
		<div class="topicContent">
			<!-- 		<div id="knowledgeIndex_header" style="background-color: ">现在位置:</div> -->
			<a onclick="saveUI();">发表帖子</a>
			<%@ include file="condition.jsp"%>
		<c:forEach items="${requestScope.pageBean.recordList}" var="topic">
				<!-- 发布日期 -->
				<input name="id" hidden="true" value="">
				<div class="post">
					<p class="date">
						<fmt:formatDate value="${topic.postTime}" pattern="MM" />
						月<b> <fmt:formatDate value="${topic.postTime}" pattern="dd" />日 </b>
					</p>

					<h2 class="title">${topic.title}</h2>
					<!-- 标题 -->
					<div class="entry">
						<!-- 文章简介 -->

						<div class="texthidden">
							${topic.content}<br />
						</div>
						.. <a href="javascript:topic_show('${topic.id}');">查看全文</a> &nbsp;&nbsp;&nbsp; <a href="">作者:${topic.author.name}</a> 发布日期:
						<fmt:formatDate value="${topic.postTime}" dateStyle="full" />

					</div>
				</div>

			</c:forEach>
			<%@ include file="pageViercss.jsp"%>
		</div>

		<%@ include file="knowledgeMeun.jsp"%>

	</div>
</div>
<script type="text/javascript">
	function saveUI() {

	var conditionStr = $('#conditionStrForm input[name=str]').val();
		if (conditionStr) {
			var conditionType = $('#conditionStrForm select[name=type]').val();

			console.info(conditionStr);
			console.info(conditionType);
			return;
		}

		var url = "topic_saveUI.action";
		addTab({
			title : '发布文章',
			href : url,
			closable : true
		});

	}

	function addTab(opts) {
		var t = $('#layout_center_Tabs');
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
	}

	function topic_show(topic_id) {
		var url = "${xymz}/topic_topicShowById.action?id=" + topic_id;
		addTab({
			title : '文章',
			href : url,
			closable : true
		});

	}
</script>
