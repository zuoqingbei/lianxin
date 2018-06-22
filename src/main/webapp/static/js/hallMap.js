
let labCode = "2,1,4",
    lineCode = "21,22,23,24,25,26,27";
$(function () {
    // parentMethod(lineCode, labCode);
    // 在7x3大屏上隱藏footer
    styleIn7x3();
    //设置产线和实验室的颜色
    addStyleForA();
    // 点击导航中的A标签
    clickNavA();
    // 实验室和产线的切换
    showLabLine();
    //全选
    selectAll();
    // 进入实验室按钮
    $(".legend .toLabData").click(function () {
        $("#content>.lab",parent.document).show().siblings(":not(.fromHallMap)").hide()
    })
});

//全选
function selectAll(){
    $(".selectAll").click(function () {
        if(!$(this).find("input").is(":checked")){
            $("#selectAll").prop("checked",true);//不知为什么不能通过点击直接修改checked的状态
            if ($(".legend-m ul.active").index() === 0) {
                labCode = "2,1,4";
            } else {
                lineCode = "21,22,23,24,25,26,27";
            }
            $(".legend-m ul.active>li").addClass("active");
            parentMethod(lineCode, labCode);
            // console.log(lineCode, labCode);
        }
        return false;
    })
}

// 实验室和产线的切换
function showLabLine(){
    $(".legend>.btn").click(function () {
        active($(this));
        if ($(this).index() === 0) {
            $(".legend-m ul").css("top", "-1.3125em");
            active($(".legend-m ul:eq(1)"))
        } else {
            $(".legend-m ul").css("top", "0");
            active($(".legend-m ul:eq(0)"));
        }
        // 判断当前激活li元素个数来确定是否选中全选按钮
        if($(".legend-m ul.active>li.active").length === 1){
            $("#selectAll").prop("checked",false);
        }else{
            $("#selectAll").prop("checked",true);
        }
    });
}

// 在7x3大屏上隱藏footer
function styleIn7x3(){
    let fromBigScreen7x3 = location.href.indexOf("bigScreen7x3")>-1;
    console.log(fromBigScreen7x3);
    if (fromBigScreen7x3){
        $("#myContainer>footer p").hide();
    }
}

//平面地图的数据加载
function parentMethod(lineCode, labCode) {
    // $("#iframeFlat")[0].contentWindow.createArrData("21,22,23,24,25,26,27", "2,1,4,3");
    $("#iframeFlat")[0].contentWindow.createArrData(lineCode, labCode);
}

// 激活
function active($this) {
    $this.addClass("active").siblings().removeClass("active");
}

//设置产线和实验室的颜色
function addStyleForA() {
    let styleStr = '';
    let l = $(".legend-m .text>ul:eq(0)>li").length;//第一个列表长度
    $(".legend-m .text>ul>li").each(function (index) {
        let [firstOrLast, labOrLine, n] = ['first', 'lab', index + 1];
        if (index >= l) { // 遍历第二个列表
            [firstOrLast, labOrLine, n] = ['last', 'line', index + 1 - l];
        }
        styleStr +=
            `.legend-m ul:${firstOrLast}-of-type>li.active:nth-of-type(${n})>a,
             .legend-m ul:${firstOrLast}-of-type>li.active:nth-of-type(${n})>a:before{
                color: var(--c_${labOrLine}${n});
                background: radial-gradient(circle,var(--c_${labOrLine}${n}),rgba(0,0,0,0));
            }`
    });
    $("head").append(`<style>${styleStr}</style>`);
}

// 点击导航中的A标签
function clickNavA() {
    // 默认全部选中
    $(".legend-m ul>li").addClass("active").find("a").click(function () {
        let $parent = $(this).parent();
        active($parent);
        if($parent.parent().index() === 0){ //实验室
            labCode = $(this).parent().index() + 1;
            if (labCode === 1) {
                labCode = 2;
            } else if (labCode === 2) {
                labCode = 1;
            }else if(labCode === 3) {
                labCode = 4;
            }else if(labCode === 4) {
                labCode = 3;
            }
        }else{ //产线
            lineCode = $(this).parent().index() + 21;
        }
        $("#selectAll").prop("checked",false);
        parentMethod(lineCode, labCode);
        return false;
    })
}
