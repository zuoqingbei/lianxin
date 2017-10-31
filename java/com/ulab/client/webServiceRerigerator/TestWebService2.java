package com.ulab.client.webServiceRerigerator;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TestWebService2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Service service = new Service();
		ServicePortType port = service.getServiceHttpPort();
		
		String labCode = "refrigerator2016001";
		int unitId = 1;
		
		//获取当前台位传感器类型
		ArrayOfSensorTypeInfo sensorTypeInfos = port.getTestUnitSensorTypeInfo(labCode, unitId);
//		if(null != sensorTypeInfos && null != sensorTypeInfos.getSensorTypeInfo()){
//			for(SensorTypeInfo sensorTypeInfo : sensorTypeInfos.getSensorTypeInfo()){
//				System.out.println(ToStringBuilder.reflectionToString(sensorTypeInfo));
//			}
//		}
		
		TestMetadata testMetadata = new TestMetadata();
		ArrayOfTestMetadata testMetadatas = port.getAllMetaData(labCode, unitId, 1);
		if(null != testMetadatas && null != testMetadatas.getTestMetadata()){
			//获取当前台位在测订单
			testMetadata = testMetadatas.getTestMetadata().get(0);
		}
		ArrayOfSensorInfo sensorInfos = port.getSensorInformation(labCode, testMetadata.getTestIdentification());
		for(SensorInfo sensorInfo : sensorInfos.getSensorInfo()){
			for(SensorTypeInfo sensorTypeInfo : sensorTypeInfos.getSensorTypeInfo()){
				if(sensorInfo.getSensorTypeId().equals(sensorTypeInfo.getSensorTypeId())){
					sensorInfo.setSensorTypeStr(sensorTypeInfo.getUnit());
				}
			}
			System.out.println(sensorInfo.getSensorId()+":"+sensorInfo.getSensorName()+"-"+sensorInfo.getSensorTypeId()+":"+sensorInfo.getSensorTypeStr());
		}
		

	}

}
