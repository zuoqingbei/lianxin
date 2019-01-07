package com.hailian.util.word;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 * poi-tl
 * word生成
 * 注意以下几点：
 * 1.使用office 工具编辑模板，wps编辑的模块部分版本有问题
 * 2.编码问题，可能会造成word生成失败
 */
public class BaseWord {
    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
    public static final String userName = Config.getStr("ftp_userName");//域用户名
    public static final String password = Config.getStr("ftp_password");//域用户密码
    public static final String ftpStore = Config.getStr("ftp_store");//ftp文件夹

    private static TemplateDictService template = new TemplateDictService();

    public static void main(String args[]) throws Exception{
        //创建主题样式 ,以下代码用于解决中文乱码问题
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋体",Font.BOLD,20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋体",Font.PLAIN,15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋体",Font.PLAIN,15));
        //应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        // 柱状图数据源
        DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
        barDataSet.addValue(800, "y1", "2010");
        barDataSet.addValue(600, "y1", "2011");
        barDataSet.addValue(200, "y1", "2012");

        //折线图数据源
        DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
        lineDataSet.addValue(0.2, "y2", "2010");
        lineDataSet.addValue(0.35, "y2", "2011");
        lineDataSet.addValue(0.8, "y2", "2012");

        BarRenderer barRender = new BarRenderer();
        //BarRenderer2D
        //展示柱状图数值
        barRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRender.setBaseItemLabelsVisible(true);
        barRender.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_CENTER));
        //最短的BAR长度，避免数值太小而显示不出
        barRender.setMinimumBarLength(0.5);
        // 设置柱形图上的文字偏离值
        barRender.setItemLabelAnchorOffset(10D);
        //设置柱子的最大宽度
        barRender.setMaximumBarWidth(0.03);
        barRender.setItemMargin(0.000000005);
        LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
        //展示折线图节点值
        lineRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        lineRender.setBaseItemLabelsVisible(true);
        lineRender.setBasePositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        // 设置柱形图上的文字偏离值
        lineRender.setItemLabelAnchorOffset(5D);

        BasicStroke brokenLine = new BasicStroke(2f,//线条粗细
                BasicStroke.CAP_SQUARE,           //端点风格
                BasicStroke.JOIN_MITER,           //折点风格
                8.f,                              //折点处理办法 ,如果要实线把该参数设置为NULL
                new float[]{8.0f },               //虚线数组
                0.0f);
        //设置第一条折线的风格
        lineRender.setSeriesStroke(0,brokenLine);
        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(0, barDataSet);
        plot.setDataset(1, lineDataSet);
        plot.setRenderer(0, barRender);
        plot.setRenderer(1, lineRender);
        plot.setDomainAxis(new CategoryAxis());
        //设置水平方向背景线颜色
        plot.setRangeGridlinePaint(Color.gray);
        //设置是否显示水平方向背景线,默认值为true
        plot.setRangeGridlinesVisible(true);
        //设置垂直方向背景线颜色
        plot.setDomainGridlinePaint(Color.gray);
        //设置是否显示垂直方向背景线,默认值为true
        plot.setDomainGridlinesVisible(true);
        //设置图表透明图0.0~1.0范围。0.0为完全透明，1.0为完全不透明。
        plot.setForegroundAlpha(0.7F);
        plot.setRangeAxis(new NumberAxis());
        //设置Y轴刻度
        plot.setRangeAxis(1, new NumberAxis());
        //设置折线图数据源应用Y轴右侧刻度
        plot.mapDatasetToRangeAxis(1, 1);
        for (int i = 0; i < plot.getRangeAxisCount(); i++)
        {
            ValueAxis rangeAxis = plot.getRangeAxis(i);
            //设置最高的一个柱与图片顶端的距离
            rangeAxis.setUpperMargin(0.25);
        }
        CategoryAxis categoryAxis = plot.getDomainAxis();
        //X轴分类标签以45度倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("数量/销量走势图");
        chart.setBackgroundPaint(SystemColor.WHITE);
        //ChartFrame chartFrame=new ChartFrame("数量/销量走势图",chart);
        //以合适的大小展现图形
        //chartFrame.pack();
        //图形是否可见
        //chartFrame.setVisible(true);
        // 将内存中的图片写到本地硬盘
        ChartUtilities.saveChartAsJPEG(new File("h://555.jpg"), chart, 600, 300);
    }


    public static void tableData(RowRenderData header2,Map<String,Object> data){

    }

    /**
     * 生成word基础类
     * @param dataMap 数据集合
     * @param sourceDoc 模板名称
     * @param targetDoc 目标名称
     */
    public static void buildWord(HashMap<String, Object> dataMap, String sourceDoc,String targetDoc) {
        FileOutputStream out = null;
        XWPFTemplate template = null;
        try {
            template = XWPFTemplate.compile(sourceDoc).render(dataMap);
            out = new FileOutputStream(targetDoc);
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (template != null) {
                try {
                    template.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据网络路径生成word基础类
     * @param dataMap 数据集合
     * @param netUrl 网络路径
     * @param targetDoc 目标名称
     */
    public static void buildNetWord(HashMap<String, Object> dataMap,String netUrl,String targetDoc) {
        FileOutputStream out = null;
        XWPFTemplate template = null;
        InputStream iputstream = null;
        try {
            URL url = new URL(netUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            //设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            iputstream = uc.getInputStream();
            //调用模板生成word
            template = XWPFTemplate.compile(iputstream).render(dataMap);
            out = new FileOutputStream(targetDoc);
            template.write(out);
            out.flush();
            out.close();
            template.close();
            iputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (template != null) {
                try {
                    template.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(iputstream!=null){
                try {
                    iputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 生成表格 - 竖表
     * @param child
     * @param rows
     * @return
     */
    public static MiniTableRenderData createTableS(String reportType,List<CreditReportModuleConf> child,List rows,String sysLanguage){
        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        //取列值
        for(int i=0;i< child.size();i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            String field_type = module.getStr("field_type");
            String word_key = module.getStr("word_key");
            if("操作".equals(temp_name)||"Operation".equals(temp_name)||"Summary".equals(temp_name)) {
            }else {
                cols.put(column_name, temp_name + "|" + field_type);
            }
        }
        //取数据
        for (int i = 0; i < rows.size(); i++) {
            BaseProjectModel model = (BaseProjectModel) rows.get(0);
            for (String column : cols.keySet()) {
                String[] strs = cols.get(column).split("\\|");
                String fieldType = strs.length == 2 ? strs[1] : "";
                String value = model.get(column) != null ? model.get(column) + "" : "";
                if ("select".equals(fieldType)) {
                    value = !"".equals(value) ? new ReportInfoGetDataController().dictIdToString(value,sysLanguage) : "N/A";
                } else {
                    value = !"".equals(value) ? value : "N/A";
                }
                Style style = new Style();
                style.setColor("000000");
                style.setFontFamily("宋体");
                //102红印用14号字体
                if("15".equals(reportType)){
                    style.setFontSize(14);
                }
                rowList.add(RowRenderData.build(new TextRenderData(cols.get(column).split("\\|")[0], style), new TextRenderData(value, style)));
            }
        }
        return new MiniTableRenderData(rowList);
    }


    /**
     * 生成表格 - 横表
     * @param child
     * @param rows
     * @param sysLanguage
     * @return
     */
    public static MiniTableRenderData createTableH(String reportType,List<CreditReportModuleConf> child,List rows,String sysLanguage){
        List<RowRenderData> rowsList = new ArrayList<RowRenderData>();
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        List<LinkedHashMap<String,String>> datas = new ArrayList<LinkedHashMap<String,String>>();
        //取列值
        for(int i=0;i< child.size();i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            String field_type = module.getStr("field_type");
            if("操作".equals(temp_name)||"Operation".equals(temp_name)||"Summary".equals(temp_name)) {
            }else{
                cols.put(column_name, temp_name + "|" + field_type);
            }
        }
        //取数据
        for(int i=0;i< rows.size();i++){
            LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
            //取行
            BaseProjectModel model = (BaseProjectModel) rows.get(i);
            for(String column : cols.keySet()) {
                String[] strs = cols.get(column).split("\\|");
                String fieldType = strs.length > 1 ? strs[1] : "";
                Integer id = model.getInt("id");
                String value = model.get(column) != null ? model.get(column) + "" : "";
                if("select".equals(fieldType)) {
                    value = !"".equals(value) ? new ReportInfoGetDataController().dictIdToString(value,sysLanguage) : "";
                } else if("file".equals(fieldType)) {
                    value = "{{@img" + id + "}}";
                } else {
                    value = !"".equals(value) ? value : "";
                }
                row.put(strs.length > 0 ? strs[0] : "", value);
                //row.put(column, value);
            }
            datas.add(row);
        }
        //居中对齐
        TableStyle tableStyle = new TableStyle();
        tableStyle.setAlign(STJc.CENTER);
        Object[] colSize = cols.keySet().toArray();
        //组装表格-表头
        RowRenderData rowRenderData = tableHeaderH(cols, reportType);
        //组装表格-数据
        for(LinkedHashMap<String,String> m:datas) {
            int j=0;
            TextRenderData[] row = new TextRenderData[colSize.length];
            for(String column : cols.keySet()) {
                String value = m.get(cols.get(column).split("\\|")[0]);
                Style style = new Style();
                if(ReportTypeCons.ROC_HY.equals(reportType)){
                    style.setFontFamily("宋体");
                    style.setFontSize(14);
                }else if(ReportTypeCons.ROC_ZH.equals(reportType)||ReportTypeCons.ROC_EN.equals(reportType)){
                    //style.setFontFamily("新细明体（PMingLiU）");
                    style.setFontSize(11);
                }
                row[j] = new TextRenderData(value,style);
                j++;
            }
            RowRenderData rowData = RowRenderData.build(row);
            rowData.setStyle(tableStyle);
            rowsList.add(rowData);
        }
        return new MiniTableRenderData(rowRenderData,rowsList);
    }

    /**
     * 生成表头
     * @param cols
     * @param reportType
     */
    public static RowRenderData tableHeaderH(LinkedHashMap<String,String> cols,String reportType){
        RowRenderData rowRenderData = null;
        TableStyle tableStyle = new TableStyle();
        Object[] colSize = cols.keySet().toArray();
        //102红印的不显示表头，其他显示表头
        if(!ReportTypeCons.ROC_HY.equals(reportType)){
            TextRenderData[] header = new TextRenderData[colSize.length];
            int i=0;
            for(String column : cols.keySet()) {
                String value = cols.get(column).split("\\|")[0];
                Style style = new Style();
                style.setBold(true);
                //102下划线
                if(ReportTypeCons.ROC_ZH.equals(reportType)||ReportTypeCons.ROC_EN.equals(reportType)){
                    style.setFontFamily("新细明体（PMingLiU）");
                    style.setUnderLine(true);
                    style.setFontSize(11);
                }
                header[i] = new TextRenderData(value,style);
                i++;
            }
            //表头居中
            rowRenderData = RowRenderData.build(header);
            rowRenderData.setStyle(tableStyle);
        }
        return rowRenderData;
    }

    /**
     * 生成表格 - 自定义
     * 按照字段匹配
     * @param child
     * @param rows
     * @param map
     * @param sysLanguage
     * @return
     */
    public static void createTableZ(List<CreditReportModuleConf> child,List rows,HashMap<String, Object> map,String reportType,String sysLanguage){
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        //取列值
        for(int i=0;i< child.size();i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            String field_type = module.getStr("field_type");
            String get_source = module.getStr("get_source");
            if("操作".equals(temp_name)||"Operation".equals(temp_name)||"Summary".equals(temp_name)) {
            }else {
                cols.put(column_name, temp_name + "|" + field_type+"|"+get_source);
            }
        }
        //取数据
        for (int i = 0; i < rows.size(); i++) {
            BaseProjectModel model = (BaseProjectModel) rows.get(i);
            for (String column : cols.keySet()) {
                if("year_result".equals(column)){
                    System.out.println(1);
                }
                String[] strs = cols.get(column).split("\\|");
                String fieldType = strs.length == 3 ? strs[1] : "";
                String getSource = strs.length == 3 ? strs[2] : "";
                String value = model.get(column) != null ? model.get(column) + "" : "";
                if ("select".equals(fieldType)) {
                    //102chiness 等级状态
                    //System.out.println(ReportTypeCons.ROC_ZH.equals(reportType));
                    //System.out.println("registration_status".equals(column));
                    if(ReportTypeCons.ROC_ZH.equals(reportType) && ("registration_status".equals(column) || "year_result".equals(column))){
                        Map<String,String> params = parseUrl(getSource);
                        String type = params.get("type");
                        value = !"".equals(value) ? template.getSysDictDetailStringWord(type,value) : "N/A";
                    }else{
                        value = !"".equals(value) ? new ReportInfoGetDataController().dictIdToString(value, sysLanguage) : "N/A";
                    }
                } else {
                    value = !"".equals(value) ? value : "N/A";
                }
                map.put(column, value);
            }
        }
    }



    /**
     * 将模块的数据，整理成类似table格式
     * @param child
     * @param rows
     * @return
     */
    public static List<LinkedHashMap<String, String>> formatData(List<CreditReportModuleConf> child,List rows){
        List<LinkedHashMap<String, String>> datas = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> cols = getModuleCols(child);
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
        return datas;
    }

    /**
     * 模块取字段值
     * @param child 当前模块对应的子节点
     * @return
     */
    public static LinkedHashMap<String, String> getModuleCols(List<CreditReportModuleConf> child){
        LinkedHashMap<String, String> cols = new LinkedHashMap<String, String>();
        //取列值
        for (int i = 0; i < child.size(); i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            cols.put(column_name, temp_name);
        }
        return cols;
    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static Map<String,String> parseUrl(String url) {
        System.out.println("url="+url);
        Map<String,String> map = new HashMap<String,String>();
        String[] urlParts = url.split("\\?");
        //没有参数
        if (urlParts.length == 1) {
            return map;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * 基本信息报告，商业报告，信用分析报告
     * 生成股东出资图形报表
     * @param pds
     * @param filePath
     */
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

    /**
     * 生成柱状图+折线图
     * @param filePath
     */
    public static void createBarChart(String title,DefaultCategoryDataset barDataSet,DefaultCategoryDataset lineDataSet,String filePath){
        try {
            //创建主题样式 ,以下代码用于解决中文乱码问题
            StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
            //设置标题字体
            standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
            //设置图例的字体
            standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
            //设置轴向的字体
            standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
            //应用主题样式
            ChartFactory.setChartTheme(standardChartTheme);

            // 柱状图数据源
            /*DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
            barDataSet.addValue(800, "数量", "1月");
            barDataSet.addValue(600, "数量", "2月");
            barDataSet.addValue(200, "数量", "3月");*/

            //折线图数据源
            /*DefaultCategoryDataset lineDataSet = new DefaultCategoryDataset();
            lineDataSet.addValue(0.2, "销量", "1月");
            lineDataSet.addValue(0.35, "销量", "2月");
            lineDataSet.addValue(0.8, "销量", "3月");*/

            //BarRenderer3D barRender = new BarRenderer3D();
            BarRenderer barRender = new BarRenderer();
            //展示柱状图数值
            barRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            barRender.setBaseItemLabelsVisible(true);
            barRender.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                    ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_CENTER));
            //最短的BAR长度，避免数值太小而显示不出
            barRender.setMinimumBarLength(0.5);
            // 设置柱形图上的文字偏离值
            barRender.setItemLabelAnchorOffset(10D);
            //设置柱子的最大宽度
            barRender.setMaximumBarWidth(0.03);
            barRender.setItemMargin(0.000000005);
            LineAndShapeRenderer lineRender = new LineAndShapeRenderer();
            //展示折线图节点值
            lineRender.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            lineRender.setBaseItemLabelsVisible(true);
            lineRender.setBasePositiveItemLabelPosition(
                    new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
            // 设置柱形图上的文字偏离值
            lineRender.setItemLabelAnchorOffset(5D);

            BasicStroke brokenLine = new BasicStroke(2f,//线条粗细
                    BasicStroke.CAP_SQUARE,           //端点风格
                    BasicStroke.JOIN_MITER,           //折点风格
                    8.f,                              //折点处理办法 ,如果要实线把该参数设置为NULL
                    new float[]{8.0f},               //虚线数组
                    0.0f);
            //设置第一条折线的风格
            lineRender.setSeriesStroke(0, brokenLine);
            CategoryPlot plot = new CategoryPlot();
            plot.setDataset(0, barDataSet);
            plot.setDataset(1, lineDataSet);
            plot.setRenderer(0, barRender);
            plot.setRenderer(1, lineRender);
            plot.setDomainAxis(new CategoryAxis());
            //设置水平方向背景线颜色
            plot.setRangeGridlinePaint(Color.gray);
            //设置是否显示水平方向背景线,默认值为true
            plot.setRangeGridlinesVisible(true);
            //设置垂直方向背景线颜色
            plot.setDomainGridlinePaint(Color.gray);
            //设置是否显示垂直方向背景线,默认值为true
            plot.setDomainGridlinesVisible(true);
            //设置图表透明图0.0~1.0范围。0.0为完全透明，1.0为完全不透明。
            plot.setForegroundAlpha(0.7F);
            plot.setRangeAxis(new NumberAxis());
            //设置Y轴刻度
            plot.setRangeAxis(1, new NumberAxis());
            // 设置折线图数据源应用Y轴右侧刻度
            plot.mapDatasetToRangeAxis(1, 1);
            for (int i = 0; i < plot.getRangeAxisCount(); i++) {
                ValueAxis rangeAxis = plot.getRangeAxis(i);
                //设置最高的一个柱与图片顶端的距离
                rangeAxis.setUpperMargin(0.25);
            }
            CategoryAxis categoryAxis = plot.getDomainAxis();
            //X轴分类标签以45度倾斜
            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            JFreeChart chart = new JFreeChart(plot);
            chart.setTitle(title);
            chart.setBackgroundPaint(SystemColor.WHITE);
            //ChartFrame chartFrame=new ChartFrame("数量/销量走势图",chart);
            //以合适的大小展现图形
            //chartFrame.pack();
            //图形是否可见
            //chartFrame.setVisible(true);
            // 将内存中的图片写到本地硬盘
            ChartUtilities.saveChartAsJPEG(new File(filePath), chart, 600, 300);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param filePath
     */
    public static String uploadReport(String filePath, String orderId, Integer userid) {
        String factpath = null;
        String storePath = ftpStore + "/" + DateUtils.getNow(DateUtils.YMD);
        File file = new File(filePath);
        List<File> commonFiles = new ArrayList<File>();
        commonFiles.add(file);
        String now = UUID.randomUUID().toString().replaceAll("-", "");
        CreditUploadFileModel fileModel = new CreditUploadFileModel();
        fileModel.set("business_type", "0");
        fileModel.set("business_id", orderId);
        try {
            boolean storeCommonFile = FtpUploadFileUtils.storeMoreFtpFile(now, commonFiles, storePath, ip, port, userName, password);
            if (!storeCommonFile) {
                System.out.println("文件上传异常!");
            }
            //将文件信息保存到实体类
            for (File f : commonFiles) {
                //获取真实文件名
                String originalFile = f.getName();
                //不带后缀的文件名
                String originalFileName = FileTypeUtils.getName(originalFile);
                //根据文件后缀名判断文件类型
                String ext = FileTypeUtils.getFileType(originalFile);
                //上传到服务器时的文件名
                String FTPfileName = originalFileName + now + "." + ext;
                String PDFfileName = originalFileName + now + "." + ext;
                //如果上传文档不是pdf或者图片则转化为pdf，以作预览
                if (!ext.equals("pdf") && !FileTypeUtils.isImg(ext)) {
                    PDFfileName = originalFileName + now + "." + "pdf";
                }
                factpath = storePath + "/" + FTPfileName;
                String ftpFactpath = storePath + "/" + PDFfileName;
                //将上传信息维护进实体表
                UploadFileService.service.save(0, file.getName(), file.length(), factpath, factpath, ftpFactpath, ftpFactpath, fileModel, originalFileName + now, userid);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return factpath;
    }

    /**
     * 发送邮件
     * @param
     */
    public static void sendReportMail(String toMail,List<Map<String,String>> fileList){
        //List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        //Map<String,String> map=new HashMap<String, String>();
        //map.put("哈哈.doc", "http://"+ip+":"+port+"/"+filePath);
        //list.add(map);
        try {
            new SendMailUtil("15269274025@163.com", "", "你好", "mycontent", fileList).sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }
}
