package com.hailian.system.menu;

import java.util.ArrayList;
import java.util.List;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.db.SQLUtils;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.menu.model.FristMenu;
import com.hailian.system.menu.model.SecondMenu;
import com.hailian.system.menu.model.ThirdMenu;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 菜单
 * 
 * @author flyfox 2014-4-24
 */
@Api(tag = "菜单路由", description = "菜单")
@ControllerBind(controllerKey = "/system/menu")
public class MenuController extends BaseProjectController {

	private static final String path = "/pages/system/menu/menu_";
	MenuSvc svc = new MenuSvc();

	public void index() {
		list();
	}

	public void list() {
		SysMenu model = getModelByAttr(SysMenu.class);

		SQLUtils sql = new SQLUtils(" from sys_menu t left join sys_menu d on d.id = t.parentid where 1=1 ");
		if (model.getAttrValues().length != 0) {
			sql.setAlias("t");
			// 查询条件
			sql.whereLike("name", model.getStr("name"));
		}

		// 排序
		String orderBy = getBaseForm().getOrderBy();
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.sort,t.id desc");
		} else {
			sql.append(" order by t.").append(orderBy);
		}

		Page<SysMenu> page = SysMenu.dao.paginate(getPaginator(), "select t.*,ifnull(d.name,'根目录') as parentname ", //
				sql.toString().toString());

		// 下拉框
		setAttr("parentSelect", svc.selectMenu(model.getInt("parentid")));
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void add() {
		setAttr("parentSelect", svc.selectMenu(0));
		render(path + "add.html");
	}
	/**
	 * 
	 * @time   2018年10月31日 下午3:33:55
	 * @author yangdong 获取三级菜单下拉表
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/system/menu/add2",httpMethod="post", 
			description = "获取订单列表")
	@Params(value = { 
		@Param(name = "", description = "", required = false, dataType = "Model"),
		})
	public void add2() {
		//一级菜单列表
		List<SysMenu> frist=SysMenu.dao.findFristMenu(0);
		List<SysMenu> second=new ArrayList<SysMenu>();
		List<SysMenu> third=new ArrayList<SysMenu>();
	
		List<FristMenu> fml=new ArrayList<FristMenu>();;
		for(SysMenu sm:frist) {
			//获取二级菜单
			List<SecondMenu> sml=new ArrayList<SecondMenu>();
			List<ThirdMenu> tml=new ArrayList<ThirdMenu>();
			 second=SysMenu.dao.findFristMenu(sm.getInt("id"));
			 if(second.size()<=0) {
				 fml.add(new FristMenu(sm.getStr("name"),sm.getInt("id"))); 
				 continue;
			 }
			for(SysMenu sm2:second) {
				//获取3级菜单
				third=SysMenu.dao.findFristMenu(sm2.getInt("id"));
				if(third.size()<=0) {
					sml.add(new SecondMenu(sm2.getStr("name"),sm2.getInt("id")));
					continue;
				}
				for(SysMenu sm3:third) {
					tml.add(new ThirdMenu(sm3.getStr("name"),sm3.getInt("id")));
				}
				 sml.add(new SecondMenu(sm2.getStr("name"),sm2.getInt("id"),tml));
			}
			
			fml.add(new FristMenu(sm.getStr("name"),sm.getInt("id"),sml));
			
		}
		fml.add(new FristMenu("根目录",0));
		renderJson(fml);
		
	}

	public void view() {
		SysMenu model = SysMenu.dao.findById(getParaToInt());
		String parent = new MenuSvc().getParentName(model);
		model.put("parentname", parent);
		setAttr("model", model);
		render(path + "view.html");
	}

	public void delete() {
		// 日志添加
		SysMenu model = new SysMenu();
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);

		model.deleteById(getParaToInt());
		list();
	}

	public void edit() {
		
		
		SysMenu model = SysMenu.dao.findById2(getParaToInt());
		setAttr("parentSelect", svc.selectMenu(model.getInt("parentid")));
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();
		SysMenu model = getModel(SysMenu.class);

		// 根目录级别为1
		Integer parentid = model.getInt("parentid");
		if (parentid != null) {
			model.set("level", parentid == 0 ? 1 : 2);
		}

		// 日志添加
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		renderMessage("保存成功");
	}

}
