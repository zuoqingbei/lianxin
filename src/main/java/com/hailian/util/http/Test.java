package com.hailian.util.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//获取企业裁判文书
		JSONObject caipanjson = HttpTest.getJudgmentDoc("阿里巴巴(中国)网络技术有限公司","");//获取api企业信息数据
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
			for(int i=1;i<=totalpage;i++){
				JSONObject json = HttpTest.getJudgmentDoc("阿里巴巴(中国)网络技术有限公司",i+"");//获取api企业裁判文书信息数据
				System.out.println(json);
				JSONArray jsonArray = json.getJSONArray("Result");
				if(jsonArray !=null && jsonArray.size()>0){
					for(int j=0;j<jsonArray.size();j++){
						JSONObject judgmentdoc = (JSONObject)jsonArray.get(j);
						String Id = judgmentdoc.getString("Id");//执行法院
						String Court = judgmentdoc.getString("Court");//执行法院
						String CaseName = judgmentdoc.getString("CaseName");//裁判文书名字   案件名称
						String CaseNo = judgmentdoc.getString("CaseNo");//裁判文书编号   案件编号
						String CaseType = judgmentdoc.getString("CaseType");//裁判文书类型
						String SubmitDate = judgmentdoc.getString("SubmitDate");//发布时间
						String UpdateDate = judgmentdoc.getString("UpdateDate");//审判时间
						String IsProsecutor = judgmentdoc.getString("IsProsecutor");//是否原告
						String IsDefendant = judgmentdoc.getString("IsDefendant");//是否被告（供参考）
						String CourtYear = judgmentdoc.getString("CourtYear");//开庭时间年份
						String CaseRole = judgmentdoc.getString("CaseRole");//案件身份
						JSONObject judgmentdocdetail = HttpTest.getJudgmentDocDetail(Id);//获取api企业裁判文书详情
						if(judgmentdocdetail.getString("Status").equals("200")){
							String CaseReason=judgmentdocdetail.getJSONObject("Result").getString("CaseReason");//案由
							JSONArray defendantjson =judgmentdocdetail.getJSONObject("Result").getJSONArray("Defendantlist");//被告
							JSONArray prosecutorjson=judgmentdocdetail.getJSONObject("Result").getJSONArray("Prosecutorlist");//原告
						}
					}
				}
			}
		}
		JSONObject courtannouncement = HttpTest.getCourtAnnouncement("阿里巴巴(中国)网络技术有限公司","");//获取api企业法院公告
		String courtannouncementstatus = courtannouncement.getString("Status");
		if(courtannouncementstatus.equals("200")){
			JSONObject Paging = caipanjson.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			for(int i=1;i<=totalpage;i++){
				JSONObject CourtAnnouncementjson = HttpTest.getCourtAnnouncement("阿里巴巴(中国)网络技术有限公司",i+"");//获取api企业法院公告
				JSONArray jsonArray = CourtAnnouncementjson.getJSONArray("Result");
				for(int j=0;j<jsonArray.size();j++){
					JSONObject CourtAnnouncement = (JSONObject)jsonArray.get(j);
					String Id = CourtAnnouncement.getString("Id");//id
					String Party = CourtAnnouncement.getString("Party");//当事人
					String Category = CourtAnnouncement.getString("Category");//裁判文书
					String PublishedDate = CourtAnnouncement.getString("PublishedDate");//刊登日期
					String PublishedPage = CourtAnnouncement.getString("PublishedPage");//刊登版面
					String UploadDate = CourtAnnouncement.getString("UploadDate");//上传日期
					String Court = CourtAnnouncement.getString("Court");//公告人
					String Content = CourtAnnouncement.getString("Content");//内容
				}
			}
		}
		
		
		JSONObject courtnotice = HttpTest.getCourtNotice("阿里巴巴(中国)网络技术有限公司","");//获取api企业法院公告
		String courtnoticestatus = courtannouncement.getString("Status");
		if(courtannouncementstatus.equals("200")){
			JSONObject Paging = caipanjson.getJSONObject("Paging");
			int TotalRecords = Integer.parseInt(Paging.getString("TotalRecords"));
			int PageSize = Integer.parseInt(Paging.getString("PageSize"));
			int totalpage=1;
			if(TotalRecords%PageSize==0){
		           totalpage=TotalRecords/PageSize;
			}else{
		           totalpage=TotalRecords/PageSize+1;
			}
			for(int i=1;i<=totalpage;i++){
				JSONObject courtnoticejson = HttpTest.getCourtNotice("阿里巴巴(中国)网络技术有限公司",i+"");//获取api企业法院公告
				JSONArray jsonArray = courtnoticejson.getJSONArray("Result");
				for(int j=0;j<jsonArray.size();j++){
					JSONObject CourtAnnouncement = (JSONObject)jsonArray.get(j);
					String Id = CourtAnnouncement.getString("Id");//id
					String CaseNo = CourtAnnouncement.getString("CaseNo");//暗号
					String LianDate = CourtAnnouncement.getString("LianDate");//开庭日期
					String CaseReason = CourtAnnouncement.getString("CaseReason");//案由
					String Prosecutorlist = CourtAnnouncement.getString("Prosecutorlist");//原告/上诉人
					String Defendantlist = CourtAnnouncement.getString("Defendantlist");//被告/被上诉人
				}
			}
		}
		
		
	}

}
