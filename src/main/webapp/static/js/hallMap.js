function parentMethod() {//平面地图的数据加载
    // $("#iframeFlat")[0].contentWindow.createArrData("21,22,23,24,25,26,27", "2,1,4,3");
    $("#iframeFlat")[0].contentWindow.createArrData("", "");
}
function active($this){
    $this.addClass("active").siblings().removeClass("active");
}
$(function () {

    // $(window.frames["iframeFlat"].document).find("");

    // a标签点击
    $(".legend-m ul>li").addClass("active").click(function () {
        active($(this));
        return false;
    });

    // 实验室和产线的切换
    $(".legend>.btn").click(function () {
        active($(this));
        if($(this).index() ===0){
            $(".legend-m ul").css("top","-1.3125em");
        }else {
            $(".legend-m ul").css("top","0");
        }
    })












})
