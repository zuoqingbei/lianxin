package com.hailian.modules.credit.agentmanager.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentCategoryModel;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.agentmanager.service.AgentService;
import com.hailian.modules.credit.city.model.CityModel;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.service.CountryService;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.company.service.CompanyService;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.hailian.modules.credit.province.model.ProvinceModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月12日 上午11:46:20
* @todo
*/
@Api(tag = "代理信息", description = "操作代理信息")
@ControllerBind(controllerKey = "/credit/agentmanager")
public class AgentController extends BaseProjectController {
	private static final String path = "/pages/credit/agentmanager/agent_";

	public void index() {
		list();
	}

	/**
	 * 
	 * @time   2018年9月12日 下午3:38:00
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   void
	 */
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		String orderBy = getBaseForm().getOrderBy();
		AgentModel attr = getModelByAttr(AgentModel.class);
		String searchType = getPara("searchType");
		if (!"1".equals(searchType)) {
			searchType = "0";
		}
		Page<AgentModel> pager = AgentService.service.getAgent(pageNumber, pageSize, orderBy, getPara("keyWord"),
				searchType, this);
		String country="";
		for(AgentModel agent:pager.getList()) {
			Object cid=agent.get("country");
			if(cid==null) {
				continue;
			}
			List<CountryModel> list=CountryModel.dao.findByIds(cid.toString());
			for(CountryModel cm:list) {
				country+=cm.get("name").toString()+",";
			}
			country=country.substring(0, country.length()-1);
			agent.set("country", country);
		}
		setAttr("page", pager);
		setAttr("attr", attr);
		render(path + "list.html");

	}

	/**
	 * 
	 * @time   2018年9月12日 下午5:10:42
	 * @author dyc
	 * @todo   转到代理信息增加页面
	 * @return_type   void
	 */
	public void add() {
		AgentModel model = getModel(AgentModel.class);
		List<String> catelist=new ArrayList<String>();
		model.put("agentCategoryList", catelist);
		setAttr("model", model);
		List<ProvinceModel> province = ProvinceModel.dao.getProvince("");//获取全部省份
		setAttr("province", province);
		String pid=model.get("province");
		List<CityModel> city = CityModel.dao.getCity("", pid, this);//获取省份下的城市
		setAttr("city", city);
		render(path + "add.html");
	}

	/**
	 * 
	 * @time   2018年9月12日 下午5:12:12
	 * @author dyc
	 * @todo   转到代理信息修改页面
	 * @return_type   void
	 */
	public void edit() {
		Integer para = getParaToInt();
		AgentModel model = AgentModel.dao.findById(para);
		
		String pid=model.get("province");
		List<ProvinceModel> province = ProvinceModel.dao.getProvince("");//获取全部省份
		List<AgentCategoryModel> agentCategoryList = AgentCategoryModel.dao.findAll(para+"");
		List<String> catelist=new ArrayList<String>();
		if(agentCategoryList!=null){
			for(AgentCategoryModel catemodel:agentCategoryList ){
				String agent_category=catemodel.get("agent_category")+"";
				catelist.add(agent_category);
				
			}
			
		}
		model.put("agentCategoryList", catelist);
		setAttr("model", model);
		setAttr("agentCategoryList", agentCategoryList);
		setAttr("province", province);
		
		render(path + "edit.html");
	}
	public void getCitySelect() {
		String province = getPara("attr.continent", "");
		List<CityModel> city = CityModel.dao.getCity("", province,this);
		setAttr("city", city);
		renderJson(city);
	}

	/**
	 * 
	 * @time   2018年9月12日 下午5:13:06
	 * @author dyc
	 * @todo   修改和新增功能
	 * @return_type   void
	 */
	public void save() {
		Integer id = getParaToInt("agent_id");
		String[] category = getParaValues("agent_category");
		String country=StringUtils.join(getParaValues("country"), ",");
		AgentModel model = getModel(AgentModel.class);
		if(StringUtil.isBlank(country)) {
		model.set("country", country);
		}
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		//		model.set("agent_id", id);
		model.set("update_by", userid);
		model.set("update_date", now);
		if (id != null && id > 0) { // 更新
			model.update();
			if(category!=null){//对代理类别表进行维护
				List<String> categoryList = Arrays.asList(category);
				updateAgentCate(id, categoryList);//维护代理类别从表
				updateAgentcateStr(id, model);//此步是在维护完代理和代理类别后再维护代理表的agent_category做列表展示
			}
			renderMessage("修改成功");
		} else { // 新增
			model.remove("agent_id");
			//			model.set("create_by", null);
			model.set("create_by", userid);
			model.set("create_date", now);
			boolean save = model.save();
			int agent_id=model.get("agent_id");
			if(category!=null){
				List<String> categoryList = Arrays.asList(category);
				updateAgentCate(agent_id, categoryList);
				updateAgentcateStr(agent_id, model);//此步是在维护完代理和代理类别后再维护代理表的agent_category做列表展示
			}
			if(save){
				renderMessage("保存成功");
			}else{
				renderMessage("保存失败");
			}
			
		}
	}

	private void updateAgentCate(int agent_id, List<String> categoryList) {
		Db.update("delete from credit_agent_category where agent_id="+agent_id);
		for(String categoryId:categoryList){
			AgentCategoryModel categorymodel=new AgentCategoryModel();
			categorymodel.set("agent_id",agent_id);
			categorymodel.set("agent_category", categoryId);
			categorymodel.save();
		}
	}

	private void updateAgentcateStr(Integer id, AgentModel model) {
		String categoryStr="";
		List<AgentCategoryModel> agentCategory = AgentCategoryModel.dao.findAll(id+"");
		int size = agentCategory.size();
		for(int i=0;i<size;i++){
			if(i==size-1){
				categoryStr+=agentCategory.get(i).get("categoryName");
			}else{
				categoryStr+=agentCategory.get(i).get("categoryName")+",";
			}
		}
		model.set("agent_category", categoryStr);
		model.update();
	}

	/**
	 * 
	 * @time   2018年9月13日 上午11:34:06
	 * @author dyc
	 * @todo   单条查看代理信息
	 * @return_type   void
	 */
	public void view() {
		Integer id = getParaToInt();
		AgentModel model = AgentService.service.getOne(id, null);
		setAttr("model", model);
		render(path + "view.html");
	}

	/**
	 * 
	 * @time   2018年9月13日 下午1:48:18
	 * @author dyc
	 * @todo    根据id删除单条代理信息
	 * @return_type   void
	 */
	public void delete() {
		Integer id = getParaToInt();
		if (AgentService.service.updateDelFlagById(id)) {
			list();
		} else {
			renderText("failure");
		}
	}
}
