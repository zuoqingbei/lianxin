package com.hailian.modules.credit.notice.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 白名单model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_notice_log")
public class NoticeLogModel extends BaseProjectModel<NoticeLogModel> {
	private static final long serialVersionUID = 1L;
	public static final NoticeLogModel dao = new NoticeLogModel();
	
	



}
