// 数据分析
var bodyScale = 1;//原始比例1
//左
var chartone = echarts.init(document
    .getElementById("echart_one"));
//右
var charttwo = echarts.init(document
    .getElementById("echart_two"));


//左
var chartthree = echarts.init(document
    .getElementById("echart_three"));

//右
var chartfour = echarts.init(document
    .getElementById("echart_four"));


//左
var chartfive = echarts.init(document
    .getElementById("echart_five"));
//右
var chartsix = echarts.init(document
    .getElementById("echart_six"));
//$("#labMain_cbro_content").load("labAnalysis_small.html");
// document.getElementById("labMain_cbro_content").innerHTML = '<object type="text/html" data="labAnalysis_small.html" width="100%" height="100%"></object>';

function initone(mValue) {
    var labelFromatter = {
        normal: {
            label: {
                formatter: function (params) {
                    return 100 - params.value + '%'
                },
            },
            labelLine: {
                show: false
            }
        },
    };
    //5
    var labelTop = {
        normal: {
            color: '#66ccff',
            label: {
                show: true,
                position: 'bottom',
                formatter: '{b}',
                textStyle: {
                    baseline: 'center',
                    color: '#66ccff',
                    fontSize: 8
                }
            },
            labelLine: {
                show: false
            }
        }
    };
    //95
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
        emphasis: {},
    };
    var radius = ['45%', '65%'];
    var option = {
        textStyle: {
            color: '#ff9933',
            fontSize: bodyScale * 12,
        },
        grid: {
            x: "0",
            y: '0',
        },
        series: [
            {
                type: 'pie',
                radius: radius,
                //x: '40%', // for funnel
                itemStyle: labelFromatter,
                data: [
                    {name: '', value: 100 - parseFloat(mValue), itemStyle: labelBottom},
                    {name: '', value: mValue, itemStyle: labelTop}
                ]
            }

        ]

    };
    return option;

}
//近12个月用户满意度趋势图
function inittwo() {
    $.post(contextPath + '/lab/satisfactionStatisForMonthForTab1Ajax', {"labTypeCode": "中海博睿"}, function (data) {
        var resu = dealSatisfactionCenterLab(data);
        $("#satisfaction_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#satisfaction_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#satisfaction_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartfive.setOption(initone(resu[0]));
        var bar_chip = contextPath + '/static/img/bar_chip.png';
        var labelSetting = {
            normal: {
                show: false,
                position: 'outside',
                offset: [10, 0],
                textStyle: {
                    fontSize: bodyScale * 8
                }
            }
        };
        chartsix.setOption(getBarEcharts());
        chartsix.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            yAxis: [
                {
                    name: "满意度/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },

                    type: 'value',
                    max: 100,
                    scale: true,
                },
            ],
            xAxis: [
                {
                    name: "",
                    type: 'category',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            grid: {
                x: "15%",
                x2: "10%",
                y: '23%',
                y2: "34%",
            },
            series: [
                {
                    symbolSize: ['50%', '10%'],
                    data: centerLabOrderHgRate(data)
                }
            ]
        });


    });

}
//近12个月一次合格率趋势图
function initThree() {
    $.post(contextPath + '/lab/orderRateForCenterLabAjax', {
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var resu = dealCenterLab(data);
        $("#hg_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#hg_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#hg_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartone.setOption(initone(resu[0]));
        charttwo.setOption(getBarEcharts());
        var bar_chip = contextPath + '/static/img/bar_chip.png';
        var labelSetting = {
            normal: {
                show: false,
                position: 'outside',
                offset: [10, 0],
                textStyle: {
                    fontSize: bodyScale * 8
                }
            }
        };


        charttwo.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            yAxis: [
                {
                    name: "合格率/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },

                    type: 'value',
                    max: 100,
                    scale: true,
                },
            ],
            xAxis: [
                {
                    name: "",
                    type: 'category',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            grid: {
                // x: "10%",
//		            x2: "25%",
//		            y: '22%',
//		            y2: "26%",

                x: "15%",
                x2: "10%",
                y: '23%',
                y2: "34%",
            },
            series: [
                {
                    symbolSize: ['50%', '10%'],
                    data: centerLabOrderHgRate(data)
                }
            ]
        });

    });


}
//按照产线统计某年各月份详细订单及时率  数据结果 订单及时率 折线图
function initfour() {
    $.post(contextPath + '/lab/findOrderYearRateForTab1Ajax', {
        "labTypeCode": "中心实验室",
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var resu = dealCenterLab(data);
        $("#order_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#order_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#order_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartthree.setOption(initone(resu[0]));
        chartfour.setOption(getAreaEcharts());
        chartfour.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            legend: {
                show: false,
                data: [''],
                textStyle: {
                    fontSize: bodyScale * 8
                },
                itemWidth: 6, //图例标记的图形宽度
                itemHeight: 6 //图例标记的图形高度
            },
            grid: {

                x: "11%",
                x2: "10%",
                y: '23%',
                y2: "20%"
            },
            xAxis: [
                {
                    name: '',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            yAxis: [
                {
                    name: "及时率/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },
                }
            ],
            series: [
                {
                    name: '',
                    type: 'line',
                    stack: '总量',
                    // areaStyle: {normal: {}},
                    data: centerLabRateData(data),
                    itemStyle: {
                        normal: {
                            color: "#ff6666"
                        }
                    }

                }
            ]

        });
    });

}

//曲线
var colorData=['#FFFFCC','#FFCCFF','#FFCC99','#FF99FF','#FF00FF','#CCCC00','#99FFFF',
               '#99CCCC','#99CC00','#FFFF99','#FFCCCC','#FF99CC','#CC66FF','#9966FF',
               '#996633','#FFFF66','#FF9999','#993333','#990099','#66FF66','#FFCC00',
               '#FFFF33','#990000','#66CCFF','#3366CC','#339900','#00FF00','#FFFF00',
               '#003300','#000066','#006666','#336699','#993333','#993399','#996600',
               '#9966CC','#999900','#99FF00','#CC0033','#CC6600'];//图例颜色 需手工扩充
var myChart1;
var myChart2;
var xData;//x轴坐标数据--对应时间
var legendData=[];//需要把全部图例放入里面 保证名称不同
var showLegendData=[];//需要展示图例 自定义
var seriesTopData=[];
var seriesBottomData=[];
var topParam=[];//上方y参数单位
var bottomParam=[];
var currentData;
var totalLegendName=[];//图例全称 包含单位 ['1:频率(Hz)','2:M1(℃)']
var dataBase={
		list:[
		     {	name:'1:温度(℃)',
		    	data:[{name:'1月',value:'-55'},{name:'2月',value:'60'},{name:'3月',value:'447'},{name:'4月',value:'400'},{name:'5月',value:'200'},{name:'6月',value:'250'},{name:'7月',value:'15'},{name:'8月',value:'202'},{name:'9月',value:'21'},{name:'10月',value:'7'},{name:'11月',value:'103'},{name:'12月',value:'215'} ]
		      },{
		    	  name:'2:电压(V)',
		    	  data:[{name:'1月',value:'144'},{name:'2月',value:'252'},{name:'3月',value:'227'},{name:'4月',value:'111'},{name:'5月',value:'241'},{name:'6月',value:'233'},{name:'7月',value:'105'},{name:'8月',value:'22'},{name:'9月',value:'55'},{name:'10月',value:'175'},{name:'11月',value:'153'},{name:'12月',value:'55'} ] 
		      },{
		    	  name:'3:电流(A)',
		    	  data:[{name:'1月',value:'24'},{name:'2月',value:'2'},{name:'3月',value:'7'},{name:'4月',value:'11'},{name:'5月',value:'54'},{name:'6月',value:'33'},{name:'7月',value:'15'},{name:'8月',value:'22'},{name:'9月',value:'5'},{name:'10月',value:'37'},{name:'11月',value:'13'},{name:'12月',value:'45'} ] 
		      },{
		    	  name:'4:功率(W)',
		    	  data:[{name:'1月',value:'4000'},{name:'2月',value:'2222'},{name:'3月',value:'1722'},{name:'4月',value:'1422'},{name:'5月',value:'1222'},{name:'6月',value:'1522'},{name:'7月',value:'5222'},{name:'8月',value:'2222'},{name:'9月',value:'1122'},{name:'10月',value:'1722'},{name:'11月',value:'1322'},{name:'12月',value:'5222'} ] 
		      },{
		    	  name:'5:耗电量(Wh)',
		    	  data:[{name:'1月',value:'14000'},{name:'2月',value:'12000'},{name:'3月',value:'3700'},{name:'4月',value:'4400'},{name:'5月',value:'5200'},{name:'6月',value:'12500'},{name:'7月',value:'5100'},{name:'8月',value:'5002'},{name:'9月',value:'4001'},{name:'10月',value:'2004'},{name:'11月',value:'15154'},{name:'12月',value:'11133'} ] 
		      },{
		    	  name:'6:频率(Hz)',
		    	  data:[{name:'1月',value:'15'},{name:'2月',value:'60'},{name:'3月',value:'70'},{name:'4月',value:'40'},{name:'5月',value:'20'},{name:'6月',value:'25'},{name:'7月',value:'15'},{name:'8月',value:'22'},{name:'9月',value:'71'},{name:'10月',value:'56'},{name:'11月',value:'43'},{name:'12月',value:'95'} ]
		      },{
		    	  name:'7:功率因数(PF)',
		    	  data:[{name:'1月',value:'40'},{name:'2月',value:'20'},{name:'3月',value:'17'},{name:'4月',value:'34'},{name:'5月',value:'12'},{name:'6月',value:'65'},{name:'7月',value:'45'},{name:'8月',value:'2'},{name:'9月',value:'71'},{name:'10月',value:'27'},{name:'11月',value:'13'},{name:'12月',value:'65'} ]
		      },{
		    	  name:'9:压力(kpa)',
		    	  data:[{name:'1月',value:'-74'},{name:'2月',value:'82'},{name:'3月',value:'57'},{name:'4月',value:'14'},{name:'5月',value:'12'},{name:'6月',value:'15'},{name:'7月',value:'5'},{name:'8月',value:'2'},{name:'9月',value:'-11'},{name:'10月',value:'67'},{name:'11月',value:'-43'},{name:'12月',value:'50'} ]
		      },{
		    	  name:'10:转速(r/min)',
		    	  data:[{name:'1月',value:'100'},{name:'2月',value:'10'},{name:'3月',value:'2700'},{name:'4月',value:'1400'},{name:'5月',value:'-2212'},{name:'6月',value:'1500'},{name:'7月',value:'522'},{name:'8月',value:'2254'},{name:'9月',value:'1100'},{name:'10月',value:'1527'},{name:'11月',value:'1333'},{name:'12月',value:'500'} ]
		      },{
		    	  name:'11:瞬时流量(L/min)',
		    	  data:[{name:'1月',value:'11'},{name:'2月',value:'80'},{name:'3月',value:'77'},{name:'4月',value:'44'},{name:'5月',value:'55'},{name:'6月',value:'15'},{name:'7月',value:'5'},{name:'8月',value:'20'},{name:'9月',value:'91'},{name:'10月',value:'77'},{name:'11月',value:'13'},{name:'12月',value:'50'} ]
		      }
		      ]
};



$(document).ready(function () {
	findSensorByLabCenetrTabAjax("HaierWasher20151222","1");
});
function findSensorByLabCenetrTabAjax(labTypeCode,testUnitId){
	$.post(contextPath+"/lab/findSensorByLabCenetrTabAjax",{"labTypeCode":labTypeCode,"testUnitId":testUnitId},function(data){
		currentData=data;
		$.each(data,function(index,item){
			totalLegendName.push(item.legend);
			if(index<4){
				topParam.push(item.unit);
			}else if(index>=4&&index<8){
				bottomParam.push(item.unit);
			}
		});
		legendData=dealBracket(totalLegendName);
		showLegendData=legendData;//默认全选
		createLegendHtmls();
		createEcharts(true);
	});
}
//生成echarts图形
function createEcharts(isFirst,obj){
	if(isFirst){
		dealSeriesData();
	}else{
		dealSeriesData2(obj);
	}
	myChart1= echarts.init(document.getElementById('main1'));
	myChart2 = echarts.init(document.getElementById('main2'));
	getCharts1();	
	getCharts2();	
}

//生成图例控制
function createLegendHtmls() {
    var htmls = '';
    for (var x = 0; x < legendData.length; x++) {
        htmls += '<input style="margin-right: 2%;margin-top: 0;float: left" type="checkbox" name="legendcheckbox" onclick="resetOptions(this)" value="' + legendData[x] + '" checked><span style="background-color:' + colorData[x] + ';display: inline-block;width:1em;height: 1em;margin-right: 2%;float: left"></span><li  style="color:#66ccff;display: inline-block;float:left" name="' + legendData[x] + '">' + legendData[x] + '</li><br>'
    }
    $("#legend_ul").html(htmls);
}

//处理线series
function dealSeriesData(){
	seriesTopData=[];
	seriesBottomData=[];
	for(var x=0;x<totalLegendName.length;x++){
		var currentName=totalLegendName[x];
		var data=[];
		for(var i=0;i<dataBase.list.length;i++){
			if(dataBase.list[i].name==currentName){
				data=dataBase.list[i].data;
			}
		};
		var topIndex=isHasElementOne(topParam,dealUnit(currentName));
		var bottomIndex=isHasElementOne(bottomParam,dealUnit(currentName));
		if(topIndex>-1||bottomIndex>-1){
			if(topIndex>-1&&isHasElementOne(showLegendData,dealBracketForObj(currentName))>-1){
				//展示在上半部分
				seriesTopData.push(joinSerise(data,currentName,topIndex,x));
			}else if(bottomIndex>-1&&isHasElementOne(showLegendData,dealBracketForObj(currentName))>-1){
				//展示在下半部分
				seriesBottomData.push(joinSerise(data,currentName,bottomIndex,x));
			}
		}else{
			//没有配置 默认画到左下
			seriesBottomData.push(joinSeriseOther(data,currentName,x));
		}
	}
};
//处理线series
function dealSeriesData2(obj){
	seriesTopData=[];
	seriesBottomData=[];
	for(var x=0;x<totalLegendName.length;x++){
		var currentName=totalLegendName[x];
		var data=[];
		for(var i=0;i<dataBase.list.length;i++){
			if(dataBase.list[i].name==currentName){
				data=dataBase.list[i].data;
			}
		};
		var topIndex=isHasElementOne(topParam,dealUnit(currentName));
		var bottomIndex=isHasElementOne(bottomParam,dealUnit(currentName));
		if(topIndex>-1||bottomIndex>-1){
			if(topIndex>-1&&isHasElementOne(showLegendData,dealBracketForObj(currentName))>-1){
				//展示在上半部分
				seriesTopData.push(joinSerise(data,currentName,topIndex,x));
			}else if(bottomIndex>-1&&isHasElementOne(showLegendData,dealBracketForObj(currentName))>-1){
				//展示在下半部分
				seriesBottomData.push(joinSerise(data,currentName,bottomIndex,x));
			}
		}else{
			//没有配置 默认画到左下
			var checked=false;
			$('input[name="legendcheckbox"]:checked').each(function(){ 
				if($(this).val()==dealBracketForObj(currentName)){
					checked=true;
				}; 
			});  
			if(checked){
				seriesBottomData.push(joinSeriseOther(data,currentName,x));
			};
		}
	}
};
function joinSerise(data,name,index,colorIndex){
	var dataArr=[];
	xData=[];
	for(var x=0;x<data.length;x++){
		dataArr.push(data[x].value);
		xData.push(data[x].name);
	};
	var item= {
	            name:dealBracketForObj(name),
 	            type:'line',
 	            data:dataArr,
 	            itemStyle:{
 	        	  normal:{
 	        		  color:colorData[colorIndex]
 	        	  }
 	           },
 	           show:false
 	        };
	if(index>0){
		item.yAxisIndex=index;
	}
	return item;
}
function joinSeriseOther(data,name,colorIndex){
	var dataArr=[];
	xData=[];
	for(var x=0;x<data.length;x++){
		dataArr.push(data[x].value);
		xData.push(data[x].name);
	};
	var item= {
	            name:dealBracketForObj(name),
 	            type:'line',
 	            data:dataArr,
 	            itemStyle:{
 	        	  normal:{
 	        		  color:colorData[colorIndex]
 	        	  }
 	           },
 	           show:false
 	        };
	//item.yAxisIndex=1;
	return item;
}

function getCharts1() {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            show: false,
            data: legendData
        },
        grid: {
            x: '13%',
            x2: '10%',
            y2: '-2%'                //下移负数 使两个图重叠
        },
        xAxis: [
            {
                type: 'category',
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    // rotate: 30,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisTick: {
                    show: false,
                    alignWithLabel: true,
                    lineStyle: {
                        color: '#66ccff'
                    }
                },
                data: xData
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: currentData[0].unit,
	            max:currentData[0].highvalue,
	            min:currentData[0].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'left',
                offset: 40,
                axisLine: { //坐标轴
                    show: false
                },
                axisLabel: {   //坐标值
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: currentData[1].unit,
	            max:currentData[1].highvalue,
	            min:currentData[1].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'left',
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: currentData[2].unit,
	            max:currentData[2].highvalue,
	            min:currentData[2].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,

            },
            {
                type: 'value',
                offset: 40,
                name: currentData[3].unit,
	            max:currentData[3].highvalue,
	            min:currentData[3].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,

            }
            
        ],
        series: seriesTopData
    };
    myChart1.clear();
    myChart1.setOption(option);
    echarts.connect([myChart1, myChart2]);
   /* myChart1.setOption({
        series: [{
            animation:false
        },{
            animation:false
        },{
            animation:false
        },]
    });
    setInterval(function () {
	 for(var i=0; i<seriesTopData.length;i++){
		 seriesTopData[i].data.shift();
	     seriesTopData[i].data.push(parseInt(Math.random() * 30));
	 }
        var month = xData.shift();
        xData.push(month)

        myChart1.setOption({
            xAxis:[
                {data:xData}],
            series: seriesTopData,
        });
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~seriesBottomData: ", seriesTopData[0].data)
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~xData: ", xData)
    }, 2000);*/
}
function getCharts2() {

    option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            show: false,
            data: legendData
        },
        grid: {
            x: '13%',
            x2: '10%',
            y: '3%',
            y2: '14%'
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    // rotate: 30,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisTick: {
                    show: true,
                    alignWithLabel: true,
                    lineStyle: {
                        color: '#66ccff'
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: currentData[4].unit,
	            max:currentData[4].highvalue,
	            min:currentData[4].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                /*      min: 0,
                 max: 100, */
                position: 'left',
                offset: 40 * bodyScale,
                axisLabel: {
                    formatter: function (params, index) {
                        //console.log(params+"--"+index+"--"+typeof(params))
                        /* if(index==7){
                         return ""
                         } */
                        return params;
                    },
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: currentData[5].unit,
	            max:currentData[5].highvalue,
	            min:currentData[5].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                position: 'left',
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,

            },
            {
                type: 'value',
                name: currentData[6].unit,
	            max:currentData[6].highvalue,
	            min:currentData[6].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                /*      min: 0,
                 max: 100, */
                position: 'right',
               
                axisLabel: {
                    formatter: function (params, index) {
                        //console.log(params+"--"+index+"--"+typeof(params))
                        /* if(index==7){
                         return ""
                         } */
                        return params;
                    },
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: currentData[7].unit,
	            max:currentData[7].highvalue,
	            min:currentData[7].lowvalue,
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                offset: 40,
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },

                symbolSize:1,
            }
        ],
        series: seriesBottomData
    };
    // myChart2.clear();
    myChart2.setOption(option2);

    echarts.connect([myChart1, myChart2]);

    /*myChart2.setOption({
        series: [{
            animation:false
        },{
            animation:false
        },{
            animation:false
        },]
    });
    setInterval(function () {
	 for(var i=0; i<seriesBottomData.length;i++){
	     seriesBottomData[i].data.shift();
	     seriesBottomData[i].data.push(parseInt(Math.random() * 30));
	 }
        var month = xData.shift();
        xData.push(month)

        myChart2.setOption({
            xAxis:[
                {data:xData}],
            series: seriesBottomData,
        });
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~seriesBottomData: ", seriesBottomData[0].data)
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~xData: ", xData)
    }, 2000);
*/
}
//处理括号
function dealBracket(arr){
	var result=new Array();
	for(var x=0;x<arr.length;x++){
		result.push(dealBracketForObj(arr[x]));
	}
	return result;
}
function dealBracketForObj(obj){
	if(obj.indexOf("(")>-1){
		return obj.substr(0,obj.indexOf("("));
	}
	return obj;
}
//判断数组中某个元素下标
function isHasElementOne(arr,value){ 
	for(var i = 0,vlen = arr.length; i < vlen; i++){ 
		if(arr[i] == value){ 
			return i; 
		} 
	} 
	return -1; 
} 
//传入字符串获取单位
function dealUnit(str){
	if(str.indexOf("(")>-1&&str.indexOf(")")>-1){
		var result=str.substr(str.indexOf("(")+1,str.indexOf(")"));
		result=result.substr(0,result.indexOf(")"));
		return result;
	}
	return "";
}
function resetOptions(obj){
	showLegendData=checkBoxVales();
	createEcharts(false,obj);
}
function checkBoxVales(){ //jquery获取复选框值 
	var chk_value =[]; 
	$('input[name="legendcheckbox"]:checked').each(function(){ 
		chk_value.push($(this).val()); 
	}); 
	return chk_value;
} 


//新增
function centerLabOrderRateLengend(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        var name = item.name;
        name = name.substr(0, 4) + "/" + name.substr(4, name.length);
        legnend.push(name);
    });
    return legnend;
}
//合格率数据
function centerLabOrderHgRate(data) {
    var d = [];
    $.each(data, function (index, item) {
        var it = {
            value: item.rate,
            symbol: bar_chip
        };
        d.push(it)
    })
    return d;
}
function centerLabRateData(data) {
    var indicatorDataTab3 = [];
    for (var i = 0; i < data.length; i++) {
        var num = data[i].rate;
        indicatorDataTab3.push(num);
    }
    return indicatorDataTab3;
}
//获取平均 最高 最低数据
function dealCenterLab(data) {
    var result = [];
    var all_num = 0;
    var js_num = 0;
    var maxData = {month: 0, rate: 0};
    var minData = {month: 0, rate: 100};
    $.each(data, function (index, item) {
        var cAllNum = parseInt(item.all_count);
        var cJsNum = parseInt(item.js_count);
        var cName = item.name;
        var cRate = parseFloat(item.rate);
        if (parseFloat(maxData.rate) < cRate) {
            maxData.rate = cRate;
            maxData.month = cName;
        }
        if (parseFloat(minData.rate) > cRate) {
            minData.rate = cRate;
            minData.month = cName;
        }
        all_num += cAllNum;
        js_num += cJsNum;
    });
    //计算整体平均值
    var allPingjun = parseFloat((parseInt(js_num) / parseInt(all_num)) * 100).toFixed(1);
    result.push(allPingjun);
    result.push(maxData);
    result.push(minData);
    return result;
}
//获取用户满意度平均 最高 最低数据
function dealSatisfactionCenterLab(data) {
    var result = [];
    var all_num = 0;
    var maxData = {month: 0, rate: 0};
    var minData = {month: 0, rate: 100};
    $.each(data, function (index, item) {
        all_num = parseFloat(all_num) + parseFloat(item.rate);
        var cName = item.name;
        var cRate = parseFloat(item.rate);
        if (parseFloat(maxData.rate) < cRate) {
            maxData.rate = cRate;
            maxData.month = cName;
        }
        if (parseFloat(minData.rate) > cRate) {
            minData.rate = cRate;
            minData.month = cName;
        }
    });
    //计算整体平均值
    var allPingjun = parseFloat(all_num / data.length).toFixed(1);
    result.push(allPingjun);
    result.push(maxData);
    result.push(minData);
    return result;
}


//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//            曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
