<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../public/public.jspf"%>
<div class="easyui-layout" style="width:100%;height:100%;">
	<div data-options="region:'north',split:true,border:false" style="height:30px;">
		开始时间: <input id="wb_count_startDate" type="text" class="Wdate" style="height: 12px;width: 140px" onClick="WdatePicker({startDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysWbStartDate:true})" name="startDate" /> 结束时间: <input id="wb_count_endDate" type="text" class="Wdate"
			style="height: 12px;width: 140px" onClick="WdatePicker({endDate:'%y-%M-01 00:00',dateFmt:'yyyy-MM-dd HH:mm',alwaysWbStartDate:true})" name="endDate" /> 查询前<input id="wb_count_number" type="text" name="count" style="width: 40px"> <a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-search',plain:true" onclick="wb_count_searchFun();">查询</a>
	</div>
	<div data-options="region:'west',split:true" style="width:180px;">
		<table id="wb_count_on_line"></table>
	</div>

	<div data-options="region:'center',border:false" style="padding:1px;background:#eee;">

		<div id="wb_container" style="min-width:700px;height:100%"></div>

	</div>
	<div data-options="region:'east',split:true" style="width:180px;">
		<table id="wb_count_visit"></table>

	</div>
</div>
<script type="text/javascript">
	//初始化
	$(function() {
		//当前日期
		var eDate = getDate();
		//前3个月
		var sDate = timeAdd(90);

		var number = 20;
		initdatagrid(sDate, eDate, 'on_line', '线上', number); //线上
		initdatagrid(sDate, eDate, 'visit', '线下', number);//线下

		wbc = {
			chart : {
				type : 'bar'
			},
			title : {
				text : ''
			},
			subtitle : {
				text : ''
			},
			xAxis : { //此处可以是网吧的名称                                                  
				categories : [],
				title : {
					text : null
				}
			},
			yAxis : {
				min : 0,
				title : {
					text : 'Population (millions)',
					align : 'high'
				},
				labels : {
					overflow : 'justify'
				}
			},
			tooltip : {
				valueSuffix : ' millions'
			},
			plotOptions : {
				bar : {
					dataLabels : {
						enabled : true
					}
				}
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'top',
				x : -40,
				y : 100,
				floating : true,
				borderWidth : 1,
				backgroundColor : '#FFFFFF',
				shadow : true
			},
			credits : {
				enabled : false
			},
			series : [

			{
				name : '总数',
				data : []
			}, {
				name : '线上',//线上
				data : []
			}, {
				name : '线下',
				data : []
			} ]
		};

		statisticByWBChart(sDate, eDate, number);

		//查询时间回显
		$('#wb_count_startDate').val(sDate);
		$('#wb_count_endDate').val(eDate);
		$('#wb_count_number').val(number);

	});

	function wb_count_searchFun() {
		//准备数据
		var sDate = $('#wb_count_startDate').val();
		var eDate = $('#wb_count_endDate').val();
		var number = $('#wb_count_number').val();

		initdatagrid(sDate, eDate, 'on_line', '线上', number); //线上
		initdatagrid(sDate, eDate, 'visit', '线下', number);//线下
		statisticByWBChart(sDate, eDate, number);

	}
	//	visit ,on_line
	function initdatagrid(sDate, eDate, type, name, count) {
		$('#wb_count_' + type).datagrid({
			url : '${xymz}/problem_statisticByWBTable.action',
			queryParams : {
				'startDate' : sDate,
				'endDate' : eDate,
				'type' : type,
				'number' : count
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
				field : 'wbName',
				title : '网吧名称',
				width : 100
			}, {
				field : 'count',
				title : name,
				width : 50
			} ] ]
		});
	}


	//1.获取网吧名称 数组
	//2.获取网吧名称对应的 线上数组
	//3.获取网吧名称对应的 线下数组
	function statisticByWBChart(sDate, eDate, number) {
		$.post('${xymz}/problem_statisticByWBChart.action', {
			'startDate' : sDate,
			'endDate' : eDate,
			'number' : number
		}, function(r) {
			wbc.xAxis.categories = r[0];
			wbc.series[0].data = r[1];
			wbc.series[1].data = r[2];
			wbc.series[2].data = r[3];
			var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
			$('#wb_container').highcharts(wbc);
		}, 'json');

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
	Highcharts.theme = {
		colors : [ '#058DC7', '#50B432', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4' ],
		chart : {
			backgroundColor : {
				linearGradient : {
					x1 : 0,
					y1 : 0,
					x2 : 1,
					y2 : 1
				},
				stops : [ [ 0, 'rgb(255, 255, 255)' ], [ 1, 'rgb(240, 240, 255)' ] ]
			},
			borderWidth : 2,
			plotBackgroundColor : 'rgba(255, 255, 255, .9)',
			plotShadow : true,
			plotBorderWidth : 1
		},
		title : {
			style : {
				color : '#000',
				font : 'bold 16px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		subtitle : {
			style : {
				color : '#666666',
				font : 'bold 12px "Trebuchet MS", Verdana, sans-serif'
			}
		},
		xAxis : {
			gridLineWidth : 1,
			lineColor : '#000',
			tickColor : '#000',
			labels : {
				style : {
					color : '#000',
					font : '11px Trebuchet MS, Verdana, sans-serif'
				}
			},
			title : {
				style : {
					color : '#333',
					fontWeight : 'bold',
					fontSize : '12px',
					fontFamily : 'Trebuchet MS, Verdana, sans-serif'
				}
			}
		},
		yAxis : {
			minorTickInterval : 'auto',
			lineColor : '#000',
			lineWidth : 1,
			tickWidth : 1,
			tickColor : '#000',
			labels : {
				style : {
					color : '#000',
					font : '11px Trebuchet MS, Verdana, sans-serif'
				}
			},
			title : {
				style : {
					color : '#333',
					fontWeight : 'bold',
					fontSize : '12px',
					fontFamily : 'Trebuchet MS, Verdana, sans-serif'
				}
			}
		},
		legend : {
			itemStyle : {
				font : '9pt Trebuchet MS, Verdana, sans-serif',
				color : 'black'
			},
			itemHoverStyle : {
				color : '#039'
			},
			itemHiddenStyle : {
				color : 'gray'
			}
		},
		labels : {
			style : {
				color : '#99b'
			}
		},
		navigation : {
			buttonOptions : {
				theme : {
					stroke : '#CCCCCC'
				}
			}
		}
	};
</script>