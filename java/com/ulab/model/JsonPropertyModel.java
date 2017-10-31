package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@TableBind(tableName = "t_b_json_property", pkName = "id")
public class JsonPropertyModel extends Model<JsonPropertyModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3229651791842824539L;
	public static final JsonPropertyModel dao = new JsonPropertyModel();

	public List<Record> findJsonProperty(String dataCenterId) {
		String sql = "select * from t_b_json_property where data_center_id='" + dataCenterId
				+ "' and parent_id=-1 and del_flag=0 order by order_num ";
		List<Record> list = Db.find(sql);
		for (Record r : list) {
			String cSql = "select * from t_b_json_property where parent_id='" + r.get("id")
					+ "' and del_flag=0 order by order_num ";
			r.set("children", Db.find(cSql));
		}
		return list;
	}
}
