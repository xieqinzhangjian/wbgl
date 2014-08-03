<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div id="wrapper">
	<div class="topicPage">
		<!-- start content -->
		<div class="topicContent">
			<div id="knowledgeIndex_header" style="background-color: #40444E;width:750px  ;height: 30px;">
				<span style="color: #FFFFFF;float: right;"> <a id="btn" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save',plain:true" style="color: #FFFFFF" onclick="topic_save();"> 保存 </a> </span>
			</div>
			<br /> <br /> <br />
			<form id="topic_save_form" action="" method="post">

				<strong>标题: </strong><input type="text" name="title" style="width: 300px"> <strong>文章所属分类: </strong> <input id="topic_meunTree_saveUI"
					name="knowledgeMenuId"> <br /> <br />


				<script id="topic_add_editor" name="content" type="text/plain"></script>
			</form>
		</div>
		<%-- <%@ include file="knowledgeMeun.jsp"%> --%>
	</div>



</div>

<script type="text/javascript">
	$('#topic_meunTree_saveUI').combotree({
		valueField : 'id',
		textField : 'name',
		url : '${xymz}/knowledgeMenu_queryTree.action',

	});

	function topic_save() {
	//准备数据
		var title = $('#topic_save_form input[name=title]').val();
		var knowledgeMenuId = $('#topic_save_form input[name=knowledgeMenuId]').val();
		var content = UE.getEditor('topic_add_editor').getContent();
	//保存
		$.post('${xymz}/topic_save.action', {
			'title' : title,
			'content' : content,
			'knowledgeMenuId' : knowledgeMenuId,
		}, function(r) {
				var tab = $('#layout_center_Tabs').tabs('getTab','知识库');
				
				if(tab){
					tab.panel('refresh');
				}else{
					//showUI("知识库","${xymz}/topic_toListUI.action");
				}
			var tab = $('#layout_center_Tabs').tabs('getSelected');
			var index = $('#layout_center_Tabs').tabs('getTabIndex',tab);
			$('#layout_center_Tabs').tabs('close',index);	

			//showUI(r);
			
		}, 'json');

	}

	function showUI(title,url) {
		addTab({
			title :title,
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

	initUeditor("topic_add_editor"); 
	function initUeditor(id) {
		 UE.topic_saveUI(id, {
			initialFrameWidth : 750 //初始化编辑器宽度,默认1000
			,
			initialFrameHeight : 400, //初始化编辑器高度,默认320
			toolbars : [ [ 'source', //源代码
			'anchor', //锚点
			'undo', //撤销
			'redo', //重做
			'bold', //加粗

			'spechars', //特殊字符
			'forecolor', //字体颜色

			'print', //打印
			'preview', //预览
			'horizontal', //分隔线
			'removeformat', //清除格式
			'unlink', //取消链接

			'insertparagraphbeforetable', //"表格前插入行" */

			'fontfamily', //字体
			'fontsize', //字号
			'paragraph', //段落格式
			'simpleupload', //单图上传
			'edittable', //表格属性
			'edittd', //单元格属性
			'link', //超链接

			'fullscreen', //全屏
			'music', //音乐
			'insertvideo', //视频
			'insertimage', //多图上传
			'attachment', //附件
			] ]

		});

	}
</script>

