
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
    	console.log(this.idArr,this.contentsArr)
        let _this = this
        
        this.idArr.forEach((item,index)=>{
        	const $table = $("#table"+item);
        	let contents = this.contentsArr[index]
        	console.log($table,contents)
        	
        	function columns(){
        		let arr = []
        		contents.forEach((item,index)=>{
        			if(item.temp_name !== '操作'){
        				arr.push({
        					title:item.temp_name,
        					field: item.column_name,
        					width:1/contents.length
        				})
        				
        			}else {
        				arr.push({
        					title:item.temp_name,
        					field: item.column_name,
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
            							$("#tableRecord").bootstrapTable("remove",{
            								field:'id',
            								values:[row.id]
            							})
            							$('.isDelete').removeClass("deleteShow")
            						})
            						
            					}
            				},
            				formatter: _this.operateFormatterRecord
        				})
        			}
        		})
        		return arr
        	}
        	
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
    initContent(){
        /**初始化内容 */
    	this.idArr = []
    	this.contentsArr = []
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id},
        	success:(data)=>{
                console.log(data)
                setTimeout(()=>{
                	_this.addressInit();
                	_this.dateInit();
                	_this.regChecked();
                	_this.initTable();
                },0)
                let modules = data.modules;    
                let contentHtml = '' 
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	contentHtml +=  `<div class="bg-f"><div class="l-title">${item.title}</div>`
                	let formArr = item.contents; 
                	//模块的类型
                	let smallModileType = item.smallModileType
                	
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
							            			let url = BASE_PATH + item.data_source
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
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-5 " type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}">+ 新增信息</button>
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
