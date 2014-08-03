<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div id="wrapper">
	<div class="topicPage">
		<!-- start content -->
		<div class="topicContent">
		

			<div id="knowledgeIndex_show_header" style="background-color: #40444E;width:650px  ;height: 30px;">
				
			</div>
			<br /> <br /> <br />


				<strong>标题: 
					${requestScope.topic.title }
				</strong>
		
						<div class="widget-content">
								
						${requestScope.topic.content }
						</div>
						
		</div>
			<%@ include file="knowledgeMeun.jsp"%>
	</div>



</div>

<script type="text/javascript">


</script>

