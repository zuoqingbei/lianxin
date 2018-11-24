package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.hailian.modules.admin.ordermanager.model.CreditReditCompanyFinancialEntry;
import com.hailian.system.dict.DictCache;
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
	 * 数据开始的行号
	 */
	private static int top = 0;
	
	/**
	 * 数据开始的列号
	 */
	private static int left = 0;
	
	/**
	 * 小模块列间距(包含本身列数)
	 */
	private static int smallModuleColSpacing = 5;
	
	/**
	 * 文件名称
	 */
	private static String fileName = "财务模板";
	
	/**
	 * sheet页名称
	 */
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
		
		List<List<CreditCompanyFinancialDict>> sonSectorList = getSonSectorList(sysLanguage);
		Map<Integer,List<List<CreditCompanyFinancialDict>>> pageNumToList = new HashMap<>();
		/**
		 * 父模块 1-合计表(子模块1) 2-资产负债表(子模块 2-6) 3-利润表(子模块 7-10) 4-重要比率表(子模块11)
		 */
		 /**
		 *	财务子板块
		 *  1-合计 2-流动资产 3-非流动资产(固定资产) 
		 *  4-流动负债 5-非流动负债(长期负债) 
		 *  6-负债及所有者权益 7-毛利润 
		 *  8-营业利润 9-税前利润
		 *   10-所得税费用 11-重要比率表 ',
		 */ 
		List<List<CreditCompanyFinancialDict>> tempFatherList1 = new ArrayList<>();
			tempFatherList1.add(sonSectorList.get(0));
		List<List<CreditCompanyFinancialDict>> tempFatherList2 = new ArrayList<>();
			tempFatherList2.add(sonSectorList.get(1));tempFatherList2.add(sonSectorList.get(2)); tempFatherList2.add(sonSectorList.get(3)); 
			tempFatherList2.add(sonSectorList.get(4)); tempFatherList2.add(sonSectorList.get(5));  
		List<List<CreditCompanyFinancialDict>> tempFatherList3 = new ArrayList<>();
			tempFatherList3.add(sonSectorList.get(6));tempFatherList3.add(sonSectorList.get(7));tempFatherList3.add(sonSectorList.get(8));
			tempFatherList3.add(sonSectorList.get(9));
		List<List<CreditCompanyFinancialDict>> tempFatherList4 = new ArrayList<>();
			tempFatherList4.add(sonSectorList.get(10));
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
			//列宽度映射
			Map<Integer,Integer> cellNumToCellWidth = new HashMap<>();
			
			//设置头
			HSSFRow rowHead = sheet.createRow(top);
			HSSFCellStyle rowHeadStyle = ExportExcel.getDefaultStyle(wb,true,false);
			HSSFCellStyle rowHeadStyle2 = ExportExcel.getDefaultStyle(wb,false,false);
			int a = 0;//列位置系数
			for (int j = 0; j < pageData.size(); j++) {
					for (int k = 0; k < firstRow.length; k++) {
						String value = firstRow[k];
						int valueLength = value.length();
						HSSFCell cell = rowHead.createCell(left + k + a*smallModuleColSpacing);
						cell.setCellValue(value);
						cell.setCellStyle(rowHeadStyle);
						sheet.setColumnWidth(left + k + a*smallModuleColSpacing, valueLength * colWidthOffSet);
						if(k == 0) {
							cellNumToCellWidth.put(j, valueLength);
						} 
					}     
					a++;
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
					if(currentLength > (cellNumToCellWidth.get(j)==null?0:cellNumToCellWidth.get(j))) {
						cellNumToCellWidth.put(j, currentLength);
					}
					HSSFCell cell = rowBody.createCell(left+j*smallModuleColSpacing);
					cell.setCellValue(cellValue);
					cell.setCellStyle(rowHeadStyle2);
				}
			}
			
			for (Integer key : cellNumToCellWidth.keySet()) {
				sheet.setColumnWidth(key*smallModuleColSpacing+left, cellNumToCellWidth.get(key)*colWidthOffSet);
				System.out.println("第"+i+"个sheet页,"+"第"+(key*smallModuleColSpacing+left)+"列,宽:"+cellNumToCellWidth.get(key)*colWidthOffSet);
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
	

	/**
	 * 导入财务模板
	 * @param file
	 * @param sysLanguage
	 * @param financeConfigId
	 * @return 上传结果
	 */
	public  static String  importExcel(File file,String sysLanguage,String financeConfigId,Integer userId, String now) {
		
		Map<Integer,List<Integer>> sumOptionMap = getSumOption(sysLanguage);
		if(file==null||file.length()==0) {
			return "上传文件不能为空!";
		}
		InputStream is = null;
		HSSFWorkbook wb = null;
		try {
			is = new FileInputStream(file);
			wb = new HSSFWorkbook(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			List<CreditReditCompanyFinancialEntry> allModel = new ArrayList<>();
			HSSFSheet sheet = wb.getSheetAt(i);
			CreditReditCompanyFinancialEntry model = new CreditReditCompanyFinancialEntry();
			model.set("conf_id", financeConfigId).set("update_date", now).set("update_by", userId).set("create_date",now).set("create_by", userId);
			switch (i) {
			//解析资产负债表
			case 1:
				model.set("parent_sector", 1);
				for (int j = 0; j < 5; j++) {
					allModel.addAll(parseSonSector(model, left+j*smallModuleColSpacing, top+1, sheet,sumOptionMap));
				}
				
				break;

			default:
				break;
			}
		}
		return financeConfigId;
	}
	
	/**
	 *  解析子模块
	 * @param model
	 * @param colStart
	 * @param rowStart
	 * @param sheet
	 * @return
	 */
	public static List<CreditReditCompanyFinancialEntry>  parseSonSector(CreditReditCompanyFinancialEntry model,int colStart, int rowStart, HSSFSheet sheet,Map<Integer,List<Integer>> sumOptionMap){
		List<CreditReditCompanyFinancialEntry> list = new ArrayList<>();
		int maxRow = 65535;
		 for (int i = 0; i < maxRow; i++) {
			HSSFRow row = sheet.getRow(rowStart);
			if(row!=null) {
				CreditReditCompanyFinancialEntry tempModel = model;
				HSSFCell itemName = row.getCell(colStart);
				
				if(itemName!=null) {
					itemName.setCellType(CellType.STRING);
					tempModel.set("item_name", itemName.getStringCellValue());
				} 
				
				HSSFCell beginDateValue = row.getCell(colStart+1);
				if(beginDateValue!=null) {
					beginDateValue.setCellType(CellType.STRING);
					tempModel.set("begin_date_value", beginDateValue.getStringCellValue());
				}
				
				HSSFCell endDateValue = row.getCell(colStart+2);
				if(endDateValue!=null) {
					endDateValue.setCellType(CellType.STRING);
					tempModel.set("end_date_value", endDateValue.getStringCellValue());
				} 
				list.add(tempModel);
			}else {
				break;
			}
		}
		return list;
	}
	
	
	
	/**
	 * 找到当前语言下所有子模板集合
	 * @param sysLanguage
	 * @return
	 */
	public static List<List<CreditCompanyFinancialDict>> getSonSectorList(String sysLanguage) {
		List<Integer> sonSectorCodeList =  DictCache.getSonSectorCodeList();
		//找到当前语言下所有模板实体
		List<CreditCompanyFinancialDict> list  = FinanceService.getFinancialDict(sysLanguage);
		//页码和数据之间的映射
		List<List<CreditCompanyFinancialDict>>  sonSectorList = new LinkedList<>();
		
		for (int i = 0; i < sonSectorCodeList.size(); i++) {
			List<CreditCompanyFinancialDict> tempList = new ArrayList<>();
			for (CreditCompanyFinancialDict model : list) {
				int sonSector = model.get("son_sector");
				if(sonSector==sonSectorCodeList.get(i)) {
					tempList.add(model);
				}
			}
			sonSectorList.add(tempList);
		}
		return sonSectorList;
	}
	
	
	/**
	 * 解析子模块获取子模块下标与对应合计项下标集合的映射
	 * 财务子板块
	 * @param sonSectorList
	 * @return 子模块下标与对应合计项下标集合的映射,
	 */
	public static Map<Integer,List<Integer>> getSumOption(String sysLanguage){
		List<List<CreditCompanyFinancialDict>> sonSectorList = getSonSectorList( sysLanguage);
		Map<Integer,List<Integer>> sonCodeToSumOptionMapping = new HashMap<>();
		for (int i = 0; i < sonSectorList.size(); i++) {
			List<Integer> sumOptionList = new LinkedList<>();
			List<CreditCompanyFinancialDict> sonSector = sonSectorList.get(i);
			for (int j = 0; j < sonSector.size(); j++) {
				//如果是合计项目
				if(sonSector.get(j).getInt("is_sum_option")==1) {
					sumOptionList.add(j);
				}
			}
			sonCodeToSumOptionMapping.put(i, sumOptionList);
		}
		return sonCodeToSumOptionMapping;
	}

	/**
	 *判断是否是合计项 
	 * @param sumOptionMap 子模块下标与对应合计项下标集合的映射,
	 * @param sonSectorCode 子模块下标
	 * @param index 实体下标
	 * @return
	 */
	public static boolean isSumOption(Map<Integer,List<Integer>> sumOptionMap,Integer sonSectorIndex,Integer entryIndex) {
		if(sumOptionMap.get(sonSectorIndex).contains(entryIndex)) {return true; }return false;
	}
	
	
	
	
	
	
	
}
