package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商业信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class CreditEn {

    public static void main(String args[]) throws Exception{

    }

    public void getSonData(String sonTable,String companyId,String sysLanguage){
        //获取主表数据
        //CreditCompanyInfo companyInfo = report.getCompanyInfo(companyId,sysLanguage);
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
        //String reportType = "8";
        //语言
        //String sysLanguage = "612";
        //公司id
        //String companyId = "7777794"; //77基本  65商业  //7777794信用分析
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;

        HashMap<String, Object> map = new HashMap<String, Object>();
        //获取订单信息
        CreditCompanyInfo order = CreditCompanyInfo.dao.findById(companyId);
        //订单公司名称
        map.put("company", order.getStr("name_en"));
        //联信编码
        map.put("code", order.getStr("lianxin_id"));
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
            Map<String, String> params = BaseWord.parseUrl(source);
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
                    table = BaseWord.createTableS(child, rows);
                } else if ("h".equals(tableType)) {
                    table = BaseWord.createTableH(child, rows);
                }
                map.put(key, table);
            }

            //2：主从表中的- 单个值
            for(CreditReportModuleConf conf : child){
                String ci = conf.getInt("id") + "";
                String s = conf.getStr("get_source");
                if(s==null||"".equals(s)){
                    continue;
                }
                Map<String, String> p = BaseWord.parseUrl(s);
                String t = p.get("tableName");
                if (t == null || "".equals(t)) {
                    continue;
                }
                String c = p.get("className");
                String cn = c.split("\\*")[0];
                //主表
                if("credit_company_info".equals(t)){
                    String word_key = conf.get("word_key")+"";
                    if(word_key!=null && !"".equals(word_key) && !"null".equals(word_key)) {
                        List rs = report.getTableData(true, companyId, t, cn, ci, "");
                        if(rs!=null&&rs.size()>0){
                            BaseProjectModel model = (BaseProjectModel)rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }
                    }
                }else{
                    String word_key = conf.get("word_key")+"";
                    if(word_key!=null && !"".equals(word_key) && !"null".equals(word_key)) {
                        List rs = report.getTableData(true, companyId, t, cn, ci, "");
                        if(rs!=null&&rs.size()>0){
                            BaseProjectModel model = (BaseProjectModel)rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }
                    }
                }
            }

            //7 输入框取数
            if("7".equals(moduleType)){
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
                LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
                //取列值
                for(int i=0;i< child.size();i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    cols.put(column_name,temp_name);
                }
                //取数据
                for (int i = 0; i < rows.size(); i++) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    for(String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        map.put(column,value);
                    }
                }
            }
        }
        BaseWord.buildWord(map, webRoot + "/word/" + "_CREDIT RISK ANALYSIS REPORT.docx", _prePath + ".docx");
    }

}
