/**
 * Sumpay.cn.
 * Copyright (c) 2007-2015 All Rights Reserved.
 */
package com.config;

import com.controller.HelloController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.model.User;

/**
 * 
 * @time   2017年4月10日 下午4:29:50
 * @author zuoqb
 * @todo   配置文件
 */
public class MyCofig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
    	loadPropertyFile("config.txt");
	    me.setDevMode(getPropertyToBoolean("devMode", true));
		//me.setError404View("${tpl_dir}404.htm");
		//me.setError500View("${tpl_dir}500.htm");
		me.setEncoding("UTF-8");  
		me.setMaxPostSize(536870912);//512M
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/hello", HelloController.class);

    }

    @Override
    public void configPlugin(Plugins me) {
    	 ActiveRecordPlugin arp=null;  
         DruidPlugin dp=new DruidPlugin(this.getProperty("oracle.url"), this.getProperty("oracle.user"), 
        		 this.getProperty("oracle.password"), getProperty("oracle.driver"));  
         dp.setInitialSize(5);
         dp.setMaxActive(5);
         dp.setMinIdle(3);
         me.add(dp);  
         arp=new ActiveRecordPlugin(dp);//设置数据库方言  
         arp.setDialect(new OracleDialect());  
         arp.setContainerFactory(new CaseInsensitiveContainerFactory(false));//忽略大小写  
         arp.setShowSql(true);
         arp.addMapping("lhjx_specific_code","id", User.class); 
         me.add(arp);  
    }

    @Override
    public void configInterceptor(Interceptors me) {
    	me.add(new SessionInViewInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    public static void main(String[] args) {
    	PathKit.setWebRootPath("src/main/webapp/");
		JFinal.start("src/main/webapp", 80, "/", 5);
    }
}
