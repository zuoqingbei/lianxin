/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hailian.component.config;

import java.net.URL;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal3.JFinal3BeetlRenderFactory;

import cn.dreampie.quartz.QuartzPlugin;

import com.beetl.functions.BeetlStrUtils;
import com.feizhou.swagger.config.routes.SwaggerRoutes;
import com.hailian.component.beelt.BeeltFunctions;
import com.hailian.component.interceptor.CommonInterceptor;
import com.hailian.component.interceptor.PageViewInterceptor;
import com.hailian.component.interceptor.SiteInterceptor;
import com.hailian.component.interceptor.UpdateCacheInterceptor;
import com.hailian.component.interceptor.UserKeyInterceptor;
import com.hailian.component.util.JFlyFoxCache;
import com.hailian.jfinal.component.annotation.AutoBindModels;
import com.hailian.jfinal.component.annotation.AutoBindRoutes;
import com.hailian.jfinal.component.handler.BasePathHandler;
import com.hailian.jfinal.component.handler.CurrentPathHandler;
import com.hailian.jfinal.component.handler.HtmlHandler;
import com.hailian.jfinal.component.handler.XssHandler;
import com.hailian.jfinal.component.interceptor.ExceptionInterceptor;
import com.hailian.jfinal.component.interceptor.JflyfoxInterceptor;
import com.hailian.jfinal.component.interceptor.SessionAttrInterceptor;
import com.hailian.modules.admin.ordermanager.model.TemplateCompanyService;
import com.hailian.modules.credit.agentmanager.service.TemplateAgentService;
import com.hailian.modules.credit.common.service.TemplateCountryService;
import com.hailian.modules.credit.reportmanager.service.TemplateReportModuleService;
import com.hailian.modules.front.template.TemplateCustomService;
import com.hailian.modules.front.template.TemplateDictService;
import com.hailian.modules.front.template.TemplateImageService;
import com.hailian.modules.front.template.TemplateReportTypeService;
import com.hailian.modules.front.template.TemplateService;
import com.hailian.modules.front.template.TemplateSysUserService;
import com.hailian.modules.front.template.TemplateVideoService;
import com.hailian.system.user.UserInterceptor;
import com.hailian.util.Config;
import com.hailian.util.StrUtils;
import com.hailian.util.cache.Cache;
import com.hailian.util.cache.CacheManager;
import com.hailian.util.cache.ICacheManager;
import com.hailian.util.cache.RedisCache;
import com.hailian.util.cache.impl.MemoryCache;
import com.hailian.util.cache.impl.MemorySerializeCache;
import com.hailian.util.serializable.FSTSerializer;
import com.hailian.util.serializable.SerializerManage;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

/**
 * API引导式配置
 */
public class BaseConfig extends JFinalConfig {

	private static final String CONFIG_WEB_ROOT = "{webroot}";
	public void configConstant(Constants me) {
		me.setDevMode(isDevMode());
		me.setViewType(ViewType.JSP);
		me.setFreeMarkerTemplateUpdateDelay(0);//html页面缓存时间为10分钟
		//me.setViewType(ViewType.JSP); // 设置视图类型为Jsp，否则默认为FreeMarker
		me.setLogFactory(new Log4jLogFactory());
		me.setError401View(Config.getStr("PAGES.401"));
		me.setError403View(Config.getStr("PAGES.403"));
		me.setError404View(Config.getStr("PAGES.404"));
		me.setError500View(Config.getStr("PAGES.500"));

		//配置国际化资源默认的basename
		//url?_local=zh-CN/en_US,前台页面可以获取到配置文件里面的key值
		me.setI18nDefaultBaseName("i18n");
		me.setI18nDefaultLocale("zh_CN");
		// 开启日志
		SqlReporter.setLog(true);
		me.setMaxPostSize(104857600*5);
		configDirective(me);

	};

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.setBaseViewPath("/pages");
		// 自动绑定
		// 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
		// 2.加入ControllerBind的 获取 key
		me.add(new AutoBindRoutes());
		me.add(new SwaggerRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = null;

		String db_type = Config.getStr("db_type") + ".";

		String webRoot = PathKit.getWebRootPath();
		String DBPath = webRoot + "\\WEB-INF\\";
		DBPath = StrUtils.replace(DBPath, "\\", "/");
		String jdbcUrl = Config.getStr(db_type + "jdbcUrl");
		if (db_type.startsWith("sqlite")) {
			jdbcUrl = StrUtils.replaceOnce(jdbcUrl, CONFIG_WEB_ROOT, DBPath);
		}

		c3p0Plugin = new C3p0Plugin( //
				jdbcUrl, Config.getStr(db_type + "user"), //
				Config.getStr(db_type + "password").trim(), //
				Config.getStr(db_type + "driverClass"));

		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		if (isDevMode()) {
			arp.setShowSql(true);
		}

		// 数据库类型
		if (db_type.startsWith("postgre")) {
			arp.setDialect(new PostgreSqlDialect());
		} else if (db_type.startsWith("sqlite")) {
			arp.setDialect(new Sqlite3Dialect());
		} else if (db_type.startsWith("oracle")) {
			arp.setDialect(new OracleDialect());
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		}

        //启用ehcache
        URL url = getClass().getResource("resources/ehcache.xml");
        me.add(new EhCachePlugin(url));

		new AutoBindModels(arp);
		
		//定时器
		QuartzPlugin quartzPlugin = new QuartzPlugin();
		quartzPlugin.setJobs("conf/quartz.properties");
		me.add(quartzPlugin);
	}

	@Override
	public void configHandler(Handlers me) {
		// Beelt
		// me.add(new BeeltHandler());
		// me.add(new ImageHandler());

		me.add(new HtmlHandler());
		// 全路径获取
		me.add(new BasePathHandler(Config.getStr("PATH.BASE_PATH")));
		// 根目录获取
		me.add(new ContextPathHandler(Config.getStr("PATH.CONTEXT_PATH")));
		// 当前获取
		me.add(new CurrentPathHandler(Config.getStr("PATH.CURRENT_PATH")));
        // xss防范
        me.add(new XssHandler(Config.getStr("PATH.BASE_PATH")));
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//国际化
		 me.add(new I18nInterceptor());
		// 异常拦截器，跳转到500页面
		me.add(new ExceptionInterceptor());
		// session model转换
		me.add(new SessionInViewInterceptor());
		// 设置session属性
		me.add(new SessionAttrInterceptor());
		// 公共拦截器
		me.add(new JflyfoxInterceptor());
		// 用户Key设置
		me.add(new UserKeyInterceptor());
		// page view 统计
		me.add(new PageViewInterceptor());
		// 缓存更新
		me.add(new UpdateCacheInterceptor());
		// 用户认证
		me.add(new UserInterceptor());
		// 站点拦截
		me.add(new SiteInterceptor());
		// 公共属性
		me.add(new CommonInterceptor());
		
		//me.add(new CreditFrontUserInterceptor());
	}

	/**
	 * 初始化
	 */
	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();

		JFlyFoxCache.init();
		System.out.println("##################################");
		System.out.println("############系统启动完成##########");
		System.out.println("##################################");
	}

	@Override
	public void beforeJFinalStop() {
		super.afterJFinalStart();

		// 初始化Cache为fst序列化
		SerializerManage.add("fst", new FSTSerializer());

		// 设置序列化工具
		String defaultKey = Config.getStr("CACHE.SERIALIZER.DEFAULT");
		defaultKey = StrUtils.isEmpty(defaultKey) ? "java" : defaultKey;
		SerializerManage.setDefaultKey(defaultKey);

		// 设置缓存
		CacheManager.setCache(new ICacheManager() {

			public Cache getCache() {
				String cacheName = Config.getStr("CACHE.NAME");
				cacheName = StrUtils.isEmpty(cacheName) ? "MemorySerializeCache" : cacheName;

				if ("MemorySerializeCache".equals(cacheName)) {
					return new MemorySerializeCache();
				} else if ("MemoryCache".equals(cacheName)) {
					return new MemoryCache();
				} else if ("RedisCache".equals(cacheName)) {
					return new RedisCache();
				} else {
					throw new RuntimeException("####init cache error!");
				}
			}
		});

		// 关闭模板
		// BeetlRenderFactory.groupTemplate.close();

		System.out.println("##################################");
		System.out.println("############系统停止完成##########");
		System.out.println("##################################");
	}

	/**
	 * 配置模板
	 */
	public void configEngine(Engine engine) {

	}
	/**
	 * 
	 * @todo  定义模板
	 * @time   2018年8月27日 下午5:44:46
	 * @author zuoqb
	 * @params 
	 */
	private void configDirective(Constants me) {
		//站点标签------------------
		//-----------------------核心服务-------------------------
		// 获取GroupTemplate ,可以设置共享变量等操作
		JFinal3BeetlRenderFactory rf = new JFinal3BeetlRenderFactory();
		rf.config();
		me.setRenderFactory(rf);
		GroupTemplate groupTemplate = rf.groupTemplate;
		groupTemplate.registerFunctionPackage("strutil", BeetlStrUtils.class);
		groupTemplate.registerFunctionPackage("flyfox", BeeltFunctions.class); 
		groupTemplate.registerFunctionPackage("temp", TemplateService.class);
		groupTemplate.registerFunctionPackage("tempImage", TemplateImageService.class);
		groupTemplate.registerFunctionPackage("tempVideo", TemplateVideoService.class);
		groupTemplate.registerFunctionPackage("tempDict", TemplateDictService.class);//字典模板
		groupTemplate.registerFunctionPackage("reportType", TemplateReportTypeService.class);//报告类型模板
		groupTemplate.registerFunctionPackage("sysUser", TemplateSysUserService.class);//系统用户模板
		groupTemplate.registerFunctionPackage("custom", TemplateCustomService.class);//系统客户模板
		groupTemplate.registerFunctionPackage("company", TemplateCompanyService.class);//系统公司模板
		groupTemplate.registerFunctionPackage("agent", TemplateAgentService.class);//代理模板
		groupTemplate.registerFunctionPackage("country", TemplateCountryService.class);//国家模板
		groupTemplate.registerFunctionPackage("module", TemplateReportModuleService.class);//国家模板
	}
	private boolean isDevMode() {
		return Config.getToBoolean("CONSTANTS.DEV_MODE");
	}
	public static void main(String[] args) {
    	PathKit.setWebRootPath("src/main/webapp/");
		JFinal.start("src/main/webapp", 8080, "/", 5);
    }
}
