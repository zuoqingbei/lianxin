package com.hailian.util.http.showapi.pachongproxy;
import net.sf.json.JSONObject;

public interface IHttpTest {
    @URLAnnotation("http://api.qichacha.com/ECIV4/GetDetailsByName")
    JSONObject getYjapi(String conpanyName) throws Exception;
    @URLAnnotation("http://api.qichacha.com/JudgeDocV4/SearchJudgmentDoc")
    JSONObject getJudgmentDoc(String conpanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/JudgeDocV4/GetJudgementDetail")
    JSONObject getJudgmentDocDetail(String id) throws Exception;
    @URLAnnotation("http://api.qichacha.com/CourtNoticeV4/SearchCourtAnnouncement")
    JSONObject getCourtAnnouncement(String conpanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/CourtAnnoV4/SearchCourtNotice")
    JSONObject getCourtNotice(String conpanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/tm/Search")
    JSONObject getBrandandpatent(String conpanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/PatentV4/SearchPatents")
    JSONObject getPatent(String conpanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/ECIRelationV4/SearchInvestment")
    JSONObject getSubsidiaries(String CompanyName,String pageIndex,String pageSize) throws Exception;
    @URLAnnotation("http://api.qichacha.com/ECIV4/SearchWide")
    JSONObject searchWide(String CompanyName,String pageIndex,String pageSize,String type) throws Exception;
}