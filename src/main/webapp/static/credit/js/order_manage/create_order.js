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
				Page.initTable()
				Events.modalInfoIsError();
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
				 if(validForm()) {
               		 $("#orderForm").submit();
             	}else{
             		return false;
             	}
				 
			}
		},
		formSave: function(){
			
		},
		closeProgress(){
			$(".close").click(function(){
				$("#Close").hide();
			})
		},
		modalInfoIsError(){
			/**模态窗列表是否有错误信息 */
			let flag = true
			if(flag){
				/**有 */
				 $(".err-box").show()
				 $("#modal_submit").addClass("btn-disabled disabled").removeClass("btn-primary")
			}else {
				$(".err-box").hide()
				$("#modal_submit").removeClass("btn-disabled disabled").addClass("btn-primary")
			}
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
        },
        initTable(){
			/**初始化表格 */
			const $tableOrder = $('#tableOrder');

			$tableOrder.bootstrapTable({
				height: $(".table-modal-content").height(),
				columns: [
					 {
					  title: '序号',
					  field: 'no',
					  align: 'center',
					  valign: 'middle',
					},{
					  field: 'client_id',
					  title: '客户ID',
					  align: 'center'
					}, {
					  field: 'client_name',
					  title: '客户曾用名',
					  align: 'center',
					}, {
					  title: '地区',
					  field: 'region',
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
					  field: 'reprot_lan',
					  align: 'center',
					  valign: 'middle',
					}, {
					  title: '公司名称',
					  field: 'firm_name',
					  align: 'center',
					  valign: 'middle',
					}, {
					  title: '速度',
					  field: 'speed',
					  align: 'center',
					  valign: 'middle',
					}
				  
				],
			   // url : 'firmSoftTable.action', // 请求后台的URL（*）
			   // method : 'post', // 请求方式（*）post/get
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
