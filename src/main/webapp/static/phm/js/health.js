
var Health = {
	bodyScale:$(window).height()/595,
	init:function(){
		this.circleEcharts();
		this.mapEcharts();
	//	this.blEcharts();
		this.jkEcharts();
	//	this.zdEcharts();
		this.ydEcharts();
	//	this.zlEcharts();
	},
	circleEcharts:function(){
		var that = this;
		this.bxzxl = echarts.init(document.getElementById("bxzxl"));
		this.jkd = echarts.init(document.getElementById("jkd"));
		this.jkfb = echarts.init(document.getElementById("jkfb"));
		
		var bxzxlOptions = {
			color:["hsl(222,76%,52%)","#00c8fc"],
			tooltip:{
				show:true,
				formatter: "{b} : {d}%" ,
				textStyle:{
					color:"#fff",
					fontSize:13*that.bodyScale
				},
			},
			series:[
				{
					type:'pie',
					name:'冰箱在线率',
					
					itemStyle:{
						normal:{
							//color:"#dd0",
							label:{
								show:true,
								formatter: "{b}{d}%" ,
								textStyle:{
										color:"#fff",
										fontSize:13*that.bodyScale,
									},
								position:'inner'
							},
							labelLine:{
								show:false
							}
						},
						emphasis:{
							label:{
								show:true,
								formatter: "{b}{d}%" ,
								textStyle:{
										color:"#fff",
										fontSize:13*that.bodyScale
									},
								position:'inner'
							},
						}
					},
					data:[/*{value:15,name:"在线"},{value:11.79,name:"离线"}*/],
					center:["50%","50%"],//圆心坐标
					radius:[0,"75%"],//内半径，外半径
					roseType:'radius',
				}
			]
		}
		
		var jkdOptions = {
			color:["#66ccff","#0066ff"],
			tooltip:{
				show:true,
				formatter: "{d}%" ,
				textStyle:{
					color:"#fff",
					fontSize:13*that.bodyScale
				},
			},
			series:[
				{
					type:'pie',
					name:'健康度',
					avoidLabelOverlap: true,
					itemStyle:{
						normal:{
							//color:"#dd0",
							label:{
								show:true,
								formatter: "{c}:{d}%" ,
								textStyle:{
										color:"#64ccff",
										fontSize:30*that.bodyScale,
									},
								position:'center',
								verticalAlign:"middle",
								align:"center"
								
								
							},
							labelLine:{
								show:false
							}
						},
						emphasis:{
							label:{
								show:true,
								formatter: "{d}%" ,
								textStyle:{
										color:"#64ccff",
										fontSize:30*that.bodyScale,
									},
								//position:'center',
								//verticalAlign:"middle"
							},
						}
					},
					data:[/*{
						value:59,name:"良好"
						},
						{
						value:41,name:"底部",
						label:{
							normal:{
								show:false
							},
							emphasis:{
								show:false,
							}
							
						}
					}*/],
					center:["50%","50%"],//圆心坐标
					radius:["50%","75%"],//内半径，外半径
					//roseType:'radius',
				}
			]
		}
		
		var jkfbOptions = {
			color:["#1584fa","#1eaae5","#60db67","#4deb94","#ec7686","#fc83ca","#ffc653"],
			tooltip:{
				show:true,
				formatter: "{b} : {d}%" ,
				textStyle:{
					color:"#fff",
					fontSize:13*that.bodyScale
				},
			},
			series:[
				{
					type:'pie',
					name:'冰箱在线率',
					
					itemStyle:{
						normal:{
							//color:"#dd0",
							label:{
								show:true,
							},
							labelLine:{
								show:false
							}
						},
						emphasis:{
							label:{
								show:false,
							},
						}
					},
					data:[],
					center:["50%","50%"],//圆心坐标
					radius:[0,"75%"],//内半径，外半径
					roseType:'radius',
				}
			]
			
		}
		
		this.bxzxl.setOption(bxzxlOptions);
		this.jkd.setOption(jkdOptions);
		this.jkfb.setOption(jkfbOptions);
	},
	mapEcharts:function(){
		var that = this;
		var mymap = echarts.init(document.getElementById("map"));
		var mapOptions = {
			legend:{
				show:true,
				x:"15%",
				y:"bottom",
				itemWidth:10*that.bodyScale,
				itemHeight:10*that.bodyScale,
				selectedMode:false,
				padding:10*that.bodyScale,
				textStyle:{
					fontSize:13*that.bodyScale,
					color:"#fff"
				},
				data:[{name:"健康度",icon:'../static/phm/images/page2/icon.png'}]
			},
			dataRange: {
		        min : 1,
		        max : 100,
		        precision:0,
		        itemWidth:20*that.bodyScale,
				itemHeight:10*that.bodyScale,
		        calculable : true,//不可拖动
		        splitNumber:0,
		        padding:10*that.bodyScale,
		        selectedMode:false,
//		        splitList:{
//		        	start:0,
//		        	end:124,
//		        },
//		        text:['高','低'],
		        color: ['aqua','lime', 'yellow','orange','#ff3333'],
		        textStyle:{
		            color:'#fff',
		            fontSize:13*that.bodyScale
		        }
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
		                	color:'#0091d1',
		                    borderWidth: 1.5*that.bodyScale,//区域边框宽度
		                    borderColor: '#000619',//区域边框颜色
		                    areaColor:'#0091d0',//区域颜色
		                },
		                emphasis: {
//		                    color:"#00e673",
		                }
	           	 	},
		            data:[],      
		        },
		        {
		            name: '良好',
		            type: 'map',
		            mapType: 'china',
		            data:[],
		            markPoint : {
		                symbol:'circle',
	            		symbolSize:8*that.bodyScale,
		                effect : {
		                    show: true,
		                    shadowBlur : 0,
		                    period:8,
		                    color:"#64ccff"
		                },
		                itemStyle:{
		                    normal:{
		                    	color:'lime',
		                        label:{show:false},
		                    },
		                    emphasis: {
		                        label:{position:'top'}
		                    }
		                },
		                data : [
		                    {name:'良好',x:150*that.bodyScale,y:110*that.bodyScale},
		                    {name:'良好',x:120*that.bodyScale,y:100*that.bodyScale},
		                    {name:'良好',x:350*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:350*that.bodyScale,y:170*that.bodyScale},
		                    {name:'良好',x:350*that.bodyScale,y:180*that.bodyScale},
		                    {name:'良好',x:366*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:357*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:359*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:356*that.bodyScale,y:210*that.bodyScale},
		                    {name:'良好',x:367*that.bodyScale,y:190*that.bodyScale},
		                    {name:'良好',x:368*that.bodyScale,y:195*that.bodyScale},
		                    {name:'良好',x:369*that.bodyScale,y:198*that.bodyScale},
		                    {name:'良好',x:366*that.bodyScale,y:218*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:217*that.bodyScale},
		                    {name:'良好',x:356*that.bodyScale,y:210*that.bodyScale},
		                    {name:'良好',x:357*that.bodyScale,y:208*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:200*that.bodyScale},
		                    {name:'良好',x:359*that.bodyScale,y:204*that.bodyScale},
		                    {name:'良好',x:356*that.bodyScale,y:205*that.bodyScale},
		                    {name:'良好',x:357*that.bodyScale,y:203*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:201*that.bodyScale},
		                    {name:'良好',x:356*that.bodyScale,y:210*that.bodyScale},
		                    {name:'良好',x:360*that.bodyScale,y:180*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:185*that.bodyScale},
		                    {name:'良好',x:359*that.bodyScale,y:168*that.bodyScale},
		                    {name:'良好',x:340*that.bodyScale,y:160*that.bodyScale},
		                    {name:'良好',x:348*that.bodyScale,y:165*that.bodyScale},
		                    {name:'良好',x:349*that.bodyScale,y:158*that.bodyScale},
		                    {name:'良好',x:330*that.bodyScale,y:140*that.bodyScale},
		                    {name:'良好',x:328*that.bodyScale,y:145*that.bodyScale},
		                    {name:'良好',x:329*that.bodyScale,y:148*that.bodyScale},
		                    {name:'良好',x:356*that.bodyScale,y:220*that.bodyScale},
		                    {name:'良好',x:357*that.bodyScale,y:238*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:230*that.bodyScale},
		                    {name:'良好',x:359*that.bodyScale,y:234*that.bodyScale},
		                    {name:'良好',x:357*that.bodyScale,y:248*that.bodyScale},
		                    {name:'良好',x:358*that.bodyScale,y:240*that.bodyScale},
		                    {name:'良好',x:359*that.bodyScale,y:244*that.bodyScale},
		                ]
		            },
		            
		        },
		        {
		            name: '一般',
		            type: 'map',
		            mapType: 'china',
		            data:[],
		            markPoint : {
		                symbol:'circle',
	            		symbolSize:8*that.bodyScale,
		                effect : {
		                    show: true,
		                    shadowBlur : 0,
		                    period:8,//周期，数字越大周期越长
		                    color:"#75ff7e"
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
		                    {name:'一般',x:100*that.bodyScale,y:140*that.bodyScale},
		                    {name:'一般',x:160*that.bodyScale,y:180*that.bodyScale},
		                    {name:'一般',x:220*that.bodyScale,y:220*that.bodyScale},
		                    {name:'一般',x:320*that.bodyScale,y:190*that.bodyScale},
		                    {name:'一般',x:380*that.bodyScale,y:110*that.bodyScale},
		                ]
		            }
		        } 
		    ]
		}
		mymap.setOption(mapOptions);
		
		mymap.on("click",function(params){
			//if(params.dataIndex !== 0) {
				window.location.href = "status";
			//}
		})
	},
	blEcharts:function(){
		var that = this;
		this.blcpqk = echarts.init(document.getElementById("blcpqk"));
		var blcpqkOptions = {
			color:["#5ed1ff"],
			 grid: {
		        x: '12%',//左
		        y:"8%",//上
		        x2: '5%',//右	
		        y2:"12%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
			yAxis:{
				type:'value',
				//max:1000,
				//min:0,
				//splitNumber:4,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					},
					margin:10*that.bodyScale
				},
				splitLine:{
	           	 show:true,
	           	 lineStyle:{
	           	 	color:'#1e3152',
	           	 	width:that.bodyScale
	           	 }
	           }
			},
			xAxis:{
				type:'value',
				//max:8,
				//min:1,
				//splitNumber:7,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					}
				},
				splitLine:{
	           	 show:false,
	           }
			},
			series:[
				{
					type:"bar",
					data:[[1,875],[2,800],[3,475],[4,725],[5,700],[6,800],[7,650],[8,850]],
					barWidth:13*that.bodyScale
				}
			]
		}
		
		this.blcpqk.setOption(blcpqkOptions);
	},
	jkEcharts:function(){
		var that = this;
		var jkddb = echarts.init(document.getElementById("jkddb"));
		var jkddbOptions = {
			color:["#5ed1ff"],
			 grid: {
		        x: '12%',//左
		        y:"8%",//上
		        x2: '5%',//右	
		        y2:"27%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
			yAxis:{
				type:'value',
				max:100,
				min:0,
				splitNumber:4,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					},
					margin:10*that.bodyScale
				},
				splitLine:{
	           	 show:true,
	           	 lineStyle:{
	           	 	color:'#1e3152',
	           	 	width:that.bodyScale
	           	 }
	           }
			},
			xAxis:{
				type:'category',
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					}
				},
				splitLine:{
	           	 show:false,
	          },
	          data:["设计","供应商","工厂","其他"]
			},
			series:[
				{
					type:"bar",
					data:[100,70,76,55],
					barWidth:13*that.bodyScale
				}
			]
		}
		
		jkddb.setOption(jkddbOptions);
	},
	zdEcharts:function(){
		var that = this;
		var zdyc = echarts.init(document.getElementById("zdyc"));
		var zdycOptions = {
			color:["#5ed1ff"],
			 grid: {
		        x: '12%',//左
		        y:"8%",//上
		        x2: '5%',//右	
		        y2:"27%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
			yAxis:{
				type:'value',
				max:100,
				min:0,
				splitNumber:4,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					},
					margin:10*that.bodyScale
				},
				splitLine:{
	           	 show:true,
	           	 lineStyle:{
	           	 	color:'#1e3152',
	           	 	width:that.bodyScale
	           	 }
	           }
			},
			xAxis:{
				type:'value',
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					}
				},
				splitLine:{
	           	 show:false,
	          	},
	          	min:1,
	          	max:6,
	          	splitNumber:5
			},
			series:[
				{
					type:"line",
					data:[[1,95],[2,77],[3,100],[4,80],[5,75],[6,90]],
					itemStyle:{
						normal:{
							lineStyle:{
								color:"#64ccff",
								width:2*that.bodyScale,
//								shadowColor:"rgba(36,70,105,0.5)",
//								shadowBlur:10,
//								shadowOffsetY:1000
							}
						}
					}
				}
			]
		}
		
		zdyc.setOption(zdycOptions);
	},
	ydEcharts:function(){
		var that = this;
		var ydqk = echarts.init(document.getElementById("ydqk"));
		var ydqkOptions = {
			color:["#5ed1ff"],
			 grid: {
		        x: '12%',//左
		        y:"8%",//上
		        x2: '5%',//右	
		        y2:"27%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
			yAxis:{
				type:'value',
				max:100,
				min:0,
				splitNumber:4,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					},
					margin:10*that.bodyScale
				},
				splitLine:{
	           	 show:true,
	           	 lineStyle:{
	           	 	color:'#1e3152',
	           	 	width:that.bodyScale
	           	 }
	           }
			},
			xAxis:{
				type:'category',
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					}
				},
				splitLine:{
	           	 show:false,
	          },
	          data:["1","2","3","4"]
			},
			series:[
				{
					type:"bar",
					data:[100,70,76,55],
					barWidth:13*that.bodyScale
				}
			]
		}
		
		ydqk.setOption(ydqkOptions);
	},
	zlEcharts:function(){
		var that = this;
		var zlqs = echarts.init(document.getElementById("zlqs"));
		var zlqsOptions = {
			color:["#5ed1ff"],
			 grid: {
		        x: '12%',//左
		        y:"8%",//上
		        x2: '5%',//右	
		        y2:"27%",//下
		        containLabel: true,
		        borderWidth:0,
		    },
			yAxis:{
				type:'value',
				max:100,
				min:0,
				splitNumber:4,
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					},
					margin:10*that.bodyScale
				},
				splitLine:{
	           	 show:true,
	           	 lineStyle:{
	           	 	color:'#1e3152',
	           	 	width:that.bodyScale
	           	 }
	           }
			},
			xAxis:{
				type:'value',
				axisLine:{
					show:false
				},
				axisLabel:{
					textStyle:{
						color:"#64ccff",
						fontSize:13*that.bodyScale
					}
				},
				splitLine:{
	           	 show:false,
	          	},
	          	min:1,
	          	max:6,
	          	splitNumber:5
			},
			series:[
				{
					type:"line",
					data:[[1,95],[2,77],[3,100],[4,80],[5,75],[6,90]],
					itemStyle:{
						normal:{
							lineStyle:{
								color:"#64ccff",
								width:2*that.bodyScale,
//								shadowColor:"rgba(36,70,105,0.5)",
//								shadowBlur:10,
//								shadowOffsetY:1000
							}
						}
					}
				},
				{
					type:"line",
					data:[[1,95],[2,55],[3,70],[4,95],[5,60],[6,80]],
					itemStyle:{
						normal:{
							lineStyle:{
								color:"#f69f50",
								width:2*that.bodyScale,
//								shadowColor:"rgba(36,70,105,0.5)",
//								shadowBlur:10,
//								shadowOffsetY:1000
							}
						}
					}
				},
				{
					type:"line",
					data:[[1,90],[2,65],[3,85],[4,90],[5,70],[6,85]],
					itemStyle:{
						normal:{
							lineStyle:{
								color:"#00e771",
								width:2*that.bodyScale,
//								shadowColor:"rgba(36,70,105,0.5)",
//								shadowBlur:10,
//								shadowOffsetY:1000
							}
						}
					}
				},
				{
					type:"line",
					data:[[1,92],[2,70],[3,950],[4,66],[5,80],[6,55]],
					itemStyle:{
						normal:{
							lineStyle:{
								color:"#fc6459",
								width:2*that.bodyScale,
//								shadowColor:"rgba(36,70,105,0.5)",
//								shadowBlur:10,
//								shadowOffsetY:1000
							}
						}
					}
				}
			]
		}
		
		zlqs.setOption(zlqsOptions);
	},
}


Health.init();