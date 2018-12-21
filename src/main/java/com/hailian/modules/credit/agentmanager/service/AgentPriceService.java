package com.hailian.modules.credit.agentmanager.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.city.model.CityModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.currencyrate.model.CurrencyRateModel;
import com.hailian.modules.credit.province.model.ProvinceModel;
import com.jfinal.plugin.activerecord.Page;

/**
 * 代理价格
* @author doushuihai  
* @date 2018年11月5日下午2:06:21  
* @TODO
 */
public class AgentPriceService {
	public static AgentPriceService service = new AgentPriceService();

	/**
	 * 
	 * @time   2018年9月12日 下午3:41:17
	 * @author dyc
	 * @todo   分页查询代理信息
	 * @return_type   Page<AgentModel>
	 */
	public Page<AgentPriceModel> getAgent(Paginator paginator,String orderBy,BaseProjectController c,String id) {
		return AgentPriceModel.dao.getAgent(paginator,orderBy, c,id);
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:35:40
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   CompanyModel
	 */
	public AgentPriceModel getOne(int id, BaseProjectController c) {
		AgentPriceModel agentModel = AgentPriceModel.dao.getOne(id, c);
		return agentModel;

	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:42:14
	 * @author dyc
	 * @todo   根据id删除单条代理信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {
		return AgentPriceModel.dao.updateDelFlagById(id);
	}
	public void updateDelFlagByAgentId(Integer agentid) {
		AgentPriceModel.dao.updateDelFlagByAgentId(agentid);
	}
	public AgentPriceModel getAgentPriceByOrder(String oid){
		CreditOrderInfo info= 	CreditOrderInfo.dao.findById(oid);
		CompanyModel companymodel = CompanyModel.dao.findById(info.get("company_id")+"");
        AgentPriceModel agentPrice=null;
        String address=null;
        if(companymodel != null){
            address=companymodel.getStr("company_address");
            if(StringUtils.isNotBlank(address)){
                String[] strs=address.split("-");
                String province=strs[0].toString();
                String city=strs[1].toString();
                String pid = "";
                String cid = "";
                ProvinceModel provinceByName = ProvinceModel.dao.getProvinceByName(province);
                if(provinceByName!=null){
                	pid = provinceByName.get("pid").toString();
                }
                CityModel cityByName = CityModel.dao.getCityByName(city);
                if(cityByName!=null){
                	pid = cityByName.get("cid").toString();
                }
                agentPrice = AgentPriceService.service.getAgentPrice(pid, cid, info.get("agent_id")+"",info.get("agent_category")+"" );
            }
        }
        return agentPrice;
		
	}
	/**
	 * 根据代理id，省，市，代理类别获取代理价格
	* @author doushuihai  
	* @date 2018年11月7日下午1:57:44  
	* @TODO
	 */
	public AgentPriceModel getAgentPrice(String pid,String cid,String agent_id,String agent_category) {
		AgentPriceModel agentpricemodel = AgentPriceModel.dao.getAgentPrice(pid, cid,agent_id,agent_category,true);//根据优先级 先根据省市代理类别代理id获取价格
		if(agentpricemodel== null){
			agentpricemodel = AgentPriceModel.dao.getAgentPrice(pid, cid,agent_id,agent_category,false);//根据优先级 先根据省代理类别代理id获取价格
		}else{
			agentpricemodel = AgentPriceModel.dao.getAgentPriceBycategory(agent_id,agent_category);//根据优先级 根据代理类别代理id获取价格
		}
		return agentpricemodel;
	}
	/*
	 * 国外人工代理分配根据代理id，国家，速度获取代理价格
	 */
	public AgentPriceModel getAgentAbroadPrice(String agent_id,String country,String speed) {
		AgentPriceModel agentpricemodel = AgentPriceModel.dao.getAgentAbroadPrice(agent_id,country,speed);
		return agentpricemodel;
	}
	/*
	 * 获取国外代理自动分配满足条件的代理
	 */
	public AgentPriceModel getAgentAbroad(String country,String speed) {
		AgentPriceModel agentAbroad = AgentPriceModel.dao.getAgentAbroad(country,speed);
		return agentAbroad;
	}
}