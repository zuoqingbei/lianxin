package com.hailian.modules.credit.currencyrate.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.currencyrate.model.CurrencyRateModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.jfinal.plugin.activerecord.Page;

public class CurrencyRateService {
	public static CurrencyRateService service=new CurrencyRateService();
	/*
	 * 汇率列表展示
	 */
	public Page<CurrencyRateModel> getPage(Paginator paginator,  String orderBy,String currency_a,String currency_b,BaseProjectController c){
		Page<CurrencyRateModel> page = CurrencyRateModel.dao.getPage(paginator,orderBy,currency_a,currency_b,c);
		return page;
	}

	public void delete(Integer id, Integer userid){
		CustomInfoModel.dao.delete(id,userid);
	}
	public List<CustomInfoModel> getCustom(Integer id){
		List<CustomInfoModel> custom = CustomInfoModel.dao.getCustom(id);
		return custom;
	}
	public CustomInfoModel getCustomById(Integer id){
		CustomInfoModel custom = CustomInfoModel.dao.getCustomById(id);
		return custom;
	}
	
}
