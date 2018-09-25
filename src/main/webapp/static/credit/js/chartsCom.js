let bodyScale = 1;
let colors = [
    '#37a2da',
    '#67e0e3',
    '#ffdb5c',
    '#ff9f7f',
    '#e062ae',
];
//所有图表的公共属性
let opt_com = {
    color: colors,
    textStyle: {
        fontFamily: 'PingFang SC, microsoft yahei,微软雅黑, sans-serif',
        fontSize: 10 * bodyScale
    },
    legend: {
        itemWidth: 7 * bodyScale,
        itemHeight: 7 * bodyScale,
        textStyle: {
            fontSize: 10 * bodyScale,
        },
        top: '2%'
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
            data: ['直接访问','邮件营销']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '70%',
                center: ['50%', '50%'],
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

