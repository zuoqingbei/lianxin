package com.ulab.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.LabMapModel;
import com.ulab.model.LabModel;
import com.ulab.model.User;
/**
 * 
 * @time   2017年4月11日 上午10:59:00
 * @author zuoqb
 * @todo   测试类
 */
@ControllerBind(controllerKey = "/lab", viewPath = "/lab")
@Before({GlobalInterceptor.class})
public class LabController extends BaseController {
	
    public void index() {
        render("index.html");
    }
    public void flatMap() {
        render("flatMap.html");
    }
  
    /**
     * 
     * @time   2017年4月13日 上午9:38:56
     * @author zuoqb
     * @todo   全球实验室数据
     * @param  
     * @return_type   void
     */
    public void labAjax(){
    	List<Record> parentList=LabMapModel.dao.labShowWorldMap();
    	renderJson(parentList);
    }
}
