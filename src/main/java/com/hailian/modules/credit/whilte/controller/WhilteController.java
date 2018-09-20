package com.hailian.modules.credit.whilte.controller;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.service.CustomService;
import com.hailian.modules.credit.pricemanager.service.ReportPriceService;
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
		String orderBy = getBaseForm().getOrderBy();
		Page<ArchivesWhilteModel> page = ArchivesWhilteService.service.getPage(getPaginator(),orderBy,custom_id,report_id,this);
		List<ReportTypeModel> reportType = ReportPriceService.service.getReportType(null);
		setAttr("reporttype", reportType);
		setAttr("page",page);
		setAttr("attr",attr);
		render(path+"list.html");
	}
	/**
	 * 查看
	* @author doushuihai  
	* @date 2018年9月3日下午2:37:33  
	* @TODO
	 */
	public void view() {
		ArchivesWhilteModel white = ArchivesWhilteService.service.getWhite(getParaToInt());
		setAttr("item", white);
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
		List<ReportTypeModel> reportType = ReportPriceService.service.getReportType(null);
		List<CustomInfoModel> custom = CustomService.service.getCustom(null);//获取所有用户信息一坐用户下拉框
		setAttr("custom", custom);
		setAttr("reporttype", reportType);
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
		List<ReportTypeModel> reportType = ReportPriceService.service.getReportType(null);
		setAttr("reporttype", reportType);
		List<CustomInfoModel> custom = CustomService.service.getCustom(null);//获取所有用户信息一坐用户下拉框
		setAttr("custom", custom);
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
		ArchivesWhilteService.service.delete(id,userid);//记录上传信息
		list();
	}
	public void save() {
		Integer pid = getParaToInt();
		// 日志添加
		ArchivesWhilteModel model = getModel(ArchivesWhilteModel.class);
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
