
package com.ulab.model;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName = "t_b_jsl",pkName="id")
public class JSLModel extends Model<JSLModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final JSLModel dao = new JSLModel();

	/**
	 * 
	 * @time   2017年5月18日 上午11:38:23
	 * @author zuoqb
	 * @todo   统计全部订单及时率-到月份
	 * @param  @param startDate
	 * @param  @param endDate
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findOrderMonthRateForAll(String startDate,String endDate,String labName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select	* ");
		sb.append(" from t_b_jsl o where 1=1 ");
		sb.append(" and to_date(o.name,'yyyy-mm')  between to_date('"+startDate+"','yyyy-mm')  and to_date('"+endDate+"','yyyy-mm')  ");
		if(StringUtils.isNotBlank(labName)){
			sb.append(" and o.lab_name='"+labName+"'");
			sb.append(" and o.DESC_NAME='及时率' ");
		}
		sb.append("  order by o.name ");
		try {
			return Db.find(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
