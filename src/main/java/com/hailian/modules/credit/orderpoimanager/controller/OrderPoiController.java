package com.hailian.modules.credit.orderpoimanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.DateAddUtil;
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
		String errormark="";
		int errornum=0;
		List<UploadFile> upLoadFile = getFiles();
		List<CreditOrderInfo> orderList = new ArrayList<CreditOrderInfo>();
		List<CreditOrderInfo> orderListReal = new ArrayList<CreditOrderInfo>();
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
			//获取Excel文档中的第一个表单
			Sheet sht0 = wb0.getSheetAt(0);
			int totalRows=sht0.getPhysicalNumberOfRows();//获取表格行数
			int coloumNum=sht0.getRow(0).getPhysicalNumberOfCells();
			  for(int r=0;r<totalRows;r++){
				  Row row = sht0.getRow(r);//获取每一行
				  if(r==0 || null==row){
					  continue;
				  }
				  if(r>0 && row!=null){
					  boolean flag=false;//判断行数是否有有效数据
					  for(int i=0;i<coloumNum;i++){
						  if (row.getCell(i) != null) {
							  flag=true;
							  break;
						  }
					  }
					  if(!flag){
						  continue;
					  }
					  CreditOrderInfo order = new CreditOrderInfo();//向页面回显
					  CreditOrderInfo orderReal = new CreditOrderInfo();//解析表格内容获取其对应编码对象
					  if (row.getRowNum() < 1) {
							continue;
						}
						if (row.getCell(0) != null) {
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							String custom_id=row.getCell(0).getStringCellValue();
							List<CustomInfoModel> customById = CustomInfoModel.dao.getCustom(Integer.parseInt(custom_id));
							if(CollectionUtils.isEmpty(customById)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第A列信息填写错误;";
							}else{
								orderReal.set("custom_id", customById.get(0).get("id"));
								order.put("customerName",customById.get(0).get("name"));
							}
							order.set("custom_id", custom_id);
							
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第A列信息漏填;";
						}
						
						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							String continent = row.getCell(1).getStringCellValue();
							List<SysDictDetail> continentList = SysDictDetail.dao.getDictDetailByContinent(continent);
							
							if(CollectionUtils.isEmpty(continentList)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第B列信息填写错误;";
							}else{
								orderReal.set("continent", continentList.get(0).get("detail_id"));
							}
							order.set("continent", continent);
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第B列信息漏填;";
						}
						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							String countryName = row.getCell(2).getStringCellValue();
							List<CountryModel> countryByName = CountryModel.dao.getCountryByName(countryName);
							if(CollectionUtils.isEmpty(countryByName)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第C列信息填写错误;";
							}else{
								orderReal.set("country", countryByName.get(0).get("id"));
								orderReal.put("type", countryByName.get(0).get("type"));
							}
							order.set("country", countryName);
						}else{
							errornum++;
							errormark+=errornum+".第"+r+"行，第C列信息漏填;";
						}
						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							String report_type = row.getCell(3).getStringCellValue();
							List<ReportTypeModel> reportTypeByName = ReportTypeModel.dao.getReportTypeByName(report_type);
							if(CollectionUtils.isEmpty(reportTypeByName)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第D列信息填写错误;";
							}else{
								orderReal.set("report_type", reportTypeByName.get(0).get("id"));
							}
							order.set("report_type", report_type);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第D列信息漏填;";

						}
						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							String order_type = row.getCell(4).getStringCellValue();
							List<SysDictDetail> dictDetailByOrderType = SysDictDetail.dao.getDictDetailByOrderType(order_type);
							if(CollectionUtils.isEmpty(dictDetailByOrderType)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第E列信息填写错误；";
							}else{
								orderReal.set("order_type", dictDetailByOrderType.get(0).get("detail_id"));
							}
							order.set("order_type", order_type);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第E列信息漏填;";
						}
						if (row.getCell(5) != null) {
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							String report_language=row.getCell(5).getStringCellValue();
							List<SysDictDetail> dictDetailBy = SysDictDetail.dao.getDictDetailBy(report_language,"language");
							if(CollectionUtils.isEmpty(dictDetailBy)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第F列信息填写错误；";
							}else{
								orderReal.set("report_language", dictDetailBy.get(0).get("detail_id"));
							}
							order.set("report_language", report_language);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第F列信息漏填;";
						}
						if (row.getCell(6) != null) {
							row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
							String name = row.getCell(6).getStringCellValue();
							List<CompanyModel> companyByName = CompanyModel.dao.getCompanyByName(name);
							if(CollectionUtils.isEmpty(companyByName)){
								CompanyModel model = getModel(CompanyModel.class);
								String now = getNow();
								Integer userid = getSessionUser().getUserid();
								model.set("create_by", userid);
								model.set("create_date", now);
								model.set("name", name);
								model.save();
								List<CompanyModel> companyByName2 = CompanyModel.dao.getCompanyByName(name);
								orderReal.set("company_id", model.get("id"));
							}else{
								orderReal.set("company_id", companyByName.get(0).get("id"));
							}
							order.set("company_by_report", name);
							
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第G列信息漏填;";

						}
						if (row.getCell(7) != null) {
							row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
							String speed = row.getCell(7).getStringCellValue();
							List<SysDictDetail> dictDetailByOrderSpeed = SysDictDetail.dao.getDictDetailByOrderSpeed(speed);
							if(CollectionUtils.isEmpty(dictDetailByOrderSpeed)){
								errornum++;
								errormark+=errornum+".第"+r+"行，第I列信息填写错误;";
							}else{
								orderReal.set("speed", dictDetailByOrderSpeed.get(0).get("detail_id"));
							}
							order.set("speed", speed);
						} else {
							errornum++;
							errormark+=errornum+".第"+r+"行，第H列信息漏填;";

						}
						if (row.getCell(8) != null) {
							row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
							String reference_num = row.getCell(8).getStringCellValue();
							order.set("reference_num", reference_num);
							orderReal.set("reference_num", reference_num);
						} 
						//地址
						if (row.getCell(9) != null) {
							row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
							String address = row.getCell(9).getStringCellValue();
							order.set("address", address);
							orderReal.set("address", address);
						} 
						//电话
						if (row.getCell(10) != null) {
							row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
							String telphone = row.getCell(10).getStringCellValue();
							order.set("telphone", telphone);
							orderReal.set("telphone", telphone);
						} 
						//传真
						if (row.getCell(11) != null) {
							row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
							String fax = row.getCell(11).getStringCellValue();
							order.set("fax", fax);
							orderReal.set("fax", fax);
						} 
						//邮箱
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
							String email = row.getCell(12).getStringCellValue();
							order.set("email", email);
							orderReal.set("email", email);
						} 
						//联系人
						if (row.getCell(13) != null) {
							row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
							String contacts = row.getCell(13).getStringCellValue();
							order.set("contacts", contacts);
							orderReal.set("contacts", contacts);
						} 
						//其他细节
						if (row.getCell(14) != null) {
							row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
							String remarks = row.getCell(14).getStringCellValue();
							order.set("remarks", remarks);
							orderReal.set("remarks", remarks);
						} 
						order.set("receiver_date", new Date());
						orderList.add(order);
						orderListReal.add(orderReal);
				  }
			  }
		}
		int totalRow = orderList.size();
		int totalRow2 = orderListReal.size();
		ResultType errorResult=null;
		if(StringUtils.isNotBlank(errormark)){
			errorResult=new ResultType(2, errormark);
		}else{
			errorResult=new ResultType();
		}
		
		ResultType resultType = new ResultType(totalRow,orderList);
		ResultType resultTypeReal = new ResultType(totalRow2,orderListReal);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("errormark", errorResult);
//		map.put("errormark", errormark);
		map.put("orderList", resultType);
		map.put("orderListReal", resultTypeReal);
		renderJson(map);
	}
	/**
	 * 获取表格json数据 插入数据库
	* @author doushuihai  
	* @date 2018年9月26日下午4:36:27  
	* @TODO
	 */
	public void savedata() {
		boolean flag=true;
		String msg="提交成功";
		List<CreditOrderInfoModel> parseArray;
		try {
			String jsonString = HttpKit.readData(getRequest());
			parseArray = JSON.parseArray(jsonString, CreditOrderInfoModel.class);
			Date now = new Date();
			Integer userid = getSessionUser().getUserid();
			for(CreditOrderInfoModel model:parseArray){
			  String num =CreditOrderInfo.dao.getNumber();
			  model.set("num", num);
			  String countryid=model.getStr("country");
			  CountryModel countrymodel = CountryModel.dao.findType(countryid);
			  String countryType =countrymodel.getStr("type");
			  String speed=model.getStr("speed");
			  String reporttype=model.getStr("report_type");
			  String orderType=model.getStr("order_type");
			  CreditReportUsetime timemodel = OrderManagerService.service.getTime(countryType, speed, reporttype/*, orderType*/);
			  int user_time_id = 0;
			  int use_time;
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			  Calendar ca = Calendar.getInstance();
		      ca.setTime(now);//设置接单时间
		      String year=String.valueOf(ca.get(Calendar.YEAR));
			  String month=String.valueOf(ca.get(Calendar.MONTH)+1);
			  model.set("year", year);
			  model.set("month", month);
				if(timemodel==null) {
					timemodel=new CreditReportUsetime();
					use_time=0;
				}else {
					user_time_id=timemodel.getInt("id");
					use_time=timemodel.get("use_time");
					model.set("user_time", use_time);
					model.set("user_time_id", user_time_id);
				}
				Calendar c = 
						new DateAddUtil().addDateByWorkDay(ca,//当前时间
								//需要用多少天
								(int)Math.ceil(use_time/24.0));
				String enddate=sdf.format(c.getTime());
				if(use_time==0) {
					enddate=null;
				}
			  model.set("end_date", enddate);
			  model.set("create_by", userid);
			  model.set("create_date", now);
			  model.set("source", "1");//订单来源-批量导入
			  model.set("receiver_date", now);
			  CreditReportPrice pricemodel = OrderManagerService.service.getPrice(countryType, speed, reporttype, orderType);
			  int price_id=pricemodel.getInt("id");
			  model.set("price_id", price_id);
			  List<SysDictDetail> dictDetailBy = SysDictDetail.dao.getDictDetailBy("订单分配","orderstate");
			  if(dictDetailBy !=null){
				  int status=dictDetailBy.get(0).getInt("detail_id");
				  model.set("status", status);
			  }
			  try {
				String reportIdtoOrder = OrderManagerService.service.getReportIdtoOrder();//根据自动分配规则获取该订单指定的报告员
				  model.set("report_user", reportIdtoOrder);//自动分配订单
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ResultType resultType = new ResultType(2, "请先设置分配规则，以便分配给报告员！");
				renderJson(resultType);
				return;
			}
			  boolean save = model.save();
				if(save==false){
					msg="提交失败";
					flag=false;
				}
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="提交失败";
			flag=false;
		}
		if(flag){
			ResultType resultType = new ResultType();
			renderJson(resultType);
		}else{
			ResultType resultType = new ResultType(2, msg);
			renderJson(resultType);
		}
		
	}
	
	
}
