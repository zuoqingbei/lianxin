package com.hailian.modules.credit.orderflowconf.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.orderflowconf.model.CreditOrderFlowConf;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
 * @className OrderFlowConfController.java
 * @time   2018年10月16日 下午4:06:46
 * @author yangdong
 * @todo   TODO 订单流程管理
 */
@ControllerBind(controllerKey = "/credit/orderflowconf")
public class OrderFlowConfController extends BaseProjectController{
	private static final String path="/pages/credit/orderflowconf/orderflowconf_";
	
	public void index() {
		list();
	}
	/**
	 * 
	 * @time   2018年10月16日 下午4:06:35
	 * @author yangdong
	 * @todo   订单流程列表展示
	 * @param  
	 * @return_type   void
	 */
	public void list() {
		CreditOrderFlowConf model = getModelByAttr(CreditOrderFlowConf.class);
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		Page<CreditOrderFlowConf> page=CreditOrderFlowConf.dao.getOrderFlowConfs(pageinator,model,orderBy, this);	
		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}
	/**
	 * 
	 * @time   2018年10月16日 下午4:22:26
	 * @author yangdong
	 * @todo   TODO订单流程详情
	 * @param  
	 * @return_type   void
	 */
	public void view() {
		int id=getParaToInt("id");
		CreditOrderFlowConf model=CreditOrderFlowConf.dao.findOrderFlowById(id);
		setAttr("model", model);
		render(path + "view.html");
	}
	/**
	 * 
	 * @time   2018年10月16日 下午4:23:50
	 * @author yangdong
	 * @todo   TODO跳转添加修改订单流程页面
	 * @param  
	 * @return_type   void
	 */
	public void add() {
		Integer id=getParaToInt("id");
		if(id==null) {
			setAttr("model",new CreditOrderFlowConf());
			render(path + "add.html");
		}else {
			CreditOrderFlowConf model=CreditOrderFlowConf.dao.findById(id);
			setAttr("model", model);
			render(path + "edit.html");
		}
	}
	/**
	 * 
	 * @time   2018年10月16日 下午5:32:43
	 * @author yangdong
	 * @todo   TODO保存订单流程
	 * @param  
	 * @return_type   void
	 */
	public void save() {
		CreditOrderFlowConf model = getModelByAttr(CreditOrderFlowConf.class);
		String remarks=model.getStr("remarks").trim();
		Integer id=getParaToInt("id");
		//获取当前时间
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(now);
		//获取登录人
		SysUser user = (SysUser) getSessionUser();
		if(id!=0) {
			try {
			model.set("id", id);
			//更新时间
			model.set("update_date", date);
			//更新人
			model.set("update_by", user.get("userid").toString());
			model.set("remarks", remarks);
			model.update();
			renderMessage("更新成功");
			}catch(Exception e) {
				e.printStackTrace();
				renderMessageByFailed("更新失败,请重试");				
			}
		}else {
			try {
			//更新时间
			model.set("create_date", date);
			//更新人
			model.set("create_by", user.get("userid").toString());
			model.set("remarks", remarks);
			model.save();
			renderMessage("保存成功");
			}catch(Exception e) {
				e.printStackTrace();
				renderMessageByFailed("保存失败,请重试");
			}
		}
	}
	/**
	 * 
	 * @time   2018年10月16日 下午7:05:02
	 * @author yangdong
	 * @todo   TODO删除
	 * @param  
	 * @return_type   void
	 */
	public void del() {
		Integer id=getParaToInt("id");
		CreditOrderFlowConf model=CreditOrderFlowConf.dao.findById(id);
		try {
			model.set("del_flag", "1");
			model.update();
			renderMessage("删除成功");
			}catch(Exception e) {
				e.printStackTrace();
				renderMessageByFailed("删除失败,请重试");
			}
	}
}
