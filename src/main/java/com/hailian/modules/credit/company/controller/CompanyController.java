package com.hailian.modules.credit.company.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.company.service.CompanyService;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月10日 上午11:17:51
* @todo
*/
@Api(tag = "企业信息", description = "操作企业信息")
@ControllerBind(controllerKey = "/credit/company")
public class CompanyController extends BaseProjectController {
	private static final String path = "/pages/credit/company/company_";


	public void index() {
		list();

	}

	/**
	 * 
	 * @time   2018年9月10日 下午2:07:57
	 * @author dyc
	 * @todo   向前台页面展示数据
	 * @return_type   void
	 */
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		String orderBy = getBaseForm().getOrderBy();
		CompanyModel attr = getModelByAttr(CompanyModel.class);
		String companyName = getPara("attr.name");
		String companyNameEn = getPara("attr.name_en");
		String registrationNum = getPara("attr.registration_num");
		String creditCode = getPara("attr.credit_code");
		Page<CompanyModel> pager = CompanyService.service.pageCompany(pageNumber, pageSize, orderBy,
			 companyName, companyNameEn, registrationNum, creditCode, this);
		setAttr("page", pager);
		setAttr("attr", attr);
		render(path + "list.html");

	}

	/**
	 * 
	 * @time   2018年9月10日 下午4:36:54
	 * @author dyc
	 * @todo   转到新增页面
	 * @return_type   void
	 */
	public void add() {
		CompanyModel model = getModelByAttr(CompanyModel.class);
		setAttr("model", model);
		render(path + "add.html");
	}

	/**
	 * 
	 * @time   2018年9月10日 下午5:05:08
	 * @author dyc
	 * @todo   转到修改页面
	 * @return_type   void
	 */
	public void edit() {
		Integer para = getParaToInt();
		CompanyModel model = CompanyModel.dao.findById(para);
		setAttr("model", model);
		render(path + "edit.html");
	}

	/**
	 * 
	 * @time   2018年9月10日 下午5:07:18
	 * @author dyc
	 * @todo   添加和修改方法
	 * @return_type   void
	 */
	public void save() {
		Integer id = getParaToInt("id");
		CompanyModel model = getModel(CompanyModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		if (id != null && id > 0) { // 更新
			model.update();
			renderMessage("修改成功");
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
			renderMessage("保存成功");
		}
	}

	/**
	 * 
	 * @time   2018年9月10日 下午5:43:00
	 * @author dyc
	 * @todo   根据id删除单条企业信息
	 * @return_type   void
	 */
	public void delete() {
		Integer id = getParaToInt();
		if (CompanyService.service.updateDelFlagById(id)) {
			list();
		} else {
			renderText("failure");
		}

	}

	/**
	 * 
	 * @time   2018年9月11日 上午10:27:10
	 * @author dyc
	 * @todo   单条查看企业信息
	 * @return_type   void
	 */
	public void view() {
		Integer id = getParaToInt();
		CompanyModel model = CompanyService.service.getOne(id, null);
		setAttr("model", model);
		render(path + "view.html");
	}
	
}
