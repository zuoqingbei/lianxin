
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:30
 * @author zuoqb
 * @todo   检测原始数据表
 */
@TableBind(tableName = "t_b_jiance",pkName="id")
public class JianCeModel extends Model<JianCeModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final JianCeModel dao = new JianCeModel();
	/**
	 * 
	 * @time   2017年5月18日 下午4:16:47
	 * @author zuoqb
	 * @todo   根据型号查询检测数据
	 * @param  @param parentId
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findProviderDicByPid(String xhCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from  t_b_jiance where GW_CODE='"+xhCode+"' order by to_number(id)  ");
		return Db.find(sb.toString());
	}
	/***
	 * 获取最大值和最小值
	 * @author Tom
	 * @param xhCode
	 * @return
	 */
	public List<Record> findProviderDicMaxMinByPid(String xhCode){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT min(to_number(t.WKQ_NUM)) min,max(to_number(t.WKQ_NUM)) max FROM T_B_JIANCE t WHERE t.GW_CODE='"+xhCode+"'");
		return Db.find(sb.toString());
	}
}
