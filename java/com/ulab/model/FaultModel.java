package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
@TableBind(tableName="phm_fault" ,pkName="f_id")
public class FaultModel extends Model<FaultModel> {

	private static final long serialVersionUID = 1L;
	public static final FaultModel dao=new FaultModel();
	/**
	 * 查询左侧数据表格中字段的故障的信息
	 * Page<User> userPage = User.dao.paginate(1, 10, "select *", "from user 
where age > ?", 18);
	 * @return
	 */
	public Page<Record> ShowFault(int pageNumber,int pageSize){
//		StringBuffer sb=new StringBuffer();
//		sb.append("select c.f_id,c.f_object,c.f_xx_bianma,c.f_xx_miaoshu,c.f_yy_bianma,c.f_yy_miaoshu,c.f_weihao,c.f_maintenance,c.f_zr_category from phm_fault c");
//		
		return  Db.paginate(pageNumber, pageSize, "select c.f_id,c.f_object,c.f_xx_bianma,c.f_xx_miaoshu,c.f_yy_bianma,c.f_yy_miaoshu,c.f_weihao,c.f_maintenance,c.f_zr_category", "from phm_fault c");
	}
	/**
	 * 查询工贸
	 */
	public List<Record> showFaultGongmao(String fObject,String fQuyu){
		StringBuffer sb=new StringBuffer();
		sb.append("select f_gongmao as name,COUNT(f_gongmao) as count from phm_fault where 1=1 "); 
		if(StringUtils.isNotBlank(fObject)){
			sb.append(" and f_object='"+fObject+"'");
		}
		if(StringUtils.isNotBlank(fQuyu)){
			sb.append(" and f_quyu='"+fQuyu+"'");
		}
		sb.append("group by f_gongmao");
		return Db.find(sb.toString());
	}
	/**
	 * 各区域压缩机备件需求
	 * @return
	 */
	public List<Record> showFaultQuyu(String fObject){
		StringBuffer sb=new StringBuffer();
		sb.append("select f_quyu as name,COUNT(f_quyu) as count from phm_fault where 1=1 ");
		if(StringUtils.isNotBlank(fObject)){
			sb.append(" and f_object='"+fObject+"'");
		}
		sb.append(" group by f_quyu");
		return Db.find(sb.toString());
	}
	/**
	 * 不同时间段压缩机的需求
	 * @param month
	 * @return
	 */
	public  List<Record>findFaultDemand(String fObject){
//		StringBuffer sb=new StringBuffer();
//		sb.append("select f_date,sum(f_object) from phm_fault where  sysdate-f_date="+month+"  group by f_date ");
		String sql="";
				sql+="SELECT c.name,count(1) as count from (SELECT to_char(A .datetime,'yyyy-mm') as name,(COUNT(a.datetime)) count FROM(SELECT F_DATE AS datetime FROM PHM_FAULT WHERE 1=1";
				if(StringUtils.isNotBlank(fObject)){
					sql+=" and f_object='"+fObject+"'";
				}
				sql+="	) A GROUP BY	to_char(A .datetime,'yyyy-mm') ORDER BY  to_char(A .datetime,'yyyy-mm') desc ) c  LEFT JOIN ( SELECT	F_DATE AS datetime	FROM	PHM_FAULT WHERE 1=1";
				if(StringUtils.isNotBlank(fObject)){
					sql+=" and f_object='"+fObject+"'";
				}
				sql+=" ) b ON c .name<= to_char(b.datetime,'yyyy-mm')";
				sql+=" GROUP BY c.name ORDER by c.name desc";
		return Db.find(sql);
	}


}
