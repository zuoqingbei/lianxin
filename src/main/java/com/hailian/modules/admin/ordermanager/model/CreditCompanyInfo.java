package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table="credit_company_info")
public class CreditCompanyInfo extends BaseProjectModel<CreditCompanyInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CreditCompanyInfo dao=new CreditCompanyInfo();
	/**
	 * 
	 * @time   2018年9月5日 上午9:15:11
	 * @author yangdong
	 * @todo   TODO
	 * @param  @return
	 * @return_type   List<CreditCompanyInfo>
	 */
	public List<CreditCompanyInfo> getCompany() {
		
		return dao.find("select * from credit_company_info order by id");
	}
}
