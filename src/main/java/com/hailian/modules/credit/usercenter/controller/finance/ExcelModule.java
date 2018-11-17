package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/finance")
public class ExcelModule extends BaseProjectController  {
	
	public void  exportExcel() {
		HttpServletResponse response = getResponse();
		String fileName = "";
		try {
			  fileName = new String("财务模板".getBytes("UTF-8"),"ISO-8859-1" );
		} catch (UnsupportedEncodingException e1) {
			fileName = "warning!";
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");//指定下载的文件名
		//response.setContentType("application/vnd.ms-excel");
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("utf-8");
		OutputStream ops = null;
		try {
			ops = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			wb.createSheet("阿斯顿发生");
			wb.createSheet("二分无法");;
			wb.write(ops);
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
