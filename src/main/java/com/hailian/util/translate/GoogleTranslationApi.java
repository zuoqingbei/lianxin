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
	         String result = translateCht("出资者/股东");
	         System.out.println(result);
	    }
	    public static String translateEnglish(String text){
	    	return detectLanguage(LANG.EN, text);
	    }
	    /**
	     * 繁体
	     * @param text
	     * @return
	     */
	    public static String translateCht(String text){
	    	//zh-TW
	    	return detectLanguage(LANG.ZHTW, text);
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
