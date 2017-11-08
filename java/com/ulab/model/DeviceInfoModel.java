
package com.ulab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月19日 上午10:40:55
 * @author chen xin
 * @todo  
 */
@TableBind(tableName = "phm_device_info",pkName="id")
public class DeviceInfoModel extends Model<DeviceInfoModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceInfoModel dao = new DeviceInfoModel();
	
	public List<Record> findAllDeviceInfo(){
		String sql="select * from phm_device_info";
		return Db.find(sql);
	}
	/*
	 * 查询商品名字相对应的数量
	 */
	public List<Record> findDeviceKindNumber(){
		String sql="select (select count(id) from PHM_DEVICE_INFO)as total,(select COUNT(id) from PHM_DEVICE_INFO where name='冰箱') as fridge, (select COUNT(id) from PHM_DEVICE_INFO where name='商空') as bussinessAirCondition ,(select COUNT(id) from PHM_DEVICE_INFO where name='洗衣机') as washMachine,(select cOUNT(id) from PHM_DEVICE_INFO where name='冷柜') as icebox,(select COUNT(id) from PHM_DEVICE_INFO where name='家空') as homeAirCondition from dual";
		return Db.find(sql);
	}
	/*
	 * 根据sn查询商品信息
	 */
	public Record findDeviceInfoById(String sn){
		String sql="select * from phm_device_info where sncode="+sn;
		List<Record>list= Db.find(sql);
		return list.size()>0?list.get(0):null;
	}
	/**
	 * @author chenxin
	 * @return List<List<Record>>
	 * 根据status分别查询处四种状态的集合
	 * 为了方便封装 把address作为name,把name作为商品种类kind(为了前台地图需要)
	 * dimension 维度 y
	 * longitude 经度x
	 */
	public Map<String,List<Record>> findDeviceInfoStatusGroup(){
			HashMap<String,List<Record>>mp=new HashMap<>();
			//String sqlSerious="select status name,sncode,dimension  || '*that.bodyScale' x,longitude || '*that.bodyScale' y,image,address,name kind from PHM_DEVICE_INFO where STATUS='严重'";
			//String sqlGeneral="select status name,sncode,dimension  || '*that.bodyScale' x,longitude || '*that.bodyScale' y,image,address,name kind from PHM_DEVICE_INFO where STATUS='一般'";
			//String sqlSlight="select status name,sncode,dimension  || '*that.bodyScale' x,longitude || '*that.bodyScale' y,image,address,name kind from PHM_DEVICE_INFO where STATUS='轻微'";
			//String sqlGood="select status name,sncode,dimension  || '*that.bodyScale' x,longitude || '*that.bodyScale' y,image,address,name kind from PHM_DEVICE_INFO where STATUS='良好'";
			String sqlSerious="select status,sncode,dimension   y,longitude  x,image,address name,name kind from PHM_DEVICE_INFO where STATUS='严重'";
			String sqlGeneral="select status,sncode,dimension   y,longitude  x,image,address name,name kind from PHM_DEVICE_INFO where STATUS='一般'";
			String sqlSlight="select status,sncode,dimension   y,longitude  x,image,address name,name kind from PHM_DEVICE_INFO where STATUS='轻微'";
			String sqlGood="select status,sncode,dimension     y,longitude  x,image,address name,name kind from PHM_DEVICE_INFO where STATUS='良好'";
				List<Record> serious=Db.find(sqlSerious);
				List<Record> general=Db.find(sqlGeneral);
				List<Record> light=Db.find(sqlSlight);
				List<Record> good=Db.find(sqlGood);
				mp.put("serious",serious);
				mp.put("general", general);
				mp.put("light", light);
				mp.put("good", good);
				List address=new ArrayList();//用来封装所有的地址所对应的经纬度
				address=address(address,serious);
				address=address(address,general);
				address=address(address,light);
				address=address(address,good);
				mp.put("address",address);
		return mp;
	}
	private List address(List ls,List<Record> list) {
			for(int i=0;i<list.size();i++) {
				Record  record=list.get(i);
				String address=record.getStr("name");
				String x=record.getStr("x");record.remove("x");
				String y=record.getStr("y");record.remove("y");
				ArrayList<String> newList=new ArrayList<>();
				HashMap<String,List<String>>address1=new HashMap<>();//用来封装所有的地址所对应的经纬度
				newList.add(x);newList.add(y);
				address1.put(address, newList);
				ls.add(address1);
			}
			return ls;
	}
	
}
