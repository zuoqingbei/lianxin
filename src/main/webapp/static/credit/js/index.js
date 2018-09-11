
let Index = {
    init(){
        this.initTable();
        this.dateForm();
        this.initReset();
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
    initReset(){
    	/**重置form表单**/
    	const $btnReset = $('.btn-light');
    	$btnReset.on('click',function(){
    		$("#formSearch input.search-input").val("");
    		$("#formSearch select.search-input").val("");
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


        /**发起ajax请求  获取表格数据*/
      })
    },
    searchEvent(){

      $("#btn_query").click(function(){
       		$.ajax({
       			type:"post",
       			url:"home/list",
       			data:$("#formSearch").serialize(),
       			 dataType:"json",
       			 success:function(data){
       			 	 $("#table").bootstrapTable("load",data)
       			 }
       		})

        /***发起ajax请求 获取表格数据*/
      })
    },
    hideShowStyle(){
      /**展开收起样式 */
      $("#hideBtn").click(()=>{
          if($('#hideBtn').text().trim() === '收起'){
            $('#hideBtn').html('展开 <i class="fa fa-angle-down"></i>')
          }else if($('#hideBtn').text().trim() === '展开'){
            $('#hideBtn').html('收起 <i class="fa fa-angle-up"></i>')
          }
      })
    },
    initTable(){
        
        const $table = $('#table');
        let _this = this
  

        $table.bootstrapTable({
            height: $(".table-content").height()/3*2,
            columns: [
                 {
                  title: '订单号',
                  field: 'id',
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
                  title: `处理状态 &nbsp;<i class="fa fa-filter"></i>`,
                  field: 'status',
                  align: 'center',
                  valign: 'middle',
                
                }, {
                  title: '订单公司名称',
                  field: 'companyName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '国家',
                  field: 'countryName',
                  align: 'center',
                  valign: 'middle',
                }, /*{
                  title: '是否有财务信息',
                  field: 'report_type',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '最近财务信息年份',
                  field: 'reportType',
                  align: 'center',
                  valign: 'middle',
                },*/ {
                  title: '报告类型',
                  field: 'report_type',
                  align: 'center',
                  valign: 'middle',
                }, /*{
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
                },*/{
                  field: 'operate',
                  title: '操作',
                  align: 'center',
                  events: {
                    "click .detail":(e,value,row,index)=>{
                      console.log(row)
                    }
                  },
                  formatter: _this.operateFormatter
                }
              
            ],
            url : 'home/list', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:10,
            pageList: [5,10 , 20],
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            fixedColumns: true,
            fixedNumber: 1,
            queryParamsType:'',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
              return {//这里的params是table提供的  
                  pageNo: params.pageNumber,//从数据库第几条记录开始  
                  recordsperpage: params.pageSize//找多少条  
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
        return '<a href="javascript:;" class="detail">详情</a>'
      }       
    }
Index.init();




