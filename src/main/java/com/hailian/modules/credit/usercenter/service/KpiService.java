package com.hailian.modules.credit.usercenter.service;
import java.util.Arrays;
import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditKpiPrice;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;

public class KpiService {
	public static KpiService service = new KpiService();// 名字都叫service，统一命名
	/**
	 * 计算方式1-字段内是否有值
	 */
	public static final int FORMULA1 = 1;
	/**
	 * 计算方式2-财务年数
	 */
	public static final int FORMULA2 = 2;
	/**
	 * 计算方式3-有无大数
	 */
	public static final int FORMULA3 = 3;

	/**
	 * 计算方式1的计算规则
	 * @param companyId
	 * @param tableName
	 * @param column1
	 * @param price
	 * @return  
	 */
	
	public double doFor1(String companyId,  String tableName, String column1, double price) {
		try {
			if (StrUtils.isEmpty(companyId, price + "", column1, tableName)) { return 0; 	}
			// 查询对应表中对应字段是否为空
			String flagStr = Db.queryColumn(
					"select " + column1 + " from " + tableName + " where del_flag=0 and company_id=?  ",
					Arrays.asList(new String[] { companyId }));
			if (StrUtils.isEmpty(flagStr.trim())) { return 0; }
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return price;
	}
	
	/**
	 * 计算方式2的计算规则
	 * @param companyId
	 * @param tableName
	 * @param column1
	 * @param column2
	 * @param price
	 * @param sourceYeal
	 * @return
	 */
	public double doFor2(String companyId, double price, int sourceYeal) {
		try {
			if (StrUtils.isEmpty(companyId )) { return 0; }
			// 查询对应表中对应字段是否为空
			List<String> flagStr = Db.query(
					"select date1,date2 from credit_company_financial_statements_conf where del_flag=0 and company_id=?  ",
					Arrays.asList(new String[] { companyId   }));
			String dateStr1 = flagStr.get(0);
			String dateStr2 = flagStr.get(1);
			if (StrUtils.isEmpty(dateStr1, dateStr2)) { return 0; }
			int beginYear = Integer.parseInt(dateStr1.trim().substring(0, 3));
			int endYear = Integer.parseInt(dateStr1.trim().substring(0, 3));
			int targetCountYeal = endYear - beginYear;
			if (targetCountYeal != sourceYeal) { return 0; }
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return price;
	}
	
	
	/**
	 * 计算规则3的计算规则
	 * @param companyId
	 * @param price
	 * @return
	 */
	public double doFor3(String companyId,  double price) {
		if(StrUtils.isEmpty(companyId)) {
			return 0;
		}
		//根据公司id找对应财务大数配置信息
		List<Integer> confIds = Db.query("select id from credit_company_financial_statements_conf where del_flag=0 and company_id=?  ",
				Arrays.asList(new String[] { companyId   }));
		//根据大数配置信息查找对应实体,如过实体值的字段有不为空则返回价格
		String strIds = "";
		for (int i = 0; i < confIds.size(); i++) {
			if(i!=confIds.size()-1) {
				strIds += confIds.get(i)+", ";
			}
		}
	    List<String> flagStrList = Db.query("select begin_date_value,end_date_value from credit_company_financial_entry where del_flag=0 and conf_id in ("+strIds+")");
		 for (String string : flagStrList) {
			if(!StrUtils.isEmpty(string)) {
				return price;
			}
		} 
		return 0;
	}
	
	/**
	 * 获取某个订单当前角色的绩效
	 * @param roleId
	 * @param orderId
	 * @return
	 */
	public double getKpi(String roleId,CreditOrderInfo order) {
		double kpi = 0;
		if(StrUtils.isEmpty(roleId)) {
			 return 0;
		}
		if(order==null){ return 0; }
		//获取报告语言
	    int  language = Integer.parseInt(order.get("report_language"));
	    //如果角色是翻译员
  		if(Integer.parseInt(roleId)==6) {
  			//报告语言是中文简体
  			if(language==213) {
  				return 0;
  			}
  		}
		//是否是国内
		boolean isInland = "106".equals(order.get("country"));
	    //获取报告类型
		String reportType = order.get("order_type");
		//获取报告速度
		int speed = Integer.parseInt(order.get("speed"));
		
	    //获取公司id
	    String  companyId =  order.get("company_id");
	    if(StrUtils.isEmpty(companyId)) {
			 return 0;
		}
	
		// 若是国外
		if(!isInland) {
 			CreditKpiPrice kpiDict = CreditKpiPrice.dao.findFirst("select normal_price,urgent_price,extra_price from  credit_kpi_price where del_flag=0 and formula_mode=4 and role_id="+roleId);
 			if(kpiDict==null) {return 0;}
 			if(speed==149) {
 				return kpiDict.getDouble("normal_price")==null?0:kpiDict.getDouble("normal_price");
 			}else if(speed==150) {
 				return kpiDict.getDouble("urgent_price")==null?0:kpiDict.getDouble("urgent_price");
 			}else if(speed==151) {
 				return kpiDict.getDouble("extra_price")==null?0:kpiDict.getDouble("extra_price");
 			}
 			return 0;
		}else {
			//若是国内
			List<CreditKpiPrice> kpiDict1 = CreditKpiPrice.dao.find("select * from credit_kpi_price where del_flag=0 and formula_mode=1 and role_id="+roleId);
			List<CreditKpiPrice> kpiDict2 = CreditKpiPrice.dao.find("select * from credit_kpi_price where del_flag=0 and formula_mode=2 and role_id="+roleId);
			List<CreditKpiPrice> kpiDict3 = CreditKpiPrice.dao.find("select * from credit_kpi_price where del_flag=0 and formula_mode=3 and role_id="+roleId);
			//如果计算方式是1的话(按字段是否有值计算的方式)
			if(kpiDict1!=null) {
				for (CreditKpiPrice entry : kpiDict1) {
					kpi += doFor1(companyId,entry.get("table_name"), entry.get("column1"),getPriceBySpeed(speed, entry));
				} }
			
			if(kpiDict2!=null) {
			for (CreditKpiPrice entry : kpiDict2) {
				kpi += doFor2(companyId,  getPriceBySpeed(speed, entry), entry.get("count_finance_year"));
			}}
			
			if(kpiDict3!=null) {
			for (CreditKpiPrice entry : kpiDict3) {
				kpi += doFor3(companyId, getPriceBySpeed(speed, entry));
			}}
			
		} 
		//如果角色是翻译员
		if(Integer.parseInt(roleId)==6) {
			//报告语言是是中文繁体体和英文
			if(language==217) {
				kpi *= 2;
			}
		}
		return kpi; 
	}
	
	/**
	 * 根据速度获取对应速度的价格
	 * @param speed
	 * @param entry
	 * @return
	 */
	public Double  getPriceBySpeed(int speed,CreditKpiPrice entry) {
		if(entry==null) { return 0.0;}
		return speed==151?entry.getDouble("extra_price"):(speed==150?entry.getDouble("urgent_price"):entry.getDouble("normal_price"));
	}
	
	
	
	
}
