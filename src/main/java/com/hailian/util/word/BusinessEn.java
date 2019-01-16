package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.usercenter.controller.finance.FinanceService;
import com.hailian.util.Config;
import com.jfinal.kit.PathKit;
import org.jfree.data.general.DefaultPieDataset;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 商业信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BusinessEn {

    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int serverPort = Config.getToInt("searver_port");//ftp端口 默认21

    /**
     * 生成商业报告
     * @param order  订单
     * @param userid 当前登录人
     */
    public static void reportTable(CreditOrderInfo order,String reportType, Integer userid) {
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> map = new HashMap<String, Object>();

        //获取订单信息
        //CreditOrderInfo order =  CreditOrderInfo.dao.findById(orderId);
        String companyId = order.getStr("company_id");
        String customId = order.getStr("custom_id");
        //String reportType = order.getStr("report_type");
        String orderId = order.getInt("id")+"";

        //获取公司信息
        CreditCompanyInfo companyInfo = CreditCompanyInfo.dao.findById(companyId);
        String sysLanguage = companyInfo.getInt("sys_language")+"";
        String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;
        //订单公司名称
        map.put("company", companyInfo.getStr("name_en"));
        //联信编码
        map.put("code", companyInfo.getStr("lianxin_id"));
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
            if (tableType != null && !"".equals(tableType)) {
                String selectInfo = "";
                //List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = null;
                if ("s".equals(tableType)) {
                    //table = BaseWord.createTableS(child, rows);
                } else if ("h".equals(tableType)) {
                    //table = BaseWord.createTableH(child, rows);
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
                Map<String, String> p = BaseWord.parseUrl(s);
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
                        /*List rs = report.getTableData(true,   companyId, t, cn, ci, "");
                        if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }*/
                    }
                } else {
                    //取word里配置的关键词
                    String word_key = conf.get("word_key") + "";
                    if (word_key != null && !"".equals(word_key) && !"null".equals(word_key)) {
                        //取数据
                        //List rs = report.getTableData(true,    companyId, t, cn, ci, "");
                        /*if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }*/
                    }
                }
            }

            //7 输入框取数
            if ("7".equals(moduleType)) {
                //List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    cols.put(column_name, temp_name);
                }
                //取数据
                /*for (int i = 0; i < rows.size(); i++) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    for (String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        map.put(column, value);
                    }
                }*/
            }

            //8-单选框
            if("8".equals(moduleType)){
                //List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String get_source = module.getStr("get_source");
                    cols.put(column_name, get_source);
                }
                //取数据

            }

            //图形表
            if ("11".equals(moduleType)) {
                /*for (int i = 0; i < rows.size(); i++) {
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
                }*/
                String selectInfo = "";
                //List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
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
                /*for (int i = 0; i < rows.size(); i++) {
                    LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
                    //取行
                    BaseProjectModel model = (BaseProjectModel) rows.get(i);
                    for (String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        row.put(column, value);
                    }
                    datas.add(row);
                }*/
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
                BaseWord.createPieChart(pds, _prePath + ".jpg");
                map.put("pie", new PictureRenderData(600, 300, _prePath + ".jpg"));
            }
        }

        //财务模块生成
        map.put("financial", financial(reportType,companyId,sysLanguage,"1"));


        BaseWord.buildWord(map, webRoot + "/word/" + "_BUSINESS INFORMATION REPORT.docx", _prePath + ".docx");
        //上传文件
        String filePath = BaseWord.uploadReport(_prePath + ".docx", orderId, userid);
        //发送邮件
        List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("_BUSINESS INFORMATION REPORT.doc", "http://" + ip + ":" + serverPort + "/" + filePath);
        fileList.add(fileMap);

        try {
            //new SendMailUtil("15953295779@126.com", "", "商业信息报告", "商业信息报告", fileList).sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 财务模板
     * @param financeType
     * @param companyId
     * @param sysLanguage
     * @param financialConfId
     * @return
     */
    public static MiniTableRenderData financial(String financeType,String companyId,String sysLanguage,String financialConfId) {
        ReportInfoGetDataController report = new ReportInfoGetDataController();

        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        //取数据
        //Integer type = new ReportInfoGetDataController().getFinanceDictByReportType(reportType);
        List<CreditCompanyFinancialEntry> finDataRows = FinanceService.getFinancialEntryList(financialConfId,financeType);

        int j = 0;
        Integer old = null;
        for (CreditCompanyFinancialEntry ccf : finDataRows) {
            Integer son_sector = ccf.getInt("son_sector");
            //判断新模块，第一行要加标题
            if(old==null){
                old = son_sector;
            }else{
                if(son_sector.intValue()!=old.intValue()){
                    old = son_sector;
                    j = 0;
                }
            }
            //j=0表示第一条
            if(j==0) {
                String title = "";
                String unit = "";
                switch (son_sector.intValue()){
                    case 1:
                        title = "合计";
                        break;
                    case 2:
                        title = "流动资产";
                        break;
                    case 3:
                        title = "非流动资产";
                        break;
                    case 4:
                        title = "流动负债";
                        break;
                    case 5:
                        title = "非流动负债";
                        break;
                    case 6:
                        title = "负债及所有者权益";
                        break;
                    case 7:
                        title = "毛利润";
                        break;
                    case 8:
                        title = "营业利润";
                        break;
                    case 9:
                        title = "税前利润";
                        break;
                    case 10:
                        title = "所得税费用";
                        break;
                    case 11:
                        title = "重要比率表";
                        break;
                }
                rowList.add(RowRenderData.build(new TextRenderData(""), new TextRenderData(""), new TextRenderData("")));
                //大标题
                Style titileStyle = new Style();
                titileStyle.setColor("843C0B");
                titileStyle.setBold(true);
                rowList.add(RowRenderData.build(new TextRenderData(title,titileStyle), new TextRenderData(""), new TextRenderData("")));
                Style header = new Style();
                header.setBold(true);
                rowList.add(RowRenderData.build(new TextRenderData(""), new TextRenderData(""), new TextRenderData("单位：人民币（千元）",header)));
            }
            String itemName = ccf.getStr("item_name");
            Integer begin = ccf.getInt("begin_date_value");
            Integer end = ccf.getInt("end_date_value");
            Integer is_sum_option = ccf.getInt("is_sum_option");

            Style sumStyle = new Style();
            if(is_sum_option.intValue()==1){
                sumStyle.setBold(true);
            }
            rowList.add(RowRenderData.build(new TextRenderData(itemName,sumStyle), new TextRenderData(begin.toString()), new TextRenderData(end.toString())));

            j++;
        }

        //取配置
        /*List<CreditReportModuleConf> confList = CreditReportModuleConf.dao.find("select * from credit_report_module_conf where float_parent in (select id from credit_report_module_conf where report_type="
                + reportType + " and small_module_type = 10 )");
        for(CreditReportModuleConf conf : confList){
            String source = conf.getStr("get_source");
            if(source!=null&&!"".equals(source)){
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

                //取子项
                List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(conf.get("id").toString(), reportType);
                //取数据
                List rows = report.getTableData(false, sysLanguage, companyId, t, cn, ci, "");
                MiniTableRenderData table = MainWord.createTableS(child, rows);


            }
        }*/


        return new MiniTableRenderData(rowList);
    }



}
