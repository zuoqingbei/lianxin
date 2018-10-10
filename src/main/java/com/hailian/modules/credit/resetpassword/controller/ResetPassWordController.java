package com.hailian.modules.credit.resetpassword.controller;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.JFlyFoxUtils;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.system.user.SysUser;
@Api(tag = "重置密码", description = "重置密码")
@ControllerBind(controllerKey = "/credit/sysuser/resetpassword")
public class ResetPassWordController extends BaseProjectController{
	private static final String path = "/pages/credit/usercenter/";
		public void index() {
			render(path+"passward_change.html");
		}
	/**
	 * 发送验证码
	* @author doushuihai  
	* @date 2018年10月10日上午11:38:21  
	* @TODO
	 */
	@Params(value = { 
			@Param(name = "recipientAddress", description = "收件人邮箱", required = false, dataType = "String"),
			})
	@ApiOperation(url = "/credit/sysuser/resetpassword/getMailCode", httpMethod = "get", description = "获取邮箱验证码")
	public void getMailCode() throws Exception {
		String recipientAddress = getPara("recipientAddress");
		String sendMailCode = SendMailUtil.sendMailCode(recipientAddress);
		setSessionAttr("sendMailCode", sendMailCode);
		renderJson(sendMailCode);
	}
	/**
	 * 验证码校验
	* @author doushuihai  
	* @date 2018年10月10日上午11:53:43  
	* @TODO
	 */
	@Params(value = { 
			@Param(name = "confirmCode", description = "填写的验证码", required = false, dataType = "String"),
			})
	@ApiOperation(url = "/credit/sysuser/resetpassword/verifyMailCode", httpMethod = "get", description = "校验验证码")
	public void verifyMailCode(){
		boolean flag=false;
		String trueCode=getSessionAttr("sendMailCode");
		String confirmCode=getPara("confirmCode");
		if(confirmCode.equals(trueCode)){
			flag=true;
		}
		if(flag){
			ResultType resultType = new ResultType();
			renderJson(resultType);
		}else{
			String msg="验证码错误";
			ResultType resultType = new ResultType(2, msg);
			renderJson(resultType);
		}
	}
	/**
	 * 
	* @author doushuihai  
	* @date 2018年10月10日下午1:36:59  
	* @TODO
	 */
	@Params(value = { 
			@Param(name = "password", description = "一次密码", required = false, dataType = "String"),
			@Param(name = "passwordConfirm", description = "二次密码", required = false, dataType = "String")
			})
	@ApiOperation(url = "/credit/sysuser/resetpassword/resetPassword", httpMethod = "get", description = "修改密码")
	public void resetPassword(){
		String password=getPara("password");
		String passwordConfirm=getPara("passwordConfirm");
		if(!password.equals(passwordConfirm)){
			String msg="两次密码输入不一致";
			ResultType resultType = new ResultType(2, msg);
			renderJson(resultType);
		}else{
			Integer userid = getSessionUser().getUserid();
			String targetPwd = 	JFlyFoxUtils.passwordEncrypt(password);
			int isSuccess = SysUser.dao.updatePwdById(userid, targetPwd);
			if(isSuccess>0){
				ResultType resultType = new ResultType();
				renderJson(resultType);
			}else{
				String msg="修改密码失败";
				ResultType resultType = new ResultType(2, msg);
				renderJson(resultType);
			}
			
		}
	}
}
