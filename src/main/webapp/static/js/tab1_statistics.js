/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧数据统计
 */
function loadTab1Data(){
	findPersonStatusTab1Ajax(1);
	findPersonStatusTab1Ajax(2);
	findPersonStatusTab1Ajax(3);
	 //标准状态
    standardStatus();
    //能力状态
    abilityStatus();
    //订单及时率
    findOrderYearRateForTab1();
    //一次合格率  整体统计 整机 模块
    findOrderPassForAllTab1();
   //一次合格率  整体统计 不区分整机 模块
    findOrderPassForTab1Ajax();
    // 共产 一致比重统计
    communistGravityStatisticForTab1Ajax();
    //根据类型 时间 统计共产 一致个月份数量
    communistStatisticForMonthForTab1Ajax();
}
//根据类型 时间 统计共产 一致个月份数量
function communistStatisticForMonthForTab1Ajax(){
	$.post(contextPath+'/lab/communistStatisticForMonthForTab1Ajax',{"startDate":"201601","endDate":"201612"},function(data){
		var myChart12 = echarts.init(document.getElementById("myChart12"));
		right_echarts.push(myChart12);
		myChart12.setOption(getBarEcharts());
		myChart12.setOption({
		    color: ['#66ccff', '#a5fff1'],
		    legend: {
		        show: true,
		        data: ['共产数', '一致数']
		    },
		    grid: {
//		            show:true,
		        x: "12%",
		        x2: "15%",
		        y: '20%',
		        y2: "22%"
		    },
		    yAxis: [
		        {
		            name: "数量",
		            type: 'value'
		        }
		    ],
		    xAxis: [
		        {
		            name: "时间",
		            type: 'category',
		            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
		        }
		    ],
		    series: [{
		        name: '共产数',
		        type: 'pictorialBar',
		        label: labelSetting,
		        symbolRepeat: true,
		        symbolSize: ['80%', '60%'],
		        barCategoryGap: '40%',
		        data: statisticRightSeriesData(data[0],bar_chip)
		    }, {
		        name: '一致数',
		        type: 'pictorialBar',
		        barGap: '10%',
		        label: labelSetting,
		        symbolRepeat: true,
		        symbolSize: ['80%', '60%'],
		        data: statisticRightSeriesData(data[1],bar_chip)
		    }]
		});
	})
}


// 共产 一致比重统计
function communistGravityStatisticForTab1Ajax(){
	$.post(contextPath+'/lab/communistGravityStatisticForTab1Ajax',{},function(data){
		var myChart11 = echarts.init(document.getElementById("myChart11"));
		right_echarts.push(myChart11);
		myChart11.setOption(getRoseEcharts());
		myChart11.setOption({
		    color: ['#66ccff', '#4397f7'],
		    legend: {
		        show: true,
		        textStyle: {
		            color: '#66ccff',
		            fontSize: 10 * bodyScale,
		        },
		        orient: 'vertical',  //布局  纵向布局
		        data: ['共产一致型号数', '共产不一致型号数'],
		        itemWidth: 10,  //图例标记的图形宽度
		        itemHeight: 2, //图例标记的图形高度
		    },
		    series: [
		        {
		            name: '',
		            type: 'pie',
		            radius: [0, '50%'],
		            center: ['50%', '55%'],
		            roseType: 'radius',
		            label: {
		                normal: {
		                    show: true,
		                    position: "inside",
		                    formatter: "{d}%"
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            lableLine: {
		                normal: {
		                    show: false
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data: [
		                {value: data.yz_num, name: '共产一致型号数'},
		                {value: (parseInt(data.gc_num)-parseInt(data.yz_num)), name: '共产不一致型号数'}
		            ]
		        },
		    ]
		});
	})
}
//人员状态 type:类型 1:学历情况 2:工作年限情况 3:批准权限
function findPersonStatusTab1Ajax(type){
	$.post(contextPath+'/lab/findPersonStatusTab1Ajax',{"type":type},function(data){
		var htmls="";
		$.each(data,function(index,item){
			htmls+='<li><span class="bar_name">'+item.name+'</span>';
			htmls+='<div class="progress">';
			htmls+='<div class="progress-bar1" role="progressbar" aria-valuenow="'+item.rate+'" aria-valuemin="0" aria-valuemax="100" style="width:'+item.rate+'%;height: 110%"></div>';
			htmls+='</div><span>'+item.rate+'%</span></li>';
		});
		$("#tab1_person_detail_"+type).html(htmls)
	})
}
//一次合格率  整体统计 不区分整机 模块
function findOrderPassForTab1Ajax(){
	$.post(contextPath+'/lab/findOrderPassForTab1Ajax',{},function(data){
		$("#tab1_pass_rate_id").html(data.rate+"%");
	})
}
//一次合格率  整体统计 整机 模块
function findOrderPassForAllTab1(){
	$.post(contextPath+'/lab/findOrderPassForAllAjax',{},function(data){
		var myChart4 = echarts.init(document.getElementById("myChart4"));
		//right_echarts.push(myChart4);
		myChart4.setOption(getCenterPie());
		var dataStyle = {
		    normal: {
		        label: {show:true},
		        labelLine: {show:true,length:3,length2:7,smooth:true}
		    }
		};
		var placeHolderStyle = {
		    normal : {
		        color: 'rgba(0,0,0,0)',
		        label: {show:false},
		        labelLine: {show:false}
		    },
		    emphasis : {
		        color: 'rgba(0,0,0,0)'
		    }
		};
		myChart4.setOption({
		    legend:{
		        data:['整机','模块']
		    },
		    textStyle:{
		        fontSize:12*bodyScale
		    },
		    color: ['#4397f7', '#66ccff'],
		    series: [
		        {
		            name: '整机',
		            type: 'pie',
		            clockWise: false,
		            radius: ['50%', '60%'],
		            itemStyle: {
		            	 normal: {
						        label: {show:true},
						        labelLine: {show:true,length:12,length2:7,smooth:false}
						    },
		            },
		            data: [
		                {
		                    value: 100-data[0].rate,
		                    name: '整机',
		                    itemStyle: placeHolderStyle
		                },
		                {
		                    value: data[0].rate,
		                    name: '整机',
		                    // itemStyle: placeHolderStyle
		                }
		            ]
		        },
		        {
		            name: '模块',
		            type: 'pie',
		            clockWise: false,
		            radius: ['40%', '50%'],
		            itemStyle: {
		            	 normal: {
						        label: {show:true},
						        labelLine: {show:true,length:5,length2:22,smooth:false}
						    },
		            },
		            data: [
		                {
		                    value: 100-data[1].rate,
		                    name: '模块',
		                    itemStyle: placeHolderStyle
		                },
		                {
		                    value: data[1].rate,
		                    name: '模块',
		                    // itemStyle: placeHolderStyle
		                }
		            ]
		        }
		    ]

		});

	})
}
//订单及时率
function findOrderYearRateForTab1(){
	$.post(contextPath+'/lab/findOrderYearRateForTab1Ajax',{"startDate":"201606","endDate":"201705"},function(data){
		console.log(data)
		var myChart6 = echarts.init(document.getElementById("myChart6"));
		right_echarts.push(myChart6);
		myChart6.setOption(getLineEcharts());
		myChart6.setOption({
		    legend: {
		        show: false,
		        data: ['及时率'],
		        itemWidth: 5,  //图例标记的图形宽度
		        itemHeight: 3, //图例标记的图形高度
		    },
		    grid: {
		        right: 43,
		        bottom: 20,
		        left: 38,
		        top:30
		    },

		    yAxis:{
		        name:'及时率/%',
		        nameTextStyle: {
		            color: '#66ccff'
		        },
		        max:100
		    },
		    xAxis: [
		        {
		            name:"时间",
		            nameTextStyle: {
		                color: '#66ccff'
		            },
		            data: statisticRightLengend2(data)
		        }
		    ],
		    series: [{
				name: '及时率',
				type: 'line',
				lineStyle:{
					normal:{
						width:1
					}
				},
				symbolSize:2,
				data: tab1OrderRateSeriseData(data)
		}]

		});
	})
}
//标准状态数据统计
function standardStatus(){
	$.post(contextPath+'/lab/standardStatusAjax',{},function(data){
		var reviseNum=parseInt(data.revisenum);
		var standardNum=parseInt(data.standardnum);
		$("#reviseNum").html(reviseNum);
		$("#tab1_qtqc_id").html(reviseNum);
		$("#standardNum").html(standardNum);
		var num0=(standardSeriesData(data.revisedata,"牵头起草数")/reviseNum).toFixed(2)*100;
		var num1=(standardSeriesData(data.revisedata,"参与起草数")/reviseNum).toFixed(2)*100;
		var gjia=standardSeriesData(data.standarddata,"国家标准");
		var gjbz=standardSeriesData(data.standarddata,"国际标准");
		var hybz=standardSeriesData(data.standarddata,"行业标准");
		var qybz=standardSeriesData(data.standarddata,"企业标准");
		$("#tab1_gjiabz_id").html(gjia);
		$("#tab1_gjibz_id").html(gjbz);
		$("#tab1_hybz_id").html(hybz);
		$("#tab1_qybz_id").html(qybz);
		
		var num2=(gjia/standardNum).toFixed(2)*100;
		var num3=(gjbz/standardNum).toFixed(2)*100;
		var num4=(hybz/standardNum).toFixed(2)*100;
		//var num5=(standardSeriesData(data.standarddata,"当地标准")/standardNum).toFixed(2)*100;
		var num6=(qybz/standardNum).toFixed(2)*100;
		//多个圆环图  标准状态
		var myChart2 = echarts.init(document.getElementById("myChart2"));
		var labelTop = {
			    normal: {
			        color: '#66ccff',
			        label: {
			            show: true,
			            position: 'center',
			            formatter: '{b}',
			            textStyle: {
			                baseline: 'bottom',
			                color: '#66ccff',
			                fontSize:8
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
			                return 100 - params.value + '%'
			            },
			            textStyle: {
			                baseline: 'top'
			            }
			        }
			    },
			};
			var labelBottom = {
			    normal: {
			        color: '#234f65',
			        label: {
			            show: true,
			            position: 'center'
			        },
			        labelLine: {
			            show: false
			        }
			    },
			    emphasis: {
			        color: 'rgba(0,0,0,0)'
			    },
			};
			var radius = [20, 25];
		option = {
		    toolbox: {
		        show: false,
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {
		                show: true,
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        width: '20%',
		                        height: '30%',
		                        itemStyle: {
		                            normal: {
		                                label: {
		                                    formatter: function (params) {
		                                        return 'other\n' + params.value + '%\n'
		                                    },
		                                    textStyle: {
		                                        baseline: 'middle'

		                                    }
		                                }
		                            },
		                        }
		                    }
		                }
		            },
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    textStyle: {
//		            color:'#66ccff'
		        color: '#ff9933'
		    },
		    series: [
				        {
				            type: 'pie',
				            center: ['35%', '22%'],
				            radius: radius,
				            x: '40%', // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num0, itemStyle: labelBottom},
				                {name: '牵头起草数', value: num0, itemStyle: labelTop}
				            ]
				        },
				        {
				            type: 'pie',
				            center: ['85%', '22%'],
				            radius: radius,
				            x: '60%', // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num1, itemStyle: labelBottom},
				                {name: '参与起草数', value: num1, itemStyle: labelTop}
				            ]
				        },
				        {
				            type: 'pie',
				            center: ['10%', '75%'],
				            radius: radius,
				            y: '55%',   // for funnel
				            x: '0%',    // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num2, itemStyle: labelBottom},
				                {name: '国家标准', value: num2, itemStyle: labelTop}
				            ]
				        },
				        {
				            type: 'pie',
				            center: ['35%', '75%'],
				            radius: radius,
				            y: '55%',   // for funnel
				            x: '20%',    // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num3, itemStyle: labelBottom},
				                {name: '国际标准', value: num3, itemStyle: labelTop}
				            ]
				        },
				        {
				            type: 'pie',
				            center: ['60%', '75%'],
				            radius: radius,
				            y: '55%',   // for funnel
				            x: '40%', // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num4, itemStyle: labelBottom},
				                {name: '行业标准', value: num4, itemStyle: labelTop}
				            ]
				        },
				      
				        {
				            type: 'pie',
				            center: ['85%', '75%'],
				            radius: radius,
				            y: '55%',   // for funnel
				            x: '80%', // for funnel
				            itemStyle: labelFromatter,
				            data: [
				                {name: 'other', value: 100-num6, itemStyle: labelBottom},
				                {name: '企业标准', value: num6, itemStyle: labelTop}
				            ]
				        }
				    ]
		};
		myChart2.setOption(option);
	})
}

//能力状态
function abilityStatus(){
	$.post(contextPath+'/lab/abilityStatusAjax',{},function(data){
		var maxNum=parseInt(data.allnum);
		$.each(data.data,function(index,item){
			if(maxNum<parseInt(item.count)){
				maxNum=parseInt(item.count);
			}
		})
		$("#ability_all_num").html(data.allnum)
		//能力状态 柱形图
		var myChart3 = echarts.init(document.getElementById("myChart3"));
		myChart3.setOption(getBarEcharts());
		var bar_chip = contextPath+'/img/bar_chip.png';
		myChart3.setOption({
		    yAxis: [
		        {
		            name: "数量",
		            type: 'value',
		            max: maxNum
		        }
		    ],
		    xAxis: [
		        {
		            name: "",
		            type: 'category',
		            data: statisticRightLengend2(data.data)
		        }
		    ],
		    grid: {
		        x: 48,
		        y: 28
		    },
		    series: [
		        {
		            symbolSize: ['60%', '10%'],
		            data: statisticRightSeriesData(data.data,bar_chip)
		        }
		    ]
		});
	})
}

function statisticRightLengend(data){
	var legnend=[];
	$.each(data,function(index,item){
		var name=item.name;
		name=name.substr(0,4)+"/"+name.substr(4,6);
		legnend.push(name);
	});
	return legnend;
}
function statisticRightLengend2(data){
	var legnend=[];
	$.each(data,function(index,item){
		var name=item.name;
		legnend.push(name);
	});
	return legnend;
}
function tab1OrderRateSeriseData(data){
	var mData=[];
	$.each(data,function(index,item){
		mData.push(item.rate);
	});
	return mData;
}

function statisticRightSeriesData(data,bar_chip){
	var series=[];
	$.each(data,function(index,item){
		var obj=new Object();
		obj.value=item.count;
		obj.symbol=bar_chip;
		series.push(obj);
	});
	return series;
}

function standardSeriesData(data,name){
	var num=0;
	$.each(data,function(index,item){
		if(item.name==name){
			num=parseInt(item.count);
		}
	});
	return num;
}
