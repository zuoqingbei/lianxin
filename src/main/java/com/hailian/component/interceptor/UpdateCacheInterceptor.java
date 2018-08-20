package com.hailian.component.interceptor;

import com.hailian.modules.admin.comment.CommentService;
import com.hailian.modules.admin.folder.FolderService;
import com.hailian.modules.front.service.FrontCacheService;
import com.hailian.modules.front.service.FrontImageService;
import com.hailian.modules.front.service.FrontVideoService;
import com.hailian.util.Config;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 缓存更新拦截器
 * 
 * @author flyfox 2015-9-21
 */
public class UpdateCacheInterceptor implements Interceptor {

	/**
	 * 缓存更新时间
	 */
	public static final long UPDATE_TIME = Config.getToLong("CMS.UPDATE_TIME");

	public static long lastUpdateTime = System.currentTimeMillis();

	public void intercept(Invocation ai) {

		// 如果想让体验更好，可以调用一遍所有缓存方法（比较麻烦）
		long now = System.currentTimeMillis();
		if (UPDATE_TIME > 0 && now - lastUpdateTime > UPDATE_TIME) {
			// 更新目录缓存
			new FolderService().updateCache();
			// 清除回复数缓存
			new CommentService().clearCache();
			// 清除所有前台缓存
			new FrontCacheService().clearCache();
			// 清除前台图片缓存
			new FrontImageService().clearCache();
			// 清除前台视频缓存
			new FrontVideoService().clearCache();

			lastUpdateTime = now;
		}
		ai.invoke();

	}
}
