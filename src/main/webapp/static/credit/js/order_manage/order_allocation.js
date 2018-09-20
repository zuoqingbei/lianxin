let Allocation = {
    init(){
        /**初始化函数 */
    	this.pageNumber = "";
    	this.pageSize = "";
    	this.sortName = "";
    	this.sortOrder = "";
    	this.reportt = "";
        this.initTable();
        this.searchEvent();
        this.popperFilter();
        this.modalSubmit();
        this.toOrderDetail();
    },
    toOrderDetail(){
        /**点击订单号跳转订单详情 */
        console.log($(".fixed-table-body-columns table tbody"));
        
        $(".fixed-table-body-columns table tbody").click(function(e){
            e = e || window.event;
            let order_num = $(e.target).text();
            window.location.href = 'order_detail.html?order_num='+order_num;
        })
    },
    modalSubmit(){
        /**模态框提交事件 */
        $("#modal_submit").click(function(){
            let reporter = $("#reporter_select option:selected").val();
            let remarks = $("#remarks").val();
            let id = $("#orderId").val();
            //console.log(reporter,remarks);
            $.ajax({
       			type:"post",
       			url:"/credit/front/orderProcess/reallocationSave",
       			data:"model.report_user="+reporter+"&model.remarks="+remarks+"&model.id="+id,
       			dataType:"json",
       			success:function(data){
       			//提交成功关闭模态窗
       			 $(".modal-header .close").trigger("click");
       			//回显
       			console.log("提交成功,开始回显:"+data);
       			 $.ajax({
       				type:"post",
           			url:"/credit/front/orderProcess/reallocationJson",
           			data:"model.report_user="+reportt+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder,
           			dataType:"json",
           			success:function(obj){
           				console.log("回显的数据:"+obj);
           			 	 $("#table").bootstrapTable("load",obj)
           			 }
       			 })
       			 
       			console.log("回显完毕");
       			}
            	
       		})
       		
            
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
    searchEvent(){
        /**搜索事件 */
        $("#btn_query").click(function(){
          let reporter = $("#txt_search_reporter").val();//报告员
          console.log(reporter)
          /***发起ajax请求 获取表格数据*/
          $.ajax({
       			type:"post",
       			url:"/credit/front/orderProcess/reallocationJson",
       			data:"model.report_user="+reporter,
       			dataType:"json",
       			success:function(data){
       				console.log(data);
       			 	 $("#table").bootstrapTable("load",data)
       			 }
       		})
        })
      },
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this
  

        $table.bootstrapTable({
            height: $(".table-content").height()/7*6,
            columns: [
                 {
                  title: '订单号',
                  field: 'num',
                  align: 'center',
                  valign: 'middle',
                },{
                  field: 'receiver_date',
                  title: '订单日期',
                  sortable: true,
                  align: 'center'
                }, {
                  field: 'end_date',
                  title: '到期日期',
                  sortable: true,
                  align: 'center',
                }, {
                  title: '客户代码',
                  field: 'custom_id',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: `订单状态 &nbsp;<i class="fa fa-filter"></i>`,
                  field: 'statusName',
                  align: 'center',
                  valign: 'middle',
                
                }, {
                  title: '订单公司名称',
                  field: 'right_company_name_en',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '公司中文名称',
                  field: 'company_by_report',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '国家',
                  field: 'country',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '是否有财务信息',
                  field: 'is_hava_finance',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '最近财务信息年份',
                  field: 'last_fiscal_year',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '报告类型',
                  field: 'reportType',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '报告员',
                  field: 'reportUser',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '翻译员',
                  field: 'translateUser',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '分析员',
                  field: 'analyzeUser',
                  align: 'center',
                  valign: 'middle',
                },{
                  field: 'operate',
                  title: '操作',
                  align: 'center',
                  events: {
                    "click .detail":(e,value,row,index)=>{
                      console.log(row);
                      $("#custom_id").html(row.custom_id);
                      $("#customId").html(row.customId);
                      $("#receiver_date").html(row.receiver_date);
                      $("#country").html(row.country);
                      $("#reportType").html(row.reportType);
                      $("#reportLanguage").html(row.reportLanguage);
                      $("#companyNames").html(row.companyNames);
                      $("#custom_id").html(row.custom_id);
                      $("#speed").html(row.speed);
                      $("#user_time").html(row.user_time);
                      $("#companyZHNames").html(row.companyZHNames);
                      $("#reporter_select").html(row.seleteStr);
                      $("#orderId").val(row.id);
                      $("#num").html(row.num);
                      $("#remarks").val("");
                      pageNumber = row.pageNumber;
                      console.log("pageNumber====="+pageNumber);
                      pageSize = row.pageSize;
                  	  sortName = row.sortName;
                  	  sortOrder = row.sortOrder;
                  	  reportt = row.report_userKey;
                  	  console.log("report_userKey====="+row.report_userKey);
                    }
                  },
                  formatter: _this.operateFormatter
                }
              
            ],
            url : '/credit/front/orderProcess/reallocationJson', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:10,
            pageList: [10 , 20],
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            fixedColumns: true,
            fixedNumber: 1,
            queryParamsType:'',
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
              console.log(params)
              return {//这里的params是table提供的  
            	  pageNumber: params.pageNumber,//从数据库第几条记录开始  
            	  pageSize: params.pageSize,//找多少条  
            	  sortName: params.sortName, 
            	  sortOrder: params.sortOrder,
              };  
          },  
          });
          // sometimes footer render error.
          setTimeout(() => {
            $table.bootstrapTable('resetView');
          }, 200);
      },
      operateFormatter(){
        /**操作按钮格式化 */
        return '<a href="javascript:;" class="detail" data-toggle="modal" data-target="#exampleModalCenter">重新分配</a>'
      }       
}


Allocation.init();