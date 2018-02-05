package com.ulab.util;

import java.util.Random;
import java.util.UUID;

public class UUIDTool {
	public UUIDTool() {
	}

	/**  
	 * 自动生成32位的UUid，对应数据库的主键id进行插入用。  
	 * @return  
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String getOrderIdByUUId() {
		int first = new Random(10).nextInt(8) + 1;
		System.out.println(first);
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {//有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return first + String.format("%015d", hashCodeV);
	}

	public static void main(String[] args) {
		String orderingID = getOrderIdByUUId();
		System.out.println(orderingID);
	}
}
