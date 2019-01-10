package com.hailian.modules.credit.custom.model;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.orderallocation.model.OrderAllocationRuleModel;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
* @Title: CustomTranFlowModel  
* @Description: 客户交易流水 
* @author lxy
* @date 2018年10月19日  上午10:37:25
 */
@ModelBind(table = "credit_custom_tran_flow")
public class CustomTranFlowModel extends BaseProjectModel<CustomTranFlowModel> {
	private static final long serialVersionUID = 1L;
	public static final CustomTranFlowModel dao=new CustomTranFlowModel();
    /**
     * 
    * @Description: 列表
    * @date 2018年10月19日 下午3:24:01
    * @author: lxy
    * @version V1.0
    * @return
     */
	public Page<CustomTranFlowModel> getPage( String orderBy,String names,String email,String type,
			 BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"SELECT f.*,de.detail_name as currency,info.id as ids,info.`name` as cusName,info.email ");
		StringBuffer fromsql = new StringBuffer(" from credit_custom_tran_flow f  ");
				fromsql.append(" LEFT JOIN credit_custom_info info on f.custom_id=info.table_id"); 
				fromsql.append(" LEFT JOIN sys_dict_detail de on de.detail_id=f.currency");
				fromsql.append(" where info.del_flag=0 ");
	
		if (StringUtils.isNotBlank(names)) {
			fromsql.append(" and info.name like '%"+names+"%'");
		}
		if (StringUtils.isNotBlank(email)) {
			fromsql.append(" and info.email like '%"+email+"%'");
		}
		if (StringUtils.isNotBlank(type)) {
			if (!type.equals("全部")) {
				fromsql.append(" and f.transaction_type ='"+type+"'");	
			}
		}
		
		if (StringUtils.isBlank(orderBy)) {
			fromsql.append(" order by f.create_time desc");
		} else {
			fromsql.append(" order by ").append(orderBy);
		}
		Page<CustomTranFlowModel> flowModel = CustomTranFlowModel.dao.paginate(new Paginator(), selectsql.toString(), fromsql.toString());
		return flowModel;
	}
}
