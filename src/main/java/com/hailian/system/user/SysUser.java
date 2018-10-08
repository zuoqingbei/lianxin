package com.hailian.system.user;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.SessionUser;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Db;

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
		if(StringUtils.isNotBlank(c.getSessionUser().getStr("userid"))) {
			sql.append("where u.userid=?");
			params.add(c.getSessionUser().getStr("userid"));
		}
		return dao.findFirst(sql.toString(), params.toArray());
	}
	
	/**
	 * 根据用户名查找实体
	 * @time   2018年9月17日 上午11:29:12
	 * @author dyc
	 * @todo   TODO
	 * @return_type   SysUser
	 */
	public SysUser findByUserName(String username) {
		List<Object> params = new ArrayList<>();
		params.add(username);
		return super.findFirstByWhere(" where username=?", params.toArray());
	}
	
	/**
	 * 根据用户ID和参数修改状态
	 * @time   2018年9月17日 上午11:28:05
	 * @author dyc
	 * @todo   TODO
	 * @return_type   Integer
	 */
	public Integer updateStateById(Integer id,Integer status) {
		StringBuffer sql = new StringBuffer("update sys_user set state=? ");
		List<Object> params=new ArrayList<Object>();
		if(null != status){
			params.add(status);
		}
		sql.append("where userid=?");
		if(null != id){
			params.add(id);
		}
		return Db.update(sql.toString(),params.toArray());
	}
	
	/**
	 * 根据角色获取
	 * @return
	 */
	public List<SysUser> getSysUserByRole(Object role) {
		String sql="select c.* from sys_user c left join sys_user_role r on c.userid=r.userid where c.del_flag='0' and r.roleid='"+role+"' order by create_time desc" ;
		System.out.println(sql);
		return dao.find(sql);
	}
	public List<SysUser> getReporter() {
		String sql="select c.* from sys_user c left join sys_user_role r on c.userid=r.userid where c.del_flag='0' and r.roleid='2' order by create_time desc" ;
		System.out.println(sql);
		return dao.find(sql);
	}
	/**
	 * 根据角色ID获取
	 * @return
	 */
	public SysUser getSysUserById(Object userid) {
		return dao.findFirst("select c.* from sys_user c where c.del_flag='0' and userid="+userid);
	}
}
