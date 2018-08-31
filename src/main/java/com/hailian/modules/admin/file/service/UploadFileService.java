package com.hailian.modules.admin.file.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.util.DateUtils;
import com.jfinal.upload.UploadFile;

public class UploadFileService {
	public static UploadFileService service=new UploadFileService();
	public boolean save(UploadFile uploadBean,String factpath,String url, String business_type, String business_id, String fileName, Integer userid){
		boolean flag=false;
		CreditUploadFileModel model=new CreditUploadFileModel();//记录上传记录信息
		String now = DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS);
		model.set("name", fileName);
		model.set("factpath", factpath);
		model.set("url", url);
		if(StringUtils.isNotBlank(business_type)){
			model.set("business_type", business_type);
		}
		model.set("business_id", business_id);
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
		model.set("create_date", now);
		model.set("create_by", userid);
		flag = model.save();
		return flag;
	}
	public List<CreditUploadFileModel> getByBusIdAndBusType(String business_id,String business_type,BaseProjectController c) {
		return CreditUploadFileModel.dao.getByBusIdAndBusType(business_id,business_type,c);
	}
}
