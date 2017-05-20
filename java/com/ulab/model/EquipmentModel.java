
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月20日09:50:143
 * @author zuoqb
 * @todo   设备数据
 */
@TableBind(tableName = "t_b_equipment_data",pkName="id")
public class EquipmentModel extends Model<EquipmentModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final EquipmentModel dao = new EquipmentModel();
	/**
	 * 
	 * @time   2017年5月20日 上午9:50:21
	 * @author zuoqb
	 * @todo  总状态 设施状态统计
	 * @param  @param dataType 1:当前2：同比
	 * @param  @param labName:实验室名称 空表示全部
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> equipmentTotal(String dataType,String labName){
		String sql="select case type when '0' then '实验室在线率' when '1' then '设备完好率' else '设备利用率' end  as name,";
		sql+=" type,to_char(sum(num)/7*100,'00.0') as rate from ";
		sql+="  t_b_equipment_data where 1=1 and data_type='"+dataType+"' ";
		if(StringUtils.isNotBlank(labName)){
			sql+=" and lab_name='"+labName+"' ";
		}else{
			sql+=" and lab_name is null ";
		}
		sql+=" group by type order by type";
		return Db.find(sql);
	}
	/**
	 * 
	 * @time   2017年5月20日 上午10:25:31
	 * @author zuoqb
	 * @todo   设备状态统计 到产线 for tab2
	 * @param  @param type 类型  0：实验室在线率 1：设备完好率 2：设备利用率 
	 * @param  @param labName
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> equipmentStatisForPl(String type,String labName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.product_name,to_char(a.num*100,'00.0') as dq,to_char(b.num*100,'00.0') as  tb ,to_char((a.num*100-b.num*100),'0.0') as change_num from ( ");
		sb.append(" select e.* from t_b_equipment_data e left join t_b_dictionary d on e.product_code=d.id ");
		sb.append(" where e.data_type=1   and e.type='"+type+"' ");
		if(StringUtils.isNotBlank(labName)){
			sb.append(" and e.lab_name='"+labName+"' ");
		}else{
			sb.append(" and e.lab_name is null ");
		}
		sb.append(" order by d.order_no )a ");
		sb.append(" join  ");
		//同比
		sb.append(" (select e.* from t_b_equipment_data e left join t_b_dictionary d on e.product_code=d.id ");
		sb.append("  where e.data_type=2   and e.type='"+type+"' ");
		if(StringUtils.isNotBlank(labName)){
			sb.append(" and lab_name='"+labName+"' ");
		}else{
			sb.append(" and lab_name is null ");
		}
		sb.append(" order by d.order_no )b on a.product_code=b.product_code ");
		return Db.find(sb.toString());
	}
	
}
