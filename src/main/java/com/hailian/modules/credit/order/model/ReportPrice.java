package com.hailian.modules.credit.order.model;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

/**
* @author dyc:
* @version 2018年8月23日 上午11:50:40
* @todo  报告价格表
*/
@ModelBind(table="credit_report_price")
public class ReportPrice extends BaseProjectModel<ReportPrice>{
   private static final long serialVersionUID = 1L;
   public static final ReportPrice dao=new ReportPrice();
   /**
    * 
    * @time   2018年8月23日 下午2:57:57
    * @author dyc
    * @todo  根据id查询报告价格信息
    * @return_type   ReportPrice
    */
   
   public ReportPrice selectOne(String id,BaseProjectController c){
	   return ReportPrice.dao.findById(id);
	   
	   
   }
}
