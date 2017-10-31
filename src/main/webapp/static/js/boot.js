(function () {


    $(function () {
        require(['start']);
    });

})();

define("start", function (require) {

    var ec = require('echarts');
    require('echarts-x');
    require('echarts/chart/map');
    require('echarts/chart/bar');
    require('echarts-x/chart/map3d');
    var myChart = ec.init($("#sphereMap")[0]);

    //这里需要去除因获取文本而定义成函数的额外字符串
    var options;
    setTimeout(function () {
        myChart.showLoading();
        mapSphere(myChart);
    });

    //点击重置按钮
    $(".reset").click(function () {
        sphereReset();
    });
    function sphereReset() {
        if (myChart) {
            myChart.dispose();
        }
        var myChart = ec.init(document.getElementById('sphereMap'));
        myChart.resize();
        myChart.showLoading();
        mapSphere(myChart);
    }


});
