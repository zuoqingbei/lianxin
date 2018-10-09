package com.hailian.modules.admin.file.model;

import java.util.ArrayList;
import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Page;
/**
 * 上传文件model层
* @author doushuihai  
* @date 2018年9月4日上午10:42:21  
* @TODO
 */
@ModelBind(table = "credit_upload_file")
public class CreditUploadFileModel extends BaseProjectModel<CreditUploadFileModel>{
	private static final long serialVersionUID = 1L;
	public static final CreditUploadFileModel dao = new CreditUploadFileModel();
	private String busName;
	public String getBusName() {
		return get("busName");
	}
	public void setBusName(String busName) {
		set("busName",busName);
	}
	/**
	 * 根据业务编号和业务类型查询
	* @author doushuihai  
	* @date 2018年9月4日上午10:40:51  
	* @TODO
	 */
	public List<CreditUploadFileModel> getByBusIdAndBusType(String business_id,String status,BaseProjectController c){
		String sql="select * from credit_upload_file where business_id=? and business_type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(business_id);
		params.add(status);
		return dao.find(sql, params.toArray());
	}
	/**
	 * 
	 * @time   2018年9月25日 下午2:21:22
	 * @author yangdong
	 * @todo   TODO 根据business_id查找订单附件
	 * @param  @param business_id
	 * @param  @return
	 * @return_type   List<CreditUploadFileModel>
	 */
	public List<CreditUploadFileModel> getFile(String business_id){
		String sql="select * from credit_upload_file where business_id=?";
		return dao.find(sql, business_id);
	}
	/**
	 * 列表分页展示
	* @author doushuihai  
	* @date 2018年9月4日上午10:41:32  
	* @TODO
	 */
	public Page<CreditUploadFileModel> getPage(Paginator paginator, CreditUploadFileModel attr, String orderBy,
			BaseProjectController c) {
		StringBuffer sql = new StringBuffer(" from credit_upload_file t left join sys_dict_detail t2 on t.business_type=t2.detail_id where t.del_flag=0");
		String type = attr.getStr("ext_id");//检索条件-文件类型
		Integer business_type = attr.getInt("business_type");//检索条件-报告类型
		String originalname = attr.getStr("originalname");//检索条件-上传文件名
		List<Object> params=new ArrayList<Object>();
		if (StrUtils.isNotEmpty(type)) {
			sql.append(" and t.ext_id = ? ");
			params.add(type);
		}
		if (business_type !=null && business_type>=0) {
			sql.append(" and t.business_type = ? ");
			params.add(business_type);
		}
		if (StrUtils.isNotEmpty(originalname)) {
			sql.append(" and t.originalname like ?");
			params.add('%'+originalname+'%');
		}
		if(!c.isAdmin(c.getSessionUser())){
			sql.append(" and t.create_by=? ");
			params.add(c.getSessionUser().getUserid());//传入的参数
		}
		// 排序
		if (StrUtils.isEmpty(orderBy)) {
			sql.append(" order by t.update_date desc");
		} else {
			sql.append(" order by t.").append(orderBy);
		}
		Page<CreditUploadFileModel> page = CreditUploadFileModel.dao
				.paginate(paginator, "select t.*,t2.detail_name as busName ", sql.toString(),params.toArray());
		return page;
	}


}
