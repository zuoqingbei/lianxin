package com.hailian.modules.credit.companychangeitem.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.companychangeitem.model.ChangeitemModel;
import com.jfinal.plugin.activerecord.Page;
/**
 * @Description: 公司历史非必须变更项
* @author: dsh 
* @date:  2018年11月27日下午7:06:21
 */
public class ChangeitemService {
	public static ChangeitemService service=new ChangeitemService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param orderBy 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<ChangeitemModel> getPage(Paginator paginator, String orderBy,String error_phrase,BaseProjectController c){
		Page<ChangeitemModel> page = ChangeitemModel.dao.getPage(paginator,orderBy,error_phrase,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午6:12:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid){
		ChangeitemModel.dao.delete(id,userid);
	}
	public ChangeitemModel getChangeitem(Integer paraToInt) {
		// TODO Auto-generated method stub
		ChangeitemModel translate = ChangeitemModel.dao.getChangeitem(paraToInt);
		return translate;
	}
	public ChangeitemModel getChangeitemByNotNessent(String nonessentialitems) {
		ChangeitemModel changeitem = ChangeitemModel.dao.getChangeitemByNotNessent(nonessentialitems);
		return changeitem;
	}
}
