/////////////////////////////////////////////////////////////////统计数据/////////////////////////////
/**
 * 右侧数据统计
 */
function loadTab4Data(){
	
    // 共产 一致比重统计
	communistGravityStatisticForTab4Ajax();
    //根据类型 时间 统计共产 一致个月份数量
    communistStatisticForMonthForTab4Ajax();
}
//根据类型 时间 统计共产 一致个月份数量
function communistStatisticForMonthForTab4Ajax(){
	$.post(contextPath+'/lab/communistStatisticForMonthForTab1Ajax',{},function(data){
		var myChart18 = echarts.init(document.getElementById("myChart18"));
		right_echarts.push(myChart18);
		myChart18.setOption(getBarEcharts());
		myChart18.setOption({
		    color: ['#66ccff', '#a5fff1'],
		    legend: {
		        show: true,
		        data: ['共产数', '一致数']
		    },
		    grid: {
//		            show:true,
		        x: "12%",
		        x2: "15%",
		        y: '20%',
		        y2: "22%"
		    },
		    yAxis: [
		        {
		            name: "数量",
		            type: 'value'
		        }
		    ],
		    xAxis: [
		        {
		            name: "时间",
		            type: 'category',
		            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
		        }
		    ],
		    series: [{
		        name: '共产数',
		        type: 'pictorialBar',
		        label: labelSetting,
		        symbolRepeat: true,
		        symbolSize: ['80%', '60%'],
		        barCategoryGap: '40%',
		        data: statisticRightSeriesTab4Data(data[0],bar_chip)
		    }, {
		        name: '一致数',
		        type: 'pictorialBar',
		        barGap: '10%',
		        label: labelSetting,
		        symbolRepeat: true,
		        symbolSize: ['80%', '60%'],
		        data: statisticRightSeriesTab4Data(data[1],bar_chip)
		    }]
		});
	})
}


// 共产 一致比重统计
function communistGravityStatisticForTab4Ajax(){
	$.post(contextPath+'/lab/communistGravityStatisticForTab1Ajax',{},function(data){
		var myChart17 = echarts.init(document.getElementById("myChart17"));
		right_echarts.push(myChart17);
		myChart17.setOption(getRoseEcharts());
		myChart17.setOption({
		    color: ['#66ccff', '#4397f7'],
		    legend: {
		        show: true,
		        textStyle: {
		            color: '#66ccff',
		            fontSize: 10 * bodyScale,
		        },
		        orient: 'vertical',  //布局  纵向布局
		        data: ['共产一致型号数', '共产不一致型号数'],
		        itemWidth: 10,  //图例标记的图形宽度
		        itemHeight: 2, //图例标记的图形高度
		    },
		    series: [
		        {
		            name: '',
		            type: 'pie',
		            radius: [0, '50%'],
		            center: ['50%', '55%'],
		            roseType: 'radius',
		            label: {
		                normal: {
		                    show: true,
		                    position: "inside",
		                    formatter: "{d}%"
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            lableLine: {
		                normal: {
		                    show: false
		                },
		                emphasis: {
		                    show: true
		                }
		            },
		            data: [
		                {value: data.yz_num, name: '共产一致型号数'},
		                {value: (parseInt(data.gc_num)-parseInt(data.yz_num)), name: '共产不一致型号数'}
		            ]
		        },
		    ]
		});
	})
}






function statisticRightSeriesTab4Data(data,bar_chip){
	var series=[];
	$.each(data,function(index,item){
		var obj=new Object();
		obj.value=item.count;
		obj.symbol=bar_chip;
		series.push(obj);
	});
	return series;
}


