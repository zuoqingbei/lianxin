/**
 * 数据统计
 */
var field="field";
//实验室数量统计
function labNumStatis(){
	$.post(contextPath+'/lab/labNumStatisAjax',{},function(data){
		$("#lab_all_count").html(data);
	})
}

//实验室区域数量统计：大洲 国家
function labAreaSpread(){
	$.post(contextPath+'/lab/labSpreadAjax',{},function(data){
		$("#area_num").html(data.areanum);
		$("#country_num").html(data.countrynum);
	})
}

//产线维度实验室数量统计
function proLineStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"product_code"},function(data){
		var htmls="";
		$.each(data,function(index,item){
			htmls+="<li>"+item.name+":"+item.count+"</li>";
		})
		$("#pro_line").html(htmls);
	})
}


//按照实验室四大类统计数量
function labTypeStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"lab_type_code"},function(data){
		var htmls="";
		$.each(data,function(index,item){
			htmls+="<li>"+item.name+":"+item.count+"</li>";
		})
		$("#lab_type_statis").html(htmls);
	})
}

//按照实验室实验室性质统计数量
function labPropertiesStatis(){
	$.post(contextPath+'/lab/labStatisByFiledAjax',{field:"properties_code"},function(data){
		var htmls="";
		$.each(data,function(index,item){
			htmls+="<li>"+item.name+":"+item.count+"</li>";
		})
		$("#properties_code_statis").html(htmls);
	})
}

//按照实验室生命周期（可开展实验）统计数量
function labLifeCycleStatis(){
	$.post(contextPath+'/lab/labCarryNumStatisAjax',{},function(data){
		var htmls="";
		$.each(data,function(index,item){
			htmls+="<li>"+item.name+":"+item.count+"</li>";
		})
		$("#kz_lab_code_statis").html(htmls);
	})
}

//实验室联通数据统计
function labLinkStatis(){
	$.post(contextPath+'/lab/labLinkAjax',{},function(data){
		$("#link_lab_all_count").html(data.all_num);
		$("#linked_status_num").html(data.link_num);
		$("#link_status_rate").html(data.link_rate);
	})
}