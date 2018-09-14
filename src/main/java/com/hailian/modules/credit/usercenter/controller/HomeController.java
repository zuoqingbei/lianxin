

package com.hailian.modules.credit.usercenter.controller;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @className HomeController.java
 * @time   2018年9月5日 下午2:00:19
 * @author yangdong
 * @todo   用户控制台
 */


@Api( tag = "用户控制台", description = "用户控制台" )
@ControllerBind(controllerKey = "/credit/front/home")
public class HomeController extends BaseProjectController {
	private static final String path = "/pages/credit/usercenter/";
	public void index() {
		render(path + "index.html");
		
	}
	/**
	 * 
	 * @time   2018年9月7日 下午3:54:46
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @throws ParseException 
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/list",httpMethod="post", 
			description = "查看订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = false, dataType = "String"),
		})
	public void list(){
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		/*SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
		String date=getPara("end_date","");
		Date end_date=null;
		if(StringUtils.isNotBlank(date)) {
			try {
				end_date=sdf.parse(date);
				model.set("end_date", end_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		SysUser user= (SysUser) getSessionUser();
		String status =getPara("status");
		if(StringUtils.isNotBlank(status)) {
			status=status.substring(0, status.length()-1);
		}
		Paginator pageinator=getPaginator();
		String orderBy = getBaseForm().getOrderBy();
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,orderBy,status, user);
		int total=page.getList().size();
		List<CreditOrderInfo> rows=page.getList();
		for(int i=0;i<rows.size();i++) {
			
			if("0".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "提交订单");
			}
			if("1".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "订单已分配");
			}
			if("2".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "处理中");
			}
			if("3".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "按照流程处理完毕");
			}
			if("4".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "订单取消");
			}
			if("5".equals(rows.get(i).getStr("status"))) {
				rows.get(i).set("status", "有相同报告，订单结束");
			}
		}
		ResultType resultType=new ResultType(total,rows);
		renderJson(resultType);
	}
	/**
	 * 
	 * @time   2018年9月10日 上午9:56:03
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/getMessage",httpMethod="post", 
			description = "获取信息")
	public void getMessage() {
		
		SysUser user= (SysUser) getSessionUser();
		List<SysDictDetail> continent=OrderManagerService.service.getDictByType("sandbar");
		List<CountryModel> country=OrderManagerService.service.getCountrys("");
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		Record record=new Record();
		record.set("user", user);
		record.set("continent", continent);
		record.set("country", country);
		record.set("customs", customs);
		renderJson(record);
	}
	@ApiOperation(url = "/credit/front/home/getCountry",httpMethod="post", 
			description = "获取信息")
	@Params(value = { 
			@Param(name = "id", description = "地区id", required = true, dataType = "String"),
			})
	public void getCountry() {
		String contient=getPara("contient");
		List<CountryModel> country=OrderManagerService.service.getCountrys(contient);
		renderJson(country);
	}
	/**
	 * 
	 * @time   2018年9月5日 下午5:25:42
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	@ApiOperation(url = "/credit/front/home/view",httpMethod="post", 
			description = "查看订单")
	@Params(value = { 
		@Param(name = "id", description = "订单id", required = true, dataType = "String"),
		})
	public void view() {
		int id=getParaToInt("id");
		CreditOrderInfo model=OrderManagerService.service.editOrder(id,this);
		setAttr("model", model);
		render(path + "view.html");
	}
	/**
	 * 创建订单
	 * @time   2018年9月11日 上午10:18:10
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	public void createOrder() {
		CreditOrderInfo model=new CreditOrderInfo();
		SysUser user = (SysUser) getSessionUser();
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CreditCompanyInfo> company=OrderManagerService.service.getCompany();
		setAttr("user",user);
		setAttr("customs",customs);
		setAttr("company",company);
		setAttr("model", model);
		render(path + "create_order.html");
	}
	/**
	 * 
	 * @time   2018年9月12日 下午5:33:20
	 * @author yangdong
	 * @todo   TODO
	 * @param  
	 * @return_type   void
	 */
	public void saveOrder() {
		int id=getParaToInt("id");
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		SysUser user = (SysUser) getSessionUser();
		try {
			OrderManagerService.service.modifyOrder(id,model,user,this);
			OrderManagerService.service.addOrderHistory(id, user);
			render(path + "index.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderMessage("保存失败");
		}
	}

	public void getCustomName() {
		String id=getPara("id");
		CreditCustomInfo custom=OrderManagerService.service.getCreater(id);
		renderJson(custom);
	}

}

