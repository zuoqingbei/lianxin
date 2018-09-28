let Login = {
    init(){
        /***初始化函数 */
        this.login();
    },
    login(){
        /**是否为空验证 */
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
                return false;
            }else if(user && !psw) {
                $(".userTip").hide()
                $(".username span").removeClass("border-error")
                $("#username").removeClass("border-error")
                $(".psdTip").show()
                $(".password span").addClass("border-error")
                $("#psw").addClass("border-error")
                return false;
            }else if(user && psw) {
                $(".userTip").hide()
                $(".username span").removeClass("border-error")
                $("#username").removeClass("border-error")
                $(".psdTip").hide()
                $(".password span").removeClass("border-error")
                $("#psw").removeClass("border-error")

                /**调用登录接口 */

               // $(".error-tip").show();

                /*$.ajax({
                	url:"/credit/front/usercenter/login",
                	data:$("meForm").serialize(),
                	type:"json",
                	success:function(data){
                		
                	}
                });*/
            }
            


          
        })
    },
  
}


Login.init();