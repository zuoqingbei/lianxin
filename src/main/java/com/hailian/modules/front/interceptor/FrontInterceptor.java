package com.hailian.modules.front.interceptor;

import java.util.List;
import java.util.Map;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.article.TbArticle;
import com.hailian.modules.admin.folder.FolderService;
import com.hailian.modules.admin.folder.TbFolder;
import com.hailian.modules.admin.friendlylink.FriendlylinkCache;
import com.hailian.modules.admin.friendlylink.TbFriendlylink;
import com.hailian.modules.admin.site.SessionSite;
import com.hailian.modules.admin.site.SiteConstant;
import com.hailian.modules.front.service.FrontCacheService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

/**
 * 用户认证拦截器
 * 
 * @author flyfox 2014-2-11
 */
public class FrontInterceptor implements Interceptor {

	public void intercept(Invocation ai) {

		Controller controller = ai.getController();
		// 栏目，缓存
		SessionSite site = new SessionSite();
		if (controller instanceof BaseProjectController) {
			site = ((BaseProjectController) controller).getSessionSite();
		} else {
			site = controller.getSessionAttr(SiteConstant.getSessionSite());
		}
		Map<String, List<TbFolder>> folders = new FolderService().getFolderMenus(site.getSiteId());
		controller.setAttr("folders", folders);

		// 推荐文章列表，缓存
		Page<TbArticle> recommendArticles = new FrontCacheService().getRecommendArticle(new Paginator(1, 8),
				site.getSiteId());
		controller.setAttr("recommendArticles", recommendArticles);

		// 友情链接，缓存
		List<TbFriendlylink> friendlylinkList = FriendlylinkCache.getFriendlylinkList(site.getSiteId());
		controller.setAttr("friendlylinkList", friendlylinkList);

		// 底部关于，缓存
		List<TbFriendlylink> aboutList = FriendlylinkCache.getAboutList(site.getSiteId());
		controller.setAttr("aboutList", aboutList);

		ai.invoke();
	}
}
