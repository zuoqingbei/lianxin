let Login = {
    init(){
        /** *初始化函数 */
    	$(".error-tip").hide();
        this.login();
    },
    login(){
        /** 是否为空验证 */
        $(".btn-login").click(function(){
            let user = $("#username").val();
            let psw = $("#psw").val();
            if(!user) {
                $(".userTip").show()
                $(".username span").addClass("border-error")
                $("#username").addClass("border-error")
                $(".psdTip").hide()
                $(".password span").removeClass("border-error")
                $("#psw").removeClass("border-error")
            }else if(user && !psw) {
                $(".userTip").hide()
                $(".username span").removeClass("border-error")
                $("#username").removeClass("border-error")
                $(".psdTip").show()
                $(".password span").addClass("border-error")
                $("#psw").addClass("border-error")
            }else if(user && psw) {
                $(".userTip").hide()
                $(".username span").removeClass("border-error")
                $("#username").removeClass("border-error")
                $(".psdTip").hide()
                $(".password span").removeClass("border-error")
                $("#psw").removeClass("border-error")

                /** 调用登录接口 */
                	console.log($("#meForm").serialize());
                	$.ajax({
                		type:"post",
                    	url:"/credit/front/usercenter/login",
                    	data:$("#meForm").serialize(),
                    	contentType:"application/x-www-form-urlencoded",// (可以)
                    	dataType:"json",
                    	success:function(data){
                    		console.log(data+"1313");
                    		console.log(data);
                    		if(data.statusCode===0){
                    			$(".error-tip").show();
                    		}else{
                    			$(".error-tip").hide();
                    			window.location.href = "/credit/front/home/menu";
                    			$("#main_content").load("/credit/front/home");
								  sessionStorage.setItem('pageUrl', '/credit/front/home');
								  sessionStorage.setItem('menuId',$(".left-nav ul li:first").attr("id"));
 								  $(".leftNav").find("li").removeClass("active");
 								  $("#work_console").addClass("active");
 								                      			
                    		}
                    	}
                    });
            }
            


          
        })
    },
  
}


Login.init();