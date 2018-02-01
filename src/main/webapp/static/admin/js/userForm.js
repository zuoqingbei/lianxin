$(function () {

    /*响应菜单点击后隐藏*/
    $(".navbar-collapse a").click(function () {
        $(".navbar-collapse").collapse("hide");
    });

    //验证表单
    var validResult = false;
    var $flatMap_form = $("#flatMap_form").find("form");
    $flatMap_form.find(":input").keyup(function () {
        validateForm()
    });
    $flatMap_form.find(":submit").click(function () {
        validateForm();

        if ($flatMap_form.valid()) {
        	var param= $flatMap_form.serialize();
        	$.post(contextPath+"/admin/updateUser",param,function(data){
        		alert("修改成功!");
        		window.location.href=contextPath+"/admin/userForm?id="+data.id;
        	});
        };

    });

    function validateForm() {
        $flatMap_form.validate({
            debug: true,
            rules: {
                name: {
                    required: true
                },
                login_name: {
                    required: true
                },
                pwd: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入用户名称"
                },
                login_name: {
                    required: "请输入登录名称"
                },
                pwd: {
                    required: "请输入账号密码"
                }
            }
//                success:function () {
//                    validResult = true;
//                }
        });
    }

})