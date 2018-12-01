package com.hailian.util.http;

import java.util.ArrayList;
import java.util.List;

import com.hailian.modules.admin.ordermanager.model.CreditCompanyCourtannouncement;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyJudgmentdoc;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyShareholder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONObject courtannouncement = HttpTest.getCourtAnnouncement("阿里巴巴(中国)网络技术有限公司","");//获取api企业法院公告
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
			for(int i=1;i<=totalpage;i++){
				JSONObject CourtAnnouncementjson = HttpTest.getCourtAnnouncement("阿里巴巴(中国)网络技术有限公司",i+"");//获取api企业法院公告
				JSONArray jsonArray = CourtAnnouncementjson.getJSONArray("Result");
				 List<CreditCompanyCourtannouncement> list2 = JSONArray.toList(jsonArray, new CreditCompanyCourtannouncement(), new JsonConfig());//参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
				 List list3 = (List)JSONArray.toCollection(jsonArray, CreditCompanyCourtannouncement.class);  
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
