package com.hailian.modules.credit.notice.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
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
@ModelBind(table = "credit_notice_log")
public class NoticeLogModel extends BaseProjectModel<NoticeLogModel> {
	private static final long serialVersionUID = 1L;
	public static final NoticeLogModel dao = new NoticeLogModel();
	
	



}
