let Orderind = {
    init(){
        /**初始化函数 */
        /*流程进度 索引是从0开始的*/
 //       this.step(${size});
        /*头部tab切换*/
        this.tabActive();
    },
    step(index){
        /*如果圆圈的索引 ==index 高亮显示  .circle_active*/
        /*如果圆圈的索引 <index 蓝色 span_active*/
        /*如果圆圈的索引 >index 灰色 span_circle*/
        /*如果横条的索引 <=index-1 蓝色*/
        /*如果横条的索引 >index-1  灰色*/
        /*如果字体的索引 ==index  黑色*/
        /*如果字体的索引 ！=index 灰色*/
        let circle_arr=$(".bar-span-box .span_circle");  //圆圈
        let bar_arr=$(".bar-span-box .span_bar");    //横条
        var world_arr=$(".bar_box>ul>li");     //字体
        let barIndex=index-1;
        for(var i=0;i<circle_arr.length;i++){
            $(circle_arr[i]).removeClass("circle_active").removeClass("span_active").removeClass("span_circle");
            $(bar_arr[i]).removeClass("bar_active").removeClass("span_circle");
            $(world_arr[i]).removeClass("world_active");
            /*判断圆圈*/
            if(i==index){
                $(circle_arr[i]).addClass("circle_active");
                /*判断字体*/
                $(world_arr[i]).children("span").addClass("world_active");
            }else if(i<index){
                $(circle_arr[i]).addClass("span_active");
            }else if(i>index){
                $(circle_arr[i]).addClass("span_circle");
            }
            /*判断横条*/
            if(barIndex>=0){
                if(i<=barIndex){
                    $(bar_arr[i]).addClass("bar_active")
                }else if(i>barIndex){
                    $(bar_arr[i]).addClass("span_circle");
                }
            }
        }
    },
    tabActive(){
        $(".tab_bar li").click(function () {
            $(this).addClass('tab-active').siblings('li').removeClass('tab-active');
            $(this).find("a").addClass('tab-a-active');
            $(this).siblings('li').find('a').removeClass('tab-a-active');
           /* 解决锚链接的偏移问题*/
            $("#container ").css('height',"calc(100% - 5.5rem)");
            /*右侧主题部分*/
            $(".main ").css('height',"calc(100% + 5.5rem)");
        })
    }

}

Orderind.init();