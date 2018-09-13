package com.hailian.modules.front.controller;

import com.hailian.component.base.BaseProjectController;
import com.hailian.component.util.JFlyFoxUtils;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.article.TbArticle;
import com.hailian.modules.front.interceptor.FrontInterceptor;
import com.hailian.modules.front.service.FrontCacheService;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;

/**
 * 关于我们
 * 
 * 2015年5月26日 上午10:42:54 flyfox 369191470@qq.com
 */
@ControllerBind(controllerKey = "/front/about")
public class AboutController extends BaseProjectController {

	public static final String path = "/about/";

	/**
	 * 关于我们
	 */
	@Before(FrontInterceptor.class)
	public void index() {
		Integer articleId = getParaToInt();

		setAttr("folders_selected", JFlyFoxUtils.MENU_ABOUT);

		Page<TbArticle> pages = new FrontCacheService().getArticle(new Paginator(1, 100), JFlyFoxUtils.MENU_ABOUT);
		setAttr("pages", pages);

		TbArticle article = null;
		if (articleId == null || articleId <= 0) {
			article = pages.getList().get(0);
		} else {
			article = new FrontCacheService().getArticle(articleId);
		}
		setAttr("article", article);

		// seo：title优化
		setAttr(JFlyFoxUtils.TITLE_ATTR, article.getTitle() + " - " + "关于我们 - " + getAttr(JFlyFoxUtils.TITLE_ATTR));

		renderAuto(path + "show_about.html");
	}
}
