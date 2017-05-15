/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧tab2数据统计
 */
function loadTab2Data(){
	//标准数量分布情况
	standardDispersedStatus("myChart28","国际标准");
	standardDispersedStatus("myChart29","国家标准");
	standardDispersedStatus("myChart30","行业标准");
	standardDispersedStatus("myChart31","企业标准");
	//不同产线的能力状态分布
	abilityByProductLine();
}
function tab2IndicatorData(data){
	//先找出最大值
	var maxNum=0;
	for(var i=0;i<data.length;i++) {
		if(parseInt(data[i].count)>parseInt(maxNum)){
			maxNum=parseInt(data[i].count);
		}
	}
	var indicatorData = [];
	for(var i=0;i<data.length;i++) {
		var obj=new Object();
		obj.max=maxNum;
		obj.text=data[i].name+":"+data[i].count;
		indicatorData.push(obj);
	}
	return indicatorData;
}

function tab2DataData(data){
	var indicatorData = [];
	for(var i=0;i<data.length;i++) {
		indicatorData.push(data[i].count);
	}
	return indicatorData;
}
function tab2Lengend(data){
	var legnend=[];
	$.each(data,function(index,item){
		legnend.push(item.type_name);
	});
	return legnend;
}
//标准数量分布情况
function standardDispersedStatus(mychartId,filedVaule){
	$.post(contextPath+'/lab/standardDispersedAjax',{"type":"0","filedVaule":filedVaule,"filed":"type_name"},function(data){
		var myChart = echarts.init(document.getElementById(mychartId));
		right_echarts.push(myChart);
		myChart.setOption(getRadarEcharts());
		myChart.setOption({
		    title: {
		        text: filedVaule,
		        left: 'center',
		        top: 'center',
		        textStyle: {
		            color: '#fff',
		            fontSize: 12,
		        }
		    },
		    polar: [
		        {
		            indicator:tab2IndicatorData(data),
		            center: ['50%', '55%'],
		            radius: '58%',
		            name: {
		                formatter: '{value}',
		                textStyle: {
		                    color: '#66ccff',
		                    fontSize: 10,
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
		            }
		        }

		    ],
		    series: [
		        {
		            type: 'radar',
		            data: [
		                {
		                    value: tab2DataData(data),
		                    name: '标准数量分布情况',
		                    itemStyle: {
		                        normal: {
		                            color: '#66ccff',
		                            areaStyle: {
		                                color: 'rgba(102,204,255,0.5)',
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

//不同产线的能力状态分布
function abilityByProductLine(){
	$.post(contextPath+'/lab/abilityByProductLineAjax',{},function(data){
		$.each(data[1],function(index,item){
			//console.log(index+"---"+item)
			if(item!=null&&item.length>0){
				$("#yingjubei_id_"+index).html(item[0].count+"&emsp;应具备数")
			}
		});
		$.each(data[0],function(index,item){
			var chartIndex=32+index;
			var myChart = echarts.init(document.getElementById("myChart"+chartIndex));
			myChart.setOption(getBarEcharts());
			right_echarts.push(myChart);
			var lengendData=[];
			var gridX="40%";
			if(index==0){
				lengendData=tab2Lengend(item);
				gridX="53%";
			}
			var seriesData=tab2DataData(item);
			myChart.setOption({
				yAxis: [
				        {
				        	type: 'category',
				        	data:lengendData,
				        	axisLine: {
				        		show: false,
				        	},
				        	axisTick: {
				        		show: false
				        	}
				        	
				        }
				        ],
				        xAxis: [
				                {
				                	show: false,
				                	type: 'value',
				                	boundaryGap: false,
				                }
				                ],
				                grid: {
				                	x: gridX,
				                	y: '0%',
				                	y2: "0%"
				                },
				                series: [
				                         {
				                        	 type: "bar",
				                        	 data: seriesData,
				                        	 barWidth: 8,
				                        	 label: {
				                        		 normal: {
				                        			 show: true,
				                        			 position: 'right',
				                        			 // formatter: "{a}%",
				                        			 textStyle: {
				                        				 fontSize: 10,
				                        				 color: "#ff9933"
				                        			 },
				                        			 formatter: '{c}'
				                        		 }
				                        	 },
				                         }
				                         ]
			});
			
		});
	})
}


