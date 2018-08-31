package com.hailian.modules.admin.ordermanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderHistory;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditReportType;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.hailian.util.extend.UuidUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
/**
 * 
 * @className OrderInfoService.java
 * @time   2018年8月23日 上午10:32:34
 * @author yangdong
 * @todo   TODO
 */
public class OrderManagerService {
	//static使该service保证了单例,public可以使Controller方便调用该service
	public static OrderManagerService service= new OrderManagerService();//名字都叫service，统一命名

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
	@Before(Tx.class)
	public void modifyOrder(int id,CreditOrderInfo coi,SysUser user,BaseProjectController c) throws Exception {
		
		try {
			if(id!=0) {
				coi.set("id", id);
				coi.update();
			}else {
				coi.save();
			}
			
			coi=coi.findById(id);
			CreditOrderHistory.dao
			.set("order_id", coi.getStr("id"))
			.set("json",JSONArray.toJSONString(coi))
			.set("change_reason", coi.getStr("remarks"))
			.set("remarks", "0")
			.set("create_by", coi.get("create_by").toString())
			.set("create_date", coi.get("receiver_date").toString())
			.set("update_by", user.get("username").toString())
			.set("update_date", new Date())
			.set("del_flag", "0").save();
			
		}catch(Exception e){
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
	public void deleteOrder(CreditOrderInfo coi,BaseProjectController c) throws Exception {
		try {
			coi.update();
		}catch(Exception e) {
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
	public Page<CreditOrderInfo> getOrdersService(Paginator pageinator,CreditOrderInfo model,String orderby,SysUser user, BaseProjectController c) {
		
		
		return CreditOrderInfo.dao.getOrders(pageinator,model,orderby,user,c);

	
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
	public CreditOrderInfo editOrder(int id,BaseProjectController c) {

		return CreditOrderInfo.dao.getOrder(id,c);
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:48:41
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditReportType>
	 */
	public List<CreditReportType> getReportType() {

		return CreditReportType.dao.getReportType();
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
	
}
