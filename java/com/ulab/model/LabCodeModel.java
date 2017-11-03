
package com.ulab.model;
/**
 * 实验室编码表
 */
import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName = "t_b_lab_code",pkName="id")
public class LabCodeModel extends Model<LabCodeModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final LabCodeModel dao = new LabCodeModel();
	/**
	 * 
	 * @time   2017年11月3日 上午9:59:05
	 * @author zuoqb
	 * @todo   根据数据中心（三级ID）获取实验室信息
	 * @param  @param dataCenterId
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findLabByDataCenterId(String dataCenterId){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from t_b_lab_code where data_center_id='"+dataCenterId+"' and del_flag=0 order by order_num  ");
		return Db.find(sb.toString());
	}
}
