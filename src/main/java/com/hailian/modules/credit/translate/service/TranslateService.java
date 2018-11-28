package com.hailian.modules.credit.translate.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单service层
* @author doushuihai  
* @date 2018年9月3日下午2:31:24  
* @TODO
 */
public class TranslateService {
	public static TranslateService service=new TranslateService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param orderBy 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<TranslateModel> getPage(Paginator paginator, String orderBy,String error_phrase,BaseProjectController c){
		Page<TranslateModel> page = TranslateModel.dao.getPage(paginator,orderBy,error_phrase,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午6:12:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid){
		TranslateModel.dao.delete(id,userid);
	}
	public TranslateModel getTranslate(Integer paraToInt) {
		// TODO Auto-generated method stub
		TranslateModel translate = TranslateModel.dao.getTranslate(paraToInt);
		return translate;
	}
	public TranslateModel getTranslateByError(String error) {
		TranslateModel translate = TranslateModel.dao.getTranslateByError(error);
		return translate;
	}
}
