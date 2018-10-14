package com.hailian.modules.credit.agentmanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 代理关联表
* @author doushuihai  
* @date 2018年10月13日下午10:52:34  
* @TODO
 */
@ModelBind(table = "credit_agent_category", key = "id")
public class AgentCategoryModel extends BaseProjectModel<AgentCategoryModel> {
	
	private static final long serialVersionUID = 1L;
	public static final AgentCategoryModel dao = new AgentCategoryModel();
	/**
	 * 根据代理id查询类别
	* @author doushuihai  
	* @date 2018年10月13日下午11:30:50  
	* @TODO
	 */
	public List<AgentCategoryModel> findAll(String agent_id) {
		StringBuffer sb=new StringBuffer("select t.*,s1.detail_name as categoryName from credit_agent_category t ");
		sb.append(" LEFT JOIN sys_dict_detail s1 ON t.agent_category = s1.detail_id ");//国家
		sb.append(" where 1=1 ");//国家
		List<Object> params=new ArrayList<Object>();
			sb.append(" and t.agent_id=?");
			params.add(agent_id);
		
		return AgentCategoryModel.dao.find(sb.toString(), params.toArray());
	}
}