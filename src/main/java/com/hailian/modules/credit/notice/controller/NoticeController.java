package com.hailian.modules.credit.notice.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
/**
 * 公告
* @author doushuihai  
* @date 2018年10月24日下午5:13:52  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/sysuser/notice")
public class NoticeController extends BaseProjectController {
	private static Logger logger=Logger.getLogger(ResetPassWordController.class);
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
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		NoticeModel model = getModel(NoticeModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
		}
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
		renderMessage("保存成功");
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
		Integer userid = getSessionUser().getUserid();
		String sql="update credit_notice_log set read_unread=0 where user_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		int update = Db.update(sql,params.toArray());
		if(update<=0){
			ResultType resultType = new ResultType(2,"操作失败");
			renderJson(resultType);
		}else{
			ResultType resultType = new ResultType(1,"标记成功");
			renderJson(resultType);
		}
	}




}
