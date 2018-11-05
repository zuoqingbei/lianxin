let Filing = {
    init() {
        this.initEcharts01();
        this.initEcharts02();
        this.initEcharts03();
        this.initEcharts04();
        this.initEcharts05();
        this.initEcharts06();
        this.initEcharts07();
        this.initEcharts08();
        this.initEcharts09_01();
        this.initEcharts09_02();
        this.initEcharts09_03();
        this.initEcharts09_04();
        this.initEcharts09_05();
        this.initEcharts09_06();
        this.initEcharts10();
        this.dateForm1();
        this.dateForm2();
        this.initTable();
        this.btnClick();
    },
   
    btnClick(){
    	$(".btn-group").click((e)=>{
    		let id = $(e.target).parent(".btn-group").attr("id")
    		let text = $(e.target).text();
    		let type = ''
    		switch(id) {
	    		case 'allOrderTrend':
	        		switch(text) {
	        			case '周':
	        				type = 'week';
	        				break;
	        			case '月':
	        				type = 'month';
	        				break;
	        			case '年':
	        				type = 'year';
	        				break;
	        			default:
	        				break;
	        		}
	        		this.initEcharts07(type);
					break;
				case 'allDelay':
	        		switch(text) {
	        			case '周':
	        				type = 'week';
	        				break;
	        			case '月':
	        				type = 'month';
	        				break;
	        			case '年':
	        				type = 'year';
	        				break;
	        			default:
	        				break;
	        		}
	        		this. initEcharts08(type);
					break;
				default:
					break;
    		}
    		
    	})
    },
    
    
    /*订单总量-日*/
    initEcharts01(){
    	$.post("/credit/orderStatistic/orderListintface",{"type":"1"},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});

            let ec001_pie = echarts.init($("#echarts_01")[0]);
            ec001_pie.clear();
            ec001_pie.setOption(opt_bar_vertical);
            ec001_pie.setOption({
                xAxis: {
                    data: xAxisData
                },
                grid: {
                    bottom: "25%",//底边留空
                    top: '25',
                    left: '0%',
                    right: '0%',
                },
                yAxis: {
                    show: false,
                    splitLine: { //分割线
                        show: false,
                    },
                },
                xAxis: {
                    show: false,
                },
                series: [{
                    data: seriesData,
                    type: 'bar'
                }],
            });
        
    	})
    },
    
  
  
    /*订单延误率-月*/
    initEcharts02() {
    	
    	$.post("/credit/orderStatistic/orderListintface",{"type":"2"},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});
    
        let ec001_pie = echarts.init($("#echarts_02")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_bar_vertical);
        ec001_pie.setOption({
            xAxis: {
                data: xAxisData
            },
            grid: {
                bottom: "25%",//底边留空
                top: '25',
                left: '0%',
                right: '0%',
            },
            yAxis: {
                show: false,
                splitLine: { //分割线
                    show: false,
                },
            },
            xAxis: {
                show: false,
            },
            series: [{
                data: seriesData,
                type: 'bar'
            }],
        });
    	})
    },
    /*订单延误率-年*/
    initEcharts03() {
    	
    	$.post("/credit/orderStatistic/orderListintface",{"type":"3"},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});
        let ec001_pie = echarts.init($("#echarts_03")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_line);
        ec001_pie.setOption({
            xAxis: {
                data: xAxisData
            },
            grid: {
                bottom: "25%",//底边留空
                top: '25',
                left: '0%',
                right: '0%',
            },
            yAxis: {
                show: false,
                splitLine: { //分割线
                    show: false,
                },
            },
            xAxis: {
                show: false,
            },
            series: [{
                data: seriesData,
                type: 'line',
                areaStyle: {},
                smooth: true,
                showSymbol: false,
            }],
        });
    	})
    },
    /*客户订单量排名*/
    initEcharts04() {
    	$.post("/credit/orderStatistic/orderListintface",{"type":"4"},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.name);
       			seriesData.push(item.num);
       		});
    	
        let ec001_pie = echarts.init($("#echarts_04")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_bar_horizon);
        ec001_pie.setOption({
            yAxis: {
                data:xAxisData,
            },
            grid: {
                bottom: "5%",//底边留空
                top: '5%',
                left: '15%',
                right: '3%',
            },
            series: [{
                data: seriesData,  //注意 数据是从大到小提供 看的是排名
                type: 'bar'
            }]
        });
    	})
    },
    /*订单分布图*/
    initEcharts05() {
        let ec001_pie = echarts.init($("#echarts_05")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_bar_horizon);
        ec001_pie.setOption({
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    var value = (params.value + '').split('.');
                    value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
                        + '.' + value[1];
                    return params.seriesName + '<br/>' + params.name + ' : ' + value;
                }
            },
            /*toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },*/
            visualMap: {
                min: 0,
                max: 1000,
                // text:['High','Low'],
                realtime: false,//拖拽时，是否实时更新
                calculable: false,//是否显示数值
                color: [ '#0A48A1','#C8E6FF'],
                itemWidth: '10%',
                itemHeight: '50%'
            },
            series: [
                {
                    name: '订单分布',
                    type: 'map',
                    mapType: 'world',
                    roam: true,
                    zoom:1.2,
                    itemStyle: {
                        emphasis: {label: {show: true}},
                        areaColor:'#C8E6FF',
                        borderWidth:0
                    },
                    data: [
                        {name: 'Afghanistan', value: 28397.812},
                        {name: 'Angola', value: 19549.124},
                        {name: 'Albania', value: 3150.143},
                        {name: 'United Arab Emirates', value: 8441.537},
                        {name: 'Argentina', value: 40374.224},
                        {name: 'Armenia', value: 2963.496},
                        {name: 'French Southern and Antarctic Lands', value: 268.065},
                        {name: 'Australia', value: 22404.488},
                        {name: 'Austria', value: 8401.924},
                        {name: 'Azerbaijan', value: 9094.718},
                        {name: 'Burundi', value: 9232.753},
                        {name: 'Belgium', value: 10941.288},
                        {name: 'Benin', value: 9509.798},
                        {name: 'Burkina Faso', value: 15540.284},
                        {name: 'Bangladesh', value: 151125.475},
                        {name: 'Bulgaria', value: 7389.175},
                        {name: 'The Bahamas', value: 66402.316},
                        {name: 'Bosnia and Herzegovina', value: 3845.929},
                        {name: 'Belarus', value: 9491.07},
                        {name: 'Belize', value: 308.595},
                        {name: 'Bermuda', value: 64.951},
                        {name: 'Bolivia', value: 716.939},
                        {name: 'Brazil', value: 195210.154},
                        {name: 'Mozambique', value: 23967.265},
                        {name: 'Mauritania', value: 3609.42},
                        {name: 'Malawi', value: 15013.694},
                        {name: 'Malaysia', value: 28275.835},
                        {name: 'Namibia', value: 2178.967},
                        {name: 'New Caledonia', value: 246.379},
                        {name: 'Niger', value: 15893.746},
                        {name: 'Nigeria', value: 159707.78},
                        {name: 'Nicaragua', value: 5822.209},
                        {name: 'Netherlands', value: 16615.243},
                        {name: 'Norway', value: 4891.251},
                        {name: 'Nepal', value: 26846.016},
                        {name: 'New Zealand', value: 4368.136},
                        {name: 'Oman', value: 2802.768},
                        {name: 'Pakistan', value: 173149.306},
                        {name: 'Panama', value: 3678.128},
                        {name: 'Peru', value: 29262.83},
                        {name: 'Philippines', value: 93444.322},
                        {name: 'Papua New Guinea', value: 6858.945},
                        {name: 'Poland', value: 38198.754},
                        {name: 'Puerto Rico', value: 3709.671},
                        {name: 'North Korea', value: 1.468},
                        {name: 'Portugal', value: 10589.792},
                        {name: 'Paraguay', value: 6459.721},
                        {name: 'Qatar', value: 1749.713},
                        {name: 'Romania', value: 21861.476},
                        {name: 'Russia', value: 21861.476},
                        {name: 'Rwanda', value: 10836.732},
                        {name: 'Western Sahara', value: 514.648},
                        {name: 'Saudi Arabia', value: 27258.387},
                        {name: 'Sudan', value: 35652.002},
                        {name: 'South Sudan', value: 9940.929},
                        {name: 'Senegal', value: 12950.564},
                        {name: 'Solomon Islands', value: 526.447},
                        {name: 'Sierra Leone', value: 5751.976},
                        {name: 'El Salvador', value: 6218.195},
                        {name: 'Somaliland', value: 9636.173},
                        {name: 'Somalia', value: 9636.173},
                        {name: 'Republic of Serbia', value: 3573.024},
                        {name: 'Suriname', value: 524.96},
                        {name: 'Slovakia', value: 5433.437},
                        {name: 'Slovenia', value: 2054.232},
                        {name: 'Sweden', value: 9382.297},
                        {name: 'Swaziland', value: 1193.148},
                        {name: 'Syria', value: 7830.534},
                        {name: 'Chad', value: 11720.781},
                        {name: 'Togo', value: 6306.014},
                        {name: 'West Bank', value: 13.565},
                        {name: 'Yemen', value: 22763.008},
                        {name: 'South Africa', value: 51452.352},
                        {name: 'Zambia', value: 13216.985},
                        {name: 'Zimbabwe', value: 13076.978}
                    ]
                }
            ]
        });
    },
    /*报告类型占比*/
    initEcharts06() {
    	$.post("/credit/orderStatistic/orderListintface",{"type":"5"},function(data){
    		   let arr=[];
       		$.each(data,function(index,item){
       		 arr.push({
                 name : item.name,
                 value : item.num
              }); 
       		});
    	
        let ec001_pie = echarts.init($("#echarts_06")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_circle);
        let showLength = 5;
        let objects = arr;
        let new_data_arr = [];
        let seriesData = [];
        let new_name = "";
        for (var i = 0; i < objects.length; i++) {
            let object = objects[i];
            //系列名的长度大于5个字的话 截取前五个字后 后面文字省略
            if (object.name.length > showLength) {
                new_name = object.name.substr(0, showLength) + '..';
            } else {
                new_name = object.name;
            }
            new_data_arr.push(new_name);
            seriesData.push({value: object.value, name: new_name});
        }
        ;
        ec001_pie.setOption({
            legend: {
                orient: 'horizontal',
                x: 'center',
                y: 'top',
                type:'scroll',
                data: new_data_arr,
            },
            calculable : true,
            series: [{
                name: '扣分情况',
                type: 'pie',
                radius: [80, 100],
                center: ['50%', '52%'],
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: seriesData
            }],
            tooltip: {
                trigger: 'item',
                formatter: function (data) {
                    let dataIndex = data.dataIndex;
                    console.log(dataIndex)
                    let cName = "";
                    let percent = "";
                    let value = "";
                    for (let x = 0; x < objects.length; x++) {
                        if (x == dataIndex) {
                            cName = objects[dataIndex].name;
                            value = objects[dataIndex].value;
                            percent = data.percent;
                        }
                    }
                    return cName + " : " + value + " " + percent + "%";
                }
            },
        });
    	})
    },
    /*总订单量趋势图*/
    initEcharts07(type='week') {
    	$.post("/credit/orderStatistic/orderBytime",{"type":type},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});
        let ec001_pie = echarts.init($("#echarts_07")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_line);
        ec001_pie.setOption({
            xAxis: {
                type: 'category',
                data: xAxisData
            },
            yAxis: {
                name: "订单量/个",
                type: 'value'
            },
            grid: {
                bottom: "15%",//底边留空
                top: '10%',
                left: '8%',
                right: '5%',
            },
            series: [{
                data: seriesData,
                type: 'line'
            }],
            dataZoom: [
                {
                    type: 'slider',
                    show: 'true',
                    height: 10,
                    bottom: 5
                }
            ]
        });
    	})
    },
    /*总订单延误率*/
    initEcharts08(type='week') {
    	$.post("/credit/orderStatistic/delayOrder",{"type":type},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});
        let ec001_pie = echarts.init($("#echarts_08")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_bar_vertical);
        ec001_pie.setOption({
            xAxis: {
                data: xAxisData
            },
            yAxis: {
                name: "厌恶率",
            },
            grid: {
                bottom: "15%",//底边留空
                top: '13%',
                left: '8%',
                right: '5%',
            },
            series: [{
                data: seriesData,
                type: 'bar'
            }],
            dataZoom: [
                {
                    type: 'slider',
                    show: 'true',
                    height: 10,
                    bottom: 5
                }
            ]
        });
    	})
    },
    /*各员工延误率*/
    initEcharts09_01() {
        let ec001_pie = echarts.init($("#echarts_09_01")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    initEcharts09_02() {
        let ec001_pie = echarts.init($("#echarts_09_02")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    initEcharts09_03() {
        let ec001_pie = echarts.init($("#echarts_09_03")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    initEcharts09_04() {
        let ec001_pie = echarts.init($("#echarts_09_04")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    initEcharts09_05() {
        let ec001_pie = echarts.init($("#echarts_09_05")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    initEcharts09_06() {
        let ec001_pie = echarts.init($("#echarts_09_06")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_liquidfill_series);
        ec001_pie.setOption({
            series: [{
                type: 'liquidFill',
                data: [0.7],
                center: ['50%', '35%'],
            }
            ].map(function (item) {
                return $.extend(true, {}, item, opt_liquidfill_series);
            })
        });
    },
    /*各员工当日情况*/
    initTable(data) {
    	var data=$("#dead_date1").val(); 
    	console.info(data);
    	data.substring
    	
        const $table = $('#table');
        let _this = this
        $table.bootstrapTable({
            columns: [
                {
                    title: '名称',
                    field: 'name',
                    align: 'center',
                }, {
                    field: 'num',
                    title: '订单量/个',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'num2',
                    title: '完成量/个',
                    sortable: true,
                    align: 'center',
                }
            ],
             url : '/credit/orderStatistic/customDay?time='+data, // 请求后台的URL（*）
             method : 'get', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20],
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            fixedColumns: false,
            fixedNumber: 1,
            queryParamsType: '',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                	sortName: params.sortName,//从数据库第几条记录开始
                	sortOrder: params.sortOrder//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    /*日期控件 */
    dateForm1() {
    	let _this = this
        laydate.render({
            elem: '#dead_date1',
            format: 'yyyy-MM-dd',
            value : new Date(),
            done: function(value, date){
               let time = date.year + '-' + date.month + '-' + date.date;
               $.ajax({
            	   url:'/credit/orderStatistic/customDay',
            	   data:{"time":time},
            	   success:function(data){
            		   $('#table').bootstrapTable('load', data);
            	   }
               })
              }
        });
    },
    /*日期控件 */
    dateForm2() {
        laydate.render({
            elem: '#dead_date2',
            format: 'yyyy年MM月dd日',
            value : new Date()
        });
    },
    /*报告员扣分情况*/
    initEcharts10() {
        let ec001_pie = echarts.init($("#echarts_10")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_bar_vertical);
        let showLength = 5;
        let objects = [
            {
                "name": "邮件营销rrr",
                "data": [{
                    "name": "周一",
                    "value": "124"
                }, {
                    "name": "周二",
                    "value": "333"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            },
            {
                "name": "邮件营销2",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                     {
                         "name": "周三",
                         "value": "333"
                     }
                ]
            },{
                "name": "邮件营销3",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            },
            {
                "name": "邮件营销4",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            },{
                "name": "邮件营销5",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            },{
                "name": "邮件营销6",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            },
            {
                "name": "邮件营销7",
                "data": [{
                    "name": "周一",
                    "value": "1293"
                }, {
                    "name": "周二",
                    "value": "33"
                },
                    {
                        "name": "周三",
                        "value": "333"
                    }
                ]
            }
        ];
        let new_data_arr = [];
        let yaxis_data = [];
        let xaxis_data = [];
        let new_name = "";
        for (var i = 0; i < objects.length; i++) {
            let object = objects[i];
            //系列名的长度大于5个字的话 截取前五个字后 后面文字省略
            if (object.name.length > showLength) {
                new_name = object.name.substr(0, showLength) + '..';
            } else {
                new_name = object.name;
            }
            if (i == 0) {
                $.each(object.data, function (index, item) {
                    xaxis_data.push(item.name);
                });
            }
            new_data_arr.push(new_name);
            let seriesData = [];
            $.each(object.data, function (index, item) {
                seriesData.push(parseFloat(item.value));
            });
            let ser = {
                name: new_name,
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: false,
                        position: 'insideRight'
                    }
                },
                data: seriesData
            };
            yaxis_data.push(ser);

        }
        ;
        ec001_pie.setOption({
            legend: {
                data: new_data_arr,
            },
            tooltip: {
                trigger: 'item',
                formatter: function (data) {
                    let cName = "";
                    cName = data.name;
                    let result = "";
                    $.each(objects, function (index, item) {
                        $.each(item.data, function (ind, it) {
                            if (it.name == cName) {
                                result += item.name + ':' + it.value + "</br>"
                            }

                        });
                    });
                    return cName + " : </br>" + result;
                }
            },
            xAxis: {
                data: xaxis_data,
            },
            yAxis: {
                name: "分数",
            },
            grid: {
                bottom: "15%",//底边留空
                top: '18%',
                left: '8%',
                right: '5%',
            },
            series: yaxis_data,
            dataZoom: [
                {
                    type: 'slider',
                    show: 'true',
                    height: 10,
                    bottom: 5
                }
            ]
        });
    },
}

setTimeout(function () {
    Filing.init();
}, 500);
