
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
    	let dateArr = Array.from($('.date-form input'))
    	dateArr.forEach((item,index)=>{
    		laydate.render({
    			elem: item,
    			format: 'yyyy年MM月dd日'
    		});
    	})
    },
    addressInit(){
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
    initContent(){
        /**初始化内容 */
    	
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
		        						            		<input type="text" class="form-control" id="" placeholder="" name=${item.column_name}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'number':
		                        					formGroup += `<div class="form-group">
		        								            		<label for="" class="mb-2">${item.temp_name}</label>
		        								            		<input type="number" class="form-control" id="" placeholder="" name=${item.column_name}>
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
							            			formGroup += `<div class="form-group address-form">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input type="text" class="form-control" id="" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
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
		                        		
		                        		if((index)%3 === 0){
		                        			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
		                        		}
		                        		contentHtml += formGroup;
		                        		
		                        		if(((index+1)%3 === 0 && index !==0) || !formArr[index+1]){
		                        			contentHtml += `</div>`    
		                        		}
		                	}) 
		                	break
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
