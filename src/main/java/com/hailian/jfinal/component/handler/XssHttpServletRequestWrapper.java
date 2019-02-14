package com.hailian.jfinal.component.handler;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * 重写HttpServletRequestWrapper用于配合XssHandler
 * @author oycw
 * @date 创建时间：2017年5月18日 下午1:49:26
 */
public class XssHttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper{

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写并过滤getParameter方法
     */
    @Override
    public String getParameter(String name) {
        return getBasicHtmlandimage(super.getParameter(name));

    }

    /**
     * 重写并过滤getParameterValues方法
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (null == values){
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = getBasicHtmlandimage(values[i]);
        }
        return values;
    }

    /**
     * 重写并过滤getParameterMap方法
     */
    @Override
    public Map<String,String[]> getParameterMap() {
        @SuppressWarnings("unchecked")
        Map<String,String[]> paraMap = super.getParameterMap();
        // 对于paraMap为空的直接return
        if (null == paraMap || paraMap.isEmpty()) {
            return paraMap;
        }

        //super.getParameterMap()不允许任何修改，所以只能做深拷贝
        Map<String, String[]> paraMapCopy = new HashMap<String, String[]>();
        //实际上putAll只对基本类型深拷贝有效，如果是自定义类型，则要找其他办法
        paraMapCopy.putAll(paraMap);

        for (Map.Entry<String, String[]> entry : paraMapCopy.entrySet()) {
            String[] values     = entry.getValue();
            if (null == values) {
                continue;
            }
            String[] newValues  = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = getBasicHtmlandimage(values[i]);
            }
            entry.setValue(newValues);
        }
        return paraMapCopy;
    }

    private static String getBasicHtmlandimage(String html) {
        if (html == null)
            return null;
        return Jsoup.clean(html, Whitelist.basicWithImages());
    }
}