let BasicWrite = {
    init(){
        /**函数初始化 */
        Public.tabFixed(".tab-bar",".main",120,90)
        let row = JSON.parse(localStorage.getItem("row"));
        this.dateForm(); 
        this.addressInit();
        this.addRcordRow();
        this.tabChange();
        this.goToInfoImport();
        this.initTable(row.company_id);
        this.addShareholdersInfoRow();
        this.addShareholdersDetailRow();
        this.addInvestmentRow();
        this.addManagementRow();
        console.log(" console.log(row):")
        console.log(row)
        //回显基本信息
        $("#custom_id").html(row.custom_id);
        $("#customId").html(row.customId);
        $("#receiver_date").html(row.receiver_date);
        $("#continent").html(row.continent);
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
        $("#company_id").val(row.company_id);
        $("#num").html(row.num);
        $("#end_date").html(row.end_date);
        console.log("companyId="+row.company_id)
      
        //回显企业注册信息
        $.ajax({
   			type:"post",
   			url:BASE_PATH+"credit/front/orderProcess/getCompanyInfo",
   			data:"company_id="+row.company_id,
   			dataType:"json",
   			success:function(data){
   				console.log(data);
   				$("#credit_code").val(data.credit_code);
   				$("#qy_person").val(data.legal);
   				$("#qy_type").val(data.company_type);
   				$("#qy_create").val(data.establishment_date);
   				$("#qy_type").val(data.company_type);
   				$("#qy_date").val(data.business_date_end);
   				$("#qy_money").val(data.registered_capital);
   				$("#qy_address").val(data.address);
   				$("#qy_office").val(data.registration_authority);
   				$("#qy_state").val(data.registration_status);
   				$("#qy_sale").val(data.operation_scope);
   			 }
   		})
   		
        //回显文件
        $.ajax({
   			type:"post",
   			url:"/credit/front/orderProcess/getFilesByOrderId",
   			data:"orderId="+row.id,
   			dataType:"json",
   			success:function(data){
   				console.log(data);
   			 console.log(data.files) 	
   	       $(".order-detail").html("");
   	       
   	        if(data.files.length === 0){$(".uploadFile:not(.upload-over)").show();return}
   	        console.log(data.files.length)
   	        /*if(data.files.length > 4) {
   	        	alert(1)
   	        	$(".uploadFile:not(.upload-over)").hide();
   	        }else {*/
   	        	$(".uploadFile:not(.upload-over)").show()
   	        //}
   	        for (var i in data.files){
   	        	let filetype = data.files[i].ext.toLowerCase()
   	        	let fileicon = ''
   	        	if(filetype === 'doc' || filetype === 'docx') {
   		             fileicon = '/static/credit/imgs/order/word.png'
   		           }else if(filetype === 'xlsx' || filetype === 'xls') {
   		             fileicon = '/static/credit/imgs/order/Excel.png'
   		           }else if(filetype === 'png') {
   		             fileicon = '/static/credit/imgs/order/PNG.png'
   		           }else if(filetype === 'jpg') {
   		             fileicon = '/static/credit/imgs/order/JPG.png'
   		           }else if(filetype === 'pdf') {
   		             fileicon = '/static/credit/imgs/order/PDF.png'
   		           }
   	        	let fileArr = ''
   	        	let filename = data.files[i].originalname
   	        	let all_name = filename + filetype
   	    		let num = filename.split(".").length;
   	            let filename_qz = []
   	            for(let i=0;i<num;i++){  
   	              filename_qz =  filename_qz.concat(filename.split(".")[i])
   	            }
   	            filename_qz_str = filename_qz.join('.')
   	            if(filename_qz_str.length>4) {
   	              filename_qz_str = filename_qz_str.substr(0,2) + '..' + filename_qz_str.substr(filename_qz_str.length-2,2)
   	            }
   	            
   	            filename = filename_qz_str + '.' +filetype
   	        	fileArr += '<div class="uploadFile mt-3 mr-4 mb-5 upload-over" fileId="'+data.files[i].id+'" url="'+data.files[i].view_url+'" style="cursor:pointer">'+
   	        				'<div class="over-box">'+
   		        				'<img src="'+fileicon+'" class="m-auto"/>'+
   		        				 '<p class="filename" title="'+all_name+'">'+filename+'</p>'+
   	        				 '</div>'+
   	        				 '</div>'
   	        
			  $(".order-detail").append(fileArr)	
   	           $(".upload-over").click(function(e){
   	        	   console.log($(e.target))
   	        	   if($(e.target).parent().attr("class") === 'close') {
   	        		   return
   	        	   }
   	        	   window.open($(this).attr("url"))
   	        	   
   	           })
   	        }
   			 }
   		})
        //将form序列化转化为json
        function getFormData($form) {
            var unindexed_array = $form.serializeArray();
            var indexed_array = {};

            $.map(unindexed_array, function (n, i) {
              indexed_array[n['name']] = n['value'];
            });

            return indexed_array;
        }
       
      //提交按钮
        $("#submit").on('click','',function(){
   			console.log($("#tableRecord").bootstrapTable('getData'))
   			console.log(JSON.stringify($("#tableRecord").bootstrapTable('getData')).replace("}]",""));//历史变更记录
   			console.log(JSON.stringify($("#tableShareholdersInfo").bootstrapTable('getData')));//股东信息
   			console.log(JSON.stringify($("#tableShareholdersDetail").bootstrapTable('getData')));//股东详情
   			console.log(JSON.stringify($("#tableInvestment").bootstrapTable('getData')));//投资情况
   			console.log(JSON.stringify($("#tableManagement").bootstrapTable('getData')));//管理层
   			$("#harea").removeAttr("name");
   			$("#hcity").removeAttr("name");
   			$("#hproper").removeAttr("name");
	   		$.ajax({
	   			   type:"post",
	   			   url:BASE_PATH+"credit/front/orderProcess/reportedSave",
	   			   data: "companyHistory="+(JSON.stringify($("#tableRecord").bootstrapTable('getData'))//公司历史数据
	   					+"&tableShareholdersInfo="+JSON.stringify($("#tableShareholdersInfo").bootstrapTable('getData'))
	   					+"&tableShareholdersDetail="+JSON.stringify($("#tableShareholdersDetail").bootstrapTable('getData'))
	   					+"&tableInvestment="+JSON.stringify($("#tableInvestment").bootstrapTable('getData'))
	   					+"&tableManagement="+JSON.stringify($("#tableManagement").bootstrapTable('getData'))
	   					+"&companyZhuCe=["+JSON.stringify(getFormData($("#meForm")))+"]"//公司注册信息数据
	   					+"&companyId="+row.company_id//公司id
	   					+"&statusCode="+294//信息录入状态码
	   					+"&model.id="+row.id//订单id
	   					/*+"&"+$("#meForm").serialize()*/),
	   			dataType:"json",
	   			success:function(data){
	   			if(data.statusCode===1){
             	 console.log("此处进入success状态2222222222");
             	Public.message("success","提交成功!");
             	 $("#main_content").load('/credit/front/orderProcess/showReportInfoImport');
             }else{
             	 console.log("此处进入error状态");
             	Public.message("error",data.message);
             }
	   			 }
	   		})
   	   	
   		})
    	//保存按钮
   		$("#save").on('click','',function(){
   			console.log($("#tableRecord").bootstrapTable('getData'))
   			console.log(JSON.stringify($("#tableRecord").bootstrapTable('getData')).replace("}]",""));//历史变更记录
   			console.log(JSON.stringify($("#tableShareholdersInfo").bootstrapTable('getData')));//股东信息
   			console.log(JSON.stringify($("#tableShareholdersDetail").bootstrapTable('getData')));//股东详情
   			console.log(JSON.stringify($("#tableInvestment").bootstrapTable('getData')));//投资情况
   			console.log(JSON.stringify($("#tableManagement").bootstrapTable('getData')));//管理层
   			$("#harea").removeAttr("name");
   			$("#hcity").removeAttr("name");
   			$("#hproper").removeAttr("name");
   			//保存按钮
   			$.ajax({
   	   			type:"post",
   	   			url:BASE_PATH+"credit/front/orderProcess/reportedSave",
   	   			data:"companyHistory="+(JSON.stringify($("#tableRecord").bootstrapTable('getData'))//公司历史数据
   	   				+"&tableShareholdersInfo="+JSON.stringify($("#tableShareholdersInfo").bootstrapTable('getData'))
   					+"&tableShareholdersDetail="+JSON.stringify($("#tableShareholdersDetail").bootstrapTable('getData'))
   					+"&tableInvestment="+JSON.stringify($("#tableInvestment").bootstrapTable('getData'))
   					+"&tableManagement="+JSON.stringify($("#tableManagement").bootstrapTable('getData'))
   	   					+"&companyZhuCe=["+JSON.stringify(getFormData($("#meForm")))+"]"//公司注册信息数据
   	   					+"&companyId="+row.company_id//公司id
   	   					+"&statusCode="+293//
   	   					+"&model.id="+row.id//订单id
   	   					/*+"&"+$("#meForm").serialize()*/),
   	   			dataType:"json",
   	   			success:function(data){
   	   			if(data.statusCode===1){
                 	 console.log("此处进入success状态2222222222");
                 	Public.message("success","保存成功!");
                 	 $("#main_content").load('/credit/front/orderProcess/showReportInfoImport');
                 }else{
                 	 console.log("此处进入error状态");
                 	Public.message("error",data.message);
                 }
   	   			 }
   	   		})
   	   	
   		})
   			
    },
    addressInit(){
        $("#qy_address").focus(function (e) {
            SelCity(this,e);
            let top = $("#qy_address").offset().top
            $("#PoPy").css("top",top+30+"px")
            $(".main").scroll(()=>{
                let top = $("#qy_address").offset().top
                $("#PoPy").css("top",top+30+"px")
                if(top < 90) {
                    $("#cColse").trigger("click")
                }
            })
        });
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#qy_create',
            format: 'yyyy年MM月dd日'
        });
        laydate.render({
            elem: '#qy_date',
           format: 'yyyy年MM月dd日'
        });
        laydate.render({
            elem: '#modal_record_date',
            format: 'yyyy年MM月dd日'
        });
        laydate.render({
            elem: '#modal_shareholdersdetail_time',
            format: 'yyyy年MM月dd日'
        });
        
        //验证
        $("#credit_code").blur(()=>{
        	let val = $("#credit_code").val();
        	console.log(val)
        	let reg = /^[a-zA-Z0-9]{18}$/
        	if (!reg.test((val))) {
        		
        		$("#credit_code").siblings(".errorInfo").show()
        		$("#credit_code").addClass("active")
        		$("#credit_code").val("")
        	}else {
        		$("#credit_code").siblings(".errorInfo").hide()
        		$("#credit_code").removeClass("active")
        	}
        })
         $("#qy_person").blur(()=>{
        	let val = $("#qy_person").val();
        	let reg =  /^(?=.*\d.*\b)/
        	if (reg.test((val))) {
        		
        		$("#qy_person").siblings(".errorInfo").show()
        		$("#qy_person").addClass("active")
        		$("#qy_person").val("")
        	}else {
        		$("#qy_person").siblings(".errorInfo").hide()
        		$("#qy_person").removeClass("active")
        	}
        })
        $("#qy_office").blur(()=>{
        	let val = $("#qy_office").val();
        	let reg = /^(?=.*\d.*\b)/
        		if (reg.test((val))) {
        			
        			$("#qy_office").siblings(".errorInfo").show()
        			$("#qy_office").addClass("active")
        			$("#qy_office").val("")
        		}else {
        			$("#qy_office").siblings(".errorInfo").hide()
        			$("#qy_office").removeClass("active")
        		}
        })
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
    goToInfoImport(){
        /**点击面包屑去信息录入列表 */
        $("#infoImport").click(()=>{
            Public.goToInfoImport()
        })
    },
    addRcordRow(){
        /**历史纪录新增或修改一行 */
        let _this = this
        this.id = 0
        $("#record_modal_save").click(()=>{
            _this.id++;
            let modal_record_date = $("#modal_record_date").val();
            let modal_change_items = $("#modal_change_items").find("option:selected").text();
            let modal_change_font = $("#modal_change_font").val();
            let modal_change_back = $("#modal_change_back").val();
            
            let _data = {
                id:_this.id,
                date:modal_record_date?modal_record_date:'-',
                change_items:modal_change_items?modal_change_items:'-',
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
    initTable(cpi){
        let _this = this
        /**历史纪录表初始化 */
       
        const $tableRecord = $('#tableRecord');
        $tableRecord.bootstrapTable({
        	url : BASE_PATH+'credit/front/orderProcess/reportedJson?flagStr=tableRecord', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
            	//let that = cpi;
           
                return {
              	 company_id : cpi
                };  
                //console.log(""+params)
            },  
            pagination: false, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:10,
            pageList: [10,20,30],
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            fixedColumns: true,
            fixedNumber: 1,
            queryParamsType:'',
            height: $(".table-content1").height()/3*2,
            columns: [
               {
                  title: '日期',
                  field: 'date',
                  valign: 'middle',
                  width:'20%'
                },{
                  field: 'change_items',
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
                        let {date,change_items,change_font,change_back} = row;
                        $("#modal_record_date").val(date)
                        $("#modal_change_items").val(change_items)
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
           // url : 'firmSoftTable.action', // 请求后台的URL（*）
           // method : 'post', // 请求方式（*）post/get
            pagination: false, //分页
           // smartDisplay:false,
           // iconsPrefix:'fa',
            locales:'zh-CN',
           
         
          });

          /**股东信息表初始化 */
          const $tableShareholdersInfo = $('#tableShareholdersInfo');
            $tableShareholdersInfo.bootstrapTable({
            	url : BASE_PATH+'credit/front/orderProcess/reportedJson?flagStr=tableShareholdersInfo', // 请求后台的URL（*）
                method : 'post', // 请求方式（*）post/get
                contentType:'application/x-www-form-urlencoded;charset=UTF-8',
                queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                    return {
                  	 company_id : cpi
                    };  
                },
                height: $(".table-content2").height()/3*2,
                pagination: false, //分页
                sidePagination: 'server',
               
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
            // url : 'firmSoftTable.action', // 请求后台的URL（*）
            // method : 'post', // 请求方式（*）post/get
                pagination: false, //分页
                smartDisplay:false,
                iconsPrefix:'fa',
                locales:'zh-CN',
            });
            /**股东详情表初始化 */
          const $tableShareholdersDetail = $('#tableShareholdersDetail');
          $tableShareholdersDetail.bootstrapTable({
        	  url : BASE_PATH+'credit/front/orderProcess/reportedJson?flagStr=tableShareholdersDetail', // 请求后台的URL（*）
              method : 'post', // 请求方式（*）post/get
              contentType:'application/x-www-form-urlencoded;charset=UTF-8',
              queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                  return {
                	 company_id :  cpi
                  };  
              },
              height: $(".table-content3").height()/3*2,
              sidePagination: 'server',
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
          // url : 'firmSoftTable.action', // 请求后台的URL（*）
          // method : 'post', // 请求方式（*）post/get
              pagination: false, //分页
              smartDisplay:false,
              iconsPrefix:'fa',
              locales:'zh-CN',
          
          });
            /**投资情况表初始化 */
            const $tableInvestment = $('#tableInvestment');
            $tableInvestment.bootstrapTable({
            	url : BASE_PATH+'credit/front/orderProcess/reportedJson?flagStr=tableInvestment', // 请求后台的URL（*）
                method : 'post', // 请求方式（*）post/get
                contentType:'application/x-www-form-urlencoded;charset=UTF-8',
                queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                    return {
                  	 company_id :  cpi
                    };  
                },
                height: $(".table-content4").height()/3*2,
                sidePagination: 'server',
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
        	  url : BASE_PATH+'credit/front/orderProcess/reportedJson?flagStr=tableManagement', // 请求后台的URL（*）
              method : 'post', // 请求方式（*）post/get
              contentType:'application/x-www-form-urlencoded;charset=UTF-8',
              queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                  return {
                	 company_id :  cpi
                  };  
              },
              sidePagination: 'server',
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
              contentType:'application/x-www-form-urlencoded;charset=UTF-8'
          });

      },
      operateFormatterRecord(){
        /**历史纪录 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#recordModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="/static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
      },  
      operateFormatterShareholdersInfo(){
        /**股东信息 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersInfoModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="/static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
      },
      operateFormatterShareholdersDetail(){
        /**股东详情 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#shareholdersDetailModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="/static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
      },    
      operateFormatterInvestment(){
        /**投资情况 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#investmentModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="/static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
      },    
      operateFormatterManagement(){
        /**管理层 */
        return '<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#managementModalCenter">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="/static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>'
      }    
}

BasicWrite.init();