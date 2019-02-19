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
	public CreditCompanyInfo getCompanyById(String id) {
		
		return dao.findFirst("select * from credit_company_info where id= ? order by id ",id);
	}
	/**
	 * 
	 * @time   2018年10月9日 下午5:12:31
	 * @author yangdong
	 * @todo   TODO 根据准确公司英文名称查找公司
	 * @param  @param right_company_name_en
	 * @param  @return
	 * @return_type   CreditCompanyInfo
	 */
	public CreditCompanyInfo findByENname(String right_company_name_en) {
		// TODO Auto-generated method stub
		String englishName=right_company_name_en.trim();
		return dao.findFirst("select * from credit_company_info t where t.name_en=? and t.del_flag=0 and t.sys_language=612 order by t.create_date desc ",englishName);
	}
}
