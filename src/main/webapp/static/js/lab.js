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
    "http://10.130.96.113:6713/mag/hls/26667e63e1234554b2295388be3ac813/0/live.m3u8?time=New Date()",//本机
    "http://192.168.1.168:6713/mag/hls/9d5be58b608c48fc8e71d09509b89ba9/0/live.m3u8?time=New Date()",//本机
    "http://192.168.1.168:6713/mag/hls/e99850d9e8fa40c88dd87bc184cd432a/1/live.m3u8?time=New Date()",//室外北侧
    "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()"//CCTV
];
var videoUlrAbroad = [
    "http://192.168.1.168:6713/mag/hls/b055403a5a464b69bebc4b670bfdff60/1/live.m3u8?time=New Date()",//泰国"RF-B"
    "http://192.168.1.168:6713/mag/hls/4c00e3f243a5464798a54d6fdd57cc82/1/live.m3u8?time=New Date()",//泰国"IPdome"
    "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()",//CCTV
    "http://192.168.1.168:6713/mag/hls/9d5be58b608c48fc8e71d09509b89ba9/0/live.m3u8?time=New Date()"//本机
];

$(function () {

    //中心实验室顶上的“返回总状态”和“中海博睿实验室”两个按钮
    $(".btn-totalStatus").click(function () {
        $("#r").show().siblings(".lab").hide();

        resetSizeRight();
    });

    $(".btn-labHome").click(function () {
        var borderUrl = $lab_content_r.css("background-image").replace(/[2-5]/, "1");
        $lab_content_r.css("background-image", borderUrl);
        $(".lab_content_r>.switchBox>div.item:eq(0)").show().siblings().hide();
    });

    //国内外切换
    var $navHeadLi = $(".labMainNav>header>ul>li");
    var $lab_content_r = $(".lab_content_r");
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

    function inlandTabShow() {
        var borderUrl = $lab_content_r.css("background-image").replace("labTabBoardForeign", "labTabBoardInland");
        labTabBoard(borderUrl);
        $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.centerCurves").show();
    }

    function abroadTabShow() {
        var borderUrl = $lab_content_r.css("background-image").replace("labTabBoardInland", "labTabBoardForeign");
        labTabBoard(borderUrl);
        $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.abroadCurves").show();
    }

    function moduleMakersTabShow() {
        var borderUrl = $lab_content_r.css("background-image").replace("labTabBoardInland", "labTabBoardForeign");
        labTabBoard(borderUrl);
        $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.moduleMakers").show();
    }

    function labTabBoard(borderUrl) {
        $(".labSubNav>ul>li").hide();
        borderUrl = borderUrl.replace(/[2-5]/, "1");
        $lab_content_r.css("background-image", borderUrl);
        $(".lab_content_r>.switchBox>div.item").eq(0).show().siblings().hide();
    }

    //左侧菜单点击事件
    var $li = $(".labMainNav>.switchBox>ul>li.noChildren, .labMainNav>.switchBox>ul>li>ul>li");
    $li.click(function () {
        $li.removeClass("active");
        $(this).addClass("active");
        var CountryName = "";
        if ($(this).text().indexOf("泰国") > -1) {
            window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop(1, "thailand");
        } else if ($(this).text().indexOf("日本") > -1) {
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(0);
        } else if ($(this).text().indexOf("新西兰") > -1) {
            window.parent.loadLabUnitInfoCenterTabAjaxWorld(2);
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

    //二级菜单的折叠与展开
    var $NavHeader = $(".labMainNav>.switchBox>ul>li>header");
    $NavHeader.click(function () {
        if ($(this).next("ul").is(":visible")) {
            $(this).next("ul").hide();
            $(this).removeClass("fold").children("span").text("﹀");
        } else {
            $(this).next("ul").show();
            $(this).addClass("fold").children("span").text("︿");
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

        var borderUrl = $lab_content_r.css("background-image").replace(/[1-4]/, $(this).index() + 1);
        if (( $navHeadLi.eq(1).hasClass("active") && $(this).index() === 4 ) || $(this).index() === 5) { //国外的曲线监控或模块商的互联页面
            borderUrl = $lab_content_r.css("background-image").replace(/[1-4]/, 2);
        }
        $lab_content_r.css("background-image", borderUrl);

        $(".lab_content_r>.switchBox>div.item").eq($(this).index()).show().siblings().hide();
        echartsResize();

        if ($(this).text().indexOf("实时监测") > -1) {
            echartsResizeWorld();
            if($(this).hasClass("centerCurves")){
                videoShow("smallVideoInland",videoUlrInland[0]);
            }else {
                videoShow("smallVideoAbroad",videoUlrAbroad[1]);
            }
        }
    });

    //实时监测中的视频和曲线切换
    $(".sheshi_tab").click(function () {
        var index = $(this).index();
        $(this).addClass("sheshi_tab_active").siblings().removeClass("sheshi_tab_active");
        $(this).parents(".monitoring").find(".shishi_right>.item").eq(index).show().siblings().hide();

    });

    function videoLayout(videoUlr,from) {
        var $monitoring = $(".monitoring");
        var $layoutBox = (from === "inland" ? $monitoring.eq(0).find(" .video_box > div"):$monitoring.eq(1).find(" .video_box > div"));
        var n = videoUlr.length;
        console.log("videoUlr.length", n);
        if (n < 2) {
            $layoutBox.removeClass().addClass("video_1x1");
        } else if (n < 5) {
            $layoutBox.removeClass().addClass("video_2x2");
        } else if (n < 10) {
            $layoutBox.removeClass().addClass("video_3x3");
        } else {
            $layoutBox.removeClass().addClass("video_4x4");
        }
        var videoHtml = "";
        for (var i = 1; i <= n; i++) {
            // videoHtml += '<div><div id="video' + i + from + '"></div></div>';
            videoHtml += '<div style="cursor: pointer"><div id="video' + i + from + '"><h1 style="font-size: 3em;">'+i+'</h1></div></div>';
            // videoShow("video"+i+from,videoUlr[i-1]);
        }
        $layoutBox.eq(0).html(videoHtml);
    }

    //另一种方式，点击图片再加载视频
    $(".video_box > div").on("dblclick","div",function () {
        $(this).parent().hide().siblings().show();
        videoShow("floatVideo",videoUlrInland[$(this).index()]);
    })
    $("#floatVideoBox").click(function () {
        $(this).parent().hide().siblings().show();
    })
    function videoShow(id,url) {
        var flashvars = {
            src: escape(url),
            plugin_m3u8: "../static/asserts/video/HLSProviderOSMF.swf",
            autoPlay : "true",
            autoSwitchQuality : "true",
        };
        var params = {
            // self-explained parameters
            allowFullScreen: true,
            allowScriptAccess: "always",
            quality:"low",
            bgcolor: "#000000"
        };
        var attrs = {
            name: "player"
        };
        swfobject.embedSWF(
            // url to SMP player
            "../static/asserts/video/StrobeMediaPlayback.swf",
            // div id where player will be place
            id,
            // width, height
            "100%", "100%",
            // minimum flash player version required
            "27",
            // other parameters
            null,
            flashvars,
            params,
            attrs
        )
        // loadSwf(id,flashvars,params,attrs);

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
    function loadSwf(id,flashvars,params,attrs) {
        swfobject.embedSWF(
            // url to SMP player
            "../static/asserts/video/StrobeMediaPlayback.swf",
            // div id where player will be place
            id,
            // width, height
            "100%", "100%",
            // minimum flash player version required
            "27",
            // other parameters
            null,
            flashvars,
            params,
            attrs
        )
    }

    //画中画的视频隐藏显示
    $(".smallVideoBox>.hideShow").click(function () {
        var $video = $(this).next();
        if($video.is(":visible")){
            $video.hide();
            $(this).children(".text").text("显示小视频");
            $(this).children(".icon").css("background-image","url(../static/img/lab/browse.png)")
        }else{
            $video.show();
            $(this).children(".text").text("点击隐藏");
            $(this).children(".icon").css("background-image","url(../static/img/lab/close.png)")
        }
    });


    videoLayout(videoUlrInland,"inland");
    videoLayout(videoUlrAbroad,"abroad");

    // 数据分析中的合格率、及时率、满意度
    initThree();//合格率
    initfour();//及时率
    inittwo();//满意度

    labAllForCenterLabAjax();


})