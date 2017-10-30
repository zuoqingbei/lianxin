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
    myChart1.resize();
    myChart2.resize();
}


var dataCenterMap = new Map();

//查询全部数据中心数据（包含层级关系）
function loadAllDataCenterAjax() {
    $.post(contextPath + "/lab/loadAllDataCenterAjax", {}, function (data) {
        // console.log(data)
        $(".inland").html(createDataCenterHtml(data, 0));
        $(".abroad").html(createDataCenterHtml(data, 1));
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
                    htmls += createClickFuntionForDataCenter(cItem) + " >" + cItem.center_name + '</li>';
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
    if (dataSource == "url") {
        htmls += " onclick= intentsUrl('" + item.id + "')";
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

//查询数据中心下实验室 level为3(单位/产品) 4（模块/整机）
function loadAllDataCenterLabAjaxFunc(dataCenterId) {
    var dataCenter = dataCenterMap.get(dataCenterId);
    var data_type = dataCenter.data_type;
    var data_source = dataCenter.data_source;
    if (data_type == 0) {
        //alert(data_source+"--"+data_type+"--"+dataCenter.center_name)
        if (data_source == "db" || data_source == "json") {
            setCenterLabHtmlDB(dataCenter);
            inlandTabShow_world();
        } else if (data_source == "webservice") {
            inlandTabShow();
        }
    } else {
        //alert(data_source+"--"+data_type+"--"+dataCenter.center_name)
        if (data_source == "db" || data_source == "json") {
            setCenterLabHtmlDB(dataCenter);
            abroadTabShow();
        } else if (data_source == "webservice") {
            abroadTabShow_center();
        }
    }
    $.post(contextPath + "/lab/loadAllDataCenterLabAjax", {"dataCenterId": dataCenterId}, function (data) {
        var html = '';
        $.each(data, function (index, item) {
            var dataSource = item.data_source;
            dataCenterMap.put(item.id, item);
            console.log(item.isshow_name)
            if (dataSource == "url") {
                html += '<li  data-center-id="' + item.id + '"  class="toLabIframe quxian_li_' + item.id + '" data-url="' + item.souce_value + '"><header ' + createClickFuntion(item) + '>' + (item.isshow_name == 0 ? item.center_name : "") + '</header></li>';
            } else {
                html += '<li class="quxian_li_' + item.id + '" data-center-id="' + item.id + '"  ><header ' + createClickFuntion(item) + '>' + (item.isshow_name == 0 ? item.center_name : "") + '</header></li>';
            }
        });
        $("#lab_unit_selected_center").html(html);
        $("#lab_unit_selected_center_world").html(html);
        /*
                $(".sheshi_tab_list>ul>li.toLabIframe").on("click", function () {
                    $(this).addClass("active");
                    $(this).parents(".monitoring").find(".shishi_right>.item.iframe").show().siblings().hide();
                    // videoShow("smallVideoInlandWeb", $(this).attr("data-url"));
                    // videoShow("videoBoxInland", $(this).attr("data-url"));
                    $("#lab_iframe_video").show();
                    $("#lab_iframe_video").attr("src", $(this).attr("data-url"))
                });
        */
        $.each(data, function (index, item) {
            if (index == 0) {
                var dataSource = item.data_source;
                if (dataSource === "db") { //国外曲线
                    window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop(item.id, item.souce_value, item.data_type)
                } else if (dataSource === "webservice") {
                    //中海博睿
                    loadLabUnitInfoCenterTabAjax(item.data_type);
                } else if (dataSource === "json") {
                    //新西兰 日本读取json文件 国外曲线
                    window.parent.loadLabUnitInfoCenterTabAjaxWorld(item.id, item.data_type);
                } else if (dataSource === "url") {
                    $("#lab_unit_selected_center").find("li").eq(0).find("header").trigger("click");
                    $("#lab_unit_selected_center_world").find("li").find("header").eq(0).trigger("click");
                }
                // $("#lab_unit_selected_center_world").find("li").eq(0).find("header").eq(0).trigger("click");
            }
        });
        //自动触发第一个实验室的第一个台位

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
        htmls += " onclick= loadLabUnitInfoCenterTabAjaxWorldHadoop('" + item.id + "','" + item.souce_value + "','" + item.data_type + "')"
    } else if (dataSource == "webservice") {
        //中海博睿
        htmls += " onclick=loadLabUnitInfoCenterTabAjax('" + item.id + "','" + item.data_type + "') ";
    } else if (dataSource == "json") {
        //新西兰 日本读取json文件 国外曲线
        htmls += " onclick= window.parent.loadLabUnitInfoCenterTabAjaxWorld('" + item.id + "','" + item.data_type + "') ";
    }
    /*else if (dataSource == "url") {
           htmls += " onclick= intentsUrl('" + item.id + "')";
       }*/
    return htmls;
}

//直接跳转第三方地址
function intentsUrl(type, thiselem) {
    var $mainNavLi = $(".labMainNav>.switchBox>ul>li.noChildren, .labMainNav>.switchBox>ul>li>ul>li");
    $mainNavLi.removeClass("active");
    $(thiselem).addClass("active");
    moduleMakersTabShow();
    var dataCenter = dataCenterMap.get(type);
    var url = dataCenter.souce_value;
    $(".lab_content_r>.switchBox>div.item").eq(5).find("iframe").attr("src", url);
    console.log("url", url)
}

var $lab_content_r = $(".lab_content_r");
var borderUrl = "";

function inlandTabShow() { //国内webservice
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.centerCurves").show();
}

function inlandTabShow_world() {//国内Hadoop
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoardInland_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.status,.labSubNav>ul>li.analysis,.labSubNav>ul>li.abroadCurves").show();
}

function abroadTabShow() {//国外Hadoop
    $(".lab_content_r").css("background-image", "url(../static/img/lab/labTabBoardForeign_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.abroadCurves").show();
}

function abroadTabShow_center() {//国外数据中心
    $(".lab_content_r").css("background-image", "url(../static/img/lab/labTabBoardForeign_1.png)");
    labNavAndItemShow();
    $(".labSubNav>ul>li.labHome,.labSubNav>ul>li.centerCurves").show();
}

function moduleMakersTabShow() { //模块商
    $lab_content_r.css("background-image", "url(../static/img/lab/labTabBoard_onlyOne.png");
    labNavAndItemShow("moduleMakers");
    $(".labSubNav>ul>li.moduleMakers").show();
}

function labNavAndItemShow(mark) {
    $(".labSubNav>ul>li").hide();
    if (mark === "moduleMakers") {
        $(".lab_content_r>.switchBox>div.item").eq(5).show().siblings().hide();
    } else {
        $(".lab_content_r>.switchBox>div.item").eq(0).show().siblings().hide();

    }
}
