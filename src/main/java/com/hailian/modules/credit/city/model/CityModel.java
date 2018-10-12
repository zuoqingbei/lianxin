package com.hailian.modules.credit.city.model;

import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_city", key = "cid")
public class CityModel extends BaseProjectModel<CityModel>{
	private static final long serialVersionUID = 1L;
	public static final CityModel dao = new CityModel();//名字都叫dao，统一命名
	/**
	 * @todo   获取省份
	 * @time   2018年9月4日 下午7:17:02
	 * @author zuoqb
	 * @params
	 */
	public List<CityModel> getCity(String cid,String pid) {
		List<Object> params=new ArrayList<Object>();
		String sql="select t.* from credit_city t where 1=1  ";
		if(StringUtils.isNotBlank(cid)){
			sql+=" and and t.cid=? ";
			params.add(cid);
		}
		if(StringUtils.isNotBlank(pid)){
			sql+=" and and t.pid=? ";
			params.add(pid);
		}
		sql+=" order by t.cid ";
		return dao.find(sql, params.toArray());
	}	
}
