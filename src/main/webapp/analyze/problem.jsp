<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../public/public.jspf"%>

<div class="easyui-layout" style="width:100%;height:100%;">
	<div data-options="region:'north',split:true,border:false" style="height:30px;">
		开始时间: <input id="problem_classifyCount_startDate" type="text" class="Wdate" style="height: 12px;width: 140px" onClick="WdatePicker({startDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" name="startDate" /> 结束时间: <input id="problem_classifyCount_endDate" type="text"
			class="Wdate" style="height: 12px;width: 140px" onClick="WdatePicker({endDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" name="endDate" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
			onclick="problem_classifyCount_searchFun();">查询</a>
	</div>

	<div data-options="region:'center',border:false" style="padding:1px;background:#eee;">

		<div id="problem_container" style="min-width:100%;height:100%"></div>

	</div>
	<div data-options="region:'east',split:true" style="width:400px;">
		<table id="classify_count"></table>
		<div style="color: red">
			注意:<br /> *这里查询的都是父分类,双击表格里的父分类即可查询相应详细子分类 <br /> *如果没有子分类,双击后,只会查询出父分类自己
		</div>
	</div>
</div>

<!-- 查看更详细 -->
<div id="problem_mx_dialog">
	<div class="easyui-layout" style="width:100%;height:100%;">


		<div data-options="region:'center',border:false" style="padding:1px;background:#eee;">
			<div id="problem_mx_container" style="min-width:100%;height:100%"></div>


		</div>
		<div data-options="region:'east',split:true" style="width:300px;">
			<table id="classify_mx_count"></table>

		</div>
	</div>

</div>





<script type="text/javascript">
	function mx_table(sDate, eDate, text) {

		var clientWidth = document.body.clientWidth;
		var clientHeight = document.body.clientHeight;
		if (clientWidth > 1000) {
			clientWidth = 1000;
		}
		if (clientHeight > 700) {
			clientHeight = 650;
		}

		$('#problem_mx_dialog').dialog({
			title : text,
			height : clientHeight,
			width : clientWidth,
			resizable : true,
			maximizable : true,
			//	fit : true,
			closed : true,
			cache : false,
			//href: 'get_content.php',    
			modal : true
		});

		$('#classify_mx_count').datagrid({
			url : '${xymz}/problem_statisticByClassifyByparentTable.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate,
				'classifyName' : text
			},
		//	pagination : true,//分页
			striped : true,//斑马线
			fitColumns : true,//自动增大减小
			singleSelect : true,
			onDblClickCell : function(rowIndex, field, value) {
				sDate = $('#problem_classifyCount_startDate').val();
				eDate = $('#problem_classifyCount_endDate').val();
				mx_table(sDate, eDate, value);
			},
			columns : [ [ {
				field : 'classifyText',
				title : '分类名称',
				width : 200
			}, {
				field : 'count',
				title : '数量',
				width : 50
			} ] ]
		});

		$.post('${xymz}/problem_statisticByClassifyByparentChart.action', {
			'startDate' : sDate,
			'endDate' : eDate,
			'classifyName' : text
		}, function(r) {
			classifyCount.series[0].data = r;
			$('#problem_mx_container').highcharts(classifyCount);
		}, 'json');

		$('#problem_mx_dialog').dialog('open');

	}

	var classifyCount;

	function problem_classifyCount_searchFun() {
		sDate = $('#problem_classifyCount_startDate').val();
		eDate = $('#problem_classifyCount_endDate').val();

		//执行图表查询
		initClassifyChart(sDate, eDate);

		//执行表格查询
		$('#classify_count').datagrid({
			url : '${xymz}/problem_claasifyAndCount.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate
			},

		});

	}

	$(function() {
		classifyCount = {
			chart : {
				plotBackgroundColor : "#E8F2FE",
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				text : ''
			},
			tooltip : {
				pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : true,
						color : '#000000',
						connectorColor : '#000000',
						format : '<b>{point.name}</b>: {point.percentage:.1f} %'
					}
				}
			},
			series : [ {
				type : 'pie',
				name : '百分比',
				data : []

			} ]
		};

		//当前日期
		var eDate = getDate();
		//前3个月
		var sDate = timeAdd(90);
		$('#problem_classifyCount_startDate').val(sDate);
		$('#problem_classifyCount_endDate').val(eDate);
		//执行图表查询
		initClassifyChart(sDate, eDate);

		//执行表格查询
		initClassifyTitle(sDate, eDate);

	});

	function initClassifyChart(sDate, eDate) {
		$.post('${xymz}/problem_statisticsByClassify.action', {
			'startDate' : sDate,
			'endDate' : eDate,
		}, function(r) {
			classifyCount.series[0].data = r;
			$('#problem_container').highcharts(classifyCount);
		}, 'json');

	}

	function initClassifyTitle(sDate, eDate) {
		$('#classify_count').datagrid({
			url : '${xymz}/problem_claasifyAndCount.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate
			},
			//pagination : true,//分页
			striped : true,//斑马线
			fitColumns : true,//自动增大减小
			singleSelect : true,
			onDblClickCell : function(rowIndex, field, value) {
				sDate = $('#problem_classifyCount_startDate').val();
				eDate = $('#problem_classifyCount_endDate').val();
				mx_table(sDate, eDate, value);
			},
			columns : [ [ {
				field : 'classifyText',
				title : '分类名称',
				width : 200
			}, {
				field : 'count',
				title : '数量',
				width : 50
			} ] ]
		});
	}

	function getDate() {
		if (true) {
			var newTime = new Date();
			var year = newTime.getFullYear();
			var month = newTime.getMonth() + 1;
			var date = newTime.getDate();
			var hours = newTime.getHours();
			var minutes = newTime.getMinutes();
			var fullDate = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes;

			return fullDate;

		}
	}

	//js时间的减法
	function timeAdd(n) {
		//-n天  
		var interval = n * 24 * 60 * 60 * 1000;
		var now = new Date();
		now.setTime(now.getTime() - interval);

		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hours = now.getHours();
		var minutes = now.getMinutes();
		return year + "-" + month + "-" + date + " " + hours + ":" + minutes;
	}

	//将字符串转换为js的时间对象，  字符串格式yyyy-MM-dd HH:ss:mm       
	function stringToJsTime_(timeString) {
		var y = timeString.substring(0, 4);
		var m = timeString.substring(5, 7) - 1;
		var d = timeString.substring(8, 10);
		var h = timeString.substring(11, 13);
		var mm = timeString.substring(14, 16);
		var time = new Date(y, m, d, h, mm, 0);
		return time;
	}
</script>
