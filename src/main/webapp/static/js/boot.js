(function () {

    function resize() {
        $('#main').height($(window).height() - $('#header').height());

    }

    //这个目前不好用
    window.onresize = resize(resize);
    $(window).resize(resize);
    resize();

    $(function () {
        require(['start']);
    });

/*
    function boot() {

    }
*/

})();

define("start", function (require) {

    var ec = require('echarts');
    require('echarts-x');
    require('echarts/chart/map');
    require('echarts/chart/bar');
    require('echarts-x/chart/map3d');
    var test = function (data) {
        return data+3;
    };
    var myChart = null;
    var jsCode = $('#code-source').text();
    myChart = ec.init(document.getElementById('chart'));
    myChart.on("click", function(data){
        // myChart.component.tooltip.showTip(true);

    });
/*    myChart.dispatchAction({
        type: 'dataZoom',
        start: 20,
        end: 30
    });*/
    //runCode传入的参数是一段JS代码

    var zoom1 = function (dataZoomIndex,start,end,startValue,endValue) {
/*
        myChart.action.legend.legendSelect({
            type: 'legendSelect',
            // 图例名称
            name: "hello",
            // type: 'dataZoom',
            // 可选，dataZoom 组件的 index，多个 dataZoom 组件时有用，默认为 0
            dataZoomIndex: dataZoomIndex,
            // 开始位置的百分比，0 - 100
            start: start+10,
            // 结束位置的百分比，0 - 100
            end: end,
            // 开始位置的数值
            startValue: startValue,
            // 结束位置的数值
            endValue: endValue
        });
*/
    }
    var showFlat = function () {
        // myChart.dispose();
        // myChart = echarts.init(document.getElementById('main'));
        // myChart.setOption(mapFlat());
    }
    setTimeout(function () {
        runCode(jsCode);
    });

    var runCode = function runCode(code) {
        myChart = ec.init(document.getElementById('chart'));
        var func = new Function('myChart', 'require', code);
        func(myChart, require);
    }

    return {
        test:test,
        zoom1:zoom1,
        showFlat:showFlat,
        runCode:runCode
    }
});
