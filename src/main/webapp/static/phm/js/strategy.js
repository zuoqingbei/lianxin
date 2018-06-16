
var Strategy = {
	bodyScale:$(window).height()/595,
	init:function(){
		var that = this;
		setTimeout(function(){
			
			that.tendencyChart();
		},500)
	},
	tendencyChart:function(){
		var that = this;
		var myChart = echarts.init(document.getElementById("line"));
		
		var options = {
			tooltip: {
		       trigger: "item",
		       formatter: "{a} <br/>{b} : {c}"
		   },
		   grid: {
		        x: '8%',//左
		        y:"25%",//上
		        //x2: '8%',//右	
		        y2:"20%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
		   xAxis: [
		       {
		           type: "category",
		           splitLine: {show: false},
		        	 data: ["1", "2", "3", "4", "5", "6", "7", "8", "10", "11", "12"],
		        	axisLabel:{
		        		textStyle:{
		        			color:'#64ccff',
		        			fontSize:16*that.bodyScale,
		        		}
		        	},
		        	axisLine:{
		        		show:false,
		        	},
		        	splitLine:{
		           	 	show:false,
		           }
		       }
		   ],
		   yAxis: [
		       {
		           type: "value",
		         	max:100,
		            splitNumber:5,
		            splitLine: {show: true,
		            	lineStyle:{
		            		color:'#1e3257',
		            	}
		            },
		            axisLabel:{
		        		textStyle:{
		        			color:'#64ccff',
		        			fontSize:16*that.bodyScale,
		        		}
		        	},
		        	axisLine:{
		        		show:false,
		        	}
		       }
		   ],
		   series: [
		   
		       {
		           name: "",
		           type: "line",
		           itemStyle:{
		           		normal:{
		           			color:'#66ccff',
		           			lineStyle:{
		           				color:'#66ccff'
		           			}
		           		}
		           },
		           data: [9,9,8,9,9,10,12,13,30,60,90]
					
		       },
		       {
		           name: "",
		           type: "line",
		           itemStyle:{
		           		normal:{
		           			color:'#fc9200',
		           			lineStyle:{
		           				color:'#fc9200'
		           			}
		           		}
		           },
		           data: [15,15,12,14,15,17,20,24,40,65,95]
					
		       }
		   ]
		}
		
		myChart.setOption(options);
	}
}

Strategy.init();
