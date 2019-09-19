package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialDict;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
public class ExcelModule extends BaseProjectController  {
	/**
	 * 列宽系数
	 */
	private static int colWidthOffSet = 621;
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
	private static String fileName = "FinancialModule";
	
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
	 */
	public  static void  exportExcel(HttpServletResponse response, OutputStream ops,int type) {
		
		if(type==1) {
			smallModuleColSpacing = 5;
			colWidthOffSet = 620;
			firstRow = new String[] {
					"科目",
					"初始日期值",
					"结束日期值"
			}; 
			fileName = "ChineseFinancialTemplate";
		}else if(type==2){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
			firstRow = new String[] {
					"itemName",
					"beginValue",
					"endValue"
			};
			fileName = "EnglishFinancialTemplate";
		}else if(type==3){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
			firstRow = new String[] {
					"itemName",
					"beginValue",
					"endValue"
			};
			fileName = "FinancialModuleFor396";
		}
		
		
		List<List<CreditCompanyFinancialDict>> sonSectorList = getSonSectorList(type);
		Map<Integer,List<List<CreditCompanyFinancialDict>>> pageNumToList = new LinkedHashMap<>();
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
		if(type==1||type==2) {
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
		}else if(type==3){
			List<List<CreditCompanyFinancialDict>> tempFatherList1 = new ArrayList<>();
			tempFatherList1.add(sonSectorList.get(0));
			pageNumToList.put(3, tempFatherList1);
		}
	 
		try {
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1" );
			response.setHeader("Content-Disposition","attachment;filename="+fileName+".xls");//指定下载的文件名
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			fileName = "warning!";
			e.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		int currentPage = 0 ;
		//插入数据
		for (Integer pageNum : pageNumToList.keySet()) {
			//按照规定的顺序创建sheet页
			wb.createSheet(moduleOrderMapping.get(pageNum));
			HSSFSheet sheet = wb.getSheetAt(currentPage);
			
			//本页数据
			List<List<CreditCompanyFinancialDict>> pageData = pageNumToList.get(pageNum);
			//列宽度映射
			Map<Integer,Integer> cellNumToCellWidth = new HashMap<>();
			
			//设置头
			HSSFRow rowHead = sheet.createRow(top);
			HSSFCellStyle rowHeadStyle = ExcelUtils.getDefaultStyle(wb,true,false);
			HSSFCellStyle rowHeadStyle2 = ExcelUtils.getDefaultStyle(wb,false,false);
			
	        // 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
	        HSSFPatriarch patr = sheet.createDrawingPatriarch();
	       
			
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
					 // 定义注释的大小和位置,详见文档
			        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)3, 5, (short) 3, 5));
			        // 设置注释内容
			        comment.setString(new HSSFRichTextString("该单元格是默认项,禁止修改!"));
			        // 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
			        comment.setAuthor("lzg");
					cell.setCellComment(comment);
				}
			}
			
			for (Integer key : cellNumToCellWidth.keySet()) {
				sheet.setColumnWidth(key*smallModuleColSpacing+left, cellNumToCellWidth.get(key)*colWidthOffSet);
				System.out.println("第"+currentPage+"个sheet页,"+"第"+(key*smallModuleColSpacing+left)+"列,宽:"+cellNumToCellWidth.get(key)*colWidthOffSet);
			}
			currentPage++;
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
					ops = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 导入财务模板
	 * @param file
	 * @param type
	 * @param financeConfigId
	 * @return 上传结果
	 */
	public  static List<CreditCompanyFinancialEntry>  parseExcel(File file,int type,String financeConfigId,String userId, String now) {
		if(type==1) {
			smallModuleColSpacing = 5;
			colWidthOffSet = 620;
		}else if(type==2){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
		}else if(type==3){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
		}
		
		
		Map<Integer,List<Integer>> sumOptionMap = getSumOption(type);
		if(file==null||file.length()==0) {
			return null;
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
		List<CreditCompanyFinancialEntry> allModel = new ArrayList<>();
		
		CreditCompanyFinancialEntry model = new CreditCompanyFinancialEntry();
		//公共属性
		model.set("conf_id", financeConfigId).set("update_date", now).set("update_by", userId).set("create_date",now).set("create_by", userId).set("del_flag", 0).set("begin_date_value", "0").set("end_date_value", "0").set("type", type);
		//为了适应jfinal框架的Db.saveBatch方法
		CreditCompanyFinancialEntry tempModel = new CreditCompanyFinancialEntry();
		tempModel._setAttrs(model).set("parent_sector", "9").set("son_sector", "9").set("is_sum_option", "9"). set("sort_no", "9").set("item_name", "9")
		.set("begin_date_value", "9").set("end_date_value", "9").set("del_flag", "9").set("class_name1", "9").set("class_name2", "9").set("is_default", "9");
		allModel.add(tempModel);
		
		if(type==1||type==2) {
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				HSSFSheet sheet = wb.getSheetAt(i);
				if(i==0) {
					 sheet = wb.getSheetAt(3);
				}else {
					sheet = wb.getSheetAt(i-1);
				}
				/**
				 * 父模块 1-合计表(子模块1) 2-资产负债表(子模块 2-6) 3-利润表(子模块 7-10) 4-重要比率表(子模块11)
				 */
				int k = 0;
				switch (i) {
				//解析合计表
				case 0:
					k = 0;
					model.set("parent_sector", 1);
					for (int j = 0; j < 1; j++) {
						int targetSonSector = j+1;
						model.set("son_sector", targetSonSector);
						List<String> isDefault = Db.query("select is_default from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className1 = Db.query("select class_name1 from credit_company_financial_dict where son_sector=? and type=?",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className2 = Db.query("select class_name2 from credit_company_financial_dict where son_sector=? and type=?",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						allModel.addAll(parseSonSector(model, left+k*smallModuleColSpacing, top+1, sheet,sumOptionMap.get(j),className1,className2,isDefault));
						k++;
					}
					break;
					//解析资产负债表
				case 1:
					model.set("parent_sector", 2);
					for (int j = 1; j < 6; j++) {
						int targetSonSector = j+1;
						model.set("son_sector", targetSonSector);
						List<String> isDefault = Db.query("select is_default from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className1 = Db.query("select class_name1 from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className2 = Db.query("select class_name2 from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						allModel.addAll(parseSonSector(model, left+k*smallModuleColSpacing, top+1, sheet,sumOptionMap.get(j),className1,className2,isDefault)); 
						k++;
					}
					break;
					//解析利润表
				case 2:
					k = 0;
					model.set("parent_sector", 3);
					for (int j = 6; j < 10; j++) {
						int targetSonSector = j+1;
						model.set("son_sector", targetSonSector);
						List<String> isDefault = Db.query("select is_default from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className1 = Db.query("select class_name1 from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className2 = Db.query("select class_name2 from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						allModel.addAll(parseSonSector(model, left+k*smallModuleColSpacing, top+1, sheet,sumOptionMap.get(j),className1,className2,isDefault)); 
						k++;
					}
					break;
					//解析重要比率表
				case 3:
					k = 0;
					model.set("parent_sector", 4);
					for (int j = 10; j < 11; j++) {
						int targetSonSector = j+1;
						model.set("son_sector", targetSonSector);
						List<String> isDefault = Db.query("select is_default from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className1 = Db.query("select class_name1"
								+ " from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						List<String> className2 = Db.query("select class_name2 from credit_company_financial_dict where son_sector=? and type=? order by sort_no,id",Arrays.asList(new String[] {targetSonSector+"",type+""}).toArray());
						allModel.addAll(parseSonSector(model, left+k*smallModuleColSpacing, top+1, sheet,sumOptionMap.get(j),className1,className2,isDefault)); 
						k++;
					}
					break;
				default:
					break;
				}
			}
		}else if(type==3) {
			
		}
		
		return allModel;
	}
	
	/**
	 *  解析子模块
	 * @param model
	 * @param colStart
	 * @param rowStart
	 * @param sheet
	 * @return
	 */
	public static List<CreditCompanyFinancialEntry>  parseSonSector(CreditCompanyFinancialEntry model,int colStart, int rowStart, HSSFSheet sheet,List<Integer> sumOptionList,List<String> className1,List<String> className2,List<String> isDefault){
		
		List<CreditCompanyFinancialEntry> list = new ArrayList<>();
		 CreditCompanyFinancialEntry tempModel = new CreditCompanyFinancialEntry();
		int maxRow = 65535;
		 for (int i = rowStart; i < maxRow; i++) {
			 
			 tempModel = new CreditCompanyFinancialEntry();
			 tempModel.clear()._setAttrs(model);
			//设置默认项
			if(i-rowStart<isDefault.size()) {
				 tempModel.set("is_default", isDefault.get(i-rowStart));
			}
			//设置字典表已有的className
			if(i-rowStart<className1.size()) {
				 tempModel.set("class_name1", className1.get(i-rowStart));
			 }else {//设置新增的className
				 if((int)model.get("son_sector")==7) {
						tempModel.set("class_name1", className1.get(2));
						tempModel.set("class_name2", className2.get(2));
					}else {
						tempModel.set("class_name1", className1.get(0));
						tempModel.set("class_name2", className2.get(0));	
					}
					
			 }
			
			 if(i-rowStart<className2.size()) {
				 tempModel.set("class_name2", className2.get(i-rowStart));
			 }
			//判断是否是合计项
			if(sumOptionList!=null&&sumOptionList.contains(i-rowStart)) {
				tempModel.set("is_sum_option", 1);
			}else {
				tempModel.set("is_sum_option", 0);
			}
			
			HSSFRow row = sheet.getRow(i);
			
			if(row!=null&&row.getPhysicalNumberOfCells()>0) {
				HSSFCell itemName = row.getCell(colStart);
				String str1 = ""; 
				if(itemName!=null) {
					itemName.setCellType(CellType.STRING);
					str1 = itemName.getStringCellValue();
					if(!StrUtils.isEmpty(str1)) {
						tempModel.set("item_name", str1.trim());
						//System.out.println("item_name:"+str1);
					} 
				} 
				if("营业总额/资产总额".equals(str1.trim())||"营业成本/营业总额".equals(str1.trim())){
					System.out.println(1);
				}
				HSSFCell beginDateValue = row.getCell(colStart+1);
				String str2 = ""; 
				if(beginDateValue!=null) {
					beginDateValue.setCellType(CellType.STRING);
					str2 = beginDateValue.getStringCellValue();
					if(!StrUtils.isEmpty(str2)) {
						tempModel.set("begin_date_value", str2.trim());
					} 
				} 
				
				HSSFCell endDateValue = row.getCell(colStart+2);
				String str3 = ""; 
				if(endDateValue!=null) {
					endDateValue.setCellType(CellType.STRING);
					str3 = endDateValue.getStringCellValue();
					if(!StrUtils.isEmpty(str3)) {
						tempModel.set("end_date_value", str3.trim());
					} 
				} 
				if(StrUtils.isEmpty(str1.trim())&&StrUtils.isEmpty(str2.trim())&&StrUtils.isEmpty(str3.trim())) {
					break;
				}
				list.add(tempModel);
				//System.out.println((String)tempModel.get("class_name1"));
			}else {
				break;
			}
		}
		return list;
	}
	
	
	
	/**
	 * 找到当前类型下所有子模板集合
	 * @param type
	 * @return
	 */
	public static List<List<CreditCompanyFinancialDict>> getSonSectorList(int type) {
		//财务字典表子模块代码集合
		List<Integer> sonSectorCodeList = Db.query
				("select son_sector from credit_company_financial_dict where type=?  GROUP  BY son_sector",
			     Arrays.asList(new String[] {type+""}).toArray());
		//找到当前类型下所有模板实体
		List<CreditCompanyFinancialDict> list  = FinanceService.getFinancialDict(type);
		//页码和数据之间的映射
		List<List<CreditCompanyFinancialDict>>  sonSectorList = new LinkedList<>();
		
		for (int i = 0; i < sonSectorCodeList.size(); i++) {
			List<CreditCompanyFinancialDict> tempList = new ArrayList<>();
			for (CreditCompanyFinancialDict model : list) {
				int sonSector = model.get("son_sector");
				if(sonSectorCodeList.get(i)!=null&&sonSector==sonSectorCodeList.get(i)) {
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
	 * @return 子模块下标与对应合计项下标集合的映射,
	 */
	public static Map<Integer,List<Integer>> getSumOption(int type){
		List<List<CreditCompanyFinancialDict>> sonSectorList = getSonSectorList( type);
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
	 * @return
	 */
	public static boolean isSumOption(Map<Integer,List<Integer>> sumOptionMap,Integer sonSectorIndex,Integer entryIndex) {
		if(sumOptionMap.get(sonSectorIndex).contains(entryIndex)) {return true; }return false;
	}
	public static List<CreditCompanyFinancialEntry> getEntries(String confId, int selector){
		List<CreditCompanyFinancialEntry> list = CreditCompanyFinancialEntry.dao.find("select * from  credit_company_financial_entry where conf_id=? and son_sector=? and del_flag=0",new String[]{confId,selector+""});
		return list;
	}
	//HSSFCell cellItemName = rowBody.createCell(left+j*smallModuleColSpacing);

	public static HSSFCell createCell(HSSFRow row,String value,HSSFCellStyle style,int multiple,int offset ){
		HSSFCell cell = row.createCell(left+offset+multiple*smallModuleColSpacing);
		try {
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(Double.parseDouble(value));
		}catch (Exception e){
			e.printStackTrace();
			cell.setCellType(CellType.STRING);
			cell.setCellValue(value);
		}

		cell.setCellStyle(style);
		return  cell;
	}
	public static void exportExcelWithData(HttpServletResponse response, ServletOutputStream ops, int type, String confId) {

		if(type==1) {
			smallModuleColSpacing = 5;
			colWidthOffSet = 620;
			firstRow = new String[] {
					"科目",
					"初始日期值",
					"结束日期值"
			};
			fileName = "ChineseFinancialTemplate";
		}else if(type==2){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
			firstRow = new String[] {
					"itemName",
					"beginValue",
					"endValue"
			};
			fileName = "EnglishFinancialTemplate";
		}else if(type==3){
			smallModuleColSpacing = 9;
			colWidthOffSet = 345;
			firstRow = new String[] {
					"itemName",
					"beginValue",
					"endValue"
			};
			fileName = "FinancialModuleFor396";
		}

		Map<Integer,List<List<CreditCompanyFinancialEntry>>> pageNumToList = new LinkedHashMap<>();
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

		if(type==1||type==2) {
			List<List<CreditCompanyFinancialEntry>> tempFatherList1 = new ArrayList<>();
			tempFatherList1.add(getEntries(confId,1));
			List<List<CreditCompanyFinancialEntry>> tempFatherList2 = new ArrayList<>();
			tempFatherList2.add(getEntries(confId,2));tempFatherList2.add(getEntries(confId,3)); tempFatherList2.add(getEntries(confId,4));
			tempFatherList2.add(getEntries(confId,5)); tempFatherList2.add(getEntries(confId,6));
			List<List<CreditCompanyFinancialEntry>> tempFatherList3 = new ArrayList<>();
			tempFatherList3.add(getEntries(confId,7));tempFatherList3.add(getEntries(confId,8));tempFatherList3.add(getEntries(confId,9));
			tempFatherList3.add(getEntries(confId,10));
			List<List<CreditCompanyFinancialEntry>> tempFatherList4 = new ArrayList<>();
			tempFatherList4.add(getEntries(confId,11));
			pageNumToList.put(0, tempFatherList2);
			pageNumToList.put(1, tempFatherList3);
			pageNumToList.put(2, tempFatherList4);
			pageNumToList.put(3, tempFatherList1);
		}else if(type==3){
			List<List<CreditCompanyFinancialEntry>> tempFatherList1 = new ArrayList<>();
			tempFatherList1.add(getEntries(confId,0));
			pageNumToList.put(3, tempFatherList1);
		}

		try {
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1" );
			response.setHeader("Content-Disposition","attachment;filename="+fileName+".xls");//指定下载的文件名
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			fileName = "warning!";
			e.printStackTrace();
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		int currentPage = 0 ;
		//插入数据
		for (Integer pageNum : pageNumToList.keySet()) {
			//按照规定的顺序创建sheet页
			wb.createSheet(moduleOrderMapping.get(pageNum));
			HSSFSheet sheet = wb.getSheetAt(currentPage);

			//本页数据
			List<List<CreditCompanyFinancialEntry>> pageData = pageNumToList.get(pageNum);
			//列宽度映射
			Map<Integer,Integer> cellNumToCellWidth = new HashMap<>();

			//设置头
			HSSFRow rowHead = sheet.createRow(top);
			HSSFCellStyle rowHeadStyle = ExcelUtils.getDefaultStyle(wb,true,false);
			HSSFCellStyle rowHeadStyle2 = ExcelUtils.getDefaultStyle(wb,false,false);

			// 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
			HSSFPatriarch patr = sheet.createDrawingPatriarch();


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
				List<CreditCompanyFinancialEntry> son_sector = pageData.get(j);
				for (int k = 0;k < son_sector.size(); k++) {
					HSSFRow rowBody = sheet.getRow(top+1+k);
					if(rowBody==null) {
						rowBody = sheet.createRow(top+1+k);
					}

					String itemName = son_sector.get(k).get("item_name");
					String begainValue = son_sector.get(k).get("begin_date_value")+"";
					String endValue = son_sector.get(k).get("end_date_value")+"";

					int currentLength = itemName.length();
					if(currentLength > (cellNumToCellWidth.get(j)==null?0:cellNumToCellWidth.get(j))) {
						cellNumToCellWidth.put(j, currentLength);
					}
					HSSFCell cellItemName = createCell(rowBody,itemName,rowHeadStyle2,j,0);
					createCell(rowBody,begainValue,rowHeadStyle2,j,1);
					createCell(rowBody,endValue,rowHeadStyle2,j,2);
					// 定义注释的大小和位置,详见文档
					HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short)3, 5, (short) 3, 5));
					// 设置注释内容
					comment.setString(new HSSFRichTextString("该单元格是默认项,禁止修改!"));
					// 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
					comment.setAuthor("lzg");
					//如果是合计项
					if ("1".equals(son_sector.get(k).get("is_default")+"")){
						cellItemName.setCellComment(comment);
					}
				}
			}

			for (Integer key : cellNumToCellWidth.keySet()) {
				sheet.setColumnWidth(key*smallModuleColSpacing+left, cellNumToCellWidth.get(key)*colWidthOffSet);
				System.out.println("第"+currentPage+"个sheet页,"+"第"+(key*smallModuleColSpacing+left)+"列,宽:"+cellNumToCellWidth.get(key)*colWidthOffSet);
			}
			currentPage++;
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
					ops = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
