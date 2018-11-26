package com.hailian.util.word;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 * poi-tl
 * word生成
 * 注意以下几点：
 * 1.使用office 工具编辑模板，wps编辑的模块部分版本有问题
 * 2.编码问题，可能会造成word生成失败
 */
public class MainWord {
    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
    public static final String userName = Config.getStr("ftp_userName");//域用户名
    public static final String password = Config.getStr("ftp_password");//域用户密码
    public static final String ftpStore = Config.getStr("ftp_store");//ftp文件夹

    public static void main(String args[]) {
        /*RowRenderData header = RowRenderData.build(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历"));
        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "博士后");
        MiniTableRenderData list = new MiniTableRenderData(header, Arrays.asList(row0, row1, row2));

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "test");
        map.put("table", list);

        MainWord.buildWord(map, "d://template.docx","d://123.docx");*/
        String str = "登记状态|";
        String[] fieldType = str.split("\\| ");
        System.out.println(fieldType.length);

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
     * 生成表格 - 竖表
     * @param child
     * @param rows
     * @return
     */
    public static MiniTableRenderData createTableS(List<CreditReportModuleConf> child,List rows){
        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        //取列值
        for(int i=0;i< child.size();i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            String field_type = module.getStr("field_type");
            if(!"操作".equals(temp_name)) {
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
                    value = !"".equals(value) ? ":" + dictIdToString(value) : ":N/A";
                } else {
                    value = !"".equals(value) ? ":" + value : ":N/A";
                }
                Style style = new Style();
                style.setColor("000000");
                style.setFontFamily("宋体");
                rowList.add(RowRenderData.build(new TextRenderData(cols.get(column).split("\\|")[0], style), new TextRenderData(value, style)));
            }
        }
        return new MiniTableRenderData(rowList);
    }


    /**
     * 生成表格 - 横表
     * @param child
     * @param rows
     * @return
     */
    public static MiniTableRenderData createTableH(List<CreditReportModuleConf> child,List rows){
        List<RowRenderData> rowsList = new ArrayList<RowRenderData>();
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        List<LinkedHashMap<String,String>> datas = new ArrayList<LinkedHashMap<String,String>>();
        //取列值
        for(int i=0;i< child.size();i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            if(!"操作".equals(temp_name)){
                cols.put(column_name,temp_name);
            }
        }
        //取数据
        for(int i=0;i< rows.size();i++){
            LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
            //取行
            BaseProjectModel model = (BaseProjectModel) rows.get(i);
            for(String column : cols.keySet()) {
                String fieldType = model.get("field_type") + "";
                String value = model.get(column) != null ? model.get(column) + "" : "";
                if("select".equals(fieldType)) {
                    value = !"".equals(value) ? dictIdToString(value) : "";
                }else {
                    value = !"".equals(value) ? value : "";
                }
                row.put(column, value);
            }
            datas.add(row);
        }
        //居中对齐
        TableStyle tableStyle = new TableStyle();
        tableStyle.setAlign(STJc.CENTER);
        Object[] colSize = cols.keySet().toArray();
        //组装表格-表头
        TextRenderData[] header = new TextRenderData[colSize.length];
        int i=0;
        for(String column : cols.keySet()) {
            String value = cols.get(column);
            Style style = new Style();
            style.setBold(true);
            header[i] = new TextRenderData(value,style);
            i++;
        }
        //表头居中
        RowRenderData rowRenderData = RowRenderData.build(header);
        rowRenderData.setStyle(tableStyle);
        //组装表格-数据
        for(LinkedHashMap<String,String> m:datas) {
            int j=0;
            String[] row = new String[colSize.length];
            for(String column : cols.keySet()) {
                row[j] = m.get(column);
                j++;
            }
            RowRenderData rowData = RowRenderData.build(row);
            rowData.setStyle(tableStyle);
            rowsList.add(rowData);
        }
        return new MiniTableRenderData(rowRenderData,rowsList);
    }

    public static Map<String,String> getSingleValue(List<CreditReportModuleConf> child,List rows){
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        Map<String,String> m = new HashMap<>();
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
                m.put(column, value);
            }
        }
        return m;
    }

    /**
     * 解析url
     *
     * @param url
     * @return
     */
    public static Map<String,String> parseUrl(String url) {
        Map<String,String> map = new HashMap<String,String>();
        String[] urlParts = url.split("\\?");
        //entity.baseUrl = urlParts[0];
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
     * 将id转化为字典表中对应的字符串
     * @param id
     */
    public static String dictIdToString(String id) {
        Map<Integer, SysDictDetail> cache = DictCache.getCacheMap();
        SysDictDetail sysDict = cache.get(Integer.parseInt(id));
        return sysDict.get("detail_name") + "";
    }

    /**
     * 上传文件
     *
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
     * @param filePath
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
