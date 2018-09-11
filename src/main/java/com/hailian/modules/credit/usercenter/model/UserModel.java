package com.hailian.modules.credit.usercenter.model;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.common.controller.ReportTimeController;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * @todo 用户表持久层处理
 * @author lzg
 * @time 2018/09/07 下午 3:20
 */
@ModelBind(table = "sys_user")
public class UserModel extends BaseProjectModel<UserModel>{
	private static final long serialVersionUID = 1L;
	public static final UserModel dao = new UserModel();//名字都叫dao，统一命名
	//根据用户名查找实体
	public List<UserModel> findByUserName(String username) {
		//防止注入
		List<Object> paras = new ArrayList<>();
		paras.add(username);
		return super.find("select * from sys_user where username=?",paras.toArray());
	}
	
}
