package com.hailian.modules.credit.orderpoimanager.service;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.agentmanager.service.AgentService;
import com.hailian.modules.credit.order.model.TbOrder;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月17日 下午6:55:40
* @todo  订单批量导入处理业务
*/
public class OrderPoiService {
	public static OrderPoiService service = new OrderPoiService();
	
	/**
	 * 
	 * @time   2018年9月18日 上午11:14:21
	 * @author dyc
	 * @todo   分页查询订单
	 * @return_type   Page<TbOrder>
	 */
	public Page<CreditOrderInfo> getOrdersService(Paginator pageinator, CreditOrderInfo model, String orderby,
			 BaseProjectController c) {

		return CreditOrderInfo.dao.getOrders(pageinator, model, orderby, c);

	}
	/**
	 * 
	 * @time   2018年9月18日 下午3:42:57
	 * @author dyc
	 * @todo   根据id查看订单信息
	 * @return_type   ReportPrice
	 */
	public CreditOrderInfo getId(int id, BaseProjectController c) {
		CreditOrderInfo creditOrderInfo = CreditOrderInfo.dao.getId(id, c);
		return creditOrderInfo;

	}
}
