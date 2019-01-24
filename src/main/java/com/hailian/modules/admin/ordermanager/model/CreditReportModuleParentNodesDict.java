package com.hailian.modules.admin.ordermanager.model;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleColumn;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

@ModelBind(table = "credit_report_module_parent_nodes_dict")
//此标签用于模型与数据库表的连接
public class CreditReportModuleParentNodesDict extends BaseProjectModel<CreditReportModuleParentNodesDict> {
	private static final long serialVersionUID = 1L;
	public static final CreditReportModuleParentNodesDict dao = new CreditReportModuleParentNodesDict();//名字都叫dao，统一命名
	
    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @param orderBy
     * @param params
     * @return
     */
    public Page<CreditReportModuleParentNodesDict> page(int pageNumber,int pageSize,String keyword ,String orderBy,List<Object> params) {
        StringBuffer from = new StringBuffer("select t.*,d.detail_name As  smallModuleType,d.detail_code As detailCode") ;
        StringBuffer where = new StringBuffer(" from credit_report_module_parent_nodes_dict t left join (select detail_name,detail_code from sys_dict_detail where 	dict_type ='small_module_type' and del_flag=0) d on t.small_module_type=d.detail_code where t.del_flag = 0 ");
         
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
        Page<CreditReportModuleParentNodesDict> page = dao.paginate(new Paginator(pageNumber, pageSize), from.toString(),where.toString(), params.toArray());
        return page;
    }

}