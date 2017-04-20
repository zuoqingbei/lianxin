/**
 * Created by Administrator on 2017/4/15 0015.
 */

var timeId = null;

function getGeoArr(data) {
    var geo = {};
    for (var i = 0; i < data.length; i++) {
        geo[data[i].name] = data[i].litude;
    }
    // console.log("geo"+geo);
    return geo;
}
var mDataBase;
var productCode,labType;
var legendData1 ;
var legendData2;
var legendDataCode1 ;
var legendDataCode2;
var selectedData;
/*productCode=24;
labType=4;*/
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('mapFlat'));
createArrData(myChart,true);



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
var $echart = $('#mapFlat');

/**
 * echart弹出新闻提示的容器
 * @type {*|jQuery|HTMLElement}
 */
var $echartTips = $('#echartTips');

/**
 * 提示渐隐时间
 * @type {number}
 */
var TIP_SETTIMEOUT_TIME = 3000;

function addNewsElem(news) {
    var $el = getTopicHtml(news);

    var divideLeft = $echart.width() / 2,
        divideTop = $echart.height() / 2;

    var left, top;
    // console.log("地点经纬度:" + news.litude);
    //getPosByGeo()：经纬度转成像素坐标
    var xypoint = myChart.chart.map.getPosByGeo("world", news.litude); //坐标
    // console.log("像素坐标————" + xypoint);
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
        $el.hover(function () {
            fadeInElList([$el]);
            stopNewsShown();
        }, function () {
            fadeOutElList();
            startNewsShown();
        });
        return $el;
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
        console.log("---start---")
        timeId = setInterval(showNews, 3000);
    }
}
function listenAnimationEnd($el, fn) {
    //transitionend是css3完成过渡后触发的事件
    $el.on('transitionend', function () {
        $el.off('transitionend');
        fn.call();
    })
}
/**
 * 初始化资讯及新闻轮循
 */
function stopNewsShown() {
    clearInterval(timeId);
    timeId = null;
}

function getTopicHtml(currentPoint) {
    var city = currentPoint.name;
    var value = currentPoint.value;
    var time = currentPoint.dateTime;
    var title = currentPoint.title;
    var id = currentPoint.id;
    var url = "/YIJIAN/article?artid=";
    return $('<div class="echart_tip">' +
        '<div class="dialog_title echart_content">' +
        '<a title="' + title + '"  href="' + url + id + '" target="_blank" >' +
        '<span style="color:#ffffff;font-size:16px;text-shadow:2px 2px 2px rgba(0,0,0,0.9);">' + title + '</span>' +
        '</a>' + city + '&nbsp;&nbsp' + time +
        '</div>' +
        '<div class="echart_tip_arrow">' +
        '<div class="echart_tip_line"></div>' +
        '<div class="echart_tip_head"></div>' +
        '</div>' +
        '</div>');
}
/**
 * 渐隐 elList的每一项
 */
function fadeOutElList() {
    $.each($elList, function (i, $el) {
        $el.removeClass('fade_in').addClass('fade_out');
        listenAnimationEnd($el, function () {
            $el.remove();
        });
    });

    $elList = [];
}
/**
 * 渐入 elList
 */
function fadeInElList($list) {
    $.each($list, function (i, $el) {
        $el.off('transitionend');
        $el.removeClass('fade_out').addClass('fade_in');
    });
    $elList = $list;
}

$(window).resize(function () {
    myChart.resize();
});