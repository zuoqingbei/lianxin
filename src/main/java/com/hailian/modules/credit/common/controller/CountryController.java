package com.hailian.modules.credit.common.controller;

import java.util.List;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
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
@Api( tag = "国家地区", description = "国家地区下拉框" )
@ControllerBind(controllerKey = "/credit/country")
public class CountryController extends BaseProjectController {
	/**
	 * 国家地区下拉框  测试
	* @author doushuihai  
	 * @return 
	* @date 2018年8月22日下午2:27:45  
	* @TODO
	 */
	@ApiOperation(url = "/credit/country/getCountrySelect",httpMethod="get", description = "获取国家地区下拉框")
	public void getCountrySelect(){
		String content=getPara("attr.continent","");
		List<CountryModel> selectCountry = CountryService.service.CountrySelect(content,this);
		renderJson(selectCountry);
	}

}
