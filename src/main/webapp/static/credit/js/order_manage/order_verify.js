let Verify = {
    init(){
        /**初始化函数*** */
    	this.num="";
    	this.numarr=[];
    	this.pageNumber = "";
    	this.pageSize = "";
    	this.sortName = "";
    	this.sortOrder = "";
        this.initTable();
        this.popperFilter();
        this.modalSubmit(this.numarr);
        //this.toOrderDetail();
        this.fileEvent();
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
          }else {
              Public.message("info","不支持上传此种类型文件！")
              return
            }
          $(this).parent(".uploadFile").addClass("upload-over");
          $(this).css("visibility","hidden")
          $(this).siblings(".over-box").html(`<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
          if($(".uploadFile").length>4) {
            return;
          }
          $(".file-upload").append(`<div class="uploadFile mt-3 mr-4 mb-5">
                                        <input type="file" name="Files_${that.fileNum}" id="upload_file" value="" class="file-input" />
                                        <div class="over-box">
                                          <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                                          <p class="mt-2">上传附件</p>
                                        </div>
                                    </div>`);
      });

      /**附件删除 */
      $(".file-upload").on('click','.uploadFile .close',function(e){
    	 $.ajax({
     			type:"post",
         		url:BASE_PATH+"credit/front/orderProcess/deleteFile",
         		data:"model.id="+$(this).parents(".uploadFile").attr("fileId"),
         		dataType:"json",
         		success:function(obj){
	         			$(e.target).parents(".uploadFile").remove()
	        	        if($(".upload-over").length<5 && $("[class='uploadFile mt-3 mr-4 mb-5']").length<1 ){
	        	            $(".file-upload").append(`<div class="uploadFile mt-3 mr-4 mb-5">
	        	                <input type="file" name="Files_${that.fileNum}" id="upload_file" value="" class="file-input" />
        	                	  <div class="over-box">
	        	                	<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
	        	                	<p class="mt-2">上传附件</p>
	        	                  </div>
	        	                </div>`);
	        	        }
         				$.ajax({
	              			type:"post",
	                  		url:BASE_PATH+"credit/front/orderProcess/listJson",
	                  		data:"pageNumber="+that.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
	                  		dataType:"json",
	                  		success:function(obj){
	                  				$("#table").bootstrapTable("load",obj);
	                  			 }
	              			}) 
         			 }
     	}) 
       
      })

    },
    modalSubmit(numarr){
        /**模态框提交事件 */
    	let that = this
    	console.log("模态框提交事件");
    	$("#exampleModalCenter3").click(function(){
        	let ids=that.numarr;
        	console.log(ids+"=====");
        	 if(numarr.length===0){
     		 	Public.message("error","请选择需要撤销的订单");
     		 	$(".modal-header .close").trigger("click");
     		 }else{
        	 $.ajax({
        			type:"post",
        			url:"/credit/front/home/cheXiao?ids="+ids,
        			data:{
        				"revoke_reason":$("#revoke_reason").val(),
        				},
        			 dataType:"json",
        		        success: function (data) {
        		        	if(data.statusCode===1){
        	               	Public.message("success",data.message);
                            //提交成功关闭模态窗
                          	$(".modal-header .close").trigger("click");
          				    $("#table").bootstrapTable("refresh");
        	               }else{
        	               	Public.message("error",data.message);
        	                $("#exampleModalCenter2 .close").trigger("click")
        	                loadtable();
        	               }
        		        }
        		        
        		});
     		 }
        	 })
    	
        $("#modal_submit").click(function(){
        	console.log("点击提交");
        	$("#status").val("816");
        	$(".tableValue").ajaxSubmit({
        		success:function(data){
        			console.log("状态为成功,message:"+data.message);
        			if(data.statusCode===1){
                   	 console.log("此处进入success状态2222222222");
                   	Public.message("success",data.message);
                    //提交成功关闭模态窗
                    $(".modal-header .close").trigger("click");
                    //回显
         	  		$.ajax({
         	  			type:"post",
         	      		url:BASE_PATH+"credit/front/orderProcess/listJson",
         	      		data:"pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
         	      		dataType:"json",
         	      		success:function(obj){
         	      				console.log("回显的数据:"+obj);
         	      			 	$("#table").bootstrapTable("load",obj);
         	      			 	console.log("回显成功!");
         	      			 }
         	  			}) 
                   }else{
                   	 console.log("此处进入error状态");
                   	Public.message("error",data.message);
                   }
        		},error :function(data){
        			console.log("状态为失败,message:"+data.message);
        			Public.message("error",data.message);
        		}
            });
           
        })
        
         $("#modal_save").click(function(){
        	$("#status").val("");
        	$(".tableValue").ajaxSubmit({
        		success:function(data){
        			 //提交成功关闭模态窗
        	           $(".modal-header .close").trigger("click");
        	           //加载表格数据
        			 $.ajax({
            				type:"post",
                			url:BASE_PATH+"credit/front/orderProcess/listJson",
                			data:"pageNumber="+that.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
                			dataType:"json",
                			success:function(obj){
                				console.log("回显的数据:"+obj);
                			 	$("#table").bootstrapTable("load",obj)
                			 }
            		 })
        			if(data.statusCode===1){
                   	 console.log("此处进入success状态2222222222");
                   	Public.message("success",data.message);
                   }else{
                   	 console.log("此处进入error状态");
                   	Public.message("error",data.message);
                   }
        		},error :function(data){
        			console.log("状态为失败,message:"+data.message);
        			Public.message("error",data.message);
        		}
            });
           
          
        })
        
         $("#modal_cancel").click(function(){
        	 //撤销313
        	 let reason = $("#revoke_reason").val()
        	 let id = $("#orderId1").val();
             $.ajax({
        			type:"post",
        			 url : BASE_PATH+"credit/front/orderProcess/statusSave",
        			data:"model.revoke_reason="+reason+"&model.id="+id+"&statusCode=313"+"&searchType=-1",
        			dataType:"json",
        			success:function(data){
        			//提交成功关闭模态窗
        			 $(".modal-header .close").trigger("click");
	        			if(data.statusCode===1){
	                   	Public.message("success",data.message);
	                   }else{
	                   	Public.message("error",data.message);
	                   }
	        			//回显
	        			$("#table").bootstrapTable("refresh")
        			}
             	
        		})
        		
             
         })
      
        
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
          $('.deal-state').toggleClass("deal-state-show")
          var value1 = $("#defaultCheck1").prop("checked");
  
          /**发起ajax请求  获取表格数据*/
        })

        /**点击重置按钮 */
        $(".resetrFilter").click(function(){
          $('.form-check-input:checkbox').removeAttr('checked');
        })
      },
  
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this
  

        $table.bootstrapTable({
            height: $(".table-content").height()*0.98,
            columns: [
{
    checkbox: true,
    visible: true,                  //是否显示复选框
    filed:'state',
    width: '18rem',
}, 
{
    title: '订单号',
    field: 'num',
    align: 'center',
    valign: 'middle',
    formatter:function(value,row,index){
        rows=encodeURIComponent(JSON.stringify(row));//对json字符串编码
        return `<a href="javascript:;" style="color:#1890ff" class="detail3" data-row=${rows}>${value}</a>  `;
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
    title: `订单状态 &nbsp;<i class="fa fa-filter"></i>`,
    field: 'statusName',
    align: 'center',
    valign: 'middle',
  
  }, {
    title: '订单公司名称',
    field: 'companyNames',
    align: 'center',
    valign: 'middle',
    class:"wrap"
  }, {
    title: '公司中文名称',
    field: 'companyZHNames',
    align: 'center',
    valign: 'middle',
    class:"wrap"
  }, {
    title: '国家',
    field: 'country',
    align: 'center',
    valign: 'middle',
  }, {
    title: '是否有财务信息',
    field: 'is_hava_finance',
    align: 'center',
    valign: 'middle',
  }, {
    title: '最近财务信息年份',
    field: 'last_fiscal_year',
    align: 'center',
    valign: 'middle',
  }, {
    title: '报告类型',
    field: 'reportType',
    align: 'center',
    valign: 'middle',
    class:"wrap"
  }, {
    title: '报告员',
    field: 'reportUser',
    align: 'center',
    valign: 'middle',
  }, {
    title: '翻译员',
    field: 'translateUser',
    align: 'center',
    valign: 'middle',
  }, {
    title: '分析员',
    field: 'analyzeUser',
    align: 'center',
    valign: 'middle',
  },{
    field: 'operate',
    title: '操作',
    align: 'center',
    events: {
    	"click .order-cancel":(e,value,row,index)=>{
    	        $("#custom_id1").html(row.custom_id);
    	        $("#customId1").html(row.customId);
    	        $("#receiveDate1").html(row.receiver_date);
    	        $("#area1").html(row.continent)
    	        $("#country1").html(row.country);
    	        $("#reportType1").html(row.reportType);
    	        $("#reportLanguage1").html(row.reportLanguage);
    	        $("#companyNames1").html(row.companyNames);
    	        $("#speed1").html(row.speed);
    	        $("#user_time1").html(row.user_time);
    	        $("#companyZHNames1").html(row.companyZHNames);
    	        $("#reporter_select1").html(row.seleteStr);
    	        $("#confirm_reason1").html(row.confirm_reason);
    	        $("#orderId1").val(row.id);
    	        $("#num1").html(row.num);
    	        $("#remarks1").val("");
    	        $("#orderType1").html(row.orderType)
    	        $("#contacts").val(row.contacts);
    	        $("#telphone").val(row.telphone);
    	        $("#address").html(row.address);
    	        $("#remarks").html(row.remarks);
    	},
      "click .detail":(e,value,row,index)=>{
        console.log(row);
        $("#custom_id").html(row.custom_id);
        $("#customId").html(row.customId);
        $("#receiver_date").html(row.receiver_date);
        $("#continent").html(row.continent)
        $("#country").html(row.country);
        $("#reportType").html(row.reportType);
        $("#reportLanguage").html(row.reportLanguage);
        $("#companyNames").html(row.companyNames);
        $("#orderType").html(row.orderType)
        $("#custom_id").html(row.custom_id);
        $("#speed").html(row.speed);
        $("#user_time").html(row.user_time);
        $("#companyZHNames").html(row.companyZHNames);
        $("#reporter_select").html(row.seleteStr);
        $("#confirm_reason").html(row.confirm_reason);
        $("#orderId").val(row.id);
        $("#num").html(row.num);
        $("#remarks").val("");
        $(".tableValue")[0].reset();
        
        $("#verify_name").val(row.verify_name);
        $("#contacts").val(row.contacts);
        $("#telphone").val(row.telphone);
        $("#address").html(row.address);
        $("#remarks").html(row.remarks);
        pageNumber = row.pageNumber;
        pageSize = row.pageSize;
    	 sortName = row.sortName;
    	  sortOrder = row.sortOrder;
        //文件回显
        console.log(row.files) 	
       // $(".file-upload").html("");
        $(".upload-over").remove();
        if(row.files.length === 0){$(".uploadFile:not(.upload-over)").show();return}
        console.log(row.files.length)
        if(row.files.length > 4) {
        	$(".uploadFile:not(.upload-over)").hide();
        }else {
        	$(".uploadFile:not(.upload-over)").show()
        }
        for (var i in row.files){
        	let filetype = row.files[i].ext.toLowerCase()
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
        	let filename = row.files[i].originalname
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
        	fileArr += '<div class="uploadFile mt-3 mr-4 mb-5 upload-over" fileId="'+row.files[i].id+'" url="'+row.files[i].url+'" style="cursor:pointer">'+
        				'<div class="over-box">'+
	        				'<button type="button" class="close" aria-label="Close">'+
	        					'<span aria-hidden="true">&times;</span>'+
	        				 '</button>'+
	        				'<img src="'+fileicon+'" />'+
	        				 '<p class="filename" title="'+all_name+'" >'+filename+'</p>'+
        				 '</div>'+
        				 '</div>'
        $(".file-upload>label").after(fileArr)	 
           
           $(".upload-over").click(function(e){
        	   console.log($(e.target))
        	   if($(e.target).parent().attr("class") === 'close') {
        		   return
        	   }
        	   window.open($(this).attr("url"))
        	   
           })
        }
        
        _this.pageNumber = row.pageNumber;
        pageSize = row.pageSize;
    	sortName = row.sortName;
    	sortOrder = row.sortOrder;
    	  console.log("report_userKey====="+row.report_userKey);
      }
    },
    formatter: _this.operateFormatter
  }

],
            url : BASE_PATH+'credit/front/orderProcess/listJson', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber:1,
            pageSize:10,
            pageList: [10,20,30],
            smartDisplay:false,
            iconsPrefix:'fa',
            locales:'zh-CN',
            fixedColumns: false,
            fixedNumber: 1,
            queryParamsType:'',
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
              console.log(params)
              /*this.pageNumber = params.pageNumber;
              this.pageSize = params.pageSize;
              this.sortName = params.sortName;
              this.sortOrder = params.sortOrder;*/
              return {//这里的params是table提供的  
            	  pageNumber: params.pageNumber,//从数据库第几条记录开始  
            	  pageSize: params.pageSize,//找多少条  
            	  sortName: params.sortName, 
            	  sortOrder: params.sortOrder,
            	  searchType: "-3"
              };  
          },
          onCheck:(row)=>{
          	console.log(row)
          	this.numarr.push(row.id)
           //  this.numarr.split().join(",");
          	console.log(this.numarr);
          },
          onUncheck:(rows)=>{
          	let index =this.numarr.indexOf(rows.id)
              this.numarr.splice(index,1);
          	console.log(this.numarr);
          },onCheckAll:(rows)=>{
         	 for(var i=0;i<rows.length;i++){
         		this.numarr.push(rows[i].id) 
         		console.log(this.numarr);
         	 }
          },onUncheckAll:(rows)=>{
	           	 for(var i=0;i<rows.length;i++){
	           		let index =this.numarr.indexOf(rows[i].id)
	                this.numarr.splice(index,1); 
	           		console.log(this.numarr);
	           	 }
          }
          });
          // sometimes footer render error.
          setTimeout(() => {
            $table.bootstrapTable('resetView');
          }, 200);
      },
      operateFormatter(){
        /**操作按钮格式化 */
        return '<a href="javascript:;" class="detail"	style="margin-right:.5rem" data-toggle="modal" data-target="#exampleModalCenter">订单核实</a>'+
        '<span style="margin-right:.5rem;color: #1890ff">|</span>' +
        '<a href="javacript:;" class="order-cancel"  data-toggle="modal" data-target="#exampleModalCenter1">订单撤销</a>' 
      }       
}


Verify.init();
//给渲染完的固定列绑定方法
$("table").on('click', '.detail3',function(){
    let rows=$(this).attr('data-row');
    let row=JSON.parse(decodeURIComponent(rows));//解码
    Public.goToOrderDetail(row.id,row)
})