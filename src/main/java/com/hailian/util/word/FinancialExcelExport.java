package com.hailian.util.word;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialStatementsConf;
import com.hailian.util.SettlrExcelExportTemplate;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;


public class FinancialExcelExport extends SettlrExcelExportTemplate {
	
    private List<CreditCompanyFinancialEntry> list = new ArrayList<CreditCompanyFinancialEntry>();
    private String begin;
    private String end;
    private boolean isEnglish=false;
    private String titlPrd="";
    private String unit="单位：人民币（千元）";
    private CreditCompanyFinancialStatementsConf financialConf=new CreditCompanyFinancialStatementsConf();

	public FinancialExcelExport(List<CreditCompanyFinancialEntry> list,String begin,String end) {
		super();
		this.list = list;
	    this.begin = begin;
        this.end = end;
    }
	public FinancialExcelExport(List<CreditCompanyFinancialEntry> list,String begin,String end,boolean isEnglish,String unit) {
		super();
		this.list = list;
	    this.begin = begin;
        this.end = end;
        this.isEnglish=isEnglish;
        this.unit=unit;
    }
	
	public FinancialExcelExport(List<CreditCompanyFinancialEntry> list,
			String begin, String end, boolean isEnglish, String titlPrd,
			String unit,CreditCompanyFinancialStatementsConf financialConf) {
		super();
		this.list = list;
		this.begin = begin;
		this.end = end;
		this.isEnglish = isEnglish;
		this.titlPrd = titlPrd;
		this.unit = unit;
		this.financialConf=financialConf;
	}
	@Override
	public String[] getSheetNames() {
		if(isEnglish){
			return new String[] { "Financial" };
		}else{
			return new String[] { "财务" };
		}
	}

	@Override
	public String[][] getTitles() {
		if(isEnglish){
			return new String[][] { 
					{"Key Financial Items","",""},
			};
		}else{
			return new String[][] { 
					{"关键财务项目","",""},
			};
		}
	}

	@Override
	public String[] getCaptions() {
		return null;
	}

	@Override
	protected void buildBody(int sheetIndex) {
		bodyRowStyle=crateBodyCellStyle();
		bodyRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		CellStyle bodyLeftStyle=crateBodyCellStyle();
		CellStyle bodyRightStyle=crateBodyCellStyle();
		bodyRightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		bodyLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		bodyRightStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		CellStyle bodyCenterStyle=crateBodyCellStyle();
		bodyCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置为文本格式，防止身份证号变成科学计数法  
        DataFormat format = workbook.createDataFormat();  
        bodyLeftStyle.setDataFormat(format.getFormat("@"));
		Sheet sheet = getSheet(sheetIndex);
		int startIndex = this.getBodyStartIndex(sheetIndex);

        List<Object[]> rowList = new ArrayList<>();

        int j = 0;
        Integer old = null;
        String begin = financialConf.get("date1");
        String end = financialConf.get("date2");
        String lrbegin = financialConf.get("date3");
        String lrend = financialConf.get("date4");
        for (CreditCompanyFinancialEntry ccf : list) {
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
                	if(isEnglish){
                		title = titlPrd+"Income Statement";
                	}else{
                		title = titlPrd+"利润表";
                	}
                    break;
                case 7:
                    //title = "毛利润";
                	title = "";
                    break;
                case 8:
                	if(isEnglish){
                		title = titlPrd+"Key Ratios";
                	}else{
                		title = titlPrd+"重要比率表";
                	}
                    break;
                case 9:
                    //title = "税前利润";
                	title = "";
                    break;
                case 10:
                    //title = "所得税费用";
                	title = "";
                    break;
                    /*case 1:
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
                        break;*/
                }

                /*rowList.add(RowRenderData.build(
                        new TextRenderData(""),
                        new TextRenderData(""),
                        new TextRenderData("")));*/

                rowList.add(new Object[]{new Object[]{"",null},new Object[]{"",null},new Object[]{"",null}});

                //大标题
                Style titileStyle = new Style();
                titileStyle.setColor("843C0B");
                titileStyle.setBold(true);
                /*rowList.add(RowRenderData.build(
                        new TextRenderData(title, titileStyle),
                        new TextRenderData(""),
                        new TextRenderData("")));*/
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setColor(HSSFColor.ORANGE.index);
                font.setBold(true);
                cellStyle.setFont(font);
                rowList.add(new Object[]{new Object[]{title,cellStyle},new Object[]{"",null},new Object[]{"",null}});
                CellStyle header = workbook.createCellStyle();
                Font font2 = workbook.createFont();
                font2.setBold(true);
                header.setFont(font2);
                header.setVerticalAlignment(CellStyle.ALIGN_RIGHT);
                if("关键财务项目".equals(title)||"财务报表".equals(title)
                		||(titlPrd+"利润表").equals(title)||(titlPrd+"重要比率表").equals(title)||
                		"Key Financial Items".equals(title)||"Financial Statement".equals(title)
                		||(titlPrd+"Income Statement").equals(title)||(titlPrd+"Key Ratios").equals(title)){
                	//添加币种行
                	 if(!((titlPrd+"Key Ratios").equals(title)||(titlPrd+"重要比率表").equals(title))){
                		 rowList.add(new Object[]{new Object[]{"",null},new Object[]{"",null},new Object[]{unit,header}});
                	 }
                	//添加时间
                	if((titlPrd+"利润表").equals(title)||(titlPrd+"Income Statement").equals(title)){
                		//利润读取date3 date4  其他都是date1、date2
                		rowList.add(new Object[]{new Object[]{"",null},new Object[]{lrbegin,header},new Object[]{lrend,header}});
                	}else{
                		rowList.add(new Object[]{new Object[]{"",null},new Object[]{begin,header},new Object[]{end,header}});
                	}
                }

            }
            String itemName = ccf.getStr("item_name");
            if(isEnglish){
            	itemName = ccf.getStr("item_name_en");
            }
            String begind = ccf.get("begin_date_value")+"";
            String endd = ccf.get("end_date_value")+"";
            Integer is_sum_option = ccf.getInt("is_sum_option");
            //Style sumStyle = new Style();
            CellStyle sumStyle = workbook.createCellStyle();
            /*if (is_sum_option.intValue() == 1) {
                Font font3 = workbook.createFont();
                font3.setBold(true);
                sumStyle.setFont(font3);
            }*/
            //rowList.add(RowRenderData.build(new TextRenderData(itemName, sumStyle), new TextRenderData(begin.toString()), new TextRenderData(end.toString())));
            rowList.add(new Object[]{new Object[]{itemName,sumStyle},new Object[]{begind.toString(),null},new Object[]{endd.toString(),null}});
            j++;
        }



		for (int i = 0; i < rowList.size(); i++) {
            int index = 0;
            Row row1 = sheet.createRow(i + startIndex);
            Cell cell1 = row1.createCell((short) 1);
            cell1.setCellStyle(bodyLeftStyle);
            row1.setHeight((short) 300);

            //String itemName = model.getStr("item_name");
            //Integer begin = model.getInt("begin_date_value");
            //Integer end = model.getInt("end_date_value");
            Object[] row = rowList.get(i);
            Object[] col1 = (Object[])row[0];
            Object[] col2 = (Object[])row[1];
            Object[] col3 = (Object[])row[2];

            createStyledCell(row1, index++, col1[0]+"", (CellStyle)col1[1]);
            createStyledCell(row1, index++, col2[0]+"", (CellStyle)col2[1]);
            createStyledCell(row1, index++, col3[0]+"", (CellStyle)col3[1]);
        }
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 7000);
	}

}
