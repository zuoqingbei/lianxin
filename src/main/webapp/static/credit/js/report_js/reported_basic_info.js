
let Reported = {
    init(){
        /**函数初始化 */
        this.dateForm(); 
        this.initTable();
        this.addRcordRow();
        this.addShareholdersInfoRow();
        this.addShareholdersDetailRow();
        this.addInvestmentRow();
        this.addManagementRow();
        this.tabChange();
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#qy_create'
        });
        laydate.render({
            elem: '#qy_date'
        });
        laydate.render({
            elem: '#modal_record_date'
        });
        laydate.render({
            elem: '#modal_shareholdersdetail_time'
        });
    },
    tabChange(){
        /**tab切换事件 */
        $(".tab-group span").click(()=>{
            $(this).addClass("tab-active").siblings().removeClass("tab-active")
        })
    },
    addRcordRow(){
        /**历史纪录新增或修改一行 */
        let _this = this
        $("#record_modal_save").click(()=>{
            let modal_record_date = $("#modal_record_date").val();
            let modal_change_items = $("#modal_change_items").val();
            let modal_change_font = $("#modal_change_font").val();
            let modal_change_back = $("#modal_change_back").val();
            
            let _data = {
                date:modal_record_date?modal_record_date:'-',
                change_item:modal_change_items?modal_change_items:'-',
                change_font:modal_change_font?modal_change_font:'-',
                change_back:modal_change_back?modal_change_back:'-'
            }
            if(_this.recordIndex !== undefined) {
                
                $('#tableRecord').bootstrapTable('updateRow', {
                    index:_this.recordIndex,
                    row:_data
                })
            }else {

                $('#tableRecord').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_record").click(()=>{
            _this.recordIndex = undefined
        })
    },
    addShareholdersInfoRow(){
        /**股东信息新增一行或修改 */
        let _this = this
        $("#shareholders_info_modal_save").click(()=>{
            let name = $("#modal_shareholdersinfo_name").val();
            let country = $("#modal_shareholdersinfo_country").val();
            let money = $("#modal_shareholdersinfo_money").val();
            
            let _data = {
                name:name?name:'-',
                country:country?country:'-',
                money:money?money:'-'
            }
            if(_this.shareholdersInfoIndex !== undefined) {
                
                $('#tableShareholdersInfo').bootstrapTable('updateRow', {
                    index:_this.shareholdersInfoIndex,
                    row:_data
                })
            }else {

                $('#tableShareholdersInfo').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_shareholders_info").click(()=>{
            _this.shareholdersInfoIndex = undefined
        })
    },
    addShareholdersDetailRow(){
        /**股东详情新增一行或修改 */
        let _this = this
        $("#shareholders_detail_modal_save").click(()=>{
            let name = $("#modal_shareholdersdetail_name").val();
            let time = $("#modal_shareholdersdetail_time").val();
            let money = $("#modal_shareholdersdetail_money").val();
            let tel = $("#modal_shareholdersdetail_tel").val();
            let fax = $("#modal_shareholdersdetail_fax").val();
            let email = $("#modal_shareholdersdetail_email").val();
            
            let _data = {
                name:name?name:'-',
                create_time:time?time:'-',
                create_money:money?money:'-',
                telephone:tel?tel:'-',
                fax_no:fax?fax:'-',
                email: email?email:'-'
            }
            if(_this.shareholdersDetailIndex !== undefined) {
                
                $('#tableShareholdersDetail').bootstrapTable('updateRow', {
                    index:_this.shareholdersDetailIndex,
                    row:_data
                })
            }else {

                $('#tableShareholdersDetail').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_shareholders_detail").click(()=>{
            _this.shareholdersDetailIndex = undefined
        })
    },
    addInvestmentRow(){
        /**出资情况新增一行或修改 */
        let _this = this
        $("#investment_modal_save").click(()=>{
            let name = $("#modal_investment_name").val();
            let money = $("#modal_investment_money").val();      
            let _data = {
                name:name?name:'-',
                money:money?money:'-'
            }
            if(_this.investmentIndex !== undefined) {
                
                $('#tableInvestment').bootstrapTable('updateRow', {
                    index:_this.investmentIndex,
                    row:_data
                })
            }else {

                $('#tableInvestment').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_investment").click(()=>{
            _this.investmentIndex = undefined
        })
    },
    addManagementRow(){
        /**管理层新增一行或修改 */
        let _this = this
        $("#management_modal_save").click(()=>{
            let name = $("#modal_management_name").val();
            let position = $("#modal_management_position").val();
            let id_card = $("#modal_management_id_card").val();
            let age = $("#modal_management_age").val();
            let tel = $("#modal_management_tel").val();
            let email = $("#modal_management_email").val();

            let _data = {
                name:name?name:'-',
                position:position?position:'-',
                id_card:id_card?id_card:'-',
                age:age?age:'-',
                tel:tel?tel:'-',
                email:email?email:'-',
            }
            if(_this.managementIndex !== undefined) {
                
                $('#tableManagement').bootstrapTable('updateRow', {
                    index:_this.managementIndex,
                    row:_data
                })
            }else {

                $('#tableManagement').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_management").click(()=>{
            _this.managementIndex = undefined
        })
    },
    initTable(){
        let _this = this
        /**历史纪录表初始化 */
        const $tableRecord = $('#tableRecord');
        $tableRecord.bootstrapTable({
            height: $(".table-content1").height()/3*2,
            columns: [
                 {
                  title: '日期',
                  field: 'date',
                  align: 'center',
                  valign: 'middle',
                  width:'20%'
                },{
                  field: 'change_item',
                  title: '变更项',
                  align: 'center',
                  width:'20%'
                }, {
                  field: 'change_font',
                  title: '变更前',
                  align: 'center',
                  width:'20%'
                }, {
                  title: '变更后',
                  field: 'change_back',
                  align: 'center',
                  valign: 'middle',
                  width:'20%'
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width:'20%',
                    events: {
                      "click .edit":(e,value,row,index)=>{
                          _this.recordIndex = index
                        //回显
                        let {date,change_item,change_font,change_back} = row;
                        $("#modal_record_date").val(date)
                        $("#modal_change_items").val(change_item)
                        $("#modal_change_font").val(change_font)
                        $("#modal_change_back").val(change_back)
                        
                      }
                    },
                    formatter: _this.operateFormatterRecord
                  }
            ],
           // url : 'firmSoftTable.action', // 请求后台的URL（*）
           // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
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

          /**股东信息表初始化 */
          const $tableShareholdersInfo = $('#tableShareholdersInfo');
            $tableShareholdersInfo.bootstrapTable({
                height: $(".table-content2").height()/3*2,
                columns: [
                    {
                    title: '姓名',
                    field: 'name',
                    align: 'center',
                    valign: 'middle',
                    editable:true
                    },{
                    field: 'country',
                    title: '国籍/国家',
                    align: 'center'
                    }, {
                    field: 'money',
                    title: '出资比例(%)',
                    align: 'center',
                    },{
                        field: 'operate',
                        title: '操作',
                        align: 'center',
                        events: {
                        "click .edit":(e,value,row,index)=>{
                            _this.shareholdersInfoIndex = index
                            //回显
                        let {name,country,money} = row;
                        $("#modal_shareholdersinfo_name").val(name)
                        $("#modal_shareholdersinfo_country").val(country)
                        $("#modal_shareholdersinfo_money").val(money)
                        }
                    },
                    formatter: _this.operateFormatterShareholdersInfo
                    }
                ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
                pagination: false, //分页
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
            /**股东详情表初始化 */
          const $tableShareholdersDetail = $('#tableShareholdersDetail');
          $tableShareholdersDetail.bootstrapTable({
              height: $(".table-content3").height()/3*2,
              columns: [
                  {
                  title: '名称',
                  field: 'name',
                  align: 'center',
                  valign: 'middle',
                  editable:true
                  },{
                  field: 'create_time',
                  title: '成立时间',
                  align: 'center'
                  }, {
                  field: 'create_money',
                  title: '注册资金',
                  align: 'center',
                  }, {
                    field: 'telephone',
                    title: '联系电话',
                    align: 'center',
                }, {
                    field: 'fax_no',
                    title: '传真号码',
                    align: 'center',
                }, {
                    field: 'email',
                    title: '邮箱地址',
                    align: 'center',
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                    "click .edit":(e,value,row,index)=>{
                        _this.shareholdersDetailIndex = index
                        //回显
                    let {name,create_time,create_money,telephone,fax_no,email} = row;
                    $("#modal_shareholdersdetail_name").val(name)
                    $("#modal_shareholdersdetail_time").val(create_time)
                    $("#modal_shareholdersdetail_money").val(create_money)
                    $("#modal_shareholdersdetail_tel").val(telephone)
                    $("#modal_shareholdersdetail_fax").val(fax_no)
                    $("#modal_shareholdersdetail_email").val(email)
                    }
                },
                formatter: _this.operateFormatterShareholdersDetail
                }
              ],
          // url : 'firmSoftTable.action', // 请求后台的URL（*）
          // method : 'post', // 请求方式（*）post/get
              pagination: false, //分页
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
            /**投资情况表初始化 */
            const $tableInvestment = $('#tableInvestment');
            $tableInvestment.bootstrapTable({
                height: $(".table-content4").height()/3*2,
                columns: [
                    {
                    title: '公司名称',
                    field: 'name',
                    align: 'center',
                    valign: 'middle',
                    editable:true
                    },{
                    field: 'money',
                    title: '出资比例(%)',
                    align: 'center'
                    },{
                      field: 'operate',
                      title: '操作',
                      align: 'center',
                      events: {
                      "click .edit":(e,value,row,index)=>{
                          _this.investmentIndex = index
                          //回显
                      let {name,money} = row;
                      $("#modal_investment_name").val(name)
                      $("#modal_investment_money").val(money)
                     
                      }
                  },
                  formatter: _this.operateFormatterInvestment
                  }
                ],
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
                pagination: false, //分页
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
            /**管理层表初始化 */
          const $tableManagement = $('#tableManagement');
          $tableManagement.bootstrapTable({
              height: $(".table-content3").height()/3*2,
              columns: [
                  {
                  title: '姓名',
                  field: 'name',
                  align: 'center',
                  valign: 'middle',
                  editable:true
                  },{
                  field: 'position',
                  title: '职务',
                  align: 'center'
                  }, {
                  field: 'id_card',
                  title: '身份证号',
                  align: 'center',
                  }, {
                    field: 'age',
                    title: '年龄',
                    align: 'center',
                }, {
                    field: 'tel',
                    title: '联系电话',
                    align: 'center',
                }, {
                    field: 'email',
                    title: '邮箱地址',
                    align: 'center',
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                    "click .edit":(e,value,row,index)=>{
                        _this.managementIndex = index
                        //回显
                    let {name,position,id_card,age,tel,email} = row;
                    $("#modal_management_name").val(name)
                    $("#modal_management_position").val(position)
                    $("#modal_management_id_card").val(id_card)
                    $("#modal_management_age").val(age)
                    $("#modal_management_tel").val(tel)
                    $("#modal_management_email").val(email)
                    }
                },
                formatter: _this.operateFormatterManagement
                }
              ],
          // url : 'firmSoftTable.action', // 请求后台的URL（*）
          // method : 'post', // 请求方式（*）post/get
              pagination: false, //分页
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

      },
      operateFormatterRecord(){
        /**操作按钮格式化 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#recordModalCenter">编辑</a><a href="javascript:;" class="delete">删除</a></div>'
      },  
      operateFormatterShareholdersInfo(){
        /**操作按钮格式化 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersInfoModalCenter">编辑</a><a href="javascript:;" class="delete">删除</a></div>'
      },
      operateFormatterShareholdersDetail(){
        /**操作按钮格式化 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersDetailModalCenter">编辑</a><a href="javascript:;" class="delete">删除</a></div>'
      },    
      operateFormatterInvestment(){
        /**操作按钮格式化 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#investmentModalCenter">编辑</a><a href="javascript:;" class="delete">删除</a></div>'
      },    
      operateFormatterManagement(){
        /**操作按钮格式化 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#managementModalCenter">编辑</a><a href="javascript:;" class="delete">删除</a></div>'
      }    
}

Reported.init();