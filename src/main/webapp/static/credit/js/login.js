let Login = {
    init(){
        /** *初始化函数 */
    	$(".error-tip").hide();
        this.login();

    },
    login(){
        /** 是否为空验证 GVerify*/
        var verifyCode = new GVerify("conVer");
        let tis=this
        $(".btn-login").click(
            function () {
                var res = verifyCode.validate(document.getElementById("veri").value);
                // if(res){
                //     alert("验证正确");
                // }else{
                //     alert("验证码错误");
                // }
                tis.logining(res)
            }
        )
        $("#username").keydown((e)=>{
        	if(e.keyCode === 13) {
        		this.logining()
        	}
        })
        $("#psw").keydown((e)=>{
        	if(e.keyCode === 13) {
        		this.logining()
        	}
        });
        $("#veri").keydown((e)=>{
            if(e.keyCode === 13) {
            	var res = verifyCode.validate(document.getElementById("veri").value);
                this.logining(res)
            }
        })
    },
    logining(res){
        let user = $("#username").val();
        let psw = $("#psw").val();
        if(!res){
            $(".error-tip2").show();
            return
        }else{
            $(".error-tip2").hide();
        }
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
//                		alert(JSON.stringify(data.roleIds));
                		/*
                		 * 1  管理员
							2  报告员
							3  客服
							4  质检员
							5  分析员
							6  翻译员
                		 */
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
                            sessionStorage.setItem('roleIds',JSON.stringify(data.roleIds));
                        }
                	}
                });
        }
        


      
    }
}


Login.init();