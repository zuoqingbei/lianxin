package com.hailian.modules.credit.company.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hailian.modules.admin.ordermanager.model.*;
import com.hailian.util.http.showapi.pachongproxy.IHttpTest;
import com.hailian.util.http.showapi.pachongproxy.ProxyHandler;
import com.jfinal.plugin.activerecord.Model;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hailian.api.constant.ReportTypeCons;
import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.companychangeitem.model.ChangeitemModel;
import com.hailian.modules.credit.companychangeitem.service.ChangeitemService;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.StrUtils;
import com.hailian.util.http.HttpCrawler;
import com.hailian.util.http.HttpTest;
import com.hailian.util.translate.TransApi;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
* @author dyc:
* @time 2018年9月10日 上午11:15:53
* @todo  企业信息处理业务
*/
public class CompanyService {
	public static CompanyService service = new CompanyService();
	HttpTest httpTest = new HttpTest();
	ProxyHandler proxy = new ProxyHandler();
	IHttpTest iHttpTest = ((IHttpTest)proxy.newProxyInstance(httpTest));
	/**
	 * 
	 * @time   2018年9月10日 下午5:32:12
	 * @author dyc
	 * @todo   删除单条企业信息
	 * @return_type   boolean
	 */
	public boolean updateDelFlagById(Integer id) {

		return CompanyModel.dao.updateDelFlagById(id);
	}
	/**
	 * 
	 * @time   2018年9月11日 上午9:58:27
	 * @author dyc
	 * @todo   分页查询企业信息
	 * @return_type   Page<CompanyModel>
	 */
	public Page<CompanyModel> pageCompany(int pageNumber, int pageSize, String orderBy, String companyName,
			String companyNameEn, String registrationNum, String creditCode,  BaseProjectController c) {
		return CompanyModel.dao.pageCompany(pageNumber, pageSize, orderBy, companyName, companyNameEn, registrationNum,
				creditCode,  c);

	}

	/**
	 * 
	 * @time   2018年9月11日 上午10:45:02
	 * @author dyc
	 * @todo   单条查看企业信息
	 * @return_type   CompanyModel
	 */

	public CompanyModel getOne(int id, BaseProjectController c) {
		CompanyModel companyModel = CompanyModel.dao.getOne(id, c);
		return companyModel;
	}
	/**
	 * 调用第三方接口获取企业信息（json）解析并存放在企业相关信息表
	* @author doushuihai  
	 * @return 
	* @date 2018年11月8日上午10:45:37  
	* @TODO
	 */
	public boolean enterpriseGrab(String companyId,String companyName,String sys_language,String reporttype){
		boolean flag=false;
		String status;
		try {
			JSONObject json = iHttpTest.getYjapi(companyName.trim());//获取api企业信息数据
			JSONArray branches = null;
			status = json.getString("Status");
			System.out.println(status);
			//200调用成功并查到企业相关信息
			if("200".equals(status)){
				flag=true;
				JSONObject jsonResulet = json.getJSONObject("Result");
                //删除原有的分支机构
                CreditCompanyBranchestwo.dao.deleteBycomIdAndLanguage(companyId,sys_language);
				branches = jsonResulet.getJSONArray("Branches");

				for (Object object : jsonResulet.keySet()) {
					System.err.println(object+":"+jsonResulet.get(object));
				}
				//企业基本信息
				String No = jsonResulet.getString("No"); //注册号
				String CreditCode = jsonResulet.getString("CreditCode").replace("null", ""); //统一信用社信用代码
				System.out.println(CreditCode);
				String EconKind = jsonResulet.getString("EconKind").replace("null", ""); //企业类型
				String OperName = jsonResulet.getString("OperName").replace("null", ""); //法人名
				String RegistCapi = jsonResulet.getString("RegistCapi").replace("null", ""); //注册资本
				String StartDate = jsonResulet.getString("StartDate"); //成立日期
				String TermStart = jsonResulet.getString("TermStart"); //营业期限自
				String TeamEnd = jsonResulet.getString("TeamEnd"); //营业期限至
				String CheckDate = jsonResulet.getString("CheckDate"); //发照日期
				String BelongOrg = jsonResulet.getString("BelongOrg").replace("null", ""); //登记机关
				String Address = jsonResulet.getString("Address").replace("null", ""); //地址
				String Province = jsonResulet.getString("Province"); //所在省
				String Scope = jsonResulet.getString("Scope").replace("null", ""); //经营范围
				String Status = jsonResulet.getString("Status"); //登记状态
				//String englishName = jsonResulet.getString("englishname"); //英文名称
				CreditCompanyInfo companyinfoModel=new CreditCompanyInfo();
				companyinfoModel.set("registration_num", No);
				//companyinfoModel.set("naem_en", englishName);
				String typeFlagStr = "";
				List<SysDictDetail> companytype = new ArrayList<>();
				if(ReportTypeCons.isRoc102(reporttype)) {
					typeFlagStr = "companyType102";
				}else {
					typeFlagStr = "companyType"; 
				}
				if("companyType".equals(typeFlagStr)){
					String sql="select * from sys_dict_detail where del_flag=0 and dict_type='companyType'";
					List<SysDictDetail> companyTypeList =  SysDictDetail.dao.find(sql);
					if(StringUtils.isNotEmpty(EconKind)){
						for (SysDictDetail  temp: companyTypeList) {
							if(temp==null||StringUtils.isEmpty(temp.getStr("detail_name"))){
								continue;
							}
							String tempStr =   temp.getStr("detail_name").replace("(","").replace("（","").replace(")","").replace("）","").trim().replaceAll("\\s*","")	;

							String tempFlagStr = EconKind		 			   .replace("(","").replace("（","").replace(")","").replace("）","").trim().replaceAll("\\s*","")	;
							if(tempFlagStr.equals(tempStr)){
								companytype.add(temp);
								break;
							}
						}
					}

				}else{
					companytype = SysDictDetail.dao.getDictDetailBy(EconKind.trim(),typeFlagStr);
				}
				if(companytype !=null && CollectionUtils.isNotEmpty(companytype)){
					companyinfoModel.set("company_type", companytype.get(0).get("detail_id"));
				}else{
					if(!StrUtils.isEmpty(EconKind)) {
						SysDictDetail detailmodel=new SysDictDetail();
						detailmodel.set("dict_type", typeFlagStr);
						detailmodel.set("detail_name", EconKind.trim());
						String value_en = TransApi.Trans(EconKind.trim(),"en");
						detailmodel.set("detail_name_en", value_en);
						detailmodel.set("isprimitive", "1");
						detailmodel.save();
						companyinfoModel.set("company_type", detailmodel.get("detail_id"));
					}else {
						companyinfoModel.set("company_type","-1");
					}
				}
				companyinfoModel.set("register_code_type", "632");
				companyinfoModel.set("register_codes", CreditCode);
				companyinfoModel.set("legal", OperName);
				//法人代表类型或者说叫负责人类型
				//638	法人代表
				//639	执行事务合伙人
				//640	负责人
				Integer principalType = 638; //默认法人代表
				if(EconKind.contains("个体户企业")||EconKind.contains("个人独资企业")||EconKind.contains("代表处")||EconKind.contains("外商办事处")||EconKind.contains("分公司")){
					principalType = 640;
				}else if(EconKind.contains("有限合伙企业")){
					principalType = 639;
				}
				companyinfoModel.set("principal_type", principalType);
				//对于爬取得来的资本进行处理
				dealCurrency(RegistCapi, companyinfoModel ,"registered_capital","currency");
				/*if(RegistCapi.indexOf("万元人民币") !=-1){
					String replace = RegistCapi.replace("万元人民币", "");
					BigDecimal a = new BigDecimal(replace);
					BigDecimal b = new BigDecimal("10000");
					companyinfoModel.set("registered_capital", a.multiply(b).toString());
					companyinfoModel.set("currency","274");
				}
				if(RegistCapi.indexOf("万美元") !=-1){
					String replace = RegistCapi.replace("万美元", "");
					BigDecimal a = new BigDecimal(replace);
					BigDecimal b = new BigDecimal("10000");
					companyinfoModel.set("registered_capital", a.multiply(b).toString());
					companyinfoModel.set("currency","267");
				}
				if(RegistCapi.indexOf("万港元") !=-1){
					String replace = RegistCapi.replace("万港元", "");
					BigDecimal a = new BigDecimal(replace);
					BigDecimal b = new BigDecimal("10000");
					companyinfoModel.set("registered_capital", a.multiply(b).toString());
					companyinfoModel.set("currency","266");
				}*/
				String StartDateFor = dateFormat(StartDate);//转成年月日
				companyinfoModel.set("establishment_date", StartDateFor);
				String CheckDateFor = dateFormat(CheckDate);//转成年月日
				companyinfoModel.set("last_modified_date", CheckDateFor);
				String TermStartFor = dateFormat(TermStart);//转成年月日
				companyinfoModel.set("business_date_start", TermStartFor);
				if(StringUtils.isNotBlank(TeamEnd)){
					String TeamEndFor = dateFormat(TeamEnd);//转成年月日
					companyinfoModel.set("business_date_end", TermStartFor+"至"+TeamEndFor);
				}else{
					companyinfoModel.set("business_date_end","长期");
				}
				
				List<SysDictDetail> dictDetailBy = SysDictDetail.dao.getDictDetailBy(Status.trim(),"registration_status");
				if(CollectionUtils.isNotEmpty(dictDetailBy) && dictDetailBy!=null){
					companyinfoModel.set("registration_status", dictDetailBy.get(0).get("detail_id"));
				}else{
					SysDictDetail detailmodel=new SysDictDetail();
					detailmodel.set("dict_type", "registration_status");
					detailmodel.set("detail_name", Status.trim());
					String value_en = TransApi.Trans(Status.trim(),"en");
					detailmodel.set("detail_name_en", value_en);
					detailmodel.set("isprimitive", "1");
					detailmodel.save();
					companyinfoModel.set("registration_status", detailmodel.get("detail_id"));
				}
				companyinfoModel.set("registration_authority", BelongOrg);
				companyinfoModel.set("address", Address);
				companyinfoModel.set("province", Province);
				companyinfoModel.set("operation_scope", Scope);
				//业务范围
				try {
					JSONObject bus = json.getJSONObject("Result").getJSONObject("Industry");//业务范围
					String Industry = bus.getString("Industry"); 
					companyinfoModel.set("business_scope", Industry);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				companyinfoModel.set("id", companyId);
				companyinfoModel.set("sys_language", sys_language);
				companyinfoModel.set("name", companyName);

				try {
					JSONObject ContactInfo = json.getJSONObject("Result").getJSONObject("ContactInfo");//联系信息
					String PhoneNumber = ContactInfo.getString("PhoneNumber").replace("null", "");//电话
					String Email = ContactInfo.getString("Email").replace("null", "");//邮箱
					companyinfoModel.set("telphone", PhoneNumber);
					companyinfoModel.set("email", Email);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String executive_director="";//执行董事
				String chairman="";//董事长
				String vice_president="";//副董事长
				String board_members="";//董事成员
				String supervisory_board_chairman="";//监事主席
				String members_of_the_supervisors="";//监事成员
				String general_manager="";//总经理
				String vice_general_manager="";//副总经理
				//管理层
				JSONArray Employees = null;
				try {
                    CreditCompanyManagement.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录
					Employees = json.getJSONObject("Result").getJSONArray("Employees");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<CreditCompanyManagement> managementlist=new ArrayList<CreditCompanyManagement>();
				if(Employees !=null && Employees.size()>0){
					for(int i=0;i<Employees.size();i++){
						JSONObject employee = (JSONObject)Employees.get(i);
						//CreditCompanyManagement
						String name = employee.getString("Name");//管理层姓名
						String job = employee.getString("Job");//职位
						if("12".equals(reporttype) || "14".equals(reporttype) || "15".equals(reporttype)){//当报告类型为102时获取主表管理层信息
							if("董事长".equals(job)){
								chairman+=name+";";
							}else if("执行董事".equals(job)){
								executive_director+=name+";";
							}else if ("副董事长".equals(job)) {
								vice_president+=name+";";
							}else if ("董事".equals(job)) {
								board_members+=name+";";
							}else if ("监事主席".equals(job)) {
								supervisory_board_chairman+=name+";";
							}else if ("监事".equals(job)) {
								members_of_the_supervisors+=name+";";
							}else if ("总经理".equals(job)) {
								general_manager+=name+";";
							}else if ("副总经理".equals(job)) {
								vice_general_manager+=name+";";
							}
						
							
						}
			
						
						CreditCompanyManagement managementModel = new CreditCompanyManagement();

						if(!StrUtils.isEmpty(job)){//判断如果job为空或者null则不添加职务信息
							List<SysDictDetail> dictDetailBy2 = SysDictDetail.dao.getDictDetailBy(job.trim(),"position");
							if(dictDetailBy2 !=null && CollectionUtils.isNotEmpty(dictDetailBy2)){
								managementModel.set("position", dictDetailBy2.get(0).get("detail_id"));
							}else{
								SysDictDetail detailmodel=new SysDictDetail();
								detailmodel.set("dict_type", "position");
								detailmodel.set("detail_name", job.trim());
								String value_en = TransApi.Trans(job.trim(),"en");
								detailmodel.set("detail_name_en", value_en);
								detailmodel.set("isprimitive", "1");
								detailmodel.save();
								managementModel.set("position", detailmodel.get("detail_id"));
							}
						}
						managementModel.set("name", name);
						managementModel.set("company_id", companyId);
						managementModel.set("sys_language", sys_language);
						managementlist.add(managementModel);


					}
					Db.batchSave(managementlist, managementlist.size());
				}
				if(chairman.lastIndexOf(";") != -1) {
					chairman=chairman.substring(0, chairman.lastIndexOf(";"));
				}
				if(executive_director.lastIndexOf(";") != -1) {
					executive_director=executive_director.substring(0, executive_director.lastIndexOf(";"));
				}
				if(vice_president.lastIndexOf(";") != -1) {
					vice_president=vice_president.substring(0, vice_president.lastIndexOf(";"));
				}
				if(board_members.lastIndexOf(";") != -1) {
					board_members=board_members.substring(0, board_members.lastIndexOf(";"));
				}
				if(supervisory_board_chairman.lastIndexOf(";") != -1) {
					supervisory_board_chairman=supervisory_board_chairman.substring(0, supervisory_board_chairman.lastIndexOf(";"));
				}
				if(members_of_the_supervisors.lastIndexOf(";") != -1) {
					members_of_the_supervisors=members_of_the_supervisors.substring(0, members_of_the_supervisors.lastIndexOf(";"));
				}
				if(supervisory_board_chairman.lastIndexOf(";") != -1) {
					supervisory_board_chairman=supervisory_board_chairman.substring(0, supervisory_board_chairman.lastIndexOf(";"));
				}
				if(general_manager.lastIndexOf(";") != -1) {
					general_manager=general_manager.substring(0, general_manager.lastIndexOf(";"));
				}
				if(vice_general_manager.lastIndexOf(";") != -1) {
					vice_general_manager=vice_general_manager.substring(0, vice_general_manager.lastIndexOf(";"));
				}
				companyinfoModel.set("chairman", chairman);
				companyinfoModel.set("executive_director", executive_director);
				companyinfoModel.set("vice_president", vice_president);
				companyinfoModel.set("board_members", board_members);
				companyinfoModel.set("supervisory_board_chairman", supervisory_board_chairman);
				companyinfoModel.set("members_of_the_supervisors", members_of_the_supervisors);
				companyinfoModel.set("general_manager", general_manager);
				companyinfoModel.set("vice_general_manager", vice_general_manager);
				companyinfoModel.update();//当报告类型为102时获取主表管理层信息
				//股东信息
				JSONArray partners = null;
				try {
                    CreditCompanyShareholder.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录  股东信息
                    CreditCompanyLegalShareholderDetail.dao.deleteBycomIdAndLanguage(companyId, sys_language);//法人股东详情
                    CreditCompanyNaturalpersonShareholderDetail.dao.deleteBycomIdAndLanguage(companyId, sys_language);//自然人股东详情
					partners = json.getJSONObject("Result").getJSONArray("Partners");
					if(partners !=null && partners.size()>0){
						List<CreditCompanyShareholder> shareholderlist=new ArrayList<CreditCompanyShareholder>();
						List<CreditCompanyLegalShareholderDetail> legalShareholderDetailList=new ArrayList<CreditCompanyLegalShareholderDetail>();
						List<CreditCompanyNaturalpersonShareholderDetail> naturalpersonShareholderDetailList=new ArrayList<CreditCompanyNaturalpersonShareholderDetail>();
						for(int i=0;i<partners.size();i++){
							JSONObject partner = (JSONObject)partners.get(i);
							String name = partner.getString("StockName");//股东
							String StockType = partner.getString("StockType");//股东类型
							if("自然人股东".equals(StockType)){
								CreditCompanyNaturalpersonShareholderDetail natural=new CreditCompanyNaturalpersonShareholderDetail();
								natural.set("company_id", companyId);
								natural.set("sys_language", sys_language);
								natural.set("name", name);
								naturalpersonShareholderDetailList.add(natural);
							}else if("法人股东".equals(StockType)||"企业法人".equals(StockType)){
								CreditCompanyLegalShareholderDetail legal=new CreditCompanyLegalShareholderDetail();
								legal.set("company_id", companyId);
								legal.set("sys_language", sys_language);
								legal.set("company_name", name);
								legalShareholderDetailList.add(legal);
							}
							String Crawlername=name;
							if(Crawlername.contains("港澳台") || Crawlername.contains("合资")){
								try {
									Crawlername = HttpCrawler.getIcrisUrl(name);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									Crawlername=name;
								}
							}
						

							String StockPercent = partner.getString("StockPercent");//出资比例
							String StockPercentEd = StockPercent.replace("%", "").trim();//去除%
							String ShouldCapi = partner.getString("ShouldCapi").replace(",", "");//出资金额
							CreditCompanyShareholder shareholderModel=new CreditCompanyShareholder(); 
							shareholderModel.set("sh_name", Crawlername);
							shareholderModel.set("money", StockPercentEd);
							try {
								if(StringUtils.isNotBlank(ShouldCapi.replace(",", ""))){
									BigDecimal a = new BigDecimal(ShouldCapi.trim());
									BigDecimal b = new BigDecimal("10000");
									shareholderModel.set("contribution", a.multiply(b).toString());
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								shareholderModel.set("contribution", "");
							}
							shareholderModel.set("company_id", companyId);
							shareholderModel.set("sys_language", sys_language);
							shareholderlist.add(shareholderModel);
						}
						Db.batchSave(shareholderlist, shareholderlist.size());
						Db.batchSave(naturalpersonShareholderDetailList, naturalpersonShareholderDetailList.size());
						Db.batchSave(legalShareholderDetailList, legalShareholderDetailList.size());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				
				//变更事项
				JSONArray ChangeRecords = null;
				try {
                    CreditCompanyHis.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录
					ChangeRecords = json.getJSONObject("Result").getJSONArray("ChangeRecords");
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(ChangeRecords != null && ChangeRecords.size()>0){
					List<CreditCompanyHis> hisModellist=new ArrayList<CreditCompanyHis>();
					for(int i=0;i<ChangeRecords.size();i++){
						JSONObject changerecord = (JSONObject)ChangeRecords.get(i);
						String ProjectName = changerecord.getString("ProjectName").replace("null", "");//变更项
						String BeforeContent = changerecord.getString("BeforeContent").replace("null", "");//变更前
						String AfterContent = changerecord.getString("AfterContent").replace("null", "");//变更后
						String ChangeDate = changerecord.getString("ChangeDate");//变更日期
						CreditCompanyHis companyhisModel=new CreditCompanyHis();
						ChangeitemModel changeitemByNotNessent = ChangeitemService.service.getChangeitemByNotNessent(ProjectName);//看是否为非必需项
						if(changeitemByNotNessent == null ){
							companyhisModel.set("change_items", ProjectName);
							companyhisModel.set("change_font", BeforeContent);
							companyhisModel.set("change_back", AfterContent);
							companyhisModel.set("date", dateFormat(ChangeDate));
							companyhisModel.set("company_id", companyId);
							companyhisModel.set("sys_language", sys_language);
							hisModellist.add(companyhisModel);
						}
					}
					if(CollectionUtils.isNotEmpty(hisModellist)){
						Db.batchSave(hisModellist, hisModellist.size());
					}
				}
				
			}
			if("8".equals(reporttype) || "9".equals(reporttype)){
			//爬取分支机构(分公司)
			try{
				if(branches!=null&&branches.size()>0){
					List<CreditCompanyBranchestwo> list = new ArrayList<>();
					//限制10条
					for (int i = 0; i < (branches.size()>10?10:branches.size()); i++) {
						CreditCompanyBranchestwo model = new CreditCompanyBranchestwo();
						JSONObject branch = branches.getJSONObject(i);
						String  branchName = branch.getString("Name");
						//if(i==0){branchName = "万达地产集团有限公司";}测试
						model.set("branch_name",branchName);

						//inter_credit_code
						try{
							String lianxinCode = "";//联信编码
							//根据公司名找到时间最近的相同公司名的订单且需要报告类型一致
							if(StringUtils.isNotBlank(branchName)){
								String sql = "select num from credit_order_info " +
										" where  del_flag=0 " +
										" and company_by_report= \""+branchName+
										"\" and report_type="+reporttype+
										" order by create_date desc";
								CreditOrderInfo temp = 	CreditOrderInfo.dao.findFirst(sql);
								if(temp!=null){
									lianxinCode = temp.getStr("num");
								}
							}
							model.set("inter_credit_code", lianxinCode);
						}catch (Exception e){
							e.printStackTrace();
						}
						/*//爬取成立日期
						if(StringUtils.isNotBlank(branchName)){
							JSONObject json_ = HttpTest.searchWide(branchName,"1","1",null);
							String  json_status = json_.getString("Status");
							if("200".equals(json_status)){
								try {
									JSONObject result = ((JSONObject)(json_.getJSONArray("Result").get(0)));
									String startDate = result.getString("StartDate");
									if(StringUtils.isNotBlank(startDate)){
										startDate  = startDate.trim().substring(0,10);
										model.set("register_date",startDate);
									}
									String legal = result.getString("OperName");
									if(legal!=null)
									model.set("principal",legal);

									String creditCode = result.getString("CreditCode");
									if(creditCode!=null)
									model.set("registered_no",creditCode);

								}catch (Exception e){
									e.printStackTrace();
								}
							}
						}*/
						model.set("company_id",companyId);
						model.set("sys_language",sys_language);
						list.add(model);
					}
					if(CollectionUtils.isNotEmpty(list)){
						Db.batchSave(list, list.size());
					}
				}


			}catch (Exception e)	{
				e.printStackTrace();
			}

			//爬取 企业对外投资
				try{

					JSONObject subsidiariesJson = iHttpTest.getSubsidiaries(companyName,"1",PAGESIZE);
					String caipanstatus = subsidiariesJson.getString("Status");
					if("200".equals(caipanstatus)){
						CreditCompanySubsidiaries.dao.deleteBycomIdAndLanguage(companyId,sys_language);//删除
						List<CreditCompanySubsidiaries> subsidiarieslist = new ArrayList<CreditCompanySubsidiaries>();

						JSONArray jsonArray = subsidiariesJson.getJSONArray("Result");

						if(jsonArray !=null && jsonArray.size()>0){
							for(int j=0;j<jsonArray.size();j++){
								CreditCompanySubsidiaries model = new CreditCompanySubsidiaries();
								JSONObject subsidiaries = (JSONObject)jsonArray.get(j);
								String comStatus = subsidiaries.getString("Status");
								if("吊销".equals(comStatus)||"注销".equals(comStatus)){
									continue;
								}

								String name = subsidiaries.getString("Name");
								String lianxinCode = "";//联信编码
								//根据公司名找到时间最近的相同公司名的订单且需要报告类型一致
								if(StringUtils.isNotBlank(name)){
									String sql = "select num from credit_order_info " +
													" where  del_flag=0 " +
													" and company_by_report= \""+name+
													"\" and report_type="+reporttype+
													" order by create_date desc";
									CreditOrderInfo temp = 	CreditOrderInfo.dao.findFirst(sql);
									 if(temp!=null){
									 	lianxinCode = temp.getStr("num");
									 }
								}
								model.set("inter_credit_code", lianxinCode);
								model.set("company_id",companyId).set("sys_language",sys_language);
								model.set("company_name", name);

								String creditCode = subsidiaries.getString("CreditCode");
								model.set("registered_no", creditCode);

								String legal  = subsidiaries.getString("OperName");
								model.set("legal", legal);

								String startDate = subsidiaries.getString("StartDate");
								model.set("register_date", startDate);

								String registCapi = subsidiaries.getString("RegistCapi");
								dealCurrency(registCapi,model,"registered_capital","registered_capital_currency");

								subsidiarieslist.add(model);
							}
						}
						if(CollectionUtils.isNotEmpty(subsidiarieslist)){
							Db.batchSave(subsidiarieslist, subsidiarieslist.size());
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
				//线程爬取企查查裁判文书，法院公告，开庭公告信息,商标数据并保存
				Thread th=new Thread(new threadEnterpriseGrabOther(companyId, companyName, sys_language));
				th.start();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DictCache.initDict();//缓存刷新
		return flag;
	}
	
	private static String dateFormat(String StartDate)  {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(StartDate);
			StartDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (ParseException e) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(StartDate);
			} catch (ParseException e1) {
				try {
					date = new SimpleDateFormat("yyyy年MM月dd日").parse(StartDate);
					StartDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					StartDate="";
				}
			}
		}
		
		return StartDate;
	}



	/**
	 * 爬取裁判文书,开庭公告，图标等等
	 * @author dou_shuiahi
	 * @date: 2019年2月21日上午9:13:16
	 * @Description:
	 */
	public static  final String  PAGESIZE = "30";//爬取的条数，最大不超过30条


	public void enterpriseGrabOther(String companyId,String companyName,String sys_language) throws Exception{

		JSONObject caipanjson = iHttpTest.getJudgmentDoc(companyName,"1",PAGESIZE);//裁判文书
		String caipanstatus = caipanjson.getString("Status");
		if("200".equals(caipanstatus)){
			/*JSONObject Paging = caipanjson.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}*/
			CreditCompanyJudgmentdoc.dao.deleteBycomIdAndLanguage(companyId, sys_language);//删除
			List<CreditCompanyJudgmentdoc> judgmentdoclist=new ArrayList<CreditCompanyJudgmentdoc>();
			//for(int i=1;i<=totalpage;i++){
				//JSONObject json = HttpTest.getJudgmentDoc(companyName,i+"",CAI_PAN_WEN_SHU_PAGESIZE);//获取每页
				JSONArray jsonArray = caipanjson.getJSONArray("Result");
				 
				if(jsonArray !=null && jsonArray.size()>0){
										for(int j=0;j<jsonArray.size();j++){
						CreditCompanyJudgmentdoc model=new CreditCompanyJudgmentdoc();
						JSONObject judgmentdoc = (JSONObject)jsonArray.get(j);
						String Id = judgmentdoc.getString("Id");
						String Court = judgmentdoc.getString("Court");
						model.set("court", Court);
						String CaseName = judgmentdoc.getString("CaseName");
						model.set("casename", CaseName);
						String CaseNo = judgmentdoc.getString("CaseNo");
						model.set("caseno", CaseNo);
						String CaseType = judgmentdoc.getString("CaseType");
						String SubmitDate = judgmentdoc.getString("SubmitDate");
						model.set("submitdate", dateFormat(SubmitDate));
//						String CaseRole = judgmentdoc.getString("CaseRole");//案件身份
						JSONArray CaseRole = judgmentdoc.getJSONArray("CaseRole");//案件身份
						String value="";
						for(int l=0;l<CaseRole.size();l++){
							JSONObject caserole=(JSONObject) CaseRole.get(l);
							String role=caserole.getString("R");
							String name=caserole.getString("P");
							value+=role+"-"+name+";";
						}
						model.set("caserole", value);
						model.set("company_id", companyId);
						model.set("sys_language", sys_language);
						JSONObject judgmentdocdetail = iHttpTest.getJudgmentDocDetail(Id);//鑾峰彇api浼佷笟瑁佸垽鏂囦功璇︽儏
						if(judgmentdocdetail.getString("Status").equals("200")){
						String CaseReason=judgmentdocdetail.getJSONObject("Result").getString("CaseReason");//妗堢敱
							model.set("casereason", CaseReason);//案由
					}
						judgmentdoclist.add(model);
					}
				}
			//}
			if(CollectionUtils.isNotEmpty(judgmentdoclist)){
				Db.batchSave(judgmentdoclist, judgmentdoclist.size());
			}
		}
		JSONObject courtannouncement = iHttpTest.getCourtAnnouncement(companyName,"1",PAGESIZE);//法院公告
		String courtannouncementstatus = courtannouncement.getString("Status");
		if(courtannouncementstatus.equals("200")){
		/*	JSONObject Paging = courtannouncement.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}*/
			CreditCompanyCourtannouncement.dao.deleteBycomIdAndLanguage(companyId, sys_language);
			List<CreditCompanyCourtannouncement> list=new ArrayList<CreditCompanyCourtannouncement>();
			//for(int i=1;i<=totalpage;i++){
				//JSONObject CourtAnnouncementjson = HttpTest.getCourtAnnouncement(companyName,i+"");
				JSONArray jsonArray = courtannouncement.getJSONArray("Result");
				if(jsonArray !=null && jsonArray.size()>0){
					for(int j=0;j<jsonArray.size();j++){
						JSONObject CourtAnnouncement = (JSONObject)jsonArray.get(j);
						CreditCompanyCourtannouncement model=new CreditCompanyCourtannouncement();
						String Id = CourtAnnouncement.getString("Id");//id
						String Party = CourtAnnouncement.getString("Party");
						model.set("party", Party);
						String Category = CourtAnnouncement.getString("Category");
						model.set("category", Category);
						String PublishedDate = CourtAnnouncement.getString("PublishedDate");//刊登日期
						model.set("publisheddate", dateFormat(PublishedDate));
						/*String PublishedPage = CourtAnnouncement.getString("PublishedPage");
						model.set("publishedpage", PublishedPage);*/
						JSONArray a=CourtAnnouncement.getJSONArray("ProsecutorList");
						if(a.size()>0){
							String PublishedPage = a.getJSONObject(0).getString("Name");
							model.set("publishedpage", PublishedPage);
						}
						String UploadDate = CourtAnnouncement.getString("UploadDate");//上传日期
						model.set("uploaddate", dateFormat(UploadDate));
						String Court = CourtAnnouncement.getString("Court");
						model.set("court", Court);
						String Content = CourtAnnouncement.getString("Content");
						model.set("content", Content);
						model.set("company_id", companyId);
						model.set("sys_language", sys_language);
						list.add(model);
					}
			}
		//}
		if(CollectionUtils.isNotEmpty(list)){//淇濆瓨
			Db.batchSave(list, list.size());
		}
		}
		
		
		JSONObject courtnotice = iHttpTest.getCourtNotice(companyName,"1",PAGESIZE);//开庭公告
		String courtnoticestatus = courtnotice.getString("Status");
		if(courtnoticestatus.equals("200")){
			/*JSONObject Paging = courtnotice.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}*/
			CreditCompanyCourtnotice.dao.deleteBycomIdAndLanguage(companyId, sys_language);//鏍规嵁鍏徃缂栫爜鍜屾姤鍛婄被鍨嬪垹闄よ褰�
			List<CreditCompanyCourtnotice> list=new ArrayList<CreditCompanyCourtnotice>();
			//for(int i=1;i<=totalpage;i++){
				//JSONObject courtnoticejson = HttpTest.getCourtNotice(companyName,i+"");//鑾峰彇api浼佷笟寮�搴叕鍛�
				JSONArray jsonArray = courtnotice.getJSONArray("Result");
				if(jsonArray !=null && jsonArray.size()>0){
					for(int j=0;j<jsonArray.size();j++){
						JSONObject CourtAnnouncement = (JSONObject)jsonArray.get(j);
						CreditCompanyCourtnotice model=new CreditCompanyCourtnotice();
						String Id = CourtAnnouncement.getString("Id");//id
						String CaseNo = CourtAnnouncement.getString("CaseNo");//鏆楀彿
						model.set("caseno", CaseNo);
						String LianDate = CourtAnnouncement.getString("LianDate");//寮�搴棩鏈�
						model.set("liandate", LianDate);
						String CaseReason = CourtAnnouncement.getString("CaseReason");//妗堢敱
						model.set("casereason", CaseReason);
						String Prosecutorlist = CourtAnnouncement.getString("Prosecutorlist");//鍘熷憡/涓婅瘔浜�
						model.set("prosecutorlist", Prosecutorlist);
						String Defendantlist = CourtAnnouncement.getString("Defendantlist");//琚憡/琚笂璇変汉
						model.set("defendantlist", Defendantlist);
						model.set("company_id", companyId);
						model.set("sys_language", sys_language);
						list.add(model);
					}
			   }
			//}
			if(CollectionUtils.isNotEmpty(list)){
				Db.batchSave(list, list.size());
			}
		}
		JSONObject brandandpatent = iHttpTest.getBrandandpatent(companyName,"1",PAGESIZE);//企业图标
		String brandandpatentstatus = brandandpatent.getString("Status");
		if(brandandpatentstatus.equals("200")){
			//JSONObject Paging = brandandpatent.getJSONObject("Paging");
			/*int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}*/
			CreditCompanyBrandandpatent.dao.deleteBycomIdAndLanguage(companyId, sys_language);//
			//for(int i=1;i<=totalpage;i++){
				//JSONObject brandandpatentjson = HttpTest.getBrandandpatent(companyName,i+"");//
				String brandandpatentstatus2 = brandandpatent.getString("Status");
				/*if(!brandandpatentstatus2.equals("200")){
					continue;
				}*/
				JSONArray jsonArray = brandandpatent.getJSONArray("Result");
				if(jsonArray !=null && jsonArray.size()>0){
					List<CreditCompanyBrandandpatent>  list= JSON.parseArray(jsonArray.toString(), CreditCompanyBrandandpatent.class);
					for(CreditCompanyBrandandpatent model:list){
						model.set("company_id", companyId);
						model.set("sys_language", sys_language);
					}
					Db.batchSave(list, list.size());
			    }
		//	}
		}
		
		
		JSONObject patent = iHttpTest.getPatent(companyName,"1",PAGESIZE);//企业专利
		String patentstatus = patent.getString("Status");
		if(patentstatus.equals("200")){
			
			CreditCompanyPatent.dao.deleteBycomIdAndLanguage(companyId, sys_language);//
			JSONArray jsonArray = patent.getJSONArray("Result");
			if(jsonArray !=null && jsonArray.size()>0){
				List<CreditCompanyPatent>  list= JSON.parseArray(jsonArray.toString(), CreditCompanyPatent.class);
				for(CreditCompanyPatent model:list){
					model.set("company_id", companyId);
					model.set("sys_language", sys_language);
				}
				Db.batchSave(list, list.size());
		    }
		}
	}
	class threadEnterpriseGrabOther implements Runnable{
		String companyId = "";
	    String company = "";
	    String sys_language="";
		public threadEnterpriseGrabOther(String companyId, String company,
				String sys_language) {
			super();
			this.companyId = companyId;
			this.company = company;
			this.sys_language = sys_language;
		}
		@Override
		public void run() {
			try {
				enterpriseGrabOther(companyId,company,sys_language);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//引用之前的报告,节省成本
	public void enterGrabTheSameCompany(String companyId,String sys_language,String reporttype){

		List<CreditCompanySubsidiaries> subsidiariesList = CreditCompanySubsidiaries.dao.getBycomIdAndLanguage(companyId, sys_language);//对外投资
		for(int i=0;i<subsidiariesList.size();i++){
			CreditCompanySubsidiaries entity= subsidiariesList.get(i);
			entity.remove("id");
			entity.set("company_id", companyId);
			entity.set("sys_language", sys_language);

		}
		Db.batchSave(subsidiariesList, subsidiariesList.size());

		List<CreditCompanyBranchestwo> branchestwoList = CreditCompanyBranchestwo.dao.getBycomIdAndLanguage(companyId, sys_language);//分支机构
		for(int i=0;i<branchestwoList.size();i++){
			CreditCompanyBranchestwo entity= branchestwoList.get(i);
			entity.remove("id");
			entity.set("company_id", companyId);
			entity.set("sys_language", sys_language);
		}
		Db.batchSave(branchestwoList, branchestwoList.size());

		List<CreditCompanyManagement> managementList = CreditCompanyManagement.dao.getBycomIdAndLanguage(companyId, sys_language);//管理层
		for(int i=0;i<managementList.size();i++){
			CreditCompanyManagement management= managementList.get(i);
			management.remove("id");
			management.set("company_id", companyId);
			management.set("sys_language", sys_language);
		 
			}
		Db.batchSave(managementList, managementList.size());

		List<CreditCompanyShareholder> shareholderList = CreditCompanyShareholder.dao.getBycomIdAndLanguage(companyId, sys_language);//股东

		for(int i=0;i<shareholderList.size();i++){
			CreditCompanyShareholder shareholder= shareholderList.get(i);
			shareholder.remove("id");
			shareholder.set("company_id", companyId);
			shareholder.set("sys_language", sys_language);
		 
			}
		Db.batchSave(shareholderList, shareholderList.size());
		List<CreditCompanyHis> creditcompanyhisList = CreditCompanyHis.dao.getBycomIdAndLanguage(companyId, sys_language);//历史变更
		for(int i=0;i<creditcompanyhisList.size();i++){
			CreditCompanyHis creditcompanyhis= creditcompanyhisList.get(i);
			creditcompanyhis.remove("id");
			creditcompanyhis.set("company_id", companyId);
			creditcompanyhis.set("sys_language", sys_language);
		 
			}
		Db.batchSave(creditcompanyhisList, creditcompanyhisList.size());
		if("8".equals(reporttype) || "10".equals(reporttype) || "9".equals(reporttype)|| "11".equals(reporttype)){
			//裁判文书，法院公告，开庭公告信息,商标数据并保存
			List<CreditCompanyJudgmentdoc> judgmentdocList = CreditCompanyJudgmentdoc.dao.getBycomIdAndLanguage(companyId, sys_language);
			for(int i=0;i<judgmentdocList.size();i++){
				CreditCompanyJudgmentdoc judgmentdoc= judgmentdocList.get(i);
				judgmentdoc.remove("id");
				judgmentdoc.set("company_id", companyId);
				judgmentdoc.set("sys_language", sys_language);
			 
				}
			Db.batchSave(judgmentdocList, judgmentdocList.size());
			List<CreditCompanyCourtannouncement> courtannouncementList = CreditCompanyCourtannouncement.dao.getBycomIdAndLanguage(companyId, sys_language);
			for(int i=0;i<courtannouncementList.size();i++){
				CreditCompanyCourtannouncement courtannouncement= courtannouncementList.get(i);
				courtannouncement.remove("id");
				courtannouncement.set("company_id", companyId);
				courtannouncement.set("sys_language", sys_language);
				}
			Db.batchSave(courtannouncementList, courtannouncementList.size());
			List<CreditCompanyCourtnotice> courtnoticeList = CreditCompanyCourtnotice.dao.getBycomIdAndLanguage(companyId, sys_language);
			for(int i=0;i<courtnoticeList.size();i++){
				CreditCompanyCourtnotice courtnotice= courtnoticeList.get(i);
				courtnotice.remove("id");
				courtnotice.set("company_id", companyId);
				courtnotice.set("sys_language", sys_language);
				}
			Db.batchSave(courtnoticeList, courtnoticeList.size());
			}
	}

	static void dealCurrency(String registCapi, Model model ,String capital_filedName,String currency_fieldName){

		if(registCapi.indexOf("万元人民币") !=-1){
			String replace = registCapi.replace("万元人民币", "");
			BigDecimal a = new BigDecimal(replace);
			BigDecimal b = new BigDecimal("10000");
			model.set(capital_filedName, a.multiply(b).toString());
			model.set(currency_fieldName,"274");
		} else
		if(registCapi.indexOf("万美元") !=-1){
			String replace = registCapi.replace("万美元", "");
			BigDecimal a = new BigDecimal(replace);
			BigDecimal b = new BigDecimal("10000");
			model.set(capital_filedName, a.multiply(b).toString());
			model.set(currency_fieldName,"267");
		}else
		if(registCapi.indexOf("万港元") !=-1){
			String replace = registCapi.replace("万港元", "");
			BigDecimal a = new BigDecimal(replace);
			BigDecimal b = new BigDecimal("10000");
			model.set(capital_filedName, a.multiply(b).toString());
			model.set(currency_fieldName,"266");
		}else{

        }
	}
	public static void main(String[] args) {
		CompanyService c = new CompanyService();
		 try{
			c.iHttpTest.getCourtAnnouncement("ddss",1+"","2");
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
