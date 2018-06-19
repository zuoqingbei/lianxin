function parentMethod(labCode) {//平面地图的数据加载
    // $("#iframeFlat")[0].contentWindow.createArrData("21,22,23,24,25,26,27", "2,1,4,3");
    $("#iframeFlat")[0].contentWindow.createArrData("", labCode);
    return false;
}
// 激活
function active($this){
    $this.addClass("active").siblings().removeClass("active");
}
$(function () {

    // $(window.frames["iframeFlat"].document).find("");

    // a标签点击
    $(".legend-m ul>li").addClass("active").click(function () {
        active($(this));
        let labCode = $(this).index() + 1;
        if (labCode === 1) {
            labCode = 2;
        } else if (labCode === 2) {
            labCode = 1;
        }
        //重置地图数据
        parentMethod(labCode);
        return false;
    });

    // 实验室和产线的切换
    $(".legend>.btn").click(function () {
        active($(this));
        if ($(this).index() === 0) {
            $(".legend-m ul").css("top", "-1.3125em");
        } else {
            $(".legend-m ul").css("top", "0");
        }
    })


})
