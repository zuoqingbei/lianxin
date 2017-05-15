
package com.ulab.model;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName = "t_b_order_data",pkName="id")
public class OrderModel extends Model<OrderModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final OrderModel dao = new OrderModel();
	private static final String ALL_NAME="总";
	private static final String JISHI_NAME="-及时";
	/**
	 * 
	 * @time   2017年5月13日 下午1:00:49
	 * @author zuoqb
	 * @todo   按照产线统计某年各月份详细订单及时率
	 * @param  @param date
	 * @param  @param plCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderMonthRateForProduct(String date,String plCode,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select o.*,case o.num  when 1 then '100' else trim (to_char(o.num * 100, '00.00')) end as rate");
		sb.append(" from ");
		sb.append(" t_b_order_data o");
		sb.append(" left join t_b_dictionary d on o.product_line_code = d . id ");
		sb.append(" where o.type = 1 and desc_name='及时率' and o.name like '%"+date+"%' ");
		sb.append(" and product_line_code='"+plCode+"'");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and o.lab_code='"+labTypeCode+"' ");
		}
		sb.append(" and d.del_flag=0 and o.del_flag = 0 order by d.order_no");
		return Db.find(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月13日 上午11:57:34
	 * @author zuoqb
	 * @todo   按照产线统计某年整体订单及时率
	 * @param  @param date
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderYearRateForProduct(String date,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.*,b.js_count,case t.all_count when 0 then '0' else to_char(b.js_count/t.all_count*100,'00.00') end as  rate from");
		sb.append(" ( ");
		sb.append(joinSql(date, ALL_NAME,labTypeCode));
		sb.append(" )t ");
		sb.append(" join ");
		sb.append(" ( ");
		sb.append(joinSql(date, JISHI_NAME,labTypeCode));
		sb.append(" )b ");
		sb.append(" on t.product_line_code=b.product_line_code ");
		return Db.find(sb.toString());
	}
	public String joinSql(String date,String desc,String labTypeCode){
		String sql="";
		if(JISHI_NAME.equals(desc)){
			sql+=" select sum (num) as js_count,";
		}else{
			sql+=" select sum (num) as all_count,";
		}
		sql+=" product_line_name as name,product_line_code,substr(o.name,0,4) as key from ";
		sql+=" t_b_order_data o left join t_b_dictionary d on d.id=o.product_line_code ";
		sql+=" where o.desc_name like '%"+desc+"%' and o.name like '%"+date+"%' and o.type = 1 ";
		if(StringUtils.isNotBlank(labTypeCode)){
			sql+=" and o.lab_code='"+labTypeCode+"' ";
		}
		sql+=" group by 	o.product_line_code,o.product_line_name, d.order_no,substr(o.name,0,4)order by d.order_no";
		return sql;
	}
	/**
	 * 
	 * @time   2017年5月13日 上午11:36:53
	 * @author zuoqb
	 * @todo   查询某年总的订单及时率
	 * @param  @param date
	 * @param  @return
	 * @return_type   Record
	 */
	public Record findOrderRate(String date,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.*,b.*,to_char(b.js_num/t.all_count*100,'00.00') as rate from  ");
		sb.append(" (select sum (num) as all_count from t_b_order_data o where ");
		sb.append(" o.desc_name like '%"+ALL_NAME+"%' and o.name like '%"+date+"%' and o.type = 1 ");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and o.lab_code='"+labTypeCode+"' ");
		}
		sb.append(" )t ");
		sb.append(" join  ");
		sb.append(" (select sum (num) as js_num from t_b_order_data o  where	o.desc_name like '%"+JISHI_NAME+"%'  ");
		sb.append(" and o.name like '%"+date+"%' and o.type = 1 ");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and o.lab_code='"+labTypeCode+"' ");
		}
		sb.append(" )b on 1=1");
		return Db.findFirst(sb.toString());
	}
	
	
	/**
	 * 
	 * @time   2017年5月13日 下午1:00:49
	 * @author zuoqb
	 * @todo   按照产线统计某年各月份详细订单类别
	 * @param  @param date
	 * @param  @param plCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderMonthTypeForProduct(String plCode,String labTypeCode,String desName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select o.* ");
		sb.append(" from ");
		sb.append(" t_b_order_data o");
		sb.append(" left join t_b_dictionary d on o.product_line_code = d . id ");
		sb.append(" where o.type = 2 and desc_name='"+desName+"'  ");
		sb.append(" and product_line_code='"+plCode+"'");
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and o.lab_code='"+labTypeCode+"' ");
		}
		sb.append(" and d.del_flag=0 and o.del_flag = 0 order by d.order_no ");
		return Db.find(sb.toString());
	}
	
	/**
	 * 
	 * @time   2017年5月14日 上午8:54:14
	 * @author zuoqb
	 * @todo   合格率 具体到产线
	 * @param  @param plCode：产线
	 * @param  @param labTypeCode：实验室
	 * @param  @param desName：描述：整机 模块
	 * @param  @param name：名称 - 总 合格 合格率
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderPassForPro(String plCode,String labTypeCode,String desName,String name){
		StringBuffer sb=new StringBuffer();
		sb.append(" select o.*,case o.num  when 1 then '100' else to_char(o.num*100,'00.00') end as rate from t_b_order_data o left join t_b_dictionary d on d.id=o.product_line_code  ");
		sb.append(" where o.del_flag=0 and o.type=3 and desc_name='"+desName+"'  ");
		if(StringUtils.isNotBlank(plCode)){
			sb.append(" and o.product_line_code='"+plCode+"' ");
		}
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and o.lab_code='"+labTypeCode+"' ");
		}
		sb.append(" and d.del_flag=0 and o.name='"+name+"' order by d.order_no ");
		return Db.find(sb.toString());
	}
	/**
	 * 
	 * @time   2017年5月14日 上午9:11:17
	 * @author zuoqb
	 * @todo   合格率 整体统计
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @param desName
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public Record findOrderPassForAll(String plCode,String labTypeCode,String desName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select to_char(b.pass_count/a.all_count*100,'00.00') as rate,a.*,b.* from  ");
		sb.append(" (select sum(num) as all_count from t_b_order_data where del_flag=0 and type=3 and name='总'  ");
		if(StringUtils.isNotBlank(desName)){
			sb.append(" and desc_name='"+desName+"'  ");
		}
		if(StringUtils.isNotBlank(plCode)){
			sb.append(" and product_line_code='"+plCode+"' ");
		}
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and lab_code='"+labTypeCode+"' ");
		}
		sb.append(" ) a ");
		sb.append(" join ");
		sb.append(" (select sum(num) as pass_count from t_b_order_data where del_flag=0 and type=3 and name='合格'  ");
		if(StringUtils.isNotBlank(desName)){
			sb.append(" and desc_name='"+desName+"'  ");
		}
		if(StringUtils.isNotBlank(plCode)){
			sb.append(" and product_line_code='"+plCode+"' ");
		}
		if(StringUtils.isNotBlank(labTypeCode)){
			sb.append(" and lab_code='"+labTypeCode+"' ");
		}
		sb.append(" )b ");
		sb.append(" on 1=1");
		return Db.findFirst(sb.toString());
	}
	
	/**
	 * 
	 * @time   2017年5月14日 上午10:02:24
	 * @author zuoqb
	 * @todo   模块整机订单及时率 
	 * @param  @param date
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderYearRateForTab1(String date,String labTypeCode,String descName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.*,b.js_count,case t.all_count when 0 then '0' else to_char(b.js_count/t.all_count*100,'00.00') end as  rate from");
		sb.append(" ( ");
		sb.append(joinSqlTab1(date, descName+"-"+ALL_NAME,labTypeCode));
		sb.append(" )t ");
		sb.append(" join ");
		sb.append(" ( ");
		sb.append(joinSqlTab1(date, descName+JISHI_NAME,labTypeCode));
		sb.append(" )b ");
		sb.append(" on t.name=b.name ");
		return Db.find(sb.toString());
	}
	public String joinSqlTab1(String date,String desc,String labTypeCode){
		String sql="";
		if(desc.indexOf(JISHI_NAME)!=-1){
			sql+=" select sum (num) as js_count,";
		}else{
			sql+=" select sum (num) as all_count,";
		}
		sql+=" o.name from ";
		sql+=" t_b_order_data o  ";
		sql+=" where o.desc_name like '%"+desc+"%' and o.name like '%"+date+"%' and o.type = 1 ";
		if(StringUtils.isNotBlank(labTypeCode)){
			sql+=" and o.lab_code='"+labTypeCode+"' ";
		}
		sql+=" group by  o. name order by o.name";
		return sql;
	}
}
