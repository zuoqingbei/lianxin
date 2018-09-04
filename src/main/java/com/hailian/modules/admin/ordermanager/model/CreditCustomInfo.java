package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditCustomInfo.java
 * @time   2018年8月31日 下午3:18:28
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_custom_info")//此标签用于模型与数据库表的连接
public class CreditCustomInfo  extends BaseProjectModel<CreditCustomInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CreditCustomInfo dao = new CreditCustomInfo();//名字都叫dao，统一命名
	/**
	 * 
	 * @time   2018年8月31日 下午3:17:45
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditCustomInfo>
	 * 获取客户列表
	 */
	public List<CreditCustomInfo> findcustoms() {
		List<CreditCustomInfo> list=dao.find("select t.* from credit_custom_info t where t.del_flag=? order by id ", "0");
		return list;
	}

}
