package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.credit.common.model.ReportTypeModel;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;

/**
 * @todo   报告类型插件
 * @time   2018年9月4日 下午7:09:06
 * @author zuoqb
 */
public class TemplateReportTypeService extends BaseService {

	

	/**
	 * 
	 * @todo  报告类型
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public List<ReportTypeModel> getReportType() {
		List<ReportTypeModel> listDetail = new ArrayList<ReportTypeModel>();
		listDetail.add(getDefaultReportTypeDetail());
		listDetail.addAll(ReportTypeModel.dao.getReportType());
		return listDetail;
	}
	
	/**
	 * 
	 * @todo  报告类型
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public String getReportTypeString(Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<ReportTypeModel> listDetail = getReportType();
		for(ReportTypeModel detail:listDetail){
			if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("id")+"' m-english='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("name_en"))){
					sb.append("<option selected='selected' m-detail-id='"+detail.get("id")+"' m-english='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
				}else{
					sb.append("<option m-detail-id='"+detail.get("id")+"' m-english='"+detail.get("name_en")+"' value='"+detail.get("id")+"'>"+detail.get("name")+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	/**
	 * 
	 * @todo   获取全部默认报告类型
	 * @time   2018年9月3日 下午7:12:53
	 * @author zuoqb
	 * @params
	 */
	protected ReportTypeModel getDefaultReportTypeDetail() {
		ReportTypeModel allDict=new ReportTypeModel();
		allDict.set("id", "");
		allDict.set("name","请选择报告类型");
		allDict.set("name_en", "ALL");
		return allDict;
	}

	
}