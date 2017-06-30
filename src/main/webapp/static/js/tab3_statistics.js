/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧tab3数据统计
 */
function loadTab3Data() {
    //左-左-下 #39 散点图 订单类别
    orderTypeAjax("myChart39", "整机", 30);
    //左-中-上 #43 同心圆 一次合格率
    productLineForTab3Tab3Ajax("myChart43", 0);
    //左-中-下 #41 折线图 一次合格率
    findOrderPassForProAjax("myChart41", "整机");
    //左-右-上 #44 同心圆 问题闭环率
    productLineForTab3Tab3Ajax("myChart44", 1);
    //左-右-下 #42 折线图 问题闭环率
    findOrderPassForProAjax("myChart42", "模块");

    //右-左-上 #6 折线图 订单及时率
    findOrderYearRateForTab3();
    //右-左-下-左 #45 雷达图 订单及时率同比变化
    findOrderYearRateForProductAjax();
    //右-左-下-右 #46 折线组图 订单及时率趋势变化
    findOrderMonthRateForProductAjax();
    //右-右-上 #7 折线图 用户满意度
    satisfactionStatisForMonthForTab3Ajax();
    //右-右-下-左 #47 竖条组图 用户满意度同比变化
    satisfactionStatisForYearTab3Ajax2016();
    //右-右-下-右 #49 折线组图 用户满意度趋势变化 到月数据统计
    productLineAndMonthForTab3Ajax();
    /*
     //整机 模块 订单类别占全部订单占比统计
     findOrderTypePercentTab3Ajax();
     //一次合格率  整体统计
     findOrderPassForAllAjax();
     //数据结果 订单类别 模块
     orderTypeAjax("myChart40","模块",240);
     //findOrderMonthTypeForProduct();

     //获取某一年订单整体及时率
     orderYearRateAjax();
     //同期 环比满意度占比统计
     satisfactionChangeForTab3Ajax();
     //#47某一年分 到产线满意度统计
     satisfactionStatisForYearTab3Ajax2017();
     //统计当前以及同比 模块 整机问题闭环率tab3
     questionForMkZjTab3Ajax();
     */
}

//左-左-下 #39 散点图 订单类别
function orderTypeAjax(myChartIds, desName, divisor) {
    $.post(contextPath + '/lab/findOrderMonthTypeForProductAjax', {
        "labTypeCode": labTypeCode,
        "desName": desName
    }, function (res) {
        var data = [];
        var yData = [];//产线
        var xData = [];//类型
        //准备数据
        $.each(res, function (index, item) {
            yData.push(item[0].product_line_name);
            for (var m = item.length - 1; m >= 0; m--) {
                var it = item[m];
                if (index == 0) {
                    xData.push(it.name);
                }
                //拼接数据
                var mV = Math.sqrt(parseInt(it.num));
                var value = [res.length - index - 1, m, dealNumberTab3(mV)];//暂时数量除以divisor
                //var value=[res.length-index-1,m,parseInt(it.num)/divisor*bodyScale];//暂时数量除以divisor
                data.push(value);
            }
            /*$.each(item,function(ind,it){
             if(index==0){
             xData.push(it.name);
             }
             //拼接数据
             var mV=Math.sqrt(parseInt(it.num));
             //var value=[index,ind,dealNumberTab3(mV)];//暂时数量除以divisor
             var value=[index,ind,parseInt(it.num)/divisor*bodyScale];//暂时数量除以divisor
             data.push(value);
             });*/
        });
        yData.reverse();
        //生成option
        var myChart = echarts.init(document.getElementById(myChartIds));
        right_echarts.push(myChart);
        myChart.setOption(getScatterEcharts());
        data = data.map(function (item) {
            return [item[1], item[0], item[2]];
        });
        myChart.setOption({
            color: ["#66ccff"],
            grid: {
                top: '5%',
                left: '4%',
                bottom: '5%',
                right: '10%',
                // containLabel: true
            },
            legend: {
                show: false
            },
            tooltip: {
                show: false,
                trigger: 'item',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#234f65'
                    }
                },
                position: 'top',
                formatter: function (params) {
                    return xData[params.value[0]] + ' 中 ' + yData[params.value[1]] + ":" + (parseInt(params.value[2] * divisor / bodyScale));
                }
            },
            xAxis: {
                data: xData,
                nameGap: 10 * bodyScale,
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false,
                    alignWithLabel: true,
                    lineStyle: {
                        color: '#66ccff'
                    },
                },
                axisLabel: {
                    interval: 1,
                    textStyle: {
                        fontSize: 10 * bodyScale,

                    },
                    // rotate:30,
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
            },
            yAxis: {
                data: yData,
                nameGap: 10 * bodyScale,
                axisLine: { //坐标轴
                    show: false
                },
                axisTick: {  //刻度值
                    show: false,
                },
            },
            series: [{
                data: data
            }]
        });
        //END

    })
}
//左-中-上 #43 #44 同心圆 一次合格率 统计当前以及同比 模块 整机问题闭环率tab3  type   0：整机 1：模块
function productLineForTab3Tab3Ajax(myChartIds, type) {
    $.post(contextPath + '/lab/productLineForTab3Tab3Ajax', {
        "labTypeCode": labTypeCode,
        "type": type
    }, function (data) {
        var mTitle;
        if (type === 0) {
            mTitle = "整机";
        } else {
            mTitle = "模块";
        }
        var myChart43 = echarts.init(document.getElementById(myChartIds));
        right_echarts.push(myChart43);
        myChart43.setOption(getCenterPie());
        var mData = [];
        var mSeries = [];
        var mRadius = [[70 * bodyScale, 76 * bodyScale], [55 * bodyScale, 61 * bodyScale]];
        $.each(data, function (index, item) {
            if (index > 1) {
                return
            }
            var mName = item.product_name + item.rate + "%";
            mData.push(mName);
            var it = {
                name: '问题闭环率',
                type: 'pie',
                clockWise: false,  //旋转方向
                radius: mRadius[index],
                center: ["55%", "50%"],
                itemStyle: dataStyle,
                data: [
                    {
                        value: parseFloat(item.rate),
                        name: mName
                        // itemStyle: placeHolderStyle
                    },
                    {
                        value: 100 - parseFloat(item.rate),
                        name: mTitle,
                        itemStyle: placeHolderStyle
                    }
                ]
            };
            mSeries.push(it);
        });
        myChart43.setOption({
            color: ['#66ccff', '#06f'],
            legend: {
                top: bodyScale * 15,
                textStyle: {
                    fontSize: bodyScale * 15
                },
                show: true,
                right: "2%",
                data: mData,
                orient: ' vertical'  //布局  纵向布局

            },
            series: mSeries
        });
    });
}
//左-中-下 #41 #42 折线图 一次合格率 数据结果 整机 模'块
function findOrderPassForProAjax(mychartIds, desName) {
    $.post(contextPath + '/lab/findOrderPassForProAjax', {
        "labTypeCode": labTypeCode,
        "desName": desName,
        "name": "合格率"
    }, function (data) {
        var myChart = echarts.init(document.getElementById(mychartIds));
        right_echarts.push(myChart);
        myChart.setOption(getAreaEcharts());
        myChart.setOption({
            legend: {
                show: false,
                data: [desName],
                itemWidth: 6 * bodyScale, //图例标记的图形宽度
                itemHeight: 6 * bodyScale //图例标记的图形高度
            },
            grid: {
                x: "5%",
                x2: "16%",
                y: "14%",

            },
            xAxis: [
                {
                    name: '产线',
                    nameGap: 10 * bodyScale,
                    data: tab3PassLengend(data),
                    axisLabel: {
                        interval: 0,
                        textStyle: {
                            fontSize: 10 * bodyScale,

                        },
                        // rotate:30,
                    },
                }
            ],
            yAxis: [
                {
                    name: '合格率/%',
                    max: 100
                }
            ],
            series: [
                {
                    name: desName,
                    type: 'line',
                    // areaStyle: {normal: {}},
                    data: tab3RateData(data),
                    itemStyle: {
                        normal: {
                            color: "#66ccff"
                        }
                    },
                    symbolSize: 2 * bodyScale,
                    lineStyle: {
                        normal: {
                            width: 1 * bodyScale
                        }
                    },

                }
            ]

        });
    })
}
//右-左-上 #6 折线图 订单及时率
function findOrderYearRateForTab3() {
    $.post(contextPath + '/lab/findOrderYearRateForTab1Ajax', {
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var myChart6 = echarts.init(document.getElementById("myChart6"));
        right_echarts.push(myChart6);
        myChart6.setOption(getLineEcharts());
        myChart6.setOption({
            legend: {
                show: false,
                data: ['及时率'],
                itemWidth: 5 * bodyScale,  //图例标记的图形宽度
                itemHeight: 3 * bodyScale, //图例标记的图形高度
            },
            grid: {
                right: '15%',
                bottom: '15%',
                left: '12%',
                top: '20%'
            },

            yAxis: {
                name: '及时率/%',
                nameGap: nameGap,
                nameTextStyle: nameTextStyle,
                axisLabel: axisLabel,
                max: 100,
                min: 0,
                scale: true
            },
            xAxis: [
                {
                    name: "时间",
                    nameGap: nameGap,
                    nameTextStyle: nameTextStyle,
                    axisLabel: axisLabel,
                    data: statistictab1LengendTime(data)
                }
            ],
            series: [{
                name: '及时率',
                nameGap: 15 * bodyScale,
                type: 'line',
                lineStyle: {
                    normal: {
                        width: 1 * bodyScale
                    }
                },
                symbolSize: 2 * bodyScale,
                data: tab1OrderRateSeriseData(data)
            }]

        });
    })
}
//右-左-下-左 #45 雷达图 订单及时率同比变化 按照产线统计某年整体订单及时率  数据结果
function findOrderYearRateForProductAjax() {
    $.post(contextPath + '/lab/findOrderYearRateForProductAjax', {"labTypeCode": labTypeCode}, function (data) {
        var myChart45 = echarts.init(document.getElementById("myChart45"));
        right_echarts.push(myChart45);
        if (data === null || data.length === 0) {
            return;
        }
        myChart45.setOption(getRadarEcharts());
        myChart45.setOption({
            legend: {
                show: true,
                orient: ' vertical',  //布局  纵向布局
                left: '4%',
                bottom: '5%',
                itemWidth: 10 * bodyScale,  //图例标记的图形宽度
                itemHeight: 2 * bodyScale, //图例标记的图形高度
                textStyle: {    //图例文字的样式
                    color: '#66ccff',
                    fontSize: 12 * bodyScale
                },
                data: ['2016年', '2017年']
            },

            calculable: true,
            polar: [
                {
                    indicator: tab3IndicatorData(data),
                    center: ['50%', '55%'],
                    radius: '65%',
                    name: {
                        formatter: '{value}',
                        textStyle: {
                            color: '#66ccff',
                            fontSize: 10 * bodyScale,
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            color: 'rgba(102,204,255,0.5)',
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: 'rgba(0,0,0,0)'
                        }
                    },
                    splitArea: {
                        show: false
                    },
                    textStyle: {
                        color: '#66ccff'
                    },
                }

            ],
            series: [
                {
                    type: 'radar',
                    data: [
                        {
                            value: tab3DataData(data, 0),
                            name: '2016年',
                            itemStyle: {
                                normal: {
                                    color: '#66ffcc',
                                    areaStyle: {
                                        color: 'rgba(102,255,204,0.2)'
                                    },
                                }
                            }
                        },
                        {
                            value: tab3DataData(data, 1),
                            name: '2017年',
                            itemStyle: {
                                normal: {
                                    color: '#ff9933',
                                    areaStyle: {
                                        color: 'rgba(255,153,51,0.2)',
                                    },
                                }
                            }
                        }
                    ]
                }
            ]
        });
    })
}
//右-左-下-右 #46 折线组图 订单及时率趋势变化  数据结果
function findOrderMonthRateForProductAjax() {
    $.post(contextPath + '/lab/findOrderMonthRateForProductAjax', {
        "labTypeCode": labTypeCode,
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var myChart46 = echarts.init(document.getElementById("myChart46"));
        right_echarts.push(myChart46);
        myChart46.setOption(getLineEcharts());
        myChart46.setOption({
            color: ['#66ccff', '#00e673', '#4397f7', '#ff9933', '#66ffcc', '#ffff99', '#ff6666'],
            legend: {
                show: true,
                data: tab3Lengend(data),
                itemWidth: 6 * bodyScale,  //图例标记的图形宽度
                itemHeight: 6 * bodyScale, //图例标记的图形高度
            },
            grid: {
                x: "15%",
                x2: "10%",
                y: '15%',
                y2: "10%"
            },
            xAxis: [
                {
                    name: '月份',
                    data: tab3OrderRateLengend(data[0])
                }
            ],
            yAxis: [
                {
                    name: '及时率/%',
                    max: 100
                }
            ],
            series: getTab3Serise(data)

        });
    })
}
//右-右-上 #7 折线图 用户满意度
function satisfactionStatisForMonthForTab3Ajax() {
    $.post(contextPath + '/lab/satisfactionStatisForMonthForTab3Ajax', {}, function (data) {
        var myChart7 = echarts.init(document.getElementById("myChart7"));
        right_echarts.push(myChart7);
        myChart7.setOption(getLineEcharts());
        myChart7.setOption({
            legend: {
                show: false,
                data: ['整机', '模块'],
                itemWidth: 5 * bodyScale,  //图例标记的图形宽度
                itemHeight: 3 * bodyScale, //图例标记的图形高度
            },
            grid: {
                right: '20%',
                bottom: '20%',
                left: '12%',
                top: '22%'
            },
            yAxis: {
                name: '满意度/%',
                max: 100,
                min: 60,
                scale: true,
                nameGap: nameGap,
                nameTextStyle: nameTextStyle,
                axisLabel: axisLabel,

            },
            xAxis: [
                {
                    name: "时间",
                    nameGap: nameGap,
                    nameTextStyle: nameTextStyle,
                    axisLabel: axisLabel,
                    data: statistictab1LengendTime(data)
                }
            ],
            series: [
                {
                    name: '满意度',
                    type: 'line',
                    stack: '总量',
                    lineStyle: {
                        normal: {
                            width: 1 * bodyScale
                        }
                    },
                    symbolSize: 2 * bodyScale,
                    data: tab1OrderRateSeriseData(data)
                }
            ]

        });
    })
}
//右-右-下-左 #47 竖条组图 用户满意度同比变化 某一年分
function satisfactionStatisForYearTab3Ajax2016() {
    $.post(contextPath + '/lab/satisfactionStatisForYearTab3Ajax', {
        "labTypeCode": labTypeCode,
        "year": "2016年",
        "year2": "2017年"
    }, function (data) {
        // console.log("-------------data:", data[0]);
        var mData = [];
        $.each(data[0], function (index, item) {
            mData.push(item.product_name);
        });
        mData.reverse();
        var myChart47 = echarts.init(document.getElementById("myChart47"));
        right_echarts.push(myChart47);
        // myChart47.setOption(getBarEcharts());
        myChart47.setOption(getLineAndBar());
        myChart47.setOption({
            legend: {
                show: true,
                textStyle: {
                    fontSize: 12 * bodyScale
                },
                itemWidth: 8 * bodyScale,
                itemHeight: 8 * bodyScale,
                data: ['2016年', '2017年']
            },
            xAxis: [
                {
                    show: true,
                    data: mData,
                    axisLine: {
                        show: true
                    },
                    //为了让x轴上的文字能全部显示
                    axisLabel: {
                        interval: 0,
                        textStyle: {
                            // fontSize: 12 * bodyScale
                        }
                    }
                }
            ],
            yAxis: [
                {
                    name: "满意度/%",
                    splitLine: {  //刻度线
                        show: true
                    },
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: "#66ccff"
                        }
                    },
                    axisTick: {
                        show: false

                    },
                    /*
                     axisLabel: {
                     show: true,
                     // rotate: 30,
                     textStyle: {
                     color: '#66ccff',
                     fontSize: 9 * bodyScale
                     }
                     },
                     */
                    scale: true,
                    min: 90
                }
            ],
            grid: {
                x: '10%',
                x2: '12%',
                y: '20%',
                y2: "15%"
            },
            series: [
                {
                    name: '2016年',
                    type: "bar",
                    data: tab3RateData(data[0]),
                    barWidth: 7 * bodyScale
                }, {
                    name: '2017年',
                    type: "bar",
                    data: tab3RateData(data[1]),
                    barWidth: 7 * bodyScale
                }
            ]
        });
    })
}
//右-右-下-右 #49 折线组图 用户满意度趋势变化 到月数据统计
function productLineAndMonthForTab3Ajax() {
    $.post(contextPath + '/lab/productLineAndMonthForTab3Ajax', {"labTypeCode": labTypeCode}, function (data) {
        var mData = [];
        var mSeries = [];
        $.each(data, function (index, item) {
            mData.push(item[0].product_name);
            var it = {
                name: item[0].product_name,
                type: 'line',
                lineStyle: {
                    normal: {
                        width: 1 * bodyScale
                    }
                },
                symbolSize: 2 * bodyScale,
                data: tab3RateData(item),

            };
            mSeries.push(it);
        });
        var myChart49 = echarts.init(document.getElementById("myChart49"));
        right_echarts.push(myChart49);
        myChart49.setOption(getLineEcharts());
        myChart49.setOption({
            color: ['#66ccff', '#00e673', '#4397f7', '#ff9933', '#66ffcc', '#ffff99', '#ff6666'],
            legend: {
                show: true,
                data: mData,
                itemWidth: 6 * bodyScale,  //图例标记的图形宽度
                itemHeight: 6 * bodyScale //图例标记的图形高度
            },
            grid: {
                x: "15%",
                x2: "15%",
                y: '23%',
                y2: "10%"
            },
            xAxis: [
                {
                    name: '月份',
                    data: tab3OrderRateLengend(data[0])
                }
            ],
            yAxis: [
                {
                    name: '满意度/%',
                    scale: true,
                    min: 60,
                    max: 100,
                    interval: 4,
                }
            ],
            series: mSeries

        });
    })
}

//data for 右-左-上 #6 折线图 订单及时率
function statistictab1LengendTime(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        var name = item.name;
        name = name.substr(2, 2) + "/" + name.substr(4, name.length);
        legnend.push(name);
    });
    return legnend;
}
//data for 右-左-上 #6 折线图 订单及时率
function tab1OrderRateSeriseData(data) {
    var mData = [];
    $.each(data, function (index, item) {
        mData.push(item.rate);
    });
    return mData;
}
//data for 左-左-下 #39 散点图 订单类别
function dealNumberTab3(num) {
    var max = 20;
    if (parseInt(num) > max) {
        var x = parseInt(parseInt(num) / (parseInt(num) - max));
        if (max + x > 30) {
            return 30 * bodyScale;
        }
        return (max + x) * bodyScale
    } else {
        return parseInt(num) * bodyScale;
    }
}
//data for 右-左-上 #6 折线图 订单及时率
function tab3IndicatorData(da) {
    var indicatorData = [];
    if (da != null && da.length > 0) {
        var data = da[0];
        var data2 = da[1];
        for (var i = 0; i < data.length; i++) {
            var obj = new Object();
            obj.max = 100;
            obj.text = data[i].name;
            indicatorData.push(obj);
        }
    }
    return indicatorData;
}

function tab3DataData(data, index) {
    var indicatorData = [];
    if (data != null && data.length > 0) {
        for (var i = 0; i < data[index].length; i++) {
            indicatorData.push(data[index][i].rate);
        }
    }
    return indicatorData;
}
function tab3RateData(data) {
    var indicatorDataTab3 = [];
    if (data !== null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            var num = data[i].rate;
            indicatorDataTab3.push(num);
        }
    }
    return indicatorDataTab3;
}
function tab3Lengend(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        legnend.push(item[0].product_line_name);
    });
    return legnend;
}
function tab3PassLengend(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        legnend.push(item.product_line_name);
    });
    return legnend;
}
function getTab3Serise(data) {
    var series = [];
    if (data != null && data.length > 0) {
        $.each(data, function (index, item) {
            var it = {
                name: item[0].product_line_name,
                type: 'line',
                /* stack: '总量',*/
                lineStyle: {
                    normal: {
                        width: 1 * bodyScale
                    }
                },
                symbolSize: 2,
                data: tab3RateData(item),

            };
            series.push(it);
        })
    }
    return series;
}
function tab3OrderRateLengend(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        var name = item.name;
        name = name.substr(2, 2) + "/" + name.substr(4, name.length);
        legnend.push(name);
    });
    return legnend;
}


//统计当前以及同比 模块 整机问题闭环率 头部信息 tab3
/*
 function questionForMkZjTab3Ajax(){
 $.post(contextPath+'/lab/questionForMkZjTab1Ajax',{"labTypeCode":labTypeCode},function(data){
 var htmls_zj=(data[1].zj==null?0:data[1].zj)+'% <span>'+dealImageForTab3(data[1].zj)+'</span><span class="up_num">'+(parseFloat(data[1].zj==null?0:data[1].zj)-parseFloat(data[0].zj==null?0:data[0].zj)).toFixed(1)+'%</span>';
 var htmls_mk=(data[1].mk==null?0:data[1].mk)+'% <span>'+dealImageForTab3(data[1].mk)+'</span><span class="up_num">'+(parseFloat(data[1].mk==null?0:data[1].mk)-parseFloat(data[0].mk==null?0:data[0].mk)).toFixed(1)+'%</span>';
 $("#tab3_question_closed_zj").html(htmls_zj);
 $("#tab3_question_closed_mk").html(htmls_mk);
 });
 }
 */
//判断图片
/*
 function dealImageForTab3(num){
 var rise_pic=contextPath+"/static/img/sheshiState/rise.png";//上升图片
 var reduce_pic=contextPath+"/static/img/sheshiState/down.png";//降低图片
 var no_change=contextPath+"/static/img/sheshiState/cp.png";//没有变化
 //判断上升或者下降
 var img="";
 if(parseFloat(num)>0){
 //上升
 img+=' <img src="'+rise_pic+'" alt=""></span>'
 }else if(parseFloat(num)<0){
 //下降
 img+=' <img src="'+reduce_pic+'" alt=""></span>'
 }else{
 //没有变化
 img+=' <img src="'+no_change+'" alt=""></span>'
 }
 return img;
 }
 */
//同期 环比满意度占比统计
/*
 function satisfactionChangeForTab3Ajax(){
 $.post(contextPath+'/lab/satisfactionChangeForTab1Ajax',{"labTypeCode":labTypeCode},function(data){
 var htmls_hb=(data.hb==null?0:data.hb)+'% <span>'+dealImageForTab3(data.hb)+'</span><span class="up_num">2.2%</span>';
 var htmls_tq=(data.tq==null?0:data.tq)+'% <span>'+dealImageForTab3(data.tq)+'</span><span class="up_num">'+(data.change_num==null?0:data.change_num)+'%</span>';
 $("#tab3_user_my_hb").html(htmls_hb);
 $("#tab3_user_my_tq").html(htmls_tq);
 })
 }
 */
//某一年分 到产线满意度统计
/*
 function satisfactionStatisForYearTab3Ajax2017(){
 $.post(contextPath+'/lab/satisfactionStatisForYearTab3Ajax',{"labTypeCode":labTypeCode,"year":"2017年"},function(data){
 var myChart48 = echarts.init(document.getElementById("myChart48"));
 right_echarts.push(myChart48);
 myChart48.setOption(getBarEcharts());
 myChart48.setOption({
 yAxis: [
 {
 show: false,
 type: 'category',
 splitLine: {  //刻度线
 show: false
 },
 axisTick: {
 show: false,

 },

 }
 ],
 xAxis: [
 {
 show: false,
 type: 'value',
 boundaryGap: false,
 }
 ],
 grid: {
 x: '28%',
 x2: "23%",
 y: '5%',
 y2: "0%"
 },
 series: [
 {
 type: "bar",
 data: tab3RateData(data),
 barWidth: 7 * bodyScale,
 label: {
 normal: {
 show: true,
 position: 'right',
 // formatter: "{a}%",
 textStyle: {
 fontSize: 10 * bodyScale,
 color: "#ff9933"
 },
 formatter: '{c}%'
 }
 },
 itemStyle: {
 normal: {
 color: "#00e673",
 }
 }
 }
 ]
 });


 })
 }
 */
//整机 模块 订单类别占全部订单占比统计
/*
 function findOrderTypePercentTab3Ajax(){
 $.post(contextPath+'/lab/findOrderTypePercentTab3Ajax',{"labTypeCode":labTypeCode},function(data){
 /!*var zj='<span>'+(data.zj_rate==undefined?0:data.zj_rate)+'%</span><span>'+dealImageForTab3(data.zj_rate)+'</span><span class="up_num">'+(data.zj_rise==undefined?0:data.zj_rise)+'%</span>';
 var mk='<span>'+(data.mk_rate==undefined?0:data.mk_rate)+'%</span><span>'+dealImageForTab3(data.mk_rate)+'</span><span class="up_num">'+(data.mk_rise==undefined?0:data.mk_rise)+'%</span>';
 $("#tab3_zj_order_type").html(zj);
 $("#tab3_mk_order_type").html(mk);*!/
 var zj='<span>'+(data.zj==undefined?0:data.zj)+'</span>';
 var mk='<span>'+(data.mk==undefined?0:data.mk)+'</span>';
 $("#tab3_zj_order_type").html(zj);
 $("#tab3_mk_order_type").html(mk);
 })
 }
 */
//一次合格率  整体统计-整机 模块
/*
 function findOrderPassForAllAjax(){
 $.post(contextPath+'/lab/findOrderPassForAllAjax',{"labTypeCode":labTypeCode},function(data){
 var zj='<span>'+(data[0].rate==undefined?0:data[0].rate)+'%</span><span>'+dealImageForTab3(data.zj_rate)+'</span><span class="up_num">2.5%</span>';
 var mk='<span>'+(data[1].rate==undefined?0:data[1].rate)+'%</span><span>'+dealImageForTab3(data.mk_rate)+'</span><span class="up_num">3.3%</span>';
 $("#once_pass_rate_zj").html(zj);
 $("#once_pass_rate_mk").html(mk);


 })
 }
 */
//获取某一年订单整体及时率
/*
 function orderYearRateAjax(){
 $.post(contextPath+'/lab/orderYearRateAjax',{"labTypeCode":labTypeCode},function(data){
 var h_2016='<span>'+(data[0].rate==undefined?0:data[0].rate)+'%</span><span>'+dealImageForTab3(data[0].rate)+'</span><span class="up_num">2.3%</span>';
 var h_2017='<span>'+(data[1].rate==undefined?0:data[1].rate)+'%</span><span>'+dealImageForTab3(data[1].rate)+'</span><span class="up_num">'+(parseFloat(data[1].rate==null?0:data[1].rate)-parseFloat(data[0].rate==null?0:data[0].rate)).toFixed(1)+'%</span>';
 $("#order_year_rate_2016").html(h_2016);
 $("#order_year_rate_2017").html(h_2017);
 })
 }
 */


[[{
    "rate": " 93.0",
    "num": "93.048897484",
    "name": "2016年",
    "lab_name": null,
    "id": "1",
    "product_code": "21",
    "type": "0",
    "product_name": "冰冷"
}, {
    "rate": " 93.7",
    "num": "93.65343159",
    "name": "2016年",
    "lab_name": null,
    "id": "2",
    "product_code": "22",
    "type": "0",
    "product_name": "洗涤"
}, {
    "rate": " 94.2",
    "num": "94.202826099",
    "name": "2016年",
    "lab_name": null,
    "id": "3",
    "product_code": "23",
    "type": "0",
    "product_name": "家空"
}, {
    "rate": " 94.2",
    "num": "94.169305717",
    "name": "2016年",
    "lab_name": null,
    "id": "4",
    "product_code": "24",
    "type": "0",
    "product_name": "商空"
}, {
    "rate": " 93.0",
    "num": "93.00049209",
    "name": "2016年",
    "lab_name": null,
    "id": "5",
    "product_code": "25",
    "type": "0",
    "product_name": "热水器"
}, {
    "rate": " 93.7",
    "num": "93.681991211",
    "name": "2016年",
    "lab_name": null,
    "id": "6",
    "product_code": "26",
    "type": "0",
    "product_name": "厨电"
}, {
    "rate": " 93.0",
    "num": "93.00185074",
    "name": "2016年",
    "lab_name": null,
    "id": "7",
    "product_code": "27",
    "type": "0",
    "product_name": "其它"
}], [{
    "rate": " 97.2",
    "num": "97.151354163",
    "name": "2017年",
    "lab_name": null,
    "id": "8",
    "product_code": "21",
    "type": "0",
    "product_name": "冰冷"
}, {
    "rate": " 95.9",
    "num": "95.938389117",
    "name": "2017年",
    "lab_name": null,
    "id": "9",
    "product_code": "22",
    "type": "0",
    "product_name": "洗涤"
}, {
    "rate": " 97.2",
    "num": "97.217527704",
    "name": "2017年",
    "lab_name": null,
    "id": "10",
    "product_code": "23",
    "type": "0",
    "product_name": "家空"
}, {
    "rate": " 98.4",
    "num": "98.366822847",
    "name": "2017年",
    "lab_name": null,
    "id": "11",
    "product_code": "24",
    "type": "0",
    "product_name": "商空"
}, {
    "rate": " 98.6",
    "num": "98.647045786",
    "name": "2017年",
    "lab_name": null,
    "id": "12",
    "product_code": "25",
    "type": "0",
    "product_name": "热水器"
}, {
    "rate": " 98.0",
    "num": "98.029862804",
    "name": "2017年",
    "lab_name": null,
    "id": "13",
    "product_code": "26",
    "type": "0",
    "product_name": "厨电"
}, {
    "rate": " 96.6",
    "num": "96.612891312",
    "name": "2017年",
    "lab_name": null,
    "id": "14",
    "product_code": "27",
    "type": "0",
    "product_name": "其它"
}]]


    [{
    "rate": " 93.0",
    "num": "93.048897484",
    "name": "2016年",
    "lab_name": null,
    "id": "1",
    "product_code": "21",
    "type": "0",
    "product_name": "冰冷"
}, {
    "rate": " 93.7",
    "num": "93.65343159",
    "name": "2016年",
    "lab_name": null,
    "id": "2",
    "product_code": "22",
    "type": "0",
    "product_name": "洗涤"
}, {
    "rate": " 94.2",
    "num": "94.202826099",
    "name": "2016年",
    "lab_name": null,
    "id": "3",
    "product_code": "23",
    "type": "0",
    "product_name": "家空"
}, {
    "rate": " 94.2",
    "num": "94.169305717",
    "name": "2016年",
    "lab_name": null,
    "id": "4",
    "product_code": "24",
    "type": "0",
    "product_name": "商空"
}, {
    "rate": " 93.0",
    "num": "93.00049209",
    "name": "2016年",
    "lab_name": null,
    "id": "5",
    "product_code": "25",
    "type": "0",
    "product_name": "热水器"
}, {
    "rate": " 93.7",
    "num": "93.681991211",
    "name": "2016年",
    "lab_name": null,
    "id": "6",
    "product_code": "26",
    "type": "0",
    "product_name": "厨电"
}, {
    "rate": " 93.0",
    "num": "93.00185074",
    "name": "2016年",
    "lab_name": null,
    "id": "7",
    "product_code": "27",
    "type": "0",
    "product_name": "其它"
}]






