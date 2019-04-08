package com.hailian.modules.credit.order.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ModelBind;
import com.hailian.modules.credit.utils.DataAuthorUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * @todo   订单表
 * @time   2018年8月20日 下午5:01:17
 * @author zuoqb
 */
@ModelBind(table = "tb_order")
public class TbOrder extends BaseProjectModel<TbOrder> {
	private static final long serialVersionUID = 1L;
	public static final TbOrder dao = new TbOrder();//名字都叫dao，统一命名

	/**
	 * @todo   根据Id查询订单
	 * @time   2018年8月20日 下午5:01:28
	 * @author zuoqb
	 * @return_type   TbOrder
	 */

	public TbOrder getOrder(String orderId, BaseProjectController c) {
		return TbOrder.dao.findById(orderId);
	}

	/**
	 * @todo   分页查询订单
	 * @time   2018年8月20日 下午7:29:23
	 * @author zuoqb
	 * @params pageNumber：当前页码 pagerSize：每页条数 customName：客户名称  BaseProjectController-当前controller  必须传 以后做数据权限使用
	 */

	public Page<TbOrder> pagerOrder(int pageNumber, int pagerSize, String customName, BaseProjectController c) {
		String authorSql = DataAuthorUtils.getAuthorByUser(c);
		StringBuffer selectSql = new StringBuffer(" select * ");
		StringBuffer fromSql = new StringBuffer(" from tb_order where 1=1 ");
		//参数集合
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(customName)) {
			fromSql.append(" and name =? ");
			params.add(customName);
		}
		Page<TbOrder> orderPage = TbOrder.dao.paginate(new Paginator(pageNumber, pagerSize), selectSql.toString(),
				fromSql.toString(), params.toArray());
		return orderPage;
	}

}