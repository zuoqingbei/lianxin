package com.ulab.client.webServiceRerigerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestWebService3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Service service = new Service();
		ServicePortType port = service.getServiceHttpPort();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = null;
		try {
			start = sdf.parse("2017-05-15 16:20:42");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date now = new Date();
		float f2 = (now.getTime() - start.getTime())/3600000f;
		float f1 = f2 - 24;
		
//		ArrayOfTestMetadata testMetadatas = port.getMetaData("refrigerator2016001", 1, "@@@@@@");
//		for(TestMetadata testMetadata : testMetadatas.getTestMetadata()){
//			System.out.println(ToStringBuilder.reflectionToString(testMetadata));
//			for(String input: testMetadata.getInputContent().getString()){
//				System.out.println(input);
//			}
//		}
//		ArrayOfTestUnitInfo testUnitInfos = port.getTestUnitInfo("refrigerator2016001");
//		for(TestUnitInfo testUnitInfo : testUnitInfos.getTestUnitInfo()){
//			ArrayOfSensorTypeInfo sensorTypeInfos = port.getTestUnitSensorTypeInfo("refrigerator2016001", testUnitInfo.getTestUnitId());
//			for(SensorTypeInfo sensorTypeInfo : sensorTypeInfos.getSensorTypeInfo()){
//				System.out.println(ToStringBuilder.reflectionToString(sensorTypeInfo));
//			}
//		}
//		float f1 = 0.19611111f + 1f/3600f;
//		float f2 = f1 + 1f;
//		ArrayOfString datas = port.getSensorTestDataByTime("refrigerator2016001", "2017-05-1516:20:422", f1, f2);
//		for(String data : datas.getString()){
//			System.out.println(data);
//		}
		
		ArrayOfSensorInfo sensorInfos = port.getSensorInformation("refrigerator2016001", "2017-05-1516:20:422");
		for(SensorInfo sensorInfo : sensorInfos.getSensorInfo()){
			System.out.println(ToStringBuilder.reflectionToString(sensorInfo));
		}
		

	}

}
