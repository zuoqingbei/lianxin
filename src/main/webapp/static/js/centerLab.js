// 数据分析
var bodyScale = 1;//原始比例1
    //左
    var chartone = echarts.init(document
        .getElementById("echart_one"));
    chartone.setOption(initone());
    //右
    var charttwo = echarts.init(document
        .getElementById("echart_two"));
    charttwo.setOption(getBarEcharts());
    charttwo.setOption(inittwo());

    //左
    var chartthree = echarts.init(document
        .getElementById("echart_three"));
    chartthree.setOption(initone());
    //右
    var chartfour = echarts.init(document
        .getElementById("echart_four"));
    chartfour.setOption(getAreaEcharts());
    chartfour.setOption(initfour());

    //左
    var chartfive = echarts.init(document
        .getElementById("echart_five"));
    chartfive.setOption(initone());
    //右
    var chartsix = echarts.init(document
        .getElementById("echart_six"));
    chartsix.setOption(getBarEcharts());
    chartsix.setOption(inittwo());
    //$("#labMain_cbro_content").load("labAnalysis_small.html");
    // document.getElementById("labMain_cbro_content").innerHTML = '<object type="text/html" data="labAnalysis_small.html" width="100%" height="100%"></object>';

function initone() {
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
                    {name: '', value: 5, itemStyle: labelBottom},
                    {name: '', value: 95, itemStyle: labelTop}
                ]
            }

        ]

    };
    return option;

}

function inittwo() {
    var bar_chip = '${contextPath!}/static/img/bar_chip.png';
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


    var option = {
        textStyle: {
            fontSize: bodyScale * 8
        },
        yAxis: [
            {
                name: "数量",

                nameTextStyle: {
                    fontSize: bodyScale * 10,

                },

                type: 'value',
                max: 100,
            },
        ],
        xAxis: [
            {
                name: "",
                type: 'category',
                data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
            }
        ],
        grid: {
            // x: "10%",
//            x2: "25%",
//            y: '22%',
//            y2: "26%",

            x: "15%",
            x2: "10%",
            y: '20%',
            y2: "34%",
        },
        series: [
            {
                symbolSize: ['50%', '10%'],
                data: [{
                    value: 99,
                    symbol: bar_chip
                }, {
                    value: 85,
                    symbol: bar_chip
                }, {
                    value: 82,
                    symbol: bar_chip
                }
                    , {
                        value: 78,
                        symbol: bar_chip
                    }
                    , {
                        value: 82,
                        symbol: bar_chip
                    }
                    , {
                        value: 78,
                        symbol: bar_chip
                    }
                    , {
                        value: 78,
                        symbol: bar_chip
                    }, {
                        value: 82,
                        symbol: bar_chip
                    }, {
                        value: 82,
                        symbol: bar_chip
                    }, {
                        value: 78,
                        symbol: bar_chip
                    }
                    , {
                        value: 78,
                        symbol: bar_chip
                    }, {
                        value: 82,
                        symbol: bar_chip
                    }

                ]
            }
        ]
    };
    return option;

}


function initfour() {

    var option = {
        textStyle: {
            fontSize: bodyScale * 8
        },
        legend: {
            show: true,
            data: ['整机', '模块'],
            textStyle: {
                fontSize: bodyScale * 8
            },
            itemWidth: 6, //图例标记的图形宽度
            itemHeight: 6 //图例标记的图形高度
        },
        grid: {

            x: "11%",
            x2: "10%",
            y: '20%',
            y2: "20%"
        },
        xAxis: [
            {
                name: '',
                data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            }
        ],
        yAxis: [
            {
                name: "数量",

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
                data: [99, 85, 82, 78, 82, 60, 82, 60, 82, 85, 82, 78],
                itemStyle: {
                    normal: {
                        color: "#ff6666"
                    }
                }

            }
        ]

    };
    return option;

}

//曲线
var colorData = ['#66ccff','#ff9933','#ff6666','#00cc66','#ffff99','#cc99ff','#99ccff','#ff99cc','#ff9900','#ffff00','#ffff00','#66ffff',
'#3366ff','#660099','#ff0099','#cc6600','#ccff00','99ff99','#00cccc','#006699','#9999ff'];//图例颜色 需手工扩充
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
    legend: ['频率(Hz)', 'M1(℃)', 'M2(℃)', 'M3(℃)', '降雨量(ml)', '电流(V)', '电压(A)', '功率(W)'],
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
    };
    var item = {
        name: dealBracketForObj(name),
        type: 'line',
        data: dataArr,
        itemStyle: {
            normal: {
                color: colorData[colorIndex]
            }
        },
        show: false
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
            x2: '3%',
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
                        fontSize:12*bodyScale
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
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,
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
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,
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
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,

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
            x2: '3%',
            y: '3%',
            y2:'12%'
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
                        fontSize:12*bodyScale
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
                offset: 40,
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
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,
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
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,

            },
            {
                type: 'value',
                name: 'A',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                position: 'right',
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,
            },
            {
                type: 'value',
                name: 'kw.h',
                nameTextStyle: {
                    color: '#66ccff'
                },
                nameLocation: 'start',
                position: 'right',
                offset: 40,
                axisLabel: {
                    formatter: '{value} ',
                    textStyle: {
                        color: '#66ccff',
                        fontSize:12*bodyScale
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
                lineStyle:{
                    normal:{
                        width:0.5
                    }
                },
                symbolSize:1,
            }
        ],
        series: seriesBottomData
    };
    myChart2.clear();
    myChart2.setOption(option2);
    echarts.connect([myChart1, myChart2]);
    /*    setTimeout(function (){
     window.onresize = function () {
     myChart1.resize();
     myChart2.resize();
     }
     },200) */

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