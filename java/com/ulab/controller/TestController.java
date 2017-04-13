package com.ulab.controller;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.aop.GlobalInterceptor;
import com.ulab.core.BaseController;
import com.ulab.model.LabModel;
import com.ulab.model.User;
/**
 * 
 * @time   2017年4月11日 上午10:59:00
 * @author zuoqb
 * @todo   测试类
 */
@ControllerBind(controllerKey = "/test", viewPath = "/test")
@Before({GlobalInterceptor.class})
public class TestController extends BaseController {
	//dubbo注入 预留口
	/*@Inject.BY_NAME
	private BlogService blogService;*/
	
    public void test() {
    	User user=User.dao.findById("7");
    	System.out.println(user);
    	setAttr("user", user);
        render("hello.html");
    }
    /**
     * 
     * @time   2017年4月11日 下午3:14:13
     * @author zuoqb
     * @todo   POST接口
     */
    @Before(POST.class)
    public void ajax() {
    	User user=User.dao.findById("7");
        renderJson(user);
    }
    public void index() {
        render("index.html");
    }
    public void flatMap() {
        render("flatMap.html");
    }
    /**
     * 
     * @time   2017年4月12日 下午2:22:17
     * @author zuoqb
     * @todo   迁徙图数据 目前是模拟：t_b_area经纬度
     * @param  
     * @return_type   void
     */
  /*  public void flatAjax(){
    	List<Record> parentList=Db.find("SELECT \"from_area\",a.\"short_name\" as shortname,a.\"lng\" ,a.\"lat\" from \"t_b_flat\" f LEFT JOIN \"t_b_dictionary\" a on a.\"id\"=f.\"from_area\" GROUP BY \"from_area\",a.\"short_name\",a.\"lng\" ,a.\"lat\" ");
    	for(Record r:parentList){
    		String sql="SELECT f.*,a.\"short_name\" as pshortname,a.\"lng\" as plng,a.\"lat\" as plat";
    		sql+=" ,b.\"short_name\" as shortname,b.\"lng\" ,b.\"lat\" from  ";
    		sql+=" \"t_b_flat\" f LEFT JOIN \"t_b_dictionary\" a on f.\"from_area\"=a.\"id\" LEFT JOIN \"t_b_dictionary\" b on f.\"to\"=b.\"id\" ";
    		sql+=" WHERE f.\"from_area\"='"+r.get("from_area")+"' ";
    		sql+="  order by b.\"order_no\" desc ";
    		List<Record> cList=Db.find(sql);
    		r.set("cList", cList);
    	}
    	renderJson(parentList);
    }*/
    /**
     * 
     * @time   2017年4月13日 上午9:38:56
     * @author zuoqb
     * @todo   全球实验室数据
     * @param  
     * @return_type   void
     */
    public void labAjax(){
    	List<Record> parentList=LabModel.dao.labShowWorldMap();
    	renderJson(parentList);
    }
}
