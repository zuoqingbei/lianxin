var htmlSetViewAll = "";
var htmlSetDetail = "";
var labTypeCode;//实验室类型
var debug = false;

/*六个指标的绑数函数在tab2.js的equipmentTotalForLab1Ajax()里面*/
equipmentStatisForPlForLab2Ajax(2);

$(function () {


    if (debug) {
        var deviceLoadArr = [
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

        }
        // console.log("-----------deviceLoadArr", deviceLoadArr);
    }
    //设置设备负荷率
    var $cover = $("#cover");
    $cover.find(".top-3").click(function () {
        $(".deviceLoad").show().siblings().hide();
        loadData(htmlSetViewAll);
    });
    $cover.find(".deviceLoad>.btn-back").click(function () {
        $(".statusCover").show().siblings().hide()
    });

    //滚动效果
    var coverScrollBoard = $("#cover .scrollBoard");
    coverScrollBoard.append(coverScrollBoard.find("table").clone());
    scrollVertical(coverScrollBoard, coverScrollBoard.children("table").eq(0), coverScrollBoard.children("table").eq(1), 120);
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
    $.post(contextPath + '/lab/equipmentTotalForLab1Ajax', {}, function (data) {
        htmlSetViewAll = '<tr><td class="name">'
            + '检测中心' + '</td><td class="barBox"><span class="bar"><span class="fill"></span></span></td><td class="value">'
            + data[2].rate + '%</td><td class="YoY '
            + "up" + '">同比：<span>'
            + 1.2 + '</span></td> </tr>\n';

        deviceLoadArr.forEach(function (item, index, array) {
            var sign = Number(item.sign) > 0 ? "up" : "down";
            htmlSetViewAll += '<tr><td class="name">'
                + item.name + '</td><td class="barBox"><span class="bar"><span class="fill"></span></span></td><td class="value">'
                + item.value + '</td><td class="YoY '
                + sign + '">同比：<span>'
                + item.YoY + '</span></td> </tr>\n'
        });
        loadData(htmlSetViewAll);
    });


}


/**
 * 拼接表格内容并显示进度条百分比
 * @param html 拼接后的表格内容
 */
function loadData(html) {
    $(".viewAll").find("tbody").html(html)
        .find(".fill").css("width", function () {
        let text = $(this).parent().parent().next().text();
        return (text.slice(0, -1) - 80) / 20 * 100 + "%";
    });
}

/*获取7条产线的概况*/
function equipmentStatisForPlForLab2Ajax(type) {
    $.post(contextPath + '/lab/equipmentStatisForPlForLab2Ajax', {
            "type": type,
            "labTypeCode": labTypeCode
        }, function (data) {
            // console.log('===================', data)
            var deviceLoadArr = [];
            $.each(data, function (index, item) {
                deviceLoadArr.push({
                    name: item.product_name,
                    value: item.dq + "%",
                    YoY: item.change_num,
                    sign: item.change_num
                });

            })
            setViewAll(deviceLoadArr);
        }
    )
}