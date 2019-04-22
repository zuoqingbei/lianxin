package com.hailian.modules.credit.utils;

import org.apache.commons.lang.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.SessionUser;

/**
 * @todo   获取当前用户数据权限数据权限
 * @time   2018年8月20日 下午10:08:05
 * @author zuoqb
 */
public class DataAuthorUtils {
	@SuppressWarnings("rawtypes")
	public static String getAuthorByUser(BaseProjectController c) {
		SessionUser user = c.getSessionUser();
		if (user == null || user.getUserid() == -1) {
			return null;
		}
		//获取权限
		return "('1','2')";
	}
	/*	@SuppressWarnings("rawtypes")
		public static String getAuthorByUser(String table,String colmun,BaseProjectController c){
			String dataAuthor="";
			SessionUser user=c.getSessionUser();
			if(user==null||user.getUserid()==-1){
				return null;
			}
			if(StringUtils.isBlank(table)){
				dataAuthor+=table+".";
			}
			if(StringUtils.isBlank(colmun)){
				dataAuthor+="colmun";
			}
			dataAuthor+="('1','2')";
			//获取权限
			return dataAuthor;
		}*/
}
