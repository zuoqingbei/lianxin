package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.ordermanager.model.*;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.usercenter.controller.finance.FinanceService;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.util.Config;
import com.jfinal.kit.PathKit;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基本信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BaseInfoZh {
    //ftp文件服务器 ip
    public static final String ip = Config.getStr("ftp_ip");
    //ftp端口 9980
    public static final int serverPort = Config.getToInt("searver_port");

    public static void main(String []args){
        try {
            /*String urlStr = "http://120.27.46.160:9980/report_type/2018-12-17/396-20181217173409.docx";
            URL url = new URL(urlStr);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            //设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            InputStream iputstream = uc.getInputStream();*/
            //System.out.println(file.getName());
            //BaseWord.buildWord(null, iputstream, "");

            String str = BaseInfoZh.downloadFile("http://tm-image.qichacha.com/a460ea8b52eda230294f1bb618c3dfc8.jpg","h:/");
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 报告生成
     * @param order  订单
     * @param userid 当前登录人
     */
    public static void reportTable(CreditOrderInfo order,String reportType, Integer userid) {
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> map = new HashMap<String, Object>();

        //获取报告信息
        ReportTypeModel reportTypeModel = ReportTypeModel.dao.findById(reportType);
        //报告文件路劲
        String tplPath = "http://"+ ip + ":" + serverPort +"/"+ reportTypeModel.getStr("tpl_path");
        //报告名称
        String reportName = reportTypeModel.getStr("name");

        //获取订单信息
        String companyId = order.getStr("company_id");
        String customId = order.getStr("custom_id");
        String orderId = order.getInt("id") + "";

        //获取公司信息
        CreditCompanyInfo companyInfo = CreditCompanyInfo.dao.findById(companyId);
        String sysLanguage = companyInfo.getInt("sys_language") + "";
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
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = null;
                if ("s".equals(tableType)) {
                    table = BaseWord.createTableS(child, rows);
                } else if ("h".equals(tableType)) {
                    table = BaseWord.createTableH(child, rows);
                }else if("z".equals(tableType)){
                    BaseWord.createTableZ(child,rows,map);
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
                        List rs = report.getTableData(true,  companyId, t, cn, ci, "");
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
                        List rs = report.getTableData(true, companyId, t, cn, ci, "");
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
                            value = value.replaceAll("(\\\\r\\\\n|\\\\n)", "\n");
                            map.put(column, value);
                        }else{
                            map.put(column, value);
                        }
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
                List<LinkedHashMap<String, String>> datas = BaseWord.formatData(child, rows);
                //jfreechart生成饼图（股东）
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

            //行业详情-柱图/线图
            if("行业情况".equals(tempName)){
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, "");
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
                BaseWord.createBarChart("",barDataSet,lineDataSet, _prePath + ".jpg");
                map.put("bar", new PictureRenderData(600, 300, _prePath + ".jpg"));
            }
        }

        //财务模块生成
        String excelPath = "";
        List<CreditCompanyFinancialStatementsConf> finanConfList = CreditCompanyFinancialStatementsConf.dao.findByWhere(" where company_id=? and del_flag=0 ",companyId);
        if(finanConfList!=null && finanConfList.size()>0) {
            CreditCompanyFinancialStatementsConf statementsConf = finanConfList.get(0);
            String finanId = statementsConf.getInt("id") + "";
            //财务-表格
            map.put("financial", financial(reportType, finanId));
            //财务-评价
            map.put("financial_eval", financialEval(statementsConf));
            //生成财务报告
            excelPath = financialExcel(reportType,finanId,_prePath,orderId,userid);
        }

        //生成word
        BaseWord.buildNetWord(map, tplPath, _prePath + ".docx");
        //重新添加图片并生成word
        String wordPath = replaceImg(_prePath, orderId, userid, companyId, sysLanguage);

        //发送邮件
        String _pre = "http://" + ip + ":" + serverPort + "/";
        List<Map<String, String>> fileList = new ArrayList<>();
        Map<String, String> fileMap = new HashMap();
        fileMap.put(reportName,_pre + wordPath);
        if(!"".equals(excelPath)) {
            fileMap.put(reportName, _pre + excelPath);
        }
        fileList.add(fileMap);
        sendMail(customId, fileList);
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
    public static String replaceImg(String tarPath,String orderId,Integer userid,String companyId,String sysLanguage) {
        //图片保存路径
        String brandPath = PathKit.getWebRootPath()+ "/upload/brand";
        //获取图片
        List<CreditCompanyBrandandpatent> list = CreditCompanyBrandandpatent.dao.getBrandandpatent(companyId,sysLanguage);
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (CreditCompanyBrandandpatent model : list) {
            Integer id = model.getInt("id");
            String url = model.getStr("brand_url");
            map.put("img" + id, new PictureRenderData(120, 120, downloadFile(url,brandPath)));
        }
        String sourcePath = tarPath + ".docx";
        String targetPath = tarPath + "_p.docx";
        BaseWord.buildWord(map, sourcePath, targetPath);
        //上传文件
        return BaseWord.uploadReport(targetPath, orderId, userid);
    }

    /**
     * 发送带附件邮件
     * @param customerId
     * @param fileList
     */
    public static void sendMail(String customerId,List<Map<String, String>> fileList){
        //发送邮件
        CreditCustomInfo  customInfo = CreditCustomInfo.dao.getCustomerById(customerId);
        if(customerId!=null) {
            try {
                //String email = customInfo.getStr("email");
                String email = "hu_cheng86@126.com";
                new SendMailUtil(email, "", "商业信息报告", "", fileList).sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
     * @param reportType
     * @param financialConfId
     * @return
     */
    public static MiniTableRenderData financial(String reportType,String financialConfId) {
        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        //取数据
        Integer type = new ReportInfoGetDataController().getFinanceDictByReportType(reportType);
        List<CreditCompanyFinancialEntry> finDataRows = FinanceService.getFinancialEntryList(financialConfId, type);
        int j = 0;
        Integer old = null;
        for (CreditCompanyFinancialEntry ccf : finDataRows) {
            Integer son_sector = ccf.getInt("son_sector");
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
                String unit = "";
                switch (son_sector.intValue()) {
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
                rowList.add(RowRenderData.build(
                        new TextRenderData(""),
                        new TextRenderData(""),
                        new TextRenderData("")));

                //大标题
                Style titileStyle = new Style();
                titileStyle.setColor("843C0B");
                titileStyle.setBold(true);
                rowList.add(RowRenderData.build(
                        new TextRenderData(title, titileStyle),
                        new TextRenderData(""),
                        new TextRenderData("")));

                Style header = new Style();
                header.setBold(true);
                rowList.add(RowRenderData.build(
                        new TextRenderData(""),
                        new TextRenderData(""),
                        new TextRenderData("单位：人民币（千元）", header)));
            }
            String itemName = ccf.getStr("item_name");
            Integer begin = ccf.getInt("begin_date_value");
            Integer end = ccf.getInt("end_date_value");
            Integer is_sum_option = ccf.getInt("is_sum_option");
            Style sumStyle = new Style();
            if (is_sum_option.intValue() == 1) {
                sumStyle.setBold(true);
            }
            rowList.add(RowRenderData.build(new TextRenderData(itemName, sumStyle), new TextRenderData(begin.toString()), new TextRenderData(end.toString())));
            j++;
        }
        return new MiniTableRenderData(rowList);
    }

    /**
     * 财务生成Excel
     * @param reportType
     * @param financialConfId
     */
    public static String financialExcel(String reportType,String financialConfId,String _prePath,String orderId,int userid){
        String filePath = "";
        //取数据
        Integer type = new ReportInfoGetDataController().getFinanceDictByReportType(reportType);
        List<CreditCompanyFinancialEntry> finDataRows = FinanceService.getFinancialEntryList(financialConfId, type);
        FinancialExcelExport export = new FinancialExcelExport(finDataRows);
        try {
            String path = _prePath + ".xls";
            export.downloadExcel(path);
            //上传文件
            filePath = BaseWord.uploadReport(path, orderId, userid);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 财务模块-评价
     * @param statementsConf
     * @return
     */
    public static String financialEval(CreditCompanyFinancialStatementsConf statementsConf) {
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
        str.append("盈利能力：" + ("".equals(profSumup) ? reportInfoGetDataController.dictIdToString(profSumup) : ""));
        str.append("\n");
        str.append(profDetail);
        str.append("\n");
        str.append("周转能力：" + ("".equals(liquSumup) ? reportInfoGetDataController.dictIdToString(liquSumup) : ""));
        str.append("\n");
        str.append(liquDetail);
        str.append("\n");
        str.append("融资能力：" + ("".equals(leverSumup) ? reportInfoGetDataController.dictIdToString(leverSumup) : ""));
        str.append("\n");
        str.append(leverDetail);
        str.append("\n");
        str.append("目标公司的总体财务状况：" + ("".equals(overSumup) ? reportInfoGetDataController.dictIdToString(overSumup) : ""));
        str.append("\n");
        str.append(overDetail);
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

}
