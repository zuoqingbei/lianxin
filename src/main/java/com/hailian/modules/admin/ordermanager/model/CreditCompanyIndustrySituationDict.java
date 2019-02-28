package com.hailian.modules.admin.ordermanager.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.DateUtils;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 企业行业情况model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_company_industry_situation_dict")
public class CreditCompanyIndustrySituationDict extends BaseProjectModel<CreditCompanyIndustrySituationDict> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyIndustrySituationDict dao = new CreditCompanyIndustrySituationDict();
	
	/**
	 * 分页
	 * @author dou_shuiahi
	 * @date: 2019年2月25日下午3:53:15
	 * @Description:
	 */
	public Page<CreditCompanyIndustrySituationDict> getPage(Paginator paginator,String orderBy, CreditCompanyIndustrySituationDict attr, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_company_industry_situation_dict t left join credit_company_industry_situation_title_dict t2 on t2.id=t.title ");
		sql.append(" where 1=1 and t.del_flag=0 ");

		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if(StringUtils.isNotBlank(attr.getStr("title"))) {
			sql.append(" and t2.title like concat('%',?,'%')");
			params.add(attr.getStr("title"));//传入的参数
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.create_date desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<CreditCompanyIndustrySituationDict> page = CreditCompanyIndustrySituationDict.dao
				.paginate(paginator, "select t.*,t2.title as titlename", sql.toString(),params.toArray());
		System.out.println(sql);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月3日下午2:59:58  
	* @TODO
	 */
	public void delete(Integer id, Integer userid) {
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_company_industry_situation_dict set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
	public CreditCompanyIndustrySituationDict getIndustrySit(Integer id){
		StringBuffer sql=new StringBuffer("select * from credit_company_industry_situation_dict where del_flag=0");
		List<Object> params=new ArrayList<Object>();
		if(id !=null){
			sql.append(" and id=?");
			params.add(id);
		}
		CreditCompanyIndustrySituationDict find = CreditCompanyIndustrySituationDict.dao.findFirst(sql.toString(),params.toArray());
		return find;
	}

}
