package com.ulab.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.DicModel;
import com.ulab.model.LabCarryModel;
import com.ulab.model.LabDataResultModel;
import com.ulab.model.LabMapModel;
import com.ulab.model.LabModel;
import com.ulab.util.SqlUtil;
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
    	List<Record> labType=DicModel.dao.findDicByType("lab_type");
    	List<Record> productLine=DicModel.dao.findDicByType("line_type");
    	setAttr("labType", labType);
    	setAttr("productLine", productLine);
        render("index_left.html");
    }
    /**
     * 
     * @time   2017年4月19日 下午3:19:44
     * @author zuoqb
     * @todo   进入平面地图
     * @param  
     * @return_type   void
     */
    public void flatMap() {
        render("flatMap.html");
    }
    public void sphereMap() {
        render("sphereMap.html");
    }
    /**
     * 
     * @time   2017年4月19日 下午3:20:03
     * @author zuoqb
     * @todo   获取字典数据
     * @param  
     * @return_type   void
     */
    public void dicAjax(){
    	List<Record> labType=DicModel.dao.findDicByType("lab_type");
    	List<Record> productLine=DicModel.dao.findDicByType("line_type");
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("labType", labType);
    	map.put("productLine", productLine);
    	renderJson(map);
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
    	List<Record> parentList=LabModel.dao.labShowWorldMap();
    	renderJson(parentList);
    }
    /**
     * 
     * @time   2017年4月19日 上午9:53:33
     * @author zuoqb
     * @todo   平面图展示数据
     * @param  
     * @return_type   void
     */
    public void labShowFlatMapAjax(){
    	String sqlWhere=SqlUtil.commonWhereSql(this,null);
    	List<Record> parentList=LabMapModel.dao.labShowFlatMap(sqlWhere);
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
    	String sqlWhere=SqlUtil.commonWhereSql(this,"lab");
    	List<Record> labStatis=LabModel.dao.labStatisByField(sqlWhere,field);
		renderJson(labStatis);
    }
    
    /**
     * 
     * @time   2017年4月21日12:12:13
     * @author zuoqb
     * @todo  开展类型数量统计-实验目的
     */
    public void labCarryNumStatisAjax(){
    	String sqlWhere=SqlUtil.commonWhereSql(this,"lab");
    	//开展类型-实验目的
    	List<Record> carryStatis=LabCarryModel.dao.labCarryNumStatis(sqlWhere);
    	//实验目的对应的订单量
		renderJson(carryStatis);
    }
    /**
     * 
     * @time   2017年4月14日 下午4:59:52
     * @author zuoqb
     * @todo   实验室联通数据统计
     */
    public void labLinkAjax(){
    	Record  recode=LabModel.dao.labLink();
		renderJson(recode);
    }
    ////////////////////////////////////右侧////////////////////////////
    public void right() {
        render("index_right.html");
    }
    /**
     * 
     * @time   2017年4月23日 下午12:07:14
     * @author zuoqb
     * @todo   标准状态数据统计
     * @param  
     * @return_type   void
     */
    public void standardStatusAjax(){
    	Record  recode=LabDataResultModel.dao.standardStatus();
		renderJson(recode);
    }
    /**
     * 
     * @time   2017年4月23日 下午12:07:14
     * @author zuoqb
     * @todo   能力状态数据统计
     * @param  
     * @return_type   void
     */
    public void abilityStatusAjax(){
    	Integer allNum=LabDataResultModel.dao.resultNumStatisticBType(3);
    	List<Record>  list=LabDataResultModel.dao.dataResultBType(1);
    	Record result=new Record();
    	result.set("allNum", allNum);
    	result.set("data", list);
		renderJson(result);
    }
}
