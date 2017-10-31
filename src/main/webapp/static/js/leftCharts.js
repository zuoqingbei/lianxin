/**
 * Created by Administrator on 2017/4/23 0023.
 */

var pathSymbols = {
    /*条形图的小格子*/
    bar_chip: '../img/bar_chip.png'
};

//球形地图右上角的环形图表
/*var myChartSphereRT = echarts.init($("#l").find(".sphere-right-top .myChart")[0]);
myChartSphereRT.setOption(getYuanhuan());
var labelTop = {
    normal: {
        color: '#064f66',
        label: {
            show: true,
            position: 'center',
//                模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。
            formatter: function (params) {
                return 100 - ( params.percent)
            },
            textStyle: {
                fontSize:16,
                color: "#f90",
                baseline: 'bottom'
            }
        },
        labelLine: {
            show: false
        }
    }
};
var labelFromatter = {
    normal: {
        label: {
            formatter: function (params) {
                return 100 - ( params.value)  + '%'
            },
            textStyle: {
                baseline: 'bottom'
            }
        }
    }
};
var labelBottom = {
    normal: {
        color: "#6cf",
        label: {
            show: true,
            position: 'center',
            formatter : '{b}',
            textStyle: {
//                    color:"#f90",
                fontSize: "60%",
                baseline: 'top'
            }
        },
        labelLine: {
            show: false
        }
    },
    emphasis: {
        color: '#6cf'
    }
};
var radius = [30, 39];
myChartSphereRT.setOption({
    textStyle: {
        color: '#6cf',
        fontSize: "60%"
    },
    grid: {
//            show:true,
//         x: "25%",
//         x2: "15%",
//         y2: "12%"
    },
    series: [
        {
            type: 'pie',
            center: ['30%', '35%'],
            radius: radius,
            x: '0%', // for funnel
            data: [
                {name: 'other', value: 450, itemStyle: labelTop},
                {name: '研发实验室', value: 150, itemStyle: labelBottom}
            ]
        },
        {
            type: 'pie',
            center: ['70%', '35%'],
            radius: radius,
            x: '20%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name:'other', value:300, itemStyle : labelTop},
                {name: '中心实验室', value: 200, itemStyle: labelBottom}
            ]
        },
        {
            type: 'pie',
            center: ['30%', '70%'],
            radius: radius,
            x: '40%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 450, itemStyle: labelTop},
                {name: '工厂实验室', value: 50, itemStyle: labelBottom}
            ]
        },
        {
            type: 'pie',
            center: ['70%', '70%'],
            radius: radius,
            x: '80%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 400, itemStyle: labelTop},
                {name: '模块实验室', value: 100, itemStyle: labelBottom}
            ]
        }
    ]
});*/

//球形地图左下角的字符云图表，在iframe里面

//平面地图右上角的横条形图表
var myChartFlatRT = echarts.init($("#l").find(".flat-right-top .myChart")[0]);
myChartFlatRT.setOption(getLineEcharts());
myChartFlatRT.setOption({
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        show: false,
        data: ['专业能力']
    },
    textStyle: {
        color: "#6cf",
        fontSize: "60%"
    },
    grid: {
//            show:true,
        x: "30%",
        x2: "20%",
//         y2: "12%"
    },
    xAxis: [
        {
            name: "数量",
            type: 'value',
            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "60%"
                }
            },
            axisLine: {
                lineStyle: {
                    width: 0
                }
            },
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: "#234f65"
                }
            },
            max: 25,
            offset: 5
        }
    ],
    yAxis: [
        {
            name: "试验类别             ",
            type: 'category',
            data: ["运输", "用户体验", "EMC", "用户模拟", "安全", "可靠性", "性能"],
            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "60%"
                }
            },
            nameGap: 10,
            offset: 5,
//                minInterval: .5
        }
    ],
    series: [
        {
            name: '专业能力',
//                type:'bar',
            type: 'pictorialBar',
//                barGap: '10%',
            symbolRepeat: true,
            symbolSize: ['40%', '80%'],
            data: [{
                value: 0,
                symbol: pathSymbols.bar_chip
            }, {
                value: 1,
                symbol: pathSymbols.bar_chip
            }, {
                value: 2,
                symbol: pathSymbols.bar_chip
            }, {
                value: 4,
                symbol: pathSymbols.bar_chip
            }, {
                value: 4,
                symbol: pathSymbols.bar_chip
            }, {
                value: 20,
                symbol: pathSymbols.bar_chip
            }, {
                value: 22,
                symbol: pathSymbols.bar_chip
            }]
//                data: [0, 1, 2, 4, 4, 20, 22]
        }
    ]
});

//平面地图右下角的面积图表
var myChartFlatRB = echarts.init($("#l").find(".flat-right-bottom .myChart")[0]);
myChartFlatRB.setOption(getAreaEcharts());
myChartFlatRB.setOption({
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        show: false,
        data: ['试验目的']
    },
    textStyle: {
        color: "#6cf",
        fontSize: "60%"
    },
    grid: {
//            show:true,
        x: "15%",
//         x2: "10%",
//         y: "48%",
//         y2: "12%"
    },
    xAxis: [
        {
            name: "",
            type: 'category',
            data: ["对比试验", "新品调试", "新品确认", "小批验证", "入厂/出厂", "年度型检", "监督抽测"],
            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "50%"
                }
            },
            axisLine: {
                lineStyle: {
                    width: 0
                }
            },
            splitLine: {  //刻度线
                show: false,
                lineStyle: {
                    color: "#234f65"
                }
            },
            offset: 5

        }
    ],
    yAxis: [
        {
            name: "数量       ",
            type: 'value',

            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "60%"
                }
            },
            nameGap: 10,
            offset: 5,
            max: 500
//                minInterval: .5
        }
    ],
    series: [
        {
            name: '试验目的1',
            type: 'line',
            symbolRepeat: true,
            itemStyle: {
                normal: {
                    color: "66ccff",
                    fontFamily: "'微软雅黑','sans-serif'",
                }
            },
            areaStyle: {
                normal: {
                    color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0, color: 'rgba(0,96,255,1)' // 0% 处的颜色
                        }, {
                            offset: 1, color: 'rgba(0,96,255,.1)' // 100% 处的颜色
                        }],
                        globalCoord: false // 缺省为 false
                    }
                },
            },
            data: [
                450, 220, 250, 320, 360, 380, 150,
            ]
        },
        {
            name: '试验目的2',
            type: 'line',
            symbolRepeat: true,
            symbolSize: ['40%', '80%'],
            itemStyle: {
                normal: {
                    color: "66ccff"
                }
            },
            areaStyle: {
                normal: {
                    color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0, color: 'rgba(0,230,115,1)' // 0% 处的颜色
                        }, {
                            offset: 1, color: 'rgba(0,230,115,.1)' // 100% 处的颜色
                        }],
                        globalCoord: false // 缺省为 false
                    }
                }
            },
            data: [
                 380, 320, 350, 210, 250, 320, 360
            ]
        }
    ]
});

//平面地图左下角的竖条形图表
var myChartFlatLB = echarts.init($("#l").find(".flat-left-bottom .myChart")[0]);
myChartFlatLB.setOption(getLineEcharts());
myChartFlatLB.setOption({
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        show: false,
        data: ['实验室统计']
    },
    textStyle: {
        color: "#6cf",
        fontSize: "60%"
    },
    grid: {
//            show:true,
//         x: "25%",
        x2: "15%",
        y: "15%",
        y2: "14%"
    },
    xAxis: [
        {
            name: "",
            type: 'category',
            data: ["研究实验室", "中心实验室", "工厂实验室", "模块实验室"],
            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "60%"
                }
            },
            axisLine: {
                lineStyle: {
                    width: 0
                }
            },

            offset: 5

        }
    ],
    yAxis: [
        {
            name: "数量       ",
            type: 'value',
            axisLabel: {
                textStyle: {
                    color: "#66ccff",
                    fontSize: "60%"
                }
            },
            nameGap: 10,
            offset: 5,
            max: 300,
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: "#234f65"
                }
            },
        }],
    series: [
        {
            name: '专业能力',
//                type:'bar',
            type: 'pictorialBar',
//                barGap: '10%',
            symbolRepeat: true,
            symbolSize: ['40%', '20%'],
            data: [{
                value: 234,
                symbol: pathSymbols.bar_chip
            }, {
                value: 148,
                symbol: pathSymbols.bar_chip
            }, {
                value: 102,
                symbol: pathSymbols.bar_chip
            }, {
                value: 76,
                symbol: pathSymbols.bar_chip
            }]
        }
    ]
});


