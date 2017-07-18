/**
 * Created by Administrator on 2017/4/7 0007.
 */
var $left = $("#l");
//进入时的视频淡出效果，开发时注掉下面这些代码
function videoFadeOut() {
    $("body").prepend('<div id="mask" style="background-color: black">' +
        '<video src="' + contextPath + '/static/img/movieHead_4480.mp4"  width="' + pageW + '" height="100%" preload="auto" >抱歉，您的浏览器不支持video标签</video>' +
        '</div>'
    );
    var $video = $('video');
    $video.click(function () {
        $(this)[0].play()
    });
    $video[0].addEventListener('ended', function () {
        $(this).parent().animate({
            opacity: 0
        }, 3000, function () {
            $(this).hide();
        })
    });
}

//切换地图显示区域及地图全屏
function switchMapArea() {

    var iframe = '<iframe id="iframe" class="iframe map" scrolling="no" frameborder="0" src="' + contextPath + '/lab/flatMap" ></iframe>';
    $(".l").find(".btnGroup img").click(function () {
        var src = $(this).attr("src");

        if (src.indexOf("off") >= 0) {
            src = src.replace("off", "on");
            $(this).attr("src", src)
                .parent().siblings(".oneBtn").find("img")
                .attr("src", $(this).parent().siblings().find("img").attr("src").replace("on", "off"))
            if ($(this).parents(".l").find(".switch.sphere").is(":hidden")) {
                $(this).parents(".l").find(".switch.sphere").css({"display": "flex"}).siblings().hide();

            } else {
                $(this).parents(".l").find(".flat .mapArea iframe").remove();
                $(this).parents(".l").find(".switch.flat").css("display", "flex").find(".mapArea").append(iframe).parent().parent()
                    .siblings().hide();
            }
        }
    });
    $(".left3x3 .btnGroup  img.fullScreen").click(function () {
        $(".fullScreen_map").animate({
            left: 0
        },1000).show();
        $(".left3x3,#r,.labMain_content").hide();
    })
}

//切换地球和平面地图按钮的提示
function switchMapBtnTip() {
    $(".btnGroup img").hover(function () {
        $(this).siblings().show()
    }, function () {
        $(this).siblings().hide()
    })
}
//重置echart图标大小 在加载平面地图时被调用
function resetSize() {
    for (var i = 0; i < myCharts.length; i++) {
        myCharts[i].resize();
    }
    /* if(getSelectLab()){
     document.getElementById('iframe').contentWindow.createArrData(productCode,labType);
     }*/
    // document.getElementById('iframe').contentWindow.createArrData(productCode, labType);
    for (var k = 0; k < $("#iframe.map").length; k++) {
        // console.log("------$('.iframe.map'):" + $(".iframe.map").length);
        $("#iframe.map").eq(k)[0].contentWindow.createArrData(productCode, labType);
    }

}
//切换生产线和实验室的列表显示
function navLabLine() {
    //切换平面地图下面导航栏内实验室和生产线
    $(".l").find(".legend .navSwitch").click(function () {
        var bgImg = $(this).css("background-image");
        if (bgImg.indexOf("off") > 0) {
            var text = $(this).text();
            bgImg = bgImg.replace("off", "on");
            $(this).css({
                color: "#00e673",
                backgroundImage: bgImg
            }).siblings(".navSwitch").css({
                color: "#6cf",
                backgroundImage: bgImg.replace("on", "off")
            });
            if (text.indexOf("产线") >= 0) {

                $(".legend .animateBox").animate({top: "-1.8em"}, "slow")
                    .find(".line").addClass("current").siblings().removeClass("current");
            } else {
                // $(".legend ul.lab").css("top","15px");
                $(".legend .animateBox").animate({top: "0"}, "slow")
                    .find(".lab").addClass("current").siblings().removeClass("current");
            }
        }
    });
}
//让元素的背景图标点亮
function bgImgOn(e) {
    var bgImg = e.find("span").css("background-image");
    e.find("span").css("background-image", bgImg.replace("off", "on"));
    return e;
}
//让元素的背景图标熄灭
function bgImgOff(e) {
    var bgImg = e.find("span").css("background-image");
    e.find("span").css("background-image", bgImg.replace("on", "off"));
    return e;
}
var selectAllBtn = $(".l").find(".legend .selectAll label");
//导航栏中的全选
function navSelectAll() {
    selectAllBtn.click(function () {
        // var bgImg = $(this).find("span").css("background-image");
        var $actLi = $(this).parents(".l").find(".legend .labLine ul.current").find("li");
/*        if ($(this).next("input[type=checkbox]").is(":checked")) {
            $(this).css("color", "#999");
            bgImgOff($(this));
            $actLi.removeClass("active").each(function () {
                bgImgOff($(this))
            })
        } else {*/
            $(this).css("color", "#6cf");
            bgImgOn($(this));
            $actLi.addClass("active").each(function () {
                bgImgOn($(this))
            });
        // }
        selectActLi($(this));
    })
}
//选取被激活li元素下面的值
function selectActLi($this) {
    getSelectLab($this);
    reloadData(productCode, labType);
}
//获取实验室类别 产线选择类型
function getSelectLab($this) {
    //实验室类别
    var changed = true;
    var mlabType = "";
    var mproductCode = "";
    var $actLi = $this.parents(".l").find(".legend .labLine .lab").find("li.active");
    $actLi.each(function () {
        mlabType += $(this).attr("code") + ","
    });
    //产线
    var $actLiPro = $this.parents(".l").find(".legend .labLine .line").find("li.active");
    console.log("------$actLiPro:" + $actLiPro.length)
    $actLiPro.each(function () {
        mproductCode += $(this).attr("code") + ","
    });
    $(this).parents = mproductCode.substr(0, mproductCode.length - 1);
    mlabType = mlabType.substr(0, mlabType.length - 1);
    console.log("------------mproductCode,mlabType:",mproductCode,mlabType);
    if (mlabType == labType && mproductCode == productCode) {
        changed = false;
    }
    labType = mlabType;
    productCode = mproductCode;
    return changed;
}
//重置数据
function reloadData(productCode, labType) {
    // console.log(labType+"----"+productCode)
    //平面地图数据mapFlat
    //console.log(echarts.init(document.getElementById('iframe').contentWindow.document.getElementById("mapFlat")))
    //document.getElementById('iframe').contentWindow.say()
    for (var k = 0; k < $("#iframe.map").length; k++) {

        $("#iframe.map").eq(k)[0].contentWindow.createArrData(productCode, labType);
    }
    reloadLeftData();
}
//点击a元素时
function navSelectA() {//这里会触发地图中要加载的数据
    $(".l").find(".legend ul li a").click(function () {
        var bgImg = $(this).find("span").css("background-image");
        var $selectLi = $(this).parent();
        $selectLi.addClass("active");
        bgImgOn($selectLi);
        $selectLi.siblings().removeClass("active").each(function () {
            bgImgOff($(this))
        });
        //关闭全选状态
        bgImgOff($(this).parents(".legend").find(".selectAll label"));
        $(this).parents(".legend").find(".selectAll label").css("color", "#999");
        //如果是生产线和实验室
        if ($(this).parents(".labLine").length > 0) {
            selectActLi($(this));
        }
    });
    //点击中海博睿切换右边页面上
    var $flatBottomSwitchLi = $("#l.left3x3").find(".flat-footer ul.legend-bottom li");
    $flatBottomSwitchLi.find("a").click(function () {
        if($(this).text() ==="中海博睿"){
            $("#r").hide();
            $(".labMain_content").show()
            $(".labMain_content_country").hide()
        }
    })
}
//地球右上角区域的数字样式
function sphereRTnumberShow(n) {
    for (var k = 0; k < $(".sphere-right-top .chartBorder ul").length; k++) {
        // console.log("~~~~~~~~~~~~~~~~~~~",$(".sphere-right-top .chartBorder ul").length,n)
        for (var j = 0; j < n.length; j++) {


            var $flatLTnumber = $(".sphere-right-top .chartBorder ul").eq(k).find("li").eq(j).find(".number");
            var str = n[j] + "";
            var newStr = "";
            for (var i = 0; i < 4; i++) {
                if (i < 4 - str.length) {
                    newStr += "0";
                } else {
                    newStr += '<span style="color: #fff;">' + str + '</span>';
                    $flatLTnumber.html(newStr);
                    break;
                }

            }
        }
        /*
         var $flatLTnumber = $(".sphere-right-top .chartBorder ul li").eq(j).find(".number");
         console.log("--------------------",$flatLTnumber.length,n);
         var str = n[j] + "";
         var newStr = "";
         for (var i = 0; i < 4; i++) {
         if (i < 4 - str.length) {
         newStr += "0";
         } else {
         newStr += '<span style="color: #fff;">' + str + '</span>';
         // console.log(newStr);
         $flatLTnumber.html(newStr);
         break;
         }
         }
         */
    }
}

//球形地图右下角的广告滚动
function sphereRBscroll() {
    var speed = 100;
    // scroll($(".fullScreen_map .scroll"),$(".fullScreen_map .scroll ul:first"),$(".fullScreen_map .scroll ul:last"),20);
    scroll($(".left3x3 .scroll"),$(".left3x3 .scroll ul:first"),$(".left3x3 .scroll ul:last"),50);

    // console.log("--------------",$(".left3x3 .scroll").length)
    function scroll($scrollBoard,$ul1,$ul2,speed) {
        $scrollBoard.css("height", $scrollBoard.width);
        $ul2.html($ul1.html());
        function Marquee() {
            //scrollTop:溢出上边界的高度
            //offsetHeight:元素包括border和padding的高度
            //$scrollBoard这个高度一定要小，且不能用百分比
            if ($ul2[0].offsetHeight <= $scrollBoard[0].scrollTop)
                $scrollBoard[0].scrollTop -= $ul2[0].offsetHeight;
            else {
                $scrollBoard[0].scrollTop++;
            }
        }

        var MyMar = setInterval(Marquee, speed);
        $scrollBoard.hover(function () {
            clearInterval(MyMar)
        }, function () {
            MyMar = setInterval(Marquee, speed);
        })
    }
}

$(function () {
    //进入时的视频淡出效果，开发时注掉下面这一行和被调用的代码
    // videoFadeOut();

    //调整字符云页面的文字大小
    // document.getElementById("wordCloud").contentWindow.resizeText(bodyScale);
    // wordCloud.window.resizeText(bodyScale);

    //切换地图显示区域
    switchMapArea();
    //切换地球和平面地图按钮的提示
    switchMapBtnTip();
    //切换生产线和实验室的列表显示
    navLabLine();
    //导航栏中的全选
    navSelectAll();
    //点击a元素时
    navSelectA();
    //球形地图右下角的广告滚动
    sphereRBscroll();

    // $(".fullScreen_map .sphere-left-bottom iframe")[0].contentWindow.run(300);
    // $("#iframe.map").eq(k)[0].contentWindow.createArrData(productCode, labType);


});
