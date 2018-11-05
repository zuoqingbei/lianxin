let Filing={
	
    init(){
        //this.initEcharts01();
        //this.initEcharts02();
        this.initEcharts03();
       // this.initEcharts04();
        this.getOrderTennder();
        this.getorderDelay();
        this.getorderFinshed();
        this.btnClick();
    },
   
    btnClick(){
    	$(".btn-group").click((e)=>{
    		let id = $(e.target).parent(".btn-group").attr("id")
    		let text = $(e.target).text();
    		let type = ''
    		switch(id) {
	    		case 'orderTennder':
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
	        		this.getOrderTennder(type);
					break;
				case 'orderDelay':
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
	        		this. getorderDelay(type);
					break;
				case 'orderFinished':
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
	        	this.getorderFinshed(type);
					break;
				default:
					break;
    		}
    		
    	})
    },
    /*订单量趋势图*/
   getOrderTennder(type="week"){
    	$.post("/credit/reporterOrderStatistic/getOrderTrend",{"type":type},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});
       		let ec001_pie = echarts.init($("#echarts_01")[0]);
            ec001_pie.clear();
            ec001_pie.setOption(opt_line);
            ec001_pie.setOption({
                xAxis: {
                    type: 'category',
                    data: xAxisData
                },
                yAxis: {
                    name:"订单量/个",
                    type: 'value'
                },
                grid: {
                    bottom: "20%",//底边留空
                    top: '10%',
                    left: '8%',
                    right: '5%',
                },
                series: [{
                    data: seriesData,
                    type: 'line'
                }],
                dataZoom:[
                    {
                        type:'slider',
                        show:'true',
                        height:10,
                        bottom:5
                    }
                ]
            });
    		
    	})
    },
    
    /*订单延误率*/
    getorderDelay(type="week"){
    	$.post("/credit/reporterOrderStatistic/getOrderdelayReport",{"type":type},function(data){
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
             yAxis: {
                 name:"延误率",
             },
             grid: {
                 bottom: "20%",//底边留空
                 top: '13%',
                 left: '8%',
                 right: '5%',
             },
             series: [{
                 data: seriesData,
                 type: 'bar'
             }],
             dataZoom:[
                 {
                     type:'slider',
                     show:'true',
                     height:10,
                     bottom:5
                 }
             ]
         });
    	})
    },
    /*扣分情况*/
    initEcharts03(){
        let ec001_pie = echarts.init($("#echarts_03")[0]);
        ec001_pie.clear();
       ec001_pie.setOption(opt_circle);
       let showLength=5;
       let objects=[{'name':'rose166','value':'12'},{'name':'rose5','value':'18'},{'name':'rose','value':'15'}];
       let new_data_arr=[];
       let seriesData=[];
       let new_name="";
       for(var i=0;i<objects.length;i++ ){
           let object=objects[i];
           //系列名的长度大于5个字的话 截取前五个字后 后面文字省略
           if(object.name.length>showLength){
               new_name=object.name.substr(0,showLength) + '..';
           }else{
               new_name=object.name;
           }
           new_data_arr.push(new_name);
           seriesData.push({value:object.value, name:new_name});
       };
        ec001_pie.setOption({
            legend: {
                orient: 'horizontal',
                x : 'center',
                y : 'top',
                data:new_data_arr,
            },
            calculable : true,
            series:[{
                name:'扣分情况',
                type:'pie',
                radius : [30, 100],
                center : ['50%', '52%'],
                roseType : 'area',
                label: {
                    normal: {
                        position: 'inner',
                        formatter: "{d}%"
                    }
                },
                data:seriesData
            }]
        });
    },
    /*订单完成量趋势图*/
    getorderFinshed(type="week"){
    	$.post("/credit/reporterOrderStatistic/getorderFinsh",{"type":type},function(data){
    		var xAxisData=[];
       		var seriesData=[];
       		$.each(data,function(index,item){
       			xAxisData.push(item.time);
       			seriesData.push(item.num);
       		});

            let ec001_pie = echarts.init($("#echarts_04")[0]);
            ec001_pie.clear();
            ec001_pie.setOption(opt_line);
            ec001_pie.setOption({
                xAxis: {
                    type: 'category',
                    data: xAxisData
                },
                yAxis: {
                    name:"订单量/个",
                    type: 'value'
                },
                grid: {
                    bottom: "20%",//底边留空
                    top: '10%',
                    left: '8%',
                    right: '5%',
                },
                series: [{
                    data: seriesData,
                    type: 'line'
                }],
                dataZoom:[
                    {
                        type:'slider',
                        show:'true',
                        height:10,
                        bottom:5
                    }
                ]
            });
    	})
    	},
    //initEcharts04(){},
};


setTimeout(function () {
    Filing.init();
},500);
