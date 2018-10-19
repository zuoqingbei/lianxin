package com.hailian.modules.credit.orderallocation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feizhou.swagger.annotation.Api;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.agentmanager.service.AgentService;
import com.hailian.modules.credit.city.model.CityModel;
import com.hailian.modules.credit.orderallocation.model.OrderAllocationRuleModel;
import com.hailian.modules.credit.orderallocation.service.OrderAlloctionRuleService;
import com.hailian.modules.credit.province.model.ProvinceModel;
import com.hailian.system.dict.SysDictDetail;
import com.jfinal.plugin.activerecord.Page;
/**
 * 
* @Title: OrderAlloctionRuleController  
* @Description:  订单分配规则
* @author lxy
* @date 2018年10月16日  下午1:49:44
 */
@Api(tag = "订单分配规则", description = "订单分配规则")
@ControllerBind(controllerKey = "/credit/orderrule")
public class OrderAlloctionRuleController extends BaseProjectController{
	private static final String path = "/pages/credit/orderallocationrule/orderrule_";
	public void index() {
		list();
	}
	/**
	 * 
	* @Description: 列表页
	* @date 2018年10月16日 下午1:51:39
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		String orderBy = getBaseForm().getOrderBy();
		OrderAllocationRuleModel attr = getModelByAttr(OrderAllocationRuleModel.class);
		
		Page<OrderAllocationRuleModel> pager = OrderAlloctionRuleService.service.getRule( orderBy, getPara("keyWord"), this);
		setAttr("page", pager);
		setAttr("attr", attr);
		render(path + "list.html");

	}
	/**
	 * 
	* @Description: 新增
	* @date 2018年10月16日 下午1:51:55
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void add() {
		OrderAllocationRuleModel model = getModel(OrderAllocationRuleModel.class);
		setAttr("model", model);
		List<SysDictDetail> dic = SysDictDetail.dao.getDictByType("orderAllocationRule");//获取全部省份
		setAttr("dicDetail", dic);
		render(path + "add.html");
	}
	
	/**
	 * 
	* @Description: 编辑
	* @date 2018年10月16日 下午1:53:05
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void edit() {
		Integer para =Integer.parseInt(getPara("id"));
		OrderAllocationRuleModel model = OrderAllocationRuleModel.dao.findById(para);
		setAttr("model", model);
		List<SysDictDetail> dic = SysDictDetail.dao.getDictByType("orderAllocationRule");//获取全部省份
		setAttr("dicDetail", dic);
		
		render(path + "edit.html");
	}
	/**
	 * 
	* @Description:修改或新增保存
	* @date 2018年10月16日 下午1:53:23
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void save() {
		Integer id = getParaToInt("order_allocation_id");
		Integer id2=Integer.parseInt(getPara("allocation_id"));
		OrderAllocationRuleModel model = getModel(OrderAllocationRuleModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("order_allocation_id", id);
		map.put("update_by", userid);
		map.put("update_date", now);
		map.put("newid", id2);
		map.put("allocation_value", getPara("allocation_value"));
		if (id != null && id > 0) { // 更新
			OrderAlloctionRuleService.service.updateid(map);
			renderMessage("修改成功");
		}else  {
		    OrderAllocationRuleModel orderModel=	OrderAlloctionRuleService.service.getOne(id2, null);
			if (orderModel==null) {
				 model.remove("order_allocation_id");
					model.set("create_by", userid);
					model.set("create_date", now);
					model.set("order_allocation_id", id2);
					model.set("allocation_value", getPara("allocation_value"));
					model.save();
					renderMessage("保存成功");
			}else {
				renderMessage("保存失败，已存在该订单规则");
			}
		   
			
		} 
	}
	
	/**
	 * 
	* @Description: 单条回显
	* @date 2018年10月16日 下午1:53:43
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void view() {
		Integer id = Integer.parseInt(getPara("id"));
		OrderAllocationRuleModel model = OrderAlloctionRuleService.service.getOne(id, null);
		setAttr("model", model);
		render(path + "view.html");
	}
	
	/**
	 * 
	* @Description: 删除
	* @date 2018年10月16日 下午1:54:05
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void delete() {
		Integer id = getParaToInt();
		if (OrderAlloctionRuleService.service.updateDelFlagById(id)) {
			list();
		} else {
			renderText("failure");
		}
	}
}
