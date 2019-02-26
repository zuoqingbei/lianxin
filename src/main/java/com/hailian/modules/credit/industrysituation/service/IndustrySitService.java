package com.hailian.modules.credit.industrysituation.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.industrysituation.model.IndustrySitModel;
import com.jfinal.plugin.activerecord.Page;

public class IndustrySitService {
	public static IndustrySitService service=new IndustrySitService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<IndustrySitModel> getPage(Paginator paginator,  String orderBy,IndustrySitModel attr,BaseProjectController c){
		Page<IndustrySitModel> page = IndustrySitModel.dao.getPage(paginator,orderBy,attr,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午5:20:30  
	* @TODO
	 */
	public void delete(Integer id, Integer userid){
		IndustrySitModel.dao.delete(id,userid);
	}
	/*
	 * 查询
	 */
	public IndustrySitModel getIndustrySit(Integer id){
		IndustrySitModel industrysit = IndustrySitModel.dao.getIndustrySit(id);
		return industrysit;
	}

	
}
