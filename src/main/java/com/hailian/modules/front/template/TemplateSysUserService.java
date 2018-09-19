package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;
import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
public class TemplateSysUserService extends BaseService {
	/**
	 * @todo  根绝角色获取系统人员
	 * @time   2018年9月19日 上午9:58:00
	 * @author lzg
	 * @params
	 */
	public List<CreditOrderInfo> getSysUserByRole(Object role) {
		List<CreditOrderInfo> listDetail = new ArrayList<CreditOrderInfo>();
		listDetail.addAll(CreditOrderInfo.dao.getSysUserByRole((Object)role));
		return listDetail;
	}
	
	/**
	 * @todo  系统人员
	 * @time   2018年9月19日 上午9:58:00
	 * @author lzg
	 * @params
	 */
	public String getSysUser(Object role,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<CreditOrderInfo> listDetail = getSysUserByRole(role);
		for(CreditOrderInfo detail:listDetail){
			if(selectedId!=null&&selectedId.toString().equals(detail.get("userid").toString())){
				sb.append("<option selected='selected'  value='"+detail.get("userid")+"'>"+detail.get("realname")+"</option>");
			}else{
				sb.append("<option  value='"+detail.get("userid")+"'>"+detail.get("realname")+"</option>");
			}
		}
		return sb.toString();
	}
}
