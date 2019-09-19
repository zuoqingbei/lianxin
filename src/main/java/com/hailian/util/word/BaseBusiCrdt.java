package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.BaseModel;
import com.hailian.modules.admin.ordermanager.model.*;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetData;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.usercenter.controller.finance.FinanceService;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.system.user.SysUser;
import com.hailian.util.Config;
import com.hailian.util.StrUtils;
import com.hailian.util.translate.TransApi;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.template.ext.directive.Str;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基本信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BaseBusiCrdt extends BaseWord{
     public static Logger log = Logger.getLogger(BaseBusiCrdt.class);
    //ftp文件服务器 ip
    public static final String ip = Config.getStr("ftp_ip");
    //ftp端口 9980
    public static final int serverPort = Config.getToInt("searver_port");
    private static Object o = new Object();
    public static void main(String []args){
        try {
            System.out.println(timeRangeHandling("2019-09-13 - 2019-10-09", " - ", "yyyy-mm-dd","yyyy年MM月dd日"));
            /*String urlStr = "http://120.27.46.160:9980/report_type/2018-12-17/396-20181217173409.docx";
            URL url = new URL(urlStr);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            //设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            InputStream iputstream = uc.getInputStream();*/
            //System.out.println(file.getName());
            //BaseWord.buildWord(null, iputstream, "");

            //String str = BaseInfoZh.downloadFile("http://tm-image.qichacha.com/a460ea8b52eda230294f1bb618c3dfc8.jpg","h:/");
            //System.out.println(str);

            //String str = String.format("%010s", "a12");
            //System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 报告生成
     * @param order  订单
     * @param reportType 报告类型
     * @param sysLanguage 报告内容语言
     * @param userid 当前登录人
     * @throws Exception
     */
    public static void reportTable(CreditOrderInfo order,String reportType,String sysLanguage,Integer userid) throws Exception {
    	reportTable(order, reportType, sysLanguage, userid, null);
    }
    public static void reportTable(CreditOrderInfo order,String reportType,String sysLanguage,Integer userid,Map<String,Object> extend) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //报告名称
        String reportNameCh = Db.query("select name from credit_report_type where del_flag=0 and id=?",reportType)+"";
        String reportNameEn = Db.query("select name_en from credit_report_type where del_flag=0 and id=?",reportType)+"";
        if("612".equals(reportNameCh)) {
             map.put("reportName",reportNameCh);
        }else if("613".equals(reportNameCh)) {
             map.put("reportName",reportNameEn);
        }


        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //获取报告信息
        ReportTypeModel reportTypeModel = ReportTypeModel.dao.findById(reportType);
        //报告文件路劲
        String tplPath = "http://"+ ip + ":" + serverPort +"/"+ reportTypeModel.getStr("tpl_path");
        //客户参考号作为发送的报告名称
        String referenceNum = order.getStr("reference_num")!=null?order.getStr("reference_num"):"000000";
        while(referenceNum.length()<7) {
                referenceNum = "0" + referenceNum;
        }
        //订单编号
        String orderCode = order.getStr("num");
        //获取订单信息
        String customId = order.getStr("custom_id");
        String orderId = order.getInt("id") + "";
        //获取公司信息
        String sql = "select * from credit_company_info t where t.order_id = ? and t.sys_language=?";
        CreditCompanyInfo companyInfo = CreditCompanyInfo.dao.findFirst(sql,orderId,sysLanguage);
        String companyId = companyInfo.getInt("id")+"";
        //报告速度
        map.put("speed",order.getStr("speedName"));
        //客户参考号
        map.put("reference_num",referenceNum);
        //订单公司名称
        if(ReportTypeCons.ROC_EN.equals(reportType)){
            map.put("company", TransApi.Trans(order.getStr("right_company_name_en"), "cht"));
        }else{
            map.put("company", companyInfo.getStr("name_en"));
        }
        //联信编码
        map.put("code", companyInfo.getStr("lianxin_id"));
        map.put("date", sdf.format(new Date()));
        map.put("order_code",orderCode);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm:ss");
        map.put("currentTime",sdf2.format(new Date()));

        //报告名称
        //String reportName = reportTypeModel.getStr("name");
        String reportName = referenceNum;
        //保存的文件名
        //String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;
        String _prePath = webRoot + "/upload/tmp/" + orderCode;
        if(!new File(_prePath).exists()){
            new File(_prePath).mkdir();
        }
        _prePath = _prePath + "/" + referenceNum;

        //找到当前报告类型下的父节点
        List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
        for (CreditReportModuleConf crmc : crmcs) {
            //找到当前父节点下的子节点  type=2表示详情
            List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon2(crmc.get("id").toString(), reportType, "4");

            String source = crmc.getStr("get_source");
            String confId = crmc.getInt("id") + "";
            String moduleType = crmc.getStr("small_module_type");
            String key = crmc.getStr("word_key");
            String tableType = crmc.getStr("word_table_type");
            String tableId = crmc.getInt("table_id")+"";
            //无url的跳过取数
            if (StringUtils.isEmpty(source)) {
                continue;
            }
            Map<String, String> params = BaseWord.parseUrl(source);
            String tableName = params.get("tableName");
            String clName = params.get("className");
            if(StringUtils.isEmpty(tableName) || StringUtils.isEmpty(clName)){
                continue;
            }
            String[] requireds = clName.split("\\*");
            String className = requireds.length > 0 ? requireds[0] : "";
            ReportInfoGetDataController report = new ReportInfoGetDataController();

            //102红印 取股东信息模块的截止时间
            String endDate = "";
            if(ReportTypeCons.ROC_HY.equals(reportType)&&"credit_company_shareholder".equals(tableName)){
                List<CreditReportModuleConf> floatModule = CreditReportModuleConf.dao.findSon3(crmc.get("id").toString(), reportType, "4");
                for(CreditReportModuleConf _conf : floatModule){
                    String _confId = _conf.getInt("id") + "";
                    String _source = _conf.getStr("get_source");
                    //无url的跳过取数
                    if (StringUtils.isEmpty(_source)) {
                        continue;
                    }
                    Map<String, String> _params = BaseWord.parseUrl(_source);
                    String _tableName = _params.get("tableName");
                    String _clName = _params.get("className");
                    if(StringUtils.isEmpty(_tableName) || StringUtils.isEmpty(_clName)){
                        continue;
                    }
                    String[] _requireds = _clName.split("\\*");
                    String _className = _requireds.length > 0 ? _requireds[0] : "";
                    List rows = report.getTableData(sysLanguage, companyId, _tableName, _className, _confId, "",reportType);
                    for (int i = 0; i < rows.size(); i++) {
                        //取一行数据
                        BaseProjectModel model = (BaseProjectModel) rows.get(i);
                        endDate = model.get("date")+"";
                        map.put("endDate"," 截至日期 " + endDate);
                    }
                }
            }
            List<Object> appendRowsForInvestmentSituation = null;
            //1：表格
            if (tableType != null && !"".equals(tableType)) {
                String selectInfo = "";
                List<BaseProjectModel> rows = (List<BaseProjectModel>)(report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo,reportType));
                //公司性质和企业类型注释的合并
                try{
                    if("credit_company_info".equals(tableName)&&"regist".equals(key)){
                        String companyType = rows.get(0).getInt("company_type")+"";//公司性质
                        companyType = getValue(reportType, sysLanguage,
                                sdf, "select", companyType+"");
                        List<String> targetList = Db.query("select type_of_enterprise_remark from credit_company_info where id=?",new String[]{companyId});
                        String comMark = targetList.get(0);//企业类型注释
                        for (BaseProjectModel row  : rows) {
                            row.set("company_type",StringUtils.isEmpty(comMark)?companyType:companyType+"\n"+comMark);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                MiniTableRenderData table = null;
                if("zhaiyao".equals(key)){
                    specialHandlingForTable(key,child,rows,reportType,sysLanguage) ;
                }
                if("creditanalysis".equals(key)){
                    specialHandlingForTable(key,child,rows,reportType,sysLanguage) ;
                }
                if("regist".equals(key)){
                    specialHandlingForTable(key,child,rows,reportType,sysLanguage) ;
                }
                if ("s".equals(tableType)) {

                    //如果是法人股东详情或者是自热人股东详情做特殊解析
                    try{
                        if("credit_company_legal_shareholder_detail".equals(tableName)||"credit_company_naturalperson_shareholder_detail".equals(tableName)){
                            appendRowsForInvestmentSituation = new ArrayList<>();
                            for (int i=0;i<child.size();i++) {
                                if ("investment_situation".equals(child.get(i).get("column_name"))){
                                    child.remove(i);
                                    break;
                                }
                            }
                            //加上一些默认的字段
                            //child.addAll(generatedInvestmentSituationConfHead(reportType));

                            for (int i=0;i<rows.size();i++) {
                                String investmentSituationStr = rows.get(i).getStr("investment_situation");//投资情况
                                rows.get(i).remove("investment_situation");
                                if(investmentSituationStr!=null&&!"".equals(investmentSituationStr=investmentSituationStr.trim())){
                                    appendRowsForInvestmentSituation.add(i,investmentSituationStr);
                                }else{
                                    appendRowsForInvestmentSituation.add(i,null);
                                }
                            }
                        }
                        table = BaseWord.createTableS(key,reportType,child, rows,sysLanguage,companyId,appendRowsForInvestmentSituation);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if ("h".equals(tableType)) {
                    //"出资情况"需要增加合计项
                    boolean hasTotal = "credit_company_shareholder".equals(tableName) ? true : false;
                    table = BaseWord.createTableH(key,reportType, child, rows, sysLanguage, hasTotal,"",companyId);
                }else if("z".equals(tableType)){
                    BaseWord.createTableZ(child,rows,map,reportType,sysLanguage);
                }
                map.put(key, table);
            }

            //2：主从表中的- 单个值
            for (CreditReportModuleConf conf : child) {
                String ci = conf.getInt("id") + "";
                String s = conf.getStr("get_source");
                String columnName = conf.getStr("column_name");
               /* if("credit_company_info".equals(tableName)&&"company_type".equals(columnName)){
                    continue;
                }*/
                String fieldType = conf.get("field_type");
                if("business_date_end".equals(columnName)){
                	System.out.println(111);
                }
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
                        List rs = report.getTableData(true,  companyId, t, cn, ci, "",reportType);
                        if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            for (Object key23 : model.getAttrs().keySet()) {
                                System.out.println(key23+"================="+model.get(key23+""));
                                if(key23.equals(columnName)){
                                	String value= model.get(key23+"")+"";
                                	 value = getValue(reportType, sysLanguage,
											sdf, fieldType, value);
                                    map.put(word_key,value);
                                    continue;
                                }
                            }

                        }
                    }
                } else {
                    //取word里配置的关键词
                    String word_key = conf.get("word_key") + "";
                    if (word_key != null && !"".equals(word_key) && !"null".equals(word_key)) {
                        //取数据
                        List rs = report.getTableData(false, companyId, t, cn, ci, "",reportType);
                        if (rs != null && rs.size() > 0) {
                            BaseProjectModel model = (BaseProjectModel) rs.get(0);
                            for (Object key23 : model.getAttrs().keySet()) {
                                if(key23.equals(columnName)){
                                	String value= model.get(key23+"")+"";
                               	 value = getValue(reportType, sysLanguage,
											sdf, fieldType, value);
                                   map.put(word_key,value);
                                    map.put(word_key, value);
                                    continue;
                                }
                            }

                        }
                    }
                }
            }

            //7 输入框取数
            if ("7".equals(moduleType)) {
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "",reportType);
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    String field_type = module.getStr("field_type");
                    cols.put(column_name, temp_name + "|" + field_type);
                }
                //取数据
                for (int i = 0; i < rows.size(); i++) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    for (String column : cols.keySet()) {
                        String[] strs = cols.get(column).split("\\|");
                        String fieldType = strs.length == 2 ? strs[1] : "";
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        if ("textarea".equals(fieldType)) {
                            //分支机构使用分号换行
                            if("embranchment_count".equals(column)){
                                value = value.replaceAll("\\；", "\n");
                            }
                            value = value.replaceAll("(\\\\r\\\\n|\\\\n)", "\n");
                            map.put(column, value);
                        }else{
                            map.put(column, value);
                        }
                    }
                }
            }

            //8-单选框 - 商业报告付款情况
            if("8".equals(moduleType)){
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "",reportType);
                //取列值
                LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
                //取列值
                for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String get_source = module.getStr("get_source");
                    cols.put(column_name, get_source);
                }
                /*for (int i = 0; i < child.size(); i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    String field_type = module.getStr("field_type");
                    cols.put(column_name, temp_name + "|" + field_type);
                }*/
                //取数据
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
                                html.append(new String(new int[]{0x2611}, 0, 1) + " " + item[1].trim().replace("</br>", "\r") + " ");
                            } else {
                                html.append(new String(new int[]{0x2610}, 0, 1) + " " + item[1].trim().replace("</br>", "\r") + " ");
                            }
                        }
                        Style style = new Style();
                        if(ReportTypeCons.ROC_ZH.equals(reportType)||ReportTypeCons.ROC_EN.equals(reportType)) {
                            style.setFontFamily("PMingLiU");
                        }
                        map.put(column, new TextRenderData(html.toString(), style));
                    }
                }*/
                if (rows!=null && rows.size()>0) {
                    BaseProjectModel model = (BaseProjectModel) rows.get(0);
                    //取单选数据
                    String get_source = "1-极好&2-好&3-一般&4-较差&5-差&6-尚无法评估";
                    String value = model.get("overall_rating")+"";
                    String[] items = get_source.split("&");
                    StringBuffer html = new StringBuffer();
                    for (int j = 0; j < items.length; j++) {
                        String[] item = items[j].split("-");
                        if(ReportTypeCons.ROC_ZH.equals(reportType)){
                            if (value.equals(item[0])) {
                                html.append(new String(new int[]{0x2611}, 0, 1) + " " + item[1].trim().replace("</br>", "\r") + " ");
                            } else {
                                html.append(new String(new int[]{0x2610}, 0, 1) + " " + item[1].trim().replace("</br>", "\r") + " ");
                            }
                        }else{
                            if (item[0].equals(value)) {
                                html.append("(√)" + item[1] + " ");
                            } else {
                                html.append("( )" + item[1] + " ");
                            }
                        }
                    }
                    map.put("overall_rating", html.toString());
                }
            }

            //图形表
            if ("11".equals(moduleType)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo,reportType);
                List<LinkedHashMap<String, String>> datas = BaseWord.formatData(child, rows);
                //jfreechart生成饼图（股东）
                DefaultPieDataset pds = new DefaultPieDataset();
                Double total = 100d;
                for (LinkedHashMap<String, String> m : datas) {
                    Object[] keys = m.keySet().toArray();
                    String n = m.get(keys[0]);
                    String v = m.get(keys[2]);
                    Double value = 0d;
                    if (v != null && !"".equals(v)) {
                        v = m.get(keys[2]);
                        try {
                            value = Double.parseDouble(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    pds.setValue(n, value);
                    total = total-value;
                }
                //如果所有股东投资比例和不等于100%，加上未知项
                if(total!=0) {
                    pds.setValue("未知", total);
                }
                BaseWord.createPieChart(pds, _prePath + "pie.jpg");
                map.put("pie", new PictureRenderData(600, 300, _prePath + "pie.jpg"));
            }

            //行业详情-柱图/线图
            if("10392".equals(tableId)){
                //取行业情况
                CreditCompanyIndustryInfo industryInfo = CreditCompanyIndustryInfo.dao.findFirst("select * from credit_company_industry_info t where t.sys_language=? and t.company_id=? and t.del_flag=0",sysLanguage,companyId);
                String title = "";
                if(industryInfo!=null){
                     title = industryInfo.getStr("chart_title");
                }
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "",reportType);
                List<LinkedHashMap<String, String>> datas = BaseWord.formatData(child,rows);
                //准备图形数据
                DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
                DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
                for (LinkedHashMap<String, String> m : datas) {
                    Object[] keys = m.keySet().toArray();
                    String n = m.get(keys[0]);
                    String v1 = m.get(keys[1]);
                    String v2 = m.get(keys[2]);
                    Double value1 = 0d , value2=0d;
                    try {
                        if (v1 != null && !"".equals(v1)) {
                            v1 = m.get(keys[1]);
                            value1 = Double.parseDouble(v1);
                        }
                        if (v2 != null && !"".equals(v2)) {
                            v2 = m.get(keys[2]);
                            value2 = Double.parseDouble(v2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    barDataSet.addValue(value1, "y1", n);
                    lineDataSet.addValue(value2,"y2",n);
                }
                BaseWord.createBarChart(title,barDataSet,lineDataSet, _prePath + "bar.jpg");
                map.put("bar", new PictureRenderData(600, 300, _prePath + "bar.jpg"));
            }
            //行业情况-GDP-柱图/线图
            if("10353".equals(tableId)){
            	 String gdpPic = webRoot + "/upload/tmp/" + orderCode;
                 if(!new File(gdpPic).exists()){
                     new File(gdpPic).mkdir();
                 }
                 gdpPic = gdpPic + "/" + referenceNum+"gdp";
                //取行业情况
            	CreditCompanyIndustrySituationTitleDict industryInfo = CreditCompanyIndustrySituationTitleDict.dao.findFirst("select * from credit_company_industry_situation_title_dict t where  t.del_flag=0");
                String title = "";
                if(industryInfo!=null){
                     title = industryInfo.getStr("title");
                }
                map.put("hangyexinxi_title", title);
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "",reportType);
                List<LinkedHashMap<String, String>> datas = BaseWord.formatData(child,rows);
                //准备图形数据
                DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
                DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
                for (LinkedHashMap<String, String> m : datas) {
                    Object[] keys = m.keySet().toArray();
                    String n = m.get(keys[0]);
                    String v1 = m.get(keys[1]);
                    String v2 = m.get(keys[2]);
                    Double value1 = 0d , value2=0d;
                    try {
                        if (v1 != null && !"".equals(v1)) {
                            v1 = m.get(keys[1]);
                            value1 = Double.parseDouble(v1);
                        }
                        if (v2 != null && !"".equals(v2)) {
                            v2 = m.get(keys[2]);
                            value2 = Double.parseDouble(v2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    barDataSet.addValue(value1, "GDP（Unit Billon Yuan）", n);
                    lineDataSet.addValue(value2,"Growth Rate(%）",n);
                }
                BaseWord.createBarChart(title,barDataSet,lineDataSet, gdpPic + "bar.jpg");
                map.put("bar_gdp", new PictureRenderData(600, 300, gdpPic + "bar.jpg"));
            }
        }
        
        //财务模块生成
        List<String> excelPath = new ArrayList<>();
        List<CreditCompanyFinancialStatementsConf> finanConfList = CreditCompanyFinancialStatementsConf.dao.findByWhere(" where company_id=? and del_flag=0 ",companyId);
        if(finanConfList!=null && finanConfList.size()>0) {
            for (CreditCompanyFinancialStatementsConf financialConf : finanConfList) {
                String begin = financialConf.get("date1");
                String end = financialConf.get("date2");
                String finanId = financialConf.getInt("id") + "";
                int realFinanceTypes = financialConf.getInt("type") ;
                //取到对应的财务类型
                /*Integer  financeType = -1;
                for (Integer tempType :  FinanceService.FINANCIAL_TYPE) {
                    financeType =  getFinancialType(companyId);
                    if(financeType!=-1) {break;}
                }*/
               //word里财务模块生成
               financial(financialConf, map,reportType);
              //生成财务报告EXCEL
               String expath = financialExcel(realFinanceTypes,finanId,_prePath,orderId,userid,begin,end);
               excelPath.add(expath);
               //财务-评价
               map.put("financial_eval", financialEval(financialConf,reportType,sysLanguage));
            }


        }else {
            map.put("financial_eval", "");
            map.put("financial", new MiniTableRenderData(null));
            map.put("bigFinancial", new MiniTableRenderData(null));
        }
        map = (HashMap<String, Object>) dealDataMapByreportType(reportType,map);
        if(extend!=null){
        	map.put("order_code", extend.get("order_code"));
        }
        //在指定路径生成word
        synchronized (o){
            BaseWord.buildNetWord(map, tplPath, _prePath + "_p.docx");
        }
        String wordPath = "";
        try{
            //重新添加图片并生成word
            synchronized (o){
                wordPath = replaceImg(_prePath, orderId, userid, companyId, sysLanguage);
            }
        }catch (Exception e){
            e.printStackTrace();
            sendErrorEmail(order,e);
        }

        //发送邮件
        synchronized (o){
            {
                String _pre = "http://" + ip + ":" + serverPort + "/";
                List<Map<String, String>> fileList = new ArrayList<>();
                Map<String, String> fileMap = new HashMap();
                fileMap.put(reportName + ".doc",_pre + wordPath);
                //检查是否上传成功
                File file = new File(_prePath+".docx");
                boolean isUploadSuccess = false;
                if(file.exists()&&file.length()>0L){
                    isUploadSuccess = Roc102.checkUpload(_pre + wordPath,file.length());
                }
                if(!isUploadSuccess){
                    sendErrorEmail(order,new RuntimeException(" 服务器文件生成路径:"+_prePath+".docx; file.exists="+file.exists()+"; file.length="+file.length()+"; wordPath="+_pre + wordPath));
                }{
                    if(excelPath!=null&&excelPath.size()!=0) {
                        int count = 0;
                        for (String   singlePath : excelPath) {
                            fileMap.put(reportName+ ++count+ ".xls", _pre + singlePath);
                        }

                    }
                    fileList.add(fileMap);
                    sendMail(reportName,customId, fileList);
                }


            }
        }
      /*旧版发送邮件代码,已经加上检查邮件发送失败的补丁
       synchronized(o){
            String _pre = "http://" + ip + ":" + serverPort + "/";
            List<Map<String, String>> fileList = new ArrayList<>();
            Map<String, String> fileMap = new HashMap();
            fileMap.put(reportName + ".doc",_pre + wordPath);//对应文件名和ftp路径
            for(String path:excelPath) {
                fileMap.put(reportName + ".xls", _pre + path);//对应文件名和财务excel 的ftp路径
            }
            fileList.add(fileMap);
            sendMail(reportName,customId, fileList);
        }*/
    }

    /**
     * 针对表格数据的特殊处理
     * @param key
     * @param child
     * @param rows
     * @param reportType
     */
    private static void specialHandlingForTable(String key, List<CreditReportModuleConf> child, List<BaseProjectModel> rows, String reportType,String sysLanguage) {
        BaseProjectModel model = rows.get(0);
        //报告摘要模块处理
        if("zhaiyao".equals(key)){
            //合并员工人数和员工统计时间
            try{
                //移除统计时间
                removeConf(child,  "emp_num_date");
                //合并数据
                String empNum = null;
                if(model.get("emp_num")!=null){
                    empNum = model.get("emp_num")+"";
                }
                String empNumDate =  String.valueOf(model.get("emp_num_date"));
                empNumDate = timeRangeHandling(empNumDate, "", "yyyy-mm-dd","yyyy年MM月dd日");
                if(!StringUtils.isEmpty(empNum)){
                    if(!StringUtils.isEmpty(empNumDate)){
                        if(ReportTypeCons.BUSI_ZH.equals(reportType)){
                            model.set("emp_num",empNum+" ("+empNumDate+")");
                        }else{
                            model.set("emp_num","("+empNumDate+") "+empNum);
                        }
                    }else{
                        model.set("emp_num",empNum);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //合并 注册资本类型、注册资本、注册资本币种
            try{
                mergerHandling2( child,   model, "registered_capital", reportType,  sysLanguage,"registered_capital","currency","capital_type","currency","capital_type");
               /* //删除模板中的注册资本币种、注册资本类型
                removeConf(child,"currency","capital_type");
                String money = model.get("registered_capital")+"";//注册资本
                String currency = model.get("currency")+"";//注册资本币种
                String capitalType = model.get("capital_type")+"";//注册资本类型
                //修改注册资本的temp_name
                if(isNotNull(capitalType)){
                    alertConf("registered_capital",capitalType,child,true,sysLanguage,reportType);
                }
                //修改值 合并值
                if(isNotNull(money)){
                    if(isNotNull(currency)){
                        currency = ReportInfoGetDataController.dictIdToString(currency, reportType, sysLanguage);
                        if(ReportTypeCons.BUSI_ZH.equals(reportType)){
                            model.set("registered_capital",money+" "+currency);
                        }else{
                            model.set("registered_capital",currency+" "+money);
                        }
                    }else{
                        model.set("registered_capital",money);
                    }
                }*/
            }catch (Exception e){
                e.printStackTrace();
            }
            //钱、币种、日期 类型的合并处理
            try{
                mergerHandling( child,   model,   reportType,  sysLanguage,"business_income","businessincome_type","time_period_for_business_income_statistics");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                mergerHandling(   child,   model,   reportType,  sysLanguage,"total_assets","totalassets_type","time_period_for_total_assets");
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                mergerHandling(  child,   model,   reportType,  sysLanguage,"period_for_equity_of_stockholder","periodofstockholder_type","time_period_for_equity_of_stockholder");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("creditanalysis".equals(key)){ //信用分析模块处理
            try {
                //信用额度、信用额度币种处理
                mergerHandling(  child,   model,   reportType,  sysLanguage,"amount","currency");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("regist".equals(key)){ //注册信息模块处理
            try {
                //注册资本处理
                mergerHandling2( child, model, "capital_type", reportType,  sysLanguage,"registered_capital","currency","capital_type","currency","registered_capital");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        rows.clear();
        rows.add(model);
    }

    /**
     *  //钱、币种、日期 类型的合并处理
     * @param child
     * @param model
     * @param reportType
     * @param sysLanguage
     * @param requiredColumns
     * @param removeColumns
     */
    private static void mergerHandling(List<CreditReportModuleConf> child, BaseProjectModel model, String reportType, String sysLanguage,String requiredColumns,String ...removeColumns) {
        removeConf(child,removeColumns);
        String money = model.get(requiredColumns)+"";//钱
        String currency = model.get(removeColumns[0])+"";//币种类型
        String dateStr;//统计时间
        try{
            dateStr = model.get(removeColumns[1])+"";
        }catch (Exception e){
            e.printStackTrace();
            dateStr = "";
        }
        //格式化日期
        dateStr = timeRangeHandling(dateStr, " - ", "yyyy-mm-dd","yyyy年MM月dd日");
        String targetStr = "";

        if(isNotNull(money)){
            if(isNotNull(currency)){
                currency = ReportInfoGetDataController.dictIdToString(currency, reportType, sysLanguage);
                if(isNotNull(currency)){
                    if(ReportTypeCons.BUSI_ZH.equals(reportType)){
                        targetStr = money+" "+currency;
                    }else{
                        targetStr = currency+" "+money;
                    }
                }else{
                    targetStr = money;
                }
                if(isNotNull(dateStr)){
                     targetStr = targetStr+" ("+dateStr+")";
                }
            }else{
                model.set("registered_capital",money);
            }
        }
        model.set(requiredColumns,targetStr);
    }

    /**
     *
     * @param child
     * @param model
     * @param sourceField
     * @param reportType
     * @param sysLanguage
     * @param moneyColumn
     * @param currencyColumn
     * @param capitalTypeColumn
     * @param removeColumns
     */
    private static void mergerHandling2(List<CreditReportModuleConf> child, BaseProjectModel model, String sourceField,String reportType, String sysLanguage,String moneyColumn,String currencyColumn,String capitalTypeColumn,String ...removeColumns) {
        try {
            //删除模板中的注册资本币种、注册资本类型
            removeConf(child,removeColumns);
            String money = model.get(moneyColumn)+"";//注册资本
            String currency = model.get(currencyColumn)+"";//注册资本币种
            String capitalType = model.get(capitalTypeColumn)+"";//注册资本类型
            boolean targetFieldIsSelect = true;
            if(!isNotNull(capitalType)){
                targetFieldIsSelect = false;
                if(ReportTypeCons.BUSI_ZH.equals(reportType)){
                    capitalType = "注册资本";
                }else{
                    capitalType = "Registered Capital";
                }
            }
            //修改注册资本的temp_name
            alertConf(sourceField,capitalType,child,targetFieldIsSelect,sysLanguage,reportType);
            //修改值 合并值
            if(isNotNull(money)){
                if(isNotNull(currency)){
                    currency = ReportInfoGetDataController.dictIdToString(currency, reportType, sysLanguage);
                    if(ReportTypeCons.BUSI_ZH.equals(reportType)){
                        model.set(sourceField,money+" "+currency);
                    }else{
                        model.set(sourceField,currency+" "+money);
                    }
                }else{
                    model.set(sourceField,money);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 时间范围格式化处理
     * @param dateStr
     * @param separater
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    private static String timeRangeHandling(String dateStr, String separater, String sourceFormat,String targetFormat) {
        try{
            if(!isNotNull(dateStr)){
                return  null;
            }
            SimpleDateFormat sourcesdf = new SimpleDateFormat(sourceFormat);
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);
            if(isNotNull(separater)){
                Date date1 = sourcesdf.parse( dateStr.split(separater)[0].trim());
                Date date2 = sourcesdf.parse( dateStr.split(separater)[1].trim());
                return targetSdf.format(date1)+separater+targetSdf.format(date2);
            }else{
                Date date1 = sourcesdf.parse(dateStr);
                return  targetSdf.format(date1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    /**
     * 修改配置
     * @param sourceField
     * @param targetName
     * @param confs
     * @param isSelect
     */
    private static void alertConf(String sourceField, String targetName, List<CreditReportModuleConf> confs, boolean isSelect,String sysLanguage,String reportType) {
        if(confs!=null){
            for (CreditReportModuleConf conf : confs) {
              if(isNotNull(sourceField)&&sourceField.equals(conf.get("column_name"))){
                  if(isSelect){
                      targetName = ReportInfoGetDataController.dictIdToString(targetName, reportType, sysLanguage);
                  }
                  conf.set("temp_name",targetName);
                  conf.set("field_type","text");
              }
            }
        }
    }

    private static boolean isNotNull(String str) {
        return StringUtils.isNotEmpty(str)&&!"null".equals(str);
    }

    /**
     * 根据字段名移除conf配置
     * @param child
     * @param columns
     */
    private static void removeConf(List<CreditReportModuleConf> child, String ...columns) {
            for (int j=0;j<columns.length;j++) {
                if(StringUtils.isNotEmpty(columns[j])){
                    for (int i=0;i<child.size();i++) {
                        if (columns[j].equals(child.get(i).get("column_name"))){
                            child.remove(i);
                            break;
                        }
                    }
                }
            }
    }

    private static String getValue(String reportType, String sysLanguage,
			SimpleDateFormat sdf, String fieldType, String value) {
		//下拉选择
		if ("select".equals(fieldType)) {
		    value = !"".equals(value) ? ReportInfoGetDataController.dictIdToString(value, reportType, sysLanguage) : "";
		}
		//下拉多选
		else if("select2".equals(fieldType)){
		    String[] vals = value.split("\\$");
		    String selName = "";
		    for(String val : vals){
		        selName += ReportInfoGetDataController.dictIdToString(val, reportType, sysLanguage) + ",";
		    }
		    if(selName.contains(",")) {
		        selName = selName.substring(0, selName.length() - 1);
		    }
		    value = selName;
		}
		else if ("country".equals(fieldType)) {
		    value = !"".equals(value) ? CountryModel.getCountryById(value,reportType,sysLanguage) : "N/A";
		}//处理千位符号
		else if ("money".equals(fieldType)) {
		    try {
		        DecimalFormat df = new DecimalFormat("###,###.##");
		        NumberFormat nf = NumberFormat.getInstance();
		        value = df.format(nf.parse(value));
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		}
		//日期
		else if("date".equals(fieldType)){
			 
			try {
				value = detailDate(sdf.parse(value),reportType);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		   
		}
		return value;
	}

    /**
     * poi-tl工具类不支持在table中插入图片
     * 1. 第一次生成时table中插入{{@var}}占位符
     * 2. 第二次用插入图片的方法替换掉占位符
     * 具体参见poi-tl的文档
     * @param tarPath
     * @param orderId
     * @param userid
     * @param companyId
     * @param sysLanguage
     */
    public static String replaceImg(String tarPath,String orderId,Integer userid,String companyId,String sysLanguage) throws  Exception {
        //图片保存路径
        String brandPath = PathKit.getWebRootPath()+ "/upload/brand";
        //获取图片
        List<CreditCompanyBrandandpatent> list = CreditCompanyBrandandpatent.dao.getBrandandpatent(companyId,sysLanguage);
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (CreditCompanyBrandandpatent model : list) {
            Integer id = model.getInt("id");
            String url = model.getStr("brand_url");
            if(StringUtils.isNotEmpty(url)) {
                map.put("img" + id, new PictureRenderData(120, 120, downloadFile(url, brandPath)));
            }
        }
        String sourcePath = tarPath + "_p.docx";
        String targetPath = tarPath + ".docx";
        BaseWord.buildWord(map, sourcePath, targetPath);
        //上传文件
        String resultPath = null;
        try {
            resultPath = BaseWord.uploadReport(targetPath, orderId, userid);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resultPath = BaseWord.uploadReport(targetPath, orderId, userid);
            } catch (Exception ex) {
                ex.printStackTrace();
                resultPath = BaseWord.uploadReport(targetPath, orderId, userid);
            }

        }
        return resultPath;
    }

    /**
     * 发送带附件邮件
     * @param customerId
     * @param fileList
     * @throws Exception
     */
    public static void sendMail(String reportName,String customerId,List<Map<String, String>> fileList) throws Exception{
        //发送邮件
        CreditCustomInfo  customInfo = CreditCustomInfo.dao.getCustomerById(customerId);
        if(customerId!=null) {
                String email = customInfo.getStr("email");
                System.out.println("email==================:"+email);
                //email = "hu_cheng86@126.com";
                new SendMailUtil(email, "", reportName, "", fileList).sendEmail();
        }
    }

    /**
     * 下载专利图片
     * @param netUrl  网络路径
     * @param path   保存位置
     */
    public static String downloadFile(String netUrl,String path) {
        InputStream iputstream = null;
        BufferedOutputStream bos = null;
        try {
            byte[] buff = new byte[512];
            URL url = new URL(netUrl);
            path = path + url.getPath();
            File file = new File(path);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            //设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            iputstream = uc.getInputStream();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            int count = 0;
            while ((count = iputstream.read(buff)) != -1) {
                bos.write(buff, 0, count);
            }
        } catch (Exception e) {
            System.out.println(netUrl);
            e.printStackTrace();
        } finally {
            if (iputstream != null) {
                try {
                    iputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path;
    }

    /**
     * 财务模板生成
     * @param financialConf
     * @param map
     * @param reportType
     * @return
     */
    public static List<RowRenderData> financial(CreditCompanyFinancialStatementsConf financialConf,HashMap<String, Object> map,String reportType) {
        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        if(financialConf==null) {
            return rowList;
        }
        TableStyle tableStyle = new TableStyle();
        tableStyle.setHasBorder(false);
        String begin = financialConf.get("date1");
        String end = financialConf.get("date2");
        String lrbegin = financialConf.get("date3");
        String lrend = financialConf.get("date4");
        String financialConfId = financialConf.getInt("id") + "";
        int financeType = financialConf.getInt("type") ;
        String companyName = financialConf.get("company_name")+"";//公司名称
        //判断语言类型
        String language = "612";
        String currencyStr = "单位";
        boolean isEnglish=false;
        if("EN".equals(ReportTypeCons.whichLanguage(reportType))){
            language = "613";
            currencyStr = "Unit";
            tableStyle.setHasBorder(false);
            isEnglish=true;
            tableStyle.setAlign(STJc.LEFT);
        }
        String currencyId = financialConf.get("currency")+"";//币种id
        String currency = ReportInfoGetDataController.dictIdToString(currencyId,reportType,language);
        String currencyUnitId = financialConf.get("currency_ubit")+"";//币种单位id
        String currencyUnit = ReportInfoGetDataController.dictIdToString(currencyUnitId,reportType,language);
        if("21".equals(reportType)){
            map.put("financialCompanyName",companyName);
            map.put("financialCurrency",currency);
            String currencyUnit396 = ReportInfoGetDataController.dictIdToStringSpecified(currencyUnitId,"detail_content");
            map.put("financialUnit",currencyUnit396);
        }



        //CreditCompanyFinancialStatementsConf config = CreditCompanyFinancialStatementsConf.dao.findById(financialConfId);
        //String companyName = config.get("company_name")+"";//公司名称
        try {
            if(!StrUtils.isEmpty(begin))
            begin = detailDate(sdf.parse(begin),reportType);
            if(!StrUtils.isEmpty(end))
            end = detailDate(sdf.parse(end),reportType);
            if(!StrUtils.isEmpty(lrbegin))
            	lrbegin = detailDate(sdf.parse(lrbegin),reportType);
            if(!StrUtils.isEmpty(lrend))
            	lrend = detailDate(sdf.parse(lrend),reportType);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //(financeType==3){
            //todo 大数渲染
       // }else{
            //财务
            //取数据
            //Integer type = new ReportInfoGetDataController().getFinanceDictByReportType(reportType);
            List<CreditCompanyFinancialEntry> finDataRows = FinanceService.getFinancialEntryList(financialConfId, financeType+"");
            int j = 0;
            Integer old = null;
            for (CreditCompanyFinancialEntry ccf : finDataRows) {
                Integer son_sector = ccf.getInt("son_sector");
                if(!"21".equals(reportType)) {
                    //判断新模块，第一行要加标题
                    if (old == null) {
                        old = son_sector;
                    } else {
                        if (son_sector.intValue() != old.intValue()) {
                            old = son_sector;
                            j = 0;
                        }
                    }
                    //j=0表示第一条
                    if (j == 0) {
                        String title = "";
                        switch (son_sector.intValue()) {
                            case 1:
                            	if(isEnglish){
                            		title = "Key Financial Items";
                            	}else{
                            		title = "关键财务项目";
                            	}
                                break;
                            case 2:
                                //title = "流动资产";
                            	if(isEnglish){
                            		title="Financial Statement";
                            	}else{
                            		title="财务报表";
                            	}
                                break;
                            case 3:
                                //title = "非流动资产";
                            	title = "";
                                break;
                            case 4:
                                //title = "流动负债";
                            	title = "";
                                break;
                            case 5:
                                //title = "非流动负债";
                            	title = "";
                                break;
                            case 6:
                                //title = "负债及所有者权益";
                            	title = "";
                                break;
                            case 7:
                                //title = "毛利润";
                            	if(isEnglish){
                            		title = "Income Statement";
                            	}else{
                            		title = "利润表";
                            	}
                                break;
                            case 8:
                                //title = "营业利润";
                            	title = "";
                                break;
                            case 9:
                                //title = "税前利润";
                            	title = "";
                                break;
                            case 10:
                                //title = "所得税费用";
                            	title = "";
                                break;
                            case 11:
                            	if(isEnglish){
                            		title = " Key Ratios";
                            	}else{
                            		title = "重要比率表";
                            	}
                                break;
                        }
                        if (financeType == 1 || financeType == 2) {
                            rowList.add(RowRenderData.build(
                                    new TextRenderData(""),
                                    new TextRenderData(""),
                                    new TextRenderData("")));
                        }


                        //大标题
                        if(StringUtils.isNotBlank(title)){
                        	if("利润表".equals(title)||"重要比率表".equals(title)||
                        			"Income Statement".equals(title)||"Key Ratios".equals(title)){
                        		//居中 
                        		Style titileStyle = new Style();
                        		titileStyle.setBold(true);
                        		titileStyle.setAlign(STJc.CENTER);
                        		titileStyle.setFontFamily("宋体");
                        		titileStyle.setFontSize(11);
                        		rowList.add(RowRenderData.build(
                        				new TextRenderData(""),
                        				new TextRenderData(title, titileStyle),
                        				new TextRenderData("")));
                        	}else{
                        		//靠左
                        		Style titileStyle = new Style();
                        		titileStyle.setColor("843C0B");
                        		titileStyle.setBold(true);
                        		rowList.add(RowRenderData.build(
                        				new TextRenderData(title, titileStyle),
                        				new TextRenderData(""),
                        				new TextRenderData("")));
                        	}
                        }

                        if("财务报表".equals(title)||"Financial Statement".equals(title)){
                        	//添加小标题 居中行
                        	Style header = new Style();
                            header.setBold(true);
                            header.setAlign(STJc.CENTER);
                            String t="资产负债表";
                            if(isEnglish){
                            	t="Balance Sheet";
                            }
                        	rowList.add(RowRenderData.build(
                        			new TextRenderData(""),
                        			new TextRenderData(t, header),
                        			new TextRenderData("")));
                        }
                        if("关键财务项目".equals(title)||"财务报表".equals(title)
                        		||"利润表".equals(title)||"重要比率表".equals(title)||
                        		"Key Financial Items".equals(title)||"Financial Statement".equals(title)
                        		||"Income Statement".equals(title)||"Key Ratios".equals(title)){
                        	//添加币种行
                        	Style header = new Style();
                            header.setBold(true);
                            header.setFontFamily("宋体");
                            header.setFontSize(11);
                            String c=currencyStr + "：" + currency + "（" + currencyUnit + "）";
                            if(isEnglish){
                            	c=currencyStr + "" + currency + "" + currencyUnit + "";
                            }
                        	rowList.add(RowRenderData.build(
                        			new TextRenderData(""),
                        			new TextRenderData(""),
                        			new TextRenderData(c, header)));
                        	//添加时间
                        	if("利润表".equals(title)||"Income Statement".equals(title)){
                        		//利润读取date3 date4  其他都是date1、date2
                        		rowList.add(RowRenderData.build(
                            			new TextRenderData(""),
                            			new TextRenderData(lrbegin,header),
                            			new TextRenderData(lrend, header)));
                        	}else{
                        		rowList.add(RowRenderData.build(
                            			new TextRenderData(""),
                            			new TextRenderData(begin,header),
                            			new TextRenderData(end, header)));
                        	}
                        }

                    }
                }
                String itemName = ccf.getStr("item_name");
                String beginValue = ccf.getInt("begin_date_value")+"";
                if("0".equals(beginValue+"")){
                    beginValue = "--";
                }
                String endValue = ccf.get("end_date_value")+"";
                if("0".equals(endValue+"")){
                    endValue = "--";
                }
                /*Integer is_sum_option = ccf.getInt("is_sum_option");
                Style sumStyle = new Style();
                if (is_sum_option.intValue() == 1) {
                    sumStyle.setBold(true);
                }*/
                
                Integer is_sum_option = ccf.getInt("is_sum_option");
                Style sumStyleValue = new Style();
                sumStyleValue.setFontSize(11);
                sumStyleValue.setFontFamily("宋体");
                Style sumStyle = new Style();
                sumStyle.setFontSize(11);
                sumStyle.setFontFamily("宋体");
                if((financeType==1||financeType==2)&&("流动资产合计".equals(itemName)||"Total Current Assets".equals(itemName)||
                		"资产总额".equals(itemName)||"Total Assets".equals(itemName)||
                		"流动负债合计".equals(itemName)||"TOTAL CURRENT LIABILITIES".equals(itemName)||
                		"负债合计".equals(itemName)||"Equities".equals(itemName)||
                		"所有者权益".equals(itemName)||"".equals(itemName)||
                		"负债及所有者权益合计".equals(itemName)||"Total liabilities & equities".equals(itemName)||
                		"毛利润".equals(itemName)||"Gross Profit".equals(itemName)||
                		"营业利润".equals(itemName)||"Operating Profit".equals(itemName)||
                		"税前利润".equals(itemName)||"Profit before tax".equals(itemName)||
                		"净利润".equals(itemName)||"Profits".equals(itemName))){
                	sumStyle.setBold(true);
                }
                RowRenderData tempRow = RowRenderData.build(new TextRenderData(itemName, sumStyle), new TextRenderData(beginValue.toString(),sumStyleValue), new TextRenderData(endValue.toString(),sumStyleValue));
                tempRow.setStyle(tableStyle);
                rowList.add(tempRow);
                j++;
            }
            //财务-表格
            if(financeType==3||financeType==4) {
                 map.put("bigFinancial", new MiniTableRenderData(rowList));
            }else {
                 map.put("financial", new MiniTableRenderData(rowList));
            }
        //}
        return rowList;
    }

    /**
     * 财务生成Excel
     * @param financeType
     * @param financialConfId
     * @param _prePath
     * @param orderId
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    public static String financialExcel(int financeType,String financialConfId,String _prePath,String orderId,int userid,String begin,String end){
        String filePath = "";
       // if(financeType==3){
            //todo 大数渲染
        //}else{
            //财务
            //Integer type = new ReportInfoGetDataController().getFinanceDictByReportType(reportType);
            List<CreditCompanyFinancialEntry> finDataRows = FinanceService.getFinancialEntryList(financialConfId, financeType+"");
            for(CreditCompanyFinancialEntry entity : finDataRows){
                String a = entity.get("begin_date_value")+"";
                String b = entity.get("end_date_value")+"";
                if("0".equals(a)){
                    entity.set("begin_date_value","--");
                }
                if("0".equals(b)){
                    entity.set("end_date_value","--");
                }
            }
            FinancialExcelExport export = new FinancialExcelExport(finDataRows,begin,end);
            try {
                String path = _prePath + ".xls";
                export.downloadExcel(path);
                //上传文件
                filePath = BaseWord.uploadReport(path, orderId, userid);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        //}
        return filePath;
    }

    /**
     * 财务模块-评价
     * @param statementsConf
     * @param reportType
     * @param sysLanguage
     * @return
     */
    public static String financialEval(CreditCompanyFinancialStatementsConf statementsConf,String reportType,String sysLanguage) {
        ReportInfoGetDataController reportInfoGetDataController = new ReportInfoGetDataController();
        String profSumup = getIntToString(statementsConf.getInt("profitablity_sumup"));
        String profDetail = statementsConf.getStr("profitablity_detail");
        String liquSumup = getIntToString(statementsConf.getInt("liquidity_sumup"));
        String liquDetail = statementsConf.getStr("liquidity_detail");
        String leverSumup = getIntToString(statementsConf.getInt("leverage_sumup"));
        String leverDetail = statementsConf.getStr("leverage_detail");
        String overSumup = getIntToString(statementsConf.getInt("overall_financial_condition_sumup"));
        String overDetail = statementsConf.getStr("overall_financial_condition_detail");

        StringBuffer str = new StringBuffer();
        str.append("盈利能力：" + (!"".equals(profSumup) ? reportInfoGetDataController.dictIdToString(profSumup,reportType,sysLanguage) : ""));
        str.append("\n");
        str.append(profDetail);
        str.append("\n");
        str.append("周转能力：" + (!"".equals(liquSumup) ? reportInfoGetDataController.dictIdToString(liquSumup,reportType,sysLanguage) : ""));
        str.append("\n");
        str.append(liquDetail);
        str.append("\n");
        str.append("融资能力：" + (!"".equals(leverSumup) ? reportInfoGetDataController.dictIdToString(leverSumup,reportType,sysLanguage) : ""));
        str.append("\n");
        str.append(leverDetail);
        str.append("\n");
        str.append("目标公司的总体财务状况：" + (!"".equals(overSumup) ? reportInfoGetDataController.dictIdToString(overSumup,reportType,sysLanguage) : ""));
      /*  str.append("\n");
        str.append(overDetail);*/
        return str.toString();
    }

    /**
     * Integer 转 String
     * @param intValue
     * @return
     */
    public static String getIntToString(Integer intValue){
        if(intValue!=null){
            return intValue.toString();
        }else{
            return "";
        }
    }

    /**
        * 找到当前公司关联下的,内容不为空的所有财务类型
     * @param companyId
     * @return  当前公司关联下的,内容不为空的所有财务类型
     */
    public static  List<Integer> getFinancialType (String companyId) {

        List<Integer> list = new ArrayList<>();

        try {
            // 查询公司id下的财务配置
            List<CreditCompanyFinancialStatementsConf> flagList = CreditCompanyFinancialStatementsConf.dao.find(
                    "select * from credit_company_financial_statements_conf where del_flag=0 and company_id=?  ",
                    Arrays.asList(new String[]{companyId   }).toArray());
            if(flagList==null||flagList.size()==0){
                return null;
            }
            String type = null;
            for (CreditCompanyFinancialStatementsConf entity : flagList) {
                String confId = entity.get("id")+"";//财务配置id
                  type = entity.get("type")+"";//财务类型
                if (StrUtils.isEmpty(confId, type+"")) { return null; }

                List<CreditCompanyFinancialEntry> targetValueList =  CreditCompanyFinancialEntry.dao.find(
                        "select begin_date_value,end_date_value from credit_company_financial_entry where del_flag=0  and conf_id=? and type=? ",
                        Arrays.asList(new String[] { confId ,type+""}).toArray());

                if(targetValueList!=null) {
                    for (CreditCompanyFinancialEntry entity1 : targetValueList) {
                        Integer value1 = entity1.getInt("begin_date_value");
                        Integer value2 = entity1.getInt("end_date_value");
                        System.out.println(value1);
                        System.out.println(value2);
                         if((value1!=null&&value1!=0)||(value2!=null&&value2!=0)) {list.add(Integer.parseInt(type));break;}
                    }

                }


            }



        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }




}
