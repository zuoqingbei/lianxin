package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年10月19日 上午5:50:01
 * @author zuoqb
 * @todo   实验室视频监控
 */
@TableBind(tableName = "t_b_lab_video",pkName="id")
public class LabVideoModel extends Model<CommunistModel>{
	private static final long serialVersionUID = 1L;
	public static final LabVideoModel dao = new LabVideoModel();
	/**
	 * 
	 * @time   2017年10月19日 上午5:59:18
	 * @author zuoqb
	 * @todo  根据数据中心ID查询实验室以及各实验室对应监控信息
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findVideosByDataCenterId(String dataCenterId){
		List<Record> list=findLabsByDataCenterId(dataCenterId);
		for(Record video:list){
			video.set("videos", findVideosByLabCode(video.getStr("lab_code")));
		}
		return list;
		
	}
	
	/**
	 * 
	 * @time   2017年10月19日 上午5:56:17
	 * @author zuoqb
	 * @todo   根据实验室编码查询监控
	 */
	public List<Record> findVideosByLabCode(String labCode){
		String sql="select * from t_b_lab_video where  lab_code='"+labCode+"' and del_flag=0 and show_flag=0 order by order_num";
		List<Record> list=Db.find(sql);
		return list;
	}
	/**
	 * 
	 * @time   2017年10月19日 上午5:56:17
	 * @author zuoqb
	 * @todo   根据数据中心查询实验室列表
	 */
	public List<Record> findLabsByDataCenterId(String dataCenterId){
		String sql="select distinct v.lab_code,lab.lab_name,lab.order_num from t_b_lab_video v left join t_b_lab_code lab on lab.lab_code=v.lab_code where lab.data_center_id in(select id from t_b_data_center where parent_id='"+dataCenterId+"' and center_level=3) and lab.del_flag=0  and v.del_flag=0 and v.show_flag=0 order by lab.order_num";
		List<Record> list=Db.find(sql);
		return list;
	}
	/**
	 * 
	 * @time   2017年10月19日 上午6:25:12
	 * @author zuoqb
	 * @todo   根据实验室编码查询某个实验室的画中画监控
	 * @param  @param labCode
	 */
	public Record findTopVideoByLabCode(String labCode){
		String sql="select * from t_b_lab_video where lab_code='"+labCode+"' and del_flag=0 and show_flag=0 and is_top=1";
		return Db.findFirst(sql);
	}
}
