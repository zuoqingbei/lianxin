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
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<ArchivesWhilteModel> getPage(Paginator paginator, String custom_id, String report_id, BaseProjectController c){
		Page<ArchivesWhilteModel> page = ArchivesWhilteModel.dao.getPage(paginator,custom_id,report_id,c);
		return page;
	}
	public void delete(Integer id, Integer userid){
		ArchivesWhilteModel.dao.delete(id,userid);
	}
}
