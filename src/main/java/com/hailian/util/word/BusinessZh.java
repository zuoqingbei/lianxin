package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.util.Config;
import com.jfinal.kit.PathKit;
import org.jfree.data.general.DefaultPieDataset;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 商业信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BusinessZh {

    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int serverPort = Config.getToInt("searver_port");//ftp端口 默认21

    /**
     * 生成商业报告
     *
     * @param reportType  报告类型
     * @param orderId     订单ID
     * @param companyId   公司ID
     * @param sysLanguage 语言
     * @param userid 当前登录人
     */
    public static void reportTable(String reportType, String orderId, String companyId, String sysLanguage, Integer userid) {
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
            if (tableType != null && !"".equals(tableType)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = null;
                if ("s".equals(tableType)) {
                    table = MainWord.createTableS(child, rows);
                } else if ("h".equals(tableType)) {
                    table = MainWord.createTableH(child, rows);
                }
                map.put(key, table);
            }

            //2：主从表中的- 单个值
            for (CreditReportModuleConf conf : child) {
                String ci = conf.getInt("id") + "";
                String s = conf.getStr("get_source");
                if (s == null || "".equals(s)) {
                    continue;
                }
                Map<String, String> p = MainWord.parseUrl(s);
                String t = p.get("tableName");
                if (t == null || "".equals(t)) {
                    continue;
                }
                String c = p.get("className");
                String cn = c.split("\\*")[0];
                //主表
                if ("credit_company_info".equals(t)) {
                    String word_key = conf.get("word_key") + "";
                    if (word_key != null && !"".equals(word_key) && !"null".equals(word_key)) {
                        List rs = report.getTableData(true, sysLanguage, companyId, t, cn, ci, "");
                        if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }
                    }
                } else {
                    //取word里配置的关键词
                    String word_key = conf.get("word_key") + "";
                    if (word_key != null && !"".equals(word_key) && !"null".equals(word_key)) {
                        //取数据
                        List rs = report.getTableData(true, sysLanguage, companyId, t, cn, ci, "");
                        if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }
                    }
                }
            }

            //7 输入框取数
            if ("7".equals(moduleType)) {
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    cols.put(column_name, temp_name);
                }
                //取数据
                for (int i = 0; i < rows.size(); i++) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    for (String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        map.put(column, value);
                    }
                }
            }

            //8-单选框
            if("8".equals(moduleType)){
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String get_source = module.getStr("get_source");
                    cols.put(column_name, get_source);
                }
                //取数据
                for (int i = 0; i < rows.size(); i++) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    for (String column : cols.keySet()) {
                        //取值
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        String get_source = cols.get(column);
                        String[] items = get_source.split("&");
                        StringBuffer html = new StringBuffer();
                        for(int j=0;j<items.length;j++) {
                            String[] item = items[j].split("-");
                            if (value.equals(item[0])) {
                                html.append("(√)" + item[1] + " ");
                            }else{
                                html.append("( )" + item[1] + " ");
                            }
                        }
                        map.put(column, html.toString());
                    }
                }


            }

            //图形表
            if ("11".equals(moduleType)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                List<LinkedHashMap<String, String>> datas = new ArrayList<LinkedHashMap<String, String>>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    cols.put(column_name, temp_name);
                }
                //取数据
                for (int i = 0; i < rows.size(); i++) {
                    LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
                    //取行
                    BaseProjectModel model = (BaseProjectModel) rows.get(i);
                    for (String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        row.put(column, value);
                    }
                    datas.add(row);
                }
                //生成图片
                DefaultPieDataset pds = new DefaultPieDataset();
                for (LinkedHashMap<String, String> m : datas) {
                    Object[] keys = m.keySet().toArray();
                    String n = m.get(keys[0]);
                    String v = m.get(keys[2]);
                    int value = 0;
                    if (v != null && !"".equals(v)) {
                        v = m.get(keys[2]);
                        try {
                            value = Integer.parseInt(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    pds.setValue(n, value);
                }
                MainWord.createPieChart(pds, _prePath + ".jpg");
                map.put("pie", new PictureRenderData(600, 300, _prePath + ".jpg"));
            }
        }
        MainWord.buildWord(map, webRoot + "/word/" + "_商业信息报告样本.docx", _prePath + ".docx");
        //上传文件
        String filePath = MainWord.uploadReport(_prePath + ".docx", orderId, userid);
        //发送邮件
        List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("商业信息报告.doc", "http://" + ip + ":" + serverPort + "/" + filePath);
        fileList.add(fileMap);
        try {
            new SendMailUtil("15953295779@126.com", "", "商业信息报告", "商业信息报告", fileList).sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void financial(){

    }



}
