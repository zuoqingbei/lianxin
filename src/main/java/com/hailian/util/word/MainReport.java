package com.hailian.util.word;

import org.apache.log4j.Logger;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.notice.model.NoticeLogModel;
import com.hailian.modules.credit.notice.model.NoticeModel;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetData;
import com.hailian.util.DateUtils;

/**
 * 报告生成主类
 */
public class MainReport {

	public static Logger log = Logger.getLogger(MainReport.class);
	
    //生成报告入口
    public void build(int orderId, Integer userid) throws Exception {
    	String speedLanguage = "s1.detail_name";
        String meataSql = "select report_type,report_language from credit_order_info  where id = ?";
        CreditOrderInfo metaOrder = CreditOrderInfo.dao.findFirst(meataSql, orderId);
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
        
    }
    
    

}
