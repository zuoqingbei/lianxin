package com.hailian.modules.credit.usercenter.controller;


import com.feizhou.swagger.annotation.Api;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanySubtables;
import com.hailian.modules.admin.ordermanager.model.CreditOperationLog;
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.util.word.*;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;

/**
 * @className UserLoginController.java
 * @time   2019年3月30日 上午9:30:00
 * @author lzg
 * @todo   根据订单号发送报告
 */
@Api( tag = "根据订单号发送报告", description = "根据订单号发送报告" )
@ControllerBind(controllerKey = "/credit/front/resend")
public class CreditReportController extends BaseProjectController{
    public static Logger log = Logger.getLogger(CreditReportController.class);
    String errorMessage = "";
    public void run() {
        String orderNum = getPara("orderNum");
        Integer userid = getSessionUser().getUserid();
        Record record = new Record();
        record.set("statusCode", 1);
        record.set("message", "发送成功!");
        CreditOrderInfo model = CreditOrderInfo.dao.findFirst("select * from credit_order_info where num=?", orderNum);
        String orderId = model.get("id")+"";
        try{
            String speedLanguage = "s1.detail_name";
            String meataSql = "select report_type,report_language from credit_order_info  where num = ?";
            CreditOrderInfo metaOrder = CreditOrderInfo.dao.findFirst(meataSql, orderNum);
            String reportType = metaOrder.getStr("report_type");
            String report_language = metaOrder.getStr("report_language");
            if("EN".equals(ReportTypeCons.whichLanguage(reportType))) {
                speedLanguage += "_en";
            }
            //业务sql
            String sql = "select t.*,"+speedLanguage+" as speedName from credit_order_info t left join sys_dict_detail s1 on t.speed = s1.detail_id  where t.id = ?";
            CreditOrderInfo order = CreditOrderInfo.dao.findFirst(sql, orderId);
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

        }catch(Exception e){
                //日志输出
                e.printStackTrace();
                errorMessage = ReportInfoGetData.outPutErroLog(log, e);
                //报告发送失败,改变状态
                CreditOrderInfo tempModel = new CreditOrderInfo();
                int statusCode = 999;
                tempModel.set("id", orderId).set("status", statusCode).update();
                model.set("status", statusCode);
                record.clear().set("statusCode", 0).set("message", "重新发送报告时失败!请联系管理员或者再次发送!");
                //增加站内信
                ReportInfoGetData.sendErrMsg(model, userid,  "重新发送报告时失败!请联系管理员或者再次发送!");
        }
        //增加跟踪记录
        CreditOrderFlow.addOneEntry(this, model,errorMessage,true);
        CreditOperationLog.dao.addOneEntry(userid, model, "重新发送/", "/report/resend/run");//操作日志记录
        renderJson(record);
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
