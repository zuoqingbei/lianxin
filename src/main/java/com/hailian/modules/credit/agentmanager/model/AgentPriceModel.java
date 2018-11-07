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
 * 代理价格
* @author doushuihai  
* @date 2018年11月5日下午1:49:37  
* @TODO
 */
@ModelBind(table = "credit_agent_price")
public class AgentPriceModel extends BaseProjectModel<AgentPriceModel> {
	private static final long serialVersionUID = 1L;
	public static final AgentPriceModel dao = new AgentPriceModel();
	
	/**
	 * 
	 * @time   2018年9月12日 下午4:02:13
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   Page<AgentModel>
	 */
	public Page<AgentPriceModel> getAgent(Paginator paginator, String orderBy,BaseProjectController c,String id) {
		StringBuffer selectsql = new StringBuffer(
				"select  t.credit_province as provinceName,t2.city as cityName,t3.detail_name as agent_categoryName,sp.detail_name as Speed,at.detail_name as agentType,c.detail_name as Currency,"
				+"a.* ");
		StringBuffer fromsql = new StringBuffer(" from  credit_agent_price a ");
		fromsql.append("   LEFT JOIN sys_dict_detail at on at.detail_id=a.agent_type ");
		fromsql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=a.currency");
		fromsql.append("   LEFT JOIN sys_dict_detail sp on sp.detail_id=a.speed ");
		fromsql.append("   LEFT JOIN credit_province t on t.pid=a.province ");
		fromsql.append("   LEFT JOIN credit_city t2 on t2.cid=a.city and t2.pid=a.province ");
		fromsql.append("   LEFT JOIN sys_dict_detail t3 on t3.detail_id=a.agent_category ");
		fromsql.append("  where a.del_flag=0 ");
		List<Object> params = new ArrayList<Object>();
		
		if(!c.isAdmin(c.getSessionUser())){
			fromsql.append(" and a.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if(StringUtil.isNotEmpty(id)){
			fromsql.append(" and a.agent_id="+id);
			
		}
		if (StrUtils.isEmpty(orderBy)) {
			fromsql.append(" order by a.create_date desc");
		} else {
			fromsql.append(" order by ").append(orderBy);
		}
		Page<AgentPriceModel> agent = AgentPriceModel.dao.paginate(new Paginator(), selectsql.toString(), fromsql.toString(),
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
	public AgentPriceModel getOne(int id, BaseProjectController c) {
		StringBuffer selectsql = new StringBuffer(
				"select  t.credit_province as provinceName,t2.city as cityName,t3.detail_name as agent_categoryName,sp.detail_name as Speed,at.detail_name as agentType,c.detail_name as Currency,a.* ");
		StringBuffer fromsql = new StringBuffer(" from credit_agent_price a ");
		fromsql.append("   LEFT JOIN sys_dict_detail at on at.detail_id=a.agent_type ");
		fromsql.append("   LEFT JOIN sys_dict_detail c on c.detail_id=a.currency");
		fromsql.append("   LEFT JOIN sys_dict_detail sp on sp.detail_id=a.speed");
		fromsql.append("   LEFT JOIN credit_province t on t.pid=a.province ");
		fromsql.append("   LEFT JOIN credit_city t2 on t2.cid=a.city and t2.pid=a.province ");
		fromsql.append("   LEFT JOIN sys_dict_detail t3 on t3.detail_id=a.agent_category ");
		fromsql.append("   where a.del_flag=0 and at.del_flag=0 and c.del_flag=0 and a.id=?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return AgentPriceModel.dao.findFirst(selectsql.toString() + fromsql.toString(), params.toArray());
	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:40:56
	 * @author dyc
	 * @todo   根据id删除单条代理信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		AgentPriceModel agent = AgentPriceModel.dao.findById(id);
		if (agent != null) {
			return agent.set("del_flag", 1).update();
		}
		return false;
	}

	public List<AgentPriceModel> findAll() {
		
		return AgentPriceModel.dao.find("select * from credit_agent t where t.del_flag=0");
	}
	public List<AgentPriceModel> findAgentCateSelect(String agent_id,boolean isCate) {
		if(StringUtils.isBlank(agent_id)){
			return null;
		}
		StringBuffer sb=new StringBuffer("select t.*,s1.detail_name as categoryName from credit_agent_price t ");
		sb.append(" LEFT JOIN sys_dict_detail s1 ON t.agent_category = s1.detail_id ");
		sb.append(" where 1=1 and t.del_flag=0 ");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(agent_id)){
			sb.append(" and t.agent_id=?");
			params.add(agent_id);
		}
		if(isCate){
			sb.append(" group by t.agent_category ");
		}
		sb.append(" order by t.id ");
		return AgentPriceModel.dao.find(sb.toString(), params.toArray());
	}
	/**
	 * 根据条件获取代理价格
	 * isSpecial为true获取特例市代理价格，false获取普通代理价格
	* @author doushuihai  
	* @date 2018年11月7日下午2:05:22  
	* @TODO
	 */
	public AgentPriceModel getAgentPrice(int pid,int cid,String agent_id,String agent_category,boolean isSpecial) {
		StringBuffer sb=new StringBuffer("select t.* from credit_agent_price t ");
		sb.append(" where 1=1 and t.del_flag=0 ");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotBlank(agent_id)){
			sb.append(" and t.agent_id=?");
			params.add(agent_id);
		}
		if(StringUtils.isNotBlank(pid+"")){
			sb.append(" and t.province=?");
			params.add(pid);
		}
		if(!isSpecial){
				sb.append(" and t.city is null or t.city=-1");
		}
		if(isSpecial){
			if(StringUtils.isNotBlank(cid+"")){
				sb.append(" and t.city=?");
				params.add(cid);
			}
		}
		if(StringUtils.isNotBlank(agent_category+"")){
			sb.append(" and t.agent_category=?");
			params.add(agent_category);
		}
		sb.append(" order by t.id ");
		return AgentPriceModel.dao.findFirst(sb.toString(), params.toArray());
	}
}