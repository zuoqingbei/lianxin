
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月20日15:45:09
 * @author zuoqb
 * @todo   问题闭环
 */
@TableBind(tableName = "t_b_question_cosed",pkName="id")
public class QuestionClosedModel extends Model<QuestionClosedModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final QuestionClosedModel dao = new QuestionClosedModel();

	/**
	 * 
	 * @time   2017年5月20日 下午12:34:15
	 * @author zuoqb
	 * @todo   统计当前以及同比 模块 整机问题闭环率tab1 
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> questionForMkZj(String labTypeCode){
		String sWhere="";
		if(StringUtils.isNotBlank(labTypeCode)){
			sWhere=" and lab_name='"+labTypeCode+"' ";
		}else{
			sWhere=" and lab_name is null ";
		}
		StringBuffer sb=new StringBuffer();
		sb.append(" select a.*,b.*,'当前' as name from ( ");
		sb.append(" select to_char(sum(num)/7*100,'00.0') as mk from t_b_question_cosed where data_type=0 "+sWhere+" and type=0)a join(  ");
		sb.append(" select to_char(sum(num)/7*100,'00.0') as zj from t_b_question_cosed where data_type=0 "+sWhere+" and type=1)b on 1=1  ");
		sb.append(" union ");
		sb.append(" select a.*,b.*,'同比' as name from ( ");
		sb.append(" select to_char(sum(num)/7*100,'00.0') as mk from t_b_question_cosed where data_type=1 "+sWhere+" and type=0)a join( ");
		sb.append(" select to_char(sum(num)/7*100,'00.0') as zj from t_b_question_cosed where data_type=1 "+sWhere+" and type=1)b on 1=1 ");
		return Db.find(sb.toString());
	}
	
	/**
	 * 
	 * @time   2017年5月20日 下午4:21:09
	 * @author zuoqb
	 * @todo   安产线统计整机 模块问题闭环率
	 * @param  @param dataType  0 :当前 1：同比
	 * @param  @param type   0：整机 1：模块
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> productLineForTab3(String dataType,String type,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select q.product_name,to_char(q.num*100,'00.0') as rate ");
		sb.append(" from t_b_question_cosed  q left join t_b_dictionary d on q.product_code=d.id  ");
		sb.append(" where q.data_type='"+dataType+"'  and q.type='"+type+"'");
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and q.lab_name='"+labTypeCode+"' ");
		}else{
			sb.append("  and q.lab_name is null ");
		}
		sb.append(" order by d.order_no ");
		return Db.find(sb.toString());
	}
}
  