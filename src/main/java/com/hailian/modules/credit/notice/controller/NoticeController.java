package com.hailian.modules.credit.notice.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.mail.service.MailService;
import com.hailian.modules.credit.notice.model.NoticeLogModel;
import com.hailian.modules.credit.notice.model.NoticeModel;
import com.hailian.modules.credit.notice.service.NoticeService;
import com.hailian.modules.credit.resetpassword.controller.ResetPassWordController;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.user.SysUser;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
/**
 * 公告
* @author doushuihai  
* @date 2018年10月24日下午5:13:52  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/sysuser/notice")
public class NoticeController extends BaseProjectController {
	private static Logger logger=Logger.getLogger(ResetPassWordController.class);
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public static final String searver_port = Config.getStr("searver_port");//域用户密码
	public static final String ftp_store = Config.getStr("ftp_store");//域用户密码
	private static final String path = "/pages/credit/notice/";
	public void index() {
		String status = getPara("status");
		Integer userid = getSessionUser().getUserid();
		Page<NoticeModel> page = NoticeService.service.getPage(getPaginator(),this,status,userid);
		setAttr("page",page);
		render(path+"msgManage.html");
	}
	
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:32:30  
	* @TODO
	 */
	public void list() {
		Integer pageNo = getParaToInt("pageNo");
		String status = getPara("status");
		Integer userid = getSessionUser().getUserid();
		Page<NoticeModel> page = NoticeService.service.getPage(getPaginator(),this,status,userid);
		renderJson(page);
	}

	/**
	 * 保存
	* @author doushuihai  
	 * @throws UnsupportedEncodingException 
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() throws UnsupportedEncodingException {
		// 日志添加
		NoticeModel model = getModel(NoticeModel.class);
		String str = model.getStr("notice_content");
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		 // 新增
		model.set("create_by", userid);
		model.set("create_date", now);
		boolean save = model.save();
		
		int noticeId=model.get("id");
		List<SysUser> sysUserList = SysUser.dao.getSysUser(null);
		//对所有用户对此公告设置未读
		for(SysUser user:sysUserList){
			int useridAll=user.get("userid");
			NoticeLogModel logModel=new NoticeLogModel();
			logModel.set("user_id", useridAll);
			logModel.set("notice_id", noticeId);
			logModel.set("read_unread", "1");
			logModel.save();
		}
		if(!save){
			ResultType resultType = new ResultType(2,"操作失败");
			renderJson(resultType);
		}else{
			ResultType resultType = new ResultType(1,"消息发布成功");
			renderJson(resultType);
		}
	}
	/**
	 * 查看公告
	* @author doushuihai  
	* @date 2018年10月25日上午9:51:29  
	* @TODO
	 */
	public void view() {
		List<NoticeModel> notice=NoticeModel.dao.getNotice(getParaToInt(),this);
		renderJson(notice);
	}
	/**
	 * 标记为已读
	* @author doushuihai  
	* @date 2018年10月30日下午2:30:47  
	* @TODO
	 */
	public void toReadAll(){
		String noticeId = getPara("noticeId");
		Integer userid = getSessionUser().getUserid();
		String sql="update credit_notice_log set read_unread=0 where user_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		if(StringUtils.isNotBlank(noticeId)){
			sql+=" and notice_id=?";
			params.add(noticeId);
		}
		int update = Db.update(sql,params.toArray());
		if(update<=0){
			ResultType resultType = new ResultType(2,"操作失败");
			renderJson(resultType);
		}else{
			ResultType resultType = new ResultType(1,"标记成功");
			renderJson(resultType);
		}
	}
	public void releaseNotice() {
		render(path+"createMsg.html");
	}
	public void imgUpload(){
		
		UploadFile uploadFile = getFile("file");//从前台获取文件
		List<File> ftpfileList=new ArrayList<File>();
		ftpfileList.add(uploadFile.getFile());
		String now=UUID.randomUUID().toString().replaceAll("-", "");
		String ext = FileTypeUtils.getFileType(uploadFile.getOriginalFileName());
		String FTPfileName=now+"."+ext;
		String storePath = ftp_store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
		try {
			boolean storeFile = FtpUploadFileUtils.storeFtpFile(now,ftpfileList,storePath,ip,port,userName,password);
			if(storeFile){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("errno", 0);
				List<String> urls = new ArrayList<>();
				String url="http://"+ip+":"+searver_port+"/"+storePath+"/"+FTPfileName;
				urls.add(url);
			    map.put("data", urls);
			    renderJson(map);
			}else{
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("errno", 1);
				List<String> urls = new ArrayList<>();
				String url="http://"+ip+":"+searver_port+"/"+storePath+"/"+FTPfileName;
				urls.add(url);
			    map.put("data", urls);
			    renderJson(map);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}//上传
	}


}
