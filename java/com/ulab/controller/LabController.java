package com.ulab.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.LabMapModel;
import com.ulab.model.LabModel;
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
     * @todo   全球实验室数据-地图
     * @param  
     * @return_type   void
     */
    public void labAjax(){
    	List<Record> parentList=LabMapModel.dao.labShowWorldMap();
    	renderJson(parentList);
    }
    
    /**
     * 
     * @time   2017年4月14日 下午3:19:37
     * @author zuoqb
     * @todo   实验室数量统计
     */
    public void labNumStatisAjax(){
		renderJson(LabModel.dao.findAllCount());
    }
   
    /**
     * 
     * @time   2017年4月14日 下午4:59:52
     * @author zuoqb
     * @todo   实验室区域数量统计：大洲 国家
     */
    public void labSpreadAjax(){
    	Record  recode=LabModel.dao.labSpread();
		renderJson(recode);
    }
    
    /**
     * 
     * @time   2017年4月14日 下午3:19:37
     * @author zuoqb
     * @todo  按照某维度统计数量
     */
    public void labStatisByFiledAjax(){
    	String field=getPara("field");
    	List<Record> labStatis=LabModel.dao.labStatisByField(field);
		renderJson(labStatis);
    }
}
