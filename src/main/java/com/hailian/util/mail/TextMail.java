package com.hailian.util.mail;

import com.jfinal.template.ext.directive.Random;

/**
* @author dyc:
* @time 2018年9月21日 上午11:37:51
* @todo
*/
public class TextMail {
	public static void main(String[] args) {
		try {
			MainMail.send_mail("17853452326@163.com", String.valueOf((Math.random() * 9 + 1)* 100000 ));
			System.out.println("邮件发送成功!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
