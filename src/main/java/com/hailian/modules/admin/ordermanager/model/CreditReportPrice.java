package com.hailian.modules.admin.ordermanager.model;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
/**
 * 
 * @className CreditReportPrice.java
 * @time   2018年9月3日 下午7:32:21
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "credit_report_price")
public class CreditReportPrice extends BaseProjectModel<CreditReportPrice>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CreditReportPrice dao = new CreditReportPrice();//名字都叫dao，统一命名
	/**
	 * 
	 * @time   2018年9月5日 上午9:14:30
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param countryType
	 * @param  @param speed
	 * @param  @param reporttype
	 * @param  @return
	 * @return_type   CreditReportPrice
	 */
	public CreditReportPrice getPrice(String countryType, String speed, String reporttype,String orderType) {
		// TODO Auto-generated method stub
			List<String> params=new ArrayList<String>();
			StringBuffer sql=new StringBuffer(" select t.* from credit_report_price t where t.del_flag='0' ");
			if (StringUtils.isNotBlank(countryType)) {
				sql.append(" and t.country_type=?");
				params.add(countryType);
			}
			if (StringUtils.isNotBlank(speed)) {
				sql.append(" and t.order_speed=?");
				params.add(speed);
			}
			if (StringUtils.isNotBlank(reporttype)) {
				sql.append(" and t.report_type=?");
				params.add(reporttype);
			}
			if (StringUtils.isNotBlank(orderType)) {
				sql.append(" and t.report_type=?");
				params.add(orderType);
			}
			
			return CreditReportPrice.dao.findFirst(sql.toString(),params.toArray());
		}
	}

