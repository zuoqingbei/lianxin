package com.hailian.modules.credit.usercenter.controller;


import com.feizhou.swagger.annotation.Api;
import com.hailian.api.constant.ReportTypeCons;
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
        String id = getPara("id");
        String sql = "select t.*,s1.detail_name as speedName from credit_order_info t left join sys_dict_detail s1 on t.speed = s1.detail_id  where t.num = ?";
        Integer userid = getSessionUser().getUserid();
        CreditOrderInfo order = CreditOrderInfo.dao.findFirst(sql, id);
        String reportType = order.getStr("report_type");
        String report_language = order.getStr("report_language");
        try {
            if (ReportTypeCons.ROC_HY.equals(reportType) || ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
                //中文繁体+英文
                if ("217".equals(report_language)) {
                    if ("12".equals(reportType) || "14".equals(reportType)) {
                        Roc102.reportTable(order, reportType, "613", userid);
                    }
                } else {
                    Roc102.reportTable(order, reportType, "612", userid);
                }
            } else {
                if ("213".equals(report_language)) {
                    BaseBusiCrdt.reportTable(order, reportType, "612", userid);
                } else if ("215".equals(report_language)) {
                    //英文
                    BaseBusiCrdt.reportTable(order, reportType, "613", userid);
                } else if ("216".equals(report_language)) {
                    //中文简体+英文
                    if ("1".equals(reportType)) {
                        BaseBusiCrdt.reportTable(order, "1", "612", userid);
                        BaseBusiCrdt.reportTable(order, "7", "613", userid);
                    } else if ("8".equals(reportType)) {
                        BaseBusiCrdt.reportTable(order, "8", "612", userid);
                        BaseBusiCrdt.reportTable(order, "9", "613", userid);
                    } else if ("10".equals(reportType)) {
                        BaseBusiCrdt.reportTable(order, "10", "612", userid);
                        BaseBusiCrdt.reportTable(order, "11", "613", userid);
                    } else {
                        BaseBusiCrdt.reportTable(order, reportType, "", userid);
                    }
                }
            }
        }catch (Exception e){
        	e.printStackTrace();
            System.out.println("报告生成异常");
        }
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
