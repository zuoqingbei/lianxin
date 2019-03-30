package com.hailian.modules.credit.industrysituation.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyIndustrySituationTitleDict;
import com.jfinal.plugin.activerecord.Page;

public class IndustrySitTitleService {
	public static IndustrySitTitleService service=new IndustrySitTitleService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<CreditCompanyIndustrySituationTitleDict> getPage(Paginator paginator,  String orderBy,CreditCompanyIndustrySituationTitleDict attr,BaseProjectController c){
		Page<CreditCompanyIndustrySituationTitleDict> page = CreditCompanyIndustrySituationTitleDict.dao.getPage(paginator,orderBy,attr,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午5:20:30  
	* @TODO
	 */
	public void delete(Integer id, Integer userid){
		CreditCompanyIndustrySituationTitleDict.dao.delete(id,userid);
	}
	/*
	 * 查询
	 */
	public CreditCompanyIndustrySituationTitleDict getIndustrySit(Integer id){
		CreditCompanyIndustrySituationTitleDict industrysit = CreditCompanyIndustrySituationTitleDict.dao.getIndustrySit(id);
		return industrysit;
	}
	/**
	 * 获取全部
	 */
	public List<CreditCompanyIndustrySituationTitleDict> getAllIndustrySit(){
		List<CreditCompanyIndustrySituationTitleDict> industrysit = CreditCompanyIndustrySituationTitleDict.dao.getAllIndustrySit();
		return industrysit;
	}

	
}
