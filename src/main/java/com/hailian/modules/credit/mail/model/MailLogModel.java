package com.hailian.modules.credit.mail.model;

import java.util.ArrayList;
import java.util.Date;
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
 * 邮件日志model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_mail_log",key="mail_log_id")
public class MailLogModel extends BaseProjectModel<MailLogModel> {
	private static final long serialVersionUID = 1L;
	public static final MailLogModel dao = new MailLogModel();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param send_result 
	 * @param mail_log_id 
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<MailLogModel> getPage(Paginator paginator,String orderBy, String module_id, String mail_log_id, String send_result, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_mail_log t ");
		sql.append(" left join sys_user t2 on t2.userid = t.user_code ");
		sql.append(" left join sys_dict_detail t3 on t3.detail_id = t.send_result ");
		sql.append(" left join credit_mail t4 on t4.mail_id = t.mail_id ");
		sql.append(" where 1=1 and t.del_flag=0 ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if (StringUtil.isNotEmpty(module_id)) {
			sql.append(" and t4.module_id like concat('%',?,'%')");
			params.add(module_id);//传入的参数
		}
		if (StringUtil.isNotEmpty(mail_log_id)) {
			sql.append(" and t.mail_log_id = ? ");
			params.add(mail_log_id);//传入的参数
		}
		if (StringUtil.isNotEmpty(send_result)) {
			sql.append(" and t.send_result = ? ");
			params.add(send_result);//传入的参数
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
		Page<MailLogModel> page = MailLogModel.dao
				.paginate(paginator, "select t.*,t2.realname,t3.detail_name,t4.module_id", sql.toString(),params.toArray());
		System.out.println(sql);
		return page;
	}
	public boolean save(Integer userid,String mail_address,String copy_mail_address,String mail_id,String send_result){
		MailLogModel model=new MailLogModel();
		model.put("user_code", userid.toString());
		model.put("mail_address", mail_address);
		model.put("copy_mail_address", copy_mail_address);
		model.put("mail_id", mail_id);
		model.put("send_result", send_result);
		model.put("create_by", userid.toString());
		model.put("create_date", new Date());
		model.put("update_by", userid.toString());
		model.put("create_date", new Date());
		return model.save();
	}

}

