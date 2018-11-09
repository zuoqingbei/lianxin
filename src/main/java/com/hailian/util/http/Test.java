package com.hailian.util.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyHis;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyManagement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String companyId="77777777";
		String reporttype="7";
		String s="{'Status':'200','Message':'查询成功','JobId':'','OrderNumber':'ECI2018110718072013417437','Result':{'Partners':[{'StockName':'程维','StockType':'自然人股东','StockPercent':'49.19%','ShouldCapi':'491.9','ShoudDate':'2032-07-01','InvestType':'货币','InvestName':null,'RealCapi':null,'CapiDate':null},{'StockName':'王刚','StockType':'自然人股东','StockPercent':'48.22%','ShouldCapi':'482.25','ShoudDate':'2032-07-01','InvestType':'货币','InvestName':null,'RealCapi':null,'CapiDate':null},{'StockName':'张博','StockType':'自然人股东','StockPercent':'1.55%','ShouldCapi':'15.53','ShoudDate':'2032-07-01','InvestType':'货币','InvestName':null,'RealCapi':null,'CapiDate':null},{'StockName':'吴睿','StockType':'自然人股东','StockPercent':'0.72%','ShouldCapi':'7.23','ShoudDate':'2032-07-01','InvestType':'货币','InvestName':null,'RealCapi':null,'CapiDate':null},{'StockName':'陈汀','StockType':'自然人股东','StockPercent':'0.31%','ShouldCapi':'3.09','ShoudDate':'2032-07-01','InvestType':'货币','InvestName':null,'RealCapi':null,'CapiDate':null}],'Employees':[{'Name':'程维','Job':'执行董事'},{'Name':'程维','Job':'经理'},{'Name':'吴睿','Job':'监事'}],'Branches':[{'CompanyId':'8dc057af59e908b6a1c05d74de114134','RegNo':'500902300083458','Name':'北京小桔科技有限公司重庆分公司','BelongOrg':'重庆市工商行政管理局南岸区分局经开区局','CreditCode':null,'OperName':null}],'ChangeRecords':[{'ProjectName':'投资人','BeforeContent':'程维*,自然人股东；王刚,自然人股东；陈汀,自然人股东；张博,自然人股东；吴睿,自然人股东；徐涛,自然人股东【退出】','AfterContent':'程维*,自然人股东；王刚,自然人股东；张博,自然人股东；吴睿,自然人股东；陈汀,自然人股东','ChangeDate':'2017-07-21'},{'ProjectName':'住所','BeforeContent':'北京市海淀区上地东路9号1幢5层北区2号','AfterContent':'北京市海淀区东北旺西路8号院35号楼5层501室','ChangeDate':'2016-09-27'},{'ProjectName':'经营范围','BeforeContent':'从事互联网文化活动；第二类增值电信业务中的信息服务业务(仅限互联网信息服务)(互联网信息服务不含新闻、出版、教育、医疗保健、药品和医疗器械、电子公告服务)(电信与信息服务业务许可证有效期至2020年04月29日)；第二类增值电信业务中的信息服务业务(不含固定网电话信息服务和互联网信息服务)(增值电信业务经营许可证有效期至2020年04月29日)。技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告；软件开发；销售自行开发后的产品；企业管理咨询；计算机系统服务；组织文化艺术交流活动(不含营业性演出)；公共关系服务；企业策划、设计；会议服务；市场调查；货物进出口、技术进出口、代理进出口从事互联网文化活动以及依法须经批准的项目,经相关部门批准后依批准的内容开展经营活动。','AfterContent':'从事互联网文化活动；互联网信息服务；经营电信业务。技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告；软件开发；销售自行开发后的产品；企业管理咨询；计算机系统服务；组织文化艺术交流活动(不含营业性演出)；公共关系服务；企业策划、设计；会议服务；市场调查；货物进出口、技术进出口、代理进出口企业依法自主选择经营项目,开展经营活动；从事互联网文化活动、互联网信息服务、经营电信业务以及依法须经批准的项目,经相关部门批准后依批准的内容开展经营活动；不得从事本市产业政策禁止和限制类项目的经营活动。','ChangeDate':'2016-09-27'},{'ProjectName':'注册资本','BeforeContent':'103.68','AfterContent':'1000（+864.51%）','ChangeDate':'2015-09-21'},{'ProjectName':'经营范围','BeforeContent':'技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告；软件开发；销售自行开发后的产品；企业管理咨询；计算机系统服务；组织文化艺术交流活动(不含营业性演出)；公共关系服务；企业策划、设计；会议服务；市场调查；货物进出口、技术进出口、代理进出口。依法须经批准的项目,经相关部门批准后依批准的内容开展经营活动。','AfterContent':'从事互联网文化活动；第二类增值电信业务中的信息服务业务(仅限互联网信息服务)(互联网信息服务不含新闻、出版、教育、医疗保健、药品和医疗器械、电子公告服务)(电信与信息服务业务许可证有效期至2020年04月29日)；第二类增值电信业务中的信息服务业务(不含固定网电话信息服务和互联网信息服务)(增值电信业务经营许可证有效期至2020年04月29日)。技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告；软件开发；销售自行开发后的产品；企业管理咨询；计算机系统服务；组织文化艺术交流活动(不含营业性演出)；公共关系服务；企业策划、设计；会议服务；市场调查；货物进出口、技术进出口、代理进出口从事互联网文化活动以及依法须经批准的项目,经相关部门批准后依批准的内容开展经营活动。','ChangeDate':'2015-09-21'},{'ProjectName':'注册资本','BeforeContent':'100','AfterContent':'103.68（+3.68%）','ChangeDate':'2015-05-28'},{'ProjectName':'投资人','BeforeContent':'程维*,自然人股东；王刚,自然人股东','AfterContent':'程维*,自然人股东；王刚,自然人股东；陈汀,自然人股东【新增】；张博,自然人股东【新增】；吴睿,自然人股东【新增】；徐涛,自然人股东【新增】','ChangeDate':'2015-05-28'},{'ProjectName':'住所','BeforeContent':'北京市海淀区中关村大街11号9层980','AfterContent':'北京市海淀区上地东路9号1幢5层北区2号','ChangeDate':'2014-06-30'},{'ProjectName':'经营范围','BeforeContent':'技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告。(依法须经批准的项目,经相关部门批准后方可开展经营活动)','AfterContent':'技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告；软件开发；销售自行开发后的产品；企业管理咨询；计算机系统服务；组织文化艺术交流活动(不含营业性演出)；公共关系服务；企业策划、设计；会议服务；市场调查；货物进出口、技术进出口、代理进出口。(依法须经批准的项目,经相关部门批准后方可开展经营活动)领取本执照后,应到市商务委备案。','ChangeDate':'2014-06-30'},{'ProjectName':'经营范围','BeforeContent':'技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务。(未取得行政许可的项目除外)','AfterContent':'技术开发、技术咨询、技术服务、技术推广；基础软件服务；应用软件服务；设计、制作、代理、发布广告。(依法须经批准的项目,经相关部门批准后方可开展经营活动)','ChangeDate':'2014-04-17'},{'ProjectName':'股东出资变更','BeforeContent':'程维*,自然人股东；吴睿,自然人股东【退出】','AfterContent':'程维*,自然人股东；王刚,自然人股东【新增】','ChangeDate':'2012-10-25'},{'ProjectName':'股东出资变更','BeforeContent':'程维*,80,自然人股东；吴睿,20,自然人股东【退出】','AfterContent':'程维*,50（-30）,自然人股东；王刚,50,自然人股东【新增】','ChangeDate':'2012-10-25'}],'ContactInfo':{'WebSite':[{'Name':null,'Url':'www.xiaojukeji.com'}],'PhoneNumber':'010-62682929','Email':null},'Industry':{'IndustryCode':'M','Industry':'科学研究和技术服务业','SubIndustryCode':'75','SubIndustry':'科技推广和应用服务业','MiddleCategoryCode':'759','MiddleCategory':'其他科技推广服务业','SmallCategoryCode':'7590','SmallCategory':'其他科技推广服务业'},'KeyNo':'4659626b1e5e43f1bcad8c268753216e','Name':'北京小桔科技有限公司','No':'110108015068911','BelongOrg':'北京市工商行政管理局海淀分局','OperName':'程维','StartDate':'2012-07-10 00:00:00','EndDate':'','Status':'开业','Province':'BJ','UpdatedDate':'2018-11-07 09:30:41','CreditCode':'9111010859963405XW','RegistCapi':'1000万元人民币','EconKind':'有限责任公司(自然人投资或控股)','Address':'北京市海淀区东北旺西路8号院35号楼5层501室','Scope':'技术开发、技术咨询、技术服务、技术推广;基础软件服务;应用软件服务;设计、制作、代理、发布广告;软件开发;销售自行开发后的产品;企业管理咨询;计算机系统服务;组织文化艺术交流活动(不含营业性演出);公共关系服务;企业策划、设计;会议服务;市场调查;货物进出口、技术进出口、代理进出口;从事互联网文化活动;互联网信息服务;经营电信业务。(企业依法自主选择经营项目,开展经营活动;从事互联网文化活动、互联网信息服务、经营电信业务以及依法须经批准的项目,经相关部门批准后依批准的内容开展经营活动;不得从事本市产业政策禁止和限制类项目的经营活动。)','TermStart':'2012-07-10 00:00:00','TeamEnd':'2032-07-09 00:00:00','CheckDate':'2017-12-11 00:00:00','OrgNo':'59963405-X','IsOnStock':'0','StockNumber':null,'StockType':null,'OriginalName':[],'ImageUrl':'https://co-image.qichacha.com/CompanyImage/4659626b1e5e43f1bcad8c268753216e.jpg'}}";
		JSONObject json = JSONObject.fromObject(s);
		String status = json.getString("Status"); //获取调用api接口的状态码
		System.out.println(status);
		//200调用成功并查到企业相关信息
		if("200".equals(status)){
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
			companyinfoModel.set("company_type", EconKind);
			companyinfoModel.set("credit_code", CreditCode);
			companyinfoModel.set("legal", OperName);
			companyinfoModel.set("registered_capital", RegistCapi);
			companyinfoModel.set("establishment_date", StartDate);
			companyinfoModel.set("business_date_start", TermStart);
			companyinfoModel.set("business_date_end", TeamEnd);
			companyinfoModel.set("registration_status", Status);
			companyinfoModel.set("registration_authority", BelongOrg);
			companyinfoModel.set("address", Address);
			companyinfoModel.set("province", Province);
			companyinfoModel.set("operation_scope", Scope);
			companyinfoModel.set("id", companyId);
			companyinfoModel.set("report_type", reporttype);
			JSONObject ContactInfo = json.getJSONObject("Result").getJSONObject("ContactInfo");//联系信息
			String PhoneNumber = ContactInfo.getString("PhoneNumber");//电话
			String Email = ContactInfo.getString("Email");//邮箱
			companyinfoModel.set("telphone", PhoneNumber);
			companyinfoModel.set("email", Email);
			companyinfoModel.update();
			//股东信息
			JSONArray partners = json.getJSONObject("Result").getJSONArray("Partners");
			if(partners !=null && partners.size()>0){
				CreditCompanyShareholder.dao.deleteBycomIdAndType(companyId, reporttype);//根据公司编码和报告类型删除记录
				for(int i=0;i<partners.size();i++){
					JSONObject partner = (JSONObject)partners.get(i);
					String name = partner.getString("StockName");//股东
					String StockPercent = partner.getString("StockPercent");//出资比例
					CreditCompanyShareholder shareholderModel=new CreditCompanyShareholder(); 
					shareholderModel.set("name", name);
					shareholderModel.set("money", StockPercent);
					shareholderModel.save();
				}
			}
			//管理层
			JSONArray Employees = json.getJSONObject("Result").getJSONArray("Employees");
			if(Employees !=null && Employees.size()>0){
				CreditCompanyManagement.dao.deleteBycomIdAndType(companyId, reporttype);//根据公司编码和报告类型删除记录
				for(int i=0;i<Employees.size();i++){
					JSONObject employee = (JSONObject)Employees.get(i);
					//CreditCompanyManagement
					String name = employee.getString("Name");//管理层姓名
					String job = employee.getString("Job");//职位
					CreditCompanyManagement managementModel = new CreditCompanyManagement();
					managementModel.set("name", name);
					managementModel.set("position", job);
					managementModel.save();
				}
			}
			//分支机构
			JSONArray Branches = json.getJSONObject("Result").getJSONArray("Branches");
			if(Branches != null && Branches.size()>0){
				for(int i=0;i<Branches.size();i++){
					JSONObject branche = (JSONObject)Branches.get(i);//分支
					String RegNo = branche.getString("RegNo");//注册号
				}
			}
			//变更事项
			JSONArray ChangeRecords = json.getJSONObject("Result").getJSONArray("ChangeRecords");
			if(ChangeRecords != null && ChangeRecords.size()>0){
				CreditCompanyHis.dao.deleteBycomIdAndType(companyId, reporttype);//根据公司编码和报告类型删除记录
				for(int i=0;i<ChangeRecords.size();i++){
					JSONObject changerecord = (JSONObject)ChangeRecords.get(i);
					String ProjectName = changerecord.getString("ProjectName");//变更项
					String BeforeContent = changerecord.getString("BeforeContent");//变更前
					String AfterContent = changerecord.getString("AfterContent");//变更后
					String ChangeDate = changerecord.getString("ChangeDate");//变更日期
					CreditCompanyHis companyhisModel=new CreditCompanyHis();
					companyhisModel.set("change_items", ProjectName);
					companyhisModel.set("change_font", BeforeContent);
					companyhisModel.set("change_back", AfterContent);
					companyhisModel.set("date", ChangeDate);
					companyhisModel.save();
				}
			}
		}
	}

}
