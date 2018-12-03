package com.hailian.modules.credit.usercenter.controller;


import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.word.BaseInfoEn;
import com.hailian.util.word.BusinessZh;
import com.hailian.util.word.RocHy;
import com.hailian.util.word.RocZh;

/**
 * @className UserLoginController.java
 * @time   2018年9月10日 上午9:30:00
 * @author lzg
 * @todo   用户登录控制
 */
@Api( tag = "用户登录控制", description = "用户登录控制" )
@ControllerBind(controllerKey = "/report/test")
public class CreditReportController extends BaseProjectController{

    //登录
    public void report(){
        Integer userid = getSessionUser().getUserid();
        //BaseInfoZh test = new BaseInfoZh();
        //test.reportTable();
        //BusinessZh test = new BaseInfoZh();
        //报告类型8  订单ID 公司ID65  语言612
        //BusinessZh.reportTable("8","","65","612",userid);
        //CreditZh.reportTable();

        //报告类型7 订单ID 公司ID65  语言612
        //BaseInfoEn.reportTable("7", "", "65", "612", userid);

        //报告类型12 订单ID 公司ID65  语言612
        //RocZh.reportTable("12","","7777813", "612", userid);
        RocHy.reportTable("15","","7777829", "612", userid);
    }


}
