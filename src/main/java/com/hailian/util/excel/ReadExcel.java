package com.hailian.util.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* @author dyc:
* @time 2018年9月18日 上午10:04:39
* @todo
*/
public class ReadExcel {
	
	List<List<String>> readXls(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        // HSSFWorkbook 标识整个excel
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        int size = hssfWorkbook.getNumberOfSheets();
        // 循环每一页，并处理当前循环页
        for (int numSheet = 0; numSheet < size; numSheet++) {
            // HSSFSheet 标识某一页
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                // HSSFRow表示行
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                int minColIx = hssfRow.getFirstCellNum();
                int maxColIx = hssfRow.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                // 遍历改行，获取处理每个cell元素
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    // HSSFCell 表示单元格
                    HSSFCell cell = hssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(getStringVal(cell));
                }
                result.add(rowList);
            }
        }
        return result;
    }
	
	 List<List<String>> readXlsx(String path) throws Exception {
	        InputStream is = new FileInputStream(path);
	        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	        List<List<String>> result = new ArrayList<List<String>>();
	        // 循环每一页，并处理当前循环页
	        for (XSSFSheet xssfSheet : xssfWorkbook) {
	            if (xssfSheet == null) {
	                continue;
	            }
	            // 处理当前页，循环读取每一行
	            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	                int minColIx = xssfRow.getFirstCellNum();
	                int maxColIx = xssfRow.getLastCellNum();
	                List<String> rowList = new ArrayList<String>();
	                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
	                    XSSFCell cell = xssfRow.getCell(colIx);
	                    if (cell == null) {
	                        continue;
	                    }
	                    rowList.add(cell.toString());
	               }
	               result.add(rowList);
	           }
	      }
	      return result;
	  }
	 
	 public static String getStringVal(HSSFCell cell) {
	        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_BOOLEAN:
	            return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
	        case Cell.CELL_TYPE_FORMULA:
	            return cell.getCellFormula();
	        case Cell.CELL_TYPE_NUMERIC:
	            cell.setCellType(Cell.CELL_TYPE_STRING);
	            return cell.getStringCellValue();
	        case Cell.CELL_TYPE_STRING:
	            return cell.getStringCellValue();
	        default:
	            return "";
	        }
	 }
}
