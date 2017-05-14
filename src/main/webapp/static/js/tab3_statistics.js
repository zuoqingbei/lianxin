/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧tab3数据统计
 */
var labTypeCode;//实验室类型
function loadTab3Data(){
	//一次合格率  整体统计
	findOrderPassForAllAjax();
	//数据结果 一次合格率 整机 模块
	findOrderPassForProAjax("myChart41","整机");
	findOrderPassForProAjax("myChart42","模块");
	//数据结果 订单类别 整机
	orderTypeAjax("myChart39","整机",60);
	//数据结果 订单类别 模块
	orderTypeAjax("myChart40","模块",320);
	//findOrderMonthTypeForProduct();
	// 按照产线统计某年整体订单及时率  数据结果 订单及时率 雷达图
	findOrderYearRateForProductAjax();
	//获取某一年订单整体及时率
	orderYearRateAjax();
	//按照产线统计某年各月份详细订单及时率myChart46  数据结果 订单及时率 折线图
	findOrderMonthRateForProductAjax();
}
//一次合格率  整体统计-整机 模块
function findOrderPassForAllAjax(){
	$.post(contextPath+'/lab/findOrderPassForAllAjax',{"labTypeCode":labTypeCode},function(data){
		$("#once_pass_rate_zj").html(data[0].rate+"%");
		$("#once_pass_rate_mk").html(data[1].rate+"%");
	})
}
//数据结果 一次合格率 整机 模块
function findOrderPassForProAjax(mychartIds,desName){
	$.post(contextPath+'/lab/findOrderPassForProAjax',{"labTypeCode":labTypeCode,"desName":desName,"name":"合格率"},function(data){
		var myChart = echarts.init(document.getElementById(mychartIds));
		right_echarts.push(myChart);
		myChart.setOption(getAreaEcharts());
		myChart.setOption({
		    legend: {
		        show: true,
		        data: [desName],
		        itemWidth: 6, //图例标记的图形宽度
		        itemHeight: 6 //图例标记的图形高度
		    },
		    grid: {
		        x: 5,
		        y: 28
		    },
		    xAxis: [
		        {
		            name: '',
		            data:tab3PassLengend(data)
		        }
		    ],
		    yAxis: [
		        {
		            name: '',
		            max:100
		        }
		    ],
		    series: [
		        {
		            name: desName,
		            type: 'line',
		            // areaStyle: {normal: {}},
		            data: tab3RateData(data),
		            itemStyle: {
		                normal: {
		                    color: "#66ccff"
		                }
		            },
		            symbolSize:2,
		            lineStyle:{
		                normal:{
		                    width:1
		                }
		            },

		        }
		    ]

		});
	})
}
function orderTypeAjax(myChartIds,desName,divisor){
	$.post(contextPath+'/lab/findOrderMonthTypeForProductAjax',{"labTypeCode":labTypeCode,"desName":desName},function(res){
		var data = [];
		var yData=[];//产线
		var xData=[];//类型
		//准备数据
		$.each(res,function(index,item){
			yData.push(item[0].product_line_name);
			$.each(item,function(ind,it){
				if(index==0){
					xData.push(it.name);
				}
				//拼接数据
				var value=[index,ind,parseInt(it.num)/divisor];//暂时数量除以divisor
				data.push(value);
			});
		});
		//生成option
		var myChart = echarts.init(document.getElementById(myChartIds));
		right_echarts.push(myChart);
		myChart.setOption(getScatterEcharts());
		data = data.map(function (item) {
		    return [item[1], item[0], item[2]];
		});
		myChart.setOption({
		    color: ["#66ccff"],
		    grid: {
		        top: 2,
		        left: 7,
		        bottom: 10,
		        right: 5,
		        // containLabel: true
		    },
		    legend: {
		        show: false
		    },
		    tooltip: {
		        trigger: 'item',
		        axisPointer: {
		            type: 'cross',
		            label: {
		                backgroundColor: '#234f65'
		            }
		        },
		        position: 'top',
	            formatter: function (params) {
	                return xData[params.value[0]] + ' 中 ' + yData[params.value[1]]+":"+(parseInt(params.value[2]*divisor));
	            }
		    },
		    xAxis: {
		        data: xData,

		        axisLine: {
		            show: false
		        },
		        axisLabel: {
		            show: true,
		            // rotate: 30,
		            textStyle: {
		                color: '#66ccff',
		                fontSize: 12*bodyScale,

		            },
		            // rotate:30,
		        },
		        axisTick: {
		            show: false,
		            alignWithLabel: true,
		            lineStyle: {
		                color: '#66ccff'
		            }
		        },
		        splitLine: {  //刻度线
		            show: true,
		            lineStyle: {
		                color: '#234f65'
		            }
		        },
		    },
		    yAxis: {
		        data: yData,
		        axisLine: { //坐标轴
		            show: false
		        },
		        axisLabel: {   //坐标值
		            show: true,
		            textStyle: {
		                color: '#66ccff',
		                fontSize: 12*bodyScale
		            }
		        },
		        axisTick: {  //刻度值
		            show: false,
		        },
		    },
		    series: [{
		        data: data
		    }]
		});
		//END
		
	})
}


//按照产线统计某年各月份详细订单及时率myChart46  数据结果 订单及时率 折线图
function findOrderMonthRateForProductAjax(){
	$.post(contextPath+'/lab/findOrderMonthRateForProductAjax',{"labTypeCode":labTypeCode},function(data){
		var myChart46 = echarts.init(document.getElementById("myChart46"));
		right_echarts.push(myChart46);
		myChart46.setOption(getLineEcharts());
		myChart46.setOption({
		    color: ['#66ccff', '#00e673', '#4397f7', '#ff9933', '#66ffcc', '#ffff99', '#ff6666'],
		    legend: {
		        show: true,
		        data: tab3Lengend(data),
		        itemWidth: 2,  //图例标记的图形宽度
		        itemHeight: 3, //图例标记的图形高度
		    },
		    grid: {
		        x: "15%",
		        x2: "10%",
		        y: '25%',
		        y2: "10%"
		    },
		    xAxis: [
		        {
		            name: '月份',
		            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
		        }
		    ],
		    yAxis: [
		        {
		            name: '及时率/%',
		            max:100
		        }
		    ],
		    series:getTab3Serise(data)

		});
	})
}
// 按照产线统计某年整体订单及时率  数据结果 订单及时率 雷达图
function findOrderYearRateForProductAjax(){
	$.post(contextPath+'/lab/findOrderYearRateForProductAjax',{"labTypeCode":labTypeCode},function(data){
		var myChart45 = echarts.init(document.getElementById("myChart45"));
		right_echarts.push(myChart45);
		myChart45.setOption(getRadarEcharts());
		myChart45.setOption({
		    legend: {
		        show: true,
		        orient: ' vertical',  //布局  纵向布局
		        left: 10,
		        bottom: 5,
		        itemWidth: 10,  //图例标记的图形宽度
		        itemHeight: 2, //图例标记的图形高度
		        textStyle: {    //图例文字的样式
		            color: '#66ccff',
		            fontSize: 12*bodyScale
		        },
		        data: ['2016年', '2017年']
		    },

		    calculable: true,
		    polar: [
		        {
		            indicator:tab3IndicatorData(data),
		            center: ['50%', '55%'],
		            radius: '65%',
		            name: {
		                formatter: '{value}',
		                textStyle: {
		                    color: '#66ccff',
		                    fontSize: 10*bodyScale,
		                }
		            },
		            splitLine: {
		                lineStyle: {
		                    color: 'rgba(102,204,255,0.5)',
		                }
		            },
		            axisLine: {
		                lineStyle: {
		                    color: 'rgba(0,0,0,0)'
		                }
		            },
		            splitArea: {
		                show: false
		            },
		            textStyle: {
		                color: '#66ccff'
		            },
		        }

		    ],
		    series: [
		        {
		            type: 'radar',
		            data: [
		                {
		                    value: tab3DataData(data,0),
		                    name: '2016年',
		                    itemStyle: {
		                        normal: {
		                            color: '#66ccff',
		                            areaStyle: {
		                                color: 'rgba(102,204,255,0.2)',
		                            },
		                        }
		                    }
		                },
		                {
		                    value:  tab3DataData(data,1),
		                    name: '2017年',
		                    itemStyle: {
		                        normal: {
		                            color: '#ff9933',
		                            areaStyle: {
		                                color: 'rgba(255,153,51,0.2)',
		                            },
		                        }
		                    }
		                }
		            ]
		        }
		    ]
		});
	})
}
//获取某一年订单整体及时率
function orderYearRateAjax(){
	$.post(contextPath+'/lab/orderYearRateAjax',{"labTypeCode":labTypeCode},function(data){
		$("#order_year_rate_2016").html(data[0].rate+"%");
		$("#order_year_rate_2017").html(data[1].rate+"%");
		$("#order_year_rate_up_2017").html((parseFloat(data[1].rate)-parseFloat(data[0].rate)).toFixed(2)+"%");
	})
}

function tab3IndicatorData(da){
	var data=da[0];
	var data2=da[1];
	//先找出最大值
	var maxNum=100;
	/*for(var i=0;i<data.length;i++) {
		if(parseInt(data[i].all_count)>parseInt(maxNum)){
			maxNum=parseInt(data[i].all_count);
		}
	}
	for(var i=0;i<data2.length;i++) {
		if(parseInt(data2[i].all_count)>parseInt(maxNum)){
			maxNum=parseInt(data2[i].all_count);
		}
	}*/
	var indicatorData = [];
	for(var i=0;i<data.length;i++) {
		var obj=new Object();
		obj.max=maxNum;
		obj.text=data[i].name;
		indicatorData.push(obj);
	}
	return indicatorData;
}

function tab3DataData(data,index){
	var indicatorData = [];
	for(var i=0;i<data[index].length;i++) {
		indicatorData.push(data[index][i].rate);
	}
	return indicatorData;
}
function tab3RateData(data){
	var indicatorDataTab3 = [];
	for(var i=0;i<data.length;i++) {
		var num=data[i].rate;
		indicatorDataTab3.push(num);
	}
	return indicatorDataTab3;
}
function tab3Lengend(data){
	var legnend=[];
	$.each(data,function(index,item){
		legnend.push(item[0].product_line_name);
	});
	return legnend;
}
function tab3PassLengend(data){
	var legnend=[];
	$.each(data,function(index,item){
		legnend.push(item.product_line_name);
	});
	return legnend;
}
function getTab3Serise(data){
	var series=[];
	$.each(data,function(index,item){
		var it={
		            name: item[0].product_line_name,
		            type: 'line',
		           /* stack: '总量',*/
		            lineStyle:{
		                normal:{
		                    width:1
		                }
		            },
		            symbolSize:2,
		            data: tab3RateData(item),

		        };
		series.push(it);
	})
	return series;
}


