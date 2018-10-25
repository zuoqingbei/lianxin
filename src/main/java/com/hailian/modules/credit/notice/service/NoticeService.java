package com.hailian.modules.credit.notice.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.notice.model.NoticeModel;

public class NoticeService {
	public static NoticeService service=new NoticeService();
	public List<NoticeModel> getNotice(Integer id,BaseProjectController c){
		return NoticeModel.dao.getNotice(id, c);
	}
}
