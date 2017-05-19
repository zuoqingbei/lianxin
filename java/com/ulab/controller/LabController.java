package com.ulab.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.CommunistModel;
import com.ulab.model.DicModel;
import com.ulab.model.JianCeModel;
import com.ulab.model.LabCarryModel;
import com.ulab.model.LabDataResultModel;
import com.ulab.model.LabMapModel;
import com.ulab.model.LabModel;
import com.ulab.model.OrderModel;
import com.ulab.model.PersonModel;
import com.ulab.util.NormalDistribution;
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
    	setSessionAttr("labType", labType);
    	setSessionAttr("productLine", productLine);
    	//实验室轮播信息
    	String sqlWhere=SqlUtil.commonWhereSql(this,null);
    	List<Record> labInfo=LabMapModel.dao.labShowFlatMap(sqlWhere);
    	for(Record r:labInfo){
    		if(r.getStr("title").length()>4){
    			r.set("title", r.getStr("title").substring(0,4)+"...");
    		}
    	}
    	setAttr("labInfo", labInfo);
        render("index.html");
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
    public void wordCloud() {
        render("wordCloud.html");
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
    	String sort=getPara("sort");
    	List<Record> labStatis=LabModel.dao.labStatisByField(sqlWhere,field,sort);
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
    /**
     * 
     * @time   2017年5月12日 上午7:45:55
     * @author zuoqb
     * @todo   标准数量分布情况
     * @param  
     * @return_type   void
     */
    public void standardDispersedAjax(){
    	String type=getPara("type");
    	String filedVaule=getPara("filedVaule");
    	String filed=getPara("filed");
    	List<Record>  recode=LabDataResultModel.dao.dataResultBTypeAndName(type,filed,filedVaule);
		renderJson(recode);
    }
    /**
     * 
     * @time   2017年5月12日 上午7:45:55
     * @author zuoqb
     * @todo   不同产线的能力状态分布
     * @param  
     * @return_type   void
     */
    public void abilityByProductLineAjax(){
    	List<Record> productLine=getSessionAttr("productLine");
    	if(productLine==null){
    		productLine=DicModel.dao.findDicByType("line_type");
    	}
    	String type=getPara("type","1");
    	String filed=getPara("filed","product_code");
    	List<List<Record>> map=new ArrayList<List<Record>>();
    	List<List<Record>> needmap=new ArrayList<List<Record>>();
    	for(Record r:productLine){
    		String filedVaule=r.get("id").toString();
    		map.add(LabDataResultModel.dao.dataResultBTypeAndName(type,filed,filedVaule));
    		needmap.add(LabDataResultModel.dao.dataResultBTypeAndName(3,filed,filedVaule));
    	}
    	List<List<List<Record>>> list=new ArrayList<List<List<Record>>>();
    	list.add(map);
    	list.add(needmap);
		renderJson(list);
    }
    /**
     * 
     * @time   2017年5月13日 下午1:08:58
     * @author zuoqb
     * @todo   获取某一年订单整体及时率
     * @param  
     * @return_type   void
     */
    public void orderYearRateAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	List<Record> list=new ArrayList<Record>();
    	list.add(OrderModel.dao.findOrderRate("2016",labTypeCode));
    	list.add(OrderModel.dao.findOrderRate("2017",labTypeCode));
		renderJson(list);
    }
    /**
     * 
     * @time   2017年5月13日 下午1:08:58
     * @author zuoqb
     * @todo   按照产线统计某年整体订单及时率
     * @param  
     * @return_type   void
     */
    public void findOrderYearRateForProductAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	list.add(OrderModel.dao.findOrderYearRateForProduct("2016",labTypeCode));
    	list.add(OrderModel.dao.findOrderYearRateForProduct("2017",labTypeCode));
		renderJson(list);
    }
    /**
     * 
     * @time   2017年5月13日 下午1:08:58
     * @author zuoqb
     * @todo   按照产线统计某年各月份详细订单及时率
     * @param  
     * @return_type   void
     */
    public void findOrderMonthRateForProductAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	String startDate=getPara("startDate","201601");
    	String endDate=getPara("endDate","201612");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	List<Record> productLine=getSessionAttr("productLine");
    	if(productLine==null){
    		productLine=DicModel.dao.findDicByType("line_type");
    	}
    	for(Record r:productLine){
    		list.add(OrderModel.dao.findOrderMonthRateForProduct(startDate,endDate,r.get("id").toString(),labTypeCode));
    	}
		renderJson(list);
    }
    
    /**
     * 
     * @time   2017年5月13日 下午1:08:58
     * @author zuoqb
     * @todo    按照产线统计某年各月份详细订单类别
     * @param  
     * @return_type   void
     */
    public void findOrderMonthTypeForProductAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	String desName=getPara("desName","");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	List<Record> productLine=getSessionAttr("productLine");
    	if(productLine==null){
    		productLine=DicModel.dao.findDicByType("line_type");
    	}
    	for(Record r:productLine){
    		list.add(OrderModel.dao.findOrderMonthTypeForProduct(r.get("id").toString(),labTypeCode,desName));
    	}
		renderJson(list);
    }
    /**
     * 
     * @time   2017年5月14日 上午8:59:27
     * @author zuoqb
     * @todo   一次合格率具体到产线
     * @param  
     * @return_type   void
     */
    public void findOrderPassForProAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	String desName=getPara("desName","");
    	String name=getPara("name","");
    	List<Record> list=OrderModel.dao.findOrderPassForPro(null, labTypeCode, desName, name);
		renderJson(list);
    }
    
    /**
     * 
     * @time   2017年5月14日 上午8:59:27
     * @author zuoqb
     * @todo   一次合格率  整体统计
     * @param  
     * @return_type   void
     */
    public void findOrderPassForAllAjax(){
    	String labTypeCode=getPara("labTypeCode","");
    	List<Record> list=new ArrayList<Record>();
    	list.add(OrderModel.dao.findOrderPassForAll(null, labTypeCode, "整机"));
    	list.add(OrderModel.dao.findOrderPassForAll(null, labTypeCode, "模块"));
		renderJson(list);
    }
    
    /**
     * 
     * @time   2017年5月14日 上午10:07:01
     * @author zuoqb
     * @todo   订单及时率  tab1
     * @param  
     * @return_type   void
     */
    
    public void findOrderYearRateForTab1Ajax(){
    	/*String labTypeCode=getPara("labTypeCode","");
    	String date=getPara("date","2017");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	list.add(OrderModel.dao.findOrderYearRateForTab1(date,labTypeCode, "整机"));
    	list.add(OrderModel.dao.findOrderYearRateForTab1(date,labTypeCode, "模块"));
		renderJson(list);*/
    	String plCode=getPara("plCode","");
    	String labTypeCode=getPara("labTypeCode","");
    	String startDate=getPara("startDate","201601");
    	String endDate=getPara("endDate","201612");
    	renderJson(OrderModel.dao.findOrderMonthRateForAll(startDate, endDate, plCode, labTypeCode));
    }
    
    /**
     * 
     * @time   2017年5月14日 上午11:04:34
     * @author zuoqb
     * @todo   一次合格率 总状态
     * @param  
     * @return_type   void
     */
    public void findOrderPassForTab1Ajax(){
		renderJson(OrderModel.dao.findOrderPassForAll(null, null, null));
    }
    /**
     * 
     * @time   2017年5月17日 上午9:49:23
     * @author zuoqb
     * @todo   人员状态-总
     * @param  
     * @return_type   void
     */
    public void findPersonStatusTab1Ajax(){
    	String labTypeCode=getPara("labTypeCode","");
    	String type=getPara("type","");
    	String plCode=getPara("plCode","");
  		renderJson(PersonModel.dao.personDetail(type, plCode, labTypeCode));
      }
    /**
     * 
     * @time   2017年5月17日 下午1:26:59
     * @author zuoqb
     * @todo   实验室状态tab2-人员状态数据
     * @param  
     * @return_type   void
     */
    public void personForTab2Ajax(){
    	String labTypeCode=getPara("labTypeCode","");
    	String type=getPara("type","");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	List<Record> productLine=getSessionAttr("productLine");
    	if(productLine==null){
    		productLine=DicModel.dao.findDicByType("line_type");
    	}
    	for(Record r:productLine){
    		list.add(PersonModel.dao.personForTab2(type, r.get("id").toString(), labTypeCode));
    	}
		renderJson(list);
    }
    //共产 一致数据统计
    /**
     * 
     * @time   2017年5月18日 上午9:55:48
     * @author zuoqb
     * @todo   共产 一致比重统计
     * @param  
     * @return_type   void
     */
    public void communistGravityStatisticForTab1Ajax(){
    	String plCode=getPara("plCode","");
    	String labTypeCode=getPara("labTypeCode","");
    	String startDate=getPara("startDate","201601");
    	String endDate=getPara("endDate","201612");
		renderJson(CommunistModel.dao.communistGravityStatistic(startDate, endDate, plCode, labTypeCode));
    }
    /**
     * 
     * @time   2017年5月18日 上午9:59:03
     * @author zuoqb
     * @todo   根据类型 时间 统计共产 一致个月份数量
     * @param  
     * @return_type   void
     */
    public void communistStatisticForMonthForTab1Ajax(){
    	String plCode=getPara("plCode","");
    	String labTypeCode=getPara("labTypeCode","");
    	String startDate=getPara("startDate","201601");
    	String endDate=getPara("endDate","201612");
    	List<List<Record>> list=new ArrayList<List<Record>>();
    	list.add(CommunistModel.dao.communistStatisticForMonth(startDate, endDate, plCode, labTypeCode, "1"));
    	list.add(CommunistModel.dao.communistStatisticForMonth(startDate, endDate, plCode, labTypeCode, "2"));
		renderJson(list);
    }
    /**
     * 
     * @time   2017年5月18日 下午4:18:54
     * @author zuoqb
     * @todo   直方图 检测数据
     * @param  
     * @return_type   void
     */
    public void jianCeDataForTab1Ajax(){
    	String xhCode=getPara("xhCode","");//型号
    	List<Record> list=JianCeModel.dao.findProviderDicByPid(xhCode);
    	for(Record r:list){
    		//r.set("gd_num_2", NormalDistribution.calc(Float.parseFloat(r.getStr("gd_num"))));
    		r.set("gd_num_2", NormalDistribution.calc(Float.parseFloat(r.getStr("gd_num")),74.7f,0.6734f));
    	}
		renderJson(list);
    }
}
