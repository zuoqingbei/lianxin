package com.hailian.modules.admin.ordermanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;

@ModelBind(table = "credit_company_subsidiaries")
public class CreditCompanySubsidiaries extends BaseProjectModel<CreditCompanySubsidiaries> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanySubsidiaries dao = new CreditCompanySubsidiaries();//名字都叫dao，统一命名

    public void deleteBycomIdAndLanguage(String companyid,String sys_language){
        String sql="update credit_company_subsidiaries set del_flag=1 where company_id=? and sys_language=?";
        List<Object> params=new ArrayList<Object>();
        params.add(companyid);
        params.add(sys_language);
        Db.update(sql, params.toArray());
    }
    public List<CreditCompanySubsidiaries> getBycomIdAndLanguage(String companyid,String sys_language){
        String sql="select * from credit_company_subsidiaries where del_flag=0 and company_id=? and sys_language=?";
        List<Object> params=new ArrayList<Object>();
        params.add(companyid);
        params.add(sys_language);
        return dao.find(sql, params.toArray());
    }

}
