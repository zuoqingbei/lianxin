package com.hailian.modules.credit.reptilian.model;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		Rule rule = new Rule(
				"http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
		new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },
				"cont_right", Rule.CLASS, Rule.POST);
		List<LinkTypeData> extracts = PickDataUtil.extract(rule);
		System.out.println(extracts);
	}
}
