package com.hailian.modules.admin.ordermanager.model;

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
	private String userName;

	public String getUserName() {
		return get("userName");
	}

	public void setUserName(String userName) {
		set("userName", userName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public static final CreditOrderInfo dao = new CreditOrderInfo();//名字都叫dao，统一命名
	
	public Page<CreditOrderInfo> getOrders(Paginator pageinator,CreditOrderInfo model,String orderby,SysUser user,BaseProjectController c) {
		// TODO Auto-generated method stub
		SQLUtils sql=null;
		String userid=user.get("userid").toString();
				if((int)user.get("usertype")==1) {
					 sql = new SQLUtils(" from credit_order_info t left join sys_user u on u.userid=t.create_by " //
							+ " where 1 = 1 and t.del_flag='0' ");
				}else {
					 sql = new SQLUtils(" from credit_order_info t left join sys_user u on u.userid=t.reportor " //
							+ " where 1 = 1 and t.del_flag='0' and t.create_by='"+userid+"'");
				}
				
				if (model.getAttrValues().length != 0) {
					sql.whereEquals("custom_id", model.getStr("custom_id"));
					/*sql.whereLike("realname", model.getStr("realname"));
					sql.whereEquals("usertype", model.getInt("usertype"));*/
					sql.whereEquals("id", model.getStr("id"));
				}
				// 排序
		//		String orderBy = getBaseForm().getOrderBy();
				if (StrUtils.isEmpty(orderby)) {
					sql.append(" order by t.id desc");
				} else {
					sql.append(" order by ").append(orderby);
				}
				Page<CreditOrderInfo> page=CreditOrderInfo.dao.paginate(pageinator, "select t.*,u.username as userName", sql.toString()
						.toString());
				
				return page;
	}

	public CreditOrderInfo getOrder(String id, BaseProjectController c) {
		// TODO Auto-generated method stub
		SQLUtils sql=new SQLUtils("select t.*,u.username as userName from credit_order_info t left join sys_user u on u.userid=t.create_by "
				+ "where 1 = 1 and t.del_flag='0' and t.id='"+id+"'");
		CreditOrderInfo coi=dao.findFirst(sql.toString());
		return coi;
	}
	

}
