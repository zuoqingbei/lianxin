package com.hailian.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hailian.api.form.ApiForm;
import com.hailian.api.form.ApiResp;
import com.hailian.api.interceptor.ApiInterceptor;
import com.hailian.api.service.ApiService;
import com.hailian.api.util.ApiUtils;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.StrUtils;
import com.hailian.util.encrypt.AES;
import com.hailian.util.encrypt.URLCoder;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/api")
@Before(ApiInterceptor.class)
public class ApiController extends BaseProjectController {

	ApiService service = new ApiService();

	/**
	 * api测试入口
	 * 
	 * 2016年10月3日 下午5:47:55 flyfox 369191470@qq.com
	 */
	public void index() {
		ApiForm from = getForm();

		renderJson(new ApiResp(from).addData("notice", "api is ok!"));
	}

	/**
	 * 开关调试日志
	 * 
	 * 2016年10月3日 下午5:47:46 flyfox 369191470@qq.com
	 */
	public void debug() {
		ApiForm from = getForm();

		ApiUtils.DEBUG = !ApiUtils.DEBUG;
		renderJson(new ApiResp(from).addData("debug", ApiUtils.DEBUG));
	}

	/**
	 * 获取信息入口
	 * 
	 * 2016年10月3日 下午1:38:27 flyfox 369191470@qq.com
	 */
	public void action() {
		long start = System.currentTimeMillis();

		ApiForm from = getForm();
		if (StrUtils.isEmpty(from.getMethod())) {
			String method = getPara();
			from.setMethod(method);
		}

		// 调用接口方法
		ApiResp resp = service.action(from);
		// 没有数据输出空
		resp = resp == null ? new ApiResp(from) : resp;

		// 调试日志
		if (ApiUtils.DEBUG) {
			log.info("API DEBUG ACTION \n[from=" + from + "]" //
					+ "\n[resp=" + JsonKit.toJson(resp) + "]" //
					+ "\n[time=" + (System.currentTimeMillis() - start) + "ms]");
		}
		renderJson(resp);
	}

	public ApiForm getForm() {
		ApiForm form = getBean(ApiForm.class, null);
		return form;
	}

    //下单接口
    public void addOrder() {
        Map<String,String> result = new HashMap<String,String>();
        result.put("status","success");
        result.put("message","保存成功！");
        //获取参数
        String data = getPara("data");
        //解密参数
        if(StringUtils.isNotEmpty(data)) {
            try {
                //解密参数串
                String params = this.decodeData(data);
                //参数转对象
                JSONObject jsonObj = JSON.parseObject(params);
                String customId = jsonObj.getString("customId");
                String continent = jsonObj.getString("continent");
                String countryName = jsonObj.getString("countryName");
                String reportType = jsonObj.getString("reportType");
                String orderType = jsonObj.getString("orderType");
                String reportLanguage = jsonObj.getString("reportLanguage");
                String company = jsonObj.getString("company");
                String speed = jsonObj.getString("speed");
                String referenceNum = jsonObj.getString("referenceNum");
                String address = jsonObj.getString("address");
                String telphone = jsonObj.getString("telphone");
                String fax = jsonObj.getString("fax");
                String email = jsonObj.getString("email");
                String contacts = jsonObj.getString("contacts");
                String remarks = jsonObj.getString("remarks");
                String onlineId = jsonObj.getString("onlineId");
                if (StringUtils.isNotEmpty(customId) && StringUtils.isNotEmpty(continent)
                        && StringUtils.isNotEmpty(countryName) && StringUtils.isNotEmpty(reportType)
                        && StringUtils.isNotEmpty(orderType) && StringUtils.isNotEmpty(reportLanguage)
                        && StringUtils.isNotEmpty(company) && StringUtils.isNotEmpty(speed) && StringUtils.isNotEmpty(onlineId)) {
                    //订单编号
                    String num =CreditOrderInfo.dao.getNumber();
                    CreditOrderInfo orderInfo = new CreditOrderInfo();
                    orderInfo.set("num",num);
                    orderInfo.setCompanyId("");
                    orderInfo.setContinent(continent);
                    orderInfo.setCountry(countryName);
                    orderInfo.setReportType(reportType);
                    orderInfo.setOrderType(orderType);
                    orderInfo.setReportLanguage(reportLanguage);
                    orderInfo.setRight_company_name_en(company);
                    orderInfo.setSpeed(speed);
                    orderInfo.setReferenceNum(referenceNum);
                    orderInfo.setAddress(address);
                    orderInfo.setTelphone(telphone);
                    orderInfo.setFax(fax);
                    orderInfo.setEmail(email);
                    orderInfo.setContacts(contacts);
                    orderInfo.setRemarks(remarks);
                    orderInfo.setOnlineId(onlineId);
                    //保存
                    orderInfo.save();
                } else {
                    result.put("status", "false");
                    result.put("message", "有必传参数未传值！");
                }
            }catch (Exception e){
                e.printStackTrace();
                result.put("status","false");
                result.put("message","格式不正确！");
            }
        }else{
            result.put("status","false");
            result.put("message","缺少参数！");
        }
        renderJson(result);
    }


    //修改订单
    public void updOrder() {
        Map<String,String> result = new HashMap<String,String>();
        result.put("status","success");
        result.put("message","保存成功！");
        String data = getPara("data");
        //解密参数
        if(!StringUtils.isEmpty(data)) {
            result.put("status","false");
            result.put("message","缺少参数！");
        }else{
            //密码生成
            String sKey = this.getKey();
            //解密参数串
            String params = AES.decrypt(data,sKey);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String customId = jsonObj.getString("customId");
            String continent = jsonObj.getString("continent");
            String countryName = jsonObj.getString("countryName");
            String reportType = jsonObj.getString("reportType");
            String orderType = jsonObj.getString("orderType");
            String reportLanguage = jsonObj.getString("reportLanguage");
            String company = jsonObj.getString("company");
            String speed = jsonObj.getString("speed");
            String referenceNum = jsonObj.getString("referenceNum");
            String address = jsonObj.getString("address");
            String telphone = jsonObj.getString("telphone");
            String fax = jsonObj.getString("fax");
            String email = jsonObj.getString("email");
            String contacts = jsonObj.getString("contacts");
            String remarks = jsonObj.getString("remarks");
            String onlineId = jsonObj.getString("onlineId");
            if(StringUtils.isNotEmpty(customId)&&StringUtils.isNotEmpty(continent)
                    &&StringUtils.isNotEmpty(countryName)&&StringUtils.isNotEmpty(reportType)
                    &&StringUtils.isNotEmpty(orderType)&&StringUtils.isNotEmpty(reportLanguage)
                    &&StringUtils.isNotEmpty(company)&&StringUtils.isNotEmpty(speed)&&StringUtils.isNotEmpty(onlineId)){
                List<CreditOrderInfo> orderList = CreditOrderInfo.dao.findByWhere(" where online_id = ? ",onlineId);
                if(orderList.size()>0){
                    CreditOrderInfo order = orderList.get(0);
                    CreditOrderInfo orderInfo = new CreditOrderInfo();
                    orderInfo.setCompanyId("");
                    orderInfo.setContinent(continent);
                    orderInfo.setCountry(countryName);
                    orderInfo.setReportType(reportType);
                    orderInfo.setOrderType(orderType);
                    orderInfo.setReportLanguage(reportLanguage);
                    orderInfo.setcompanyName(company);
                    orderInfo.setSpeed(speed);
                    orderInfo.setReferenceNum(referenceNum);
                    orderInfo.setAddress(address);
                    orderInfo.setTelphone(telphone);
                    orderInfo.setFax(fax);
                    orderInfo.setEmail(email);
                    orderInfo.setContacts(contacts);
                    orderInfo.setRemarks(remarks);
                    orderInfo.setId(order.getInt("id"));
                    //修改
                    orderInfo.update();
                }
            }else{
                result.put("status","false");
                result.put("message","有必传参数未传值！");
            }
        }
        renderJson(result);
    }

    //修改订单
    public void delOrder() {
        Map<String,String> result = new HashMap<String,String>();
        //获取数据参数
        String data = getPara("data");
        //解密参数
        if(StringUtils.isEmpty(data)){
            result.put("status","false");
            result.put("message","缺少参数！");
        } else{
            //通过浏览器传来的json自动解码了，再做一次转码
            data = URLCoder.getURLEncoderString(data);
            //获取加密key
            String sKey = getKey();
            //解密参数串
            String params = AES.decrypt(data,sKey);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String onlineId = jsonObj.getString("onlineId");
            String reason = jsonObj.getString("reason");
            if(StringUtils.isNotEmpty(onlineId)){
                List<CreditOrderInfo> orderList = CreditOrderInfo.dao.findByWhere(" where online_id = ? ",onlineId);
                if(orderList.size()>0){
                    CreditOrderInfo order = orderList.get(0);
                    CreditOrderInfo orderInfo = new CreditOrderInfo();
                    orderInfo.setId(order.getInt("id"));
                    orderInfo.setRemarks(reason);
                    //删除
                    orderInfo.delete();
                    result.put("status","success");
                    result.put("message","保存成功！");
                }else{
                    result.put("status","false");
                    result.put("message","订单不存在！");
                }
            }else{
                result.put("status","false");
                result.put("message","有必传参数未传值！");
            }
        }
        renderJson(result);
    }

    //生成加密key
    public String getKey(){
        String companyID = getPara("companyID");
        String randomCode = getPara("randomCode");
        String timestamp = getPara("timestamp");
        //密码生成
        return companyID+timestamp+randomCode;
    }

    //解密参数
    public String decodeData(String data){
        data = URLCoder.getURLEncoderString(data);
        //密码生成
        String sKey = this.getKey();
        //解密参数串
        return AES.decrypt(data,sKey);
    }


    /**
	 * api对接线上新增客户同步
	 * @author dou_shuiahi
	 * @date: 2019年1月24日下午3:45:21
	 * @Description:
	 */
    public void addCustomer() {
        Map<String,String> result = new HashMap<String,String>();
        result.put("status","success");
        result.put("message","保存成功！");
        //获取参数
        String data = getPara("data");
       
        
        //解密参数
        if(StringUtils.isNotEmpty(data)) {
        	//解密参数串
            String params = this.decodeData(data);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String id = jsonObj.getString("id");//客户编码
            String name = jsonObj.getString("name");//客户名称
            String contacts = jsonObj.getString("contacts");//联系人全称
            String contactsShortName = jsonObj.getString("contactsShortName");//联系人简称
            String telphone = jsonObj.getString("telphone");//电话
            String email = jsonObj.getString("email");//邮箱
            String fax = jsonObj.getString("fax");//传真
            String country = jsonObj.getString("country");//国家
            String accountCount = jsonObj.getString("accountCount");//账户点数
            String isArrearage = jsonObj.getString("isArrearage");//是否可以欠费
            String isOldCustomer = jsonObj.getString("isOldCustomer");//是否为老用户
            String address = jsonObj.getString("address");
            String remarks = jsonObj.getString("remarks");
           
            if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(country)
                    &&StringUtils.isNotEmpty(accountCount)&&StringUtils.isNotEmpty(isArrearage)
                    &&StringUtils.isNotEmpty(isOldCustomer)&&StringUtils.isNotEmpty(email)){
            	CustomInfoModel model = new CustomInfoModel();
            	List<CustomInfoModel> customByid = CustomInfoModel.dao.getCustomByid(Integer.parseInt(id));//根据客户编码查找
            	if(customByid.size()>0) {
            		result.put("status","false");
                    result.put("message","已经存在该客户编码！");
                    renderJson(result);
                    return;
            	}
                model.set("id", id);
                model.set("name", name);
                model.set("contacts", contacts);
                model.set("contacts_short_name", contactsShortName);
                model.set("telphone", telphone);
                model.set("email", email);
                model.set("fax", fax);
                List<SysDictDetail> dictDetailBy=null;
                dictDetailBy = SysDictDetail.dao.getDictDetailBy(country,"country");
               
                if(CollectionUtils.isEmpty(dictDetailBy)){
                	model.set("country", dictDetailBy.get(0).get("detail_id"));
                }
                
                model.set("account_count", accountCount);
                if(isArrearage.equals("1")) {
                	 dictDetailBy = SysDictDetail.dao.getDictDetailBy("是","isArrearage");
                	  model.set("is_arrearage", dictDetailBy.get(0).get("detail_id"));
                }else if(isArrearage.equals("2")) {
                	 dictDetailBy = SysDictDetail.dao.getDictDetailBy("否","isArrearage");
                	  model.set("is_arrearage", dictDetailBy.get(0).get("detail_id"));
                }
                if(isOldCustomer.equals("1")) {
               	 dictDetailBy = SysDictDetail.dao.getDictDetailBy("是","is_old_customer");
               	  model.set("is_old_customer", dictDetailBy.get(0).get("detail_id"));
               }else if(isOldCustomer.equals("2")) {
               	 dictDetailBy = SysDictDetail.dao.getDictDetailBy("否","is_old_customer");
               	  model.set("is_old_customer", dictDetailBy.get(0).get("detail_id"));
               }
                model.set("address", address);
                model.set("remarks", remarks);
                //保存
                model.save();
                result.put("status","success");
                result.put("message","保存成功！");
            }else{
                result.put("status","false");
                result.put("message","有必传参数未传值！");
            }
        }else{
            result.put("status","false");
            result.put("message","缺少参数！");
        }
        renderJson(result);
    }
/**
 * api对接线上客户充值
 * @author dou_shuiahi
 * @date: 2019年1月25日下午3:33:27
 * @Description:
 */
	public void paySave(){
		Map<String,String> result = new HashMap<String,String>();
		result.put("status","success");
        result.put("message","保存成功！");
        //获取参数
        String data = getPara("data");
        if(StringUtils.isNotEmpty(data)) {
        	//解密参数串
            String params = this.decodeData(data);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String id = jsonObj.getString("userId");//客户编码
            String money = jsonObj.getString("money");//充值金额
            int moneyToInt =	Integer.parseInt(money); //充值金额
            String currency = getPara("currency");//充值币种
            String count = jsonObj.getString("units");//充值点数
            int countToInt =	Integer.parseInt(count); //充值点数
            String updateTime = jsonObj.getString("userId");//时间
            if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(money)
                    &&StringUtils.isNotEmpty(currency)&&StringUtils.isNotEmpty(count)){
            	 List<CustomInfoModel> customByid = CustomInfoModel.dao.getCustomByid(Integer.parseInt(id));//根据客户编码查找
                 if(CollectionUtils.isEmpty(customByid)) {
                 	result.put("status","false");
                     result.put("message","目标系统不存在该客户编码！");
                     renderJson(result);
                     return;
                 }
            	CustomInfoModel model = new CustomInfoModel();
            	int account_count=0;
            	int account_money=0;
            	if (null!=customByid.get(0).get("account_count")) {
         	 	    account_count=  Integer.parseInt(customByid.get(0).get("account_count").toString());//获取已有点数	
         		}
            	if (null!=customByid.get(0).get("money")) {
         	 	    account_money=  Integer.parseInt(customByid.get(0).get("money").toString());//获取已有点数	
         		}
            	model.set("table_id", customByid.get(0).get("table_id"));
            	model.set("account_count", countToInt+account_count);//点数
            	model.set("money", moneyToInt+account_money);//金额
         	    model.set("money_updatetime", getNow());
         	    model.update();
         	    //充值新增流水记录表，修改客户当前账户点数与金额更新时间
        		CustomTranFlowModel flowmodel=new CustomTranFlowModel();
        		flowmodel.remove("id");
        		flowmodel.set("custom_id", model.get("table_id"));
        		flowmodel.set("transaction_type", "充值");
        		flowmodel.set("money",money);
        		flowmodel.set("currency", currency);
        		flowmodel.set("oper_point_num", count);
        		flowmodel.set("oper_point_after_num", model.get("account_count"));
        		flowmodel.set("create_time", getNow());
        		flowmodel.save();
        		result.put("status","success");
                result.put("message","保存成功！");
            }else {
            	result.put("status","false");
                result.put("message","有必传参数未传值！");
            }
        }else {
        	 result.put("status","false");
             result.put("message","缺少参数！");
        }
        renderJson(result);
	}
	/**
	 * api对接线上客户扣款
	 * @author dou_shuiahi
	 * @date: 2019年1月25日下午3:34:23
	 * @Description:
	 */
	public void chargeSave(){
		Map<String,String> result = new HashMap<String,String>();
		
        //获取参数
        String data = getPara("data");
        if(StringUtils.isNotEmpty(data)) {
            //密码生成
        	//解密参数串
            String params = this.decodeData(data);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String id = jsonObj.getString("userId");//客户编码
            String count = jsonObj.getString("units");//扣款点数
            int countToInt =	Integer.parseInt(count); //扣款点数
            String money = jsonObj.getString("money");//充值金额
            int moneyToInt =	Integer.parseInt(money); //充值金额
            String updateTime = jsonObj.getString("userId");//时间
            if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(count) && StringUtils.isNotEmpty(money)){
            	 List<CustomInfoModel> customByid = CustomInfoModel.dao.getCustomByid(Integer.parseInt(id));//根据客户编码查找
                 if(CollectionUtils.isEmpty(customByid)) {
                 	 result.put("status","false");
                     result.put("message","目标系统不存在该客户编码！");
                     renderJson(result);
                     return;
                 }
            	CustomInfoModel model = new CustomInfoModel();
            	model.set("table_id", customByid.get(0).get("table_id"));
            	int account_count=0;
            	if (null!=customByid.get(0).get("account_count")) {
         	 	    account_count=  Integer.parseInt(customByid.get(0).get("account_count").toString());//获取已有點數
         		}
            	int surplus_count=account_count-countToInt;//剩余点数
            	if ("508".equals(customByid.get(0).get("is_arrearage"))&&surplus_count<0) {
            		result.put("status","false");
                    result.put("message","扣款失败，该客户不允许欠费");
                    renderJson(result);
                    return;
     			}
            	model.set("account_count", surplus_count);
            	int account_money=0;
            	if (null!=customByid.get(0).get("money")) {
         	 	    account_money=  Integer.parseInt(customByid.get(0).get("money").toString());//获取已有金额	
         		}
            	int surplus_money=account_money-moneyToInt;//剩余金额
            	if ("508".equals(customByid.get(0).get("is_arrearage"))&&surplus_money<0) {
            		result.put("status","false");
                    result.put("message","扣款失败，该客户不允许欠费");
                    renderJson(result);
                    return;
     			}
            	model.set("money", surplus_money);
         	    model.set("money_updatetime", getNow());
         	    model.update();
         	    //流水记录表
        		CustomTranFlowModel flowmodel=new CustomTranFlowModel();
        		flowmodel.remove("id");
        		flowmodel.set("custom_id", model.get("table_id"));
        		flowmodel.set("transaction_type", "扣款");
        		flowmodel.set("money",money);
        		flowmodel.set("oper_point_num", countToInt);
        		flowmodel.set("oper_point_after_num",surplus_count);
        		flowmodel.set("create_time", getNow());
        		flowmodel.save();
        		result.put("status","success");
                result.put("message","扣款成功！");
            }else {
            	result.put("status","false");
                result.put("message","有必传参数未传值！");
            }
        }else {
        	 result.put("status","false");
             result.put("message","缺少参数！");
        }
        renderJson(result);
	}
}
