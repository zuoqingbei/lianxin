package com.hailian.modules.credit.company.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.ext.directive.Str;

/**
* @author dyc:
* @time 2018年9月10日 上午11:15:53
* @todo  企业信息处理业务
*/
public class CompanyService {
	public static CompanyService service = new CompanyService();

	/**
	 * 
	 * @time   2018年9月10日 下午5:32:12
	 * @author dyc
	 * @todo   删除单条企业信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {

		return CompanyModel.dao.updateDelFlagById(id);
	}

	/**
	 * 
	 * @time   2018年9月11日 上午9:58:27
	 * @author dyc
	 * @todo   分页查询企业信息
	 * @return_type   Page<CompanyModel>
	 */
	public Page<CompanyModel> pageCompany(int pageNumber, int pageSize, String orderBy, String companyName,
			String companyNameEn, String registrationNum, String creditCode,  BaseProjectController c) {
		return CompanyModel.dao.pageCompany(pageNumber, pageSize, orderBy, companyName, companyNameEn, registrationNum,
				creditCode,  c);

	}

	/**
	 * 
	 * @time   2018年9月11日 上午10:45:02
	 * @author dyc
	 * @todo   单条查看企业信息
	 * @return_type   CompanyModel
	 */

	public CompanyModel getOne(int id, BaseProjectController c) {
		CompanyModel companyModel = CompanyModel.dao.getOne(id, c);
		return companyModel;

	}
}
