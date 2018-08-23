package com.hailian.modules.credit.order.model;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.jfinal.component.annotation.ModelBind;

/**
* @author dyc:
* @version 2018年8月23日 下午4:41:58
* @todo
*/
@ModelBind(table="credit_report_price")
public class ReportPrice extends BaseProjectModel<ReportPrice>{
 private static final  long serialVersionUID = 1L;
 public static final ReportPrice dao=new ReportPrice();
 
 
}
