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
					$.ajax({
						type: "POST", // 数据提交类型
						url: BASE_PATH+"credit/orderpoimanager/importExcel", // 发送地址
						data: formData, //发送数据
						dataType:"json",
						async: true, // 是否异步
						processData: false, //processData 默认为false，当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
						contentType: false, //
						success:(data)=>{
							/**成功 */
							if(data.statusCode===4){
								Public.message("error",data.message);
							}
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
							if(data.isTheSameOrderMark.statusCode===2){
								console.log(data.isTheSameOrderMark)
								 $(".tips-box span").html(data.isTheSameOrderMark.message)
								 $(".tips-box").show()
								 $("#modal_submit").addClass("btn-disabled disabled").removeClass("btn-primary")
								
				               }else{
				            	 $(".tips-box").hide()
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
		$("body").mLoading("show");//显示loading组件
	    $.ajax({
	        type: "POST",
	        url: "/credit/orderpoimanager/savedata",
	        contentType: "application/json; charset=utf-8",
	        data: JSON.stringify(jsondata),
	        dataType: "json",
	        success: function (data) {
	        	$("body").mLoading("hide");//
	        	if(data.statusCode===1){
               	Public.message("success",data.message);
               	$("#importModal").find(".close").trigger("click")
               }else if(data.statusCode===2){
               	Public.message("error",data.message);
               }else if(data.statusCode===3){
                  	Public.message("info",data.message);
                   	$("#importModal").find(".close").trigger("click")
                   }
	        },
	        error: function (message) {
	            $("#request-process-patent").html("提交数据失败！");
	        }
	     });
	   })	
			
			
			
		},
		
		download_mod(){
			/**点击下载模板 */
			$("#btn_download").click(()=>{
				window.open("/static/credit/assets/files/order_mod.xls")
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
			console.log('提交订单')
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
				var value=$("#priceid").val();
				if(value==0){
					Public.message("error","未发现该报告价格,请联系管理员");
					return;
				}
//				isTheSameCompany();暂时取消快速递交报告的功能
				tableSubmit();
			    $('#exampleModalCenter3 .close').trigger('click')
			}
		},
		/*formSave: function(){
			$("input[name='attr.status']").val("289");
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
		},*/
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
			$("#btn_save").click(Events.formSave);
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
			Events.download_mod();
			Page.fileEvent();
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
							  class:"wrap"
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
							  class:"wrap"
							}, {
							  title: '订单类型',
							  field: 'order_type',
							  align: 'center',
							  valign: 'middle',
							  class:"wrap"
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
							  class:"wrap"
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
		  },
		  fileEvent(){
    	this.fileNum = 0;
    	let that = this;
    	$(".close").click(function(){
    		that.fileNum = 0;
    	});
      /**文件上传事件 */
      $(".file-upload").on('change','.uploadFile .file-input',function(){
    	  that.fileNum = that.fileNum+1;
          /**如果上传成功 */
          let filename = $(this).val().replace("C:\\fakepath\\","");
          let num = filename.split(".").length;
          let filename_qz = []
          for(let i=0;i<num-1;i++){  
            filename_qz =  filename_qz.concat(filename.split(".")[i])
          }
          filename_qz_str = filename_qz.join('.')
          if(filename_qz_str.length>4) {
            filename_qz_str = filename_qz_str.substr(0,2) + '..' + filename_qz_str.substr(filename_qz_str.length-2,2)
          }
          
          let filetype = filename.split(".")[num-1];
          filename = filename_qz_str + '.' +filetype
          let fileicon = '';
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
          }else if(filetype === 'html') {
            fileicon = '/static/credit/imgs/order/html.png'
          }
          $(this).parent(".uploadFile").addClass("upload-over");
          $(this).css("visibility","hidden")
          $(this).siblings(".over-box").html(`<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
          if($(".uploadFile").length>4) {
            return;
          }
          $(".file-upload").append(`<div class="uploadFile mt-3 mr-4">
                                        <input type="file" name="Files_${that.fileNum}" id="upload_file" value="" class="file-input" />
                                        <div class="over-box">
                                          <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                                          <p class="mt-2">上传附件</p>
                                        </div>
                                    </div>`);
      });

      /**附件删除 */
      $(".file-upload").on('click','.uploadFile .close',function(){
        $(this).parents(".uploadFile").remove()
        
        if($(".upload-over").length<5 && $("[class='uploadFile mt-3 mr-4']").length<1 ){
            $(".file-upload").append(`<div class="uploadFile mt-3 mr-4">
                <input type="file" name="" id="upload_file" value="" class="file-input" />
                 <div class="over-box">
	                <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
	                <p class="mt-2">上传附件</p>
	             </div>
            </div>`);
        }
      })

    },
	};
$(document).ready(function () {
    Page.init();
});
 function reste(){
 	$(".upload-over").remove()
 	$(".reste").val("");
    	
	/*layui.use('form', function(){
	  var form = layui.form;
	  
	  //各种基于事件的操作，下面会有进一步介绍
	  form.render('select');
	});*/
 }
 function isTheSameCompany(){
	 var companyname=$("#right_company_name_en").val();
	 var report_type=$("select[name='attr.report_type']").find("option:selected").val();
	 var report_language=$("select[name='attr.report_language']").find("option:selected").val();
	 console.log('samecompany',companyname,report_type,report_language)
   	 $.ajax({
   		 	type: "get",
   		 	contentType: "application/json; charset=utf-8",
   		 	url:"/admin/ordermanager/isTheSameCompany",
	        data:{
	        	"companyname":encodeURI(companyname),
	        	"report_type":report_type,
	        	"report_language":report_language
	        },	
	        dataType:"json",
	        success: function(data) {
	        	console.log(data)
	        	if(data.flag.statusCode===1){
	        		$("#reporttime").html(data.result.receiver_date);
	        		$("#isfinance").html(data.result.is_hava_finance);
	        		$("#lastFiscalYear").html(data.result.last_fiscal_year);
	        		$("#is_fastsubmit").val(data.result.id);
	        		//弹出提示
	        		$("#show_checked_modal").trigger("click")
           		 }else{
           			tableSubmit();
           		 }
	        	
	        },
	    });
}
 $("#modal_checked_submit").click(function(){
		 
		  tableSubmit();
 })
 $("#modal_checked_create").click(function(){
		  $("#is_fastsubmit").val("-1");
		  tableSubmit();
 })
  function tableSubmit(){
		$("input[name='attr.status']").val("291");
		$("body").mLoading("show");//显示loading组件
		$("#orderForm").ajaxSubmit({
			success:function(data){
				$("body").mLoading("hide");
				$("#checkedModal .close").trigger("click")
				  if(data.statusCode===1){
            		Public.message("success",data.message);
            		//Public.goList();
            		reste();
           		 }else if(data.statusCode===3){
           			Public.message("info",data.message);
            		//Public.goList();
            		reste();
           		 }
				  else{
            		Public.message("error",data.message);
            		//Public.goList();
            		reste();
            	}

			},
			error:function(data){
				$("body").mLoading("hide");//显示loading组件
				Public.message("error",data.message);
				//Public.goList();
				reste();
			}
		});
 }
