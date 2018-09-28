let 
/* 事件 */
	Events = {
		selectChange: function () {
			let selItem = $(this).val();
			if (selItem != $(this).find('option:first').val()) {
				$(this).css("color", "#495057");
			} 
		},
		btn_download:function(){
			/**点击批量上传 */
			$("#btn_import").click(function(){
				$("#more_upload").trigger("click");
				$("#more_upload").on("change",()=>{
					var form = document.getElementById("form_File");//获取到form表单
					var formData = new FormData(form);
					formData.append("pic",$("#more_upload")[0].files[0]);
					console.log(formData)
					$.ajax({
						type: "POST", // 数据提交类型
						url: "/credit/orderpoimanager/importExcel", // 发送地址
						data: formData, //发送数据
						dataType:"json",
						async: true, // 是否异步
						processData: false, //processData 默认为false，当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
						contentType: false, //
						success:(data)=>{
							/**成功 */
							
							console.log(data)
							jsondata=data.orderListReal.rows;
							$("#show_modal").trigger("click")
							Page.initTable(data.orderList)
							if(data.errormark.statusCode===2){
								 $(".err-box span").html(data.errormark.message)
								 $(".err-box").show()
								 $("#modal_submit").addClass("btn-disabled disabled").removeClass("btn-primary")
								
				               }else{
				            	 $(".err-box").hide()
				   				 $("#modal_submit").removeClass("btn-disabled disabled").addClass("btn-primary")
				               }
							$("#tableOrder").bootstrapTable("load",data.orderList)
//							$(".err-box span").html(data.errormark.message)
							
						},
						error:()=>{
//							Public.message("info")
						}
					});
				})
			}),
		
	$("#modal_submit").click(function() {
		if($(this).hasClass("disabled")) {
			Public.message("error","提交数据有误，请检查数据");
			return;
		}
	    $.ajax({
	        type: "POST",
	        url: "/credit/orderpoimanager/savedata",
	        contentType: "application/json; charset=utf-8",
	        data: JSON.stringify(jsondata),
	        dataType: "json",
	        success: function (data) {
	        	if(data.statusCode===1){
               	Public.message("success",data.message);
               }else{
               	Public.message("error",data.message);
               }
	        },
	        error: function (message) {
	            $("#request-process-patent").html("提交数据失败！");
	        }
	    });
	})	
			
			
			
		},
		emailAdd: function (obj){
            let tv = $(obj).val();
            let reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if(reg.test(tv)){
                $(obj).removeClass("active");
				$(obj).siblings(".errorInfo").hide();
            }else{
                $(obj).addClass("active");
				$(obj).siblings(".errorInfo").show();
            }
        },
		formSubmit: function(){
			//$("#orderForm").submit();
			let that = null,
				formSelect = formInput = true;
				
			$("#orderForm .formSelect").each(function(){
				that = $(this);
				if(that.find("option:selected").val() == 0){
					formSelect = false;
					$(that).addClass("active");
					$(that).siblings(".errorInfo").show();
					return;
				}
			});
			$("#orderForm .formSelect").change(function(){
				that = $(this);
				$(that).removeClass("active");
				$(that).siblings(".errorInfo").hide();
			});
			$("#orderForm .formInput").each(function(){
				that = $(this);
				if(that.val() == 0){
					formInput = false;
					$(that).addClass("active");
					$(that).siblings(".errorInfo").show();
					return;
				}
			});
			$("#orderForm .formInput").keyup(function(){
				that = $(this);
				if(that.val() == 0){
					$(that).addClass("active");
					$(that).siblings(".errorInfo").show();
				}
				else{
					$(that).removeClass("active");
					$(that).siblings(".errorInfo").hide();
				}
			});
			//表单验证成功，请求后台接口
			if(formSelect && formInput){
				$("input[name='attr.status']").val("290");
				$("#btn_submit").click(function(){
					$("#orderForm").ajaxSubmit({
						success:function(data){
							console.log(JSON.stringify(data));
							  if(data.statusCode===1){
                        		Public.message("success",data.message);
                        		Public.goList();
                       		 }else{
                        		Public.message("error",data.message);
                        		Public.goList();
                        	}

						},
						error:function(data){
							Public.message("error",data.message);
							Public.goList();
						}
					});
				});
			}
		},
		formSave: function(){
			$("input[name='attr.status']").val("289");
				$("#btn_save").click(function(){
					$("#orderForm").ajaxSubmit({
						success:function(data){
							  if(data.statusCode===1){
                        		Public.message("success",data.message);
                        		Public.goList();
                       		 }else{
                        		Public.message("error",data.message);
                        		Public.goList();
                        	 }

						},
						error:function(data){
							Public.message("error",data.message);
							Public.goList();
						}
					});
				});
		},
		closeProgress(){
			$(".close").click(function(){
				$("#Close").hide();
			})
		}
		
	},
/* 画面对象 */
	Page = {
		time: new Date(),
        // 初始化Validator
        initValidator: function () {
			
        },
        // 事件初期化
        initEvents: function () {
        	/*下拉菜单切换*/
			$("#orderForm select option").css("color", "#495057");
			$("#orderForm select").change(Events.selectChange);
			/*表单提交*/
			$("#btn_submit").click(Events.formSubmit);
			/*表单保存*/
			$("#btn_submit").click(Events.formSave);
        },
        // 画面初始化
        initialize: function () {
        	/*获取当前日期*/
        	let date = Page.time,
        		year = date.getFullYear(),
        		month = date.getMonth() + 1,
        		day = date.getDate(),
        		todayDate = '';
        		if(month < 10){
        			month = '0' + month;
        		};
        		if(day < 10){
        			day = '0' + day;
        		};
        	todayDate = year + '-' + month + '-' + day;
        	$('#client_order_date').val(todayDate);
        	
        },
        init: function () {
            Page.initialize();
            Page.initEvents();
			Page.initValidator();
			Events.btn_download();
			Events.closeProgress();
		},
		initTable(){
			/**初始化表格 */
			const $tableOrder = $('#tableOrder');

			$tableOrder.bootstrapTable({
				height: $(".table-modal-content").height(),
				columns: [
							{
							  field: 'custom_id',
							  title: '客户ID',
							  align: 'center'
							}, {
							  field: 'customerName',
							  title: '客户用户名',
							  align: 'center',
							}, {
							  title: '地区',
							  field: 'continent',
							  align: 'center',
							  valign: 'middle',
							}, {
							  title: '国家',
							  field: 'country',
							  align: 'center',
							  valign: 'middle',
							
							}, {
							  title: '报告类型',
							  field: 'report_type',
							  align: 'center',
							  valign: 'middle',
							}, {
							  title: '订单类型',
							  field: 'order_type',
							  align: 'center',
							  valign: 'middle',
							}, {
							  title: '报告语言',
							  field: 'report_language',
							  align: 'center',
							  valign: 'middle',
							}, {
							  title: '公司名称',
							  field: 'company_by_report',
							  align: 'center',
							  valign: 'middle',
							}, {
							  title: '速度',
							  field: 'speed',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '客户参考号',
							  field: 'reference_num',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '地址',
							  field: 'address',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '电话',
							  field: 'telphone',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '传真',
							  field: 'fax',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '邮箱',
							  field: 'email',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '联系人',
							  field: 'contacts',
							  align: 'center',
							  valign: 'middle',
							},{
							  title: '其他细节',
							  field: 'remarks',
							  align: 'center',
							  valign: 'middle',
							}
							
							
						  
						],
			   // url : 'firmSoftTable.action', // 请求后台的URL（*）
			   // method : 'post', // 请求方式（*）post/get
				pagination: false, //分页
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
				$tableOrder.bootstrapTable('resetView');
			  }, 200);
		  }
		
	};
$(document).ready(function () {
    Page.init();
});
