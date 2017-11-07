//导航选中切换

function Map() {
    var struct = function (key, value) {
        this.key = key;
        this.value = value;
    }
    var put = function (key, value) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                this.arr[i].value = value;
                return;
            }
        }
        this.arr[this.arr.length] = new struct(key, value);
    }
    var get = function (key) {
        for (var i = 0; i < this.arr.length; i++) {
            if (this.arr[i].key === key) {
                return this.arr[i].value;
            }
        }
        return null;
    }
    var remove = function (key) {
        var v;
        for (var i = 0; i < this.arr.length; i++) {
            v = this.arr.pop();
            if (v.key === key) {
                continue;
            }
            this.arr.unshift(v);
        }
    }
    var size = function () {
        return this.arr.length;
    }
    var isEmpty = function () {
        return this.arr.length <= 0;
    }
    this.arr = new Array();
    this.get = get;
    this.put = put;
    this.remove = remove;
    this.size = size;
    this.isEmpty = isEmpty;
}

function echartsResize() {
    chartone.resize();
    charttwo.resize();
    chartthree.resize();
    chartfour.resize();
    chartfive.resize();
    chartsix.resize();
}


var dataCenterMap = new Map();//数据中心
var labsMap = new Map();//实验室
var labsHtmlsMap = new Map();//实验室HTMLS
//查询全部数据中心数据（包含层级关系）
function loadAllDataCenterAjax() {
    $.post(contextPath + "/lab/loadAllDataCenterAjax", {}, function (data) {
        // console.log(data)
        $(".inland").html(createDataCenterHtml(data, 0));
        $(".abroad").html(createDataCenterHtml(data, 1));
        $(".inland>li:eq(0)").trigger("click");
        //alert(dataCenterMap.get("1"));
    });
}

function createDataCenterHtml(data, dataType) {
    //dataType:0：国内 1：国外
    var htmls = "";
    var cuNum = 0;
    $.each(data, function (index, item) {
        if (item.data_type == dataType) {
            var haschildren = false;
            if (item.haschildren == 1) {
                haschildren = true;
            }
            // console.log("item",item)
            dataCenterMap.put(item.id, item);
            if (!haschildren) {
                htmls += '<li data-centerid="' + item.id + '" ';
                if (cuNum == 0) {
                    htmls += ' class=" noChildren active" ';
                } else {
                    htmls += ' class=" noChildren " ';
                }
                ;
                cuNum++;
                //创建li响应方法
                htmls += createClickFuntionForDataCenter(item);
                htmls += ' >' + item.center_name + '</li>';
            } else {
                htmls += '<li '
                if (cuNum == 0) {
                    htmls += ' class=" hasChildren active" >';
                } else {
                    htmls += ' class=" hasChildren " >';
                }
                ;
                cuNum++;
                // htmls+=' <header class="fold">'+item.center_name+'<span>︿</span></header> ';
                htmls += ' <header class="fold">' + item.center_name + '<span>∧</span></header> ';
                htmls += '<ul>';
                dataCenterMap.put(item.id, item);
                $.each(item.children, function (index, cItem) {
                    dataCenterMap.put(cItem.id, cItem);
                    htmls += '<li data-centerid="' + cItem.id + '" ';
                    htmls += createClickFuntionForDataCenter(cItem) + " ><span class='icon'></span> " + cItem.center_name + '</li>';
                });
                htmls += '</ul></li>';
            }

        }
    });
    return htmls;

}

//创建数据中心li响应方法
function createClickFuntionForDataCenter(item) {
    var htmls = "";
    var dataSource = item.data_source;
    //如果是在数据中心配置的为url则直接跳转，否则根据数据中心再去查询对应实验室level为3(单位/产品) 4（模块/整机）
    if (dataSource === "url") {
        htmls += " data-urltype =" + item.id + " ";
    } else {
        htmls += " onclick= loadAllDataCenterLabAjaxFunc('" + item.id + "') ";
    }
    return htmls;
}

function setCenterLabHtmlDB(dataCenter) {
    $(".labMain_cblt_tone_world").html("<p style:'font-size:1.3em'>" + dataCenter.center_desc + "</p>");
    $(".labMain_cblt_ttwo_world img").attr("src", dataCenter.img_content);
    $("#labName_world").html(dataCenter.center_name);
    $("#labnameIcon_world").html(dataCenter.center_name);
    $("#secondName_world").html(dataCenter.center_name);
}

//查询数据中心下实验室 level为3(单位/产品)
function loadAllDataCenterLabAjaxFunc(dataCenterId) {
    var dataCenter = dataCenterMap.get(dataCenterId);
    var parentDataCenter = dataCenterMap.get(dataCenter.parent_id);
    var data_type = dataCenter.data_type;
    var data_source = dataCenter.data_source;
    //判断出现那块DIV（国内 国外）
   /* if (data_type == 0) {
        if (data_source == "webservice") {
            // inlandTabShow();
        } else {
            setCenterLabHtmlDB(parentDataCenter);
            // inlandTabShow_world();
        }
    } else {
        if (data_source == "webservice") {
            // abroadTabShow();
        } else {
            setCenterLabHtmlDB(parentDataCenter);
            // abroadTabShow();
        }
    }*/
    setCenterLabHtmlDB(dataCenter);
    //加载数据中心第三级
    $.post(contextPath + "/lab/loadAllDataCenterLabAjax", {"dataCenterId": dataCenterId}, function (data) {
        var html = '';
        var firstLabCode = "";
        $.each(data, function (index, item) {
            var dataSource = item.data_source;
            dataCenterMap.put(item.id, item);
            if (dataSource == "url") {
                html += '<li  data-center-id="' + item.id + '"  class="toLabIframe quxian_li_' + item.id + '" data-url="' + item.souce_value + '"><header>' + (item.isshow_name == 0 ? item.center_name : "") + '</header></li>';
            } else {
                //生成实验室
            	if(item.data_source=="webservice"){
            		html += '<li class="quxian_li_' + item.id + '" '+ createClickFuntion(item)+' data-center-id="' + item.id + '"  ><header>' + (item.isshow_name == 0 ? item.center_name : "") + '<span>∨</span></header>';
            		html += '</li>';
            	}else{
            		html += '<li class="quxian_li_' + item.id + '" data-center-id="' + item.id + '"  ><header>' + (item.isshow_name == 0 ? item.center_name : "") + '<span>∨</span></header>';
            		var labsHtmls = "<ul>";
            		$.each(item.children, function (ind, it) {
            			if (index == 0 && ind == 0) {
            				firstLabCode = it.lab_code+"_"+it.id;
            			}
            			if(it.data_source!="webservice"){
            				var currentHtmls = ' <li class="lab_code_' + it.lab_code + '_'+it.id;
            				var header = '<header labcode="' + it.lab_code + '"  ' + createClickFuntion(it) + '>' + it.lab_name ;
            				if(it.data_source=="url"){
            					currentHtmls+=' toLabIframe " data-url="'+it.souce_value+'">';
            					header+='</header>';
            				}else{
            					currentHtmls+=' ">';
            					header+= '<span>∨</span></header>';
            				}
            				labsMap.put(it.id, it);
            				labsHtmlsMap.put(it.id, '<header labcode="' + it.lab_code + '"  ' + createClickFuntion(it) + '>' + it.lab_name + '<span>∧</span></header>');
            				//如果是中海博睿 不拼接header
            				labsHtmls = labsHtmls + currentHtmls + header + "</li>";
            			}else if(it.data_source=="webservice"){
            				//直接获取webservice实验室信息
            				var htmls="";
            				 $.post(contextPath+'/lab/loadLabUnitInfoCenterTabAjax',{},function(data){

         				        $.each(data,function(index,item){
         				        	htmls+=' <li><header>'+item.labName+'<span>∨</span></header>';
         							if(item.testUnitList.length>0){
         								htmls+='<ul class="taiwei_hide">';
         								$.each(item.testUnitList,function(ind,it){
         									if(it.testUnitStatus=="停测"){
         										htmls+='<li>台位：'+it.testUnitName+'  ('+it.testUnitStatus+')</li>';
         									}else{
         										htmls+='<li onclick=findSensorByLabCenetrTabAjax(\"'+item.labCode+'\",\"'+item.url+'\",\"'+it.testUnitId+'\")>台位：'+it.testUnitName+'  ('+it.testUnitStatus+')</li>';
         									}
         								});
         								htmls+='</ul>';
         							}
         							htmls+=' </li>';
         						});
         				        $(".quxian_li_"+item.id).find("ul:eq(0)").append(htmls);
         				 });
            				
            				///
            			}
            		});
            		labsHtmls += '</ul>';
            		html += labsHtmls;
            		html += '</li>';
            	}

            }
        });
        $("#lab_unit_selected_center").html(html);
        $("#lab_unit_selected_center_world").html(html);
        $(".lab_code_" + firstLabCode).find("header").trigger("click");

    });

}

//创建li响应方法
function createClickFuntion(item) {
    var htmls = "";
    /**
     * 数据源 db-直连数据库； url-第三方链接；
     webservice-连接webservice；json-读取json文件
     */
        // console.log("item",item)
    var dataSource = item.data_source;
    if (dataSource == "db") { //国外曲线
        htmls += " onclick= loadLabUnitInfoCenterTabAjaxWorldHadoop('" + item.id + "')"
    } else if (dataSource == "webservice") {
        //中海博睿
        htmls += " onclick=loadLabUnitInfoCenterTabAjax('" + item.id + "') ";
    } else if (dataSource == "json") {
        //新西兰 日本读取json文件 国外曲线
        htmls += " onclick= window.parent.loadLabUnitInfoCenterTabAjaxWorld('" + item.id + "') ";
    }
    return htmls;
}
