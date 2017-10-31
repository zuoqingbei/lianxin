
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author zuoqb
 * @todo   量产 共产数据
 */
@TableBind(tableName = "t_b_communist_data",pkName="id")
public class CommunistModel extends Model<CommunistModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final CommunistModel dao = new CommunistModel();
	/**
	 * 
	 * @time   2017年5月18日 上午8:46:00
	 * @author zuoqb
	 * @todo   根据时间 实验室 产线统计共产 一致所有数量及比率
	 * @param  @param startDate
	 * @param  @param endDate
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   Record
	 */
	public Record communistGravityStatistic(String startDate,String endDate,String plCode,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append("  select a.*,b.*,to_char(b.yz_num/a.gc_num*100,'00.0') as rate from  ");
		sb.append("  ( ");
		sb.append("  select sum(num) as gc_num,'共产总量' as name from t_b_communist_data c where c.del_flag=0  ");
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and c.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and c.lab_type='"+labTypeCode+"' ");
		}
		sb.append(" and to_date(c.name,'yyyy-mm')  between to_date('"+startDate+"','yyyy-mm')  and to_date('"+endDate+"','yyyy-mm')  ");
		sb.append("  and c.type=1 ");
		sb.append("  )a join (");
		sb.append("  select sum(num) as yz_num,'一致总量' as name from t_b_communist_data c where c.del_flag=0  ");
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and c.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and c.lab_type='"+labTypeCode+"' ");
		}
		sb.append("  and c.type=2 ");
		sb.append("  )b on 1=1 ");
		return Db.findFirst(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月18日 上午9:51:31
	 * @author zuoqb
	 * @todo   根据类型 时间 统计共产 一致个月份数量
	 * @param  @param startDate
	 * @param  @param endDate
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @param type
	 * @param  @return
	 * @return_type   Record
	 */
	public List<Record> communistStatisticForMonth(String startDate,String endDate,String plCode,String labTypeCode,String type){
		StringBuffer sb=new StringBuffer();
		sb.append(" select c.name,sum(c.num) as count from t_b_communist_data c  ");
		sb.append("  where c.del_flag=0 ");
	
		if(StringUtils.isNoneBlank(type)){
			sb.append(" and c.type='"+type+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and c.lab_type='"+labTypeCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and c.lab_type='"+labTypeCode+"' ");
		}
		sb.append(" and to_date(c.name,'yyyy-mm')  between to_date('"+startDate+"','yyyy-mm')  and to_date('"+endDate+"','yyyy-mm')  ");
		sb.append(" group by c.name  order by c.name");
		return Db.find(sb.toString());
	}
}
