let Verify = {
    init(){
        /**初始化函数 */
        this.initTable();
        this.toOrderDetail();
        this.operateFormatter();
        this.modalSubmit();
        this.popperFilter();
    },

 
    /**初始化表格 */
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this


        $table.bootstrapTable({
            height: $(".table-content").height()*0.98,
            columns: [
                {
                    title: '订单号',
                    field: 'orderNum',
                    align: 'center',
                    valign: 'middle',
                },{
                    field: 'orderDate',
                    title: '订单日期',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'deadDate',
                    title: '到期日期',
                    sortable: true,
                    align: 'center',
                }, {
                    title: '客户代码',
                    field: 'clientCode',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: `订单状态 &nbsp;<i class="fa fa-filter"></i>`,
                    field: 'doState',
                    align: 'center',
                    valign: 'middle',

                }, {
                    title: '订单公司名称',
                    field: 'orderComName',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '公司中文名称',
                    field: 'orderComName',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '国家',
                    field: 'country',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '是否有财务信息',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '最近财务信息年份',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '报告类型',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '报告员',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '翻译员',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                }, {
                    title: '分析员',
                    field: 'reportType',
                    align: 'center',
                    valign: 'middle',
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        "click .write":(e,value,row,index)=>{
                            Public.goToBasicInfoWrite();
                        }
                    },
                    formatter: _this.operateFormatter
                }

            ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:20,
            pageList: [20 , 30],
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            fixedColumns: true,
            fixedNumber: 1,
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
    /**操作按钮格式化 */
    operateFormatter(){
        return '<a href="javascript:;" class="write">填报</a>'
    },
    toOrderDetail(){
        /**点击订单号跳转订单详情 */
        $(".fixed-table-body-columns table tbody").click(function(e){
            e = e || window.event;
            let order_num = $(e.target).text();
            window.location.href = 'order_detail.html?order_num='+order_num;
        })
    },
    modalSubmit(){
        /**模态框提交事件 */
        $("#modal_submit").click(function(){
            // 提交成功关闭模态窗
            $(".modal-header .close").trigger("click");
        })
    },
    popperFilter(){
        /**筛选图标事件 */
        var referenceElement = document.querySelector(".fa-filter");
        var onPopper = document.querySelector(".deal-state");
        var popper = new Popper(referenceElement, onPopper, {
            placement: 'top'
        });
        /**点击筛选图标 */
        $(".fa-filter").click(function(e){
            let evt = e || window.event;
            evt.stopPropagation();
            $('.deal-state').toggleClass("deal-state-show")
        })
        /**点击任意地方隐藏 */
        $(document).click(function(){
            if($('.deal-state').hasClass("deal-state-show")) {
                $('.deal-state').toggleClass("deal-state-show")
            }
        })
        /**阻止冒泡 */
        $('.deal-state').click(function(e){
            let evt = e || window.event;
            evt.stopPropagation();
        })

        /**点击确定按钮 */
        $(".enterFilter").click(function(){
            $('.deal-state').toggleClass("deal-state-show")
            var value1 = $("#defaultCheck1").prop("checked");

            /**发起ajax请求  获取表格数据*/
        })

        /**点击重置按钮 */
        $(".resetrFilter").click(function(){
            $('.form-check-input:checkbox').removeAttr('checked');
        })
    },
};

Verify.init();