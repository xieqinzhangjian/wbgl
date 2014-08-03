<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>

<div class="easyui-layout" style="width:100%;height:100%;">
	<div data-options="region:'north',split:true,border:false" style="height:30px;">
		开始时间: <input id="user_disposeCount_startDate" type="text" class="Wdate" style="height: 12px;width: 140px" onClick="WdatePicker({startDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" name="startDate" /> 结束时间: <input id="user_disposeCount_endDate" type="text"
			class="Wdate" style="height: 12px;width: 140px" onClick="WdatePicker({endDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysUseStartDate:true})" name="endDate" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="user_userCount_searchFun();">查询</a>
	</div>

	<div data-options="region:'center',border:false" style="padding:1px;background:#eee;">
		<div id="dispose_countChart_on_line" style="min-width:60%;height:50%"></div>
		<div id="dispose_countChart_visit" style="min-width:60%;height:50%"></div>

	</div>
	<div data-options="region:'east',split:true" style="width:220px;">

		<table id="dispose_countTable_on_line"></table>
		<br /> <br /> <br /> <br /> <br />

		<table id="dispose_countTable_visit"></table>
	</div>
</div>
<script type="text/javascript">
	function user_userCount_searchFun() {
		var sDate = $('#user_disposeCount_startDate').val();
		var eDate = $('#user_disposeCount_endDate').val();

		userTableFn(sDate, eDate, 'on_line', '线上'); //线上
		userTableFn(sDate, eDate, 'visit', '线下');//线下

		userChartFn(sDate, eDate, 'on_line', '线上');
		userChartFn(sDate, eDate, 'visit', '线下');

	}

	$(function() {
		//当前日期
		var eDate = getDate();
		//前3个月
		var sDate = timeAdd(90);
		initUserCountTitle(sDate, eDate, 'on_line', '线上'); //线上
		initUserCountTitle(sDate, eDate, 'visit', '线下');//线下
		initUserCountChart(sDate, eDate, 'on_line', '线上');
		initUserCountChart(sDate, eDate, 'visit', '线下');

		//查询时间回显
		$('#user_disposeCount_startDate').val(sDate);
		$('#user_disposeCount_endDate').val(eDate);

	});

	//初始化表格
	function initUserCountTitle(sDate, eDate, type, name) {
		$("#dispose_countTable_" + type + "").datagrid({
			url : '${xymz}/problem_statisticByUserTable.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate,
				'type' : type
			},
			striped : true,//斑马线
			fitColumns : true,//自动增大减小
			singleSelect : true,
			columns : [ [ {
				field : 'userName',
				title : '员工姓名',
				width : 100
			}, {
				field : 'count',
				title : name + '受理量',
				width : 50
			} ] ]
		});

	}

	//初始化饼图
	function initUserCountChart(sDate, eDate, type, name) {
		$(function() {
			uc = {
				chart : {
					plotBackgroundColor : "#E8F2FE",
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : "",
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
					name : 'Browser share',
					data : []

				} ]
			};

			$.post('${xymz}/problem_statisticByUserChart.action', {
				'startDate' : sDate,
				'endDate' : eDate,
				'type' : type
			}, function(r) {
				uc.series[0].data = r;
				$("#dispose_countChart_" + type + "").highcharts(uc);
			}, 'json');

		});
	}

	function userChartFn(sDate, eDate, type, name) {
		$.post('${xymz}/problem_statisticByUserChart.action', {
			'startDate' : sDate,
			'endDate' : eDate,
			'type' : type
		}, function(r) {
			uc.series[0].data = r;
			$("#dispose_countChart_" + type + "").highcharts(uc);
		}, 'json');
	}


	function userChartFn(sDate, eDate, type, name) {
		$.post('${xymz}/problem_statisticByUserChart.action', {
			'startDate' : sDate,
			'endDate' : eDate,
			'type' : type
		}, function(r) {
	 		console.debug(r);
		}, 'json');
	}

	function userTableFn(sDate, eDate, type, name) {
		$("#dispose_countTable_" + type + "").datagrid({
			url : '${xymz}/problem_statisticByUserTable.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate,
				'type' : type
			},
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