let OrderDetail = {
    init() {
        this.rows = JSON.parse(localStorage.getItem("row"));
        console.log('--rows', this.rows)
        this.initContent();
        /*/!**初始化函数 *!/
        this.initTable();
       /!* 股东及管理层背景*!/
        this.initTable2();
        /!*投资情况*!/
        this.initTable3();
        /!*质检意见*!/
        this.initTable4();
        /!*附件*!/
        //this.fileJudge();
         /!*出资比例环形图*!/
        this.initEchartsPie();*/
    },
    initContent() {
        let _this = this;
        let id = this.rows.id;
        let reportType = this.rows.report_type;
        $.get(`${BASE_PATH}credit/front/getmodule/detail`,
            {id, reportType, type: 0},
            (data) => {
                setTimeout(() => {
                    console.log('--data', data)
                    _this.data = data;
                    _this.setHeader();
                    _this.setTabs();
                    _this.setContent();
                }, 0)
            }
        )
    },
    // 设置内容
    setContent() {
        let $moduleWrap = $('<div class="module-wrap bg-f company-info"></div>');
        let $moduleTitle = $('<div class="l-title"></div>');
        this.data.modules.forEach((item) => {
            let smallModuleType = item.smallModileType;
            let $wrap = $moduleWrap.clone().append($moduleTitle.clone()
                .attr('id', item.title.id).text(item.title.temp_name));
            switch (smallModuleType) {
                //表单类型
                case '0':
                    let contentHtml = '';
                    item.contents.forEach((item) => {
                        contentHtml += `
                            <div class="col-md-4 mt-2 mb-2">
                                <span>${item.temp_name}：</span>
                                <span id="${item.column_name}"></span>
                            </div>`
                    });
                    $wrap.append(`
                        <div class="order-detail mb-4 order-content">
                            <div class="row mt-2 mb-2">${contentHtml}</div>
                        </div>`);
                    //绑数
                    let urlArr = item.title.data_source.split("*");
                    let url = '';
                    urlArr.forEach((param, index) => {
                        if (index === 0) {
                            url = param;
                        } else {
                            if (param === 'orderId') {//这里的orderId对应rows的id，和填报配置中不一样
                                url += `&${param}=${this.rows.id}`
                            } else {
                                url += `&${param}=${this.rows[param]}`
                            }
                        }
                    });
                    url += `&conf_id=${item.title.id}`;
                    $.get(BASE_PATH + 'credit/front/ReportGetData/' + url, (data) => {
                        if (data.rows) {
                            console.log(BASE_PATH + 'credit/front/ReportGetData/' + url, data.rows[0]);
                            $wrap.find('[id]').each(function (index, item) {
                                let id = $(this).attr('id');
                                $(this).text(data.rows[0][id])
                            })
                        }
                    });
                    break;
                case '1':

                    break;
            }
            $(".main .table-content").append($wrap);

        })
        /*let tabsHtml = '';
        this.data.tabFixed.forEach((item,index) =>{
            tabsHtml += `<li><a href="#tab${index}">${item.temp_name}</a></li>`
        });
        $("#tabs").html(tabsHtml).children().eq(0).addClass('tab-active');*/
    },
    // 设置tabs标签
    setTabs() {
        let tabsHtml = '';
        this.data.tabFixed.forEach((item, index) => {
            tabsHtml += `<li><a href="#${item.anchor_id}">${item.temp_name}</a></li>`
        });
        $("#tabs").html(tabsHtml).children().eq(0).addClass('tab-active');
    },
    // 设置头部信息
    setHeader() {
        $("#orderName").text(this.data.defaultModule[0].temp_name + " :")
        $("#orderNum").text(this.rows.num);
        $("#status").text(this.rows.statusName);
    },
    /*出资比例环形图*/
    initEchartsPie() {
        let ec001_pie = echarts.init($("#ec001_pie")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_pie);
        ec001_pie.setOption({
            series: [
                {
                    data: [
                        {value: 335, name: '直接访问'},
                        {value: 310, name: '邮件营销'}
                    ],
                }
            ]
        });
    },

    /*附件*/
    fileJudge() {
        let filename = "";
        let filetype = "";
        //let file_content=[];
        let file_content = [{name: "出资比例环形图", type: "doc", url: ""}, {
            name: "出资比例环形图",
            type: "png",
            url: ""
        }, {name: "出资比例环形图", type: "pdf", url: ""}];
        /* 循环遍历文件数组
           取出文件名称
           对文件名称进行判断*/
        for (var i = 0; i < file_content.length; i++) {
            filename = file_content[i].name;
            filetype = file_content[i].type;
            let num = filename.split("").length; //转化成数组后求文件名称的长度
            /*如果文件名称大于四个字 截取前两个字和后两个字*/
            if (num > 4) {
                filename = filename.substr(0, 2) + '..' + filename.substr(filename.length - 2, 2) + '.' + filetype;
            }
            //根据文件类型分配图片
            let fileicon = '';
            if (filetype === 'doc' || filetype === 'docx') {
                fileicon = '../imgs/order/word.png'
            } else if (filetype === 'xlsx' || filetype === 'xls') {
                fileicon = '../imgs/order/Excel.png'
            } else if (filetype === 'png') {
                fileicon = '../imgs/order/PNG.png'
            } else if (filetype === 'jpg') {
                fileicon = '../imgs/order/JPG.png'
            } else if (filetype === 'pdf') {
                fileicon = '../imgs/order/PDF.png'
            } else {
                Public.message("info", "不支持上传此种类型文件！");
                return
            }
            /*写入新的文件名称*/
            $(".accessory_box").append(
                ` <div>
    <div class="uploadFile mt-3 mr-3 ml-3">
    <img src=${fileicon} />
    <p class="filename">${filename}</p>
    </div>
    <div class="order-yulan  mt-3 ml-3"><a href="#">预览</a></div></div>`);
        }
        if (file_content.length == 0) {
            $(".accessory_box").append(`
  <div>
                            <div class="uploadFile mt-3 mr-3 ml-3">
                                <div class="over-box">
                                    <img src="../imgs/order/！.png" class="m-auto"/>
                                    <p class="mt-2">暂无附件</p>
                                </div>
                            </div>
                            <div class="order-yulan  mt-3 ml-3 "><a href="#">预览</a></div>
                        </div>
`);
        }
    },
    initTable() {
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
                    width: "25%"
                }, {
                    field: 'deadDate',
                    title: '变更项',
                    //sortable: true,
                    align: 'left',
                    width: "25%"
                }, {
                    title: '变更前',
                    field: 'clientCode',
                    align: 'left',
                    width: "25%"
                }, {
                    title: `
变更后
`,
                    field: 'doState',
                    align: 'left',
                    width: "25%"
                },
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable2() {
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
                    width: '33.3333%'
                }, {
                    field: 'deadDate',
                    title: '国籍/国家',
                    //sortable: true,
                    align: 'left',
                    width: '33.3333%'
                }, {
                    title: '出资比例（%）',
                    field: 'clientCode',
                    align: 'left',
                    width: '33.3333%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable3() {
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
                    width: '50%'
                }, {
                    field: 'deadDate',
                    title: '出资比例（%）',
                    //sortable: true,
                    align: 'left',
                    width: '50%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable4() {
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
                }, {
                    field: 'deadDate',
                    title: '操作时间',
                    //sortable: true,
                    align: 'left',
                }, {
                    field: 'deadDate',
                    title: '质检意见',
                    //sortable: true,
                    align: 'left',
                }, {
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
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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

    initTable() {
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
                    width: "25%"
                }, {
                    field: 'deadDate',
                    title: '变更项',
                    //sortable: true,
                    align: 'left',
                    width: "25%"
                }, {
                    title: '变更前',
                    field: 'clientCode',
                    align: 'left',
                    width: "25%"
                }, {
                    title: `
变更后`,
                    field: 'doState',
                    align: 'left',
                    width: "25%"
                },
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable2() {
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
                    width: '33.3333%'
                }, {
                    field: 'deadDate',
                    title: '国籍/国家',
                    //sortable: true,
                    align: 'left',
                    width: '33.3333%'
                }, {
                    title: '出资比例（%）',
                    field: 'clientCode',
                    align: 'left',
                    width: '33.3333%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable3() {
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
                    width: '50%'
                }, {
                    field: 'deadDate',
                    title: '出资比例（%）',
                    //sortable: true,
                    align: 'left',
                    width: '50%'
                }
            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            sidePagination: 'server',
            /*pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],*/
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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
    initTable4() {
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
                }, {
                    field: 'deadDate',
                    title: '操作时间',
                    //sortable: true,
                    align: 'left',
                }, {
                    field: 'deadDate',
                    title: '质检意见',
                    //sortable: true,
                    align: 'left',
                }, {
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
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            //fixedColumns: true,   // 固定列
            // fixedNumber: 1,
            queryParamsType: '',
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


    step(index) {
        /*如果圆圈的索引 =index 高亮显示  .circle_active*/
        /*如果圆圈的索引 <index 蓝色 span_active*/
        /*如果圆圈的索引 >index 灰色 span_circle*/
        let circle_arr = $(".bar-span-box .span_circle");
        let bar_arr = $(".bar-span-box .span_bar");
        var world_arr = $(".bar_box>ul>li");
        let barIndex = index - 1;
        for (var i = 0; i < circle_arr.length; i++) {
            $(circle_arr[i]).removeClass("circle_active").removeClass("span_active").removeClass("span_circle");
            $(bar_arr[i]).removeClass("bar_active").removeClass("span_circle");
            $(world_arr[i]).removeClass("world_active");
            /*判断圆圈*/
            if (i == index) {
                $(circle_arr[i]).addClass("circle_active");
                $(world_arr[i]).children("span").addClass("world_active");
            } else if (i < index) {
                $(circle_arr[i]).addClass("span_active");
            } else if (i > index) {
                $(circle_arr[i]).addClass("span_circle");
            }
            /*判断横条*/
            if (barIndex >= 0) {
                if (i <= barIndex) {
                    $(bar_arr[i]).addClass("bar_active")
                } else if (i > barIndex) {
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

OrderDetail.init();