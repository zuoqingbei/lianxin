package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年10月19日 下午2:22:42
 * @author zuoqb
 * @todo   数据中心json文件读取属性
 */
@TableBind(tableName = "t_b_json_property",pkName="id")
public class JsonPropertyModel extends Model<CommunistModel>{
	private static final long serialVersionUID = 1L;
	public static final JsonPropertyModel dao = new JsonPropertyModel();
	/**
	 * 
	 * @time   2017年10月19日 上午5:59:18
	 * @author zuoqb
	 * @todo   数据中心json文件读取属性
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findJsonProperty(String dataCenterId){
		String sql=" select * from t_b_json_property where data_center_id='"+dataCenterId+"' and parent_id=-1 and del_flag=0 order by order_num   ";
		List<Record> list=Db.find(sql);
		for(Record center:list){
			String sql1=" select * from t_b_json_property where parent_id='"+center.get("id")+"'  and del_flag=0 order by order_num   ";
			center.set("children", Db.find(sql1));
		}
		return list;
		
	}
	
}
