package com.hailian.system.dict;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @className SysDictDetail.java
 * @time   2018年8月31日 上午11:54:49
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "sys_dict_detail", key = "detail_id")
public class SysDictDetail extends BaseProjectModel<SysDictDetail> {

	private static final long serialVersionUID = 1L;
	public static final SysDictDetail dao = new SysDictDetail();
	private static List<String> columnnNames = new ArrayList<>();
	static{
		columnnNames.add("detail_name");
		columnnNames.add("detail_name_en");
		columnnNames.add("detail_remark");
	}

	/**
	 * 
	 * @time   2018年8月31日 上午11:54:43
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param dictType
	 * @param  @return
	 * @return_type   List<SysDictDetail>
	 */
	public List<SysDictDetail> getDictByType(String dictType) {
		return dao.find("select t.* from sys_dict_detail t where t.dict_type=? and del_flag=0 order by detail_sort desc",
				dictType);

	}
	
	/**
	 * 
	 * @todo   分页查询字典类型
	 * @time   2018年9月4日 下午12:05:36
	 * @author zuoqb
	 * @params
	 */

	public Page<SysDictDetail> pagerSysDictDetail(int pageNumber, int pagerSize, String keyWord,
			String orderBy,String dictType,BaseProjectController c) {
		StringBuffer selectSql = new StringBuffer(" select t.*,u.realname ");
		StringBuffer fromSql = new StringBuffer(" from sys_dict_detail t left join sys_user u on u.userid=t.create_id where t.del_flag=0 ");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		if (StringUtil.isNotEmpty(dictType)) {
			fromSql.append(" and t.dict_type=? ");
			params.add(dictType);//传入的参数
		}
		if (StringUtil.isNotEmpty(keyWord)) {
			fromSql.append(" and ");
			for (int i = 0; i < columnnNames.size(); i++) {
				if(i!=0){
					fromSql.append(" || ");
				}
				fromSql.append(columnnNames.get(i)+" like concat('%',?,'%')");
				params.add(keyWord);//传入的参数
			}
			
		}
		if(!c.isAdmin(c.getSessionUser())){
			fromSql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		//排序
		if (StrUtils.isEmpty(orderBy)) {
			fromSql.append(" order by t.detail_sort  ");
		} else {
			fromSql.append(" order by ").append(orderBy);
		}
		
		Page<SysDictDetail> sysDictDetailPage = SysDictDetail.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),
				fromSql.toString(), params.toArray());
		return sysDictDetailPage;
	}
	public static List<SysDictDetail> getDictDetailByContinent(String continent){
		String sql="select * from sys_dict_detail where del_flag=0";
		sql+=" and dict_type='continent' ";
		sql+=" and detail_name='"+continent+"'";
		List<Object> params=new ArrayList<Object>();
		params.add(continent);
		System.out.println(sql);
		List<SysDictDetail> list = SysDictDetail.dao.find(sql);
		return list;
	}
	public static List<SysDictDetail> getDictDetailByOrderType(String ordertype){
		String sql="select * from sys_dict_detail where del_flag=0";
		sql+=" and dict_type='ordertype' ";
		sql+=" and detail_name=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(ordertype);
		List<SysDictDetail> list = SysDictDetail.dao.find(sql, params.toArray());
		return list;
	}
	public static List<SysDictDetail> getDictDetailBy(String detail_name,String dictType){
		List<Object> params=new ArrayList<Object>();
		String sql="select * from sys_dict_detail where del_flag=0";
		sql+=" and dict_type=? ";
		params.add(dictType);
		if(StringUtil.isNotEmpty(detail_name)){
			sql+=" and detail_name=? ";
			params.add(detail_name);
		}
		
		List<SysDictDetail> list = SysDictDetail.dao.find(sql, params.toArray());
		return list;
	}
	public static List<SysDictDetail> getDictDetailByOrderSpeed(String orderspeed){
		String sql="select * from sys_dict_detail where del_flag=0";
		sql+=" and dict_type='orderspeed' ";
		sql+=" and detail_name=? ";
		List<Object> params=new ArrayList<Object>();
		params.add(orderspeed);
		List<SysDictDetail> list = SysDictDetail.dao.find(sql, params.toArray());
		return list;
	}



	

}
