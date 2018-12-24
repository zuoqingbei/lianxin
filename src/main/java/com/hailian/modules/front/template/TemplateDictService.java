package com.hailian.modules.front.template;

import java.util.ArrayList;
import java.util.List;

import com.hailian.jfinal.base.BaseService;
import com.hailian.modules.admin.ordermanager.model.CreditCountry;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.hailian.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;

/**
 * 
 * @todo   字典模板方法接口
 * @time   2018年8月27日 下午5:37:28
 * @author zuoqb
 */
public class TemplateDictService extends BaseService {
	public static TemplateDictService templatedictservice=new TemplateDictService();
	

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
	 * @time   2018年9月25日 下午6:23:14
	 * @author yangdong
	 * @todo   TODO 返回List
	 * @param  @param type
	 * @param  @return
	 * @return_type   List<SysDictDetail>
	 */
	public String getListSysDictDetail4(String type,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_name")+"'>"+detail.get("detail_name")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("detail_name_en"))){
					sb.append("<option  selected='selected'  m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value=''>"+detail.get("detail_name")+"</option>");
				}else{
					sb.append("<option m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_name")+"'>"+detail.get("detail_name")+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	public List<SysDictDetail> getListSysDictDetail(String type) {
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		return listDetail;
	}
	/**
	 * 根据dict_type获取字典,用于添加,无默认选项,无全部选项
	 * @time   2018年9月13日 下午12:05:29
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param type
	 * @param  @return
	 * @return_type   String
	 */
	public String getSysDictDetailString(String type) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			if("普通".equals(detail.get("detail_name"))) {
				sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");

			}else {
				sb.append("<option m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
			}
		}
		return sb.toString();
	}
	/**
	 * 根据dict_type获取字典,用于添加,有默认选项,展示id
	 * @time   2018年9月13日 下午12:08:26
	 * @author yangdong
	 * @todo   TODO
	 * @param  @param type
	 * @param  @param selectedId
	 * @param  @return
	 * @return_type   String
	 */
	public String getSysDictDetailString2(String type,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("detail_name_en"))){
					sb.append("<option  selected='selected'  m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
				}else{
					sb.append("<option m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	
	public String getSysDictDetailString3(String type,Object selectedId) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_id")+"</option>");
			}else{
				if("ALL".equals(detail.getStr("detail_name_en"))){
					sb.append("<option  selected='selected'  m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"</option>");
				}else{
					sb.append("<option m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_id")+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	
	public String getSysDictDetailString3(String type,Object selectedId,String disPalyCol) {
		StringBuffer sb=new StringBuffer();
		String flagStr = "detail_id";
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		if (type!=null&&"translate_quality".equals(type)) {
			type="translate_quality";//翻译质检下拉选项
			selectedId="642";
		}else if(type!=null&&"analyze_quality".equals(type)){
			type="analyze_quality";//分析质检下拉选项
			selectedId="652";
		}else if(type!=null&&"entering_quality".equals(type)){
			type="entering_quality";//填报质检下拉选项
			selectedId="646";
		}else if(type!=null&&"small_module_type".equals(type)){
			if(StrUtils.isEmpty((String)(selectedId))){
				selectedId = "1";
			}
			//type="entering_quality";//小模板类型
			flagStr = "detail_code";
			selectedId=  Db.query(" select detail_code from sys_dict_detail where del_flag=0 and dict_type='small_module_type' and detail_code="+selectedId).get(0);
		}else if(type!=null&&"word_table_type".equals(type)){
			if(StrUtils.isEmpty((String)(selectedId))){
				selectedId = "s";
			}
			flagStr = "detail_code";
			//type="entering_quality"; 
			selectedId=  Db.query(" select detail_code from sys_dict_detail where del_flag=0 and dict_type='word_table_type' and detail_code='"+selectedId+"'").get(0);
		}else if(type!=null&&"boolean".equals(type)){
			if(StrUtils.isEmpty((String)(selectedId))){
				selectedId = "0";
			}
			flagStr = "detail_code";
			//type="entering_quality"; 
			selectedId=  Db.query(" select detail_code from sys_dict_detail where del_flag=0 and dict_type='boolean' and detail_code='"+selectedId+"'").get(0);
		}
		
		//listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		sb.append("<option>请选择  </option>");
		for(SysDictDetail detail:listDetail){
			if(detail.get(disPalyCol)==null||"".equals(detail.get(disPalyCol))) {
				continue;
			}
			  
			 if(selectedId!=null&& selectedId.toString().equals(detail.get(flagStr))){
				sb.append("<option  m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get(flagStr)+"' selected>"+detail.get(disPalyCol)+"</option>");
			}else{
				
			    sb.append("<option m-detail-name='"+detail.get("detail_name")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get(flagStr)+"'>"+detail.get(disPalyCol)+"</option>");
			}
			
		}
		return sb.toString();
	}

	/**
	 * 根据dict_type获取字典,用于添加,有默认选项,无全部选项 复选框
	* @author doushuihai  
	* @date 2018年10月15日下午3:13:34  
	* @TODO
	 */
	public String getSysDictDetailCheckboxString(String type,List<Object> selectedId,String rowsize) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		System.out.println(selectedId);
		int rowSize=9;
		if(rowsize != null && !rowsize.equals("")){
			rowSize = Integer.parseInt(rowsize);
		}
		
		int temp=0;
		for(SysDictDetail detail:listDetail){
			temp++;
			boolean isSelected=false;
			for(Object selected:selectedId){
				if(selected!=null&&selected.toString().equals(detail.get("detail_id").toString())){
					isSelected=true;
					break;
				}
			}
			int i = temp%rowSize;
			if(isSelected){
				sb.append("<input type='checkbox' checked='checked' name='agent_category' m-detail-id='"+detail.get("detail_id")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"&nbsp;&nbsp;&nbsp;");
				if(i==0){
					sb.append("</br>");
				}
			}else{
				sb.append("<input type='checkbox'  name='agent_category' m-detail-id='"+detail.get("detail_id")+"' value='"+detail.get("detail_id")+"'>"+detail.get("detail_name")+"&nbsp;&nbsp;&nbsp;");
				if(i==0){
					sb.append("</br>");
				}
			}
		}
		return sb.toString();
	}
	/**
	 * @param type 根据dict_type获取字典
	 * @param selectedId
	 * @param showColumnName 所展示字段名
	 * @return
	 */
	public String getSysDictDetailString(String type,Object selectedId,String showColumnName) {
		StringBuffer sb=new StringBuffer();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		listDetail.add(getDefaultDictDetail(type));
		listDetail.addAll(DictCache.getSysDictDetailByType(type));
		for(SysDictDetail detail:listDetail){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("detail_id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get(showColumnName)+"</option>");
			}else{
				if("ALL".equals(detail.getStr("detail_name_en"))){
					sb.append("<option  selected='selected'  m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get(showColumnName)+"</option>");
				}else{
					sb.append("<option m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_id")+"'>"+detail.get(showColumnName)+"</option>");
				}
			}
			
		}
		return sb.toString();
	}
	
	
	/**
	 lzg 2018/12/05
	 * @return
	 */
	public String getCounty(Object selectedId,String showColumnName ) {
		List<CreditCountry> listDetail = new ArrayList<>();
		StringBuffer sb=new StringBuffer();
	 listDetail = CreditCountry.dao.find("select *  from credit_country where del_flag=0");
	
		for(CreditCountry detail:listDetail){
			 if(selectedId!=null&&selectedId.toString().equals(detail.get("id").toString())){
				sb.append("<option selected='selected' m-detail-id='"+detail.get("id")+"' value='"+detail.get("id")+"'>"+detail.get(showColumnName)+"</option>");
			}else{
				sb.append("<option  m-detail-id='"+detail.get("id")+"' value='"+detail.get("id")+"'>"+detail.get(showColumnName)+"</option>");
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
		if("continent".equals(type)) {
			allDict.set("detail_id", "");
			allDict.set("dict_type",type);
			allDict.set("detail_name","请选择地区");
			allDict.set("detail_name_en", "ALL");
			allDict.set("detail_code", "");
		}
		else if("ordertype".equals(type)) {
			allDict.set("detail_id", "");
			allDict.set("dict_type",type);
			allDict.set("detail_name","请选择订单类型");
			allDict.set("detail_name_en", "ALL");
			allDict.set("detail_code", "");
			}
		else if("language".equals(type)) {
			allDict.set("detail_id", "");
			allDict.set("dict_type",type);
			allDict.set("detail_name","请选择语言");
			allDict.set("detail_name_en", "ALL");
			allDict.set("detail_code", "");
			}
		else if("orderspeed".equals(type)) {
			allDict.set("detail_id", "");
			allDict.set("dict_type",type);
			allDict.set("detail_name","请选择订单速度");
			allDict.set("detail_name_en", "ALL");
			allDict.set("detail_code", "");
			}
		else{
			allDict.set("detail_id", "");
			allDict.set("dict_type",type);
			allDict.set("detail_name","请选择");
			allDict.set("detail_name_en", "ALL");
			allDict.set("detail_code", "");
		}
			
		return allDict;
	}

    /**
     * 下拉框value显示detail_code
     * @param type
     * @param detailName
     * @return
     */
    public String getSysDictDetailCode(String type,String detailName) {
        StringBuffer sb=new StringBuffer();
        List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
        listDetail.addAll(DictCache.getSysDictDetailByType(type));
        for(SysDictDetail detail:listDetail){
            if(detailName!=null&&detailName.toString().equals(detail.get("detail_name").toString())){
                sb.append("<option selected='selected' m-detail-id='"+detail.get("detail_id")+"' m-detail-content='"+detail.get("detail_content")+"' m-detail-code='"+detail.get("detail_code")+"'  m-english='"+detail.get("detail_name_en")+"' value='"+detail.get("detail_name")+"'>"+detail.get("detail_remark")+"</option>");
            }else {
                sb.append("<option m-detail-id='" + detail.get("detail_id") + "' m-detail-content='" + detail.get("detail_content") + "' m-detail-code='" + detail.get("detail_code") + "'  m-english='" + detail.get("detail_name_en") + "' value='" + detail.get("detail_name") + "'>" + detail.get("detail_remark") + "</option>");
            }
        }
        return sb.toString();
    }
	

	
}