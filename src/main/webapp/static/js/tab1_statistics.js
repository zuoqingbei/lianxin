/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧数据统计
 */
var data = [];//直方图数据
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
    //直方图
    loadTab1JianData( $(".total_bottom_tab .active").attr("data"))
}
//加载量产一致性保障 xhId:产品id  name：产品名称
function loadTab1JianData(xhId){
	$.post(contextPath+'/lab/jianCeXhProForTab1Ajax',{"xhCode":xhId},function(xhPro){
		$("#tab1_jiance_xh_name").html("\""+xhPro.xh_name+"\"");
		$("#tab1_jiance_xh_result").html(xhPro.jielun);
		//模块商质量水平分布
		mkSqualityLevelForTab1(xhPro);
		//SPC分析
		scpDataForTab1("myChart9",xhPro,1);
		scpDataForTab1("myChart9_2",xhPro,2);
		//直方图
		cpkDataForTab1(xhPro);
	
	});
}
//模块商质量水平分布
function mkSqualityLevelForTab1(xhPro){
	var myChart8 = echarts.init(document.getElementById("myChart8"));
	right_echarts.push(myChart8);
	myChart8.setOption(getBarEcharts());
	var bar_chip = '../img/bar_chip.png';
	myChart8.setOption({
	    color:["#66ccff","#ff9933"],
	    title: {
	        show:false,
	        text: '模块商质量水平分布',
	        left: 'center'
	    },
	    grid: {
//	            show:true,
	        x: "25%",
	        x2: "21%",
	        y: '25%',
	        y2: "25%"
	    },
	    yAxis: [
	        {
	            name: "Cpk",
                axisPointer:{
                    tiggerTooltip:false
                },
	            nameGap:2*bodyScale,
	            // type: 'category',
	            position: 'left',
                axisLabel:{
                    textStyle: {
                        fontSize: 9 * bodyScale
                    }
                },
	            data: [1, 1.33, 1.67, 2],
	            axisLine: { //坐标轴
	                show: false,
	                textStyle: {
	                    color: 'rgba(0,0,0,0)'
	                }
	            },
	            axisTick: {  //刻度值
	                show: false,
	            }
	        }, {
	            name: " ",/*ppm用div替代*/
	            // nameGap:8*bodyScale,
	            position: 'right',
	            // type: 'category',
	            data: [2700,63,0.57,0.002],
                axisLabel:{
                    textStyle: {
                        fontSize: 9 * bodyScale
                    }
                },
	            axisLine: { //坐标轴
	                show: true,
	                textStyle: {
	                    color: '#66ccff',
	                }
	            },
	            axisTick: {  //刻度值
	                show: false,
	            }
	        }, {
	            name: "",
                axisPointer:{
	            	tiggerTooltip:false
				},
	            nameGap:2*bodyScale,
	            position: 'right',
	            type: 'category',
	            data: [0.6, 1.16, 1.5, 1.85,2.5],
	            axisLabel:{
                    show:false,
	                textStyle:{
	                    color: '#66ccff',
	                }
	            },
	            axisLine: { //坐标轴
	                show: true,
	                textStyle: {
	                    color: '#66ccff',
	                }
	            },
	            axisTick: {  //刻度值
	                show: false,
	            }
	        }

	    ],
	    xAxis: [
	        {
	            type: 'value',
                nameGap:2*bodyScale,
                axisLabel: {
                    show:false
                },
	            splitLine: {  //刻度线
	                show: true,
	                lineStyle: {
	                    color: "#234f65"
	                }
	            },
	            axisLine: { //坐标轴
	                show: false,
	                textStyle: {
	                    color: '#66ccff',
	                }
	            },
	            axisTick: {  //刻度值
	                show: false,
	            }
	        }
	    ],
	    series: [
	        {
	            symbolSize: ['40%', '60%'],
	            data: [{
	                value: 1,
	                symbol: bar_chip
	            }, {
	                value: 5,
	                symbol: bar_chip
	            }, {
	                value: 8,
	                symbol: bar_chip
	             }, {
	                value: 3,
	                symbol: bar_chip
	             }, {
	                value: 0,
	                symbol: bar_chip
	            }
	            ]
	        }
	    ]
	});
/*	$.post(contextPath+'/lab/jianCeXbarForTab1Ajax',{"xhName":xhPro.xh_name},function(data){
		
	})*/
}
//SPC分析  xbar
function scpDataForTab1(myChartIds,xhPro,type){
	//类型 1：样本平均值 2：样本标准差
	$.post(contextPath+'/lab/jianCeXbarForTab1Ajax',{"xhName":xhPro.xh_name,"type":type},function(data){
		var maxAndMin=getMaxMinForScpTab1(data,xhPro,type);
		var mTitle,mLcl,mValue,mUcl;
		if(type==1){
			mTitle="样本平均值";
			mLcl=xhPro.jz_lcl;
			mValue=xhPro.jz_value;
			mUcl=xhPro.jz_ucl;
		}else{
			mTitle="样本标准差";
			mLcl=xhPro.fc_lcl;
			mValue=xhPro.fc_value;
			mUcl=xhPro.fc_ucl;
		}
		var myChart9 = echarts.init(document.getElementById(myChartIds));
		right_echarts.push(myChart9);
		myChart9.setOption(getLineEcharts());
		myChart9.setOption({
		    color:["#ff9933"],
		    textStyle:{
		        fontSize:4*bodyScale
		    },
		    title: {
		        show:false,
		        text: 'Xbar 控制图',
		        left: 'center'
		    },
		    grid: {
		        right: "29%",
		        bottom: "28%",
		        left: "15%",
		        top: "16%"
		    },
		    yAxis: {
		        name: mTitle,
		        max: parseFloat(maxAndMin[0]),
		        min: parseFloat(maxAndMin[1]),
		        axisLabel:{
		            textStyle:{
		                fontSize:5*bodyScale
		            }
		        },
		        splitLine: {  //刻度线
		            show: false
		        },
		        nameGap:2*bodyScale,
		        nameTextStyle:{fontSize:6*bodyScale},
		    },
		    xAxis: [
		        {
		            name: "",
		            axisLabel:{
		                textStyle:{
		                    fontSize:5*bodyScale
		                },
		                margin:2*bodyScale
		            },
		            nameGap:2*bodyScale,
		            nameTextStyle:{fontSize:6*bodyScale},
		            data: statisticRightLengend2(data)
		        }
		    ],
		    visualMap: {
		        show:false,
		        top: 10,
		        right: 10,
		        pieces: [{
		            gt: 74,
		            lte: 74.1,
		            color: '#096'
		        }],
                outOfRange: {
                    color: '#cc0033'
                }
		    },
		    series: [
		        {
		            name: mTitle,
		            type: 'line',
		            lineStyle: {
		                normal: {
                            // color:"#00e673",
		                    width: 1
		                }
		            },
                    itemStyle:{normal:{
                        // borderColor:"#00e673"
                        borderColor:function (params) {
							console.log("...............................",params)
                        }
                    }},
		            symbolSize: 2,
		            data: tab1OrderRateSeriseData(data),
		            markLine: {
		                symbolSize:0,
		                silent: true,
		                label:{normal:{formatter:"{b}={c}"}},
		                data: [{
		                    name:"UCL",
		                    yAxis: parseFloat(mUcl)
		                },{
		                    name:"x",
		                    yAxis: parseFloat(mValue)
		                }, {
		                    name:"LCL",
		                    yAxis: parseFloat(mLcl)
		                }]
		            }
		        }
		    ]

		});
		
	});
	
}
//直方图
function cpkDataForTab1(xhPro){
	$.post(contextPath+'/lab/jianCeDataForTab1Ajax',{"xhCode":xhPro.xh_name},function(data){
		var mData=[];
		//var mData2=[];
		$.each(data,function(index,item){
			mData.push([parseFloat(item.wkq_num),parseFloat(xhPro.pj_value)]);
			//mData2.push([parseFloat(item.wkq_num),parseFloat(item.gd_num_2)]);
		});
		mHeightChart.series[0].setData(histogram(mData, 0.5)); // 更新 series
		mHeightChart.series[1].setData(histogram(mData, 0.5));
	});
	
}
/*function histogram2(arr) {
   
    // Finally, sort the array
    arr.sort(function (a, b) {
        return a[0] - b[0];
    });
    return arr;
}*/
function histogram(data, step) {
    var histo = {},
        x,
        i,
        arr = [];
    // Group down
    for (i = 0; i < data.length; i++) {
        x = Math.floor(data[i][0] / step) * step;
        if (!histo[x]) {
            histo[x] = 0;
        }
        histo[x]++;
    }
    // Make the histo group into an array
    for (x in histo) {
        if (histo.hasOwnProperty((x))) {
            arr.push([parseFloat(x), histo[x]]);
        }
    }
    // Finally, sort the array
    arr.sort(function (a, b) {
        return a[0] - b[0];
    });
    return arr;
}
var mHeightChart=$('#myChart10').highcharts({
    chart: {
        type: 'column',
        backgroundColor: 'rgba(0,0,0,0)',
        marginBottom: 5*bodyScale
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled:false
    },
    title: {
        text: '',
    },
    legend:{
        enabled:false,
    },
    xAxis: {
        gridLineWidth: 1,
        min:71,
        max:77,
        plotLines:[{
            color:'red',            //线的颜色，定义为红色
            dashStyle:'shortDot',//认是solid（实线），这里定义为长虚线
            value:71,                //定义在哪个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
            width:2  ,               //标示线的宽度，2px
            label:{
                text:'LSL',  //标签的内容
                align:'center',                //标签的水平位置，水平居左,默认是水平居中center
                x:5                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
            },
            zIndex:100,  //值越大，显示越向前，默认标示线显示在数据线之后
        },{
            color:'red',            //线的颜色，定义为红色
            dashStyle:'shortDot',//标示线的样式，默认是solid（实线），这里定义为长虚线
            value:77,                //定义在哪个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
            width:2  ,               //标示线的宽度，2px
            label:{
                text:'USL',//标签的内容
                align:'center',                //标签的水平位置，水平居左,默认是水平居中center
                x:5                         //标签相对于被定位的位置水平偏移的像素，重新定位，水平居左10px
            },
            zIndex:100,  //值越大，显示越向前，默认标示线显示在数据线之后
        }
        ]
    },
    yAxis: [{
        title: {
            text: ''
        },
        visible:false
    }, {
        opposite: true,
        title: {
            text: ''
        },
        visible:false,
    }],
    series: [{
        name: '直方图',
        type: 'column',
        data: histogram(data, 0.5),
        color:"#4397f7",
        pointPadding: 0,
        groupPadding: 0,
        pointPlacement: 'between'
    }, {
        name: '概率密度',
        type: 'spline',
        data: data,
		color:"#00e673",
        yAxis: 1,
        marker: {
            radius: 1.5
        }
    }]
}).highcharts();
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
		        data: ['共产数', '一致数'],
                textStyle: {
                    fontSize: 10 * bodyScale,
                },
                itemWidth: 6 * bodyScale,  //图例标记的图形宽度
                itemHeight: 6 * bodyScale, //图例标记的图形高度
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
		            type: 'value',
					scale:true
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
		    	show:false,
                x: 'right',
		        data:['整机','模块']
		    },
		    textStyle:{
		        fontSize:12*bodyScale
		    },
		    color: ['#4397f7', '#66ccff'],
			grid:{ //grid在极坐标中不起作用，只能应用于直角坐标系
                x:"",
				x2:""
			},
		    series: [
		        {
		            name: '整机',
		            type: 'pie',
		            clockWise: false,
		            radius: ['50%', '60%'],
					center:['40%', '45%'],
		            itemStyle: {
		            	 normal: {
						        label: {show:true},
						        labelLine: {show:true,length:12*bodyScale,length2:47*bodyScale,smooth:false}
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
                    center:['40%', '45%'],
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
		// console.log(data)
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
				                {name: '起草数', value: num0, itemStyle: labelTop}
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
				                {name: '起草数', value: num1, itemStyle: labelTop}
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
//获取最大值 最小值
function getMaxMinForScpTab1(data,xhPro,type){
	var result=[];
	var max;
	var min;
	if(type==1){
		min=xhPro.jz_lcl;
		max=xhPro.jz_ucl;
	}else{
		min=xhPro.fc_lcl;
		max=xhPro.fc_ucl;
	}
	$.each(data,function(index,item){
		if(parseFloat(item.num)>parseFloat(max)){
			max=item.num;
		}
		if(parseFloat(item.num)<parseFloat(min)){
			min=item.num;
		}
	});
	result.push(parseFloat(max)+0.1);
	result.push(parseFloat(min)-0.1);
	return result;
}
