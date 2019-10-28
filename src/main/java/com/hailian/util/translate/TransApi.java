package com.hailian.util.translate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hailian.util.Config;
import com.hailian.util.MD5;
import com.hailian.util.http.HttpUtil;
import com.hailian.util.http.RandomUtil;
import com.hailian.util.http.TransResult;
public class TransApi {	 
	public static final String appId = Config.getStr("appid");//百度翻译appid
	public static final String secretkey = Config.getStr("secretkey");//百度翻译秘钥
	
	  /**
     * 百度翻译接口地址
     */
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";


    private static String appid=Config.getStr("appid");//百度翻译appid

    private static String securityKey=Config.getStr("secretkey");//百度翻译秘钥
	public static String Trans(String q,String targetlanguage) {
		if("en".equals(targetlanguage)){
			//英语走google翻译
			return GoogleTranslationApi.translateEnglish(q);
		}else{
			return GoogleTranslationApi.translateCht(q);
			/*String result="";
			//繁体走百度  百度翻译有调用频率限制
			try {
				result=getTransResult(q, "auto", targetlanguage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;*/
		}
	}
	 /**
     * 获得翻译结果
     * @param query
     * @param from
     * @param to
     * @return
     * @throws IOException
     */
    public static String getTransResult(String query, String from, String to) throws IOException {
        Map<String, String> params = buildParams(query, from, to);
        JSONObject jsonObject;
        try {
        	 Thread.sleep(RandomUtil.randomLong(2000,10000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //当请求翻译内容过长 用post
        if (query.length() >= 2000) {
            //post 请求方式
            jsonObject = HttpUtil.doPostStr(TRANS_API_HOST, params);
        } else {
           //  get请求方式
            String url = getUrlWithQueryString(TRANS_API_HOST, params);
            jsonObject = HttpUtil.doGetStr(url);
        }
        if (jsonObject.get("error_code")!=null) {
        	System.out.println(jsonObject);
        	String r=query+" 翻译失败，原因："+jsonObject.get("error_msg")+",错误码："+jsonObject.get("error_code");
             System.out.println(r);//54003	访问频率受限	请降低您的调用频率
             if("54003".equals(jsonObject.get("error_code"))){
            	 return getTransResulRepeat(query, from, to);
             }else{
            	 return "";
             }
        }else{
            TransResult transResult = JSON.parseObject(jsonObject.toString(), TransResult.class);
            return " 翻译结果 "+transResult.getTrans_result().get(0).getDst();
        }
    }
    public static String getTransResulRepeat(String query, String from, String to) throws IOException {
        Map<String, String> params = buildParams(query, from, to);
        JSONObject jsonObject;
        try {
        	 Thread.sleep(RandomUtil.randomLong(2000,10000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //当请求翻译内容过长 用post
        if (query.length() >= 2000) {
            //post 请求方式
            jsonObject = HttpUtil.doPostStr(TRANS_API_HOST, params);
        } else {
           //  get请求方式
            String url = getUrlWithQueryString(TRANS_API_HOST, params);
            jsonObject = HttpUtil.doGetStr(url);
        }
        if (jsonObject.get("error_code")!=null) {
        	System.out.println(jsonObject);
        	String r=query+" 翻译失败，原因："+jsonObject.get("error_msg")+",错误码："+jsonObject.get("error_code");
            return r;
        }else{
            TransResult transResult = JSON.parseObject(jsonObject.toString(), TransResult.class);
            return transResult.getTrans_result().get(0).getDst();
        }
    }

    /**
     * 构建参数map
     *
     * @param query
     * @param from
     * @param to
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Map<String, String> buildParams(String query, String from, String to) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }


    /**
     * 拼接url get方式拼接参数  返回url
     *
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }


    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
	
	public static void main(String[] args) {
		String trans = Trans("监事会主席","en");
		System.out.println(trans);
		/*String transTOEn = Trans("山东省青岛市高新区汇智桥路151号中科研发城2号楼5层13室","en");
		System.out.println(transTOEn);*/
	}
}


