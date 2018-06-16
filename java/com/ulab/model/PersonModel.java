
package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:39:40
 * @author zuoqb
 * @todo   设备人员信息
 */
@TableBind(tableName = "t_b_person_data",pkName="id")
public class PersonModel extends Model<LabModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final PersonModel dao = new PersonModel();
	
	/**
	 * 
	 * @time   2017年5月17日 上午9:03:55
	 * @author zuoqb
	 * @todo   统计人员
	 * @param  @param type:类型 1:学历情况 2:工作年限情况 3:批准权限
	 * @param  @param plCode: 产线
	 * @param  @param labTypeCode 实验室类型
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> personDetail(String type,String plCode,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select to_char(a.num/b.all_num * 100, '00.0') as rate,a.*,b.* from ( ");
		sb.append(" select d.name,sum(d.num) as num from t_b_person_data p right join t_b_person_detail d on d.lab_id=p.id  ");
		sb.append(" where d.del_flag=0 ");
		if(StringUtils.isNoneBlank(type)){
			sb.append(" and d.type='"+type+"' ");
		}
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and p.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and p.lab_type='"+labTypeCode+"' ");
		}
		sb.append("   group by d.name,d.order_no order by d.order_no )a   ");
		sb.append(" join (");
		sb.append(" select sum(d.num) as all_num from t_b_person_data p right join t_b_person_detail d on d.lab_id=p.id  ");
		sb.append(" where d.del_flag=0 ");
		if(StringUtils.isNoneBlank(type)){
			sb.append(" and d.type='"+type+"' ");
		}
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and p.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and p.lab_type='"+labTypeCode+"' ");
		}
		sb.append(" )b on 1=1 ");
		return Db.find(sb.toString());
	}
	
	
	/**
	 * 
	 * @time   2017年5月17日 下午1:24:15
	 * @author zuoqb
	 * @todo   实验室状态tab2-人员状态数据
	 * @param  @param type
	 * @param  @param plCode
	 * @param  @param labTypeCode
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> personForTab2(String type,String plCode,String labTypeCode){
		StringBuffer sb=new StringBuffer();
		sb.append(" select p.product_code,p.product_name,d.name,sum(d.num) as all_num ");
		sb.append(" from t_b_person_data p right join  t_b_person_detail d on d .lab_id = p . id ");
		sb.append(" where 1=1 ");
		if(StringUtils.isNoneBlank(type)){
			sb.append(" and d.type='"+type+"' ");
		}
		if(StringUtils.isNoneBlank(plCode)){
			sb.append(" and p.product_code='"+plCode+"' ");
		}
		if(StringUtils.isNoneBlank(labTypeCode)){
			sb.append("  and p.lab_type='"+labTypeCode+"' ");
		}
		sb.append("  group by d.name,p.product_code,p.product_name,d.order_no order by d.order_no");
		return Db.find(sb.toString());
	}

}
