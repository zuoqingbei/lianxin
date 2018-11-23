package com.hailian.util;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;



public class AchievementsExport extends SettlrExcelExportTemplate {
	
private List<CreditOrderInfo> list = new ArrayList<CreditOrderInfo>();
	
	public AchievementsExport(List<CreditOrderInfo> list) {
		super();
		this.list = list;
	}

	@Override
	public String[] getSheetNames() {
		return new String[] { "订单绩效" };
	}

	@Override
	public String[][] getTitles() {
		return new String[][] { 
				{"名称","订单日期","到期日期","客户代码","订单公司名称","公司中文名称","扣分情况","提成"},
		};
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
//		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期

		for (int i = 0; i < list.size(); i++) {
			int index = 0;
			Row row1 = sheet.createRow(i + startIndex );
			Cell cell1 = row1.createCell((short) 1);  
	        cell1.setCellStyle(bodyLeftStyle);
			row1.setHeight((short) 300);
		CreditOrderInfo searchIndex = list.get(i);
			createStyledCell(row1, index++, searchIndex.get("reportName")==null? "":searchIndex.get("reportName").toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchIndex.get("receiver_date")==null? "":searchIndex.get("receiver_date").toString(),bodyLeftStyle);
			createStyledCell(row1, index++, searchIndex.get("end_date")==null? "":searchIndex.get("end_date").toString(),bodyLeftStyle);
			createStyledCell(row1, index++, searchIndex.get("custom_id")==null? "":searchIndex.get("custom_id").toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchIndex.get("englishName")==null? "":searchIndex.get("englishName").toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchIndex.get("companyName")==null? "":searchIndex.get("companyName").toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchIndex.get("custom_id")==null? "":searchIndex.get("custom_id").toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchIndex.get("custom_id")==null? "":searchIndex.get("custom_id").toString(),bodyRowStyle);
			
		}
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		
	
	}

}
