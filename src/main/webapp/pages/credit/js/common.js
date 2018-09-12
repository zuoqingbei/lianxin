
/**公共js部分 */

let Public = {
    init(){
        this.importMenu();
       
    }, 
    importMenu(){
        /**
         * 导入菜单组件
         */
        let _this = this
        $('header').load("./menu.html .head-row",function(){
            _this.logoutEvent();
        })

        $('.nav-row').load("./menu.html .nav-box" ,function(){
            _this.menuEvent()
        })
    },
    menuEvent(){
        /**
         * 菜单事件
         */
        var $leftNav = $(".left-nav");
        // 含有子菜单的a添加样式
        $leftNav.find("a").each(function (index, item) {
            if ($(this).next().length > 0) {
                $(this).parent().addClass("hasChild");
            }
        });
        //菜单点击
        $leftNav.find("li").click(function () {
            if ($(this).hasClass("hasChild")) {
                if ($(this).hasClass("show")) {
                    $(this).children("ul").slideUp(150, function () {
                        $(this).parent().toggleClass("show");
                    });
                } else {
                    $(this).children("ul").slideDown(150, function () {
                        $(this).parent().toggleClass("show");
                    });
                }
            } else {
                $leftNav.find("li").removeClass("active");
                $(this).addClass("active")
            }
            return false;
        });
    },
    logoutEvent(){
      
        $(".user-content").click(function(){
           $(".logout").toggleClass("logout-show animated slideInDown")
        })

        $(".logout").click(function(){
            /**发送ajax请求 */
            $(".logout").toggleClass("logout-show")

        })
    }

}

$(function(){

    Public.init();
})


