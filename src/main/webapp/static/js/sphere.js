// var mapSphere = function mapSphere(myChart) {
function mapSphere() {
    var  opts;
    $.ajax({
    	url: contextPath+'/lab/labAjax',
        async:false,
        success: function (mData) {
            var markPointStyle = {
                normal: {
                    color: '#fbf679'
                }
            };
            // Airport: [name（机场名？）, city, country, longitude（经度）, latitude（纬度）]
            //map和forEach都可以对数组进行遍历，区别是map可以返回一个修改后的数组，但不影响原数组
            var airports=getAirports(mData,markPointStyle);
            // Route: [airlineIndex(航空公司编号), sourceAirportIndex（起点）, destinationAirportIndex（终点）]


            opts = {
            		   title: {
            		        show:true,
            		        text: '全球实验中心互联，加速产品迭代升级，创用户最佳体验',
            		        //subtext: '数据纯属虚构',
            		        x: 'center',
            		        textStyle: {
            		            color: '#66ccff',
            		            fontSize:13,
            		        }
            		    },
                    legend: {
                        show: false,
                        //   遍历航空公司名称显示图例
                        data:legendData(mData),
                        selected: {},
                        x: 'center',
                        y:"bottom",
                        // orient: 'vertical',
                        textStyle: {
                            color: 'white'
                        }
                    },
                    tooltip: {
                        formatter: '{b}'
                    },
                    series: [{
                         type: 'map3d',
                    mapType: 'world',
                    flat: false, /*是否使用平面图*/
                    flatAngle: 0,
                    mapLocation: {
                        x: 0,
                        y: 0,
                        width: '100%',
                        height: '100%'
                    },
                    baseLayer: {
                        backgroundColor: 'rgba(20,143,204,.8)',
                    },
                    itemStyle: {
                        normal: {
                            width: 2,
                            borderWidth: 1,
                            borderColor: '#00ffff',
                            areaStyle: {
                                color: 'rgba(0, 0, 0, 0)'
                            }
                        }
                    },
                        markPoint: {
                            symbol: "triangle",
                            effect: {
                                shadowBlur: 0.1
                            },
                            large: true,
                            symbolSize: 5,
                            data: airports
                        },
                        roam:{
                        zoom: 1.3,
                        minZoom: 1.3,
                        maxZoom: 1.3,
                        // focus:"Pakistan"
                    }
                    }]
                };

                //默认选中
	            opts.legend.data.forEach(function (name) {
	            	opts.legend.selected[name] = true;
	            });
                /*
                 "青岛":[120.33,36.07],
                 "北京":[116.46,39.92],
                 */
	            for(var x=0;x<mData.length;x++){
	            	 opts.series.push({
	                        type: 'map3d',
	                        name: mData[x].shortname,
	                        markLine: {
	                            smooth: true,
	                            effect: {
	                                show: true
	                            },
	                            distance:6,
	                              itemStyle : {
									normal: {
										// 线的颜色默认是取 legend 的颜色
										// color: null
										// 线宽，这里线宽是屏幕的像素大小
										width: 5,
										// 线的透明度
										opacity: 1,
										// color:"rgba(50,143,204,1)",
										color:"#7bb4ff",
										borderWidth:5,
										borderColor:"#7bb4ff",
										lineStyle: {
											type: 'solid',
											shadowBlur: 5
										}
									}
								},
								distance: 0,
	                            data: lineGeoCoord(mData[x])
	                        }
	                       
	                    });
	            }

               // myChart.hideLoading();
          

        }

    });
    return opts;

};