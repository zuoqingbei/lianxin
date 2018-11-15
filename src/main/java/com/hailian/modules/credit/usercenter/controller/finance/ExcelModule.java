package com.hailian.modules.credit.usercenter.controller.finance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.usermodel.Font;
import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/finance")
public class ExcelModule extends BaseProjectController  {
	
	public void  exportExcel() {
		try {
			OutputStream ops = getResponse().getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			wb.setSheetName(0, "阿斯顿发生");
			wb.setSheetName(1, "啊啊啊");
			wb.write(ops);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
