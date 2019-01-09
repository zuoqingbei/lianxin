package com.hailian.modules.credit.usercenter.controller;


import com.alibaba.fastjson.JSONObject;
import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanySubtables;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.util.word.*;
import com.jfinal.plugin.activerecord.Record;

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
    public void report() {
        String sql = "select t.*,s1.detail_name as speedName from credit_order_info t left join sys_dict_detail s1 on t.speed = s1.detail_id  where t.id = ?";
        Integer userid = getSessionUser().getUserid();
        CreditOrderInfo order = CreditOrderInfo.dao.findFirst(sql, "778367");
        String reportType = order.getStr("report_type");
        String report_language = order.getStr("report_language");

        //中文简体
        if ("213".equals(report_language)) {
            BaseInfoZh.reportTable(order, reportType, "612", userid);
        } else if ("215".equals(report_language)) {
            //英文
            BaseInfoZh.reportTable(order, reportType, "613", userid);
        } else if ("216".equals(report_language)) {
            //中文简体+英文
            if ("1".equals(reportType)) {
                BaseInfoZh.reportTable(order, "1", "612", userid);
                BaseInfoZh.reportTable(order, "7", "613", userid);
            } else if ("8".equals(reportType)) {
                BaseInfoZh.reportTable(order, "8", "612", userid);
                BaseInfoZh.reportTable(order, "9", "613", userid);
            } else if ("10".equals(reportType)) {
                BaseInfoZh.reportTable(order, "10", "612", userid);
                BaseInfoZh.reportTable(order, "11", "613", userid);
            } else {
                BaseInfoZh.reportTable(order, reportType, "", userid);
            }
        } else if ("217".equals(report_language)) {
            //中文繁体+英文
            if ("12".equals(reportType)) {
                BaseInfoZh.reportTable(order, reportType, "612", userid);
            }
            if("14".equals(reportType)){
                BaseInfoZh.reportTable(order, reportType, "613", userid);
            }
        } else {
            BaseInfoZh.reportTable(order, reportType, "", userid);
        }


        //BaseInfoZh test = new BaseInfoZh();
        //BaseInfoZh.reportTable(order, "12", userid);
        //BaseInfoZh.reportTable(order,"8",userid);
        //红印
        //BaseInfoZh.reportTable(order,"15",userid);
        //BusinessZh test = new BaseInfoZh();
        //报告类型8  订单ID 公司ID65  语言612
        //BusinessZh.reportTable(order,"8",userid);
        //CreditZh.reportTable("8","","65","612",userid);
        //CreditEn.reportTable("8","","65","612",userid);
        //Word105.reportTable("16","777891","7777831","612",userid);
        //报告类型7 订单ID 公司ID65  语言612
        //BaseInfoEn.reportTable("7", "", "65", "612", userid);

        //报告类型12 订单ID 公司ID65  语言612
        //RocZh.reportTable("12","777875","7777813", "612", userid);
        //RocHy.reportTable("15","778243","7778182", "612", userid);
    }

    public void getData(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        this.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        this.getResponse().setHeader("Access-Control-Max-Age", "3600");
        this.getResponse().setHeader("Access-Control-Allow-Headers", "x-requested-with");

        CreditCompanySubtables model = CreditCompanySubtables.dao.findById("47");
        String overview = model.getStr("overview");
        //JSONObject.toJSONString(result)

        overview = overview.replaceAll("(\\\\r\\\\n|\\\\n)", "\n");

        System.out.println(overview);

        renderJson(new Record().set("overview", overview));
    }


}
