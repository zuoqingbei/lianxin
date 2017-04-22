/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 数据统计
 */
var field="field";
var labType="",productCode="";
//实验室数量统计
function labNumStatis(){
	$.post(contextPath+'/lab/labNumStatisAjax',{},function(data){
		$("#lab_all_count").html(data);
	})
}

//实验室区域数量统计：大洲 国家
function labAreaSpread(){
	$.post(contextPath+'/lab/labSpreadAjax',{},function(data){
		$("#area_num").html(data.areanum);
		$("#country_num").html(data.countrynum);
	})
}

//产线维度实验室数量统计
function proLineStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"product_code"},function(data){
		var nums=[];
		$("#pro_line").html("");
		$.each(data,function(index,item){
			$("#pro_line").append('<li><span class="icon"></span>'+item.name+'<span class="number">'+item.count+'</span></li>');
			nums.push(item.count);
		});
		flatLTnumberShow(nums);
	})
}


//按照实验室四大类统计数量
function labTypeStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"lab_type_code"},function(data){
			myChartFlatLB.clear();
		 	myChartFlatLB.setOption(getLineEcharts());
		    myChartFlatLB.setOption({
		        tooltip: {
		            trigger: 'axis'
		        },
		        legend: {
		            show: false,
		            data: ['实验室统计']
		        },
		        textStyle: {
		            color: "#6cf",
		            fontSize: "60%"
		        },
		        grid: {
//		            show:true,
		            x: "25%",
		            x2: "15%",
		            y: "40%",
		            y2: "12%"
		        },
		        xAxis: [
		            {
		                name: "",
		                type: 'category',
		                data: statisticLengend(data),
		                axisLabel: {
		                    textStyle: {
		                        color: "#66ccff",
		                        fontSize: "60%"
		                    }
		                },
		                axisLine: {
		                    lineStyle: {
		                        width: 0
		                    }
		                },

		                offset: 5

		            }
		        ],
		        yAxis: [
		            {
		                name: "数量 ",
		                type: 'value',
		                axisLabel: {
		                    textStyle: {
		                        color: "#66ccff",
		                        fontSize: "60%"
		                    }
		                },
		                nameGap: 10,
		                offset: 5,
		              max: 300,
		                splitLine: {  //刻度线
		                    show: true,
		                    lineStyle: {
		                        color: "#234f65"
		                    }
		                },
		            }],
		        series: [
		            {
		                name: '',
//		                type:'bar',
		                type: 'pictorialBar',
//		                barGap: '10%',
		                symbolRepeat: true,
		                symbolSize: ['40%', '20%'],
		                data: statisticSeriesDataData(data)
		            }
		        ]
		    });
	})
}

//按照实验室实验室性质统计数量
function labPropertiesStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"properties_code","labType":labType,"productCode":productCode},function(data){
		myChartFlatRT.clear();
		//右上角的图表
	    myChartFlatRT.setOption(getLineEcharts());
	    myChartFlatRT.setOption({
	        tooltip: {
	            trigger: 'axis'
	        },
	        legend: {
	            show: false,
	            data: ['专业能力']
	        },
	        textStyle: {
	            color: "#6cf",
	            fontSize: "60%"
	        },
	        grid: {
//	            show:true,
	            x: "25%",
	            x2: "15%",
	            y2: "12%"
	        },
	        xAxis: [
	            {
	                name: "数量",
	                type: 'value',
	                axisLabel: {
	                    textStyle: {
	                        color: "#66ccff",
	                        fontSize: "60%"
	                    }
	                },
	                axisLine: {
	                    lineStyle: {
	                        width: 0
	                    }
	                },
	                splitLine: {  //刻度线
	                    show: true,
	                    lineStyle: {
	                        color: "#234f65"
	                    }
	                },
	                max: 25,
	                offset: 5

	            }
	        ],
	        yAxis: [
	            {
	                name: "",
	                type: 'category',
	                data: statisticLengend(data),
	                axisLabel: {
	                    textStyle: {
	                        color: "#66ccff",
	                        fontSize: "60%"
	                    }
	                },
	                nameGap: 10,
	                offset: 5,
//	                minInterval: .5
	            }
	        ],
	        series: [
	            {
	                name: '专业能力',
//	                type:'bar',
	                type: 'pictorialBar',
//	                barGap: '10%',
	                symbolRepeat: true,
	                symbolSize: ['40%', '80%'],
	                data: statisticSeriesDataData(data)
	            }
	        ]
	    });
	})
}

//按照实验室生命周期（可开展实验）统计数量
function labLifeCycleStatis(){
	$.post(contextPath+'/lab/labCarryNumStatisAjax',{"labType":labType,"productCode":productCode},function(data){
		myChartFlatRB.clear();
		myChartFlatRB.setOption(getAreaEcharts());
	    myChartFlatRB.setOption({
	        tooltip: {
	            trigger: 'axis'
	        },
	        legend: {
	            show: false,
	            data: ['试验目的']
	        },
	        textStyle: {
	            color: "#6cf",
	            fontSize: "60%"
	        },
	        grid: {
//	            show:true,
	            x: "25%",
	            x2: "10%",
	            y: "48%",
	            y2: "12%"
	        },
	        xAxis: [
	            {
	                name: "",
	                type: 'category',
	                data: statisticLengend(data),
	                axisLabel: {
	                    textStyle: {
	                        color: "#66ccff",
	                        fontSize: "50%"
	                    }
	                },
	                axisLine: {
	                    lineStyle: {
	                        width: 0
	                    }
	                },
	                splitLine: {  //刻度线
	                    show: false,
	                    lineStyle: {
	                        color: "#234f65"
	                    }
	                },
	                offset: 5

	            }
	        ],
	        yAxis: [
	            {
	                name: "数量 ",
	                type: 'value',

	                axisLabel: {
	                    textStyle: {
	                        color: "#66ccff",
	                        fontSize: "60%"
	                    }
	                },
	                nameGap: 10,
	                offset: 5,
	                max: 500
//	                minInterval: .5
	            }
	        ],
	        series: [
	            {
	                name: '',
	                type: 'line',
	                symbolRepeat: true,
	                symbolSize: ['40%', '80%'],
	                itemStyle: {
	                    normal: {
	                        color: "66ccff"
	                    }
	                },
	                areaStyle: {
	                    normal: {
	                        color: {
	                            type: 'linear',
	                            x: 0,
	                            y: 0,
	                            x2: 0,
	                            y2: 1,
	                            colorStops: [{
	                                offset: 0, color: 'rgba(102,204,255,1)' // 0% 处的颜色
	                            }, {
	                                offset: 1, color: 'rgba(102,204,255,.1)' // 100% 处的颜色
	                            }],
	                            globalCoord: false // 缺省为 false
	                        }
	                    },
	                },

	                data: statisticSeriesDataData(data)
	            }
	        ]
	    });
	})
}

//实验室联通数据统计
function labLinkStatis(){
	$.post(contextPath+'/lab/labLinkAjax',{},function(data){
		$("#link_lab_all_count").html(data.all_num);
		$("#linked_status_num").html(data.link_num);
		$("#link_status_rate").html(data.link_rate);
	})
}
function statisticLengend(data){
	var legnend=[];
	$.each(data,function(index,item){
		legnend.push(item.name);
	});
	return legnend;
}
function statisticSeriesDataData(data){
	var series=[];
	$.each(data,function(index,item){
		var obj=new Object();
		obj.value=item.count;
		obj.symbol=pathSymbols.bar_chip;
		series.push(obj);
	});
	return series;
}