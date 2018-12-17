package com.hailian.system.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.JFlyFoxUtils;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.db.SQLUtils;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.department.DepartmentSvc;
import com.hailian.system.role.SysRole;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.StrUtils;
import com.hailian.util.encrypt.Md5Utils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

/**
 * 用户管理
 * 
 * @author flyfox 2014-2-11
 */
@ControllerBind(controllerKey = "/system/user")
public class UserController extends BaseProjectController {

	private static final String path = "/pages/system/user/user_";
	public static final String store = Config.getStr("ftp_store");//ftp文件服务器 root下存放目录
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public void index() {
		list();
	}

	public void list() {
		SysUser model = getModelByAttr(SysUser.class);
		SQLUtils sql = new SQLUtils(" from sys_user t " //
				+ " left join sys_department d on d.id = t.departid " //
				+ " where 1 = 1 and userid != 1 ");
		if (model.getAttrValues().length != 0) {
			sql.whereLike("username", model.getStr("username"));
			sql.whereLike("realname", model.getStr("realname"));
			sql.whereEquals("usertype", model.getInt("usertype"));
			sql.whereEquals("departid", model.getInt("departid"));
		}
		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by userid desc");
		} else {
			sql.append(" order by ").append(orderBy);
		}
		Page<SysUser> page = SysUser.dao.paginate(getPaginator(), "select t.*,d.name as departname ", sql.toString()
				.toString());
		List<SysUser> list = page.getList();
		for(SysUser user:list){
			String userid = user.get("userid").toString();
			 List<Object> query = Db.query("select t2.`name` as rolename from sys_user_role t LEFT JOIN sys_role t2 on t.roleid=t2.id where t.userid=?",Arrays.asList(new String[] {userid+""}).toArray());
			 user.put("rolename", query);
		}
		// 下拉框
		setAttr("departSelect", new DepartmentSvc().selectDepart(model.getInt("departid")));
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		setAttr("departSelect", new DepartmentSvc().selectDepart(0));
		render(path + "add.html");
	}

	public void view() {
		SysUser model = SysUser.dao.findById(getParaToInt());
		model.put("secretKey", new Md5Utils().getMD5(model.getStr("password")).toLowerCase());
		// 部门名称
		model.put("departname", new DepartmentSvc().getDepartName(model.getInt("departid")));
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		int userid = getParaToInt();
		// 日志添加
		SysUser model = new SysUser();
		String now = getNow();
		model.put("update_id", getSessionUser().getUserid());
		model.put("update_time", now);

		// 删除授权
		Db.update("delete from sys_user_role where userid = ? ", userid);

		model.deleteById(userid);

		UserCache.init();
		list();
	}

	public void edit() {
		SysUser model = SysUser.dao.findById(getParaToInt());

		setAttr("departSelect", new DepartmentSvc().selectDepart(model.getInt("departid")));

		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		String markFile="";
		String title_url="";
		List<File> filelist=new ArrayList<File>();
		UploadFile uploadFile=getFile("model.title_url");
		SysUser model = getModel(SysUser.class);
		SysUser findByUserName = SysUser.dao.findByUserName(model.get("username"));
		if(null!=findByUserName){
			renderMessage("登录名不允许重复！请重新修改登录名");
			return;
		}
		if(uploadFile != null){
			String ext=FileTypeUtils.getFileType(uploadFile.getOriginalFileName());//获取上传文件类型
			if(FileTypeUtils.isImg(ext)){
				filelist.add(uploadFile.getFile());
				String now=UUID.randomUUID().toString().replaceAll("-", "");
				String storePath = store+"/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
				try {
					boolean storeFtpFile = FtpUploadFileUtils.storeFtpFile(now, filelist, storePath, ip, port, userName, password);
					if(storeFtpFile){
						String FTPfileName=now+"."+ext;
						title_url="http://"+ip+"/" + storePath+"/"+FTPfileName;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					markFile+="出现未知异常，头像上传失败！";
					renderMessage(markFile);
				}
			}
			
		}
		
		Integer pid = getParaToInt();
		

		// 日志添加
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_id", userid);
		model.set("update_time", now);
		if(StringUtils.isNotBlank(title_url)){
			model.set("title_url", title_url);
		}
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("userid");
			if (StrUtils.isEmpty(model.getStr("password"))) {
				model.put("password", JFlyFoxUtils.getDefaultPassword());
			}
			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		UserCache.init();
		renderMessage("保存成功"+markFile);
	}

	/**
	 * 跳转到授权页面
	 * 
	 * 2015年4月28日 下午12:00:05 flyfox 369191470@qq.com
	 */
	public void auth() {
		int userid = getParaToInt();
		List<SysRole> list = SysRole.dao.findByWhere(" where status=1 order by sort ");

		String roleids = new UserSvc().getRoleids(userid);
		setAttr("userid", userid);
		setAttr("roleids", roleids);
		// 根结点
		setAttr("list", list);
		render(path + "auth.html");
	}

	/**
	 * 保存角色信息
	 * 
	 * 2015年4月28日 下午3:18:33 flyfox 369191470@qq.com
	 */
	public void auth_save() {
		int userid = getParaToInt("userid");
		String roleids = getPara("roleids");
		new UserSvc().saveAuth(userid, roleids, getSessionUser().getUserid());
		renderMessage("保存成功");
	}

	/**
	 * 
	 * @time   2018年9月17日 上午9:39:11
	 * @author dyc
	 * @todo   根据用户id修改订单状态
	 * @return_type   void
	 */
	public void update() {
		Integer id = getParaToInt("id");
		Integer status = getParaToInt("status");
		Integer result = SysUser.dao.updateStateById(id, status);
		if (result != -1) {
			setAttr("result", result);
			list();
		} else {
			renderText("操作失败!");
		}
	}
}
