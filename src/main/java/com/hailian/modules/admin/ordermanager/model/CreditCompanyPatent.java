package com.hailian.modules.admin.ordermanager.model;


import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;
/**
 * @Description: 企业专利
* @author: zuoqb
 */
@ModelBind(table = "credit_company_patent")
//此标签用于模型与数据库表的连接
public class CreditCompanyPatent extends BaseProjectModel<CreditCompanyPatent> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyPatent dao = new CreditCompanyPatent();//名字都叫dao，统一命名
	private String KindCodeDesc ;
	private String PublicationNumber  ;
	private String PublicationDate ;
	
	private String Title ;
	

	public String getKindCodeDesc() {
		return KindCodeDesc;
	}
	public void setKindCodeDesc(String kindCodeDesc) {
		set("kind_code_desc", kindCodeDesc);
	}
	public String getPublicationDate() {
		return PublicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		set("publication_date", publicationDate);
	}
	public String getPublicationNumber() {
		return PublicationNumber;
	}
	public void setPublicationNumber(String publicationNumber) {
		set("publication_number", publicationNumber);
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		set("title", title);
	}
	public void deleteBycomIdAndLanguage(String companyid,String sys_language){
		String sql="update credit_company_patent set del_flag=1 where company_id=? and sys_language=?";
		List<Object> params=new ArrayList<Object>();
		params.add(companyid);
		params.add(sys_language);
		Db.update(sql, params.toArray());
		
	}

    /**
     * 获取公司专利
     * @param companyId 公司id
     * @param sysLanguage 语言
     * @return
     */
    public List<CreditCompanyPatent> getBrandandpatent(String companyId,String sysLanguage){
        return dao.find("select * from credit_company_patent " +
                "where company_id = ? and sys_language=? and del_flag = 0",
                companyId,
                sysLanguage);
    }
}
