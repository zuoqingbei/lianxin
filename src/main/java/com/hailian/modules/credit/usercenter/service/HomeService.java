package com.hailian.modules.credit.usercenter.service;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditOrderHistory;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;

public class HomeService {
	public static HomeService service = new HomeService();//名字都叫service，统一命名

	public SysUser getUser(BaseProjectController c) {
		
		return SysUser.dao.getUser(c);
	}
	/**
	 * 
	 * @time   2018年9月21日 上午9:56:03
	 * @author yangdong
	 * @todo   TODO 历史记录表,根据orderid获取历史记录
	 * @param  @param id
	 * @param  @return
	 * @return_type   CreditOrderHistory
	 */
	public List<CreditOrderHistory> getHistroy(String orderid) {
		// TODO Auto-generated method stub
		return CreditOrderHistory.dao.getHistroy(orderid);
	}
	/**
	 * 
	 * @time   2018年9月21日 上午9:55:28
	 * @author yangdong
	 * @todo   TODO 在字典表中根据id获取地区
	 * @param  @param id
	 * @param  @return
	 * @return_type   SysDictDetail
	 */
	public SysDictDetail getContinent(String id) {
		
		return SysDictDetail.dao.findById(id);
	}

	
}
