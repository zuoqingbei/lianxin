package com.hailian.modules.credit.custom.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.jfinal.plugin.activerecord.Page;

public class CustomService {
	public static CustomService service=new CustomService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<CustomInfoModel> getPage(Paginator paginator,  String orderBy,String keyword,BaseProjectController c){
		Page<CustomInfoModel> page = CustomInfoModel.dao.getPage(paginator,orderBy,keyword,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午5:20:30  
	* @TODO
	 */
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
