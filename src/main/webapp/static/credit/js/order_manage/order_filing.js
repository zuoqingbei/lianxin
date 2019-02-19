let Filing = {
    init(){
        /**初始化函数 */
    	
    	this.pageNumber = "";
    	this.pageSize = "";
    	this.sortName = "";
    	this.sortOrder = "";
    	this.num="";
    	this.numarr=[];
    	this.orderFlag= 0;
        this.initTable();
        this.popperFilter();
        this.modalSubmit(this.numarr);
        this.fileEvent();
        this.select();
        this.dl_btn3();
    },
    dl_btn3(){
    	
    	$("#dl_btn2").click(function(){
    	    $(this).addClass('btn-primaryAc').siblings().removeClass('btn-primaryAc')
    		$("#table").bootstrapTable(
    			"refresh",
    			{query: {statusName: '2'}}
    		)	
    	});
    	$("#dl_btn3").click(function(){
            $(this).addClass('btn-primaryAc').siblings().removeClass('btn-primaryAc')
    		$("#table").bootstrapTable(
    			"refresh"
    		)	
    	});
    	$("#dl_btn1").click(function(){
            $(this).addClass('btn-primaryAc').siblings().removeClass('btn-primaryAc')
    		$("#table").bootstrapTable(
    			"refresh",
    			{query: {statusName: '1'}}
    		)	
    	})
    },
    
    fileEvent(){
    	this.fileNum = 0;
    	let that = this;
    	$(".close").click(function(){
    		that.fileNum = 0;
    	});
      /**文件上传事件 */
      $(".file-upload2").on('change','.uploadFile .file-input',function(e){
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
          $(e.target).parent(".uploadFile").addClass("upload-over");
          $(e.target).css("visibility","hidden")
          $(e.target).siblings(".over-box").html(`<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
          console.log($(e.target).parents(".uploadFile").siblings(".uploadFile").length)
          if($(e.target).parents(".uploadFile").siblings(".uploadFile").length>3) {
            return;
          }
          $(e.target).parents(".file-upload2").append(`<div class="uploadFile mt-3 mr-4">
                                        <input type="file" name="" id="upload_file" value="" class="file-input" />
                                        <div class="over-box">
                                          <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                                          <p class="mt-2">上传附件</p>
                                        </div>
                                    </div>`);
      });

      /**附件删除 */
      $(".file-upload2").on('click','.uploadFile .close',function(e){
    	  
	        if($(e.target).parents(".upload-over").length<6 && $(e.target).parents(".upload-over").siblings(".uploadFile.mt-3.mr-4:not(.upload-over)").length<1 ){
	        	$(e.target).parents(".file-upload2").append(`<div class="uploadFile mt-3 mr-4">
	                <input type="file" name="" id="upload_file" value="" class="file-input" />
	                 <div class="over-box">
		                <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
		                <p class="mt-2">上传附件</p>
		               </div>
	            </div>`);
	        }
    	  $(this).parents(".uploadFile").remove()
      })

    },
    modalSubmit(numarr){
        /**模态框提交事件 */
    	let _this = this
        	 $("#modal_submit").click(function(){
        		 console.log($("#exampleModalCenter .upload-over").length)
        		 if($("#exampleModalCenter .upload-over").length === 5) {
        			 $("#exampleModalCenter .file-upload").append(`<div class="uploadFile mt-3 mr-4">
                             <input type="file" name="" id="upload_file" value="" class="file-input" />
                             <div class="over-box">
                               <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                               <p class="mt-2">上传附件</p>
                             </div>
                         </div>`);
        		 }
    			 $(".upload-over").remove();
        		 
             	$("#status").val("296");
             	$(".tableValue").ajaxSubmit( {
             		success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                        // 此处可对 data 作相关处理
                        //alert(JSON.stringify(data));
             			 console.log(JSON.stringify(data));
                        console.log(data.statusCode);
                        if(data.statusCode===1){
                        	 console.log("此处进入success状态,状态296");
                        	Public.message("success",data.message);
                        	   //提交成功关闭模态窗
                            $(".modal-header .close").trigger("click");
                            //回显
                           	console.log("提交成功,开始回显:");
                           		/*$.ajax({
                           			type:"post",
                               		url:BASE_PATH+"credit/front/orderProcess/listJson",
                               		data:"pageNumber="+_this.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-4",
                               		dataType:"json",
                               		success:function(obj){
                               				//console.log("回显的数据:"+JSON.stringify(obj.rows));
                               			 	$("#table").bootstrapTable("load",obj);
                               			 	console.log(obj);
                               			 console.log("回显成功!");
                               			 }
                           			})*/
                        	$("#table").bootstrapTable("refresh");
                        }else{
                        	 console.log("此处进入error状态");
                        	Public.message("error",data.message);
                        }
                        //Public.message(a,b)  a为info,success,error   b为接口返回的信息
                        
                    }, error: function(ret) {
            			console.log(ret);
            			console.log(ret.readyState);
            			console.log(ret.responseText);
            			console.log(ret.responseText.result);
            			console.log("提交失败!");
            		}
                 });
               
             }),
//             订单确认
             $("#btn_submit").click(function(){
            	 
        		  if($("#agency_id").val()==-1 || $("#agency_id").val()=="") {
        	      		Public.message("error","请选择代理ID");
        	      	  return false;
        	      	  }
                 let agentid = $("#agency_id option:selected").val();
                 let ismail = $("#entrust_email option:selected").val();
                 let id = $("#orderId2").val();
                 let country = $("#countryid").val();
                 let speed = $("#speedid").val();
                 let num = $("#orderNum3").text();
                 let ids=this.numarr;
                 console.log(ids);
                $.ajax({
            			type:"post",
            			url:BASE_PATH+"credit/front/orderProcess/orderAgentAbroadSave",
            			data:"agent_id="+agentid+"&ismail="+ismail+"&model.id="+id+"&model.num="+num+"&statusCode="+"295"+"&orderId="+id+"&country="+country+"&speed="+speed+"&ids="+ids,
            			dataType:"json",
            			success:function(data){
            			
            			if(data.statusCode===1){
                       	console.log("此处进入success状态2222222222");
                       	Public.message("success",data.message);
                      	//提交成功关闭模态窗
                       	$(".modal-header .close").trigger("click");
           			 		$("#table").bootstrapTable("refresh");
                       }else{
                       	 console.log("此处进入error状态");
                       	Public.message("error",data.message);
                       }
            			}
            		})
	    			
             }),
             
             $("#modal_submit_allocation2").click(function(){
            	 console.log(numarr);
            	 console.log("分配事件:"+BASE_PATH);
        		  if($("#agency_id").val()==-1 || $("#agency_id").val()=="") {
        	      		Public.message("error","请选择代理ID");
        	      	  return false;
        	      	  }
                 let agentid = $("#agency_id2 option:selected").val();
                 let ismail = $("#entrust_email option:selected").val();
                 let id = $("#orderId2").val();
                 let country = $("#countryid").val();
                 let speed = $("#speedid").val();
                 let num = $("#num2").text();
                 let ids=numarr;
                 console.log(ids+"jjjj"+this.numarr);
                 $.ajax({
            			type:"post",
            			url:BASE_PATH+"credit/front/orderProcess/orderAgentAbroadSave",
            			data:"agent_id="+agentid+"&ismail="+ismail+"&model.id="+id+"&model.num="+num+"&statusCode="+"295"+"&orderId="+id+"&country="+country+"&speed="+speed+"&ids="+ids,
            			dataType:"json",
            			success:function(data){
            			
            			if(data.statusCode===1){
                       	 console.log("此处进入success状态2222222222");
                       	Public.message("success",data.message);
                      //提交成功关闭模态窗
                       	$(".modal-header .close").trigger("click");
                       	$.ajax({
    	           			type:"post",
    	               		url:BASE_PATH+"credit/front/orderProcess/listJson",
    	               		data:"pageNumber="+_this.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-4",
    	               		dataType:"json",
    	               		success:function(obj){
    	           			 	$("#table").bootstrapTable("load",obj);
    	           			 $(".modal-header .close").trigger("click");
    	           			 }
    	       			})
                      
                       
                       }else{
                       	 console.log("此处进入error状态");
                       	Public.message("error",data.message);
                       }
            			}
            		})
	    			
             }),
             $("#modal_submit1").click(function(){
             	console.log("点击提交");
             	$("#status").val("292");
             	$(".tableValue1").ajaxSubmit({
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
              	      		data:"pageNumber="+_this.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
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
                     			data:"pageNumber="+_this.pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
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
    select(){
    	 
        	 //代理ID
        	  layui.use('form', function(){
    	        var form = layui.form;
               $.ajax({
          			type:"get",
          			 url : BASE_PATH+"credit/front/orderProcess/getAgent",
          			dataType:"json",
          			success:function(data){ 
          				var html="";
          				 html+="<option value='-1' selected='selected'>请选择</option>"
          				for(item in data){
          			 	html+="<option  m-type='"+data[item].agent_id+"' value='"+data[item].agent_id+"'>"+data[item].agent_id+"</option>";
          	        }
          	        
          	        $("select[name='agent_id']").html(html);
          	      form.render('select');
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
              height: $(".table-content").height()*0.93,
              clickToSelect:true,
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
    }, {
        title: '是否分配',
        field: 'status',
        align: 'center',
        valign: 'middle',
      },{
      field: 'operate',
      title: '操作',
      align: 'center',
      events: {
        "click .detail":(e,value,row,index)=>{
          console.log(row);
          $("#custom_id").html(row.custom_id);
          $("#customId").html(row.customId);
          $("#receiver_date").html(row.receiver_date);
          $("#country").html(row.country);
          $("#continent").html(row.continent);
          $("#reportType").html(row.reportType);
          $("#orderType").html(row.orderType);
          $("#reportLanguage").html(row.reportLanguage);
          $("#companyNames").html(row.companyNames);
          $("#speed").html(row.speed);
          $("#user_time").html(row.user_time);
          $("#companyZHNames").html(row.companyZHNames);
          $("#reporter_select").html(row.seleteStr);
          $("#confirm_reason").html(row.confirm_reason);
          $("#orderId").val(row.id);
          $("#status").val(row.status);
          $("#num").html(row.num);
          $("#remarks").val("");
          $(".tableValue")[0].reset();
          
          $("#verify_name").val(row.verify_name);
          $("#contacts").val(row.contacts);
          $("#telphone").val(row.telphone);
          $("#address").html(row.address);
          $("#remarks").html(row.remarks);
          
          _this.pageNumber = row.pageNumber;
          pageSize = row.pageSize;
      	sortName = row.sortName;
      	sortOrder = row.sortOrder;
      	  console.log("report_userKey====="+row.report_userKey);
      	  $(".detail").removeAttr("data-toggle");
      	  $(".detail").removeAttr("data-target");
      	  var status=$("#status").val();
      	/*  if("295"!=status) {
      		Public.message("error","请先进行代理分配才可以上传附件");
      	  }else {*/
      		//data-toggle="modal" data-target="#exampleModalCenter" 
      		  $(".detail").attr("data-toggle","modal");
      		  $(".detail").attr("data-target","#exampleModalCenter");
      	 /* }*/
          
        },
        "click .dl":(e,value,row,index)=>{
        	 console.log(12222222333333);
            console.log(row);
            //orderId
            $("#orderId2").val(row.id)
            //订单号
            $("#orderNum3").text(row.num)
           //代理ID
            $("#agency_id").next().find('.layui-anim').children('dd[lay-value="'+row.agent_id+'"]').click()
            //代理价格
            $("#agentPrice").val(row.agentPrice)
            //价格币种
            $("#agentCurrency").val(row.agentCurrency)
            //Working days
            $("#agentTime").val(row.agentTime)
            
            //地区
             $("#client_area").next().find('.layui-anim').children('dd:contains('+row.continent+')').click()
           //国家
           $("#client_country").next().find('.layui-anim').children('dd:contains('+row.country+')').click()
           //接单日期
           $("#client_order_date").val(row.receiver_date)
           //报告类型
          $("#client_reportType").next().find('.layui-anim').children('dd:contains('+row.reportType+')').click()
            //订单类型
          $("#client_orderType").next().find('.layui-anim').children('dd[lay-value="'+row.order_type+'"]').click()
            //报告语言
          $("#client_reportLanguage").next().find('.layui-anim').children('dd:contains('+row.reportLanguage+')').click()
          //订单公司名称
          $("#right_company_name_en").val(row.companyNames)
          //速度
          $("#client_speed").next().find('.layui-anim').children('dd:contains('+row.speed+')').click()
            //报告用时
          $("#client_reportTime").val(row.report_user)
          //客户参考号
          $("#client_referenceNumber").val(row.reference_num)
          //地址
          $("#client_address").val(row.address)
          //电话
          $("#client_telephone").val(row.telphone)
          //传真
          $("#client_fax").val(row.fax)
          //联系人
          $("#client_contact").val(row.contacts)
          //email
          $("#client_email").val(row.email)
          //备注
          $("#remakes").val(row.remarks)
  	},
  
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
              pageList: [10,20,30,50],
              smartDisplay:false,
              iconsPrefix:'fa',
              locales:'zh-CN',
              fixedColumns: false,
              fixedNumber: 2,
              sortOrder: "desc",//排序方式
              queryParamsType:'',
              contentType:'application/x-www-form-urlencoded;charset=UTF-8',
              queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                console.log(params)
               /* this.pageNumber = params.pageNumber;
                this.pageSize = params.pageSize;
                this.sortName = params.sortName;
                this.sortOrder = params.sortOrder;*/
                return {//这里的params是table提供的  
              	  pageNumber: params.pageNumber,//从数据库第几条记录开始  
              	  pageSize: params.pageSize,//找多少条  
              	  sortName: params.sortName, 
              	  sortOrder: params.sortOrder,
              	  searchType: "-4",
                };  
            },  
            onLoadSuccess:(data)=>{
            	console.log(data)
            	let rows = data.rows;
            	rows.forEach((item,index)=>{
            		if("295"===item.status) {
            			$($(".dl").get(index)).css({"color":"#ccc","cursor":"default"})
            			$($(".dl").get(index)).removeAttr("data-target")
            		}
            	})
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
            return '<div><a href="javascript:;" class="dl" data-toggle="modal" data-target="#exampleModalCenter_allocation">代理确认</a>' +
                '<span style="margin-left:.5rem;color: #1890ff">|</span>' +
                '<a href="javascript:;" class="detail" style="margin-left:.5rem">上传附件</a></div>'
                // +
                // '<div style="margin-top:.5rem"><a href="javascript:;" class="order-hs" data-toggle="modal" data-target="#exampleModalCenter4">确认信息</a>'
                // '<span style="margin-left:.5rem;color: #1890ff">|</span>' +
                // '<a href="javascript:;" class="order-cancel" style="margin-left:.5rem" data-toggle="modal" data-target="#exampleModalCenter1">订单撤销</a></div>'
          }             
}
Filing.init();
//给渲染完的固定列绑定方法
$("table").on('click', '.detail3',function(){
    let rows=$(this).attr('data-row');
    let row=JSON.parse(decodeURIComponent(rows));//解码
    Public.goToOrderDetail(row.id,row)
})
