package com.hailian.modules.credit.agentmanager.controller;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.agentmanager.service.AgentService;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.company.service.CompanyService;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
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
		setAttr("model", model);
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
		setAttr("model", model);
		render(path + "edit.html");

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
		AgentModel model = getModel(AgentModel.class);
		System.out.println(model.get("price"));
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		//		model.set("agent_id", id);
		model.set("update_by", userid);
		model.set("update_date", now);
		if (id != null && id > 0) { // 更新
			model.update();
			renderMessage("修改成功");
		} else { // 新增
			model.remove("agent_id");
			//			model.set("create_by", null);
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
			renderMessage("保存成功");
		}

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
