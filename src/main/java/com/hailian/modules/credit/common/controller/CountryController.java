package com.hailian.modules.credit.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.common.service.CountryService;
/**
 * 国家地区
* @author doushuihai  
* @date 2018年8月22日下午1:17:47  
* @TODO
 */
@Api( tag = "国家地区", description = "国家地区下拉框" )
@ControllerBind(controllerKey = "/credit/country" )
public class CountryController extends BaseProjectController {
	/**
	 * 国家地区下拉框  测试
	* @author doushuihai  
	 * @return 
	 * @throws UnsupportedEncodingException 
	* @date 2018年8月22日下午2:27:45  
	* @TODO
	 */
	@ApiOperation(url = "/credit/country/getCountrySelect",httpMethod="get", description = "获取国家地区下拉框")
	@Params(value = { 
			@Param(name = "attr.continent", description = "大洲", required = false, dataType = "String"),
			})
	public void getCountrySelect() throws UnsupportedEncodingException{
		String continent=getPara("attr.continent");
		System.out.println("=========="+continent);
		Map<Object, Object> selectCountry = CountryService.service.CountrySelect(continent,this);
		renderJson(selectCountry);
	}

}
