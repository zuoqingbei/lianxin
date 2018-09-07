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
import com.hailian.util.StrUtils;
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
	 * @param orderBy 
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<ArchivesWhilteModel> getPage(Paginator paginator, String orderBy,String custom_id, String report_id,BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sqlbuffer=new StringBuffer(" from credit_archives_whilte t1 ");
		sqlbuffer.append("left join credit_custom_info t2 on t1.custom_id=t2.id ");
		sqlbuffer.append("left join sys_user t4 on t4.userid = t1.create_by ");
		sqlbuffer.append("left join sys_dict_detail t5 on t5.detail_id = t1.used ");
		sqlbuffer.append("LEFT JOIN credit_report_type t3 on t1.report_id=t3.id where t1.del_flag=0 and t2.del_flag=0 and t5.del_flag=0 and t3.del_flag=0");
		if(StringUtils.isNotBlank(custom_id)){
			sqlbuffer.append(" and t2.name like ? or t2.name_en=? ");
			params.add('%'+custom_id+'%');
			params.add('%'+custom_id+'%');
		}
		if(StringUtils.isNotBlank(report_id)){
			sqlbuffer.append(" and t3.id = ?");
			params.add(report_id);
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sqlbuffer.append(" order by t1.update_date desc");
		} else {
			sqlbuffer.append(" order by ").append(orderBy);
		}
		Page<ArchivesWhilteModel> page = ArchivesWhilteModel.dao
				.paginate(paginator, "select t1.*,t2.name as cusName,t3.name as reportName,t4.realname,t5.detail_name", sqlbuffer.toString(),params.toArray());
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
