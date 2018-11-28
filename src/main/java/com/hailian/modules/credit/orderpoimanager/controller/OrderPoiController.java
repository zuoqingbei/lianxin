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
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOperationLog;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfoModel;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.agentmanager.service.AgentPriceService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.mail.service.MailService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.DateAddUtil;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
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
		String isTheSameOrder="";
		int isTheSameOrderNum=0;
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
							CreditCompanyInfo company=CreditCompanyInfo.dao.findByENname(name);
							//List<CompanyModel> companyByName = CompanyModel.dao.getCompanyByName(name);
							if(company==null){
								CompanyModel model = getModel(CompanyModel.class);
								String now = getNow();
								Integer userid = getSessionUser().getUserid();
								model.set("create_by", userid);
								model.set("create_date", now);
								model.set("name_en", name);
								model.save();
								
								//List<CompanyModel> companyByName2 = CompanyModel.dao.getCompanyByName(name);
								orderReal.set("company_id", model.get("id"));
								orderReal.set("right_company_name_en",name);
							}else{
								orderReal.set("company_id", company.get("id"));
								orderReal.set("right_company_name_en",name);
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
						
						//是否有相同报告
						CreditOrderInfo theSameOrder = OrderManagerService.service.isTheSameOrder(orderReal.get("company_id")+"",orderReal.get("report_type")+"", this);
						if(theSameOrder!=null){
							isTheSameOrderNum++;
							isTheSameOrder+=isTheSameOrderNum+".第"+r+"行，第G列监测到有相同企业名称;";
						}
						//获取报告价格
						if(orderReal.get("type") != null && orderReal.get("speed")!=null && orderReal.get("report_type")!= null  && orderReal.get("order_type")!=null && orderReal.get("custom_id")!=null && orderReal.get("country")!=null){
							CreditReportPrice pricemodel = OrderManagerService.service.getOrderprice(orderReal.get("type").toString(), orderReal.get("speed").toString(), orderReal.get("report_type").toString(), orderReal.get("order_type").toString(), orderReal.get("custom_id").toString(), orderReal.get("country").toString());
							if(pricemodel!=null){
								orderReal.set("price_id", pricemodel.get("id"));
							}else{
								errormark+=errornum+".第"+r+"行，此订单没有获取到报告价格，请联系管理员！;";
							}
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
		ResultType isTheSameOrderResult=null;
		if(StringUtils.isNotBlank(errormark)){
			errorResult=new ResultType(2, errormark);
		}else{
			errorResult=new ResultType();
		}
		if(StringUtils.isNotBlank(isTheSameOrder)){
			isTheSameOrderResult=new ResultType(2, isTheSameOrder);
		}else{
			isTheSameOrderResult=new ResultType();
		}
		
		ResultType resultType = new ResultType(totalRow,orderList);
		ResultType resultTypeReal = new ResultType(totalRow2,orderListReal);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("errormark", errorResult);
		map.put("isTheSameOrderMark", isTheSameOrderResult);
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
			List<CreditOrderInfoModel> modellist=new ArrayList<CreditOrderInfoModel>();
			for(CreditOrderInfoModel model:parseArray){
			  String num =CreditOrderInfo.dao.getNumber();
			  model.set("num", num);
			  String countryid=model.getStr("country");
			  CountryModel countrymodel = CountryModel.dao.findType(countryid);
			  String countryType =countrymodel.getStr("type");
			  String speed=model.getStr("speed");
			  String reporttype=model.getStr("report_type");
			  String orderType=model.getStr("order_type");
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			  String date = sdf.format(new Date());
			  Record reportTime = OrderManagerService.service.getReportTime(countryType, speed, reporttype, date);
			  CreditReportUsetime reportUsetime=reportTime.get("usetime");
			  String enddate = reportTime.get("enddate");
			  String time=reportUsetime.get("use_time").toString();
			  String user_time_id = reportUsetime.get("id").toString();
			  model.set("user_time", time);
			  model.set("user_time_id", user_time_id);
			  model.set("end_date", enddate);
			  model.set("create_by", userid);
			  model.set("create_date", now);
			  model.set("update_by", userid);
			  model.set("update_date", now);
			  model.set("source", "1");//订单来源-批量导入
			  model.set("receiver_date", now);
			  /*
			   * 获取报告价格
			   */
//			  CreditReportPrice pricemodel = OrderManagerService.service.getOrderprice(countryType, speed, reporttype, orderType, model.getStr("custom_id"), countryid);
//			  if(pricemodel!=null){
//				  int price_id=pricemodel.getInt("id");
//				  model.set("price_id", price_id);
//			  }
			 
			  
			  
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
			  //国外代理自动分配 除韩国新加坡马来西亚
			  if(!countryid.equals("106") && !countryid.equals("61") && !countryid.equals("62") && !countryid.equals("92")){
				  //代理自动分配
				  AgentPriceModel agentPrice = AgentPriceService.service.getAgentAbroad(countryid,speed);
				  if(agentPrice!=null){
					  model.set("status", "295");
					  model.set("agent_id", agentPrice.get("agent_id"));
					  model.set("agent_priceId", agentPrice.get("id"));
				  }else{
					  msg="提交成功，但该订单没有找到合适的代理，请注意!";
				  }
			  }
			  modellist.add(model);
			 }
			 Db.batchSave(modellist, modellist.size());
			 CreditOperationLog.dao.addOneEntry(this, null, "订单管理/批量导入/提交","/credit/orderpoimanager/savedata");//操作日志记录
			 for(CreditOrderInfoModel model:modellist){
				 if(model.get("agent_id")!= null){
					 MailService.service.toSendMail("1", model.getStr("id")+"",model.get("agent_id")+"",userid,this);//代理分配发送邮件
				 }
				 }
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg="提交失败";
			flag=false;
		}
		if(flag){
			if(msg.equals("提交成功")){
				ResultType resultType = new ResultType(1,msg);
				renderJson(resultType);
			}
			if(msg.equals("提交成功，但订单没有找到合适的代理，请注意!")){
				ResultType resultType = new ResultType(3,msg);
				renderJson(resultType);
			}
			
		}else{
			ResultType resultType = new ResultType(2, msg);
			renderJson(resultType);
		}
		
	}
	
	
}
