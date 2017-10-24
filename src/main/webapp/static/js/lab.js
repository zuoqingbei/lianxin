
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
var videoUlrInland = [
    "http://10.130.96.113:6713/mag/hls/85d598e47ce4411c9196e965385e895d/0/live.m3u8?time=New Date()",//本机
    // "http://192.168.1.168:6713/mag/hls/e99850d9e8fa40c88dd87bc184cd432a/1/live.m3u8?time=New Date()",//室外北侧
    // "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()"//CCTV
];
var videoUlrAbroad = [
    "http://10.130.96.113:6713/mag/hls/7329e487e5c84c41a1ba9040e89f7814/1/live.m3u8?time=New Date()",//泰国"RF-B"
    // "http://192.168.1.168:6713/mag/hls/4c00e3f243a5464798a54d6fdd57cc82/1/live.m3u8?time=New Date()",//泰国"IPdome"
    // "http://live.hkstv.hk.lxdns.com/live/hks/playlist.m3u8?time=New Date()",//香港卫视
    // "http://111.13.42.8/PLTV/88888888/224/3221225851/index.m3u8?time=New Date()",//CCTV
    // "http://192.168.1.168:6713/mag/hls/9d5be58b608c48fc8e71d09509b89ba9/0/live.m3u8?time=New Date()"//本机
];
var dataCenterMap=new Map();
//查询全部数据中心数据（包含层级关系）
function loadAllDataCenterAjax(){
	$.post(contextPath+"/lab/loadAllDataCenterAjax",{},function(data){
		// console.log(data)
		$(".inland").html(createDataCenterHtml(data,0));
		$(".abroad").html(createDataCenterHtml(data,1));
		//alert(dataCenterMap.get("1"));
	});
}
function createDataCenterHtml(data,dataType){
	//dataType:0：国内 1：国外
	var htmls="";
	var cuNum=0;
	$.each(data,function(index,item){
		if(item.data_type==dataType){
			var haschildren=false;
			if(item.haschildren==1){
				haschildren=true;
			}
			console.log("item",item)
			dataCenterMap.put(item.id,item);
			if(!haschildren){
				htmls+='<li data-centerid="'+item.id+'" ';
				if(cuNum==0){
					htmls+=' class=" noChildren active" ';
				}else{
					htmls+=' class=" noChildren " ';
				};
				cuNum++;
				//创建li响应方法
				htmls+=createClickFuntion(item);
				htmls+=' >'+item.center_name+'</li>';
			}else{
				htmls+='<li '
				if(cuNum==0){
					htmls+=' class=" hasChildren active" >';
				}else{
					htmls+=' class=" hasChildren " >';
				};
				cuNum++;
				htmls+=' <header class="fold">'+item.center_name+'<span>︿</span></header> ';
				htmls+='<ul>';
				dataCenterMap.put(item.id,item);
				$.each(item.children,function(index,cItem){
					dataCenterMap.put(cItem.id,cItem);
					htmls+='<li data-centerid="'+cItem.id+'" ';
					htmls+=createClickFuntion(cItem)+" >"+cItem.center_name+'</li>';
				});
				htmls+='</ul></li>';
			}
			
		}
	});
	return htmls;
	
}
//创建li响应方法
function createClickFuntion(item){
	var htmls="";
	/**
	 * 数据源 db-直连数据库； url-第三方链接；
		webservice-连接webservice；json-读取json文件
	 */
	// console.log("item",item)
	var dataSource=item.data_source;
	if(dataSource=="db"){ //国外曲线
		htmls+=" onclick= window.parent.loadLabUnitInfoCenterTabAjaxWorldHadoop('"+item.id+"','"+item.souce_value+"','"+item.data_type+"',this)"
	}else if(dataSource=="webservice"){
		//中海博睿
		htmls+=" onclick=loadLabUnitInfoCenterTabAjax('"+item.data_type+"',this) ";
	}else if(dataSource=="json"){
		//新西兰 日本读取json文件 国外曲线
		htmls+=" onclick= window.parent.loadLabUnitInfoCenterTabAjaxWorld('"+item.id+"','"+item.data_type+"',this) ";
	}else if(dataSource=="url"){
		htmls+=" onclick= intentsUrl('"+item.id+"',this)";
	}
	return htmls;
}
//直接跳转第三方地址
function intentsUrl(type,thiselem){
    var $mainNavLi = $(".labMainNav>.switchBox>ul>li.noChildren, .labMainNav>.switchBox>ul>li>ul>li");
    $mainNavLi.removeClass("active");
    $(thiselem).addClass("active");
    moduleMakersTabShow();
    var dataCenter=dataCenterMap.get(type);
	var url=dataCenter.souce_value;
    $(".lab_content_r>.switchBox>div.item").eq(5).find("iframe").attr("src",url);
	console.log("url",url)
}

var $lab_content_r = $(".lab_content_r");
var borderUrl ="";
function inlandTabShow() { //国内数据中心
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
    if(mark === "moduleMakers"){
        $(".lab_content_r>.switchBox>div.item").eq(5).show().siblings().hide();
    }else{
        $(".lab_content_r>.switchBox>div.item").eq(0).show().siblings().hide();

    }
}
// 视频加载方法
function videoShow(id,url) {
    var flashvars = {
        src: escape(url),
        plugin_m3u8: "../static/asserts/video/HLSProviderOSMF.swf",
        autoPlay : "true",
        autoSwitchQuality : "true"
    };
    var params = {
        // self-explained parameters
        allowFullScreen: true,
        allowScriptAccess: "always",
        quality:"low",
        bgcolor: "#000000"
    };
    var attrs = {
        name: "player"
    };
    swfobject.embedSWF(
        // url to SMP player
        "../static/asserts/video/StrobeMediaPlayback.swf",
        // div id where player will be place
        id,
        // width, height
        "100%", "100%",
        // minimum flash player version required
        "27",
        // other parameters
        null,
        flashvars,
        params,
        attrs
    )
    loadSwf(id,flashvars,params,attrs);

    /*    swfobject.embedSWF()的五个必须参数和四个可选参数：
    swfUrl（String，必须的）指定SWF的URL。
    id（String，必须的）指定将会被Flash内容替换的HTML元素（包含你的替换内容）的id。
    width（String，必须的）指定SWF的宽。
    height（String，必须的）指定SWF的高。
    version（String，必须的）指定你发布的SWF对应的Flash Player版本（格式为：major.minor.release）。
    expressInstallSwfurl（String，可选的）指定express install SWF的URL并激活Adobe express install [ http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=6a253b75 ]。
    flashvars（String，可选的）用name:value对指定你的flashvars。
    params（String，可选的）用name:value对指定你的嵌套object元素的params。
    attributes（String，可选的）用name:value对指定object的属性。
     */
}
function loadSwf(id,flashvars,params,attrs) {
    swfobject.embedSWF(
        // url to SMP player
        "../static/asserts/video/StrobeMediaPlayback.swf",
        // div id where player will be place
        id,
        // width, height
        "100%", "100%",
        // minimum flash player version required
        "27",
        // other parameters
        null,
        flashvars,
        params,
        attrs
    )
}





$(function () {
	loadAllDataCenterAjax();
    //中心实验室顶上的“返回总状态”和“中海博睿实验室”两个按钮
    $(".btn-totalStatus").click(function () {
        $("#r").show().siblings(".lab").hide();

        resetSizeRight();
    });

    $(".btn-labHome").click(function () {
        var borderUrl = $lab_content_r.css("background-image").replace(/[2-5]/, "1");
        $lab_content_r.css("background-image", borderUrl);
        $(".lab_content_r>.switchBox>div.item:eq(0)").show().siblings().hide();
    });

    //国内外切换
    var $navHeadLi = $(".labMainNav>header>ul>li");
    $navHeadLi.click(function () {
        $navHeadLi.removeClass("active");
        $(this).addClass("active");
        $(".labMainNav>.switchBox>ul:eq(" + $(this).index() + ")").show().siblings().hide();
        var borderUrl = "";
        if ($(this).index() === 0) { //国内
            inlandTabShow();
        } else {                    //国外
            abroadTabShow();
        }
    });

    //左侧菜单点击事件
    $(".labMainNav>.switchBox").on("click","ul>li.noChildren, ul>li>ul>li",function (){
        var $li = $(".switchBox>ul>li.noChildren, .switchBox>ul>li>ul>li");
        $li.removeClass("active");
        $(this).addClass("active");
        var $thisElem = $(this);
        $.post(contextPath+'/lab/loadVideosByDataCenterAjax?dataCenterId='+$thisElem.data("centerid"),function(data){
            console.log("data",data)
            var currentUrl = data[0].videos[0].videl_url;
            console.log("currentUrl",currentUrl);
            // console.log("$(this).parents(\".inland\")",$thisElem[0]);
            var videoId = "";

            //转成子码流可以流畅些
            currentUrl = currentUrl.replace("/0/live.m3u8","/1/live.m3u8")

            if($thisElem.parents(".inland").length>0){
                videoShow("videoBoxInland",currentUrl)
                console.log("videoBoxInland");
            }else{
                videoShow("videoBoxAbroad",currentUrl)
                console.log("videoBoxAbroad");
            }

        })
    })

    //非模块商的列表
    var $inlandLiNoModuleMakers = $(".labMainNav>.switchBox>ul>li.noChildren:not(.moduleMakers), .labMainNav>.switchBox>ul>li>ul>li");
    $inlandLiNoModuleMakers.click(function () {
        if ($(this).parents(".inland").length > 0) {
            inlandTabShow();
        } else {
            abroadTabShow();
        }
    });

    //二级菜单的折叠与展开
    $(".switchBox").on("click","li>header",function () {
        if ($(this).next("ul").is(":visible")) {
            $(this).next("ul").hide();
            $(this).removeClass("fold").children("span").text("﹀");
        } else {
            $(this).next("ul").show();
            $(this).addClass("fold").children("span").text("︿");
        }
    });

    //模块商
    var $moduleMakerLi = $(".labMainNav>.switchBox>ul>li.moduleMakersInland,.labMainNav>.switchBox>ul>li.moduleMakersAbroad");
    $moduleMakerLi.click(function () {
        moduleMakersTabShow();
    });

    //頂部子菜单点击事件
    var subNavLi = $(".labSubNav>ul>li");
    subNavLi.click(function () {
        subNavLi.removeClass("active");
        $(this).addClass("active");

        var borderUrl = $lab_content_r.css("background-image").replace(/[1-4]/, $(this).index() + 1);
        if (( $navHeadLi.eq(1).hasClass("active") && $(this).index() === 4 ) || $(this).index() === 5) { //国外的曲线监控或模块商的互联页面
            borderUrl = $lab_content_r.css("background-image").replace(/[1-4]/, 2);
        }
        $lab_content_r.css("background-image", borderUrl);

        $(".lab_content_r>.switchBox>div.item").eq($(this).index()).show().siblings().hide();
        echartsResize();

        if ($(this).text().indexOf("实时监测") > -1) {
            echartsResizeWorld();
            if($(this).hasClass("centerCurves")){
                videoShow("smallVideoInland",videoUlrInland[0]);
            }else {
                videoShow("smallVideoAbroad",videoUlrAbroad[1]);
            }
        }
    });

    //实时监测中的视频和曲线切换
    $(".sheshi_tab").click(function () {
        var index = $(this).index();
        $(this).addClass("sheshi_tab_active").siblings().removeClass("sheshi_tab_active");
        $(this).parents(".monitoring").find(".shishi_right>.item").eq(index).show().siblings().hide();
    });

    function videoLayout(videoUlr,from) {
        var $monitoring = $(".monitoring");
        var $layoutBox = (from === "inland" ? $monitoring.eq(0).find(" .video_box > div"):$monitoring.eq(1).find(" .video_box > div"));
        var n = videoUlr.length;
        // console.log("videoUlr.length", n);
        if (n < 2) {
            $layoutBox.removeClass().addClass("video_1x1");
        } else if (n < 5) {
            $layoutBox.removeClass().addClass("video_2x2");
        } else if (n < 10) {
            $layoutBox.removeClass().addClass("video_3x3");
        } else {
            $layoutBox.removeClass().addClass("video_4x4");
        }
        var videoHtml = "";
        for (var i = 1; i <= n; i++) {
            videoHtml += '<div ><div id="video' + i + from + '"></div></div>';
            videoShow("video"+i+from,videoUlr[i-1]);
        }
        $layoutBox.eq(0).html(videoHtml);
    }

    videoLayout(videoUlrInland,"inland");
    videoLayout(videoUlrAbroad,"abroad");

    //画中画的视频隐藏显示
    $(".smallVideoBox>.hideShow").click(function () {
        var $video = $(this).next();
        if($video.is(":visible")){
            $video.hide();
            $(this).children(".text").text("显示小视频");
            $(this).children(".icon").css("background-image","url(../static/img/lab/browse.png)")
        }else{
            $video.show();
            $(this).children(".text").text("点击隐藏");
            $(this).children(".icon").css("background-image","url(../static/img/lab/close.png)")
        }
    });
    
    // 数据分析中的合格率、及时率、满意度
    initThree();//合格率
    initfour();//及时率
    inittwo();//满意度

    labAllForCenterLabAjax();
})
