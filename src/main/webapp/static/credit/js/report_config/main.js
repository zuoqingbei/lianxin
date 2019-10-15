/**
 * 模块
 * 0 表单/1 表格/2 附件 / 5 固定底部的按钮/6 信用等级 /
 * 7 单独小模块的多行输入框 / 8 radio类型 / 9 浮动类型 /10 财务模块 /11 对于填报页面是表格
 */
let ReportConfig = {
	cwConfigAlterSource:'',
	dsConfigAlterSource:'',
    init(){
    	this.rows = JSON.parse(localStorage.getItem("row"));
        this.initContent();
        // this.transition()
    },
    transitionStyle(obj){
        let newObj = {}
        for (var key in obj) {
            let key1 = key.replace(/([A-Z])/g,"-$1").toLowerCase();
            newObj[key1] = obj[key]    
        }
       return JSON.stringify(newObj).replace(/\"/g,"").replace("{","").replace("}","").replace(/,/g,";")
    },
    initTable(){
    	/**
    	 * 表格初始化
    	 */
        let _this = this
        
        this.idArr.forEach((item,index)=>{
        	const $table = $("#table"+item);
        	let contents = this.contentsArr[index]
        	let titles = this.title
        	let urlTemp = titles[index].get_source;
        	let conf_id = titles[index].id;
        	if(!urlTemp){return}
        	let url = BASE_PATH  + 'credit/front/ReportGetData/'+ urlTemp.split("*")[0] + `&conf_id=${conf_id}`
        	if(urlTemp.split("*")[1]){
        		let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
        		tempParam.forEach((item,index)=>{
        			url += `&${item}=${this.rows[item]}`
        		})
        	}
			 
			 let selectInfo = []
        	selectInfo.push(_this.selectInfoObj)
        	//合计
    		if(titles[index]["get_source"].includes("credit_company_shareholder")) {
    			$table.bootstrapTable({
            		height:300,
            		columns: columns(index,item),
            		showFooter:true,
        			url:url, // 请求后台的URL（*）
    			    method : 'post', // 请求方式（*）post/get
    			    queryParams:function(param){
    			    	param.selectInfo = JSON.stringify(selectInfo)
    			    	return param
    			    },
    			    sidePagination: 'server',
    			    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
        			pagination: false, //分页
        			smartDisplay:false,
        			locales:'zh-CN',
        			onLoadSuccess:(data)=>{
        				//console.log(data)
        				if(data.rows === null){
//        					alert("出错了")
        					return
        				}
        				let rows = data.rows
        				rows.forEach((item,index)=>{
        					//增加一列序号
        					item["order_num"] = index+1
        					if(item.brand_url) {
        						let url = item.brand_url.includes("http")?item.brand_url:`http://${item["brand_url"]}`
        						item["brand_url"] = `<a href="${url}" target="_blank"><img src="${url}" style="height:40px;width:40px"></a>`
        					}
        					//console.log(item)
        				})
        				$table.bootstrapTable("load",rows)
        				
        				/*$(".monyCol").each((index,item)=>{
        					if(!$(item).attr("data-field") && $(item).text() !== ''){
        						//不是表头
        						$(item).text(Number($(item).text().replace(/,/g,"")).toLocaleString('en-US'))
        					}
        				})*/
        				setTimeout(() => {
        					let height = $table.parents(".fixed-table-body").siblings(".fixed-table-header").height();
    	    				if(rows.length < 1) {
    	    					$table.parents(".fixed-table-container").css("height",(40+height)+"px!important")
    	    				}else if(rows.length < 4) {
    	    					$table.parents(".fixed-table-container").css("height",(140+height)+"px")
    	    				}
        				 }, 200);
        			}
            	});
    		}else {
    			$table.bootstrapTable({
            		height:300,
            		columns: columns(index,item),
        			url:url, // 请求后台的URL（*）
    			    method : 'post', // 请求方式（*）post/get
    			    queryParams:function(param){
    			    	param.selectInfo = JSON.stringify(selectInfo)
    			    	return param
    			    },
    			    sidePagination: 'server',
    			    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
        			pagination: false, //分页
        			smartDisplay:false,
        			locales:'zh-CN',
        			onLoadSuccess:(data)=>{
//        				console.log(data)
        				if(data.rows === null){
//        					alert("出错了")
        					return
        				}
        				let rows = data.rows
        				rows.forEach((item,index)=>{
        					//增加一列序号
        					item["order_num"] = index+1
        					if(item.brand_url) {
        						let url = item.brand_url.includes("http")?item.brand_url:`http://${item["brand_url"]}`
        						item["brand_url"] = `<a href="${url}" target="_blank"><img src="${url}" style="height:40px;width:40px"></a>`
        					}
//        					console.log(item)
        				})
        				$table.bootstrapTable("load",rows)
        				
        				$(".monyCol").each((index,item)=>{
        					if(!$(item).attr("data-field")){
        						//不是表头
        						$(item).text(Number($(item).text().replace(/,/g,"")).toLocaleString('en-US'))
        					}
        					
        					if($(item).text() === 'NaN') {
        						$(item).text('')
        					}
        				})
        				setTimeout(() => {
        					let height = $table.parents(".fixed-table-body").siblings(".fixed-table-header").height();
    	    				if(rows.length < 1) {
    	    					$table.parents(".fixed-table-container").css("height",(40+height)+"px!important")
    	    				}else if(rows.length < 4) {
    	    					$table.parents(".fixed-table-container").css("height",(140+height)+"px")
    	    				}
        				 }, 200);
        				
        			}
            	});
    		}
        	
        	
        	function columns(tempI,tempId){
        		
        		let arr = []
        		//增加一列序号
        		contents.unshift({temp_name: "序号",column_name:"order_num"})
        		contents.forEach((ele,index)=>{
        			if(ele.temp_name !== '操作'){
        				if(ele.field_type === 'money') {
        					arr.push({
            					title:ele.temp_name,
            					field: ele.column_name,
            					class:'monyCol',
            					footerFormatter:(a)=>{
                        			if(a){
                        				let arr = []
                        				let total = 0;
                        				a.forEach((item,index)=>{
                        					if(item[ele.column_name]){
                        						total += Number(item[ele.column_name].toString().replace(/,/g,''))
                        					}
                        				})
                        				
                        				return total
                        			}
                        		},
//            					width:(1/contents.length)*100+'%'
            				})
        				}else {
        					arr.push({
        						title:ele.temp_name,
        						field: ele.column_name,
        						footerFormatter:(a)=>{
                        			if(a){
                        				let arr = []
                        				let total = 0;
                        				a.forEach((item,index)=>{
                        					// console.log(ele.column_name)
                        					if(ele.column_name === 'order_num'){
                        						total = '合计'
                        					}else if(ele.column_name !== 'currency') {
                        						if(item[ele.column_name]){
                        							total += Number(item[ele.column_name].toString().replace(/,/g,''))
                        						}
                        					}else if(ele.column_name === 'currency'){//出资币种不加合计
                        						total = 'NaN';
											}
                        					
                        				})
                        				if(typeof total === 'number'){
                        					total = total.toFixed(2)
                        					 if (total === '99.99' || total === '100.01') {
                        						 total = '100.00'
		                                    }
                        				}
                        				if(total === 'NaN'){
                        					return
                        				}
                        				return total
                        			}
                        		},
//        						width:(1/contents.length)*100+'%'
        					})
        				}
        				
        			}else {
        				arr.push({
        					title:ele.temp_name,
        					field: 'operate',
//        					width:1/contents.length,
        					events: {
            					"click .edit":(e,value,row,index)=>{
            						_this.isAdd = false
            						_this.rowId = row.id
            						//回显
            						let formArr = Array.from($("#modal"+tempId).find(".form-inline"))
            						formArr.forEach((item,index)=>{
            							let id = $(item).children("label").next().attr("id");
            							let anotherIdArr = id.split("_")
            							anotherIdArr.pop();
            							let anotherId = anotherIdArr.join('_')
            							if($("#"+id).hasClass('select2')) {
            								let str = row[anotherId];
            								let arr = str.split(",")
            								arr.forEach((item,index)=>{
            									$(`#${id} option`).each((i,it)=>{
            										 if($(it).text() === item) {
            											 let val = $(it).attr("value")
            											 arr[index] = val
            										 }
            									})
            								})
            								console.log($("#"+id),arr)
            								$("#"+id).val(arr).change()
            								return 
            							}
            							if($("#"+id).is('select')) {
            								//如果是select
            								console.log($("#"+id),row[anotherId])
            								if(row[anotherId] === 'null'){
            									$(".modal-body #"+id).find("option:first").attr("selected",true);
            								}else {
            									$(".modal-body #"+id).find("option[m-detail-name='"+row[anotherId]+"']").attr("selected",true);
            								}
            							}else {
            								if($("#"+id).hasClass("money-checked")){
            									$("#"+id).val(Number(row[anotherId].replace(/,/g,"")).toLocaleString('en-US'))
            								}else {
            									
            									$("#"+id).val(row[anotherId])
            								}
            							}
            						})
            					},
            					"click .delete":(e,value,row,index)=>{
            						$("#popEnter").on('click', function(){
            							//确定删除
            							let urlTemp = _this.title[tempI]["remove_source"];
            							let url = BASE_PATH + 'credit/front/ReportGetData/' + urlTemp;
            							$.ajax({
            								url,
            								type:'post',
            								data:{
            									id:row.id
            								},
            								success:(data)=>{
            									if(data.statusCode === 1) {
            										Public.message('success',data.message)
            										//刷新数据
            										_this.refreshTable($("#table"+tempId));
            									}else {
            										Public.message('error',data.message)
            									}
            								}
            							})
            						})
            					}
            				},
            				formatter: function(){return _this.formatBtnArr[tempI]}
        				})
        			}
        		})
        		
        		return arr
        	}
        })
    },
    initModal(){
    	/**
    	 * 初始化模态窗
    	 */
    	let _this = this
    	let modalHtml = ''
    	let ids = this.idArr
    	let contents = this.contentsArr;
    	let titles = this.title
    	//格式化按钮数组
    	this.formatBtnArr = []
    	ids.forEach((item,index)=>{
    		let modalBody = ''
			let myIndex = index;
    		contents[index].forEach((ele,index)=>{
    			if(ele.temp_name === '操作') {
    				return;
    			}
    			
    			if(!ele.field_type || ele.field_type === 'text') {
					if(ele.place_hold==null||ele.place_hold=='null'){
						modalBody += ` <div class="form-inline justify-content-center my-3">
									<label for="" class="control-label" >${ele.temp_name}：</label>
									<input type="text" class="form-control"  id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
	    						</div>`
					}else{
						modalBody += ` <div class="form-inline justify-content-center my-3">
									<label for="" class="control-label" >${ele.temp_name}：</label>
									<input type="text" class="form-control" placeholder ="${ele.place_hold}" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
	    						</div>`
					}
    				
    			}
    			switch(ele.field_type) {
    				case 'date':
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-date">
    										<img src="${BASE_PATH }static/credit/imgs/total_manage/calen.png" alt="" style="position: absolute;right: 13.5rem;width: 1rem;height: 1.125rem;">
						                    <label for="" class="control-label" >${ele.temp_name}：</label>
						                    <input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
						                </div>`
    					break;
    				case 'number':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
				    						<label for="" class="control-label" >${ele.temp_name}：</label>
				    						<input type="number" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
    							</div>`
    					break;
    				case 'money':
    					modalBody += ` <div class="form-inline justify-content-center my-3 money-box">
    						<label for="" class="control-label">${ele.temp_name}</label>
    						<input type="text" class="form-control money-checked" id="${ele.column_name + '_' + myIndex}" placeholder="" name=${ele.column_name} reg=${ele.reg_validation}>
    						<p class="errorInfo">${item.error_msg}</p>
    						</div>`
    						break;
    				case 'textarea':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
    						<label for="" class="control-label" >${ele.temp_name}：</label>
    						<textarea  class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" ></textarea>
    						</div>`
    						break;
    				case 'select':
    					if(!ele.get_source) {return}
    					let url = BASE_PATH + 'credit/front/ReportGetData/' + ele.get_source
    					ele.get_source = ele.get_source.replace(new RegExp(/&/g),"$")
    					_this.selectInfoObj[ele.get_source] = ele.column_name
    					
    					//console.log("这是填报的select1，ele::",ele)
            			$.ajax({
            				type:'get',
            				url,
            				async:false,
            				dataType:'json',
            				success:(data)=>{
            					modalBody += ` <div class="form-inline justify-content-center my-3">
            						<label for="" class="control-label" >${ele.temp_name}：</label>
            						<select  class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
            							${data.selectStr}
            						</select>
            						</div>`
            						 
            				}
            			})
    					break;
    				case 'select2':
    					if(!ele.get_source) {return}
    					let url1 = BASE_PATH + 'credit/front/ReportGetData/' + ele.get_source
    					ele.get_source = ele.get_source.replace(new RegExp(/&/g),"$")
    					_this.selectInfoObj[ele.get_source] = ele.column_name
    					//console.log("这是填报的select2，ele::",ele)
    					$.ajax({
    						type:'get',
    						url:url1,
    						async:false,
    						dataType:'json',
    						success:(data)=>{
    							modalBody += ` <div class="form-inline justify-content-center my-3">
    								<label for="" class="control-label" >${ele.temp_name}：</label>
    								<select  class="form-control select2" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
    									${data.selectStr}
    								</select>
    								</div>`
    						}
    					})
    					break;
    				case 'file':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
						                    <label for="" class="control-label">${ele.temp_name}：</label>
						                    <button type="button" class="form-control" id="modal_logo_icon">
						                        <span style="display:block;height:1.5rem">${ele.place_hold}</span>
						                        <form class="iconUp" action="/credit/front/ReportGetData/uploadBrand" class="form-inline " enctype="multipart/form-data" method="post">
						                        	<input type="file" class="file-input" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
						                  		</form>
						                    </button>
						                </div>`
    					break;
    				case 'address' :
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-address">
		                    <label for="" class="control-label" >${ele.temp_name}：</label>
		                    <input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
		                </div>`
    					break;
    				default:
    					break;
    			}
    			
    			
    		})
    		this.formatBtnArr.push(`<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#modal${item}">编辑</a><a href="javascript:;"  class="delete" data-toggle="modal" data-target="#modal_delete">删除</a></div>`)
    		modalHtml += `<div class="modal fade" id="modal${item}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered" role="document">
					        <div class="modal-content">
					            <div class="modal-header">
					              	${titles[index].temp_name===null?'':titles[index].temp_name}
					            </div>
					            <div class="modal-body">
					                ${modalBody}
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-primary" id="modal_save${item}" data-dismiss="modal">保存</button>
					                <button type="button" class="btn btn-default" id="modal_cancel${item}" data-dismiss="modal">取消</button>
					            </div>
					        </div>
					    </div>
					</div>`
    		$("#modal"+item).remove()
    	})
    	
    	$("#container").append(modalHtml)
    },
    bindFormData(){
    	/**
    	 * 绑定表单数据
    	 */
    	let titles = this.formTitle;
    	let formIndex = this.formIndex;
    	let _this = this
    	formIndex.forEach((item,index)=>{
    		let conf_id = titles[index].id;
    		let getFormUrl = titles[index].get_source;
    		if(getFormUrl === null || getFormUrl === ''){return}
			let url = BASE_PATH  + 'credit/front/ReportGetData/' + getFormUrl.split("*")[0] 
			let paramObj = {}
			if(getFormUrl.split("*")[1]){
				let tempParam = getFormUrl.split("*")[1].split("$");//必要参数数组
				tempParam.forEach((item,index)=>{
					paramObj[item] = this.rows[item]
				})
			}
			 paramObj["conf_id"] = conf_id
			 paramObj["type"] = _this.rows.report_type
			 let temp;
			 $.ajax({
				 url,
				 type:'post',
				 data:paramObj,
				 success:(data)=>{
					 if($("#title"+item).text() === '企业类型注释') {
						 console.log('11111'+data.rows[0].type_of_enterprise_remark)
						 setTimeout(()=>{
						 $.ajax({
							 url,
							 type:'post',
							 data:paramObj,
							 success:(data)=>{
								 let text = data.rows[0].type_of_enterprise_remark
								 $("textarea[name='type_of_enterprise_remark']").val(text) 
							 }
						})
						 },5000)
					 }
					 temp = data
					 let arr = Array.from($("#title"+item))
					 if(!temp.rows || temp.rows === null){return}
					 arr.forEach((item,index)=>{
						 if($(item).siblings(".radio-con").length !== 0) {
							 //radio类型绑数
							 if(temp.rows.length === 0){return}
							 let obid = temp.rows[0].id;
							 $(item).siblings(".radio-con").find(".radio-box").find("input").attr("entityid",obid)
							 let name = $(item).siblings(".radio-con").find(".radio-box").find("input").attr("name")
							 let val =  temp.rows[0][name];
							 
							 $("input:radio[name="+name+"][value="+val+"]").attr("checked",true);  
							 return
						 }
						 if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
							 //信用等级
							 if(temp.rows.length === 0){return}
							 let name =$(item).next().find("select").attr("name")
							 $(item).next().find("select").val(temp.rows[0][name])
							
							 return;
						 }
						 if($(item).next().hasClass("textarea-module")) {
							 //无标题多行文本输入框
							 if(temp.rows.length === 0){return}
							 let obid = temp.rows[0].id;
							 $(item).next().find("textarea").attr("entityid",obid)
							 let name =$(item).next().find("textarea").attr("name")
							 $(item).next().find("textarea").val(temp.rows[0][name])
							 return;
						 }
						 if(($(item).next().find("input").hasClass("float-date"))) {
							 //浮动非财务
							 if(temp.rows.length === 0){return}
							 let obid = temp.rows[0].id;
							 $(item).next().find("input").attr("entityid",obid)
							 let name =$(item).next().find("input").attr("name")
							 $(item).next().find("input").val(temp.rows[0][name])
							 return;
						 }
						 let formArr = Array.from($(item).siblings().find(".form-control"))
						 if(!temp.rows || temp.rows.length === 0){return}
						 //实体id
						 let obid = temp.rows[0].id;
						 formArr.forEach((item,index)=>{
							 let obj = temp.rows[0];
//							 console.log(obid)
							 let id = $(item).attr("id");
							 let anotherIdArr = id.split("_")
							 anotherIdArr.pop();
							 let anotherId = anotherIdArr.join('_')
							 $("#"+id).attr("entryid",obid)
							 if($(item).is('select')){
								 //如果是select
								 $("#"+id).find("option[value='"+obj[anotherId]+"']").attr("selected",true);
							 }else {
//								 console.log($("#"+id),obj[anotherId])
								
								 if($("#"+id).hasClass("money-checked")){
									 //如果是金融
									 if(obj[anotherId]){
										 $("#"+id).val(Number(obj[anotherId].replace(/,/g,'')).toLocaleString('en-US'))
									 }
								 }else {
									 if($("#"+id).attr("name") === 'emp_num_date'&& obj[anotherId]==='') {
										 //2、报告摘要--员工人数统计时间默认填报当天
										 let nowDate = new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate()
										 $("#"+id).val(nowDate)
									 }else {
										 $("#"+id).val(obj[anotherId])
									 }
								 }
							 }
						 })
					 })
					 
					 //年检年度默认2012
					 //2012年以后成立的公司年度为空结果默认为 未作年检（新成立）就行
					 var mId='';
					 mId=$("input[name='year']").attr("id");
					 mId=mId.replace("year_","");
			    	if(!$("#year_"+mId).val()) {
			    		$("#year_"+mId).val("");
			    		var clsj=$("#establishment_date_"+mId).val();
			    		if(clsj!=null&&clsj!=""){
			    			if(parseInt(clsj.substring(0,4))>2012){
			    				var numbers = $("#year_result_"+mId).find("option"); //获取select下拉框的所有值
			    				for (var j = 1; j < numbers.length; j++) {
			    					$(numbers[j]).removeAttr("selected");
			    					//console.log($(numbers[j]).val(),($(numbers[j]).val() == '687'))
				    				if ($(numbers[j]).val() == '687') {
				    					//$(numbers[j]).attr("selected", "selected");
				    					$("#year_result_"+mId+" option[value='687']").prop("selected","selected");//两种方法效果一样
				    				}
			    				} 
			    			}else{
			    				$("#year_"+mId).val("2012");
			    				var numbers = $("#year_result_"+mId).find("option"); //获取select下拉框的所有值
			    				for (var j = 1; j < numbers.length; j++) {
			    					$(numbers[j]).removeAttr("selected");
			    					//console.log($(numbers[j]).val(),($(numbers[j]).val() == '687'))
				    				if ($(numbers[j]).val() == '685') {
				    					//$(numbers[j]).attr("selected", "selected");
				    					$("#year_result_"+mId+" option[value='685']").prop("selected","selected");//两种方法效果一样
				    				}
			    				} 
			    			}
			    		}
			    	}
					 
					 if(!$("#transaction_payment").val()) {
				    		$("#transaction_payment").val("目标公司未提供供应商名称，目前无法获取此信息。（SC did not provide any name of trade/service suppliers and we have no other sources to conduct the enquiry at present.  ）");
				    	}
				 }
			 })
	    	
    	
    	
    	})
    	
    },
    tabChange(){
        /**tab切换事件 */
    	$(".tab-bar li:eq(0) a").addClass("tab-active")
        $(".tab-bar li").click((e)=>{
            $(e.target).addClass("tab-active").parents("li").siblings().children('a').removeClass("tab-active")

            
           $("#container ").css('height',"calc(100% - 4.5rem)");
           $("#container ").css('padding-bottom',"0");
           $(".container-fluid").removeClass("over-hidden")
           /* 解决锚链接的偏移问题*/
           setTimeout(() => {
        	   $(".main").scrollTop( $(".main").scrollTop()-100)
        	   $(".container-fluid").addClass("over-hidden")
           }, 0);
           
        })
    },
    initFloat(){
    	/**
    	 * 初始化浮动模块
    	 */
    	let _this = this
    	let floatIndex = this.floatIndex;
    	if(floatIndex.length === 0){return}
    	let cw_title = []
    	let cw_contents = []
    	let ds_cw_title = []
    	let ds_cw_contents = []
    	let cw_dom;
    	let ds_dom;
    	_this.tableTitle = []
    	floatIndex.forEach((item,index)=>{//遍历类型为9的小模块
    		let floatParentId = this.floatTitle[index]['float_parent'];//浮动的父节点id
    		let titleId;
    		this.entityTitle.forEach((item,i)=>{//遍历所有的小模块
    			if(item.id === floatParentId ) {
    				if(_this.entityModalType[i] !== '10') {
    					//非财务模块浮动
    					let html = this.notMoneyFloatHtml[i+1]
    					$("#title"+i).after(html)
    					this.formIndex.push(i)
    					this.formTitle.push(this.floatTitle[index])
    				}else{
    					if(_this.entityTitle[i]["get_source"].includes("type=3") || this.entityTitle[i]["get_source"].includes("type=4")){
    						//大数财务模块浮动
    						ds_cw_title.push(this.floatTitle[index])
    						ds_cw_contents.push(this.floatContents[index])
    						ds_dom = $("#titleDs"+i)
    					}else {
    						//财务模块浮动
    						cw_title.push(this.floatTitle[index])
    						cw_contents.push(this.floatContents[index])
    						cw_dom = $("#titleCw"+i)
    					}
    				}
    				
    			}
    		})
    	})
    	//大数财务逻辑
    	let ds_top_html = ''
		let ds_table_html = ''
		if(ds_cw_title.length!==0){
			this.dsConfigAlterSource = ds_cw_title[0]['alter_source'];
			ds_cw_title.forEach((item,index)=>{
				//初始化大数财务模块
				let this_content = ds_cw_contents[index];
	    		let moneySource = ds_cw_contents[0][1].get_source;
	    		let moneyStr = ''
				let unitSource = ds_cw_contents[0][2].get_source;
	    		let unitStr = ''
				$.ajax({
					url:BASE_PATH + 'credit/front/ReportGetData/' + moneySource,
					async:false,
					type:'post',
					success:(data)=>{
						moneyStr = data.selectStr
					}
				})
				$.ajax({
					url:BASE_PATH + 'credit/front/ReportGetData/' + unitSource,
					async:false,
					type:'post',
					success:(data)=>{
						unitStr = data.selectStr
					}
				})
				if(item.sort === 1) {
					ds_top_html += `<div class="top-html mx-4">
						<div class="d-flex justify-content-between align-items-center mt-4">
							<!-- 企业名称和单位 -->
							<div class="ds-unit" style="width:100%">
								<div class="firm-name form-inline">
									<label class="mr-3" style="font-weight:600">${this_content[0].temp_name}</label>
									<input type="text" style="width:14rem" class="form-control" id="${this_content[0].column_name}ds" placeholder=${this_content[0].place_hold} name=${this_content[0].column_name}>
								</div>
								<div class="form-inline my-3" >
									<label style="font-weight:600;margin-left:60%" class="mr-3">${this_content[1].temp_name}</label>
									<select class="form-control mr-3" id="${this_content[1].column_name}ds" style="width:10rem" name=${this_content[1].column_name}>${moneyStr}</select>
									<select class="form-control mr-3" id="${this_content[2].column_name}ds" style="width:10rem" name=${this_content[2].column_name}>${unitStr}</select>
								</div>
							</div>
						</div>
						<div class="d-flex justify-content-between align-items-center mt-4">
							<!-- 日期 -->
							<div class="ds-date form-inline" style="width:100%">
								<input class="form-control  my-3" id="${this_content[3].column_name}ds" style="margin-left:44%;margin-right:20%" type="text" name=${this_content[3].column_name}  placeholder=${this_content[3].place_hold} />
								<input class="form-control"  id="${this_content[4].column_name}ds" type="text" name=${this_content[4].column_name}  placeholder=${this_content[4].place_hold} />
							</div>
						</div>`
				}else {
					ds_table_html += `<div class="table-content1 ds-table" style="background:#fff">
										<table id="tableDs"
											data-toggle="table"
											style="position: relative"
										>
										</table>
									</div>`
				}
			})
			if(ds_dom){
				ds_dom.after(ds_table_html)
				ds_dom.after(ds_top_html)
			}
	    	setTimeout(()=>{
	    		if(ds_cw_title[0]){
	    			InitObj.bindDsConfig(ds_cw_title[0]['get_source'],_this.rows)
	        		InitObj.initDsTable(ds_cw_contents[1],_this.dsGetSource,_this.dsAlterSource,_this.rows)
	    		}
	    	},0)
		}
    	
    	//财务逻辑
    	if(cw_title.length === 0){return}
    	this.cwConfigAlterSource = cw_title[0]['alter_source'];
    	this.cwConfigGetSource = cw_title[0]['get_source'];
    	let cw_top_html = ''
    	let cw_table_html = ''
    	let cw_bottom_html = ''
    	let tableCwId = []
    	cw_title.forEach((item,index)=>{
    		//初始化财务模块
    		let this_content = cw_contents[index];
    		let moneySource = cw_contents[0][4].get_source;
    		let moneyStr = ''
			let unitSource = cw_contents[0][5].get_source;
    		let unitStr = ''
			$.ajax({
				url:BASE_PATH + 'credit/front/ReportGetData/' + moneySource,
				async:false,
				type:'post',
				success:(data)=>{
					moneyStr = data.selectStr
				}
			})
			$.ajax({
				url:BASE_PATH + 'credit/front/ReportGetData/' + unitSource,
				async:false,
				type:'post',
				success:(data)=>{
					unitStr = data.selectStr
				}
			})
    		if(item.sort === 1) {
    			//财务模块杂七腊八的配置
    			let radioArr = this_content[1]["get_source"].split("&");
    			cw_top_html += `<div class="top-html mx-4">
    								<div class="cw-box d-flex justify-content-between align-items-center mt-4">
    									<div class="firm-name form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[0].temp_name}</label>
    										<input type="text" style="width:14rem" class="form-control" id="${this_content[0].column_name}cw" placeholder=${this_content[0].place_hold} name=${this_content[0].column_name}>
    									</div>
    									<div class="is-merge form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[1].temp_name}</label>
    										<div class="form-check form-check-inline">
	    										<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[0].split("-")[0]}>
				                                <label class="form-check-label mx-0" for="">${radioArr[0].split("-")[1]}</label>
			                                </div>
						    				<div class="form-check form-check-inline">
							    				<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[1].split("-")[0]}>
							    				<label class="form-check-label mx-0" for="">${radioArr[1].split("-")[1]}</label>
						    				</div>
    									</div>
    									<!-- 上传下载按钮 -->
    									<div class="btn-group">
    										<button class="btn btn-default mr-3 cwDown" >${this_content[2].temp_name}</button>
    										<button class="btn btn-default mr-3 cwDownData" style="background-color: #37485a;color:#fff;">${this_content[10].temp_name}</button>
						    				<button class="aa-btn btn btn-primary cwUp" style="position:relative">
						    					<form class="uploadForm" enctype="multipart/form-data" action="" method="POST" >
						    						<input style="opacity:0;cursor:pointer;width:100%;height:100%;position:absolute;left:0;top:0" type="file" name="file" class="fileInp">
						    						<input name="report_type" type="hidden" class="report_type"/>
								    				<input name="ficConf_id" type="hidden" class="ficConf_id"/>
						    					</form>
						    					${this_content[3].temp_name}
						    				</button>
    									</div>
    								</div>
    									<!-- 单位 -->
    								<div class="cw-unit">
    									<div class="form-inline my-3">
    										<label style="font-weight:600;margin-left:60%" class="mr-3">${this_content[4].temp_name}</label>
    										<select class="form-control mr-3 moneySel" id="${this_content[4].column_name}cw" style="width:10rem" name=${this_content[4].column_name}>${moneyStr}</select>
    										<select class="form-control mr-3 unitSel" id="${this_content[5].column_name}cw" style="width:10rem" name=${this_content[5].column_name}>${unitStr}</select>
    									</div>
    								</div>
    								<!-- 日期 -->
    								<div class="cw-date form-inline">
    									<input class="form-control my-3 dateInp1" id="${this_content[6].column_name}cw" style="margin-left:32%;margin-right:11%" type="text" name=${this_content[6].column_name}  placeholder=${this_content[6].place_hold} />
    									<input class="form-control dateInp2"  id="${this_content[7].column_name}cw" type="text" name=${this_content[7].column_name}  placeholder=${this_content[7].place_hold} />
    								</div>
    							</div>`
    			/**let tempUrl1 = BASE_PATH+ 'credit/front/ReportGetData/' +this_content[10]['get_source'];
    			let tempUrl2 = BASE_PATH+ 'credit/front/ReportGetData/' +this_content[12]['get_source'];
    			let tempUrl3 = BASE_PATH+ 'credit/front/ReportGetData/' +this_content[14]['get_source'];
    			let tempUrl4 = BASE_PATH+ 'credit/front/ReportGetData/' +this_content[16]['get_source'];
    			let options1 = ''
				let options2 = ''
				let options3 = ''
				let options4 = ''
    			$.when($.ajax({
    				url:tempUrl1,
    				async:false,
    				success:(data)=>{
    					options1 = data.selectStr
    				}
    			}),
    				$.ajax({
    					url:tempUrl2,
    					async:false,
    					success:(data)=>{
    						options2 = data.selectStr
    					}
    				}),
					$.ajax({
						url:tempUrl3,
						async:false,
						success:(data)=>{
							options3 = data.selectStr
						}
					}),
						$.ajax({
							url:tempUrl4,
							async:false,
							success:(data)=>{
								options4 = data.selectStr
							}
						})).done(()=>{
							
							cw_bottom_html +=`<div class="bottom-html"><div class="cw-bottom p-4">
								<label class="control-label">${this_content[10].temp_name}</label>
								<select class="form-control my-3 ${this_content[10].column_name}" id="${this_content[10].column_name}cw" name="${this_content[10].column_name}">${options1}</select>
								<textarea class="form-control ${this_content[11].column_name}" id="${this_content[11].column_name}cw" name="${this_content[11].column_name}" placeholder="${this_content[11].place_hold}"></textarea>
								</div>
								<div class="cw-bottom p-4">
								<label class="control-label">${this_content[12].temp_name}</label>
								<select class="form-control my-3 ${this_content[12].column_name}" id="${this_content[12].column_name}cw" name="${this_content[12].column_name}" >${options2}</select>
								<textarea class="form-control ${this_content[13].column_name}" id="${this_content[13].column_name}cw" name="${this_content[13].column_name}" placeholder="${this_content[13].place_hold}"></textarea>
								</div>
								<div class="cw-bottom p-4">
								<label class="control-label">${this_content[14].temp_name}</label>
								<select class="form-control my-3 ${this_content[14].column_name}" id="${this_content[14].column_name}cw" name="${this_content[14].column_name}" >${options3}</select>
								<textarea class="form-control ${this_content[15].column_name}" id="${this_content[15].column_name}cw" name="${this_content[15].column_name}" placeholder="${this_content[15].place_hold}"></textarea>
								</div>
								<div class="cw-bottom p-4">
								<label class="control-label">${this_content[16].temp_name}</label>
								<select class="form-control my-3 ${this_content[16].column_name}" id="${this_content[16].column_name}cw" name="${this_content[16].column_name}" >${options4}</select>
								
								</div></div>`
								//<textarea class="form-control ${this_content[17].column_name}" id="${this_content[17].column_name}cw" name="${this_content[17].column_name}" placeholder="${this_content[17].place_hold}"></textarea>
						})
    		**/
			}else {
    			let addtext = cw_title[1].place_hold
    			let conf_id = cw_title[0].id
//    			console.log(item,this_content,conf_id)
    			if(item.temp_name !== null && item.temp_name !== '') {
    				_this.tableTitle.push(item.temp_name.split("||"));
    				cw_table_html += `<div class="table-title">${item.temp_name.split("||")}</div>`
    				if(index !== 1){
    					//如果不是合计表，
    					cw_table_html += `<div class="cw-unit">
	    						<div class="form-inline my-3">
	    						<label style="font-weight:600;margin-left:60%" class="mr-3">${cw_contents[0][4].temp_name}</label>
	    						<select disabled="disabled" class="form-control mr-3 moneySel" style="width:10rem" name=${cw_contents[0][4].column_name}>${moneyStr}</select>
	    						<select disabled="disabled" class="form-control mr-3 unitSel" style="width:10rem" name=${cw_contents[0][5].column_name}>${unitStr}</select>
	    						</div>
    						</div>`
    					if(index === 3) {
    						//利润表
    						cw_table_html += `<div class="cw-date form-inline cw-range">
    							<input class="form-control my-3" id="${cw_contents[0][8].column_name}cw" style="margin-left:32%;margin-right:11%" type="text" name=${cw_contents[0][8].column_name}  placeholder=${cw_contents[0][6].place_hold} />
    							<input class="form-control" id="${cw_contents[0][9].column_name}cw" type="text" name=${cw_contents[0][9].column_name} placeholder=${cw_contents[0][7].place_hold} />
    							</div>`
    					}else {
    						cw_table_html += `<div class="cw-date form-inline">
    							<input class="form-control my-3 dateInp1" disabled="disabled" style="margin-left:32%;margin-right:11%" type="text" name=${cw_contents[0][6].column_name}  placeholder=${cw_contents[0][6].place_hold} />
    							<input class="form-control dateInp2" disabled="disabled" type="text" name=${cw_contents[0][7].column_name} placeholder=${cw_contents[0][7].place_hold} />
    							</div>`
    					}
    				}
    			}
    			
    			switch(item.sort) {
    				case 2:
    					//合计表
    					cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
					            				<table id="tableCwHj"
					            				data-toggle="table"
					            				style="position: relative"
					            				>
					            				</table>
											</div>`
    						tableCwId.push('tableCwHj')
    					
    				break;
    				case 3:
    					//资产负债表
    					for(let i=0;i<4;i++){
    						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
    							<table id="tableCwFz${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4 addBtn" type="button" >+ ${addtext}</button>
    							</div>`
    						tableCwId.push(`tableCwFz${i}`)
    					}
    				break;	
    				case 4:
    					//利润表
    					for(let i=0;i<2;i++){
    						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
    							<table id="tableCwLr${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4 addBtn" type="button"  >+ ${addtext}</button>
    							</div>`
    							tableCwId.push(`tableCwLr${i}`)
    					}
    					break;	
    				case 5:
    					//比率表
						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
							<table id="tableCwBl"
							data-toggle="table"
							style="position: relative"
							>
							</table>
							</div>`
							tableCwId.push(`tableCwBl`)
    					break;	
    				default:
    				break;
    			}
     		}
    	})
    	$(cw_dom).after(cw_bottom_html)
    	$(cw_dom).after(cw_table_html)
    	$(cw_dom).after(cw_top_html)
    	setTimeout(()=>{
    		InitObj.bindCwConfig(_this.cwConfigGetSource,cw_contents[0][1].column_name,_this.rows,_this.tableTitle)
    		InitObj.initCwTable(tableCwId,cw_contents[1],_this.cwGetSource,_this.cwAlterSource,_this.cwDeleteSource,_this.rows)
    		InitObj.cwModalCompute(_this.cwAlterSource)
    		InitObj.downLoadCw(cw_contents[0][2].alter_source,_this.rows);
			InitObj.downLoadCwData(cw_contents[0][10].alter_source,_this.rows);
			InitObj.upLoadCw(cw_contents[0][3].alter_source,_this.rows,_this.cwGetSource,_this.cwAlterSource,tableCwId);
			InitObj.addNewCwModal(_this.cwConfigAlterSource,_this.rows);
    	},0)
    },
    initContent(){
        /**初始化内容 */
    	this.entityTitle = [] //存放小模块的实体title
    	this.entityModalType = [] //存放小模块的实体类型
    	this.idArr = []    //存放table类型模块对应的index
    	this.contentsArr = [] //存放table类型模块的contents
    	this.title = [] //存放table类型模块的title
    	this.formIndex = [] //存放form类型模块对应的index
    	this.formTitle = [] //存放form类型模块的title
    	this.floatIndex = [] //存放float类型模块对应的index
    	this.floatTitle = [] //存放float类型模块的title
    	this.floatContents = [] //存放float类型模块的Contents
    	this.selectInfoObj = {} //存放选择框信息传给后台
    	this.notMoneyFloatHtml = {} //存放非财务模块的浮动html
    	this.cwGetSource = '' //存放获取财务url
    	this.cwAlterSource = '' //存放修改财务url
    	this.cwDeleteSource = '' //删除财务url
		this.dsGetSource = '' //存放大数获取财务url
		this.dsAlterSource = '' //存放大数修改财务url
    	this.saveStatusUrl = ''
		this.submitStatusUrl = ''
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
    	let reportType = JSON.parse(row).report_type
    	let type = 1
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id,reportType,type},
        	success:(data)=>{
                setTimeout(()=>{
                	_this.initModal();
                	InitObj.addressInit();
                	InitObj.regChecked();
                	_this.initTable();
                	_this.initFloat();
                	InitObj.dateInit();
                	InitObj.initSelect2();
                	_this.bindFormData();
                	_this.tabChange();
                	_this.modalClean();
                	_this.bottomBtnEvent();
                	_this.companyTypeSelect()
            	    Public.tabFixed(".tab-bar",".main",120,90)
            	    Public.textAreaEvent();
            	    let firmArr = Array.from($(".firm-info"));
            	    firmArr.forEach((item,index)=>{
            	    	if($(item).children().length === 2) {
            	    		$(item).addClass("abc")
            	    	}
            	    })
                },0)
                /**
                 * 头部
                 */
                let header = data.defaultModule;
                let headerHtml = ''
            	let temp = ''
            	let flagIndex;
        		 header.forEach((item,index)=>{
                 	if(item.node_level === '1') {
                 		flagIndex = index
                 	}
        		 })
                header.forEach((item,index)=>{
                	if(item.node_level === '1') {
                		headerHtml += `<div class="order-num main-title">${item.temp_name}：<span id="num"></span></div>`
                	}else {
                		if (flagIndex < 4) {
                			if(index < 4){
                				temp += `<div class="order-item col-md-4">
                					<span class="fw"  >${item.temp_name}：</span>
                					<span id=""></span>
                					</div>`
                			}else {
                				temp += `<div class="order-item col-md-4 mt-3">
                					<span class="fw"  >${item.temp_name}：</span>
                					<span id=""></span>
                					</div>`
                			}
						}else {
							if(index < 3){
                				temp += `<div class="order-item col-md-4">
                					<span class="fw"  >${item.temp_name}：</span>
                					<span id=""></span>
                					</div>`
                			}else {
                				temp += `<div class="order-item col-md-4 mt-3">
                					<span class="fw"  >${item.temp_name}：</span>
                					<span id=""></span>
                					</div>`
                			}
						}
                	}
                })
                headerHtml += `<div class="order-item-box d-flex flex-wrap justify-content-start row pr-4">`
            	headerHtml += 	temp;
                headerHtml += `</div>`
                $(".order-box").html(headerHtml)
                //头部绑数
                let row = JSON.parse(localStorage.getItem("row"))
                $("#num").html(row.num)
                let tempArr = [row.companyNames,row.companyZHNames,row.reportType,row.receiver_date,row.end_date]
                let headItem = Array.from($(".fw"))
                headItem.forEach((item,index)=>{
                	$(item).siblings("span").html(tempArr[index])
                })
                /**
                 * tabFixed
                 */
                let tabFixed = data.tabFixed;
                let tabFixedHtml = ''
                tabFixed.forEach((item,index)=>{
                	tabFixedHtml += `<li class="tab-info"><a href="#anchor${item.anchor_id}">${item.temp_name}</a></li>`
                })
                
                $(".tab-bar").html(tabFixedHtml)
                /**
                 * 内容模块部分
                 */
                let modules = data.modules;    
                let contentHtml = '' 
                let bottomBtn = ''
				console.log("~~data:",data);
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	_this.entityTitle.push(item.title)
                	_this.entityModalType.push(item.smallModileType)
                	let smallModileType = item.smallModileType
                	
                	if(item.title.is_merger_next === '0'){
                		if(item.title.temp_name === '行业分析'){
                				return
                		}
                		if(item.title.temp_name === null || item.title.temp_name === "" || item.title.float_parent ) {
                			if(item.title.word_key !== 'hangyexinxi'){
                				contentHtml +=  `<div class="bg-f mb-3"  ><a  style="display:none" class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                			}else {
                				contentHtml +=  `<div class="bg-f pb-4 mb-3" ><a style="display:none" class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                			}
                		}else if(smallModileType === '10'){
                			//财务模块
                			if(item["title"]["get_source"].includes("type=3") || item["title"]["get_source"].includes("type=4")){
                				//大数
                				_this.dsGetSource = item.title.get_source;
                				_this.dsAlterSource = item.title.alter_source;
                				contentHtml +=  `<div class="bg-f pb-4 mb-3 gjds"><a class="l-title dsModal" name="anchor${item.title.id}" id="titleDs${index}">${item.title.temp_name}</a>`
                			}else {
                				_this.cwGetSource = item.title.get_source;
                				_this.cwAlterSource = item.title.alter_source;
                				_this.cwDeleteSource = item.title.remove_source;
                				contentHtml +=  `<div class="bg-f pb-4 mb-3 gjcw"><a class="l-title cwModal" name="anchor${item.title.id}" id="titleCw${index}">${item.title.temp_name}</a>`
                			}
                		}else if(smallModileType !== '-2' && smallModileType !== '5' ) {
                			contentHtml +=  `<div class="bg-f pb-4 mb-3"><a class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                		}
                		
                	}else {
                		if(item.title.temp_name === null || item.title.temp_name === "" || item.title.float_parent || item.title.temp_name === '行业分析') {
                			contentHtml +=  `<div class="bg-f pb-4" ><a style="display:none" class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                		}else if(smallModileType === '10'){
                			//财务模块
                			_this.cwGetSource = item.title.get_source;
                			_this.cwAlterSource = item.title.alter_source;
                			_this.cwDeleteSource = item.title.remove_source;
                			contentHtml +=  `<div class="bg-f pb-4gjcw"><a class="l-title cwModal" name="anchor${item.title.id}" id="titleCw${index}">${item.title.temp_name}</a>`
                		}else if(smallModileType !== '-2' && smallModileType !== '5' ) {
                			contentHtml +=  `<div class="bg-f pb-4 "><a class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                		}
                	}
                	
                	let btnText = item.title.place_hold;
                	let formArr = item.contents; 
                	//模块的类型

					let outItem = item;
					//console.assert(outItem.title.is_disable!=='1',outItem)
                	switch(smallModileType) {
                		case '0':
                			//表单类型
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ind = index
                			let rowNum = 0;//代表独占一行的input数量
		                	formArr.forEach((item,index)=>{
		                		//console.assert(item.is_disable!=='1',item)
		                				let formGroup = ''
		                        		//判断input的类型
		                        		let field_type = item.field_type
		                        		if(!field_type) {
		                        			formGroup += `<div class="form-group">
		        						            		<label for="" class="mb-2">${item.temp_name}</label>
		        						            		<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} ${outItem.title.is_disable==='2'||item.is_disable==='2'?"readonly":''} type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'text':
		                        					formGroup += `<div class="form-group">
	        						            		<label for="" class="mb-2">${item.temp_name}</label>
	        						            		<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} ${outItem.title.is_disable==='2'||item.is_disable==='2'?"readonly":''} type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
	        						            		<p class="errorInfo">${item.error_msg}</p>
	        					            		</div>`
	                        						break;
		                        				case 'number':
			                    						formGroup += `<div class="form-group">
					                        							<label for="" class="mb-2">${item.temp_name}</label>
					                        							<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''+''} type="number" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
					                        							<p class="errorInfo">${item.error_msg}</p>
				                        							</div>`
		                        					
		                        					break;
		                        				case 'money':
		                        					formGroup += `<div class="form-group">
		                        						<label for="" class="mb-2">${item.temp_name}</label>
		                        						<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} type="text" class="form-control money-checked" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		                        						<p class="errorInfo">${item.error_msg}</p>
		                        						</div>`
		                        						
		                        						break;
		                        				case 'date':
		                        					formGroup += `<div class="form-group date-form" style="position: relative">
                                                                    <img src="${BASE_PATH }static/credit/imgs/total_manage/calen.png" alt="" style="position: absolute;right: 1rem;top: 2.4rem;width: 1rem;height: 1.125rem;">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
		                        					break;
		                        				case 'date_scope':
		                        					formGroup += `<div class="form-group date-scope-form" style="position: relative;">
                                                        <img src="${BASE_PATH }static/credit/imgs/total_manage/calen.png" alt="" style="position: absolute;right: 1rem;top: 2.4rem;width: 1rem;height: 1.125rem;">
									            		<label for="" class="mb-2">${item.temp_name}</label>
									            		<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
									            		<p class="errorInfo">${item.error_msg}</p>
								            		</div>`
		                        					break;
							            		case 'address':
							            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''}  ${outItem.title.is_disable==='2'||item.is_disable==='2'?"readonly":''} type="text" class="form-control"  style="width: 100%" name=${item.column_name} id="${item.column_name}_${ind}">
									                                </div>`
							            			break;
							            		case 'select':
							            			if(item.get_source === null){return}
							            			let url = BASE_PATH + 'credit/front/ReportGetData/' + item.get_source
							            			$.ajax({
							            				type:'get',
							            				url,
							            				async:false,
							            				dataType:'json',
							            				success:(data)=>{
							            				formGroup += `<div class="form-group">
										            					<label for="" class="mb-2">${item.temp_name}</label>
										            					<select ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} name=${item.column_name} id="${item.column_name}_${ind}" class="form-control">
										            						${data.selectStr}
										            					</select>
							            							</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'select3':
							            			if(item.get_source === null){return}
							            			let urls = BASE_PATH + 'credit/front/ReportGetData/' + item.get_source
							            			$.ajax({
							            				type:'get',
							            				url:urls,
							            				async:false,
							            				dataType:'json',
							            				success:(data)=>{
							            					let a = data.selectStr.replace(/value/g,'a')
							            					formGroup += `<div class="form-group">
							            						<label for="" class="mb-2">${item.temp_name}</label>
							            						<input ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} name=${item.column_name} id="${item.column_name}_${ind}" class="form-control" list="cars">
							            						<datalist id="cars">
							            							${a}
							            						</datalist>
							            						</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'textarea':
							            			formGroup += `  <div class="form-group"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <textarea ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} class="form-control"  style="width: 100%;height: 6rem" name=${item.column_name} id="${item.column_name}_${ind}"></textarea>
									                                </div>`
									                break;
		        							    default :
		        							    	break;
		                        			}
		                        		}
		                				if(formArr[index-1] && formArr[index-1]['field_type'] === 'address')  {
		                					rowNum += 1
		                				}
		                        		
		                        		if((index - rowNum)%3 === 0  || formArr[index-1]['field_type'] === 'address' || formArr[index]['field_type'] === 'address' || formArr[index]['field_type'] === 'textarea'){
		                        			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
		                        		}
		                        		contentHtml += formGroup;
		                        		
		                        		if(((index+1 - rowNum)%3 === 0 && index !==0) || formArr[index]['field_type'] === 'address' || (formArr[index+1]&&formArr[index+1]['field_type'] === 'textarea') || (formArr[index+1]&&formArr[index+1]['field_type'] === 'address') ||((formArr[index+1]&&formArr[index+1]['field_type'] === 'textarea') && formArr[index+2]&&formArr[index+2]['field_type'] === 'textarea')){
		                        			contentHtml += `</div>`    
		                        		}
		                        		
		                	}) 
		                	contentHtml += `</div>`   
		                	break;
                		case '1':
							console.assert(item.is_disable!=='1',item);

							//table类型
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			if(item.title.temp_name && item.title.temp_name.includes('质检意见')){
                				//质检意见无新增
                				contentHtml += `<div class="table-content1" style="background:#fff">
                					<table id="table${index}"
                					data-toggle="table"
                					style="position: relative;table-layout: fixed;"
                					>
                					</table>
                					</div>`
                			}else if(item.title.temp_name && (item.title.temp_name.includes('股东详情') || item.title.temp_name.includes('管理层'))){
                				//法人，自然人股东 不换行
                				contentHtml += `<div class="table-content1 nowrap" style="background:#fff">
                					<table id="table${index}"
                					data-toggle="table"
                					style="position: relative"
                					>
                					</table>
                					<button class="btn btn-lg btn-block mb-3 mt-4 ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''}" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                					</div>`
                			}else {
                				contentHtml += `<div class="table-content1" style="background:#fff">
                					<table id="table${index}"
                					data-toggle="table"
                					style="position: relative;table-layout: fixed;"
                					>
                					</table>
                					<button class="btn btn-lg btn-block mb-3 mt-4 ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''}" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                					</div>`
                			}
                		
                			break;
                		case '11':
							console.assert(item.is_disable!=='1',item);
                			//table类型
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative;table-layout: fixed;"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-3 mt-4 ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''}" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                				</div>`
                		
                			break;
                		case '2':
                			//附件类型
                			contentHtml += Public.fileConfig(item,_this.rows)
                			break;
                		case '5':
                			//固定底部的按钮组
                			item.title.column_name === 'save'?_this.saveStatusUrl = item.title.alter_source:_this.submitStatusUrl = item.title.alter_source
                			let className = item.title.column_name === 'save'?'btn btn-default ml-4':'btn btn-primary ml-4'
                			bottomBtn += `<button id=${item.title.column_name} class="${className}">${item.title.temp_name}</button>`
                			break;
                		case '6':
                			//信用等级
							console.error(item)
                			let inputObj = item.contents[0]
                			_this.formTitle.push(inputObj)
                			_this.formIndex.push(index)
                			let selectUrl = BASE_PATH + 'credit/front/ReportGetData/' + item.contents[0]["remove_source"]
	            			$.ajax({
	            				type:'get',
	            				url:selectUrl,
	            				async:false,
	            				dataType:'json',
	            				success:(data)=>{
	            					contentHtml += `<div class="form-group form-inline p-4 mx-3" id="xydj">
	            						<label >${inputObj.temp_name}</label>
	            						<select ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} type="text" id=${inputObj.column_name} name=${inputObj.column_name} class="form-control mx-3" placeholder="" aria-describedby="helpId" style="border-color:#1890ff">
	            							 ${data["selectStr"]}
	            						</select>
	            						<span id="helpId" class="text-muted">${inputObj.suffix}</span>
	            						</div>`
	            				}
            				})
                			
                			let tableContents = []
                			item.contents.forEach((item,index)=>{
                				if(index > 0 && index < 5) {
                					tableContents.push(item)
                				}
                			})
                			_this.idArr.push(index)
                			_this.contentsArr.push(tableContents)
                			_this.title.push(item.title)
            				contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				style="position: relative"
				                				>
				                				</table>
                							</div>`
            				let explainObj = item.contents[5];
                			let explainUrl = explainObj.get_source;
                			let paramObj = {}
                			if(explainUrl.split("*")[1]) {
                				let tempParam = explainUrl.split("*")[1].split("$");//必要参数数组
                				tempParam.forEach((item,index)=>{
                					paramObj[item] = this.rows[item]
                				})
                			}
                			let conf_id = item.title.id;
                        	paramObj["conf_id"] = conf_id
                			let returnData;
                			$.ajax({
                				url:BASE_PATH + 'credit/front/ReportGetData/' + explainUrl.split("*")[0],
                				data:paramObj,
                				async:false,
                				type:'post',
                				success:(data)=>{
                					console.log(data)
                					returnData = data.rows
                				}
                			})
                			returnData.forEach((item,index)=>{
                				contentHtml += `<p class="m-3 ml-4">${item.describe}</p>`
                			})
                			break;
                		case '7':
                			//单独小模块的多行输入框 保存回显同表单模块
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ot_item = item
                			if(item["title"]["temp_name"] !== '行业分析'){
	                			item.contents.forEach((item,index)=>{
	                				if(ot_item.title.temp_name === '' || ot_item.title.temp_name === null) {
	                					contentHtml += ` <div class="textarea-module form-group mb-3 p-4" style="background:#fff;margin-top:-2rem">
	                						<label for="" class="thead-label">${item.temp_name}</label>
	                						<textarea ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
	            						</div>`
	                				}else{
	                					contentHtml += ` <div class="textarea-module form-group mb-3 p-4" style="background:#fff">
		                						<label for="" class="thead-label">${item.temp_name}</label>
		                						<textarea ${outItem.title.is_disable==='1'||item.is_disable==='1'?"disabled":''} name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
	                						</div>`
	                				}
	                			})
                			}
                			break;
                		case '8':
                			//radio类型 总体评价模块    保存回显同表单模块
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			
                			let str = item.contents[0].get_source;
                			let this_item = item
                			let strItem = str.split("&")
                			contentHtml += `<div class="table-content1 form-group radio-con" style="background:#fff">
					                        	<div class="radio-box">`
                				strItem.forEach((item,index)=>{
                				contentHtml += ` <div class="form-check form-check-inline mr-5">
					                                <input class="form-check-input" type="radio" name=${this_item.contents[0].column_name} id="inlineRadio${index}" value=${item.split("-")[0]}>
					                                <label class="form-check-label mx-0" for="inlineRadio${index}">${item.split("-")[1]}</label>
					                            </div>`
                			})
                				
                				
            				contentHtml += `</div></div>`
                			break;
                		case '9':
                			//浮动类型
                			_this.floatIndex.push(index)
                			_this.floatTitle.push(item.title)
                			_this.floatContents.push(item.contents)
                			if(item.contents.length === 0) {
                				//非财务模块的浮动
                				_this.notMoneyFloatHtml[index] = `<div class="form-group form-inline p-4" style="position: relative">
                                              <img src="${BASE_PATH }static/credit/imgs/total_manage/calen.png" alt="" style="position: absolute;left: 13.5rem;top: 2.1rem;width: 1rem;height: 1.125rem;">
					                          <label >${item.title.temp_name === null?'':item.title.temp_name}</label>
					                          <input type="text" placeholder=${item.title.place_hold} name=${item.title.column_name} class="form-control mx-3 float-date" >
					                        </div>`
                			}
                			break;
                		case '10':
                			//财务模块
                			
                			break;
            			default:
            				break;
            		}
                	
                	if(smallModileType === '10') {
                		//财务
                		contentHtml += `</div><button class="btn mb-3 btn-lg btn-block" id="addCwMdal" style="display:none">增加一个财务模版</button>`
                	}else {
                		contentHtml += `</div>`
                	}
                })
                
                $(".main-content").html(contentHtml)
                $(".position-fixed").html(bottomBtn)
            }
        })
    },
    modalClean(){
    	/**上传图标 */
    	let _this =this
        $(".file-input").change(function(){
            let filename = $(this).val().replace("C:\\fakepath\\","");
            let num = filename.split(".").length;
            let filetype = filename.split(".")[num-1].toLowerCase();
            if(filetype === 'jpeg' || filetype === 'jpg' || filetype === 'png' ) {
                $("#modal_logo_icon span").text(filename)
               $(".iconUp").ajaxSubmit({
            	   data:{
            		 "order_id":_this.rows["id"]  
            	   },
            	   success:(data)=>{
            		   $(".file-input").attr("iconurl",data["url"])
            	   }
               })
            }else {
                Public.message("info","上传文件格式错误！")
            }
        })
    	
    	this.idArr.forEach((item,index)=>{
    		//点击新增一条清空模态框中内容
    		$("#addBtn"+item).click(()=>{
    			this.isAdd = true
    			$("#modal"+item+" select.select2").val(null).trigger("change" )
    			$("#modal"+item+" input").val("");
    			$("#modal"+item+" input").siblings("span").html("");
		    	$("#modal"+item+" textarea").val("");
	    	    $("#modal"+item+" select").find("option:selected").attr("selected", false);
			    $("#modal"+item+" select").find("option").first().attr("selected", true);
    
    		})
    		//自动计算出生年月和年龄
    		if($("#modal"+item).find(".modal-header").text().trim() === '自然人股东详情' || $("#modal"+item).find(".modal-header").text().trim() === '管理层' || $("#modal"+item).find(".modal-header").text().trim() === 'Mangement' || $("#modal"+item).find(".modal-header").text().trim() === 'Natural Person Shareholder Details') {
    			let arr = $("#modal"+item).find(".modal-body").find("label").next().attr("id").split("_")
    			let index  = arr[arr.length-1];
    			let $idCard = $("#id_no_"+index)
				if($("#modal"+item).find(".modal-header").text().trim() === '管理层' || $("#modal"+item).find(".modal-header").text().trim() === 'Mangement'){
					$idCard = $("#id_card_"+index)
				}
    			let $age = $("#age_"+index)
    			let $birth = $("#birth_date_"+index)
    			$idCard.blur(()=>{
    				let val = $idCard.val()
    				if(val.length === 18 && typeof Number(val) === 'number' && Number(val) !== NaN) {
    					val = val.substring(6,14)
    					let  aDate = new Date(val.substring(0,4)+'-'+val.substring(4,6)+'-'+val.substring(6,8));
    					let bDate = new Date();
    					let age = bDate.getFullYear() - aDate.getFullYear();
    					$age.val(age)
    					$birth.val(val.substring(0,4)+'-'+val.substring(4,6)+'-'+val.substring(6,8))
    				}else {
    					$age.val('')
    					$birth.val('')
    				}
    				
    			})
    			
    		}
    		//点击模态框保存按钮，新增一条数据
    		$("#modal_save"+item).unbind().click(()=>{
    			let dataJson = []
    			let dataJsonObj = {}
    			let formArr = Array.from($("#modal"+item).find(".form-inline"));
				formArr.forEach((item,index)=>{
					let id = $(item).children("label").next().attr("id");
					//锟э窖拷锟锟斤拷锟斤拷*锟斤拷
					if($(item).hasClass("money-box")){
						let name = $('#'+id).attr("name")
						let val = $('#'+id).val().replace(/,/g,"锟э窖拷锟锟斤拷锟斤拷*锟斤拷")
						dataJsonObj[name] = val
						return
					}
					if($("#"+id).is("button")) {
						//商标
						let name = $('#'+$('#'+id).find("input").attr("id")).attr("name")
						let val = $('#'+$('#'+id).find("input").attr("id")).attr("iconurl")
						dataJsonObj[name] = val
						return
					}
					if($("#"+id).hasClass("select2")) {
						let name = $('#'+id).attr("name")
						let val = $('#'+id).val()
						if(val){
							val = val.join("$")
							dataJsonObj[name] = val
						}
						return
					}
					
					//调用form格式化数据函数
					console.log($('#'+id))
					let tempObj = this.getFormData($(item).children("label").next());
					for(let i in tempObj){
						if(tempObj.hasOwnProperty(i))
						dataJsonObj[i] = tempObj[i]
					}
				})
    			
    			
    			let urlTemp = this.title[index].alter_source
    			if(!urlTemp){return}
    			let url = BASE_PATH  + 'credit/front/ReportGetData/' + urlTemp.split("*")[0]
    			if(!this.isAdd){
    				//是修改保存
    				dataJsonObj["id"] = this.rowId
    			}
            	let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
            	let paramObj = {}
            	tempParam.forEach((item,index)=>{
            		dataJsonObj[item] = this.rows[item]
    			 })
				 delete dataJsonObj['order_num'];
    			 dataJson.push(dataJsonObj)
				//设置经营地址接口
				if(urlTemp.split("*")[0].indexOf('className=CreditCompanyBussinessAddress')!==-1){
					let index = 0;
					$('label').each(function () {
						if($(this).text()==='营业收入统计时间段'){
							// console.log($($(this).next('input')[0]).attr('id').split('_')[6])
							index = $($(this).next('input')[0]).attr('id').split('_')[6];
						}
					})
					$('#company_address_'+index).val(dataJson[0]['business_address']) //报告摘要的经营地址为新增的经营地址
				}
            	paramObj["dataJson"] = JSON.stringify(dataJson)

            	//调用新增修改接口
            	$.ajax({
            		url,
            		type:'post',
            		dataType:'json',
            		data:paramObj,
            		contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            		success:(data)=>{
            			if(data.statusCode === 1) {
            				Public.message("success",this.isAdd?'新增成功！':'修改成功！')
            				//刷新数据
        					_this.refreshTable($("#table"+item));
            				let formArr = Array.from($("#modal"+item).find(".monyCol"));
            				//console.log(formArr)
            				formArr.forEach((item,index)=>{
            					let name = $(item).text();
            					if(name!=""&&name!=undefined)
            					$(item).text(format(name));
            				});
            			}else {
            				Public.message("error",data.message)
            			}
            		}
            	})
    		})
    	})
    },
    refreshTable(ele){
    	//刷新表格中的数据
    	$(ele).bootstrapTable("refresh");
    },
    getFormData(form) {
    	//序列化
        var unindexed_array = form.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function (n, i) {
          indexed_array[n['name']] = n['value'];
        });

        return indexed_array;
    },
    bottomBtnEvent(){
    	/**
    	 * 底部按钮点击事件
    	 */
    	let formTitles = this.formTitle;
    	let formIndex = this.formIndex;
    	let _this = this
//    	console.log(formIndex)
    	if(formIndex.length === 0) {
    		 //点击保存按钮
    		$(".position-fixed").on("click","#save",(e)=>{
    			InitObj.saveDsConfigInfo(_this.dsConfigAlterSource,_this.rows);
    			Public.message("success","操作成功!")
    		})
    		 //点击提交按钮
    		$(".position-fixed").on("click","#commit",(e)=>{
    			InitObj.saveDsConfigInfo(_this.dsConfigAlterSource,_this.rows);
    			console.log("_this.dsConfigAlterSource-----_this.rows",_this.dsConfigAlterSource,_this.rows)
    			 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.submitStatusUrl + `model.id=${_this.rows["id"]}&statusCode=294`;
				 $.ajax({
					 url,
					 type:'post',
					 success:(data)=>{
						 $("body").mLoading("hide")
						 if(data.statusCode === 1) {
							 Public.message("success",data.message)
							 Public.goToInfoImportPage();
						 }else if(data.statusCode !== 1){
							 Public.message("error",data.message)
						 }
					 }
				 })
    		})
    		
    		return
    	}
    	formIndex.forEach((item,index)=>{
    		let alterSource = formTitles[index]["alter_source"];
    		if(alterSource === null || alterSource === ''){return}
    		let url = BASE_PATH +'credit/front/ReportGetData/'+ alterSource.split("*")[0] ;
    		let dataJson = []
    		let dataJsonObj = {} 
    		if(alterSource.split("*")[1]) {
    			
    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
    			tempParam.forEach((item,index)=>{
    				if(item === 'company_id') {
    					if(this.rows["info_language"] === '' || this.rows["info_language"] === null || this.rows["info_language"] === 612) {
    						dataJsonObj[item] = this.rows[item]
    					}else if(this.rows["info_language"] === 613) {
    						dataJsonObj[item] = this.rows["company_id_en"]
    					}else if(this.rows["info_language"] === 614) {
    						dataJsonObj[item] = this.rows["company_id_fan"]
    					}
    				}
    				dataJsonObj[item] = this.rows[item]
    			})
    		}
			 //点击保存按钮
    		$(".position-fixed").on("click","#save",(e)=>{
    			$("body").mLoading("show")
    			InitObj.saveCwConfigInfo(_this.cwConfigAlterSource,_this.rows);
    			InitObj.saveDsConfigInfo(_this.dsConfigAlterSource,_this.rows);
    			$("#save").addClass("disabled")
				
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }else if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
    					 //信用等级
    					 let name =$(item).next().find("select").attr("name")
    					 let val =$(item).next().find("select option:selected").val()
    					  val = val?val:''
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					  let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    				 }else if($(item).next().find("input").hasClass("float-date")){
    					 //浮动非财务
    					  let name =$(item).next().find("input").attr("name")
    					  let val =$(item).next().find("input").val()
    					  let id = $(item).next().find("input").attr("entityid")
    					  dataJsonObj["id"] = id
    					  dataJsonObj[name] = val
    				 }else {
    				 	//财务
    					 let formArr = Array.from($(item).siblings().find(".form-control"))
    					 formArr.forEach((item,index)=>{
    						 let id = $(item).attr("id");
    						 let anotherIdArr = id.split("_")
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    						 }
    					 })
    				 }
    				 
    			 })
    			 dataJson[0] = dataJsonObj
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("body").mLoading("hide")
    					 $("#save").removeClass("disabled")
    					 if(data.statusCode === 1 && !formIndex[index+1]) {
							 Public.message("success",data.message)
    					 }else if(data.statusCode !== 1){
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    			 //点击提交按钮
    		$(".position-fixed").on("click","#commit",(e)=>{
    			 $("body").mLoading("show")
    			 console.log("_this.cwConfigAlterSource,_this.rows",_this.cwConfigAlterSource,_this.rows)
    			InitObj.saveCwConfigInfo(_this.cwConfigAlterSource,_this.rows);
    			InitObj.saveDsConfigInfo(_this.dsConfigAlterSource,_this.rows);
    			$("#commit").addClass("disabled")
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }else if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
    					 //信用等级
    					 let name =$(item).next().find("select").attr("name")
    					 let val =$(item).next().find("select option:selected").val()
    					  val = val?val:''
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					 let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    				 }else if($(item).next().find("input").hasClass("float-date")){
    					 //浮动非财务
	   					  let name =$(item).next().find("input").attr("name")
	   					  let val =$(item).next().find("input").val()
	   					  let id = $(item).next().find("input").attr("entityid")
	   					  dataJsonObj["id"] = id
	   					  dataJsonObj[name] = val
	   				 }else {
    					 let formArr = Array.from($(item).siblings().find(".form-control"))
    					 formArr.forEach((item,index)=>{
    						 let id = $(item).attr("id");
    						 let anotherIdArr = id.split("_")
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3")
    						 }
    					 })
    				 }
    			 })
    			 dataJson[0] = dataJsonObj
    			 console.log("dataJson",dataJson)
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("#commit").removeClass("disabled")
    					 if(data.statusCode === 1 && !formIndex[index+1]) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.submitStatusUrl + `model.id=${_this.rows["id"]}&statusCode=294`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 $("body").mLoading("hide")
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 Public.goToInfoImportPage();
    								 }else if(data.statusCode !== 1){
    									 Public.message("error",data.message)
    								 }
    							 }
    						 })
    					 }else if(data.statusCode !== 1 ){
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    	})
    },
    companyTypeSelect(){
    	//企业类型选择关联企业类型注释
    	
    	$("select[name='company_type']").change((e)=>{
    		let $option = $(e.target).children("option:selected")
    		let reportType = this.rows.report_type
    		console.log(typeof reportType)
    		if(reportType === '12') {
    			//中文
    			let remark = $option.attr("m-detail-remark")
    			$("textarea[name='type_of_enterprise_remark']").val(remark==='null'?'':remark)
    		}else if(reportType === '14') {
    			//英文
    			let con = $option.attr("m-detail-content")
    			$("textarea[name='type_of_enterprise_remark']").val(con==='null'?'':con)
    		}
    	})
    	
    },
}
function format (num) {
    return (num.toFixed() + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}
ReportConfig.init();
$('.return_back').on('click',function () {
    layer.confirm('是否保存已录入信息？', {
        btn: ['保存','取消'] //按钮
    }, function(){
    	$('#save').trigger('click')

        location.reload();
    }, function(){
        location.reload();
    });

});

// 19-03-30 新增禁用功能, by lijianlong
// 如果模块或字段中有属性is_disable: "1"，
	// 如果是表单输入类型则禁用此模块下所有输入或对应字段输入，方法是增加属性disabled，
	// 如果模块是表格则禁用新增按钮，方法是增加样式 disabled。
