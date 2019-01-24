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
import com.hailian.util.StrUtils;
import com.hailian.util.encrypt.AES;
import com.jfinal.aop.Before;
import com.jfinal.kit.JsonKit;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
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
        //获取4个固定参数
        String companyID = getPara("companyID");
        String randomCode = getPara("randomCode");
        String timestamp = getPara("timestamp");
        String data = getPara("data");
        //解密参数
        if(StringUtils.isNotEmpty(companyID) && StringUtils.isNotEmpty(randomCode)
                && StringUtils.isNotEmpty(timestamp) && StringUtils.isNotEmpty(data)) {
            //密码生成
            String sKey = companyID+randomCode+timestamp;
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
                CreditOrderInfo orderInfo = new CreditOrderInfo();
                orderInfo.setCompanyId(companyID);
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
                orderInfo.setOnlineId(onlineId);
                //保存
                orderInfo.save();
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

}
