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
/**
 * 查询所有的phm_fault
 * @return
 * @author chen xin
 */
	public List<Record> findAllFaultInfo(){
		String sql="select"
	+ " f_id,f_object,F_XX_BIANMA,F_XX_MIAOSHU,F_YY_BIANMA,F_YY_MIAOSHU,F_WEIHAO,F_MAINTENANCE,F_ZR_CATEGORY,F_DATE,F_QUYU,F_GONGMAO,F_SB_NAME,F_SB_NUMBER,product_id from PHM_FAULT ";
		return Db.find(sql);
	}
	
/**
 * 插入phm_fault
 * @author chen xin
 */
		public void insertFault(String id,String f_object,String f_xx_bianma,String f_xx_miaoshu,String f_yy_bianma,String f_yy_miaoshu,String f_weihao,String f_maintenance,String f_zr_category,String f_date, String f_quyu,String f_gongmao,String f_sb_number,String sn,String product_id){
	/*		String sql="insert into phm_fault "
	+ "(f_id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,f_yy_miaoshu,f_weihao,f_maintenance,f_zr_category,f_date,f_quyu,f_gongmao,f_sb_number,f_sb_name)"
	+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";*/
			String sql="insert into phm_fault "
					+ "(f_id,f_object,f_xx_bianma,f_xx_miaoshu,f_yy_bianma,f_yy_miaoshu,f_weihao,f_maintenance,f_zr_category,f_date,f_quyu,f_gongmao,f_sb_number,f_sb_name,product_id)"
					+ "values('"+id+"','"+f_object+"','"+f_xx_bianma+"','"+f_xx_miaoshu+"','"+f_yy_bianma+"','"+f_yy_miaoshu+"','"+f_weihao+"','"+f_maintenance+"','"+f_zr_category+"',TO_DATE('"+f_date+"','yyyy-MM-dd HH24:mi:ss')"+",'"+f_quyu+"','"+f_gongmao+"','"+f_sb_number+"','"+sn+"','"+product_id+"')";
			Db.update(sql);
		}
/**
 * 更新phm_fault 根据id
 * @author chen xin
 */
		public void updateFault(String id,String f_object,String f_xx_bianma,String f_xx_miaoshu,String f_yy_bianma,String f_yy_miaoshu,String f_weihao,String f_maintenance,String f_zr_category,String f_date, String f_quyu,String f_gongmao,String f_sb_number,String sn,String product_id){
		/*	String sql="update phm_fault"
		+ "  set f_object=?,f_xx_bianma=?,f_xx_miaoshu=?,f_yy_bianma=?,f_yy_miaoshu=?,f_weihao=?,f_maintenance=?,f_zr_category=?,f_date=?,f_quyu=?,f_gongmao=?,f_sb_number=?,f_sb_name=? where f_id="+id;
		*/	
			String sql="update phm_fault"
					+ "  set f_object='"+f_object+"',f_xx_bianma='"+f_xx_bianma+"',f_xx_miaoshu='"+f_xx_miaoshu+"',f_yy_bianma='"+f_yy_bianma+"',f_yy_miaoshu='"+f_yy_miaoshu+"',f_weihao='"+f_weihao+"',f_maintenance='"+f_maintenance+"',f_zr_category='"+f_zr_category+"',f_date=TO_DATE('"+f_date+"','yyyy-MM-dd HH24:mi:ss'),f_quyu='"+f_quyu+"',f_gongmao='"+f_gongmao+"',f_sb_number='"+f_sb_number+"',f_sb_name='"+f_sb_number+"' where f_id='"+id+"' and product_id='"+product_id+"'";
				
			Db.update(sql);
		}
/**
 * 分页查询phm_fault
 * @param pageStart
 * @param pageSize
 * @author chen xin 
 */
		public List<Record> findPageFaultInfo(int page,int pageSize,String f_object){
			String sql="select fault.num,f_id,f_object,F_XX_BIANMA,F_XX_MIAOSHU,faultInfo.F_YY_BIANMA,faultInfo.F_YY_MIAOSHU,faultInfo.F_WEIHAO,faultInfo.F_MAINTENANCE,faultInfo.F_ZR_CATEGORY,F_DATE,F_SB_NAME,F_SB_NUMBER,product_id from "
					+ "(select"
					+ " rownum num, f_id,f_object,F_XX_BIANMA,F_XX_MIAOSHU,F_YY_BIANMA,F_YY_MIAOSHU,F_WEIHAO,F_MAINTENANCE,F_ZR_CATEGORY,F_DATE,F_QUYU,F_GONGMAO,F_SB_NAME,F_SB_NUMBER,product_id from PHM_FAULT where rownum<="+page*pageSize+") fault"
					+ " inner join PHM_FAULT_LOSTINFO faultInfo on fault.F_YY_BIANMA=faultInfo.F_YY_BIANMA"
					+ " where fault.num>"+((page-1)<0?0:(page-1))*pageSize;
			if(StringUtils.isNoneBlank(f_object)){
				sql+="and f_object='"+f_object+"'";
			}
			return Db.find(sql);
		}
}
