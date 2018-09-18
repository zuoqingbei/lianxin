package com.hailian.modules.credit.mail.model;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.system.dict.SysDictDetail;
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
@ModelBind(table = "credit_mail",key="mail_id")
public class MailModel extends BaseProjectModel<MailModel> {
	private static final long serialVersionUID = 1L;
	public static final MailModel dao = new MailModel();
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("t.name");
		columnnNames.add("t.name_en");
		columnnNames.add("t.contacts");
		columnnNames.add("t.contacts_short_name");
		columnnNames.add("t.reference_num");
		columnnNames.add("t.company_id");
		columnnNames.add("t.address");
		columnnNames.add("t.telphone");
		columnnNames.add("t.email");
		columnnNames.add("t.fax");
		columnnNames.add("t.country");
		columnnNames.add("t.remarks");
		columnnNames.add("t.create_date");
	}
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<MailModel> getPage(Paginator paginator,String orderBy, String keyword, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_mail t ");
		sql.append(" left join sys_user t2 on t2.userid = t.create_by ");
		sql.append(" left join sys_dict_detail t3 on t3.detail_id = t.send_type ");
		sql.append(" left join sys_dict_detail t4 on t4.detail_id = t.enabled ");
		sql.append(" where 1=1 and t.del_flag=0 ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if (StringUtil.isNotEmpty(keyword)) {
			sql.append("and t.module_name like concat('%',?,'%')");
			params.add(keyword);//传入的参数
		}
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.create_date desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<MailModel> page = MailModel.dao
				.paginate(paginator, "select t.*,t2.realname,t3.detail_name,t4.detail_name as enabledName", sql.toString(),params.toArray());
		System.out.println(sql);
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
		String sql="update credit_mail set del_flag=1,update_by=?,update_date=? where mail_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
	/**
	 * 设置邮件禁用
	* @author doushuihai  
	* @date 2018年9月17日下午4:27:01  
	* @TODO
	 */
	public void toEnabled(Integer id, Integer userid) {
		int enabled = 0;
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_mail set enabled=?,update_by=?,update_date=? where mail_id=?";
		List<Object> params=new ArrayList<Object>();
		List<SysDictDetail> dictDetailList=SysDictDetail.dao.getDictByType("usabled");
		for(SysDictDetail dictdetail:dictDetailList){
			if("禁用".equals(dictdetail.getStr("detail_name"))){
				enabled=dictdetail.getInt("detail_id");
				params.add(enabled);
				params.add(userid);
				params.add(now);
				params.add(id);
				Db.update(sql,params.toArray());
			}
		}
		
	}
	public List<MailModel> getMail(Integer id){
		StringBuffer sql=new StringBuffer("select * from credit_mail where del_flag=0");
		List<Object> params=new ArrayList<Object>();
		if(id !=null){
			sql.append(" and id=?");
			params.add(id);
		}
		List<MailModel> find = MailModel.dao.find(sql.toString(),params.toArray());
		return find;
	}
	public  MailModel getMailById(Integer paraToInt) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select t.*,t2.detail_name as usableName,t3.detail_name as isOldCusName,t4.name as companyName,t5.name as countryName from credit_mail t left join sys_dict_detail t2 on  t.usabled=t2.detail_id ");
		sql.append(" left join sys_dict_detail t3 on t.is_old_customer=t3.detail_id ");
		sql.append(" left join credit_company_info t4 on t.company_id=t4.id ");
		sql.append(" left join credit_country t5 on t.country=t5.id ");
		sql.append(" where 1=1 and t.del_flag=0 and t.id=?");
		params.add(paraToInt);
		MailModel findFirst = MailModel.dao.findFirst(sql.toString(),params.toArray());
		return findFirst;
	}
}
