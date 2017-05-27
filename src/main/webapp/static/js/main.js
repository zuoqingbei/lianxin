/**
 * Created by Administrator on 2017/4/7 0007.
 */


$(function () {

    //进入时的首页效果
/*
    $("#mask").click(function () {
        $(this).animate({"opacity":0},1000,function () {
            $(this).hide();
        })
    });
*/

    var $left = $("#left");
    var iframe = '<iframe id="iframe" scrolling="no" frameborder="0" src="'+contextPath+'/lab/flatMap" ></iframe>';
    //切换echarts-x和chearts的显示

    //切换echarts-x和chearts的显示(单页面内嵌div)
    $left.find(".btnGroup img").click(function () {
        var src = $(this).attr("src");
        if (src.indexOf("off") >= 0) {
            src = src.replace("off", "on");
            $(this).attr("src", src)
                .siblings().attr("src", $(this).siblings().attr("src").replace("on", "off"))
            var $chart = $("#chart");
            if ($left.find(".switch.sphere").is(":hidden")) {
                $left.find(".switch.sphere").show().siblings().hide()
            } else {
                $left.find(".flat .mapArea iframe").remove();
                $left.find(".switch.flat").show().find(".mapArea").append(iframe).parent()
                    .siblings().hide()
            }
        }
    })

})
