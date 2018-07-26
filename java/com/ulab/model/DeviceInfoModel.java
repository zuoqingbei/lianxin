
package com.ulab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@TableBind(tableName = "phm_device_info",pkName="sncode")
public class DeviceInfoModel extends Model<DeviceInfoModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final DeviceInfoModel dao = new DeviceInfoModel();
	/**
	 * 
	 * @time   2017年12月6日 下午2:48:41
	 * @author zuoqb
	 * @todo   查询全部设备
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findAllDeviceInfo(){
		String sql="select * from phm_device_info";
		return Db.find(sql);
	}
	
	/**
	 * @author chenxin
	 * @return List<List<Record>>
	 * 根据status分别查询处四种状态的集合-实时状态设备状态信息（中国地图数据）
	 * 为了方便封装 把address作为name,把name作为商品种类kind(为了前台地图需要)
	 * dimension 维度 y
	 * longitude 经度x
	 */
	public Map<String,List<Record>> findDeviceInfoStatusGroup(){
			HashMap<String,List<Record>>mp=new HashMap<>();
			String sqlSerious="select status,sncode,dimension   y,longitude  x,image,address name,name kind from phm_device_info where status='严重'";
			String sqlGeneral="select status,sncode,dimension   y,longitude  x,image,address name,name kind from phm_device_info where status='一般'";
			String sqlSlight="select status,sncode,dimension   y,longitude  x,image,address name,name kind from phm_device_info where status='轻微'";
			String sqlGood="select status,sncode,dimension     y,longitude  x,image,address name,name kind from phm_device_info where status='良好'";
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

