function parentMethod(lineCode,labCode) {//平面地图的数据加载
    // $("#iframeFlat")[0].contentWindow.createArrData("21,22,23,24,25,26,27", "2,1,4,3");
    $("#iframeFlat")[0].contentWindow.createArrData(lineCode, labCode);
}
// 激活
function active($this){
    $this.addClass("active").siblings().removeClass("active");
}
$(function () {

/*
    //设置产线和实验室的颜色
    $(".legend-m ul:eq(0)>li").each(function (index,item) {
        if($(this).hasClass("active")){}
    })
*/



    let lineCode,labCode;
    $(".legend-m ul:eq(0)>li").addClass("active").click(function () {
        active($(this));
        let labCode = $(this).index() + 1;
        if (labCode === 1) {
            labCode = 2;
        } else if (labCode === 2) {
            labCode = 1;
        }
        parentMethod(lineCode,labCode);
        return false;
    });
    $(".legend-m ul:eq(1)>li").addClass("active").click(function () {
        active($(this));
        let lineCode = $(this).index() + 21;
        parentMethod(lineCode,labCode);
        return false;
    });

    // 实验室和产线的切换
    $(".legend>.btn").click(function () {
        active($(this));
        if ($(this).index() === 0) {
            $(".legend-m ul").css("top", "-1.3125em");
            active($(".legend-m ul:eq(1)"))
        } else {
            $(".legend-m ul").css("top", "0");
            active($(".legend-m ul:eq(0)"));
        }
    })
    //全选
    $(".selectAll").click(function () {
        $(".legend-m ul.active>li").addClass("active");
        if($(this).index() ===0){
            labCode = "2,1,3,4";
        }else{
            lineCode = "21,22,23,24,25,26,27";
        }
        parentMethod(lineCode,labCode);

    })


})
