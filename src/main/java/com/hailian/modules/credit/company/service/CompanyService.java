package com.hailian.modules.credit.company.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyBrandandpatent;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyCourtannouncement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyCourtnotice;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyJudgmentdoc;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.companychangeitem.model.ChangeitemModel;
import com.hailian.modules.credit.companychangeitem.service.ChangeitemService;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
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
			JSONObject json = HttpTest.getYjapi(companyName);//获取api企业信息数据
			status = json.getString("Status");
			System.out.println(status);
			//200调用成功并查到企业相关信息
			if("200".equals(status)){
				flag=true;
				JSONObject jsonResulet = json.getJSONObject("Result");
				//企业基本信息
				String No = jsonResulet.getString("No"); //注册号
				String CreditCode = jsonResulet.getString("CreditCode"); //统一信用社信用代码
				System.out.println(CreditCode);
				String EconKind = jsonResulet.getString("EconKind"); //企业类型
				String OperName = jsonResulet.getString("OperName"); //法人名
				String RegistCapi = jsonResulet.getString("RegistCapi"); //注册资本
				String StartDate = jsonResulet.getString("StartDate"); //成立日期
				String TermStart = jsonResulet.getString("TermStart"); //营业期限自
				String TeamEnd = jsonResulet.getString("TeamEnd"); //营业期限至
				String BelongOrg = jsonResulet.getString("BelongOrg"); //登记机关
				String Address = jsonResulet.getString("Address"); //地址
				String Province = jsonResulet.getString("Province"); //所在省
				String Scope = jsonResulet.getString("Scope"); //经营范围
				String Status = jsonResulet.getString("Status"); //登记状态
				CreditCompanyInfo companyinfoModel=new CreditCompanyInfo();
				companyinfoModel.set("registration_num", No);
				
				List<SysDictDetail> companytype = SysDictDetail.dao.getDictDetailBy(EconKind.trim(),"companyType");
				if(companytype !=null && CollectionUtils.isNotEmpty(companytype)){
					companyinfoModel.set("company_type", companytype.get(0).get("detail_id"));
				}else{
					SysDictDetail detailmodel=new SysDictDetail();
					detailmodel.set("dict_type", "companyType");
					detailmodel.set("detail_name", EconKind.trim());
					String value_en = TransApi.Trans(EconKind.trim(),"en");
					detailmodel.set("detail_name_en", value_en);
					detailmodel.set("isprimitive", "1");
					detailmodel.save();
					companyinfoModel.set("company_type", detailmodel.get("detail_id"));
				}
				companyinfoModel.set("register_code_type", "632");
				companyinfoModel.set("register_codes", CreditCode);
				companyinfoModel.set("principal", OperName);
				companyinfoModel.set("registered_capital", RegistCapi);
				if(RegistCapi.indexOf("万元人民币") !=-1){
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
				String StartDateFor = dateFormat(StartDate);//转成年月日
				companyinfoModel.set("establishment_date", StartDateFor);
				String TermStartFor = dateFormat(TermStart);//转成年月日
				companyinfoModel.set("business_date_start", TermStartFor);
				if(StringUtils.isNotBlank(TeamEnd)){
					String TeamEndFor = dateFormat(TeamEnd);//转成年月日
					companyinfoModel.set("business_date_end", TeamEndFor);
				}else{
					companyinfoModel.set("business_date_end", "长期");
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
				
				companyinfoModel.set("id", companyId);
				companyinfoModel.set("sys_language", sys_language);
				companyinfoModel.set("name", companyName);
				
				JSONObject ContactInfo = json.getJSONObject("Result").getJSONObject("ContactInfo");//联系信息
				String PhoneNumber = ContactInfo.getString("PhoneNumber");//电话
				String Email = ContactInfo.getString("Email");//邮箱
				companyinfoModel.set("telphone", PhoneNumber);
				companyinfoModel.set("email", Email);
				companyinfoModel.update();
				//股东信息
				JSONArray partners = json.getJSONObject("Result").getJSONArray("Partners");
				if(partners !=null && partners.size()>0){
					CreditCompanyShareholder.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录
					List<CreditCompanyShareholder> shareholderlist=new ArrayList<CreditCompanyShareholder>();
					for(int i=0;i<partners.size();i++){
						JSONObject partner = (JSONObject)partners.get(i);
						String name = partner.getString("StockName");//股东
						String StockPercent = partner.getString("StockPercent");//出资比例
						String StockPercentEd = StockPercent.replace("%", "").trim();//去除%
						String ShouldCapi = partner.getString("ShouldCapi");//出资金额
						CreditCompanyShareholder shareholderModel=new CreditCompanyShareholder(); 
						shareholderModel.set("sh_name", name);
						shareholderModel.set("money", StockPercentEd);
						BigDecimal a = new BigDecimal(ShouldCapi);
						BigDecimal b = new BigDecimal("10000");
						shareholderModel.set("contribution", a.multiply(b).toString());
						shareholderModel.set("company_id", companyId);
						shareholderModel.set("sys_language", sys_language);
						shareholderlist.add(shareholderModel);
					}
					Db.batchSave(shareholderlist, shareholderlist.size());
				}
				//管理层
				JSONArray Employees = json.getJSONObject("Result").getJSONArray("Employees");
				List<CreditCompanyManagement> managementlist=new ArrayList<CreditCompanyManagement>();
				if(Employees !=null && Employees.size()>0){
					CreditCompanyManagement.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录
					for(int i=0;i<Employees.size();i++){
						JSONObject employee = (JSONObject)Employees.get(i);
						//CreditCompanyManagement
						String name = employee.getString("Name");//管理层姓名
						String job = employee.getString("Job");//职位
						CreditCompanyManagement managementModel = new CreditCompanyManagement();
						
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
						managementModel.set("name", name);
						managementModel.set("company_id", companyId);
						managementModel.set("sys_language", sys_language);
						managementlist.add(managementModel);
					}
					Db.batchSave(managementlist, managementlist.size());
				}

				//变更事项
				JSONArray ChangeRecords = json.getJSONObject("Result").getJSONArray("ChangeRecords");
				if(ChangeRecords != null && ChangeRecords.size()>0){
					CreditCompanyHis.dao.deleteBycomIdAndLanguage(companyId, sys_language);//根据公司编码和报告类型删除记录
					List<CreditCompanyHis> hisModellist=new ArrayList<CreditCompanyHis>();
					for(int i=0;i<ChangeRecords.size();i++){
						JSONObject changerecord = (JSONObject)ChangeRecords.get(i);
						String ProjectName = changerecord.getString("ProjectName");//变更项
						String BeforeContent = changerecord.getString("BeforeContent");//变更前
						String AfterContent = changerecord.getString("AfterContent");//变更后
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
			if("8".equals(reporttype) || "10".equals(reporttype)){
			//线程爬取企查查裁判文书，法院公告，开庭公告信息,商标数据并保存
			Thread th=new Thread(new threadEnterpriseGrabOther(companyId, companyName, sys_language));
			th.start();
			}
//			enterpriseGrabOther(companyId,companyName,sys_language);//抓取企查查裁判文书，法院公告，开庭公告信息数据并保存
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return StartDate;
	}
	public void enterpriseGrabOther(String companyId,String companyName,String sys_language){
		JSONObject caipanjson = HttpTest.getJudgmentDoc(companyName,"");//裁判文书
		String caipanstatus = caipanjson.getString("Status");
		if("200".equals(caipanstatus)){
			JSONObject Paging = caipanjson.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			CreditCompanyJudgmentdoc.dao.deleteBycomIdAndLanguage(companyId, sys_language);//删除
			List<CreditCompanyJudgmentdoc> judgmentdoclist=new ArrayList<CreditCompanyJudgmentdoc>();
			for(int i=1;i<=totalpage;i++){
				JSONObject json = HttpTest.getJudgmentDoc(companyName,i+"");//获取每页
				JSONArray jsonArray = json.getJSONArray("Result");
				 
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
						JSONObject judgmentdocdetail = HttpTest.getJudgmentDocDetail(Id);//鑾峰彇api浼佷笟瑁佸垽鏂囦功璇︽儏
						if(judgmentdocdetail.getString("Status").equals("200")){
						String CaseReason=judgmentdocdetail.getJSONObject("Result").getString("CaseReason");//妗堢敱
							model.set("casereason", CaseReason);//案由
					}
						judgmentdoclist.add(model);
					}
				}
			}
			if(CollectionUtils.isNotEmpty(judgmentdoclist)){
				Db.batchSave(judgmentdoclist, judgmentdoclist.size());
			}
		}
		JSONObject courtannouncement = HttpTest.getCourtAnnouncement(companyName,"");//法院公告
		String courtannouncementstatus = courtannouncement.getString("Status");
		if(courtannouncementstatus.equals("200")){
			JSONObject Paging = courtannouncement.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			CreditCompanyCourtannouncement.dao.deleteBycomIdAndLanguage(companyId, sys_language);
			List<CreditCompanyCourtannouncement> list=new ArrayList<CreditCompanyCourtannouncement>();
			for(int i=1;i<=totalpage;i++){
				JSONObject CourtAnnouncementjson = HttpTest.getCourtAnnouncement(companyName,i+"");
				JSONArray jsonArray = CourtAnnouncementjson.getJSONArray("Result");
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
						String PublishedPage = CourtAnnouncement.getString("PublishedPage");
						model.set("publishedpage", PublishedPage);
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
		}
		if(CollectionUtils.isNotEmpty(list)){//淇濆瓨
			Db.batchSave(list, list.size());
		}
		}
		
		
		JSONObject courtnotice = HttpTest.getCourtNotice(companyName,"");//开庭公告
		String courtnoticestatus = courtnotice.getString("Status");
		if(courtnoticestatus.equals("200")){
			JSONObject Paging = courtnotice.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			CreditCompanyCourtnotice.dao.deleteBycomIdAndLanguage(companyId, sys_language);//鏍规嵁鍏徃缂栫爜鍜屾姤鍛婄被鍨嬪垹闄よ褰�
			List<CreditCompanyCourtnotice> list=new ArrayList<CreditCompanyCourtnotice>();
			for(int i=1;i<=totalpage;i++){
				JSONObject courtnoticejson = HttpTest.getCourtNotice(companyName,i+"");//鑾峰彇api浼佷笟寮�搴叕鍛�
				JSONArray jsonArray = courtnoticejson.getJSONArray("Result");
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
			}
			if(CollectionUtils.isNotEmpty(list)){
				Db.batchSave(list, list.size());
			}
		}
		JSONObject brandandpatent = HttpTest.getBrandandpatent(companyName,"");//企业图标
		String brandandpatentstatus = brandandpatent.getString("Status");
		if(brandandpatentstatus.equals("200")){
			JSONObject Paging = brandandpatent.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			CreditCompanyBrandandpatent.dao.deleteBycomIdAndLanguage(companyId, sys_language);//
			for(int i=1;i<=totalpage;i++){
				JSONObject brandandpatentjson = HttpTest.getBrandandpatent(companyName,i+"");//
				JSONArray jsonArray = brandandpatentjson.getJSONArray("Result");
				if(jsonArray !=null && jsonArray.size()>0){
					List<CreditCompanyBrandandpatent>  list= JSON.parseArray(jsonArray.toString(), CreditCompanyBrandandpatent.class);
					for(CreditCompanyBrandandpatent model:list){
						model.set("company_id", companyId);
						model.set("sys_language", sys_language);
					}
					Db.batchSave(list, list.size());
			    }
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
	public static void main(String[] args) {
		String date="2018-11-11";
		System.out.println(dateFormat(date));
	}
}
