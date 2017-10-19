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
 * @todo   数据中心基础信息
 */
@TableBind(tableName = "t_b_data_center",pkName="id")
public class DataCenterModel extends Model<CommunistModel>{
	private static final long serialVersionUID = 1L;
	public static final DataCenterModel dao = new DataCenterModel();
	/**
	 * 
	 * @time   2017年10月19日 上午5:59:18
	 * @author zuoqb
	 * @todo   查询数据中心 包含层级关系
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findAllDataCenter(){
		List<Record> list=findDataCenterByLevel("1");
		for(Record center:list){
			if("1".equals(center.getStr("haschildren"))){
				center.set("children", findDataCenterByParentId(center.get("id")));
			}
		}
		return list;
		
	}
	/**
	 * 
	 * @time   2017年10月19日 上午5:54:59
	 * @author zuoqb
	 * @todo   根据等级查询数据中心
	 * @param  @param level
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findDataCenterByLevel(Object level){
		String sql="select * from t_b_data_center where level='"+level+"' and del_flag=0 order by order_num";
		return Db.find(sql);
	}
	/**
	 * 
	 * @time   2017年10月19日 上午5:56:17
	 * @author zuoqb
	 * @todo   根据父级ID查询数据中心
	 * @param  @param parentId
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findDataCenterByParentId(Object parentId){
		String sql="select * from t_b_data_center where parent_id='"+parentId+"' and del_flag=0 order by order_num";
		List<Record> list=Db.find(sql);
		for(Record center:list){
			if("1".equals(center.getStr("haschildren"))){
				center.set("children", findDataCenterByParentId(center.get("id")));
			}
		}
		return list;
	}
}
