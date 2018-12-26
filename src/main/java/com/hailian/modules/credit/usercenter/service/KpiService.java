package com.hailian.modules.credit.usercenter.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.hailian.modules.admin.ordermanager.model.CreditKpiPrice;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.util.StrUtils;
import com.hailian.util.http.showapi.util.StringUtils;
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
	static final String IMPORT_AND_EXPORT_DATA_FLAG_STR = "ERWRWQRQR";//进出口数据模块判断标识
	static final String BASIC_INFO_FLAG_STR = "ASFSFASDFSA";//基本信息模块判断标识
	/**
	 * 计算方式1的计算规则
	 * @param companyId
	 * @param tableName
	 * @param column1
	 * @param price
	 * @return  
	 */
	
	public BigDecimal doFor1(String companyId,  String tableName, String column1, BigDecimal price) {
		try {
			if (StrUtils.isEmpty(companyId, price + "", column1, tableName)) { return new BigDecimal(0);}
			// 查询对应表中对应字段是否为空
			List<Object> flagStrList = Db.query(
					"select " + column1 + " from " + tableName + " where del_flag=0 and company_id=?  ",
					Arrays.asList(new String[] { companyId }).toArray());
			
			if (flagStrList==null||flagStrList.size()==0) { return new BigDecimal(0);}
			int count = 0;
			for (Object flagStr : flagStrList) {
				if (!StrUtils.isEmpty(((String)flagStr).trim())) count++;
			}
			if(count<1) {return new BigDecimal(0);}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal(0);
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
	public BigDecimal doFor2(String companyId, BigDecimal price, int sourceYeal) {
		try {
			if (StrUtils.isEmpty(companyId )) { return new BigDecimal(0); }
			// 查询公司id下的财务配置
			List<String> flagStr = Db.query(
					"select date1,date2,id from credit_company_financial_statements_conf where del_flag=0 and company_id=?  ",
					Arrays.asList(new String[] { companyId   }));
			String dateStr1 = flagStr.get(0);
			String dateStr2 = flagStr.get(1);
			if (StrUtils.isEmpty(dateStr1, dateStr2)) { return new BigDecimal(0); }
			int beginYear = Integer.parseInt(dateStr1.trim().substring(0, 3));
			int endYear = Integer.parseInt(dateStr1.trim().substring(0, 3));
			int targetCountYeal = endYear - beginYear; 
			if (targetCountYeal != sourceYeal) { return new BigDecimal(0); }
			//查询配置下的财务信息
			String confId = flagStr.get(2)+"";
			List<Integer> targetValueList =  Db.query(
					"select begin_date_value,end_date_value from credit_company_financial_entry where del_flag=0  and conf_id=?  ",
					Arrays.asList(new String[] { confId }));
			
			if(targetValueList!=null) {
				for (Integer integer : targetValueList) {
					if(integer!=null) { if(!(integer==null||integer==0)) { return price; } }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal(0);
		}
		return new BigDecimal(0);
	}
	
	
	/**
	 * 计算规则3的计算规则
	 * @param companyId
	 * @param price
	 * @return
	 */
	public BigDecimal doFor3(String companyId,  BigDecimal price) {
		if(StrUtils.isEmpty(companyId)) {
			return new BigDecimal(0);
		}
		//根据公司id找对应财务大数配置信息
		List<Integer> confIds = Db.query("select id from credit_company_financial_statements_conf where del_flag=0 and company_id=?  ",
				Arrays.asList(new String[] { companyId  }));
		//根据大数配置信息查找对应实体,如果实体值的字段有不为空则返回价格
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
		return new BigDecimal(0);
	}
	
	
	
	public BigDecimal doFor6(String companyId, BigDecimal price, int sourceYeal) { return   doFor2(companyId, price, sourceYeal); }

	private BigDecimal doFor7(String companyId, BigDecimal price) {return doFor3(companyId, price);}

	private BigDecimal doFor5(String reportType, String tableName,BigDecimal price) {
		if(StrUtils.isEmpty(reportType,tableName)) {new BigDecimal(0);}
		List<CreditReportModuleConf> listModuleConf = CreditReportModuleConf.dao.findByReport(reportType);
		if(listModuleConf!=null)
		for (CreditReportModuleConf entry : listModuleConf) {
			if(entry==null) {continue;}
			String getSource = entry.get("get_source");
			//如果存在该模块
			if(getSource!=null&&getSource.contains(tableName)) {
				return price;
			}
		}
		return new BigDecimal(0);
	}

	/**
	 * 根据速度获取对应速度的价格
	 * @param speed
	 * @param entry
	 * @return
	 */
	public BigDecimal  getPriceBySpeed(int speed,CreditKpiPrice entry) {
		BigDecimal a = new BigDecimal(0);
		if(entry==null) { return a;}
		return speed==151?(entry.getBigDecimal("extra_price")==null? a:entry.getBigDecimal("extra_price"))
			 :(speed==150?entry.getBigDecimal("urgent_price")==null? a:entry.getBigDecimal("urgent_price")
			  :entry.getBigDecimal("normal_price")==null? a:entry.getBigDecimal("normal_price"));
	}
	
	/**
	 * 获取公式实体
	 */
	public List<CreditKpiPrice> getFormulaModel(Integer formulaId,String roleId) {
		if(formulaId==null||StrUtils.isEmpty(formulaId+"",roleId)) {
			return new ArrayList<>();
		}
	   List<CreditKpiPrice> list = CreditKpiPrice.dao.find("select * from credit_kpi_price where del_flag=0 and formula_mode="+formulaId+" and role_id="+roleId);
	return list;
	}
	
	/*//加法  
	 6 bignum3 =  bignum1.add(bignum2);       
	 7 System.out.println("和 是：" + bignum3);  
	 8   
	 9 //减法  
	10 bignum3 = bignum1.subtract(bignum2);  
	11 System.out.println("差  是：" + bignum3);  
	12   
	13 //乘法  
	14 bignum3 = bignum1.multiply(bignum2);  
	15 System.out.println("积  是：" + bignum3);  
	16   
	17 //除法  
	18 bignum3 = bignum1.divide(bignum2);  
	19 System.out.println("商  是：" + bignum3);   */
	/**
	 * 获取某个订单当前角色的绩效
	 * @param roleId
	 * @param orderId
	 * @return
	 */
	public BigDecimal getKpi(String roleId,CreditOrderInfo order) {
		BigDecimal kpi = new BigDecimal(0);
		if(StrUtils.isEmpty(roleId)) {
			 return new BigDecimal(0);
		}
		if(order==null){ return new BigDecimal(0); }
		//获取报告语言
	    int  language = Integer.parseInt(order.get("report_language"));
	    //如果角色是翻译员
  		if(Integer.parseInt(roleId)==6) {
  			//报告语言是中文简体
  			if(language==213) { return new BigDecimal(0); }
  		}
		//是否是国内
		boolean isInland = "106".equals(order.get("country"));
	    //获取报告类型
		String reportType = order.get("order_type");
		//获取报告员填写的中文名称
		String companyByReport = order.get("company_by_report");
		boolean isPa = !StringUtils.isEmpty(companyByReport);
		//获取报告速度
		int speed = Integer.parseInt(order.get("speed"));
	    //获取公司id
	    String  companyId =  order.get("company_id");
	    //获取代理类别
	    String  agentCategory =  order.get("agent_category");
	    
	    if(StrUtils.isEmpty(companyId,speed+"",reportType,language+"")) {
			 return new BigDecimal(0);
		}
	
		// 若是国外则计算方式为4:单个报告统一计算价格方式
		if(!isInland) {
 			CreditKpiPrice kpiDict = CreditKpiPrice.dao.findFirst("select normal_price,urgent_price,extra_price from  credit_kpi_price where del_flag=0 and formula_mode=4 and role_id="+roleId );
 			if(kpiDict==null) {return new BigDecimal(0);}
 			if(speed==149) {
 				return kpiDict.getBigDecimal("normal_price")==null?new BigDecimal(0):kpiDict.getBigDecimal("normal_price");
 			}else if(speed==150) {
 				return kpiDict.getBigDecimal("urgent_price")==null?new BigDecimal(0):kpiDict.getBigDecimal("urgent_price");
 			}else if(speed==151) {
 				return kpiDict.getBigDecimal("extra_price")==null?new BigDecimal(0):kpiDict.getBigDecimal("extra_price");
 			}
 			return new BigDecimal(0);
		}else {//若是国内
				 
				/*计算方式 1-字段是否有值 2-财务 3-大数 4-单个报告统一计算价格方式  5-进出口数据和基本信息校对模块被爬取时计算方式 6-财务被查档时计算方式 7-大数被查档是计算方式*/
				List<CreditKpiPrice> kpiDict1 = getFormulaModel(1, roleId);
				List<CreditKpiPrice> kpiDict2 = getFormulaModel(2, roleId);
				List<CreditKpiPrice> kpiDict3 =  getFormulaModel(3, roleId);
				List<CreditKpiPrice> kpiDict5 =  getFormulaModel(5, roleId);
				List<CreditKpiPrice> kpiDict6 =  getFormulaModel(6, roleId);
				List<CreditKpiPrice> kpiDict7 =  getFormulaModel(7, roleId);
				//如果计算方式是1的话(按字段是否有值计算的方式)
				BigDecimal totalFor1 = new BigDecimal(0);
				if(kpiDict1!=null) {
					for (CreditKpiPrice entry : kpiDict1) {
						BigDecimal temp = doFor1(companyId,entry.get("table_name"), entry.get("column1"),getPriceBySpeed(speed, entry));
						kpi.add(temp); totalFor1 = totalFor1.add(temp);
					} 
				}
				// 计算方式是 2时
				BigDecimal totalFor2 = new BigDecimal(0);
				if(kpiDict2!=null) {
				for (CreditKpiPrice entry : kpiDict2) { 
					BigDecimal temp = doFor2(companyId,  getPriceBySpeed(speed, entry), entry.get("count_finance_year"));
				    kpi.add(temp);  totalFor2 = totalFor1.add(temp);
				}}
				
				// 计算方式是3 
				BigDecimal totalFor3 = new BigDecimal(0);
				if(kpiDict3!=null) {
				for (CreditKpiPrice entry : kpiDict3) {
					BigDecimal temp = doFor3(companyId, getPriceBySpeed(speed, entry));
				    kpi.add(temp); totalFor3 = totalFor1.add(temp);
				}}
				
				//如果角色是报告员 做处理特殊
				if("2".equals(roleId) ) {
					if(isPa) {//如果是爬取的
						//先减去计算方式一的结果
						kpi.subtract(totalFor1);
						//累加计算方式5的结果
						if(kpiDict5!=null) {
							for (CreditKpiPrice entry : kpiDict5) { 
								BigDecimal temp = doFor5(reportType, entry.get("table_name"), getPriceBySpeed(speed, entry) );
								kpi.add(temp);
							}
						}
					} 
					/*代理类别
					343	注股
					344	变更
					345	单大数
					346	全国税务
					347	全国三年税务
					348	当地税务
					349	当地excel税务
					350	统计
					351	管理层ID
					352	全国税务+管理层ID*/
					/** 如果财务信息是查档得来*/
					/*计算方式 1-字段是否有值 2-财务 3-大数 4-单个报告统一计算价格方式  5-进出口数据和基本信息校对模块被爬取时计算方式 6-财务被查档时计算方式 7-大数被查档是计算方式*/
					if("346".equals(agentCategory)||"347".equals(agentCategory)||"348".equals(agentCategory)||"349".equals(agentCategory)||"352".equals(agentCategory)) {
						//先减去计算方式2的结果
						kpi.subtract(totalFor2);
						//累加计算方式6的
						if(kpiDict6!=null) {
							for (CreditKpiPrice entry : kpiDict6) { 
								BigDecimal temp = doFor6(companyId,  getPriceBySpeed(speed, entry), entry.get("count_finance_year"));
								kpi.add(temp);
							}
						}
					} 
					//如果大数信息是查档得来
					if("345".equals(agentCategory)) {
						//先减去计算方式3的结果
						kpi.subtract(totalFor3);
						//累加计算方式7的
						if(kpiDict7!=null) {
							for (CreditKpiPrice entry : kpiDict7) { 
								BigDecimal temp = doFor7(companyId, getPriceBySpeed(speed, entry));
								kpi.add(temp);
							}
						}
					}
				}
		
		} 
		
		//如果角色是翻译员
		if(Integer.parseInt(roleId)==6) {
			//报告语言是是中文繁体体和英文
			if(language==217) { kpi.multiply(new BigDecimal(2)); }
		}
		return kpi; 
	}
}
