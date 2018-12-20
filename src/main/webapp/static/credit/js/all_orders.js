
let Index = {
    init(){
        this.initTable();
        this.dateForm();
        this.popperFilter();
        this.hideShowStyle();
        this.searchEvent();
        this.fileEvent();
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#txt_search_date'
        });
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
                <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAABxSURBVGhD7c9BCsAwCABB//82/9RcPJRibg1rYAe8BCRuSDojM5/31PM9DKAZQDOAZgDNAJoBNANoBtCwgO/H06bO3OuWJk2dudctTZo6c69bmjR15nnYx38xgGYAzQCaATQDaAbQDKAZQLs+QLpCxAKykAXNUf4CGwAAAABJRU5ErkJggg==">
                <p class="mt-2">上传附件</p>
            </div>`);
        }
      })

    },
    popperFilter(){
    	let that=this;
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

        $(".enterFilter").click(function(){
            that.checked=[];
            that.checkchar="";
            $("input[name='status']:checked").each(function(i){
                that.checked[i] = $(this).val();
                that.checkchar+=that.checked[i]+","
            });
            $("#table").bootstrapTable("refresh",{
                query:{
                    pageNo: that.paramsObj.pageNumber,//页码
                    recordsperpage:  that.paramsObj.pageSize,//每页多少条
                    sortName: that.paramsObj.sortName,
                    sortOrder: that.paramsObj.sortOrder,
                    custom_id:$("#custom_id").find("option:selected").val(),
                    country:$("#country").find("option:selected").val(),
                    end_date:$("#dead_date").val(),
                    agent_id:$("#agentId").find("option:selected").val(),
                    company_by_report:$("#txt_search_departmentname").val(),
                    right_company_name_en:$("#txt_search_companyEngName").val(),
                    num:$("#num").val(),
                    reference_num:$("#reference_num").val(),
                    status:that.checkchar
                }
            });
            getMessage();
            console.log('orde')
        })

        /**点击重置按钮 */
        $(".resetrFilter").click(function(){
            $('.form-check-input:checkbox').removeAttr('checked');
        })
    },
    searchEvent(){
		let that=this;
        $("#btn_query").click(function(){
        	that.checked=[];
			 that.checkchar="";
	     	$("input[name='status']:checked").each(function(i){
                that.checked[i] = $(this).val();
                that.checkchar+=that.checked[i]+","
            });
        	/**发起ajax请求  获取表格数据*/
           $("#table").bootstrapTable("refresh",{
				query:{
					pageNo: that.paramsObj.pageNumber,//页码
                  recordsperpage:  that.paramsObj.pageSize,//每页多少条
                  sortName: that.paramsObj.sortName,
                  sortOrder: that.paramsObj.sortOrder,
                  custom_id:$("#custom_id").find("option:selected").val(),
                  country:$("#country").find("option:selected").val(),
    			  end_date:$("#dead_date").val(),
    			  agent_id:$("#agentId").find("option:selected").val(),
    			  company_by_report:$("#txt_search_departmentname").val(),
    			  right_company_name_en:$("#txt_search_companyEngName").val(),
    			  num:$("#num").val(),
    			  reference_num:$("#reference_num").val(),
    			  status:that.checkchar
				}
			});
		        		
            let companyName = $("#txt_search_departmentname").val();//公司名称
            let orderCName = $("#txt_search_companyEngName").val();//订单公司名称
            let deadDate = $("#txt_search_date").val();//到期日期
            let client = $("#txt_search_cus option:selected").val();//客户
            let proxy = $("#txt_search_agency option:selected").val();//代理
            let country = $("#txt_search_country option:selected").val();//国家

            console.log(companyName,orderCName,deadDate,client,proxy,country)

        })
        /***发起ajax请求 获取表格数据*/
        //清空统一写在common.js
        // $("#btn_reset").click(function(){
        //     $("#formSearch div input").val("");
        //     $("#client option:first").prop("selected","selected")
        //     $("#proxy option:first").prop("selected","selected")
        //     $("#country option:first").prop("selected","selected")
        //     $("#btn_query").trigger("click")
        // })
    },
    hideShowStyle(){
        /**展开收起样式 */
        $("#hideBtn").click((e)=>{
            if($('#hideBtn').text().trim() === '收起'){
                $("#btnCollapse").css({'height':'0',"overflow":'hidden'})
                $('#hideBtn').html('展开 <i class="fa fa-angle-down"></i>')
                $(".fixed-table-body").css({'height':'115%','transition':'all .1s'})
                $(".bootstrap-table .table:not(.table-condensed) > tbody > tr > td").css({"padding":"12px",'transition':'all .1s'})
              }else if($('#hideBtn').text().trim() === '展开'){
                $("#btnCollapse").css({'height':'3rem',"overflow":'visibility'})
                $('#hideBtn').html('收起 <i class="fa fa-angle-up"></i>')
                $(".fixed-table-body").css({'height':'100%','transition':'all .1s'})
                 $(".bootstrap-table .table:not(.table-condensed) > tbody > tr > td").css({"padding":"8px",'transition':'all .1s'})
              }
        })
    },
    initTable(){
    	let _this = this;
    	 this.checked=[];
		 this.checkchar="";
     	$("input[name='status']:checked").each(function(i){
           _this.checked[i] = $(this).val();
           _this.checkchar+=_this.checked[i]+","
     	});
        const $table = $('#table');
        $table.bootstrapTable({
            height: $(".table-content").height()*0.74,
            columns: [
            	{
                  title: '订单号',
                  field: 'num',
                  align: 'center',
                  valign: 'middle',
                    formatter:function(value,row,index){
                        rows=encodeURIComponent(JSON.stringify(row));//对json字符串编码
                        return `<a href="javascript:;" style="color:#1890ff" class="detail3" data-row=${rows}>${value}</a>  `;
                    }
                  // formatter:function(value,row,index){
                  //
                	// var url = '<a href="#" style="color:#1890ff" onclick="orderinfo(\'' + row.id + '\')">' + value + '</a>  ';
                	// return url;
              		// }
            	},
                {
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
                },
                {
                    title: '客户参考号',
                    field: 'reference_num',
                    align: 'center',
                    valign: 'middle',
                  },{
                  title: '代理ID',
                  field: 'agent_id',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: `处理状态 &nbsp;<i class="fa fa-filter"></i>`,
                  field: 'statuName',
                  align: 'center',
                  valign: 'middle',
                
                }, {
                  title: '订单公司名称',
                  field: 'right_company_name_en',//englishName
                  align: 'center',
                  valign: 'middle',
                  class:"wrap"
                },{
                  title: '公司中文名称',
                  field: 'company_by_report',//companyName
                  align: 'center',
                  valign: 'middle',
                  class:"wrap"
                }, {
                  title: '国家',
                  field: 'countryName',
                  align: 'center',
                  valign: 'middle',
                }, 
				{
                  title: '是否有财务信息',
                  field: 'is_hava_finance',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '最近财务信息年份',
                  field: 'last_fiscal_year',
                  align: 'center',
                  valign: 'middle',
                },
                {
                  title: '报告类型',
                  field: 'reportType',
                  align: 'center',
                  valign: 'middle',
                  class:"wrap"
                }, 
				{
                  title: '报告员',
                  field: 'reportName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '翻译员',
                  field: 'translateName',
                  align: 'center',
                  valign: 'middle',
                }, {
                  title: '分析员',
                  field: 'analyzeName',
                  align: 'center',
                  valign: 'middle',
                },{
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                        "click .order-cancel":(e,value,row,index)=>{
                          console.log(row);
                          $("#customId").text(row.custom_id);
                          $("#customName").text(row.customName);
                          $("#receiveDate").text(row.receiver_date);
                          $("#area").text(row.continentName);
                          $("#country1").text(row.countryName);
                          $("#reportType").text(row.reportType);
                          $("#orderType").text(row.orderType);
                          $("#reportLanguage").text(row.reportLanguage);
                          $("#orderCompany").text(row.englishName);
                          $("#useTime").text(row.useTime);
                          $("#companyName").text(row.companyName);
                          $("#reportSpeed").text(row.reportSpeed);
                          $("#orderid").val(row.id);
                          $("#orderNum1").text(row.num);
                          $("#revoke_reason").val(row.revoke_reason);
                        
                        },
                        "click .order-update":(e,value,row,index)=>{
                          $("#orderNum").text(row.num);
                          $("#customId1").text(row.custom_id);
                          $("#customName1").text(row.customName);
                          $("#receiveDate1").text(row.receiver_date);
                          $("#area1").text(row.continentName);
                          $("#country2").text(row.countryName);
                          $("#reportType1").text(row.reportType);
                          $("#orderType1").text(row.orderType);
                          $("#reportLanguage1").text(row.reportLanguage);
                          $("#orderCompany1").text(row.englishName);
                          $("#useTime1").text(row.useTime);
                          $("#companyName1").text(row.companyName);
                          $("#reportSpeed1").text(row.reportSpeed);
                          $("#orderid1").val(row.id);
                          $("#update_reason").val(row.update_reason);
                        },
                        "click .ask":(e,value,row,index)=>{
                        	let id = row.id;
                        	$.ajax({
                        		url:'/credit/front/orderProcess/askOrder',
                        		data:{
                        			status:'003',
                        			id
                        		},
                        		type:'post',
                        		success:(data)=>{
                        			console.log(data)
                        			if(data.statusCode === 1) {
                        				Public.message("success",data.message)
                        				$table.bootstrapTable("refresh")
                        			}else {
                        				Public.message("error",data.message)
                        			}
                        		}
                        	})
                        }
                    },
                    formatter: _this.operateFormatter
                }
            ],
             url : '/credit/front/home/list', // 请求后台的URL（*）
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
            sortable: true,                     //是否启用排序
            sortOrder: "desc",
            sortName:"receiver_date",
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
            	console.log(params);
            	_this.paramsObj = params
              return {//这里的params是table提供的  
                  pageNo: params.pageNumber,//页码
                  recordsperpage: params.pageSize,//每页多少条
                  sortName:params.sortName,
                  sortOrder:params.sortOrder,
                  custom_id:$("#txt_search_cus").find("option:selected").val(),
                  country:$("#txt_search_country").find("option:selected").val(),
    			  end_date:$("#txt_search_date").val(),
    			  agent_id:$("#txt_search_agency").find("option:selected").val(),
    			  company_by_report:$("#txt_search_departmentname").val(),
    			  right_company_name_en:$("#txt_search_companyEngName").val(),
    			  num:$("#num").val(),
    			  reference_num:$("#reference_num").val(),
    			  status:_this.checkchar
              };  
            },
            onLoadSuccess:(data)=>{
            	console.log(data)
            	let rows = data.rows;
            	rows.forEach((item,index)=>{
            		let dead_time = new Date(item.end_date).getTime();//截止日期
            		let now_time = new Date(new Date().getFullYear() + '-' + (new Date().getMonth()+1) + '-' + new Date().getDate()).getTime();
            		let diffValue = now_time - dead_time; //差值
            		if(diffValue > 0) {
            			//已过期
            			$("#table tr").eq(index+1).addClass("order-dead")
            			$(".fixed-table-body-columns .table tr").eq(index).addClass("order-dead")
            		}else if(diffValue === 0) {
            			//今天过期
            			$("#table tr").eq(index+1).addClass("order-ing")
            			$(".fixed-table-body-columns .table tr").eq(index).addClass("order-ing")
            		}
            		let isAsk = item.is_ask;
            		if(isAsk === '1') {
            			//已催问
            			$("#table tr").eq(index+1).addClass("order-ask")
            			$(".fixed-table-body-columns .table tr").eq(index).addClass("order-ask")
            		}
            	})
            }
        });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    operateFormatter(){
        /**操作按钮格式化 */
        return '<a href="javacript:;" class="ask" style="margin-right:.5rem">催问</a>' +
		        '<span style="margin-right:.5rem;color: #1890ff">|</span>' +
		        '<a href="javacript:;" class="order-cancel" style="margin-right:.5rem" data-toggle="modal" data-target="#exampleModalCenter1">订单撤销</a>' +
		        '<span style="margin-right:.5rem;color: #1890ff">|</span>' +
		        '<a href="javacript:;" class="order-update" data-toggle="modal" data-target="#exampleModalCenter2">内容更新</a>'
    }
}
Index.init();
function loadtable(){
	var checked=[];
	 var checkchar=""
             $("input[name='status']:checked").each(function(i){
                   checked[i] = $(this).val();
                   checkchar+=checked[i]+","
             });
             console.log(checkchar);
    		$.ajax({
    			type:"post",
    			url:"/credit/front/home/list",
    			data:{"attr.custom_id":$("#txt_search_cus").find("option:selected").val(),
    				"attr.country":$("#txt_search_country").find("option:selected").val(),
    				"attr.end_date":$("#txt_search_date").val(),
    				"attr.agent_id":$("#txt_search_agency").find("option:selected").val(),
    				"attr.company_by_report":$("#txt_search_departmentname").val(),
    				"attr.right_company_name_en":$("#txt_search_companyEngName").val(),
    				"attr.num":$("#num").val(),
       			    "attr.reference_num":$("#reference_num").val(),
    				"status":checkchar},
    			 dataType:"json",
    			 success:function(data){
    			 	 $("#table").bootstrapTable("load",data)
    			 }
    		});
};
function orderinfo(id){
			Public.goToOrderDetail(id)
		};


$('input').keyup(function (e) {//捕获文档对象的按键弹起事件
    if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
        $('#btn_query').trigger("click")
    }
});
$("table").on('click', '.detail3',function(){
    let rows=$(this).attr('data-row');
    let row=JSON.parse(decodeURIComponent(rows));//解码
    Public.goToOrderDetail(row.id,row)
})
