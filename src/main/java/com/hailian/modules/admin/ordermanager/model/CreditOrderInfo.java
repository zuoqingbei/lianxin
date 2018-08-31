package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.jfinal.component.db.SQLUtils;
import com.hailian.system.user.SysUser;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className CreditOrderInfo.java
 * @time   2018年8月23日 上午9:05:28
 * @author yangdong
 * @todo   
 * 订单模型,jfinal创建模型不需要写属性,只需要继承Model或者model的子类,就会获取与数据库表的连接
 * 继承model的子类会得到很多方法的扩展
 */
@ModelBind(table = "credit_order_info")//此标签用于模型与数据库表的连接
public class CreditOrderInfo extends BaseProjectModel<CreditOrderInfo>{
	//客户姓名
	private String customName;
	//国家
	private String countryName;
	//订单创建者的username
	private String createName;
	public String getCustomName() {
		return get("userName");
	}

	public void setCustomName(String customName) {
		set("customName", customName);
	}
	public String getCountryName() {
		return get("countryName");
	}

	public void setCountryName(String countryName) {
		set("countryName", countryName);
	}
	public String getcreateName() {
		return get("createName");
	}
	public void setCreateName(String createName) {
		set("createName", createName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public static final CreditOrderInfo dao = new CreditOrderInfo();//名字都叫dao，统一命名
	/**
	 * 
	 * @time   2018年8月31日 下午2:21:15
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param pageinator
	 * @param  @param model
	 * @param  @param orderby
	 * @param  @param user
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<CreditOrderInfo>
	 */
	public Page<CreditOrderInfo> getOrders(Paginator pageinator,CreditOrderInfo model,String orderby,SysUser user,BaseProjectController c) {
		// TODO Auto-generated method stub
		StringBuffer sql=null;
		String userid=user.get("userid").toString();
		String custom_id=model.getStr("custom_id");
		String id=model.getStr("id");
		List<Object> params = new ArrayList<Object>();
				if((int)user.get("usertype")==1) {
					 sql = new StringBuffer(" from credit_order_info t "
					 		+ "left join credit_custom_info u on u.id=t.custom_id "
					 		+ "left join credit_country c on c.id=t.country "
					 		+ "left join sys_user s on s.userid=t.create_by " 
							+ " where 1 = 1 and t.del_flag='0' ");
				}else {
					 sql = new StringBuffer(" from credit_order_info t "
					 		+ "left join credit_custom_info u on u.id=t.custom_id"
					 		+ " left join credit_country c on c.id=t.country"
					 		+ "left join sys_user s on s.userid=t.create_by "
							+ " where 1 = 1 and t.del_flag='0'");
					 sql.append(" and t.create_by=?");
					 params.add(userid);
				}

					
				if (StringUtils.isNotBlank(custom_id)) {
					sql.append(" and t.custom_id=?");
					params.add(custom_id);
				}
				if(StringUtils.isNotBlank(id)) {
					sql.append(" and t.id=?");
					params.add(id);
				}
				if (StrUtils.isEmpty(orderby)) {
					sql.append(" order by t.id desc");
				} else {
					sql.append(" order by ").append(orderby);
				}
				Page<CreditOrderInfo> page=CreditOrderInfo.dao.paginate(pageinator, "select t.*,u.name as customName,c.name as countryName,s.username as createName", sql.toString(),params.toArray());
				
				return page;
	}
	/**
	 * 
	 * @time   2018年8月31日 下午2:40:47
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param c
	 * @param  @return
	 * @return_type   CreditOrderInfo
	 */
	public CreditOrderInfo getOrder(int id, BaseProjectController c) {

		return dao.findFirst("select t.*,u.name as userName,c.name as countryName from credit_order_info t left join credit_custom_info u on u.id=t.custom_id left join credit_country c on c.id=t.country  "
				+ "where 1 = 1 and t.del_flag='0' and t.id=?", id);
	}
	

}
