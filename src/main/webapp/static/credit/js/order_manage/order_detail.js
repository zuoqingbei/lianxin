let Filing = {
    init(){
        /**初始化函数 */
        this.initTable();
       /* 股东及管理层背景*/
        this.initTable2();
        /*投资情况*/
        this.initTable3();
        /*质检意见*/
        this.initTable4();
        /*文件预览*/
      /*  this.fileEvent();*/
         /*出资比例环形图*/
        this.initEchartsPie();
    },
     /*出资比例环形图*/
    initEchartsPie(){
        let ec001_pie = echarts.init($("#ec001_pie")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_pie);
        ec001_pie.setOption({
            series : [
                {
                    data:[
                        {value:335, name:'直接访问'},
                        {value:310, name:'邮件营销'}
                    ],
                }
            ]
        });
    },
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
              {
                    field: 'orderDate',
                    title: '日期',
                    //: true,
                    align: 'left',
                  width:"25%"
                }, {
                    field: 'deadDate',
                    title: '变更项',
                    //sortable: true,
                    align: 'left',
                    width:"25%"
                }, {
                    title: '变更前',
                    field: 'clientCode',
                    align: 'left',
                    width:"25%"
                }, {
                    title: `变更后`,
                    field: 'doState',
                    align: 'left',
                    width:"25%"
                },
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
           // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable2(){
        /**初始化表格 */
        const $table = $('#table2');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '姓名',
                    //: true,
                    align: 'left',
                    width:'33.3333%'
                }, {
                    field: 'deadDate',
                    title: '国籍/国家',
                    //sortable: true,
                    align: 'left',
                    width:'33.3333%'
                }, {
                    title: '出资比例（%）',
                    field: 'clientCode',
                    align: 'left',
                    width:'33.3333%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable3(){
        /**初始化表格 */
        const $table = $('#table3');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '公司名称',
                    //: true,
                    align: 'left',
                    width:'50%'
                }, {
                    field: 'deadDate',
                    title: '出资比例（%）',
                    //sortable: true,
                    align: 'left',
                    width:'50%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable4(){
        /**初始化表格 */
        const $table = $('#table4');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '质检类型',
                    //: true,
                    align: 'left'
                }, {
                    field: 'deadDate',
                    title: '操作人',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '操作时间',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '质检意见',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '评分',
                    //sortable: true,
                    align: 'left',
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
  /*  fileEvent(){
        /!*文件预览事件*!/
        /!*获取文件名称和地址*!/

        /!*根据文件名称判断图片和文件名称展示形式*!/
        let num = filename.split(".").length;
        let filename_qz = []
        for(let i=0;i<num-1;i++){
            filename_qz =  filename_qz.concat(filename.split(".")[i])
        }
        /!*文件名称大于四个字时暂展示前两个字和后两个字，中间用...*!/
        filename_qz_str = filename_qz.join('.')
        if(filename_qz_str.length>4) {
            filename_qz_str = filename_qz_str.substr(0,2) + '..' + filename_qz_str.substr(filename_qz_str.length-2,2)
        }
        /!*根据文件类型判断背景图片*!/
        let filetype = filename.split(".")[num-1];
        filename = filename_qz_str + '.' +filetype
        let fileicon = '';
        if(filetype === 'doc' || filetype === 'docx') {
            fileicon = '../../imgs/order/word.png'
        }else if(filetype === 'xlsx') {
            fileicon = '../../imgs/order/Excel.png'
        }else if(filetype === 'png') {
            fileicon = '../../imgs/order/PNG.png'
        }else if(filetype === 'jpg') {
            fileicon = '../../imgs/order/JPG.png'
        }else if(filetype === 'pdf') {
            fileicon = '../../imgs/order/PDF.png'
        }
        $(this).parent(".uploadFile").addClass("upload-over");
        $(this).parent(".uploadFile").html(`<span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
        /!*预览*!/

    },

        	流程进度
        this.step(2);
    },*/
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
              {
                    field: 'orderDate',
                    title: '日期',
                    //: true,
                    align: 'left',
                  width:"25%"
                }, {
                    field: 'deadDate',
                    title: '变更项',
                    //sortable: true,
                    align: 'left',
                    width:"25%"
                }, {
                    title: '变更前',
                    field: 'clientCode',
                    align: 'left',
                    width:"25%"
                }, {
                    title: `变更后`,
                    field: 'doState',
                    align: 'left',
                    width:"25%"
                },
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
           // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable2(){
        /**初始化表格 */
        const $table = $('#table2');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '姓名',
                    //: true,
                    align: 'left',
                    width:'33.3333%'
                }, {
                    field: 'deadDate',
                    title: '国籍/国家',
                    //sortable: true,
                    align: 'left',
                    width:'33.3333%'
                }, {
                    title: '出资比例（%）',
                    field: 'clientCode',
                    align: 'left',
                    width:'33.3333%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable3(){
        /**初始化表格 */
        const $table = $('#table3');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '公司名称',
                    //: true,
                    align: 'left',
                    width:'50%'
                }, {
                    field: 'deadDate',
                    title: '出资比例（%）',
                    //sortable: true,
                    align: 'left',
                    width:'50%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initTable4(){
        /**初始化表格 */
        const $table = $('#table4');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '质检类型',
                    //: true,
                    align: 'left'
                }, {
                    field: 'deadDate',
                    title: '操作人',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '操作时间',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '质检意见',
                    //sortable: true,
                    align: 'left',
                },{
                    field: 'deadDate',
                    title: '评分',
                    //sortable: true,
                    align: 'left',
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                console.log(params)
                return {//这里的params是table提供的
                    offset: params.offset,//从数据库第几条记录开始
                    limit: params.limit//找多少条
                };
            },
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
  /*  fileEvent(){
        /!*文件预览事件*!/
        /!*获取文件名称和地址*!/

        /!*根据文件名称判断图片和文件名称展示形式*!/
        let num = filename.split(".").length;
        let filename_qz = []
        for(let i=0;i<num-1;i++){
            filename_qz =  filename_qz.concat(filename.split(".")[i])
        }
        /!*文件名称大于四个字时暂展示前两个字和后两个字，中间用...*!/
        filename_qz_str = filename_qz.join('.')
        if(filename_qz_str.length>4) {
            filename_qz_str = filename_qz_str.substr(0,2) + '..' + filename_qz_str.substr(filename_qz_str.length-2,2)
        }
        /!*根据文件类型判断背景图片*!/
        let filetype = filename.split(".")[num-1];
        filename = filename_qz_str + '.' +filetype
        let fileicon = '';
        if(filetype === 'doc' || filetype === 'docx') {
            fileicon = '../../imgs/order/word.png'
        }else if(filetype === 'xlsx') {
            fileicon = '../../imgs/order/Excel.png'
        }else if(filetype === 'png') {
            fileicon = '../../imgs/order/PNG.png'
        }else if(filetype === 'jpg') {
            fileicon = '../../imgs/order/JPG.png'
        }else if(filetype === 'pdf') {
            fileicon = '../../imgs/order/PDF.png'
        }
        $(this).parent(".uploadFile").addClass("upload-over");
        $(this).parent(".uploadFile").html(`<span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
        /!*预览*!/

    },*/

    step(index){
        /*如果圆圈的索引 =index 高亮显示  .circle_active*/
        /*如果圆圈的索引 <index 蓝色 span_active*/
        /*如果圆圈的索引 >index 灰色 span_circle*/
        let circle_arr=$(".bar-span-box .span_circle");
        let bar_arr=$(".bar-span-box .span_bar");
        var world_arr=$(".bar_box>ul>li");
        let barIndex=index-1;
        for(var i=0;i<circle_arr.length;i++){
            $(circle_arr[i]).removeClass("circle_active").removeClass("span_active").removeClass("span_circle");
            $(bar_arr[i]).removeClass("bar_active").removeClass("span_circle");
            $(world_arr[i]).removeClass("world_active");
            /*判断圆圈*/
            if(i==index){
               $(circle_arr[i]).addClass("circle_active");
                $(world_arr[i]).children("span").addClass("world_active");
            }else if(i<index){
                $(circle_arr[i]).addClass("span_active");
            }else if(i>index){
                $(circle_arr[i]).addClass("span_circle");
            }
            /*判断横条*/
            if(barIndex>=0){
                if(i<=barIndex){
                    $(bar_arr[i]).addClass("bar_active")
                }else if(i>barIndex){
                    $(bar_arr[i]).addClass("span_circle")
                }
            }
        }

/*

        /!*如果横条的索引 <=index-1 蓝色*!/
        /!*如果横条的索引 >index-1  灰色*!/
        let bar_arr=$(".bar-span-box .span_bar").index();
      /!*  $(".bar-span-box .span_bar").removeClass("span_active");*!/
        $(".bar-span-box .span_bar").removeClass("span_circle");
        if(bar_arr<=index-2){
          /!*  $(this).previousSibling(".span_bar").addClass("span_active");*!/
            $(this).addClass("span_active")
        }else if(bar_arr>index-2){
          /!*  $(this).previousSibling(".span_bar").addClass("span_circle");*!/
            $(this).addClass("span_circle");
        }
*/

        /*如果字体的索引 =index-1  黑色*/
        /*如果字体的索引 ！=index-1 灰色*/
    }
}

Filing.init();