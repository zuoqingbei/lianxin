package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

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
		listDetail.add(getDefaultDictDetail(type));
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
	public String getSysDictDetailString(String type,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			if(selectedId!=null&&selectedId.toString().equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("detail_name_en"))){
					sb.append("<option  selected='selected'  m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
				}else{
					sb.append("<option m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	/**
	 * 
	 * @todo   获取全部默认字典
	 * @time   2018年9月3日 下午7:12:53
	 * @author zuoqb
	 * @params
	 */
	protected SysDictDetail getDefaultDictDetail(String type) {
		SysDictDetail allDict=new SysDictDetail();
		allDict.set("detail_id", "");
		allDict.set("dict_type",type);
		allDict.set("detail_name","全部");
		allDict.set("detail_name_en", "ALL");
		allDict.set("detail_code", "");
		return allDict;
	}

	
}