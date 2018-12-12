package com.hailian.util.word;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

/**
 * Created by Thinkpad on 2018/12/9.
 */
public class MainReport {

    public void build(int orderId,Integer userid){
        CreditOrderInfo order =  CreditOrderInfo.dao.findById(orderId);
        String reportType = order.getStr("report_type");
        String report_language = order.getStr("report_language");
        //基本报告类型
        if("1".equals(reportType)){
            //中文简体
            if("213".equals(report_language)){
                BaseInfoZh.reportTable(order,"1",userid);
            }
            //中文简体+英文
            if("216".equals(report_language)){
                BaseInfoZh.reportTable(order,"1",userid);
                BaseInfoEn.reportTable(order,"1",userid);
            }
        }

        //商业报告中文版
        if("8".equals(reportType)){
            //中文简体
            if("213".equals(report_language)) {
                BusinessZh.reportTable(order, reportType, userid);
            }
            //英文
            if("215".equals(reportType)){
                BusinessEn.reportTable(order, reportType, userid);
            }
            //中文简体+英文
            if("216".equals(report_language)){
                BusinessZh.reportTable(order, reportType, userid);
                BusinessEn.reportTable(order, reportType, userid);
            }
        }
    }

}
