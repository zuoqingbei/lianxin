let creditAnalysis = {
    // 初始化
    pageNumber: 1,
    pageSize: 16,
    selectNum: 0,
    selectMoney: 0,
    selectAll: false,
    selectedData: new Map(),
    selectedData_thisPage: [],
    init() {
        this.initTable();
        this.initDateInput();
//        this.selectItem();
        this.searchEvent();
        this.reSet();
        this.butExport();
       
    },
    
    searchEvent(){
		let that=this;
        $("#btn_query").click(function(){
        	/**发起ajax请求  获取表格数据*/
           $("#table").bootstrapTable("refresh",{
				query:{
				  pageNo: that.paramsObj.pageNumber,//页码
                  recordsperpage:  that.paramsObj.pageSize,//每页多少条
                  sortName: that.paramsObj.sortName,
                  sortOrder: that.paramsObj.sortOrder,
    			  time:$("#time").val(),
    			  customerId:$("#txt_search_reporter").find("option:selected").val(),
    			  agentId:$("#txt_search").find("option:selected").val()
				}
			});
            let deadDate = $("#time").val();//到期日期
          
        })
    },
    reSet(){
		let that=this;
		 $("#btn_resett").click(function(){
			 document.getElementById("txt_search_reporter").options.selectedIndex = 0;  
			 document.getElementById("txt_search").options.selectedIndex = 0; 
			 $("#time").val("");
			 
		 })
    },
    butExport(){
		let that=this;
		 $("#btn_export").click(function(){
			var time=$("#time").val();
			 var customerId=$("#txt_search_reporter").find("option:selected").val();
			 var agentId=$("#txt_search").find("option:selected").val();
			// $.post("/credit/settle/SettleExport", { time: time, customerId:customerId,agentId:agentId});
			 window.document.location.href="/credit/settle/SettleExport?time="+time+"&customerId="+customerId+"&agentId="+agentId;	    
					   
			 
		 })
    },
    initTable() {
        let oT = this;
        this.pcount = 0;//报告价格总
        this.acount = 0;//代理价格总
        this.itemNum = 0;//选择的数量
        const $table = $("#table");
        $table.bootstrapTable({
            height: $(".table-content").height() * 0.82,
            clickToSelect:true,
            responseHandler:responseHandler,
            columns: [
                {
                    checkbox: true,
                    visible: true,                  //是否显示复选框
                    width: '18rem',
                    field:'state'
                }, {
                    title: '订单号',
                    field: 'num',
                }, {
                    field: 'pprice',
                    title: '报告价格',
                   
                },{
                    field: 'pcurrency',
                    title: '',
                   
                },{
                    field: 'aprice',
                    title: '代理价格',
                    
                }, {
                    field: 'acurrency',
                    title: '',       
                }, {
                    title: '订单日期',
                    field: 'receiver_date',
                    sortable: true,
                }, {
                    title: '到期日期',
                    field: 'end_date',
                    sortable: true,
                }, {
                    title: '客户代码',
                    field: 'custom_id',
                }, {
                    title: '订单公司名称',
                    field: 'ordername',
                }, {
                    title: '公司中文名称',
                    field: 'cname',
                },{
                    title: '报告价格汇率',
                    field: 'reprate',
                    visible: false,
                },{
                    title: '代理价格汇率',
                    field: 'agentrate',
                    visible: false,
                },{
                    title: '报告价格人民币',
                    field: 'rmb2',
                    visible: false,
                },{
                    title: '代理价格人民币',
                    field: 'rmb',
                    visible: false,
                }

            ]/*.map(function (item,index) {
                if(index === 0){
                    item.align = 'right'
                }else{
                    item.align = 'center';
                }

                item.valign = 'middle';
                return item;
            })*/,
             url : '/credit/settle/list', // 请求后台的URL（*）
             method : 'get', // 请求方式（*）post/get
            pagination: true, //分页
            sidePagination: 'server',
            pageNumber: 1,
            pageSize: 5,
            pageList: [20, 30],
            smartDisplay: false,
            iconsPrefix: 'fa',
            locales: 'zh-CN',
            fixedColumns: false,
            fixedNumber: 1,
            queryParamsType: '',
            sortable: true,                     //是否启用排序
            sortOrder: "desc",
            sortName:"receiver_date",
            contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是分页用的
            	console.log(params);
            	oT.paramsObj = params;
            	oT.pageNumber = params.pageNumber;
              return {//这里的params是table提供的  
                  pageNo: params.pageNumber,//页码
                  recordsperpage: params.pageSize,//每页多少条
                  sortName:params.sortName,
                  sortOrder:params.sortOrder,
    			  time:$("#time").val(),
    			  customerId:$("#txt_search_reporter").find("option:selected").val(),
    			  agentId:$("#txt_search").find("option:selected").val(),
              };  
            },
            onLoadSuccess(){
            	//　$('#table').bootstrapTable('mergeCells', {index:0 , field: 'pprice', colspan: 2});
                // 加入footer
                $(".fixed-table-footer").html(`<footer class="total d-flex justify-content-between">
                    <div>已选择<strong id="selectedTotalNum">0</strong>项</div>
                    <div style="padding-left: 40rem">报告价格合计：<strong id="commission">0.00</strong><span class="unit">CNY</span></div>
                    <div>代理价格合计：<strong id="agent_commission">0.00</strong><span class="unit">CNY</span></div>
                </footer>`).show();
                // console.log(oT.this);
                $("[name=btSelectAll]").prop('checked', false);
                // if(oT.selectedData){
                oT.selectedData_thisPage = oT.selectedData.get(oT.pageNumber)?oT.selectedData.get(oT.pageNumber):[];

                if (oT.selectedData_thisPage) {
                    if (oT.selectedData_thisPage.length === $("#table>tbody>tr").length) {
                        $table.parents('.bootstrap-table').find('.fixed-table-header-columns [name=btSelectAll]').prop('checked', true)
                    }
                    oT.selectedData_thisPage.forEach(function (item) {
                        // $table.find('.fixed-table-body-columns [name=btSelectItem]').eq(item.index).prop('checked', true)
                        $table.parents('.bootstrap-table').find('.fixed-table-body-columns [name=btSelectItem]').eq(item.index).prop('checked', true)
                    });
                    $("#commission").text(oT.selectMoney);
                    $("#selectedTotalNum").text(oT.selectNum);
                }
               // console.log('已选择的所有内容', oT.selectedData, '当前页内容',oT.selectedData)
                // }
            },
            onCheck:(rows)=>{
            	if(rows.reprate===null){
            		Public.message("info","请补全"+rows.pcname+"-人民币的汇率，方可结算")
            		return 
            	}
            	if(rows.agentrate===null){
            		Public.message("info","请补全 "+rows.acname+"-人民币的汇率，方可结算")
            		return 
            	}
            	oT.pcount += rows.rmb2;
            	oT.acount += rows.rmb;
            	oT.itemNum += 1;
            	$("#commission").html(oT.pcount.toFixed(2));
            	$("#agent_commission").html(oT.acount.toFixed(2).replace("-",""))
            	$("#selectedTotalNum").text(oT.itemNum)
            	console.log(rows.rmb,oT.acount,rows.rmb2,oT.pcount);
            },
            onUncheck:(rows)=>{
            	if(rows.reprate!=null&&rows.agentrate!=null){
            	oT.pcount -= rows.rmb2;
            	oT.acount -= rows.rmb;
            	oT.itemNum -= 1;
            	$("#commission").html(oT.pcount.toFixed(2));
            	$("#agent_commission").html(oT.acount.toFixed(2).replace("-",""))
            	$("#selectedTotalNum").text(oT.itemNum)
            	console.log(rows.rmb,oT.acount,rows.rmb2,oT.pcount);
            	}
            },onCheckAll:(rows)=>{
            	 for(var i=0;i<rows.length;i++){
            		 if(rows[i].reprate===null){
                 		Public.message("info","请补全"+rows[i].pcname+"-人民币的汇率，方可结算")
                 		return
                 	}
                 	if(rows[i].agentrate===null){
                 		Public.message("info","请补全 "+rows[i].acname+"-人民币的汇率，方可结算")
                 		return  
                 	}
            		  console.log(rows[i])
            		  oT.pcount += rows[i].rmb2;
                  	oT.acount += rows[i].rmb;
                  	oT.itemNum += 1;
                  	$("#commission").html(oT.pcount.toFixed(2));
                  	$("#agent_commission").html(oT.acount.toFixed(2))
                  	$("#selectedTotalNum").text(oT.itemNum)
                  	console.log(rows[i].rmb,oT.acount,rows[i].rmb2,oT.pcount);
            		}
            	
            },onUncheckAll:(rows)=>{
	           	 for(var i=0;i<rows.length;i++){
	           		 if(rows[i].reprate!=null&&rows[i].agentrate!=null){	 
	       		  console.log(rows[i])
	       		oT.pcount -= rows[i].rmb2;
	          	oT.acount -= rows[i].rmb;
	          	oT.itemNum -= 1;
	          	$("#commission").html(oT.pcount.toFixed(2).replace("-",""));
	          	$("#agent_commission").html(oT.acount.toFixed(2).replace("-",""))
	          	$("#selectedTotalNum").text(oT.itemNum)
	          	console.log(rows[i].rmb,oT.acount,rows[i].rmb2,oT.pcount);
	           		 }
       		}
       },
  		onPageChange:()=>{
  			setTimeout(()=>{
  				$("#commission").html(oT.pcount.toFixed(2));
  				$("#agent_commission").html(oT.acount.toFixed(2))
  				$("#selectedTotalNum").text(oT.itemNum)
  				
  			},200)
   		},
   		
            	
            	
        });
      //选中事件操作数组
    	var union = function(array,ids){
    		$.each(ids, function (i, id) {
    			if($.inArray(id,array)==-1){
    				array[array.length] = id;
    			}
    	    	 });
    	    	return array;
    	};
    	//取消选中事件操作数组
        var difference = function(array,ids){
    	    	$.each(ids, function (i, id) {
    	    		 var index = $.inArray(id,array);
    	    		 if(index!=-1){
    	    			 array.splice(index, 1);
    	    		 }
    	    	 });
    	    	return array;
    	};
        var selectionIds = [];	//保存选中ids
        var _ = {"union":union,"difference":difference}
        $table.on('check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table', function (e, rows) {
	        var ids = $.map(!$.isArray(rows) ? [rows] : rows, function (row) {
	                 return row.id;
	        });
             func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';
             selectionIds = _[func](selectionIds, ids);	
        });
        
        function responseHandler(res) {
  	      $.each(res.rows, function (i, row) {
  	          row.state = $.inArray(row.id, selectionIds) != -1;	//判断当前行的数据id是否存在与选中的数组，存在则将多选框状态变为true
  	      });
  	      return res;
        }

        // sometimes footer render error.
        setTimeout(() => {
            $table.bootstrapTable('resetView');
        }, 200);
    },
    initDateInput() {
        laydate.render({
            elem: '#time'
            , range:'~'
        })
    }
};

creditAnalysis.init();










