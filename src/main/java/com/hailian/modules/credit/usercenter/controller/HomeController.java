

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
import com.hailian.modules.admin.file.model.CreditUploadFileModel;
import com.hailian.modules.admin.file.service.UploadFileService;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderHistory;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.admin.ordermanager.service.OrderManagerService;
import com.hailian.modules.credit.common.model.CountryModel;
import com.hailian.modules.credit.usercenter.model.ResultType;
import com.hailian.modules.credit.usercenter.service.HomeService;
import com.hailian.modules.credit.utils.FileTypeUtils;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.system.user.SysUser;
import com.hailian.util.cache.Cache;
import com.hailian.util.Config;
import com.hailian.util.DateUtils;
import com.hailian.util.FtpUploadFileUtils;
import com.hailian.util.getOrderNum;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

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
	public static final int maxPostSize=Config.getToInt("ftp_maxPostSize");//上传文件最大容量
	public static final String ip = Config.getStr("ftp_ip");//ftp文件服务器 ip
	public static final int port = Config.getToInt("ftp_port");//ftp端口 默认21
	public static final String userName = Config.getStr("ftp_userName");//域用户名
	public static final String password = Config.getStr("ftp_password");//域用户密码
	public void index() {
		render(path + "index.html");
		
	}
	/**
	 * 
	 * @time   2018年9月17日 上午11:54:31
	 * @author yangdong
	 * @todo   TODO 获取详情页
	 * @param  
	 * @return_type   void
	 */
	public void orderInfo() {
		//获取订单id
		int id=getParaToInt("id");
		//根据订单id获取订单信息
		CreditOrderInfo order=OrderManagerService.service.editOrder(id,this);
		//根据订单信息获取公司信息
		CreditCompanyInfo company=OrderManagerService.service.getCompany(order.getInt("company_id"));
		//根据订单id获取历史记录信息
		List<CreditOrderHistory> histroy=HomeService.service.getHistroy(String.valueOf(id));
		//获取客户信息
		CreditCustomInfo custom=OrderManagerService.service.getCreater(order.getStr("custom_id"));
		//获取地区
		SysDictDetail continent=HomeService.service.getContinent(order.getStr("continent"));
		//绑定订单信息和公司信息
		setAttr("order",order);
		setAttr("company",company);
		setAttr("histroy",histroy);
		setAttr("custom",custom);
		//转发页面
		render(path+"order_detail.html");
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
		String uri = this.getRequest().getRequestURI();
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String sortname=getPara("sortName");
		String sortorder=getPara("sortOrder");
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
		Page<CreditOrderInfo> page=OrderManagerService.service.getOrdersService(pageinator,model,status, user,sortname,sortorder);
//		List<CreditOrderInfo> result=OrderManagerService.service.getOrdersService(status,model, user);
		int total= page.getTotalRow();
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
		List<CountryModel> country=OrderManagerService.service.getCountrys("");
		List<CreditCustomInfo> customs=OrderManagerService.service.getCreater();
		List<CreditCompanyInfo> companys=OrderManagerService.service.getCompany();
		Record record=new Record();
		record.set("user", user);
		record.set("country", country);
		record.set("customs", customs);
		record.set("companys", companys);
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
	 * @todo   TODO 保存订单
	 * @param  
	 * @throws Exception 
	 * @return_type   void
	 */
	public void saveOrder() throws Exception {
		List<UploadFile>  upFileList = getFiles("Files");//从前台获取文件
		CreditOrderInfo model = getModelByAttr(CreditOrderInfo.class);
		String num=new getOrderNum().getNumber();
		model.set("num", num);
		SysUser user = (SysUser) getSessionUser();
		CreditUploadFileModel model1= new CreditUploadFileModel();
		model1.set("business_type", "0");
		model1.set("business_id", num);
		int num1=0;
		String message="";
		int size=upFileList.size();
		if(size >0){
			try {
				for(UploadFile uploadFile:upFileList){
					String originalFile=uploadFile.getOriginalFileName();
					int dot = originalFile.lastIndexOf(".");
					String ext="";
					String originalFileName=FileTypeUtils.getName(originalFile);
					ext=FileTypeUtils.getFileType(originalFile);
					if (uploadFile != null && uploadFile.getFile().length()<=maxPostSize && FileTypeUtils.checkType(ext)) {
						String storePath = "zhengxin_File/"+DateUtils.getNow(DateUtils.YMD);//上传的文件在ftp服务器按日期分目录
						String now=DateUtils.getNow(DateUtils.YMDHMS);
						String FTPfileName=originalFileName+now+"."+ext;
						String fileName=originalFileName+now;
						boolean storeFile = FtpUploadFileUtils.storeFile(FTPfileName, uploadFile.getFile(),storePath,ip,port,userName,password);//上传
						if(storeFile){
							String factpath=storePath+"/"+FTPfileName;
							String url="http://"+ip+"/" + storePath+"/"+FTPfileName;
							Integer userid = getSessionUser().getUserid();
							model1.set("business_id", num);
							UploadFileService.service.save(0,uploadFile, factpath,url,model1,fileName,userid);//记录上传信息
						}else{
							num1+=1;
							message+=uploadFile.getOriginalFileName()+"上传失败!";
							renderMessage(message);
							render(path + "index.html");
							return;
						}
					}else{
						num1+=1;
						message+=uploadFile.getOriginalFileName()+"上传失败!";
						renderMessage(message);
						render(path + "index.html");
						return;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				renderMessage(message);
				render(path + "index.html");
				return;
			}
		}
		try {
			OrderManagerService.service.modifyOrder(0,model,user,this);
			render(path + "index.html");
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			renderMessage("保存失败");
			render(path + "index.html");
		}		
	}
	
	/**
	 * 
	 * @time   2018年9月17日 上午9:58:23
	 * @author yangdong
	 * @todo   TODO  根据客户id获取客户姓名
	 * @param  
	 * @return_type   void
	 */
	public void getCustomName() {
		String id=getPara("id");
		CreditCustomInfo custom=OrderManagerService.service.getCreater(id);
		renderJson(custom);
	}

}

