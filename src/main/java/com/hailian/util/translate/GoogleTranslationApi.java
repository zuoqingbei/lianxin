package com.hailian.util.translate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hailian.util.http.RandomUtil;

public class GoogleTranslationApi {
	 private static Map<String,String> language = new HashMap<>();
	    private static int i = 0;
	    private static Querier<GoogleTranslatorHtml> querierTrans = null;
	    public static void main(String[] args) {
	         String result = translateEnglish("该评估为揭示目标公司付款习惯和支付能力作参考。基于三个重要因素：交易支付历史（通过询问现阶段目标公司供货商）、我方收集目标公司的不正当支付记录和债务追讨记录。");
	         System.out.println(result);
	    }
	    public static String translateEnglish(String text){
	    	String value="";
	    	if(StringUtils.isBlank(text)){
	    		return value;
	    	}
	    	value=detectLanguage(LANG.EN, text);
	    	if(StringUtils.isBlank(value)){
	    		value=detectLanguage(LANG.EN, text);
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(500,2000));
		                value=detectLanguage(LANG.EN, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,3000));
		                value=detectLanguage(LANG.EN, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,4000));
		                value=detectLanguage(LANG.EN, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,5000));
		                value=detectLanguage(LANG.EN, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	return value;
	    }
	    /**
	     * 繁体
	     * @param text
	     * @return
	     */
	    public static String translateCht(String text){
	    	//zh-TW
	    	String value="";
	    	if(StringUtils.isBlank(text)){
	    		return value;
	    	}
	    	value=detectLanguage(LANG.ZHTW, text);
	    	if(StringUtils.isBlank(value)){
	    		value=detectLanguage(LANG.ZHTW, text);
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(500,2000));
		                value=detectLanguage(LANG.ZHTW, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,3000));
		                value=detectLanguage(LANG.ZHTW, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,4000));
		                value=detectLanguage(LANG.ZHTW, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	if(StringUtils.isBlank(value)){
	    		 try {
		                Thread.sleep(RandomUtil.randomLong(1000,5000));
		                value=detectLanguage(LANG.ZHTW, text);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
	    	}
	    	return value;
	    }
	    public static String detectLanguage(LANG target,String text){
	       /* if(StringUtils.isBlank(text)){
	            return "";
	        }
	        if(querierTrans==null||i==50){
	            querierTrans = new Querier<GoogleTranslatorHtml>();                   // 获取查询器
	            querierTrans.attach(new GoogleTranslatorHtml());     // 向查询器中添加 Google 翻译器
	        }
	        if(i==50){
	            try {
	                //System.out.println("----waiting");
	                Thread.sleep(RandomUtil.randomLong(500,5000));
	                //System.out.println("----complete");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            i=0;
	        }
	        querierTrans.setParams(LANG.AUTO, target, text);    // 设置参数
	        List<String> result = querierTrans.execute();    // 执行查询并接收查询结果
	        i++;
	        return result.get(0);*/
	    	if(StringUtils.isBlank(text)){
	            return "";
	        }
	    	if(querierTrans==null){
	            querierTrans = new Querier<GoogleTranslatorHtml>();                   // 获取查询器
	            querierTrans.attach(new GoogleTranslatorHtml());     // 向查询器中添加 Google 翻译器
	        }
	        querierTrans.setParams(LANG.AUTO, target, text);    // 设置参数
	        List<String> result = querierTrans.execute();    // 执行查询并接收查询结果
	        i++;
	        return result.get(0);
	    }
}
