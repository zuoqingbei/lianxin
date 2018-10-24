
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
      //菜单展开收缩
        this.isSQ = false
        $("#handleMenu").click(()=>{
            if(!this.isSQ){
                this.isSQ = !this.isSQ
                //没被收起
                $(".fixed-table-header>.table").css("width","99.9%")
                $(".head-logo-box").removeClass("col-xs-2 col-sm-2 col-md-2 col-lg-2")
                $("#main_content").css({"width":"99.9%"})
                $(".nav-row").css({"width":"0.1%"})
                $(".head-logo-box").css({"width":"0.1%"})
                $(".nav-row").removeClass("col-md-2")
                $("#main_content").removeClass("col-md-10")
                $(".border-div").css({"background":"url(/static/credit/imgs/index/right_arrow.png) no-repeat","backgroundSize":"100% 100%"})
            }else {
                this.isSQ = !this.isSQ
                //被收起
                $(".nav-row").css("width","16.66%")
                $("#main_content").css("width","83.33%")
                $(".head-logo-box").css("width","16.66%")
                $(".border-div").css({"background":"url(/static/credit/imgs/index/left_arrow.png) no-repeat","backgroundSize":"100% 100%"})
                
            }
        })
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
                let href = $(this).find("a").attr("href");/*.replace("/menu","")*/;
//                console.log(id,href)
               
                sessionStorage.setItem('menuId',id);
                 $("#main_content").load(href?href+"?from=commonjs":'/credit/front/orderProcess/showUnderdevelopment',function(response){
                	 //console.log(response)
                	 	if("nologin"==response){
	                	 $("#main_content").html("")
                	 		window.location.href="/credit/front/usercenter/showLogin";
                	 		return;
                	 	}else{
                	 		 _this.initReset();
                             _this.gotop()
                             sessionStorage.setItem('pageUrl',href)
                	 	}
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
    	console.log("开始订单详情页跳转")
    	//跳转订单详情
    	$("#main_content").load(BASE_PATH+'credit/front/home/orderInfo?id='+id)
    },
    createOrder(){
    	 $("#main_content").load(BASE_PATH+'credit/front/home/createOrder')
    },
    goList(){
    	 $("#main_content").load(BASE_PATH+'credit/front/home')
    },
    goAll(){
    	 $("#main_content").load(BASE_PATH+'credit/front/home/allOrder')
    },
    goToCreateOrder(){
        /**跳转新建订单页面 */
        $("#main_content").load(BASE_PATH+'credit/front/home/createOrder');
    },
    goToBasicInfoWrite(e){
        /**跳转基本信息填报 */
        $("#main_content").load(BASE_PATH+'credit/front/orderProcess/showReportedBasicInfo');
        localStorage.setItem("row",JSON.stringify(e));
    },
    tabFixed(fixedEle,scrollEle,min,max){
        /**
         * 滚动之后固定tab函数
         * fixedEle:固定的dom   string
         * scrollEle:滚动的dom  string
         * min:超出后固定   number
         * max:小于取消固定 number
         */
        $(scrollEle).scroll(()=>{
            let top =  $(scrollEle).scrollTop();
            if(top > min) {
                $(fixedEle).addClass("tab-fixed")
            }else if(top < max) {
                $(fixedEle).removeClass("tab-fixed")
            }
        })
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

