package com.hailian.modules.credit.currencyrate.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.currencyrate.model.CurrencyRateModel;
import com.hailian.modules.credit.currencyrate.service.CurrencyRateService;
import com.jfinal.plugin.activerecord.Page;
@ControllerBind(controllerKey = "/credit/currencyrate")
public class CurrencyRateController extends BaseProjectController {
	private static final String path = "/pages/credit/currencyrate/currencyrate_";
	public void index() {
		list();
	}
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:32:30  
	* @TODO
	 */
	public void list() {
		String currency_a = getPara("currency_a");
		String currency_b = getPara("currency_b");
		String orderBy = getBaseForm().getOrderBy();
		Page<CurrencyRateModel> page2 = CurrencyRateService.service.getPage(getPaginator(), orderBy, currency_a,currency_b, this);
		setAttr("page",page2);
		render(path+"list.html");
	}
	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:28  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		CurrencyRateModel attr = getModel(CurrencyRateModel.class);
		setAttr("model", attr);
		render(path + "add.html");
	}
	/**
	 * 编辑跳转
	* @author doushuihai  
	* @date 2018年11月15日下午4:00:20  
	* @TODO
	 */
	public void edit() {
		Integer paraToInt = getParaToInt();
		CurrencyRateModel model = CurrencyRateModel.dao.findById(paraToInt);
		setAttr("model", model);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除汇率配置
	* @author doushuihai  
	* @date 2018年9月4日下午2:46:04  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		CurrencyRateService.service.delete(id,userid);
		list();
	}
	/**
	 * 保存
	* @author doushuihai  
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() {
		Integer pid = getParaToInt();
		
		// 日志添加
		CurrencyRateModel model = getModel(CurrencyRateModel.class);
		String currency_a=model.get("currency_a")+"";
		String currency_b=model.get("currency_b")+"";
		if(currency_a.equals(currency_b)){
			renderMessage("两个币种不能相同，请核对！");
			return;
		}
		CurrencyRateModel rateByA2B = CurrencyRateService.service.getRateByA2B(currency_a, currency_b);
		if(rateByA2B!=null){
			renderMessage("该汇率配置已经存在！如有需要可以对该汇率配置进行修改");
			return;
		}
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.set("update_by", userid);
			model.set("update_date", now);
			model.save();
		}
		renderMessage("保存成功");
	}

	


}
