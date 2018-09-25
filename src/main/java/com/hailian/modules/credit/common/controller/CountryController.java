package com.hailian.modules.credit.common.controller;

import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.service.CountryService;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.company.service.CompanyService;
import com.jfinal.plugin.activerecord.Page;

/**
 * 国家地区
* @author doushuihai  
* @date 2018年8月22日下午1:17:47  
* @TODO
 */
@Api(tag = "国家地区", description = "国家地区下拉框")
@ControllerBind(controllerKey = "/credit/country")
public class CountryController extends BaseProjectController {
	private static final String path = "/pages/credit/country/country_";

	public void index() {
		list();

	}

	/**
	 * 国家地区下拉框  测试
	* @author doushuihai  
	 * @return 
	* @date 2018年8月22日下午2:27:45  
	* @TODO
	 */
	@ApiOperation(url = "/credit/country/getCountrySelect", httpMethod = "get", description = "获取国家地区下拉框")
	public void getCountrySelect() {
		String content = getPara("attr.continent", "");
		List<CountryModel> selectCountry = CountryService.service.CountrySelect(content, this);
		renderJson(selectCountry);
	}

	/**
	 * 
	 * @time   2018年9月20日 上午9:54:21
	 * @author dyc
	 * @todo   向台页面展示数据
	 * @return_type   void
	 */
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 30);
		String orderby = getBaseForm().getOrderBy();
		CountryModel attr = getModelByAttr(CountryModel.class);
		String Continent = getPara("attr.continent");
		String countryName = getPara("attr.name");
		String countrynameEn = getPara("attr.name_en");
		String telPre = getPara("attr.tel_pre");
		String usabledName = getPara("attr.usabled");
		Page<CountryModel> pager = CountryService.service.pageCountry(pageNumber, pageSize, orderby, Continent,
				countryName, countrynameEn, telPre, usabledName, this);
		setAttr("page", pager);
		setAttr("attr", attr);
		render(path + "list.html");
	}
	/**
	 * 
	 * @time   2018年9月20日 下午2:15:06
	 * @author dyc
	 * @todo   转到新增页面
	 * @return_type   void
	 */
	public void add() {
		CountryModel model = getModelByAttr(CountryModel.class);
		setAttr("model", model);
		render(path + "add.html");
	}
	/**
	 * 
	 * @time   2018年9月20日 下午2:24:34
	 * @author dyc
	 * @todo   转到修改页面
	 * @return_type   void
	 */
	public void edit() {
		Integer para = getParaToInt();
		CountryModel model = CountryModel.dao.findById(para);
		setAttr("model", model);
		render(path + "edit.html");
	}
	/**
	 * 
	 * @time   2018年9月20日 下午3:37:50
	 * @author dyc
	 * @todo   新增和修改方法
	 * @return_type   void
	 */
	public void save() {
		Integer id = getParaToInt("id");
		CountryModel model = getModel(CountryModel.class);
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
	 * @time   2018年9月20日 下午3:50:02
	 * @author dyc
	 * @todo   根据id单条查看国家信息
	 * @return_type   void
	 */
	public void view() {
		Integer id = getParaToInt();
		CountryModel model = CountryService.service.getOne(id, null);
		setAttr("model", model);
		render(path + "view.html");
	}
	/**
	 * 
	 * @time   2018年9月21日 上午9:24:08
	 * @author dyc
	 * @todo   根据id删除单条国家信息
	 * @return_type   void
	 */
	public void delete(){
		Integer id = getParaToInt();
		if (CountryService.service.updateDelFlagById(id)) {
			list();
		} else {
			renderText("failure");
		}
		
	} 
}
