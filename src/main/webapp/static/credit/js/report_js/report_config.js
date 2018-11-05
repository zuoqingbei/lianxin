
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
    initContent(){
        /**初始化内容 */
    	
    	let row = localStorage.getItem("row");
    	let id = JSON.parse(row).id;
        $.ajax({
        	type:"get",
        	url:"/credit/front/getmodule/list",
        	data:{id},
        	success:(data)=>{
                console.log(data)
                let modules = data.modules;    
                let contentHtml = '' 
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	contentHtml +=  `<div class="bg-f"><div class="l-title">${item.title}</div>`
                	let formArr = item.contents; 
                	formArr.forEach((item,index)=>{
                		//模块的类型
                		let smallModileType = item.smallModileType
                		switch(smallModileType) {
                			case '0':
                				let formGroup = ''
                        		//判断input的类型
                        		let field_type = item.field_type
                        		if(!field_type) {
                        			formGroup += `<div class="form-group">
        						            		<label for="" class="mb-2">${item.temp_name}</label>
        						            		<input type="text" class="form-control" id="" placeholder="">
        						            		<p class="errorInfo">${item.error_msg}</p>
        					            		</div>`
                        		}else {
                        			
                        			switch(field_type) {
                        				case 'number':
                        					formGroup += `<div class="form-group">
        								            		<label for="" class="mb-2">${item.temp_name}</label>
        								            		<input type="number" class="form-control" id="" placeholder="">
        								            		<p class="errorInfo">${item.error_msg}</p>
        							            		</div>`
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
                				
                				
                				break
                			default:
                				break;
                		}
                			
                		
                		
                		
    				            		
    				  
                	}) 
                	contentHtml += `</div>`
                })
                
                $(".table-content").html(contentHtml)
            }
        })
    }
}

ReportConfig.init();
