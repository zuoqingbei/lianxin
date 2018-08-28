package com.hailian.modules.admin.file.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table = "credit_upload_file")
public class CreditUploadFileModel extends BaseProjectModel<CreditUploadFileModel>{
	private static final long serialVersionUID = 1L;
	public static final CreditUploadFileModel dao = new CreditUploadFileModel();
	public List<CreditUploadFileModel> getByBusIdAndBusType(String business_id,String business_type,BaseProjectController c){
		String sql="select * from credit_upload_file where business_id=? and business_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(business_id);
		params.add(business_type);
		return dao.find(sql, params.toArray());
	}

}
