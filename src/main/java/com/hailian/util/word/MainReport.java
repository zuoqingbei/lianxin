package com.hailian.util.word;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

/**
 * Created by Thinkpad on 2018/12/9.
 */
public class MainReport {

    public void build(int orderId,Integer userid) {
        String sql = "select t.*,s1.detail_name as speedName from credit_order_info t left join sys_dict_detail s1 on t.speed = s1.detail_id  where t.id = ?";
        CreditOrderInfo order = CreditOrderInfo.dao.findFirst(sql,orderId);
        String reportType = order.getStr("report_type");
        String report_language = order.getStr("report_language");

        //中文简体
        if ("213".equals(report_language)) {
            Roc102.reportTable(order, reportType, "612", userid);
        } else if ("215".equals(report_language)) {
            //英文
            Roc102.reportTable(order, reportType, "613", userid);
        } else if ("216".equals(report_language)) {
            //中文简体+英文
            if ("1".equals(reportType)) {
                Roc102.reportTable(order, "1", "612", userid);
                Roc102.reportTable(order, "7", "613", userid);
            } else if ("8".equals(reportType)) {
                Roc102.reportTable(order, "8", "612", userid);
                Roc102.reportTable(order, "9", "613", userid);
            } else if ("10".equals(reportType)) {
                Roc102.reportTable(order, "10", "612", userid);
                Roc102.reportTable(order, "11", "613", userid);
            } else {
                Roc102.reportTable(order, reportType, "", userid);
            }
        } else if ("217".equals(report_language)) {
            //中文繁体+英文
            if ("12".equals(reportType)) {
                Roc102.reportTable(order, reportType, "613", userid);
            }
            if("14".equals(reportType)){
                Roc102.reportTable(order, reportType, "613", userid);
            }
        } else {
            Roc102.reportTable(order, reportType, "", userid);
        }

    }

}
