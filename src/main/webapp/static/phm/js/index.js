

var Index = {
	bodyScale:$(window).height()/595,
	init:function(){
		var that = this;
		setTimeout(function(){
			that.mapCharts();
			
		},1000)
	},
	mapCharts:function(){
		var mapChart = echarts.init(document.getElementById("chinaMap"));
		
		var myArr = [];
		for(var i=0;i<1000;i++){
			myArr.push(Math.random()*115+20)
		}
		
		var mapChartsOptions = {
			//backgroundColor: '#404a59',
	        
	        tooltip: {},
	        itemStyle:{
	        	normal:{
	        		borderWidth:1,
                    borderColor: '#fff',//区域边框颜色
//	        		 shadowBlur:5,
//                  shadowColor:'#000',
//                  shadowOffsetX:-3,
//                  shadowOffsetY:1
	        	}
	        },
	        legend: {
//	            left: 'left',
//	            data: ['强', '中', '弱'],
//	            textStyle: {
//	                color: '#ccc'
//	            }
	        },
	        geo: {
	            map: 'china',
	            label: {
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                normal: {
	                    areaColor: '#323c48',
	                    borderColor: '#111'
	                },
	                emphasis: {
	                    areaColor: '#2a333d'
	                }
	            }
	        },
	        series: [{
	            name: '弱',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            symbolSize: 1,
	            large: true,
	            itemStyle: {
	                normal: {
	                    shadowBlur: 2,
	                    shadowColor: 'rgba(37, 140, 249, 0.8)',
	                    color: 'rgba(37, 140, 249, 0.8)'
	                }
	            },
	           // data: weiboData[0]

	        }, {
	            name: '中',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            symbolSize: 1,
	            large: true,
	            itemStyle: {
	                normal: {
	                    shadowBlur: 2,
	                    shadowColor: 'rgba(14, 241, 242, 0.8)',
	                    color: 'rgba(14, 241, 242, 0.8)'
	                }
	            },
	            //data: weiboData[1]
	           data:[['80','32',1],['90','42',1]]
	            
	        }, {
	            name: '强',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            symbolSize: 1,
	            large: true,
	            itemStyle: {
	                normal: {
	                    shadowBlur: 2,
	                    shadowColor: 'rgba(255, 255, 255, 0.8)',
	                    color: 'rgba(255, 255, 255, 0.8)'
	                }
	            },
	          //  data: weiboData[2]
	        }]
		}
		
		mapChart.setOption(mapChartsOptions)
	}
}


Index.init();
