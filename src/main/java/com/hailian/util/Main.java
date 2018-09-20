package com.hailian.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main {
	private static String latestVocationName="";
	public static String getVocationName(DomNodeList<HtmlElement> htmlElements, String date) throws ParseException{
	String rst = "";
	boolean pastTimeFlag = false;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date paramDate = dateFormat.parse(date);
	if(new Date().getTime() >= paramDate.getTime()){
	pastTimeFlag = true;
	}
	//first step //jugde if can get vocation name from html page
	for(int i = 0; i < htmlElements.size(); i++){
	HtmlElement element = htmlElements.get(i);
	if(element.getAttribute("class").indexOf("vacation")!=-1){
	boolean hitFlag = false;
	String voationName = "";
	for(; i < htmlElements.size(); i++){
	HtmlElement elementTmp = htmlElements.get(i);
	String liDate = elementTmp.getAttribute("date");
	List<HtmlElement> lunar = elementTmp.getElementsByAttribute("span", "class", "lunar");
	String lanarText = lunar.get(0).asText();
	if(lanarText.equals("元旦")){
	voationName = "元旦";
	}else if(lanarText.equals("除夕")||lanarText.equals("春节")){
	voationName = "春节";
	}else if(lanarText.equals("清明")){
	voationName = "清明";
	}else if(lanarText.equals("国际劳动节")){
	voationName = "国际劳动节";
	}else if(lanarText.equals("端午节")){
	voationName = "端午节";
	}else if(lanarText.equals("中秋节")){
	voationName = "中秋节";
	}else if(lanarText.equals("国庆节")){
	voationName = "国庆节";
	}
	if(liDate.equals(date)){
	hitFlag = true;
	}
	if(elementTmp.getAttribute("class").indexOf("vacation")==-1){
	break;
	}
	}
	if(hitFlag == true && !voationName.equals("")){
	rst = voationName;
	break;
	}
	}else{
	continue;
	}
	}
	//if first step fail(rarely), get from the latest Vocation name
	if(rst.equals("")){
	System.out.println("warning: fail to get vocation name from html page.");
	//you can judge by some simple rule 
	//from the latest Vocation name
	rst = Main.latestVocationName;
	}else if(pastTimeFlag == true){
	//更新《当前时间，且最近一次的可见的假期名
	Main.latestVocationName = rst;
	}
	return rst;
	}
	
	public static List<ChinaDate> getCurrentDateInfo(){
	WebClient webClient = null;
	List<ChinaDate> dateList = null;
	try{
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	dateList = new ArrayList<ChinaDate>();
	webClient = new WebClient();
	HtmlPage page = webClient.getPage("http://hao.360.cn/rili/");
	//最大等待60秒
	for(int k = 0; k < 60; k++){
	if(!page.getElementById("M-dates").asText().equals("")) break;
	Thread.sleep(1000);
	}
	//睡了8秒，等待页面加载完成...，有时候，页面可能获取不到，不稳定（）
	//Thread.sleep(8000);
	DomNodeList<HtmlElement> htmlElements = page.getElementById("M-dates").getElementsByTagName("li");
	//System.out.println(htmlElements.size());
	for(HtmlElement element : htmlElements){
	ChinaDate chinaDate = new ChinaDate();
	List<HtmlElement> lunar = element.getElementsByAttribute("span", "class", "lunar");
	List<HtmlElement> solar = element.getElementsByAttribute("div", "class", "solar");
	chinaDate.setLunar(lunar.get(0).asText());
	chinaDate.setSolar(solar.get(0).asText());
	chinaDate.setSolarDate(dateFormat.parse(element.getAttribute("date")));
	if(element.getAttribute("class").indexOf("vacation")!=-1){
	chinaDate.setVacation(true);
	chinaDate.setVacationName(getVocationName(htmlElements, element.getAttribute("date")));
	}
	if(element.getAttribute("class").indexOf("weekend")!=-1 && 
	element.getAttribute("class").indexOf("last")==-1){
	chinaDate.setSaturday(true);
	}
	if(element.getAttribute("class").indexOf("last weekend")!=-1){
	chinaDate.setSunday(true);
	}
	if(element.getAttribute("class").indexOf("work")!=-1){
	chinaDate.setWorkFlag(true);
	}else if(chinaDate.isSaturday() == false &&
	chinaDate.isSunday() == false && 
	chinaDate.isVacation() == false ){
	chinaDate.setWorkFlag(true);
	}else{
	chinaDate.setWorkFlag(false);
	}
	dateList.add(chinaDate);
	}
	}catch(Exception e){
	e.printStackTrace();
	System.out.println("get date from http://hao.360.cn/rili/ error~");
	}finally{
	webClient.close();
	}
	return dateList;
	}
	
	public ChinaDate getTodayInfo(){
	List<ChinaDate> dateList = getCurrentDateInfo();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	for(ChinaDate date: dateList){
	if(dateFormat.format(date.getSolarDate()).equals(dateFormat.format(new Date()))){
	return date;
	}
	}
	return new ChinaDate();
	}
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
	List<ChinaDate> dateList = getCurrentDateInfo();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	for(ChinaDate date: dateList){
	System.out.println(dateFormat.format(date.getSolarDate()));
	}

	}
	}
