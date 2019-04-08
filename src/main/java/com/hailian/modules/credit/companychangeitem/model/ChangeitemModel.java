package com.hailian.modules.credit.companychangeitem.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
 * @Description: 公司历史非必须变更项维护
* @author: dsh 
* @date:  2018年11月27日下午6:47:51
 */
@ModelBind(table = "credit_companyhistory_changeitem")
public class ChangeitemModel extends BaseProjectModel<ChangeitemModel> {
	private static final long serialVersionUID = 1L;
	public static final ChangeitemModel dao = new ChangeitemModel();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param orderBy 
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<ChangeitemModel> getPage(Paginator paginator, String orderBy,String nonessentialitems,BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sqlbuffer=new StringBuffer(" from credit_companyhistory_changeitem t1 ");
		sqlbuffer.append("left join sys_user t4 on t4.userid = t1.create_by where t1.del_flag=0");
		if(!c.isAdmin(c.getSessionUser())){
			sqlbuffer.append(" and t1.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if(StringUtils.isNotBlank(nonessentialitems)){
			sqlbuffer.append(" and t1.nonessentialitems like ? ");
			params.add('%'+nonessentialitems+'%');
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sqlbuffer.append(" order by t1.update_date desc");
		} else {
			sqlbuffer.append(" order by ").append(orderBy);
		}
		Page<ChangeitemModel> page = ChangeitemModel.dao
				.paginate(paginator, "select t1.*,t4.realname", sqlbuffer.toString(),params.toArray());
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
		String sql="update credit_companyhistory_changeitem set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
	/**
	 * 根据id查询数据
	* @author doushuihai  
	* @date 2018年9月7日下午6:17:24  
	* @TODO
	 */
	public ChangeitemModel getChangeitem(Integer paraToInt) {
		// TODO Auto-generated method stub
		String sql="select t.*,t4.realname as createName,t5.realname as updateName from credit_companyhistory_changeitem t ";
		sql+=" left join sys_user t4 on t4.userid=t.create_by ";
		sql+=" left join sys_user t5 on t5.userid=t.update_by ";
		sql+=" where t.del_flag=0 and t.id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(paraToInt);
		ChangeitemModel findFirst = ChangeitemModel.dao.findFirst(sql,params.toArray());
		return findFirst;
	}
	public ChangeitemModel getChangeitemByNotNessent(String nonessentialitems) {
		// TODO Auto-generated method stub
		String sql="select t.*,t4.realname as createName,t5.realname as updateName from credit_companyhistory_changeitem t ";
		sql+=" left join sys_user t4 on t4.userid=t.create_by ";
		sql+=" left join sys_user t5 on t5.userid=t.update_by ";
		sql+=" where t.del_flag=0 and t.nonessentialitems=?";
		List<Object> params=new ArrayList<Object>();
		params.add(nonessentialitems);
		ChangeitemModel findFirst = ChangeitemModel.dao.findFirst(sql,params.toArray());
		return findFirst;
	}
}
