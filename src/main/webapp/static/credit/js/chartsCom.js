let bodyScale = 1;
let colors = [
    '#1890ff',
    '#13c2c2',
    '#2fc25c',
    '#facc15',
    '#ef4763',
    '#8543e0',
    '#40a9ff',
    '#36cfc9',
    '#73d13d',
    '#ffec3d',
    '#ff4d4f',
    '#9254de',
];
let [cTextWeek, cSplitLine] = ["#6a7985", "#6a7985"];
let barWidth = 12 * bodyScale;
let lineStyle = {
    shadowColor: '#666666',
    shadowBlur: 4 * bodyScale
};
let pieLabelOut = {
    color: '#fff',
    normal: {
        position: 'outer'
    }
};
//所有图表的公共属性
let opt_com = {
    color: colors,
    textStyle: {
        fontFamily: 'PingFang SC, microsoft yahei,微软雅黑, sans-serif',
        fontSize: 12 * bodyScale
    },
    legend: {
        itemWidth: 10 * bodyScale,
        itemHeight: 10 * bodyScale,
        textStyle: {
            fontSize: 10 * bodyScale,
        },
        top: '2%',
        type:'scroll'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            //type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        },
        textStyle:{
            align:'left'
        }

    },
    grid: {
        bottom: "15%",//底边留空
        top: '15%',
        left: '8%',
        right: '5%',
    }
};
//直角坐标系坐标轴
let axis_com = {
    axisLabel: { //标签名称
        fontSize: 12 * bodyScale,
    },
    nameTextStyle: { //坐标轴名称
        fontSize: 12 * bodyScale
    },
    nameGap: 10 * bodyScale, //坐标轴名称距离
    axisTick: { //小刻度线
        show: false
    },
    axisLine: { //坐标轴
        lineStyle: {
            color: cTextWeek
        }
    },
    yAxis:{
       // nameGap:30,
        splitLine: { //分割线
            show: true,
            lineStyle: {
                color: cSplitLine
            }
        },
    },
    boundaryGap: false
};
//圆环图 series里的属性设置
let circle_series_label = {
    normal: {
        show: true,
        fontSize: 12 * bodyScale
    },
    emphasis: {
        show: true,
        textStyle: {
            fontSize: 15 * bodyScale,
            fontWeight: 'normal'
        }
    }
};
let circle_series_labline = {
    normal: {
        show: true,
    }

};
let axisLine_Y = {
    lineStyle: {
        color: '#0083b3'
    }
};
let splitLine = {
    show: false
};
//条形图(水平)公共属性
//opt_bar_horizon写在里面是为了不让后面的对象覆盖opt_com
let opt_bar_horizon = $.extend(true, {}, opt_com, {
    xAxis: $.extend(true, {}, axis_com, {
        type: 'value',
        splitLine:{
            show:false
        },
        show:false
    }),
    yAxis: $.extend(true, {}, axis_com, {
        boundaryGap: true,
        type: 'category',
        axisLine:{
            show:false
        },
        splitLine:{
            show:false
        },
        show:true
    }),
    barWidth: '30%'
});

//条形图(竖直)公共属性
let opt_bar_vertical = $.extend(true, {}, opt_com, {
    xAxis: $.extend(true, {}, axis_com, {
        boundaryGap: true,
        type: 'category',
        axisLine:{
            show:false
        },

    }),
    yAxis: $.extend(true, {}, axis_com, {
        type: 'value',
        axisLine:{
            show:false
        },
    }),
    barWidth: '40%'
    //这里写此类图表其他属性
});

//折线图公共属性
let opt_line = $.extend(true, {}, opt_com, {
    xAxis: $.extend(true, {}, axis_com, {
        type: 'category',
    }),
    yAxis: $.extend(true, {}, axis_com, {
            type: 'value',
            // nameLocation:'start',
            nameGap: 10 * bodyScale,
            nameTextStyle: {
                padding: [0, 12 * bodyScale, 0, 0],
                // backgroundColor:'#f60'
            },
            axisLine:{
                show:false
            }
        }
        //这里写此类图表其他属性
    ),
});

//饼图公共属性
let opt_pie = $.extend(true, opt_com,
    {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            textStyle:{
                color:'#808080'
            },
            orient: 'vertical',
            right: 'right',
            bottom:'0px',
            type:'scroll'
        },
        series : [
            {
                name: '',
                type: 'pie',
                radius : '80%',
                center: ['50%', '52%'],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
       /* animationType: 'scale',
        animationEasing: 'elasticOut',
        animationDelay: function (idx) {
            return Math.random() * 200;
        }*/
    });

//圆环图
let opt_circle = $.extend(true, {}, opt_com, {
    tooltip: {
        formatter: "{a} <br/>{b} : {c} ({d}%)",
        trigger: 'item',
        axisPointer: {
            //type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        },
        textStyle:{
            align:'left'
        },
    },
    color: colors,
    legend: {
        textStyle: {
            color: '#666666',
            fontSize: 12 * bodyScale,
            type:'scroll'
        },
    },
});

//水球图
let opt_liquidfill_series={
    type: 'liquidFill',
    color: ['#1890ff'],
    radius: '57%',
    backgroundStyle: {
        color: 'white',
    },
    label:{
        normal:{
            textStyle:{
                fontSize:10
            }
        }
    },
    outline: {
        borderDistance: 3,//border向内占据的空间 不是width
        itemStyle: {
            borderWidth:3,
            borderColor: '#1890ff',
            shadowBlur: 0,
        }
    },

}

