package com.hailian.modules.credit.usercenter.controller.finance;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public class CreateHistory {
		public static void main(String[] args) {
			Map<Integer,String> sourceMap = new HashMap<>();
			sourceMap.put(0,"客服中心");
			sourceMap.put(1,"系统管理");
			sourceMap.put(2,"系统管理2");
			sourceMap.put(3,"业务部门");
			sourceMap.put(4,"组业务");
			sourceMap.put(5,"个人信息");
			sourceMap.put(6,"用户帮助");
			
			
			
			List<Map<String,int[]>> fatherList = new ArrayList<>();
			Map<String,int[]> fatherMap = new HashMap<>();
			fatherMap.put("客服中心", new int[] {5,0});
			fatherMap.put("系统管理", new int[] {8,6}); 
			fatherMap.put("系统管理2", new int[] {9,14}); 
			fatherMap.put("业务部门", new int[] {6,23});  
			fatherMap.put("组业务", new int[] {2,29});  
			fatherMap.put("个人信息", new int[] {1,31});  
			fatherMap.put("用户帮助", new int[] {1,32});  
			for (String key : fatherMap.keySet()) {
				Map<String,int[]> abc = new HashMap();
				abc.put(key, fatherMap.get(key));
				fatherList.add(abc);
			}
			
			List<Map<Integer,String[]>> sonList = new ArrayList<>();
			Map<Integer,String[]> sonMap = new HashMap<Integer,String[]>();
			sonMap.put(0, new String[] {"新订单","1"});
			sonMap.put(1, new String[] {"订单分配","2"});
			sonMap.put(2, new String[] {"快速查询","3"});
			sonMap.put(3, new String[] {"订单查询","4"});
			sonMap.put(4, new String[] {"递交客户","5"});
			
			sonMap.put(6, new String[] {"客户维护","6"});
			sonMap.put(7, new String[] {"操作员管理","7"});
			sonMap.put(8, new String[] {"国内代理管理","8"});
			sonMap.put(9, new String[] {"国外代理管理","9"});
			sonMap.put(10, new String[] {"地区国家维护","10"});
			sonMap.put(11, new String[] {"页面管理","11"});
			sonMap.put(12, new String[] {"角色管理","12"});
			sonMap.put(13, new String[] {"提成设置","13"});
			
			sonMap.put(14, new String[] {"报告类型维护","14"});
			sonMap.put(15, new String[] {"内部状态维护","15"});
			sonMap.put(16, new String[] {"报告价格维护","16"});
			sonMap.put(17, new String[] {"组管理","17"});
			sonMap.put(18, new String[] {"在线充值","18"});
			sonMap.put(19, new String[] {"点数详细信息","19"});
			sonMap.put(20, new String[] {"报告价格管理","20"});
			sonMap.put(21, new String[] {"报告\\点数\\时间","21"});
			sonMap.put(22, new String[] {"假期管理","22"});
			
			sonMap.put(23, new String[] {"订单录入","23"});
			sonMap.put(24, new String[] {"订单查询","24"});
			sonMap.put(25, new String[] {"报告质检","25"});
			sonMap.put(26, new String[] {"评分及国内委托","26"});
			sonMap.put(27, new String[] {"订单查重","27"});
			sonMap.put(28, new String[] {"国际委托","28"});
			
			sonMap.put(29, new String[] {"组订单管理","29"});
			sonMap.put(30, new String[] {"订单分配(国内)","30"});
			
			sonMap.put(31, new String[] {"修改密码","31"});
			sonMap.put(32, new String[] {"全部邮件","32"});

			for (Integer key : sonMap.keySet()) {
				 Map<Integer,String[]>  abc = new HashMap<>();
				abc.put(key, sonMap.get(key));
				sonList.add(abc);
			}
			
			List<Map<Integer,String[]>> grandSonList = new ArrayList<>();
			Map<Integer,String[]>  grandSonMap = new HashMap<>();
			grandSonMap.put(1,new String[] {"上传","导入","查询"});
			grandSonMap.put(2,new String[] {"查询","国内分配（给业务员)","反馈"});
			grandSonMap.put(3,new String[] {"查询"});
			grandSonMap.put(4,new String[] {"查询" });
			grandSonMap.put(5,new String[] {"处理","自动发送（客户）","自动发送（订单）"});
			grandSonMap.put(6,new String[] {"编辑","增加","取消"});
			grandSonMap.put(7,new String[] {"编辑","增加","取消"});
			grandSonMap.put(8,new String[] {"编辑","增加","取消"});
			grandSonMap.put(9,new String[] {"编辑","增加","取消"});
			grandSonMap.put(10,new String[] {"编辑","增加","取消"});
			grandSonMap.put(11,new String[] {"导入","编辑"});
			grandSonMap.put(12,new String[] {"编辑","增加","取消"});
			grandSonMap.put(13,new String[] {"编辑","增加","取消"});
			grandSonMap.put(14,new String[] {"编辑","增加","取消"});
			grandSonMap.put(15,new String[] {"编辑","增加","取消"});
			grandSonMap.put(16,new String[] {"编辑","增加","取消"});
			grandSonMap.put(17,new String[] {"编辑","增加","取消"});
			
			grandSonMap.put(18,new String[] {"编辑"});
			grandSonMap.put(19,new String[] {"查询","打印"});
			grandSonMap.put(20,new String[] {"编辑","确定"});
			grandSonMap.put(21,new String[] {"ADD"});
			grandSonMap.put(22,new String[] {"编辑"});
			grandSonMap.put(23,new String[] {"增加"});
			grandSonMap.put(24,new String[] {"查询","批量改状态"});
			grandSonMap.put(25,new String[] {"查询","清除","查询"});
			grandSonMap.put(26,new String[] {"编辑"});
			grandSonMap.put(27,new String[] {"查询"});
			grandSonMap.put(28,new String[] {"编辑"});
			grandSonMap.put(29,new String[] {"编辑"});
			grandSonMap.put(30,new String[] {"编辑"});
			grandSonMap.put(31,new String[] {"更新"});
			grandSonMap.put(32,new String[] {"彻底删除"});
			for (Integer key : grandSonMap.keySet()) {
				 Map<Integer,String[]>  abc = new HashMap<>();
				abc.put(key, grandSonMap.get(key));
				grandSonList.add(abc);
			}
			
			File file = new File("D:/操作日志.xlsx");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date startTime = null;
			Date endTime = null;
			try {
				startDate = sdf3.parse("2018-5-26");
				startTime = sdf2.parse("08:59");
				endTime = sdf2.parse("18:23");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			//一天的毫秒数
			int day = 86400000; 
			//一小时的毫秒
			int hour = 3600000; 
			//一分钟的毫秒
			int minute = 60000; 
			//总天数
			int totalDay = 178;
			Random rd = new Random();
			long addMinute = minute;
			//每天增加的上限条数
			int countEntry = 0;
			//员工id
			int[] tempIds = new int[] {4,9,12,13,20,21,23,27,30};
			
			try {
				String format = "HH:mm:ss";
		        Date aa = null;
		        Date ab = null;;
		        Date ac = null;;
		        Date ad = null;;
				try {
					aa = new SimpleDateFormat(format).parse("08:50:00");
				    ab = new SimpleDateFormat(format).parse("12:19:00");
				    ac = new SimpleDateFormat(format).parse("13:01:00");
				    ad = new SimpleDateFormat(format).parse("18:20:00");
				} catch (ParseException e) {
					e.printStackTrace();
				}
		      
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("操作日志汇总");
				int rowNum = 1;
				for (int i = 0; i < totalDay; i++) {
					//当天上限
					countEntry = rd.nextInt(119)+163;
					//当天真实条数
					int realCount = 0;
					
					for (long current = startTime.getTime(); current <endTime.getTime();current+=addMinute) {
						//若增加条数超出上限或者不在时间范围内
					    if(realCount>countEntry){
					    	break;
					    }
					    boolean a = isEffectiveDate(current, aa, ab);
					    boolean b = isEffectiveDate(current, aa, ad);
					    if(!a){
					    	current += 2000000;
					    	if(!b) {
					    		break;
					    	}
					    }
					    HSSFRow row = sheet.createRow(rowNum);
					    String value = "";
					    String father = sourceMap.get(rd.nextInt(7));
					    //提高业务几率
					    if(!"组业务".equals(father)||!"业务部门".equals(father)) {
					    	if(!(rd.nextInt(4)==0)) {
					    		father = "组业务";
					    	}
					    	if(!(rd.nextInt(4)==0)) {
					    		father = "业务部门";
					    	}
					    }
					    	
					    int[] sourceSon = fatherMap.get(father);
					    int sonReal = rd.nextInt(sourceSon[0])+sourceSon[1];
					    String  sonStr = "";
					    try {
					    	    sonStr = sonMap.get(sonReal)[0];
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					   
					    String  grandSonAddress = sonMap.get(sonReal)[1];
					    String[]  grandSonArray = grandSonArray = grandSonMap.get(17);
					    //降低删除几率
					    if(Integer.parseInt(grandSonAddress)==32) {
					    	if(rd.nextInt(40)==0) {
					    		grandSonArray = grandSonMap.get(32);
					    	}
					    }
					    String grandSonReal = grandSonArray[rd.nextInt(grandSonArray.length)];
					    //降低取消几率
					    if("取消".equals(grandSonReal)&&!(rd.nextInt(4)==0)) {
					    	if(rd.nextInt(2)==0) {
					    		grandSonReal = "增加";
					    	}else {
					    		grandSonReal = "编辑";
					    	}
					    }
					    String dateStr = "";
					    if(realCount==0) {
					    	dateStr = sdf3.format(startDate)+" "+ sdf2.format(new Date(current)).substring(0, sdf2.format(new Date(current)).length()-1)+rd.nextInt(10);
					    }else {
					    	dateStr = sdf3.format(startDate)+" "+ sdf2.format(new Date(current));
					    }
					    for (int j = 0; j <5; j++) {
					    	HSSFCell cell = row.createCell(j);
					    	switch (j) {
							case 0:
								value = dateStr;
								break;
							case 1:
								value = tempIds[rd.nextInt(9)]+"";
								break;
							case 2:
								value = father;
								break;
							case 3:
								value = sonStr;
								break;
							case 4:
								value = grandSonReal;
								break;
							default:
								break;
							}
					    	cell.setCellValue(value);
					    	System.out.println("第"+realCount+"行,"+"第"+j+"列"+"值:"+value);
					    }
						//每次操作相隔毫秒数
					    addMinute = (rd.nextInt(1*minute)+45000);
					    realCount++;
					    rowNum++;
					}
					System.out.println("当天增加操作"+realCount+"条");
					System.out.println("当天上限"+countEntry+"条");
					System.out.println("共"+rowNum+"条");
					startDate = tomorrowDate( startDate );
				}
				wb.write(fos);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				
				if( fos!=null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		
		}
		 /**
	     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	     * 
	     * @param nowTime 当前时间
	     * @param startTime 开始时间
	     * @param endTime 结束时间
	     * @return
	     * @author jqlin
	     */
	    public static boolean isEffectiveDate(Long nowTime, Date startTime, Date endTime) {
	    	String format = "HH:mm:ss";
	    	SimpleDateFormat sdf = new SimpleDateFormat(format);
	        if (nowTime  == startTime.getTime()
	                || nowTime == endTime.getTime()) {
	            return true;
	        }

	        Calendar date = Calendar.getInstance();
	        try {
				date.setTime(sdf.parse(sdf.format( new Date(nowTime))));
			} catch (ParseException e) {
				e.printStackTrace();
			}

	        Calendar begin = Calendar.getInstance();
	        begin.setTime(startTime);

	        Calendar end = Calendar.getInstance();
	        end.setTime(endTime);

	        if (date.after(begin) && date.before(end)) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    
	    public static Date tomorrowDate(Date today) {  
	    	
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	    
        System.out.println("今天是:" + f.format(today));
 
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        Date tomorrow = c.getTime();
        System.out.println("明天是:" + f.format(tomorrow));
		return tomorrow;
        }
	  

}
