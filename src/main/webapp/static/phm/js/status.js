
var Status = {
	bodyScale:$(window).height()/595,
	x1:0,
	x2:90,
	init:function(){
		var that = this;
		setTimeout(function(){
		//	that.mapCharts();
		//	that.stateCharts();
		},0)
		that.liquidMove([["liquid1",100,0,40,"℃"],["liquid2",100,0,10,"℃"],["liquid3",100,0,20,"℃"],["liquid4",100,0,60,"℃"],["liquid5",100,0,30,"℃"],["liquid6",100,10,80,"℃"]]);
		this.pageChanged();
		this.tabChanges();
//		this.lightEffect();
		this.delayLoadiframe();
		this.lightRotate();
	},
	delayLoadiframe:function(){
		setTimeout(function(){
			$("#light-if").attr("src","lightEffect");
		},1000);
	},
	lightRotate:function(){
		var that = this;
		setInterval(function(){
			that.x1 += 1;
			that.x2 += 1;
			$(".light1").css("transform","rotateY("+that.x1+"deg)");
			$(".light2").css("transform","rotateY("+that.x2+"deg)");
		},10);
		
	},
	lightEffect:function(){
		 var $container = $("#container"),
	        $can = $("canvas#circleBox");
	    $container.css("transform", "rotateX(60deg) scale("+this.bodyScale+")");
	    var w, h;
	    $can.attr("width", w = $container.outerWidth());
	    $can.attr("height", h = $container.outerHeight());
	    console.log(w, h);
	    var color = "#fff";
	    var ctx = $can[0].getContext("2d");
	    var t1;

	    ctx.lineWidth = 2;//每个细圈的线宽
	    var fps = 11;//帧频
	    var circleNum = 3;//多少组圆圈,也会影响速度
	    var r = 20; //起始半径
	    var vr = 2;//变化速率
	    var circleDelay = (w/2-r)*(1000/fps)/(vr)/circleNum -(0-vr*12);//每组圆圈的延迟, 括号内减数用来微调首尾衔接
	    var circles = [];
//	    console.log("circleDelay：",circleDelay,"计算周期：",w*(1000/fps)/(2*vr));

	    function Circle() {}
	    Circle.prototype = {
	        init: function () {
	                this.r = r; //半径
	                this.vr = vr ;//放大速率人
	                this.a = 1;//透明度
	                this.vaOuter = .93;//消失变化率
	                this.vaInner = .85;//每个圈内部拖尾变化率
	                this.circleStrokeNum = 20;//每组圈数,和线宽共同影响厚度

	        },
	        draw: function () {//绘制一组圆圈
	            for (var i = 0; i < this.circleStrokeNum; i++) {
	                ctx.strokeStyle = "rgba(70,255,120," + this.a*Math.pow(this.vaInner, i) + ")";
	                ctx.beginPath();
	                var r = (this.r - i > 0) ? this.r - i : 0;
	                ctx.arc(w / 2, h / 2, r, 0, Math.PI * 2 );
	                ctx.stroke();
	            }
	            this.update();

	        },

	        update: function () {//更新圆圈大小
	            if (this.r < w/2) {
	                this.r += this.vr;
	                if (this.r > (w / 3)) {
	                    this.a *= this.vaOuter;
	                }
	            } else {
	                this.init();
//	                console.log("时间差：",new Date()-t1);
	            }
	        }
	    };


	    function circleByCircle() {
	        for (var i = 0; i < circleNum; i++) {
	            setTimeout(function () {
	                var circle = new Circle();
	                circle.init();
	                circles.push(circle);
	            }, circleDelay * i)
	        }
	        t1 = new Date();
	        setInterval(function () {
	            ctx.clearRect(0, 0, w, h);
//	            console.log("------清除画布");
	            for (var j = 0; j < circles.length; j++) {
	                circles[j].draw();
	            }
	        }, 1000/fps)
	    }
	    circleByCircle();
	    /*$(function () {
	        circleByCircle();

	    })*/

	},
	tabChanges:function(){
		var that = this;
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
			$(".gzzdBox").css("display","none");
			$(".gzycBox").css("display","none");
			
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
			$(".gzzdBox").css("display","none");
			$(".gzycBox").css("display","none");
			
			//改变宽度
			$(".state-box").css("width","100%");
			
			
			//别的tab页面在第二页的时候点进来确保能显示
			$(".state-box").css({"left":"0"});
			
			//隐藏左右按钮
			$(".fyBtn").css("visibility","hidden");
			
			//调用echarts函数
			if(that.t1) {
				//如果有定时器，先清除定时器
				clearInterval(that.t1);
			}
			that.ztqsCharts();
		});
		//点击故障诊断
		$(".gzzd").click(function(){
			//所有nav去掉active样式
			var navs = $(".tnav");
			for(var nav of navs){
				$(nav).removeClass("navActive");
			}
			//给点击的nav加active样式
			$(this).addClass("navActive");
			//切换内容
			$(".leftStates").css("display","none");
			$(".syfxBox").css("display","none");
			$(".ztqsBox").css("display","none");
			$(".gzzdBox").css("display","block");
			$(".gzycBox").css("display","none");
		});
		//点击故障预测
		$(".gzyc").click(function(){
			//所有nav去掉active样式
			var navs = $(".tnav");
			for(var nav of navs){
				$(nav).removeClass("navActive");
			}
			//给点击的nav加active样式
			$(this).addClass("navActive");
			//切换内容
			$(".leftStates").css("display","none");
			$(".syfxBox").css("display","none");
			$(".ztqsBox").css("display","none");
			$(".gzzdBox").css("display","none");
			$(".gzycBox").css("display","block");
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
		//console.log("123454545")
		
		var that = this;
		
		$(".leftBtn").click(function(){
			if($(".syfxBox").css("display") == 'block'){
				
				$(".state-box").css({"left":"0"});
			}else if($(".leftStates").css("display") == 'block'){
				//改变数据
				//console.log("改变数据")
			}
		})
		$(".rightBtn").click(function(){
			if($(".syfxBox").css("display") == 'block'){
				$(".state-box").css({"left":"-100%"});
				
			}else if($(".leftStates").css("display") == 'block'){
				//改变数据
			}
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
		              width:12*that.bodyScale
		            }
		          },
		          axisLabel:{
		          	textStyle:{
		          		color:'#fff',
		          		fontSize:5*that.bodyScale,
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
	ztqsCharts:function(){
		/*状态趋势tab页*/
		var that = this;
		
		var ztqsDom = echarts.init(document.getElementById("ztqsCanvas"));
		var color = ["#64ccff","#54c6e7","#dd0617","#3f6e84","#5544fa","#122ffc","#ee554a","#ade56a","#e4820f","#f85a64"];
		var xtxt;
		var axisData = [];
		function returnAxisData(){
			xtxt = new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();
			return xtxt;
		}
		
		//初始化x轴坐标
		for (var i = 0; i <10; i++) {
		    axisData.push(returnAxisData());
		}
		
		
		var ztqsOptions = {};
		ztqsDom.showLoading();
		$.ajax({
			url:"http://localhost:8088/api/yzd/product/"+sncode,
			
			type:"get",
			success:function(res){
				//console.log(res.info)
				var legendData = [];//用来存放温度的键的数组，也就是图例的数据
				var data = [];//用来存放温度的值的数组
				var lineArr = [];//用来存放每条线的数组
				var seriesArr = [];//用来存放series的数组
				var colorArr = [];//用来存放每条线的颜色数组
				
				//遍历对象info的键
				var n = 0;
				for(var Key in res.info) {
					
					//判断Key是否含有T，是的话就是温度
					if(Key.indexOf("T") != -1 && Key.length <5) {
						legendData.push({name:Key,textStyle:{color:color[n]}});
						data.push(res["info"][Key]);
						n++;
					}
				}
				//console.log(legendData)
				//console.log(data)
				
				var indicatorArr =  res.monitorConf;//用来存放数据指标的数组
				for(var i=0;i<legendData.length;i++){
					//遍历提示数据的长度，来改变提示值，例如：t1冷藏室温度
					for(var k=0;k<indicatorArr.length;k++){
						if(indicatorArr[k].feature == legendData[i].name){
							var str = indicatorArr[k]["param"]["title"];
							//console.log("执行了")
							legendData[i].name += str;
						}
					}
					
					//遍历提示数据的长度，来生成series的数组
					lineArr = [];
					for(var j=0;j<10;j++){
						//每条线要显示几个点
						lineArr.push(data[i]);
					}
					colorArr.push(color[i]);
					seriesArr.push({
						name:legendData[i].name,
						type:'line',
						data:lineArr,
						itemStyle:{
							normal:{
								lineStyle:{
									color:colorArr[i],
									width:2*that.bodyScale
								}
							}
						}
					})
				}
				
				
				ztqsDom.hideLoading();
				ztqsDom.setOption({
					color:['#64ccff'],
					 grid:{
					 	borderWidth:0,
					 	x:105*that.bodyScale,
					 	y:55*that.bodyScale,
					 	x2:25*that.bodyScale,
					 	y2:35*that.bodyScale,
					 },
				     tooltip : {
				        trigger: 'axis',
				        axisPointer:{
				            show: true,
				            type : 'cross',
				            lineStyle: {
				                type : 'dashed',
				                width : 10*that.bodyScale
				            }
				        },
				        textStyle:{
				        	color:'#fff',
				        	fontSize:13*that.bodyScale
				        },
				       /* formatter : function (params) {
				        	console.log(params)
				        	var sname = "";
				        	
				            for(var i=0;i<params.length;i++){
				            	if(params[i]["0"] == legendData[i]["name"]){
				            		console.log(params[i]["0"],legendData[i]["name"])
				            		sname = params[i]["0"];
				            	}
				            }
				            return sname;
				        }*/
				    },
				    legend: {
				        data:legendData,
				        textStyle:{
				        	fontSize:10*that.bodyScale,
				        },
				        itemWidth:13*that.bodyScale,
				        itemHeight:13*that.bodyScale
				    },
				    xAxis : [
				        {
				            type: 'category',
				            axisLabel:{
				            	textStyle:{
				             		color:'#fff',
				             		fontSize:13*that.bodyScale
				             	}
				            },
				            splitLine:{
				            	show:false
				            },
				            axisLine: {
				                lineStyle: {
				                    color: '#66ccff',
				                    width:2*that.bodyScale,
				                }
				            },
				            data:axisData
				        }
				    ],
				    yAxis : [
				        {
				            type: 'value',
				            axisLine: {
				                lineStyle: {
				                    color: '#66ccff',
				                    width:2*that.bodyScale,
				                }
				            },
				             axisLabel:{
				             	textStyle:{
				             		color:'#fff',
			             			fontSize:13*that.bodyScale
				             	}
				            },
				             splitLine:{
				            	show:false
				            },
				            //min:0,
				            //max:50
				        }
				    ],
				    series : seriesArr
				});
			}
		});
		
	},
}




Status.init();
