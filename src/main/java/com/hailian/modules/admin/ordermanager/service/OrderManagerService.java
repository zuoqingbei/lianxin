package com.hailian.modules.admin.ordermanager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
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
	 * @time   2018年8月23日 上午10:31:17
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param c
	 * @param  @return
	 * @return_type   CreditOrderInfo
	 * 根据id获取订单
	 */
	
	public CreditOrderInfo getOrder(String id,BaseProjectController c) {
//		String authorSql=DataAuthorUtils.getAuthorByUser(c);//验证权限
		CreditOrderInfo coi= CreditOrderInfo.dao.findFirst("select * from credit_order_info c  where c.del_flag='0' and c.id=?",id);
		return coi;
	}
	/**
	 * 
	 * @time   2018年8月23日 上午10:32:20
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param pageNumber
	 * @param  @param pageSize
	 * @param  @param customid
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<CreditOrderInfo>
	 * 根据客户id获取订单并分页
	 */
	public  Page<CreditOrderInfo> getOrdersService(int pageNumber,int pageSize,String customid,BaseProjectController c){
//		String authorSql=DataAuthorUtils.getAuthorByUser(c);//验证权限
		StringBuffer selectSql=new StringBuffer(" select * ");
		StringBuffer fromSql=new StringBuffer(" from credit_order_info c where 1=1 and c.del_flag='0' ");
		//参数集合
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(customid)){
			fromSql.append(" and c.custom_id =? ");
			params.add(customid);
		}
		return CreditOrderInfo.dao.paginate(new Paginator(pageNumber, pageSize),  selectSql.toString()
				,fromSql.toString(),params.toArray());
	}
	
	/**
	 * 
	 * @time   2018年8月23日 下午3:02:57
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param coi
	 * @param  @return
	 * @return_type   Boolean
	 * 添加订单
	 */
	public Boolean addOrder(CreditOrderInfo coi,BaseProjectController c) {
		/*if(coi==null) {
			return false;
		}*/
		String id= UuidUtils.getUUID();
		coi.set("id",id);
		coi.set("del_flag", "0");
		Boolean flag=coi.save();
		return flag;
	}
	/**
	 * 
	 * @time   2018年8月23日 下午3:45:09
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param coi
	 * @throws Exception 
	 * @return_type   void
	 * 修改订单
	 */
	@Before(Tx.class)
	public void modifyOrder(CreditOrderInfo coi,String changeReason,SysUser user,BaseProjectController c) throws Exception {
		
		try {
			coi.update();
			coi=coi.findById(coi.get("id").toString());
			CreditOrderHistory.dao.set("id", UuidUtils.getUUID())
			.set("order_id", coi.get("id").toString())
			.set("json",JSONArray.toJSONString(coi))
			.set("change_reason", changeReason)
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
	 * @return_type   void
	 * 删除订单
	 */
	public void deleteOrder(CreditOrderInfo coi,BaseProjectController c) {
		coi.update();
		
	}
	/*public void deleteListOrder(List<CreditOrderInfo> list) {
		Db.batchUpdate(list, 100);
	}*/
	
	public Page<CreditOrderInfo> getOrdersService(Paginator pageinator,CreditOrderInfo model,String orderby,SysUser user, BaseProjectController c) {
		
		
		Page<CreditOrderInfo> page = CreditOrderInfo.dao.getOrders(pageinator,model,orderby,user,c);

		return page;
		
	}
	public CreditOrderInfo editOrder(String id,BaseProjectController c) {
		CreditOrderInfo coi=CreditOrderInfo.dao.getOrder(id,c);
		return coi;
	}
	
	public Boolean saveOrder(CreditOrderInfo model, BaseProjectController c) {
		// TODO Auto-generated method stub
		model.set("del_flag", "0");
		model.set("num", "2");
		Boolean flag=model.update();
		return flag;
	}
	public CreditOrderInfo orderView(String id, BaseProjectController c) {
		// TODO Auto-generated method stub
		CreditOrderInfo coi=CreditOrderInfo.dao.findById(id);
		return coi;
	}
	public List<CreditReportType> getReportType() {
		List<CreditReportType> list=CreditReportType.dao.getReportType();
		
		return list;
	}
	public List<SysDictDetail> getReportLanguage() {
		// TODO Auto-generated method stub
		List<SysDictDetail> list=SysDictDetail.dao.getReportLanguage();
		return list;
	}
	public List<CountryModel> getCountrys(String continent) {
		List<CountryModel> country=CountryModel.dao.getCountrys(continent);
		return country;
	}
	public List<SysDictDetail> getspeed(String dictType) {
		List<SysDictDetail> speed=SysDictDetail.dao.getSpeed(dictType);
		return speed;
	}
	public CountryModel getCountryType(String countryid) {
		CountryModel cm=CountryModel.dao.findType(countryid);
		return cm;
	}
	public List<SysDictDetail> getOrderType() {
		List<SysDictDetail> list=SysDictDetail.dao.getReportType();
		return list;
	}
	
}
