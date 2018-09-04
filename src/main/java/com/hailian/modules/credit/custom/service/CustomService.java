package com.hailian.modules.credit.custom.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.jfinal.plugin.activerecord.Page;

public class CustomService {
	public static CustomService service=new CustomService();
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<CustomInfoModel> getPage(Paginator paginator, String custom_id, String report_id, BaseProjectController c){
		Page<CustomInfoModel> page = CustomInfoModel.dao.getPage(paginator,custom_id,report_id,c);
		return page;
	}
	public void delete(Integer id, Integer userid){
		CustomInfoModel.dao.delete(id,userid);
	}
}
