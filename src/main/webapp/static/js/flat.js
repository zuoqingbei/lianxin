/**
 * Created by Administrator on 2017/4/15 0015.
 */
var bodyScale = parent.bodyScale;
var pageH = parent.pageH;
var pageW = parent.pageW;

var TIP_SETTIMEOUT_TIME = 2000;//静止显示的时间
var TIP_SETINTERVAL_TIME = 4000;//每个提示的周期时长
var stop = false;
var timeId = null;

//使提示框和其中文字大小随屏幕自动伸缩
function tipResize() {
    $("head").append("<style>" +
        "      .echart_tip_arrow > .echart_tip_line {" +
        "        height: " + 3.1 * bodyScale + "em;" +
        "        top: " + (-4 * bodyScale) + "em;" +
        "    }\n" +
        "    .dialog_title {\n" +
        "        font-size: " + bodyScale + "em;\n" +
        "    }"
        + "</style>")
}

$(function () {
    tipResize();
    //从地图提示框内直接打开实验室曲线
    $("body").on("click","#echartTips .echart_content>a",function () {
        var centerId = $(this).data("centerid");
        var $centerList = $('.lab .lab_content_l .switchBox>ul>li.noChildren,.lab .lab_content_l .switchBox>ul>li>ul>li', parent.document);
        $centerList.each(function(index,elem){
            if($(elem).data("centerid") === centerId){
                var $headerBtn = $(elem).parents(".switchBox").prev().find("ul>li");
                if($(elem).parents(".inland").length>0){
                    $headerBtn.eq(0).click();
                }else{
                    $headerBtn.eq(1).click();
                }
                $(elem).click();
                return false;
            }
        });

    });

});

function getGeoArr(data) {
    var geo = {};
    for (var i = 0; i < data.length; i++) {
        geo[data[i].name] = data[i].litude;
    }
    // console.log("geo"+geo);
    return geo;
};

/**
 * 程序被调用的入口——————————————————————————————————————————————————————
 * 平面世界地图数据准备
 * @param myChart
 * @param isFirst
 */

function createArrData(productCode, labType) {
    $.post(contextPath + "/lab/labShowFlatMapAjax", {
        "productCode": productCode,
        "labType": labType
    }, function (dataBase1) {
        mDataBase = jsonToArray(dataBase1);
        var option = {
            // backgroundColor: "rgba(255,0,0,0)",
            color: ['gold', 'aqua', 'lime'],
            calculable: false,
            tooltip: {
                show: true,
                showContent: true,
                enterable: true,
                trigger: 'item',
                //				        showDelay:100,
                hideDelay: 300,
                position: function (p) {
                    //                return [p[0] - 130, p[1] - 90];
                    return [p[0] + 100, p[1] + 100];
                },
                padding: [0, 0, 0, 0],
                //            width: 207,
                //            height: 110,
                //            backgroundColor: 'rgba(13,43,67,0.7)',
                //            borderColor: 'rgba(31,120,214,1)',
                // params : 数组内容同模板变量，
                formatter: function (param) {
                    // console.log("------------------param:",param)
                    /*                    var CountryName = param.name;
                                        var $l3x3 = $("#l", parent.document);
                                        $l3x3.siblings("#r,.labMain_content").hide().siblings(".labMain_content_country").show();
                                        $l3x3.find(".legend-bottom li").removeClass('active');
                                        parent.bgImgOff($l3x3.find(".legend-bottom li"));
                                        console.log("show",CountryName);

                                        switch (CountryName) {

                                            case '日本研发中心':
                                                window.parent.loadLabUnitInfoCenterTabAjaxWorld(0);
                                                break;
                                            case '新西兰研发中心':
                                                window.parent.loadLabUnitInfoCenterTabAjaxWorld(2);
                                                break;
                                            case '泰国模块中心':
                                                window.parent.loadLabUnitInfoCenterTabAjaxWorld(1);
                                                break;
                                            default:
                                            console.log("暂无该国家实验室信息")
                                        }
                    */
                    //在这里是第一步
                    $elList = [];
                    //提示框的内容清空
                    $echartTips.empty();
                    //初始化轮播，就是将轮播定时器停止
                    stopNewsShown();
                    //调用轮播方法，参数主要是弹出点坐标
                    var $el = addNewsElem(param.data);
                    return '';
                },
            },


            series: seriesData(mDataBase)
        }
        myFlatMap.clear();
        startNewsShown();
        myFlatMap.setOption(option);
        // myFlatMap.resize()
    })

}

function seriesData(data) {
    var seriesData = [];
    var item = {
        tooltip: {
            show: false
        },
        name: 'zy_hotpoint',
        type: 'map',
        roam: false,
        // hoverable: false,
        mapType: 'world',
        mapLocation: {
            x: 'right',
        },
        itemStyle: {
            normal: {
                borderColor: '#8DF0FF',
                borderWidth: 0.2 * bodyScale,
                areaStyle: {
                    color: 'rgba(20,143,204,0.6)'
                }
            }
        },
        data: [{
            name: "China",
            selected: true,
            itemStyle: {
                emphasis: {
                    areaStyle: {
                        color: '#00ffff'
                    }
                }
            }
        }, {
            name: "Japan",
            selected: true,
        },
            {
                name: "New Zealand",
                selected: true,
            },
            {
                name: "Thailand",
                selected: true,
            }
        ],
        markPoint: {
            symbol: 'emptyCircle',
            symbolSize: function (v) {
                return 3 * bodyScale;
            },
            effect: {
                show: true,
                type: 'scale',//圈圈
                loop: true,
                shadowBlur: 0
            },
            itemStyle: {
                normal: {label: {show: false}},
                emphasis: {label: {show: false}}
            },
            data: data
        },
        geoCoord: getGeoArr(data)

    };
    seriesData.push(item);

    item = {
        name: '',
        type: 'map',
        roam: false,
        // hoverable: false,
        mapType: 'world',
        mapLocation: {
            x: 0,
            // y: "top"
        },
        itemStyle: {
            normal: {
                borderColor: 'rgba(100,149,237,1)',
                borderWidth: 0.5 * bodyScale,
                areaStyle: {
                    color: '#1b1b1b'
                }
            }
        },
        data: [],
        markPoint: {
            symbol: 'emptyCircle',
            symbolSize: function (v) {
                return 6 * bodyScale;
            },
            effect: {
                show: true,
                type: 'scale',//圈圈
                loop: true,
                shadowBlur: 0,
                period:30
            },
            itemStyle: {
                normal: {label: {show: false}},
                emphasis: {label: {show: false}}
            },
            data: data
        },
        markLine: {
            smooth: true,
            tooltip: {
                show: false
            },
            effect: {
                show: true,
                scaleSize: 1,
                period: 5 * bodyScale,
                color: '#ff0',
                shadowBlur: 10 * bodyScale
            },
            itemStyle: {
                normal: {
                    color: "rgba(20,143,204,.7)",
                    borderWidth: 1 * bodyScale,
                    lineStyle: {
                        type: 'solid',
                        shadowBlur: 0
                    }
                }
            },
            data: dataToArrayContinueArray(data)
        },
    }

    seriesData.push(item);
    return seriesData;
}

// 基于准备好的dom，初始化echarts实例
var myFlatMap = echarts.init($('.mapFlat')[0]);
//调用父页面 获取数据
//window.parent.selectActLi();

// 处理点击事件打印国家名,这种写法有bug，即提示出来后那个圈圈就把可点击的国家位置堵在后面了，所以要给圈圈加点击事件。
/*myFlatMap.on('click', function (params) {
    var CountryName = params.name;
    var $l3x3 = $("#l", parent.document);
    $l3x3.siblings("#r,.labMain_content").hide().siblings(".labMain_content_country").show();
    $l3x3.find(".legend-bottom li").removeClass('active');
    parent.bgImgOff($l3x3.find(".legend-bottom li"));
    // console.log("点击",CountryName);
    switch (CountryName) {
        case 'Japan':
            // a.siblings("#r").hide();
            // a.parent().find(".labMain_content_country").show();
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(0);
            break;
        case 'New Zealand':
            // a.siblings("#r").hide();
            // a.parent().find(".labMain_content_country").show();
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(2);
            break;
        case 'Thailand':
            // a.siblings("#r").hide();
            // a.parent().find(".labMain_content_country").show();
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(1);
            break;
        default:
            console.log("暂无该国家实验室信息")
    }
});*/

/*平面地图上点击各个国家的点，右侧切换到对应的实验室页面*/
var $l3x3 = $("#l", parent.document);

/*
$("#echartTips").on("click", ".echart_tip_head", function () {
    var CountryName = $(this).parent().prev().find("a").attr("data-country");
    $l3x3.siblings("#r,.lab").hide().siblings(".labMain_content_country").show();
    $l3x3.find(".legend-bottom li").removeClass('active');
    parent.bgImgOff($l3x3.find(".legend-bottom li"));
    curvesSwitch(CountryName);
/!*
    switch (CountryName) {
        case ('日本'):
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(0);
            break;
        case ('新西兰'):
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(2);
            break;
        case ('泰国'):
            //window.parent.loadLabUnitInfoCenterTabAjaxWorld(1);
	        window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop(1,"thailand");
            break;
        default:
            console.log("暂无该国家实验室信息")
    }
*!/
    console.log("国家名:",CountryName)
});
*/
function curvesSwitch(CountryName) {
    switch (CountryName) {
        case ('日本'):
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(0);
            break;
        case ('新西兰'):
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(2);
            break;
        case ('泰国'):
            //window.parent.loadLabUnitInfoCenterTabAjaxWorld(1);
            window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop(1, "thailand");
            break;
        default:
            console.log("暂无该国家实验室信息")
    }
    console.log("国家名:", CountryName)
}

var showTopicIndex = 0;
/**
 * echartTips内的提示元素
 * @type {Array}
 */
var $elList = [];

/**
 * echart容器
 * @type {*|jQuery|HTMLElement}
 */
var $echart = $('.mapFlat');

/**
 * echart弹出新闻提示的容器
 * @type {*|jQuery|HTMLElement}
 */
var $echartTips = $('.echartTips');


function addNewsElem(news) {
    var $el = getTopicHtml(news);

    var divideLeft = $echart.width() / 2,
        divideTop = $echart.height() / 2;

    var left, top;
    // console.log("地点经纬度:" + news.litude);
    //getPosByGeo()：经纬度转成像素坐标
    // console.log("~~~~~~~~~~~~~~~~~~~~~news.litude:" + news.litude);
    var xypoint = [0, 0];
    if (news.litude) {//若不判断则在markLine上的提示会报错
        xypoint = myFlatMap.chart.map.getPosByGeo("world", news.litude); //坐标
    }

    left = xypoint[0];
    top = xypoint[1];


    $echartTips.append($el);

    $el.css({left: left, top: top});

    //分屏区域添加样式
    if (left > divideLeft) {
        $el.addClass('left');
    } else {
        $el.addClass('right');
    }

    if (top > divideTop) {
        $el.addClass('top');
    } else {
        $el.addClass('bottom');
    }

    //获取css中对应屏幕区域的相对偏移样式
    var $content = $el.children('.echart_content'),
        contentOffset = $content.offset();

    var offset = {
        left: contentOffset.left,
        top: contentOffset.top,
        height: $content.outerHeight(),
        width: $content.outerWidth()
    };

    if (false) {
        $el.remove();
    } else {
        $el.data('offset', offset);
        $elList.push($el);
        fadeOutElList();
        startNewsShown();
        $el.hover(function () {//鼠标移动上去后暂停
            fadeInElList([$el]);
            stopNewsShown($el);
        }, function () {
            fadeOutElList();
            startNewsShown();
        });
        return $el;
        $el.click(function () {
            alert()
        })
    }
    return null;
}

/**
 * 每格一定时间显示的资讯及新闻
 */
function showNews() {
    if (document.hidden) {
        return;
    }
    $elList = [];
    //循环显示器
    showTopicIndex = addEl(mDataBase, showTopicIndex);
    fadeOutElList();

    function addEl(list, index) {
        if (list.length === 0) {
            return;
        }
        for (var num = 0; num < 1; num++) {
            var $el = addNewsElem(list[index]);
            //console.log(index,num);
            if ($el) {
                if ((++index) >= list.length) {
                    return 0;
                }
            } else {
                return index;
            }
        }
        return index;
    }
}

function startNewsShown() {

    if (timeId === null) {
        // console.log("---自动提示启动---")
        //每个提示的周期长度
        timeId = setInterval(showNews, TIP_SETINTERVAL_TIME);
    }
}

function listenAnimationEnd($el, fn) {
    //transitionend是css3完成过渡后触发的事件,下一步将被删除
    $el.on('transitionend', function () {
        $el.off('transitionend');
        fn.call();
    })
}

/**
 * 初始化资讯及新闻轮循
 */
function stopNewsShown($el) {
    clearInterval(timeId);
    timeId = null;
    // $el.css("")

    // animation-play-state: paused
}

/**
 * 拼提示框的标签
 * */
function getTopicHtml(currentPoint) {
    // console.log("currentPoint",currentPoint)
    var city = "";
    if (currentPoint.name) {
        city = currentPoint.name;
    }
    var country = currentPoint.country;
    var value = currentPoint.value;
    var time = currentPoint.dateTime;
    var title = currentPoint.title;
    var id = currentPoint.id;
    var url = "";
    var centerId = currentPoint.centerId;
    var imgUrl = currentPoint.imgUrl;
    /*if((value=="0"&&title=="青岛")||title==undefined){
    	return "";
    }*/
    return $('<div class="echart_tip">' +
        '<div class="dialog_title echart_content">' +
        '   <img src="'+ imgUrl +'" alt="实验室图片">' +
        '   <div class="textBox">'  +
            '   <h4 style="">' + title + '</h4>' +
            '   <div>实验室数量：' + value + '</div>' +
            '   <a data-centerId="'+centerId+'" href="javascript:void(0);">进入实验室 →</a>' +
        '   </div>' +
        '</div>' +
        '<div class="echart_tip_arrow">' +
        '   <div class="echart_tip_line"></div>' +
        '   <div class="echart_tip_head"></div>' +
        '</div>' +
        '</div>');
}

/**
 * 渐隐 elList的每一项
 */
function fadeOutElList() {
    $.each($elList, function (i, $el) {
        // console.log("---delay3000前")
        setTimeout(function () {
            $el.removeClass('fade_in').addClass('fade_out');
            listenAnimationEnd($el, function () {
                $el.remove();
            });
        }, TIP_SETTIMEOUT_TIME)
        // console.log("---delay3000后")
    })


    $elList = [];
}

/**
 * 渐入 elList
 */
function fadeInElList($list) {
    // console.log("---fadeInElList")
    $.each($list, function (i, $el) {
        $el.off('transitionend');
        $el.removeClass('fade_out').addClass('fade_in');
    });
    $elList = $list;
}
