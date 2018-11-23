package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.File;
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
	 * 列宽系数
	 */
	private static int colWidthOffSet = 600;
	/**
	 * excel首行值
	 */
	private static String[] firstRow = new String[] {
			"科目",
			"初始日期值",
			"结束日期值"
	};
	
	/**
	 * 合计项key
	 */
	private static String  isSumOption = "isSumOption";
	
	/**
	 * 小模块编码 
	 */
	private static String  smallModelCode = "smallModelCode";
	
	/**
	 * 数据开始的行号
	 */
	private static int top = 0;
	
	/**
	 * 数据开始的列号
	 */
	private static int left = 0;
	
	/**
	 * 小模块列间距
	 */
	private static int smallModuleColSpacing = 3;
	
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
	
	/**
	 * 导出财务模板
	 * @param response
	 * @param ops
	 * @param sysLanguage
	 */
	public  static void  exportExcel(HttpServletResponse response, OutputStream ops,String sysLanguage) {
		
		List<CreditCompanyFinancialDict> list  = FinanceService.getFinancialDict(sysLanguage);
	
		//页码和数据之间的映射
		Map<Integer ,List<List<CreditCompanyFinancialDict>>>  pageNumToList = new HashMap<>();
		
		List<CreditCompanyFinancialDict> tempList1 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList2 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList3 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList4 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList5 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList6 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList7 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList8 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList9 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList10 = new ArrayList<>();
		List<CreditCompanyFinancialDict> tempList11 = new ArrayList<>();
		
		
		for (CreditCompanyFinancialDict model : list) {
				/**
				 *	财务子板块
				 *  1-合计 2-流动资产 3-非流动资产(固定资产) 
				 *  4-流动负债 5-非流动负债(长期负债) 
				 *  6-负债及所有者权益 7-毛利润 
				 *  8-营业利润 9-税前利润
				 *   10-所得税费用 11-重要比率表 ',
				 */
				int sonSector = model.get("son_sector");
				switch (sonSector) {
				case 1:
					tempList1.add(model);
					break;
				case 2:
					tempList2.add(model);
					break;
				case 3:
					tempList3.add(model);
					break;
				case 4:
					tempList4.add(model);
					break;
				case 5:
					tempList5.add(model);
					break;
				case 6:
					tempList6.add(model);
					break;
				case 7:
					tempList7.add(model);
					break;
				case 8:
					tempList8.add(model);
					break;
				case 9:
					tempList9.add(model);
					break;
				case 10:
					tempList10.add(model);
					break;
				case 11:
					tempList11.add(model);
					break;

				default:
					break;
				}
		}
		/**
		 * 父模块 1-合计表(子模块1) 2-资产负债表(子模块 2-6) 3-利润表(子模块 7-10) 4-重要比率表(子模块11)
		 */
		List<List<CreditCompanyFinancialDict>> tempFatherList1 = new ArrayList<>();
			tempFatherList1.add(tempList1);
		List<List<CreditCompanyFinancialDict>> tempFatherList2 = new ArrayList<>();
			tempFatherList2.add(tempList2);tempFatherList2.add(tempList3); tempFatherList2.add(tempList4); 
			tempFatherList2.add(tempList5); tempFatherList2.add(tempList6);  
		List<List<CreditCompanyFinancialDict>> tempFatherList3 = new ArrayList<>();
			tempFatherList3.add(tempList7);tempFatherList3.add(tempList8);tempFatherList3.add(tempList9);
			tempFatherList3.add(tempList10);
		List<List<CreditCompanyFinancialDict>> tempFatherList4 = new ArrayList<>();
			tempFatherList3.add(tempList11);
		pageNumToList.put(0, tempFatherList2);
		pageNumToList.put(1, tempFatherList3);
		pageNumToList.put(2, tempFatherList4);
		pageNumToList.put(3, tempFatherList1);
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
			//本页数据
			List<List<CreditCompanyFinancialDict>> pageData = pageNumToList.get(i);
			//设置头
			HSSFRow rowHead = sheet.createRow(top);
			//列宽度映射
			Map<Integer,Integer> cellNumToCellWidth = new HashMap<>();
			int a = 0;
			for (int j = 0; j < pageData.size(); j++) {
				for (int k = a; k < firstRow.length; k++) {
					String value = firstRow[k];
					int valueLength = value.length();
					rowHead.createCell(left + k).setCellValue(value);
					cellNumToCellWidth.put(left + k, valueLength * colWidthOffSet);
					if(k == 0) {
						cellNumToCellWidth.put(j, valueLength);
					} 
				}     
				a += smallModuleColSpacing;
			}
			//设置body
			for (int j = 0;j < pageData.size(); j++) {
				List<CreditCompanyFinancialDict> son_sector = pageData.get(j);
				for (int k = 0;k < son_sector.size(); k++) {
					HSSFRow rowBody = sheet.getRow(top+1+k);
					if(rowBody==null) {
						rowBody = sheet.createRow(top+1+k);
					}
					String cellValue = son_sector.get(k).get("item_name");
					int currentLength = cellValue.length();
					if(currentLength > (cellNumToCellWidth.get(j)==null?cellNumToCellWidth.get(j):0)) {
						cellNumToCellWidth.put(j, currentLength);
					}
					rowBody.createCell(left+j*smallModuleColSpacing).setCellValue(cellValue);
				}
			}
			
			//cellNumToCellWidth.put(left, maxLength*colWidthOffSet);
			//设置自动列宽 
			for (int k = left; k < firstRow.length+left; k++) {
				//sheet.autoSizeColumn(k);
				sheet.setColumnWidth(k, cellNumToCellWidth.get(k));
				System.out.println("第"+i+"个sheet页,"+"第"+k+"列,宽:"+cellNumToCellWidth.get(k));
			}
			for (Integer key : cellNumToCellWidth.keySet()) {
				sheet.setColumnWidth(key*smallModuleColSpacing, cellNumToCellWidth.get(key)*colWidthOffSet);
				System.out.println("第"+i+"个sheet页,"+"第"+key*smallModuleColSpacing+"列,宽:"+cellNumToCellWidth.get(key)*colWidthOffSet);
			}
			
			
		}
		try {
			wb.write(ops);
			ops.flush();
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
			
			response.reset();
		}
	}
	
	
	public  static void  exportExcel(File file, OutputStream ops,String sysLanguage) {
		
	}

	
}
