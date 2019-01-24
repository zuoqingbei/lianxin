/**公共js部分 */


let Public = {
    init() {
        let that = this
        this.menuEvent()
        this.logoutEvent()
        that.bellEvent()
        setInterval(function () {
            that.bellEvent()
        }, 30000)

    },
    /**
     * 如果文本是逻辑false或传来的参数是includesStr或其中一项则返回outPutStr
     * @param text 输入文本
     * @param includesStr 判断范围
     * @param outPutStr 被替换的文本
     * @return {*} 原样输出
     */
    textFilter(text, includesStr, outPutStr = "") {
        if (!text || Array.isArray(includesStr) && includesStr.includes(text) || text === includesStr) {
            return outPutStr;
        } else {
            return text;
        }
    },
    initReset() {
        /**重置form表单**/
        const $btnReset = $('#btn_reset');
        $btnReset.on('click', function () {
            $("#formSearch input.search-input").val("");
            $("#formSearch select.search-input").val("");
            layui.use('form', function () {
                var form = layui.form;

                //各种基于事件的操作，下面会有进一步介绍
                form.render('select');
            });
            $("#btn_query").trigger("click")
        });
    },
    bellEvent() {
        //绑定信息事件
        $.ajax({
            url: BASE_PATH + 'credit/sysuser/notice/getNoticenum',
            type: 'get',
            success: (data) => {
                if (data == 0) {
                    $(".layui-badge").hide();
                } else {
                    $(".layui-badge").html(data)
                }

            }
        })

     
        $(".message").unbind().click(() => {
           /* if ($('#mynotice').parents("li").hasClass("hasChild")) {
                $('#mynotice').parents("li").toggleClass("show")
                $('#mynotice').parents("ul").show(200)

            }
            $('#mynotice').trigger("click")*/
        	$.ajax({
        		url:  BASE_PATH +'credit/sysuser/notice/list',
        		type:'post',
        		data:{
        			recordsperpage:'100000'
        		},
        		success:(data)=>{
        			console.log(data)
        			let html = ''
        			data.list.forEach((item,index)=>{
        				if(item.read_unread === '0') {
        					//已读
        					html += `<li id="${item.id}"><h4>【公告】${item.notice_title}</h4><section style="display:none">${item.notice_content}</section></li>`
        				}else if(item.read_unread === '1')  {
        					//未读
        					html += `<li id="${item.id}" class='unread'><h4>【公告】${item.notice_title}</h4><section style="display:none">${item.notice_content}</section></li>`
        				}
        			})
        			$(".messageBox ul").html(html)
        			$(".messageBox").toggleClass("hideThis")
        		}
        	})
        })
        //标记全读
		$(".clickRead").click(function(){
			$.post('/credit/sysuser/notice/toReadAll',function(data){
				if(data.statusCode===1){
		          	Public.message("success",data.message);
				}else{
					Public.message("error",data.message);
				}
			})
			
		})
		// 打开弹窗
		$(".messageBox").on('click', 'li', function () {
			let id=$(this).attr("id");
			$.post('/credit/sysuser/notice/toReadAll',{noticeId:id},function(data){
				if(data.statusCode===1){
				}else{
					Public.message("error",data.message);
				}
			})
	        let title = $(this).find('h4').html();
	        let content = $(this).find('section').html();
	        let $modal = $('#notice');
	        $modal.find('.modal-title').html(title);
	        $modal.find('.modal-body').html(content);
	        $modal.modal('show');
	        $("[aria-selected='true']").click();
	        
	    })
	    
    },
    menuEvent() {
        /**
         * 菜单事件
         */
        var $leftNav = $(".left-nav");
        let _this = this
        // 含有子菜单的a添加样式
        $leftNav.find("a").each(function (index, item) {
            if ($(this).next().length > 0) {
                $(this).parent().addClass("hasChild");
            }
        });
        //菜单展开收缩
        this.isSQ = false
        $("#handleMenu").click(() => {
            if (!this.isSQ) {
                this.isSQ = !this.isSQ
                //被收起
                $(".fixed-table-header>.table").css("width", "99.9%")
                $(".head-logo-box").removeClass("col-xs-2 col-sm-2 col-md-2 col-lg-2")
                $("#main_content").css({"width": "99.9%"})
                $(".nav-row").css({"width": "0.1%"})
                $(".head-logo-box").css({"width": "0.03%"})
                $(".nav-row").removeClass("col-md-2")
                $("#main_content").removeClass("col-md-10")
                $(".border-div").css({
                    "background": "url(/static/credit/imgs/index/right_arrow.png) no-repeat",
                    "backgroundSize": "100% 100%"
                })
                $(".abc").addClass("aaa")
                $(".dateInp1").css({"margin": "0 14% 0 32.5%"})
            } else {
                this.isSQ = !this.isSQ
                //没被收起
                $(".nav-row").css("width", "16.66%")
                $("#main_content").css("width", "83.33%")
                $(".head-logo-box").css("width", "16.6%")
                $(".border-div").css({
                    "background": "url(/static/credit/imgs/index/left_arrow.png) no-repeat",
                    "backgroundSize": "100% 100%"
                })
                $(".abc").removeClass("aaa")
                $(".dateInp1").css({"margin": "0 11% 0 32%"})

            }
        })
        //菜单点击
        $leftNav.find("li").click(function (e) {

            if (sessionStorage.getItem("isResetPw")) {
                Public.message("info", "请先重置密码！")
                return false
            }
            if ($(this).hasClass("hasChild")) {
                if ($(this).hasClass("show")) {
                    $(this).children("ul").slideUp(150, function () {
                        $(this).parent().toggleClass("show");
                    });
                } else {
                    $(this).children("ul").slideDown(150, function () {
                        $(this).parent().toggleClass("show");
                    });
                }

            } else {
                $leftNav.find("li").removeClass("active");
                $(this).addClass("active")

                let id = $(this).attr("id");
                let href = $(this).find("a").attr("href");/*.replace("/menu","")*/
                ;
//                console.log(id,href)

                sessionStorage.setItem('menuId', id);
                $("#main_content").load(href ? href + "?from=commonjs" : '/credit/front/orderProcess/showUnderdevelopment', function (response) {
                    //console.log(response)
                    if ("nologin" == response) {
                        $("#main_content").html("")
                        window.location.href = "/credit/front/usercenter/showLogin";
                        return;
                    } else {
                        _this.initReset();
                        _this.gotop()
                        sessionStorage.setItem('pageUrl', href)
                    }
                })

            }

            return false;
        });
    },
    textAreaEvent() {
        //获取textarea的内容，替换换行符
        /*$(document).ajaxStop(function(){
          //遍历所有textarea标签
          $('textarea').each(function(){
            var text = $(this).val();
            console.log($(this).attr("name")+"="+text);
            text = text.replace(/\\r\\n/g, '\r\n').replace(/\\n/g, '\n');
            $(this).val(text);
          });
        });*/
    },
    logoutEvent() {
        $(".user-content").click(function () {
            $(".logout").toggleClass("logout-show")
        })

        $(".logout").click(function () {
            /**发送ajax请求 */
            $(".logout").toggleClass("logout-show")

        })
    },
    goToOrderDetail(id, row) {
        console.log("开始订单详情页跳转");
        console.log(id);
        console.log(row)
        //跳转订单详情
        $("#main_content").load(BASE_PATH + 'credit/front/home/orderInfo?id=' + id);
        if (row) {
            localStorage.setItem("row", JSON.stringify(row));
        }
    },
    createOrder() {
        $("#main_content").load(BASE_PATH + 'credit/front/home/createOrder')
    },
    goList() {
        $("#main_content").load(BASE_PATH + 'credit/front/home')
    },
    goAll() {
        $("#main_content").load(BASE_PATH + 'credit/front/home/allOrder')
    },
    goToCreateOrder() {
        /**跳转新建订单页面 */
        $("#main_content").load(BASE_PATH + 'credit/front/home/createOrder');
    },
    goToBasicInfoWrite(e) {
        /**跳转基本信息填报 */
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportedBasicInfo');
        localStorage.setItem("row", JSON.stringify(e));
    },
    goToInfoImportPage() {
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportInfoImport');
    },
    goToReportConfig(param) {
        /**跳转可配置的填报页面*/
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportedConfig', () => {
            this.gotop()
        });
        localStorage.setItem("row", JSON.stringify(param));
    },
    goToReportTranslate(param) {
        /**跳转可配置翻译页面*/
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportedTranslate', () => {
            this.gotop()
        });
        localStorage.setItem("row", JSON.stringify(param));
    },
    goToReportAnalyze(param) {
        /**跳转可配置分析页面*/
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportedAnalyze', () => {
            this.gotop()
        });
        localStorage.setItem("row", JSON.stringify(param));
    },
    goToReportedAnalyzeQuality(param) {
        /**跳转可配置质检翻译页面*/
        $("#main_content").load(BASE_PATH + 'credit/front/orderProcess/showReportedAnalyzeQuality', () => {
            this.gotop()
        });
        localStorage.setItem("row", JSON.stringify(param));
    },
    tabFixed(fixedEle, scrollEle, min, max) {
        /**
         * 滚动之后固定tab函数
         * fixedEle:固定的dom   string
         * scrollEle:滚动的dom  string
         * min:超出后固定   number
         * max:小于取消固定 number
         */
        $(scrollEle).scroll(() => {
            let top = $(scrollEle).scrollTop();
            if (top > min) {
                if (this.isSQ === false) {
                    $(fixedEle).css("left", "16.667%")
                } else {
                    $(fixedEle).css("left", "0")
                }
                $(fixedEle).addClass("tab-fixed")
            } else if (top < max) {
                $(fixedEle).removeClass("tab-fixed")
                $(fixedEle).css("left", "0")
            }
        })
    },
    gotop() {
        $(".main").scroll(function () {
            // 滚动条距离顶部的距离 大于 200px时
            if ($(".main").scrollTop() >= 200) {
                $(".fixed-backup").show(500); // 开始淡入
            } else {
                $(".fixed-backup").fadeOut(500); // 如果小于等于 200 淡出
            }
        })

        $('.fixed-backup').click(function () {
            $('.main').animate({scrollTop: 0}, 'normal');
        });
    },
    message(state, text) {
        let txt = text || '获取数据失败！'
        if (state === 'info') {
            $(".infoTip").show();
            $(".info-text").html(txt)
            setTimeout(() => {
                $(".infoTip").hide()
            }, 2000);
        } else if (state === 'error') {
            $(".errorTip").show();
            $(".error-text").html(txt)
            setTimeout(() => {
                $(".errorTip").hide()
            }, 2000);
        } else if (state === 'success') {
            $(".successTip").show();
            $(".success-text").html(txt)
            setTimeout(() => {
                $(".successTip").hide()
            }, 2000);
        }
    },
    fileConfig(item, row) {
        let _this = this
        _this.row = row
        console.log(item)
        let content = ''
        if(item.title.temp_name === '订单附件'){
        	content += ` <div class="orderFile order-detail mb-4 order-content d-flex flex-wrap mx-4 justify-content-start">
		        		<div class="uploadFile mt-3 mr-3 ml-3">
		        		<div class="over-box">
		        		<img src="/static/credit/imgs/order/fujian.png" class="m-auto"/>
		        		<p class="mt-2">暂无附件</p>
		        		</div>
	        		</div>
        		</div>`;
        }else if(item.title.temp_name === '核实附件'){
        	content += ` <div class="verifyFile order-detail mb-4 order-content d-flex flex-wrap mx-4 justify-content-start">
        		<div class="uploadFile mt-3 mr-3 ml-3">
        		<div class="over-box">
        		<img src="/static/credit/imgs/order/fujian.png" class="m-auto"/>
        		<p class="mt-2">暂无附件</p>
        		</div>
    		</div>
		</div>`;
        }else if(item.title.temp_name === "查档附件"){
        	content += ` <div class="filingFile order-detail mb-4 order-content d-flex flex-wrap mx-4 justify-content-start">
        		<div class="uploadFile mt-3 mr-3 ml-3">
        		<div class="over-box">
        		<img src="/static/credit/imgs/order/fujian.png" class="m-auto"/>
        		<p class="mt-2">暂无附件</p>
        		</div>
    		</div>
		</div>`;
        }
        let url = item.title.get_source ? item.title.get_source : item.title.data_source;
        url = BASE_PATH + url;
        $.ajax({
            url,
            type: 'post',
            data: {
                orderId: _this.row.id
            },
            success: (data) => {
                if (data.statusCode === 1) {
                    let files = data.files;
                    if (data.files.length === 0) {
                        return
                    }
                    if(item.title.temp_name === '订单附件'){
                    	$(".orderFile").html("")
                    }else if(item.title.temp_name === '核实附件'){
                    	$(".verifyFile").html("")
                    }else if(item.title.temp_name === "查档附件"){
                    	$(".filingFile").html("")
                    }
                    //   	$(".uploadFile:not(.upload-over)").show()
                    for (var i = 0; i < files.length; i++) {
                        let filetype = files[i].ext.toLowerCase()
                        let fileicon = ''
                        if (filetype === 'doc' || filetype === 'docx') {
                            fileicon = '/static/credit/imgs/order/word.png'
                        } else if (filetype === 'xlsx' || filetype === 'xls') {
                            fileicon = '/static/credit/imgs/order/Excel.png'
                        } else if (filetype === 'png') {
                            fileicon = '/static/credit/imgs/order/PNG.png'
                        } else if (filetype === 'jpg') {
                            fileicon = '/static/credit/imgs/order/JPG.png'
                        } else if (filetype === 'pdf') {
                            fileicon = '/static/credit/imgs/order/PDF.png'
                        } else if (filetype === 'html') {
                            fileicon = '/static/credit/imgs/order/html.png'
                        }
                        let fileArr = ''
                        let filename = data.files[i].originalname
                        let all_name = filename + filetype
                        let num = filename.split(".").length;
                        let filename_qz = []
                        for (let i = 0; i < num; i++) {
                            filename_qz = filename_qz.concat(filename.split(".")[i])
                        }
                        filename_qz_str = filename_qz.join('.')
                        if (filename_qz_str.length > 4) {
                            filename_qz_str = filename_qz_str.substr(0, 2) + '..' + filename_qz_str.substr(filename_qz_str.length - 2, 2)
                        }

                        filename = filename_qz_str + '.' + filetype
                        fileArr += '<div class="uploadFile mt-3 mr-4 mb-5 upload-over" fileId="' + data.files[i].id + '" url="' + data.files[i].view_url + '" style="cursor:pointer">' +
                            '<div class="over-box">' +
                            '<img src="' + fileicon + '" class="m-auto"/>' +
                            '<p class="filename" title="' + all_name + '">' + filename + '</p>' +
                            '</div>' +
                            '</div>'
                            if(item.title.temp_name === '订单附件'){
                            	$(".orderFile").append(fileArr)
                            }else if(item.title.temp_name === '核实附件'){
                            	$(".verifyFile").append(fileArr)
                            }else if(item.title.temp_name === "查档附件"){
                            	$(".filingFile").append(fileArr)
                            }
                        
                        $(".upload-over").click(function (e) {
                            if ($(e.target).parent().attr("class") === 'close') {
                                return
                            }
                            window.open($(this).attr("url"))

                        })
                    }
                }
            }
        })
        return content
    }
}

$(function () {
    $(document).ajaxStop(function () {
        //遍历所有textarea标签
        $('textarea').each(function () {
            var text = $(this).val();
            text = text.replace(/\\r\\n/g, '\r\n').replace(/\\n/g, '\n');
            $(this).val(text);
        });
    });

    Public.init();
    window.onload = function () {
        if (sessionStorage.getItem("isResetPw") === '1') {
            setTimeout(() => {
                console.log($(".warning"))
                $(".warning").css("display", "flex")
            }, 0)
        } else {
            setTimeout(() => {
                $(".warning").hide()
            }, 0)
        }
        let id = sessionStorage.getItem('menuId') ? sessionStorage.getItem('menuId') : $(".left-nav ul").children("li").eq(0).attr("id")
        $("#main_content").load(sessionStorage.getItem('pageUrl') ? sessionStorage.getItem('pageUrl') : '/credit/front/home', () => {
            Public.initReset()
        })
        $(".left-nav").find("li").removeClass("active");
        $('#' + id).addClass("active");
        if ($('#' + id).parents("li").hasClass("hasChild")) {
            $('#' + id).parents("li").toggleClass("show")
            $('#' + id).parents("ul").show(200)

        }

    }
});

// 定义一些常量
const creditLevel_cn = `<div class="credit-level-title pt-3">
                    信用等级：<strong id="creditLevel" class="pl-3 pr-2"></strong>
                    <span class="myExplain">(见以下详情评估标准)</span>
                </div>
                <div class="table-content1" style="background:#fff">
                    <div class="bootstrap-table">
                        <div class="fixed-table-toolbar"></div>
                        <div class="fixed-table-container" style="padding-bottom: 0px;">
                            <div class="fixed-table-header" style="display: none;">
                                <table></table>
                            </div>
                            <div class="fixed-table-body">
                                <div class="fixed-table-loading" style="top: 41px; display: none;">正在努力地加载数据中，请稍候……
                                </div>
                                <table id="table7" style="position: relative" class="table table-hover">
                                    <thead>
                                    <tr class="border-bottom">
                                        <th style="width: 25%; " data-field="level">
                                            <div class="th-inner ">级别:</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="describe">
                                            <div class="th-inner "></div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="amount">
                                            <div class="th-inner ">建议信用额度</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="risk_evaluation">
                                            <div class="th-inner ">信用风险评估</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr data-index="0">
                                        <td style="width: 25%; ">CA1</td>
                                        <td style="width: 25%; ">很小</td>
                                        <td style="width: 25%; ">可放宽信用额度</td>
                                        <td style="width: 25%; ">大额度</td>
                                    </tr>
                                    <tr data-index="1">
                                        <td style="width: 25%; ">CA2</td>
                                        <td style="width: 25%; ">低</td>
                                        <td style="width: 25%; ">可适当放宽信用额度</td>
                                        <td style="width: 25%; ">较大额度</td>
                                    </tr>
                                    <tr data-index="2">
                                        <td style="width: 25%; ">CA3</td>
                                        <td style="width: 25%; ">一般</td>
                                        <td style="width: 25%; ">可给予一般的信用</td>
                                        <td style="width: 25%; ">中等额度</td>
                                    </tr>
                                    <tr data-index="3">
                                        <td style="width: 25%; ">CA4</td>
                                        <td style="width: 25%; ">高于一般</td>
                                        <td style="width: 25%; ">可在监控的条件下给予信用</td>
                                        <td style="width: 25%; ">小额度=定期监控</td>
                                    </tr>
                                    <tr data-index="4">
                                        <td style="width: 25%; ">CA5</td>
                                        <td style="width: 25%; ">较高</td>
                                        <td style="width: 25%; ">可在有担保的条件下给予信用</td>
                                        <td style="width: 25%; ">现金交易或小额度</td>
                                    </tr>
                                    <tr data-index="5">
                                        <td style="width: 25%; ">CA6</td>
                                        <td style="width: 25%; ">高</td>
                                        <td style="width: 25%; ">建议不给予信用</td>
                                        <td style="width: 25%; ">现金交易</td>
                                    </tr>
                                    <tr data-index="6">
                                        <td style="width: 25%; ">NR</td>
                                        <td style="width: 25%; ">无法评定</td>
                                        <td style="width: 25%; ">无充足的材料</td>
                                        <td style="width: 25%; ">无法评定</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="fixed-table-footer" style="display: none;">
                                <table>
                                    <tbody>
                                    <tr></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="fixed-table-pagination" style="display: none;"></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="credit-level-title pt-3">信用等级说明</div>
                <p class="m-3 ml-4 mt-4">以上级别可用来评估该公司的信用风险和确定给予该公司的信用额度。它是经过计算本报告每部分内容综合进行评估的因素和它们在评估中所占的比重（%）</p>
                <p class="m-3 ml-4">如下所示：
                <p class="m-3 ml-4">财务状况（40%） 股东背景（10%） 付款记录（10%）</p>
                <p class="m-3 ml-4">信用历史（15%） 市场趋势（10%） 经营规模（15%）</p>
                <p class="m-3 ml-4">如果是个体户或无限责任性质的公司，新建立的或缺少财务资料的公司，评估的比重会增加到“股东背景”和“付款记录”两项。</p>
                <p class="m-3 ml-4 pb-4">使用缩写： N/A-不详 CNY-人民币 SC-目标公司</p>`
    , creditLevel_en = `<div class="credit-level-title pt-3">
                    CREDIT RATING：<strong id="creditLevel" class="pl-3 pr-2"></strong>
                    <span class="myExplain">(see below explanatory notes)</span>
                </div>
                <div class="table-content1" style="background:#fff">
                    <div class="bootstrap-table">
                        <div class="fixed-table-toolbar"></div>
                        <div class="fixed-table-container" style="padding-bottom: 0px;">
                            <div class="fixed-table-header" style="display: none;">
                                <table></table>
                            </div>
                            <div class="fixed-table-body">
                                <div class="fixed-table-loading" style="top: 41px; display: none;">loading……
                                </div>
                                <table id="table7" style="position: relative" class="table table-hover">
                                    <thead>
                                    <tr class="border-bottom">
                                        <th style="width: 25%; " data-field="level">
                                            <div class="th-inner ">RATING KEY:</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="describe">
                                            <div class="th-inner "></div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="amount">
                                            <div class="th-inner ">CREDIT RISK ASSESSMENT</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                        <th style="width: 25%; " data-field="risk_evaluation">
                                            <div class="th-inner ">PROPOSED CREDIT LIMIT<br>(in concerned business field)</div>
                                            <div class="fht-cell"></div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr data-index="0">
                                        <td style="width: 25%; ">CA1</td>
                                        <td style="width: 25%; ">Minimal</td>
                                        <td style="width: 25%; ">Large amount</td>
                                        <td style="width: 25%; ">Credit can proceed with favorable terms</td>
                                    </tr>
                                    <tr data-index="1">
                                        <td style="width: 25%; ">CA2</td>
                                        <td style="width: 25%; ">Low</td>
                                        <td style="width: 25%; ">Fairly large amount</td>
                                        <td style="width: 25%; ">Credit can proceed promptly</td>
                                    </tr>
                                    <tr data-index="2">
                                        <td style="width: 25%; ">CA3</td>
                                        <td style="width: 25%; ">Average</td>
                                        <td style="width: 25%; ">Moderate amount</td>
                                        <td style="width: 25%; ">Credit can proceed normally</td>
                                    </tr>
                                    <tr data-index="3">
                                        <td style="width: 25%; ">CA4</td>
                                        <td style="width: 25%; ">Above average</td>
                                        <td style="width: 25%; ">Small amount – periodical review</td>
                                        <td style="width: 25%; ">Credit should proceed with monitor</td>
                                    </tr>
                                    <tr data-index="4">
                                        <td style="width: 25%; ">CA5</td>
                                        <td style="width: 25%; ">Fairly High</td>
                                        <td style="width: 25%; ">C.O.D. To small amount</td>
                                        <td style="width: 25%; ">Credit should be extended under guarantee</td>
                                    </tr>
                                    <tr data-index="5">
                                        <td style="width: 25%; ">CA6</td>
                                        <td style="width: 25%; ">High</td>
                                        <td style="width: 25%; ">C.O.D.</td>
                                        <td style="width: 25%; ">Credit is not recommended</td>
                                    </tr>
                                    <tr data-index="6">
                                        <td style="width: 25%; ">NR</td>
                                        <td style="width: 25%; ">Assessment not conducted</td>
                                        <td style="width: 25%; ">No recommendation</td>
                                        <td style="width: 25%; ">Insufficient data available</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="fixed-table-footer" style="display: none;">
                                <table>
                                    <tbody>
                                    <tr></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="fixed-table-pagination" style="display: none;"></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <div class="credit-level-title pt-3">CREDIT RATING</div>
                <p class="m-3 ml-4 mt-4">This rating serves as a reference to assess SC’s credit risk and to set the amount of credit to be extended.  It is calculated from a composite of weighted scores obtained from each of the major sections of this report.  </p>
                <p class="m-3 ml-4">The assessed factors and their relative weights (as indicated through %) in our credit analysis are as follows:
                <p class="m-3 ml-4">Financial condition (40%) Ownership background (10%) Payment record (10%)</p>
                <p class="m-3 ml-4">Credit history (15%) Market trend (10%) Operational size (15%)</p>
                <p class="m-3 ml-4">In case of unlimited companies, newly established companies, or lack of financial data, more weight is given to ‘Ownership background’ and ‘Payment record’ in our analysis.</p>
                <p class="m-3 ml-4 pb-4">Adopted abbreviations：
                <br>N/A – not available	CNY – China Yuan Renminbi	SC – subject company (the company inquired by you) </p>`
    , processObj = {
    '订单分配': ['订单分配'],
    '信息录入': ['信息录入', '信息录入完成'],
    '订单核实': ['客户确认'],
    '订单查档': ['代理中', '代理完成', '代理补充'],
    '信息质检': ['信息质检中', '信息质检不通过', '信息质检合格'],
    '分析录入': ['分析制作', '分析完成'],
    '分析质检': ['分析质检中', '分析质检不通过', '分析质检合格'],
    '翻译录入': ['翻译制作', '翻译完成'],
    '翻译质检': ['翻译质检中', '翻译质检不通过', '翻译质检合格'],
    '报告完成': ['报告完成'],
    '客户内容已更新': ['客户内容已更新'],
    '订单完成': ['客户内容已更新'],
}
    , type0_extraUrl = '[' + JSON.stringify({
    "getSelete?type=register_code_type$selectedId=632$disPalyCol=detail_name": "register_code_type",
    "getSelete?type=principal_type$selectedId=638$disPalyCol=detail_name": "principal_type",
    "getSelete?type=companyType$selectedId=318$disPalyCol=detail_name": "company_type",
    "getSelete?type=currency$selectedId=267$disPalyCol=detail_name": "currency",
    "getSelete?type=gender$selectedId=630$disPalyCol=detail_name": "gender",
    "getSelete?type=id_type$selectedId=628$disPalyCol=detail_name": "id_type",
    "getSelete?type=position$selectedId=615$disPalyCol=detail_name": "position",
    "getSelete?type=registration_status$selectedId=596$disPalyCol=detail_name": "registration_status",
    "getSelete?type=capital_type$selectedId=267$disPalyCol=detail_name": "capital_type",
    "getSelete?type=is_regular_check$selectedId=762$disPalyCol=detail_name": "is_regular_check",
    "getSelete?type=annual_inspection$selectedId=685$disPalyCol=detail_name": "year_result",
    "getSelete?type=registration_status_roc$selectedId=680$disPalyCol=detail_name": "roc_registration_status"
}) + ']'
    , type23_html = `<div class="module-content type23-content">
                    <form>
                        <div class="row justify-content-between">
                            <div class="my-1">
                                <label for="grade"></label>
                                <!--<output></output>-->
                                <output style="pointer-events: none;" class="ml-3" id="grade" name="grade"></output>
                                <span class="unit">分</span>
                            </div>
                            <div class="text-right my-3 mr-3">
                                订单处理：
                                <div class="radio-box d-inline">
                                    <label><input type="radio" name="statusCode" checked>完成</label>
                                    <label><input type="radio" name="statusCode">退回</label>
                                    <label><input type="radio" name="statusCode">修改</label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div style="width: 6rem"><label for="quality_opinion"></label></div>
                            <div class="pr-0 flex-grow-1"><textarea class="form-control" name="quality_opinion" id="quality_opinion" rows="10"></textarea></div>
                        </div>
                        <div style="height: 5rem"></div>
                        <!--<div class="col-md-12 d-flex justify-content-end">-->
                            <!--<button class="btn btn-light m-3" id="save" type="button">保存</button>-->
                            <!--<button class="btn btn-primary m-3" id="submit" type="button">提交</button>-->
                        <!--</div>-->
                    </form>
                </div>`
    , qualitySelectHtml = `<div class="row form-group select2Box">
                    <label for="" class="col-form-label">质检结果:</label>
                    <div class="">
                        <select style="width: 100%;" class="js-example-basic-multiple" id="" name="states[]" multiple="multiple">
                        </select>
                    </div>
                </div>`
    , qualityTypeToChinese = {
    entering_quality: '填报质检',
    analyze_quality: '分析质检',
    translate_quality: '翻译质检'
}






