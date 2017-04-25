// 多个方向盘 设施状态
var myCharts = echarts.init(document.getElementById("myChart1"));
option = {
    tooltip: {
        formatter: "{a} <br/>{c}%"
    },
    toolbox: {
        show: false,
        feature: {
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    series: [
        {
            name: '在线率',
            type: 'gauge',
            z: 3,
            min: 0,
            max: 100,
            splitNumber: 5,
            radius: '80%',
            textStyle: {
                fontSize:'60%'
            },
            axisLine: {            // 坐标轴线
                show:false,
                lineStyle: {       // 属性lineStyle控制线条样式
                    width: 7,
                    color: [[0.2, '#66ccff'], [0.8, '#66ccff'], [1, '#66ccff']]
                },

            },
            axisLabel:{
                show:true,
                textStyle:{
                    fontSize:8
                }
            },
            axisTick: {            // 坐标轴小标记
                length: 5,        // 属性length控制线长
                lineStyle: {       // 属性lineStyle控制线条样式
                    color: '#66ccff'
                }
            },
            splitLine: {           // 分隔线
                length: 11,         // 属性length控制线长
                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    color: '#66ccff'
                }
            },
            itemStyle: {
                normal: {
                    color: "#ff9933",
                       // borderColor:"red",
                       // borderWidth:2,
//                        opacity:0.5
                }
            },
            pointer: {
                length: "55%",
                width: 3
            },
            title: {
                offsetCenter: [0, '110%'],       // x, y，单位px
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize: 12,
                    color: '#66ccff',
//                        fontStyle: 'italic'
                },

            },
            detail: {
                offsetCenter: [0, '70%'],
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: '170%',
                    color: "#ff9933"
                },
                formatter: '{value}%'
            },
            data: [{value: 98, name: '在线率'}]
        },
        {
            name: '利用率',
            type: 'gauge',
            center: ['29%', '58%'],    // 默认全局居中
            radius: '70%',
            min: 0,
            max: 100,
            endAngle: 45,
            splitNumber: 5,
            axisLine: {            // 坐标轴线
                lineStyle: {       // 属性lineStyle控制线条样式
                    width: 7,
                    color: [[0.2, '#66ccff'], [0.8, '#66ccff'], [1, '#66ccff']]

                },
            },
            axisLabel:{
                show:true,
                textStyle:{
                    fontSize:8
                }
            },
            axisTick: {            // 坐标轴小标记
                length: 7,        // 属性length控制线长
                lineStyle: {       // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },
            splitLine: {           // 分隔线
                length: 11,         // 属性length控制线长
                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto'
                }
            },
            itemStyle: {
                normal: {
                    color: "#ff9933",
//                        borderColor:"red",
//                        borderWidth:2,
//                        opacity:0.5
                }
            },
            pointer: {
                length: "55%",
                width: 3
            },
            title: {
                offsetCenter: [0, '100%'],       // x, y，单位px
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize: 14,
                    color: '#66ccff',
//                        fontStyle: 'italic'
                },
            },
            detail: {
                offsetCenter: ['15%', '50%'],//数字显示的位置
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: '170%',
                    color: "#ff9933"
                },
                formatter: '{value}%' //数字显示的样式
            },
            data: [{value: 96, name: '利用率'}]
        },
        {
            name: '完好率',
            type: 'gauge',
            center: ['71%', '59%'],    // 默认全局居中
            radius: '70%',
            min: 0,
            max: 100,
            startAngle: 135,
            endAngle: -45,
            splitNumber: 5,
            axisLine: {            // 坐标轴线
                lineStyle: {       // 属性lineStyle控制线条样式
                    width: 7,
                    color: [[0.2, '#66ccff'], [0.8, '#66ccff'], [1, '#66ccff']]

                }
            },
            axisLabel:{
                show:true,
                textStyle:{
                    fontSize:8
                }
            },
            axisTick: {            // 坐标轴小标记
                length: 7,        // 属性length控制线长
                lineStyle: {       // 属性lineStyle控制线条样式
                    color: 'auto'
                }
            },
            splitLine: {           // 分隔线
                length: 11,         // 属性length控制线长
                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    color: 'auto'
                }
            },
            itemStyle: {
                normal: {
                    color: "#ff9933",
//                        borderColor:"red",
//                        borderWidth:2,
//                        opacity:0.5
                }
            },
            pointer: {
                length: "55%",
                width: 3
            },
            title: {
                offsetCenter: [0, '100%'],       // x, y，单位px
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontSize: 14,
                    color: '#66ccff',
//                        fontStyle: 'italic'
                },
            },
            detail: {
                offsetCenter: ['-15%', '50%'],
                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    fontWeight: 'bolder',
                    fontSize: '170%',
                    color: "#ff9933"
                },
                formatter: '{value}%'
            },
            data: [{value: 95, name: '完好率'}]
        }
    ]
};
myCharts.setOption(option);

//多个圆环图  标准状态
var myChart2 = echarts.init(document.getElementById("myChart2"));
var labelTop = {
    normal: {
        color: '#66ccff',
        label: {
            show: true,
            position: 'center',
            formatter: '{b}',
            textStyle: {
                baseline: 'bottom',
                color: '#66ccff',
                fontSize:8
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
                return 100 - params.value + '%'
            },
            textStyle: {
                baseline: 'top'
            }
        }
    },
};
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
    emphasis: {
        color: 'rgba(0,0,0,0)'
    },
};
var radius = [20, 25];
option = {
    toolbox: {
        show: false,
        feature: {
            dataView: {show: true, readOnly: false},
            magicType: {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        width: '20%',
                        height: '30%',
                        itemStyle: {
                            normal: {
                                label: {
                                    formatter: function (params) {
                                        return 'other\n' + params.value + '%\n'
                                    },
                                    textStyle: {
                                        baseline: 'middle'

                                    }
                                }
                            },
                        }
                    }
                }
            },
            restore: {show: true},
            saveAsImage: {show: true}
        }
    },
    textStyle: {
//            color:'#66ccff'
        color: '#ff9933'
    },
    series: [
//            {
//                type : 'pie',
//                center : ['10%', '30%'],
//                radius : radius,
//                x: '0%', // for funnel
//                itemStyle : labelFromatter,
//                data : [
//                    {name:'other', value:46, itemStyle : labelBottom},
//                    {name:'GoogleMaps', value:54,itemStyle : labelTop}
//                ]
//            },
//            {
//                type : 'pie',
//                center : ['30%', '30%'],
//                radius : radius,
//                x:'20%', // for funnel
//                itemStyle : labelFromatter,
//                data : [
//                    {name:'other', value:56, itemStyle : labelBottom},
//                    {name:'Facebook', value:44,itemStyle : labelTop}
//                ]
//            },
        {
            type: 'pie',
            center: ['35%', '22%'],
            radius: radius,
            x: '40%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 65, itemStyle: labelBottom},
                {name: '牵头起草数', value: 35, itemStyle: labelTop}
            ]
        },
        {
            type: 'pie',
            center: ['65%', '22%'],
            radius: radius,
            x: '60%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 70, itemStyle: labelBottom},
                {name: '参与起草数', value: 30, itemStyle: labelTop}
            ]
        },
//            {
//                type : 'pie',
//                center : ['90%', '30%'],
//                radius : radius,
//                x:'80%', // for funnel
//                itemStyle : labelFromatter,
//                data : [
//                    {name:'other', value:73, itemStyle : labelBottom},
//                    {name:'Weixin', value:27,itemStyle : labelTop}
//                ]
//            },
        {
            type: 'pie',
            center: ['10%', '75%'],
            radius: radius,
            y: '55%',   // for funnel
            x: '0%',    // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 78, itemStyle: labelBottom},
                {name: '国家标准', value: 22, itemStyle: labelTop}
            ]
        },
        {
            type: 'pie',
            center: ['30%', '75%'],
            radius: radius,
            y: '55%',   // for funnel
            x: '20%',    // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 78, itemStyle: labelBottom},
                {name: '国际标准', value: 22, itemStyle: labelTop}
            ]
        },
        {
            type: 'pie',
            center: ['50%', '75%'],
            radius: radius,
            y: '55%',   // for funnel
            x: '40%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 78, itemStyle: labelBottom},
                {name: '行业标准', value: 22, itemStyle: labelTop}
            ]
        },
        {
            type: 'pie',
            center: ['70%', '75%'],
            radius: radius,
            y: '55%',   // for funnel
            x: '60%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 83, itemStyle: labelBottom},
                {name: '当地标准', value: 17, itemStyle: labelTop}
            ]
        },
        {
            type: 'pie',
            center: ['90%', '75%'],
            radius: radius,
            y: '55%',   // for funnel
            x: '80%', // for funnel
            itemStyle: labelFromatter,
            data: [
                {name: 'other', value: 89, itemStyle: labelBottom},
                {name: '企业标准', value: 11, itemStyle: labelTop}
            ]
        }
    ]
};
myChart2.setOption(option);

//能力状态 柱形图
var myChart3 = echarts.init(document.getElementById("myChart3"));
myChart3.setOption(getBarEcharts());
var bar_chip = '../img/bar_chip.png';
//myChart3.setOption({
//    textStyle:{
//        fontSize:8
//    },
//    yAxis: [
//        {
//            name: "数量",
//            type: 'value',
//        },
//    ],
//    xAxis: [
//        {
//            name: "试验类别",
//            type: 'category',
//            data: ["完全具备数", "部分具备", "不具备"]
//        }
//    ],
//    grid: {
//        x: 48,
//        y: 28
//    },
//    series: [
//        {
//            symbolSize: ['60%', '10%'],
//            data: [{
//                value: 134,
//                symbol: bar_chip
//            }, {
//                value: 32,
//                symbol: bar_chip
//            }, {
//                value: 2,
//                symbol: bar_chip
//            }
//            ]
//        }
//    ]
//});
//

//一次合格率 同心圆
var myChart4 = echarts.init(document.getElementById("myChart4"));
myChart4.setOption(getCenterPie());
var dataStyle = {
    normal: {
        label: {
            normal: {
                show:false,
                textStyle: {
                    color: '#66ccff'
                },
            }
        },
        labelLine: {
            normal: {
                show: false,
                lineStyle: {
                    color: 'gray'
                },
                smooth: 0.2,
                length: 1,
                length2: 2
            }
        },
    }
};
var placeHolderStyle = {
    normal: {
        color: 'rgba(0,0,0,0)',
        label: {
            normal: {
                show:true,
                textStyle: {
                    color: '#66ccff'
                },
            }
        },
        labelLine: {
            normal: {
                show: true,
                lineStyle: {
                    color: 'gray'
                },
                smooth: 0.2,
                length: 1,
                length2: 2
            }
        },
    },
    emphasis: {
        color: 'rgba(0,0,0,0)'
    }
};
myChart4.setOption({
    // textStyle:{
    //     fontSize:8
    // },
    color: ['#4397f7', '#66ccff'],
    series: [
        {
            name: '一次合格率',
            type: 'pie',
            clockWise: false,
            radius: [50, 60],
            itemStyle: dataStyle,
            data: [
                {
                    value: 68,
                    name: '整机',
                    itemStyle: placeHolderStyle
                },
                {
                    value: 32,
                    name: '整机',
                    // itemStyle: placeHolderStyle
                }
            ]
        },
        {
            name: '一次合格率',
            type: 'pie',
            clockWise: false,
            radius: [40, 50],
            itemStyle: dataStyle,
            data: [
                {
                    value: 29,
                    name: '模块',
                    itemStyle: placeHolderStyle
                },
                {
                    value: 71,
                    name: '模块',
                    // itemStyle: placeHolderStyle
                }
            ]
        }
    ]

});

//问题闭环率
var myChart5 = echarts.init(document.getElementById("myChart5"));
myChart5.setOption(getCenterPie());
myChart5.setOption({
    // textStyle:{
    //     fontSize:8
    // },
    color: ['#4397f7', '#66ccff'],
    series: [
        {
            name: '问题闭环率',
            type: 'pie',
            clockWise: false,
            radius: [50, 60],
            itemStyle: dataStyle,
            data: [
                {
                    value: 68,
                    name: '整机',
                    itemStyle: placeHolderStyle
                },
                {
                    value: 32,
                    name: '整机',
                    // itemStyle: placeHolderStyle
                }
            ]
        },
        {
            name: '问题闭环率',
            type: 'pie',
            clockWise: false,
            radius: [40, 50],
            itemStyle: dataStyle,
            data: [
                {
                    value: 29,
                    name: '模块',
                    itemStyle: placeHolderStyle
                },
                {
                    value: 71,
                    name: '模块',
                    // itemStyle: placeHolderStyle
                }
            ]
        }
    ]

});

//订单及时率
var myChart6 = echarts.init(document.getElementById("myChart6"));
myChart6.setOption(getAreaEcharts());
myChart6.setOption({
    textStyle:{
        fontSize:8
    },
    legend: {
        show: true,
        data: ['整机', '模块'],
        textStyle:{
            fontSize:8
        },
    },
    grid: {
        x: 23,
        y: 28
    },
    yAxis:{
    	max:100
    },
    xAxis: [
        {
            name: '月份',
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        }
    ],
    series: [
        {
            name: '整机',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [40, 32, 40, 34, 50, 55, 40, 41, 44, 46, 45, 45]
        },
        {
            name: '模块',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [54, 52, 53,49, 40, 40, 48, 43, 40, 46, 48, 52]
        }
    ]

});


//用户满意度
var myChart7 = echarts.init(document.getElementById("myChart7"));
myChart7.setOption(getAreaEcharts());
myChart7.setOption({
    textStyle:{
        fontSize:8
    },
    legend: {
        show: true,
        data: ['整机', '模块'],
        textStyle:{
            fontSize:8
        },
    },
    grid: {
        y: 28
    },
    yAxis:{
    	max:100
    },
    xAxis: [
        {
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        }
    ],
    series: [
        {
            name: '整机',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [30, 32, 25, 34, 45, 30, 40, 23,34,46, 21,34]
        },
        {
            name: '模块',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [40, 33,61, 34, 29, 30, 31, 34, 42, 44, 34, 28]
        }
    ]

});

//量产一致与不一致占比
var myChart8 = echarts.init(document.getElementById("myChart8"));
myChart8.setOption(getRoseEcharts());
myChart8.setOption({

    color: ['#66ccff', '#4397f7'],
    legend: {
        show: true,
        textStyle: {
            color: '#66ccff',
            fontSize: 8,
        },
        orient: 'vertical',  //布局  纵向布局
        data: ['量产一致型号数', '量产不一致型号数'],
        itemWidth: 10,  //图例标记的图形宽度
        itemHeight: 2, //图例标记的图形高度
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: [0, 50],
            center: ['50%', '55%'],
            roseType: 'radius',
            label: {
                normal: {
                    show: true,
                    position: "inside",
                    formatter: "{d}%"
                },
                emphasis: {
                    show: true
                }
            },
            lableLine: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [
                {value: 10, name: '量产一致型号数'},
                {value: 15, name: '量产不一致型号数'}
            ]
        },
    ]
});

//量产一致
var myChart9 = echarts.init(document.getElementById("myChart9"));
myChart9.setOption(getBarEcharts());
var bar_chip = '../img/bar_chip.png';
var labelSetting = {
    normal: {
        show: false,
        position: 'outside',
        offset: [10, 0],
        textStyle: {
            fontSize: 8
        }
    }
};
myChart9.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#66ccff', '#a5fff1'],
    legend: {
        show: true,
        data: ['2015年', '2016年']
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "17%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});



//共产一致与不一致占比
var myChart11 = echarts.init(document.getElementById("myChart11"));
myChart11.setOption(getRoseEcharts());
myChart11.setOption({
    color: ['#66ccff', '#4397f7'],
    legend: {
        show: true,
        textStyle: {
            color: '#66ccff',
            fontSize: 8,
        },
        orient: 'vertical',  //布局  纵向布局
        data: ['共产一致型号数', '共产不一致型号数'],
        itemWidth: 10,  //图例标记的图形宽度
        itemHeight: 2, //图例标记的图形高度
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: [0, 50],
            center: ['50%', '55%'],
            roseType: 'radius',
            label: {
                normal: {
                    show: true,
                    position: "inside",
                    formatter: "{d}%"
                },
                emphasis: {
                    show: true
                }
            },
            lableLine: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [
                {value: 10, name: '共产一致型号数'},
                {value: 15, name: '共产不一致型号数'}
            ]
        },
    ]
});

//共产一致
var myChart12 = echarts.init(document.getElementById("myChart12"));
myChart12.setOption(getBarEcharts());
myChart12.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#66ccff', '#a5fff1'],
    legend: {
        show: true,
        data: ['2015年', '2016年']
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "17%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});


//数据挖掘 量产一致与不一致占比
var myChart14 = echarts.init(document.getElementById("myChart14"));
myChart14.setOption(getRoseEcharts());
myChart14.setOption({
    color: ['#66ccff', '#4397f7'],
    grid:{
        x:10
    },
    legend: {
        x: 'right',
        y: 'top',
        show: true,
        textStyle: {
            color: '#66ccff',
            fontSize: 11,
        },
        orient: 'vertical',  //布局  纵向布局
        data: ['量产一致型号数', '量产不一致型号数'],
        itemWidth: 10,  //图例标记的图形宽度
        itemHeight: 2, //图例标记的图形高度
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: [0, 50],
            center: ['45%', '60%'],
            roseType: 'radius',
            label: {
                normal: {
                    show: true,
                    position: "inside",
                    formatter: "{d}%"
                },
                emphasis: {
                    show: true
                }
            },
            lableLine: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [
                {value: 10, name: '量产一致型号数'},
                {value: 15, name: '量产不一致型号数'}
            ]
        },
    ]
});

//数据挖掘  量产一致
var myChart15= echarts.init(document.getElementById("myChart15"));
myChart15.setOption(getBarEcharts());
myChart15.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#66ccff', '#a5fff1'],
    legend: {
        show: true,
        data: ['2015年', '2016年']
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "17%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            data: ["冰冷",'家空','洗涤','商空','热水器','厨电'],
            axisLabel: {
                 // rotate: 30,

            },
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});

//数据量挖掘 产不一致性
var myChart16 = echarts.init(document.getElementById("myChart16"));
myChart16.setOption(getBarEcharts());
myChart16.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#00e673', '#66ffcc'],
    legend: {
        show: true,
        data: ['2015年', '2016年'],
        orient: ' vertical' //布局  纵向布局
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "17%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            rote:30,
            data: ["冰冷",'家空','洗涤','商空','热水器','厨电']
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});


//数据挖掘 共产一致与不一致占比
var myChart17 = echarts.init(document.getElementById("myChart17"));
myChart17.setOption(getRoseEcharts());
myChart17.setOption({
    color: ['#66ccff', '#4397f7'],
    grid:{
        x:10
    },
    legend: {
        x: 'right',
        y: 'top',
        show: true,
        textStyle: {
            color: '#66ccff',
            fontSize: 11,
        },
        orient: 'vertical',  //布局  纵向布局
        data: ['量产一致型号数', '量产不一致型号数'],
        itemWidth: 10,  //图例标记的图形宽度
        itemHeight: 2, //图例标记的图形高度
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: [0, 50],
            center: ['45%', '60%'],
            roseType: 'radius',
            label: {
                normal: {
                    show: true,
                    position: "inside",
                    formatter: "{d}%"
                },
                emphasis: {
                    show: true
                }
            },
            lableLine: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            data: [
                {value: 10, name: '量产一致型号数'},
                {value: 15, name: '量产不一致型号数'}
            ]
        },
    ]
});

//数据挖掘  共产一致
var myChart18= echarts.init(document.getElementById("myChart18"));
myChart18.setOption(getBarEcharts());
myChart18.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#66ccff', '#a5fff1'],
    legend: {
        show: true,
        data: ['2015年', '2016年']
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "17%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            rote:30,
            data: ["冰冷",'家空','洗涤','商空','热水器','厨电']
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});

//数据量挖掘 共产不一致性
var myChart19 = echarts.init(document.getElementById("myChart19"));
myChart19.setOption(getBarEcharts());
myChart19.setOption({
    textStyle:{
        fontSize:8
    },
    color: ['#00e673', '#66ffcc'],
    legend: {
        show: true,
        data: ['2015年', '2016年'],
        orient: ' vertical' //布局  纵向布局
    },
    grid: {
//            show:true,
        x: "12%",
        x2: "15%",
        y: '20%',
        y2: "14%"
    },
    yAxis: [
        {
            name: "数量",
            type: 'value'
        }
    ],
    xAxis: [
        {
            name: "月份",
            type: 'category',
            rote:30,
            data: ["冰冷",'家空','洗涤','商空','热水器','厨电']
        }
    ],
    series: [{
        name: '2015',
        type: 'pictorialBar',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        barCategoryGap: '40%',
        data: [{
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 157,
            symbol: bar_chip
        }, {
            value: 21,
            symbol: bar_chip
        }, {
            value: 66,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }
        ]
    }, {
        name: '2016',
        type: 'pictorialBar',
        barGap: '10%',
        label: labelSetting,
        symbolRepeat: true,
        symbolSize: ['80%', '60%'],
        data: [{
            value: 184,
            symbol: bar_chip
        }, {
            value: 29,
            symbol: bar_chip
        }, {
            value: 73,
            symbol: bar_chip
        }, {
            value: 91,
            symbol: bar_chip
        }, {
            value: 95,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }, {
            value: 123,
            symbol: bar_chip
        }, {
            value: 78,
            symbol: bar_chip
        }
        ]
    }]
});
