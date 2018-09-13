package com.hailian.system.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.SessionUser;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "sys_user", key = "userid")
public class SysUser extends SessionUser<SysUser> {
	
	private static final long serialVersionUID = 1L;
	private String departName;
	private String roleName;
	
	public void setDepartName(String departName) {
		set("departName",departName);
	}
	public String getDepartName() {
		return get("departName");
	}
	public void setRoleName(String roleName) {
		set("roleName",roleName);
	}
	public String getRoleName() {
		return get("roleName");
	}
	public static final SysUser dao = new SysUser();
	public SysUser getUser(BaseProjectController c) {
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		sql.append("select u.* ,s.name as departName,s1.name as roleName from sys_user u ");
		sql.append("left join sys_department s on u.departid=s.id ");
		sql.append("left join sys_role s1 on u.usertype=s1.id ");
		if(StringUtils.isNotBlank(c.getSessionUser().getStr("id"))) {
			sql.append("where u.id=?");
			params.add(c.getSessionUser().getStr("id"));
		}
		return dao.findFirst(sql.toString(), params.toArray());
	}
	
	/**
	 * lzg
	 * @param username
	 * @return
	 */
	//根据用户名查找实体
	public SysUser findByUserName(String username) {
		List<Object> params = new ArrayList<>();
		params.add(username);
		return super.findFirstByWhere(" where username=?", params.toArray());
	}
	

}
