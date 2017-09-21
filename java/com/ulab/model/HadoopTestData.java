package com.ulab.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.Constants;
/**
 * 
 * @time   2017年9月20日15:54:19
 * @author zuoqb
 * @todo   测试数据对应的表（传感器生成的测试数据）
 */
public class HadoopTestData {
	public static final HadoopTestData dao = new HadoopTestData();
	/**
	 * 
	 * @time   2017年9月20日 下午5:32:33
	 * @author zuoqb
	 * @todo   根据时间间隔、测试主键查询测试数据
	 * @param  @param configName
	 * @param  @param testIdentification
	 * @param  @param startHowLong
	 * @param  @param endHowLong
	 * @param  @return
	 * @return_type   List<Record>
	 */
	@Deprecated
	public List<Record> findDataByTestIdentification(String configName,String testIdentification,Float startHowLong,Float endHowLong){
		String tableName=DbConfigModel.dao.getTableNameByColumn(configName, Constants.TESTDATA);
		String sql="select * from "+tableName+" where testIdentification='"+testIdentification+"'  ";
		if(startHowLong!=null){
			sql+=" and howlong > "+startHowLong;
		}
		if(endHowLong!=null){
			sql+=" and howlong < "+endHowLong;
		}
		return Db.use(configName).find(sql);
	}
	/**
	 * 
	 * @time   2017年9月20日 下午6:00:17
	 * @author zuoqb
	 * @todo   根据时间间隔、测试主键查询测试数据
	 * @param  @param configName
	 * @param  @param testIdentification
	 * @param  @param startHowLong
	 * @param  @param endHowLong
	 * @param  @param sensorInfoList:传感器集合 用于删选查询字段 如果日此全部 查询太慢
	 * @param  @return
	 * @return_type   List<Record>
	 */
	public List<Record> findDataByTestIdentification(String configName,String testIdentification,Float startHowLong,Float endHowLong,List<Record> sensorInfoList){
		String tableName=DbConfigModel.dao.getTableNameByColumn(configName, Constants.TESTDATA);
		String sql="select howlong  ";
		for(Record sInfo:sensorInfoList ){
			sql+=" , sensorValue_"+sInfo.get("sensorid");
		}
		sql+=" from "+tableName+" where testIdentification='"+testIdentification+"'  ";
		if(startHowLong!=null){
			sql+=" and howlong > "+startHowLong;
		}
		if(endHowLong!=null){
			sql+=" and howlong < "+endHowLong;
		}
		return Db.use(configName).find(sql);
	}
	/**
	 * 
	 * @time   2017年9月21日 上午9:07:02
	 * @author zuoqb
	 * @todo   查询曲线数据
	 * @param  @param configName
	 * @param  @param labCode
	 * @param  @param testUnitId
	 * @param  @param startTime
	 * @param  @param interval：查询时间间隔
	 * @param  @return
	 * @return_type   Record
	 */
	public Record findTestData(String configName,String labCode,String testUnitId,String startTime,Float interval){
		Record finalTestData=new Record();
		Record metaData=HadoopTestMetadata.dao.findLastTestMetadata(configName, labCode, testUnitId);
		//获取当前台位实时曲线
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date testBeginTime = null;//开始测试时间
		Date now = new Date();
		if(metaData!=null){
			try {
				testBeginTime = sdf.parse(metaData.get("testBeginTime")+"");
				if(StringUtils.isNotBlank(startTime)){
					now=sdf.parse(startTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		float f2 = (now.getTime() - testBeginTime.getTime())/3600000f;//已经测试时长
		float startHowLong = f2 - interval> 0 ? f2-interval : 0;
		float endHowLong=startHowLong+interval;
		joinTestData(configName, startHowLong, endHowLong, finalTestData, metaData);
		return finalTestData;
	}
	/**
	 * 
	 * @time   2017年9月20日 下午6:01:27
	 * @author zuoqb
	 * @todo   组装曲线数据格式
	 * @param  @param configName
	 * @param  @param labCode
	 * @param  @param testUnitId
	 * @param  @param startHowLong
	 * @param  @param endHowLong
	 * @param  @return
	 * @return_type   Record
	 */
	public Record findTestData(String configName,String labCode,String testUnitId,Float startHowLong,Float endHowLong){
		Record finalTestData=new Record();
		//step1 查询测试基础信息 根据实验室以及测试单元编码获取最后一次测试信息
		Record metaData=HadoopTestMetadata.dao.findLastTestMetadata(configName, labCode, testUnitId);
		joinTestData(configName, startHowLong, endHowLong, finalTestData, metaData);
		return finalTestData;
	}
	private void joinTestData(String configName, Float startHowLong, Float endHowLong, Record finalTestData,
			Record metaData) {
		if(metaData!=null){
			/**
			 * sybh:'实验编号',
				ybbh:'样品编号',
				cpxh:'产品型号',
				testUnitStatus:试验项目
			 */
			String testIdentification=metaData.getStr("testIdentification");//实验编号
			finalTestData.set("sybh",testIdentification);
			finalTestData.set("ybbh", metaData.get("sample_code"));
			finalTestData.set("cpxh", metaData.get("ProductModel"));
			finalTestData.set("testUnitStatus", metaData.get("testItemName"));
			//step2 :查询传感器信息
			List<Record> sensorInfoList=HadoopSensorInfo.dao.findSensorInfoByTestIdentification(configName, testIdentification);
			//step3 查询具体数据
			List<Record> allTestData=findDataByTestIdentification(configName, testIdentification, startHowLong, endHowLong,sensorInfoList);
			//step3 拼接结构 
			/**
			 *  {	name:'1:温度(℃)',
		    	data:[{name:'1月',value:'-55'},
		    	{name:'2月',value:'60'},
		    	{name:'3月',value:'447'} ]
		      }
			 */
			List<Record> dataList=new ArrayList<Record>();
			for(Record sensorInfo:sensorInfoList){
				Record mData=new Record();
				mData.set("name", sensorInfo.get("legend"));
				List<Record> data=new ArrayList<Record>();
				for(Record testData:allTestData){
					Record innerData=new Record();
					innerData.set("name", testData.get("howlong"));
					//传感器数据，跟sensorinfo中的sensorId对应
					innerData.set("value", testData.get("sensorValue_"+sensorInfo.get("sensorid")));
					data.add(innerData);
				}
				mData.set("data", data);
				dataList.add(mData);
			}
			finalTestData.set("list", dataList);
		}
	}
	public Record findTestData(String configName,String labCode,String testUnitId,Float endHowLong){
		return this.findTestData(configName, labCode, testUnitId, "", endHowLong);
	}
	public Record findTestData(String configName,String labCode,String testUnitId){
		return this.findTestData(configName, labCode, testUnitId, null);
	}
}
