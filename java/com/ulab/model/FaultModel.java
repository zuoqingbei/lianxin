package com.ulab.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
//@TableBind(tableName="phm_fault" ,pkName="f_id")
@TableBind(tableName="phm_fault2" ,pkName="id")
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
/**
 * 查询所有的phm_fault
 * @return
 * @author chen xin
 */
	public List<Record> findAllFaultInfo(){
		String sql="select"
				+ " *  from PHM_FAULT2 ";
		return Db.find(sql);
	}
	


/**
 * 分页查询phm_fault
 * @param pageStart
 * @param pageSize
 * @author chen xin 
 */
		public List<Record> findPageFaultInfo(int page,int pageSize,String fault_name){
			String sql="select * from (	select ROWNUM num,fault.* from PHM_FAULT2 fault where ROWNUM<="+page*pageSize+") where num >"+((page-1)<0?0:(page-1))*pageSize;
			if(StringUtils.isNoneBlank(fault_name)){
				sql+="and fault_name='"+fault_name+"'";
			}
			return Db.find(sql);
		}
	/**
	 * 删除 phm_fault2
	 * @author chen xin 
	 * @param sncode
	 */		
		public void deleteFaultBySncode(String sncode) {
			String sql="delete from phm_fault2 where sncode='"+sncode+"'";
			Db.update(sql);
		}
		/**
		 * 添加   phm_fault
		 * @author chen xin 
		 * @param sncode
		 */	
		public void  addFault(List<String>sqlList) {
			
			Db.batch(sqlList, sqlList.size());
		}
		/**
		 * 根据日期查询  phm_fault
		 * @author chen xin 
		 * @param 
		 * TO_DATE('"+f_date+"','yyyy-MM-dd HH24:mi:ss')
		 */	
		public Integer deviceByDate(String startTime,String endTime,String fault_name ){
			String sql="select count(sncode) num from phm_fault2 where 1=1";
			if(StringUtils.isNotBlank(startTime)) {
				sql+=" and fault_time>TO_DATE('"+startTime+"','yyyy-MM-dd HH24:mi:ss')";
			}
			if(StringUtils.isNotBlank(endTime)) {
				sql+=" and fault_time<=TO_DATE('"+endTime+"','yyyy-MM-dd HH24:mi:ss')";
				
			}
			if(StringUtils.isNotBlank(fault_name)) {
				sql+=" and fault_name='"+fault_name+"'";
			}
			
			
		
			Object obj=	Db.queryColumn(sql);
				String s=obj.toString();
//				int num=Db.queryInt(sql);
//				List<Record> list=Db.find(sql);
//				Record record=list.get(0);
//				Double num=record.get("num");
				//String num=record.getStr("num");//record.getInt("num");直接转报异常java.math.BigDecimal cannot be cast to java.lang.Integer
				
			return Integer.parseInt(s);
			
		}
		
}
