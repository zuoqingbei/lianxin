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
        this.initEcharts10();
        this.dateForm1();
        this.dateForm2();
        this.initTable();
        this.btnClick();
        this.selectChange();
    },
    selectChange(){
	    $("#customer").on("change",()=>{
	    	var customerId=$("#customer option:selected").val()
	    	console.info(customerId);
	    	this.initEcharts05(customerId);
	    })
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
    initEcharts05(customerId) {
    	$.post("/credit/orderStatistic/Ordermap",{"customId":customerId},function(data){
 		   let arr=[];
    		$.each(data,function(index,item){
    		 arr.push({
              name : item.country,
              value : item.num
           }); 
    		});
    	console.info(arr);
        let ec001_pie = echarts.init($("#echarts_05")[0]);
        ec001_pie.clear();
        //ec001_pie.setOption(opt_bar_horizon);
        ec001_pie.setOption({
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    var value = (params.value + '').split('.');
                   /* value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, '$1,')
                        + '.' + value[1];*/
                    //return params.seriesName + '<br/>' + params.name;
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
                max: 10,
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
                    data: arr,
                    nameMap: {
                        'Afghanistan': '阿富汗',
                            'Angola': '安哥拉',
                            'Albania': '阿尔巴尼亚',
                            'United Arab Emirates': '阿联酋',
                            'Argentina': '阿根廷',
                            'Armenia': '亚美尼亚',
                            'French Southern and Antarctic Lands': '法属南半球和南极领地',
                            'Australia': '澳大利亚',
                            'Austria': '奥地利',
                            'Azerbaijan': '阿塞拜疆',
                            'Burundi': '布隆迪',
                            'Belgium': '比利时',
                            'Benin': '贝宁',
                            'Burkina Faso': '布基纳法索',
                            'Bangladesh': '孟加拉国',
                            'Bulgaria': '保加利亚',
                            'The Bahamas': '巴哈马',
                            'Bosnia and Herzegovina': '波斯尼亚和黑塞哥维那',
                            'Belarus': '白俄罗斯',
                            'Belize': '伯利兹',
                            'Bermuda': '百慕大',
                            'Bolivia': '玻利维亚',
                            'Brazil': '巴西',
                            'Brunei': '文莱',
                            'Bhutan': '不丹',
                            'Botswana': '博茨瓦纳',
                            'Central African Republic': '中非共和国',
                            'Canada': '加拿大',
                            'Switzerland': '瑞士',
                            'Chile': '智利',
                            'China': '中国大陆',
                            'Ivory Coast': '象牙海岸',
                            'Cameroon': '喀麦隆',
                            'Democratic Republic of the Congo': '刚果民主共和国',
                            'Republic of the Congo': '刚果共和国',
                            'Colombia': '哥伦比亚',
                            'Costa Rica': '哥斯达黎加',
                            'Cuba': '古巴',
                            'Northern Cyprus': '北塞浦路斯',
                            'Cyprus': '塞浦路斯',
                            'Czech Republic': '捷克共和国',
                            'Germany': '德国',
                            'Djibouti': '吉布提',
                            'Denmark': '丹麦',
                            'Dominican Republic': '多明尼加共和国',
                            'Algeria': '阿尔及利亚',
                            'Ecuador': '厄瓜多尔',
                            'Egypt': '埃及',
                            'Eritrea': '厄立特里亚',
                            'Spain': '西班牙',
                            'Estonia': '爱沙尼亚',
                            'Ethiopia': '埃塞俄比亚',
                            'Finland': '芬兰',
                            'Fiji': '斐',
                            'Falkland Islands': '福克兰群岛',
                            'France': '法国',
                            'Gabon': '加蓬',
                            'United Kingdom': '英国',
                            'Georgia': '格鲁吉亚',
                            'Ghana': '加纳',
                            'Guinea': '几内亚',
                            'Gambia': '冈比亚',
                            'Guinea Bissau': '几内亚比绍',
                            'Equatorial Guinea': '赤道几内亚',
                            'Greece': '希腊',
                            'Greenland': '格陵兰',
                            'Guatemala': '危地马拉',
                            'French Guiana': '法属圭亚那',
                            'Guyana': '圭亚那',
                            'Honduras': '洪都拉斯',
                            'Croatia': '克罗地亚',
                            'Haiti': '海地',
                            'Hungary': '匈牙利',
                            'Indonesia': '印尼',
                            'India': '印度',
                            'Ireland': '爱尔兰',
                            'Iran': '伊朗',
                            'Iraq': '伊拉克',
                            'Iceland': '冰岛',
                            'Israel': '以色列',
                            'Italy': '意大利',
                            'Jamaica': '牙买加',
                            'Jordan': '约旦',
                            'Japan': '日本',
                            'Kazakhstan': '哈萨克斯坦',
                            'Kenya': '肯尼亚',
                            'Kyrgyzstan': '吉尔吉斯斯坦',
                            'Cambodia': '柬埔寨',
                            'South Korea': '韩国',
                            'Kosovo': '科索沃',
                            'Kuwait': '科威特',
                            'Laos': '老挝',
                            'Lebanon': '黎巴嫩',
                            'Liberia': '利比里亚',
                            'Libya': '利比亚',
                            'Sri Lanka': '斯里兰卡',
                            'Lesotho': '莱索托',
                            'Lithuania': '立陶宛',
                            'Luxembourg': '卢森堡',
                            'Latvia': '拉脱维亚',
                            'Morocco': '摩洛哥',
                            'Moldova': '摩尔多瓦',
                            'Madagascar': '马达加斯加',
                            'Mexico': '墨西哥',
                            'Macedonia': '马其顿',
                            'Mali': '马里',
                            'Myanmar': '缅甸',
                            'Montenegro': '黑山',
                            'Mongolia': '蒙古',
                            'Mozambique': '莫桑比克',
                            'Mauritania': '毛里塔尼亚',
                            'Malawi': '马拉维',
                            'Malaysia': '马来西亚',
                            'Namibia': '纳米比亚',
                            'New Caledonia': '新喀里多尼亚',
                            'Niger': '尼日尔',
                            'Nigeria': '尼日利亚',
                            'Nicaragua': '尼加拉瓜',
                            'Netherlands': '荷兰',
                            'Norway': '挪威',
                            'Nepal': '尼泊尔',
                            'New Zealand': '新西兰',
                            'Oman': '阿曼',
                            'Pakistan': '巴基斯坦',
                            'Panama': '巴拿马',
                            'Peru': '秘鲁',
                            'Philippines': '菲律宾',
                            'Papua New Guinea': '巴布亚新几内亚',
                            'Poland': '波兰',
                            'Puerto Rico': '波多黎各',
                            'North Korea': '北朝鲜',
                            'Portugal': '葡萄牙',
                            'Paraguay': '巴拉圭',
                            'Qatar': '卡塔尔',
                            'Romania': '罗马尼亚',
                            'Russia': '俄罗斯',
                            'Rwanda': '卢旺达',
                            'Western Sahara': '西撒哈拉',
                            'Saudi Arabia': '沙特阿拉伯',
                            'Sudan': '苏丹',
                            'South Sudan': '南苏丹',
                            'Senegal': '塞内加尔',
                            'Solomon Islands': '所罗门群岛',
                            'Sierra Leone': '塞拉利昂',
                            'El Salvador': '萨尔瓦多',
                            'Somaliland': '索马里兰',
                            'Somalia': '索马里',
                            'Republic of Serbia': '塞尔维亚共和国',
                            'Suriname': '苏里南',
                            'Slovakia': '斯洛伐克',
                            'Slovenia': '斯洛文尼亚',
                            'Sweden': '瑞典',
                            'Swaziland': '斯威士兰',
                            'Syria': '叙利亚',
                            'Chad': '乍得',
                            'Togo': '多哥',
                            'Thailand': '泰国',
                            'Tajikistan': '塔吉克斯坦',
                            'Turkmenistan': '土库曼斯坦',
                            'East Timor': '东帝汶',
                            'Trinidad and Tobago': '特里尼达和多巴哥',
                            'Tunisia': '突尼斯',
                            'Turkey': '土耳其',
                            'United Republic of Tanzania': '坦桑尼亚联合共和国',
                            'Uganda': '乌干达',
                            'Ukraine': '乌克兰',
                            'Uruguay': '乌拉圭',
                            'United States': '美国',
                            'Uzbekistan': '乌兹别克斯坦',
                            'Venezuela': '委内瑞拉',
                            'Vietnam': '越南',
                            'Vanuatu': '瓦努阿图',
                            'West Bank': '西岸',
                            'Yemen': '也门',
                            'South Africa': '南非',
                            'Zambia': '赞比亚',
                            'Zimbabwe': '津巴布韦'
                    },
                }
            ]
        });
    	})
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
    	$.post("/credit/orderStatistic/orderListintface",{"type":"6"},function(data){
    		
       		$.each(data,function(index,item){
       			var xAxisData="";
           		var seriesData=[];
       			console.info(index+"kkkkkkkk"+item.num/100+"ppp"+item.name);
       			xAxisData=item.name;
       			seriesData.push(item.num/100);
       			
       			var html='<li><div id="echarts_09_'+index+'" style="width: 100%;height:100%"></div></li>'
       			$('ul.swiper-slide').append(html);
       			let ec001_pie = echarts.init($("#echarts_09_"+index)[0]);
             ec001_pie.clear();
             //ec001_pie.setOption(opt_liquidfill_series);
             ec001_pie.setOption({
            	 title: {
                     text: xAxisData,
                     textStyle: {
                         fontSize: 12 * bodyScale,
                         color: '#666'
                     },
                     left: '48%',
                     top: '1%',
                     textAlign: 'center',
                     textBaseAlign: 'middle'
                 },
                 series: [{
                     type: 'liquidFill',
                     data: seriesData,
                     center: ['50%', '57%'],
                 }
                 ].map(function (item) {
                     return $.extend(true, {}, item, opt_liquidfill_series);
                 })
             });
       		});
    	})
       
    },
   
    /*各员工当日情况*/
    initTable(data) {
    	var data=$("#dead_date1").val(); 
    	console.info(data);
//    	data.substring
    	
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
             url : '/credit/orderStatistic/customDay', // 请求后台的URL（*）
             method : 'get', // 请求方式（*）post/get
             data:{
            	 time:data
             },
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
            queryParamsType: "",
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                let time = $("#dead_date1").val();
                return {//这里的params是table提供的
                	sortName: params.sortName,//从数据库第几条记录开始
                	sortOrder: params.sortOrder,
                	time
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
