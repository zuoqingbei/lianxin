package com.hailian.modules.admin.file.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;
@ModelBind(table = "credit_upload_file")
public class CreditUploadFileModel extends BaseProjectModel<CreditUploadFileModel>{
	private static final long serialVersionUID = 1L;
	public static final CreditUploadFileModel dao = new CreditUploadFileModel();

}
