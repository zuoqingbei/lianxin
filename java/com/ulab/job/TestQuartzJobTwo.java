package com.ulab.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ulab.model.OrderNumModel;
/**
 * 
 * @time   2017年5月25日 上午10:41:40
 * @author zuoqb
 * @todo   定时器测试
 * 新增的定时器需要在quartz.properties进行配置
 */
public class TestQuartzJobTwo implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		 System.out.println("我是第二个定时任务 " + new Date());
		 if(isBelong()){
			 Date now=new Date();
			 int index=now.getSeconds()/6;
			 System.out.println(now.getSeconds()+"---"+index);
			 List<OrderNumModel> list=OrderNumModel.dao.findOrderNums();
			 for(OrderNumModel order:list){
				 int change=Integer.parseInt(order.getStr("interval").split(",")[index]);
				 if(change>0){
					 order.set("now_num", Integer.parseInt(order.get("now_num")+"")+change).update();
				 }
			 }
		 }
	}
	public boolean isBelong(){

	    SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
	    Date now =null;
	    Date beginTime = null;
	    Date endTime = null;
	    try {
	        now = df.parse(df.format(new Date()));
	        beginTime = df.parse("9:00");
	        endTime = df.parse("24:00");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Boolean flag = belongCalendar(now, beginTime, endTime);
	    return flag;
	}


	/**
	     * 判断时间是否在时间段内
	     * @param nowTime
	     * @param beginTime
	     * @param endTime
	     * @return
	     */
	    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
	        Calendar date = Calendar.getInstance();
	        date.setTime(nowTime);

	        Calendar begin = Calendar.getInstance();
	        begin.setTime(beginTime);

	        Calendar end = Calendar.getInstance();
	        end.setTime(endTime);

	        if (date.after(begin) && date.before(end)) {
	            return true;
	        } else {
	            return false;
	        }
	    }

}
