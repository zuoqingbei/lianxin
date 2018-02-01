
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.core.Constants;
@TableBind(tableName = "t_b_lab_map",pkName="id")
public class LabMapModel extends Model<LabMapModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final LabMapModel dao = new LabMapModel();
	
	/**
	 * 
	 * @time   2017年4月18日 下午5:16:53
	 * @author zuoqb
	 * @todo   展示在平面图上的数据
	 * @param  @param productCode
	 * @param  @param labType
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> labShowFlatMap(String sqlWhere){
		String sWhere=" del_flag="+Constants.DEL_FALG;
		sWhere+=sqlWhere;
    	String sql="";
    	sql+=" select m.lng,m.lat,m.short_name as title,m.name,lab.num,'' as datetime,m.location,m.lab_type,lab.country,c.img_content,m.data_center_id  from t_b_lab_map m left join t_b_data_center c on c.id=m.data_center_id inner join (";
    	sql+=" select belong_gl_code,count(1) as num,country from t_b_lab_info lab where "+sWhere+" group by lab.belong_gl_code,country ";
    	sql+=" ) lab on lab.belong_gl_code=m.id where m.del_flag=0 and m.show_in_map=1 order by m.id ";
		return Db.find(sql);
	}
	public List<Record> labShowFlatMap2(String sqlWhere){
		String sWhere=" del_flag="+Constants.DEL_FALG;
		sWhere+=sqlWhere;
    	String sql="";
    	sql+=" select distinct name as title,lab_type_name as lab_type,city as location  from t_b_lab_info where del_flag=0 and link_status=1";
		return Db.find(sql);
	}
	/**
	 * 
	 * @time   2018年1月30日 下午4:07:58
	 * @author zuoqb
	 * @todo   分页查询平面地图上实验室数据
	 * @param  @param pageSize
	 * @param  @param pageNumber
	 * @param  @param c
	 * @param  @return
	 * @return_type   Page<LabMapModel>
	 */
	public Page<LabMapModel> pager(Integer pageSize,Integer pageNumber,BaseController c ){
		String fromSql=" from t_b_lab_map m left join t_b_data_center c on m.data_center_id=c.id where  m.del_flag='"+Constants.DEL_FALG+"' ";
		if(StringUtils.isNotBlank(c.getPara("name"))){
			fromSql+=" and (m.name like '%"+c.getPara("name")+"%' or m.short_name like '%"+c.getPara("name")+"%' "
					+ "or m.location like '%"+c.getPara("name")+"%') ";
		}
		if(StringUtils.isNotBlank(c.getPara("labType"))){
			fromSql+=" and m.lab_type='"+c.getPara("labType")+"' ";
		}
		fromSql+=" order by m.create_date desc ";
		Page<LabMapModel> pager = LabMapModel.dao.paginate(pageNumber, pageSize,"select m.*,c.center_name as centername",fromSql);
		return pager;
	}
}
