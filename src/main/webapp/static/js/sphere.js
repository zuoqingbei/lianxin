/**
 * 
 */
var mapSphere = function mapSphere(myChart) {
    myChart.showLoading();
        $.ajax({
            url: contextPath+'/lab/labAjax',
            success: function (mData) {
                var markPointStyle = {
                    normal: {
                        color: 'red'
                    }
                };
                // Airport: [name（机场名？）, city, country, longitude（经度）, latitude（纬度）]
                //map和forEach都可以对数组进行遍历，区别是map可以返回一个修改后的数组，但不影响原数组
              /*  var airports = data.airports.map(function (item) {
                    //返回带有标记样式和经纬度的对象
                    return {
                        itemStyle: markPointStyle,
                        geoCoord: [item[3], item[4]]
                    }
                });*/
                var airports=getAirports(mData,markPointStyle);
                // Route: [airlineIndex(航空公司编号), sourceAirportIndex（起点）, destinationAirportIndex（终点）]
               
                var opts = {
                    legend: {
                        show: true,
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
                            x: "0%"
                            // y: "top"
                        },
                        baseLayer: {
                            backgroundColor: '',
                            backgroundImage: contextPath+'/static/img/earth.jpg',
                            quality: 'high'
                        },
                        itemStyle: {
                            normal: {
                                borderWidth: 1,
                                borderColor: 'yellow',
                                areaStyle: {
                                    color: 'rgba(0, 0, 0, 0)'
                                }
                            }
                        },
                        markPoint: {
                            symbol: "heart",
                            effect: {
                                shadowBlur: 0.2
                            },
                            large: true,
                            symbolSize: 10,
                            data: airports
                        },
                        roam: {
                        	autoRotate: false,//是否自动旋转
                            autoRotateAfterStill: 30,
                            focus: 'China'
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
	                            distance:3,
	                            itemStyle: {
		                            normal: {
		                                // 线的颜色默认是取 legend 的颜色
		                                // color: "red",
		                                // 线宽，这里线宽是屏幕的像素大小
		                                borderWidth: 20,
                                        width: '30',
                                        opacity: '1'
		                            }
		                        },
	                            data: lineGeoCoord(mData[x])
	                        }
	                       
	                    });
	            }
	            console.log(opts.series)
                myChart.setOption(opts);

                myChart.hideLoading();

            }

        });
        

};

