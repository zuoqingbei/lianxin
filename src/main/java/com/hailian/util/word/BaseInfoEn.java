package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BaseInfoEn {

    public static void main(String args[]) throws Exception{
        //getForm?tableName=credit_company_info&className=CreditCompanyInfo*company_id
        String s = "getForm?tableName=credit_company_info&className=CreditCompanyInfo*company_id";
        //Map<String,String> map = parseUrl(s);
        //System.out.println(map.get("tableName"));
    }

    /**
     * 生成商业报告
     * @param reportType  报告类型
     * @param orderId     订单ID
     * @param companyId   公司ID
     * @param sysLanguage 语言
     * @param userid 当前登录人
     */
    public static void reportTable(String reportType, String orderId, String companyId, String sysLanguage, Integer userid) {
        //报告类型
        //String reportType = "1";
        //语言
        //String sysLanguage = "612";
        //公司id
        //String companyId = "77";
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("company", "海尔集团");
        map.put("code", "123");
        map.put("date", sdf.format(new Date()));

        //找到当前报告类型下的父节点
        List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
        for (CreditReportModuleConf crmc : crmcs) {
            //找到当前父节点下的子节点
            List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(crmc.get("id").toString(), reportType);
            String tempName = crmc.getStr("temp_name");
            String source = crmc.getStr("get_source");
            String confId = crmc.getInt("id") + "";
            //无url的跳过取数
            if (source == null || "".equals(source)) {
                continue;
            }
            Map<String, String> params = MainWord.parseUrl(source);
            String tableName = params.get("tableName");
            String clName = params.get("className");
            if (clName == null || "".equals(clName)) {
                continue;
            }
            String[] requireds = clName.split("\\*");
            String className = requireds.length > 0 ? requireds[0] : "";
            ReportInfoGetDataController report = new ReportInfoGetDataController();
            if ("企业注册信息".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("regist", table);
            }
            if ("历史变更记录".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableH(child, rows);
                map.put("history", table);
            }
            if ("股东信息".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableH(child, rows);
                map.put("partner", table);
            }
            if("法人股东详情".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("details", table);
            }
            if("投资情况".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableH(child, rows);
                map.put("invest", table);
            }
            if("管理层".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("leader", table);
            }
        }

        String str = "该公司目前主要从事管道支吊架、垃圾给料机、钢结构件（除建筑构件）的制造、加工；机械零部件加工；金属材料的批发、零售、代购代销。\n" +
                "周**先生目前在该公司担任董事长。\n" +
                "该公司目前有120名员工。\n" +
                "该公司目前在首页所述之地址办公。该地址位于浙江丽水市***********，面积未能获知。\n";

        //总结
        map.put("result", str);

        MainWord.buildWord(map, webRoot + "/word/" + "_REGISTRATION REPORT.docx", _prePath + ".docx");
    }

}
