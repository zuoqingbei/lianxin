package com.hailian.modules.credit.common.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.common.service.CountryService;
/**
 * 国家地区
* @author doushuihai  
* @date 2018年8月22日下午1:17:47  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/country")
public class CountryController extends BaseProjectController {
	/**
	 * 国家地区下拉框  测试
	* @author doushuihai  
	* @date 2018年8月22日下午2:27:45  
	* @TODO
	 */
	public void getCountrySelect(){
		String countryName= getPara("countryName");//获取从前台查询国家地区的关键词查询条件参数
		List<CountryModel> selectCountry = CountryService.service.selectCountry(countryName,this);
		setAttr("countrylist",selectCountry);
		System.out.println(selectCountry.size()+"=================selectCountry==================="+selectCountry);
	}

}
