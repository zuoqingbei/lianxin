let Password = {
    init(){
        this.checkedEmail()
        this.clickCodeNext()
        this.passwordChecked()
        this.passwordChecked2()
        this.passwordNext()
    },
    checkedEmail(){
        /**验证邮箱格式 */
        $("#btn_getCode").click(()=>{
            let email = $("#email").val()
            let reg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
            if(!reg.test(email)) {
                /**验证不通过 */
                $("#email").addClass("error-input")
                $(".error-email").show()
            }else {
                $("#email").removeClass("error-input")
                $(".error-email").hide()
                this.getMailCode(email);

            }

            return false;
        })
    },
    clickCodeNext(){
        /**验证码页面点击下一步 */
    	let that = this
        $(".getCode .btn[type=submit]").click(function () {
            let email = $('#email').val()
            let code = $('#securityCode').val();
            if(!email) {
                Public.message("info",'请输入邮箱！')
                return false;
            }else if(email && !code) {
                Public.message("info",'请输入验证码！')
                return false;
            }
            that.verifyMailCode(code);
            
            /**验证 输入的验证码 */

           
            return false;
        });
    },
    passwordChecked(){
        /**密码验证 */
        $("#password").blur(()=>{
            let psd = $("#password").val()
            let reg =  /^[a-zA-Z0-9_]{6,16}$/;
            if(!reg.test(psd)) {
                /**验证不通过 */
                $(".tip-text").hide()
                $("#password").addClass("error-input")
                $(".error-psd").show()
            }else {
                $(".tip-text").show()
                $("#password").removeClass("error-input")
                $(".error-psd").hide()

            }
        })

    },
    passwordChecked2(){
        /**再次密码验证 */
        $("#password2").blur(()=>{
            let psd = $("#password").val()
            let psd2 = $("#password2").val()
            if(psd !== psd2) {
                /**验证不通过 */
                $("#password2").addClass("error-input")
                $(".error-psd2").show()
            }else {
                $("#password2").removeClass("error-input")
                $(".error-psd2").hide()

            }
        })

    },
    passwordNext(){
    	let that = this
        /**密码下一步 */
        $(".changePassword .btn[type=submit]").click(function () {
            let psd = $('#password').val()
            let psd2 = $('#password2').val();
            if(!psd) {
                Public.message("info",'请输入密码！')
                return false;
            }else if(psd && !psd2) {
                Public.message("info",'请再次输入验证码！')
                return false;
            }
            that.resetPassword(psd,psd2)//密码后台修改
            
            return false;
        });
    },
    getMailCode(mail){
        /**接收验证码 */
    	$.post("/credit/sysuser/resetpassword/getMailCode",{recipientAddress:mail},function(data){
    		alert(data)
    	})
    },
    verifyMailCode(code){
        /**核实验证码 */
    	$.post("/credit/sysuser/resetpassword/verifyMailCode",{confirmCode:code},function(data){
    		if(data.statusCode===1){
    			 $(".changePassword").show().siblings().hide();
                 $(".stage ul>li").eq(1).addClass('active').siblings().removeClass('active')
               }else{
            	   Public.message("error",'验证码不正确！')
               }
    	})
    },
    resetPassword(password,passwordConfirm){
        /**接收验证码 */
    	$.post("/credit/sysuser/resetpassword/reset",{password:password,passwordConfirm:passwordConfirm},function(data){
    		if(data.statusCode===1){
    			$(".success").show().siblings().hide();
                $(".stage ul>li").eq(2).addClass('active').siblings().removeClass('active')
              }else{
           	   Public.message("error",'修改没有成功，请稍后再试！')
              }
    	})
    }
    
}


Password.init();