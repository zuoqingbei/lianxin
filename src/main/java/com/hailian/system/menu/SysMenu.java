package com.hailian.system.menu;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "sys_menu")
public class SysMenu extends BaseProjectModel<SysMenu> {

	private static final long serialVersionUID = 1L;
	public static final SysMenu dao = new SysMenu();
	
	public List<SysMenu> findFristMenu(int id){
		String sql="select t.name,t.id from sys_menu t where t.status=1 and t.parentid=?";
		return dao.find(sql,id);
	}

	public SysMenu findById2(int id) {
		String sql="select t.name,t.*,ifnull(d.name,'根目录') as parentname from sys_menu t left join sys_menu d on d.id = t.parentid where t.status=1 and t.id=?";
		return dao.findFirst(sql,id);
	}

}