let Filing = {
    init(){
        /**初始化函数 */
        this.initTable0();
        this.initTable();
        this.initTable();
        /* 股东及管理层背景*/
        this.initTable2();
        /*投资情况*/
        this.initTable3();
        /*行业情况*/
        this.initTable4();
        /*业务情况*/
        this.initTable5();
        /*采购情况*/
        this.initTable6();
        /*销售情况*/
        this.initTable7();
        /*商标和专利*/
        this.initTable8();
        /*员工情况*/
        this.initTable9();
        /*地址*/
        this.initTable10();
        /*文件预览*/
        /*  this.fileEvent();*/
        /*出资比例环形图*/
        this.initEchartsPie();


    },
    initTable0(){
        /**初始化表格 */
        const $table = $('#table0');
        let _this = this;


        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '级别',
                    //: true,
                    align: 'left',
                    width:"25%"
                }, {
                    field: 'deadDate',
                    title: '',
                    //sortable: true,
                    align: 'left',
                    width:"25%"
                }, {
                    title: '建议信用额度',
                    field: 'clientCode',
                    align: 'left',
                    width:"25%"
                }, {
                    title: `信用分析报告`,
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
                    title: '行业代码',
                    //: true,
                    align: 'left',
                    width:'50%'
                }, {
                    field: 'deadDate',
                    title: '行业名称',
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
    initTable5(){
        /**初始化表格 */
        const $table = $('#table5');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '产品',
                    //: true,
                    align: 'left',
                    width:'25%'
                }, {
                    field: 'deadDate',
                    title: '进口',
                    //sortable: true,
                    align: 'left',
                    width:'25%'
                }, {
                    field: 'orderDate',
                    title: '出口',
                    //: true,
                    align: 'left',
                    width:'25%'
                }, {
                    field: 'deadDate',
                    title: '海关代码',
                    //sortable: true,
                    align: 'left',
                    width:'25%'
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
    initTable6(){
        /**初始化表格 */
        const $table = $('#table6');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '国家',
                    //: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'deadDate',
                    title: '百分比',
                    //sortable: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'orderDate',
                    title: '结算方式',
                    //: true,
                    align: 'left',
                    width:'33.333%'
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
    initTable7(){
        /**初始化表格 */
        const $table = $('#table7');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '国家',
                    //: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'deadDate',
                    title: '百分比',
                    //sortable: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'orderDate',
                    title: '结算方式',
                    //: true,
                    align: 'left',
                    width:'33.333%'
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
    initTable8(){
        /**初始化表格 */
        const $table = $('#table8');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '注册号',
                    //: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'deadDate',
                    title: '注册日期',
                    //sortable: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'orderDate',
                    title: '商标国家',
                    //: true,
                    align: 'left',
                    width:'33.333%'
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
    initTable9(){
        /**初始化表格 */
        const $table = $('#table9');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '员工人数',
                    //: true,
                    align: 'left',
                    width:'50%'
                }, {
                    field: 'deadDate',
                    title: '主要职能部门',
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
    initTable10(){
        /**初始化表格 */
        const $table = $('#table10');
        let _this = this;
        $table.bootstrapTable({
            height: $(".order-content").height(),
            columns: [
                {
                    field: 'orderDate',
                    title: '经营地址',
                    //: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'deadDate',
                    title: '所有权',
                    //sortable: true,
                    align: 'left',
                    width:'33.333%'
                }, {
                    field: 'orderDate',
                    title: '面积',
                    //: true,
                    align: 'left',
                    width:'33.333%'
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
    }
};

Filing.init();