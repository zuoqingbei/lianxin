package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hailian.jfinal.base.BaseService;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;

/**
 * 
 * @todo   字典模板方法接口
 * @time   2018年8月27日 下午5:37:28
 * @author zuoqb
 */
public class TemplateDictService extends BaseService {

	

	/**
	 * 
	 * @todo  根据dict_type获取字典
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public List<SysDictDetail> getSysDictDetailByType(String type) {
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		SysDictDetail allDict=new SysDictDetail();
		allDict.set("detail_id", "");
		allDict.set("dict_type",type);
		allDict.set("detail_name","全部");
		allDict.set("detail_name_en", "ALL");
		allDict.set("detail_code", "");
		listDetail.add(allDict);
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		return listDetail;
	}
	
	/**
	 * 
	 * @todo  根据dict_type获取字典
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public String getSysDictDetailSelect(String type,String selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		SysDictDetail allDict=new SysDictDetail();
		allDict.set("detail_id", "");
		allDict.set("dict_type",type);
		allDict.set("detail_name","全部");
		allDict.set("detail_name_en", "ALL");
		allDict.set("detail_code", "");
		listDetail.add(allDict);
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			if(StringUtils.isNotBlank(selectedId)&&selectedId.equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
			}else{
				sb.append("<option m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
			}
			
		}
		return sb.toString();
	}

	
}