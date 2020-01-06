package com.hailian.util.http.showapi.pachongproxy;
import net.sf.json.JSONObject;

public interface IHttpTest {
    @URLAnnotation("http://api.qichacha.com/ECIV4/GetDetailsByName")
    JSONObject getYjapi(String conpanyName) throws Exception;//企业基本信息
    @URLAnnotation("http://api.qichacha.com/JudgeDocV4/SearchJudgmentDoc")
    JSONObject getJudgmentDoc(String conpanyName,String pageIndex,String pageSize) throws Exception;//裁判文书
    @URLAnnotation("http://api.qichacha.com/JudgeDocV4/GetJudgementDetail")
    JSONObject getJudgmentDocDetail(String id) throws Exception;//裁判文书详情
    @URLAnnotation("http://api.qichacha.com/CourtNoticeV4/SearchCourtAnnouncement")
    JSONObject getCourtAnnouncement(String conpanyName,String pageIndex,String pageSize) throws Exception;//法院公告
    @URLAnnotation("http://api.qichacha.com/CourtAnnoV4/SearchCourtNotice")
    JSONObject getCourtNotice(String conpanyName,String pageIndex,String pageSize) throws Exception;//开庭公告
    @URLAnnotation("http://api.qichacha.com/tm/SearchByApplicant")
    JSONObject getBrandandpatent(String conpanyName,String pageIndex,String pageSize) throws Exception;//企业商标 
    @URLAnnotation("http://api.qichacha.com/tm/GetDetails")
    JSONObject getBrandandpatentDetail(String id) throws Exception;//商标详情
    @URLAnnotation("http://api.qichacha.com/PatentV4/SearchPatents")
    JSONObject getPatent(String conpanyName,String pageIndex,String pageSize) throws Exception;//企业专利 
    @URLAnnotation("http://api.qichacha.com/ECIInvestment/GetInvestmentList")
    JSONObject getSubsidiaries(String CompanyName,String pageIndex,String pageSize) throws Exception;//企业对外投资
    @URLAnnotation("http://api.qichacha.com/ECIV4/SearchWide")
    JSONObject searchWide(String CompanyName,String pageIndex,String pageSize,String type) throws Exception;//废弃
    @URLAnnotation("http://api.qichacha.com/CourtV4/SearchZhiXing")
    JSONObject getBrokenPromisesPerson(String conpanyName,String pageIndex,String pageSize) throws Exception;//被执行人信息
    @URLAnnotation("http://api.qichacha.com/CourtV4/SearchShiXin")
    JSONObject getBrokenPromises(String conpanyName,String pageIndex,String pageSize) throws Exception;//失信被执行
}