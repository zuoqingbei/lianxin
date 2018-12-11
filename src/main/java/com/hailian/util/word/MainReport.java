package com.hailian.util.word;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;

/**
 * Created by Thinkpad on 2018/12/9.
 */
public class MainReport {

    public void build(int orderId,Integer userid){
        CreditOrderInfo order =  CreditOrderInfo.dao.findById(orderId);
        String reportType = order.getStr("report_type");
        //基本报告类型
        if("1".equals(reportType)){
            BaseInfoZh.reportTable(order,userid);
        }
        //商业报告中文版
        if("8".equals(reportType)){
            BusinessZh.reportTable(order,userid);
        }
    }

}
