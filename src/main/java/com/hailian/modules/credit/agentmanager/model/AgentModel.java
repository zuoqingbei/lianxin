package com.hailian.modules.credit.agentmanager.model;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月12日 上午11:45:31
* @todo
*/
@ModelBind(table = "credit_agent", key = "agent_id")
public class AgentModel extends BaseProjectModel<AgentModel> {
	private static final long serialVersionUID = 1L;
	public static final AgentModel dao = new AgentModel();
	private static List<String> columnnNames = new ArrayList<>();
	static {
		columnnNames.add("at.detail_name");
		columnnNames.add("agent_name");
		columnnNames.add("postal_code");
		columnnNames.add("price");
		columnnNames.add("c.detail_name");
	}

	/**
	 * 
	 * @time   2018年9月12日 下午4:02:13
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   Page<AgentModel>
	 */
	public Page<AgentModel> getAgent(Paginator paginator, int pageNumber, int pageSize, String orderBy, String keyWord,
			String searchType, BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"select  t.credit_province as provinceName,t2.city as cityName,t3.detail_name as agent_categoryName,sp.detail_name as Speed,at.detail_name as agentType,c.detail_name as Currency,us.detail_name as Usabled,a.* ");
		StringBuffer fromsql = new StringBuffer(" from  credit_agent a ");
		fromsql.append("   LEFT JOIN sys_dict_detail at on at.detail_id=a.agent_type ");
		fromsql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=a.currency");
		fromsql.append("   LEFT JOIN sys_dict_detail us on us.detail_id=a.usabled");
		fromsql.append("   LEFT JOIN sys_dict_detail sp on sp.detail_id=a.speed ");
		fromsql.append("   LEFT JOIN credit_province t on t.pid=a.province ");
		fromsql.append("   LEFT JOIN credit_city t2 on t2.cid=a.city and t2.pid=a.province ");
		fromsql.append("   LEFT JOIN sys_dict_detail t3 on t3.detail_id=a.agent_category ");
		fromsql.append("  where a.del_flag=0 and at.del_flag=0 and c.del_flag=0 and us.del_flag=0");
		List<Object> params = new ArrayList<Object>();
		if (StringUtil.isNotEmpty(keyWord)) {
			fromsql.append(" and ");
			for (int i = 0; i < columnnNames.size(); i++) {
				if ("create_date".equals(columnnNames.get(i))) {
					continue;
				}
				if (i != 0) {
					fromsql.append(" || ");
				}
				//搜索类型
				if ("0".equals(searchType)) {
					fromsql.append(columnnNames.get(i) + " like concat('%',?,'%')");
				} else {
					fromsql.append(columnnNames.get(i) + " = ? ");
				}
				params.add(keyWord);//传入的参数
			}
		}
		if (StrUtils.isEmpty(orderBy)) {
			fromsql.append(" order by a.create_date desc");
		} else {
			fromsql.append(" order by ").append(orderBy);
		}
		Page<AgentModel> agent = AgentModel.dao.paginate(new Paginator(), selectsql.toString(), fromsql.toString(),
				params.toArray());
		return agent;
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:37:02
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   CompanyModel
	 */
	public AgentModel getOne(int id, BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"select  t.credit_province as provinceName,t2.city as cityName,t3.detail_name as agent_categoryName,sp.detail_name as Speed,at.detail_name as agentType,c.detail_name as Currency,us.detail_name as Usabled,a.* ");
		StringBuffer fromsql = new StringBuffer(" from credit_agent a ");
		fromsql.append("   LEFT JOIN sys_dict_detail at on at.detail_id=a.agent_type ");
		fromsql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=a.currency");
		fromsql.append("   LEFT JOIN sys_dict_detail us on us.detail_id=a.usabled");
		fromsql.append("   LEFT JOIN sys_dict_detail sp on sp.detail_id=a.speed");
		fromsql.append("   LEFT JOIN credit_province t on t.pid=a.province ");
		fromsql.append("   LEFT JOIN credit_city t2 on t2.cid=a.city and t2.pid=a.province ");
		fromsql.append("   LEFT JOIN sys_dict_detail t3 on t3.detail_id=a.agent_category ");
		fromsql.append("  where a.del_flag=0 and at.del_flag=0 and c.del_flag=0 and us.del_flag=0 and a.agent_id=?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return AgentModel.dao.findFirst(selectsql.toString() + fromsql.toString(), params.toArray());
	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:40:56
	 * @author dyc
	 * @todo   根据id删除单条代理信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		AgentModel agent = AgentModel.dao.findById(id);
		if (agent != null) {
			return agent.set("del_flag", 1).update();
		}
		return false;
	}

	public List<AgentModel> findAll() {
		
		return AgentModel.dao.find("select * from credit_agent t where t.del_flag=0");
	}
}