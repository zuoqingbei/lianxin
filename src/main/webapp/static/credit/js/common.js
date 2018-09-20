
/**公共js部分 */

let Public = {
    init(){
        this.importMenu();
        this.initReset();
    }, 
    importMenu(){
        /**
         * 导入菜单组件
         */
        let _this = this
       
        if(location.pathname.indexOf('order_manage') !== -1){
            $('header').load("/credit/front/common/menu .head-row",function(){
                _this.logoutEvent();
            })
    
            $('.nav-row').load("/credit/front/common/menu .nav-box" ,function(){
                _this.menuEvent()
            })
        }else {
            $('header').load("/credit/front/common/menu .head-row",function(){
                _this.logoutEvent();
            })
    
            $('.nav-row').load("/credit/front/common/menu .nav-box" ,function(){
                _this.menuEvent()
            })

        }
    },
    initReset(){
    	/**重置form表单**/
    	const $btnReset = $('#btn_reset');
    	$btnReset.on('click',function(){
    		$("#formSearch input.search-input").val("");
    		$("#formSearch select.search-input").val("");
    	});
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

           
            // return false;
        });
    },
    logoutEvent(){
      
        $(".user-content").click(function(){
           $(".logout").toggleClass("logout-show")
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


