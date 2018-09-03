package com.hailian.modules.admin.file.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.util.DateUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;

public class UploadFileService {
	public static UploadFileService service=new UploadFileService();
	/**
	 * 保存文件上传记录
	* @author doushuihai  
	* @date 2018年9月3日上午9:25:15  
	* @TODO
	 */
	public boolean save(Integer pid,UploadFile uploadBean,String factpath,String url, CreditUploadFileModel model, String fileName, Integer userid){
		boolean flag=false;
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		model.set("name", fileName);
		model.set("factpath", factpath);
		model.set("url", url);
		String originalName = uploadBean.getOriginalFileName();
		String fileExt = null;
		int dot = originalName.lastIndexOf(".");
		String originalFileName="";
		if (dot != -1) {
			originalFileName = originalName.substring(0, dot);
			fileExt = originalName.substring(dot + 1);
		} else {
			fileExt = "";
		}
		model.set("ext", fileExt);
		model.set("originalname",originalFileName);
		model.set("size", uploadBean.getFile().length());
		model.set("update_date", now);
		model.set("update_by", userid);
		String type = FileTypeUtils.getType(fileExt);
		if("word".equals(type)){
			model.set("ext_id", "258");//数据字典相关参数
		}
		if("excel".equals(type)){
			model.set("ext_id", "259");
		}
		if("pdf".equals(type)){
			model.set("ext_id", "260");
		}
		if("img".equals(type)){
			model.set("ext_id", "257");
		}
		if (pid != null && pid > 0) { // 更新
			model.set("id", pid);
			flag=model.update();

		} else { // 新增
			model.remove("id");
			model.set("create_date", now);
			model.set("create_by", userid);
			flag=model.save();
		}
		return flag;
	}
	public List<CreditUploadFileModel> getByBusIdAndBusType(String business_id,String business_type,BaseProjectController c) {
		return CreditUploadFileModel.dao.getByBusIdAndBusType(business_id,business_type,c);
	}
	public void delete(Integer id, Integer userid) {
		// TODO Auto-generated method stub
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		String sql="update credit_upload_file set del_flag=1,update_by=?,update_date=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(now);
		params.add(id);
		Db.update(sql,params.toArray());
	}

}
