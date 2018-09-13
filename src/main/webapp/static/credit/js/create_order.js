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
			$("#btn_download").click(function(){
				$("#upload_file").trigger('click')
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
			alert(1)
			//表单验证成功，请求后台接口
			if(formSelect && formInput){
				  $("#orderForm").submit();
			}
		},
		formSave: function(){
			
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
        }
	};
$(document).ready(function () {
    Page.init();
});
