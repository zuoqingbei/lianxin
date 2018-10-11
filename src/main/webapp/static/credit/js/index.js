
let Index = {
    init(){
        this.initTable();
        this.dateForm(); 
        this.popperFilter();
        this.hideShowStyle();
        this.searchEvent();
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#dead_date'
        });
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
			var checked=[];
		    	 var checkchar=""
		                 $("input[name='status']:checked").each(function(i){
		                       checked[i] = $(this).val();
		                       checkchar+=checked[i]+","
		                 });
		                 console.log(checkchar);
		        		$.ajax({
		        			type:"post",
		        			url:"/credit/front/home/list",
		        			data:{"attr.custom_id":$("#custom_id").find("option:selected").val(),
		        				"attr.continent":$("#continent").find("option:selected").val(),
		        				"attr.country":$("#country").find("option:selected").val(),
		        				"attr.end_date":$("#dead_date").val(),
		        				"attr.agent_id":$("#agentId").find("option:selected").val(),
		        				"attr.company_by_report":$("#txt_search_departmentname").val(),
		        				"attr.right_company_name_en":$("#txt_search_companyEngName").val(),
		        				"status":checkchar},
		        			 dataType:"json",
		        			 success:function(data){
		        			 	 $("#table").bootstrapTable("load",data)
		        			 }
		        		});

       		getMessage();
      })

      /**点击重置按钮 */
      $("#btn_reset").click(function(){
        $('.form-check-input:checkbox').removeAttr('checked');
        $("#formSearch div input").val("");
      })
    },
    searchEvent(){

      $("#btn_query").click(function(){
        let companyName = $("#txt_search_departmentname").val();//公司名称
        let orderCName = $("#txt_search_companyEngName").val();//订单公司名称
        let deadDate = $("#dead_date").val();//到期日期
        let client = $("#client option:selected").val();//客户
        let proxy = $("#proxy option:selected").val();//代理
        let country = $("#country option:selected").val();//国家

        console.log(companyName,orderCName,deadDate,client,proxy,country)

        /***发起ajax请求 获取表格数据*/
      })
    },
    hideShowStyle(){
      /**展开收起样式 */
      $("#hideBtn").click((e)=>{
		 if($('#hideBtn').text().trim() === '收起'){
	         $("#btnCollapse").css({'height':'0',"overflow":'hidden'})
	         $('#hideBtn').html('展开 <i class="fa fa-angle-down"></i>')
	         $(".fixed-table-body").css({'height':'115%','transition':'all .1s'})
	         $(".bootstrap-table .table:not(.table-condensed) > tbody > tr > td").css({"padding":"12px",'transition':'all .1s'})
	        }else if($('#hideBtn').text().trim() === '展开'){
	         $("#btnCollapse").css({'height':'3rem',"overflow":'visibility'})
	         $('#hideBtn').html('收起 <i class="fa fa-angle-up"></i>')
	         $(".fixed-table-body").css({'height':'100%','transition':'all .1s'})
	         $(".bootstrap-table .table:not(.table-condensed) > tbody > tr > td").css({"padding":"8px",'transition':'all .1s'})
	        }
    })
    },
    initTable(){
        
        const $table = $('#table');
        let _this = this
  

        $table.bootstrapTable({
            height: $(".table-content").height()*0.75,
            columns: [
                 {
                  title: '订单号',
                  field: 'num',
                  align: 'center',
                  valign: 'middle',
                  formatter:function(value,row,index){ 
  
                	var url = '<a href="#" style="color:#1890ff" onclick="orderinfo(\'' + row.id + '\')">' + value + '</a>  '; 
                	return url; 
              		} 
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
                  title: `处理状态 &nbsp;<i class="fa fa-filter"></i>`,
                  field: 'statuName',
                  align: 'center',
                  valign: 'middle',
                
                }, {
                  title: '订单公司名称',
                  field: 'englishName',
                  align: 'center',
                  valign: 'middle',
                },{
                  title: '公司中文名称',
                  field: 'companyName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '国家',
                  field: 'countryName',
                  align: 'center',
                  valign: 'middle',
                }, 
				{
                  title: '是否有财务信息',
                  field: 'is_hava_finance',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '最近财务信息年份',
                  field: 'last_fiscal_year',
                  align: 'center',
                  valign: 'middle',
                },
                {
                  title: '报告类型',
                  field: 'reportType',
                  align: 'center',
                  valign: 'middle',
                }, 
				{
                  title: '报告员',
                  field: 'reportName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '翻译员',
                  field: 'translateName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '分析员',
                  field: 'analyzeName',
                  align: 'center',
                  valign: 'middle',
                },
                /*{
                  field: 'operate',
                  title: '操作',
                  align: 'center',
                  events: {
                    "click .detail":(e,value,row,index)=>{
                      console.log(row)
                    }
                  },
                  formatter: _this.operateFormatter
                }*/
              
            ],
           // url : 'firmSoftTable.action', // 请求后台的URL（*）
           // method : 'post', // 请求方式（*）post/get
             url : '/credit/front/home/list', // 请求后台的URL（*）
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
            sortable: true,                     //是否启用排序
            sortOrder: "desc",
            sortName:"receiver_date",
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
            	console.log(params);
              return {//这里的params是table提供的  
                  pageNo: params.pageNumber,//页码
                  recordsperpage: params.pageSize,//每页多少条
                  sortName:params.sortName,
                  sortOrder:params.sortOrder
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
        return '<a href="create_order.html" class="detail">详情</a>'
      }       
    }
Index.init();




