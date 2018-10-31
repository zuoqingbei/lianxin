package com.hailian.modules.credit.notice.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.notice.model.NoticeModel;
import com.jfinal.plugin.activerecord.Page;

public class NoticeService {
	public static NoticeService service=new NoticeService();
	public List<NoticeModel> getNotice(Integer id,BaseProjectController c){
		return NoticeModel.dao.getNotice(id, c);
	}
	public Page<NoticeModel> getPage(Paginator paginator, BaseProjectController c,String status,Integer userid) {
		Page<NoticeModel> page = NoticeModel.dao.getPage(paginator,c,status,userid);
		return page;
	}
}
