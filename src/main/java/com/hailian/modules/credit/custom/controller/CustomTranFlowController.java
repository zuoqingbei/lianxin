package com.hailian.modules.credit.custom.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.impl.config.NameSet;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.hailian.modules.credit.custom.service.CustomTranFlowService;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
* @Title: CustomTranFlowController  
* @Description:  客户交易流水
* @author lxy
* @date 2018年10月19日  下午3:19:19
 */
@ControllerBind(controllerKey = "/credit/customflow")
public class CustomTranFlowController extends BaseProjectController {
	private static final String path = "/pages/credit/custom/customflow_";
	public void index() {
		list();
	}
	/**
	 * 
	* @Description: 客户交易流水列表
	* @date 2018年10月19日 下午3:19:53
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void list() {
		CustomTranFlowModel attr = getModelByAttr(CustomTranFlowModel.class);
		String names = getPara("names");
		String email = getPara("email");
         String type =   getPara("type");
		
		String keyword = getPara("keyword");
		String orderBy = getBaseForm().getOrderBy();
		Page<CustomTranFlowModel> page = CustomTranFlowService.service.getPage(getPaginator(),orderBy,names,email,type,this);
		setAttr("page",page);
		render(path+"list.html");
	}
}
