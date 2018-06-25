
package com.ulab.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
@TableBind(tableName = "t_b_order_num",pkName="id")
public class OrderNumModel extends Model<OrderNumModel> {
	private static final long serialVersionUID = 4762813779629969917L;
	public static final OrderNumModel dao = new OrderNumModel();
	
	public List<OrderNumModel> findOrderNums(){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from t_b_order_num where del_flag=0  order by order_no  ");
		return dao.find(sb.toString());
	}
	
}
