package com.hailian.modules.credit.whilte.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.whilte.model.ArchivesWhilteModel;
import com.hailian.modules.credit.whilte.service.ArchivesWhilteService;
import com.jfinal.plugin.activerecord.Page;
/**
 * 白名单维护接口
* @author doushuihai  
* @date 2018年9月3日下午2:32:56  
* @TODO
 */
@ControllerBind(controllerKey = "/credit/whilte")
public class WhilteController extends BaseProjectController{
	private static final String path = "/pages/credit/whilte/whilte_";
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
		ArchivesWhilteModel attr = getModelByAttr(ArchivesWhilteModel.class);
		String custom_id = attr.getStr("custom_id");
		String report_id = attr.getStr("report_id");
		Page<ArchivesWhilteModel> page = ArchivesWhilteService.service.getPage(getPaginator(),custom_id,report_id,this);
		getPaginator();
		setAttr("page",page);
		render(path+"list.html");
	}
	/**
	 * 查看
	* @author doushuihai  
	* @date 2018年9月3日下午2:37:33  
	* @TODO
	 */
	public void view() {
		ArchivesWhilteModel item = ArchivesWhilteModel.dao.findById(getParaToInt());
		setAttr("item", item);
		render(path + "view.html");
	}
	/**
	 * 新增跳转
	* @author doushuihai  
	* @date 2018年9月3日下午2:40:00  
	* @TODO
	 */
	public void add() {
		// 获取页面信息,设置目录传入
		ArchivesWhilteModel attr = getModel(ArchivesWhilteModel.class);
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
		Integer paraToInt = getParaToInt();
		ArchivesWhilteModel model = ArchivesWhilteModel.dao.findById(paraToInt);
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
		UploadFileService.service.delete(id,userid);//记录上传信息
		list();
	}
/*	public void add() {
		String dictType = getPara("dict_type");
		setAttr("optionList", svc.selectDictType(dictType));
		render(path + "add.html");
	}

	public void view() {
		SysDictDetail item = SysDictDetail.dao.findById(getParaToInt());
		setAttr("optionList", svc.selectDictType(item.getStr("dict_type")));
		setAttr("item", item);
		render(path + "view.html");
	}

	public void delete() {
		// 日志添加
		SysDictDetail model = new SysDictDetail();
		
				
		Integer userid = getSessionUser().getUserid();

		String now = getNow();
		
				
		model.put("update_id", userid);
		model.put("update_time", now);
		model.set("detail_id", getParaToInt());
			
		svc.updateDetail(model.set("del_flag", Integer.parseInt("1")));
		//svc.deleteDetail(model);
		
		list();
	}

	public void edit() {
		SysDictDetail item = SysDictDetail.dao.findById(getParaToInt());
		setAttr("optionList", svc.selectDictType(item.getStr("dict_type")));
		setAttr("item", item);
		render(path + "edit.html");
	}

	public void save() {
		Integer pid = getParaToInt();

		// 日志添加
		SysDictDetail model = getModel(SysDictDetail.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		if (pid != null && pid > 0) { // 更新
			svc.updateDetail(model);
		} else { // 新增
			model.remove("detail_id");
			model.put("create_id", userid);
			model.put("create_time", now);
			svc.addDetail(model);
		}
		renderMessage("保存成功");
	}

	public void edit_dict() {
		SysDict item = SysDict.dao.findFirstByWhere(" where dict_type = ? ", getPara());
		setAttr("item", item);
		render(path + "edit_dict.html");
	}

	public void save_dict() {
		Integer pid = getParaToInt();
		if (pid != null && pid > 0) { // 更新
			SysDict model = getModel(SysDict.class);
			// 日志添加
			Integer userid = getSessionUser().getUserid();
			String now = getNow();
			model.put("update_id", userid);
			model.put("update_time", now);
			model.update();
		} else { // 新增
			SysDict model = getModel(SysDict.class, "model");
			model.remove("dict_id");
			// 日志添加
			Integer userid = getSessionUser().getUserid();
			String now = getNow();
			model.put("update_id", userid);
			model.put("update_time", now);

			model.put("create_id", userid);
			model.put("create_time", now);
			model.save();
		}
		renderMessage("保存成功");
	}

	public void delete_dict() {
		// 日志添加
		SysDict model = new SysDict();
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);

		model.deleteById(getParaToInt());
		renderMessage("删除成功");
	}*/
}
