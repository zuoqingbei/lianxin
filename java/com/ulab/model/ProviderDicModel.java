
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
 * @todo   模块商已经产品型号字典数据
 */
@TableBind(tableName = "t_b_provider_dic",pkName="id")
public class ProviderDicModel extends Model<ProviderDicModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final ProviderDicModel dao = new ProviderDicModel();
	/**
	 * 
	 * @time   2017年5月18日 下午4:10:21
	 * @author zuoqb
	 * @todo   查询全部供应商及产品数据
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findProviderDic(){
		List<Record>  list=findProviderDicByPid("0");
		for(Record r:list){
			r.set("children", findProviderDicByPid(r.get("id")+""));
		}
		return list;
	}
	public List<Record> findProviderDicByPid(String parentId){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from t_b_provider_dic where parent_id='"+parentId+"' order by order_no   ");
		return Db.find(sb.toString());
	}
}
