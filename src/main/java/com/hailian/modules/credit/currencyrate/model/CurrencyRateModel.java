package com.hailian.modules.credit.currencyrate.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 公司model层
* @author doushuihai  
* @date 2018年9月10日上午9:58:38  
* @TODO
 */
@ModelBind(table = "credit_rate")
public class CurrencyRateModel extends BaseProjectModel<CurrencyRateModel> {
	/** 
	* @Fields serialVersionUID : TODO 
	*/
	private static final long serialVersionUID = 1L;
	public static final CurrencyRateModel dao = new CurrencyRateModel();
	public CurrencyRateModel getCateToRmb(String currency_a){
		String sql="select * from credit_rate where 1=1 and del_flag=0 ";
		sql+="and currency_a= ? and currency_b='274' ";
		List<Object> params=new ArrayList<Object>();
		params.add(currency_a);
		return CurrencyRateModel.dao.findFirst(sql, params.toArray());
	}
	/*
	 * 
	 */
	public Page<CurrencyRateModel> getPage(Paginator paginator,String orderBy, String keyword, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_rate t ");
		sql.append(" where 1=1 and t.del_flag=0 ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.create_date desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<CurrencyRateModel> page = CurrencyRateModel.dao
				.paginate(paginator, "select t.* ", sql.toString(),params.toArray());
		return page;
	}
	
}
