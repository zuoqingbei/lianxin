package com.ulab.util;

import org.apache.commons.lang3.StringUtils;

import com.ulab.core.BaseController;

public class SqlUtil {
	/**
	 * 
	 * @time 2017年4月22日 上午8:17:19
	 * @author zuoqb
	 * @todo 根据传入的参数拼接实验室筛选条件
	 * @param @param c
	 * @param @return
	 * @return_type String
	 */
	public static String commonWhereSql(BaseController c,String tableName) {
		String sWhere = " ";
		if(tableName!=null&&StringUtils.isNotEmpty(tableName)){
			tableName=tableName+".";
		}else{
			tableName=" ";
		}
		if (StringUtils.isNotEmpty(c.getPara("productCode"))) {
			sWhere += "  and "+tableName+"product_code in" + commonSqlGenerator(c.getPara("productCode")) + " ";
		}
		if (StringUtils.isNotEmpty(c.getPara("labType"))) {
			sWhere += " and "+tableName+"lab_type_code in" + commonSqlGenerator(c.getPara("labType")) + " ";
		}
		return sWhere;
	}

	/**
	 * 
	 * @time 2017年4月22日 上午8:21:51
	 * @author zuoqb
	 * @todo 拼接的sql，如('a','b','c')或('')
	 * @param @return
	 * @return_type String
	 */
	public static String commonSqlGenerator(String filedStr) {
		// 拼接sql--('a','b','c')
		StringBuilder subSql = new StringBuilder();
		String[] arr=new String[]{};
		arr=filedStr.split(",");
		subSql.append("('");
		for (int i = 0; i < arr.length; i++) {
			subSql.append(arr[i]);
			if (i != arr.length - 1) {
				subSql.append("','");
			}
		}
		subSql.append("')");

		return subSql.toString();
	}
}
