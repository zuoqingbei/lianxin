package com.hailian.modules.credit.province.model;

import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

@ModelBind(table = "credit_province", key = "pid")
public class ProvinceModel extends BaseProjectModel<ProvinceModel>{
	private static final long serialVersionUID = 1L;
	public static final ProvinceModel dao = new ProvinceModel();//名字都叫dao，统一命名
	/**
	 * @todo   获取省份
	 * @time   2018年9月4日 下午7:17:02
	 * @author zuoqb
	 * @params
	 */
	public List<ProvinceModel> getProvince(String pid) {
		List<Object> params=new ArrayList<Object>();
		String sql="select t.* from credit_province t where 1=1 ";
		if(StringUtils.isNotBlank(pid)){
			sql+=" and and t.pid=? ";
			params.add(pid);
		}
		sql+=" order by t.pid ";
		return dao.find(sql, params.toArray());
	}	
}
