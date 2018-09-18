package com.hailian.modules.credit.usercenter.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.jfinal.plugin.activerecord.Page;
/**
* @time   2018年9月14日 上午11:00:00
* @author lzg
* todo 订单再分配
*/
@Api(tag = "订单流程", description = "订单流程")
@ControllerBind(controllerKey = "/credit/front/orderProcess")
public class OrderProcess extends BaseProjectController{
	private static final String PATH = "/pages/credit/usercenter/";
	//存储关键词字段名
	private static List<Object> IsExistColumnList= new ArrayList<>();
	//检索重复判断字段
	public static List<Object> columnNames = new ArrayList<>();
	//每种搜索类型需要对应的save参数
	public static final Map<String,List<String>> TYPE_PARAMS = new HashMap<>();
	//搜索类型
	/**
	 * 订单分配的搜索类型
	 */
	public static String orderAllocation = "-1";
	public static List<String> orderAllocationParams =  new ArrayList<>();
	static{
		orderAllocationParams.add("");
	}
	/**
	 * 报告管理中的订单核实
	 */
	public static String orderVerifyOfReport = "-2";
	public static List<String> orderVerifyOfReportParams =  new ArrayList<>();
	static{
		orderVerifyOfReportParams.add("");
	}
	/**
	 * 订单管理中的订单核实
	 */
	public static String orderVerifyOfOrder = "-3";
	public static List<String> orderVerifyOfOrderParams =  new ArrayList<>();
	static{
		orderVerifyOfOrderParams.add("");
	}
	static{
		TYPE_PARAMS.put(orderAllocation, orderAllocationParams);
		TYPE_PARAMS.put(orderAllocation, orderVerifyOfReportParams);
		TYPE_PARAMS.put(orderAllocation, orderVerifyOfOrderParams);
		IsExistColumnList.add("report_id");
		IsExistColumnList.add("report_speed");
		IsExistColumnList.add("country_type");
		IsExistColumnList.add("order_type");
	}
	
	//展示列表功能公共雏形
	private Page<CreditOrderInfo> PublicListMod(String searchType){
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//获取前台关键词
		List<Object> keywords = new LinkedList<>();
		for (Object columnName : columnNames) {
			if(StringUtil.isEmpty((String)getPara((String)columnName))){
				keywords.add("");
			}else{
				keywords.add(getPara((String)columnName));
			}
		}
		//分页查询
		Page<CreditOrderInfo> pager = CreditOrderInfo.dao.pagerOrder(pageNumber, pageSize,keywords, orderBy, searchType, this);
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
	public void reallocationList() {
		//分页查询
		Page<CreditOrderInfo> pager = PublicListMod(orderAllocation);
		setAttr("page", pager);
		keepPara();
		render(PATH+"order_allocation.html");
	}
	
	/**
	 * @todo   订单分配查看单个订单信息
	 * @time   2018年9月14日 下午1:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void reallocationView() {
		List<Object> keywords = new ArrayList<>();
		keywords.add(getPara("id"));
		CreditOrderInfo model = CreditOrderInfo.dao.pagerOrder(1, 1, keywords,"",orderAllocation+"id",this).getList().get(0);
		setAttr("model", model);
		keepPara();
		render(PATH+"view.html");
	}
	
	/**
	 * @todo   重新分配
	 * @time   2018年9月14日 下午4:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void goReallocation() {
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		model.update();
		renderMessage("修改成功");
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
