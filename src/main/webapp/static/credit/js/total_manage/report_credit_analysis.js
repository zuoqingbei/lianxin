let creditAnalysis = {
    // 初始化
    pageNumber: 1,
    pageSize: 5,
    selectNum: 0,
    selectMoney: 0,
    selectAll: false,
    selectedData: new Map(),
    selectedData_thisPage: [],
    init() {
        this.initTable();
        this.initDateInput();
        this.selectItem();
		this.searchEvent();
		this.butExport();
    },
    /**
     * 页面变量中设置勾选的数据
     */
    setNumAfterChecked() {
        let oT = this; //oT为outThis或者外部对象this
        oT.selectedData.set(oT.pageNumber, oT.selectedData_thisPage);

        for (let key of oT.selectedData.keys()) {
            oT.selectNum += oT.selectedData.get(key).length;
            oT.selectedData.get(key).forEach(function (item) {
                if (!Number.isNaN(item.money))
                    oT.selectMoney += item.money;
            })
        }
        $("#commission").text(this.selectMoney);
        $("#selectedTotalNum").text(this.selectNum);
        // console.log('oT.selectedData', oT.selectedData, 'selectMoney', this.selectMoney,'当前页内容',oT.selectedData_thisPage)
    },
    selectItem() {
        let oT = this;
        // oT.selectedData_thisPage = [];
        //全选
        $("[name=btSelectAll]").click(function () {
            oT.selectedData_thisPage = [];
            [oT.selectNum, oT.selectMoney] = [0, 0];
            let $btSelectItems = $(this).parents('.bootstrap-table').find('.fixed-table-body-columns [name=btSelectItem]');
            if ($(this).is(":checked")) {
                $btSelectItems.prop('checked', true);
                oT.selectAll = true;
            } else {
                $btSelectItems.removeAttr('checked', false);
                oT.selectAll = false;
            }
            $btSelectItems.change();
            // oT.setNumAfterChecked(selectedData_thisPage);
        }).css({
            position: 'relative',
            left: '.0625rem',
            top: '.25rem'
        });
        $('.table-content').on('change', '[name=btSelectItem]', function () {
            [oT.selectNum, oT.selectMoney] = [0, 0];
            let index = $(this).parents("tr").index();
            if ($(this).is(":checked")) {//加
                oT.selectedData_thisPage.push({
                    index,
                    money: parseFloat($('#table tbody tr').eq(index).find('td').eq($('.fixed-table-header').find('[data-field = reportType]').index()).text())
                });
            } else {//减
                oT.selectedData_thisPage.forEach(function (item, thisIndex, array) {
                    if (item.index === index) {
                        array.splice(thisIndex, 1);
                    }
                });
            }
            oT.setNumAfterChecked();


        });
    },
	 searchEvent(){
		let that=this;
        $("#btn_query").click(function(){
            //alert($("#time").val())
        	/**发起ajax请求  获取表格数据*/
           $("#table").bootstrapTable("refresh",{

				query:{
				  pageNo: that.paramsObj.pageNumber,//页码
                  recordsperpage:  that.paramsObj.pageSize,//每页多少条
                  sortName: that.paramsObj.sortName,
                  sortOrder: that.paramsObj.sortOrder,
    			  end_date:$("#time").val(),
    			  reportername:$("#txt_search_reporter").find("option:selected").val(),
    			 
				}
			});
            let deadDate = $("#time").val();//到期日期
          
        })
    },
    butExport(){
		let that=this;
		 $("#btn_export").click(function(){
			var reportername=$("#txt_search_reporter").find("option:selected").val();
			var time=$("#time").val();
			
			// $.post("/credit/settle/SettleExport", { time: time, customerId:customerId,agentId:agentId});
			 window.document.location.href="/credit/achievements/AchievementsExport?time="+time+"&reportername="+reportername;	    
					   
			 
		 })
    },
    initTable() {
        let oT = this;
        const $table = $("#table");
        $table.bootstrapTable({
            height: $(".table-content").height() * 0.82,
            columns: [
                      {
                          checkbox: true,
                          visible: true,                  //是否显示复选框
                          width: '18rem',
                      }, {
                         title: '订单号',
                         field: 'num',
                      },{
                          title: '姓名',
                          field: 'realname',
                      }, {
                          field: 'receiver_date',
                          title: '订单日期',
                          sortable: true,
                      }, {
                          field: 'end_date',
                          title: '到期日期',
                          sortable: true,
                      }, {
                          title: '客户代码',
                          field: 'custom_id',
                      }, {
                          title: '订单公司名称',
                          field: 'right_company_name_en',
                      }, {
                          title: '公司中文名称',
                          field: 'company_by_report',
                      }, {
                          title: '提成',
                          field: 'money',
                      }

            ]/*.map(function (item,index) {
                if(index === 0){
                    item.align = 'right'
                }else{
                    item.align = 'center';
                }

                item.valign = 'middle';
                return item;
            })*/,
           url : '/credit/achievements/list', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 10,
            pageList: [10,20, 30],
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            fixedColumns: true,
            fixedNumber: 1,
            queryParamsType:'',
            sortable: true,                     //是否启用排序
            sortOrder: "desc",
            sortName:"receiver_date",
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
            	console.log(params);
            	oT.paramsObj = params;
            	oT.pageNumber = params.pageNumber;
              return {//这里的params是table提供的  
                  pageNo: params.pageNumber,//页码
                  recordsperpage: params.pageSize,//每页多少条
                  sortName:params.sortName,
                  sortOrder:params.sortOrder,
    			  end_date:$("#time").val(),
    			  reportername:$("#txt_search_reporter").find("option:selected").val(),
              };  
            },
            onLoadSuccess(){
                // 加入footer
                $(".fixed-table-footer").html(`<footer class="total d-flex justify-content-between">
                    <div>已选择<strong id="selectedTotalNum">0</strong>项</div>
                    <div>提成合计：<strong id="commission">0.00</strong><span class="unit">CNY</span></div>
                </footer>`).show();
                // console.log(oT.this);
                $("[name=btSelectAll]").prop('checked', false);
                // if(oT.selectedData){
                oT.selectedData_thisPage = oT.selectedData.get(oT.pageNumber)?oT.selectedData.get(oT.pageNumber):[];

                if (oT.selectedData_thisPage) {
                    if (oT.selectedData_thisPage.length === $("#table>tbody>tr").length) {
                        $table.parents('.bootstrap-table').find('.fixed-table-header-columns [name=btSelectAll]').prop('checked', true)
                    }
                    oT.selectedData_thisPage.forEach(function (item) {
                        // $table.find('.fixed-table-body-columns [name=btSelectItem]').eq(item.index).prop('checked', true)
                        $table.parents('.bootstrap-table').find('.fixed-table-body-columns [name=btSelectItem]').eq(item.index).prop('checked', true)
                    });
                    $("#commission").text(oT.selectMoney);
                    $("#selectedTotalNum").text(oT.selectNum);
                }
                console.log('已选择的所有内容', oT.selectedData, '当前页内容',oT.selectedData_thisPage)
                // }
            }
        });


        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initDateInput() {
        laydate.render({
            elem: '#time'
            , range: '~'
        })
    }
};

creditAnalysis.init();










