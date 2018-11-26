package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanySubtables;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.jfinal.kit.PathKit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 商业信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BusinessZh {

    public static void reportTable() {
        //报告类型
        String reportType = "8";
        //语言
        String sysLanguage = "612";
        //公司id
        String companyId = "65"; //77基本  65商业
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
                    table = MainWord.createTableH(child, rows);
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
                Map<String, String> p = MainWord.parseUrl(s);
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
                        List rs = report.getTableData(true, sysLanguage, companyId, t, cn, ci, "");
                        if(rs!=null&&rs.size()>0){
                            BaseProjectModel model = (BaseProjectModel)rs.get(0);
                            String v = model.get(word_key) + "";
                            map.put(word_key, v);
                        }
                    }
                }else{
                    String word_key = conf.get("word_key")+"";
                    if(word_key!=null && !"".equals(word_key) && !"null".equals(word_key)) {
                        List rs = report.getTableData(true, sysLanguage, companyId, t, cn, ci, "");
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

            //图形表
            if("11".equals(moduleType)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
                List<LinkedHashMap<String,String>> datas = new ArrayList<LinkedHashMap<String,String>>();
                //取列值
                for(int i=0;i< child.size();i++) {
                    CreditReportModuleConf module = child.get(i);
                    String column_name = module.getStr("column_name");
                    String temp_name = module.getStr("temp_name");
                    cols.put(column_name,temp_name);
                }
                //取数据
                for(int i=0;i< rows.size();i++){
                    LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
                    //取行
                    BaseProjectModel model = (BaseProjectModel) rows.get(i);
                    for(String column : cols.keySet()) {
                        String value = model.get(column) != null ? model.get(column) + "" : "";
                        row.put(column, value);
                    }
                    datas.add(row);
                }
                DefaultPieDataset pds = new DefaultPieDataset();
                for(LinkedHashMap<String,String> m : datas){
                    String[] keys = (String[]) m.keySet().toArray();
                    pds.setValue(m.get(keys[0]), 100);
                }
                createPieChart(pds,"h:/pie.jpg");
            }
        }
        MainWord.buildWord(map, "h://word/_商业信息报告样本.docx", "h://2.docx");
    }

    public static void main(String[] args) {
        DefaultPieDataset pds = new DefaultPieDataset();
        pds.setValue("00点-04点", 100);
        pds.setValue("04点-08点", 200);
        pds.setValue("08点-12点", 300);
        pds.setValue("12点-16点", 400);
        pds.setValue("16点-20点", 500);
        pds.setValue("20点-24点", 600);
        String filePath = "h:/pie.jpg";
        createPieChart(pds,filePath);
    }

    public static void createPieChart(DefaultPieDataset pds, String filePath) {
        try {
            // 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
            JFreeChart chart = ChartFactory.createPieChart("出资比例（%）", pds, true, false, true);
            // 如果不使用Font,中文将显示不出来
            Font font = new Font("宋体", Font.BOLD, 12);
            // 设置图片标题的字体
            chart.getTitle().setFont(font);
            // 得到图块,准备设置标签的字体
            PiePlot plot = (PiePlot) chart.getPlot();
            // 设置标签字体
            plot.setLabelFont(font);
            //设置图例字体
            chart.getLegend().setItemFont(font);
            plot.setStartAngle(new Float(3.14f / 2f));
            // 设置plot的前景色透明度
            plot.setForegroundAlpha(0.7f);
            // 设置plot的背景色透明度
            plot.setBackgroundAlpha(0.0f);
            // 设置标签生成器(默认{0})
            // {0}:key {1}:value {2}:百分比 {3}:sum
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
            // 将内存中的图片写到本地硬盘
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 600, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
