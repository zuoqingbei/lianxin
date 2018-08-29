package com.hailian.system.dict;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.ordermanager.model.CreditReportType;

@ModelBind(table = "sys_dict_detail", key = "detail_id")
public class SysDictDetail extends BaseProjectModel<SysDictDetail> {

	private static final long serialVersionUID = 1L;
	public static final SysDictDetail dao = new SysDictDetail();
	
	public List<SysDictDetail> getReportLanguage() {
		List<SysDictDetail> list=dao.find("select t.* from sys_dict_detail t where t.dict_type=?", "reportlanguage");
		return list;
	}

	public List<SysDictDetail> getSpeed(String dictType) {
		List<SysDictDetail> speed=dao.find("select t.* from sys_dict_detail t where t.dict_type=?", dictType);
		return speed;
	}

	public List<SysDictDetail> getReportType() {
		List<SysDictDetail> list=dao.find("select t.* from sys_dict_detail t where t.dict_type=?", "ordertype");
		return list;
	}

}
