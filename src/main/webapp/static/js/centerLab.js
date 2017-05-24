// 数据分析
var bodyScale = 1;//原始比例1
//左
var chartone = echarts.init(document
    .getElementById("echart_one"));
//右
var charttwo = echarts.init(document
    .getElementById("echart_two"));


//左
var chartthree = echarts.init(document
    .getElementById("echart_three"));

//右
var chartfour = echarts.init(document
    .getElementById("echart_four"));


//左
var chartfive = echarts.init(document
    .getElementById("echart_five"));
//右
var chartsix = echarts.init(document
    .getElementById("echart_six"));
//$("#labMain_cbro_content").load("labAnalysis_small.html");
// document.getElementById("labMain_cbro_content").innerHTML = '<object type="text/html" data="labAnalysis_small.html" width="100%" height="100%"></object>';

function initone(mValue) {
    var labelFromatter = {
        normal: {
            label: {
                formatter: function (params) {
                    return 100 - params.value + '%'
                },
            },
            labelLine: {
                show: false
            }
        },
    };
    //5
    var labelTop = {
        normal: {
            color: '#66ccff',
            label: {
                show: true,
                position: 'bottom',
                formatter: '{b}',
                textStyle: {
                    baseline: 'center',
                    color: '#66ccff',
                    fontSize: 8
                }
            },
            labelLine: {
                show: false
            }
        }
    };
    //95
    var labelBottom = {
        normal: {
            color: '#234f65',
            label: {
                show: true,
                position: 'center'
            },
            labelLine: {
                show: false
            }
        },
        emphasis: {},
    };
    var radius = ['45%', '65%'];
    var option = {
        textStyle: {
            color: '#ff9933',
            fontSize: bodyScale * 12,
        },
        grid: {
            x: "0",
            y: '0',
        },
        series: [
            {
                type: 'pie',
                radius: radius,
                //x: '40%', // for funnel
                itemStyle: labelFromatter,
                data: [
                    {name: '', value: 100 - parseFloat(mValue), itemStyle: labelBottom},
                    {name: '', value: mValue, itemStyle: labelTop}
                ]
            }

        ]

    };
    return option;

}
//近12个月用户满意度趋势图
function inittwo() {
    $.post(contextPath + '/lab/satisfactionStatisForMonthForTab1Ajax', {"labTypeCode": "中海博睿"}, function (data) {
        var resu = dealSatisfactionCenterLab(data);
        $("#satisfaction_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#satisfaction_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#satisfaction_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartfive.setOption(initone(resu[0]));
        var bar_chip = contextPath + '/static/img/bar_chip.png';
        var labelSetting = {
            normal: {
                show: false,
                position: 'outside',
                offset: [10, 0],
                textStyle: {
                    fontSize: bodyScale * 8
                }
            }
        };
        chartsix.setOption(getBarEcharts());
        chartsix.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            yAxis: [
                {
                    name: "满意度/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },

                    type: 'value',
                    max: 100,
                    scale: true,
                },
            ],
            xAxis: [
                {
                    name: "",
                    type: 'category',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            grid: {
                x: "15%",
                x2: "10%",
                y: '23%',
                y2: "34%",
            },
            series: [
                {
                    symbolSize: ['50%', '10%'],
                    data: centerLabOrderHgRate(data)
                }
            ]
        });


    });

}
//近12个月一次合格率趋势图
function initThree() {
    $.post(contextPath + '/lab/orderRateForCenterLabAjax', {
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var resu = dealCenterLab(data);
        $("#hg_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#hg_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#hg_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartone.setOption(initone(resu[0]));
        charttwo.setOption(getBarEcharts());
        var bar_chip = contextPath + '/static/img/bar_chip.png';
        var labelSetting = {
            normal: {
                show: false,
                position: 'outside',
                offset: [10, 0],
                textStyle: {
                    fontSize: bodyScale * 8
                }
            }
        };


        charttwo.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            yAxis: [
                {
                    name: "合格率/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },

                    type: 'value',
                    max: 100,
                    scale: true,
                },
            ],
            xAxis: [
                {
                    name: "",
                    type: 'category',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            grid: {
                // x: "10%",
//		            x2: "25%",
//		            y: '22%',
//		            y2: "26%",

                x: "15%",
                x2: "10%",
                y: '23%',
                y2: "34%",
            },
            series: [
                {
                    symbolSize: ['50%', '10%'],
                    data: centerLabOrderHgRate(data)
                }
            ]
        });

    });


}
//按照产线统计某年各月份详细订单及时率  数据结果 订单及时率 折线图
function initfour() {
    $.post(contextPath + '/lab/findOrderYearRateForTab1Ajax', {
        "labTypeCode": "中心实验室",
        "startDate": "201606",
        "endDate": "201705"
    }, function (data) {
        var resu = dealCenterLab(data);
        $("#order_rate_center_lab_pj").html("平均:" + resu[0] + "%");
        $("#order_rate_center_lab_height").html("最高:" + resu[1].rate + "%(" + resu[1].month + "月)");
        $("#order_rate_center_lab_low").html("最低:" + resu[2].rate + "%(" + resu[2].month + "月)");
        chartthree.setOption(initone(resu[0]));
        chartfour.setOption(getAreaEcharts());
        chartfour.setOption({
            textStyle: {
                fontSize: bodyScale * 8
            },
            legend: {
                show: false,
                data: [''],
                textStyle: {
                    fontSize: bodyScale * 8
                },
                itemWidth: 6, //图例标记的图形宽度
                itemHeight: 6 //图例标记的图形高度
            },
            grid: {

                x: "11%",
                x2: "10%",
                y: '23%',
                y2: "20%"
            },
            xAxis: [
                {
                    name: '',
                    data: centerLabOrderRateLengend(data)
                }
            ],
            yAxis: [
                {
                    name: "及时率/%",

                    nameTextStyle: {
                        fontSize: bodyScale * 10,

                    },
                }
            ],
            series: [
                {
                    name: '',
                    type: 'line',
                    stack: '总量',
                    // areaStyle: {normal: {}},
                    data: centerLabRateData(data),
                    itemStyle: {
                        normal: {
                            color: "#ff6666"
                        }
                    }

                }
            ]

        });
    });

}

//曲线
var colorData = ['#66ccff', '#ff9933', '#ff6666', '#00cc66', '#ffff99', '#cc99ff', '#99ccff', '#ff99cc', '#ff9900', '#ffff00', '#ffff00', '#66ffff',
    '#3366ff', '#660099', '#ff0099', '#cc6600', '#ccff00', '99ff99', '#00cccc', '#006699', '#9999ff'];//图例颜色 需手工扩充
var myChart1;
var myChart2;
var xData;//x轴坐标数据--对应时间
var legendData = [];//需要把全部图例放入里面 保证名称不同
var showLegendData = [];//需要展示图例 自定义
var seriesTopData = [];
var seriesBottomData = [];
var topParam = ['Hz', '℃', 'ml'];//上方y参数单位
var bottomParam = ['V', 'W', 'A', 'kw.h'];
var dataBase = {
    legend: ['频率(Hz)', 'M1(℃)', 'M2(℃)', 'M3(℃)', '降雨量(ml)', '电流(V)', '电压(A)', '功率(W)','电压y(kw.h)'],
    list: [
        {
            name: '频率(Hz)',
            data: [{name: '1月', value: '5'}, {name: '2月', value: '6'}, {name: '3月', value: '7'}, {
                name: '4月',
                value: '4'
            }, {name: '5月', value: '2'}, {name: '6月', value: '25'}, {name: '7月', value: '15'}, {
                name: '8月',
                value: '22'
            }, {name: '9月', value: '21'}, {name: '10月', value: '7'}, {name: '11月', value: '3'}, {
                name: '12月',
                value: '15'
            }]
        }, {
            name: 'M1(℃)',
            data: [{name: '1月', value: '44'}, {name: '2月', value: '52'}, {name: '3月', value: '7'}, {
                name: '4月',
                value: '1'
            }, {name: '5月', value: '24'}, {name: '6月', value: '33'}, {name: '7月', value: '15'}, {
                name: '8月',
                value: '22'
            }, {name: '9月', value: '5'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        }, {
            name: 'M3(℃)',
            data: [{name: '1月', value: '24'}, {name: '2月', value: '2'}, {name: '3月', value: '7'}, {
                name: '4月',
                value: '11'
            }, {name: '5月', value: '54'}, {name: '6月', value: '33'}, {name: '7月', value: '15'}, {
                name: '8月',
                value: '22'
            }, {name: '9月', value: '5'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        }, {
            name: 'M2(℃)',
            data: [{name: '1月', value: '4'}, {name: '2月', value: '2'}, {name: '3月', value: '17'}, {
                name: '4月',
                value: '14'
            }, {name: '5月', value: '12'}, {name: '6月', value: '15'}, {name: '7月', value: '5'}, {
                name: '8月',
                value: '2'
            }, {name: '9月', value: '11'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        }, {
            name: '降雨量(ml)',
            data: [{name: '1月', value: '14'}, {name: '2月', value: '12'}, {name: '3月', value: '37'}, {
                name: '4月',
                value: '44'
            }, {name: '5月', value: '52'}, {name: '6月', value: '45'}, {name: '7月', value: '51'}, {
                name: '8月',
                value: '52'
            }, {name: '9月', value: '41'}, {name: '10月', value: '24'}, {name: '11月', value: '54'}, {
                name: '12月',
                value: '33'
            }]
        }, {
            name: '电流(V)',
            data: [{name: '1月', value: '15'}, {name: '2月', value: '6'}, {name: '3月', value: '7'}, {
                name: '4月',
                value: '4'
            }, {name: '5月', value: '2'}, {name: '6月', value: '25'}, {name: '7月', value: '15'}, {
                name: '8月',
                value: '22'
            }, {name: '9月', value: '21'}, {name: '10月', value: '7'}, {name: '11月', value: '3'}, {
                name: '12月',
                value: '15'
            }]
        }, {
            name: '电压(A)',
            data: [{name: '1月', value: '4'}, {name: '2月', value: '2'}, {name: '3月', value: '17'}, {
                name: '4月',
                value: '14'
            }, {name: '5月', value: '12'}, {name: '6月', value: '15'}, {name: '7月', value: '5'}, {
                name: '8月',
                value: '2'
            }, {name: '9月', value: '11'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        }, {
            name: '功率(W)',
            data: [{name: '1月', value: '14'}, {name: '2月', value: '112'}, {name: '3月', value: '57'}, {
                name: '4月',
                value: '14'
            }, {name: '5月', value: '12'}, {name: '6月', value: '15'}, {name: '7月', value: '5'}, {
                name: '8月',
                value: '2'
            }, {name: '9月', value: '11'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        },
        {
            name: '电压y(kw.h)',
            data: [{name: '1月', value: '24'}, {name: '2月', value: '2'}, {name: '3月', value: '17'}, {
                name: '4月',
                value: '14'
            }, {name: '5月', value: '12'}, {name: '6月', value: '15'}, {name: '7月', value: '5'}, {
                name: '8月',
                value: '2'
            }, {name: '9月', value: '11'}, {name: '10月', value: '17'}, {name: '11月', value: '13'}, {
                name: '12月',
                value: '5'
            }]
        }
    ]
};


$(document).ready(function () {
    legendData = dealBracket(dataBase.legend);
    showLegendData = legendData;//默认全选
    //console.log(showLegendData)
    createLegendHtmls();
    createEcharts();
});

//生成echarts图形
function createEcharts() {
    dealSeriesData();
    myChart1 = echarts.init(document.getElementById('main1'));
    myChart2 = echarts.init(document.getElementById('main2'));
    getCharts1();
    getCharts2();

    /*
     setInterval(function () {

     seriesBottomData[0].data.shift();
     seriesBottomData[0].data.push(parseInt(Math.random() * 30)+"");
     var opt2 = myChart2.getOption();
     myChart2.clear();
     opt2.series[0].data = seriesBottomData;
     myChart2.setOption({
     series: [{
     data: seriesBottomData
     }]
     });
     //            myChart2.resize();
     console.log(opt2.series[0].data[0].data)
     },3000)
     */

}

//生成图例控制
function createLegendHtmls() {
    var htmls = '';
    for (var x = 0; x < legendData.length; x++) {
        htmls += '<input style="margin-right: 2%;margin-top: 0;float: left" type="checkbox" name="legendcheckbox" onclick="resetOptions(this)" value="' + legendData[x] + '" checked><span style="background-color:' + colorData[x] + ';display: inline-block;width:1em;height: 1em;margin-right: 2%;float: left"></span><li  style="color:#66ccff;display: inline-block;float:left" name="' + legendData[x] + '">' + legendData[x] + '</li><span style="float: right">1111</span><br>'
    }
    $("#legend_ul").html(htmls);
}

//处理线series
function dealSeriesData() {
    seriesTopData = [];
    seriesBottomData = [];
    for (var x = 0; x < dataBase.legend.length; x++) {
        var currentName = dataBase.legend[x];
        var data = [];
        for (var i = 0; i < dataBase.list.length; i++) {
            if (dataBase.list[i].name == currentName) {
                data = dataBase.list[i].data;
            }
        }
        var topIndex = isHasElementOne(topParam, dealUnit(currentName));
        var bottomIndex = isHasElementOne(bottomParam, dealUnit(currentName));
        if (topIndex > -1 && isHasElementOne(showLegendData, dealBracketForObj(currentName)) > -1) {
            //展示在上半部分
            seriesTopData.push(joinSerise(data, currentName, topIndex, x));
        } else if (bottomIndex > -1 && isHasElementOne(showLegendData, dealBracketForObj(currentName)) > -1) {
            //展示在下半部分
            seriesBottomData.push(joinSerise(data, currentName, bottomIndex, x));
        }
        //console.log(currentName+"==="+topIndex+"==="+bottomIndex)
    }
}

function joinSerise(data, name, index, colorIndex) {
    var dataArr = [];
    xData = [];
    for (var x = 0; x < data.length; x++) {
        dataArr.push(data[x].value);
        xData.push(data[x].name);
    }
    ;
    var item = {
        name: dealBracketForObj(name),
        type: 'line',
        data: dataArr,
        itemStyle: {
            normal: {
                color: colorData[colorIndex]
            }
        },
        show: false,
    };
    if (index > 0) {
        item.yAxisIndex = index;
    }
    return item;
}

function getCharts1() {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            show: false,
            data: legendData
        },
        grid: {
            x: '13%',
            x2: '10%',
            y2: '-2%'                //下移负数 使两个图重叠
        },
        xAxis: [
            {
                type: 'category',
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    // rotate: 30,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisTick: {
                    show: false,
                    alignWithLabel: true,
                    lineStyle: {
                        color: '#66ccff'
                    }
                },
                data: xData
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: 'Hz',
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'left',
                offset: 40,
                axisLine: { //坐标轴
                    show: false
                },
                axisLabel: {   //坐标值
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: '℃',
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'left',
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: 'ml',
                nameTextStyle: {
                    color: '#66ccff'
                },
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    show: true,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,

            }
        ],
        series: seriesTopData
    };
    myChart1.clear();
    myChart1.setOption(option);
    echarts.connect([myChart1, myChart2]);
}
function getCharts2() {

    option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
        },
        legend: {
            show: false,
            data: legendData
        },
        grid: {
            x: '13%',
            x2: '17%',
            y: '3%',
            y2: '14%'
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                splitLine: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                axisLabel: {
                    show: true,
                    // rotate: 30,
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisTick: {
                    show: true,
                    alignWithLabel: true,
                    lineStyle: {
                        color: '#66ccff'
                    }
                },
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: 'V',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                /*      min: 0,
                 max: 100, */
                position: 'left',
                offset: 40 * bodyScale,
                axisLabel: {
                    formatter: function (params, index) {
                        //console.log(params+"--"+index+"--"+typeof(params))
                        /* if(index==7){
                         return ""
                         } */
                        return params;
                    },
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: 'W',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                position: 'left',
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,

            },
            {
                type: 'value',
                name: 'A',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                /*      min: 0,
                 max: 100, */
                position: 'right',
               
                axisLabel: {
                    formatter: function (params, index) {
                        //console.log(params+"--"+index+"--"+typeof(params))
                        /* if(index==7){
                         return ""
                         } */
                        return params;
                    },
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },
                symbolSize: 1,
            },
            {
                type: 'value',
                name: 'kw.h',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                offset: 40,
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize: 12 * bodyScale
                    }
                },
                axisLine: { //坐标轴
                    show: false
                },
                splitLine: {  //刻度线
                    show: true,
                    lineStyle: {
                        color: '#234f65'
                    }
                },
                axisTick: {  //刻度值
                    show: false,
                },
                lineStyle: {
                    normal: {
                        width: 0.5
                    }
                },

                symbolSize:1,
            }
        ],
        series: seriesBottomData
    };
    // myChart2.clear();
    myChart2.setOption(option2);

    echarts.connect([myChart1, myChart2]);

    /*
     function randomData() {
     now = new Date(+now + oneDay);
     value = value + Math.random() * 21 - 10;
     return {
     name: now.toString(),
     value: [
     [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
     Math.round(value)
     ]
     }
     }
     */


    /*
     option22 = {
     title: {
     text: '动态数据 + 时间坐标轴'
     },
     tooltip: {
     trigger: 'axis',
     formatter: function (params) {
     params = params[0];
     var date = new Date(params.name);
     return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
     },
     axisPointer: {
     animation: false
     }
     },
     xAxis: {
     type: 'time',
     splitLine: {
     show: false
     }
     },
     yAxis: {
     type: 'value',
     boundaryGap: [0, '100%'],
     splitLine: {
     show: false
     }
     },
     series: [{
     name: '模拟数据',
     type: 'line',
     showSymbol: false,
     hoverAnimation: false,
     data: data
     }]
     };
     */

    // myChart2.setOption(option22);
    myChart2.setOption({
        series: [{
            animation:false
        },{
            animation:false
        },{
            animation:false
        },]
    });
    setInterval(function () {
for(var i=0; i<seriesBottomData.length;i++){
    seriesBottomData[i].data.shift();
    seriesBottomData[i].data.push(parseInt(Math.random() * 30));
}
        var month = xData.shift();
        xData.push(month)

        myChart2.setOption({
            xAxis:[
                {data:xData}],
            series: seriesBottomData,
        });
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~seriesBottomData: ", seriesBottomData[0].data)
        console.log("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~xData: ", xData)
    }, 2000);


}
//处理括号
function dealBracket(arr) {
    var result = new Array();
    for (var x = 0; x < arr.length; x++) {
        result.push(dealBracketForObj(arr[x]));
    }
    return result;
}
function dealBracketForObj(obj) {
    if (obj.indexOf("(") > -1) {
        return obj.substr(0, obj.indexOf("("));
    }
    return obj;
}
//判断数组中某个元素下标
function isHasElementOne(arr, value) {
    for (var i = 0, vlen = arr.length; i < vlen; i++) {
        if (arr[i] == value) {
            return i;
        }
    }
    return -1;
}
//传入字符串获取单位
function dealUnit(str) {
    if (str.indexOf("(") > -1 && str.indexOf(")") > -1) {
        var result = str.substr(str.indexOf("(") + 1, str.indexOf(")"));
        result = result.substr(0, result.indexOf(")"));
        return result;
    }
    return "";
}
function resetOptions(obj) {
    showLegendData = checkBoxVales();
    createEcharts();
}
function checkBoxVales() { //jquery获取复选框值
    var chk_value = [];
    $('input[name="legendcheckbox"]:checked').each(function () {
        chk_value.push($(this).val());
    });
    return chk_value;
}


//新增
function centerLabOrderRateLengend(data) {
    var legnend = [];
    $.each(data, function (index, item) {
        var name = item.name;
        name = name.substr(0, 4) + "/" + name.substr(4, name.length);
        legnend.push(name);
    });
    return legnend;
}
//合格率数据
function centerLabOrderHgRate(data) {
    var d = [];
    $.each(data, function (index, item) {
        var it = {
            value: item.rate,
            symbol: bar_chip
        };
        d.push(it)
    })
    return d;
}
function centerLabRateData(data) {
    var indicatorDataTab3 = [];
    for (var i = 0; i < data.length; i++) {
        var num = data[i].rate;
        indicatorDataTab3.push(num);
    }
    return indicatorDataTab3;
}
//获取平均 最高 最低数据
function dealCenterLab(data) {
    var result = [];
    var all_num = 0;
    var js_num = 0;
    var maxData = {month: 0, rate: 0};
    var minData = {month: 0, rate: 100};
    $.each(data, function (index, item) {
        var cAllNum = parseInt(item.all_count);
        var cJsNum = parseInt(item.js_count);
        var cName = item.name;
        var cRate = parseFloat(item.rate);
        if (parseFloat(maxData.rate) < cRate) {
            maxData.rate = cRate;
            maxData.month = cName;
        }
        if (parseFloat(minData.rate) > cRate) {
            minData.rate = cRate;
            minData.month = cName;
        }
        all_num += cAllNum;
        js_num += cJsNum;
    });
    //计算整体平均值
    var allPingjun = parseFloat((parseInt(js_num) / parseInt(all_num)) * 100).toFixed(1);
    result.push(allPingjun);
    result.push(maxData);
    result.push(minData);
    return result;
}
//获取用户满意度平均 最高 最低数据
function dealSatisfactionCenterLab(data) {
    var result = [];
    var all_num = 0;
    var maxData = {month: 0, rate: 0};
    var minData = {month: 0, rate: 100};
    $.each(data, function (index, item) {
        all_num = parseFloat(all_num) + parseFloat(item.rate);
        var cName = item.name;
        var cRate = parseFloat(item.rate);
        if (parseFloat(maxData.rate) < cRate) {
            maxData.rate = cRate;
            maxData.month = cName;
        }
        if (parseFloat(minData.rate) > cRate) {
            minData.rate = cRate;
            minData.month = cName;
        }
    });
    //计算整体平均值
    var allPingjun = parseFloat(all_num / data.length).toFixed(1);
    result.push(allPingjun);
    result.push(maxData);
    result.push(minData);
    return result;
}


//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//            曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？
