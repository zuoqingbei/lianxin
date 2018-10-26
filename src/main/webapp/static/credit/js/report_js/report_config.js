
let ReportConfig = {
    init(){
        this.initHeader();
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
    initHeader(){
        /**初始化头部 */
       
    },
    initContent(){
        /**初始化内容 */
        $.get("/credit/front/getmodule/list?id=1",(data)=>{
            console.log(data)
            let contentHtml = '' 
            data.forEach((item,index) => {
                if(item.contentType === 'form'){
                    /**
                     * 如果这是表单快
                     */
                    let title = item.title
                    contentHtml += `<div class="l-title"></div>`
                }else if(item.contentType === 'table'){

                }
                
            });
            console.log(contentHtml)
            $(".table-content").html(contentHtml)
        })
    }
}

ReportConfig.init();
