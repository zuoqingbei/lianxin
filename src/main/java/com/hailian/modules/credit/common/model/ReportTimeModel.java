package com.hailian.modules.credit.common.model;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.controller.ReportTimeController;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * @todo 时间配置持久层处理
 * @author lzg
 * @time 2018/09/07 下午 3:20
 */
@ModelBind(table = "credit_report_usetime")
public class ReportTimeModel extends BaseProjectModel<ReportTimeModel>{
	private static final long serialVersionUID = 1L;
	public static final ReportTimeModel dao = new ReportTimeModel();//名字都叫dao，统一命名
	public Page<ReportTimeModel> pagerOrder(int pageNumber, int pagerSize, List<Object> keywords, String orderBy,String searchType, BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select c.*, ");
		selectSql.append(" s1.name AS reportType, ");
		selectSql.append(" s2.detail_name AS reportSpeed, ");
		selectSql.append(" s3.detail_name AS countryType, ");
		selectSql.append(" s4.detail_name AS orderType, ");
		selectSql.append(" s5.detail_name AS usabled, ");
		selectSql.append(" u.realname AS realname ");
		StringBuffer fromSql = new StringBuffer(" FROM credit_report_usetime c ");
		fromSql.append(" LEFT JOIN credit_report_type s1 ON c.report_id = s1.id ");
		fromSql.append(" LEFT JOIN sys_dict_detail s2 ON c.report_speed = s2.detail_id ");
		fromSql.append(" LEFT JOIN sys_dict_detail s3 ON c.country_type = s3.detail_id ");
		fromSql.append(" LEFT JOIN sys_dict_detail s4 ON c.order_type = s4.detail_id ");
		fromSql.append(" LEFT JOIN sys_dict_detail s5 ON c.usabled = s5.detail_id ");
		fromSql.append(" LEFT JOIN sys_user u ON u.userid = c.update_by ");
		fromSql.append(" where c.del_flag = 0 ");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		//若搜索类型是通过id查询
		if(ReportTimeController.searchById.equals(searchType)){
			fromSql.append(" and c.id = ? ");
			return ReportTimeModel.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),fromSql.toString(), keywords.toArray());
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
		return ReportTimeModel.dao.paginate(new Paginator(pageNumber, pagerSize), selectSqlStr ,fromSql.toString(), params.toArray());
	}
}
