package com.hailian.util.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditOperationLog;
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetData;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.SplitString;
import com.hailian.util.StrUtils;
import com.hailian.util.translate.TransApi;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.Align;
import org.jfree.ui.TextAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.xml.soap.Detail;

/**
 * poi-tl
 * word生成
 * 注意以下几点：
 * 1.使用office 工具编辑模板，wps编辑的模块部分版本有问题
 * 2.编码问题，可能会造成word生成失败
 */
public class BaseWord {
    public static Logger log= Logger.getLogger(BaseWord.class);

    //文件服务器配置
    public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
    public static final String userName = Config.getStr("ftp_userName");//域用户名
    public static final String password = Config.getStr("ftp_password");//域用户密码
    public static final String ftpStore = Config.getStr("ftp_store");//ftp文件夹

    public static TemplateDictService template = new TemplateDictService();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf_zh = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat sdf_en_hy = new SimpleDateFormat("dd MMMM yyyy",Locale.ENGLISH);

    public static void main(String args[]) throws Exception{
        /*DecimalFormat demo=new DecimalFormat("###,###.##");
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println(demo.format(nf.parse("5000.1")));*/

        /*BigDecimal bd = new BigDecimal("1");
        BigDecimal bd2 = new BigDecimal("1");
        bd.add(bd2);
        System.out.println(bd);*/
    	String[] a = "|w ".split("\\|");
    	
    	for (int i=0;i<a.length;i++) {
    		System.out.println(i+a[i]);
		}
    }
    private static List<String> mergerKeyList = new ArrayList<>();
    private static List<String> mergerValueList = new ArrayList<>();
    static {
    	mergerKeyList.add("register_code_type");   mergerValueList.add("register_codes");mergerValueList.add("register_code");
    	mergerKeyList.add("principal_type");	   mergerValueList.add("legal");mergerValueList.add("principal");
    	
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
        HttpURLConnection uc = null;
        try {
            URL url = new URL(netUrl);
             uc = (HttpURLConnection) url.openConnection();
            //设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            iputstream = uc.getInputStream();
            //调用模板生成word
            template = XWPFTemplate.compile(iputstream);
            template.render(dataMap);
            out = new FileOutputStream(targetDoc);
            template.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            CreditOrderFlow.addOneEntry(null, new CreditOrderInfo().set("status","monitor1"),e.toString(),false);
            if(e.getMessage().contains("No valid entries or contents found, this is not a valid OOXML (Office Open XML) file")){
                throw new RuntimeException(e.getMessage());
            }

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(uc!=null){

                uc.disconnect();
            }
            if (template != null) {
                try {
                    template.close();
                } catch (IOException e) {
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
    public static MiniTableRenderData createTableS(String moduleName,String reportType,  List<CreditReportModuleConf> child,
    		List rows,String sysLanguage,String companyId){
        List<RowRenderData> rowList = new ArrayList<RowRenderData>();
        LinkedHashMap<String,String> cols = new LinkedHashMap<String,String>();
        Style style = new Style();
        style.setColor("000000");
        style.setFontFamily("宋体");
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
        List<String> mergeList = new ArrayList<>();
        //取数据
        for (int i = 0; i < rows.size(); i++) {
            BaseProjectModel model = (BaseProjectModel) rows.get(i);
            for (String column : cols.keySet()) {
                String[] strs = cols.get(column).split("\\|");
                String fieldType = strs.length == 2 ? strs[1] : "";
             /*   String tempName = "";
                try{
                     tempName  = strs[0];
                }catch ( Exception e){
                    e.printStackTrace();
                }*/


                //int tempNameLength = tempName.length();
                String value = "";
                
                //特殊处理
                /*if("name_en".equals(column)) {
                	if("name_en".equals(column)&&(tempName.replace("英文", "").length()<tempNameLength ||
                            tempName.replace("ENGLISH", "").length()<tempNameLength||tempName.replace("English", "").length()<tempNameLength)) {
                         	value = detailByColumn(companyId);
                         }
                }else {
                 	value = model.get(column) != null ? model.get(column) + "" : "";
                 }*/
                value = model.get(column) != null ? model.get(column) + "" : "";
               
                if ("select".equals(fieldType)) {
                	if("国籍".equals(strs[0])||"国家".equals(strs[0])){
                		 value = !"".equals(value) ? CountryModel.getCountryById(value,reportType,sysLanguage) : "N/A";
                	}else{
                		value = !"".equals(value) ? new ReportInfoGetDataController().dictIdToString(value,reportType,sysLanguage) : "N/A";
                	}
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
                }else  if("date".equals(fieldType)&&!StrUtils.isEmpty(value)){
                    try {
						value = detailDate(sdf.parse(value),reportType);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }else {
                    value = !"".equals(value) ? value : "N/A";
                }
                
                //针对不同模块中的不同字段的样式的特殊处理
                style = MiniTableRenderDataForCellStyle(moduleName,column,reportType,style);
                //二合一的特殊处理
                if(merger(value,column,reportType,mergeList,rowList,style)) {
                    rowList.add(RowRenderData.build(new TextRenderData(cols.get(column).split("\\|")[0], style), new TextRenderData(value, style)));
                }
               
            }
            
            //每一个实体之间空一行
            if(i!=rows.size()-1) {rowList.add(RowRenderData.build(new TextRenderData("", style), new TextRenderData("", style)));}
        }
        return new MiniTableRenderData(rowList);
    }
    
    /**
         * 两个下拉选二合一的特殊处理
     * @param value
     * @param column
     * @param reportType
     * @param mergeList
     * @param rowList
     * @param style
     * @return
     */
    private static boolean merger(String value, String column, String reportType, List<String> mergeList,
			List<RowRenderData> rowList, Style style) {
    	 if(mergerFlagKey(column,reportType)) { mergeList.add(value); return false;}
         if(mergerFlagValue(column,reportType)) { mergeList.add(value);}
         if(mergeList.size()==2) {
         	rowList.add(RowRenderData.build(new TextRenderData(mergeList.get(0), style), new TextRenderData(mergeList.get(1), style)));
         	mergeList.clear();
         	return false; 
         }
         mergeList.clear();
		 return true;
	}
    
	//判断是否为二合一中的key
	private static boolean mergerFlagKey(String column, String reportType) {if(mergerKeyList.contains(column)) {return true; }return false;}
	//判断是否为二合一中的值
	private static boolean mergerFlagValue(String column, String reportType) {if(mergerValueList.contains(column)) {return true;}return false;}
	
	/**
	 * lzg
	 * @param companyId
	 * @return
	 */
	private static String detailByColumn(String companyId) {
    	if(StrUtils.isEmpty(companyId)) return "";
    	String orderId = Db.query("select order_id from credit_company_info where del_flag=0 and id=? ",companyId).get(0)+"";
    	if(StrUtils.isEmpty(orderId)) return "";
    	String result = Db.query("select info_en_name from credit_order_info where del_flag=0 and id=? ",orderId).get(0)+"";
		return result;
	}
	
	
	
	//针对不同模块中的不同字段的样式的特殊处理
    private static Style MiniTableRenderDataForCellStyle(String moduleName, String columnName,String reportType, Style style) {
    	
    	if(ReportTypeCons.ROC_ZH.equals(reportType)||ReportTypeCons.ROC_EN.equals(reportType)){
            style.setFontFamily("PMingLiU");
        }else{
            style.setFontFamily("宋体");
        }
        //102红印用14号字体
        if(ReportTypeCons.ROC_HY.equals(reportType)){
            style.setFontSize(14);
        }
        if(ReportTypeCons.ROC_EN.equals(reportType)) {
        	style.setFontFamily("Times New Roman");
        }
        if(("contribution".equals(columnName)||"money".equals(columnName))&&"capital".equals(moduleName)) {
        	style.setFontFamily("Times New Roman");
        }
    	//股东模块
    	if("partner".equals(moduleName)) {
    		switch(columnName) {
    			//出资金额
    			case "contribution" : style.setAlign(STJc.RIGHT);
    			//出资比例
    			case "money" : style.setAlign(STJc.RIGHT);
    			default : break;
    		}
    		
    	}
		return style;
	}

	/**
     * 生成表格 - 横表
     * @param child
     * @param rows
     * @param sysLanguage
     * @param hasTotal
     * @param temp
     * @return
     */
    public static MiniTableRenderData createTableH(String moduleName,String reportType,List<CreditReportModuleConf> child,List rows,String sysLanguage,boolean hasTotal,String temp,String companyId) {
        //存放行数据-word模板
        List<RowRenderData> rowsList = new ArrayList<RowRenderData>();
        //表格列字段集合
        LinkedHashMap<String, Map<String,String>> cols = new LinkedHashMap<String, Map<String,String>>();
        //存放表格数据
        List<LinkedHashMap<String, String>> datas = new ArrayList<LinkedHashMap<String, String>>();
        //合计项
        LinkedHashMap<String, String> totalRow = new LinkedHashMap<String, String>();
        //取列名
        for (int i = 0; i < child.size(); i++) {
            CreditReportModuleConf module = child.get(i);
            String column_name = module.getStr("column_name");
            String temp_name = module.getStr("temp_name");
            String field_type = module.getStr("field_type");
            String word_default = module.get("word_default") != null ? module.get("word_default") : "";
            if ("操作".equals(temp_name) || "Operation".equals(temp_name) || "Summary".equals(temp_name)) {
            } else {
            	 Map<String, String> colMap = new HashMap<>();
                 colMap.put("temp_name", temp_name);
                 colMap.put("field_type", field_type);
                 colMap.put("word_default", word_default);
                 //cols.put(column_name, temp_name + "|" + field_type);
                 cols.put(column_name, colMap);
            }
        }
        //取数据
        for (int i = 0; i < rows.size(); i++) {
            LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
            //取一行数据
            BaseProjectModel model = (BaseProjectModel) rows.get(i);
            for (String column : cols.keySet()) {
                Map<String,String> colMap = cols.get(column);
                String tempName = colMap.get("temp_name");
                String fieldType = colMap.get("field_type");
                String wordDefault = colMap.get("word_default");
                Integer id = model.getInt("id");
                String value = "";
                /*//不同字段的特殊处理
                if("name_en".equals(column)) {
                	value = detailByColumn(companyId);
                }else {
                	value = model.get(column) != null ? model.get(column) + "" : "";
                }*/
                value = model.get(column) != null ? model.get(column) + "" : "";
                if("变更前".equals(tempName)||"变更后".equals(tempName)){
                	if(StringUtils.isNotBlank(value))
                	//value=StrUtils.toJoinString(value, 10);
                		value=SplitString.str_split(value, 9, "\n");
                }
                //合计项计算
                if(hasTotal) {
                    try {
                        //数字和金额类型的字段才能计算
                        if ("number".equals(fieldType) || "money".equals(fieldType)) {
                            String val = totalRow.get(column);
                            val = val != null ? val.replaceAll(",", "") : "0";
                            val = new BigDecimal(val).add(new BigDecimal(value.replaceAll(",", ""))).toString();
                            if ("money".equals(fieldType)) {
                                DecimalFormat df = new DecimalFormat("###,###.##");
                                NumberFormat nf = NumberFormat.getInstance();
                                val = df.format(nf.parse(val));
                            }
                            totalRow.put(column, val);
                        } else {
                            String val = totalRow.get(column);
                            if(!("合计".equals(val)||"合計".equals(val))){
                                val = "-";
                            }
                           /* 这段代码是对
                            String v = totalRow.keySet().size() == 0 ? "合计" : "合计".equals(val)||"合計".equals(val) ? val : "-";
                           的分解
                           String v = "-";
                            if(totalRow.keySet().size()==0){
                                v = "合计";
                            }else{
                                if(!("合计".equals(val)||"合計".equals(val))){
                                    v = "-";
                                }else{
                                    v = val;
                                }
                                if("合计".equals(v)){
                                v="合計";
                            }
                            }*/
                           if("612".equals(sysLanguage)){
                               val = "合计";
                           }else if("613".equals(sysLanguage)){
                               val = "Total";
                           }else if("614".equals(sysLanguage)){
                               val = "合計";
                            }
                            totalRow.put(column,val);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //出资情况，出资金额后面跟币种
                if("contribution".equals(column)) {
                    if(ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType) || ReportTypeCons.ROC_HY.equals(reportType)) {
                        if(!tempName.contains("(")){
                            String str = tempName + "(" + temp + ")";
                            colMap.put("temp_name", str);
                        }
                    }
                }

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
                }
                //处理千位符号
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
                else if("date".equals(fieldType)
                		&&!"history".equals(moduleName)//登记变更情况，日期用中文就可以
                		){
                	 
                	try {
						value = detailDate(sdf.parse(value),reportType);
					} catch (ParseException e) {
						e.printStackTrace();
					}
                	 
                    if(StringUtils.isEmpty(value)){
                        value = wordDefault;
                    }
                   
                }
                //专利和商标图片先用占位符占用，再二次替换成图片
                else if ("file".equals(fieldType)) {
                    value = "{{@img" + id + "}}";
                }
              
                row.put(column,value);
            }
            datas.add(row);
        }
        //居中对齐
        TableStyle tableStyle = new TableStyle();
        tableStyle.setAlign(STJc.LEFT);
        //表格边框
        if(ReportTypeCons.ROC_HY.equals(reportType)){
            //红印的表格不显示边框
            tableStyle.setHasBorder(false);
        }
        //102模板，出资情况列表中的币种不展示
        /*if(ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
            if(cols.containsKey("contribution")&&cols.containsKey("currency")){
                cols.remove("currency");
            }
        }*/
        Object[] colSize = cols.keySet().toArray();

        //组装表格-表头
        RowRenderData rowRenderData = tableHeaderH(cols, reportType,sysLanguage);
        rowRenderData.setStyle(tableStyle);
        //组装表格-数据
        for (LinkedHashMap<String, String> m : datas) {
            int j = 0;
            TextRenderData[] row = new TextRenderData[colSize.length];
            for (String column : cols.keySet()) {
                String value = m.get(column);
                Style style = new Style();
                if (ReportTypeCons.ROC_HY.equals(reportType)) {
                    style.setFontFamily("宋体");
                    style.setFontSize(14);
                    if("sh_name".equals(column)){
                        style.setAlign(STJc.LEFT);
                    }else if("contribution".equals(column)||"money".equals(column)){
                        style.setAlign(STJc.RIGHT);
                        style.setFontFamily("Times New Roman");
                    }
                } else if (ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
                    //字号
                    style.setFontSize(11);
                    //单元格对齐方式
                    if("sh_name".equals(column)){
                        style.setAlign(STJc.LEFT);
                    }else if("contribution".equals(column)||"money".equals(column)){
                        style.setAlign(STJc.RIGHT);
                    }
                    //字体
                    if(ReportTypeCons.ROC_EN.equals(reportType)||ReportTypeCons.ROC_ZH.equals(reportType)){
                        style.setFontFamily("PMingLiU");//my_ todo
                    }else{
                        if("sh_name".equals(column)){
                            style.setFontFamily("PMingLiU");
                        }else if("contribution".equals(column)||"money".equals(column)){
                            style.setFontFamily("Times New Roman");
                        }
                    }
                    if("money".equals(column)){
                        value += "%";
                    }
                }
                row[j] = new TextRenderData(value, style);
                j++;
            }
            RowRenderData rowData = RowRenderData.build(row);
            rowData.setStyle(tableStyle);
            rowsList.add(rowData);
        }
        //合计项生成word格式
        if(hasTotal) {
            TextRenderData[] row = new TextRenderData[colSize.length];
            int j = 0;
            for (String column : cols.keySet()) {
                String value = totalRow.get(column);
                Style style = new Style();
                style.setFontFamily("Times New Roman");
                //style.setBold(true);
                //对齐方式
                if (ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)||ReportTypeCons.ROC_HY.equals(reportType)) {
                    if ("sh_name".equals(column)) {
                        style.setAlign(STJc.LEFT);
                    } else if ("contribution".equals(column) || "money".equals(column)) {
                        style.setAlign(STJc.RIGHT);
                    }
                }
                //字体
                if (ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
                    if ("sh_name".equals(column)) {
                        style.setFontFamily("PMingLiU");
                    } else if ("contribution".equals(column) || "money".equals(column)) {
                        style.setFontFamily("Times New Roman");
                    }
                    if("money".equals(column)){
                        value += "%";
                    }
                    style.setFontSize(11);
                    
                }else if(ReportTypeCons.ROC_HY.equals(reportType)){
                    style.setBold(false);
                    if ("sh_name".equals(column)) {
                        style.setFontFamily("宋体");
                    } else if ("contribution".equals(column) || "money".equals(column)) {
                        style.setFontFamily("Times New Roman");
                    }
                    //4号字体
                    style.setFontSize(14);
                }
                //针对不同模块中的不同字段的样式的特殊处理
                style = MiniTableRenderDataForCellStyle(moduleName,column,reportType,style);
                
                row[j] = new TextRenderData(value, style);
                j++;
            }
            RowRenderData rowData = RowRenderData.build(row);
            rowData.setStyle(tableStyle);
            rowsList.add(rowData);
        }
        return new MiniTableRenderData(rowRenderData, rowsList);
    }
    
    /**
     * 日期的特殊处理
     * @param date
     * @param reportType
     * @return
     */
    protected static String detailDate(Date date, String reportType) {
    	if(date == null) {return "";}
    	String result = "";
    	 String currentLanguage = ReportTypeCons.whichLanguage(reportType);
		 if("ZH".equals(currentLanguage)) { 
			 result = sdf_zh.format(date); 
		 }else if("EN".equals(currentLanguage)) {
			 result = sdf_en_hy.format(date); 
		 }else if("HY".equals(currentLanguage)) {
			 result = sdf_zh.format(date); 
		 }
		return result;
	}

	/**
     * 生成表头
     * @param cols
     * @param reportType
     * @param sysLanguage
     */
    public static RowRenderData tableHeaderH(LinkedHashMap<String, Map<String,String>> cols,String reportType,String sysLanguage) {
        RowRenderData rowRenderData = null;
        TableStyle tableStyle = new TableStyle();

        //表格边框
        if(ReportTypeCons.ROC_HY.equals(reportType)){
            //红印的表格不显示边框
            tableStyle.setHasBorder(false);
        }
        Object[] colSize = cols.keySet().toArray();
        TextRenderData[] header = new TextRenderData[colSize.length];
        int i = 0;
        for (String column : cols.keySet()) {
            Map<String,String> colMap = cols.get(column);
            String temp_name = colMap.get("temp_name");
            Style style = new Style();
            //102 股东信息
            if (ReportTypeCons.ROC_ZH.equals(reportType) || ReportTypeCons.ROC_EN.equals(reportType)) {
                style.setFontFamily("PMingLiU");
                //style.setFontFamily("Times New Roman");
                style.setUnderLine(true);
                style.setFontSize(11);
                if("sh_name".equals(column)){
                    style.setAlign(STJc.LEFT);
                }else if("contribution".equals(column)||"money".equals(column)){
                    style.setAlign(STJc.RIGHT);
                }
                if ("date".equals(column)||
                		"change_items".equals(column)||
                		"change_font".equals(column)||
                		"change_back".equals(column)
                		){
                	temp_name=TransApi.Trans(temp_name, "cht");
                }

            } else if (ReportTypeCons.ROC_HY.equals(reportType)) {
                //四号字体
                style.setFontSize(14);
                //style.setBold(true);
                if("sh_name".equals(column)){
                    style.setAlign(STJc.LEFT);
                }else if("contribution".equals(column)||"money".equals(column)){
                    style.setAlign(STJc.RIGHT);
                }
            }
            header[i] = new TextRenderData(temp_name, style);
            i++;
        }
        //表头居中
        rowRenderData = RowRenderData.build(header);
        if(ReportTypeCons.ROC_HY.equals(reportType)){
            tableStyle.setAlign(STJc.LEFT);
        }
        rowRenderData.setStyle(tableStyle);
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
                if("name".equals(column)){
                    log.error("---------company_type-------------");
                }
                String[] strs = cols.get(column).split("\\|");
                String fieldType = strs.length > 1 ? strs[1] : "";
                String getSource = strs.length > 2 ? strs[2] : "";
                String value = model.get(column) != null ? model.get(column) + "" : "";

                if ("select".equals(fieldType)) {
                    //102chiness 等级状态
                    //System.out.println(ReportTypeCons.ROC_ZH.equals(reportType));
                    //System.out.println("registration_status".equals(column));
                    if((ReportTypeCons.ROC_ZH.equals(reportType)||ReportTypeCons.ROC_EN.equals(reportType))
                            && ("registration_status".equals(column) || "year_result".equals(column) || "roc_registration_status".equals(column))){
                        Map<String,String> params = parseUrl(getSource);
                        String type = params.get("type");
                        value = !"".equals(value) ? template.getSysDictDetailStringWord(reportType,type,value) : "--";
                    }else{
                        value = !"".equals(value) ? new ReportInfoGetDataController().dictIdToString(value,reportType, sysLanguage) : "--";
                    }
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
                }
                else if("date".equals(fieldType)
                		){
                    try {
                        //处理日期格式
                        if(!StringUtils.isEmpty(value)) {
                            Date date = sdf.parse(value.trim());
                            if (ReportTypeCons.ROC_EN.equals(reportType)) {
                                value = sdf_en_hy.format(date);
                            } else {
                                value = sdf_zh.format(date);
                                //value=TransApi.Trans(value, "cht");
                            }
                        }
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                }else if("money".equals(fieldType)) {
                    //处理千位符号
                    try {
                        DecimalFormat df = new DecimalFormat("###,###.##");
                        NumberFormat nf = NumberFormat.getInstance();
                        if(!StrUtils.isEmpty(value)) {
                        	 value = df.format(nf.parse(value));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    if("business_date_end".equals(column)) {
                        //营业期限只有两种格式1. 自xxxx至xxxx  2.长期
                        if (value.contains("至")) {
                            String[] _qx = value.split("至");
                            if (_qx.length > 1) {
                                if (ReportTypeCons.ROC_EN.equals(reportType)) {
                                    value = DateUtils.getYmdHmsssEn(_qx[0])+" to " + DateUtils.getYmdHmsssEn(_qx[1]);
                                } else {
                                    value = DateUtils.getYmdHmsssZh(_qx[0]) +" 至 "+ DateUtils.getYmdHmsssZh(_qx[1]);
                                }
                            }
                        }
                    }else{
                        value = !StringUtils.isEmpty(value) ? value : "--";
                    }
                }
                value = !StringUtils.isEmpty(value) ? value : "--";
                log.error("whc 测试输出：column=" + column + "  fieldType=" + fieldType + " value=" + value);
                Style style = new Style();
                if(ReportTypeCons.ROC_ZH.equals(reportType)){
                    //单元格字体
                    if("name_trans_en".equals(column)||"postal_code".equals(column)||"telphone".equals(column)||"fax".equals(column)||
                            "registered_capital".equals(column)||"currency".equals(column)||
                        "establishment_date".equals(column)||"business_date_end".equals(column)||"registration_num".equals(column)||
                            "last_modified_date".equals(column)||"register_codes".equals(column)||"year".equals(column)){
                        //郵政編碼 電話號碼 傳真號碼 登記編號 統一信用代碼 年檢情況
                        style.setFontFamily("Times New Roman");
                    }else {
                        style.setFontFamily("PMingLiU");
                    }
                }else if(ReportTypeCons.ROC_EN.equals(reportType)){
                    //单元格字体
                    if("name_trans_en".equals(column)||"address".equals(column)||"postal_code".equals(column)||"telphone".equals(column)||"fax".equals(column)||
                            "registration_num".equals(column)||"register_codes".equals(column)||"year".equals(column)||
                            "roc_registration_status".equals(column)||"company_type".equals(column)||"currency".equals(column)||
                            "registered_capital".equals(column)||"currency".equals(column)||"establishment_date".equals(column)||"business_date_end".equals(column)||
                            "last_modified_date".equals(column)||"registration_authority".equals(column)||"year_result".equals(column)){
                        //郵政編碼 電話號碼 傳真號碼 登記編號 統一信用代碼 年檢情況
                        style.setFontFamily("Times New Roman");
                    }else {
                        style.setFontFamily("PMingLiU");
                    }
                }
                if(ReportTypeCons.ROC_EN.equals(reportType) || ReportTypeCons.ROC_ZH.equals(reportType)) {
                    if ("--".equals(value)) {
                        style.setFontFamily("Times New Roman");
                    }
                }else{
                    if ("--".equals(value)) {
                        style.setFontFamily("宋体");
                    }
                }
                map.put(column, new TextRenderData(value, style));
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
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}(占{2})"));
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
    public static String uploadReport(String filePath, String orderId, Integer userid) throws FileNotFoundException {
        String factpath = null;
        String storePath = ftpStore + "/" + DateUtils.getNow(DateUtils.YMD);
        File file = new File(filePath);
        List<File> commonFiles = new ArrayList<File>();
        List<File> pdfFiles = new ArrayList<File>();
        commonFiles.add(file);
        String now = UUID.randomUUID().toString().replaceAll("-", "");
        CreditUploadFileModel fileModel = new CreditUploadFileModel();
        fileModel.set("business_type", "311");//订单完成
        fileModel.set("business_id", orderId);

            for(File fl:commonFiles) {
                //获取真实文件名
                String originalFile = fl.getName();
                //根据文件后缀名判断文件类型
                String ext = FileTypeUtils.getFileType(originalFile);
                //检查格式
                if(!FileTypeUtils.checkType(ext)){
                    System.out.println("请检查 "+originalFile+" 的格式!");
                }
                //检查大小
                if(fl.length()>maxPostSize){
                    System.out.println(originalFile+" 必须小于5兆!");
                }
                File pdf = null;
                //如果上传文档不是pdf或者图片或者则转化为pdf，以作预览
                if (!ext.equals("pdf") && !FileTypeUtils.isImg(ext) && !ext.equalsIgnoreCase("html")) {
                    try {
                        pdf = Office2PDF.toPdf(fl);
                    }catch (Exception e){
                        e.printStackTrace();
                        ReportInfoGetData.outPutErroLog(log, e);
                        CreditOrderInfo order = CreditOrderInfo.dao.findById(orderId);
                        ReportInfoGetData.sendErrMsg(order, userid, originalFile+",预览文件生成异常,将影响预览功能,请注意!",log);
                    }
                }
                //commonFiles.add(fl);
                if (pdf != null)
                    pdfFiles.add(pdf);
            }

            boolean storePdfFile = true;
            if(pdfFiles.size()>0) {
                //预览文件上传异常宽松化处理,不向外抛出异常
                try {
                    storePdfFile = FtpUploadFileUtils.storeMoreFtpFile(now+"",pdfFiles,storePath,ip,port,userName,password);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            boolean storeCommonFile = FtpUploadFileUtils.storeMoreFtpFile(now, commonFiles, storePath, ip, port, userName, password);
            if(!storePdfFile){
                System.out.println("预览文件生成异常!");
            }
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

        return factpath;
    }

    /**
     * 发送邮件
     * @param
     * @throws Exception 
     */
    public static void sendReportMail(String toMail,List<Map<String,String>> fileList) throws Exception{
        //List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        //Map<String,String> map=new HashMap<String, String>();
        //map.put("哈哈.doc", "http://"+ip+":"+port+"/"+filePath);
        //list.add(map);
          new SendMailUtil("15269274025@163.com", "", "你好", "mycontent", fileList).sendEmail();
    }

    public static Map<String,Object> dealDataMapByreportType(String reportType,Map<String,Object> mapData){
        Map<String,Object> newMapData = new HashMap<>();

        if(ReportTypeCons.OTHER_396.equals(reportType)){
            for (String key : mapData.keySet()) {
                if("financialCompanyName".equals(key)||"396CredibilityCode".equals(key)||"financialCurrency".equals(key)||"financialUnit".equals(key)||"bigFinancial".equals(key)){
                    newMapData.put(key,mapData.get(key));
                }
            }
            mapData = newMapData;
        }
       return mapData;
    }
//发向客服送异常回执
static void sendErrorEmail(CreditOrderInfo order) throws Exception {
    sendErrorEmail( order,null);
}
    static void sendErrorEmail(CreditOrderInfo order,Exception e1) throws Exception {
        if (e1==null){
            e1 = new Exception();
            throw new RuntimeException();
        }
        if(order.get("id")==null||order.get("num")==null){
            return;
        }
            //获取当前订单客服的id
            String staffId = String.valueOf(order.getStr("create_by"));
            //获取客服的邮箱
            String  staffEmail = null;
            if(staffId!=null){
                SysUser staff =  SysUser.dao.findFirst(" select * from sys_user where userid = "+staffId );
                if(staff!=null){
                    staffEmail = String.valueOf(staff.getStr("email"));
                }
            }
            if(StringUtils.isNotBlank(staffEmail)){
                String content = "  尊敬的工作人员您好,由于文件上传至ftp服务器失败导致发往客户的订单号为:"+order.getStr("num")+"的邮件发送失败!" +
                        "<br/><br/>请重新发送,或者联系管理员!";
                try{
                    new SendMailUtil(staffEmail, "", "(订单异常回执)"+order.getStr("num"), content).sendEmail();
                }catch (Exception e2){
                    e2.printStackTrace();
                    throw new Exception("报告上传失败导致的错误且当前订单客服没有正确的邮箱!错误原因"+e1);
                }

            }
            throw new Exception("报告上传失败导致的错误!"+e1);
        }

}
