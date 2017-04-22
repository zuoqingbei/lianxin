/**
 * Created by Administrator on 2017/4/12.
 */

// 折线图
function getLineEcharts() {
    var option = {
        title: {
            left: 'center',
            text: '',
            textStyle: {
                fontSize: 12
            }
        },
        grid: {
            right: 0,
            bottom: 20,
            left: 40,
            top: 55
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            show: true,
            top: 20
        },
        color: ['#4ea3d9', '#f6b65f', '#6be6c1', '#bba1df', '#434247', '#e66bdf'],
        textStyle: {
            color: '#333',
            fontSize: 12
        },

        xAxis: {
            data: [],
            boundaryGap: false,
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
                    color: '#66ccff'
                }
            },
            axisTick: {
                show: false,
                alignWithLabel: true,
                lineStyle: {
                    color: '#66ccff'
                }
            },

        },
        yAxis: {
            axisLine: { //坐标轴
                show: false
            },
            axisLabel: {   //坐标值
                show: true,
                textStyle: {
                    color: '#66ccff'
                }
            },
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: '#123a67'
                }
            },
            axisTick: {  //刻度值
                show: false,
            }

        },
        series: []
    };
    return option;

}
// 柱状图 横向
function getBarEcharts() {
    option = {
        title: {
            left: 'center',
            text: '',
            textStyle: {
                color: '#333',
                fontSize: 12
            }
        },
        tooltip: {},
        legend: {
            show: false
        },
        textStyle: {
            color: '#333',
            fontSize: 12
        },
        grid: {
            right: 10,
            bottom: 20,
            left: 60,
            top: 30
        },
        xAxis: {
            data: [],
            boundaryGap: false,
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
                    color: '#66ccff'
                }
            },
            axisTick: {
                show: false,
                alignWithLabel: true,
                lineStyle: {
                    color: '#66ccff'
                }
            },

        },
        yAxis: {
            axisLine: { //坐标轴
                show: false
            },
            axisLabel: {   //坐标值
                show: true,
                textStyle: {
                    color: '#66ccff'
                }
            },
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: '#123a67'
                }
            },
            axisTick: {  //刻度值
                show: false,
            }

        },
        series: [
            {
                type: 'bar',
                barWidth: 6,
                itemStyle: {
                    normal: {
                        color: "#4ea3d9"
                    }
                },
                label: {
                    normal: {
                        show: false
                    }
                },
                data: []
            }]
    };

    return option;
}
// 饼图
function getPieEcharts() {
    option = {
        title: {
            text: '',
            x: 'left',
            textStyle: {
                color: '#333',
                fontSize: 12
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '',
                type: 'pie',
                radius: '55%',
                center: ['30%', '60%'],
                data: [],
                color: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    return option;
}
//面积图
function getAreaEcharts() {
    option = {
        title: {
            text: '',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: []
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: {
            data: [],
            boundaryGap: false,
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
                    color: '#66ccff'
                }
            },
            axisTick: {
                show: false,
                alignWithLabel: true,
                lineStyle: {
                    color: '#66ccff'
                }
            },

        },
        yAxis: {
            axisLine: { //坐标轴
                show: false
            },
            axisLabel: {   //坐标值
                show: true,
                textStyle: {
                    color: '#66ccff'
                }
            },
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: '#123a67'
                }
            },
            axisTick: {  //刻度值
                show: false
            }

        },
        series: []
    };
    return option;
}
//雷达图
function getRadarEcharts() {
    option = {
        title: {
            text: '',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            x: 'center',
            data: []
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        polar: [],
        series: [
            {
                type: 'radar',
                itemStyle: {
                    normal: {
                        areaStyle: {
                            type: 'default'
                        }
                    }
                },
                data: []
            }
        ]
    };

    return option;
}
//折线图和柱状图
function getLineAndBar() {
    option = {
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        legend: {
            data: []
        },
        xAxis: {
            data: [],
            boundaryGap: false,
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
                    color: '#66ccff'
                }
            },
            axisTick: {
                show: false,
                alignWithLabel: true,
                lineStyle: {
                    color: '#66ccff'
                }
            },

        },
        yAxis: {
            axisLine: { //坐标轴
                show: false
            },
            axisLabel: {   //坐标值
                show: true,
                textStyle: {
                    color: '#66ccff'
                }
            },
            splitLine: {  //刻度线
                show: true,
                lineStyle: {
                    color: '#123a67'
                }
            },
            axisTick: {  //刻度值
                show: false,
            }

        },
        series: []
    };
    return option;
}
//仪表盘
function getGaugeEcharts() {
    option = {
        tooltip: {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: []
    };
    return option;
}
//多个仪表盘
function getManyGauge() {
    option = {
        tooltip: {
            formatter: "{a} <br/>{c} {b}"
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: []
    };
    //clearInterval(timeTicket);
    //timeTicket = setInterval(function (){
    //    option.series[0].data[0].value = (Math.random()*100).toFixed(2) - 0;
    //    option.series[1].data[0].value = (Math.random()*7).toFixed(2) - 0;
    //    option.series[2].data[0].value = (Math.random()*2).toFixed(2) - 0;
    //    option.series[3].data[0].value = (Math.random()*2).toFixed(2) - 0;
    //    myChart.setOption(option,true);
    //},2000)
    return option;
}
//圆环图
function getYuanhuan() {
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'center',
                            max: 1548
                        }
                    }
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: []
    };
    return option;
}
//同心圆
function getCenterPie() {
    option = {
        title: {
            text: '',
            subtext: '',
            sublink: '',
            x: 'center',
            y: 'center',
            itemGap: 20,
            textStyle: {
                color: 'rgba(30,144,255,0.8)',
                fontFamily: '微软雅黑',
                fontSize: 35,
                fontWeight: 'bolder'
            }
        },
        tooltip: {
            show: true,
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: []
    };
    return option;
}
//环形图
function getHuanXing() {
    option = {
        legend: {
            x: 'center',
            y: 'center',
            data: []
        },
        title: {
            text: '',
            subtext: '',
            x: 'center'
        },
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
        series: []
    };
    return option;

}