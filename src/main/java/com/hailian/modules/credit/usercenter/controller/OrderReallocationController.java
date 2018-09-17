package com.hailian.modules.credit.usercenter.controller;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.utils.StringUtil;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.jfinal.plugin.activerecord.Page;
/**
* @time   2018年9月14日 上午11:00:00
* @author lzg
* todo 订单再分配
*/
@Api(tag = "订单流程", description = "订单再分配")
@ControllerBind(controllerKey = "/credit/front/orderReallocation")
public class OrderReallocationController extends BaseProjectController{
	private static final String PATH = "/pages/credit/usercenter/";
	//存储关键词字段名
	private static List<Object> IsExistColumnList= new ArrayList<>();
	//检索重复判断字段
	public static List<Object> columnNames = new ArrayList<>();
	//搜索类型
	public static String searchById = "-1";
	public static String fuzzySearch = "-2";
	public static String refineSearch = "-3";
	static{
		columnNames.add("report_id");
		columnNames.add("report_speed");
		columnNames.add("order_type");
		columnNames.add("time");
		columnNames.add("usabled");
		IsExistColumnList.add("report_id");
		IsExistColumnList.add("report_speed");
		IsExistColumnList.add("country_type");
		IsExistColumnList.add("order_type");
	}
		
	/**
	 * @todo   展示订单分配页
	 * @time   2018年9月14日 下午 14:38
	 * @author lzg
	 * @return_type   void
	 */
	
	public void list() {
		int pageNumber = getParaToInt("pageNo", 1);
		int pageSize = getParaToInt("pageSize", 10);
		//从表单获取排序语句
		String orderBy = getBaseForm().getOrderBy();
		//模糊搜索或者精确搜索
		String searchType = getPara("searchType");
		if(!refineSearch.equals(searchType)){
			searchType = fuzzySearch;
		}
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
		setAttr("page", pager);
		keepPara();
		render(PATH+"order_allocation.html");
	}
	
	/**
	 * @todo   查看单个订单信息
	 * @time   2018年9月14日 下午1:30:00
	 * @author lzg
	 * @return_type   void
	 */
	public void view() {
		List<Object> keywords = new ArrayList<>();
		keywords.add(getPara("id"));
		CreditOrderInfo model = CreditOrderInfo.dao.pagerOrder(1, 1, keywords,"",searchById,this).getList().get(0);
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
	public void goWork() {
		CreditOrderInfo model = getModel(CreditOrderInfo.class);
		Integer userid = getSessionUser().getUserid();
		String now = getNow();
		model.set("update_by",userid);
		model.set("update_date", now);
		model.update();
		renderMessage("修改成功");
	}
}
