package com.hailian.modules.admin.ordermanager.model;
import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.utils.DataAuthorUtils;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

@ModelBind(table = "credit_company_growth_rate")
public class CreditCompanyGdp extends BaseProjectModel<CreditCompanyGdp> {
	private static final long serialVersionUID = 1L;
	public static final CreditCompanyGdp dao = new CreditCompanyGdp();//名字都叫dao，统一命名
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("name");
		columnnNames.add("name_en");
		columnnNames.add("name_trad");
		columnnNames.add("tpl_path");
		columnnNames.add("remarks");
		columnnNames.add("order_no");
		columnnNames.add("create_date");
	}
	
	public Page<CreditCompanyGdp> pagerReportType(int pageNumber, int pagerSize, String keyWord,String orderBy,String searchType,BaseProjectController c) {
		String authorSql = DataAuthorUtils.getAuthorByUser(c);
		StringBuffer selectSql = new StringBuffer(" SELECT g.*,u.realname as uname ");
		StringBuffer fromSql = new StringBuffer("  from credit_company_growth_rate   g "
				+ " LEFT JOIN sys_user u on u.userid=g.create_by where  g.del_flag='0'");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		if (StringUtil.isNotEmpty(keyWord)) {
			fromSql.append(" and ");
			for (int i = 0; i < columnnNames.size(); i++) {
				if("create_date".equals(columnnNames.get(i))){
					continue;
				}
				if(i!=0){
					fromSql.append(" || ");
				}
				//搜索类型
				if("0".equals(searchType)){
					fromSql.append(columnnNames.get(i)+" like concat('%',?,'%')");
				}else{
					fromSql.append(columnnNames.get(i)+" = ? ");
				}
				params.add(keyWord);//传入的参数
			}
		}
		//权限区分
		if(!c.isAdmin(c.getSessionUser())){
			fromSql.append(" and create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		//排序
		if (StrUtils.isEmpty(orderBy)) {
			fromSql.append(" order by create_date desc");
		} else {
			fromSql.append(" order by ").append(orderBy);
		}
		
		Page<CreditCompanyGdp> gdp  = CreditCompanyGdp.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),
				fromSql.toString(), params.toArray());
		return gdp;
	}
	 
}
