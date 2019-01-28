package com.online2lianxin.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.custom.model.CustomInfoModel;
import com.hailian.modules.credit.custom.model.CustomTranFlowModel;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.encrypt.AES;
@ControllerBind(controllerKey = "/api/zz")
public class CustomController extends BaseProjectController {
	/**
	 * api对接线上新增客户同步
	 * @author dou_shuiahi
	 * @date: 2019年1月24日下午3:45:21
	 * @Description:
	 */
    public void addCustomer() {
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
        //获取4个固定参数
        String companyID = getPara("companyID");
        String randomCode = getPara("randomCode");
        String timestamp = getPara("timestamp");
        String data = getPara("data");
        if(StringUtils.isNotEmpty(companyID) && StringUtils.isNotEmpty(randomCode)
                && StringUtils.isNotEmpty(timestamp) && StringUtils.isNotEmpty(data)) {
            //密码生成
            String sKey = companyID+randomCode+timestamp;
            //解密参数串
            String params = AES.decrypt(data,sKey);
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
        //获取4个固定参数
        String companyID = getPara("companyID");
        String randomCode = getPara("randomCode");
        String timestamp = getPara("timestamp");
        String data = getPara("data");
        if(StringUtils.isNotEmpty(companyID) && StringUtils.isNotEmpty(randomCode)
                && StringUtils.isNotEmpty(timestamp) && StringUtils.isNotEmpty(data)) {
            //密码生成
            String sKey = companyID+randomCode+timestamp;
            //解密参数串
            String params = AES.decrypt(data,sKey);
            //参数转对象
            JSONObject jsonObj = JSON.parseObject(params);
            String id = jsonObj.getString("userId");//客户编码
            String count = jsonObj.getString("units");//扣款点数
            int countToInt =	Integer.parseInt(count); //扣款点数
            String money = jsonObj.getString("money");//充值金额
            int moneyToInt =	Integer.parseInt(money); //充值金额
            String updateTime = jsonObj.getString("userId");//时间
            if(StringUtils.isNotEmpty(id)&&StringUtils.isNotEmpty(count)){
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
