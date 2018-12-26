package com.hailian.modules.admin.ordermanager.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hailian.api.constant.RoleCons;

import org.apache.commons.lang.StringUtils;

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
import com.hailian.modules.credit.orderallocation.service.OrderAlloctionRuleService;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.hailian.util.DateAddUtil;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

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
	 * @return 
	 * @throws Exception 
	 * @return_type   void
	 * 修改订单/添加订单
	 */
	public String modifyOrder(int id, CreditOrderInfo coi, SysUser user, BaseProjectController c) throws Exception {

		try {
			if (id != 0) {
				coi.set("id", id);
				coi.update();
			} else {
				coi.save();
				
			}
			return coi.get("id")+"";

		} catch (Exception e) {
			e.printStackTrace();
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
	 * 查找以往是否有该订单公司的已完成订单
	* @author doushuihai  
	* @date 2018年11月18日下午5:55:29  
	* @TODO
	 */
	public CreditOrderInfo isTheSameOrder(String companyname,String report_type,String report_language,BaseProjectController c){
		return CreditOrderInfo.dao.isTheSameOrder(companyname,report_type,report_language, c);
	}
	/**
	 * 查找以往是否有该订单公司的真正要引用的报告订单
	* @author doushuihai  
	* @date 2018年11月18日下午5:55:29  
	* @TODO
	 */
	public CreditOrderInfo getTheSameOrder(String companyname,String report_type,String report_language,BaseProjectController c){
		return CreditOrderInfo.dao.getTheSameOrder(companyname,report_type,report_language,c);
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
	public CreditOrderInfo getOrder(String id, BaseProjectController c) {

		return CreditOrderInfo.dao.getOrderById(id,c);
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
	public CreditReportUsetime getTime(String countryType, String speed, String reporttype/*,String orderType*/) {
		// TODO Auto-generated method stub
		return CreditReportUsetime.dao.getTime(countryType,speed,reporttype/*,orderType*/);
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
	/*
	 * 
	 */
	public Page<CreditOrderInfo> getAchievementsOrders(Paginator pageinator, CreditOrderInfo model,String reportername,String time,
			SysUser user,boolean isadmin,String sortname,String sortorder) throws ParseException {
		// TODO Auto-generated method stub
		return CreditOrderInfo.dao.getAchievementsOrders(pageinator, model,reportername,time,user,isadmin,sortname,sortorder);
	}
	
	/**
	 * 
	* @Description: 订单结算
	* @date 2018年11月14日 下午2:36:27
	* @author: lxy
	* @version V1.0
	* @return
	 * @throws ParseException 
	 */
	public Page<CreditOrderInfo> getSettleOrders(Paginator pageinator,String customerId,String agentId,String time,
			String sortname,String sortorder) throws ParseException {
	                
		return CreditOrderInfo.dao.getSettleOrders(pageinator, customerId,agentId,time,sortname,sortorder);
	}
	
	public List<CreditOrderInfo> exportSettle(String customerId,String agentId,String time){
		return CreditOrderInfo.dao.exportSettle(customerId, agentId, time);
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
	public CreditOrderInfo getFinishedOrderNum(int reportid){
		return CreditOrderInfo.dao.getFinishedOrderNum(reportid);
	}

    /**
     * 给订单自动分配报告员
     * @return
     */
	public String getReportIdtoOrder(){
		System.out.println("==================================");
		List<SysUser> reporterlist = SysUser.dao.getReporter("2");
		List<Reporter> reporterList=new ArrayList<Reporter>();
		String reportId="";
		if(reporterlist != null){
			for(SysUser report:reporterlist){
				int reportid=report.get("userid");
				double finalScore = getFinalScore(reportid);//报告员评分
				long inDoingOrderNum = OrderManagerService.service.getInDoingOrderNum(reportid).get("inDoingOrderNum");//当日在做单量
				long finishedOrderNum=OrderManagerService.service.getFinishedOrderNum(reportid).get("finishedOrderNum");//当日完成量
				System.out.println(inDoingOrderNum);
				Reporter reporter=new Reporter(reportid+"", finalScore, inDoingOrderNum,finishedOrderNum);//自定义排序
				reporterList.add(reporter);
			}
			Collections.sort(reporterList);//根据分配逻辑进行集合排序
			reportId=reporterList.get(0).getReportId();

		}
		return reportId;
	}

    /**
     * 给订单自动分配业务人员
     *  2报告员 3客服 4质检员 5分析员 6翻译员
     * @return
     */
    public String getUserIdtoOrder(int roleId){
        List<SysUser> reporterlist = SysUser.dao.getReporter(roleId+"");
        List<Reporter> reporterList=new ArrayList<Reporter>();
        String reportId = null;
        if(reporterlist != null){
            if(reporterlist.size()==0){
                System.out.println("未查到相关业务人员，请先创建相关角色，角色ID="+roleId);
                return null;
            }else if(reporterlist.size()==1){
                //相应角色只有一个人的话，直接分配人员
                reportId = reporterlist.get(0).getInt("userid")+"";
            }else {
                //业务人员不止一人时，通过计算得到
                for (SysUser report : reporterlist) {
                    int reportid = report.get("userid");
                    //报告员评分
                    double finalScore = getFinalScoreBusi(reportid, roleId);
                    //当日在做单量
                    long inDoingOrderNum = 0;
                    //当日完成量
                    long finishedOrderNum = 0;
                    if (RoleCons.REPORTER == roleId) {
                        //报告员
                        inDoingOrderNum = CreditOrderInfo.dao.getInDoingOrderNum(reportid).get("inDoingOrderNum");
                        finishedOrderNum = CreditOrderInfo.dao.getFinishedOrderNum(reportid).get("finishedOrderNum");
                    } else if (RoleCons.TRANSER == roleId) {
                        //翻译员
                        inDoingOrderNum = CreditOrderInfo.dao.getInDoingOrderNumTrans(reportid).get("inDoingOrderNum");
                        finishedOrderNum = CreditOrderInfo.dao.getFinishedOrderNumTrans(reportid).get("finishedOrderNum");
                    } else if (RoleCons.IQC == roleId) {
                        //4质检员
                        inDoingOrderNum = CreditOrderInfo.dao.getInDoingOrderNumIqc(reportid).get("inDoingOrderNum");
                        finishedOrderNum = CreditOrderInfo.dao.getFinishedOrderNumIqc(reportid).get("finishedOrderNum");
                    } else if (RoleCons.ANALER == roleId) {
                        //5分析员
                        inDoingOrderNum = CreditOrderInfo.dao.getInDoingOrderNumAnal(reportid).get("inDoingOrderNum");
                        finishedOrderNum = CreditOrderInfo.dao.getFinishedOrderNumAnal(reportid).get("finishedOrderNum");
                    }
                    System.out.println(inDoingOrderNum);
                    //自定义排序
                    Reporter reporter = new Reporter(reportid + "", finalScore, inDoingOrderNum, finishedOrderNum);
                    reporterList.add(reporter);
                }
                //根据分配逻辑进行集合排序
                Collections.sort(reporterList);
                reportId = reporterList.get(0).getReportId();
            }
        }
        return reportId;
    }

	/**
	 * 获取报告员评分
	* @author doushuihai
	* @date 2018年10月9日上午9:30:41
	* @TODO
	 */
	private double getFinalScore(int reportid) {
		//获取分配规则规定的比率
		Float rate1 = OrderAlloctionRuleService.service.getOne(501, null).get("allocation_value");//做单量占比
		Float rate2 = OrderAlloctionRuleService.service.getOne(589, null).get("allocation_value");//报告数量占比占比
		Float rate3 = OrderAlloctionRuleService.service.getOne(502, null).get("allocation_value");//递交率占比
		Float rate4 = OrderAlloctionRuleService.service.getOne(503, null).get("allocation_value");//质量占比
		Float rate5 = OrderAlloctionRuleService.service.getOne(504, null).get("allocation_value");//商业信息/分析报告数量占比
		Float rate6 = OrderAlloctionRuleService.service.getOne(506, null).get("allocation_value");//注册信息数量占比
		Float rate7 = OrderAlloctionRuleService.service.getOne(505, null).get("allocation_value");//国外报告数量占比
		Long orderNum = OrderManagerService.service.getOrderNum(reportid).get("orderNum");//订单数量
		Long orderOnTimeNum =OrderManagerService.service.getOnTimeSubmitOrderNum(reportid).get("orderOnTimeNum");//根据报告员获取按时递交数
		double submitNum=0;
		if(orderNum==0){
			 submitNum=0;
		}else{
			 submitNum=(orderOnTimeNum/orderNum);//递交率占比
		}
//		BigDecimal score =OrderManagerService.service.getScore(reportid).get("score"); //获取报告员质量占比
		double score = 0;
		CreditOrderInfo coreInfo = OrderManagerService.service.getScore(reportid);
		try {
			if(coreInfo!=null){
				score=coreInfo.get("score");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			score = 0;
		}
		
		double scoreTo=score;
//		BigDecimal reportnum1 =OrderManagerService.service.getReportNumPart(reportid).get("reportnum");//报告数量占比一
		BigDecimal reportnum1 = null;
		BigDecimal reportnum = null;
		CreditOrderInfo reportNumPart = OrderManagerService.service.getReportNumPart(reportid);
		if(reportNumPart!=null){
			reportnum=reportNumPart.get("reportnum1");//商业信息/分析报告数量
			reportnum1=reportNumPart.get("reportnum2");//注册信息报告数量
		}
		if(reportnum1==null){
			reportnum1=new BigDecimal(0);
		}
		if(reportnum==null){
			reportnum=new BigDecimal(0);
		}
		long reportnumTo=reportnum.longValue();
		long reportnum1To=reportnum1.longValue();
//		long reportnum2 = OrderManagerService.service.getOrderPeportAbroad(reportid).get("orderPeportAbroad");//报告数量占比二
		long reportnum2=0;
		CreditOrderInfo orderPeportAbroad= OrderManagerService.service.getOrderPeportAbroad(reportid);
		if(orderPeportAbroad!=null){
			reportnum2=orderPeportAbroad.get("orderPeportAbroad");//国外报告数量
		}
		//根据动态规则获取报告数量占比
		BigDecimal reportnum2To = new BigDecimal(reportnum2);
//		double reportnumAll=reportnum1.add(reportnum2To).doubleValue();
		long reportnumAll=(long) (reportnumTo*rate5+reportnum1To*rate6+reportnum2*rate7);
		double finalScore=orderNum*rate1+submitNum*rate3+scoreTo*rate4+reportnumAll*rate2;
		return finalScore;
	}


    /**
     * 获取业务员（翻译员/分析员/质检员）评分
     * @author whc
     * @date
     * @TODO
     */
    private double getFinalScoreBusi(int reportid,int roleId) {
        //获取分配规则规定的比率
        Float rate1 = OrderAlloctionRuleService.service.getOne(501, null).get("allocation_value");//做单量占比
        Float rate2 = OrderAlloctionRuleService.service.getOne(589, null).get("allocation_value");//报告数量占比占比
        Float rate3 = OrderAlloctionRuleService.service.getOne(502, null).get("allocation_value");//递交率占比
        Float rate4 = OrderAlloctionRuleService.service.getOne(503, null).get("allocation_value");//质量占比
        Float rate5 = OrderAlloctionRuleService.service.getOne(504, null).get("allocation_value");//商业信息/分析报告数量占比
        Float rate6 = OrderAlloctionRuleService.service.getOne(506, null).get("allocation_value");//注册信息数量占比
        Float rate7 = OrderAlloctionRuleService.service.getOne(505, null).get("allocation_value");//国外报告数量占比
        Long orderNum = CreditOrderInfo.dao.getOrderNum(reportid,roleId).get("orderNum");//订单数量
        //Long orderOnTimeNum =OrderManagerService.service.getOnTimeSubmitOrderNum(reportid).get("orderOnTimeNum");//根据报告员获取按时递交数
        double submitNum=0;
        if(orderNum==0){
            submitNum=0;
        }
        //else{
        //    submitNum=(orderOnTimeNum/orderNum);//递交率占比
        //}
        double score = 0;
        CreditOrderInfo coreInfo = OrderManagerService.service.getScore(reportid);
        try {
            if(coreInfo!=null){
                score=coreInfo.get("score");
            }
        } catch (Exception e) {
            score = 0;
        }
        double scoreTo=score;
        BigDecimal reportnum1 = null;
        BigDecimal reportnum = null;
        CreditOrderInfo reportNumPart = OrderManagerService.service.getReportNumPart(reportid);
        if(reportNumPart!=null){
            reportnum=reportNumPart.get("reportnum1");//商业信息/分析报告数量
            reportnum1=reportNumPart.get("reportnum2");//注册信息报告数量
        }
        if(reportnum1==null){
            reportnum1=new BigDecimal(0);
        }
        if(reportnum==null){
            reportnum=new BigDecimal(0);
        }
        long reportnumTo=reportnum.longValue();
        long reportnum1To=reportnum1.longValue();
        long reportnum2=0;
        CreditOrderInfo orderPeportAbroad= OrderManagerService.service.getOrderPeportAbroad(reportid);
        if(orderPeportAbroad!=null){
            reportnum2=orderPeportAbroad.get("orderPeportAbroad");//国外报告数量
        }
        //根据动态规则获取报告数量占比
        long reportnumAll=(long) (reportnumTo*rate5+reportnum1To*rate6+reportnum2*rate7);
        double finalScore=orderNum*rate1+submitNum*rate3+scoreTo*rate4+reportnumAll*rate2;
        return finalScore;
    }


	public Record getReportTime(String countryType,String speed,String reporttype,String receivedate) throws ParseException{
		if("207".equals(countryType) || "208".equals(countryType) || "209".equals(countryType)) {
			countryType="148";
		}
		CreditReportUsetime usetime=OrderManagerService.service.getTime(countryType,speed,reporttype/*,orderType*/);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		if(StringUtils.isNotBlank(receivedate)) {
		ca.setTime(sdf.parse(receivedate));//设置接单时间
		}
		int time;
		int days;
		int hour;
		if(usetime==null) {
			 usetime=new CreditReportUsetime();
			 time=0;
		}else {
			time=usetime.get("use_time");
			usetime.set("use_time", (int)Math.ceil(time/24.0));
		}
		ca.setTime(new Date());
		hour = ca.get(Calendar.HOUR_OF_DAY);
		if(hour<12) {
			days=(int)Math.ceil(time/24.0);
		}else {
			days=(int)Math.ceil(time/24.0)+1;
		}
		Calendar c = 
				new DateAddUtil().addDateByWorkDay(ca,//当前时间
						//需要用多少天
						days);
		String enddate=sdf.format(c.getTime());
		if(time==0) {
			enddate="";
		}
		if("148".equals(countryType)&&!("15".equals(reporttype) || "22".equals(reporttype))) {
			ca.add(Calendar.DATE, days);
			enddate=sdf.format(ca.getTime());
		}
		Record record=new Record();
		record.set("usetime", usetime);
		record.set("enddate", enddate);
		return record;
	}
	/**
	 * @Description: 根据订单获取报告价格
	* @author: dsh 
	* @date:  2018年12月20日
	 */
	public CreditReportPrice getOrderprice(String countryType,String speed,String reporttype,String orderType,String customid,String countryid){
		 CreditReportPrice price = new CreditReportPrice();
	        if ("207".equals(countryType) || "208".equals(countryType) || "209".equals(countryType)) {
	            countryType = "148";
	        }
	        //根据客户id判断新老客户新老客户价格区分字段是versions=老系统
	        CreditCustomInfo cci = CreditCustomInfo.dao.findById(customid);
	        if ("261".equals(cci.getStr("is_old_customer"))) {
	            //3种不同类型老客户
	            if ("373".equals(customid)) {
	                //获取上月的第5天
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	                Calendar c = Calendar.getInstance();
	                c.add(Calendar.MONTH, -1);//上月
	                c.set(Calendar.DAY_OF_MONTH, 5);//设置为5号,当前日期既为上月第5天
	                String first = format.format(c.getTime());
	                Calendar ca = Calendar.getInstance();
	                ca.add(Calendar.MONTH, 0);//本月
	                ca.set(Calendar.DAY_OF_MONTH, 5);//本月5日
	                String last = format.format(ca.getTime());
	                //此客户依据每月订单量和国家决定订单价格
	                //求该客户的该月订单量
	                List<CreditOrderInfo> list = CreditOrderInfo.dao.findByCustom(customid, first, last);
	                Integer size = list.size();
	                price = CreditReportPrice.dao.getoldPrice(customid, countryid, null, null, size,null);
	            } else if ("399".equals(customid)) {
	                //此客户依据国家决定订单价格
	                price = CreditReportPrice.dao.getoldPrice(customid,countryid, null, null, null,null);
	            } else {
	                //此类客户依据国家,报告类型,和速度,订单类型决定订单价格
	                price = CreditReportPrice.dao.getoldPrice(customid,countryid, reporttype, speed, null,orderType);
	            }
	        } else {
	        	//获取新客户价格
	            price = OrderManagerService.service.getPrice(countryType, speed, reporttype, orderType);
	        }
	        return price;
	}

	public List<CreditOrderInfo> exportAchievements(String reportername,
			String time, String userid,
			BaseProjectController c) {
		return CreditOrderInfo.dao.exportAchievements(reportername,time,userid,c);
	}



}
