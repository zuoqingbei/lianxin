package com.ulab.util;

import java.util.Map;
import java.util.Map.Entry;

import org.beetl.core.Tag;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;

import com.ulab.core.Constants;

/**
 * 
 * @time   2017年4月14日 上午10:12:35
 * @author zuoqb
 * @todo   布局文件如果用到前端模板，使用该layout tag，
 * 可以使插件的页面自动响应到模板中的布局，这个要配合{@link UrlHandler}使用
 * ## layout("ulab.html",{title:"测试"}){
 * ##}
 */
public class TemplteLayoutTag extends Tag {


	@SuppressWarnings("unchecked")
	public void render() {
		if (args.length==0||args.length > 2) {
			throw new RuntimeException("参数错误，期望child,map");
		}
		String tpl_dir = Constants.LAYOUT_TEMPLTE_DIR;
		
		String child = tpl_dir + args[0];
	
		if (BeetlUtil.isOutsideOfRoot(child)) {
			throw new RuntimeException("layout 文件非法，不在根目录里:" + child);
		}
		Template t = gt.getTemplate(child);
		t.binding(ctx.globalVar);
		t.binding(Constants.ATTR_TEMPLTE_DIR, tpl_dir);
		String varName = "layoutContent";
		t.binding(varName, getBodyContent());

		if (args.length == 2) {
			Map<String, Object> map = (Map<String, Object>) args[1];
			for (Entry<String, Object> entry : map.entrySet()) {
				t.binding(entry.getKey(), entry.getValue());
			}
		}
		t.renderTo(ctx.byteWriter);
	}

}
