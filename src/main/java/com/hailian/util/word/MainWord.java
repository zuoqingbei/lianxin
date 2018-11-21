package com.hailian.util.word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 * poi-tl
 * word生成
 * 注意以下几点：
 * 1.使用office 工具编辑模板，wps编辑的模块部分版本有问题
 * 2.编码问题，可能会造成word生成失败
 */
public class MainWord {

    public static void main(String args[]) {
        RowRenderData header = RowRenderData.build(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历"));
        RowRenderData row0 = RowRenderData.build("张三", "研究生");
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        RowRenderData row2 = RowRenderData.build("王五", "博士后");
        MiniTableRenderData list = new MiniTableRenderData(header, Arrays.asList(row0, row1, row2));

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "test");
        map.put("table", list);

        MainWord.buildWord(map, "d://template.docx","d://123.docx");
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
            cols.put(column_name,temp_name);
        }
        //取数据
        for (int i = 0; i < rows.size(); i++) {
            BaseProjectModel model = (BaseProjectModel) rows.get(0);
            for(String column : cols.keySet()) {
                String value = model.get(column) != null ? model.get(column) + "" : "";
                Style style = new Style();
                style.setColor("000000");
                rowList.add(RowRenderData.build(new TextRenderData(cols.get(column), style), new TextRenderData(value,style)));
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
        //居中对齐
        TableStyle tableStyle = new TableStyle();
        tableStyle.setAlign(STJc.CENTER);
        //组装表格-表头
        TextRenderData[] header = new TextRenderData[child.size()];
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
            String[] row = new String[child.size()];
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
}
