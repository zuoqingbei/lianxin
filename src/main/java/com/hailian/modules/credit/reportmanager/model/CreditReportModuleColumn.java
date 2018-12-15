package com.hailian.modules.credit.reportmanager.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

@ModelBind(table = "credit_report_module_column")
public class CreditReportModuleColumn extends BaseProjectModel<CreditReportModuleColumn>{

    public static final CreditReportModuleColumn dao = new CreditReportModuleColumn();//名字都叫dao，统一命名

    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param keyword
     * @param orderBy
     * @param params
     * @return
     */
    public Page<CreditReportModuleColumn> page(int pageNumber,int pageSize,String keyword ,String orderBy,List<Object> params) {
        StringBuffer from = new StringBuffer("select t.* ") ;
        StringBuffer where = new StringBuffer(" from credit_report_module_column t where t.del_flag = 0 ");
        where.append(" and t.table_id= ? ");
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
        Page<CreditReportModuleColumn> page = dao.paginate(new Paginator(pageNumber, pageSize), from.toString(),where.toString(), params.toArray());
        return page;
    }

}
