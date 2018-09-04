package com.hailian.system.dict;

import java.util.List;

import com.hailian.component.base.BaseProjectModel;
import com.hailian.jfinal.component.annotation.ModelBind;

/**
 * 
 * @className SysDictDetail.java
 * @time   2018年8月31日 上午11:54:49
 * @author yangdong
 * @todo   TODO
 */
@ModelBind(table = "sys_dict_detail", key = "detail_id")
public class SysDictDetail extends BaseProjectModel<SysDictDetail> {

	private static final long serialVersionUID = 1L;
	public static final SysDictDetail dao = new SysDictDetail();

	/**
	 * 
	 * @time   2018年8月31日 上午11:54:43
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param dictType
	 * @param  @return
	 * @return_type   List<SysDictDetail>
	 */
	public List<SysDictDetail> getDictByType(String dictType) {
		return dao.find(
				"select t.* from sys_dict_detail t where t.dict_type=? and del_flag=0 order by detail_sort desc",
				dictType);

	}

}
