package com.hailian.util;

import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;



public class AchievementsExport extends SettlrExcelExportTemplate {
	
private List<CreditOrderInfo> list = new ArrayList<CreditOrderInfo>();
	
	public AchievementsExport(List<CreditOrderInfo> list) {
		super();
		this.list = list;
	}

	@Override
	public String[] getSheetNames() {
		return new String[] { "订单绩效" };
	}

	@Override
	public String[][] getTitles() {
		return new String[][] { 
				//{"姓名","订单日期","到期日期","客户代码","订单公司名称","公司中文名称","扣分情况","提成"},
				{"订单号","姓名","订单日期","到期日期","客户代码","订单公司名称","公司中文名称","提成"},
		};
	}

	@Override
	public String[] getCaptions() {
		return null;
	}

	@Override
	protected void buildBody(int sheetIndex) {
		
		bodyRowStyle=crateBodyCellStyle();
		bodyRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		CellStyle bodyLeftStyle=crateBodyCellStyle();
		CellStyle bodyRightStyle=crateBodyCellStyle();
		bodyRightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		bodyLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		bodyRightStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		CellStyle bodyCenterStyle=crateBodyCellStyle();
		bodyCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 设置为文本格式，防止身份证号变成科学计数法  
        DataFormat format = workbook.createDataFormat();  
        bodyLeftStyle.setDataFormat(format.getFormat("@"));  
		
		Sheet sheet = getSheet(sheetIndex);
		int startIndex = this.getBodyStartIndex(sheetIndex);
//		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期

		for (int i = 0; i < list.size(); i++) {
			int index = 0;
			Row row1 = sheet.createRow(i + startIndex );
			Cell cell1 = row1.createCell((short) 1);  
	        cell1.setCellStyle(bodyLeftStyle);
			row1.setHeight((short) 300);
		CreditOrderInfo searchIndex = list.get(i);
			//{"订单号","姓名","订单日期","到期日期","客户代码","订单公司名称","公司中文名称","提成"},
			createStyledCell(row1, index++, searchIndex.get("num")==null? "":searchIndex.get("num").toString(),bodyRowStyle);//订单号
			createStyledCell(row1, index++, searchIndex.get("realname")==null? "":searchIndex.get("realname").toString(),bodyRowStyle);//姓名
			createStyledCell(row1, index++, searchIndex.get("receiver_date")==null? "":searchIndex.get("receiver_date").toString(),bodyLeftStyle);//订单日期
			createStyledCell(row1, index++, searchIndex.get("end_date")==null? "":searchIndex.get("end_date").toString(),bodyLeftStyle);//到期日期
			createStyledCell(row1, index++, searchIndex.get("custom_id")==null? "":searchIndex.get("custom_id").toString(),bodyRowStyle);//客户代码
			createStyledCell(row1, index++, searchIndex.get("right_company_name_en")==null? "":searchIndex.get("right_company_name_en").toString(),bodyRowStyle);//订单公司名称
			createStyledCell(row1, index++, searchIndex.get("company_by_report")==null? "":searchIndex.get("company_by_report").toString(),bodyRowStyle);//公司中文名称
			createStyledCell(row1, index++, searchIndex.get("money")==null? "":searchIndex.get("money").toString(),bodyRowStyle);//提成
			//createStyledCell(row1, index++, searchIndex.get("custom_id")==null? "":searchIndex.get("custom_id").toString(),bodyRowStyle);
			//{country:106, receiver_date:2018-12-12, agent_id:null, year:2018, num:DD201812120050, price_id:99, source:0, confirm_reason:null, credit_level:null, IQC:null, id:778076, fax:null, create_date:2018-12-12 16:51:40.0, order_type:210, del_flag:0, report_filing_agent_id:null, translate_user:null, custom_id:400, update_reason:null, customName:测试客户, report_type:8, update_date:2018-12-12 16:51:56.0, agent_category:null, month:12, telphone:null, grade:null, report_user:25, agent_priceId:null, tempId:25, status:694, is_fastsubmmit:-1, continent:157, end_date:2018-12-17, order_no:null, revoke_reason:null, user_time_id:21, info_en_name:null, right_company_name_en:海尔集团电器产业有限公司, is_ask:0, verify_name:null, speed:150, company_by_report:海尔集团电器产业有限公司, last_fiscal_year:null, create_by:16, update_by:16, submit_date:null, email:null, reference_num:null, address:null, company_id:7777893, mail_receiver:null, report_language:216, analyze_user:null, mail_associate_recipient:null, realname:王琪Dilys, money:66, is_hava_finance:null, user_time:2, contacts:null, remarks:null, online_id:null}
		}
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		
	
	}

}
