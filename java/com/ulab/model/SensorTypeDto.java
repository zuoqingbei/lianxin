package com.ulab.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;
/**
 * 
 * @time   2017年5月27日 上午9:59:56
 * @author zuoqb
 * @todo   曲线数据
 * var dataBase={
		sensorList:[],
		sybh:'实验编号',
		ybbh:'样品编号',
		cpxh:'产品型号',
		list:[
		     {	name:'1:温度(℃)',
		    	data:[{name:'1月',value:'-55'},{name:'2月',value:'60'},{name:'3月',value:'447'},{name:'4月',value:'400'},{name:'5月',value:'200'},{name:'6月',value:'250'},{name:'7月',value:'15'},{name:'8月',value:'202'},{name:'9月',value:'21'},{name:'10月',value:'7'},{name:'11月',value:'103'},{name:'12月',value:'215'} ]
		      },{
		    	  name:'2:电压(V)',
		    	  data:[{name:'1月',value:'144'},{name:'2月',value:'252'},{name:'3月',value:'227'},{name:'4月',value:'111'},{name:'5月',value:'241'},{name:'6月',value:'233'},{name:'7月',value:'105'},{name:'8月',value:'22'},{name:'9月',value:'55'},{name:'10月',value:'175'},{name:'11月',value:'153'},{name:'12月',value:'55'} ] 
		      },{
		    	  name:'3:电流(A)',
		    	  data:[{name:'1月',value:'24'},{name:'2月',value:'2'},{name:'3月',value:'7'},{name:'4月',value:'11'},{name:'5月',value:'54'},{name:'6月',value:'33'},{name:'7月',value:'15'},{name:'8月',value:'22'},{name:'9月',value:'5'},{name:'10月',value:'37'},{name:'11月',value:'13'},{name:'12月',value:'45'} ] 
		      },{
		    	  name:'4:功率(W)',
		    	  data:[{name:'1月',value:'4000'},{name:'2月',value:'2222'},{name:'3月',value:'1722'},{name:'4月',value:'1422'},{name:'5月',value:'1222'},{name:'6月',value:'1522'},{name:'7月',value:'5222'},{name:'8月',value:'2222'},{name:'9月',value:'1122'},{name:'10月',value:'1722'},{name:'11月',value:'1322'},{name:'12月',value:'5222'} ] 
		      },{
		    	  name:'5:耗电量(Wh)',
		    	  data:[{name:'1月',value:'14000'},{name:'2月',value:'12000'},{name:'3月',value:'3700'},{name:'4月',value:'4400'},{name:'5月',value:'5200'},{name:'6月',value:'12500'},{name:'7月',value:'5100'},{name:'8月',value:'5002'},{name:'9月',value:'4001'},{name:'10月',value:'2004'},{name:'11月',value:'15154'},{name:'12月',value:'11133'} ] 
		      },{
		    	  name:'6:频率(Hz)',
		    	  data:[{name:'1月',value:'15'},{name:'2月',value:'60'},{name:'3月',value:'70'},{name:'4月',value:'40'},{name:'5月',value:'20'},{name:'6月',value:'25'},{name:'7月',value:'15'},{name:'8月',value:'22'},{name:'9月',value:'71'},{name:'10月',value:'56'},{name:'11月',value:'43'},{name:'12月',value:'95'} ]
		      },{
		    	  name:'7:功率因数(PF)',
		    	  data:[{name:'1月',value:'40'},{name:'2月',value:'20'},{name:'3月',value:'17'},{name:'4月',value:'34'},{name:'5月',value:'12'},{name:'6月',value:'65'},{name:'7月',value:'45'},{name:'8月',value:'2'},{name:'9月',value:'71'},{name:'10月',value:'27'},{name:'11月',value:'13'},{name:'12月',value:'65'} ]
		      },{
		    	  name:'9:压力(kpa)',
		    	  data:[{name:'1月',value:'-74'},{name:'2月',value:'82'},{name:'3月',value:'57'},{name:'4月',value:'14'},{name:'5月',value:'12'},{name:'6月',value:'15'},{name:'7月',value:'5'},{name:'8月',value:'2'},{name:'9月',value:'-11'},{name:'10月',value:'67'},{name:'11月',value:'-43'},{name:'12月',value:'50'} ]
		      },{
		    	  name:'10:转速(r/min)',
		    	  data:[{name:'1月',value:'100'},{name:'2月',value:'10'},{name:'3月',value:'2700'},{name:'4月',value:'1400'},{name:'5月',value:'-2212'},{name:'6月',value:'1500'},{name:'7月',value:'522'},{name:'8月',value:'2254'},{name:'9月',value:'1100'},{name:'10月',value:'1527'},{name:'11月',value:'1333'},{name:'12月',value:'500'} ]
		      },{
		    	  name:'11:瞬时流量(L/min)',
		    	  data:[{name:'1月',value:'11'},{name:'2月',value:'80'},{name:'3月',value:'77'},{name:'4月',value:'44'},{name:'5月',value:'55'},{name:'6月',value:'15'},{name:'7月',value:'5'},{name:'8月',value:'20'},{name:'9月',value:'91'},{name:'10月',value:'77'},{name:'11月',value:'13'},{name:'12月',value:'50'} ]
		      }
		      ]
};
 */
public class SensorTypeDto {
	private List<Record> sensorList;//存放传感器
	private List<Line> list;//存放数据
	private String sybh;//实验编号
	private String ybbh;//样品编号
	private String cpxh;//产品型号
	public List<Record> getSensorList() {
		return sensorList;
	}
	public void setSensorList(List<Record> sensorList) {
		this.sensorList = sensorList;
	}
	public List<Line> getList() {
		return list;
	}
	public void setList(List<Line> list) {
		this.list = list;
	}
	public String getSybh() {
		return sybh;
	}
	public void setSybh(String sybh) {
		this.sybh = sybh;
	}
	public String getYbbh() {
		return ybbh;
	}
	public void setYbbh(String ybbh) {
		this.ybbh = ybbh;
	}
	public String getCpxh() {
		return cpxh;
	}
	public void setCpxh(String cpxh) {
		this.cpxh = cpxh;
	}
}
