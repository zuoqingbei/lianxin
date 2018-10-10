package com.hailian.modules.admin.ordermanager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.controller.Reporter;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderHistory;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportLanguage;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.admin.ordermanager.model.CreditReportUsetime;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @className OrderInfoService.java
 * @time   2018年8月23日 上午10:32:34
 * @author yangdong
 * @todo   TODO
 */
public class OrderManagerService {
	//static使该service保证了单例,public可以使Controller方便调用该service
	public static OrderManagerService service = new OrderManagerService();//名字都叫service，统一命名

	/**
	 * 
	 * @time   2018年8月23日 下午3:45:09
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param coi
	 * @throws Exception 
	 * @return_type   void
	 * 修改订单/添加订单
	 */
	public void modifyOrder(int id, CreditOrderInfo coi, SysUser user, BaseProjectController c) throws Exception {

		try {
			if (id != 0) {
				coi.set("id", id);
				coi.update();
			} else {
				Date date=new Date();
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(date);
			    String year=String.valueOf(calendar.get(Calendar.YEAR));
			    String month=String.valueOf(calendar.get(Calendar.MONTH));
				coi.set("receiver_date", date);
				coi.set("year", year);
				coi.set("month", month);
				coi.save();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 
	 * @time   2018年9月3日 上午11:06:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param user
	 * @throws Exception 
	 * @return_type   void
	 * 添加历史记录
	 * 
	 */
	public void addOrderHistory(int id, SysUser user) throws Exception {
		try {
			if (id != 0) {
				CreditOrderInfo coi = CreditOrderInfo.dao.findById(id);
				CreditOrderHistory his=new CreditOrderHistory();
				String order_id=coi.get("id").toString();
				String json=Json.getJson().toJson(coi);
				his.set("order_id", order_id);
				his.set("json", json);
				his.set("change_reason", coi.getStr("remarks")).set("remarks", "0");
				his.set("create_by", coi.get("create_by").toString());
				his.set("create_date", coi.get("receiver_date").toString());
				his.set("update_by", user.get("username").toString()).set("update_date", new Date());
				his.set("del_flag", "0");
				his.save();
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @time   2018年8月23日 下午3:45:40
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param coi
	 * @throws Exception 
	 * @return_type   void
	 * 删除订单
	 */
	public void deleteOrder(CreditOrderInfo coi, BaseProjectController c) throws Exception {
		try {
			coi.update();
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:13
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param pageinator
	 * @param  @param model
	 * @param  @param orderby
	 * @param  @param user
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<CreditOrderInfo>
	 * 获取订单列表并分页
	 */
	public Page<CreditOrderInfo> getOrdersService(Paginator pageinator, CreditOrderInfo model, String orderby,
			 BaseProjectController c) {

		return CreditOrderInfo.dao.getOrders(pageinator, model, orderby, c);

	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:21
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param c
	 * @param  @return
	 * @return_type   CreditOrderInfo
	 * 根据id获取订单
	 */
	public CreditOrderInfo editOrder(int id, BaseProjectController c) {

		return CreditOrderInfo.dao.getOrder(id, c);
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:41
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditReportType>
	 */
	public List<ReportTypeModel> getReportType() {

		return ReportTypeModel.dao.getReportType();
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:50
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param dictType
	 * @param  @return
	 * @return_type   List<SysDictDetail>
	 */
	public List<SysDictDetail> getDictByType(String dictType) {
		return SysDictDetail.dao.getDictByType(dictType);
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:55
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param continent
	 * @param  @return
	 * @return_type   List<CountryModel>
	 */
	public List<CountryModel> getCountrys(String continent) {

		return CountryModel.dao.getCountrys(continent);
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:49:00
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryid
	 * @param  @return
	 * @return_type   CountryModel
	 */
	public CountryModel getCountryType(String countryid) {
		return CountryModel.dao.findType(countryid);
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:49:04
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditCustomInfo>
	 */
	public List<CreditCustomInfo> getCreater() {
		return CreditCustomInfo.dao.findcustoms();
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:13:16
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryType
	 * @param  @param speed
	 * @param  @param reporttype
	 * @param  @return
	 * @return_type   CreditReportUsetime
	 */
	public CreditReportUsetime getTime(String countryType, String speed, String reporttype,String orderType) {
		// TODO Auto-generated method stub
		return CreditReportUsetime.dao.getTime(countryType,speed,reporttype,orderType);
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:13:27
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryType
	 * @param  @param speed
	 * @param  @param reporttype
	 * @param  @return
	 * @return_type   CreditReportPrice
	 */
	public CreditReportPrice getPrice(String countryType, String speed, String reporttype,String orderType) {
		// TODO Auto-generated method stub
		return CreditReportPrice.dao.getPrice(countryType,speed,reporttype,orderType);
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:13:33
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryType
	 * @param  @param reporttype
	 * @param  @return
	 * @return_type   List<CreditReportLanguage>
	 */
	public List<CreditReportLanguage> getLanguage(String countryType, String reporttype) {
		// TODO Auto-generated method stub
		return CreditReportLanguage.dao.getLanguage(countryType,reporttype);
	}
	/**
	 * 
	 * @time   2018年9月5日 上午9:13:45
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditCompanyInfo>
	 */
	public List<CreditCompanyInfo> getCompany() {
		
		return CreditCompanyInfo.dao.getCompany();
	}
	public CreditCompanyInfo getCompany(int id) {
		return CreditCompanyInfo.dao.findById(id);
	}

	public List<CreditOrderInfo> getOrdersService(CreditOrderInfo model, String orderby,
			BaseProjectController c) {
		
		return CreditOrderInfo.dao.getOrders( model, orderby, c);
	}

	public Page<CreditOrderInfo> getOrdersService(Paginator pageinator, CreditOrderInfo model,
			String status,SysUser user,String sortname,String sortorder) {
		// TODO Auto-generated method stub
		return CreditOrderInfo.dao.getOrders(pageinator, model, status, user,sortname,sortorder);
	}

	public CreditCustomInfo getCreater(String id) {
		// TODO Auto-generated method stub
		
		return CreditCustomInfo.dao.findById(id);
	}

	public CreditOrderInfo find(String num) {
		
		return CreditOrderInfo.dao.findOrder(num);
	}

	public int getOrdersService( String statu,CreditOrderInfo model,  SysUser user,String status) {
		
		return CreditOrderInfo.dao.getOrders( statu, model, user,status);
	}

	public CreditOrderInfo getMaxOrderId() {
		return CreditOrderInfo.dao.getMaxOrderId();
	}
	/**
	 * 根据报告员获取订单数量
	* @author doushuihai  
	 * @return 
	* @date 2018年9月25日下午1:28:51  
	* @TODO
	 */
	public CreditOrderInfo getOrderNum(int reportid){
		return CreditOrderInfo.dao.getOrderNum(reportid);
	}
	/**
	 * 根据报告员获取按时递交数
	 * @return 
	 */
	public CreditOrderInfo getOnTimeSubmitOrderNum(int reportid){
		return CreditOrderInfo.dao.getOnTimeSubmitOrderNum(reportid);
	}
	/**
	 * 获取报告员质量占比
	 * @return 
	 * 
	 */
	public CreditOrderInfo getScore(int reportid){
		return CreditOrderInfo.dao.getScore(reportid);
	}
	/**
	 * 获取报告员报告数量占比一
	 * @return 
	 * 
	 */
	public CreditOrderInfo getReportNumPart(int reportid){
		return CreditOrderInfo.dao.getReportNumPart(reportid);
	}
	/*
	 * 获取报告员报告数量占比二
	 */
	public CreditOrderInfo getOrderPeportAbroad(int reportid){
		return CreditOrderInfo.dao.getOrderPeportAbroad(reportid);
		
	}
	/**
	 * 获取报告员当日在做单量
	* @author doushuihai  
	* @date 2018年10月9日上午9:57:52  
	* @TODO
	 */
	public CreditOrderInfo getInDoingOrderNum(int reportid){
		return CreditOrderInfo.dao.getInDoingOrderNum(reportid);
	}
	public String getReportIdtoOrder(){
		System.out.println("==================================");
		List<SysUser> reporterlist = SysUser.dao.getReporter();
		List<Reporter> reporterList=new ArrayList<Reporter>();
		for(SysUser report:reporterlist){
			int reportid=report.get("userid");
			double finalScore = getFinalScore(reportid);//报告员评分
			long inDoingOrderNum = OrderManagerService.service.getInDoingOrderNum(reportid).get("inDoingOrderNum");
			System.out.println(inDoingOrderNum);
			Reporter reporter=new Reporter(reportid+"", finalScore, inDoingOrderNum);
			reporterList.add(reporter);
		}
		Collections.sort(reporterList);//根据分配逻辑进行集合排序
		System.out.println(reporterList);
		String reportId=reporterList.get(0).getReportId();
		return reportId;
		
	}
	/**
	 * 获取报告员评分
	* @author doushuihai  
	* @date 2018年10月9日上午9:30:41  
	* @TODO
	 */
	private double getFinalScore(int reportid) {
		Long orderNum = OrderManagerService.service.getOrderNum(reportid).get("orderNum");//订单数量
		Long orderOnTimeNum =OrderManagerService.service.getOnTimeSubmitOrderNum(reportid).get("orderOnTimeNum");//根据报告员获取按时递交数
		double submitNum=(orderOnTimeNum/orderNum)*0.1;//递交率占比
		BigDecimal score =OrderManagerService.service.getScore(reportid).get("score"); //获取报告员质量占比
		if(score==null){
			score =new BigDecimal(0);
		}
		double scoreTo=score.doubleValue();
		BigDecimal reportnum1 =OrderManagerService.service.getReportNumPart(reportid).get("reportnum");//报告数量占比一
		long reportnum2 = OrderManagerService.service.getOrderPeportAbroad(reportid).get("orderPeportAbroad");
		BigDecimal reportnum2To = new BigDecimal(reportnum2);
		double reportnum=reportnum1.add(reportnum2To).doubleValue();
		double finalScore=orderNum*0.5+submitNum+scoreTo+reportnum;
		return finalScore;
	}
}
