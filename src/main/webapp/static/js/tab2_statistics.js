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
		indicatorData.push(data.count);
	}
	return indicatorData;
}
//标准数量分布情况
function standardDispersedStatus(mychartId,typeName){
	$.post(contextPath+'/lab/standardDispersedAjax',{"type":"0","typeName":typeName},function(data){
		var myChart = echarts.init(document.getElementById(mychartId));
		right_echarts.push(myChart);
		myChart.setOption(getRadarEcharts());
		myChart.setOption({
		    title: {
		        text: typeName,
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

//能力状态
function abilityStatus(){
	$.post(contextPath+'/lab/abilityStatusAjax',{},function(data){
		
	})
}


