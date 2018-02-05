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
        // alert(1)
        validateForm();

        if ($flatMap_form.valid()&&dealSelectName()) {
        	var param= $flatMap_form.serialize();
        	$.post(contextPath+"/admin/updateLabInfo",param,function(data){
        		alert("修改成功!");
        		window.location.href=contextPath+"/admin/labInfoForm?labCode="+data.code;
        	});
        };

    });
    function dealSelectName(){
    	$("#lab_type_name").val($('#lab_type_code option:selected').text());
    	$("#properties_name").val($('#properties_code option:selected').text());
    	$("#professional_name").val($('#professional_code option:selected').text());
    	var pcode = document.getElementsByName('product_code');
        var productCodes = new Array();
        var productNames = new Array();
        for(var i = 0; i < pcode.length; i++){
         if(pcode[i].checked){
        	 productCodes.push(pcode[i].value);
        	 productNames.push($(pcode[i]).attr("data"));
         };
        };
        if(productCodes.length==0){
        	return false;
        }else{
        	$("#product_codes").val(productCodes.join(","));
        	$("#product_name").val(productNames.join(","));
        };
        //可开展实验类别
        var ctype = document.getElementsByName('carryTypeCode');
        var carryTypeCode = new Array();
        var carryTypeName = new Array();
        for(var i = 0; i < ctype.length; i++){
         if(ctype[i].checked){
        	 carryTypeCode.push(ctype[i].value);
        	 carryTypeName.push($(ctype[i]).attr("data"));
         };
        };
        if(carryTypeCode.length==0){
        	return false;
        }else{
        	$("#carryTypeCodes").val(carryTypeCode.join(","));
        	$("#carryTypeName").val(carryTypeName.join(","));
        };
        console.log(carryTypeCode.join(","))
        return true;
    }
    function validateForm() {
        $flatMap_form.validate({
            debug: true,
            rules: {
                name: {
                    required: true
                },
                short_name: {
                    required: true
                },
                location: {
                    required: true
                },
                lng: {
                    required: true,
                    number:true,
                    range:[-180,180]
                },
                lat: {
                    required: true,
                    number:true,
                    range:[-90,90]
                },
                jiance37_name: {
                    required: true
                },
                country: {
                    required: true
                },
                city: {
                    required: true
                },
                product_code: {
                    required: true
                },
                carryTypeCode: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入实验室名称"
                },
                short_name: {
                    required: "请输入实验室简称"
                },
                location: {
                    required: "请输入实验室所在地区"
                },
                lng: {
                    required: "请输入实验室经度",
                    number:"请输入-180~180之间的数值",
                    range: "请输入-180~180之间的数值"
                },
                lat: {
                    required: "请输入实验室纬度",
                    number: "请输入-90~90之间的数值",
                    range: "请输入-90~90之间的数值"
                },
                jiance37_name: {
                    required: "请输入检测名称"
                },
                country: {
                    required: "请输入国家名称"
                },
                city: {
                    required: "请输入城市名称"
                },
                product_code: {
                    required: "请选择产线"
                },
                carryTypeCode: {
                    required: "请选择可开展实验类别"
                }
            }

//                success:function () {
//                    validResult = true;
//                }
        });
    }

})