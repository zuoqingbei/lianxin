/**
 * Created by Administrator on 2017/4/12 0012.
 */
/**
 * Created by Administrator on 2017/4/12 0012.
 */
var timeId;
// var dataBase = eval('(' + $("input").val() + ')');
// console.log(eval('(' + $("input").val() + ')'))
// rollStr(dataBase);
/*
function rollStr(dataBase) {
    var str = '<ul>';
    for(var i=0;i<dataBase.length;i++){
        str+=
            '<li><dl>'+
            '<dd><a style="color: #0298D7;" href="/YIJIAN/article?artid='+dataBase[i].id+'" target="_blank">'+(dataBase[i].title.length>6?(dataBase[i].title.substr(0,6)+"..."):dataBase[i].title)+'</a><span>'+dataBase[i].dateTime+'</span></dd>'+
            '<dt>'+(dataBase[i].text.length>14?(dataBase[i].title.substr(0,15)+"..."):dataBase[i].title)+'</dt>'+
            '</dl>'+
            '</li>'
    }
    str+='</ul>';
    $("#slide1").html("").append(str);
    getRoll();
}
*/

function getGeoArr(data){
    var geo = {};
    for (var i =0; i<data.length; i++) {
        geo[data[i].name]=data[i].litude;
    }
    console.log(geo);
    return geo;
}
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

var option = {
    //backgroundColor: 'rgba(0,0,0,0)',
    //color: ['gold','aqua','lime'],
    title : {
        show:false,
        text: '',
        x:'left',
        y:'top',
        textStyle:{
            fontFamily: '微软雅黑',
            fontSize: 20,
            fontStyle: 'normal',
            fontWeight: 'normal',
            color:'#ffffff'
        }
    },

    calculable : false,
    tooltip : {
        show:true,
        showContent:true,
        enterable:true,
        trigger: 'item',
//				        showDelay:100,
        hideDelay:300,
        position: function(p){
            return [p[0]-130,p[1]-90];
        },
        padding:[0,0,0,0],
        width:207,
        height:110,
        backgroundColor: 'rgba(13,43,67,0.7)',
        borderColor:'rgba(31,120,214,1)',
        formatter: function(param){
            console.log(param)
            $elList=[];
            $echartTips.empty();
            stopNewsShown();
            var $el = addNewsElem(param.data);
            return '';
        },
        textStyle:{
            color:'#ffffff'
        }
    },
    legend: {
        show: false,
        orient:  'vertical' ,
        x:'left',
        y:530,
        data:['zy_world','zy_hotpoint'],
        selectedMode: 'single',
        textStyle : {color: '#fff'}
    },
    dataRange: {
        show:false,
        orient:'vertical',
        x:'right',
        y:'bottom',
        min : 0,
        max : 100,
        calculable : false,
        selectedMode:false,
        itemWidth: 10,
        itemHeight:14,
        padding:10,
        color: ['#ff3333', 'orange','gray' ,'white','yellow','lime','aqua','pink','red'/*'#00ffb5',   '#fff94e ' , '#fff94e'*/
        ],
        textStyle:{
            color:'#fff'
        }
    },
    series : [
        {
            tooltip:{
                show:false
            },
            name: 'zy_world',
            type: 'map',
            mapType: 'world',
            roam: false,
            hoverable: false,//控制是否显示国家
            mapLocation:{
                x: "center",
                y: "center",
                heigth:"70%",
                width : "70%"

            },
            itemStyle:{
                normal:{
                    color:'#052d55',
                    borderColor:'#052d55',
                    borderWidth:1,
                    // shadowBlur:10,
                    // shadowColor:'red',
                    // shadowOffsetY:10,
                    // shadowOffsetX:10,
                    // opacity:0.5,
                    areaStyle:{
                        color: '#050c16'
                        //color: '#01a9e3'
                    },
                    label:{
                        show:false,
                        textStyle:{
                            color:'#050c16'//,
                            //fontSize:0
                        }

                    }
                },
                emphasis:{
                    areaStyle:{
                        //color: '#02899f'
                    },
                    label:true
                }
            },
            data:[],
            markLine : {
                smooth:true,
                symbol: ['none', 'circle'],
                symbolSize : 1,
                itemStyle : {
                    normal: {
                        color:'#fff',
                        borderWidth:1,
                        borderColor:'#677fa0'
                    },
                    emphasis:{
                        label:false
                    }
                },
                data : []
            },
            geoCoord: getGeoArr(dataBase),

        }, //添加背景线
        {

            name: 'zy_hotpoint',
            type: 'map',
            mapType: 'world',
            itemStyle:{
                normal:{label:{show:false}},
                emphasis:{label:{show:true}}
            },
            data:[],
            markLine : {
                smooth:true,
                effect : {
                    show: false,
                    scaleSize: 2,
                    period: 40,
                    shadowBlur: 10,
                    shadowColor: '#fff'
                },
                itemStyle : {
                    normal: {
                        borderWidth:0,
                        lineStyle: {
                            type: 'solid',
                            shadowBlur: 10
                        }
                    }
                },
                data : []//data_1995
            },
            markPoint : {
                symbol:'emptyCircle',
                symbolSize : function(v){
                    if(v>15){
                        return v/12;
                    }else{
                        return 1;
                    }
                },
                effect : {
                    show: true,
                    type:'scale',//圈圈
                    loop:true,
                    shadowBlur : 0
                },
                itemStyle:{
                    normal:{label:{ show:false}},
                    emphasis:{label:{show:false}}
                },
                data : dataBase
            }
        }//添加资讯
    ]
};

/**
 * 资讯轮循的index,为将要循环的index
 * @type {number}
 */
var showTopicIndex = 0;
/**
 * echartTips内的提元素
 * @type {Array}
 */
var $elList = [];

/**
 * echart容器
 * @type {*|jQuery|HTMLElement}
 */
var $echart = $('#main');

/**
 * echart弹出新闻提示的容器
 * @type {*|jQuery|HTMLElement}
 */
var $echartTips = $('#echartTips');

/**
 * 提示渐隐时间
 * @type {number}
 */
var TIP_SETTIMEOUT_TIME = 8000;

function addNewsElem(news){
    var $el=getTopicHtml(news);

    var divideLeft = $echart.width()/ 2,
        divideTop = $echart.height()/2;

    var left,top;
    console.log(news.litude)
    //全球热点
    var xypoint = myChart.chart.map.getPosByGeo("world",news.litude); //坐标

    left = xypoint[0];
    top=xypoint[1];

    console.log(xypoint)

    $echartTips.append($el);


    $el.css({left:left,top:top});

    if(left>divideLeft){
        $el.addClass('left');
    }else{
        $el.addClass('right');
    }

    if(top>divideTop){
        $el.addClass('top');
    }else{
        $el.addClass('bottom');
    }

    var $content = $el.children('.echart_content'),
        contentOffset = $content.offset();

    var offset = {left:contentOffset.left,top:contentOffset.top,height:$content.outerHeight(),width:$content.outerWidth()};

    if(false){
        $el.remove();
    }else{
        $el.data('offset',offset);

        $elList.push($el);
        fadeOutElList();
        startNewsShown();
        $el.hover(function(){
            fadeInElList([$el]);
            stopNewsShown();
        },function(){
            fadeOutElList();
            startNewsShown();
        });
        return $el;
    }
    return null;
}

/**
 * 每格一定时间显示资讯及新闻
 */
function showNews(){
    if(document.hidden){
        return;
    }
    $elList = [];
    showTopicIndex = addEl(dataBase,showTopicIndex);
    fadeOutElList();
    function addEl(list,index){
        if(list.length==0){
            return;
        }
        for(var num=0;num<1;num++){
            var $el = addNewsElem(list[index]);
            //console.log(index,num);
            if($el){
                if((++index)>=list.length){
                    return 0;
                }
            }else{
                return index;
            }
        }
        return index;
    }
}
function startNewsShown(){
    if(timeId==null){
        timeId = setInterval(showNews,10000);
    }
}
function listenAnimationEnd($el,fn){
    $el.on('transitionend',function(){
        $el.off('transitionend');
        fn.call();
    })
}
/**
 * 初始化资讯及新闻轮循
 */
function stopNewsShown(){
    clearInterval(timeId);
    timeId = null;
}

function getTopicHtml(currentPoint){
    var city=currentPoint.name;
    var value=currentPoint.value;
    var time = currentPoint.dateTime;
    var title = currentPoint.title;
    var id = currentPoint.id;
    var url = "/YIJIAN/article?artid=";
    return $('<div class="echart_tip"><div class="dialog_title echart_content"><a title="'+title+'"  href="'+url+id+'" target="_blank" ><span style="color:#ffffff;font-size:16px;text-shadow:2px 2px 2px rgba(0,0,0,0.9);">'+title+'</span></a>'+city+'&nbsp;&nbsp'+time+'</div>' +
        '<div class="echart_tip_arrow"><div class="echart_tip_line"></div><div class="echart_tip_head"></div></div></div>');
}
/**
 * 渐隐 elList
 */
function fadeOutElList(){
    $.each($elList,function(i,$el){
        $el.removeClass('fade_in').addClass('fade_out');
        listenAnimationEnd($el,function(){
            $el.remove();
        });
    });

    $elList = [];
}
/**
 * 渐入 elList
 */
function fadeInElList($list){
    $.each($list,function(i,$el){
        $el.off('transitionend');
        $el.removeClass('fade_out').addClass('fade_in');
    });
    $elList = $list;
}
startNewsShown();
myChart.setOption(option);
$(window).resize(function(){
    myChart.resize();
})
dingshiRoll();

//将字符串格式的整数增加千分位符号“，”
function comdify(num){
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    {
        num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
    }
    return num;
}

function getDateBase(){
    return 10000000+Math.ceil(Math.random()*10000000);
}
//定时刷新数据
function dingshiRoll() {
    var dataBase = getDateBase();
    var no_a = parseInt(0.22*dataBase);
    var no_b = parseInt(0.25*dataBase);
    var no_c = parseInt(0.35*dataBase);
    var no_d = parseInt(0.18*dataBase);
    var all = no_a + no_b + no_c + no_d;
    $("#all").html(comdify(all.toString()));
    $("#no_a").html(comdify(no_a.toString()));
    $("#no_b").html(comdify(no_b.toString()));
    $("#no_c").html(comdify(no_c.toString()));
    $("#no_d").html(comdify(no_d.toString()));
    setInterval(function() {
        if(all>20000000){
            dingshiRoll();
        }

        no_a += (1+Math.ceil(Math.random()*20));
        no_b += (1+Math.ceil(Math.random()*15));
        no_c += (1+Math.ceil(Math.random()*18));
        no_d += (1+Math.ceil(Math.random()*10));
        all = no_a + no_b + no_c + no_d;
        $("#all").html(comdify(all.toString()));
        $("#no_a").html(comdify(no_a.toString()));
        $("#no_b").html(comdify(no_b.toString()));
        $("#no_c").html(comdify(no_c.toString()));
        $("#no_d").html(comdify(no_d.toString()));
    }, 2000);
}
function getRoll(){
    var speed=10
    var slide=document.getElementById("slide");
    var slide2=document.getElementById("slide2");
    var slide1=document.getElementById("slide1");
    slide2.innerHTML=slide1.innerHTML
    function Marquee(){
        if(slide2.offsetTop<=slide.scrollTop){
            slide.scrollTop-=slide1.offsetHeight
        }else{
            slide.scrollTop++;
        }
    }
    var MyMar=setInterval(Marquee,speed)
    slide.onmouseover=function(){clearInterval(MyMar)}
    slide.onmouseout=function(){MyMar=setInterval(Marquee,speed)}
}



    $(function () {
        var aopen = document.getElementById("an_open");
        var aopenon = document.getElementById("an_open_on");
        var Ax = true;
        aopen.onclick = function () {
            if (Ax) {
                aopenon.style.display = "block";
                Ax = false;
//                console.log("aaaaaaaaaaaa")
            } else {
                aopenon.style.display = "none";
                Ax = true;
//                console.log("dddddddddddddd")
            }
        }
        lineEchart()
        function lineEchart(){
            var Trend1 = echarts.init(document.getElementById('echart1'), 'walden');
            Trend1.setOption(TrendOption());
            Trend1.setOption({
                series: [{
//                data:[setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom()]
                    data: [123, 345, 321, 456, 789, 987, 654, 456, 765, 455, 532, 432]
                }]
            });
            var Trend2 = echarts.init(document.getElementById('echart2'), 'walden');
            Trend2.setOption(TrendOption());
            Trend2.setOption({
                series: [{
//                data:[setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom()]
                    data: [342, 644, 789, 564, 987, 653, 567, 789, 534, 324, 123, 678]
                }]
            });
            var Trend3 = echarts.init(document.getElementById('echart3'), 'walden');
            Trend3.setOption(TrendOption());
            Trend3.setOption({
                series: [{
//                data:[setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom()]
                    data: [342, 567, 764, 789, 742, 144, 541, 643, 345, 778, 643, 987]
                }]
            });
            var Trend4 = echarts.init(document.getElementById('echart4'), 'walden');
            Trend4.setOption(TrendOption());
            Trend4.setOption({
                series: [{
//                data:[setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom(),setRandom()]
                    data: [542, 532, 746, 786, 986, 522, 635, 899, 666, 555, 543, 234]
                }]
            });
        }
        setInterval(function(){
//            alert(1111)
            lineEchart()
        },5000)

    })

function TrendOption() {
    var option = {
        grid: {
            right: 3,
            bottom: 20,
            left: 3,
            top: 10
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            show: false
        },
        xAxis: {
            data: [],
            splitLine: {
                show: false,
                lineStyle: {
                    color: "#0F3961"
                }
            },
            axisTick: {
                show: true,
                alignWithLabel: true
            },
            show: false,
            axisLabel: {
                margin: 2,
                textStyle: {
                    color: '#333',
                    fontSize: 9
                }
            }
        },
        yAxis: {
            axisLabel: {
                show: false
            },
            splitLine: {
                show: false,
                lineStyle: {
                    color: "#053565"
                }
            },
            axisLine: {
                show: false
            }
        },
        series: [
            {
                type: 'line',
                showSymbol: "circle",
                symbolSize: 4,
                itemStyle: {
                    normal: {
                        lineStyle: {
                            color: '#0287B2'
                        }
                    }
                },
                lineStyle: {
                    normal: {
                        width: 2
                    }
                },
                data: []
            }]
    };
    return option;
}


var canvas1, context1, canvas2, context2, canvas3, context3;
window.onload = function () {
// 	var canvas,context;
    canvas1 = document.getElementById("myCanvas");
    canvas2 = document.getElementById("onCanvas");
    canvas3 = document.getElementById("openCanvas");
    context1 = canvas1.getContext("2d");
    context2 = canvas2.getContext("2d");
    context3 = canvas3.getContext("2d");
    setInterval("draw()", 1000);
}
function draw() {



    ///得到当前系统时间的：时、分、秒
    var now_date = new Date();
    var radius = Math.min(canvas1.width / 2, canvas1.height / 2),
        sec = now_date.getSeconds(),
        min = now_date.getMinutes(),
        hour = now_date.getHours();
    hour = hour >= 12 ? hour - 12 : hour;


    //canvas1
    //初始化画布
    context1.save();
    context1.clearRect(0, 0, canvas1.width, canvas1.height);
    context1.translate(canvas1.width / 2, canvas1.height / 2);
    context1.scale(1, 1);
    context1.rotate(-Math.PI / 2);
    context1.save();

    //画上时针
    context1.rotate((Math.PI / 6) * hour + (Math.PI / 360) * min + (Math.PI / 21600) * sec);
    context1.lineWidth = 2;
    context1.lineCap = "butt";
    context1.beginPath();
    context1.moveTo(0, 0);
    context1.lineTo(radius * 0.5, 0);
    context1.strokeStyle = "#00b2f8";
    context1.stroke();
    context1.restore();
    context1.save();

    //分针计算
    context1.rotate((Math.PI / 30) * min + (Math.PI / 1800) * sec);
    //分针颜色
    context1.strokeStyle = "#00b2f8";
    //分针粗细
    context1.lineWidth = 2;
    //向线条的每个末端添加圆形线帽。
    context1.lineCap = "round";
    context1.beginPath();
    //指针非指向端长度，第一个0
    context1.moveTo(0, 0);
    context1.lineTo(radius * 0.7, 0);
    context1.stroke();
    context1.restore();
    context1.save();

    context1.restore();
    context1.restore();


    //canvas2
//     new Date('1/01/2015 00:00:01')
    var now_date = new Date();
    var radius = Math.min(canvas2.width / 2, canvas2.height / 2),
        sec = now_date.getSeconds(),
        min = now_date.getMinutes(),
        hour = now_date.getHours();
    hour = hour >= 12 ? hour - 12 : hour;
    hour = hour - 1;
    //初始化画布
    context2.save();
    context2.clearRect(0, 0, canvas2.width, canvas2.height);
    context2.translate(canvas2.width / 2, canvas2.height / 2);
    context2.scale(1, 1);
    context2.rotate(-Math.PI / 2);
    context2.save();

    //画上时针
    context2.rotate((Math.PI / 6) * hour + (Math.PI / 360) * min + (Math.PI / 21600) * sec);
    context2.lineWidth = 2;
    context2.lineCap = "butt";
    context2.beginPath();
    context2.moveTo(0, 0);
    context2.lineTo(radius * 0.5, 0);
    context2.strokeStyle = "#00b2f8";
    context2.stroke();
    context2.restore();
    context2.save();

    //分针计算
    context2.rotate((Math.PI / 30) * min + (Math.PI / 1800) * sec);
    //分针颜色
    context2.strokeStyle = "#00b2f8";
    //分针粗细
    context2.lineWidth = 2;
    //向线条的每个末端添加圆形线帽。
    context2.lineCap = "round";
    context2.beginPath();
    //指针非指向端长度，第一个0
    context2.moveTo(0, 0);
    context2.lineTo(radius * 0.7, 0);
    context2.stroke();
    context2.restore();
    context2.save();

    context2.restore();
    context2.restore();
    //canvas3 //canvas1
    var now_date = new Date();
    var radius = Math.min(canvas2.width / 2, canvas2.height / 2),
        //秒
        sec = now_date.getSeconds(),
        //分
        min = now_date.getMinutes(),
        //小时
        hour = now_date.getHours();
    hour = hour >= 12 ? hour - 12 : hour;
    hour = hour - 7;
    //初始化画布
    context3.save();
    context3.clearRect(0, 0, canvas3.width, canvas3.height);
    context3.translate(canvas3.width / 2, canvas3.height / 2);
    context3.scale(1, 1);
    context3.rotate(-Math.PI / 2);
    context3.save();

    //画上时针
    context3.rotate((Math.PI / 6) * hour + (Math.PI / 360) * min + (Math.PI / 21600) * sec);
    context3.lineWidth = 2;
    context3.lineCap = "butt";
    context3.beginPath();
    context3.moveTo(0, 0);
    context3.lineTo(radius * 0.5, 0);
    context3.strokeStyle = "#00b2f8";
    context3.stroke();
    context3.restore();
    context3.save();

    //分针计算
    context3.rotate((Math.PI / 30) * min + (Math.PI / 1800) * sec);
    //分针颜色
    context3.strokeStyle = "#00b2f8";
    //分针粗细
    context3.lineWidth = 2;
    //向线条的每个末端添加圆形线帽。
    context3.lineCap = "round";
    context3.beginPath();
    //指针非指向端长度，第一个0
    context3.moveTo(0, 0);
    context3.lineTo(radius * 0.7, 0);
    context3.stroke();
    context3.restore();
    context3.save();

    context3.restore();
    context3.restore();
}
