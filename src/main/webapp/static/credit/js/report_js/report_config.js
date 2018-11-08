
let ReportConfig = {
    init(){
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
    	dateArr.forEach((item,index)=>{
    		laydate.render({
    			elem: item,
    			format: 'yyyy年MM月dd日'
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
    },
    regChecked(){
    	/**
    	 * 正则验证
    	 */
    	$(".form-control").blur((e)=>{
    		let val = $(e.target).val();
    		let reg = $(e.target).attr("reg")
    		console.log(typeof reg)
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
        	
        	function columns(){
        		let arr = []
        		contents.forEach((ele,index)=>{
        			if(item.temp_name !== '操作'){
        				arr.push({
        					title:ele.temp_name,
        					field: ele.column_name,
        					width:1/contents.length
        				})
        				
        			}else {
        				arr.push({
        					title:ele.temp_name,
        					field: ele.column_name,
        					width:1/contents.length,
        					events: {
            					"click .edit":(e,value,row,index)=>{
            						_this.recordIndex = index
            						//回显
            						let {date,change_item,change_font,change_back} = row;
            						$("#modal_record_date").val(date)
            						$("#modal_change_items").val(change_item)
            						$("#modal_change_font").val(change_font)
            						$("#modal_change_back").val(change_back)
            						
            						
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
            							
            						})
            						
            					}
            				},
            				formatter: _this.formatBtnArr[item]
        				})
        			}
        		})
        		return arr
        	}
        	let url = contents[index].get_source;
        	console.log(url)
        	$table.bootstrapTable({
        		columns: columns(),
    			// url : 'firmSoftTable.action', // 请求后台的URL（*）
    			// method : 'post', // 请求方式（*）post/get
    			pagination: false, //分页
    			smartDisplay:false,
    			iconsPrefix:'fa',
    			locales:'zh-CN',
        	});
        	
        })
    },
    initModal(){
    	/**
    	 * 初始化模态窗
    	 */
    	let modalHtml = ''
    	let ids = this.idArr
    	let contents = this.contentsArr;
    	let titles = this.title
    	//格式化按钮数组
    	this.formatBtnArr = []
    	ids.forEach((item,index)=>{
    		let modalBody = ''
    		contents[index].forEach((ele,index)=>{
    			
    			if(ele.temp_name === '操作') {
    				return;
    			}
    			
    			if(!ele.field_type) {
    				modalBody += ` <div class="form-inline justify-content-center my-3">
									<label for="" class="control-label" >${ele.temp_name}：</label>
									<input type="text" class="form-control" id="" >
	    						</div>`
    			}
    			switch(ele.field_type) {
    				case 'date':
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-date">
						                    <label for="" class="control-label" >${ele.temp_name}：</label>
						                    <input type="text" class="form-control" id="" >
						                </div>`
    					break;
    				case 'number':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
				    						<label for="" class="control-label" >${ele.temp_name}：</label>
				    						<input type="number" class="form-control" id="" >
    							</div>`
    					break;
    				case 'select':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
				    					  <label for="" class="control-label" >${ele.temp_name}：</label>
				    					  <select  class="form-control" id="">
						                    	
					                    </select>
    								</div>`
    					break;
    				
    				default:
    					break;
    			}
    			
    			
    		})
    		this.formatBtnArr.push('<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#modal${item}">编辑</a><a href="javascript:;"  class="delete">删除<div class="isDelete"><span class="popover-arrow"></span><div><img src="../imgs/index/info.png" />是否要删除此行？</div><div><button class="btn btn-default popCancel" id="popCancel">取消</button><button class="btn btn-primary popEnter" id="popEnter">确定</button></div></div></a></div>')
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
    tabChange(){
        /**tab切换事件 */
    	$(".tab-bar li:eq(0) a").addClass("tab-active")
        $(".tab-bar li").click((e)=>{
            $(e.target).addClass("tab-active").parents("li").siblings().children('a').removeClass("tab-active")

//            /* 解决锚链接的偏移问题*/
//            $("#container ").css('height',"calc(100% - 5.6rem)");
//            $(".main ").css('marginBottom',"-.6rem");
        })
    },
    initContent(){
    	
    	
        /**初始化内容 */
    	this.idArr = []    //存放table类型模块对应的index
    	this.contentsArr = [] //存放table类型模块的contents
    	this.title = [] //存放table类型模块的title
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
    	let reportType = JSON.parse(row).report_type
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id,reportType},
        	success:(data)=>{
                console.log(data)
                setTimeout(()=>{
                	_this.addressInit();
                	_this.initModal();
                	_this.dateInit();
                	_this.regChecked();
                	_this.initTable();
                	_this.tabChange();
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
                console.log(tempArr,headItem)
                headItem.forEach((item,index)=>{
                	$(item).siblings("span").html(tempArr[index])
                })
                /**
                 * tabFixed
                 */
                let tabFixed = data.tabFixed;
                let tabFixedHtml = ''
                tabFixed.forEach((item,index)=>{
                	tabFixedHtml += `<li class="tab-info"><a href="#info_anchor">${item.temp_name}</a></li>`
                })
                
                $(".tab-bar").html(tabFixedHtml)
                /**
                 * 内容模块部分
                 */
                let modules = data.modules;    
                let contentHtml = '' 
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	let smallModileType = item.smallModileType
                	if(smallModileType === '-2') {
                		return;
                	}
                	contentHtml +=  `<div class="bg-f pb-3 mb-3"><div class="l-title">${item.title.temp_name}</div>`
                	let btnText = item.title.place_hold;
                	let formArr = item.contents; 
                	//模块的类型
                	
                	switch(smallModileType) {
                		case '0':
                			//表单类型
		                	formArr.forEach((item,index)=>{
		                		
		                				let formGroup = ''
		                        		//判断input的类型
		                        		let field_type = item.field_type
		                        		if(!field_type) {
		                        			formGroup += `<div class="form-group">
		        						            		<label for="" class="mb-2">${item.temp_name}</label>
		        						            		<input type="text" class="form-control" id="" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'number':
			                    						formGroup += `<div class="form-group">
					                        							<label for="" class="mb-2">${item.temp_name}</label>
					                        							<input type="number" class="form-control" id="" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
					                        							<p class="errorInfo">${item.error_msg}</p>
				                        							</div>`
		                        					
		                        					break;
		                        				case 'date':
		                        					formGroup += `<div class="form-group date-form">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input type="text" class="form-control" id="" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
		                        					break;
							            		case 'address':
							            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <input type="text" class="form-control"  style="width: 100%" name=${item.column_name}>
									                                </div>`
							            			break;
							            		case 'select':
							            			let url = BASE_PATH + item.get_source
							            			$.ajax({
							            				type:'get',
							            				url,
							            				async:false,
							            				dataType:'json',
							            				success:(data)=>{
							            				formGroup += `<div class="form-group">
										            					<label for="" class="mb-2">${item.temp_name}</label>
										            					<select name=${item.column_name} id="" class="form-control">
										            						${data.selectStr}
										            					</select>
							            							</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'textarea':
							            			formGroup += `  <div class="form-group"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <textarea class="form-control"  style="width: 100%;height: 6rem" name=${item.column_name}></textarea>
									                                </div>`
									                break;
		        							    default :
		        							    	break;
		                        			}
		                        		}
		                        		
		                        		if((index)%3 === 0 || !formArr[index+1]){
		                        			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
		                        		}
		                        		contentHtml += formGroup;
		                        		
		                        		if(((index+1)%3 === 0 && index !==0) || !formArr[index+2]){
		                        			contentHtml += `</div>`    
		                        		}
		                        		
		                        	
		                	}) 
		                	break;
                		case '1':
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}">+ ${btnText}</button>
                				</div>`
                		
                			break; 
            			default:
            				break;
            		}
                	contentHtml += `</div>`
                })
                
                $(".table-content").html(contentHtml)
            }
        })
    }
}

ReportConfig.init();
