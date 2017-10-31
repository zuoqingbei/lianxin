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

function loadingAnimate($videoParent) {
    $videoParent.find(".videoWait").text("视频接入中 ").fadeIn(500, function () {
        var t = setTimeout(loadingOut, 8000);
        var loop;
        changeTxt();

        function loadingOut() {
            $videoParent.find(".videoWait").fadeOut(3000, clearTimeout(loop));
        }

        var counter = 0

        function changeTxt() {
            if (counter === 3) {
                counter = 0;
                $videoParent.find(".videoWait").text("视频接入中 ");
            }else{
                counter++;
                $videoParent.find(".videoWait").append("。")
            }
            loop = setTimeout(changeTxt, 1000);

        }
    });

}

// 视频加载方法
function videoShow(id, url, mainStream) {
    //mainStream 0-主码流，1-子码流


    // window.clearTimeout(t1);//去掉定时器

    // console.log("videoShow加载中。。。");
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
    var $videoParent = $("#" + id).parent();
    // console.log("$videoParent0",$videoParent[0])
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
        attrs,
        function () {
            loadingAnimate($videoParent);
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
        $(".monitoring.world").find(".bigVideoBox").html("<div id=\"bigVideoWorld\"></div>" +
            "<div class=\"videoWait\">正在连接到实时监控。。。</div>");
        $(".monitoring.world").find(".smallVideoBox").html("<div class=\"hideShow\"><span class=\"text\">点击隐藏</span><span class=\"icon\"></span></div>\n" +
            "                                    <div id=\"smallVideoWorld\"></div>" +
            "<div class=\"videoWait\">正在连接到实时监控。。。</div>");
        $(".monitoring.webSocket").find(".bigVideoBox").html("<div id=\"bigVideoWS\"></div>" +
            "<div class=\"videoWait\">正在连接到实时监控。。。</div>");
        $(".monitoring.webSocket").find(".smallVideoBox:eq(0)").html("<div class=\"hideShow\"><span class=\"text\">点击隐藏</span><span class=\"icon\"></span></div>\n" +
            "                                    <div id=\"smallVideoWS\"></div>" +
            "<div class=\"videoWait\">正在连接到实时监控。。。</div>");

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
            if (prevIsLabUrl) {
                $(this).parents(".monitoring").find(".shishi_right>.item").eq(2).show().siblings().hide();
            }
            echartsResizeWorld();
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

    var smallVideoID = ""; //画中画id，放在外面是因为每次加载视频会替换原来指定id的标签
    var bigVideoID = ""; //大视频id，放在外面是因为每次加载视频会替换原来指定id的标签
    var prevTaiwei = null;

    //台位点击事件
    $(".sheshi_tab_list").on("click", "ul>li>ul>li>ul>li", function () {
        echartsResizeWorld();
        var $taiweiList = $(".sheshi_tab_list>ul>li>ul>li>ul>li");
        $taiweiList.removeClass("active");
        $(".sheshi_tab_list>ul>li.toLabIframe.active").removeClass("active");
        $(this).addClass("active");

        // $(".sheshi_tab.sheshi_tab_lines").click();//自动切换到曲线显示
        //实时监测的画中画视频切换
        if (!$(this).parent().is(prevTaiwei)) {//如果不是同一个实验室下的台位
            // console.log("不同实验室")
            prevTaiwei = $(this).parent();
            var labCode = $(this).parent().prev().text();//暂时从这里获取，以后拼到属性中
            labCode = labCode.slice(0, labCode.length - 1);

            var $parentMonitoring = $(this).parents(".monitoring");
            if ($parentMonitoring.find(".smallVideoBox").children("object").length === 0) {//还没有被加载过视频
                smallVideoID = $parentMonitoring.find("div[id^=smallVideo]").attr("id");
                bigVideoID = $parentMonitoring.find(".bigVideoBox").children().attr("id");
                console.log("还没有被加载过视频：", smallVideoID, bigVideoID);
            } else {//加载过视频后重置id
                $parentMonitoring.find(".smallVideoBox").append("<div id='" + smallVideoID + "'></div>").children("object").remove();
                $parentMonitoring.find(".bigVideoBox").append("<div id='" + bigVideoID + "'></div>").children("object").remove();
                console.log("重置id盒子：", smallVideoID, bigVideoID);
            }

            $.post(contextPath + "/lab/loadTopVideoByLabCodeAjax/?labCode=" + labCode, function (data) {
                if (data) {
                    var videoUrlMain = data.videl_url.replace("/1/live.m3u8", "/0/live.m3u8");//切换成主码流
                    var videoUrlSub = videoUrlMain.replace("/0/live.m3u8", "/1/live.m3u8");//切换成子码流
                    console.log("-------data.videl_url", data.videl_url, "smallVideoID", smallVideoID, "bigVideoID", bigVideoID);
                    videoShow(smallVideoID, videoUrlSub, 1);
                    videoShow(bigVideoID, videoUrlMain, 0);
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
