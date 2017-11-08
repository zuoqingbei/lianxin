
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:39:11
 * @author zuoqb
 * @todo   量产一致性保障  SPC分析
 */
@TableBind(tableName = "t_b_xh_property",pkName="id")
public class XbarModel extends Model<XbarModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final XbarModel dao = new XbarModel();
	/**
	 * 
	 * @time   2017年5月19日 上午10:47:34
	 * @author zuoqb
	 * @todo    SPC分析
	 * @param  @param xhName
	 * @param  @param type
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findXbarData(String xhName,String type){
		String sql="select id,xh_code,xh_name,type,num as rate,num,result,order_no as name from t_b_xbar where GW_CODE='"+xhName+"' and type='"+type+"' order by order_no";
		return Db.find(sql);
	}
	
}
