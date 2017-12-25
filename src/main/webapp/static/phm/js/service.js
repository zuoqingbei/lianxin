

var Service = {
	bodyScale:$(window).height()/595,
	init:function(){
		var that = this;
		setTimeout(function(){
			that.echartsMap();
			that.echartsArea();
			//that.echartsTime();
			
		},100)
//		this.scrollWatch()
		this.clickSelected()
	},
	clickSelected:function(){
	
	},
	
	myHeight:35*this.bodyScale,
//	scrollWatch:function(){
//		var that = this;
//		console.log(that.bodyScale);
//		//var myHeight = 35*that.bodyScale;
//		that.myHeight = 35*that.bodyScale;
//		var time=0;
//		$(".content").scroll(function(){
//			//滚动条到顶部的距离
//			 var totalheight = $(".content").scrollTop();
//			 console.log(totalheight, that.myHeight	)
////			 console.log(1)
//			if (that.myHeight >totalheight) {
//				 console.log(2)
//				//模拟生成tr
//				time+=1;
////				alert(myHeight);
//				$.ajax({
//		    			type:"get",
//		    			url:"findPageFaultInfo",
//		    			 data:{page:time,pageSize:"1",f_object:""}, 
//		    			success:function(data){
//		    				var fautlInformation="";
//			    			$.each(data,function(index,element){
//			    				fautlInformation+=
//			    				"<tr class='row'>"
//				    				+"<td class='col'>"+element.f_object+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_xx_bianma+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_xx_miaoshu+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_yy_bianma+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_yy_miaoshu+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_weihao+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_maintenance+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_maintenance+"<div class='slide'></div></td>"
//				    				+"<td class='col'>"+element.f_zr_category+"<div class='slide'></div></td>"
//			    				+"</tr>";
//							
//			    			});
//			    			
//			    			$("#faultInfo").append(fautlInformation);
//		    			},
//		    			error:function(){alert("加载信息错误11 ")}
//		    		}) ; 
//		    		
////			   for(var i=0;i<2;i++){
////			   		var mytr = document.createElement("tr");
////			   		for(var j=0;j<9;j++){
////			   			var mytd = document.createElement("td")
////			   			$(mytr).append(mytd);
////			   		}
////			   		$(".content").append(mytr)
////			   }
////			   myHeight += 2*$("tr").height();
//			   that.myHeight += 2*$("tr").height();
//			  
//				//生产
//				
//				
//			} 
//		})
//	},
	echartsMap:function(){
		var that = this;
		var myMapCharts = echarts.init(document.getElementById("myMap"));
		
		var mapChartsOptions = {
//			title:{
//				show:true,
//				text:"全国冰箱故障零件分布图",
//				textStyle:{
//					color:'#fffefe',
//					fontSize:20*that.bodyScale,
//					fontWeight:'normal',
//					align:'center'
//				}
//			},
			legend:{
				x:'left',
				y:'bottom',
				itemWidth:10*that.bodyScale,
				itemHeight:10*that.bodyScale,
				selectedMode:false,
				data:[{
						name:'工贸',
						textStyle:{
							color:'#fffefe',
							fontSize:16*that.bodyScale
						},
					/*	icon:'image://../images/page2/icon.png'*/
						icon:'../static/phm/images/page2/icon.png'
					}]
			},
		    tooltip: {
	//          show: false //不显示提示标签
	            formatter: '{b}', //提示标签格式
	            backgroundColor:"#ff7f50",//提示标签背景颜色
	            textStyle:{
	            	color:"#fff",
		        	fontSize:16*that.bodyScale
	            } //提示标签字体颜色
	        },
			series : [
		        {
		            name: '全国',
		            type: 'map',
		            mapType: 'china',
		            itemStyle: {
		                normal: {
		                	color:'#0091d0',
		                    borderWidth: 0,//区域边框宽度
		                    borderColor: '#000419',//区域边框颜色
		                    areaColor:'#0091d0',//区域颜色
	//	                    shadowBlur:2,
	//	                    shadowColor:'#000',
	//	                    shadowOffsetX:-8,
	//	                    shadowOffsetY:3
		                },
		                emphasis: {
		                    color:"#00e673",
		                }
	           	 	},
		            data:[],      
		        },
		        {
		            name: '工贸',
		            type: 'map',
		            mapType: 'china',
		            data:[],
		            markPoint : {
		                symbol:'circle',
	            		symbolSize:5*that.bodyScale,
		                effect : {
		                    show: true,
		                    shadowBlur : 0
		                },
		                itemStyle:{
		                    normal:{
		                    	color:'#eff962',
		                        label:{show:false}
		                    },
		                    emphasis: {
		                        label:{position:'top'}
		                    }
		                },
		                data : [
		                    {name:'新疆',x:100*that.bodyScale,y:140*that.bodyScale},
		                    {name:'甘肃',x:160*that.bodyScale,y:180*that.bodyScale},
		                    {name:'四川',x:220*that.bodyScale,y:220*that.bodyScale},
		                    {name:'河南',x:320*that.bodyScale,y:190*that.bodyScale},
		                    {name:'吉林',x:380*that.bodyScale,y:110*that.bodyScale},
		                ]
		            }
		        } 
		    ]
		}
		myMapCharts.setOption(mapChartsOptions)
//		myMapCharts.on('click',function(){
//			console.log(1)
//		})
	},
	echartsArea:function(){
		var that = this;
		var myAreaCharts = echarts.init(document.getElementById("bar1"));
		var areaChartsOptions = {
			color: ['#2fcfff'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        textStyle:{
		        	fontSize:16*that.bodyScale
		        }
		    },
		    grid: {
		        x: '15%',//左
		        y:"25%",//上
		        x2: '8%',//右	
		        y2:"15%",//下
		        containLabel: true,
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['华中', '华东', '华北', '华南', '西南', '西北', '东北'],
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
		            nameGap:18*that.bodyScale,//坐标轴名称与轴线之间的距离
		            nameTextStyle:{
		            	color:'#64ccff',
		            	fontSize:16*that.bodyScale,
		            },
		            min:0,
		            max:200,
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
//		            barWidth: 50*that.bodyScale,
		            barCategoryGap:8*that.bodyScale,
		            cursor:'pointer',
		            data:[150, 110, 130, 170, 70, 60, 110],
		            
		        }
		    ]
		}
		myAreaCharts.setOption(areaChartsOptions);
	},
	echartsTime:function(){
		var that = this;
		var myTimeEcharts = echarts.init(document.getElementById("bar2"));
		
		var timeEchartsOptions = {
			color: ['#00e96d'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        },
		        textStyle:{
		        	fontSize:16*that.bodyScale
		        }
		    },
		    grid: {
		        x: '15%',//左
		        y:"25%",//上
		        x2: '10%',//右	
		        y2:"16%",//下
		        containLabel: true,
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['华中', '华东', '华北', '华南', '西南', '西北', '东北'],
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel:{
		            	interval:0,
		            	show:'true',
		            	rotate:-30,
		            	textStyle:{
		            		fontSize:16*that.bodyScale,
		            		color:'#64ccff'
		            	}
		            },
		           splitLine:{
		           	 show:false
		           },
		          
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name:'服务站数量/个',
		            nameLocation:'end',
		             nameGap:18*that.bodyScale,//坐标轴名称与轴线之间的距离
		             nameTextStyle:{
		            	color:'#64ccff',
		            	fontSize:16*that.bodyScale,
		            },
		            min:0,
		            max:500,
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
		            name:'服务站能力匹配',
		            type:'bar',
//		            barWidth: 30*that.bodyScale,
		            barGap:50*that.bodyScale,
		            cursor:'pointer',
		            data:[200, 290, 130, 400, 410, 130, 270],
		            
		        }
		    ]
		}
		
		myTimeEcharts.setOption(timeEchartsOptions);
		
		
	}
}


Service.init();