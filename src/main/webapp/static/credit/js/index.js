
let Index = {
    init(){
        this.initTable();
        this.dateForm();
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#dead_date'
        });
    },
    initTable(){
        
        const $table = $('#table');
        let _this = this
  

        $table.bootstrapTable({
            height: $(".table-content").height()/3*2,
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
                  title: '处理状态',
                  field: 'doState',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '订单公司名称',
                  field: 'orderComName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '国家',
                  field: 'country',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '报告类型',
                  field: 'reportType',
                  align: 'center',
                  valign: 'middle',
                },{
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
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:10,
            pageList: [10 , 20, 50, 100],
            iconsPrefix:'fa',
            locales:'zh-CN'
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




