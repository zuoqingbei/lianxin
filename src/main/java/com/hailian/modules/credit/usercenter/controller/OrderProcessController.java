package com.hailian.modules.credit.usercenter.controller;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hailian.api.constant.ReportTypeCons;
import com.hailian.api.constant.RoleCons;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.company.service.CompanyService;
import com.hailian.util.http.HttpCrawler;

import org.apache.commons.lang3.StringUtils;




//import ch.qos.logback.core.status.Status;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInvestment;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;
import com.hailian.modules.admin.ordermanager.model.CreditKpiResult;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyLegalShareholderDetail;
import com.hailian.modules.admin.ordermanager.model.CreditOperationLog;
import com.hailian.modules.admin.ordermanager.model.CreditOrderFlow;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfoModel;
import com.hailian.modules.admin.ordermanager.model.CreditReportPrice;
import com.hailian.modules.credit.agentmanager.model.AgentCategoryModel;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.agentmanager.model.AgentPriceModel;
import com.hailian.modules.credit.agentmanager.service.AgentPriceService;
import com.hailian.modules.credit.agentmanager.service.TemplateAgentService;
import com.hailian.modules.credit.city.model.CityModel;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.mail.service.MailService;
import com.hailian.modules.credit.notice.model.NoticeLogModel;
import com.hailian.modules.credit.notice.model.NoticeModel;
import com.hailian.modules.credit.province.model.ProvinceModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.usercenter.service.KpiService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.modules.credit.utils.Office2PDF;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.modules.front.template.TemplateSysUserService;
import com.hailian.util.CharacterParser;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.ext.directive.Str;
import com.jfinal.upload.UploadFile;

/**
 * @time   2018年9月14日 上午11:00:00
 * @author lzg
 * todo 订单再分配
 */
/**
 * @author zhuch
 *
 */
@Api(tag = "订单流程", description = "订单流程")
@ControllerBind(controllerKey = "/credit/front/orderProcess")
public class OrderProcessController extends BaseProjectController{
    //文件服务器配置
   // public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
    public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
    public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
    public static final String userName = Config.getStr("ftp_userName");//域用户名
    public static final String password = Config.getStr("ftp_password");//域用户密码
    public static final String searverPort = Config.getStr("searver_port");//文件服务器端口
    public static final String ftpStore = Config.getStr("ftp_store");//ftp文件夹
    //订单管理菜单下公共路径
    private static final String ORDER_MANAGE_PATH = "/pages/credit/usercenter/order_manage/";
    //报告管理下菜单公共路径
    private static final String REPORT_MANAGE_PATH = "/pages/credit/usercenter/report_pages/";
    //正在开发页面路径
    private static final String DEVELOPING_PATH = "/pages/credit/usercenter/developing.html";
    //每种搜索类型需要对应的sql关键词字段名
    public static final Map<String,List<Object>> TYPE_KEY_COLUMN = new HashMap<>();
    //每种搜索类型需要对应的前端属性名
    public static final Map<String,List<Object>> WEB_PARAM_NAMES = new HashMap<>();
    /**
     * 订单分配的搜索类型
     */
    public static final String orderAllocation = "-1";
    public static LinkedList<Object> orderAllocationColumns = new LinkedList<>();//存储模糊搜索时后台sql对应字段
    public static LinkedList<Object> orderAllocationParamNames = new LinkedList<>();//存储模糊搜索时前台对应参数名
    /**
     * 订单管理中的订单核实
     */
    public static final String orderVerifyOfOrder = "-3";
    public static LinkedList<Object> orderVerifyOfOrderColumns = new LinkedList<>();
    public static LinkedList<Object> orderVerifyOfOrderParamNames = new LinkedList<>();
    /**
     * 订单管理中的订单查档(国外)
     */
    public static final String orderFilingOfOrder = "-4";
    public static LinkedList<Object> orderFilingOfOrderColumns = new LinkedList<>();
    public static LinkedList<Object> orderFilingOfOrderParamNames = new LinkedList<>();
    /**
     * 订单管理中的递交订单
     */
    public static final String orderSubmitOfOrder = "-5";
    public static LinkedList<Object> orderSubmitOfOrderColumns = new LinkedList<>();
    public static LinkedList<Object> orderSubmitOfOrderParamNames = new LinkedList<>();
    /**
     * 报告管理中的订单核实
     */
    public static final String orderVerifyOfReport = "-2";
    public static LinkedList<Object> orderVerifyOfReportColumns = new LinkedList<>();
    public static LinkedList<Object> orderVerifyOfReportParamNames = new LinkedList<>();
    /**
     * 报告管理中的信息录入
     */
    public static final String infoOfReport = "-6";
    public static LinkedList<Object> infoOfReportColumns = new LinkedList<>();
    public static LinkedList<Object>infoOfReportParamNames = new LinkedList<>();
    /**
     * 报告管理中的订单查档(国内)
     */
    public static final String orderFilingOfReport = "-7";
    public static LinkedList<Object> orderFilingOfReportColumns = new LinkedList<>();
    public static LinkedList<Object> orderFilingOfReportParamNames = new LinkedList<>();
   
    public static final String orderQualityOfReport = "-8";
    static{
        orderAllocationColumns.add("u1.realname");
        orderAllocationColumns.add("c.num");
        orderAllocationColumns.add("c.right_company_name_en");
        orderAllocationColumns.add("c.info_en_name");//录入的公司英文名称
    }
    static{
        orderAllocationParamNames.add("report_user");
        orderAllocationParamNames.add("num");
        orderAllocationParamNames.add("right_company_name_en");
        orderAllocationParamNames.add("info_en_name");
    }
    static{
        TYPE_KEY_COLUMN.put(orderAllocation,orderAllocationColumns);
        TYPE_KEY_COLUMN.put(orderVerifyOfReport,orderVerifyOfReportColumns);
        TYPE_KEY_COLUMN.put(orderVerifyOfOrder,orderVerifyOfOrderColumns);
        TYPE_KEY_COLUMN.put(orderFilingOfOrder,orderFilingOfOrderColumns);
        TYPE_KEY_COLUMN.put(orderSubmitOfOrder,orderSubmitOfOrderColumns);
        TYPE_KEY_COLUMN.put(infoOfReport,infoOfReportColumns);
        TYPE_KEY_COLUMN.put(orderFilingOfReport,orderFilingOfReportColumns);
    }
    static{
        WEB_PARAM_NAMES.put(orderAllocation, orderAllocationParamNames);
        WEB_PARAM_NAMES.put(orderVerifyOfReport, orderVerifyOfReportParamNames);
        WEB_PARAM_NAMES.put(orderVerifyOfOrder, orderVerifyOfOrderParamNames);
        WEB_PARAM_NAMES.put(orderFilingOfOrder, orderFilingOfOrderParamNames);
        WEB_PARAM_NAMES.put(orderSubmitOfOrder, orderSubmitOfOrderParamNames);
        WEB_PARAM_NAMES.put(infoOfReport, infoOfReportParamNames);
        WEB_PARAM_NAMES.put(orderFilingOfReport, orderFilingOfReportParamNames);
        WEB_PARAM_NAMES.put(orderQualityOfReport, orderFilingOfReportParamNames);
    }
    /**
     * @todo   展示订单分配页
     * @time   2018年9月14日 下午 14:38
     * @author lzg
     * @return_type   void
     */
    public void showReallocation(){
        render(ORDER_MANAGE_PATH+"order_allocation.html");
    }
    /**
     * @todo   展示订单管理下的订单核实页
     * @time   2018年9月12日 下午 13:30
     * @author lzg
     * @return_type   void
     */
    public void showOrderVerifyOfOrders(){
        render(ORDER_MANAGE_PATH+"order_verify.html");
    }
    /**
     * @todo   展示订单查档页
     * @time   2018年9月26日 下午 13:30
     * @author lzg
     * @return_type   void
     */
    public void showOrderFiling(){
        render(ORDER_MANAGE_PATH+"order_filing.html");
    }
    /**
     * @todo   展示未开发的页数
     * @time   2018年10月8日 下午05:02
     * @author lzg
     * @return_type   void
     */
    public void showUnderdevelopment(){
        render(DEVELOPING_PATH);
    }
    /**
     * @todo   展示递交报告
     * @time   2018年10月9日 上午11:24
     * @author lzg
     * @return_type   void
     */
    public void showSubmitReport(){
        render(ORDER_MANAGE_PATH+"order_submit.html");
    }
    /**
     * @todo   展示报告管理下的信息录入
     * @time   2018年10月11日 上午 11:02
     * @author lzg
     * @return_type   void
     */
    public void showReportInfoImport(){
        render(REPORT_MANAGE_PATH+"report_info_import.html");
    }
    /**
     *
     * @time   2018年10月14日 下午3:12:08
     * @author yangdong
     * @todo   展示报告管理下的报告质检
     * @param
     * @return_type   void
     */
    public void showReportBusiness(){
        render(REPORT_MANAGE_PATH+"report_quality_checking.html");
    }
    /**
     *
     * @time   2018年10月14日 下午3:12:17
     * @author yangdong
     * @todo   展示报告管理下的订单核实
     * @param
     * @return_type   void
     */
    public void showOrderVerify(){
        render(REPORT_MANAGE_PATH+"report_order_verify.html");
    }
    /**
     *
     * @time   2018年10月14日 下午3:12:25
     * @author yangdong
     * @todo   展示报告管理下的订单查档
     * @param
     * @return_type   void
     */
    public void showshowOrderFiling(){
        render(REPORT_MANAGE_PATH+"report_order_filing.html");
    }
    /**
     * 2018/10/15 15:20
     * lzg
     * 获取公司信息
     */
    public void getCompanyInfo(){
        String companyId = getPara("company_id");
        CompanyModel model = getModel(CompanyModel.class);
        model  = model.findById(companyId);
        renderJson(model);
    }

    /**
     * @todo   展示报告管理下的信息录入的填报详情页
     * @time   2018年9月29日 上午 11:02
     * @author lzg
     * @return_type   void
     */
    public void showReportedBasicInfo(){
        render(REPORT_MANAGE_PATH+"reported_basic_info.html");
    }
    /**
     * @todo   展示报告管理下的可配置的填报页面
     * @time   2018年10月26日 上午 11:02
     * @author zhuch
     * @return_type   void
     */
    public void showReportedConfig(){
        render(REPORT_MANAGE_PATH+"report_config.html");
    }
    
    /**
     * @author zhuch
     * 填报翻译
     */
    public void showReportedTranslate(){
    	render(REPORT_MANAGE_PATH+"report_translate.html");
    }
    /**
     * @author zhuch
     * 
     */
    public void showReportedAnalyze(){
    	render(REPORT_MANAGE_PATH+"report_analyze.html");
    }
    /**
     * @author zhuch
     * 质检翻译
     */
    public void showReportedAnalyzeQuality(){
    	render(REPORT_MANAGE_PATH+"report_translate_quality.html");
    }

    //展示列表功能公共雏形
    private Page<CreditOrderInfo> PublicListMod(String searchType){
        int pageNumber = getParaToInt("pageNumber",1);
        int pageSize = getParaToInt("pageSize",10);
        //从表单获取排序语句
        String sortName = getPara("sortName");
        String sortOrder = getPara("sortOrder");
        String statusName=getPara("statusName");
        String orderBy = "";
        if(!StringUtil.isEmpty(sortName)){
            if(sortOrder!=null){
                orderBy = sortName+" "+sortOrder;
            }else{
                sortOrder = "";
                orderBy = sortName+" desc ";
            }
        }else{
            sortName = "";
        }
        //获取前台关键词
        List<Object> keywords = new LinkedList<>();
        CreditOrderInfo model = getModel(CreditOrderInfo.class);
        for (Object  columnName: WEB_PARAM_NAMES.get(searchType)) {
            if(StringUtil.isEmpty((String) getPara((String) columnName))){
                keywords.add("");
            }else{
                keywords.add((String) getPara((String) columnName).trim());
            }
        }
        //分页查询
        Page<CreditOrderInfo> pager = CreditOrderInfo.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType,statusName, this);
        //插入回显数据
        for (CreditOrderInfo page : pager.getList()) {
            for (int i = 0; i <  WEB_PARAM_NAMES.get(searchType).size(); i++) {
                page.put((String)WEB_PARAM_NAMES.get(searchType).get(i)+"Key",keywords.get(i));
            }
            page.put("pageNumber",pageNumber);
            page.put("pageSize",pageSize);
            page.put("sortName",sortName);
            page.put("sortOrder",sortOrder);
            //插入文件信息
            Integer orderId = page.get("id");
            String status = page.get("status");
            List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusType(orderId+"", status, this);
            for (CreditUploadFileModel creditUploadFileModel : files) {
                creditUploadFileModel.set("view_url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("view_url"));
                creditUploadFileModel.set("url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("url"));
            }
            page.put("files",files);
        }
        return pager;
    }
    /**
     * 订单查档（国内）
     * @param searchType
     * @param status
     * @return
     */
    private Page<CreditOrderInfo> PublicListMod(String searchType,String status1){
        int pageNumber = getParaToInt("pageNumber",1);
        int pageSize = getParaToInt("pageSize",10);
        //从表单获取排序语句
        String sortName = getPara("sortName");
        String sortOrder = getPara("sortOrder");
        String statusName=getPara("statusName");
        String orderBy = "";
        if(!StringUtil.isEmpty(sortName)){
            if(sortOrder!=null){
                orderBy = sortName+" "+sortOrder;
            }else{
                sortOrder = "";
                orderBy = sortName+" desc ";
            }
        }else{
            sortName = "";
        }
        //获取前台关键词
        List<Object> keywords = new LinkedList<>();
        CreditOrderInfo model = getModel(CreditOrderInfo.class);
        for (Object  columnName: WEB_PARAM_NAMES.get(searchType)) {
            if(StringUtil.isEmpty((String) getPara((String) columnName))){
                keywords.add("");
            }else{
                keywords.add((String) getPara((String) columnName).trim());
            }
        }
        //分页查询
        Page<CreditOrderInfo> pager = CreditOrderInfo.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType,statusName, this);
        //插入回显数据
        for (CreditOrderInfo page : pager.getList()) {
            for (int i = 0; i <  WEB_PARAM_NAMES.get(searchType).size(); i++) {
                page.put((String)WEB_PARAM_NAMES.get(searchType).get(i)+"Key",keywords.get(i));
            }
            page.put("pageNumber",pageNumber);
            page.put("pageSize",pageSize);
            page.put("sortName",sortName);
            page.put("sortOrder",sortOrder);
            //插入文件信息
            Integer orderId = page.get("id");
            String status = page.get("status");
            List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusType(orderId+"", status, this);
            for (CreditUploadFileModel creditUploadFileModel : files) {
                creditUploadFileModel.set("view_url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("view_url"));
                creditUploadFileModel.set("url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("url"));
            }
            page.put("files",files);
        }
        return pager;
    }
    //修改或者删除功能公共雏形
    @SuppressWarnings("unused")
    private void PublicUpdateMod(Map<String,Object> map){
        CreditOrderInfo model = getModel(CreditOrderInfo.class);
        model.removeNullValueAttrs();
        model = getModel(CreditOrderInfo.class);
        Integer userid = getSessionUser()==null?444:getSessionUser().getUserid();
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
    }
    /**
     * 代理分配
     * @author doushuihai
     * @throws Exception
     * @date 2018年10月13日下午7:29:46
     * @TODO
     */
    private CreditOrderInfo PublicUpdateAgentMod(Map<String,Object> map,String ismail) throws Exception{
        CreditOrderInfo model = getModel(CreditOrderInfo.class);
        model = getModel(CreditOrderInfo.class);
        String orderId = model.get("id")+"";
        String oldStatus = model.findById(orderId).get("status");
        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        model.set("update_by",userid);
        model.set("update_date", now);
        if(map!=null){
            for (String key : map.keySet()) {
                model.set(key, map.get(key));
            }
        }
        String companyid=model.get("company_id");

        model.set("status", "295");
        model.update();
        //获取订单记录对象
        CreditOrderFlow cof = new CreditOrderFlow();
        //订单号
        cof.set("order_num", model.get("num"));
        //订单状态
        cof.set("order_state", model.get("status"));
        //操作人
        cof.set("create_oper", userid);
        //操作时间
        cof.set("create_time",DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYYMMDD));
        cof.save();
//        MailService.service.toSendMail(ismail, orderId,model.get("agent_id"),userid,this);//代理分配发送邮件
        return model.set("status", oldStatus);
    }
  
    /**
     *获取订单数据
     */
    /**
     *  update加入获取代理下拉框
     * @author doushuihai
     * @date 2018年10月13日下午2:11:44
     * @TODO
     */
    public void listJson() {
        //获取查询类型
        String searchType = (String) getRequest().getParameter("searchType");
        String status =   getPara("statusName");
        //分页查询
        Page<CreditOrderInfo> pager = PublicListMod(searchType);
        List<CreditOrderInfo> rows = pager.getList();
        TemplateSysUserService templete = new TemplateSysUserService();
       
        //若是搜索类型是订单分配做特殊处理
        if(searchType.equals(orderAllocation)){
            for (CreditOrderInfo creditOrderInfo : rows) {
                //参数2代表角色id为2
                String seleteStr = templete.getSysUser(2, creditOrderInfo.get("report_user"));
                creditOrderInfo.put("seleteStr",seleteStr);
            }
        }
        //若是搜索类型是订单查档做特殊处理
        if(searchType.equals(orderFilingOfOrder)||searchType.equals(orderFilingOfReport)){
            for (CreditOrderInfo creditOrderInfo : rows) {
                //查询代理类型
                String seleteStr = TemplateAgentService.templateagentservice.getAgentIdString(creditOrderInfo.get("agent_id"));
                creditOrderInfo.put("seleteAgentStr",seleteStr);
                String seleteAgentCateStr = TemplateAgentService.templateagentservice.getAgentCateString(creditOrderInfo.get("agent_id"),creditOrderInfo.get("agent_category"));
                creditOrderInfo.put("seleteAgentCateStr",seleteAgentCateStr);
                
            }
        }
        int totalRow = pager.getTotalRow();
        //若时搜索按钮
        ResultType resultType = new ResultType();
        resultType = new ResultType(totalRow,rows);

        renderJson(resultType);
    }

    public void getAgentCategory(){
        String agentid = (String) getRequest().getParameter("agentid");
        List<AgentCategoryModel> findAll = AgentCategoryModel.dao.findAll(agentid);
        renderJson(findAll);
    }
    public void getAgentCate(){
        String agentid = (String) getRequest().getParameter("agentid");
//		List<AgentCategoryModel> findAll = AgentCategoryModel.dao.findAll(agentid);
        List<AgentPriceModel> findAll = AgentPriceModel.dao.findAgentCateSelect(agentid,true);
        renderJson(findAll);
    }
    public void getAgent(){
       
        List<AgentModel> findAll = AgentModel.dao.find("select * from credit_agent where del_flag='0'");
        renderJson(findAll);
    }
    /**
     * @todo   订单状态保存
     * @time   2018年9月20日 下午4:30:00
     * @author lzg
     * @return_type   订单编号
     */
    public  ResultType  statusSave() {

        try {
            String code = (String) getRequest().getParameter("statusCode");
            Map<String,Object> map = new HashMap<>();
            if(code==null||"".equals(code.trim())){
                map = null;
            }else{
                map.put("status", code);
            }
          //说明批量操作
            String ids=	getPara("ids");//订单批量重新分配
            if(StringUtils.isNotBlank(ids)) {
            	map = new HashMap<>();
            	 String [] orderId=	ids.split(",");
                 for (String oid : orderId) {
     	           map.put("id", oid);
     	           PublicUpdateMod(map);
     	        //添加站内信，
     	            addNoice(code,oid);
     			}
            }else {
            	CreditOrderInfo model = getModel(CreditOrderInfo.class);
                int orderId = model.get("id");
                //计算绩效
                /*if(!StrUtils.isEmpty(orderId+"")&&"314".equals(code)) {
                	 getKpi(model);
                }*/
                //报告填报完成
                if("294".equals(code)){
                    //todo 报告填报完成后需要分配质检员
                    String iqcId = OrderManagerService.service.getUserIdtoOrder(RoleCons.IQC);
                    if(iqcId!=null){
                        map.put("IQC",iqcId);
                    }
                }
                //修改订单状态
                PublicUpdateMod(map);
                if("595".equals(code)){
                	//根据订单号找到填报语言对应的公司id
                    CreditOrderInfo orderInfo = model.findById(orderId);
                	String companyId = orderInfo.get("company_id")+"";
                    String reportType = orderInfo.get("report_type")+"";
                    //new CompanyService().enterpriseGrab(companyId,getPara("model.company_by_report"),"612");
                    //调用香港查册网
                    //HttpCrawler.getIcrisUrl(getPara("model.company_by_report"), getPara("companyId"), getModel(CreditOrderInfo.class));
                    //爬取商务部业务系统网站
                    //HttpCrawler.getMofcomUrl(getPara("model.company_by_report"), getPara("companyId"), getModel(CreditOrderInfo.class));
                    //爬虫完毕更新状态
                    //CreditOrderInfo model2 = new CreditOrderInfo(); model2.set("id", orderId).set("status", 694);  model2.update();
                    //使用线程调用爬虫接口
                    Thread td = new Thread(new CrawlerThreed(reportType,companyId,getPara("model.company_by_report"),orderInfo));
                    td.start();
                    try {
                        //增加跟踪记录
                      CreditOrderInfo tempModel = model;
                      tempModel.set("status","-596");
                      CreditOrderFlow.addOneEntry(this, model,"说明:此处记录爬取公司名称"+getPara("model.company_by_report"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                addNoice(code,"");
            }
           
            
            Integer userid = getSessionUser().getUserid();
            CreditOperationLog.dao.addOneEntry(userid, null,"订单管理/","/credit/front/orderProcess/statusSave");//操作日志记录
            
            
            //调用企查查接口
           
            renderJson(new ResultType());
            return new ResultType();
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"订单状态更新失败!"));
            return new ResultType(0,"订单状态更新失败!");
        }
    }

    public  void  addNoice(String status,String orderid){
        //新增公告内容
        NoticeModel model=new NoticeModel();
        Integer userid = getSessionUser()==null?444:getSessionUser().getUserid();
        String now = getNow();
        CreditOrderInfoModel orderInfoModel=getModel(CreditOrderInfoModel.class);
        //查订单
        CreditOrderInfo info=null;
        if(StringUtils.isNotBlank(orderid)) {
        	info=	CreditOrderInfo.dao.getId(Integer.parseInt(orderid), null);
        }else {
        	 info=	CreditOrderInfo.dao.getId(orderInfoModel.get("id"), null);
        }
       
        //公告子表添加
        NoticeLogModel logModel=new NoticeLogModel();
        //订单核实，向客服发起
        if (status!=null&&status.equals("500")) {
            logModel.set("user_id", userid);
            model.clear();
            model.set("notice_title", "订单核实");
            model.set("notice_content", info.get("right_company_name_en")+"公司信息需要您核实");
        }
        //102,396 国外订单填报完成 向客服发起
        if (status!=null&&status.equals("314")) {
            if (info.get("report_type").equals("21")
                    ||info.get("report_type").equals("12")
                    ||!info.get("country").equals("中国大陆")) {
                logModel.set("user_id", userid);
                model.clear();
                model.set("notice_title", "102,396 国外订单填报完成");
                model.set("notice_content", "您有新的报告需要质检");
            }
            //除102,396 国外订单填报完成 向质检员发起
            if (!info.get("report_type").equals("21")
                    ||!info.get("report_type").equals("12")
                    ||info.get("country").equals("中国大陆")) {
                logModel.set("user_id", info.get("IQC"));
                model.clear();
                model.set("notice_title", "102,396 国外订单填报完成");
                model.set("notice_content", "您有新的报告需要质检");
            }
        }
        //新订单分配，查档完成，核实完成，报告退回，订单催问 向报告员
        if (status!=null&&status.equals("291")) {
            logModel.set("user_id", info.get("report_user"));
            model.clear();
            model.set("notice_title", "新订单分配");
            model.set("notice_content", "您有一个新订单待处理");

        }
        if (status!=null&&status.equals("295")) {
            logModel.set("user_id", info.get("report_user"));
            model.clear();
            model.set("notice_title", "查档完成");
            model.set("notice_content", "您查档的"+info.get("right_company_name_en")+"公司信息已查档完成");
        }
        if(status!=null&&status.equals("292")){
            logModel.set("user_id", info.get("report_user"));
            model.clear();
            model.set("notice_title", "核实完成");
            model.set("notice_content", "您核实的"+info.get("right_company_name_en")+"公司信息已核实完成");
        }
        if (status!=null&&status.equals("299")) {
            logModel.set("user_id", info.get("report_user"));
            model.clear();
            model.set("notice_title", "报告退回");
            model.set("notice_content", "您有报告被退回，请及时修改");
        }

        //订单查档 向质检员发起
        if(status!=null&&status.equals("294")){
            logModel.set("user_id", info.get("IQC"));
            model.clear();
            model.set("notice_title", "订单查档");
            model.set("notice_content", info.get("right_company_name_en")+"公司信息需要您查档");
        }
        model.set("create_by", userid);
        model.set("create_date", now);
        model.save();
        logModel.set("notice_id", model.get("id"));
        logModel.set("read_unread", "1");
        logModel.save();

    }

    /**
     *
     * @Description: 订单催问
     * @date 2018年11月15日 上午9:45:40
     * @author: lxy
     * @version V1.0
     * @return
     */
    public void askOrder(){
        String status=getPara("status");
        String id=getPara("id");
        String ids= getPara("ids");//接收批量催问的id参数
        NoticeModel model=new NoticeModel();
        Integer userid = getSessionUser().getUserid();
        String now = getNow();
        try {
			
		
        if(StringUtils.isNotBlank(ids)&&StringUtils.isBlank(id)){
        	//批量催问订单id不是空
          String [] id2 = 	ids.split(",");
         for (String id3 : id2) {
        	//查订单
             CreditOrderInfo info=	CreditOrderInfo.dao.getId(Integer.parseInt(id3), null);
             //公告子表添加
             NoticeLogModel logModel=new NoticeLogModel();
             //是否催问过，催问后改变订单催问状态
                 logModel.set("user_id", info.get("report_user"));
                 model.clear();
                 model.set("notice_title", "订单催问");
                 model.set("notice_content", "您有订单催问，请及时处理。");
                 //修改订单催问信息
                 CreditOrderInfo orderInfo=new CreditOrderInfo();
                 orderInfo.set("id",info.get("id") );
                 orderInfo.set("is_ask", "1");
                 orderInfo.update();
                 model.set("create_by", userid);
                 model.set("create_date", now);
                 model.save();
                 logModel.set("notice_id", model.get("id"));
                 logModel.set("read_unread", "1");
                 logModel.save();   
		    }
         renderJson(new ResultType(1,"订单催问成功!"));
        }else{ 
        //查订单
        CreditOrderInfo info=	CreditOrderInfo.dao.getId(Integer.parseInt(id), null);
        //公告子表添加
        NoticeLogModel logModel=new NoticeLogModel();
        //是否催问过，催问后改变订单催问状态
       
            logModel.set("user_id", info.get("report_user"));
            model.clear();
            model.set("notice_title", "订单催问");
            model.set("notice_content", "您有订单催问，请及时处理。");
            //修改订单催问信息
            CreditOrderInfo orderInfo=new CreditOrderInfo();
            orderInfo.set("id",info.get("id") );
            orderInfo.set("is_ask", "1");
            orderInfo.update();
            model.set("create_by", userid);
            model.set("create_date", now);
            model.save();
            logModel.set("notice_id", model.get("id"));
            logModel.set("read_unread", "1");
            logModel.save();
            renderJson(new ResultType(1,"订单催问成功!"));
       }
        } catch (Exception e) {
        	e.printStackTrace();
        	renderJson(new ResultType(0,"订单催问失败!"));
		}
    }
    /**
     * 订单代理分配国内
     * @author doushuihai
     * @date 2018年10月13日下午7:20:55
     * @TODO
     */
    public  void  orderAgentSave() {
        try {
            String code = (String) getRequest().getParameter("statusCode");
            Map<String,Object> map = new HashMap<>();
            if(code==null||"".equals(code.trim())){
                map = null;
            }else{
                map.put("status", code);
            }
          
            String companyid = (String) getRequest().getParameter("company_id");
            String agent_id = (String) getRequest().getParameter("agent_id");
            map.put("agent_id", agent_id);
            String agent_category = (String) getRequest().getParameter("agent_category");
            map.put("agent_category", agent_category);
            if (StringUtils.isBlank(companyid)) {
				//说明批量操作
	            String ids=	getPara("ids");
	            String [] orderId=	ids.split(",");
	            for (String oid : orderId) {
						//查订单获取companyid
		           CreditOrderInfo info= 	CreditOrderInfo.dao.findById(oid);  
		           map.put("id", oid);
		           map.put("num", info.get("num"));
		           AgentPriceModel agentPrice=null;
		           agentPrice = AgentPriceService.service.getAgentPriceByOrder(oid);
		           if(agentPrice !=null){
		               map.put("agent_priceId", agentPrice.get("id"));
		           }
		           PublicUpdateMod(map);
				}
			}else {
				  String oid = (String) getRequest().getParameter("model.id");
                AgentPriceModel agentPrice = AgentPriceService.service.getAgentPriceByOrder(oid);
                if(agentPrice !=null){
                    map.put("agent_priceId", agentPrice.get("id"));
                }
                PublicUpdateMod(map);
		   }
            renderJson(new ResultType());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"订单代理分配更新失败!"));
        }
    }
    /**
     * 订单代理分配国外
     * @author doushuihai
     * @date 2018年10月13日下午7:20:55
     * @TODO
     */
    public  void  orderAgentAbroadSave() {
        try {
            String code = (String) getRequest().getParameter("statusCode");
            String orderId = (String) getRequest().getParameter("orderId");
             String idS=  getPara("ids");
            Integer userid = getSessionUser().getUserid();
            Map<String,Object> map = new HashMap<>();
            if(code==null||"".equals(code.trim())){
                map = null;
            }else{
                map.put("status", code);
            }
            String ismail = (String) getRequest().getParameter("ismail");
            String agent_id = (String) getRequest().getParameter("agent_id");
            String country= (String) getRequest().getParameter("country");
            String speed = (String) getRequest().getParameter("speed");
            map.put("agent_id", agent_id);
           if (StringUtils.isBlank(country)&&StringUtils.isBlank(speed)) {
        	   String ids[]=idS.split(",");
               for (String oid : ids) {
                   CreditOrderInfo orderInfo=	CreditOrderInfo.dao.findById(oid);
                   AgentPriceModel agentPrice = AgentPriceService.service.getAgentAbroadPrice(agent_id,orderInfo.get("country"),orderInfo.get("speed"));
                   if(agentPrice !=null){
                       map.put("agent_priceId", agentPrice.get("id"));
                   }
                   map.put("id", oid);
                   map.put("num", orderInfo.get("num"));
                   PublicUpdateMod(map);
                   MailService.service.toSendMail(ismail, oid,agent_id,userid,this);//代理分配发送邮件
               }
		   }else {
                AgentPriceModel agentPrice = AgentPriceService.service.getAgentAbroadPrice(agent_id,country,speed);
                if(agentPrice !=null){
                    map.put("agent_priceId", agentPrice.get("id"));
                }
            
            PublicUpdateMod(map);
            MailService.service.toSendMail(ismail, orderId,agent_id,userid,this);//代理分配发送邮件
		   }
            renderJson(new ResultType());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"订单代理分配更新失败!"));
        }
    }
    /**
     *
     * @Description: 批量分批
     * @date 2018年11月15日 下午4:24:21
     * @author: lxy
     * @version V1.0
     * @return
     */
    public  void  orderbatchAgent() {
        try {
            String code = (String) getRequest().getParameter("statusCode");
            String orderId = (String) getRequest().getParameter("orderId");
             String ids= getPara("ids");
            Map<String,Object> map = new HashMap<>();
            if(code==null||"".equals(code.trim())){
                map = null;
            }else{
                map.put("status", code);
            }
            String ismail = (String) getRequest().getParameter("ismail");
            String agent_id = (String) getRequest().getParameter("agent_id");
            map.put("agent_id", agent_id);
            Integer userid = getSessionUser().getUserid();
            String id[]=ids.split(",");
            for (String oid : id) {
                CreditOrderInfo orderInfo=	CreditOrderInfo.dao.findById(oid);
                AgentPriceModel agentPrice = AgentPriceService.service.getAgentAbroadPrice(agent_id,orderInfo.get("country"),orderInfo.get("speed"));
                if(agentPrice !=null){
                    map.put("agent_priceId", agentPrice.get("id"));
                }
                map.put("id", oid);
                PublicUpdateMod(map);
                MailService.service.toSendMail(ismail, orderId,agent_id,userid,this);//代理分配发送邮件
            }
            renderJson(new ResultType());
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"订单代理分配更新失败!"));
        }
    }

    /**
     * 通过订单id获取不同流程下文件信息
     */
    public void getFilesByOrderId(){
        String orderId = getPara("orderId");
        String business_type = getPara("business_type");
        List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusinessType(orderId+"" ,business_type, this);
        for (CreditUploadFileModel creditUploadFileModel : files) {
            creditUploadFileModel.set("view_url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("view_url"));
            creditUploadFileModel.set("url","http://"+ ip + ":" + searverPort+"/"+creditUploadFileModel.get("url"));
        }
        ResultType result = new ResultType();
        result.setFiles(files);
        renderJson(result);
    }
    /**
     * @todo   带有文件上传功能的保存功能
     * @time   2018年9月21日 上午9:21:00
     * @author lzg
     * @return_type   void
     */
    public void statusSaveWithFileUpLoad(){
        try {
            //从前台获取文件
            List<UploadFile>  upFileList = getFiles("Files");
            //获取订单id和状态
            CreditOrderInfo model = getModel(CreditOrderInfo.class);
            String orderId = model.get("id")+"";
            String oldStatus = model.findById(orderId).get("status")+"";
            if(orderId==null||oldStatus==null||"".equals(orderId)||"".equals(oldStatus)){
                renderJson(new ResultType(0,"订单编号和订单状态不能为空!"));
                return;
            }
            //上传文件
            ResultType result = uploadFile(orderId,oldStatus,upFileList,/*getSessionUser().getUserid()*/1,null);
            Integer userid = getSessionUser().getUserid();
            CreditOperationLog.dao.addOneEntry(userid, null, "","/credit/front/orderProcess/statusSaveWithFileUpLoad");//操作日志记录
            if(result.getStatusCode()==0){
                renderJson(result);
            }else{
                //更新状态
                if(statusSave().getStatusCode()==0){
                    renderJson(new ResultType(0,"订单状态更新失败!"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0,"发生未知错误!"));
        }
    }
    /**
     * 获取前台文件上传到文件服务器并将文件信息记录到文件实体表
     * return resultJson
     */
    public static ResultType uploadFile(String businessId, String businessType,List<UploadFile> upFileList,int userid,String randomCode){
        List<File> commonFiles = new ArrayList<File>();
        List<File> pdfFiles = new ArrayList<File>();
        CreditUploadFileModel fileModel = new CreditUploadFileModel();
        fileModel.set("business_type", businessType);
        fileModel.set("business_id",businessId);
        fileModel.set("random_code",randomCode);
        int size = upFileList.size();
        if(size>0){
            //long now = new Date().getTime();
            String now = UUID.randomUUID().toString().replaceAll("-", "");
            //上传的文件在ftp服务器按日期分目录
            String storePath = ftpStore+"/"+DateUtils.getNow(DateUtils.YMD);
            try {
                List<String> pdfNameList = new LinkedList<>();
                for(UploadFile uploadFile:upFileList){
                    //获取真实文件名
                    String originalFile = uploadFile.getOriginalFileName();
                    //根据文件后缀名判断文件类型
                    String ext = FileTypeUtils.getFileType(originalFile);
                    //检查格式
                    if(!FileTypeUtils.checkType(ext)){
                        return new ResultType(0, "请检查 "+originalFile+" 的格式!");
                    }
                    //检查大小
                   /* if(uploadFile.getFile().length()>maxPostSize){
                        return new ResultType(0, originalFile+" 必须小于5兆!");
                    }*/
                    File pdf = null;
                    //如果上传文档不是pdf或者图片或者则转化为pdf，以作预览
                    if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)&&!ext.equalsIgnoreCase("html")){
                       try {
                    	   pdf = Office2PDF.toPdf(uploadFile);
					} catch (Exception e) {
						// TODO: handle exception
					}
                    }
                    commonFiles.add(uploadFile.getFile());
                    if(pdf!=null)
                        pdfFiles.add(pdf);
                }
                //将文件上传到服务器
                boolean storePdfFile = true;
                if(pdfFiles.size()>0) {
                    storePdfFile = FtpUploadFileUtils.storeMoreFtpFile3(now+"",pdfFiles,storePath,ip,port,userName,password);
                }
                boolean storeCommonFile = FtpUploadFileUtils.storeMoreFtpFile3(now+"",commonFiles,storePath,ip,port,userName,password);
                if(!storePdfFile){
                    return new ResultType(0, "预览文件生成异常!");
                }
                if(!storeCommonFile){
                    return new ResultType(0, "文件上传异常!");
                }
                //String ftpName=name+now+"."+type;

                //将文件信息保存到实体类
                for (int i=0;i<upFileList.size();i++) {
                    //获取真实文件名
                    String originalFile = upFileList.get(i).getFileName();
                    //不带后缀的文件名
                    String originalFileName = FileTypeUtils.getName(originalFile);
                	CharacterParser c=new CharacterParser();
                	originalFileName=c.getSpelling(originalFileName);
                    //originalFileName=URLEncoder.encode(originalFileName, "UTF-8");
                    //originalFileName="";
                    //根据文件后缀名判断文件类型
                    String ext = FileTypeUtils.getFileType(originalFile);
                    //上传到服务器时的文件名
                    String FTPfileName = originalFileName + now + "." + ext;
                    String PDFfileName = originalFileName + now + "." + ext;
                    if(!ext.equals("pdf") && !FileTypeUtils.isImg(ext)){//如果上传文档不是pdf或者图片则转化为pdf，以作预览
                        PDFfileName = originalFileName + now + "." + "pdf";
                    }
                    //String pdfFactpath=storePath+"/"+pdf_FTPfileName;
						/*if(pdf!=null){
							pdf.delete();
						}*/
                    String factpath = storePath + "/" + FTPfileName;
                    String ftpFactpath = storePath + "/" + PDFfileName;
                    //String url = "http://" + ip+"/" + storePath + "/" + FTPfileName;
                    //将上传信息维护进实体表
                    UploadFileService.service.save(0,upFileList.get(i), factpath,factpath,ftpFactpath,ftpFactpath,fileModel,originalFileName+now,userid);
                }


            }catch(Exception e){
                e.printStackTrace();
                return new ResultType(0, "发生未知错误!");
            }finally {
                pdfFiles.forEach((File pdf)->{pdf.delete();});
            }
            return new ResultType(1, "文件上传成功!");
        }else{
            return new ResultType(1, "操作成功!");
        }
    }

    /**
     * 根据文件id删除文件
     */
    public void deleteFile(){
        try {
            CreditUploadFileModel model = getModel(CreditUploadFileModel.class);
            model.set("del_flag", "1");
            model.update();
            renderJson(new ResultType(1, "操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            renderJson(new ResultType(0, "操作失败!"));
        }
    }


    public void getKpi( CreditOrderInfo model,Integer userId) {
        getKpi(model,userId,false);
    }
    /**
        * 获取绩效
     * lzg
     */
    public void getKpi( CreditOrderInfo model,Integer userId,boolean saveResource) {
    	String  orderId = model.get("id")+"";
    	if(model==null||StrUtils.isEmpty(orderId)) {
    		throw new RuntimeException("实体和实体id不能为空!");
    	}
    	 
    	 String now = getNow();
    	if(!saveResource){
            model = model.findById(orderId);
        }
    	 String reportUser = model.get("report_user");
    	 String IQC = model.get("IQC");
    	 String  translateUser = model.get("translate_user");
    	 String  analyzeUser = model.get("analyze_user");
    	 final CreditOrderInfo modelForTx = CreditOrderInfo.dao._setAttrs(model);
         //计算绩效
    	boolean isSucceed =  Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
					CreditKpiResult publicModel = new CreditKpiResult();
					publicModel.set("update_by", userId);
					publicModel.set("create_by", userId);
					publicModel.set("update_date", now);
					publicModel.set("create_date", now);
					publicModel.set("order_id", orderId);
					
					CreditKpiResult tempModel = new CreditKpiResult();
		           	KpiService kpiServie = new KpiService();
		           	boolean isHasKpi_reportUser  = Db.query("select id from credit_kpi_result where order_id="+orderId+" and role_id="+2).size()==0?false:true;
		           	boolean isHasKpi_IQC = Db.query("select id from credit_kpi_result where order_id="+orderId+" and role_id="+4).size()==0?false:true;
		           	boolean isHasKpi_translateUser  = Db.query("select id from credit_kpi_result where order_id="+orderId+" and role_id="+6).size()==0?false:true;
		           	boolean isHasKpi_analyzeUser  = Db.query("select id from credit_kpi_result where order_id="+orderId+" and role_id="+5).size()==0?false:true;
		           	if(!(!isHasKpi_reportUser&&!isHasKpi_IQC&&!isHasKpi_translateUser&&!isHasKpi_analyzeUser)) {
		           		return false;
		           	}
		           	//报告员计算逻辑
		           	if(!StrUtils.isEmpty(reportUser)) {
		           		BigDecimal coefficient = getCoefficient(2, orderId);//报告员绩效系数
		           		BigDecimal reportUserKpi = kpiServie.getKpi(2+"",modelForTx).multiply(coefficient);//当前订单的报告员
		           		System.out.println("报告员绩效:"+reportUserKpi);
		           		tempModel.clear()._setAttrs(publicModel).set("user_id", reportUser).set("money", reportUserKpi).set("role_id", 2);
		           		tempModel.save();
		           	}
		        	//质检员计算逻辑
		           	if(!StrUtils.isEmpty(IQC)) {
		           		BigDecimal IQCKpi = kpiServie.getKpi(4+"" ,modelForTx);//当前订单的质检员
		            	System.out.println("质检员绩效:"+IQCKpi);
		           		tempModel.clear()._setAttrs(publicModel).set("user_id", IQC).set("money", IQCKpi).set("role_id", 4);;
		           		tempModel.save();
		           	}
		        	//翻译员计算逻辑
		           	if(!StrUtils.isEmpty(translateUser)) {
		           		BigDecimal coefficient = getCoefficient(6, orderId);//翻译绩效系数
		           		BigDecimal translateKpi = kpiServie.getKpi(6+"" ,modelForTx).multiply(coefficient);//当前订单的翻译
		           		System.out.println("翻译员绩效:"+translateKpi);
		           		tempModel.clear()._setAttrs(publicModel).set("user_id", translateUser).set("money", translateKpi).set("role_id", 6);;
		           		tempModel.save();
		           	}
		           	//分析员计算逻辑
		           	if(!StrUtils.isEmpty(analyzeUser)) {
		           		BigDecimal coefficient = getCoefficient(5, orderId);//翻译绩效系数
		           		BigDecimal analystKpi = kpiServie.getKpi(5+"" ,modelForTx).multiply(coefficient);//当前订单的分析员
		           		System.out.println("分析员绩效:"+analystKpi);
		           		tempModel.clear()._setAttrs(publicModel).set("user_id", analyzeUser).set("money", analystKpi).set("role_id", 5);;
		           		tempModel.save();
		           	}
				return true;
			}
		 });
    	
         if(!isSucceed) {
        	throw new  RuntimeException("该订单已经计算过绩效,请勿重复计算!");
        }
         
    }
    /**
         * 根据角色id和订单id获取绩效
     * lzg
     * @param roleId
     * @param orderId
     * @return系数
     */
    public static   BigDecimal getCoefficient(int  roleId,String orderId) {
    	int qualityType = -1;
    	if(roleId==2) {
    		qualityType = 2;
    	}else  if(roleId==6) {
    		qualityType = 1;
    	}else  if(roleId==5) {
    		qualityType = 3;
    	}
    	//分析质检分
   		String qualityScore = Db.queryStr("select grade from  credit_quality_opintion where quality_type="+qualityType+" and del_flag=0 and  order_id="+orderId);
   		//绩效系数
   		BigDecimal coefficient  = qualityScore==null?null:new BigDecimal(100).subtract(new BigDecimal(qualityScore)); 
   		coefficient = coefficient==null?new BigDecimal(1):(coefficient.compareTo(new BigDecimal(0))==-1?new BigDecimal(0):coefficient); 
		return coefficient;
    }
    
}
	
	/*//加法  
	 * int a = bigdemical.compareTo(bigdemical2)
		a = -1,表示bigdemical小于bigdemical2；
		a = 0,表示bigdemical等于bigdemical2；
		a = 1,表示bigdemical大于bigdemical2；
		6 bignum3 =  bignum1.add(bignum2);       
		7 System.out.println("和 是：" + bignum3);  
		8   
		9 //减法  
		10 bignum3 = bignum1.subtract(bignum2);  
		11 System.out.println("差  是：" + bignum3);  
		12   
		13 //乘法  
		14 bignum3 = bignum1.multiply(bignum2);  
		15 System.out.println("积  是：" + bignum3);  
		16   
		17 //除法  
		18 bignum3 = bignum1.divide(bignum2);  
		19 System.out.println("商  是：" + bignum3);   */
//爬虫通过线程操作
class CrawlerThreed implements Runnable {
        String companyId = "";
        String company = "";
        String reportType = "";
        CreditOrderInfo orderInfo = null;

        /**
         * 数据初始化
         * @param reportType 模板类型
         * @param companyId 公司id
         * @param company   公司名称
         * @param orderInfo 订单信息
         */
        public CrawlerThreed(String reportType,String companyId, String company, CreditOrderInfo orderInfo) {
            this.companyId = companyId;
            this.company = company;
            this.orderInfo = orderInfo;
            this.reportType = reportType;
        }

        @Override
        public void run() {
            try {
                String infoLanguage = Db.query("select info_language from credit_report_type where id=?",reportType).get(0)+"";
                //查询语言
                /*if(ReportTypeCons.BUSI_EN.equals(reportType)||ReportTypeCons.BUSI_ZH.equals(reportType)){
                	CreditCompanyInfo in = CreditCompanyInfo.dao.findFirst("select * from credit_company_info where order_id=? and del_flag=0",orderInfo.get("company_id")+"");
                	if(in!=null){
                		infoLanguage=in.get("sys_language")+"";
                	}
                }*/
                //调用企查查接口
                new CompanyService().enterpriseGrab(companyId, company, infoLanguage,reportType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //调用香港查册网
                HttpCrawler.getIcrisUrl(company, companyId, orderInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //爬取商务部业务系统网站
                //HttpCrawler.getMofcomUrl(company, companyId, orderInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //爬虫完毕更新状态
            CreditOrderInfo model2 = new CreditOrderInfo();
            model2.set("id", orderInfo.getInt("id")).set("status", 694);
            model2.update();
        }
    }
