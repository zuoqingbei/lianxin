let Verify = {
    init(){
        this.pageNumber = "";
        this.pageSize = "";
        this.sortName = "";
        this.sortOrder = "";
        /**初始化函数 */
        this.initTable();
//        this.operateFormatter();
        this.modalSubmit();
        this.popperFilter();
    },

   
    /**初始化表格 */
    initTable(){
        /**初始化表格 */
        const $table = $('#table');
        let _this = this

      
        $table.bootstrapTable({
            height: $(".table-content").height()*1.02,
            columns: [
                      {
                          title: '订单号',
                          field: 'num',
                          align: 'center',
                          valign: 'middle',
                          formatter:function(value,row,index){
                          	return `<a href="javascript:;" style="color:#1890ff" onclick='Public.goToOrderDetail(${row.id},${JSON.stringify(row)})'>${value}</a>`;
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
                        }, {
                          title: '公司中文名称',
                          field: 'companyZHNames',
                          align: 'center',
                          valign: 'middle',
                        }, {
                        title: '公司英文名称',
                        field: 'info_en_name',
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
                            "click .write":(e,value,row,index)=>{
                              console.log(row);
                              //Public.goToBasicInfoWrite(row);
                              Public.goToReportConfig(row)
                            },  "click .recordName":(e,value,row,index)=>{
                            	$("#orderType").html(row.orderType);
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
                                $("#companyZHNames").val(row.companyZHNames);
                                $("#reporter_select").html(row.seleteStr);
                                $("#orderId").val(row.id);
                                $("#num").html(row.num);
                                $("#remarks").val("");
                                $("#companyId").val(row.company_id);
                                pageNumber = row.pageNumber;
                                pageSize = row.pageSize;
                            	  sortName = row.sortName;
                            	  sortOrder = row.sortOrder;
                            	  reportt = row.report_userKey;
                              },
                              "click .translate":(e,value,row,index)=>{
                            	  Public.goToReportTranslate(row)
                              },
                              "click .analyze":(e,value,row,index)=>{
                            	  Public.goToReportAnalyze(row)
                              },
                              "click .chadang":(e,value,row,index)=>{
                                  $("#custom_id23").html(row.custom_id);
                                  $("#customId23").html(row.customId);
                                  $("#receiver_date23").html(row.receiver_date);
                                  $("#country23").html(row.country);
                                  $('#continent23').html(row.continent)
                                  $("#reportType23").html(row.reportType);
                                  $("#orderType23").html(row.orderType);
                                  $("#reportLanguage23").html(row.reportLanguage);
                                  $("#companyNames23").html(row.companyNames);
                                  $("#speed23").html(row.speed);
                                  $("#user_time23").html(row.user_time);
                                  $("#companyZHNames23").html(row.companyZHNames);
                                  $("#reporter_select23").html(row.seleteStr);
                                  // $("#confirm_reason").html(row.confirm_reason);
                                  $("#orderId").val(row.id);
                                  $("#num23").html(row.num);
                                  pageNumber = row.pageNumber;
                                  pageSize = row.pageSize;
                                  sortName = row.sortName;
                                  sortOrder = row.sortOrder;
                              },
                              "click .heshi":(e,value,row,index)=>{
                                  $("#custom_id2").html(row.custom_id);
                                  $("#customId2").html(row.customId);
                                  $("#receiver_date2").html(row.receiver_date);
                                  $("#country2").html(row.country);
                                  $('#continent2').html(row.continent);
                                  $("#reportType2").html(row.reportType);
                                  $("#orderType2").html(row.orderType)
                                  $("#reportLanguage2").html(row.reportLanguage);
                                  $("#companyNames2").html(row.companyNames);
                                  $("#speed2").html(row.speed);
                                  $("#user_time2").html(row.user_time);
                                  $("#companyZHNames2").html(row.companyZHNames);
                                  $("#reporter_select2").html(row.seleteStr);
                                  // $("#confirm_reason").html(row.confirm_reason);
                                  $("#orderId2").val(row.id);
                                  $("#num2").html(row.num);
                                  pageNumber = row.pageNumber;
                                  pageSize = row.pageSize;
                                  sortName = row.sortName;
                                  sortOrder = row.sortOrder;
                                  $("#cfr").val("");
                                  $("#cfr").val(row.confirm_reason);

                              }
                          },
                          // formatter: _this.operateFormatter(arr)
                            formatter:function () {
                                let arr=JSON.parse(sessionStorage.getItem('roleIds'))
                                if(arr!=null&&arr.indexOf(1)>-1||arr!=null&&arr.indexOf(3)>-1||arr==''||arr==null){
                                    return `<a href="javascript:;" class="recordName"  data-toggle="modal" data-target="#recordingName">录入名称</a>
                                            <span style="margin-left:.5rem;color: #1890ff">|</span>
                                            <a href="javascript:;" class="write" style="margin-left:.5rem">填报</a>
                                            <span style="margin-left:.5rem;color: #1890ff">|</span>
                                            <a href="javascript:;" class="analyze" style="margin-left:.5rem">分析</a>
                                            <span style="margin-left:.5rem;color: #1890ff">|</span>
                                            <a href="javascript:;" class="translate" style="margin-left:.5rem">翻译</a>
                                     
                                            <div style="margin-top: 0.5rem;text-align: left">
                                                <a href="javascript:;" class="chadang" style="margin-top: .5rem" data-toggle="modal" data-target="#exampleModalCenter_allocation">发起查档</a>
                                                <span style="margin-left:.5rem;color: #1890ff">|</span>
                                                <a href="javascript:;" class="heshi" style="margin-left:.5rem;margin-top: .5rem" data-toggle="modal" data-target="#exampleModalCenter">发起核实</a>
                                            </div>`
                                }else if(arr!=null&&arr.indexOf(2)>-1){
                                    return `<a href="javascript:;" class="recordName"  data-toggle="modal" data-target="#recordingName">录入名称</a>
                                            <span style="margin-left:.5rem;color: #1890ff">|</span>
                                            <a href="javascript:;" class="write" style="margin-left:.5rem">填报</a>
                                            <div style="margin-top: 0.5rem;text-align: left">
                                                <a href="javascript:;" class="chadang" style="margin-top: .5rem" data-toggle="modal" data-target="#exampleModalCenter_allocation">发起查档</a>
                                                <span style="margin-left:.5rem;color: #1890ff">|</span>
                                                <a href="javascript:;" class="heshi" style="margin-left:.5rem;margin-top: .5rem" data-toggle="modal" data-target="#exampleModalCenter">发起核实</a>
                                            </div>
                                            `
                                }else if(arr!=null&&arr.indexOf(5)>-1){
                                    return`<a href="javascript:;" class="analyze" >分析</a>`
                                }else if(arr!=null&&arr.indexOf(6)>-1){
                                    return`<a href="javascript:;" class="translate" >翻译</a>`
                                }

                            }
                        }
                      
                    ],
                    url : '/credit/front/orderProcess/listJson', // 请求后台的URL（*）
                    method : 'post', // 请求方式（*）post/get
                    pagination: true, //分页
                    sidePagination: 'server',
                    pageNumber:1,
                    pageSize:10,
                    pageList: [10,20,30,50],
                    smartDisplay:false,
                    iconsPrefix:'fa',
                    locales:'zh-CN',
                    fixedColumns: true,
                    fixedNumber: 1,
                    queryParamsType:'',
                    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
                    queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
                      window.aaa = params.pageSize;
                      return {//这里的params是table提供的  
                    	  pageNumber: params.pageNumber,//从数据库第几条记录开始  
                    	  pageSize: params.pageSize,//找多少条  
                    	  sortName: params.sortName, 
                    	  sortOrder: params.sortOrder,
                    	  searchType: "-6"
                      };  
                    }, 
                    onLoadSuccess:function(data){
                    	// console.log(data)
                    	let rows = data.rows;
                    	rows.forEach((item,index)=>{
                    		//录入名称
                    		if(!item.country || item.country.trim() !== '中国大陆' || !(item.status === '293'|| item.status === '291'|| item.status === '295' || item.status === '296' || item.status === '694' || item.status === '816')){
                    			$(Array.from($(".recordName"))[index]).addClass("disable");
                    		}else {
                    			// console.log($(Array.from($(".recordName"))[index]))
                    			$(Array.from($(".recordName"))[index]).removeClass("disable")
                    		}
                    		//分析
                    		if( item.report_type !== '10' && item.report_type !== '11') {
                    			$($(".analyze").get(index)).css ({"color":"#ccc","cursor":"default"});
                    			$($(".analyze").get(index)).unbind()
                    		}else {
                    			$($(".analyze").get(index)).css({"color":"#007bff","cursor":"pointer"});
                    		}
                    		//翻译
                    		if(item.report_language === '215' || item.report_language === '213') {
                    			//报告语言如果是中文简体或者英文
                    			$($(".translate").get(index)).css({"color":"#ccc","cursor":"default"})
                    			$($(".translate").get(index)).unbind()
                    		}else {
                    			$($(".translate").get(index)).css({"color":"#007bff","cursor":"pointer"});
                    		}
                    		
                    		//检测订单流程  当status为301的时候只能进行分析；为306的时候只能进行翻译;694的时候只能进行填报
                    		//595系统查询中
                    		//293信息录入
                    		//295代理中
                    		//694 系统查询完毕
                    		if(item.status === '301'){
                    			$($(".recordName").get(index)).addClass("disable")
                    			$($(".translate").get(index)).addClass("disable")
                    			$($(".write").get(index)).addClass("disable")
                    		}else if(item.status === '306') {
                    			$($(".recordName").get(index)).addClass("disable")
                    			$($(".analyze").get(index)).addClass("disable")
                    			$($(".write").get(index)).addClass("disable")
                    		}else if(item.status === '293'||item.status === '295'||item.status === '694') {
//                    			$($(".recordName").get(index)).addClass("disable")
                    			$($(".analyze").get(index)).addClass("disable")
                    			$($(".translate").get(index)).addClass("disable")
                    		}else if (item.status === '595') {
                    			$($(".write").get(index)).addClass("disable")
                    			$($(".analyze").get(index)).addClass("disable")
                    			$($(".translate").get(index)).addClass("disable")
							}
                    	});
                        let $fixedTableBodyColumns = $('.fixed-table-body-columns');
                        setTimeout(function () {
                            $('#table tbody tr').each(function (index,item) {
                                // $(item).height($fixedTableBodyColumns.find('tbody tr').eq(index).height()+1);
                                $fixedTableBodyColumns.find('tr').eq(index).css('height',$(item).height());
                                // console.log('表头行高:'+$fixedTableBodyColumns.find('tr').eq(index).height(),
                                //     '正常行高:'+$(item).height())
                            });
                        },500)
                    }
                  });
        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
        
        //点击核实提交
        $("#modal_submit").click(function(){
            $("#status").val("500");
            let remarks = $("#remarks").val();
            let id = $("#orderId2").val();
            //console.log(reporter,remarks);
            $.ajax({
                type:"post",
                url:BASE_PATH+"credit/front/orderProcess/statusSave",
                data:"model.id="+id+"&pageNumber="+pageNumber+"&pageSize="
                +pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder
                +"&model.confirm_reason="+$("#cfr").val()
                +"&statusCode="+$("#status").val(),
                dataType:"json",
                success:function(data){
                    //提交成功关闭模态窗
                    $(".modal-header .close").trigger("click");
                    if(data.statusCode===1){
                        console.log("此处进入success状态2222222222");
                        Public.message("success",data.message);
                    }else{
                        console.log("此处进入error状态");
                        Public.message("error",data.message);
                    }
                    //回显
                    console.log("提交成功,开始回显:"+data.message);
                    $.ajax({
                        type:"post",
                        url:BASE_PATH+"credit/front/orderProcess/listJson",
                        data:"pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-6",
                        dataType:"json",
                        success:function(obj){
                            console.log("回显的数据:"+obj);
                            $("#table").bootstrapTable("load",obj)
                        }
                    })

                    console.log("回显完毕");
                }
            })
        })
        //点击发起查档提交
        $("#modal_submit2").click(function(){
            $("#status2").val("295");

            let remarks = $("#remarks").val();
            let id = $("#orderId").val();
            console.log('dasdsadasdas',$("#agent_category").val());
            $.ajax({
                type:"post",
                url:BASE_PATH+"credit/front/orderProcess/statusSave",
                data:"model.id="+id+"&pageNumber="+pageNumber+"&pageSize="
                +pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder
                +"&model.agent_category="+$("#agent_category").val()
                +"&statusCode="+$("#status2").val(),
                dataType:"json",
                success:function(data){
                    //提交成功关闭模态窗
                    $(".modal-header .close").trigger("click");
                    if(data.statusCode===1){
                        console.log("此处进入success状态2222222222");
                        Public.message("success",data.message);
                    }else{
                        console.log("此处进入error状态");
                        Public.message("error",data.message);
                    }
                    //回显
                    console.log("提交成功,开始回显:"+data.message);
                    $.ajax({
                        type:"post",
                        url:BASE_PATH+"credit/front/orderProcess/listJson",
                        data:"pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortName="+sortName+"&sortOrder="+sortOrder+"&searchType=-6",
                        dataType:"json",
                        success:function(obj){
                            console.log("回显的数据:"+obj);
                            $("#table").bootstrapTable("load",obj)
                        }
                    })

                    console.log("回显完毕");
                }
            })
        })
        /**
         * 点击录入名称提交
         */
        $("#modal_submit_allocation").click(()=>{
        	console.log("点击录入名称提交")
        	let val = $("#companyZHNames").val();
        	let valEn = $("#companyEnNames").val()
        	if(!val){
        		Public.message("error","公司中文名称不能为空")
        	}else {
        		/*调用接口*/
        		$("body").mLoading("show");
        		$.ajax({
           			type:"post",
               		url:BASE_PATH+"credit/front/orderProcess/statusSave",
               		data:"statusCode=595&isPa=yes&num="+$("#num").html()+"&model.id="+$("#orderId").val()+"&model.company_by_report="+val+"&companyId="+$("#companyId").val(),
               		dataType:"json",
               		success:function(obj){
               			$("body").mLoading("hide");
               			if(obj.statusCode===1){
                         	Public.message("success",obj.message);
                         	
                         }else{
                         	Public.message("error",obj.message);
                         }
               		  $(".modal-header .close").trigger("click");
               			$("#table").bootstrapTable("refresh");
               			 	
           			 }
       			})
        		
        	}
        })
        
    },
    /**操作按钮格式化 */
    // operateFormatter(){
    //     return `<a href="javascript:;" class="recordName"  data-toggle="modal" data-target="#recordingName">录入名称</a>
    //     <span style="margin-left:.5rem;color: #1890ff">|</span>
    //     <a href="javascript:;" class="write" style="margin-left:.5rem">填报</a>
    //     <span style="margin-left:.5rem;color: #1890ff">|</span>
    //     <a href="javascript:;" class="analyze" style="margin-left:.5rem">分析</a>
    //     <span style="margin-left:.5rem;color: #1890ff">|</span>
    //     <a href="javascript:;" class="translate" style="margin-left:.5rem">翻译</a>`
    // },
    modalSubmit(){
        /**模态框提交事件 */
        $("#modal_submit").click(function(){
            // 提交成功关闭模态窗
            $(".modal-header .close").trigger("click");
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
};

Verify.init();
$('body').on('blur','#registered_capital_1',function () {
    let vals=$(this).val();
    $('#registered_capital_7').val(vals).trigger('blur');
    $('#registered_capital_6').val(vals).trigger('blur');

});
$('body').on('blur','#registered_capital_6,#registered_capital_7',function () {
    let vals=$(this).val();
    $('#registered_capital_1').val(vals).trigger('blur');

});
$('body').on('change','#currency_1',function () {
    let nows=$(this).val()
    layui.use('form', function(){
        var form = layui.form;
        $("#currency_6").val(nows)
        $("#currency_7").val(nows)
        form.render('select');
    });
});
$('body').on('change','#capital_type_1',function () {
    let nows=$(this).val()
    layui.use('form', function(){
        var form = layui.form;
        $("#capital_type_6").val(nows)
        $("#capital_type_7").val(nows)
        form.render('select');
    });
})
$('body').on('change','#capital_type_6，#capital_type_7',function () {
    let nows=$(this).val()
    layui.use('form', function(){
        var form = layui.form;
        $("#capital_type_1").val(nows)
        form.render('select');
    });
});
$('body').on('change','#currency_6,#currency_7',function () {

    let nows=$(this).val()
    layui.use('form', function(){
        var form = layui.form;
        $("#currency_1").val(nows)
        form.render('select');
    });
});