(function(a){a.SewisePlayerSkin={version:"1.0.0"};a.SewisePlayer=a.SewisePlayer||{}})(window);(function(a){a.SewisePlayer.IVodPlayer=a.SewisePlayer.IVodPlayer||{play:function(){},pause:function(){},stop:function(){},seek:function(){},changeClarity:function(){},setVolume:function(){},toPlay:function(){},duration:function(){},playTime:function(){},type:function(){},showTextTip:function(){},hideTextTip:function(){},muted:function(){},bufferProgress:function(){}}})(window);(function(a){a.SewisePlayerSkin.IVodSkin=a.SewisePlayerSkin.IVodSkin||{player:function(){},started:function(){},paused:function(){},stopped:function(){},seeking:function(){},buffering:function(){},bufferProgress:function(){},loadedProgress:function(){},loadedOpen:function(){},loadedComplete:function(){},programTitle:function(){},duration:function(){},playTime:function(){},startTime:function(){},loadSpeed:function(){},initialClarity:function(){},lang:function(){},logo:function(){},timeUpdate:function(){},
volume:function(){},clarityButton:function(){},timeDisplay:function(){},controlBarDisplay:function(){},topBarDisplay:function(){},customStrings:function(){},customDatas:function(){},fullScreen:function(){},noramlScreen:function(){},initialAds:function(){},initialStatistics:function(){}}})(window);(function(){SewisePlayerSkin.Utils={stringer:{time2FigFill:function(a){var b,a=Math.floor(a);10>a?b="0"+a:b=""+a;return b},secondsToHMS:function(a){if(!(0>a)){var b=this.time2FigFill(Math.floor(a/3600)),c=this.time2FigFill(a/60%60),a=this.time2FigFill(a%60);return b+":"+c+":"+a}},secondsToMS:function(a){if(!(0>a)){var b=this.time2FigFill(a/60),a=this.time2FigFill(a%60);return b+":"+a}},dateToTimeString:function(a){var b;b=a?a:new Date;var a=b.getFullYear(),c=b.getMonth()+1,j=b.getDate(),f=this.time2FigFill(b.getHours()),
h=this.time2FigFill(b.getMinutes());b=this.time2FigFill(b.getSeconds());return a+"-"+c+"-"+j+" "+f+":"+h+":"+b},dateToTimeStr14:function(a){var b=a.getFullYear(),c=this.time2FigFill(a.getMonth()+1),j=this.time2FigFill(a.getDate()),f=this.time2FigFill(a.getHours()),h=this.time2FigFill(a.getMinutes()),a=this.time2FigFill(a.getSeconds());return b+c+j+f+h+a},dateToStrHMS:function(a){var b=this.time2FigFill(a.getHours()),c=this.time2FigFill(a.getMinutes()),a=this.time2FigFill(a.getSeconds());return b+
":"+c+":"+a},dateToYMD:function(a){var b=a.getFullYear(),c=this.time2FigFill(a.getMonth()+1),a=this.time2FigFill(a.getUTCDate());return b+"-"+c+"-"+a},hmsToDate:function(a){var a=a.split(":"),b=new Date;b.setHours(a[0]?a[0]:0,a[1]?a[1]:0,a[2]?a[2]:0);return b},hmsToSeconds:function(a){var b=a.split(":"),a=b[0]?parseInt(b[0]):0,c=b[1]?parseInt(b[1]):0,b=b[2]?parseInt(b[2]):0;return 3600*a+60*c+b}},language:{zh_cn:{stop:"\u505c\u6b62\u64ad\u653e",pause:"\u6682\u505c",play:"\u64ad\u653e",fullScreen:"\u5168\u5c4f",
normalScreen:"\u6062\u590d",soundOn:"\u6253\u5f00\u58f0\u97f3",soundOff:"\u5173\u95ed\u58f0\u97f3",clarity:"\u6e05\u6670\u5ea6",titleTip:"\u6b63\u5728\u64ad\u653e\uff1a",claritySetting:"\u6e05\u6670\u5ea6\u8bbe\u7f6e",clarityOk:"\u786e\u5b9a",clarityCancel:"\u53d6\u6d88",backToLive:"\u56de\u5230\u76f4\u64ad",loading:"\u7f13\u51b2",subtitles:"\u5b57\u5e55","default":"\u9ed8\u8ba4"},en_us:{stop:"Stop",pause:"Pause",play:"Play",fullScreen:"Full Screen",normalScreen:"Normal Screen",soundOn:"Sound On",
soundOff:"Sound Off",clarity:"Clarity",titleTip:"Playing: ",claritySetting:"Definition Setting",clarityOk:"OK",clarityCancel:"Cancel",backToLive:"Back To Live",loading:"Loading",subtitles:"Subtitles","default":"Default"},lang:{},init:function(a){switch(a){case "en_US":this.lang=this.en_us;break;case "zh_CN":this.lang=this.zh_cn;break;default:this.lang=this.zh_cn}},getString:function(a){return this.lang[a]}}}})();(function(a){SewisePlayerSkin.BannersAds=function(b,c){function j(){v=SewisePlayerSkin.Utils.stringer.dateToYMD(new Date);l=c[v]||c["0000-00-00"];i=0;if(void 0!=l)o=l.length}function f(){for(var a=(new Date).getTime(),b=0;b<o;b++){var c=SewisePlayerSkin.Utils.stringer.hmsToDate(l[b].time).getTime();if(b<o-1){var w=SewisePlayerSkin.Utils.stringer.hmsToDate(l[b+1].time).getTime();if(a>c&&a<w){i=b;g(i);break}}else if(a>c){i=b;g(i);break}}}function h(){var a=(new Date).getTime();if(i<o-1){var b=SewisePlayerSkin.Utils.stringer.hmsToDate(l[i+
1].time).getTime();a>b&&(i++,g(i))}else SewisePlayerSkin.Utils.stringer.dateToYMD(new Date)!=v&&(j(),f())}function g(a){p=l[a].ads[0].picurl;q=l[a].ads[1].picurl;""==p&&""==q||(""==p&&""!=q?p=q:""!=p&&""==q&&(q=p),k.attr("src",p),w.attr("src",q))}var d=a(' <div style="position:absolute; display:table; width:100%; height:100%;"><div style="display:table-cell; text-align:left; vertical-align:middle;"><img style="pointer-events:auto; cursor:pointer; width: auto; height: auto;"></div></div> ');d.appendTo(b);
var k=d.find("img"),d=a(' <div style="position:absolute; display:table; width:100%; height:100%;"><div style="display:table-cell; text-align:right; vertical-align:middle;"><img style="pointer-events:auto; cursor:pointer; width: auto; height: auto;"></div></div> ');d.appendTo(b);var w=d.find("img"),p,q,v,l,i,o;j();void 0!=l&&(1==o&&""==l[0].time?g(0):(f(),setInterval(h,1E4)),k.click(function(a){a.originalEvent.stopPropagation();a=l[i].ads[0].link_url;window.openAdsLink&&"function"==typeof window.openAdsLink?
window.openAdsLink(a):window.open(a,"_blank")}),w.click(function(a){a.originalEvent.stopPropagation();a=l[i].ads[1].link_url;window.openAdsLink&&"function"==typeof window.openAdsLink?window.openAdsLink(a):window.open(a,"_blank")}))}})(window.jQuery);(function(a){SewisePlayerSkin.AdsContainer=function(b,c){var j=b.$container,f=b.$sewisePlayerUi,h=c.banners;if(h){var g=a("<div class='sewise-player-ads-banner'></div>");g.css({position:"absolute",width:"100%",height:"100%",left:"0px",top:"0px",overflow:"hidden","pointer-events":"none"});g.appendTo(j);f.css("z-index",1);SewisePlayerSkin.BannersAds(g,h)}}})(window.jQuery);(function(a){SewisePlayerSkin.Statistics=function(b){function c(b){var c=b["request-url"],h=b["request-data"];setInterval(function(){a.ajax({type:"post",async:!1,dataType:"json",url:c,data:h,success:function(){},error:function(){console.log("play count ajax request fail!")}})},b["request-data"].intervallen?b["request-data"].intervallen:1E4)}(b=b.playCount)&&c(b)}})(window.jQuery);(function(a){SewisePlayerSkin.ElementObject=function(){this.$sewisePlayerUi=a(".sewise-player-ui");this.$container=this.$sewisePlayerUi.parent();this.$video=this.$container.find("video").get(0);this.$controlbar=this.$sewisePlayerUi.find(".controlbar");this.$playBtn=this.$sewisePlayerUi.find(".controlbar-btns-play");this.$pauseBtn=this.$sewisePlayerUi.find(".controlbar-btns-pause");this.$fullscreenBtn=this.$sewisePlayerUi.find(".controlbar-btns-fullscreen");this.$normalscreenBtn=this.$sewisePlayerUi.find(".controlbar-btns-normalscreen");
this.$soundopenBtn=this.$sewisePlayerUi.find(".controlbar-btns-soundopen");this.$soundcloseBtn=this.$sewisePlayerUi.find(".controlbar-btns-soundclose");this.$shareBtn=this.$sewisePlayerUi.find(".controlbar-btns-share");this.$playtime=this.$sewisePlayerUi.find(".controlbar-playtime");this.$controlBarProgress=this.$sewisePlayerUi.find(".controlbar-progress");this.$progressPlayedLine=this.$sewisePlayerUi.find(".controlbar-progress-playedline");this.$progressPlayedPoint=this.$sewisePlayerUi.find(".controlbar-progress-playpoint");
this.$progressLoadedLine=this.$sewisePlayerUi.find(".controlbar-progress-loadedline");this.$progressSeekLine=this.$sewisePlayerUi.find(".controlbar-progress-seekline");this.$logo=this.$sewisePlayerUi.find(".logo");this.$logoIcon=this.$sewisePlayerUi.find(".logo-icon");this.$topbar=this.$sewisePlayerUi.find(".topbar");this.$programTip=this.$sewisePlayerUi.find(".topbar-program-tip");this.$programTitle=this.$sewisePlayerUi.find(".topbar-program-title");this.$topbarClock=this.$sewisePlayerUi.find(".topbar-clock");
this.$buffer=this.$sewisePlayerUi.find(".buffer");this.$bufferTip=this.$sewisePlayerUi.find(".buffer-text-tip");this.$bigPlayBtn=this.$sewisePlayerUi.find(".big-play-btn");if(!SewisePlayerSkin.defStageWidth)SewisePlayerSkin.defStageWidth=this.defStageWidth=this.$container.width(),SewisePlayerSkin.defStageHeight=this.defStageHeight=this.$container.height()}})(window.jQuery);(function(a){SewisePlayerSkin.ElementLayout=function(b){var c=b.$container,j=b.$controlBarProgress,f=b.$playtime,h=this,g=b.defStageWidth,d=b.defStageHeight,k=parseInt(g);this.screenRotate=!1;0>k&&(k+=f.width(),f.hide());j.css("width",k);this.fullScreen=function(){if(window.toFullScreen&&"function"==typeof window.toFullScreen){window.toFullScreen();c.get(0).style.transform="rotateZ(90deg)";c.get(0).style.MsTransform="rotateZ(90deg)";c.get(0).style.MozTransform="rotateZ(90deg)";c.get(0).style.WebkitTransform=
"rotateZ(90deg)";c.get(0).style.OTransform="rotateZ(90deg)";var b=document.getElementsByTagName("html")[0].clientWidth,d=document.getElementsByTagName("html")[0].clientHeight,g=(b-d)/2;c.css({width:d,height:b,left:g,bottom:g});h.screenRotate=!0}else c.css("width",window.screen.width),c.css("height",window.screen.height);b=parseInt(a(window).width())-btnsWidth;0>b?(b+=f.width(),f.hide()):f.show();j.css("width",b)};this.normalScreen=function(){if(window.toNormalScreen&&"function"==typeof window.toNormalScreen)window.toNormalScreen(),
c.get(0).style.transform="rotateZ(0deg)",c.get(0).style.MsTransform="rotateZ(0deg)",c.get(0).style.MozTransform="rotateZ(0deg)",c.get(0).style.WebkitTransform="rotateZ(0deg)",c.get(0).style.OTransform="rotateZ(0deg)",h.screenRotate=!1;c.css({width:g,height:d,left:0,bottom:0});k=parseInt(g);0>k?(k+=f.width(),f.hide()):f.show();j.css("width",k)}}})(window.jQuery);(function(){SewisePlayerSkin.LogoBox=function(a){var b=a.$logoIcon;b.hide();b.click(function(a){a.originalEvent.stopPropagation()});var c="http://www.sewise.com/";this.setLogo=function(a){b.css("background","url("+a+") 0px 0px no-repeat");b.attr("href",c)};this.setLink=function(a){c=a;b.attr("href",c)}}})(window.jQuery);(function(){SewisePlayerSkin.TopBar=function(a){var b=a.$topbar,c=a.$programTip,j=a.$programTitle,f=a.$topbarClock;setInterval(function(){var a=SewisePlayerSkin.Utils.stringer.dateToTimeString();f.text(a)},1E3);c.hide();this.setTitle=function(a){j.text(a)};this.show=function(){b.css("visibility","visible")};this.hide=function(){b.css("visibility","hidden")};this.hide2=function(){b.hide()};this.initLanguage=function(){}}})(window.jQuery);(function(a){SewisePlayerSkin.ControlBar=function(b,c,j){function f(){!1==M&&(i.css("visibility","visible"),j.show(),M=!0,x=setTimeout(d,N))}function h(){0!=x&&(clearTimeout(x),x=0)}function g(){0==x&&(x=setTimeout(d,N))}function d(){i.css("visibility","hidden");j.hide();M=!1}function k(a){a=m+(a[r]-z);0<a&&a<s&&(t.css("width",a),n.css("left",a-E/2))}function w(b){v.unbind("mousemove",k);a(document).unbind("mouseup",w);K=b[r];z!=K&&(m=t.width(),A=m/s,u.seek(A*y));F=!1}function p(a){e=a.originalEvent;
1==e.targetTouches.length&&(e.preventDefault(),a=m+(e.targetTouches[0][r]-z),0<a&&a<s&&(t.css("width",a),n.css("left",a-E/2)))}function q(a){e=a.originalEvent;n.unbind("touchmove",p);n.unbind("touchend",q);1==e.changedTouches.length&&(K=e.changedTouches[0][r],z!=K&&(m=t.width(),A=m/s,u.seek(A*y)));F=!1}var v=b.$container,l=b.$video,i=b.$controlbar,o=b.$playBtn,C=b.$pauseBtn,I=b.$fullscreenBtn,D=b.$normalscreenBtn,J=b.$soundopenBtn,G=b.$soundcloseBtn,U=b.$shareBtn,T=b.$playtime,t=b.$progressPlayedLine,
n=b.$progressPlayedPoint,V=b.$progressLoadedLine,B=b.$progressSeekLine,O=b.$buffer,W=b.$bufferTip,L=b.$bigPlayBtn,H=this,u,y=1,P=0,Q="00:00",R="00:00",E=0,F=!1,z=0,K=0,m=0,s=0,A=0,S=!1,r="pageX",M=!0,x,N=5E3,E=n.width(),s=B.width();J.hide();G.hide();C.hide();D.hide();G.hide();O.hide();T.text(Q+"/"+R);x=setTimeout(d,N);o.click(function(){u.play()});C.click(function(){u.pause()});L.click(function(a){a.originalEvent.stopPropagation();u.play()});I.click(function(){if(SewisePlayerSkin.mobileExtEvent.block)return!1;
H.fullScreen()});D.click(function(){if(SewisePlayerSkin.mobileExtEvent.block)return!1;H.noramlScreen()});this.fullScreen=function(){var a=v.get(0);a.requestFullscreen?a.requestFullscreen():a.msRequestFullscreen?a.msRequestFullscreen():a.mozRequestFullScreen?a.mozRequestFullScreen():a.webkitRequestFullscreen&&a.webkitRequestFullscreen();c.fullScreen();S=c.screenRotate;r="pageY";I.hide();D.show()};this.noramlScreen=function(){document.exitFullscreen?document.exitFullscreen():document.msExitFullscreen?
document.msExitFullscreen():document.mozCancelFullScreen?document.mozCancelFullScreen():document.webkitCancelFullScreen&&document.webkitCancelFullScreen();c.normalScreen();S=c.screenRotate;r="pageX";I.show();D.hide()};SewisePlayerSkin.mobileExtEvent={block:!1,fullScreen:I,normalScreen:D,intoFullScreen:H.fullScreen,exitFullScreen:H.noramlScreen};SewisePlayerSkin.exitFullscreen=function(){H.noramlScreen()};i.on("tap",function(a){a.originalEvent.stopPropagation()});i.click(function(a){a.originalEvent.stopPropagation()});
a(l).css("pointer-events","none");v.bind({mousemove:f,touchmove:f});i.bind({mouseover:h,touchstart:h});i.bind({mouseout:g,touchend:g});J.click(function(){u.muted(!0);J.hide();G.show()});G.click(function(){u.muted(!1);J.show();G.hide()});U.click(function(){window.shareVideo&&"function"==typeof window.shareVideo?window.shareVideo():console.log("Not found the shareVideo function of window")});B.mousedown(function(a){m=S?a[r]-a.target.getBoundingClientRect().top:a[r]-a.target.getBoundingClientRect().left;
s=B.width();t.css("width",m);n.css("left",m-E/2);A=m/s;u.seek(A*y)});n.mousedown(function(b){this.blur();F=!0;z=b[r];m=t.width();s=B.width();v.bind("mousemove",k);a(document).bind("mouseup",w)});n.bind("touchstart",function(a){e=a.originalEvent;n.blur();a=e.targetTouches[0];F=!0;z=a[r];m=t.width();s=B.width();n.bind("touchmove",p);n.bind("touchend",q)});this.setPlayer=function(a){u=a};this.started=function(){o.hide();C.show();L.hide()};this.paused=function(){o.show();C.hide();L.show()};this.stopped=
function(){o.show();C.hide();L.show()};this.setDuration=function(a){y=Infinity!=a?a:3600;1<a&&(R=SewisePlayerSkin.Utils.stringer.secondsToMS(y));SewisePlayerSkin.duration=y};SewisePlayerSkin.duration&&this.setDuration(SewisePlayerSkin.duration);this.timeUpdate=function(a){if(void 0==a||Infinity==a)a=Infinity!=SewisePlayer.video.currentTime?SewisePlayer.video.currentTime:0;P=a;Q=SewisePlayerSkin.Utils.stringer.secondsToMS(P);T.text(Q+"/"+R);F||(m=P/y*(B.width()-E),t.css("width",m),a=t.width(),n.css("left",
a))};this.loadProgress=function(a){V.css("width",100*a+"%")};this.hide2=function(){i.hide()};this.showBuffer=function(){O.show()};this.hideBuffer=function(){O.hide()};this.initLanguage=function(){var a=SewisePlayerSkin.Utils.language.getString("loading");W.text(a)}}})(window.jQuery);(function(a,b){b(document).ready(function(){var a,b,f,h,g,d;SewisePlayerSkin.init=function(){d=g=h=f=b=a=null;a=SewisePlayer.IVodPlayer;b=new SewisePlayerSkin.ElementObject;f=new SewisePlayerSkin.ElementLayout(b);h=new SewisePlayerSkin.LogoBox(b);g=new SewisePlayerSkin.TopBar(b);d=new SewisePlayerSkin.ControlBar(b,f,g)};SewisePlayerSkin.reinit=function(){f=b=null;b=new SewisePlayerSkin.ElementObject;f=new SewisePlayerSkin.ElementLayout(b)};SewisePlayerSkin.init();SewisePlayerSkin.IVodSkin.player=
function(b){a=b;d.setPlayer(a)};SewisePlayerSkin.IVodSkin.started=function(){d.started()};SewisePlayerSkin.IVodSkin.paused=function(){d.paused()};SewisePlayerSkin.IVodSkin.stopped=function(){d.stopped()};SewisePlayerSkin.IVodSkin.duration=function(a){d.setDuration(a)};SewisePlayerSkin.IVodSkin.timeUpdate=function(a){d.timeUpdate(a)};SewisePlayerSkin.IVodSkin.loadedProgress=function(a){d.loadProgress(a)};SewisePlayerSkin.IVodSkin.loadedOpen=function(){d.showBuffer()};SewisePlayerSkin.IVodSkin.loadedComplete=
function(){d.hideBuffer()};SewisePlayerSkin.IVodSkin.programTitle=function(a){g.setTitle(a)};SewisePlayerSkin.IVodSkin.logo=function(a){h.setLogo(a)};SewisePlayerSkin.IVodSkin.volume=function(){};SewisePlayerSkin.IVodSkin.initialClarity=function(){};SewisePlayerSkin.IVodSkin.clarityButton=function(){};SewisePlayerSkin.IVodSkin.timeDisplay=function(){};SewisePlayerSkin.IVodSkin.controlBarDisplay=function(a){"enable"!=a&&d.hide2()};SewisePlayerSkin.IVodSkin.topBarDisplay=function(a){"enable"!=a&&g.hide2()};
SewisePlayerSkin.IVodSkin.customStrings=function(){};SewisePlayerSkin.IVodSkin.customDatas=function(a){a&&a.logoLink&&h.setLink(a.logoLink)};SewisePlayerSkin.IVodSkin.fullScreen=function(){d.fullScreen()};SewisePlayerSkin.IVodSkin.noramlScreen=function(){d.noramlScreen()};SewisePlayerSkin.IVodSkin.initialAds=function(a){a&&SewisePlayerSkin.AdsContainer(b,a)};SewisePlayerSkin.IVodSkin.initialStatistics=function(a){a&&SewisePlayerSkin.Statistics(a)};SewisePlayerSkin.IVodSkin.lang=function(a){SewisePlayerSkin.Utils.language.init(a);
d.initLanguage();g.initLanguage()};try{SewisePlayer.CommandDispatcher.dispatchEvent({type:SewisePlayer.Events.PLAYER_SKIN_LOADED,playerSkin:SewisePlayerSkin.IVodSkin})}catch(k){console.log("No Main Player")}})})(window,window.jQuery);
