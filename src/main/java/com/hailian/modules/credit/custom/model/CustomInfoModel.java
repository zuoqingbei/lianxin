package com.hailian.modules.credit.custom.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
import com.hailian.util.DateUtils;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单model层
* @author doushuihai  
* @date 2018年9月3日上午11:48:31  
* @TODO
 */
@ModelBind(table = "credit_custom_info")
public class CustomInfoModel extends BaseProjectModel<CustomInfoModel> {
	private static final long serialVersionUID = 1L;
	public static final CustomInfoModel dao = new CustomInfoModel();
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("t.name");
		columnnNames.add("t.name_en");
		columnnNames.add("t.contacts");
		columnnNames.add("t.contacts_short_name");
		columnnNames.add("t.reference_num");
		columnnNames.add("t.company_id");
		columnnNames.add("t.address");
		columnnNames.add("t.telphone");
		columnnNames.add("t.email");
		columnnNames.add("t.fax");
		columnnNames.add("t.country");
		columnnNames.add("t.remarks");
		columnnNames.add("t.create_date");
		columnnNames.add("t2.detail_name");
		columnnNames.add("t3.detail_name");
		columnnNames.add("t5.detail_name");
		columnnNames.add("t4.name");
	}
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:30:06  
	* @TODO
	 */
	public Page<CustomInfoModel> getPage(Paginator paginator,String orderBy, String keyword, BaseProjectController c) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" from credit_custom_info t left join sys_dict_detail t2 on  t.usabled=t2.detail_id ");
		sql.append(" left join sys_dict_detail t3 on t.is_old_customer=t3.detail_id ");
		sql.append(" left join credit_company_info t4 on t.company_id=t4.id ");
		sql.append(" left join sys_dict_detail t5 on t.country=t5.detail_id ");
		sql.append(" left join sys_dict_detail t6 on t.is_arrearage=t6.detail_id ");
		sql.append(" where 1=1 and t.del_flag=0 ");

		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		if (StringUtil.isNotEmpty(keyword)) {
			sql.append(" and ");
			for (int i = 0; i < columnnNames.size(); i++) {
				if("create_date".equals(columnnNames.get(i))){
					continue;
				}
				if(i!=0){
					sql.append(" || ");
				}
				//搜索类型
				sql.append(columnnNames.get(i)+" like binary ? ");
				params.add('%'+keyword+'%');
//				sql.append(columnnNames.get(i)+" like concat('%',?,'%')");
//				params.add(keyword);//传入的参数
			}
		}
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
		Page<CustomInfoModel> page = CustomInfoModel.dao
				.paginate(paginator, "select t.*,t2.detail_name as usableName,t3.detail_name as isOldCusName,t4.name as companyName,t5.detail_name as countryName,t6.detail_name as is_arrearage ", sql.toString(),params.toArray());
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
		String sql="update credit_custom_info set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
		
	}
	public List<CustomInfoModel> getCustom(Integer id){
		StringBuffer sql=new StringBuffer("select * from credit_custom_info where del_flag=0");
		List<Object> params=new ArrayList<Object>();
		if(id !=null){
			sql.append(" and id=?");
			params.add(id);
		}
		List<CustomInfoModel> find = CustomInfoModel.dao.find(sql.toString(),params.toArray());
		return find;
	}
	public  CustomInfoModel getCustomById(Integer paraToInt) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select t.*,t2.detail_name as usableName,t3.detail_name as isOldCusName,t4.name as companyName,t5.name as countryName from credit_custom_info t left join sys_dict_detail t2 on  t.usabled=t2.detail_id ");
		sql.append(" left join sys_dict_detail t3 on t.is_old_customer=t3.detail_id ");
		sql.append(" left join credit_company_info t4 on t.company_id=t4.id ");
		sql.append(" left join credit_country t5 on t.country=t5.id ");
		sql.append(" where 1=1 and t.del_flag=0 and t.id=?");
		params.add(paraToInt);
		CustomInfoModel findFirst = CustomInfoModel.dao.findFirst(sql.toString(),params.toArray());
		return findFirst;
	}
	/**
	 * 
	* @Description: 查询所有客户
	* @date 2018年11月6日 上午10:46:49
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public  List<CustomInfoModel> getAllcutomer(){
		String sql="SELECT  id ,name  from credit_custom_info ";
		return CustomInfoModel.dao.find(sql);
				
	}
}
