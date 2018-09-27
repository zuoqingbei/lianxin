package com.hailian.modules.credit.orderpoimanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfoModel;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.dict.SysDictDetail;
import com.jfinal.kit.HttpKit;
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
	 * @todo   解析订单表格获取数据
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/orderpoimanager/importExcel", httpMethod = "POST")
	public void importExcel() throws IOException {
		Integer userid = getSessionUser().getUserid();
		String errormark="";
		int errornum=0;
		List<UploadFile> upLoadFile = getFiles();
		List<Object> list=new ArrayList<Object>();
		List<CreditOrderInfo> orderList = new ArrayList<CreditOrderInfo>();
		File file = upLoadFile.get(0).getFile();
		FileInputStream fileIn = new FileInputStream(file);
		//根据指定的文件输入流导入Excel从而产生Workbook对象
		Workbook wb0 = null;
		if (file != null) {
			//根据指定的文件输入流导入Excel从而产生Workbook对象
			if (FileTypeUtils.getFileType(upLoadFile.get(0).getOriginalFileName()).equals("xlsx")) {
				wb0 = new XSSFWorkbook(fileIn);
			} else if (FileTypeUtils.getFileType(upLoadFile.get(0).getOriginalFileName()).equals("xls")) {
				wb0 = new HSSFWorkbook(new POIFSFileSystem(fileIn));
			}
			List<CountryModel> countrys = CountryModel.dao.getCountrys(null);
			//获取Excel文档中的第一个表单
			Sheet sht0 = wb0.getSheetAt(0);
			
			int totalRows=sht0.getPhysicalNumberOfRows();//获取表格行数
//			totalRows=sht0.getLastRowNum();//获得总行数
			  for(int r=0;r<totalRows;r++){
				  Row row = sht0.getRow(r);//获取每一行
				  if(r==0 || null==row){
					  continue;
				  }
				  if(r>0 && row!=null){
					  CreditOrderInfo order = new CreditOrderInfo();
					  CreditOrderInfo orderReal = new CreditOrderInfo();
					  String countryId="";
					  String reportId ="";
					  if (row.getRowNum() < 1) {
							continue;
						}
						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							String custom_id=row.getCell(1).getStringCellValue();
							List<CustomInfoModel> customById = CustomInfoModel.dao.getCustom(Integer.parseInt(custom_id));
							if(CollectionUtils.isEmpty(customById)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第B列信息填写错误;";
							}
							order.set("custom_id", custom_id);
							order.put("customerName",customById.get(0).get("name"));
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第B列信息漏填;";
						}
						
						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							String continent = row.getCell(2).getStringCellValue();
							List<SysDictDetail> continentList = SysDictDetail.dao.getDictDetailByContinent(continent);
							
							if(CollectionUtils.isEmpty(continentList)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第C列信息填写错误;";
							}
							order.set("continent", continent);
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第C列信息漏填;";
						}
						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							String countryName = row.getCell(3).getStringCellValue();
							List<CountryModel> countryByName = CountryModel.dao.getCountryByName(countryName);
							if(CollectionUtils.isEmpty(countryByName)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第D列信息填写错误;";
							}
							order.set("country", countryName);
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第D列信息漏填;";
						}
						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							String report_type = row.getCell(4).getStringCellValue();
							List<ReportTypeModel> reportTypeByName = ReportTypeModel.dao.getReportTypeByName(report_type);
							
							if(CollectionUtils.isEmpty(reportTypeByName)){
								reportId = reportTypeByName.get(0).get("id");
								errornum++;
								errormark+=errornum+".第"+r+"行，第E列信息填写错误;";
							}
							order.set("report_type", report_type);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第E列信息漏填;";

						}
						if (row.getCell(5) != null) {
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							String order_type = row.getCell(5).getStringCellValue();
							List<SysDictDetail> dictDetailByOrderType = SysDictDetail.dao.getDictDetailByOrderType(order_type);
							if(CollectionUtils.isEmpty(dictDetailByOrderType)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第F列信息填写错误；";
							}
							order.set("order_type", order_type);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第F列信息漏填;";
						}
						if (row.getCell(6) != null) {
							row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
							order.set("report_language", row.getCell(6).getStringCellValue());
						} else {
							renderText("报告语言不能为空");
						}
						if (row.getCell(7) != null) {
							row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
							String name = row.getCell(7).getStringCellValue();
							List<CompanyModel> companyByName = CompanyModel.dao.getCompanyByName(name);
							if(CollectionUtils.isEmpty(companyByName)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第H列信息填写错误;";
							}
							order.set("company_by_report", name);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第H列信息漏填;";

						}
						if (row.getCell(8) != null) {
							row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
							String speed = row.getCell(8).getStringCellValue();
							List<SysDictDetail> dictDetailByOrderSpeed = SysDictDetail.dao.getDictDetailByOrderSpeed(speed);
							if(CollectionUtils.isEmpty(dictDetailByOrderSpeed)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第I列信息填写错误;";
							}
							order.set("speed", speed);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第I列信息漏填;";

						}
						if (row.getCell(9) != null) {
							row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
							String reference_num = row.getCell(9).getStringCellValue();
							order.set("reference_num", reference_num);
						} 
						//地址
						if (row.getCell(10) != null) {
							row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
							String address = row.getCell(10).getStringCellValue();
							order.set("address", address);
						} 
						//电话
						if (row.getCell(11) != null) {
							row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
							String telphone = row.getCell(11).getStringCellValue();
							order.set("telphone", telphone);
						} 
						//传真
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
							String fax = row.getCell(12).getStringCellValue();
							order.set("fax", fax);
						} 
						//邮箱
						if (row.getCell(13) != null) {
							row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
							String email = row.getCell(13).getStringCellValue();
							order.set("email", email);
						} 
						//联系人
						if (row.getCell(14) != null) {
							row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
							String contacts = row.getCell(14).getStringCellValue();
							order.set("contacts", contacts);
						} 
						//其他细节
						if (row.getCell(15) != null) {
							row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
							String remarks = row.getCell(15).getStringCellValue();
							order.set("remarks", remarks);
						} 
						order.set("receiver_date", new Date());
						orderList.add(order);
				  }
			  }
		}
		int totalRow = orderList.size();
		ResultType resultType = new ResultType(totalRow,orderList);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("errormark", errormark);
		map.put("orderList", resultType);
		System.out.println(orderList);
		renderJson(map);
	}
	/**
	 * 获取表格json数据 插入数据库
	* @author doushuihai  
	* @date 2018年9月26日下午4:36:27  
	* @TODO
	 */
	public void savedata() {
		String msg="提交成功";
		List<CreditOrderInfoModel> parseArray;
		try {
			String jsonString = HttpKit.readData(getRequest());
			parseArray = JSON.parseArray(jsonString, CreditOrderInfoModel.class);
			String now = getNow();
			Integer userid = getSessionUser().getUserid();
			for(CreditOrderInfoModel model:parseArray){
				model.remove("customName");
				 model.set("create_by", userid);
				 model.set("create_date", now);
				 boolean save = model.save();
					if(save==false){
						msg="提交失败";
					}
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="提交失败";
		}
		renderMessage(msg);
	}
	
	
}
