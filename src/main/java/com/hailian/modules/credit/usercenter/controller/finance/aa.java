package com.hailian.modules.credit.usercenter.controller.finance;

import com.feizhou.swagger.utils.StringUtil;

public class aa {
	
	public static void main(String[] args) {
		String a = "";
		String b= "a";
		String c = null;
		System.out.println(isEmpty(b,a));
	}
	
	public static boolean isEmpty(String... str) {
		for (int i = 0; i < str.length; i++) {
			if(StringUtil.isEmpty(str[i])) {
				return false;
			}
		}
		return true;
	}
}
