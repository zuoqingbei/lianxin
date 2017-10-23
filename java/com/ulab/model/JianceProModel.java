
package com.ulab.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:39:11
 * @author zuoqb
 * @todo   产线对应检测属性
 */
@TableBind(tableName = "t_b_xh_property",pkName="id")
public class JianceProModel extends Model<JianceProModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final JianceProModel dao = new JianceProModel();
	
	public Record findProByFiled(String filed){
		String sql="select * from t_b_xh_property where GW_ID='"+filed+"'";
		return Db.findFirst(sql);
	}
	
}
