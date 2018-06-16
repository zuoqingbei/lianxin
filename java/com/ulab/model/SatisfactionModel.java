
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
 * @todo   用户满意度
 */
@TableBind(tableName = "t_b_satisfaction",pkName="id")
public class SatisfactionModel extends Model<SatisfactionModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final SatisfactionModel dao = new SatisfactionModel();

	/**
	 * 
	 * @time   2017年5月20日 下午12:34:15
	 * @author zuoqb
	 * @todo   满意度 到月份数据统计tab1  centerTab
	 * @param  @param startDate
	 * @param  @param endDate
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> satisfactionStatisForMonth(String startDate,String endDate,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.name,to_char(sum(num)/7,'00.0') as rate from t_b_satisfaction s ");
		sb.append(" where  s.type=1  ");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and s.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append(" and s.lab_name is null ");
		}
		sb.append(" and to_date(trim(s.name),'yyyy-mm')  between to_date('"+startDate+"','yyyy-mm')  and to_date('"+endDate+"','yyyy-mm')  ");
		sb.append(" group by s.name order by s.name ");
		return Db.find(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月20日 下午12:46:59
	 * @author zuoqb
	 * @todo   同期 环比满意度占比统计
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   Record
	 */
	public Record satisfactionChange(String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select  a.*,b.*,to_char(a.tq-b.hb,'0.0') as change_num from ( ");
		sb.append(" select to_char(sum(num)/7,'00.0') as tq from t_b_satisfaction s  ");
		sb.append("  where  s.type=0 and name='2017年'");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and s.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append(" and s.lab_name is null ");
		}
		sb.append("  group by s.name )a join( ");
		sb.append(" select to_char(sum(num)/7,'00.0') as hb from t_b_satisfaction s  ");
		sb.append("  where  s.type=0 and name='2016年'  ");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and s.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append(" and s.lab_name is null ");
		}
		sb.append(" group by s.name) b on 1=1 ");
		return Db.findFirst(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月20日 下午1:49:29
	 * @author zuoqb
	 * @todo   tab3 某一年分 到产线满意度统计
	 * @param  @param year
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> satisfactionStatisForYear(String year,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.*,to_char(s.num,'00.0') as rate from t_b_satisfaction s left join t_b_dictionary d  ");
		sb.append(" on d.id=s.product_code where s.type=0 and s.name='"+year+"'  ");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and s.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append(" and s.lab_name is null  ");
		}
		sb.append(" order by d.order_no ");
		return Db.find(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月20日 下午2:22:25
	 * @author zuoqb
	 * @todo   满意度到产线 到月数据统计
	 * @param  @param startDate
	 * @param  @param endDate
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> productLineAndMonth(String startDate,String endDate,String plCode,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select s.*,to_char(s.num,'00.0') as rate,s.product_name as product_line_name from t_b_satisfaction s left join t_b_dictionary d  ");
		sb.append("  on d.id=s.PRODUCT_CODE WHERE s.type=1  ");
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and s.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and s.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append("  and s.lab_name is null ");
		}
		sb.append(" and to_date(trim(s.name),'yyyy-mm')  between to_date('"+startDate+"','yyyy-mm')  and to_date('"+endDate+"','yyyy-mm')  ");
		sb.append("  order by s.name ");
		return Db.find(sb.toString());
	}
	
	
}
  