package  com.hailian.util;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WangXuzheng
 *
 */
public abstract class SettlrExcelExportTemplate<T> implements ExcelExportTemplate<T> {
	Logger logger=LoggerFactory.getLogger(SettlrExcelExportTemplate.class);
	/**
	 * 默认表格宽度
	 */
	private static final int DEFAULT_COLUMN_WIDTH = 5000;
	/**
	 * excel文件对象
	 */
	protected Workbook  workbook;
	/**
	 * excel sheet列表
	 */
	protected List<Sheet> sheets = new ArrayList<Sheet>();
	/**
	 * 标题栏
	 */
	protected String[][] titles;
	protected CellStyle captionRowSytle;
	/**
	 * 默认标题行样式
	 */
	protected CellStyle titleRowStyle;
	/**
	 * 默认内容行样式
	 */
	protected CellStyle bodyRowStyle;
	/**
	 * 各个sheet是否包含抬头,key:sheet坐标，value:包含true,否则false
	 */
	protected Map<Integer, Boolean> hasCaptionMap = new HashMap<Integer, Boolean>();
	/**
	 * 默认单元格宽度
	 */
	protected int columnWidth = DEFAULT_COLUMN_WIDTH;
	/**
	 * 参数列表
	 */
	protected T parameters;

	/* (non-Javadoc)
	 * @see com.haier.openplatform.excel.ExcelExportService#doExport(java.io.OutputStream)
	 */
	@Override
	public void doExport(HttpServletResponse response,String fileName) throws IOException {
 		String[] sheetNames = this.getSheetNames();
		Validate.notEmpty(sheetNames);
		this.workbook = new SXSSFWorkbook(getRowAccessWindowSize());
		this.titles = this.getTitles();
		this.captionRowSytle = crateCaptionCellStyle();
		this.titleRowStyle = crateTitleCellStyle();
		this.bodyRowStyle = crateBodyCellStyle();
		this.afterCreateWorkBook();
		
		for (int i = 0; i < sheetNames.length; i++) {
			Sheet sheet = workbook.createSheet(sheetNames[i]);
			this.sheets.add(sheet);
			afterBuildSheet(i);
			buildCaption(i);
			buildTitle(i);
			afterBuildTitle(i);
			buildBody(i);
			afterBuildBody(i);
		}
		response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);     
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        //workbook.close();
        ouputStream.close(); 
        
	}

    /**
     * 下载excel到指定目录
     * @throws IOException
     */
    public void downloadExcel(String path) throws IOException{
        File file = new File(path);
        String[] sheetNames = this.getSheetNames();
        Validate.notEmpty(sheetNames);
        this.workbook = new SXSSFWorkbook(getRowAccessWindowSize());
        this.titles = this.getTitles();
        this.captionRowSytle = crateCaptionCellStyle();
        this.titleRowStyle = crateTitleCellStyle();
        this.bodyRowStyle = crateBodyCellStyle();
        this.afterCreateWorkBook();
        for (int i = 0; i < sheetNames.length; i++) {
            Sheet sheet = workbook.createSheet(sheetNames[i]);
            this.sheets.add(sheet);
            afterBuildSheet(i);
            buildCaption(i);
            buildTitle(i);
            afterBuildTitle(i);
            buildBody(i);
            afterBuildBody(i);
        }
        BufferedOutputStream ouputStream = new BufferedOutputStream(new FileOutputStream(file));
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

	/**
	 * 创建单元格
	 * @param row
	 * @param index
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void createStyledCell(Row row,int index,String cellValue,CellStyle cellStyle){
		Cell cell = row.createCell(index);
		cell.setCellValue(cellValue==null?"":cellValue);
		cell.setCellStyle(cellStyle);
	}
	/**
	 * 创建单元格（数字类型）
	 * @param row
	 * @param index
	 * @param cellValue
	 * @param cellStyle
	 * @throws Exception 
	 */
	protected <E extends Number> void createNumStyledCell(Row row,int index,E cellValue,CellStyle cellStyle){
		Cell cell = row.createCell(index);
		cell.setCellValue(numParseDouble(cellValue));
		cell.setCellStyle(cellStyle);
	}
	/**
	 * 数字类型转double
	 * @param e
	 * @return
	 */
	private  <E extends Number> Double numParseDouble(E e){
		double value=0;
		if(e!=null){
			Method m;
			try {
				m = e.getClass().getMethod("doubleValue");
				value=(Double)m.invoke(e);
			} catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
			}
		}
		return value;
	}
	/**
	 * 创建单元格
	 * @param row
	 * @param index
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void createStyledCell(Row row,int index,Double cellValue,CellStyle cellStyle){
		Cell cell = row.createCell(index);
		if(cellValue==null){
			cell.setCellValue("");
		}else{
			cell.setCellValue(cellValue);
		}
		cell.setCellStyle(cellStyle);
	}
	
	/**
	 * 创建Integer单元格
	 * @param row
	 * @param index
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void createStyledCell(Row row,int index,Integer cellValue,CellStyle cellStyle){
		Cell cell = row.createCell(index);
		if(cellValue==null){
			cell.setCellValue("");
		}else{
			cell.setCellValue(cellValue);
		}
		cell.setCellStyle(cellStyle);
	}	
	/**
	 * @description 创建double类型单元格
	 * @author lau
	 * @version 2016-7-13上午11:36:06
	 * @param
	 */
	protected void createStyledCell(Row row,int index,Long cellValue,CellStyle cellStyle){
		Cell cell = row.createCell(index);
		if(cellValue==null){
			cell.setCellValue("");
		}else{
			cell.setCellValue(cellValue);
		}
		cell.setCellStyle(cellStyle);
	}
	
	/**
	 * 在创建完毕HSSFWorkBook对象和样式对象后作的处理操作，通常用来对默认的样式进行重新定义
	 */
	protected void afterCreateWorkBook(){
	}
	/**
	 * 获取excel抬头样式
	 * @return
	 */
	protected CellStyle crateCaptionCellStyle() {
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_NORMAL);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		font.setFontHeight((short)250);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}
	
	/**
	 * 获取excel表头样式
	 * @return
	 */
	protected CellStyle crateTitleCellStyle() {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);// 字体大小
		font.setColor(HSSFColor.WHITE.index);// 字体颜色
		font.setFontName("微软雅黑");
//		font.setColor(Font.COLOR_NORMAL);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		font.setFontHeight((short)250);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		short border = 1;
		setCellBorder(cellStyle,border,border,border,border);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return cellStyle;
	}
	
	/**
	 * 设置单元格的border
	 * @param cellStyle
	 * @param top
	 * @param bottom
	 * @param left
	 * @param right
	 */
	protected void setCellBorder(CellStyle cellStyle,short top,short bottom,short left,short right){
		cellStyle.setBorderBottom(bottom);
		cellStyle.setBorderLeft(left);
		cellStyle.setBorderRight(right);
		cellStyle.setBorderTop(top);
	}
	
	/**
	 * 获取excel内容样式
	 * @return
	 */
	protected CellStyle crateBodyCellStyle() {
		Font font = workbook.createFont();
		//font.setColor(HSSFColor.BLUE_GREY.index);
		font.setFontHeightInPoints((short) 9);// 字体大小
		font.setColor(HSSFColor.BLACK.index);// 字体颜色
		font.setFontName("微软雅黑");
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(false);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		short border = 1;
		setCellBorder(cellStyle,border,border,border,border);
		return cellStyle;
	}

	/**
	 * 获取第n个excel sheet
	 * @param sheetIndex
	 * @return
	 */
	protected Sheet getSheet(int sheetIndex) {
		return this.sheets.get(sheetIndex);
	}

	/**
	 * 创建sheet完毕后做的操作
	 * @param sheetIndex
	 */
	protected void afterBuildSheet(int sheetIndex) {
	}
	
	/**
	 * 在sheet的第一行插入标题
	 * @param sheetIndex
	 */
	protected void buildCaption(int sheetIndex){
		Sheet sheet = getSheet(sheetIndex);
		String[] captions = this.getCaptions(); 
		hasCaptionMap.put(sheetIndex, false);
		if(captions != null && captions.length >=sheetIndex +1){
			String caption = captions[sheetIndex];
			if(StringUtils.isNotBlank(caption)){
				Row row = sheet.createRow(0);
				int lastColumn = calculateLastColumn(sheetIndex);
				CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, lastColumn);
				sheet.addMergedRegion(cellRangeAddress);
				createStyledCell(row, 0, caption, this.captionRowSytle);
				hasCaptionMap.put(sheetIndex, true);
			}
		}
	}
	
	/**
	 * 计算最后一列数据数据的
	 * @param sheetIndex
	 * @return
	 */
	protected int calculateLastColumn(int sheetIndex){
		if(this.titles != null && sheetIndex <= this.titles.length -1 && this.titles[sheetIndex] != null){
			return this.titles[sheetIndex].length - 1;
		}else{
			return 1;
		}
	}

	/**
	 * 创建sheet中数据的标题
	 * @param sheetIndex
	 */
	protected void buildTitle(int sheetIndex){
		// TODO Auto-generated method stub
		if(this.titles.length < sheetIndex + 1){
			return;
		}
		String[] ts = this.titles[sheetIndex];
		if(ts == null){
			return;
		}
		//表头样式创建
		titleRowStyle=crateTitleCellStyle();
		//表头数填充
		Sheet sheet=this.getSheet(sheetIndex);
		int titleStartRow=this.getTitleStartIndex(sheetIndex);
		for(int i=titleStartRow;i<this.titles.length+titleStartRow;i++){
			Row title=sheet.createRow(i);
			for(int j=0;j<this.titles[i].length;j++){
				sheet.setColumnWidth(j, columnWidth);
				createStyledCell(title, j, this.titles[i][j], titleRowStyle);
			}
		}
	}
	
	/**
	 * 获取各个sheet内容部分起始行index,默认为从第一行开始
	 * @param sheetIndex sheet的index
	 * @return
	 */
	protected int getBodyStartIndex(int sheetIndex){
		int captionRow = getTitleStartIndex(sheetIndex);;
		int titleRow = 0;
		if(this.titles != null && this.titles.length >= sheetIndex + 1){
			if(titles[sheetIndex] != null && titles[sheetIndex].length >0){
				titleRow = 1;
			}
		}
		return captionRow + titleRow;
	}
	
	/**
	 * 获取各个sheet内容部分起始行index,默认为从第一行开始，支持三行以上动态表头
	 * @param sheetIndex sheet的index
	 * @return
	 */
	protected int getBodyStartIndex1(int sheetIndex){
		int captionRow = getTitleStartIndex(sheetIndex);;
		int titleRow = 0;
		if(this.titles != null && this.titles.length >= sheetIndex + 1){
			if(titles[sheetIndex] != null && titles.length >0){
				titleRow = titles.length;
			}
		}
		return captionRow + titleRow;
	}

	protected int getTitleStartIndex(int sheetIndex){
		return this.hasCaptionMap.get(sheetIndex) ? 1 : 0;
	}
	/**
	 * 创建sheet中数据的标题之后做的操作
	 * @param sheetIndex
	 */
	protected void afterBuildTitle(int sheetIndex) {
	}

	/**
	 * 创建sheet中数据的内容
	 * @param sheetIndex
	 */
	protected abstract void buildBody(int sheetIndex);

	/**
	 * 创建sheet中数据的内容之后做的操作
	 * @param sheetIndex
	 */
	protected void afterBuildBody(int sheetIndex) {
	}

	@Override
	public String[] getCaptions() {
		return new String[]{};
	}

	@Override
	public int getRowAccessWindowSize() {
		return 200;
	}
}
