package com.hailian.modules.credit.usercenter.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.system.user.SysUser;

public class HomeService {
	public static HomeService service = new HomeService();//名字都叫service，统一命名

	public SysUser getUser(BaseProjectController c) {
		
		return SysUser.dao.getUser(c);
	}

	
}
