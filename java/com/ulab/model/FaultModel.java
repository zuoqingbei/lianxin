package com.ulab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.util.HttpClientUtil;
@TableBind(tableName="phm_fault" ,pkName="id")
public class FaultModel extends Model<FaultModel> {

	private static final long serialVersionUID = 1L;
	public static final FaultModel dao=new FaultModel();
	/**
	 * 查询左侧数据表格中字段的故障的信息
	 * @return
	 */
	public Page<Record> ShowFault(int pageNumber,int pageSize){
		return  Db.paginate(pageNumber, pageSize, "select c.f_id,c.f_object,c.f_xx_bianma,c.f_xx_miaoshu,c.f_yy_bianma,c.f_yy_miaoshu,c.f_weihao,c.f_maintenance,c.f_zr_category", "from phm_fault c");
	}
	
	/**
	 * 不同时间段压缩机的需求
	 * @param month
	 * @return
	 */
	public  List<Record>findFaultDemand(String fObject){
		String sql="";
		sql+="select c.name,count(1) as count from (select to_char(a .datetime,'yyyy-mm') as name,(count(a.datetime)) count from(select f_date as datetime from phm_fault where 1=1";
		if(StringUtils.isNotBlank(fObject)){
			sql+=" and f_object='"+fObject+"'";
		}
		sql+="	) a group by	to_char(a .datetime,'yyyy-mm') order by  to_char(a .datetime,'yyyy-mm') desc ) c  left join ( select	f_date as datetime	from	phm_fault where 1=1";
		if(StringUtils.isNotBlank(fObject)){
			sql+=" and f_object='"+fObject+"'";
		}
		sql+=" ) b ON c .name<= to_char(b.datetime,'yyyy-mm')";
		sql+=" group by c.name order by c.name desc";
		return Db.find(sql);
	}
/**
 * 查询所有的phm_fault
 * @return
 * @author chen xin
 */
	public List<Record> findAllFaultInfo(){
		String sql="select  *  from phm_fault ";
		return Db.find(sql);
	}
	


/**
 * 分页查询phm_fault
 * @param pageStart
 * @param pageSize
 * @author chen xin 
 */
	public List<Record> findPageFaultInfo(int page,int pageSize,String fault_name){
		String sql="select * from (	select rownum num,fault.* from phm_fault fault where rownum<="+page*pageSize+") where num >"+((page-1)<0?0:(page-1))*pageSize;
		if(StringUtils.isNoneBlank(fault_name)){
			sql+="and fault_name='"+fault_name+"'";
		}
		return Db.find(sql);
	}
	/**
	 * 删除 phm_fault
	 * @author chen xin 
	 * @param sncode
	 */		
		public void deleteFaultBySncode(String sncode) {
			String sql="delete from phm_fault where sncode='"+sncode+"'";
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
			String sql="select count(sncode) num from phm_fault where 1=1";
			if(StringUtils.isNotBlank(startTime)) {
				sql+=" and fault_time>to_date('"+startTime+"','yyyy-MM-dd HH24:mi:ss')";
			}
			if(StringUtils.isNotBlank(endTime)) {
				sql+=" and fault_time<=to_date('"+endTime+"','yyyy-MM-dd HH24:mi:ss')";
				
			}
			if(StringUtils.isNotBlank(fault_name)) {
				sql+=" and fault_name='"+fault_name+"'";
			}
			Object obj=	Db.queryColumn(sql);
				String s=obj.toString();
			return Integer.parseInt(s);
			
		}
		/**
		 * 
		 * @time   2017年12月6日 下午2:59:56
		 * @author zuoqb
		 * @todo   定时任务更新设备故障信息数据
		 * @return_type   void
		 */
	    @SuppressWarnings("rawtypes")
		public void synchroFaultInfo() {
			List<Record> list=PhmDeviceInfoModel.dao.getAllDevice();
			List<Record> FaultList=FaultModel.dao.findAllFaultInfo();
			List<String> sncodeList=new ArrayList<>();
			for(int i=0;i<FaultList.size();i++) {//遍历先有的 错误表中的已存在的sncode
				Record record=FaultList.get(i);
				sncodeList.add(record.getStr("sncode"));
			}
			for(int i=0;i<list.size();i++) {
				String sn=list.get(i).getStr("sncode");
				String info=HttpClientUtil.sendGetRequest("http://localhost:8088/api/yzd/product/"+sn+"/diagnosisResult","UTF-8");
				JSONArray jsonArray=(JSONArray) JSONObject.parse(info);
				if(sncodeList.contains(sn)) {
					FaultModel.dao.deleteFaultBySncode(sn);
				}else {
					sncodeList.add(sn);
				}
				List<String>sqlList=new ArrayList<>();
				for(int j=0;j<jsonArray.size();j++) {
					Map mp=(Map) jsonArray.get(j);
					String time=(String) mp.get("time");//故障发生时间
					String name=(String) mp.get("fault");//故障名字
					JSONArray advice=(JSONArray) mp.get("advice");
					String adviceString=""; //TO_DATE('"+f_date+"','yyyy-MM-dd HH24:mi:ss')
					 for(int k=0;k<advice.size();k++) {
						 if(k==0) {
							 adviceString+=advice.get(k);
						 }else {
							 adviceString+=","+advice.get(k);
						 }
					 }                    
					String sql="insert into phm_fault (sncode,fault_name,fault_repair,fault_time)values('"+sn+"','"+name+"','"+adviceString+"',TO_DATE('"+time+"','yyyy-MM-dd HH24:mi:ss'))";
					sqlList.add(sql);
				}
				FaultModel.dao.addFault(sqlList);
			}
	    }
			
		
}
