package com.hailian.modules.credit.company.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;
import com.hailian.modules.credit.company.model.CompanyModel;
import com.hailian.modules.credit.companychangeitem.model.ChangeitemModel;
import com.hailian.modules.credit.companychangeitem.service.ChangeitemService;
import com.hailian.modules.credit.pricemanager.model.ReportPrice;
import com.hailian.system.dict.DictCache;
import com.hailian.system.dict.SysDictDetail;
import com.hailian.util.http.HttpTest;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.ext.directive.Str;

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
	public boolean enterpriseGrab(String companyId,String companyName,String sys_language){
		boolean flag=false;
		JSONObject json = HttpTest.getYjapi(companyName);//获取api企业信息数据
		String status = json.getString("Status"); //获取调用api接口的状态码
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
			
			List<SysDictDetail> companytype = SysDictDetail.dao.getDictDetailBy(EconKind,"companyType");
			if(companytype !=null && CollectionUtils.isNotEmpty(companytype)){
				companyinfoModel.set("company_type", companytype.get(0).get("detail_id"));
			}else{
				SysDictDetail detailmodel=new SysDictDetail();
				detailmodel.set("dict_type", "companyType");
				detailmodel.set("detail_name", EconKind);
				detailmodel.save();
				companyinfoModel.set("company_type", detailmodel.get("detail_id"));
			}
			companyinfoModel.set("register_code_type", "632");
			companyinfoModel.set("register_codes", CreditCode);
			companyinfoModel.set("principal", OperName);
			companyinfoModel.set("registered_capital", RegistCapi);
			int indexOf = RegistCapi.indexOf("万元人民币");
			if(indexOf !=-1){
				String replace = RegistCapi.replace("万元人民币", "0000");
				companyinfoModel.set("registered_capital", replace);
				companyinfoModel.set("currency","274");
			}
			
			String replaceStartDate = StartDate.replace("00:00:00", "").trim();
			companyinfoModel.set("establishment_date", replaceStartDate);
			String replaceTermStart = TermStart.replace("00:00:00", "").trim();
			companyinfoModel.set("business_date_start", replaceTermStart);
			if(StringUtils.isNotBlank(TeamEnd)){
				String replaceTeamEnd = TeamEnd.replace("00:00:00", "").trim();
				companyinfoModel.set("business_date_end", replaceTeamEnd);
			}else{
				companyinfoModel.set("business_date_end", "无期");
			}
			
			List<SysDictDetail> dictDetailBy = SysDictDetail.dao.getDictDetailBy(Status,"registration_status");
			if(CollectionUtils.isNotEmpty(dictDetailBy) && dictDetailBy!=null){
				companyinfoModel.set("registration_status", dictDetailBy.get(0).get("detail_id"));
			}else{
				SysDictDetail detailmodel=new SysDictDetail();
				detailmodel.set("dict_type", "registration_status");
				detailmodel.set("detail_name", Status);
				detailmodel.save();
				companyinfoModel.set("registration_status", detailmodel.get("detail_id"));
			}
			companyinfoModel.set("registration_authority", BelongOrg);
			companyinfoModel.set("address", Address);
			companyinfoModel.set("province", Province);
			companyinfoModel.set("operation_scope", Scope);
			companyinfoModel.set("id", companyId);
			companyinfoModel.set("sys_language", sys_language);
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
					String ShouldCapi = partner.getString("ShouldCapi");//出资金额
					CreditCompanyShareholder shareholderModel=new CreditCompanyShareholder(); 
					shareholderModel.set("name", name);
					shareholderModel.set("money", StockPercent);
					shareholderModel.set("contribution", ShouldCapi);
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
					
					List<SysDictDetail> dictDetailBy2 = SysDictDetail.dao.getDictDetailBy(job,"position");
					if(dictDetailBy2 !=null && CollectionUtils.isNotEmpty(dictDetailBy2)){
						managementModel.set("position", dictDetailBy2.get(0).get("detail_id"));
					}else{
						SysDictDetail detailmodel=new SysDictDetail();
						detailmodel.set("dict_type", "position");
						detailmodel.set("detail_name", job);
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
						companyhisModel.set("date", ChangeDate);
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
		
		System.out.println(status);
		DictCache.initDict();//缓存刷新
		return flag;
	}
}
