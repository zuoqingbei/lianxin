package com.ulab.client.webServiceRerigerator;

import java.util.Calendar;

public class TestWebService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Service service = new Service();
		ServicePortType port = service.getServiceHttpPort();
		
		//获得所有监测单元信息
		ArrayOfTestUnitInfo testUnitInfos = port.getTestUnitInfo("refrigerator2016001");
		
//		for(TestUnitInfo testUnitInfo : testUnitInfos.getTestUnitInfo()){
//			System.out.println(ToStringBuilder.reflectionToString(testUnitInfo));
//		}
		int count = testUnitInfos.getTestUnitInfo().size();
		System.out.println("refrigerator2016001台位总数："+count);
		
		//获得传感器基本配置信息
//		ArrayOfSensorInfo sensorInfos = port.getSensorConfig("refrigerator2016001");
//		
//		for(SensorInfo sensorInfo : sensorInfos.getSensorInfo()){
//			System.out.println(ToStringBuilder.reflectionToString(sensorInfo));
//		}
		
//		//获得测试数据元数据信息
//		ArrayOfTestMetadata testMetadatas = port.getAllMetaData("refrigerator2016001", 0, 2);
//		
//		for(TestMetadata testMetadata : testMetadatas.getTestMetadata()){
//			System.out.println(ToStringBuilder.reflectionToString(testMetadata));
//			for(String input: testMetadata.getInputContent().getString()){
//				System.out.println(input);
//			}
//		}
		
		int num = 0;
		//录入条目
		ArrayOfTestProdInfoItem testProdInfoItems = port.getTestProdInfoItemByLabCode("refrigerator2016001");
		for(TestProdInfoItem testProdInfoItem : testProdInfoItems.getTestProdInfoItem()){
//			System.out.println(ToStringBuilder.reflectionToString(testProdInfoItem));
			if(1 == testProdInfoItem.getQueryCondition()){
				num++;
			}
			
		}
		StringBuilder flagBuilder = new StringBuilder("@@");
		for(int i = 0; i < num; i++){
			flagBuilder.append("@");
		}
		String flag = flagBuilder.toString();
		System.out.println("flag:"+flag);
		//获取在测的铭牌信息
//		ArrayOfTestMetadata testMetadatas = port.getMetaData("refrigerator2016001", -1, "2017-05-01 00:00:00@@@@@@");
//		for(TestMetadata testMetadata : testMetadatas.getTestMetadata()){
//			System.out.println(ToStringBuilder.reflectionToString(testMetadata));
//		}
		Calendar calendar =Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		StringBuilder firstDaybBuilder = new StringBuilder();
		firstDaybBuilder.append(year).append("-01-01 00:00:00").append(flag);
		String firstDayFlag = firstDaybBuilder.toString();
		System.out.println("firstDayFlag:"+firstDayFlag);
		int stopProcessOrderNum = 0;
		int processingOrderNum = 0;
		StringBuilder processingUnits = new StringBuilder();
		int processingUnitNum = 0;
		ArrayOfTestMetadata stopTestMetadatas = port.getMetaData("refrigerator2016001", 0, firstDayFlag);
		if(null != stopTestMetadatas && null != stopTestMetadatas.getTestMetadata()){
			stopProcessOrderNum = stopTestMetadatas.getTestMetadata().size();
		}
		System.out.println("已测订单："+stopProcessOrderNum);
		ArrayOfTestMetadata testMetadatas = port.getMetaData("refrigerator2016001", 1, flag);
		if(null != testMetadatas && null != testMetadatas.getTestMetadata() && testMetadatas.getTestMetadata().size() > 0){
			System.out.println("实验室状态：在用");
			for(TestMetadata testMetadata : testMetadatas.getTestMetadata()){
				processingOrderNum++;
				if(!(processingUnits.toString().contains(testMetadata.getTestUnitId() + ","))){
					processingUnits.append(testMetadata.getTestUnitId()).append(",");
					processingUnitNum ++;
				}				
			}
		}else {
			System.out.println("实验室状态：停测");			
		}
		System.out.println("台位负荷："+processingUnitNum+"/"+count);
		System.out.println("在测订单数："+processingOrderNum);
		//传感器信息
//		ArrayOfSensorInfo sensorInfos = port.getSensorInformation("refrigerator2016001", "2017-05-1516:20:261");
//		for(SensorInfo sensorInfo : sensorInfos.getSensorInfo()){
//			System.out.println(ToStringBuilder.reflectionToString(sensorInfo));
//		}
		//获取测试数据
//		ArrayOfString datas = port.getSensorTestDataByTime("refrigerator2016001", "2017-05-1516:20:261", (float)0.0, (float)0.2);
//		for(String data : datas.getString()){
//			System.out.println(data);
//		}
		//获取传感器类型信息
//		ArrayOfSensorTypeInfo sensorTypeInfos = port.getTestUnitSensorTypeInfo("refrigerator2016001", 1);
//		for(SensorTypeInfo sensorTypeInfo : sensorTypeInfos.getSensorTypeInfo()){
//			System.out.println(ToStringBuilder.reflectionToString(sensorTypeInfo));
//		}

	}

}
