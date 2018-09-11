package com.hailian.modules.credit.whilte.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单service层
* @author doushuihai  
* @date 2018年9月3日下午2:31:24  
* @TODO
 */
public class ArchivesWhilteService {
	public static ArchivesWhilteService service=new ArchivesWhilteService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param orderBy 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<ArchivesWhilteModel> getPage(Paginator paginator, String orderBy, String custom_id, String report_id, BaseProjectController c){
		Page<ArchivesWhilteModel> page = ArchivesWhilteModel.dao.getPage(paginator,orderBy,custom_id,report_id,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午6:12:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid){
		ArchivesWhilteModel.dao.delete(id,userid);
	}
	public ArchivesWhilteModel getWhite(Integer paraToInt) {
		// TODO Auto-generated method stub
		ArchivesWhilteModel whilte = ArchivesWhilteModel.dao.getWhilte(paraToInt);
		return whilte;
	}
}
