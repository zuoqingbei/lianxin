/**
 * 世界地图平面图
 */
function mapFlat(data) {
        var option = {
            // backgroundColor: '#1b1b1b',
            color: ['gold', 'aqua', 'lime'],
            title: {
               /* text: '模拟迁徙',
                subtext: '数据纯属虚构',*/
                x: 'center',
                textStyle: {
                    color: '#fff'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b}'
            },
            legend: {
                // orient: 'vertical',
                x: 'center',
                y:"bottom",
                data: legendData(data),
                selectedMode: 'single',
                selected: {
                },
                textStyle: {
                    color: '#fff'
                }
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
/*
            dataRange: {
                min: 0,
                max: 100,
                calculable: true,
                color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua'],
                textStyle: {
                    color: '#fff'
                }
            },
*/
            series: seriesData(data)
        };
        return option;
};