/**
 * Created by Administrator on 2017/4/7 0007.
 */

$(function () {
	
    // 切换地图球形和平面的显示
    var txt = $("#code-source").text();
    var displaySphere;//当前状态

    if (localStorage.mapDisplay === "sphere") {
        txt = txt.replace("flat: false", "flat: true");
        displaySphere = true;
    } else {
        txt = txt.replace("flat: true", "flat: false");
        displaySphere = false;
    }
    $("#code-source").text(txt);

    $("#switch").click(function () {/*点击事件*/
        if(displaySphere){/*localStorage不能直接存储布尔值！！！*/
            localStorage.mapDisplay = "flat";
        }else{
            localStorage.mapDisplay = "sphere";
        }
        document.location.reload();
    });


    require(['start'],function (start) {
        // alert(start.test(5));
        $("#bigger").click(function () {
            start.zoom1(0,0,0,100,100)
        })
        $("#switch").click(function () {
            // start.showFlat()
        })
        $("#echarts2").click(function () {

        })
    });


})
