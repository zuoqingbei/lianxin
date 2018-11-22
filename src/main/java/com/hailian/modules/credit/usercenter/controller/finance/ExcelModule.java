package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
public class ExcelModule extends BaseProjectController  {
	/**
	 * excel首行值
	 */
	private static String[] firstRow = new String[] {
			"科目",
			"初始日期值",
			"结束日期值"
	};
	
	/**
	 * 数据到顶部距离的系数
	 */
	private static int top = 4;
	
	/**
	 * 数据到左边距离的系数
	 */
	private static int left = 4;
	
	/**
	 * 文件名称
	 */
	private static String fileName = "财务模板";
	
	public static String liabilities = "资产负债表";
	public static String profit = "利润表";
	public static String rate = "比率表";
	public static String total = "合计表";
	/**
	 * 生成excel时sheet页顺序对应名称的映射
	 */
	public static Map<Integer,String> moduleOrderMapping = new HashMap<>();
	static {
		moduleOrderMapping.put(0,liabilities);
		moduleOrderMapping.put(1, profit);
		moduleOrderMapping.put(2, rate);
		moduleOrderMapping.put(3, total);
	}
	
	public  static void  exportExcel(HttpServletResponse response, OutputStream ops,String sysLanguage) {
		
		//是否是合计项
		String isSumOption = "isSumOption";
		List<CreditCompanyFinancialDict> list  = FinanceService.getFinancialDict(sysLanguage);
		List<CreditCompanyFinancialDict> liabilitiesTable = new ArrayList<>();
		List<CreditCompanyFinancialDict> profitTable = new ArrayList<>();
		List<CreditCompanyFinancialDict> rateTable = new ArrayList<>();
		List<CreditCompanyFinancialDict> totalTable = new ArrayList<>();
		//页码和数据之间的映射
		Map<Integer,List<CreditCompanyFinancialDict>> pageNumToList = new HashMap<>();
		for (CreditCompanyFinancialDict model : list) {
			//判断是否为合计项 0-否 1-是
			if((Integer)model.get("is_sum_option")==1) {
				model.put(isSumOption, true);
			}
			//父模块 1-合计表 2-资产负债表 3-利润表 4-重要比率表
			int parentSector = model.get("parent_sector");
			if(parentSector==1) {
				liabilitiesTable.add(model);
				pageNumToList.put(0, liabilitiesTable);
			}else if (parentSector==2) {
				profitTable.add(model);
				pageNumToList.put(1, profitTable);
			}else if (parentSector==3) {
				rateTable.add(model);
				pageNumToList.put(2, rateTable);
			}else if (parentSector==4) {
				totalTable.add(model);
				pageNumToList.put(3, totalTable);
			}
		}
		
		try {
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1" );
			response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");//指定下载的文件名
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			fileName = "warning!";
			e.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		//按照规定的顺序创建sheet页
		for (int i = 0; i < moduleOrderMapping.size(); i++) {
			wb.createSheet(moduleOrderMapping.get(i));
		}
		//插入数据
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			HSSFSheet sheet = wb.getSheetAt(i);
			//设置头
			HSSFRow rowHead = sheet.createRow(top-1);
			for (int j = 0; j < firstRow.length; j++) {
				rowHead.createCell(left+j-1).setCellValue(firstRow[j]);
			}
			//设置body
			for (int j = 0; j < pageNumToList.get(i).size(); j++) {
				HSSFRow rowBody = sheet.createRow(top+j);
				rowBody.createCell(left-1).setCellValue(pageNumToList.get(i).get(j).getStr("item_name"));
			}
			//设置自动列宽(随表头)
			for (int k = left; k < firstRow.length+left; k++) {
				sheet.autoSizeColumn(k);
			}
			
		}
		try {
			wb.write(ops);
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(ops!=null) {
				try {
					ops.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
