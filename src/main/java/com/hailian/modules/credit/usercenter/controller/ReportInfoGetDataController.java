package com.hailian.modules.credit.usercenter.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.hailian.api.constant.RoleCons;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyGdp;
import com.hailian.modules.admin.ordermanager.model.CreditOperationLog;
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditQualityOpintion;
import com.hailian.modules.admin.ordermanager.model.CreditQualityOpintionHistory;
import com.hailian.modules.admin.ordermanager.model.CreditQualityResult;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialEntry;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyFinancialStatementsConf;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanySubtables;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.agentmanager.service.AgentPriceService;
import com.hailian.modules.credit.reportmanager.model.CreditReportDetailConf;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.finance.ExcelModule;
import com.hailian.modules.credit.usercenter.controller.finance.FinanceService;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.util.StrUtils;
import com.hailian.util.word.MainReport;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

@ControllerBind(controllerKey = "/credit/front/ReportGetData")
public class ReportInfoGetDataController extends ReportInfoGetData {
	private TemplateDictService template = new TemplateDictService();
	private final static String PAKAGENAME_PRE = "com.hailian.modules.admin.ordermanager.model.";
	//private static FinanceService  financeService = new FinanceService();

	/**
	 * 获取form类型的数据 2018/11/12 10:13 lzg
	 */
	public void getForm() {
		getBootStrapTable();
	}
	/**
	 * 
	* @Description: 详情form
	* @date 2018年11月20日 上午11:09:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void getForms() {
		getBootStrapTables();
	}

	/**
	 * 根据参数获取下拉选
	 */
	public void getSelete() {
		String selectStr = "";
		String type = getPara("type");
		String disPalyCol =  getPara("disPalyCol");
		if(!StrUtils.isEmpty(type)&&"country".equals(type)) {
			if("detail_name_en".equals(disPalyCol)) {
			 selectStr =  template.getCounty(getPara("selectedId"), "name_en");
			}else if("name".equals(disPalyCol)) {
		     selectStr =  template.getCounty(getPara("selectedId"), "name");
			 }
		 }
		selectStr =  template.getSysDictDetailString3(getPara("type"), getPara("selectedId"), disPalyCol);
		renderJson(new Record().set("selectStr", selectStr));
	}
	/**
	 * alterBootStrapTable
	 */
	public void alterForm() {
		alterBootStrapTable();
	}

	/**
	 * 2018/11/9 lzg 删除bootStrapTable式的数据
	 */
	public void deleteOneEntry() {
		try {
			this.deleteOneEntry(PAKAGENAME_PRE + getPara("className"), getPara("id"));
			renderJson(new ResultType(1, "删除成功!"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
	}

	/**
	 * 2018/11/8 lzg 修改或者新增bootStrapTable式的数据
	 */
	public void alterBootStrapTable() {
		if("CreditCompanySubtables".equals(getPara("className"))) {
			int a = 0;
			a++;
		}
		try {
			this.infoEntry(getPara("dataJson"), PAKAGENAME_PRE + getPara("className"), StrUtils.isEmpty(getPara("sys_language"))?SimplifiedChinese:getPara("sys_language"),isCompanyMainTable());
			renderJson(new ResultType(1, "操作成功!"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
	}

	/**
	 * 获取bootstraptable类型的数据 链接形如:
	 * http://localhost:8080/credit/front/ReportGetData/getBootStrapTable?conf_id=18
	 * company_id=24&report_type=1&tableName=credit_company_his&className=CreditCompanyHis
	 *
	 */
	public void getBootStrapTable() {
		getBootStrapTable(isCompanyMainTable(),  null);
	}
	//详情
	public void getBootStrapTables() {
		getBootStrapTables(isCompanyMainTable(),null);
	}


	@SuppressWarnings("unchecked")
	public void getBootStrapTable(boolean isCompanyMainTable,String companyId) {

		Record record = new Record();
		String tableName = getPara("tableName", "");
		String className = getPara("className");
		if(companyId==null||"".equals(companyId)) {
			 companyId = getPara("company_id","");
		}
		String confId = getPara("conf_id", "");
		
		// 获取关联字典表需要转义的下拉选
		String selectInfo = getPara("selectInfo");
        List rows = getTableData(isCompanyMainTable,companyId,tableName,className,confId,selectInfo);
		renderJson(record.set("rows", rows).set("total", rows!=null?rows.size():null));
	}

   //详情模式
	public void getBootStrapTables(boolean isCompanyMainTable, String companyId) {
		Record record = new Record();
		String tableName = getPara("tableName", "");
		String className = getPara("className");
		if(companyId==null||"".equals(companyId)) {
			 companyId = getPara("company_id","");
		}
		String confId = getPara("conf_id", "");
		 String orderId = getPara("orderId");
		  List rows = getTableDatas(isCompanyMainTable, companyId,tableName,className,confId,orderId);
			renderJson(record.set("rows", rows).set("total", rows!=null?rows.size():null));
		}

    /**
     * 反向映射数据
     * @param sysLanguage
     * @param companyId
     * @param tableName
     * @param className
     * @param confId
     * @param selectInfo
     * @return
     */
    public List getTableData(String sysLanguage,String companyId,String tableName,String className,String confId,String selectInfo){
        boolean isCompanyMainTable = tableName.equals("credit_company_info")||className.equals("CreditCompanyInfo");
        return getTableData(isCompanyMainTable,companyId,tableName,className,confId,selectInfo);
    }


      

	
    /**
     * 反向映射数据
     * @param isCompanyMainTable 是否是主表
     * @param companyId 公司id
     * @param tableName 表名
     * @param className 反向映射的类名
     * @param confId 父节点id
     * @param selectInfo  下拉框（把id转value）
     * @return
     */
    public List getTableData(boolean isCompanyMainTable,String companyId,String tableName,String className,String confId,String selectInfo){
        // 解析实体获取required参数
      /*  String type = null;
        if(getRequest()!=null){
            type = getPara("type");
        }
        
    	if (type!=null) {
			CreditReportDetailConf confModel=CreditReportDetailConf.dao.findById(confId);
			 getSource = confModel.getStr("data_source");
    	}else{*/
    	String getSource = "";
			CreditReportModuleConf confModel = CreditReportModuleConf.dao.findById(confId);
			 getSource = confModel.getStr("get_source");
//		}
		
		StringBuffer sqlSuf = new StringBuffer();

		if ((tableName != null && tableName.contains("_dict"))) {
			sqlSuf.append(" 1=1 and ");
		}else if ((!("".equals(getSource) || getSource == null)) && getSource.contains("*")) {
			String[] requireds = getSource.split("\\*");
			String[] required = requireds[1].split("\\$");
			for (String str : required) {
                if(str.equals("company_id")){
                    sqlSuf.append(str.trim() + "=" + companyId + " and ");
                }else{
                    if(getRequest()!=null) {
                        sqlSuf.append(str.trim() + "=" + getPara(str).trim() + " and ");
                    }
                }

			}
		} else {
			sqlSuf.append(" company_id=" + companyId.trim() + " and ");

		}

		if (sqlSuf.length() < 1) {
			//renderJson(record.set("rows", null));
			return null;
		}

		// 如果是公司主表,将company_id改为id
		if (isCompanyMainTable) {
			String sqlSuf2 = sqlSuf + "";
			sqlSuf2 = sqlSuf2.replace("company_id", "id");
			sqlSuf = new StringBuffer(sqlSuf2);
		}
		List rows = null;
		try {
            System.out.println(confId+"="+className);
            Class<?> table = Class.forName(PAKAGENAME_PRE + className);
            BaseProjectModel model = (BaseProjectModel) table.newInstance();
            //rows = model.find("select * from " + tableName + " where del_flag=0 and " + sqlSuf + " 1=1 ");
            //使用ehcache缓存数据
            System.out.println(tableName + sqlSuf);
            rows = model.findByCache("company", tableName + sqlSuf, "select * from " + tableName + " where del_flag=0 and " + sqlSuf + " 1=1 ");
            String type= getPara("type");
            if (StringUtils.isNotBlank(companyId)) {
				//关联设置企业类型注释
           CreditCompanyInfo info= 	CreditCompanyInfo.dao.findById(companyId);
			if(StringUtils.isBlank(info.get("type_of_enterprise_remark"))){
				//企业类型注释是空，设置进去
			SysDictDetail detail=	SysDictDetail.dao.findById(info.get("company_type"));
			CreditCompanyInfo cmodel=new CreditCompanyInfo();
			  cmodel.set("id", companyId);
			  if ("12".equals(table)) {
				  cmodel.set("type_of_enterprise_remark", detail.get("detail_remark"));
			}else if("14".equals(type)){
				  cmodel.set("type_of_enterprise_remark", detail.get("detail_content"));
			}
			  cmodel.update();
			}
           }
            if (!("".equals(selectInfo) || selectInfo == null)) {

                // 解析前端传入的字符串
                List<Map<Object, Object>> selectInfoMap = parseJsonArray(selectInfo);

                // 将id转化为字典表中对应的字符串
                dictIdToString(rows, selectInfoMap);
            }
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
        return rows;
    }
    //详情
    public List getTableDatas(boolean isCompanyMainTable, String companyId,String tableName,String className,String confId,String orderId){
        // 获取关联字典表需要转义的下拉选
		String selectInfo = getPara("selectInfo");
		List rows = null;
		try {
			Class<?> table = Class.forName(PAKAGENAME_PRE + className);
			BaseProjectModel model = (BaseProjectModel) table.newInstance();
			rows = model.find(
					"select info.*,detai.detail_name as area,t.name as reportType,cu.`name` as name,de.detail_name as speeds,de.detail_name as report_language ,det.detail_name as country from credit_order_info  info "
					+ " LEFT JOIN credit_custom_info cu on info.custom_id=cu.id "
					+ " LEFT JOIN sys_dict_detail de on de.detail_id=info.report_language"
					+ " LEFT JOIN sys_dict_detail det on det.detail_id=info.country"
					+ " LEFT JOIN sys_dict_detail deta on deta.detail_id=info.speed"
					+ " LEFT JOIN sys_dict_detail detai on detai.detail_id=info.continent"
					+ " LEFT JOIN credit_report_type t on t.id=info.report_type"
					+ " where info.id=?",
					orderId);
			if (!("".equals(selectInfo) || selectInfo == null)) {

				// 解析前端传入的字符串
				List<Map<Object, Object>> selectInfoMap = parseJsonArray(selectInfo);

				// 将id转化为字典表中对应的字符串
				dictIdToString(rows, selectInfoMap);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "类文件未找到异常!"));
		} catch (InstantiationException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "实例化异常!"));
		} catch (IllegalAccessException e) {
			renderJson(new ResultType(0, "非法存取异常!"));
			e.printStackTrace();
		}
        return rows;
    }
    
   
  /**
   * 
  * @Description: 新增/查询质检意见表
  * @date 2018年11月21日 下午3:15:50
  * @author: lxy
  * @version V1.0
  * @return
   */
    public void getOrsaveOpintion() {//质检意见新增
        Record record = new Record();
        Integer userId = getSessionUser().getUserid();
        String now = getNow();
        // CreditQualityOpintion model=getModel(CreditQualityOpintion.class);
        String orderId = getPara("orderId");
        String id = getPara("id");
        String opintion = getPara("quality_opinion");
        String type = getPara("quality_type");
        String reportType = getPara("report_type");
        String moduleId = getPara("report_module_id");
        String deal = getPara("quality_deal");
        String grade = getPara("grade");
        String update = getPara("update");//修改的状态
        String code = (String) getRequest().getParameter("statusCode");//获取提交的是完成还是修改状态
        String submit = getPara("submit");
        CreditQualityOpintionHistory history = new CreditQualityOpintionHistory();
        history.set("quality_opinion", opintion);
        history.set("quality_type", type);
        history.set("order_id", orderId);
        history.set("report_type", reportType);
        history.set("quality_deal", deal);
        history.set("grade", grade);
        history.set("create_by", userId);
        history.set("create_date", now);
        history.set("update_by", userId);
        history.set("update_date", now);
        history.save();
        List<CreditQualityOpintion> opintion2 = new ArrayList<CreditQualityOpintion>();
   	     opintion2 = CreditQualityOpintion.dao.find("SELECT * from credit_quality_opintion where order_id=? and quality_type=?", orderId, type);
        if (StringUtils.isBlank(update)) {//查询或新增
                //查询
                renderJson(record.set("rows", opintion2).set("total", opintion2 != null ? opintion2.size() : null));
            
        } else {
        	//通过查询该订单下 该质检类型的质检意见是否存在，因不同订单不同类型质检一条数据，可以确定是否新增或修改
             if (opintion2.size() <= 0) {
                 //如果根据订单id 与质检类型 查询为空 则新增
                 CreditQualityOpintion model = new CreditQualityOpintion();
                 model.clear();
                 opintion2.clear();
                 model.set("quality_opinion", opintion);
                 model.set("quality_type", type);
                 model.set("order_id", orderId);
                 model.set("report_type", reportType);
                 model.set("quality_deal", deal);
                 model.set("grade", grade);
                 model.set("create_by", userId);
                 model.set("create_date", now);
                 model.set("update_by", userId);
                 model.set("update_date", now);
                 model.save();
                 opintion2.add(model);
                 renderJson(record.set("rows", opintion2).set("total", opintion2 != null ? opintion2.size() : null));
             }else{
            CreditQualityOpintion model = new CreditQualityOpintion();
            model.clear();
            opintion2.clear();
            model.set("quality_opinion", opintion);
            model.set("quality_type", type);
            model.set("order_id", orderId);
            model.set("report_type", reportType);
            model.set("quality_deal", deal);
            model.set("grade", grade);
            model.set("create_by", userId);
            model.set("create_date", now);
            model.set("update_by", userId);
            model.set("update_date", now);
            model.set("id", id);
            model.update();
            opintion2.add(model);
            renderJson(record.set("rows", opintion2).set("total", opintion2 != null ? opintion2.size() : null));
             }
          }
        if (StringUtils.isNotBlank(submit)) {
            //自动分配的分析员ID
            String analerId = null;
            //自动分配的翻译员ID
            String transerId = null;
            AgentPriceModel agentPrice = new AgentPriceModel();
            String status = "";
            if (type.equals("translate_quality")) {//翻译质检
                if (deal.equals("1")) {//1 完成 2修改
                    status = "311";
                    agentPrice = AgentPriceService.service.getAgentPriceByOrder(orderId);
                } else {
                    status = "306";
                }
            } else if (type.equals("entering_quality")) {//填报质检
                if (deal.equals("1")) {//1 完成 2修改
                    //如果是信用分析报告走分析流程，其他则质检走翻译流程
                    CreditOrderInfo info = CreditOrderInfo.dao.findFirst("select * from credit_order_info where id=?", orderId);
                    if (info.get("report_type").equals("10") || info.get("report_type").equals("11")) {
                        status = "301"; //走分析
                        //todo 填报质检完成后自动分配分析员
                        analerId = OrderManagerService.service.getUserIdtoOrder(RoleCons.ANALER);
                    }else if((info.get("report_language").equals("216")||info.get("report_language").equals("217"))&&(!info.get("report_type").equals("10") || !info.get("report_type").equals("11"))){
                    	//报告语言，中文简体+英文  ，中文繁体+英文 除信用报告 其余都是走翻译
                        status = "306";//走翻译
                        //todo 分析质检完成后自动分配翻译员
                        transerId = OrderManagerService.service.getUserIdtoOrder(RoleCons.TRANSER);
                    
                    }else if((info.get("report_language").equals("213")||info.get("report_language").equals("215"))&&(!info.get("report_type").equals("10") || !info.get("report_type").equals("11"))){
                       //报告语言是中文，或英文，除信用分析，全部直接结束报告
		                status = "311";
		                agentPrice = AgentPriceService.service.getAgentPriceByOrder(orderId);
                    }
                } else { 
                    status = "293";    //信息录入
                }
            } else if (type.equals("analyze_quality")) {
                if (deal.equals("1")) {//1 完成 2修改
                    //分析完成，判断订单报告语言，213，215，没有翻译，质检完成则发送报告邮件
                    CreditOrderInfo info = CreditOrderInfo.dao.findFirst("select * from credit_order_info where id=?", orderId);
                    if (info.get("report_language").equals("213") || info.get("report_language").equals("215")) {
                        status = "311";
                        agentPrice = AgentPriceService.service.getAgentPriceByOrder(orderId);
                    } else {
                        status = "306";
                        //todo 分析质检完成后自动分配翻译员
                        transerId = OrderManagerService.service.getUserIdtoOrder(RoleCons.TRANSER);
                    }
                } else {
                    status = "301";
                }
            }
            Map<String, Object> map = new HashMap<>();
            if (deal == null || "".equals(deal.trim())) {
                map = null;
            } else {
                map.put("status", status);
                if (agentPrice != null) {
                    map.put("agent_priceId", agentPrice.get("id"));
                }
                //分配分析员
                if (analerId != null) {
                    map.put("analyze_user",analerId);
                }
                //分配翻译员
                if (transerId != null) {
                    map.put("translate_user",transerId);
                }
            }

            CreditOrderInfo model = getModel(CreditOrderInfo.class);
            model.removeNullValueAttrs();
            model = getModel(CreditOrderInfo.class);
            model.set("update_by", userId);
            model.set("update_date", now);
            model.set("id", orderId);
            if (map != null) {
                for (String key : map.keySet()) {
                    model.set(key, map.get(key));
                }
            }
            model.update();
            //如果报告状态是311 发送邮件，报告
            if (status.equals("311")) {

                new MainReport().build(Integer.parseInt(orderId), getSessionUser().getUserid());
            }

            //增加跟踪记录
            CreditOrderFlow.addOneEntry(this, model);
            CreditOperationLog.dao.addOneEntry(userId, null, "订单管理/", "/credit/front/orderProcess/statusSave");//操作日志记录
            renderJson(record.set("submit", submit));
        }

    }

   /** 
    * 
   * @Description: 质检结果的查询与新增，修改
   * @date 2018年11月21日 下午3:30:55
   * @author: lxy
   * @version V1.0
   * @return
    */
    public  void getOrSaveResult(){
    	Record record = new Record();
    	Integer userId = getSessionUser().getUserid();
		 String now = getNow();
    	 String orderId = 	getPara("orderId");
         String id =   getPara("id"); 
         String result =  getPara("quality_result");
         String  type =   getPara("quality_type");
         String moduleId= getPara("report_module_id");
         String   update= getPara("update");
         String datajson = getPara("datajson");
          List<CreditQualityResult> list=new ArrayList<CreditQualityResult>();
          if (update!=null&&update.equals("true")) {//修改
        	  List<Map<Object, Object>> entrys = parseJsonArray(datajson);
  			for (Map<Object, Object> entry : entrys) {
  			 //循环取出parentId的值
  		     String pid=(String) entry.get("parentId");//取父模板id
	  		   CreditQualityResult result2=  CreditQualityResult.dao.findFirst("select * from credit_quality_result where report_model_id=? and quality_type=? and order_id=?",pid,type,orderId);
	  		 CreditQualityResult model=new CreditQualityResult();
	  		   model.set("id", result2.get("id"));
	  			model.set("quality_result", entry.get("quality_result"));
	  			model.set("create_by", userId);
				model.set("create_date", now);
				model.set("update_by", userId);
				model.set("update_date", now);
				model.set("report_model_id", pid);
				model.update();
				list.add(model);
  			 
  			}	
		}else {//查询或新增
			//取json数据
			List<Map<Object, Object>> entrys = parseJsonArray(datajson);
			for (Map<Object, Object> entry : entrys) {
			 //循环取出parentId的值
		     String pid=(String) entry.get("parentId");//取父模板id
		     //查询当前质检结果是不是有值
		CreditQualityResult result2=  CreditQualityResult.dao.findFirst("select * from credit_quality_result where report_model_id=? and quality_type=? and order_id=?",pid,type,orderId);
		     if (result2!=null) {
				list.add(result2);
			}else {
			CreditQualityResult model=new CreditQualityResult();
				model.clear();
				model.set("report_model_id", pid);
				model.set("quality_result", entry.get("quality_result"));
				model.set("order_id", orderId);
				model.set("quality_type", type);
				model.set("create_by", userId);
				model.set("create_date", now);
				model.set("update_by", userId);
				model.set("update_date", now);
				model.save();
				list.add(model);
			 }	
		  }	
		}
		renderJson(record.set("rows", list).set("total", list!=null?list.size():null));
		
    }
    /**
     * 
    * @Description: 质检意见
    * @date 2018年11月24日 上午10:50:43
    * @author: lxy
    * @version V1.0
    * @return
     */
    public void getquality(){
    	Record record = new Record();
        String order_num=	getPara("orderId");
         List<CreditQualityOpintion> opintion=   CreditQualityOpintion.dao.find("select o.*,u.username as name from credit_quality_opintion o "
        		+ "LEFT JOIN sys_user u on u.userid=o.create_by "
        		+ "where  o.order_id=?",order_num);
         
        renderJson(record.set("rows", opintion).set("total", opintion!=null?opintion.size():null));	
        }
    /**
     * 
    * @Description: 订单流程进度
    * @date 2018年11月22日 上午10:34:09
    * @author: lxy
    * @version V1.0
    * @return
     */
    public void getflow(){
    	
    Record record = new Record();	
    String order_num=	getPara("num");
     List<CreditOrderFlow> flows=   CreditOrderFlow.dao.find("select d.detail_id, d.detail_name as order_state,u.username as create_oper,f.create_time from credit_order_flow f "
    		+ "LEFT JOIN sys_dict_detail d on d.detail_id=f.order_state "
    		+ "LEFT JOIN sys_user u on u.userid=f.create_oper "
    		+ "where  f.order_num=?",order_num);
     renderJson(record.set("rows", flows).set("total", flows!=null?flows.size():null));		
    }
    /**
     * 查询主表数据
     * @param companyId
     * @param sysLanguage
     * @return
     */
    public CreditCompanyInfo getCompanyInfo(String companyId,String sysLanguage){
        List<CreditCompanyInfo> comList = CreditCompanyInfo.dao.find(
                "select * from credit_company_info where del_flag=0 and id="+companyId+" and sys_language in(?)",
                Arrays.asList(new String[]{sysLanguage}).toArray());
        return (comList!=null && comList.size()>0)?comList.get(0):null;
    }

    /**
     * 查询从表数据
     * @param companyId
     * @param sysLanguage
     * @return
     */
    public CreditCompanySubtables getSonTableInfo(String companyId,String sysLanguage){
        List<CreditCompanySubtables> comList = CreditCompanySubtables.dao.find(
                "select * from credit_company_subtables where del_flag=0 and id="+companyId+" and sys_language in(?)",
                Arrays.asList(new String[]{sysLanguage}).toArray());
        return (comList!=null && comList.size()>0)?comList.get(0):null;
    }
    
    /**
     * 根据财务配置id获取财务信息
     */
    public void getFinancialEntrys() {
    	String financialConfId = getPara("ficConf_id");
    	String reportType = getPara("report_type");
    	if(StrUtils.isEmpty(financialConfId)) {
    		 renderJson(new ResultType(0, "获取财务信息失败,需要ficConf_id,report_type这两个参数!"));
			 return;
    	}
    	//Integer type = getFinanceDictByReportType(reportType);
    	String type = getPara("type"); 
		if(StrUtils.isEmpty(type)) { renderJson(new ResultType(0, "缺少财务类型参数!")); return;}
    	List<CreditCompanyFinancialEntry>  row = FinanceService.getFinancialEntryList(financialConfId,type);
    	renderJson(new Record().set("rows", row).set("total", row==null?0:row.size()));
    }
    
    
    
	 /**
	  * 解析并上传财务模板
	  * lzg 2018/11/24
	  * 
	  */
	public void uploadFinancialEntrys() {
		UploadFile uploadFile = getFile("file");
		if(uploadFile==null) {
			renderJson(new ResultType(0, "上传文件不能为空!"));
			return;
		}
		String financialConfId = getPara("ficConf_id");
		String reportType = getPara("report_type");
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		if(StrUtils.isEmpty(financialConfId)) {
			renderJson(new ResultType(0, "配置id不能为空!"));
			return;
		}
		if(StrUtils.isEmpty(reportType)) {
			renderJson(new ResultType(0, "报告类型不能为空!"));
			return;
		}
		//Integer type = getFinanceDictByReportType(reportType);
		String type = getPara("type"); 
		if(StrUtils.isEmpty(type)) {
			renderJson(new ResultType(0, "缺少财务类型参数!"));
			return;
		}
		String message = "导入失败,请检查文件内容!";
		try {
			message = FinanceService.alterFinancialEntryListForUpload(uploadFile.getFile(), Integer.parseInt(type), financialConfId, userId, now);
		} catch (Exception e) {
			renderJson( new ResultType(0, message));
			e.printStackTrace();
			return;
		}
		renderJson(new ResultType(1, message));
	}
	
	 /**
      *下载财务模板
      *lzg 2018/11/24
     */
	public void getFinanceExcelExport() {
		String reportType = getPara("report_type");
		if(StrUtils.isEmpty(reportType)) {
			renderJson(new ResultType(0, "报告类型不能为空!"));
			return;
		}
		//Integer type = getFinanceDictByReportType(reportType);
		String type = getPara("type"); 
		if(StrUtils.isEmpty(type)) {
			renderJson(new ResultType(0, "缺少财务类型参数!"));
			return;
		}
		ServletOutputStream ops = null;
		HttpServletResponse response = this.getResponse();
		try {
			ops = response.getOutputStream();response.reset();
		} catch (IOException e) {
			e.printStackTrace();
			renderJson(new ResultType(0, "导入出现未知异常!"));
			return;
		}
		ExcelModule.exportExcel(response,ops, Integer.parseInt(type));
		renderJson(new ResultType(0, "导入成功!"));
	}
	
	/**
	 * 增加或修改财务实体信息
	 * lzg 2018/11/24
	 */
	public void alterFinanceOneEntry() {
		String dataJson = getPara("dataJson");
		if(StrUtils.isEmpty(dataJson)) {
			renderJson(new ResultType(0, "请检查这个必要参数 dataJson!"));
			return;
		}
		List<Map<Object, Object>> entrys = ReportInfoGetData.parseJsonArray(dataJson);
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		try {
			FinanceService.alterFinancialEntryList(entrys, userId, now);
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"发生未知异常!"));
			return;
		}
		renderJson(new ResultType(1,"操作成功!"));
	}
    
	
	/**
	 * 删除一条财务信息
	 * lzg 2018/11/24
	 */
	public void deleteOneFinanceOneEntry() {
		
		String entryId = getPara("id");
		if(StrUtils.isEmpty(entryId)) {
			renderJson(new ResultType(0, "缺少正确参数entryId!"));
			return;
		}
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		try {
			FinanceService.deleteFinancialEntryList(entryId, userId, now);
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"发生未知异常!"));
			return;
		}
		renderJson(new ResultType(1,"删除成功!"));
	}
    
	/**
	 * 获取当前公司下所有财务配置信息
	 */
	public void getFinanceConfigs() {
		String reportType = getPara("report_type");
		String companyId = getPara("company_id");
		if(StrUtils.isEmpty(companyId,companyId)) {
			renderJson(new ResultType(0, "请检查这两个必要参数companyId,report_type!"));
			return;
		}
		//Integer type = getFinanceDictByReportType(reportType);
		String type = getPara("type"); 
		if(StrUtils.isEmpty(type)) {
			renderJson(new ResultType(0, "缺少财务类型参数!"));
			return;
		}
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
	    List<CreditCompanyFinancialStatementsConf> rows = FinanceService.getFinancialConfigList(companyId,type);
	    //如果不存在就创建一个默认的
		if(rows==null||rows.size()==0) {
			List<Map<Object, Object>> entrys = new ArrayList<Map<Object, Object>>();
			Map<Object, Object> entryMap = new HashMap<>();
			entryMap.put("company_id", companyId);
			entryMap.put("type", type);
			entrys.add(entryMap);
			FinanceService.alterFinancialConfig(entrys, Integer.parseInt(type), userId, now);
			rows = FinanceService.getFinancialConfigList(companyId,type);
		}
		renderJson(new Record().set("rows", rows));
	}

	/**
	 * 增加或修改一条财务配置信息
	 * lzg 2018/11/24
	 */
	public void alterFinanceOneConfig() {
		String dataJson = getPara("dataJson");
		String reportType = getPara("report_type");
		if(StrUtils.isEmpty(dataJson,reportType)) {
			renderJson(new ResultType(0, "请检查这两个必要参数reportType,dataJson!"));
			return;
		}
		List<Map<Object, Object>> entrys = ReportInfoGetData.parseJsonArray(dataJson);
		if(entrys.size()!=1) {
			renderJson(new ResultType(0,"只能单条操作!"));
			return;
		}
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		//Integer type = getFinanceDictByReportType(reportType);
		String type = getPara("type"); 
		if(StrUtils.isEmpty(type)) {
			renderJson(new ResultType(0, "缺少财务类型参数!"));
			return;
		}
		try {
			FinanceService.alterFinancialConfig(entrys, Integer.parseInt(type), userId, now);
		} catch (Exception e) {
			e.printStackTrace();
			renderJson(new ResultType(0,"发生未知异常!"));
			return;
		}
		renderJson(new ResultType(1,"操作成功!"));
	}
    
	
	/**
	 * 删除一条财务配置信息
	 * lzg 2018/11/24
	 */
	public void deleteOneFinanceConfig() {
		String financialConfId = getPara("ficConf_id");
		if(StrUtils.isEmpty(financialConfId)) {
			renderJson(new ResultType(0, "请检查必要参数financialConfId!"));
			return;
		}
		String userId = getSessionUser().getUserid()+"";
		String now = getNow();
		try {
			FinanceService.deleteFinancialConfig(financialConfId, userId, now);
		} catch (Exception e) { 
			e.printStackTrace();
			renderJson(new ResultType(0,"发生未知异常!"));
			return;
		}
		renderJson(new ResultType(1,"删除成功!"));
	}
	
	 /**
	  * 上传商标
	  * lzg 2018/11/28
	  */
	 public void  uploadBrand() {
		int userId = getSessionUser().getUserid();
		String randomCode = UUID.randomUUID().toString().replaceAll("-", "");
		//从前台获取文件
        List<UploadFile>  upFileList = getFiles("file");
		String orderId = getPara("order_id");
		//获取真实文件名
        String originalFile = upFileList.get(0).getOriginalFileName();
        //根据文件后缀名判断文件类型
        String ext = FileTypeUtils.getFileType(originalFile);
        //检查格式
        if(!"png".equals(ext)&&!"jpg".equals(ext)&&!"jpeg".equals(ext)){
            renderJson(new ResultType(0, "请检查 "+originalFile+"的格式!"));
			return;
        }
        if(upFileList.size()<1) {
        	renderJson(new ResultType(0, "上传文件为空!"));
			return;
        }
        if(StrUtils.isEmpty(orderId)) {
        	renderJson(new ResultType(0, "请检查必要参数order_id!"));
			return;
        }
        OrderProcessController.uploadFile(orderId, "-1", upFileList,userId,randomCode);
        CreditUploadFileModel  file = CreditUploadFileModel.dao.getByRandomCode(orderId+"", "-1",randomCode);
		renderJson(new Record().set("url", OrderProcessController.ip + ":" + OrderProcessController.searverPort+"/"+file.get("url")));
	 }
	
	/**
     * 将id转化为字典表中对应的字符串
     * @param id
     * @param sysLanguage
     */
    public static String dictIdToString(String id,String sysLanguage) {
        //判断id必须是数字
        if (id.matches("-?[0-9]+.*[0-9]*")){
            Map<Integer, SysDictDetail> cache = DictCache.getCacheMap();
            SysDictDetail sysDict = cache.get(Integer.parseInt(id));
            if (sysDict != null) {
                //英文
                if ("613".equals(sysLanguage)) {
                    return sysDict.get("detail_name_en") + "";
                } else {
                    return sysDict.get("detail_name") + "";
                }
            }
        } else {
            System.out.println("此信息输出不影响程序往下运行，异常id=" + id);
        }
        return "";
    }
	
    
    /**
     * 
    * @Description: 质检下订单完成修改提交状态
    * @date 2018年11月30日 下午4:09:00
    * @author: lxy
    * @version V1.0
    * @return
     */
    public  ResultType  qualityOrderStatus() {
        try {
        	String status="";
            String code = (String) getRequest().getParameter("statusCode");//获取提交的是完成还是修改状态
           String  type= getPara("quality_type");//获取是填报质检，分析质检，翻译质检中的哪一种
           if (type.equals("translate_quality")) {//翻译质检
			if (code.equals("1")) {//1 完成 2修改
				status="311";
			}else {
				status="306";
			}
		   }else if(type.equals("entering_quality")){//填报质检
			   if (code.equals("1")) {//1 完成 2修改
					status="301";
				}else {
					status="293";	//信息录入
				}
		  }else if(type.equals("analyze_quality")){
			  if (code.equals("1")) {//1 完成 2修改
					status="306";
				}else {
					status="301";
				} 
		  }
            Map<String,Object> map = new HashMap<>();
            if(code==null||"".equals(code.trim())){
                map = null;
            }else{
                map.put("status", status);
            }
            
            CreditOrderInfo model = getModel(CreditOrderInfo.class);
            model.removeNullValueAttrs();
            model = getModel(CreditOrderInfo.class);
            Integer userid = getSessionUser().getUserid();
            String now = getNow();
            model.set("update_by",userid);
            model.set("update_date", now);
            if(map!=null){
                for (String key : map.keySet()) {
                    model.set(key, map.get(key));
                }
            }
            
            model.update();
            //增加跟踪记录 
            CreditOrderFlow.addOneEntry(this, model);
            CreditOperationLog.dao.addOneEntry(userid, null,"订单管理/","/credit/front/orderProcess/statusSave");//操作日志记录
            renderJson(new ResultType());
            return new ResultType();
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"订单状态更新失败!"));
            return new ResultType(0,"订单状态更新失败!");
        }
    }
    
    /**
    * @Description: 质检结果下拉选项
    * @date 2018年12月1日 下午5:33:00
    * @author: lxy
    * @version V1.0
    * @return
     */
    public void selectQuality(){
    	   Record record = new Record();
    String type= getPara("type");
    String  name= getPara("disPalyCol");
    String sql="select detail_id,dict_type,"+name
    		+ " as detail_name,detail_code as value from sys_dict_detail where dict_type=?";
    
    List<SysDictDetail> details =  SysDictDetail.dao.find(sql,type);
    renderJson(record.set("rows", details).set("total", details!=null?details.size():null));	
    }
	
    
    public void selectGdp(){
   List<CreditCompanyGdp>  gdps= CreditCompanyGdp.dao.find("select * from credit_company_growth_rate");
   renderJson(new Record().set("rows", gdps).set("total", gdps!=null?gdps.size():null));	

    }
    /**
     * 
    * @Description: 报告完成，获取报告价钱及币种，扣除该客户的点数
    * @date 2019年1月14日 下午4:55:59
    * @author: lxy
    * @version V1.0
    * @return
     */
    public void  price(String id){
    CreditOrderInfo orderInfo=	CreditOrderInfo.dao.findById(id);
    	
    }
}
