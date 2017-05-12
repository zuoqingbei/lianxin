
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.Constants;
@TableBind(tableName = "t_b_date_result",pkName="id")
public class LabDataResultModel extends Model<LabDataResultModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final LabDataResultModel dao = new LabDataResultModel();
	/**
	 * 
	 * @time   2017年4月23日 上午11:57:30
	 * @author zuoqb
	 * @todo   根据类型统计数据结果 
	 * @param  @param type 数据结果类型。0:标准状态 1：能力状态  2:起草数
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> dataResultBType(Object type){
		StringBuffer sb=new StringBuffer();
		sb.append(" select type_name as name,sum(num) as  count from t_b_date_result t  WHERE  t.del_flag="+Constants.DEL_FALG+" and t.type='"+type+"' group by t.type_name ");
		return Db.find(sb.toString());
	}
	
	/**
	 * 
	 * @time   2017年4月23日 上午11:57:30
	 * @author zuoqb
	 * @todo   根据类型统计数量
	 * @param  @param type 0:标准状态 1：能力状态  2:起草数
	 * @param  @return
	 */
	public Integer resultNumStatisticBType(Object type){
		StringBuffer sb=new StringBuffer();
		sb.append(" select nvl(sum(num),0) as count from t_b_date_result t where t.del_flag="+Constants.DEL_FALG+" and t.type='"+type+"'");
		return Integer.valueOf(Db.findFirst(sb.toString()).getBigDecimal("count").toString());
	}
	/**
	 * 
	 * @time   2017年4月23日 下午12:02:41
	 * @author zuoqb
	 * @todo   标准状态数据统计
	 * @param  @return
	 * @return_type   Record
	 */
	public Record standardStatus(){
		Record record=new Record();
		//统计修订版数据
		record.set("reviseNum", LabDataResultModel.dao.resultNumStatisticBType(2));
		record.set("reviseData", LabDataResultModel.dao.dataResultBType(2));
		//标准状态
		record.set("standardNum", LabDataResultModel.dao.resultNumStatisticBType(0));
		record.set("standardData", LabDataResultModel.dao.dataResultBType(0));
		return record;
	}
	
	/**
	 * 
	 * @time   2017年5月12日 上午7:42:41
	 * @author zuoqb
	 * @todo   根据类型统计数据结果 
	 * @param  @param type 数据结果类型。0:标准状态 1：能力状态  2:起草数
	 * @param  @param typeName 
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> dataResultBTypeAndName(Object type,Object typeName){
		StringBuffer sb=new StringBuffer();
		sb.append(" select product_name as name,num as  count from t_b_date_result t  WHERE  t.del_flag="+Constants.DEL_FALG+" and t.type='"+type+"' and t.type_name='"+typeName+"' ");
		return Db.find(sb.toString());
	}
	
}
