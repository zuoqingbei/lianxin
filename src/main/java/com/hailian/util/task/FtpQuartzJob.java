/*package com.hailian.util.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hailian.modules.credit.company.model.FtpErrorModel;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.util.Config;
import com.hailian.util.FtpUploadFileUtils;

*//**
 * 
 * @time   2017年5月25日 上午10:41:40
 * @author zuoqb
 * @todo   定时器测试
 * 新增的定时器需要在quartz.properties进行配置
 *//*
public class FtpQuartzJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 FTPClient ftp =  FtpUploadFileUtils.getFtpConnection(Config.getStr("ftp_ip"),Integer.valueOf( Config.getStr("ftp_port")),
				 Config.getStr("ftp_userName"), Config.getStr("ftp_password"),0l);
		// 判断返回码是否合法
         boolean replyCodeFlag = FTPReply.isPositiveCompletion(ftp.getReplyCode());
		if (!replyCodeFlag) {
			// 不合法时断开连接
			System.err.println(sdf.format(new Date())+"---ftp不可用");
			 FtpErrorModel m=new FtpErrorModel();
			 m.put("error_date", sdf.format(new Date()));
			 m.save();
			 String content = " FTP异常，请及时处理!";
			 try {
				new SendMailUtil("1348898564@qq.com", "", "FTP连接异常", content).sendEmail();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println(sdf.format(new Date())+"---ftp正常");
		}
		try {
			// 判断连接是否存在
			if (ftp.isConnected()) {
	            ftp.logout();
				// 断开连接
				ftp.disconnect();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
*/