
package com.hailian.modules.admin.ordermanager.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.controller.ReportTimeController;
import com.hailian.modules.credit.common.model.ReportTimeModel;
import com.hailian.system.user.SysUser;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className CreditOrderInfo.java
 * @time   2018年8月23日 上午9:05:28
 * @author yangdong
 * @todo   
 * 订单模型,jfinal创建模型不需要写属性,只需要继承Model或者model的子类,就会获取与数据库表的连接
 * 继承model的子类会得到很多方法的扩展
 */
@ModelBind(table = "credit_order_info")//此标签用于模型与数据库表的连接
public class CreditOrderInfo extends BaseProjectModel<CreditOrderInfo>{
	//客户姓名
	private String customName;
	//国家
	private String countryName;
	//订单创建者的username
	private String createName;
	//地区
	private String continentName;
	//报告类型
	private String reportType;
	//报告语言
	private String reportLanguage;
	//报告速度
	private String reportSpeed;
	//订单来源
	private String orderType;
	//订单价格
	private String  price;
	//公司名称
	private String companyName;
	//公司英文名称
	private String englishName;
	
	public String getenglishName() {
		return get("englishName");
	}

	public void setenglishName(String englishName) {
		set("englishName", englishName);
	}
	
	public String getcompanyName() {
		return get("companyName");
	}

	public void setcompanyName(String companyName) {
		set("companyName", companyName);
	}
	public String getCustomName() {
		return get("customName");
	}

	public void setCustomName(String customName) {
		set("customName", customName);
	}
	public String getCountryName() {
		return get("countryName");
	}

	public void setCountryName(String countryName) {
		set("countryName", countryName);
	}
	public String getCreateName() {
		return get("createName");
	}
	public void setCreateName(String createName) {
		set("createName", createName);
	}
	public String getContinentName() {
		return get("continentName");
	}
	public void setContinentName(String continentName) {
		set("continentName", continentName);
	}
	public String getReportType() {
		return get("reportType");
	}
	public void setReportType(String reportType) {
		set("reportType", reportType);
	}
	public String getReportLanguage() {
		return get("reportLanguage");
	}
	public void setReportLanguage(String reportLanguage) {
		set("reportLanguage", reportLanguage);
	}
	public String getOrderType() {
		return get("orderType");
	}
	public void setOrderType(String orderType) {
		set("orderType", orderType);
	}
	public String getPrice() {
		return get("price");
	}
	public void setPrice(String price) {
		set("price", price);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	public static final CreditOrderInfo dao = new CreditOrderInfo();//名字都叫dao，统一命名
	/**
	 * 
	 * @time   2018年8月31日 下午2:21:15
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param pageinator
	 * @param  @param model
	 * @param  @param orderby
	 * @param  @param user
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<CreditOrderInfo>
	 */
	public Page<CreditOrderInfo> getOrders(Paginator pageinator,CreditOrderInfo model,String orderby,BaseProjectController c) {
		StringBuffer sql = new StringBuffer();
		//客户id
		String custom_id=model.getStr("custom_id");
		//地区
		String continent=model.getStr("continent");
		//国家
		String country=model.getStr("country");
		//结束时间
		String end_date=model.getStr("end_date");
		//准确公司名称(经过翻译后的公司名称是中文)
		String company_by_report=model.getStr("company_by_report");
		//填写订单时输入的公司名称
		String right_company_name_en=model.getStr("right_company_name_en");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_order_info t ");
		sql.append(" left join credit_custom_info u on u.id=t.custom_id ");
		sql.append(" left join credit_country c on c.id=t.country ");
		sql.append(" left join credit_report_price c1 on c1.id=t.price_id ");
		sql.append(" left join credit_company_info c2 on c2.id=t.company_id");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_dict_detail s2  on s2.detail_id=t.continent ");
		sql.append(" left join credit_report_type s3  on s3.id=t.report_type ");
		sql.append(" left join sys_dict_detail s4  on s4.detail_id=t.report_language ");
		sql.append(" left join sys_dict_detail s5  on s5.detail_id=t.speed ");
		sql.append(" left join sys_dict_detail s6  on s6.detail_id=t.order_type ");
		sql.append(" where 1 = 1 and t.del_flag='0' ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}

		if (StringUtils.isNotBlank(custom_id)) {
			sql.append(" and t.custom_id=?");
			params.add(custom_id);
		}
		if(StringUtils.isNotBlank(continent)) {
			sql.append(" and t.continent=?");
			params.add(continent);
		}
		if(StringUtils.isNotBlank(country)) {
			sql.append(" and t.country=?");
			params.add(country);
		}
		if(StringUtils.isNotBlank(end_date)) {
			sql.append(" and t.end_date=?");
			params.add(end_date);
		}
		if(StringUtils.isNotBlank(company_by_report)) {
			sql.append(" and t.company_by_report like %?%");
			params.add(company_by_report);
		}
		if(StringUtils.isNotBlank(right_company_name_en)) {
			sql.append(" and t.right_company_name_en like %?%");
			params.add(right_company_name_en);
		}
		if (StrUtils.isEmpty(orderby)) {
			sql.append(" order by t.id desc");
		} else {
			sql.append(" order by ").append(orderby);
		}
		Page<CreditOrderInfo> page=CreditOrderInfo.dao.paginate(pageinator, "select t.*,u.name as customName,c.name as countryName,s.username as createName"
				+ ",s2.detail_name as continentName,s3.name as reportType,s4.detail_name as reportLanguage,"
				+ "s5.detail_name as reportSpeed,s6.detail_name as orderType,c1.price as price,c2.name as companyName,c2.name_en as englishName ", sql.toString(),params.toArray());
		
		return page;
	}
	/**
	 * 
	 * @time   2018年8月31日 下午2:40:47
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param id
	 * @param  @param c
	 * @param  @return
	 * @return_type   CreditOrderInfo
	 */
	public CreditOrderInfo getOrder(int id, BaseProjectController c) {

		StringBuffer sql=new StringBuffer();
		sql.append("select t.*,u.name as customName,c.name as countryName,s.username as createName,");
		sql.append("s2.detail_name as continentName,s3.name as reportType,s4.detail_name as reportLanguage,");
		sql.append("s5.detail_name as reportSpeed,s6.detail_name as orderType,c1.price as price, c2.name as companyName,c2.name_en as englishName  from credit_order_info t ");
		sql.append("left join credit_custom_info u on u.id=t.custom_id ");
		sql.append("left join credit_country c on c.id=t.country  ");
		sql.append("left join credit_report_price c1 on c1.id=t.price_id ");
		sql.append(" left join credit_company_info c2 on c2.id=t.company_id ");
		sql.append("left join sys_user s on s.userid=t.create_by ");
		sql.append("left join sys_dict_detail s2  on s2.detail_id=t.continent ");
		sql.append("left join credit_report_type s3  on s3.id=t.report_type ");
		sql.append("left join sys_dict_detail s4  on s4.detail_id=t.report_language ");
		sql.append("left join sys_dict_detail s5  on s5.detail_id=t.speed ");
		sql.append("left join sys_dict_detail s6  on s6.detail_id=t.order_type ");
		sql.append("where 1 = 1 and t.del_flag='0' and t.id=?");
		return dao.findFirst(sql.toString(),id);
	}

	public List<CreditOrderInfo> getOrders(CreditOrderInfo model, String orderby, BaseProjectController c) {
		StringBuffer sql = new StringBuffer();
		String custom_id=model.getStr("custom_id");
		String id=model.getStr("id");
		
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_order_info t ");
		sql.append(" left join credit_custom_info u on u.id=t.custom_id ");
		sql.append(" left join credit_country c on c.id=t.country ");
		sql.append(" left join credit_report_price c1 on c1.id=t.price_id ");
		sql.append(" left join credit_company_info c2 on c2.id=t.company_id");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_dict_detail s2  on s2.detail_id=t.continent ");
		sql.append(" left join credit_report_type s3  on s3.id=t.report_type ");
		sql.append(" left join sys_dict_detail s4  on s4.detail_id=t.report_language ");
		sql.append(" left join sys_dict_detail s5  on s5.detail_id=t.speed ");
		sql.append(" left join sys_dict_detail s6  on s6.detail_id=t.order_type ");
		sql.append(" where 1 = 1 and t.del_flag='0' ");
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}

		if (StringUtils.isNotBlank(custom_id)) {
			sql.append(" and t.custom_id=?");
			params.add(custom_id);
		}
		if(StringUtils.isNotBlank(id)) {
			sql.append(" and t.id=?");
			params.add(id);
		}
		if (StrUtils.isEmpty(orderby)) {
			sql.append(" order by t.id desc");
		} else {
			sql.append(" order by ").append(orderby);
		}
		List<CreditOrderInfo> list=CreditOrderInfo.dao.find( "select t.*,u.name as customName,c.name as countryName,s.username as createName"
				+ ",s2.detail_name as continentName,s3.name as reportType,s4.detail_name as reportLanguage,"
				+ "s5.detail_name as reportSpeed,s6.detail_name as orderType,c1.price as price,c2.name as companyName,c2.name_en as englishName "+ sql.toString(),params.toArray());
		
		return list;

	}
	
	public Page<CreditOrderInfo> getOrders(Paginator pageinator, CreditOrderInfo model, String orderby, String status,
			SysUser user) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		//客户id
		String custom_id=model.getStr("custom_id");
		//地区
		String continent=model.getStr("continent");
		//国家
		String country=model.getStr("country");
		//结束时间
		Date end_date=model.get("end_date");
		String date="";
		if(end_date!=null) {
		 date=sdf.format(end_date);
		}
		
		//准确公司名称(经过翻译后的公司名称是中文)
		String company_by_report=model.getStr("company_by_report");
		//填写订单时输入的公司名称
		String right_company_name_en=model.getStr("right_company_name_en");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_order_info t ");
		sql.append(" left join credit_custom_info u on u.id=t.custom_id ");
		sql.append(" left join credit_country c on c.id=t.country ");
		sql.append(" left join credit_report_price c1 on c1.id=t.price_id ");
		sql.append(" left join credit_company_info c2 on c2.id=t.company_id");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_dict_detail s2  on s2.detail_id=t.continent ");
		sql.append(" left join credit_report_type s3  on s3.id=t.report_type ");
		sql.append(" left join sys_dict_detail s4  on s4.detail_id=t.report_language ");
		sql.append(" left join sys_dict_detail s5  on s5.detail_id=t.speed ");
		sql.append(" left join sys_dict_detail s6  on s6.detail_id=t.order_type ");
		sql.append(" where 1 = 1 and t.del_flag='0' ");
		if(!"1".equals(user.getInt("usertype").toString())){
			sql.append(" and t.create_by=? ");
			params.add(user.getStr("usertype"));
		}

		if (StringUtils.isNotBlank(custom_id)) {
			sql.append(" and t.custom_id=?");
			params.add(custom_id);
		}
		if(StringUtils.isNotBlank(continent)) {
			sql.append(" and t.continent=? ");
			params.add(continent);
		}
		if(StringUtils.isNotBlank(country)) {
			sql.append(" and t.country=?");
			params.add(country);
		}
		if(StringUtils.isNotBlank(date)) {
			sql.append(" and t.end_date=?");
			params.add(date);
		}
		if(StringUtils.isNotBlank(company_by_report)) {
			sql.append(" and t.company_by_report like concat('%',?,'%')");
			params.add(company_by_report);
		}
		if(StringUtils.isNotBlank(right_company_name_en)) {
			sql.append(" and t.right_company_name_en  like concat('%',?,'%')");
			params.add(right_company_name_en);
		}
		if(StringUtils.isNotBlank(status)) {
			sql.append(" and t.status in(?)");
			params.add(status);
		}
		if (StrUtils.isEmpty(orderby)) {
			sql.append(" order by t.id desc");
		} else {
			sql.append(" order by ").append(orderby);
		}
		Page<CreditOrderInfo> page=CreditOrderInfo.dao.paginate(pageinator, "select t.*,u.name as customName,c.name as countryName,s.username as createName"
				+ ",s2.detail_name as continentName,s3.name as reportType,s4.detail_name as reportLanguage,"
				+ "s5.detail_name as reportSpeed,s6.detail_name as orderType,c1.price as price,c2.name as companyName,c2.name_en as englishName ", sql.toString(),params.toArray());
		
		return page;
	}
	/**
	 * @todo 定单分配列表展示
	 * @author lzg
	 * @time 2018/09/14下午 3:20
	 */
	public Page<CreditOrderInfo> pagerOrder(int pageNumber, int pagerSize, List<Object> keywords, String orderBy,String searchType, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select c.*, ");
		selectSql.append(" s1.detail_name AS country, ");
		selectSql.append(" s2.name AS reportType, ");
		selectSql.append(" u1.username AS reportUser,");
		selectSql.append(" u2.username AS translateUser,");
		selectSql.append(" u3.username AS analyzeUser");
		StringBuffer fromSql = new StringBuffer(" FROM credit_order_info c ");
		fromSql.append(" LEFT JOIN sys_dict_detail s1 ON c.country = s1.detail_id ");//国家
		fromSql.append(" LEFT JOIN credit_report_type s2 ON c.report_type = s2.id ");//报告类型
		fromSql.append(" LEFT JOIN sys_user u1 ON u1.userid = c.report_user ");//报告员
		fromSql.append(" LEFT JOIN sys_user u2 ON u2.userid = c.translate_user ");//翻译员
		fromSql.append(" LEFT JOIN sys_user u3 ON u3.userid = c.analyze_user ");//分析员
		fromSql.append(" where c.del_flag = 0 ");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		//若搜索类型是通过id查询单条信息
		if(ReportTimeController.searchById.equals(searchType)){
			selectSql.setLength(0);
			fromSql.setLength(0);
			selectSql.append(" s1.detail_name AS country, ");
			selectSql.append(" s2.name AS reportType, ");
			selectSql.append(" s3.detail_name AS continent, ");
			selectSql.append(" s4.detail_name AS orderType, ");
			selectSql.append(" s5.detail_name AS reportLanguage, ");
			selectSql.append(" s6.detail_name AS speed, ");
			selectSql.append(" u1.username AS reportUser,");
			selectSql.append(" u2.username AS translateUser,");
			selectSql.append(" u3.username AS analyzeUser,");
			selectSql.append(" u4.username AS customId");
			fromSql.append(" LEFT JOIN sys_dict_detail s1 ON c.country = s1.detail_id ");//国家
			fromSql.append(" LEFT JOIN credit_report_type s2 ON c.report_type = s2.id ");//报告类型
			//比列表多展示的四个字段
			fromSql.append(" LEFT JOIN sys_dict_detail s3 ON c.continent = s3.detail_id ");//地区
			fromSql.append(" LEFT JOIN sys_dict_detail s4 ON c.order_type = s4.detail_id ");//订单类型
			fromSql.append(" LEFT JOIN sys_dict_detail s5 ON c.report_language = s5.detail_id ");//报告语言
			fromSql.append(" LEFT JOIN sys_dict_detail s6 ON c.speed = s6.detail_id ");//报告速度
			
			fromSql.append(" LEFT JOIN sys_user u1 ON u1.userid = c.report_user ");//报告员
			fromSql.append(" LEFT JOIN sys_user u2 ON u2.userid = c.translate_user ");//翻译员
			fromSql.append(" LEFT JOIN sys_user u3 ON u3.userid = c.analyze_user ");//分析员
			fromSql.append(" LEFT JOIN sys_user u4 ON u4.userid = c.custom_id ");//客户
			fromSql.append(" where c.del_flag = 0 ");
			fromSql.append(" and c.id = ? ");
			return CreditOrderInfo.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),fromSql.toString(), keywords.toArray());
		}
		//若为其他搜索类型
		if (keywords!=null&&keywords.size()>0) {
			List<Object> columnNames = ReportTimeController.columnNames;
			//搜索类型
			String preStr = "";
			if(ReportTimeController.fuzzySearch.equals(searchType)){
				preStr = " like concat('%',?,'%') ";
			}else{
				preStr = " = ? ";
			}
			//条件语句拼接
			for (int i = 0; i < columnNames.size(); i++) {
				if("create_date".equals(columnNames.get(i))){
					continue;
				}
				if(StringUtil.isEmpty((String)keywords.get(i))){
					continue;
				}
				fromSql.append(" and ");
				//搜索类型
				fromSql.append(columnNames.get(i)+preStr);
				params.add(keywords.get(i));//传入的参数
			}
			
		}
		//权限区分
		if(!c.isAdmin(c.getSessionUser())){
			fromSql.append(" and c.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		//排序
		if (StrUtils.isEmpty(orderBy)) {
			fromSql.append(" order by c.create_date desc");
		} else {
			fromSql.append(" order by ").append(orderBy);
		}
		String selectSqlStr = selectSql.toString();
		return CreditOrderInfo.dao.paginate(new Paginator(pageNumber, pagerSize), selectSqlStr ,fromSql.toString(), params.toArray());
	}
	
	
}
