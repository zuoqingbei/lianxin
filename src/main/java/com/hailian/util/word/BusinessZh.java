package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商业信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BusinessZh {

    public static void main(String args[]) throws Exception{

    }

    public static void reportTable() {
        //报告类型
        String reportType = "8";
        //语言
        String sysLanguage = "612";
        //公司id
        String companyId = "77";
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
            String moduleType = crmc.getStr("small_module_type");
            String key = crmc.getStr("word_key");
            String tableType = crmc.getStr("word_table_type");
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

            //1：表格
            if(tableType!=null&&!"".equals(tableType)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = null;
                if ("s".equals(tableType)) {
                    table = MainWord.createTableS(child, rows);
                } else if ("h".equals(tableType)) {
                    table = MainWord.createTableS(child, rows);
                }
                map.put(key, table);
            }

            //信用等级
            if("6".equals(moduleType)){
                List rows = report.getTableData(true,sysLanguage, companyId, "credit_company_info", "CreditCompanyInfo", confId, "");
                Map<String,String> result = MainWord.getSingleValue(child, rows);
                map.put(key, result.get(key));
            }

            /*
            if ("报告摘要".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("zhaiyao", table);
            }

            if ("信用分析".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("xinyongfenxi", table);
            }

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
            if("行业信息".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("hangyexinxi", table);
            }
            if("业务情况".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("yewu", table);
            }
            if("采购情况".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("caugou", table);
            }
            if("销售情况".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("sales", table);
            }
            if("供应商".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("gongyingshang", table);
            }
            if("商标和专利".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("zhuanli", table);
            }

            if("商标和专利".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = MainWord.createTableS(child, rows);
                map.put("zhuanli", table);
            }*/
        }




        MainWord.buildWord(map, "h://word/_商业信息报告样本.docx", "h://2.docx");



    }





}
