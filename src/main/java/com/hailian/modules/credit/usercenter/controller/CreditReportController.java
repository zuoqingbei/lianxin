package com.hailian.modules.credit.usercenter.controller;


import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.util.word.*;

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
        //BaseInfoZh.reportTable("1","","7777843","612",userid);
        //BusinessZh test = new BaseInfoZh();
        //报告类型8  订单ID 公司ID65  语言612
        BusinessZh.reportTable("8","","7777838","612",userid);
        //CreditZh.reportTable("8","","65","612",userid);
        //CreditEn.reportTable("8","","65","612",userid);
        //Word105.reportTable("16","777891","7777831","612",userid);
        //报告类型7 订单ID 公司ID65  语言612
        //BaseInfoEn.reportTable("7", "", "65", "612", userid);

        //报告类型12 订单ID 公司ID65  语言612
        //RocZh.reportTable("12","777875","7777813", "612", userid);
        //RocHy.reportTable("15","","7777829", "612", userid);
    }


}
