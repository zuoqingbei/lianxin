package com.hailian.modules.credit.custom.service;

import java.util.Map;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.jfinal.plugin.activerecord.Page;

public class CustomTranFlowService {
	public static final CustomTranFlowService service=new CustomTranFlowService();
	/**
	 * 
	* @Description: 列表展示
	* @date 2018年10月19日 下午3:22:31
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public Page<CustomTranFlowModel> getPage(Paginator paginator,  String orderBy,String names,String email,String type,BaseProjectController c){
		Page<CustomTranFlowModel> page = CustomTranFlowModel.dao.getPage(orderBy,names,email,type,c);
		return page;
	}
}
