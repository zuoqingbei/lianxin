package com.hailian.modules.credit.usercenter.controller;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.front.template.TemplateSysUserService;
import com.jfinal.plugin.activerecord.Page;
/**
* @time   2018年9月14日 上午11:00:00
* @author lzg
* todo 订单再分配
*/
@Api(tag = "订单流程", description = "订单流程")
@ControllerBind(controllerKey = "/credit/front/orderProcess")
public class OrderProcessController extends BaseProjectController{
	private static final String PATH = "/pages/credit/usercenter/order_manage/";
	//每种搜索类型需要对应的关键词字段名
	public static final Map<String,List<Object>> TYPE_KEY_COLUMN = new HashMap<>();
	//每种搜索类型需要对应的前端属性名
	public static final Map<String,List<Object>> WEB_PARAM_NAMES = new HashMap<>();
	/**
	 * 订单分配的搜索类型
	 */
	public static String orderAllocation = "-1";
	public static LinkedList<Object> orderAllocationColumns = new LinkedList<>();
	public static LinkedList<Object> orderAllocationParamNames = new LinkedList<>();
	/**
	 * 报告管理中的订单核实
	 */
	public static String orderVerifyOfReport = "-2";
	public static LinkedList<Object> orderVerifyOfReportColumns = new LinkedList<>(); 
	public static LinkedList<Object> orderVerifyOfReportParamNames = new LinkedList<>(); 
	/**
	 * 订单管理中的订单核实
	 */
	public static String orderVerifyOfOrder = "-3";
	public static LinkedList<Object> orderVerifyOfOrderColumns = new LinkedList<>();
	public static LinkedList<Object> orderVerifyOfOrderParamNames = new LinkedList<>();
	static{
		orderAllocationColumns.add("u1.realname");
	}
	static{
		orderAllocationParamNames.add("report_user");
	}
	static{
		TYPE_KEY_COLUMN.put(orderAllocation,orderAllocationColumns);
		TYPE_KEY_COLUMN.put(orderVerifyOfReport,orderVerifyOfReportColumns);
		TYPE_KEY_COLUMN.put(orderVerifyOfOrder,orderVerifyOfOrderColumns);
	}
	static{
		WEB_PARAM_NAMES.put(orderAllocation, orderAllocationParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfReport, orderVerifyOfReportParamNames);
		WEB_PARAM_NAMES.put(orderVerifyOfOrder, orderVerifyOfOrderParamNames);
	}
	
	//展示列表功能公共雏形
	private Page<CreditOrderInfo> PublicListMod(String searchType){
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		//从表单获取排序语句
		String sortName = getPara("sortName");
		String sortOrder = getPara("sortOrder");
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
			if(StringUtil.isEmpty((String) model.get((String) columnName))){
				keywords.add("");
			}else{
				keywords.add((String) model.get((String) columnName));
			}
		}
		//分页查询
		Page<CreditOrderInfo> pager = CreditOrderInfo.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType, this);
		//存入回显数据
		for (CreditOrderInfo page : pager.getList()) {
			for (int i = 0; i <  WEB_PARAM_NAMES.get(searchType).size(); i++) {
				page.put((String)WEB_PARAM_NAMES.get(searchType).get(i)+"Key",keywords.get(i));
			}
			page.put("pageNumber",pageNumber);
			page.put("pageSize",pageSize);
			page.put("sortName",sortName);
			page.put("sortOrder",sortOrder);
		}
		return pager;
	}
	
	//修改或者删除功能公共雏形
	private void PublicUpdateMod(List<String> paramList){
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		for (String param : paramList) {
			model.set(param,model.get(param));
		}
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		model.update();
		renderMessage("修改成功");
	}
	
	/**
	 * @todo   展示订单分配页
	 * @time   2018年9月14日 下午 14:38
	 * @author lzg
	 * @return_type   void
	 */
	public void showReallocation(){
		render(PATH+"order_allocation.html");
	}
	
	/**
	 *获取订单数据
	 */
	public void reallocationJson() {
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(orderAllocation);
		List<CreditOrderInfo> rows = pager.getList();
		TemplateSysUserService templete = new TemplateSysUserService();
		for (CreditOrderInfo creditOrderInfo : rows) {
			String seleteStr= templete.getSysUser(2, creditOrderInfo.get("report_user"));
			creditOrderInfo.put("seleteStr",seleteStr);
		}
		int totalRow = pager.getTotalRow();
		ResultType resultType = new ResultType(totalRow,rows);
		renderJson(resultType);
	}
	
	/**
	 * @todo   重新分配
	 * @time   2018年9月14日 下午4:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void reallocationSave() {
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		//status='292'值状态为客户确认状态 ,其维护在字典表中
		model.set("status", 292);
		model.update();
		reallocationJson();
	}
	
	
	/**
	 * @todo   订单管理目录下的订单核实
	 * @time   2018年9月14日 下午15:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void VerifyOfOrderMannege() {
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(orderVerifyOfOrder);
		setAttr("page", pager);
		keepPara();
		render(PATH+"order_allocation.html");
	}
	
	
}
