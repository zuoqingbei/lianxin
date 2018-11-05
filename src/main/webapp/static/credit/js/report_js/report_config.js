
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
        $.get("/credit/front/getmodule/list?id="+id,(data)=>{
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
            		//判断input的类型
            		let field_type = item.field_type
            		
            		if((index)%3 === 0){
            			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
            		}
            		contentHtml += `<div class="form-group">
					            		<label for="" class="mb-2">${item.temp_name}</label>
					            		<input type="text" class="form-control" id="" placeholder="">
				            		</div>`
            		if(((index+1)%3 === 0 && index !==0) || !formArr[index+1]){
            			contentHtml += `</div>`    
            		}
				            		
				    console.log(index)
            	}) 
            	contentHtml += `</div>`
            })
            
            $(".table-content").html(contentHtml)
        })
    }
}

ReportConfig.init();
