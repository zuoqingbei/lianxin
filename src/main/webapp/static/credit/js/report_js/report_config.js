
let ReportConfig = {
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
    dateInit(){
    	/**
    	 * 日期初始化
    	 */
    	let dateArr = Array.from($('.date-form input'))
    	console.log(dateArr)
    	dateArr.forEach((item,index)=>{
    		laydate.render({
    			elem: item,
    			format: 'yyyy年MM月dd日'
    		});
    	})
    	let dateScopeArr = Array.from($('.date-scope-form input'))
    	dateScopeArr.forEach((item,index)=>{
    		laydate.render({
    			elem: item,
    			format: 'yyyy年MM月dd日',
    			range:true
    		});
    	})
    	
    	
    	//模态窗里面的时间控件初始化
    	let modals = Array.from($(".modal"))
    	let modalDates = Array.from($(".modal .modal-date input"))
    	modals.forEach((item,index)=>{
    		modalDates.forEach((item,index)=>{
    			laydate.render({
	    			elem: item,
	    			format: 'yyyy年MM月dd日'
	    		});
    		})
    	})
    },
    addressInit(){
    	/**
    	 * 地址初始化
    	 */
    	let addArr = Array.from($('.address-form input'))
    	addArr.forEach((item,index)=>{
    		$(item).focus(function (e) {
    			SelCity(this,e);
    			let top = $(item).offset().top
    			$("#PoPy").css("top",top+30+"px")
    			$(".main").scroll(()=>{
    				let top = $(item).offset().top
    				$("#PoPy").css("top",top+30+"px")
    				if(top < 90) {
    					$("#cColse").trigger("click")
    				}
    			})
    		});
    	})
    	
    	//模态窗里面的地址控件初始化
    	let modals = Array.from($(".modal"))
    	let modalAdd = Array.from($(".modal .modal-address input"))
    	modals.forEach((item,index)=>{
    		modalAdd.forEach((item,index)=>{
    			$(item).focus(function (e) {
        			SelCity(this,e);
        			let top = $(item).offset().top
        			$("#PoPy").css("top",top+30+"px")
        			$(".main").scroll(()=>{
        				let top = $(item).offset().top
        				$("#PoPy").css("top",top+30+"px")
        				if(top < 90) {
        					$("#cColse").trigger("click")
        				}
        			})
        		});
    		})
    	})
    },
    regChecked(){
    	/**
    	 * 正则验证
    	 */
    	$(".firm-info .form-control").blur((e)=>{
    		let val = $(e.target).val();
    		let reg = $(e.target).attr("reg")
    		if(reg === 'null' || !reg) {
    			return;
    		}else {
    			
    			if(!eval("("+reg+")").test(val)){
    				$(e.target).siblings(".errorInfo").show();
    				$(e.target).val("")
    				$(e.target).addClass("active")
    			
    			}else {
    			
    				$(e.target).siblings(".errorInfo").hide();
    				$(e.target).removeClass("active")
    			}
    		}
    	})
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
        	
        	$table.bootstrapTable({
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
    			smartDisplay:true,
    			locales:'zh-CN',
        	});
        	
        	
        	function columns(tempI,tempId){
        		
        		let arr = []
        		contents.forEach((ele,index)=>{
        			if(ele.temp_name !== '操作'){
        				arr.push({
        					title:ele.temp_name,
        					field: ele.column_name,
        					width:(1/contents.length)*100+'%'
        				})
        				
        			}else {
        				arr.push({
        					title:ele.temp_name,
        					field: 'operate',
        					width:1/contents.length,
        					events: {
            					"click .edit":(e,value,row,index)=>{
            						_this.isAdd = false
            						_this.rowId = row.id
            						//回显
            						let formArr = Array.from($("#modal"+tempId).find(".form-inline"))
            						formArr.forEach((item,index)=>{
            							let id = $(item).children("label").siblings().attr("id");
            							let anotherIdArr = id.split("_")
            							anotherIdArr.pop();
            							let anotherId = anotherIdArr.join('_')
            							if($("#"+id).is('select')) {
            								//如果是select
            								$("#"+id).find("option[text='"+row[anotherId]+"']").attr("selected",true);
            							}else {
            								
            								$("#"+id).val(row[anotherId])
            							}
            						})
            					},
            					"click .delete":(e,value,row,index)=>{
            						e.stopPropagation();
            						$('.isDelete').removeClass("deleteShow")
            						$(e.target).children(".isDelete").toggleClass("deleteShow")
            						$(document).click(function(){
            							$('.isDelete').removeClass("deleteShow")
            						})
            						$(e.target).children(".isDelete").click(function(e){
            							e.stopPropagation();
            						})
            						
            						$(".popCancel").click(function(){
            							$('.isDelete').removeClass("deleteShow")
            						})
            						$(".popEnter").on('click', function(){
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
    				modalBody += ` <div class="form-inline justify-content-center my-3">
									<label for="" class="control-label" >${ele.temp_name}：</label>
									<input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
	    						</div>`
    			}
    			switch(ele.field_type) {
    				case 'date':
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-date">
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
    				case 'select':
    					if(!ele.get_source) {return}
    					let url = BASE_PATH + 'credit/front/ReportGetData/' + ele.get_source
    					ele.get_source = ele.get_source.replace(new RegExp(/&/g),"$")
    					_this.selectInfoObj[ele.get_source] = ele.column_name
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
    				case 'file':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
						                    <label for="" class="control-label">${ele.temp_name}：</label>
						                    <button type="button" class="form-control" id="modal_logo_icon">
						                        <span style="display:block;height:1.5rem">${ele.place_hold}</span>
						                        <input type="file" class="file-input" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
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
    		this.formatBtnArr.push(`<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#modal${item}">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="${BASE_PATH}static/credit/imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>`)
    		modalHtml += `<div class="modal fade" id="modal${item}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered" role="document">
					        <div class="modal-content">
					            <div class="modal-header">
					              	${titles[index].temp_name}
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
    	})
    	
    	$("#container").append(modalHtml)
    },
    bindFormData(){
    	/**
    	 * 绑定表单数据
    	 */
    	let titles = this.formTitle;
    	let formIndex = this.formIndex;
    	console.log(titles,formIndex)
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
			 let temp;
			 $.ajax({
	    			url,
	    			type:'post',
	    			async:false,
	    			data:paramObj,
	    			success:(data)=>{
	    				temp = data
	    			}
	    			
	    		})
			 let arr = Array.from($("#title"+item))
			 arr.forEach((item,index)=>{
				 if($(item).siblings(".radio-con").length !== 0) {
					 //radio类型绑数
					 if(temp.rows.length === 0){return}
					 let obid = temp.rows[0].id;
					 $(item).siblings(".radio-con").find(".radio-box").find("input").attr("entityid",obid)
					 let overall_rating =  temp.rows[0].overall_rating;
					 let name = $(item).siblings(".radio-con").find(".radio-box").find("input").attr("name")
					 
					 $("input:radio[name="+name+"][value="+overall_rating+"]").attr("checked",true);  
					 return
				 }
				 let formArr = Array.from($(item).siblings().find(".form-control"))
				 if(temp.rows.length === 0){return}
				 formArr.forEach((item,index)=>{
					 let obj = temp.rows[0];
    				let id = $(item).attr("id");
    				let anotherIdArr = id.split("_")
    				anotherIdArr.pop();
    				let anotherId = anotherIdArr.join('_')
    				if($(item).is('select')){
    					//如果是select
    					$("#"+id).find("option[value='"+obj[anotherId]+"']").attr("selected",true);
    				}else {
    					$("#"+id).val(obj[anotherId])
    				}
				 })
			 })
    		
    	})
    	
    },
    tabChange(){
        /**tab切换事件 */
    	$(".tab-bar li:eq(0) a").addClass("tab-active")
        $(".tab-bar li").click((e)=>{
            $(e.target).addClass("tab-active").parents("li").siblings().children('a').removeClass("tab-active")

            /* 解决锚链接的偏移问题*/
            $("#container ").css('height',"calc(100% - 5.6rem)");
            $(".main ").css('marginBottom',"-.6rem");
        })
    },
    initContent(){
        /**初始化内容 */
    	this.idArr = []    //存放table类型模块对应的index
    	this.formIndex = [] //存放form类型模块对应的index
    	this.contentsArr = [] //存放table类型模块的contents
    	this.title = [] //存放table类型模块的title
    	this.formTitle = [] //存放form类型模块的title
    	this.selectInfoObj = {} //存放选择框信息传给后台
    	this.saveStatusUrl = ''
		this.submitStatusUrl = ''
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
    	let reportType = JSON.parse(row).report_type
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id,reportType},
        	success:(data)=>{
                setTimeout(()=>{
                	_this.initModal();
                	_this.dateInit();
                	_this.addressInit();
                	_this.regChecked();
                	_this.initTable();
                	_this.bindFormData();
                	_this.tabChange();
                	_this.modalClean();
                	_this.bottomBtnEvent();
            	    Public.tabFixed(".tab-bar",".main",120,90)
            	    
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
                header.forEach((item,index)=>{
                	if(item.node_level === '1') {
                		headerHtml += `<div class="order-num main-title">${item.temp_name}：<span id="num"></span></div>`
                	}else {
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
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	let smallModileType = item.smallModileType
                	if(smallModileType !== '-2' && smallModileType !== '5' && item.title.temp_name !== null && item.title.temp_name !== "") {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3"><a class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                	}
                	let btnText = item.title.place_hold;
                	let formArr = item.contents; 
                	//模块的类型
                	
                	switch(smallModileType) {
                		case '0':
                			//表单类型
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ind = index
                			let rowNum = 0;//代表独占一行的input数量
		                	formArr.forEach((item,index)=>{
		                		
		                				let formGroup = ''
		                        		//判断input的类型
		                        		let field_type = item.field_type
		                        		if(!field_type) {
		                        			formGroup += `<div class="form-group">
		        						            		<label for="" class="mb-2">${item.temp_name}</label>
		        						            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'text':
		                        					formGroup += `<div class="form-group">
	        						            		<label for="" class="mb-2">${item.temp_name}</label>
	        						            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
	        						            		<p class="errorInfo">${item.error_msg}</p>
	        					            		</div>`
	                        						break;
		                        				case 'number':
			                    						formGroup += `<div class="form-group">
					                        							<label for="" class="mb-2">${item.temp_name}</label>
					                        							<input type="number" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
					                        							<p class="errorInfo">${item.error_msg}</p>
				                        							</div>`
		                        					
		                        					break;
		                        				case 'date':
		                        					formGroup += `<div class="form-group date-form">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
		                        					break;
		                        				case 'date_scope':
		                        					formGroup += `<div class="form-group date-scope-form">
									            		<label for="" class="mb-2">${item.temp_name}</label>
									            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
									            		<p class="errorInfo">${item.error_msg}</p>
								            		</div>`
		                        					break;
							            		case 'address':
							            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <input type="text" class="form-control"  style="width: 100%" name=${item.column_name} id="${item.column_name}_${ind}">
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
										            					<select name=${item.column_name} id="${item.column_name}_${ind}" class="form-control">
										            						${data.selectStr}
										            					</select>
							            							</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'textarea':
							            			formGroup += `  <div class="form-group"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <textarea class="form-control"  style="width: 100%;height: 6rem" name=${item.column_name} id="${item.column_name}_${ind}"></textarea>
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
		                        		
		                        		if(((index+1 - rowNum)%3 === 0 && index !==0) || formArr[index]['field_type'] === 'address' || (formArr[index+1]&&formArr[index+1]['field_type'] === 'textarea')){
		                        			contentHtml += `</div>`    
		                        		}
		                        		
		                	}) 
		                	contentHtml += `</div>`   
		                	break;
                		case '1':
                			//table类型
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                				</div>`
                		
                			break;
                		case '2':
                			//附件类型
                			contentHtml += ` <div class="order-detail mb-4 order-content d-flex flex-wrap mx-4 justify-content-start">
					                			 <div class="uploadFile mt-3 mr-3 ml-3">
					                               <div class="over-box">
					                                   <img src="/static/credit/imgs/order/fujian.png" class="m-auto"/>
					                                   <p class="mt-2">暂无附件</p>
					                               </div>
					                           </div>
				                			</div>`
                			let url = item.title.get_source;
                			url = BASE_PATH + url;
                			$.ajax({
                				url,
                				type:'post',
                				data:{
                					orderId:_this.rows.id
                				},
                				success:(data)=>{
                					if(data.statusCode === 1) {
                						let files = data.files;
                						
                				   	       
            				   	        if(data.files.length === 0){
            				   	        	
            				   	        	return
            				   	        }
            				   	        $(".order-detail").html("");
//            				        	$(".uploadFile:not(.upload-over)").show()
            				   	        for (var i = 0;i<files.length; i++){
            				   	        	let filetype = files[i].ext.toLowerCase()
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
            				   	        	let filename = data.files[i].originalname
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
            				   	        	fileArr += '<div class="uploadFile mt-3 mr-4 mb-5 upload-over" fileId="'+data.files[i].id+'" url="'+data.files[i].view_url+'" style="cursor:pointer">'+
            				   	        				'<div class="over-box">'+
            				   		        				'<img src="'+fileicon+'" class="m-auto"/>'+
            				   		        				 '<p class="filename" title="'+all_name+'">'+filename+'</p>'+
            				   	        				 '</div>'+
            				   	        				 '</div>'
            				   	        
            							  $(".order-detail").append(fileArr)	
            				   	           $(".upload-over").click(function(e){
            				   	        	   if($(e.target).parent().attr("class") === 'close') {
            				   	        		   return
            				   	        	   }
            				   	        	   window.open($(this).attr("url"))
            				   	        	   
            				   	           })
            				   	        }
	            					}
                				}
                			})
                			break;
                		case '5':
                			//固定底部的按钮组
                			item.title.column_name === 'save'?_this.saveStatusUrl = item.title.alter_source:_this.submitStatusUrl = item.title.alter_source
                			let className = item.title.column_name === 'save'?'btn btn-default ml-4':'btn btn-primary ml-4'
                			bottomBtn += `<button id=${item.title.column_name} class="${className}">${item.title.temp_name}</button>`
                			break;
                		case '6':
                			//信用等级
                			let inputObj = item.contents[0]
                			_this.formTitle.push(inputObj)
                			_this.formIndex.push(index)
                			contentHtml += `<div class="form-group form-inline p-4 mx-3">
					                          <label >${inputObj.temp_name}</label>
					                          <input type="text" name="" id=${inputObj.column_name} name=${inputObj.column_name} class="form-control mx-3" placeholder="" aria-describedby="helpId" style="border-color:blue">
					                          <span id="helpId" class="text-muted">${inputObj.suffix}</span>
					                        </div>`
                			
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
                			//表格下面的无标题多行输入框 保存回显同表单模块
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ot_item = item
                			item.contents.forEach((item,index)=>{
                				if(ot_item.title.temp_name === '' || ot_item.title.temp_name === null) {
                					contentHtml += ` <div class="form-group mb-3 p-4" style="background:#fff;margin-top:-2rem">
                						<label for="" class="thead-label">${item.temp_name}</label>
                						<textarea name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
            						</div>`
                				}else{
                					contentHtml += ` <div class="form-group mb-3 p-4" style="background:#fff">
	                						<label for="" class="thead-label">${item.temp_name}</label>
	                						<textarea name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
                						</div>`
                				}
                			})
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
            			default:
            				break;
            		}
                	contentHtml += `</div>`
                })
                
                $(".table-content").html(contentHtml)
                $(".position-fixed").html(bottomBtn)
            }
        })
    },
    modalClean(){
    	/**上传图标 */
        $(".file-input").change(function(){
            let filename = $(this).val().replace("C:\\fakepath\\","");
            let num = filename.split(".").length;
            let filetype = filename.split(".")[num-1];
            if(filetype === 'jpg' || filetype === 'png' || filetype === 'pdf') {
                $("#modal_logo_icon span").text(filename)
            }else {
                Public.message("info","上传文件格式错误！")
            }
            console.log(filetype)
        })
    	
    	this.idArr.forEach((item,index)=>{
    		//点击新增一条清空模态框中内容
    		$("#addBtn"+item).click(()=>{
    			this.isAdd = true
    			$("#modal"+item+" input").val("");
    			$("#modal"+item+" input").siblings("span").html("");
		    	$("#modal"+item+" textarea").val("");
		    	 $("#modal"+item+" select").find("option:selected").attr("selected", false);
			    $("#modal"+item+" select").find("option").first().attr("selected", true);
    
    		})
    		//点击模态框保存按钮，新增一条数据
    		$("#modal_save"+item).unbind().click(()=>{
    			let dataJson = []
    			let dataJsonObj = {}
    			let formArr = Array.from($("#modal"+item).find(".form-inline"))
				formArr.forEach((item,index)=>{
					let id = $(item).children("label").siblings().attr("id");
					if($("#"+id).is("button")) {
						let name = $('#'+$('#'+id).children("input").attr("id")).attr("name")
						let val = $('#'+id).children("span").html()
						dataJsonObj[name] = val
					}
					
					//调用form格式化数据函数
					let tempObj = this.getFormData($('#'+id));
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
    			 dataJson.push(dataJsonObj)
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
            				this.refreshTable($("#table"+item));
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
    	$(ele).bootstrapTable("refresh")
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
    	console.log(formTitles,formIndex)
    	let _this = this
    	formIndex.forEach((item,index)=>{
    		let alterSource = formTitles[index]["alter_source"];
    		if(alterSource === null || alterSource === ''){return}
    		let url = BASE_PATH +'credit/front/ReportGetData/'+ alterSource.split("*")[0] ;
    		let dataJson = []
    		let dataJsonObj = {} 
    		if(alterSource.split("*")[1]) {
    			
    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
    			tempParam.forEach((item,index)=>{
    				dataJsonObj[item] = this.rows[item]
    			})
    		}
    		let this_item = item
			 //点击保存按钮
    		$(".position-fixed").unbind().on("click","#save",(e)=>{
    			$("#save").addClass("disabled")
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 console.log(url,this_item)
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }
    				 
    				 let formArr = Array.from($(item).siblings().find(".form-control"))
    				 formArr.forEach((item,index)=>{
        				let id = $(item).attr("id");
        				let anotherIdArr = id.split("_")
        				anotherIdArr.pop();
        				let anotherId = anotherIdArr.join('_')
        				let tempObj = _this.getFormData($('#'+id))
        				for(let i in tempObj){
    						if(tempObj.hasOwnProperty(i))
    						dataJsonObj[i] = tempObj[i]
    					}
    				 })
    				 
    			 })
    			 dataJson.push(dataJsonObj)
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("#save").removeClass("disabled")
    					 if(data.statusCode === 1) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.saveStatusUrl + `&model.id=${_this.rows["id"]}`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 Public.goToInfoImportPage();
    									 
    								 }else {
    									 Public.message("error",data.message)
    								 }
    							 }
    						 })
    					 }else {
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    			 //点击提交按钮
    		$(".position-fixed").unbind().on("click","#commit",(e)=>{
    			$("#commit").addClass("disabled")
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 console.log(url,this_item)
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }
    				 let formArr = Array.from($(item).siblings().find(".form-control"))
    				 formArr.forEach((item,index)=>{
        				let id = $(item).attr("id");
        				let anotherIdArr = id.split("_")
        				anotherIdArr.pop();
        				let anotherId = anotherIdArr.join('_')
        				let tempObj = _this.getFormData($('#'+id))
        				for(let i in tempObj){
    						if(tempObj.hasOwnProperty(i))
    						dataJsonObj[i] = tempObj[i]
    					}
    				 })
    				 
    			 })
    			 dataJson.push(dataJsonObj)
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("#commit").removeClass("disabled")
    					 if(data.statusCode === 1) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.submitStatusUrl + `&model.id=${_this.rows["id"]}`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 Public.goToInfoImportPage();
    									 
    								 }else {
    									 Public.message("error",data.message)
    								 }
    							 }
    						 })
    					 }else {
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    	})
    }
}

ReportConfig.init();
