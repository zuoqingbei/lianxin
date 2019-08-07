package com.hailian.util;

/* 
 * 题目：
 * 编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。 但是要保证汉字不被截半个，如“我ABC”4，应该截为“我AB”，输入“我ABC汉DEF”，6，应该输出为“我ABC”而不是“我ABC+汉的半个”。 
 * 
 * 解释：
 * 此处的编码方式应该是操作系统默认的GB编码，即汉字占2个字节且第一个字节的最高位是1，
 * 如果理解为有符号数的话，就是负数；而英文占1个字节，符合ASC2码。
 */
public class SplitString {
	public static String[] str_split(String str, int length) {
		int len = str.length();
		String[] arr = new String[(len + length - 1) / length];
		for (int i = 0; i < len; i += length) {
			int n = len - i;
			if (n > length)
				n = length;
			arr[i / length] = str.substring(i, i + n);
		}
		return arr;
	}

	public static String str_split(String str, int length,
			CharSequence delimiter) {
		return String.join(delimiter, str_split(str, length));
	}

	public static void main(String[] args) throws Exception {
		String s="股東(發起人)名稱:江万花,證件(照)類型:中華人民共和國居民身份證,證件(照)號碼:****,認繳出資額:500萬,幣種:,認繳出資額折萬美元:0,認繳出資方式:貨幣,認繳出資時間:2036-10-13;【退出】";
		System.out.println(str_split(s, 9, "\r"));
	}

}
