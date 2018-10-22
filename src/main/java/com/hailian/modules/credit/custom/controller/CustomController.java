package com.hailian.modules.credit.custom.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.hailian.system.dict.DictController;
import com.hailian.system.dict.SysDictDetail;
import com.jfinal.plugin.activerecord.Page;
@ControllerBind(controllerKey = "/credit/custom")
public class CustomController extends BaseProjectController {
	private static final String path = "/pages/credit/custom/custom_";
	public void index() {
		list();
	}
	/**
	 * 列表展示
	* @author doushuihai  
	* @date 2018年9月3日下午2:32:30  
	* @TODO
	 */
	public void list() {
		CustomInfoModel attr = getModelByAttr(CustomInfoModel.class);
		String keyword = getPara("keyword");
		String orderBy = getBaseForm().getOrderBy();
		Page<CustomInfoModel> page = CustomService.service.getPage(getPaginator(),orderBy,keyword,this);
		setAttr("page",page);
		render(path+"list.html");
	}
	/**
	 * 查看
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:16  
	* @TODO
	 */
	public void view() {
//		CustomInfoModel item = CustomInfoModel.dao.findById(getParaToInt());
		CustomInfoModel item=CustomInfoModel.dao.getCustomById(getParaToInt());
		setAttr("item", item);
		render(path + "view.html");
	}
	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:28  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		CustomInfoModel attr = getModel(CustomInfoModel.class);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("model", attr);
		setAttr("company", company);
		render(path + "add.html");
	}
	/**
	 * 编辑跳转
	* @author doushuihai  
	* @date 2018年9月4日下午2:45:44  
	* @TODO
	 */
	public void edit() {
		Integer paraToInt = getParaToInt();
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
		setAttr("model", model);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("company", company);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除
	* @author doushuihai  
	* @date 2018年9月4日下午2:46:04  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		CustomService.service.delete(id,userid);//记录上传信息
		list();
	}
	/**
	 * 保存
	* @author doushuihai  
	* @date 2018年9月4日下午3:00:29  
	* @TODO
	 */
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		CustomInfoModel model = getModel(CustomInfoModel.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by", userid);
		model.set("update_date", now);
		if (pid != null && pid > 0) { // 更新
			model.update();
		} else { // 新增
			model.remove("id");
			model.set("create_by", userid);
			model.set("create_date", now);
			model.save();
		}
		renderMessage("保存成功");
	}
	
	/**
	 * 
	* @Description: 充值记录
	* @date 2018年10月18日 上午9:37:41
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void pay(){
		Integer paraToInt = getParaToInt();
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
		List<SysDictDetail>  details=SysDictDetail.dao.getDictByType("currency");
		setAttr("model", model);
		setAttr("dict", details);
		// 查询下拉框
		render(path + "pay_add.html");
	}
	/**
	 * 
	* @Description: 支付保存
	* @date 2018年10月19日 下午2:02:42
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void paySave(){
		Integer paraToInt = Integer.parseInt(getPara("id"));
		CustomInfoModel Infomodel = CustomInfoModel.dao.findById(paraToInt);
		CustomInfoModel model =getModel(CustomInfoModel.class);
	    int count =	Integer.parseInt(getPara("count")); //充值点数
	    String money =  getPara("money"); //充值金额 
	    String currency = getPara("currency");//充值币种
	    String remarks = getPara("remarks");
	    String now = getNow();
	    Integer userid = getSessionUser().getUserid();
	    int account_count=0;
	    if (Infomodel.get("account_count")!=null) {
	 	    account_count=  Integer.parseInt(Infomodel.get("account_count").toString());	
		}
	    model.set("account_count", count+account_count);
	    model.set("money_updatetime", now);
	    model.set("update_by", userid);
	    model.update();
		//充值新增流水记录表，修改客户当前账户点数与金额更新时间
		CustomTranFlowModel model2=getModel(CustomTranFlowModel.class);
		  model2.remove("id");
		  model2.set("custom_id", model.get("id"));
		  model2.set("transaction_type", "充值");
		  model2.set("money",money);
		  model2.set("currency", currency);
		  model2.set("oper_point_num", getPara("count"));
		  model2.set("oper_point_after_num", count+account_count);
		  model2.set("remark", remarks);
		  model2.set("create_time", now);
		  model2.set("create_by", userid);
		  model2.save();
		renderMessage("充值成功");
	}
	
	/**
	 * 
	* @Description: 扣款记录
	* @date 2018年10月18日 上午9:39:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void charge(){
		Integer paraToInt = getParaToInt();
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
		setAttr("model", model);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("company", company);
		// 查询下拉框
		render(path + "charge_add.html");
	}
	/**
	 * 
	* @Description: 扣款保存
	* @date 2018年10月19日 下午2:02:22
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void chargeSave(){
			Integer paraToInt = Integer.parseInt(getPara("id"));
			CustomInfoModel model=getModel(CustomInfoModel.class);
			CustomInfoModel infoModel = CustomInfoModel.dao.findById(paraToInt);
			int count =	Integer.parseInt(getPara("count")); //充值点数
		    String remarks = getPara("remarks");
		    String now = getNow();
		    Integer userid = getSessionUser().getUserid();
		   //修改当前账户点数
		    int account_count=0;
		    if (infoModel.get("account_count").toString()!=null) {
		 	    account_count=  Integer.parseInt(infoModel.get("account_count").toString());	
			}
		    
		   if ("508".equals(infoModel.get("is_arrearage"))&&account_count-count<0) {
			   renderMessage("扣款失败，该客户不允许欠费");		
			}else {
		    model.set("account_count", account_count-count);
		    model.set("money_updatetime", now);
		    model.set("update_by", userid);
		    model.update();
		  //交易流水  
		  CustomTranFlowModel model2=getModel(CustomTranFlowModel.class);
		  model2.remove("id");
		  model2.set("custom_id", model.get("id"));
		  model2.set("transaction_type", "扣款");
		  model2.set("oper_point_num", getPara("count"));
		  model2.set("oper_point_after_num",account_count-count);
		  model2.set("remark", remarks);
		  model2.set("create_time", now);
		  model2.set("create_by", userid);
		  model2.save();
		  renderMessage("扣款成功");
			}
	}
}
