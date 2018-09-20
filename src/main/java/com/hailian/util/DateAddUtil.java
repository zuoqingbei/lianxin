package com.hailian.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 用于工作日日期的计算
 * @className DateAddUtil.java
 * @time   2018年9月14日 下午2:29:02
 * @author yangdong
 * @todo   TODO
 */
public class DateAddUtil {
/*	public static void main(String[] args) {
		Calendar ca = Calendar.getInstance();
		Date d = new Date();
		ca.setTime(d);//设置当前时间
		Calendar c = new DateAddUtil().addDateByWorkDay(ca,5);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(c.getTime());
		System.out.println(date);
	}*/
	
	private static List<Calendar> holidayList= new ArrayList<Calendar>();  //节假日列表
	/**
	 * 初始化节日列表
	 */
	private static String year=String.valueOf(Calendar.getInstance() .get(Calendar.YEAR));
	static {
		initHolidayList(year+"-12-30");//元旦
		initHolidayList(year+"-12-31");//元旦
		initHolidayList(year+"-1-1");//元旦
		initHolidayList(year+"-5-29");//劳动节
		initHolidayList(year+"-5-30");//劳动节
		initHolidayList(year+"-5-31");//劳动节
		initHolidayList(year+"-10-1");//国庆
		initHolidayList(year+"-10-2");//国庆
		initHolidayList(year+"-10-3");//国庆
		initHolidayList(year+"-10-4");//国庆
		initHolidayList(year+"-10-5");//国庆
		initHolidayList(year+"-10-6");//国庆
		initHolidayList(year+"-10-7");//国庆
		initHolidayList(year+"-1-1");//初始节假日
		initHolidayList(year+"-1-1");//初始节假日
		initHolidayList(year+"-1-1");//初始节假日
		initHolidayList(year+"-1-1");//初始节假日
		initHolidayList(year+"-1-1");//初始节假日
		//传统节日过一年改一次
		initHolidayList(year+"-2-15");//年假
		initHolidayList(year+"-2-16");//年假
		initHolidayList(year+"-2-17");//年假
		initHolidayList(year+"-2-18");//年假
		initHolidayList(year+"-2-19");//年假
		initHolidayList(year+"-2-20");//年假
		initHolidayList(year+"-2-21");//年假
		initHolidayList(year+"-6-18");//端午
		initHolidayList(year+"-6-16");//端午
		initHolidayList(year+"-6-17");//端午
		initHolidayList(year+"-9-22");//中秋
		initHolidayList(year+"-9-23");//中秋
		initHolidayList(year+"-9-24");//中秋
		initHolidayList(year+"-4-5");//清明
		initHolidayList(year+"-4-6");//清明
		initHolidayList(year+"-4-7");//清明
		//获取节假日,需要在能正常访问日历的网络中使用
		/*List<ChinaDate> dateList = Main.getCurrentDateInfo();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(ChinaDate date: dateList){
			initHolidayList(dateFormat.format(date.getSolarDate()));
			}*/
		
	}
	/**
	 * 
	 * @time   2018年9月14日 下午2:39:02
	 * @author yangdong
	 * @todo   TODO 计算相加day天，并且排除节假日和周末后的日期
	 * @param  @param calendar 当前日期
	 * @param  @param day 相加天数
	 * @param  @return 排除节假日后相加得到的日期
	 * @return_type   Calendar
	 */
	 public Calendar addDateByWorkDay(Calendar calendar,int day){
		 
		 try {
			for (int i = 0; i < day; i++) {
				
				 calendar.add(Calendar.DAY_OF_MONTH, 1);
				 
				 if(checkHoliday(calendar)){
					 i--;
				 }
			}
			return calendar;
			
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calendar;
	 }
	 /**
	  * 
	  * @time   2018年9月14日 下午3:40:51
	  * @author yangdong
	  * @todo   TODO  判断某一天是不是工作日
	  * @param  @param calendar
	  * @param  @return
	  * @param  @throws Exception
	  * @return_type   boolean
	  */
	 public boolean checkHoliday(Calendar calendar) throws Exception{
		 
		 //判断日期是否是周六周日
		 if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || 
				 calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			 return true;
		 }
		 //判断日期是否是节假日
		 for (Calendar ca : holidayList) {
			if(ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
					ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)&&
					ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
				return true;
			}
		}
		  
		 return false;
	 }
	 /**
	  * 
	  * @time   2018年9月14日 下午3:40:24
	  * @author yangdong
	  * @todo   TODO 维护节假日列表
	  * @param  @param date
	  * @return_type   void
	  */
	private static void initHolidayList( String date){
		
		String [] da = date.split("-");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
		calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);//月份比正常小1,0代表一月
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
		holidayList.add(calendar);
}
}
