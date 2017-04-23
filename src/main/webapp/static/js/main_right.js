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
            textStyle: {},
            axisLine: {            // 坐标轴线
                lineStyle: {       // 属性lineStyle控制线条样式
                    width: 7,
                    color: [[0.2, '#66ccff'], [0.8, '#66ccff'], [1, '#66ccff']]
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
                    fontSize: 14,
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






//一次合格率 同心圆
var myChart4 = echarts.init(document.getElementById("myChart4"));
myChart4.setOption(getCenterPie());
var dataStyle = {
    normal: {
        label: {show: false},
        labelLine: {show: false}
    }
};
var placeHolderStyle = {
    normal: {
        color: 'rgba(0,0,0,0)',
        label: {show: false},
        labelLine: {show: false}
    },
    emphasis: {
        color: 'rgba(0,0,0,0)'
    }
};
myChart4.setOption({

    legend: {
        // // right:0,  //图例组件离右边的距离
        // orient :' horizontal',  //布局  纵向布局
        // // width:80,      //图行例组件的宽度,默认自适应
        // x : 'right',   //图例显示在右边
        // // itemWidth:10,  //图例标记的图形宽度
        // // itemHeight:10, //图例标记的图形高度
        textStyle: {    //图例文字的样式
            color: '#66ccff',
            fontSize: 12
        },
        data: ['整机', '模块']
    },
    color: ['#4397f7', '#66ccff'],
    series: [
        {
            name: '1',
            type: 'pie',
            clockWise: false,
            radius: [40, 50],
            itemStyle: dataStyle,
            label: {
                normal: {
                    textStyle: {
                        color: '#66ccff'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: true,
                    lineStyle: {
                        color: 'gray'
                    },
                    smooth: 0.2,
                    length: 3,
                    length2: 5
                }
            },
            data: [
                {
                    value: 68,
                    name: '68%的人表示过的不错'
                },
                {
                    value: 32,
                    name: '',
                    itemStyle: placeHolderStyle
                }
            ]
        },
        {
            name: '2',
            type: 'pie',
            clockWise: false,
            radius: [50, 60],
            itemStyle: dataStyle,
            data: [
                {
                    value: 29,
                    name: ''
                },
                {
                    value: 71,
                    name: 'invisible',
                    itemStyle: placeHolderStyle
                }
            ]
        }
    ]

});

//问题闭环率
var myChart5 = echarts.init(document.getElementById("myChart5"));
myChart5.setOption(getCenterPie());
myChart5.setOption({
    color: ['#4397f7', '#66ccff'],
    series: [
        {
            name: '1',
            type: 'pie',
            clockWise: false,
            radius: [40, 50],
            itemStyle: dataStyle,
            label: {
                normal: {
                    textStyle: {
                        color: '#66ccff'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: true,
                    lineStyle: {
                        color: 'gray'
                    },
                    smooth: 0.2,
                    length: 3,
                    length2: 5
                }
            },
            data: [
                {
                    value: 68,
                    name: ''
                },
                {
                    value: 32,
                    name: 'invisible',
                    itemStyle: placeHolderStyle
                }
            ]
        },
        {
            name: '2',
            type: 'pie',
            clockWise: false,
            radius: [50, 60],
            itemStyle: dataStyle,
            data: [
                {
                    value: 29,
                    name: ''
                },
                {
                    value: 71,
                    name: 'invisible',
                    itemStyle: placeHolderStyle
                }
            ]
        }
    ]

});

//订单及时率
var myChart6 = echarts.init(document.getElementById("myChart6"));
myChart6.setOption(getAreaEcharts());
myChart6.setOption({
    legend: {
        show: true,
        data: ['整机', '模块']
    },
    grid: {
        x: 23,
        y: 28
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
            data: [120, 132, 101, 134, 90, 230, 210, 231, 124, 156, 321, 154]
        },
        {
            name: '模块',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [220, 182, 191, 234, 290, 330, 310, 124, 342, 234, 134, 256]
        }
    ]

});


//用户满意度
var myChart7 = echarts.init(document.getElementById("myChart7"));
myChart7.setOption(getAreaEcharts());
myChart7.setOption({
    legend: {
        show: true,
        data: ['整机', '模块']
    },
    grid: {
        y: 28
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
            data: [120, 132, 101, 134, 90, 230, 210, 231, 124, 156, 321, 154]
        },
        {
            name: '模块',
            type: 'line',
            stack: '总量',
            areaStyle: {normal: {}},
            data: [220, 182, 191, 234, 290, 330, 310, 124, 342, 234, 134, 256]
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
var bar_chip = contextPath+'/img/bar_chip.png';
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

//量产不一致性
var myChart10 = echarts.init(document.getElementById("myChart10"));
myChart10.setOption(getBarEcharts());
myChart10.setOption({
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
            fontSize: 11,
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

//共产不一致性
var myChart13 = echarts.init(document.getElementById("myChart13"));
myChart13.setOption(getBarEcharts());
myChart13.setOption({
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