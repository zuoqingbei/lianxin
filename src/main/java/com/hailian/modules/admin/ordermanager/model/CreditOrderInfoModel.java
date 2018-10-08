package com.hailian.modules.admin.ordermanager.model;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.credit.common.controller.ReportTimeController;
import com.hailian.modules.credit.common.model.ReportTimeModel;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.modules.credit.usercenter.controller.OrderProcessController;
import com.hailian.system.user.SysUser;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 
 * @className CreditOrderInfo.java
 * @time   2018年8月23日 上午9:05:28
 * @author yangdong
 * @todo   
 * 订单模型,jfinal创建模型不需要写属性,只需要继承Model或者model的子类,就会获取与数据库表的连接
 * 继承model的子类会得到很多方法的扩展
 */
@ModelBind(table = "credit_order_info")
//此标签用于模型与数据库表的连接
public class CreditOrderInfoModel extends BaseProjectModel<CreditOrderInfoModel> {
	//客户姓名
		private String customId;
		//客户姓名
		private String customName;
		//国家
		private String continent;
		//订单创建者的username
		private String country;
		//地区
		private String reportType;
		//报告类型
		private String orderType;
		//报告语言
		private String reportLanguage;
		//报告速度
		private String companyByReport;
		//订单来源
		private String speed;
		//订单价格
		private String referenceNum;
		//公司名称
		private String address;
		//公司英文名称
		private String telphone;
		private String fax;
		private String email;
		private String contacts;
		private String remarks;

		
	
		
		public String getCustomId() {
			return customId;
		}
		public void setCustomId(String customId) {
			set("custom_id", customId);
		}
		public String getcompanyName() {
			return get("companyName");
		}

		public void setcompanyName(String companyName) {
			set("companyName", companyName);
		}
		public String getContinent() {
			return continent;
		}
		public void setContinent(String continent) {
			set("continent", continent);
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			set("country", country);
		}
		public String getReportType() {
			return reportType;
		}
		public void setReportType(String reportType) {
			set("report_type", reportType);
		}
		public String getOrderType() {
			return orderType;
		}
		public void setOrderType(String orderType) {
			set("order_type", orderType);
		}
		public String getReportLanguage() {
			return reportLanguage;
		}
		public void setReportLanguage(String reportLanguage) {
			set("report_language", reportLanguage);
		}
		public String getCompanyByReport() {
			return companyByReport;
		}
		public void setCompanyByReport(String companyByReport) {
			set("company_by_report", companyByReport);
		}
		public String getSpeed() {
			return speed;
		}
		public void setSpeed(String speed) {
			set("speed", speed);
		}
		public String getReferenceNum() {
			return referenceNum;
		}
		public void setReferenceNum(String referenceNum) {
			set("reference_num", referenceNum);
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			set("address", address);
		}
		public String getTelphone() {
			return telphone;
		}
		public void setTelphone(String telphone) {
			set("telphone", telphone);
		}
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			set("fax", fax);
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			set("email", email);
		}
		public String getContacts() {
			return contacts;
		}
		public void setContacts(String contacts) {
			set("contacts", contacts);
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			set("remarks", remarks);
		}
		
		

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//
		public static final CreditOrderInfoModel dao = new CreditOrderInfoModel();//名字都叫dao，统一命名

}
