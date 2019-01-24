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
    public void addCustomer1() {
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
	 * 
	* @Description: 充值记录
	* @date 2018年10月18日 上午9:37:41
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void pay(){
		Integer paraToInt = getParaToInt();
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
		List<SysDictDetail>  details=SysDictDetail.dao.getDictByType("currency");
		setAttr("model", model);
		setAttr("dict", details);
		// 查询下拉框
//		render(path + "pay_add.html");
	}
	/**
	 * 
	* @Description: 支付保存
	* @date 2018年10月19日 下午2:02:42
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void paySave(){
		Integer paraToInt = Integer.parseInt(getPara("table_id"));
		CustomInfoModel Infomodel = CustomInfoModel.dao.findById(paraToInt);
		CustomInfoModel model =getModel(CustomInfoModel.class);
	    int count =	Integer.parseInt(getPara("count")); //充值点数
	    String money =  getPara("money"); //充值金额 
	    String currency = getPara("currency");//充值币种
	    String remarks = getPara("remarks");
	    String now = getNow();
	    Integer userid = getSessionUser().getUserid();
	    int account_count=0;
	    if (Infomodel.get("account_count")!=null) {
	 	    account_count=  Integer.parseInt(Infomodel.get("account_count").toString());	
		}
	    model.set("account_count", count+account_count);
	    model.set("money_updatetime", now);
	    model.set("update_by", userid);
	    model.update();
		//充值新增流水记录表，修改客户当前账户点数与金额更新时间
		CustomTranFlowModel model2=new CustomTranFlowModel();
		  model2.remove("id");
		  model2.set("custom_id", model.get("table_id"));
		  model2.set("transaction_type", "充值");
		  model2.set("money",money);
		  model2.set("currency", currency);
		  model2.set("oper_point_num", getPara("count"));
		  model2.set("oper_point_after_num", count+account_count);
		  model2.set("remark", remarks);
		  model2.set("create_time", now);
		  model2.set("create_by", userid);
		  model2.save();
		renderMessage("充值成功");
	}
	
	/**
	 * 
	* @Description: 扣款记录
	* @date 2018年10月18日 上午9:39:10
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void charge(){
		Integer paraToInt = getParaToInt();
		CustomInfoModel model = CustomInfoModel.dao.findById(paraToInt);
		setAttr("model", model);
		List<CompanyModel> company = CompanyModel.dao.getCompany(null);
		setAttr("company", company);
		// 查询下拉框
//		render(path + "charge_add.html");
	}
	/**
	 * 
	* @Description: 扣款保存
	* @date 2018年10月19日 下午2:02:22
	* @author: lxy
	* @version V1.0
	* @return
	 */
	public void chargeSave(){
			Integer paraToInt = Integer.parseInt(getPara("table_id"));
			CustomInfoModel model=getModel(CustomInfoModel.class);
			CustomInfoModel infoModel = CustomInfoModel.dao.findById(paraToInt);
			int count =	Integer.parseInt(getPara("count")); //充值点数
		    String remarks = getPara("remarks");
		    String now = getNow();
		    Integer userid = getSessionUser().getUserid();
		   //修改当前账户点数
		    int account_count=0;
		    if (infoModel.get("account_count").toString()!=null) {
		 	    account_count=  Integer.parseInt(infoModel.get("account_count").toString());	
			}
		    
		   if ("508".equals(infoModel.get("is_arrearage"))&&account_count-count<0) {
			   renderMessage("扣款失败，该客户不允许欠费");		
			}else {
		    model.set("account_count", account_count-count);
		    model.set("money_updatetime", now);
		    model.set("update_by", userid);
		    model.update();
		  //交易流水  
		  CustomTranFlowModel model2=new CustomTranFlowModel();
		  model2.remove("id");
		  model2.set("custom_id", model.get("table_id"));
		  model2.set("transaction_type", "扣款");
		  model2.set("oper_point_num", getPara("count"));
		  model2.set("oper_point_after_num",account_count-count);
		  model2.set("remark", remarks);
		  model2.set("create_time", now);
		  model2.set("create_by", userid);
		  model2.save();
		  renderMessage("扣款成功");
			}
	}
}
