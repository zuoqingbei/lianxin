package com.hailian.modules.credit.order.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年8月23日 下午4:41:58
* @todo
*/
@ModelBind(table = "credit_report_price")
public class ReportPrice extends BaseProjectModel<ReportPrice> {
	private static final long serialVersionUID = 1L;
	public static final ReportPrice dao = new ReportPrice();


	/**
	 * 
	 * @time   2018年8月23日 下午7:18:17
	 * @author dyc
	 * @todo   根据id查询报告价格
	 * @return_type   ReportPrice
	 */

	public ReportPrice selectId(String id) {
		return ReportPrice.dao.findById(id);
	}
	/**
	 * 
	 * @time   2018年8月27日 上午11:42:38
	 * @author dyc
	 * @todo   根据id删除报告价格信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(String id) {
		//ReportPrice price=ReportPrice.dao.findFirst("select * from credit_report_price where id="+id);
		ReportPrice price=ReportPrice.dao.findById(id);
		if(price!=null){
			return  price.set("del_flag", 1).update();
		}
		return false;
	}
}
