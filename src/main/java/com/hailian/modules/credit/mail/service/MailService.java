package com.hailian.modules.credit.mail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.hailian.component.base.BaseProjectController;
import com.hailian.jfinal.base.Paginator;
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.agentmanager.model.AgentModel;
import com.hailian.modules.credit.mail.model.MailLogModel;
import com.hailian.modules.credit.mail.model.MailModel;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.util.Config;
import com.jfinal.plugin.activerecord.Page;

public class MailService {
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static MailService service=new MailService();
	/**
	 * 列表展示
	* @author doushuihai  
	 * @param report_id2 
	* @date 2018年9月3日下午2:31:12  
	* @TODO
	 */
	public Page<MailModel> getPage(Paginator paginator,  String orderBy,String keyword,BaseProjectController c){
		Page<MailModel> page = MailModel.dao.getPage(paginator,orderBy,keyword,c);
		return page;
	}
	/**
	 * 逻辑删除
	* @author doushuihai  
	* @date 2018年9月7日下午5:20:30  
	* @TODO
	 */
	public int delete(Integer id, Integer userid){
		int delete = MailModel.dao.delete(id,userid);
		return delete;
	}
	public void toEnabled(Integer id, Integer userid){
		MailModel.dao.toEnabled(id, userid);
	}
	public List<MailModel> getCustom(Integer id){
		List<MailModel> mail = MailModel.dao.getMail(id);
		return mail;
	}
	public MailModel getCustomById(Integer id){
		MailModel mail = MailModel.dao.getMailById(id);
		return mail;
	}
	  /*
     * 代理分配发送邮件
     */
    public void toSendMail(String ismail, String orderId,String agentId,Integer userid,BaseProjectController c) throws Exception {
        if("1".equals(ismail)){
            CreditOrderInfo order = OrderManagerService.service.getOrder(orderId, c);
            AgentModel agent=	AgentModel.dao.findById(agentId);
            String mailaddr=agent.get("memo");
            if(StringUtils.isNotBlank(mailaddr)){
                String title="New Order";
                String content="Dear Sir/Madam,Good day!"
                        +"We would like to place an order for a complete credit report on the following company:"
                        +"Speed:" +order.get("reportSpeed")+" "
                        +"Ref No.:"+order.get("reference_num")+" "
                        +"Company name:"+order.get("right_company_name_en")+" "
                        +"Address:"+order.get("address")+" "
                        +"Country:"+order.get("countryname")+" "
                        +"Tel:"+order.get("telphone")+" "
                        +"Fax:"+order.get("fax")+" "
                        +"E-mail:"+order.get("email")+" "
                        +"Special Note:"+order.get("remarks")+" "
                        +"Please confirm receiving this order."
                        +"Thank you.";
                //获取创建该订单时的附件
            	List<CreditUploadFileModel> files = CreditUploadFileModel.dao.getByBusIdAndBusinessType(orderId ,"291", c);
            	//获取项目服务器url
			 	List<Map<String,String>> list=new ArrayList<Map<String,String>>();
			 	if(files != null && CollectionUtils.isNotEmpty(files)){
			 		for (CreditUploadFileModel creditUploadFileModel : files) {
			        	Map<String,String> map =new HashMap<String, String>();
			            String url="http://"+ ip + ":" + port+"/"+creditUploadFileModel.get("url");
			            String filename=creditUploadFileModel.get("originalname")+"."+creditUploadFileModel.get("ext");
			            map.put(filename, url);
			            list.add(map);
			        }
			 	}
		        
                boolean sendMail = new SendMailUtil(mailaddr, "", title, content, list).sendEmail();
                String send_result="";
                if(sendMail){
                	send_result="278";
                }else{
                	send_result="277";
                }
                MailLogModel.dao.save(userid, mailaddr, "", "3", send_result);//邮件日志记录
            }
        }
    }
	
}
