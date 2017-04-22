
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName = "t_b_lab_carry",pkName="id")
public class LabCarryModel extends Model<LabCarryModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final LabCarryModel dao = new LabCarryModel();
	/**
	 * 
	 * @time   2017年4月21日 下午12:04:00
	 * @author zuoqb
	 * @todo   根据实验室编码查询对应开展实验类型
	 * @param  @param labCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findCarryByCode(String labCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from t_b_lab_carry where lab_code='"+labCode+"' and del_flag=0  ");
		return Db.find(sb.toString());
	}
	/**
	 * 
	 * @time   2017年4月21日 下午12:10:37
	 * @author zuoqb
	 * @todo   开展类型数量统计
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> labCarryNumStatis(String sqlWhere){
		StringBuffer sb=new StringBuffer();
		sb.append(" select d.name as name, nvl(count(1),0) as count from   ");
		sb.append(" t_b_lab_carry c left join t_b_lab_info lab on lab.code=c.lab_code left join t_b_dictionary d on c.carry_code = d.id");
		sb.append("  where c.del_flag=0  group by d.name,d .order_no order by d.order_no ");
		return Db.find(sb.toString());
	}
}
