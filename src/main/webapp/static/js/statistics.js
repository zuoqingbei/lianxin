/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 数据统计
 */
var field="field";
var labType="",productCode="";
function reloadLeftData(){
	 //实验室数量统计
    labNumStatis();
    //产线维度实验室数量统计
    proLineStatis();
    //实验室区域数量统计：大洲 国家
	labAreaSpread();
	//按照实验室四大类统计数量
    labTypeStatis();
	//专业能力-实验室性质
	labPropertiesStatis();
	//实验目的--可开展实验类型
	labLifeCycleStatis();
	//labCarouselInfo();
}
//实验室信息轮播数据
/*function labCarouselInfo(){
	$.post(contextPath+"/lab/labShowFlatMapAjax",{},function(data){
		console.log(data)
	})
}*/
//实验室数量统计
function labNumStatis(){
	$.post(contextPath+'/lab/labNumStatisAjax',{},function(data){
		$("#lab_all_count_1").html(data);
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
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"product_code","labType":labType,"sort":"asc"},function(data){
		var nums=[];
		$("#pro_line").html("");
		$.each(data,function(index,item){
			$("#pro_line").append('<li><span class="icon"></span>'+item.name+'<span class="number">'+item.count+'</span></li>');
			nums.push(item.count);
		});
		flatLTnumberShow(nums);
	})
}

//球形图 右上角实验室数量按照四大类展示
function worldTyleEchart(data){
	//球形地图右上角的环形图表
	console.log("datadatadatadatadatadatadata")
	console.log(data)
	var num2=standardSeriesData(data,"研发实验室");
	var num3=standardSeriesData(data,"中心实验室");
	var num4=standardSeriesData(data,"工厂实验室");
	var num5=standardSeriesData(data,"模块商实验室");
	var allNum=parseInt(num2)+parseInt(num3)+parseInt(num4)+parseInt(num5);
	var myChartSphereRT = echarts.init($("#l").find(".sphere-right-top .myChart")[0]);
	myChartSphereRT.setOption(getYuanhuan());
	var labelTop = {
	    normal: {
	        color: '#064f66',
	        label: {
	            show: true,
	            position: 'center',
//	                模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。
	            formatter: function (params) {
	                return allNum -  params.value
	            },
	            textStyle: {
	                fontSize:16,
	                color: "#f90",
	                baseline: 'bottom'
	            }
	        },
	        labelLine: {
	            show: false
	        }
	    }
	};
	var labelFromatter = {
	    normal: {
	        label: {
	            formatter: function (params) {
	                return 100 - ( params.value)  + '%'
	            },
	            textStyle: {
	                baseline: 'bottom'
	            }
	        }
	    }
	};
	var labelBottom = {
	    normal: {
	        color: "#6cf",
	        label: {
	            show: true,
	            position: 'center',
	            formatter : '{b}',
	            textStyle: {
//	                    color:"#f90",
	                fontSize: "60%",
	                baseline: 'top'
	            }
	        },
	        labelLine: {
	            show: false
	        }
	    },
	    emphasis: {
	        color: '#6cf'
	    }
	};
	var radius = [30, 39];
	myChartSphereRT.setOption({
	    textStyle: {
	        color: '#6cf',
	        fontSize: "60%"
	    },
	    grid: {
//	            show:true,
//	         x: "25%",
//	         x2: "15%",
//	         y2: "12%"
	    },
	    series: [
	        {
	            type: 'pie',
	            center: ['30%', '35%'],
	            radius: radius,
	            x: '0%', // for funnel
	            data: [
	                {name: 'other', value: allNum-num2, itemStyle: labelTop},
	                {name: '研发实验室', value: num2, itemStyle: labelBottom}
	            ]
	        },
	        {
	            type: 'pie',
	            center: ['70%', '35%'],
	            radius: radius,
	            x: '20%', // for funnel
	            itemStyle: labelFromatter,
	            data: [
	                {name:'other', value:allNum-num3, itemStyle : labelTop},
	                {name: '中心实验室', value: num3, itemStyle: labelBottom}
	            ]
	        },
	        {
	            type: 'pie',
	            center: ['30%', '73%'],
	            radius: radius,
	            x: '40%', // for funnel
	            itemStyle: labelFromatter,
	            data: [
	                {name: 'other', value: allNum-num4, itemStyle: labelTop},
	                {name: '工厂实验室', value: num4, itemStyle: labelBottom}
	            ]
	        },
	        {
	            type: 'pie',
	            center: ['70%', '73%'],
	            radius: radius,
	            x: '80%', // for funnel
	            itemStyle: labelFromatter,
	            data: [
	                {name: 'other', value: allNum-num5, itemStyle: labelTop},
	                {name: '模块实验室', value: num5, itemStyle: labelBottom}
	            ]
	        }
	    ]
	});
}

//按照实验室四大类统计数量
function labTypeStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"lab_type_code","productCode":productCode},function(data){
		worldTyleEchart(data)
		myChartFlatLB.resize();
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
		        	 x2: "15%",
		             y: "15%",
		             y2: "14%"
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
		                    },
		                    rotate:0
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

//按照实验室实验室性质统计数量  ---全方位测试
function labPropertiesStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"properties_code","labType":labType,"productCode":productCode},function(data){
		myChartFlatRT.resize();
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
	             x2: "20%",
	             y:"15%"
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
		myChartFlatRB.resize();
		myChartFlatRB.clear();
		myChartFlatRB.setOption(getAreaEcharts());
		var colors = ['#00e673', '#66ccff'];
		console.log(statisticLengend(data))
	    myChartFlatRB.setOption({
	        tooltip: {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'cross'
	            }
	        },
	        grid: {
	            left:"10%",
	            right: '10%',
	            top:"30%"
	        },
	        legend: {
	        	   data:['实验室数量','检测订单量'],
	               textStyle:{
	                   fontSize: "50%"
	               },
	        },
	        xAxis: [
	            {
	                name: "",
	                type: 'category',
	                axisTick: {
	                    alignWithLabel: true
	                },
	                axisLabel: {
	                    textStyle: {
	                        fontSize: "70%"
	                    }
	                },
	                splitLine: {  //刻度线
	                    show: false,
	                },
	                data: statisticLengend(data)
	                

	            }
	        ],
	        yAxis: [
	            {
	            	type: 'value',
	            	//name: '实验室数量',
	                min: 0,
	                max: 500,
	                axisLine: {

	                    lineStyle: {
	                        color: colors[0]
	                    }
	                },
	                axisLabel: {
	                    textStyle: {
	                        fontSize: "60%"
	                    }
	                    // formatter: '{value} ml'
	                },
	                splitLine: {  //刻度线
	                    show: true,
	                    lineStyle: {
	                        color: "#234f65"
	                    }
	                }
	            },{
	            	 type: 'value',
	                 //name: '检测订单量',
	                 min: 0,
	                 max: 500,
	                 position: 'right',
	                 axisLine: {
	                     lineStyle: {
	                         color: colors[1]
	                     }
	                 },
	                 axisLabel: {
	                     textStyle: {
	                         fontSize: "60%"
	                     }
	                     // formatter: '{value} ml'
	                 },
	                 splitLine: {  //刻度线
	                     show: true,
	                     lineStyle: {
	                         color: "#234f65"
	                     }
	                 }
	            }
	        ],
	        series: [
	            {	 name:'实验室数量',
	                type: 'line',
	                max: 500,
	                areaStyle: {
	                    normal: {
	                        color: {
	                            type: 'linear',
	                            x: 0,
	                            y: 0,
	                            x2: 0,
	                            y2: 1,
	                            colorStops: [{
	                                offset: 0, color: 'rgba(102,204,255,.1)' // 0% 处的颜色
	                            }, {
	                                offset: 1, color: 'rgba(102,204,255,1)' // 100% 处的颜色
	                            }],
	                            globalCoord: false // 缺省为 false
	                        }
	                    },
	                },

	                data: statisticSeriesDataData(data)
	            },
	            {
	                name:'检测订单量',
	                type:'line',
	                yAxisIndex: 1,
	                data:[26, 59, 90, 120, 126, 150, 175,220,230],

	                areaStyle: {
	                    normal: {
	                        color: {
	                            type: 'linear',
	                            x: 0,
	                            y: 0,
	                            x2: 0,
	                            y2: 1,
	                            colorStops: [{
	                                offset: 0, color: 'rgba(96,192,255,.1)' // 0% 处的颜色
	                            }, {
	                                offset: 1, color: 'rgba(96,192,255,1)' // 100% 处的颜色
	                            }],
	                            globalCoord: false // 缺省为 false
	                        }
	                    },
	                },
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