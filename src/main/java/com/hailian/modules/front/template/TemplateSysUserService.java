package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;
import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.system.user.SysUser;
public class TemplateSysUserService extends BaseService {
	/**
	 * @todo  根绝角色获取系统人员
	 * @time   2018年9月19日 上午9:58:00
	 * @author lzg
	 * @params
	 */
	public List<SysUser> getSysUserByRole(Object role) {
		List<SysUser> listDetail = new ArrayList<SysUser>();
		listDetail.addAll(SysUser.dao.getSysUserByRole((Object)role));
		return listDetail;
	}
	
	/**
	 * @todo  除了默认选项的选项
	 * @time   2018年9月19日 上午9:58:00
	 * @author lzg
	 * @params
	 */
	public String getSysUser(Object role,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysUser> listDetail = getSysUserByRole(role);
		for(SysUser detail:listDetail){
			if(selectedId!=null&&selectedId.toString().equals(detail.get("userid").toString())){
				sb.append("<option selected='selected'  value='"+detail.get("userid")+"'>"+detail.get("realname")+"</option>");
			}else{
				sb.append("<option  value='"+detail.get("userid")+"'>"+detail.get("realname")+"</option>");
			}
		}
		return sb.toString();
	}
	/**
	 * 生成默认选项
	 * @param selectedId
	 * @return
	 */
	public String defaultOption(Object selectedId){
		SysUser defaultUser = SysUser.dao.getSysUserById(selectedId);
		return "<option selected='selected'  value='"+defaultUser.get("userid")+"'>"+defaultUser.get("realname")+"</option>";
	}
	
	
}