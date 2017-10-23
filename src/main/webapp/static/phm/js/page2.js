
/*function inittwo() {
	var myChart = echarts.init(document.getElementById('bar1')); 
    
}*/




var page2 = {
	bodyScale:$(window).height()/595,
	init:function(){
		var that = this;
		setTimeout(function(){
			that.echartsMap();
			that.echartsArea();
			that.echartsTime();
			
		},1000)
	},
	echartsMap:function(){
		var that = this;
		var myMapCharts = echarts.init(document.getElementById("myMap"));
		
		var mapChartsOptions = {
			
			legend:{
				x:'left',
				y:'bottom',
				data:[{
						name:'工贸',
						textStyle:{
							color:'#fffefe',
							fontSize:16*that.bodyScale
						},
// icon:'image://../images/page2/icon.png'
						icon:'circle'
					}]
			},
		    tooltip: {
	// show: false //不显示提示标签
	            formatter: '{b}', // 提示标签格式
	            backgroundColor:"#ff7f50",// 提示标签背景颜色
	            textStyle:{color:"#fff"} // 提示标签字体颜色
	        },
	        series: [{
	            type: 'map',
	            mapType: 'china',
	           name:'工贸',
	            itemStyle: {
	                normal: {
	                    borderWidth: 0,// 区域边框宽度
	                    borderColor: '#000419',// 区域边框颜色
	                    areaColor:'#0091d0',// 区域颜色
	                    shadowBlur:2,
	                    shadowColor:'#000',
	                    shadowOffsetX:-8,
	                    shadowOffsetY:3
	                },
	                emphasis: {
	                    areaColor:"#0092ce",
	                }
	            },
	            data:[
// {name:'福建', selected:true}//福建为选中状态
	            ]
	        }],
		}
		myMapCharts.setOption(mapChartsOptions)
	},
	echartsArea:function(){
		//alert("dd");
		var that = this;
		var myAreaCharts = echarts.init(document.getElementById("bar1"));
		$.post(contextPath+"/fault/quYuInfoDataAjax", {}, function (data) {
	           var dataName=[];
	           var dataValue=[];
			   $.each(data,function(index,item){
				   dataName.push(item.name);
				   dataValue.push(item.count);
			   });
	   var areaChartsOptions = {
				color: ['#2fcfff'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        x: '5%',// 左
			        y:"20%",// 上
	// x2: '5%',//右
			        y2:"10%",// 下
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : dataName,
			            axisTick: {
			                alignWithLabel: true
			            },
			            axisLabel:{
			            	interval:0,
			            	show:'true',
			            	textStyle:{
			            		fontSize:16*that.bodyScale,
			            		color:'#64ccff'
			            	}
			            },
			            splitLine:{
			           	 show:false,
			           }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            name:'需求量/台',
			            nameLocation:'end',
			            nameTextStyle:{
			            	color:'#64ccff',
			            	fontSize:16*that.bodyScale,
			            },
			            min:0,
			            splitNumber:4,
			            axisLabel:{
			            	show:'true',
			            	textStyle:{
			            		color:'#64ccff',
			            		fontSize:16*that.bodyScale,
			            	}
			            },
			            splitLine:{
			            	lineStyle:{
			            		color:['#123c62']
			            	}
			            }
			        }
			    ],
			    series : [
			        {
			            name:'各区域压缩机备件需求',
			            type:'bar',
	// barWidth: 50*that.bodyScale,
			            barCategoryGap:8*that.bodyScale,
			            cursor:'pointer',
			            data:dataValue,
			            
			        }
			    ]
			}
			myAreaCharts.setOption(areaChartsOptions);
	   
	});
		
		 /*});*/
	},
	echartsTime:function(){
		var that = this;
		var myTimeEcharts = echarts.init(document.getElementById("bar2"));
		/*$.post(contextPath+"/fault/quYuInfoDataAjax", {}, function (data) {*/
		$.post(contextPath+"/fault/DemandInfoDataAjax",{},function(data){
			/*var dataName=[];
	           var dataValue=[];
			   $.each(data,function(index,item){
				   dataName.push(item.name);
				   dataValue.push(item.count);
			   });*/
			var dataName=[];
			var dataValue=[];
			
			$.each(data,function(index,item){
				dataName.push(item.name);
				dataValue.push(item.count);
			});
		
		var timeEchartsOptions = {
			color: ['#00e96d'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        x: '5%',// 左
		        y:"20%",// 上
// x2: '5%',//右
		        y2:"10%",// 下
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : dataName,
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel:{
		            	interval:0,
		            	show:'true',
		            	textStyle:{
		            		fontSize:16*that.bodyScale,
		            		color:'#64ccff'
		            	}
		            },
		           splitLine:{
		           	 show:false
		           }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name:'需求量/台',
		            nameLocation:'end',
		             nameTextStyle:{
		            	color:'#64ccff',
		            	fontSize:16*that.bodyScale,
		            },
		            min:0,
		            splitNumber:5,
		            axisLabel:{
		            	show:'true',
		            	textStyle:{
		            		color:'#64ccff',
		            		fontSize:16*that.bodyScale,
		            	}
		            },
		            splitLine:{
		            	lineStyle:{
		            		color:['#123c62']
		            	}
		            }
		        }
		    ],
		    series : [
		        {
		            name:'各区域压缩机备件需求',
		            type:'bar',
// barWidth: 30*that.bodyScale,
		            barGap:50*that.bodyScale,
		            cursor:'pointer',
		            data:dataValue,
		            
		        }
		    ]
		}
		
		myTimeEcharts.setOption(timeEchartsOptions);
		});
	}
}

page2.init();

