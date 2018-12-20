package com.hailian.modules.credit.common.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyGdp;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo 报告类型业务层处理
 * @author lzg
 * @time 2018/09/03 9:50
 */


public class CreditCompanyGdpService {
	public static CreditCompanyGdpService service = new CreditCompanyGdpService();//名字都叫service，统一命名
	

	/**
	 * 
	* @Description: 分页查询
	* @date 2018年12月20日 上午9:51:53
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public Page<CreditCompanyGdp> pagerOrder(int pageNumber, int pagerSize, String keyWord ,String orderBy,String searchType, BaseProjectController c) {
		return CreditCompanyGdp.dao.pagerReportType(pageNumber, pagerSize, keyWord , orderBy,searchType, c);
	}
}
