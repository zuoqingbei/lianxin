package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import com.alibaba.fastjson.JSONObject;


public class ExcelUtils {



	 
	/**
	 * 用户传入的公式或者约束规则
	 */
	private Map<Integer, String> map;
	// 构造方法，传入要导出的数据
	public ExcelUtils() {
		super();
	}


	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 设置单元格背景颜色
		style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return style;

	}
	
	/*
	 * 列数据信息单元格样式
	 */
	@SuppressWarnings("deprecation")
	public  static HSSFCellStyle getDefaultStyle(HSSFWorkbook workbook,boolean isBigFont,boolean isCenter) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		 font.setFontHeightInPoints((short)12);
		// 字体加粗
		 font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 设置字体名字
		font.setFontName("微软雅黑");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		/*// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
*/		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		if(isBigFont) {
			style.setFont(font);
		}
		style.setFillBackgroundColor(new HSSFColor.ORANGE().getIndex());
		style.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		// 设置自动换行;
		style.setWrapText(false);
		if(isCenter) {
			// 设置水平对齐的样式为居中对齐;
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 设置垂直对齐的样式为居中对齐;
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}
		

		return style;

	}
	
	/*
	 * 鍒楁暟鎹俊鎭崟鍏冩牸鏍峰紡
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		// font.setFontHeightInPoints((short)10);
		// 瀛椾綋鍔犵矖
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Courier New");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setWrapText(false);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
}
