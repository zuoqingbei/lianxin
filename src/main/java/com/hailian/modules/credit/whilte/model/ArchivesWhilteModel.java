package com.hailian.modules.credit.whilte.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_archives_whilte")
public class ArchivesWhilteModel extends BaseProjectModel<ArchivesWhilteModel> {
	private static final long serialVersionUID = 1L;
	public static final ArchivesWhilteModel dao = new ArchivesWhilteModel();
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<ArchivesWhilteModel> getPage(Paginator paginator, String custom_id, String report_id, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		String sql=" from credit_archives_whilte where 1=1 ";
		if(StringUtils.isNotBlank(custom_id)){
			sql+=" and custom_id like ?";
			params.add('%'+custom_id+'%');
		}
		if(StringUtils.isNotBlank(report_id)){
			sql+=" and report_id like ?";
			params.add('%'+report_id+'%');
		}
		Page<ArchivesWhilteModel> page = ArchivesWhilteModel.dao
				.paginate(paginator, "select * ", sql.toString(),params.toArray());
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月3日下午2:59:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid) {
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_archives_whilte set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
}
