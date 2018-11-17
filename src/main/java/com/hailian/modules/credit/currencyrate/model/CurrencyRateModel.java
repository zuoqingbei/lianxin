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
import com.hailian.util.DateUtils;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
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
	 * 汇率列表展示
	 */
	public Page<CurrencyRateModel> getPage(Paginator paginator,String orderBy, String currency_a,String currency_b, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_rate t ");
		sql.append(" left join sys_dict_detail t2 on t.currency_a=t2.detail_id ");
		sql.append(" left join sys_dict_detail t3 on t.currency_b=t3.detail_id ");
		sql.append("left join sys_user t4 on t4.userid = t.update_by ");
		sql.append(" where 1=1 and t.del_flag=0 ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if (StrUtils.isNotEmpty(currency_a)) {
			sql.append(" and t.currency_a=?");
			params.add(currency_a);
		}
		if (StrUtils.isNotEmpty(currency_b)) {
			sql.append(" and t.currency_b=?");
			params.add(currency_b);
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.create_date desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<CurrencyRateModel> page = CurrencyRateModel.dao
				.paginate(paginator, "select t.*,t2.detail_name as a_name,t2.detail_name_en as a_name_en,t3.detail_name as b_name,t3.detail_name_en as b_name_en,t4.realname as username ", sql.toString(),params.toArray());
		return page;
	}
	public CurrencyRateModel getRateByA2B(String currency_a, String currency_b) {
		StringBuffer sql=new StringBuffer("select * from credit_rate t where t.del_flag=0 and t.currency_a=? and t.currency_b=?");
		List<Object> params=new ArrayList<Object>();
		params.add(currency_a);
		params.add(currency_b);
		return CurrencyRateModel.dao.findFirst(sql.toString(),params.toArray());
	}
	public void delete(Integer id, Integer userid) {
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_rate set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
	}
	
	
}
