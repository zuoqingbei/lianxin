package com.hailian.jfinal.component.handler;

import com.jfinal.handler.Handler;
import org.jsoup.helper.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * 全局xss过滤
 * @author oycw
 * @date 创建时间：2017年5月18日 下午1:45:37
 */
public class XssHandler extends Handler {

    // 排除的url，使用的target.startsWith匹配的
    private String excludePattern;

    /**
     * 忽略列表，使用正则
     * @param excludePattern
     */
    public XssHandler(String excludePattern) {
        this.excludePattern = excludePattern;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        java.util.regex.Pattern pattern = Pattern.compile(excludePattern);
        //带.表示非action请求，忽略（其实不太严谨，如果是伪静态，比如.html会被错误地排除）；匹配excludePattern的，忽略
        if (target.indexOf(".") == -1 && !(!StringUtil.isBlank(excludePattern) && pattern.matcher(target).find() ) ){
            request = new XssHttpServletRequestWrapper(request);
        }
        //别忘了
        next.handle(target, request, response, isHandled);

    }
}