var htmlSetViewAll = "";
var htmlSetDetail = "";
$(function () {
    var debug = true;
    if (debug) {
        var deviceLoadArr  = [
            {name: "检测中心"},
            {name: "冰冷产线"},
            {name: "检测中心3"},
            {name: "检测中心4"},
            {name: "检测中心5"},
            {name: "检测中心6"},
            {name: "检测中心7"},
            {name: "检测中心8"}
        ];
        var deviceDetailLoadArr = [
            {name: "检测中心"},
            {name: "冰冷产线"},
            {name: "检测中心3"},
            {name: "检测中心4"},
            {name: "检测中心5"},
            {name: "检测中心6"},
            {name: "检测中心7"},
            {name: "检测中心8"}
        ];
        for (var i = 0; i < 8; i++) {
            deviceLoadArr[i].value = (Math.random() * 10 + 90).toFixed(1) + "%";
            deviceLoadArr[i].YoY = (Math.random() + 1).toFixed(1) + "%";
            deviceLoadArr[i].sign = Math.random() - 0.3;

            deviceDetailLoadArr[i].type = "整机";
            deviceDetailLoadArr[i].deviceName = "贵州冰箱噪音室";
            deviceDetailLoadArr[i].value = (Math.random() * 20 + 60).toFixed(1) + "%";;

        }
        console.log("-----------deviceLoadArr", deviceLoadArr);
        console.log("-----------deviceDetailLoadArr", deviceDetailLoadArr)
    }
    var arr = [1,2,3,4,5]
    var arr2 = arr.concat()
    arr2[2] = 5
    console.log("~~~~~~~~~~~~",arr)
    console.log("~~~~~~~~~~~~",arr2)
    //设置设备负荷率
    setViewAll(deviceLoadArr);
    setDeviceDetail(deviceDetailLoadArr);
    var $cover = $("#cover");
    $cover.find(".top-3").click(function () {
        $(".deviceLoad").show().siblings().hide();
        loadData(htmlSetViewAll);
    });
    $cover.find(".deviceLoad>.btn-back").click(function () {
        $(".statusCover").show().siblings().hide()
    });


});

/**
 * 设置设备负荷率
 * @param {object} deviceLoadArr 用于遍历的对象
 *                 .name    产线名称
 *                 .value   当前值
 *                 .YoY     同比
 *                 .sign    上升还是下降
 */
function setViewAll(deviceLoadArr) {
    deviceLoadArr.forEach(function (item, index, array) {
        var sign = Number(item.sign) > 0 ? "up" : "down";
        htmlSetViewAll += '<tr><td class="name">'
            + item.name + '</td><td class="barBox"><span class="bar"><span class="fill"></span></span></td><td class="value">'
            + item.value + '</td><td class="YoY '
            +sign+ '">同比：<span>'
            + item.YoY + '</span></td> </tr>\n'
    });
    loadData(htmlSetViewAll);

}

/**
 * 设置设备负荷率低于80%的设备明细
 * @param {object} deviceDetailLoadArr 用于遍历的对象
 *                 .name    产线名称
 *                 .type    类型
 *                 .deviceName  设备名称
 *                 .value   当前值
 */
function setDeviceDetail(deviceDetailLoadArr) {
    deviceDetailLoadArr.forEach(function (item, index, array) {
        var sign = Number(item.sign) > 0 ? "up" : "down";
        htmlSetDetail += '<tr><td class="name">'
            + item.name + '</td><td class="type">'
            + item.type + '</td><td class="deviceName">'
            +item.deviceName+ '</td><td class="value">'
            + item.value + '</td> </tr>\n'
    });
    $(".detail").find("table").html(htmlSetDetail);
}

/**
 * 拼接表格内容
 * @param html 拼接后的表格内容
 */
function loadData(html) {
    $(".viewAll").find("tbody").html(html)
        .find(".fill").css("width", function () {
        return $(this).parent().parent().next().text();
    });
}