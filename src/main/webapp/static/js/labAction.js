var $lab_content_r = $(".lab_content_r");//国内外右边的大框
var $monitoring = $lab_content_r.find(".monitoring");//曲线监控页
var $sheshi_tab_list = $monitoring.find(".sheshi_tab_list");//曲线左侧的台位列表
var $prevTaiwei = null;
var videoUrlMain = "";
var prevIsLabUrl = false;//主菜单的URL类型
var loadingAnimateVideoLoop = null;
var loadingAnimateCurveLoop = null;

function inlandTabShow(mark) { //国内
    if (mark === "zhonghaiborui") {
        $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_1.png)");
    } else {
        $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_4.png)");
    }
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.curves").show();
}

function abroadTabShow() {//国外
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardForeign_2.png)");
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.curves").show();
}

function moduleMakersShow(url) {//模块商
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoard_onlyOne.png");
    $(".labSubNav>ul>li").hide().eq(4).addClass("active").show().siblings().removeClass("active").hide();
    $lab_content_r.find(".switchBox>div.item.moduleMakers").children("iframe").attr("src", url).parent().show().siblings().hide();
    console.log("url", url)
}

//视频加载动画
function loadingAnimate($thisElem, text, time) {
    // console.log("thisElem---",thisElem[0]);
    $thisElem.fadeIn(200, function () {
        // var loadingAnimateLoop;
        var counter = 0;
        if (time) {
            clearTimeout(loadingAnimateVideoLoop);
            clearTimeout(loadingAnimateFadeOut);
            // console.log("```loadingAnimateFadeOut")
            var loadingAnimateFadeOut = setTimeout(function (loadingAnimateFadeOut) {
                // console.log("自动loadingAnimateFadeOut");
                $thisElem.fadeOut(3000, function () {
                    //如果把清除定时器直接放在回调函数的位置，则不会起作用
                    clearTimeout(loadingAnimateVideoLoop);
                    clearTimeout(loadingAnimateFadeOut);
                });
                // console.log("`````loadingAnimateFadeOut")

            }, time);
        } else {
            clearTimeout(loadingAnimateCurveLoop);
        }
        changeTxt();


        function changeTxt() {
            // clearTimeout(loadingAnimateLoop);
            if (counter === 3) {
                counter = 0;
                $thisElem.text(text);
            } else {
                counter++;
                var point = " ";
                for (var i = 0; i < counter; i++) {
                    point += "。";
                }
                $thisElem.text(text + point);
            }
            if (time) {
                loadingAnimateVideoLoop = setTimeout(changeTxt, 1500);
            } else {
                loadingAnimateCurveLoop = setTimeout(changeTxt, 1000);
            }
            // console.log("changeTxt()");

        }
    });

}

//视频加载动画淡出
function loadingAnimateOut(type, time) {
    // console.log("调用loadingAnimateOut");
    if (type === "curve") {
        // console.log("曲线调用loadingAnimateOut");
        $(".item.curve>.sheshi_right_left>.loadingAnimation").fadeOut(time, function () {
            clearTimeout(loadingAnimateCurveLoop);
        })
    } else {
        // console.log("非曲线调用loadingAnimateOut")
    }
}

// 视频加载方法
function videoShow(id, url, mainStream) {
    //mainStream 0-主码流，1-子码流
    var flashvars = {
            src: escape(url + "?time=" + new Date()),

            plugin_m3u8: "../static/asserts/video/HLSProviderOSMF.swf",
            autoPlay:
                "true",
            autoSwitchQuality:
                "true"
        }
    ;
    var params = {
        allowFullScreen: true,
        allowScriptAccess: "always",
        quality: "low",
        bgcolor: "#000000"
    };
    var attrs = {
        name: "player"
    };

    var $videoParent = $("#" + id).parent();
    swfobject.embedSWF(
        // url to SMP player
        "../static/asserts/video/StrobeMediaPlayback.swf?time=New Date()",
        // div id where player will be place
        id,
        // width, height
        // 根据主子码流选择尺寸比例
        "100%",
        mainStream ? "80%" : "96%",
        // minimum flash player version required
        "27",
        // other parameters
        null,
        flashvars,
        params,
        attrs,
        function () {
            loadingAnimate($videoParent.find(".loadingAnimation"), "视频接入中", 8000);
        }
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
    //中心实验室顶上的“返回总状态”按钮
    $(".btn-totalStatus").click(function () {
        $("#r").show().siblings(".lab").hide();
        resetSizeRight();
    });

    //国内外切换
    var $navHeadLi = $(".labMainNav>header>ul>li");
    $navHeadLi.click(function () { //这里需要按钮，主菜单，子菜单，内容4部分都有变化
        $navHeadLi.removeClass("active");
        $(this).addClass("active");
        $(".labMainNav>.switchBox>ul:eq(" + $(this).index() + ")").show().siblings().hide();
        $(".labSubNav>ul>li").hide().eq(3).addClass("active").siblings().removeClass("active");

        if ($(this).index() === 0) { //国内
            inlandTabShow();
        } else {                    //国外
            abroadTabShow();
        }
        $(".lab_content_r>.switchBox>div.item").eq(3).show().siblings().hide();
    });

    //左侧菜单数据中心点击事件
    $(".labMainNav>.switchBox").on("click", "ul>li.noChildren, ul>li>ul>li", function () {
        prevIsLabUrl = false;

        $(".labMainNav>.switchBox ul>li").removeClass("active");
        $(this).addClass("active");

        if ($(this).data("urltype")) {//url类型
            var moduleMakersUrl = dataCenterMap.get($(this).data("urltype") + '').souce_value;
            moduleMakersShow(moduleMakersUrl);
        } else {
            // console.log("---非URL数据中心");
            $(".labSubNav>ul>li").hide().eq(3).addClass("active").siblings().removeClass("active");
            $lab_content_r.find(".switchBox>div.item.monitoring").show().siblings().hide();
            var dataCenterId = $(this).data("centerid");
            if ($(this).parents("ul.inland")[0]) {//从url类型跳回到国内其他列表
                if (dataCenterId === 1) {
                    // console.log("---中海")
                    $(".labSubNav>ul>li").hide().eq(0).addClass("active").siblings().removeClass("active");
                    $lab_content_r.find(".switchBox>div.item.labHome").show().siblings().hide();
                    inlandTabShow("zhonghaiborui");
                } else {
                    // console.log("---国内非中海")
                    inlandTabShow();
                    //只依靠台位来切换曲线不行，万一读不出来台位就一直显示体验馆，而且之前如果显示视频也不会自动隐藏
                    var $curveBox = $monitoring.find(".shishi_right").children(".item.curve");
                    if ($curveBox.is(":hidden")) {//曲线没有显示
                        $curveBox.show().siblings().hide();
                    }
                    $(".smallVideoBox").hide();
                    labCurveResize();
                }
            } else {
                // console.log("---国外")
                abroadTabShow();
            }
            //获取数据中心的视频列表
            loadVideosByDataCenterAjax(dataCenterId);
        }
    });

    //菜单的折叠与展开
    $(".switchBox").on("click", "ul>li>header", function () {
        if ($(this).next().is(":visible")) {
            // console.log("ul:visible")
            $(this).children("span").text("∨").parent().next("ul").hide(200);
        } else {
            $(this).children("span").text("∧").parent().next("ul").show(200);
            if ($(this).next("ul").length > 0) {
                $(this).next("ul").children("li:eq(0)").click();
            }
        }
        $(this).parent().siblings().children("ul").hide()
            .prev("header").children("span").text("∨");
    });

    //頂部子菜单点击事件
    var subNavLi = $(".labSubNav>ul>li");
    subNavLi.click(function () {
        subNavLi.removeClass("active");
        $(this).addClass("active");
        var borderUrl = $lab_content_r.css("background-image");

        //边框变换
        if ($(".labMainNav>header>ul>li:eq(0)").hasClass("active")) {//国内
            if ($(this).index() < 4) {
                borderUrl = borderUrl.replace(/_[1-4]/, "_" + ($(this).index() + 1));
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
            labCurveResize();
        }
    });

    //实时监测-视频监控按钮
    $(".sheshi_tab.centerVideo").click(function () {
        if ($(this).hasClass("disabled")) {
            return;
        }
        $(this).addClass("sheshi_tab_active").siblings().removeClass("sheshi_tab_active");
        if($(this).next().is(":hidden")){
            $(this).next().show().siblings(".sheshi_tab_list").hide();
            $(this).next().find("ul>li:first").click();
        }
    });
    //实时监测-视频地址选择
    $(".centerVideoList>ul").on("click","li",function () {
        $(this).addClass("active").siblings().removeClass("active");
       var videoUrl = $(this).data("videourl").replace("/1/live.m3u8", "/0/live.m3u8");
       console.log("videoUrl",videoUrl);
       // videoShow("bigVideo",videoUrl,0);
        $("#bigVideo").children("iframe").attr("src",videoUrl);
       $(".shishi_right>.item.video").show().siblings().hide();
    });
    //实时监测-实时数据按钮
    $(".sheshi_tab.sheshi_tab_lines").click(function () {
        $(this).addClass("sheshi_tab_active").siblings().removeClass("sheshi_tab_active");
        if($(this).next().is(":hidden")){
            $(this).next().show().siblings(".centerVideoList").hide();
        }
        if(prevIsLabUrl){
            $(".shishi_right").children(".item.iframe").show().siblings().hide();
        }else{
            $(".shishi_right").children(".item.curve").show().siblings().hide();

        }
    });
    //链接型实验室点击和画中画切换
    $sheshi_tab_list.on("click", ".toLabIframe>header", function () {
        prevIsLabUrl = true;
        $(".sheshi_tab_list>ul>li>ul>li>ul>li").removeClass("active");
        $(this).addClass("active");
        var webUrl = $(this).parent().data("url");
        // console.log("webUrl", webUrl);
        $monitoring.find(".shishi_right>.item.iframe").children("iframe").attr("src", webUrl)
            .parent().show().siblings().hide();

        var labCode = $(this).attr("labcode");
        videoUrlAjax(labCode, "toUrl")
        window.clearInterval(intevalChart1);
    	window.clearInterval(intevalChartHadoop);
    });

    //台位点击事件
    $sheshi_tab_list.on("click", "ul>li>ul>li>ul>li", function () {
        prevIsLabUrl = false;
        var $curveBox = $monitoring.find(".shishi_right").children(".item.curve");
        if ($curveBox.is(":hidden")) {//曲线没有显示
            $curveBox.show().siblings().hide();
        }
        labCurveResize();
        $(".sheshi_tab_list>ul>li>ul>li>ul>li").removeClass("active");
        $(".sheshi_tab_list>ul>li>ul>li.toLabIframe>header").removeClass("active");
        $(this).addClass("active");
        //自动切换到曲线显示
        // $(".sheshi_tab.sheshi_tab_lines").click();
        //实时监测的画中画视频切换
        if (!$(this).parent().is($prevTaiwei)) {//如果不是同一个实验室下的台位
            $prevTaiwei = $(this).parent();
            var labCode = $(this).parent().prev().attr("labcode");//获取實驗室編碼
            var $parentMonitoring = $(this).parents(".monitoring");
            videoUrlAjax(labCode);
        }
    });
    //画中画的视频隐藏显示
    $(".smallVideoBox").on("click", ".hideShow", function () {
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

    //获取小视频地址的ajax
    function videoUrlAjax(labCode, toUrl) {
        $.post(contextPath + "/lab/loadTopVideoByLabCodeAjax/?labCode=" + labCode, function (data) {
            var videoUrl = data.videl_url;
            // console.log("---labCode", labCode, "videoUrl:", videoUrl);
            if (videoUrl) {
                videoUrlMain = videoUrl.replace("/1/live.m3u8", "/0/live.m3u8");//切换成主码流
                var videoUrlSub = videoUrlMain.replace("/0/live.m3u8", "/1/live.m3u8");//切换成子码流
                $(".smallVideoBox").show();
                $(".sheshi_tab:eq(0)").removeClass("disabled");
                if (toUrl) {
                    // videoShow("smallVideoWeb", videoUrlSub, 1);
                    $("#smallVideoWeb").children("iframe").attr("src",videoUrl)
                } else {
                    // videoShow("smallVideo", videoUrlSub, 1);
                    $("#smallVideo").children("iframe").attr("src",videoUrl)
                }
            } else {
                // $(".sheshi_tab:eq(0)").addClass("disabled");
                $(".smallVideoBox").hide();
            }
        })
    }

    // 数据分析中的合格率、及时率、满意度
    initThree();//合格率
    initfour();//及时率
    inittwo();//满意度

    labAllForCenterLabAjax();
});
