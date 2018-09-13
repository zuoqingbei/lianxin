
let Reported = {
    init(){
        /**函数初始化 */
        this.dateForm();
    },
    dateForm(){
        /**日期控件 */
        laydate.render({
            elem: '#qy_create'
        });
        laydate.render({
            elem: '#qy_date'
        });
    }
}

Reported.init();