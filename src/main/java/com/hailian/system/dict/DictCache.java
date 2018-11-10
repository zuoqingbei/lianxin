package com.hailian.system.dict;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hailian.util.StrUtils;
import com.hailian.util.cache.Cache;
import com.hailian.util.cache.CacheManager;
import com.jfinal.log.Log;

/**
 * 数据字典缓存
 * 
 * 2014年1月21日 下午11:23:23 flyfox 369191470@qq.com
 */
public class DictCache {

	private final static Log log = Log.getLog(DictCache.class);
	private final static String cacheName = "DictCache";
	private static Cache cache;

	/**
	 * 初始化Map
	 * 
	 * @author flyfox 2013-11-15
	 */
	public static void init() {
		if (cache == null) {
			cache = CacheManager.get(cacheName);
		}
		log.info("####数据字典Cache初始化......");
		DictCache.initDict();
	}

	/**
	 * 初始化数据字典明细表
	 * 
	 * @author flyfox 2013-11-19
	 */
	public static void initDict() {
		Map<Integer, SysDictDetail> dictMap = new LinkedHashMap<Integer, SysDictDetail>();
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		// detailSort
		listDetail = SysDictDetail.dao.findByWhere(" order by detail_sort,detail_id");
		
		
		
		
		//new SysDictDetail(detail.getInt("detail_id"));
		
		//listDetail.remove();
		
		for (SysDictDetail detail : listDetail) {
				
		//	System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC"+listDetail.remove(detail.getInt("detail_id")));
			
			//System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC2C"+listDetail.size());
			//System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC"+detail);
			
			
			dictMap.put(detail.getInt("detail_id"), detail);
			
			
		}
		cache.add("map", dictMap);
	}

	public static Map<Integer, SysDictDetail> getCacheMap() {
		return cache.get("map");
	}

	//////////////////////////jstl 标签/////////////////////////////

	/**
	 * 获取下拉菜单
	 * 
	 * 2014年1月22日 下午10:08:38 flyfox 369191470@qq.com
	 * 
	 * @param type
	 * @param selected_value
	 * @return
	 */
	public static String getSelect(String type, Integer selected_value) {
		Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
		if (map == null || map.size() <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Integer key : map.keySet()) {
			SysDictDetail dict = map.get(key);
			if (dict.getStr("dict_type").equals(type)) {
				sb.append("<option value=\"");
				sb.append(dict.getInt("detail_id"));
				sb.append("\" ");
				sb.append(key.equals(selected_value) ? "selected" : "");
				sb.append(">");
				sb.append(dict.getStr("detail_name"));
				sb.append("</option>");
			}
		}
		return sb.toString();
	}
	/**
	 * 
	 * @todo  根据dict_type获取字典
	 * @time   2018年8月27日 下午5:06:56
	 * @author zuoqb
	 * @params
	 */
	public static List<SysDictDetail> getSysDictDetailByType(String type){
		List<SysDictDetail> listDetail = new ArrayList<SysDictDetail>();
		Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
		if (map == null || map.size() <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Integer key : map.keySet()) {
			SysDictDetail dict = map.get(key);
			if (dict.getStr("dict_type").equals(type)) {
				listDetail.add(dict);
			}
		}
		return listDetail;
	}

	/**
	 * 获取Value值
	 * 
	 * 2014年1月22日 下午10:10:26 flyfox 369191470@qq.com
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(Integer key) {
		if (key == null) {
			return null;
		}
		SysDictDetail dict = getCacheMap().get(key);
		return dict == null ? null : dict.getStr("detail_name");
	}

	/**
	 * 获取Code值
	 * 
	 * 2014年1月22日 下午10:10:26 flyfox 369191470@qq.com
	 * 
	 * @param key
	 * @return
	 */
	public static String getCode(Integer key) {
		if (key == null) {
			return null;
		}
		SysDictDetail dict = getCacheMap().get(key);
		return dict == null ? null : dict.getStr("detail_code");
	}

	/**
	 * 获取下拉菜单 code:value形式
	 * 
	 * 2015年11月3日 上午9:46:55
	 * flyfox 369191470@qq.com
	 * @param type
	 * @param selected_code
	 * @return
	 */
	public static String getSelectByCode(String type, String selected_code) {
		Map<Integer, SysDictDetail> map = DictCache.getCacheMap();
		if (map == null || map.size() <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Integer key : map.keySet()) {
			SysDictDetail dict = map.get(key);
			if (dict.getStr("dict_type").equals(type)) {
				String code = dict.getStr("detail_code");
				sb.append("<option value=\"");
				sb.append(dict.getStr("detail_code"));
				sb.append("\" ");
				sb.append(StrUtils.isNotEmpty(code) && code.equals(selected_code) ? "selected" : "");
				sb.append(">");
				sb.append(dict.getStr("detail_name"));
				sb.append("</option>");
			}
		}
		return sb.toString();
	}

	/**
	 * 通过type和code获取Value值
	 * 
	 * 2015年11月3日 上午9:47:20
	 * flyfox 369191470@qq.com
	 * @param type
	 * @param code
	 * @return
	 */
	public static String getValueByCode(String type, String code,String disPalyCol) {
		if (type == null || code == null) {
			return null;
		}
		Map<Integer, SysDictDetail> map = getCacheMap();
		for (SysDictDetail dict : map.values()) {
			if (code.equals(dict.getStr("detail_id")) && type.equals(dict.getStr("dict_type"))) {
				return dict.getStr(disPalyCol);
			}
		}
		return null;
	}

}
