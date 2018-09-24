let Verify = {
    init(){
        /**初始化函数 */
    	this.pageNumber = "";
    	this.pageSize = "";
    	this.sortName = "";
    	this.sortOrder = "";
        this.initTable();
        this.popperFilter();
        //this.modalSubmit();
        this.toOrderDetail();
        this.fileEvent();
        function goBack(){
      	  console.log("提交成功,开始回显:"+data);
      	  $.ajax({
  				type:"post",
     			url:"/credit/front/orderProcess/reallocationJson",
     			data:"model.report_user="+reportt+"&pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-3",
     			dataType:"json",
     			success:function(obj){
     				console.log("回显的数据:"+obj);
     			 	 $("#table").bootstrapTable("load",obj)
     			 }
  			 });
        }
    },
    fileEvent(){
      /**文件上传事件 */
      $(".file-upload").on('change','.uploadFile .file-input',function(){
    	  console.log("开始文件上传==")
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
            fileicon = '../../imgs/order/word.png'
          }else if(filetype === 'xlsx' || filetype === 'xls') {
            fileicon = '../../imgs/order/Excel.png'
          }else if(filetype === 'png') {
            fileicon = '../../imgs/order/PNG.png'
          }else if(filetype === 'jpg') {
            fileicon = '../../imgs/order/JPG.png'
          }else if(filetype === 'pdf') {
            fileicon = '../../imgs/order/PDF.png'
          }
          $(this).parent(".uploadFile").addClass("upload-over");
          $(this).parent(".uploadFile").html(` <button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button><img src=${fileicon} /><p class="filename">${filename}</p>`);
          if($(".uploadFile").length>4) {
            return;
          }
          $(".file-upload").append(`<div class="uploadFile mt-3 mr-4">
                                        <input type="file" name="Files_1" id="upload_file" value="" class="file-input" />
                                        <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                                        <p class="mt-2">上传附件</p>
                                    </div>`);
      });

      /**附件删除 */
      $(".file-upload").on('click','.uploadFile .close',function(){
        $(this).parents(".uploadFile").remove()
        if($(".upload-over").length<5 && $("[class='uploadFile mt-3 mr-4']").length<1 ){
            $(".file-upload").append(`<div class="uploadFile mt-3 mr-4">
                <input type="file" name="" id="upload_file" value="" class="file-input" />
                <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                <p class="mt-2">上传附件</p>
            </div>`);
        }
      })

    },
    toOrderDetail(){
        /**点击订单号跳转订单详情 */
        $(".fixed-table-body-columns table tbody").click(function(e){
            e = e || window.event;
            let order_num = $(e.target).text();
            window.location.href = 'order_detail.html?order_num='+order_num;
        })
    },
    modalSubmit(){/*
        模态框提交事件
        $("#modal_submit").click(function(){
        	let remarks = $("#remarks").val();
            let id = $("#orderId").val();
            let verify_name = $("#verify_name").val();
            let contacts = $("#contacts").val();
            let telphone = $("#telphone").val();
            let address = $("#address").val();
            var formData = new FormData();
            formData.append("model.verify_name",verify_name);
            formData.append("model.searchType",-3);
            formData.append("model.id",id);
            console.log($(".tableValue").serialize());
           $.ajax({
       			type:"post",
       			url:"/credit/front/orderProcess/verifyOfOrderMangerSave",
       			data:$(".tableValue").serialize()"model.verify_name="+verify_name+"&model.remarks="+remarks+"&model.id="+id+"&statusCode=293"+"&searchType=-3"+
       			"&model.contacts="+contacts+"&model.telphone="+telphone+"&model.address="+address,,
       			//dataType:"json",
       			*//**
                 *必须false才会自动加上正确的Content-Type
                 *//*
                 //contentType: false,
                 *//**
                 * 必须false才会避开jQuery对 formdata 的默认处理
                 * XMLHttpRequest会对 formdata 进行正确的处理
                 *//*
                 //processData: false,
       			success:function(data){
       				
       			//提交成功关闭模态窗
       			$(".modal-header .close").trigger("click");
       			//回显
       			goBack();
       			console.log("回显完毕");
       			}
            	
       		})
        })
    */
    	$("#modal_submit").click(function(){
    	$(".tableValue").submit();	
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
            height: $(".table-content").height()*25/26,
            columns: [
                      {
                          title: '订单号',
                          field: 'num',
                          align: 'center',
                          valign: 'middle',
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
                          field: 'right_company_name_en',
                          align: 'center',
                          valign: 'middle',
                        }, {
                          title: '公司中文名称',
                          field: 'company_by_report',
                          align: 'center',
                          valign: 'middle',
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
                            "click .detail":(e,value,row,index)=>{
                              console.log(row);
                              $("#custom_id").html(row.custom_id);
                              $("#customId").html(row.customId);
                              $("#receiver_date").html(row.receiver_date);
                              $("#country").html(row.country);
                              $("#reportType").html(row.reportType);
                              $("#reportLanguage").html(row.reportLanguage);
                              $("#companyNames").html(row.companyNames);
                              $("#custom_id").html(row.custom_id);
                              $("#speed").html(row.speed);
                              $("#user_time").html(row.user_time);
                              $("#companyZHNames").html(row.companyZHNames);
                              $("#reporter_select").html(row.seleteStr);
                              $("#confirm_reason").html(row.confirm_reason);
                              //console.log(row.id+"=====");
                              $("#orderId").val(row.id);
                              $("#num").html(row.num);
                              $("#verify_name").html(row.verify_name);
                              $("#remarks").html(row.remarks);
                              $("#contacts").val(row.contacts);
                              $("#telphone").val(row.telphone);
                              $("#address").html(row.address);
                              pageNumber = row.pageNumber;
                              pageSize = row.pageSize;
                          	  sortName = row.sortName;
                          	  sortOrder = row.sortOrder;
                            }
                          },
                          formatter: _this.operateFormatter
                        }
                      
                    ],
            url : '/credit/front/orderProcess/reallocationJson', // 请求后台的URL（*）
            method : 'post', // 请求方式（*）post/get
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
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
              console.log(params)
              return {//这里的params是table提供的  
            	  pageNumber: params.pageNumber,//从数据库第几条记录开始  
            	  pageSize: params.pageSize,//找多少条  
            	  sortName: params.sortName, 
            	  sortOrder: params.sortOrder,
            	  searchType: "-3"
              };  
          },  
          });
          // sometimes footer render error.
          setTimeout(() => {
            $table.bootstrapTable('resetView');
          }, 200);
      },
      operateFormatter(){
        /**操作按钮格式化 */
        return '<a href="javascript:;" class="detail" data-toggle="modal" data-target="#exampleModalCenter">核实</a>'
      }
      
}


Verify.init();