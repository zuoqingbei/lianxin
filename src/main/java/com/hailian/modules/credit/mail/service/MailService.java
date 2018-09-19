package com.hailian.modules.credit.mail.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.mail.model.MailModel;
import com.jfinal.plugin.activerecord.Page;

public class MailService {
	public static MailService service=new MailService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<MailModel> getPage(Paginator paginator,  String orderBy,String keyword,BaseProjectController c){
		Page<MailModel> page = MailModel.dao.getPage(paginator,orderBy,keyword,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午5:20:30  
	* @TODO
	 */
	public int delete(Integer id, Integer userid){
		int delete = MailModel.dao.delete(id,userid);
		return delete;
	}
	public void toEnabled(Integer id, Integer userid){
		MailModel.dao.toEnabled(id, userid);
	}
	public List<MailModel> getCustom(Integer id){
		List<MailModel> mail = MailModel.dao.getMail(id);
		return mail;
	}
	public MailModel getCustomById(Integer id){
		MailModel mail = MailModel.dao.getMailById(id);
		return mail;
	}
	
}
