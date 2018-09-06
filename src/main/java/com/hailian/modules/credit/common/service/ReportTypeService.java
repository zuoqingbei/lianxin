package com.hailian.modules.credit.common.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo 报告类型业务层处理
 * @author lzg
 * @time 2018/09/03 9:50
 */


public class ReportTypeService {
	public static ReportTypeService service = new ReportTypeService();//名字都叫service，统一命名
	/**
	 * @todo   根据ID查询订单信息
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   ReportTypeModel
	 */
	public ReportTypeModel getOrder(String reportTypeId, BaseProjectController c) {
		return ReportTypeModel.dao.findById(reportTypeId, c);
	}

	/**
	 * @todo   分页查询订单
	 * @time   2018年9月3日 上午9:10:00
	 * @author lzg
	 * @return_type   Page<ReportTypeModel>
	 */
	public Page<ReportTypeModel> pagerOrder(int pageNumber, int pagerSize, String keyWord ,String orderBy,String searchType, BaseProjectController c) {
		return ReportTypeModel.dao.pagerReportType(pageNumber, pagerSize, keyWord , orderBy,searchType, c);
	}
}
