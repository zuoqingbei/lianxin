package com.ulab.util;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.core.BodyContent;
import org.beetl.core.Tag;
import org.beetl.core.TagFactory;
import org.beetl.core.Template;

import com.jfinal.kit.StrKit;
import com.ulab.core.Constants;

/**
 *  org.bee.tl.ext.HTMLTagSupportWrapper <br>
 *      框架自带的实现不太满足我的需求，自己重载了一个，增加了功能。<br>
 *      1、增加一个判断是否存在tagBody的变量hasBody <br>
 *      2、修改了加载tag文件的目录位置，增加对插件自己的tag的支持
 * @author zuoqb
 * @date 2017年4月14日10:11:52
 * <#echarts url="${url}" title="折线图" chartType="line" docId="${docId}" showToolBox="false">
				<div id="${docId}" style="height: 190px;width:350px;"></div>
			</#echarts>
 * 
 */
public class HTMLTagSupportWrapper extends Tag {


	@SuppressWarnings("unchecked")
	protected void callHtmlTag(String file) {
		Template t = null;
		t = gt.getTemplate(file);
		t.binding(ctx.globalVar);
		if (args.length == 2) {
			Map<String, Object> map = (Map<String, Object>) args[1];
			for (Entry<String, Object> entry : map.entrySet()) {
				t.binding(entry.getKey(), entry.getValue());
			}
		}
		t.binding("hasBody", false);
		BodyContent bodyContent = super.getBodyContent();
		if(StrKit.notBlank(bodyContent.getBody())){
			t.binding("hasBody", true);
		}
		t.binding("tagBody", bodyContent);
		t.renderTo(ctx.byteWriter);
	}

	protected void callTag(TagFactory tagFactory)
	{

		Tag tag = tagFactory.createTag();
		tag.init(ctx, args, bs);
		tag.render();

	}

	public void render() {

		if (args.length == 0 || args.length > 2) {
			throw new RuntimeException("参数错误，期望child,Map .....");
		}
		String child = (String) args[0];
		// 首先查找 已经注册的Tag
		TagFactory tagFactory = null;
		String functionTagName = child.replace(':', '.');
		tagFactory = this.gt.getTagFactory(functionTagName);
	
		// 获得当前模板页面的相对目录空间
		String path = child.replace(':', File.separatorChar);
		if (tagFactory == null) {
			callHtmlTag(Constants.TAG_TEMPLTE_DIR + path.replace("_", "") + ".tag");
		} else {
			callTag(tagFactory);
		}
	}

}
