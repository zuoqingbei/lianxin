package com.hailian.modules.credit.reportmanager.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hailian.util.StrUtils;
import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.admin.ordermanager.model.CreditReportModuleParentNodesDict;
import com.jfinal.plugin.activerecord.Page;
@ModelBind(table = "credit_report_module_conf")
public class CreditReportModuleConf extends BaseProjectModel<CreditReportModuleConf>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final long DefaultModule = -1L;
	private static final long TabFixed = -2L;
	public static final CreditReportModuleConf dao = new CreditReportModuleConf();//名字都叫dao，统一命名
	public Page<CreditReportModuleConf> findTemps(CreditReportModuleConf temp, Paginator pageinator,
			BaseProjectController c) {
		StringBuffer sql = new StringBuffer();
		//报告类型
		String report = temp.getStr("report_type");
		//模板名称
		String temp_name = temp.getStr("temp_name");
		//创建者
		String create = temp.getStr("create");
		List<Object> params = new ArrayList<Object>();
		sql.append(" from credit_report_module_conf t ");
		sql.append(" left join credit_report_type c on c.id=t.report_type ");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_user s1 on s1.userid=t.update_by ");
		sql.append(" left join credit_report_module_conf t1 on t1.id=t.parent_temp ");
		sql.append(" where t.del_flag=0 and t.parent_temp is not null and t.parent_temp !='' and 1=1 ");
		if (!c.isAdmin(c.getSessionUser())) {
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());
		}
		if (StringUtils.isNotBlank(report)) {
			sql.append(" and t.report_type=?");
			params.add(report);
		}
		if (StringUtils.isNotBlank(temp_name)) {
			sql.append(" and t.temp_name like concat('%',?,'%')");
			params.add(temp_name);
		}
		if (StringUtils.isNotBlank(create)) {
			sql.append(" and t.create_by=?");
			params.add(create);
		}
		Page<CreditReportModuleConf> page = dao
				.paginate(
						pageinator,
						"select t.*,c.name as reportName,s.username as createName,"
						+ "s1.username as updateName,t1.temp_name as parentTempName ",
						sql.toString(), params.toArray());
		return page;
	}
	public List<CreditReportModuleConf> findSon(String parent_temp, String report) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report_type=?  order by t.sort,t.id";
		return dao.find(sql, parent_temp,report);
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+parent_temp+report,sql,parent_temp,report);
	}
	public List<CreditReportModuleConf> findSon2(String parent_temp, String report,String type) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.parent_temp=? and t.report_type=? ";
		if (type.equals("1")) {//查填报
			sql+=" and t.is_enter='0'";
			
		}else if (type.equals("2")) {//查详情
			sql+=" and t.is_detail='0'";
		}else if(type.equals("3")){//查质检的
			sql+=" and t.is_quality='0'";
		}
		sql+=" order by t.sort,t.id";
		return dao.find(sql, parent_temp,report);
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+parent_temp+report,sql,parent_temp,report);
	}
	
	public List<CreditReportModuleConf> findReportNodes(String report) {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.report_type=? order by t.sort,t.id";
		return dao.find(sql,report);
	}
	public List<CreditReportModuleConf> getAllTemp() {
		
		 return dao.find("select t.* from credit_report_module_conf t where t.del_flag='0'  ");
	}
	
  public List<CreditReportModuleConf> findByReport(String report) {
		
		
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.node_level=1 and t.report_type=? and t.small_module_type not in(-1,-2) order by t.sort,t.id";
		return dao.find(sql,report);
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+report,sql,report);
	}
   /**
    * 
   * @Description: 合并详情与填报的页面获取结构
   * @date 2018年12月19日 下午5:57:18
   * @author: lxy
   * @version V1.0
   * @return
    */
    public List<CreditReportModuleConf> findByType(String report,String type) {
	
			String sql="select t.* from credit_report_module_conf t where"
					+ " t.del_flag=0 and t.node_level=1 and t.report_type=? and t.small_module_type not in(-1,-2)";
			if (type.equals("1")) {//查填报
				sql+=" and t.is_enter='0'";
				
			}else if (type.equals("2")) {//查详情
				sql+=" and t.is_detail='0'";
			}else if(type.equals("3")){//查质检的
				sql+=" and t.is_quality='0'";
			}
			sql+=" order by t.sort,t.id";
			return dao.find(sql,report);
      }
	public List<CreditReportModuleConf> findReportType() {
		String sql="select t.* from credit_report_module_conf t where"
				+ " t.del_flag=0 and t.parent_temp=-9999999 order by sort,id";
		return dao.find(sql);
	}
	public CreditReportModuleConf findReportModuleById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from credit_report_module_conf t ");
		sql.append(" left join credit_report_type c on c.id=t.report_type ");
		sql.append(" left join sys_user s on s.userid=t.create_by ");
		sql.append(" left join sys_user s1 on s1.userid=t.update_by ");
		sql.append(" left join credit_report_module_conf t1 on t1.id=t.parent_temp ");
		sql.append(" where t.del_flag=0 and 1=1 and t.id=? order by sort,id");
		return dao.findFirst("select t.*,c.name as reportName,s.username as createName,s1.username as updateName"
				+ ",t1.temp_name as parentTempName "+sql.toString(),id);
	}
	/**
	 * Author:lzg
	 * 默认模块
	 * 依据small_module_type=-1
	 * @return
	 */
	public List<CreditReportModuleConf> getDefaultModule(String reportType) {
		List<Object> params = new ArrayList<>();
		String sql = " select a.*  from credit_report_module_conf a "
				+ " where parent_temp=(SELECT id from credit_report_module_conf where small_module_type=? and del_flag=0  and report_type=? ) "
				+ " or  small_module_type=? "
				+ " and report_type=? and del_flag=0 order by sort,id ";
		params.add(DefaultModule);
		params.add(reportType);
		params.add(DefaultModule);
		params.add(reportType);
		return dao.find(sql,params.toArray());
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+DefaultModule+reportType+DefaultModule+reportType,sql,params.toArray());
	}
	/**
	 * 
	* @Description: TODO
	* @date 2018年12月21日 上午10:32:39
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public List<CreditReportModuleConf> getDefaultModule2(String reportType,String type) {
		List<Object> params = new ArrayList<>();
		String sql = " select a.*  from credit_report_module_conf a "
				+ " where parent_temp=(SELECT id from credit_report_module_conf where small_module_type=? and del_flag=0  and report_type=? ) "
				+ " or  small_module_type=? "
				+ " and report_type=? ";
		if (type.equals("1")) {//查填报
			sql+=" and is_enter=?";
			
		}else if (type.equals("2")) {//查详情
			sql+=" and is_detail=?";
		}else if(type.equals("3")){//查质检的
			sql+=" and is_quality=?";
		}		
			sql	+= "and del_flag=0 order by sort,id ";
		params.add(DefaultModule);
		params.add(reportType);
		params.add(DefaultModule);
		params.add(reportType);
		params.add("0");
		return dao.find(sql,params.toArray());
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+DefaultModule+reportType+DefaultModule+reportType,sql,params.toArray());
	}
	
	
	
	/**
	 * Author:lzg
	 * 获取带锚点模板
	 * 依据small_module_type=-2
	 * @return
	 */
	public List<CreditReportModuleConf> getTabFixed(String reportType) {
		List<Object> params = new ArrayList<>();
		String sql = "select a.*  from credit_report_module_conf a where a.small_module_type=? and report_type=? and del_flag=0  order by sort,id  ";
		params.add(TabFixed);
		params.add(reportType);
		return dao.find(sql,params.toArray());
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+TabFixed+reportType,sql,params.toArray());
	}
	
	
    /**
     * 
    * @Description: 填报与详情合并获取带锚点的标题
    * @date 2018年12月21日 上午10:22:31
    * @author: lxy
    * @version V1.0
    * @return
     */
	public List<CreditReportModuleConf> getTabFixed2(String reportType,String type) {
		List<Object> params = new ArrayList<>();
		String sql = "select a.*  from credit_report_module_conf a where a.small_module_type=? and report_type=? ";
		if (type.equals("1")) {//查填报
			sql+=" and is_enter=?";
			
		}else if (type.equals("2")) {//查详情
			sql+=" and is_detail=?";
		}else if(type.equals("3")){//查质检的
			sql+=" and is_quality=?";
		}
		sql+=" and del_flag=0  order by sort,id  ";
		params.add(TabFixed);
		params.add(reportType);
		params.add("0");
		return dao.find(sql,params.toArray());
        //缓存2个小时
        //return dao.findByCache("reportModuleConf","credit_report_module_conf"+TabFixed+reportType,sql,params.toArray());
	}

    /**
     * 分页查询字段
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @param params
     * @return
     */
    public Page<CreditReportModuleConf> findSon(int pageNumber,int pageSize,String keyword ,String orderBy,List<Object> params) {
        StringBuffer from = new StringBuffer("select t.* ") ;
        StringBuffer where = new StringBuffer(" from credit_report_module_conf t where t.del_flag = 0 ");
        where.append(" and t.parent_temp=? and t.report_type=? ");
        if(StringUtils.isNotEmpty(keyword)){
            where.append(" and t.temp_name like concat('%',?,'%')");
            params.add(keyword);
        }
        //排序
        if (StrUtils.isEmpty(orderBy)) {
            where.append(" order by t.sort,t.id");
        } else {
            where.append(" order by ").append(orderBy);
        }
        Page<CreditReportModuleConf> page = dao.paginate(new Paginator(pageNumber, pageSize), from.toString(),where.toString(), params.toArray());
        return page;
    }
    
    public Page<CreditReportModuleConf> page(int pageNumber,int pageSize,String keyword ,String orderBy,List<Object> params) {
        StringBuffer from = new StringBuffer("select t.*,d.detail_name As  smallModuleType,s.detail_name As wordTableTypeName, e.detail_name As isMergerNext") ;
        StringBuffer where = new StringBuffer(" from credit_report_module_conf t ");
        where.append(" left join sys_dict_detail d on d.detail_code=t.small_module_type and d.dict_type='small_module_type' ");
        where.append(" left join sys_dict_detail e on e.detail_code=t.is_merger_next and e.dict_type='boolean'  ");
        where.append(" left join sys_dict_detail s on s.detail_code=t.word_table_type  and s.dict_type='word_table_type' ");
        where.append(" where t.del_flag = 0 and t.report_type=? "
        		+ "and t.parent_temp='-9999999' "
        		 );
        if(keyword!=null&&!StrUtils.isEmpty(keyword.trim())) {
       	 where.append(" and t.temp_name like concat('%','"+keyword+"','%')  ");
       }
        /*if(StringUtils.isNotEmpty(keyword)){
            where.append(" and t.temp_name like concat('%',?,'%')");
            params.add(keyword);
        }*/
        //排序
        if (StrUtils.isEmpty(orderBy)) {
            where.append(" order by t.sort,t.id");
        } else {
            where.append(" order by ").append(orderBy);
        }
        Page<CreditReportModuleConf> page = dao.paginate(new Paginator(pageNumber, pageSize), from.toString(),where.toString(), params.toArray());
        return page;
    }
}
