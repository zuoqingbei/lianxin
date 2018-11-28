package com.hailian.modules.credit.companychangeitem.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.companychangeitem.model.ChangeitemModel;
import com.hailian.modules.credit.companychangeitem.service.ChangeitemService;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
import com.hailian.modules.credit.translate.model.TranslateModel;
import com.hailian.modules.credit.translate.service.TranslateService;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
import com.hailian.modules.credit.whilte.service.ArchivesWhilteService;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单维护接口
* @author doushuihai  
* @date 2018年9月3日下午2:32:56  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/changeitem")
public class ChangeitemController extends BaseProjectController{
	private static final String path = "/pages/credit/changeitem/changeitem_";
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
		ChangeitemModel attr = getModelByAttr(ChangeitemModel.class);
		String nonessentialitems = attr.getStr("nonessentialitems");
		String orderBy = getBaseForm().getOrderBy();
		Page<ChangeitemModel> page = ChangeitemService.service.getPage(getPaginator(),orderBy,nonessentialitems,this);
		setAttr("page",page);
		render(path+"list.html");
	}

	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月3日下午2:40:00  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		ChangeitemModel attr = getModel(ChangeitemModel.class);
		setAttr("model", attr);
		render(path + "add.html");
	}
	/**
	 * 编辑跳转
	* @author doushuihai  
	* @date 2018年9月3日下午2:45:58  
	* @TODO
	 */
	public void edit() {
		ChangeitemModel model = ChangeitemService.service.getChangeitem(getParaToInt());
		setAttr("model", model);
		// 查询下拉框
		render(path + "edit.html");
	}
	/**
	 * 删除
	* @author doushuihai  
	* @date 2018年9月3日下午2:53:44  
	* @TODO
	 */
	public void delete() {
		// 日志添加
		Integer id = getParaToInt();
		Integer userid = getSessionUser().getUserid();
		ChangeitemService.service.delete(id,userid);//记录上传信息
		list();
	}
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		ChangeitemModel model = getModel(ChangeitemModel.class);
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
}
