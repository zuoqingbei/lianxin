//导航选中切换

function Map() {
    var struct = function (key, value) {
        this.key = key;
        this.value = value;
    }
    var put = function (key, value) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                this.arr[i].value = value;
                return;
            }
        }
        this.arr[this.arr.length] = new struct(key, value);
    }
    var get = function (key) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                return this.arr[i].value;
            }
        }
        return null;
    }
    var remove = function (key) {
        var v;
        for (var i = 0; i < this.arr.length; i++) {
            v = this.arr.pop();
            if (v.key === key) {
                continue;
            }
            this.arr.unshift(v);
        }
    }
    var size = function () {
        return this.arr.length;
    }
    var isEmpty = function () {
        return this.arr.length <= 0;
    }
    this.arr = new Array();
    this.get = get;
    this.put = put;
    this.remove = remove;
    this.size = size;
    this.isEmpty = isEmpty;
}

function echartsResize() {
    chartone.resize();
    charttwo.resize();
    chartthree.resize();
    chartfour.resize();
    chartfive.resize();
    chartsix.resize();
    myChart1.resize();
    myChart2.resize();
}

var videoUlrInland = [
    // "http://192.168.1.168:6713/mag/hls/9d5be58b608c48fc8e71d09509b89ba9/1/live.m3u8?time=New Date()"//本机
    "http://10.130.96.65:6713/mag/hls/3e158a568dd84c2890d095a25517f78b/1/live.m3u8?time=New Date()",//体验馆2
    // "http://10.130.96.113:6713/mag/hls/85d598e47ce4411c9196e965385e895d/0/live.m3u8?time=New Date()",//本机
    // "http://192.168.1.168:6713/mag/hls/e99850d9e8fa40c88dd87bc184cd432a/1/live.m3u8?time=New Date()",//室外北侧
    // "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()"//CCTV
];
var videoUlrAbroad = [
    // "http://10.130.96.113:6713/mag/hls/7329e487e5c84c41a1ba9040e89f7814/1/live.m3u8?time=New Date()",//泰国"RF-B"
    "http://10.130.96.65:6713/mag/hls/4c00e3f243a5464798a54d6fdd57cc82/1/live.m3u8?time=New Date()",//泰国"IPdome"
    // "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()",//CCTV
    // "http://192.168.1.168:6713/mag/hls/9d5be58b608c48fc8e71d09509b89ba9/0/live.m3u8?time=New Date()"//本机
];
var dataCenterMap = new Map();

//查询全部数据中心数据（包含层级关系）
function loadAllDataCenterAjax() {
    $.post(contextPath + "/lab/loadAllDataCenterAjax", {}, function (data) {
        // console.log(data)
        $(".inland").html(createDataCenterHtml(data, 0));
        $(".abroad").html(createDataCenterHtml(data, 1));
        //alert(dataCenterMap.get("1"));
    });
}

function createDataCenterHtml(data, dataType) {
    //dataType:0：国内 1：国外
    var htmls = "";
    var cuNum = 0;
    $.each(data, function (index, item) {
        if (item.data_type == dataType) {
            var haschildren = false;
            if (item.haschildren == 1) {
                haschildren = true;
            }
            // console.log("item",item)
            dataCenterMap.put(item.id, item);
            if (!haschildren) {
                htmls += '<li data-centerid="' + item.id + '" ';
                if (cuNum == 0) {
                    htmls += ' class=" noChildren active" ';
                } else {
                    htmls += ' class=" noChildren " ';
                }
                ;
                cuNum++;
                //创建li响应方法
                htmls += createClickFuntionForDataCenter(item);
                htmls += ' >' + item.center_name + '</li>';
            } else {
                htmls += '<li '
                if (cuNum == 0) {
                    htmls += ' class=" hasChildren active" >';
                } else {
                    htmls += ' class=" hasChildren " >';
                }
                ;
                cuNum++;
                // htmls+=' <header class="fold">'+item.center_name+'<span>︿</span></header> ';
                htmls += ' <header class="fold">' + item.center_name + '<span>∧</span></header> ';
                htmls += '<ul>';
                dataCenterMap.put(item.id, item);
                $.each(item.children, function (index, cItem) {
                    dataCenterMap.put(cItem.id, cItem);
                    htmls += '<li data-centerid="' + cItem.id + '" ';
                    htmls += createClickFuntionForDataCenter(cItem) + " >" + cItem.center_name + '</li>';
                });
                htmls += '</ul></li>';
            }

        }
    });
    return htmls;

}

//创建数据中心li响应方法
function createClickFuntionForDataCenter(item) {
    var htmls = "";
    var dataSource = item.data_source;
    //如果是在数据中心配置的为url则直接跳转，否则根据数据中心再去查询对应实验室level为3(单位/产品) 4（模块/整机）
    if (dataSource == "url") {
        htmls += " onclick= intentsUrl('" + item.id + "')";
    } else {
        htmls += " onclick= loadAllDataCenterLabAjaxFunc('" + item.id + "') ";
    }
    return htmls;
}

function setCenterLabHtmlDB(dataCenter) {
    $(".labMain_cblt_tone_world").html("<p style:'font-size:1.3em'>" + dataCenter.center_desc + "</p>");
    $(".labMain_cblt_ttwo_world img").attr("src", dataCenter.img_content);
    $("#labName_world").html(dataCenter.center_name);
    $("#labnameIcon_world").html(dataCenter.center_name);
    $("#secondName_world").html(dataCenter.center_name);
}

//查询数据中心下实验室 level为3(单位/产品) 4（模块/整机）
function loadAllDataCenterLabAjaxFunc(dataCenterId) {
    var dataCenter = dataCenterMap.get(dataCenterId);
    var data_type = dataCenter.data_type;
    var data_source = dataCenter.data_source;
    if (data_type == 0) {
        //alert(data_source+"--"+data_type+"--"+dataCenter.center_name)
        if (data_source == "db" || data_source == "json") {
            setCenterLabHtmlDB(dataCenter);
            inlandTabShow_world();
        } else if (data_source == "webservice") {
            inlandTabShow();
        }
    } else {
        //alert(data_source+"--"+data_type+"--"+dataCenter.center_name)
        if (data_source == "db" || data_source == "json") {
            setCenterLabHtmlDB(dataCenter);
            abroadTabShow();
        } else if (data_source == "webservice") {
            abroadTabShow_center();
        }
    }
    $.post(contextPath + "/lab/loadAllDataCenterLabAjax", {"dataCenterId": dataCenterId}, function (data) {
        var html = '';
        $.each(data, function (index, item) {
            var dataSource = item.data_source;
            dataCenterMap.put(item.id, item);
            console.log(item.isshow_name)
            if (dataSource == "url") {
                html += '<li  data-center-id="' + item.id + '"  class="toLabIframe quxian_li_' + item.id + '" data-url="' + item.souce_value + '"><header ' + createClickFuntion(item) + '>' + (item.isshow_name == 0 ? item.center_name : "") + '</header></li>';
            } else {
                html += '<li class="quxian_li_' + item.id + '" data-center-id="' + item.id + '"  ><header ' + createClickFuntion(item) + '>' + (item.isshow_name == 0 ? item.center_name : "") + '</header></li>';
            }
        });
        $("#lab_unit_selected_center").html(html);
        $("#lab_unit_selected_center_world").html(html);
        /*
                $(".sheshi_tab_list>ul>li.toLabIframe").on("click", function () {
                    $(this).addClass("active");
                    $(this).parents(".monitoring").find(".shishi_right>.item.iframe").show().siblings().hide();
                    // videoShow("smallVideoInlandWeb", $(this).attr("data-url"));
                    // videoShow("videoBoxInland", $(this).attr("data-url"));
                    $("#lab_iframe_video").show();
                    $("#lab_iframe_video").attr("src", $(this).attr("data-url"))
                });
        */
        $.each(data, function (index, item) {
            if (index == 0) {
                var dataSource = item.data_source;
                if (dataSource === "db") { //国外曲线
                    window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop(item.id, item.souce_value, item.data_type)
                } else if (dataSource === "webservice") {
                    //中海博睿
                    loadLabUnitInfoCenterTabAjax(item.data_type);
                } else if (dataSource === "json") {
                    //新西兰 日本读取json文件 国外曲线
                    window.parent.loadLabUnitInfoCenterTabAjaxWorld(item.id, item.data_type);
                } else if (dataSource === "url") {
                    $("#lab_unit_selected_center").find("li").eq(0).find("header").trigger("click");
                    $("#lab_unit_selected_center_world").find("li").find("header").eq(0).trigger("click");
                }
                // $("#lab_unit_selected_center_world").find("li").eq(0).find("header").eq(0).trigger("click");
            }
        });
        //自动触发第一个实验室的第一个台位

    });

}

//创建li响应方法
function createClickFuntion(item) {
    var htmls = "";
    /**
     * 数据源 db-直连数据库； url-第三方链接；
     webservice-连接webservice；json-读取json文件
     */
        // console.log("item",item)
    var dataSource = item.data_source;
    if (dataSource == "db") { //国外曲线
        htmls += " onclick= loadLabUnitInfoCenterTabAjaxWorldHadoop('" + item.id + "','" + item.souce_value + "','" + item.data_type + "')"
    } else if (dataSource == "webservice") {
        //中海博睿
        htmls += " onclick=loadLabUnitInfoCenterTabAjax('" + item.id + "','" + item.data_type + "') ";
    } else if (dataSource == "json") {
        //新西兰 日本读取json文件 国外曲线
        htmls += " onclick= window.parent.loadLabUnitInfoCenterTabAjaxWorld('" + item.id + "','" + item.data_type + "') ";
    }
    /*else if (dataSource == "url") {
           htmls += " onclick= intentsUrl('" + item.id + "')";
       }*/
    return htmls;
}

//直接跳转第三方地址
function intentsUrl(type, thiselem) {
    var $mainNavLi = $(".labMainNav>.switchBox>ul>li.noChildren, .labMainNav>.switchBox>ul>li>ul>li");
    $mainNavLi.removeClass("active");
    $(thiselem).addClass("active");
    moduleMakersTabShow();
    var dataCenter = dataCenterMap.get(type);
    var url = dataCenter.souce_value;
    $(".lab_content_r>.switchBox>div.item").eq(5).find("iframe").attr("src", url);
    console.log("url", url)
}

var $lab_content_r = $(".lab_content_r");
var borderUrl = "";

function inlandTabShow() { //国内webservice
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.centerCurves").show();
}

function inlandTabShow_world() {//国内Hadoop
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.abroadCurves").show();
}

function abroadTabShow() {//国外Hadoop
    $(".lab_content_r").css("background-image", "url(../static/img/lab/labTabBoardForeign_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.abroadCurves").show();
}

function abroadTabShow_center() {//国外数据中心
    $(".lab_content_r").css("background-image", "url(../static/img/lab/labTabBoardForeign_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.centerCurves").show();
}

function moduleMakersTabShow() { //模块商
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoard_onlyOne.png");
    labNavAndItemShow("moduleMakers");
    $(".labSubNav>ul>li.moduleMakers").show();
}

function labNavAndItemShow(mark) {
    $(".labSubNav>ul>li").hide();
    if (mark === "moduleMakers") {
        $(".lab_content_r>.switchBox>div.item").eq(5).show().siblings().hide();
    } else {
        $(".lab_content_r>.switchBox>div.item").eq(0).show().siblings().hide();

    }
}

// 视频加载方法
function videoShow(id, url, mainStream) {
    //mainStream 0-主码流，1-子码流

    console.log("videoShow加载中。。。")
    var flashvars = {
        src: escape(url),
        plugin_m3u8: "../static/asserts/video/HLSProviderOSMF.swf",
        autoPlay: "true",
        autoSwitchQuality: "true"
    };
    var params = {
        // self-explained parameters
        allowFullScreen: true,
        allowScriptAccess: "always",
        quality: "low",
        bgcolor: "#000000"
    };
    var attrs = {
        name: "player"
    };
    loadSwf(id, flashvars, params, attrs, mainStream);

}

function loadSwf(id, flashvars, params, attrs, mainStream) {
    swfobject.embedSWF(
        // url to SMP player
        "../static/asserts/video/StrobeMediaPlayback.swf?time=New Date()",
        // div id where player will be place
        id,
        // width, height
        // 根据主子码流选择尺寸比例
        mainStream ? "56%" : "100%",
        mainStream ? "80%" : "96%",
        // minimum flash player version required
        "27",
        // other parameters
        null,
        flashvars,
        params,
        attrs
    )
    /*    swfobject.embedSWF()的五个必须参数和四个可选参数：
    swfUrl（String，必须的）指定SWF的URL。
    id（String，必须的）指定将会被Flash内容替换的HTML元素（包含你的替换内容）的id。
    width（String，必须的）指定SWF的宽。
    height（String，必须的）指定SWF的高。
    version（String，必须的）指定你发布的SWF对应的Flash Player版本（格式为：major.minor.release）。
    expressInstallSwfurl（String，可选的）指定express install SWF的URL并激活Adobe express install [ http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=6a253b75 ]。
    flashvars（String，可选的）用name:value对指定你的flashvars。
    params（String，可选的）用name:value对指定你的嵌套object元素的params。
    attributes（String，可选的）用name:value对指定object的属性。
     */
}


$(function () {
    loadAllDataCenterAjax();
    //中心实验室顶上的“返回总状态”和“中海博睿实验室”两个按钮
    $(".btn-totalStatus").click(function () {
        $("#r").show().siblings(".lab").hide();

        resetSizeRight();
    });

    //国内外切换
    var $navHeadLi = $(".labMainNav>header>ul>li");
    $navHeadLi.click(function () {
        $navHeadLi.removeClass("active");
        $(this).addClass("active");
        $(".labMainNav>.switchBox>ul:eq(" + $(this).index() + ")").show().siblings().hide();
        var borderUrl = "";
        if ($(this).index() === 0) { //国内
            inlandTabShow();
        } else {                    //国外
            abroadTabShow();
        }
    });

    //左侧菜单点击事件
    $(".labMainNav>.switchBox").on("click", "ul>li.noChildren, ul>li>ul>li", function () {
        prevIsLabUrl = false;

        //初始化视频盒子
        $(".monitoring.world").find(".bigVideoBox").html("<div id=\"bigVideoWorld\"></div>");
        $(".monitoring.world").find(".smallVideoBox").html("<div class=\"hideShow\"><span class=\"text\">点击隐藏</span><span class=\"icon\"></span></div>\n" +
            "                                    <div id=\"smallVideoWorld\"></div>");
        $(".monitoring.webSocket").find(".bigVideoBox").html("<div id=\"bigVideoWS\"></div>");
        $(".monitoring.webSocket").find(".smallVideoBox:eq(0)").html("<div class=\"hideShow\"><span class=\"text\">点击隐藏</span><span class=\"icon\"></span></div>\n" +
            "                                    <div id=\"smallVideoWS\"></div>");

        var $li = $(".switchBox>ul>li.noChildren, .switchBox>ul>li>ul>li");
        $li.removeClass("active");
        $(this).addClass("active");
        var $thisElem = $(this);
        if ($thisElem.attr("onclick").indexOf("intentsUrl") > -1) {
            $(".labSubNav>ul>li:last").addClass("active").siblings().removeClass("active");
        } else {
            $(".labSubNav>ul>li:first").addClass("active").siblings().removeClass("active");
        }
    });

    //非模块商的列表
    var $inlandLiNoModuleMakers = $(".labMainNav>.switchBox>ul>li.noChildren:not(.moduleMakers), .labMainNav>.switchBox>ul>li>ul>li");
    $inlandLiNoModuleMakers.click(function () {
        if ($(this).parents(".inland").length > 0) {
            inlandTabShow();
        } else {
            abroadTabShow();
        }
    });

    //主菜单二级菜单的折叠与展开
    $(".switchBox").on("click", "ul>li>header", function () {

        if ($(this).next().is(":visible")) {
            $(this).next("ul").hide();
            $(this).removeClass("fold").children("span").text("∨");
        } else {
            $(this).next("ul").show();
            $(this).addClass("fold").children("span").text("∧");
        }
    });

    //模块商
    var $moduleMakerLi = $(".labMainNav>.switchBox>ul>li.moduleMakersInland,.labMainNav>.switchBox>ul>li.moduleMakersAbroad");
    $moduleMakerLi.click(function () {
        moduleMakersTabShow();
    });

    //頂部子菜单点击事件
    var subNavLi = $(".labSubNav>ul>li");
    subNavLi.click(function () {
        subNavLi.removeClass("active");
        $(this).addClass("active");
        var borderUrl = $lab_content_r.css("background-image");

        //边框变换
        if ($(".labMainNav>header>ul>li:eq(0)").hasClass("active")) {//国内
            if ($(this).index() < 3) {
                borderUrl = borderUrl.replace(/_[1-4]/, "_" + ($(this).index() + 1));
            } else {
                borderUrl = borderUrl.replace(/_[1-4]/, "_4");
            }
        } else {//国外
            if ($(this).index() < 1) {
                borderUrl = borderUrl.replace(/_[1-2]/, "_1");
            } else {
                borderUrl = borderUrl.replace(/_[1-2]/, "_2");
            }
        }
        $lab_content_r.css("background-image", borderUrl);
        $(".lab_content_r>.switchBox>div.item").eq($(this).index()).show().siblings().hide();
        echartsResize();

        if ($(this).text().indexOf("实时监测") > -1) {
            echartsResizeWorld();
        }
    });

    var prevIsLabUrl = false;
    //实时监测中的视频和曲线切换
    $(".sheshi_tab").click(function () {
        var index = $(this).index();
        $(this).addClass("sheshi_tab_active").siblings().removeClass("sheshi_tab_active");
        $(this).parents(".monitoring").find(".shishi_right>.item").eq(index).show().siblings().hide();

        if (index === 0) {//大视频页
            $(".smallVideoBox").hide(500);
            $(".bigVideoBox").show(500);
        } else {//小视频页
            $(".bigVideoBox").hide(500);
            $(".smallVideoBox").show(500);
            if(prevIsLabUrl){
                $(this).parents(".monitoring").find(".shishi_right>.item").eq(2).show().siblings().hide();
            }
        }
        echartsResizeWorld();
    });

    //画中画的视频隐藏显示
    $(".smallVideoBox").on("click",".hideShow",function () {
        var $video = $(this).next();
        if ($video.is(":visible")) {
            $video.hide();
            $(this).children(".text").text("点击显示监控");
            $(this).children(".icon").css("background-image", "url(../static/img/lab/browse.png)")
        } else {
            $video.show();
            $(this).children(".text").text("点击隐藏");
            $(this).children(".icon").css("background-image", "url(../static/img/lab/close.png)")
        }
    });

    var videoID = ""; //画中画id，放在外面是因为每次加载视频会替换原来指定id的标签
    var videoIDBig = ""; //大视频id，放在外面是因为每次加载视频会替换原来指定id的标签
    var prevTaiwei = null;

    //台位点击事件
    $(".sheshi_tab_list").on("click", "ul>li>ul>li>ul>li", function () {
        echartsResizeWorld();
        var $taiweiList = $(".sheshi_tab_list>ul>li>ul>li>ul>li");
        $taiweiList.removeClass("active");
        $(".sheshi_tab_list>ul>li.toLabIframe.active").removeClass("active");
        $(this).addClass("active");

        //实时监测的画中画视频切换
        if (!$(this).parent().is(prevTaiwei)) {//如果不是同一个实验室下的台位
            prevTaiwei = $(this).parent();
            var labCode = $(this).parent().prev().text();//暂时从这里获取，以后拼到属性中
            labCode = labCode.slice(0, labCode.length - 1);

            var $parentMonitoring = $(this).parents(".monitoring");
            if ($parentMonitoring.find(".smallVideoBox").children("object").length === 0) {//还没有被加载过视频
                videoID = $parentMonitoring.find("div[id^=smallVideo]").attr("id");
                videoIDBig = $parentMonitoring.find(".bigVideoBox").children().attr("id");
                console.log("还没有被加载过视频：", videoID, videoIDBig);
            } else {//加载过视频后重置id
                $parentMonitoring.find(".smallVideoBox").append("<div id='" + videoID + "'></div>").children("object").remove();
                $parentMonitoring.find(".bigVideoBox").append("<div id='" + videoIDBig + "'></div>").children("object").remove();
                console.log("重置id盒子：", videoID, videoIDBig);
            }

            $.post(contextPath + "/lab/loadTopVideoByLabCodeAjax/?labCode=" + labCode, function (data) {
                if (data) {
                    var videoUrlMain = data.videl_url.replace("/1/live.m3u8", "/0/live.m3u8");//切换成主码流
                    var videoUrlSub = videoUrlMain.replace("/0/live.m3u8", "/1/live.m3u8");//切换成子码流
                    console.log("data.videl_url", data.videl_url, "videoID", videoID, "videoIDBig", videoIDBig);
                    videoShow(videoID, videoUrlSub, 1);
                    videoShow(videoIDBig, videoUrlMain, 0);
                }
            })
        }

    });
    //链接型实验室点击和画中画切换
    $(".sheshi_tab_list").on("click", ".toLabIframe>header", function () {
        prevIsLabUrl = true;
        $(".sheshi_tab_list>ul>li>ul>li>ul>li").removeClass("active");
        $(this).addClass("active");
        $(this).parents(".monitoring").find(".shishi_right>.item.iframe").show().siblings().hide();
        $(this).parents(".monitoring").find(".shishi_right>.item.iframe")
            .find(".smallVideoBox").children("div:eq(1)").attr("id", "smallVideoWSWeb");
        var webUrl = $(this).parent().data("url");
        $(this).parents(".monitoring").find(".shishi_right>.item.iframe>iframe").attr("src", webUrl);
        videoShow("smallVideoWSWeb", videoUlrInland[0].replace("/0/live.m3u8", "/1/live.m3u8"), 1);
        videoShow("bigVideoWS", videoUlrInland[0].replace("/1/live.m3u8", "/0/live.m3u8"), 0)
    })
    // 数据分析中的合格率、及时率、满意度
    initThree();//合格率
    initfour();//及时率
    inittwo();//满意度

    labAllForCenterLabAjax();
})
