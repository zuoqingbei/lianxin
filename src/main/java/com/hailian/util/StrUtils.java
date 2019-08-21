/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.hailian.util;

import java.util.regex.Pattern;

import com.feizhou.swagger.utils.StringUtil;

/**
 * 字符串处理
 * 
 * @author flyfox 2012.08.08
 * @email 330627517@qq.com
 */
public class StrUtils {

	/**
	 * 为空
	 * 
	 * 2013年9月8日 下午10:08:44
	 * flyfox 330627517@qq.com
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	/**
	 * 2018/11/20
	 * lzg
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String... str) {
		for (int i = 0; i < str.length; i++) {
			if(StringUtil.isEmpty(str[i])) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 不为空
	 * 
	 * 2013年9月8日 下午10:08:31
	 * flyfox 330627517@qq.com
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 转大写
	 * 
	 * 2013年9月8日 下午10:08:23
	 * flyfox 330627517@qq.com
	 * @param instr
	 * @return
	 */
	public static String toUpperCase(String instr) {
		return instr == null ? instr : instr.toUpperCase();
	}

	/**
	 * 转小写
	 * 
	 * 2013年9月8日 下午10:08:10
	 * flyfox 330627517@qq.com
	 * @param instr
	 * @return
	 */
	public static String toLowerCase(String instr) {
		return instr == null ? instr : instr.toLowerCase();
	}

	/**
	 * 首字母大写 ,其余不变
	 * 
	 * 2013年9月8日 下午10:08:17
	 * flyfox 330627517@qq.com
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toUpperCase());
	}

	/**
	 * 首字母小写 ,其余不变
	 * 
	 * 2013年9月8日 下午10:08:17
	 * flyfox 330627517@qq.com
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirst(String str) {
		if (str == null)
			return null;
		if (str.length() == 0)
			return str;
		String pre = String.valueOf(str.charAt(0));
		return str.replaceFirst(pre, pre.toLowerCase());
	}

	/**
	 * 不会抛NullPointerException 的trim() <br>
	 * 传入null会返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 过滤 ;当instr==null时返回长度为0的""; <br>
	 * 与 nvl(...)系的区别在于只处理null ,不处理长度为0的"";
	 * 
	 * @param instr
	 * @return
	 */
	public static String nvl(String instr) {
		return nvl(instr, "");
	}

	/**
	 * 过滤 ,把null和长度为0的""当成同一种情况处理; <br>
	 * 当instr==null||"".equals(instr)时返回defaultValue ;其它情况返回 instr
	 * 
	 * @param instr
	 * @param defaultValue
	 * @return
	 */
	public static String nvl(String instr, String defaultValue) {
		return instr == null || "".equals(instr) ? defaultValue : instr;
	}

	/**
	 * 比较 str1 和 str2 如果都是 null 或者 str1.equals(str2) 返回 true 表示一样 ;
	 * 
	 * @author wangp
	 * @since 2009.01.10
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 != null && str1.equals(str2))
			return true;
		return false;
	}

	public static String apadLeft(double a, int b, int len) {
		return apadLeft(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadRight(double a, int b, int len) {
		return apadRight(String.valueOf(a), String.valueOf(b), len);
	}

	public static String apadLeft(String str, String str2, int len) {
		if (str == null || str.length() == len || str2 == null)
			return str;
		if (str.length() > len)
			return str.substring(str.length() - len, len);
		return apadpro(str, str2, len, true);
	}

	public static String apadRight(String str, String str2, int len) {
		if (str == null || str.length() == len || str2 == null)
			return str;
		if (str.length() > len)
			return str.substring(0, len);
		return apadpro(str, str2, len, false);
	}

	private static String apadpro(String a, String b, int len, boolean appendleft) {
		int f = len - a.length();
		for (int i = 0; i < f; i++) {
			a = appendleft == true ? b + a : a + b;
		}
		return a;
	}

	/**
	 * 清除字符串中所有的空格 ,传入null返回null
	 * 
	 * @author wangp
	 * @since 2009.02.06
	 * @param str
	 * @return
	 */
	public static String clear(String str) {
		return clear(str, " ");
	}

	/**
	 * 清除str中出现的所有str2字符序列 直到结果中再也找不出str2为止 str2 == null时 返回str
	 * 
	 * @author wangp
	 * @since 2009.02.06
	 * @param str
	 *            原始字符串
	 * @param str2
	 *            清除的目标
	 * @return
	 */
	public static String clear(String str, String str2) {
		if (str == null)
			return str;
		if (str2 == null)
			return str;
		String reg = "(" + str2 + ")+";
		Pattern p = Pattern.compile(reg);
		while (p.matcher(str).find()) {
			str = str.replaceAll(reg, "");
		}
		return str;
	}

	/**
	 * 如果str的长度超过了c则取c-sub.length长度,然后拼上sub结尾
	 * 
	 * @author wangp
	 * @since 2009.06.11
	 * @param str
	 * @param c
	 * @param sub
	 * @return
	 */
	public static String suojin(String str, int c, String sub) {
		if (isEmpty(str))
			return str;
		if (str.length() <= c)
			return str;
		sub = nvl(sub);
		c = c - sub.length();
		c = c > str.length() ? 0 : c;
		str = str.substring(0, c);
		return str + sub;
	}

	/**
	 * 如果str的长度超过了length,取前length位然后拼上...
	 * 
	 * @author yimian
	 * @since 2009.06.11
	 * @param str
	 * @param length
	 * @return
	 */
	public static String suojin(String str, int length) {
		return suojin(str, length, "…");
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0)
			return text;
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1)
			return text;
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = increase >= 0 ? increase : 0;
		increase *= max >= 0 ? max <= 64 ? max : 64 : 16;
		StringBuffer buf = new StringBuffer(text.length() + increase);
		do {
			if (end == -1)
				break;
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0)
				break;
			end = text.indexOf(searchString, start);
		} while (true);
		buf.append(text.substring(start));
		return buf.toString();
	}
	public static String[] splitStr(String str, int splitLen) {  
	    int count = str.length() / splitLen + (str.length() % splitLen == 0 ? 0 : 1);  
	    String[] strs = new String[count];  
	    for (int i = 0; i < count; i++) {  
	        if (str.length() <= splitLen) {  
	            strs[i] = str;  
	        } else {  
	            strs[i] = str.substring(0, splitLen);  
	            str = str.substring(splitLen);  
	        }  
	    }  
	    return strs;  
	} 
	public static String toJoinString(String str,int splitLen){
		String[] splitStrings=splitStr(str, splitLen);
		StringBuffer sb=new StringBuffer();
		for(int x=0;x<splitStrings.length;x++){
			System.out.println(splitStrings[x]+"\n");
			if(x<splitStrings.length-1){
				sb.append(splitStrings[x]+"\n");
			}else{
				sb.append(splitStrings[x]);
			}
		}
		return sb.toString();
	}
	public static String bSubstring(String s,int start, int length) throws Exception
    {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2+start; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++)
        {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1)
            {
                n++; // 在UCS2第二个字节时n加1
            }
            else
            {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0)
                {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)

        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }

        return new String(bytes, start, i, "Unicode");
    }
	public static int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;
                
        }
        return length;
        
    }

	public static void main(String[] args) throws Exception {
		String s="股東(發起人)名稱:江万花,證件(照)類型:中華人民共和國居民身份證,證件(照)號碼:****,認繳出資額:500萬,幣種:,認繳出資額折萬美元:0,認繳出資方式:貨幣,認繳出資時間:2036-10-13;【退出】";
		toJoinString(s, 9);
		/*System.out.println("500萬,幣種:,".length());
		System.out.println(getWordCount(s));
		System.out.println(s.length());
		System.out.println(bSubstring(s,0, 16));
		System.out.println(bSubstring(s,16, 16));*/
	}
}
