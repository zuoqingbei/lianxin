
let BusinessWrite = {
    init(){
        this.initTable();
        this.dateForm(); 
        this.addRcordRow();
        this.addShareholdersInfoRow();
        this.addShareholdersDetailRow();
        this.addInvestmentRow();
        this.addManagementRow();
        this.addVocationRow();
        this.addYwRow();
        this.addCgRow();
        this.addXsRow();
        this.addCorpRow();
        this.tabChange();
        Public.tabFixed(".tab-bar",".main",120,90);
    },
    tabChange(){
        /**tab切换事件 */
        $(".tab-bar li").click((e)=>{
            $(e.target).addClass("tab-active").parents("li").siblings().children('a').removeClass("tab-active")

            /* 解决锚链接的偏移问题*/
            $("#container ").css('height',"calc(100% - 5.6rem)");
            $(".main ").css('marginBottom',"-.6rem");
        })
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#qy_aa'
        });
        laydate.render({
            elem: '#qy_bb'
        });
        laydate.render({
            elem: '#modal_record_date'
        });
        laydate.render({
            elem: '#modal_shareholdersdetail_time'
        });
        laydate.render({
            elem: '#modal_logo_date'
        });
    },
    addRcordRow(){
        /**历史纪录新增或修改一行 */
        let _this = this
        this.id = 0
        $("#record_modal_save").click(()=>{
            _this.id++;
            let modal_record_date = $("#modal_record_date").val();
            let modal_change_items = $("#modal_change_items").val();
            let modal_change_font = $("#modal_change_font").val();
            let modal_change_back = $("#modal_change_back").val();
            
            let _data = {
                id:_this.id,
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
        this.id_1 = 0
        $("#shareholders_info_modal_save").click(()=>{
            this.id_1++;
            let name = $("#modal_shareholdersinfo_name").val();
            let country = $("#modal_shareholdersinfo_country").val();
            let money = $("#modal_shareholdersinfo_money").val();
            
            let _data = {
                id:this.id_1,
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
        this.id_2 = 0
        $("#shareholders_detail_modal_save").click(()=>{
            this.id_2++; 
            let name = $("#modal_shareholdersdetail_name").val();
            let time = $("#modal_shareholdersdetail_time").val();
            let money = $("#modal_shareholdersdetail_money").val();
            let tel = $("#modal_shareholdersdetail_tel").val();
            let fax = $("#modal_shareholdersdetail_fax").val();
            let email = $("#modal_shareholdersdetail_email").val();
            
            let _data = {
                id:this.id_2,
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
        this.id_3 = 0
        $("#investment_modal_save").click(()=>{
            this.id_3++;
            let name = $("#modal_investment_name").val();
            let money = $("#modal_investment_money").val();      
            let _data = {
                id: this.id_3,
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
        this.id_4 = 0
        $("#management_modal_save").click(()=>{
            this.id_4++;
            let name = $("#modal_management_name").val();
            let position = $("#modal_management_position").val();
            let id_card = $("#modal_management_id_card").val();
            let age = $("#modal_management_age").val();
            let tel = $("#modal_management_tel").val();
            let email = $("#modal_management_email").val();

            let _data = {
                id:this.id_4,
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
    addVocationRow(){
        /**行业信息新增一行或修改 */
        let _this = this
        this.id_5 = 0
        $("#vocation_modal_save").click(()=>{
            this.id_5++;
            let code = $("#modal_vocation_code").val();
            let name = $("#modal_vocation_name").val();

            let _data = {
                id:this.id_5,
                code:code?code:'-',
                name:name?name:'-'
            }
            if(_this.vocationIndex !== undefined) {
                
                $('#tableVocation').bootstrapTable('updateRow', {
                    index:_this.vocationIndex,
                    row:_data
                })
            }else {

                $('#tableVocation').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_vocation").click(()=>{
            _this.vocationIndex = undefined
        })
    },
    addYwRow(){
        /**业务情况新增一行或修改 */
        let _this = this
        this.id_6 = 0
        $("#yw_modal_save").click(()=>{
            this.id_6++;
            let product = $("#modal_yw_product").val();
            let jk = $("#modal_yw_jk").val();
            let ck = $("#modal_yw_ck").val();
            let code = $("#modal_yw_code").val();

            let _data = {
                id:this.id_6,
                product:product?product:'-',
                jk:jk?jk:'-',
                ck:ck?ck:'-',
                code:code?code:'-',
            }
            if(_this.ywIndex !== undefined) {
                
                $('#tableYw').bootstrapTable('updateRow', {
                    index:_this.ywIndex,
                    row:_data
                })
            }else {

                $('#tableYw').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_yw").click(()=>{
            _this.ywIndex = undefined
        })
    },
    addCgRow(){
        /**采购情况新增一行或修改 */
        let _this = this
        this.id_7 = 0
        $("#cg_modal_save").click(()=>{
            this.id_7++;
            let country = $("#modal_cg_country").val();
            let percent = $("#modal_cg_percent").val();
            let js = $("#modal_cg_js").val();

            let _data = {
                id:this.id_7,
                country:country?country:'-',
                percent:percent?percent:'-',
                js:js?js:'-',
            }
            if(_this.cgIndex !== undefined) {
                
                $('#tableCg').bootstrapTable('updateRow', {
                    index:_this.cgIndex,
                    row:_data
                })
            }else {
                $('#tableCg').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_cg").click(()=>{
            _this.cgIndex = undefined
        })
    },
    addXsRow(){
        /**销售情况新增一行或修改 */
        let _this = this
        this.id_8 = 0
        $("#xs_modal_save").click(()=>{
            this.id_8++;
            let country = $("#modal_xs_country").val();
            let percent = $("#modal_xs_percent").val();
            let js = $("#modal_xs_js").val();

            let _data = {
                id:this.id_8,
                country:country?country:'-',
                percent:percent?percent:'-',
                js:js?js:'-',
            }
            if(_this.xsIndex !== undefined) {
                
                $('#tableXs').bootstrapTable('updateRow', {
                    index:_this.xsIndex,
                    row:_data
                })
            }else {
                $('#tableXs').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_xs").click(()=>{
            _this.xsIndex = undefined
        })
    },
    addCorpRow(){
        /**供应商新增一行或修改 */
        let _this = this
        this.id_9 = 0
        $("#corp_modal_save").click(()=>{
            this.id_9++;
            let name = $("#modal_corp_name").val();
            let pro = $("#modal_corp_pro").val();
            let tel = $("#modal_corp_tel").val();
            let time = $("#modal_corp_time").val();
            let cases = $("#modal_corp_case").val();

            let _data = {
                id:this.id_9,
                name:name?name:'-',
                pro:pro?pro:'-',
                tel:tel?tel:'-',
                time:time?time:'-',
                cases:cases?cases:'-',
            }
            if(_this.corpIndex !== undefined) {
                
                $('#tableCorp').bootstrapTable('updateRow', {
                    index:_this.corpIndex,
                    row:_data
                })
            }else {
                $('#tableCorp').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_corp").click(()=>{
            _this.corpIndex = undefined
        })
    },
    addLogoRow(){
        /**专利和商标新增一行或修改 */
        let _this = this
        this.id_10 = 0
        $("#logo_modal_save").click(()=>{
            this.id_10++;
            let num = $("#modal_logo_num").val();
            let date = $("#modal_logo_date").val();
            let icon = $("#modal_logo_icon").val();

            let _data = {
                id:this.id_10,
                num:num?num:'-',
                date:date?date:'-',
                icon:icon?icon:'-',
            }
            if(_this.logoIndex !== undefined) {
                
                $('#tableLogo').bootstrapTable('updateRow', {
                    index:_this.logoIndex,
                    row:_data
                })
            }else {
                $('#tableLogo').bootstrapTable('append', _data)
            }
        })

        /**点击新增 */
        $("#add_logo").click(()=>{
            _this.logoIndex = undefined
        })
    },
    initTable(){
        let _this = this;

        let $table = $('#table1');
        let data = [
            {
                "rank": "<span><span style='margin-right: 10rem;color:#000'>CA1</span><span>很小</span></span>",
                "money": "可放宽信用限制",
                "comment": "<span style='color:#000'>大额度</span>"
            },
            {
                "rank": "<span><span style='margin-right: 10rem;color:#000'>CA2</span><span>低</span></span>",
                "money": "可适当放宽信用限制",
                "comment": "<span style='color:#000'>较大额度</span>"
            },
            {
                "rank": "<span><span style='margin-right: 10rem;color:#000'>CA3</span><span>一般</span></span>",
                "money": "可给予一般的信用",
                "comment": "<span style='color:#000'>中等额度</span>"
            },
            {
                "rank": "<span><span style='margin-right: 10rem;color:#000'>CA4</span><span>高于一般</span></span>",
                "money": "可在监控的条件下给与信用",
                "comment": "<span style='color:#000'>小额度-定期监控</span>"
            },
            {
                "rank": "<span><span style='margin-right: 10rem;color:#000'>CA5</span><span>较高</span></span>",
                "money": "可在有担保的条件下给予信用",
                "comment": "<span style='color:#000'>现金交易或小额度</span>"
            },
            {
                "rank":  "<span><span style='margin-right: 10rem;color:#000'>CA6</span><span>高</span></span>",
                "money": "建议不给予信用",
                "comment": "<span style='color:#000'>现金交易</span>"
            },
            {
                "rank":  "<span><span style='margin-right: 10rem;color:#000'>NR</span><span>无法评定</span></span>",
                "money": "无充足的材料",
                "comment": "<span style='color:#000'>无法评定</span>"
            }
        ];
        $table.bootstrapTable({data: data});


        /**历史纪录表初始化 */
        const $tableRecord = $('#tableRecord');
        $tableRecord.bootstrapTable({
            height: $(".table-content1").height()/3*2,
            columns: [
            {
                title: '日期',
                field: 'date',
                valign: 'middle',
                width:'20%'
                },{
                field: 'change_item',
                title: '变更项',
                width:'20%'
                }, {
                field: 'change_font',
                title: '变更前',
                width:'20%'
                }, {
                title: '变更后',
                field: 'change_back',
                valign: 'middle',
                width:'20%'
                },{
                    field: 'operate',
                    title: '操作',
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
                        
                    },
                    "click .delete":(e,value,row,index)=>{
                        e.stopPropagation();
                        $('.isDelete').removeClass("deleteShow")
                        $(e.target).children(".isDelete").toggleClass("deleteShow")
                        $(document).click(function(){
                            $('.isDelete').removeClass("deleteShow")
                        })
                        $(e.target).children(".isDelete").click(function(e){
                            e.stopPropagation();
                        })

                        $(".popCancel").click(function(){
                            $('.isDelete').removeClass("deleteShow")
                        })
                        $(".popEnter").on('click', function(){
                            $("#tableRecord").bootstrapTable("remove",{
                                field:'id',
                                values:[row.id]
                            })
                            $('.isDelete').removeClass("deleteShow")
                        })
                        
                    }
                    },
                    formatter: _this.operateFormatterRecord
                }
            ],
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        });
         /**股东信息表初始化 */
         const $tableShareholdersInfo = $('#tableShareholdersInfo');
         $tableShareholdersInfo.bootstrapTable({
             height: $(".table-content2").height()/3*2,
             columns: [
               {
                 title: '姓名',
                 field: 'name',
                 valign: 'middle',
                 width:'25%',
                 editable:true
                 },{
                 field: 'country',
                 title: '国籍/国家',
                 width:'25%',
                 }, {
                 field: 'money',
                 title: '出资比例(%)',
                 width:'25%',
                 },{
                     field: 'operate',
                     title: '操作',
                     width:'25%',
                     events: {
                     "click .edit":(e,value,row,index)=>{
                         _this.shareholdersInfoIndex = index
                         //回显
                     let {name,country,money} = row;
                     $("#modal_shareholdersinfo_name").val(name)
                     $("#modal_shareholdersinfo_country").val(country)
                     $("#modal_shareholdersinfo_money").val(money)
                     },
                     "click .delete":(e,value,row,index)=>{
                         e.stopPropagation();
                         $('.isDelete').removeClass("deleteShow")
                         $(e.target).children(".isDelete").toggleClass("deleteShow")
                         $(document).click(function(){
                           $('.isDelete').removeClass("deleteShow")
                         })
                         $(e.target).children(".isDelete").click(function(e){
                           e.stopPropagation();
                         })

                         $(".popCancel").click(function(){
                           $('.isDelete').removeClass("deleteShow")
                         })
                         $(".popEnter").on('click', function(){
                             $tableShareholdersInfo.bootstrapTable("remove",{
                               field:'id',
                               values:[row.id]
                           })
                           $('.isDelete').removeClass("deleteShow")
                         })
                       
                     }
                 },
                 formatter: _this.operateFormatterShareholdersInfo
                 }
             ],
             pagination: false, //分页
             smartDisplay:false,
             iconsPrefix:'fa',
             locales:'zh-CN',
         });
          /**股东详情表初始化 */
          const $tableShareholdersDetail = $('#tableShareholdersDetail');
          $tableShareholdersDetail.bootstrapTable({
              height: $(".table-content3").height()/3*2,
              columns: [
                  {
                  title: '名称',
                  field: 'name',
                  width :'14.2%',
                  valign: 'middle',
                  editable:true
                  },{
                  field: 'create_time',
                  title: '成立时间',
                    width :'14.2%',
                  }, {
                  field: 'create_money',
                  title: '注册资金',
                    width :'14.2%',
                  }, {
                    field: 'telephone',
                    title: '联系电话',
                      width :'14.2%',
                }, {
                    field: 'fax_no',
                    title: '传真号码',
                      width :'14.2%',
                }, {
                    field: 'email',
                    title: '邮箱地址',
                      width :'14.2%',
                },{
                    field: 'operate',
                    title: '操作',
                      width :'14.2%',
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
                    },
                    "click .delete":(e,value,row,index)=>{
                        e.stopPropagation();
                        $('.isDelete').removeClass("deleteShow")
                        $(e.target).children(".isDelete").toggleClass("deleteShow")
                        $(document).click(function(){
                          $('.isDelete').removeClass("deleteShow")
                        })
                        $(e.target).children(".isDelete").click(function(e){
                          e.stopPropagation();
                        })

                        $(".popCancel").click(function(){
                          $('.isDelete').removeClass("deleteShow")
                        })
                        $(".popEnter").on('click', function(){
                            $tableShareholdersDetail.bootstrapTable("remove",{
                              field:'id',
                              values:[row.id]
                          })
                          $('.isDelete').removeClass("deleteShow")
                        })
                      
                    }
                },
                formatter: _this.operateFormatterShareholdersDetail
                }
              ],
              pagination: false, //分页
              smartDisplay:false,
              iconsPrefix:'fa',
              locales:'zh-CN',
          
          });
        /**投资情况表初始化 */
        const $tableInvestment = $('#tableInvestment');
        $tableInvestment.bootstrapTable({
            height: $(".table-content4").height()/3*2,
            columns: [
                {
                title: '公司名称',
                field: 'name',
                width :'33.3%',
                valign: 'middle',
                editable:true
                },{
                field: 'money',
                title: '出资比例(%)',
                width :'33.3%',
                },{
                field: 'operate',
                title: '操作',
                width :'33.3%',
                events: {
                "click .edit":(e,value,row,index)=>{
                    _this.investmentIndex = index
                    //回显
                let {name,money} = row;
                $("#modal_investment_name").val(name)
                $("#modal_investment_money").val(money)
                
                },
                "click .delete":(e,value,row,index)=>{
                    e.stopPropagation();
                    $('.isDelete').removeClass("deleteShow")
                    $(e.target).children(".isDelete").toggleClass("deleteShow")
                    $(document).click(function(){
                    $('.isDelete').removeClass("deleteShow")
                    })
                    $(e.target).children(".isDelete").click(function(e){
                    e.stopPropagation();
                    })

                    $(".popCancel").click(function(){
                    $('.isDelete').removeClass("deleteShow")
                    })
                    $(".popEnter").on('click', function(){
                        $tableInvestment.bootstrapTable("remove",{
                        field:'id',
                        values:[row.id]
                    })
                    $('.isDelete').removeClass("deleteShow")
                    })
                
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
            locales:'zh-CN'            
        
        });
         /**管理层表初始化 */
         const $tableManagement = $('#tableManagement');
         $tableManagement.bootstrapTable({
             height: $(".table-content3").height()/3*2,
             columns: [
                 {
                 title: '姓名',
                 field: 'name',
                   width :'14.2%',
                 valign: 'middle',
                 editable:true
                 },{
                 field: 'position',
                 title: '职务',
                   width :'14.2%',
                 }, {
                 field: 'id_card',
                 title: '身份证号',
                   width :'14.2%',
                 }, {
                   field: 'age',
                   title: '年龄',
                     width :'14.2%',
               }, {
                   field: 'tel',
                   title: '联系电话',
                     width :'14.2%',
               }, {
                   field: 'email',
                   title: '邮箱地址',
                     width :'14.2%',
               },{
                   field: 'operate',
                   title: '操作',
                     width :'14.2%',
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
                   },
                   "click .delete":(e,value,row,index)=>{
                       e.stopPropagation();
                       $('.isDelete').removeClass("deleteShow")
                       $(e.target).children(".isDelete").toggleClass("deleteShow")
                       $(document).click(function(){
                         $('.isDelete').removeClass("deleteShow")
                       })
                       $(e.target).children(".isDelete").click(function(e){
                         e.stopPropagation();
                       })

                       $(".popCancel").click(function(){
                         $('.isDelete').removeClass("deleteShow")
                       })
                       $(".popEnter").on('click', function(){
                           $tableManagement.bootstrapTable("remove",{
                             field:'id',
                             values:[row.id]
                         })
                         $('.isDelete').removeClass("deleteShow")
                       })
                     
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
         
         });
           /**行业信息表初始化 */
           const $tableVocation = $('#tableVocation');
           $tableVocation.bootstrapTable({
               height: $(".table-content6").height()/3*2,
               columns: [
                   {
                   title: '行业代码',
                   field: 'code',
                   width :'33.3%',
                   valign: 'middle',
                   },{
                   field: 'name',
                   title: '行业名称',
                     width :'33.3%',
                   },{
                     field: 'operate',
                     title: '操作',
                       width :'33.3%',
                     events: {
                     "click .edit":(e,value,row,index)=>{
                         _this.vocationIndex = index
                         //回显
                     let {code,name} = row;
                     $("#modal_vocation_code").val(code)
                     $("#modal_vocation_name").val(name)
                    
                     },
                     "click .delete":(e,value,row,index)=>{
                         e.stopPropagation();
                         $('.isDelete').removeClass("deleteShow")
                         $(e.target).children(".isDelete").toggleClass("deleteShow")
                         $(document).click(function(){
                           $('.isDelete').removeClass("deleteShow")
                         })
                         $(e.target).children(".isDelete").click(function(e){
                           e.stopPropagation();
                         })
  
                         $(".popCancel").click(function(){
                           $('.isDelete').removeClass("deleteShow")
                         })
                         $(".popEnter").on('click', function(){
                             $tableVocation.bootstrapTable("remove",{
                               field:'id',
                               values:[row.id]
                           })
                           $('.isDelete').removeClass("deleteShow")
                         })
                       
                     }
                 },
                 formatter: _this.operateFormatterVocation
                 }
               ],
           // url : 'firmSoftTable.action', // 请求后台的URL（*）
           // method : 'post', // 请求方式（*）post/get
               pagination: false, //分页
               smartDisplay:false,
               iconsPrefix:'fa',
               locales:'zh-CN',
           
           });
        /**业务情况表初始化 */
        const $tableYw = $('#tableYw');
        $tableYw.bootstrapTable({
            height: $(".table-content6").height()/3*2,
            columns: [
                {
                title: '产品',
                field: 'product',
                width :'20%',
                valign: 'middle',
                },{
                field: 'jk',
                title: '进口',
                  width :'20%',
                },{
                field: 'ck',
                title: '出口',
                width :'20%',
                },{
                field: 'code',
                title: '海关代码',
                width :'20%',
                },{
                  field: 'operate',
                  title: '操作',
                width :'20%',
                  events: {
                  "click .edit":(e,value,row,index)=>{
                      _this.ywIndex = index
                      //回显
                  let {product,jk,ck,code} = row;
                  $("#modal_yw_product").val(product)
                  $("#modal_yw_jk").val(jk)
                  $("#modal_yw_ck").val(ck)
                  $("#modal_yw_code").val(code)
                 
                  },
                  "click .delete":(e,value,row,index)=>{
                      e.stopPropagation();
                      $('.isDelete').removeClass("deleteShow")
                      $(e.target).children(".isDelete").toggleClass("deleteShow")
                      $(document).click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(e.target).children(".isDelete").click(function(e){
                        e.stopPropagation();
                      })

                      $(".popCancel").click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(".popEnter").on('click', function(){
                          $tableYw.bootstrapTable("remove",{
                            field:'id',
                            values:[row.id]
                        })
                        $('.isDelete').removeClass("deleteShow")
                      })
                    
                  }
              },
              formatter: _this.operateFormatterYw
              }
            ],
        // url : 'firmSoftTable.action', // 请求后台的URL（*）
        // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        
        });
        /**采购情况表初始化 */
        const $tableCg = $('#tableCg');
        $tableCg.bootstrapTable({
            height: $(".table-content6").height()/3*2,
            columns: [
                {
                title: '国家',
                field: 'country',
                width :'25%',
                valign: 'middle',
                },{
                field: 'percent',
                title: '百分比',
                  width :'25%',
                },{
                field: 'js',
                title: '结算方式',
                width :'25%',
                },{
                  field: 'operate',
                  title: '操作',
                width :'25%',
                  events: {
                  "click .edit":(e,value,row,index)=>{
                      _this.cgIndex = index
                      //回显
                  let {country,percent,js} = row;
                  $("#modal_cg_country").val(country)
                  $("#modal_cg_percent").val(percent)
                  $("#modal_cg_js").val(js)
                 
                  },
                  "click .delete":(e,value,row,index)=>{
                      e.stopPropagation();
                      $('.isDelete').removeClass("deleteShow")
                      $(e.target).children(".isDelete").toggleClass("deleteShow")
                      $(document).click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(e.target).children(".isDelete").click(function(e){
                        e.stopPropagation();
                      })

                      $(".popCancel").click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(".popEnter").on('click', function(){
                          $tableCg.bootstrapTable("remove",{
                            field:'id',
                            values:[row.id]
                        })
                        $('.isDelete').removeClass("deleteShow")
                      })
                    
                  }
              },
              formatter: _this.operateFormatterCg
              }
            ],
       
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        
        });
        /**销售情况表初始化 */
        const $tableXs = $('#tableXs');
        $tableXs.bootstrapTable({
            height: $(".table-content6").height()/3*2,
            columns: [
                {
                title: '国家',
                field: 'country',
                width :'25%',
                valign: 'middle',
                },{
                field: 'percent',
                title: '百分比',
                  width :'25%',
                },{
                field: 'js',
                title: '结算方式',
                width :'25%',
                },{
                  field: 'operate',
                  title: '操作',
                width :'25%',
                  events: {
                  "click .edit":(e,value,row,index)=>{
                      _this.xsIndex = index
                      //回显
                  let {country,percent,js} = row;
                  $("#modal_xs_country").val(country)
                  $("#modal_xs_percent").val(percent)
                  $("#modal_xs_js").val(js)
                 
                  },
                  "click .delete":(e,value,row,index)=>{
                      e.stopPropagation();
                      $('.isDelete').removeClass("deleteShow")
                      $(e.target).children(".isDelete").toggleClass("deleteShow")
                      $(document).click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(e.target).children(".isDelete").click(function(e){
                        e.stopPropagation();
                      })

                      $(".popCancel").click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(".popEnter").on('click', function(){
                          $tableXs.bootstrapTable("remove",{
                            field:'id',
                            values:[row.id]
                        })
                        $('.isDelete').removeClass("deleteShow")
                      })
                    
                  }
              },
              formatter: _this.operateFormatterXs
              }
            ],
       
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        
        });
        /**供应商表初始化 */
        const $tableCorp = $('#tableCorp');
        $tableCorp.bootstrapTable({
            height: $(".table-content6").height()/3*2,
            columns: [
                {
                title: '供应商名称',
                field: 'name',
                width :'16.6%',
                valign: 'middle',
                },{
                field: 'pro',
                title: '供应产品',
                  width :'16.66%',
                },{
                field: 'tel',
                title: '联系电话',
                width :'16.66%',
                },{
                field: 'time',
                title: '合作时间',
                width :'16.66%',
                },{
                field: 'cases',
                title: '付款情况',
                width :'16.66%',
                },{
                field: 'operate',
                title: '操作',
                width :'16.66%',
                  events: {
                  "click .edit":(e,value,row,index)=>{
                      _this.corpIndex = index
                      //回显
                    let {name,pro,tel,time,cases} = row;
                    $("#modal_corp_name").val(name)
                    $("#modal_corp_pro").val(pro)
                    $("#modal_corp_tel").val(tel)
                    $("#modal_corp_time").val(time)
                    $("#modal_corp_case").val(cases)
                 
                  },
                  "click .delete":(e,value,row,index)=>{
                      e.stopPropagation();
                      $('.isDelete').removeClass("deleteShow")
                      $(e.target).children(".isDelete").toggleClass("deleteShow")
                      $(document).click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(e.target).children(".isDelete").click(function(e){
                        e.stopPropagation();
                      })

                      $(".popCancel").click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(".popEnter").on('click', function(){
                          $tableCorp.bootstrapTable("remove",{
                            field:'id',
                            values:[row.id]
                        })
                        $('.isDelete').removeClass("deleteShow")
                      })
                    
                  }
              },
              formatter: _this.operateFormatterCorp
              }
            ],
       
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        
        });
        /**商标和专利表初始化 */
        const $tableLogo = $('#tableLogo');
        $tableLogo.bootstrapTable({
            height: $(".table-content6").height()/3*2,
            columns: [
                {
                title: '注册号',
                field: 'num',
                width :'25%',
                valign: 'middle',
                },{
                field: 'date',
                title: '注册日期',
                  width :'25%',
                },{
                field: 'icon',
                title: '商标图案',
                width :'25%',
                },{
                field: 'operate',
                title: '操作',
                width :'25%',
                  events: {
                  "click .edit":(e,value,row,index)=>{
                      _this.logoIndex = index
                      //回显
                    let {num,date,icon} = row;
                    $("#modal_logo_num").val(num)
                    $("#modal_logo_date").val(date)
                    $("#modal_logo_icon").val(icon)
                  },
                  "click .delete":(e,value,row,index)=>{
                      e.stopPropagation();
                      $('.isDelete').removeClass("deleteShow")
                      $(e.target).children(".isDelete").toggleClass("deleteShow")
                      $(document).click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(e.target).children(".isDelete").click(function(e){
                        e.stopPropagation();
                      })

                      $(".popCancel").click(function(){
                        $('.isDelete').removeClass("deleteShow")
                      })
                      $(".popEnter").on('click', function(){
                          $tableLogo.bootstrapTable("remove",{
                            field:'id',
                            values:[row.id]
                        })
                        $('.isDelete').removeClass("deleteShow")
                      })
                    
                  }
              },
              formatter: _this.operateFormatterLogo
              }
            ],
       
            pagination: false, //分页
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
        
        });
    },
    operateFormatterRecord(){
        /**历史纪录 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#recordModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },  
    operateFormatterShareholdersInfo(){
        /**股东信息 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersInfoModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },
    operateFormatterShareholdersDetail(){
        /**股东详情 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersDetailModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterInvestment(){
        /**投资情况 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#investmentModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },    
    operateFormatterManagement(){
        /**管理层 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#managementModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterVocation(){
        /**行业信息 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#vocationModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterYw(){
        /**业务情况 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#ywModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'  
    },   
    operateFormatterCg(){
        /**采购情况 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#cgModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterXs(){
        /**销售情况 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#xsModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterCorp(){
        /**供应商 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#corpModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    },   
    operateFormatterLogo(){
        /**商标和专利 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#logoModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
    }   
}

BusinessWrite.init();