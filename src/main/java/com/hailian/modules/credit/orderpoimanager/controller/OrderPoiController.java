package com.hailian.modules.credit.orderpoimanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.orderpoimanager.service.OrderPoiService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
* @author dyc:
* @time 2018年9月17日 下午6:57:23
* @todo
*/
@Api(tag = "订单批量导入", description = "操作订单批量导入")
@ControllerBind(controllerKey = "/credit/orderpoimanager")
public class OrderPoiController extends BaseProjectController {
	private static final String path = "/pages/credit/orderpoimanager/orderpoi_";

	/**
	 * 
	 * @time   2018年9月19日 上午10:42:31
	 * @author dyc
	 * @todo   向数据库中导入数据
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/orderpoimanager/importExcel", httpMethod = "POST")
	public void importExcel() throws IOException {
		UploadFile upLoadFile = getFile("xxxx");
		List<CreditOrderInfo> orderList = new ArrayList<CreditOrderInfo>();
		File file = upLoadFile.getFile();
		FileInputStream fileIn = new FileInputStream(file);
		//根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb0 = null;
		if (file != null) {
			//根据指定的文件输入流导入Excel从而产生Workbook对象
			if (FileTypeUtils.getFileType(upLoadFile.getOriginalFileName()).equals("xlsx")) {
				wb0 = new XSSFWorkbook(fileIn);
			} else if (FileTypeUtils.getFileType(upLoadFile.getOriginalFileName()).equals("xls")) {
				wb0 = new HSSFWorkbook(new POIFSFileSystem(fileIn));
			}
			//获取Excel文档中的第一个表单
			Sheet sht0 = wb0.getSheetAt(0);
			//对Sheet中的每一行进行迭代
			for (Row r : sht0) {
				CreditOrderInfo order = new CreditOrderInfo();
				//如果当前行的行号（从0开始）未达到2（第三行）则从新循环
				if (r.getRowNum() < 1) {
					continue;
				}
				if (r.getCell(0) != null) {
					r.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					order.set("num", r.getCell(0));
				}
				if (r.getCell(1) != null) {
					r.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					order.set("receiver_date", r.getCell(1));
				}
				if (r.getCell(2) != null) {
					r.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					order.set("end_date", r.getCell(2));
				}
				if (r.getCell(3) != null) {
					r.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					order.set("custom_id", r.getCell(3));
				}
				if (r.getCell(4) != null) {
					r.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					order.set("company_by_report", r.getCell(4));
				} else {
					renderText("公司名称不能为空");

				}
				if (r.getCell(5) != null) {
					r.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					order.set("report_language", r.getCell(5));
				} else {
					renderText("报告语言不能为空");

				}
				if (r.getCell(6) != null) {
					r.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					order.set("country", r.getCell(6));
				} else {
					renderText("国家不能为空");
				}
				if (r.getCell(7) != null) {
					r.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					order.set("report_type", r.getCell(7));
				} else {
					renderText("报告类型不能为空");

				}
				if (r.getCell(8) != null) {
					r.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					order.set("speed", r.getCell(8));
				} else {
					renderText("报告速度不能为空");

				}
				orderList.add(order);
			}
		}
		renderJson(orderList);
	}
}
