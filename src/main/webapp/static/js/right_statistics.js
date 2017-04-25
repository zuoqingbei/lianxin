/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧数据统计
 */
//标准状态数据统计
function standardStatus(){
	$.post(contextPath+'/lab/standardStatusAjax',{},function(data){
		var reviseNum=parseInt(data.revisenum);
		var standardNum=parseInt(data.standardnum);
		$("#reviseNum").html(reviseNum);
		$("#standardNum").html(standardNum);
		var num0=(standardSeriesData(data.revisedata,"牵头起草数")/reviseNum).toFixed(2)*100;
		var num1=(standardSeriesData(data.revisedata,"参与起草数")/reviseNum).toFixed(2)*100;
		
		var num2=(standardSeriesData(data.standarddata,"国家标准")/standardNum).toFixed(2)*100;
		var num3=(standardSeriesData(data.standarddata,"国际标准")/standardNum).toFixed(2)*100;
		var num4=(standardSeriesData(data.standarddata,"行业标准")/standardNum).toFixed(2)*100;
		var num5=(standardSeriesData(data.standarddata,"当地标准")/standardNum).toFixed(2)*100;
		var num6=(standardSeriesData(data.standarddata,"企业标准")/standardNum).toFixed(2)*100;
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
				            center: ['65%', '22%'],
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
		            data: statisticRightLengend(data.data)
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
		legnend.push(item.name);
	});
	return legnend;
}
function statisticRightSeriesData(data,bar_chip){
	var series=[];
	$.each(data,function(index,item){
		var obj=new Object();
		obj.value=item.count;
		obj.symbol=bar_chip;
		series.push(obj);
	});
	console.log(series)
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
