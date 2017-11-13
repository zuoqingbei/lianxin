
var Status = {
	bodyScale:$(window).height()/595,
	init:function(){
		var that = this;
		setTimeout(function(){
		//	that.mapCharts();
		//	that.stateCharts();
		},0)
		that.liquidMove([["liquid1",100,0,40,"℃"],["liquid2",100,0,10,"℃"],["liquid3",100,0,20,"℃"],["liquid4",100,0,60,"℃"],["liquid5",100,0,30,"℃"],["liquid6",100,10,80,"℃"]]);
		this.pageChanged();
		this.tabChanges();
	},
	delayIframe:function(param){
		//延迟加载iframe标签
		setTimeout(function(){
			$(".ztqsBox").html("<iframe src='http://47.95.109.158/#/product/"+param+"' width='100%' height='100%'></iframe>");
		},1000);
	},
	tabChanges:function(){
		//syfxBox   leftStates
		$(".syfx").click(function(){
			//所有nav去掉active样式
			var navs = $(".tnav");
			for(var nav of navs){
				$(nav).removeClass("navActive")
			}
			//给点击的nav加active样式
			$(this).addClass("navActive")
			
			//切换内容
			$(".leftStates").css("display","none");
			$(".syfxBox").css("display","block");
			$(".ztqsBox").css("display","none");
		
			//改变宽度
			$(".state-box").css("width","200%");
			//显示左右按钮
			$(".fyBtn").css("visibility","visible");
			//别的tab页面在第二页的时候点进来确保能显示
			$(".state-box").css({"left":"0"});
			//$(".state-box").css({"left":"-100%"});
		});
		$(".sszt").click(function(){
			//所有nav去掉active样式
			var navs = $(".tnav");
			for(var nav of navs){
				$(nav).removeClass("navActive")
			}
			//给点击的nav加active样式
			$(this).addClass("navActive")
			
			//切换内容
			$(".leftStates").css("display","block");
			$(".syfxBox").css("display","none");
			$(".ztqsBox").css("display","none");
			//改变宽度
			$(".state-box").css("width","200%");
			//显示左右按钮
			$(".fyBtn").css("visibility","visible");
			//别的tab页面在第二页的时候点进来确保能显示
			$(".state-box").css({"left":"0"});
		});
		$(".ztqs").click(function(){
			//所有nav去掉active样式
			var navs = $(".tnav");
			for(var nav of navs){
				$(nav).removeClass("navActive")
			}
			//给点击的nav加active样式
			$(this).addClass("navActive")
			
			//切换内容
			$(".leftStates").css("display","none");
			$(".syfxBox").css("display","none");
			$(".ztqsBox").css("display","block");
			
			//改变宽度
			$(".state-box").css("width","100%");
			//别的tab页面在第二页的时候点进来确保能显示
			$(".state-box").css({"left":"0"});
			
			//隐藏左右按钮
			$(".fyBtn").css("visibility","hidden");
		
		});
	},

	liquidMove:function(arr){
			/**c
			 * [["liquid1",100,0,40,"℃"],["liquid2",100,0,70,"℃"]]
			 * 该函数接收一个二维数组作为参数，该数组内容依次是：
			 * className:字符串，类名
			 * max:数字，最大值,默认为100
			 * min:数字 ，最小值,默认为0
			 * num:数字，当前值 ，,默认为40
			 * unit:字符串，单位，默认为℃
			 */
			
			for (var i=0;i<arr.length;i++){
			/*	var max = arr[i][1] || 100;
				var min = arr[i][2] || 0;
				var num = arr[i][3] || 40;
				var unit = arr[i][4] || "℃";*/
				var max = arr[i][1] ;
				var min = arr[i][2] ;
				var num = arr[i][3] ;
				var unit = arr[i][4];
				
				var rate = (num - min) / (max - min);
	            $("."+arr[i][0]).css("height", (rate * 100 +  6 )*0.75 + "%");
	            $("."+arr[i][0]).parent().siblings(".max").text(max+unit);
	             $("."+arr[i][0]).parent().siblings(".mid").text(((max-min)/2+min)+unit);
	             $("."+arr[i][0]).parent().siblings(".min").text(min+unit);
	             $("."+arr[i][0]).parent().siblings(".tempTxt").text(num+unit);
	       }
	},
	pageChanged:function(){
		console.log("123454545")
		
		var that = this;
		//只有实时状态有分页
		$(".leftBtn").click(function(){
			console.log("123")
			$(".state-box").css({"left":"0"});
		})
		$(".rightBtn").click(function(){
			console.log("456")
			$(".state-box").css({"left":"-100%"});
		})
		
	},
//	mapCharts:function(){
//		var that = this;
//		var myMapCharts = echarts.init(document.getElementById("myMap"));
//		
//		var mapChartsOptions = {
////			legend:{
//////				orient: 'vertical',
////				x:65*that.bodyScale,
////				y:442*that.bodyScale,
////				itemWidth:0*that.bodyScale,
////				itemHeight:0*that.bodyScale,
//////				selectedMode:false,
//////				backgroundColor:'#dd0',
//////				data:[]
////				data:[{
////						name:'健康度',
////						textStyle:{
////							color:'#fffefe',
////							fontSize:16*that.bodyScale
////						},
////					}],
////			},
//			dataRange: {
//		        min : 1,
//		        max : 125,
//		        precision:0,
//		        itemWidth:20*that.bodyScale,
//				itemHeight:10*that.bodyScale,
//		        calculable : true,//不可拖动
////		        splitNumber:0,
//		        padding:10*that.bodyScale,
////		        splitList:{
////		        	start:0,
////		        	end:124,
////		        },
////		        text:['高','低'],
//		        color: ['aqua','lime', 'yellow','orange','#ff3333'],
//		        textStyle:{
//		            color:'#fff',
//		            fontSize:13*that.bodyScale
//		        }
//		    },
//		    tooltip: {
//	//          show: false //不显示提示标签
//	            formatter: '{b}', //提示标签格式
//	            backgroundColor:"#ff7f50",//提示标签背景颜色
//	            textStyle:{
//	            	color:"#fff",
//		        	fontSize:16*that.bodyScale
//	            } //提示标签字体颜色
//	        },
//			series : [
//		        {
//		            name: '全国',
//		            type: 'map',
//		            mapType: 'china',
//		            itemStyle: {
//		                normal: {
//		                	color:'#0091d1',
//		                    borderWidth: 1.5*that.bodyScale,//区域边框宽度
//		                    borderColor: '#000619',//区域边框颜色
//		                    areaColor:'#0091d0',//区域颜色
//		                },
//		                emphasis: {
////		                    color:"#00e673",
//		                }
//	           	 	},
//		            data:[],      
//		        },
//		        {
//		            name: '良好',
//		            type: 'map',
//		            mapType: 'china',
//		            data:[],
//		            markPoint : {
//		                symbol:'circle',
//	            		symbolSize:5*that.bodyScale,
//		                effect : {
//		                    show: true,
//		                    shadowBlur : 0,
//		                    period:5
//		                },
//		                itemStyle:{
//		                    normal:{
//		                    	color:'lime',
//		                        label:{show:false}
//		                    },
//		                    emphasis: {
//		                        label:{position:'top'}
//		                    }
//		                },
//		                data : [
//		                    {name:'良好',x:150*that.bodyScale,y:110*that.bodyScale},
//		                    {name:'良好',x:120*that.bodyScale,y:100*that.bodyScale},
//		                    {name:'良好',x:320*that.bodyScale,y:220*that.bodyScale},
//		                   
//		                ]
//		            },
//		            
//		              markLine : {
//		                smooth:true,
//		                smoothness:0,
//		                symbol:['circle','none'],
//		                symbolSize:[10*that.bodyScale,10*that.bodyScale],
//		                effect : {
//		                    show: true,
//		                    scaleSize: 1,
//		                    period: 15,//运动周期
//		                    color: '#fff',
//		                    shadowBlur: 10
//		                },
//		                itemStyle : {
//		                    normal: {
//		                        borderWidth:1*that.bodyScale,
//		                        lineStyle: {
//		                            type: 'solid',
//		                            color:'#64ccff',
//		                            shadowBlur: 10
//		                        }
//		                    }
//		                },
//		            	 data : [
//		                    [{name:'良好',x:320*that.bodyScale,y:220*that.bodyScale},{x:500*that.bodyScale,y:220*that.bodyScale}],
////		                    [{name:'上海'},{name:'昆明',value:90}],
//		                ]
//		            },
//		        },
//		        {
//		            name: '一般',
//		            type: 'map',
//		            mapType: 'china',
//		            data:[],
//		            markPoint : {
//		                symbol:'circle',
//	            		symbolSize:5*that.bodyScale,
//		                effect : {
//		                    show: true,
//		                    shadowBlur : 0,
//		                    period:5
//		                },
//		                itemStyle:{
//		                    normal:{
//		                    	color:'#eff962',
//		                        label:{show:false}
//		                    },
//		                    emphasis: {
//		                        label:{position:'top'}
//		                    }
//		                },
//		                data : [
//		                    {name:'一般',x:100*that.bodyScale,y:140*that.bodyScale},
//		                    {name:'一般',x:160*that.bodyScale,y:180*that.bodyScale},
//		                    {name:'一般',x:220*that.bodyScale,y:220*that.bodyScale},
//		                    {name:'一般',x:320*that.bodyScale,y:190*that.bodyScale},
//		                    {name:'一般',x:380*that.bodyScale,y:110*that.bodyScale},
//		                ]
//		            }
//		        } 
//		    ]
//		}
//		myMapCharts.setOption(mapChartsOptions)
//	},
	stateCharts:function(){
		var that = this;
		//判断 是否存在
		if(!document.getElementById("zsCanvas")){
			return;
		}
		var zs = echarts.init(document.getElementById("zsCanvas"));
		var pqyl = echarts.init(document.getElementById("pqylCanvas"));
		var jsd = echarts.init(document.getElementById("jsdCanvas"));
		var zll = echarts.init(document.getElementById("zllCanvas"));
		
		/*****************************zsOptions***********************/
		var zsOptions = {
			title:{
				text:'x1000 r/min',
				x:40*that.bodyScale,
				y:70*that.bodyScale,
				textStyle:{
					color:'#64ccff',
					fontSize:13*that.bodyScale
				}
			},
//			 tooltip : {
//		        formatter: "{a} <br/>{b} : {c}"
//		    },
		    series : [
		        {
		            name:'转速',
		            type:'gauge',
		            detail : {formatter:'{value}',
		            	textStyle:{
		            		color:'#64ccff',
		            		fontSize:15*that.bodyScale,
		            		fontWeight:600,
		            	}
		            },
		            data:[{value: 4}],
		          itemStyle:{
		          	color:'#64ccff'
		          },
		          startAngle:200,
		          endAngle:-20,
		          min:0,
		          max:10,
		          //radius:"[2%,75%]",
		          radius:["80%","100%"],
		          center:["50%","60%"],
		          axisLine:{
		          	show:true,
		            lineStyle:{
		            	color:'#64ccff',
		              width:15*that.bodyScale
		            }
		          },
		          axisLabel:{
		          	textStyle:{
		          		color:'#fff'
		          	}
		          },
		          splitLine:{
		          	length:that.bodyScale,
		          	lineStyle:{
		          		color:'#64ccff',
		          		width:2*that.bodyScale
		          	}
		          },
		          axisTick:{
		          	show:false
		          },
		          pointer:{
		          	length:40*that.bodyScale,
		            width:5*that.bodyScale,
		            color:'#dd0'
		          },
		          title:{
		          	show:true,
		          	
		          }
		        }
		    ]
		}
		
		/*****************************pqylOptions***********************/
		var pqylOptions = {
			title:{
				text:'x0.01 Mpa',
				x:40*that.bodyScale,
				y:50*that.bodyScale,
				textStyle:{
					color:'#64ccff',
					fontSize:13*that.bodyScale
				}
			},
			 tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    series : [
		        {
		            name:'排气压力',
		            type:'gauge',
		            detail : {formatter:'{value}',
		            	textStyle:{
		            		color:'#64ccff',
		            		fontSize:15*that.bodyScale,
		            		fontWeight:600,
		            	},
		            	offsetCenter:[0,'-5%']
		            },
		            data:[{value: 23}],
		          itemStyle:{
		          	color:'#64ccff'
		          },
		          startAngle:180,
		          endAngle:0,
		          min:0,
		          max:100,
		          //radius:"[2%,75%]",
		          radius:["80%","100%"],
		          center:["50%","60%"],
		          axisLine:{
		          	show:true,
		            lineStyle:{
		            	color:[
		            		[0.23,'#64ccff'],
		            		[1,'#0a5365']
		            	],
		              width:15*that.bodyScale
		            }
		          },
		          splitLine:{
		          	show:false
		          },
		          axisTick:{
		          	show:false
		          },
		          axisLabel:{
		          	show:false
		          },
		          pointer:{
		          	length:0,
		            width:0,
		            color:'#dd0'
		          },
		          title:{
		          	show:true,
		          	
		          }
		        }
		    ]
		}
		/*****************************jsdOptions***********************/
		var jsdOptions = {
			title:{
				text:'M/S²',
				x:60*that.bodyScale,
				y:50*that.bodyScale,
				textStyle:{
					color:'#64ccff',
					fontSize:13*that.bodyScale
				}
			},
			 tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    series : [
		        {
		            name:'加速度',
		            type:'gauge',
		            detail : {formatter:'{value}',
		            	textStyle:{
		            		color:'#64ccff',
		            		fontSize:15*that.bodyScale,
		            		fontWeight:600,
		            	},
		            	offsetCenter:[0,'-5%']
		            },
		            data:[{value: 56}],
		          itemStyle:{
		          	color:'#64ccff'
		          },
		          startAngle:180,
		          endAngle:0,
		          min:0,
		          max:100,
		          //radius:"[2%,75%]",
		          radius:["80%","100%"],
		          center:["50%","60%"],
		          axisLine:{
		          	show:true,
		            lineStyle:{
		            	color:[
		            		[0.56,'#64ccff'],
		            		[1,'#0a5365']
		            	],
		              width:15*that.bodyScale
		            }
		          },
		          splitLine:{
		          	show:false
		          },
		          axisTick:{
		          	show:false
		          },
		          axisLabel:{
		          	show:false
		          },
		          pointer:{
		          	length:0,
		            width:0,
		            color:'#dd0'
		          },
		          title:{
		          	show:true,
		          	
		          }
		        }
		    ]
		}
		/*****************************zllOptions***********************/
		var zllOptions = {
			title:{
				text:'W',
				x:60*that.bodyScale,
				y:50*that.bodyScale,
				textStyle:{
					color:'#64ccff',
					fontSize:13*that.bodyScale
				}
			},
			 tooltip : {
		        formatter: "{a} <br/>{b} : {c}"
		    },
		    series : [
		        {
		            name:'加速度',
		            type:'gauge',
		            detail : {formatter:'{value}',
		            	textStyle:{
		            		color:'#64ccff',
		            		fontSize:15*that.bodyScale,
		            		fontWeight:600,
		            	},
		            	offsetCenter:[0,'-5%']
		            },
		            data:[{value: 170}],
		          itemStyle:{
		          	color:'#64ccff'
		          },
		          startAngle:180,
		          endAngle:0,
		          min:0,
		          max:200,
		          //radius:"[2%,75%]",
		          radius:["80%","100%"],
		          center:["50%","60%"],
		          axisLine:{
		          	show:true,
		            lineStyle:{
		            	color:[
		            		[0.85,'#64ccff'],
		            		[1,'#0a5365']
		            	],
		              width:15*that.bodyScale
		            }
		          },
		          splitLine:{
		          	show:false
		          },
		          axisTick:{
		          	show:false
		          },
		          axisLabel:{
		          	show:false
		          },
		          pointer:{
		          	length:0,
		            width:0,
		            color:'#dd0'
		          },
		          title:{
		          	show:true,
		          	
		          }
		        }
		    ]
		}
		
		zs.setOption(zsOptions);
		pqyl.setOption(pqylOptions);
		jsd.setOption(jsdOptions);
		zll.setOption(zllOptions);
	},
}




Status.init();
