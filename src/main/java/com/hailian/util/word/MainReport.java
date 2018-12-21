package com.hailian.util.word;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

/**
 * Created by Thinkpad on 2018/12/9.
 */
public class MainReport {

    public void build(int orderId,Integer userid) {
        CreditOrderInfo order = CreditOrderInfo.dao.findById(orderId);
        String reportType = order.getStr("report_type");
        String report_language = order.getStr("report_language");

        //中文简体
        if ("213".equals(report_language)) {
            BaseInfoZh.reportTable(order, reportType, userid);
        }else if("215".equals(report_language)){
            //英文
            BaseInfoZh.reportTable(order, reportType, userid);
        }else if("216".equals(report_language)) {
            //中文简体+英文
            if("1".equals(reportType)){
                BaseInfoZh.reportTable(order, "1", userid);
                BaseInfoZh.reportTable(order, "7", userid);
            }else if("8".equals(reportType)){
                BaseInfoZh.reportTable(order, "8", userid);
                BaseInfoZh.reportTable(order, "9", userid);
            }else if("10".equals(reportType)){
                BaseInfoZh.reportTable(order, "10", userid);
                BaseInfoZh.reportTable(order, "11", userid);
            }else if("12".equals(reportType)){
                BaseInfoZh.reportTable(order, "12", userid);
                BaseInfoZh.reportTable(order, "14", userid);
            }else{
                BaseInfoZh.reportTable(order, reportType, userid);
            }
        }else {
            BaseInfoZh.reportTable(order, reportType, userid);
        }
    }

}
