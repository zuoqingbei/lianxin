
/**公共js部分 */


let Public = {
    init(){
        this.menuEvent()
        this.logoutEvent()
      
    }, 
  
    initReset(){
    	/**重置form表单**/
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
        let _this = this
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
               
                let id = $(this).attr("id");
                let href = $(this).find("a").attr("href");
                sessionStorage.setItem('menuId',id)
                 $("#main_content").load(href?href:'/credit/front/home',function(){
                        _this.initReset();
                        _this.gotop()
                        sessionStorage.setItem('pageUrl',href)
                    })
               
            }

             return false;
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
    },
    goToOrderDetail(id){
    	//跳转订单详情
    	$("#main_content").load('/credit/front/home/orderInfo?id='+id)
    },
    createOrder(){
    	 $("#main_content").load('/credit/front/home/createOrder')
    },
    goList(){
    	 $("#main_content").load('/credit/front/home')
    },
    goToCreateOrder(){
        /**跳转新建订单页面 */
        $("#main_content").load('/credit/front/home/createOrder');
    },
    goToBasicInfoWrite(){
        /**跳转基本信息填报 */
        $("#main_content").load('./report_pages/reported_basic_info.html');
    },
    gotop(){
        console.log($(".main"))
        $(".main").scroll(function(){
            // 滚动条距离顶部的距离 大于 200px时
            if($(".main").scrollTop() >= 200){
                 $(".fixed-backup").fadeIn(500); // 开始淡入
            } else{
                 $(".fixed-backup").fadeOut(500); // 如果小于等于 200 淡出
            }
        })

        $('.fixed-backup').click(function(){
            $('.main').animate({scrollTop: 0},'normal');
        });
    },
    message(state,text){
        let txt = text || '获取数据失败！'
        if(state === 'info') {
            $(".info-tip").show();
            $(".info-text").html(txt)
            setTimeout(() => {
                $(".info-tip").hide()
            }, 2000);
        }else if(state === 'error'){
            $(".error-tip").show();
            $(".error-text").html(txt)
            setTimeout(() => {
                $(".error-tip").hide()
            }, 2000);
        }else if(state === 'success'){
            $(".success-tip").show();
            $(".success-text").html(txt)
            setTimeout(() => {
                $(".success-tip").hide()
            }, 2000);
        }
    }

}

$(function(){

    Public.init();
    window.onload = function(){
        let id = sessionStorage.getItem('menuId')?sessionStorage.getItem('menuId'):$(".left-nav ul").children("li").eq(0).attr("id")
        $("#main_content").load(sessionStorage.getItem('pageUrl')?sessionStorage.getItem('pageUrl'):'/credit/front/home')
        $(".leftNav").find("li").removeClass("active");
        $('#'+id).addClass("active");
        if($('#'+id).parents("li").hasClass("hasChild")) {
            $('#'+id).parents("li").toggleClass("show")
            $('#'+id).parents("ul").show(200)
           
        }	
    }
})

