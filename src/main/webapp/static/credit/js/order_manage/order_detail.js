let OrderDetail = {
    init: function () {
        this.row = JSON.parse(localStorage.getItem("row"));
        console.log('~~row：', this.row);
        this.isQuality = !!this.row.quality_type;//是否质检页面
        this.quality_deal = ''; //订单处理阶段 0-完成，1-退回/修改
        this.qualityOpinionId = '';
        this.selectInfo = '';
        this.cwModules = [];
        //财务模块专用
        [this.type9MulText, this.type9BigData, this.type9TableHead, this.type9TableHeadBigData, this.type10Items, type10BigData] = [{}, {}, [], [], {}, {}];
        this.BASE_PATH = BASE_PATH + 'credit/front/';
        /**
         *
         * @param item 本次循环的数据
         * @param otherProperty 如果地址不是放在get_source里面
         * @param paramObj 附加的object用于传参
         * @param fromContents 地址在 .contents[0].get_source里面，信用等级改成这样了
         * @return {string}
         */
        this.getUrl = (item, otherProperty, paramObj, fromContents) => {
            let urlArr = (otherProperty ? item.title[otherProperty] : item.title.get_source).split("*");
            urlArr = fromContents === 'fromContents' ? item.contents[0].get_source.split("*") : urlArr;
            if (urlArr[0] === '') {
                return '';
            }
            let url = '';
            urlArr.forEach((param, index) => {
                if (index === 0) {
                    url = param;
                } else {
                    if (param.includes("$")) {
                        let paramArr = param.split("$");
                        paramArr.forEach((item) => {
                            if (item === 'orderId') { //这里的orderId对应rows的id，和填报配置中不一样
                                url += `&${item}=${this.row.id}`;
                            } else {
                                if (paramObj[item] || paramObj[item] === '') {
                                    url += `&${item}=${paramObj[item]}`;
                                } else {
                                    url += `&${item}=${this.row[item]}`
                                }
                            }
                        });
                    } else if (param === 'orderId') {//这里的orderId对应rows的id，和填报配置中不一样
                        url += `&${param}=${this.row.id}`
                    } else {
                        url += `&${param}=${this.row[param]}`
                    }
                }
            });
            console.assert(url, item);
            url = `${this.BASE_PATH}ReportGetData/${url}&conf_id=${item.title.id}`;
            url += url.includes("getForm") ? '&type=' + this.row.report_type : '';
            return url;
        };
        this.processNames = this.row.country === '中国大陆' ?
            ['订单分配', '信息录入', '订单核实', '订单查档', '信息质检', '分析录入', '分析质检', '翻译录入', '翻译质检', '报告完成', '客户内容已更新', '订单完成']
            : ['订单查档', '订单分配', '信息录入', '订单核实', '信息质检', '分析录入', '分析质检', '翻译录入', '翻译质检', '报告完成', '客户内容已更新', '订单完成'];
        this.chartColors = ['#1890ff', '#13c2c2', '#2fc25c', '#facc15', '#ef4763', '#8543e0', '#40a9ff', '#36cfc9', '#73d13d', '#ffec3d', '#ff4d4f', '#9254de'];
        // 1-基本信息报告,7-REGISTRATION REPORT,8-商业信息报告,9-BUSINESS INFORMATION REPORT,10-信用分析报告,11-CREDIT RISK ANALYSIS REPORT,
        // 12-102 ROC Chinese,13-102 ROC English-1,14  102 ROC English,15  102红印,16  105,17  107-1,18  107-2,19  117,20  394,21  396,22  注册,
        // 汉语类型[1,8,10],英语类型[7,9,11]
        this.english = [7, 9, 11].includes(this.row.report_type - 0);
        this.creditLevel = this.english ? creditLevel_en : creditLevel_cn;
        this.initContent();

        // 返回按钮
        let tis = this;
        $('.return_back').on('click', function () {
            if (tis.isQuality) {
                layer.confirm('是否保存已录入信息？', {
                    btn: ['保存', '取消'] //按钮
                }, function () {
                    $('#save').trigger('click')

                    location.reload();
                }, function () {
                    location.reload();
                });
            } else {
                location.reload();
            }
        });
        if (tis.isQuality) {
            $(".position-fixed").append(`<div class="col-md-12 d-flex justify-content-end">
                            <button class="btn btn-light m-3" id="save" type="button">保存</button>
                            <button class="btn btn-primary m-3" id="submit" type="button">提交</button>
                        </div>`)
        } else {
            $(".position-fixed").removeClass('d-flex').hide()
        }

    },
    // 页面结构
    initContent() {
        let _this = this;
        let id = this.row.id;
        let reportType = this.row.report_type;
        // type:1-填报，2-详情，3-质检
        let type = this.row.quality_type ? 3 : 2;// 质检页面type传'',详情页面type传0
        $.get(`${this.BASE_PATH}getmodule/list/`, {id, reportType, type}, (data) => {
            setTimeout(() => {
                console.log('~~~详情data.modules：', data.modules);
                if (!data.defaultModule) {
                    console.error(`--本页面接口故障：
                        ${this.BASE_PATH}getmodule/detail/?id=${id}&reportType=${reportType}&type=0`);
                    return;
                }
                _this.data = data;
                _this.setHeader();
                _this.setTabs();
                _this.setContent();
            }, 0)
        })
    },
    // 设置内容数据
    setContent: function () {
        let $moduleWrap = $('<div class="module-wrap bg-f company-info px-4 mb-4"></div>');
        let $moduleTitle = $('<h3 class="l-title"></h3>');

        //获取将数字转换成汉字的selectInfo
        let selectInfoObj = {};
        this.data.modules.forEach((item, index) => {
            if ([0, 1, 11].includes(item.smallModileType - 0)) {
                item.contents.forEach(content => {
                    if (content.get_source && ['select', 'select2'].includes(content.field_type)) {
                        let key = content.get_source.replace(new RegExp(/&/g), "$");
                        selectInfoObj[key] = content.column_name
                    }
                });
            }
        });
        this.selectInfo = '[' + JSON.stringify(selectInfoObj) + ']';
        // console.log('~~this.selectInfo:', this.selectInfo);

        // smallModileType数据类型：0-表单，1-表格，11-带饼图的表格，2-附件，4-流程进度，6-信用等级，7-多行文本框
        this.data.modules.forEach((item, index, items) => {
            let smallModuleType = item.smallModileType;
            let itemId = item.title.id;
            let _this = this;
            let $wrap;
            if (smallModuleType !== '9') {
                $wrap = $moduleWrap.clone().attr('id', itemId)
                    .append($moduleTitle.clone().text(item.title.temp_name));
            }
            // console.log(index)
            switch (smallModuleType) {
                // 0-表单
                case '0':
                    if (_this.isQuality && (item.title.temp_name === '基本信息' || item.title.temp_name === '基本信息')) {
                        return;
                    }
                    const htmlRoc_registration_status = ['', '未有登记', '正在办理成立登记', '登记成立', '已自行注销登记', '已被吊销登记']
                        .reduce(function (prev, cur) {
                            return `${prev}<input type="radio" name="registration_status" >${cur}`
                        });
                    const htmlAnnual_inspection_status = ['', '通过', '不通过', '未做年检（新成立）', '未做年检（原因不明）', '并未显示']
                        .reduce(function (prev, cur) {
                            return `${prev}<input type="radio" name="annual_inspection_status" >${cur}`
                        });

                    let formHtml = '';
                    item.contents.forEach((item) => {
                        if (item.field_type === 'radio') {
                            formHtml += `
                            <div class="col-md-12 mt-2 mb-2">
                                <span>${item.temp_name}：</span>
                                <span data-column_name="${item.column_name}" class="radioBox">
                                    ${item.column_name === 'roc_registration_status' ? htmlRoc_registration_status : htmlAnnual_inspection_status}
                                </span>
                            </div>`;
                        } else {
                            formHtml += `
                            <div class="col-md-4 mt-2 mb-2 ${item.field_type === 'money' ? 'moneyCol' : ''}">
                                <span>${item.temp_name}：</span>
                                <span data-column_name="${item.column_name}"></span>
                            </div>`
                        }
                    });
                    $wrap.append(`
                        <div class="type1-content module-content">
                            <div class="row mt-2 mb-2">${formHtml}</div>
                        </div>`);
                    //绑数
                    if (item.title.temp_name === '基本信息') { //表单头部取数于本地存储
                        $wrap.find(`span[data-column_name]`).each(function (index, item) {
                            let text = _this.row[$(this).data('column_name')];
                            $(this).text(Public.textFilter(text, 'null'));
                        });
                        $wrap.find('div.moneyCol [data-column_name]').text(function () {
                            return Number($(this).text().replace(/,/g, "")).toLocaleString('en-US');
                        });
                    } else {
                        $.post(this.getUrl(item), {selectInfo: this.selectInfo}, (data) => {
                            if (data.rows && data.rows.length > 0) {
                                $wrap.find('span[data-column_name]').each(function (index, dom) {
                                    let column_name = $(this).data('column_name');
                                    if ($(this).hasClass('radioBox')) {
                                        $(this).children().eq(data.rows[0][column_name] - 1).prop('checked', true);
                                    } else {
                                        $(this).text(Public.textFilter(data.rows[0][column_name], 'null'));
                                    }
                                });
                                $wrap.find('div.moneyCol [data-column_name]').text(function () {
                                    return Number($(this).text().replace(/,/g, "")).toLocaleString('en-US');
                                });
                            } else {
                                console.warn(item.title.temp_name + '-表单-没有返回数据！')
                            }
                        });
                    }
                    break;
                // 1-表格
                case '1':
                    this.setTable(item, $wrap);
                    break;
                // 11-表格+饼图
                case '11':
                    this.setTable(item, $wrap, 'pie');
                    break;
                // 22-表格+柱线图
                case '22':
                    this.setTable(item, $wrap, 'lineBar');
                    break;
                // 2-附件
                case '2':
                    let html = Public.fileConfig(item, this.row);
                    $wrap.append(`<div class="type2-content module-content tabelBox p-4">${html}</div>`);
                    break;
                // 4-流程进度
                case '4':
                    if (_this.isQuality) {
                        return;
                    }
                    let $ul = this.initProcess();
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows && data.rows.length > 0) {
                            this.processNames.forEach((name, index) => {
                                data.rows.forEach((item) => {
                                    if (processObj[name].includes(item.order_state)) {
                                        $ul.children('li').eq(index).addClass('active').children('span:eq(1)').text(item.create_oper)
                                            .next('span').text(item.create_time)
                                            .siblings('.bar-span-box').children('.span_bar').addClass('bar_active')
                                            .end().children('.span_circle').addClass('span_active');
                                    }
                                })
                            })
                        } else {
                            console.warn(item.title.temp_name + '-流程进度-没有返回数据！')
                        }
                        $ul.find('.span_active:last').addClass('circle_active')
                            .end().children('li.active:last').addClass('current');
                        $wrap.append(`<div class="module-wrap company-info">
                            <div class="type4-content module-content bar_box py-3 process">${$ul[0].outerHTML}</div></div>`);
                    });
                    break;
                // 6-信用等级
                case '6':
                    $wrap.append(`<div class="type6-content module-content">${this.creditLevel}</div>`);
                    //绑数
                    // 190510,取数方式改变，废弃alter_source
                    $.get(this.getUrl(item, '',{},'fromContents'), {selectInfo: `[{"getSelete?type=credit_level$selectedId=767$disPalyCol=detail_name":"credit_level"}]`}, (data) => {
                        if (data.rows && data.rows.length > 0) {
                            $wrap.find("#creditLevel").text(data.rows[0][item.title.column_name])
                        } else {
                            console.warn(item.title.temp_name + '-信用等级-没有返回数据！')
                        }
                    });
                    break;
                // 7-多行文本框
                case '7':
                    $wrap.append(`<div class="type7-content module-content"><div class="border multiText mt-4 p-2"></div><div class="pt-1"></div></div>`);
                    $.get(`${this.getUrl(item, '', {report_type: this.row.report_type})}&order_num=${this.row.num}`, (data) => {
                        if (data.rows && data.rows.length > 0) {
                            $wrap.find(".module-content .multiText").text(data.rows[0][item.title.column_name] || '');
                        } else {
                            console.warn(item.title.temp_name + '-总结-没有返回数据！')
                        }
                    });
                    break;
                // 8-总体评价，一个对勾列表和两个多行文本框
                case '8':
                    let $type8_ul = $('<ul class=""></ul>').append(function () {
                        /*
                                                let list = _this.english ? ['', 'Excellent', 'Good', 'Average', 'Fair', 'Poor', 'Not yet determined'] : ['', '极好', '好', '一般', '较差', '差', '尚无法评估'];
                                                return list.reduce(function (prev, cur) {
                                                    return `${prev} <li>（<span></span>）${cur}</li>`
                                                });
                        */
                        let list = _this.english ? ['', 'Excellent', 'Good', 'Average', 'Fair', 'Poor', 'Not yet determined'] : ['', '极好', '好', '一般', '较差', '差', '尚无法评估'];
                        return list.reduce(function (prev, cur) {
                            return `${prev} <li><input type="radio" name="${item.title.id}_zongtipingjia">${cur}</li>`
                        });
                    });
                    $wrap.append(`
                            <div class="module-content type8-content pt-4">
                                <h4>${item.contents[0].temp_name}</h4>
                                ${$type8_ul[0].outerHTML}
                                <h4>${item.contents[1].temp_name}</h4>
                                <div class="border multiText mt-4 p-2"></div>
                                <h4>${item.contents[2].temp_name}</h4>
                                <div class="border multiText mt-4 p-2"></div>
                            </div>`);
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows && data.rows.length > 0) {
                            // $wrap.find('ul>li').eq(1 + data.rows[0][item.contents[0].column_name]).find('span').text('√')
                            $wrap.find('ul>li').eq(data.rows[0][item.contents[0].column_name] - 1).find('[type=radio]').prop('checked', true)
                                .parents('ul').siblings('.multiText:eq(0)').text(data.rows[0][item.contents[1].column_name])
                                .siblings('.multiText').text(data.rows[0][item.contents[2].column_name]);
                        } else {
                            console.warn(item.title.temp_name + '-总体评价-没有返回数据！')
                        }
                    });
                    break;
                // 9-财务模块结构和多行文本框数据
                /*case '9':
                    if (item.title.sort === 1) { //财务
                        if (item.title.get_source.includes('type=1') || item.title.get_source.includes('type=2')) {
                            _this.type9MulText = item;
                        } else {
                            _this.type9BigData = item;
                        }
                    } else {
                        items.forEach((item0, index0) => {
                            if (item.title.float_parent === item0.title.id) {
                                if (item0.smallModileType === '10') {//财务表头
                                    if (item0.title.get_source.includes('type=1') || item0.title.get_source.includes('type=2')) {
                                        _this.type9TableHead.push(item)
                                    } else {
                                        _this.type9TableHeadBigData.push(item)
                                    }
                                } else {
                                    //非财务的浮动——表格上面的时间
                                    // 12-102中文；14-102English；下面给这两种的出资情况加时间
                                    if ((['12', '14'].includes(_this.row.report_type) && item0.title.temp_name === '出资情况') ||
                                        (['15'].includes(_this.row.report_type) && item0.title.temp_name === '股东信息')) {
                                        $.get(this.getUrl(item), (data) => {
                                            let date = data.rows && data.rows.length > 0 ? data.rows[0].date : '';
                                            $("#" + item0.title.id + " .module-content").prepend(`<h4>截止日期：${date}</h4>`);
                                        })


                                    }

                                }
                            }
                        })
                    }
                    break;*/
                case '9':
                    items.forEach((item0, index0) => {
                        if (item.title.float_parent === item0.title.id) {
                            if (item0.smallModileType === '10') {//财务浮动
                                if (item.title.sort === 1) { //财务表头
                                    if (item.title.get_source.includes('type=1') || item.title.get_source.includes('type=2')) {
                                        _this.type9MulText = item;
                                    } else {
                                        _this.type9BigData = item;
                                    }
                                } else { //内部各表
                                    if (item0.title.get_source.includes('type=1') || item0.title.get_source.includes('type=2')) {
                                        _this.type9TableHead.push(item)
                                    } else {
                                        _this.type9TableHeadBigData.push(item)
                                    }
                                }
                            } else {
                                //非财务的浮动——表格上面的时间
                                // 12-102中文；14-102English；下面给这两种的出资情况加时间
                                if ((['12', '14'].includes(_this.row.report_type) && item0.title.temp_name === '出资情况') ||
                                    (['15'].includes(_this.row.report_type) && item0.title.temp_name === '股东信息')) {
                                    $.get(this.getUrl(item), (data) => {
                                        let date = data.rows && data.rows.length > 0 ? data.rows[0].date : '';
                                        $("#" + item0.title.id + " .module-content").prepend(`<h4>截止日期：${date}</h4>`);
                                    })
                                }
                            }
                        }
                    });

                    break;
                // 10-财务模块表格数据
                case '10':
                    $wrap.append(`<div class="module-content type10-content"></div>`);
                    if (item.title.get_source.includes('type=1') || item.title.get_source.includes('type=2')) {
                        _this.type10Items = item;
                    } else {
                        _this.type10BigData = item;
                    }

                    break;
                // 20-单选框判断后加一个多行文本框
                case '20':
                    const $type20_div = $('<div class="radioBox p-3" ></div>').append(['', '有，詳列如下。Yes, as provided below.', '無，根據企業登記機關所示資料，該企業並未設立任何分支機構。No, according to the registry, subject company has not set up any branches.', '企業登記機關並未提供該企業分支機構資料。The registry does not disclose any information on branches.']
                        .reduce(function (prev, cur) {
                            return `${prev}<input class="my-2" type="radio" name="embranchment" >${cur}<br>`
                        }));
                    $wrap.append(`<div class='module-content type20-content'>${$type20_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows && data.rows.length > 0) {
                            let column_name_radio = item.title.column_name.split(',')[0];
                            let column_name_text = item.title.column_name.split(',')[1];
                            let radioSelect = data.rows[0][column_name_radio];
                            if (radioSelect === null) {
                                return
                            }
                            $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                            if (radioSelect === 1) {
                                $wrap.find('.type20-content').append(`<div class="border multiText mt-4 p-2">${data.rows[0][column_name_text]}</div>`)
                            }
                        } else {
                            console.warn(item.title.temp_name + '-102单选&文本框-没有返回数据！')
                        }
                    });
                    break;
                // 21-单选框判断后加一个表格
                case '21':
                    const $type21_div = $('<div class="radioBox p-3" ></div>').append(['', '有，詳列如下。Yes, as provided below.', '無，根據企業登記機關所示資料，無變更記錄。No, according to the registry, subject company has not altered its registration record.', '企業登記機關並未提供該企業變更記錄。The registry does not disclose any information on changes in registration record.']
                        .reduce(function (prev, cur) {
                            return `${prev}<input class="my-2" type="radio" name="registration_change" >${cur}<br>`
                        }));
                    $wrap.append(`<div class='module-content type20-content'>${$type21_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows && data.rows.length > 0) {
                            let radioSelect = data.rows[0][item.title.column_name];


                            // 为了解决有时爬取到数据，表格绘制出来了但单选状态却没有更新的情况
                            $.get(`${this.BASE_PATH}ReportGetData/getBootStrapTable?type=1&tableName=credit_company_his&className=CreditCompanyHis&conf_id=${item.title.id}&company_id=${this.row.company_id}`, data => {
                                if (data.rows.length > 0) {
                                    $wrap.find('.radioBox>[type=radio]').eq(0).prop('checked', true);
                                    this.setTable(item, $wrap, '', 'alter_source');
                                } else {
                                    if (radioSelect === null) {
                                        return
                                    }
                                    $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                                    if (radioSelect === 1) {
                                        this.setTable(item, $wrap, '', 'alter_source');
                                    }
                                }
                            });
                        } else {
                            console.warn(item.title.temp_name + '-102单选&表格-没有返回数据！')
                        }
                    });
                    break;
                // 23-质检表单
                case '23':
                    $wrap.append(type23_html);
                    $wrap.find("[for=grade]").text(item.contents[0].temp_name + ' : ')
                        .end().find("[for=quality_opinion]").text(item.contents[1].temp_name + ' : ');
                    let dealQualityData = (param, param2) => {
                        let checkedIndex = $(".type23-content").find('.radio-box [type=radio]:checked').parent().index() + 1;
                        $.get(this.getUrl(item, '', {
                                id: this.qualityOpinionId,
                                quality_opinion: $wrap.find("#quality_opinion").val() || '',
                                quality_deal: checkedIndex,
                                quality_type: _this.row.quality_type,
                                report_type: _this.row.report_type,
                                report_module_id: item.title.id,
                                grade: $wrap.find("#grade").val()
                            }) + (param === 'update' ? '&update=true' : '')
                            + (param2 === 'submit' ? '&submit=true' : ''),
                            data => {
                                if (!data.rows || data.rows.length === 0) {
                                    return
                                }
                                // 保存或提交后如果出错则报错再跳转，实际上业务只有提交可能报错
                                // data.message = "报告生成或者发送失败！请联系管理员！";
                                // data.statusCode = 0;
                                if (param === 'update' || param2 === 'submit') {
                                    if (data.statusCode === 0) {
                                        Public.message('error', data.message)
                                    } else {
                                        Public.message('success', '保存成功！')
                                    }
                                    setTimeout(function () {
                                        $('#reportbusiness').click();//触发报告质检菜单跳转到列表
                                    }, 1500);
                                }
                                // console.log('$wrap.find("#quality_opinion").val()2', $wrap.find("#quality_opinion").val());
                                this.qualityOpinionId = data.rows.length > 0 && data.rows[0].id ? data.rows[0].id : '';
                                let quality_type = this.row.quality_type;
                                let status = this.row.status;
                                this.quality_deal = data.rows[0].quality_deal;
                                if (this.quality_deal === '3') {
                                    $('.select2-container').addClass('disable');
                                }
                                //不同的质检类型禁用页面相关组件
                                switch (quality_type) {
                                    case 'entering_quality':
                                        if ($('.select2-container'.length === 0)) {

                                        }
                                        if (!(status === '298' || status === '294')) {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                        } else if (data.rows.quality_deal === '2') {// 还可以修改质检意见
                                            $('.select2-container').addClass('disable');
                                        }
                                        break;
                                    case 'analyze_quality':
                                        if (status !== '303') {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                        } else if (data.rows.quality_deal === '2') {
                                            $('.select2-container').addClass('disable');
                                        }
                                        break;
                                    case 'translate_quality':
                                        if (status !== '308') {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                        } else if (data.rows.quality_deal === '2') {
                                            $('.select2-container').addClass('disable');
                                        }
                                        break;
                                }

                                if (data.rows && data.rows.length > 0) {
                                    $("#quality_opinion").val(data.rows[0].quality_opinion || '');
                                    $("#grade").val(data.rows[0].grade || 0);
                                    $(".type23-content").find('.radio-box [type=radio]').eq(data.rows[0].quality_deal - 1).prop('checked', true);
                                    _this.row.qualityDataId = data.rows[0].id;
                                } else {
                                    console.warn(item.title.temp_name + '-质检评分-没有返回数据！')
                                }
                            });
                    };
                    dealQualityData();
                    $("#save").click(function (e, param) {
                        _this.getQualitySelectData('update'); //质检结果
                        dealQualityData('update', param); //质检意见、分数等

                    });
                    $("#submit").click(function () {
                        $("#save").trigger('click', 'submit');
                    });
                    break;
                // 25-企业结构树形图
                case '25':
                    $wrap.append(`<div class='module-content type25-content'><div id="ec04_tree" style="height: 32rem;"></div></div>`);
                    $(".main .table-content").append($wrap);
                    let getLastChildren = str => {
                        if (!str) {
                            return ''
                        }
                        let lastIndex = str.lastIndexOf('[');
                        str = str.substr(0, lastIndex);
                        return str;
                    };
                    let data = [
                        '123/人/男人/老头,小男孩',
                        '123/神仙/天神',
                        '123/人/男人/男子',
                        '123/人/女人/老太太',
                        '123/人/机器人/alpha狗',
                        '123/魔鬼',
                        '123/人/女人/妇女,小姑娘',
                    ];
                    let level1Str = data[0].split('/')[0];
                    let isSameLevel1 = data.every((item, index, arr) => {
                        return arr[0].split('/')[0] === item.split('/')[0]
                    });
                    if (!isSameLevel1) {
                        level1Str = ''
                    }

                    // 逗号问题
                    let newData = [];
                    data = data.forEach(function (item) {
                        if (isSameLevel1) {
                            item = item.slice(item.indexOf('/') + 1);
                        }
                        if (item.includes(',')) {
                            let arr = item.substring(item.lastIndexOf('/') + 1).split(',').forEach(function (str) {
                                newData.push(item.substring(0, item.lastIndexOf('/') + 1) + str)
                            });
                        } else {
                            newData.push(item)
                        }
                    });

                    // 封装成数组
                    let objArr = [];
                    newData.forEach((item, index) => {
                        let obj = {};
                        item.split("/").forEach((leverData, levelIndex) => {
                            let path = '.children[0]'.repeat(levelIndex);
                            eval('obj' + path).children = [];
                            eval('obj' + path).children.push({name: leverData});
                        });
                        objArr.push(obj)
                    });
                    console.log("~~~对象数组：", objArr);

                    //递归初始化
                    let [targetPosition, currPosition] = ['', ''];
                    let targetObj = objArr[0];
                    objArr.forEach(function (obj, index) {
                        if (index > 0) {
                            targetPosition = '.children[0]';
                            currPosition = '.children[0]';
                            process(obj);
                        }
                    });

                    //递归调用遍历属性
                function process(obj) {
                    let i = '';
                    eval('targetObj' + getLastChildren(targetPosition)).forEach(function (node, index) {
                        // console.log(node)
                        if (node.name === eval('obj' + currPosition + '.name')) {
                            targetPosition = getLastChildren(targetPosition) + '[' + index + ']';
                            i = index;
                        }
                    });
                    if (typeof i !== 'number') { //属性不存在，直接添加
                        eval('targetObj' + getLastChildren(targetPosition)).push(eval('obj' + currPosition))
                        return;
                    }
                    targetPosition += '.children[0]';
                    currPosition += '.children[0]';
                    if (eval('obj' + getLastChildren(currPosition))) {
                        process(obj)
                    }
                }

                    console.log("~~~targetObj：", JSON.stringify(targetObj, undefined, 2));
                    let myChart = echarts.init($("#ec04_tree")[0]);
                    myChart.setOption(option = {
                        tooltip: {
                            trigger: 'item',
                            triggerOn: 'mousemove'
                        },
                        series: [
                            {
                                type: 'tree',
                                data: [$.extend(true, {name: level1Str || '企业组织结构'}, targetObj)],
                                left: '2%',
                                right: '2%',
                                top: '8%',
                                bottom: '20%',
                                symbol: 'emptyCircle',
                                orient: 'vertical',
                                expandAndCollapse: true,
                                initialTreeDepth: 3,
                                label: {
                                    normal: {
                                        position: 'top',
                                        // rotate: -90,
                                        verticalAlign: 'middle',
                                        align: 'right',
                                        fontSize: 9
                                    }
                                },
                                leaves: {
                                    label: {
                                        normal: {
                                            position: 'bottom',
                                            // rotate: -90,
                                            verticalAlign: 'middle',
                                            align: 'left'
                                        }
                                    }
                                },
                                animationDurationUpdate: 750
                            }
                        ]
                    });
                    break;
                // 26-文本框+柱线图 (行业情况-xy)
                case '26':
                    $wrap.append(`<div class='module-content type26-content'>
                                    <div class="border multiText mt-4 p-2"></div>
                                    <div class="chartBox" id="ec02_lineBar" ></div>
                                </div>`);
                    $(".main .table-content").append($wrap);
                    $.get(this.getUrl(item), data => {
                        if (data.rows.length === 0) {
                            console.warn(item.title.temp_name + ' 没有返回数据！');
                            return;
                        }
                        let chartData = {xAxisData: [], y1Data: [], y2Data: []};
                        let [title, remark] = ['', ''];
                        item.contents.forEach((content, i) => {//5个数据分别是标题，备注，图表的x轴、y1轴、y2轴
                            let [param, url] = ['', ''];
                            if (content.get_source) {
                                param = content.get_source.split('*')[1];
                                url = BASE_PATH + `credit/front/ReportGetData/${content.get_source.split('*')[0]}&${param}=${this.row[param]}&conf_id=${item.title.id}`;
                            }
                            // console.assert(url,url,item);
                            if (i === 0) {
                                title = content.temp_name;
                                $.get(url, data => {
                                    if (data.rows[0][content.column_name]) {
                                        $wrap.find('h3').text(data.rows[0][content.column_name])
                                    }
                                })
                            } else if (i === 1) {
                                remark = content.temp_name;
                                $.get(url, data => {
                                    $wrap.find('.multiText').text(data.rows[0][content.column_name])
                                })
                            } else {
                                let arr = ['xAxis', 'y1', 'y2'];
                                chartData[arr[i - 2] + 'Name'] = content.temp_name;
                                data.rows.forEach(oneData => {
                                    chartData[arr[i - 2] + 'Data'].push(oneData[content.column_name])
                                })
                            }
                        });
                        if (data.rows.length > 0) {
                            this.drawChart($wrap, chartData)['lineBar']('#ec02_lineBar');// 绘制图表
                        }
                    });
                    break;
                default:
                    console.warn(item.title.temp_name + '没有找到模块类型！');
            }
            if (!['1', '11', '22', '26'].includes(smallModuleType)) {
                $(".main .table-content").append($wrap);
            }
        });
        this.setCwData();
        // 此处数据结构有改动(190226)，根据item.title.get_source里面的参数type判断，为1是普通财务，否则是大数财务
        const bigData = this.type9BigData.title && this.type10BigData.title;
        if (bigData) {
            this.setCwData(bigData);
        }

        // 质检结果下拉列表
        if (this.isQuality) {
            this.setQualitySelect();
            $(".main-header .tab_bar>li:lt(2)").hide();
        }
        //点击返回时判断是否保存
        $('.return_back').on('click', function () {
            layer.confirm('是否要保存？', {
                btn: ['保存', '取消'] //按钮
            }, function () {
                $('#save').trigger('click');
                location.reload();
            }, function () {
                location.reload();
            });

        })
    },
    //财务部分
    setCwData(bigData) {
        /*
        * type9MulText：包含单位、多行文本框部分的标签，其data_source可获取对应的值、表格头部的日期；
        *               其中的contents的[4,5]是单位的标签，其中的data_source可获取下拉框来将数值转换为对应文本，
        *               contents的[10]开始往后是多行文本框的标签。
        *               这个数组并不安全，后台可能从前面插入元素，所以发现后要平移下标。
        * type9TableHead：表格4个部分的标题
        * type10Items：其data_source可获取表格数据内容，其中parent_sector、son_sector和表格顺序对应
        * */
        let [type9MulText, type9TableHead, type10Items] = bigData ? [this.type9BigData, this.type9TableHeadBigData, this.type10BigData] : [this.type9MulText, this.type9TableHead, this.type10Items];
        let $tableBox = $('<div class="tableBox m-4"><h4 class="text-center p-3"></h4></div>');
        let $allTable = $('<div class="tableAll"></div>');
        type9TableHead.forEach(function (item) { //每个表格组的标题
            $allTable.append($tableBox.find('h4').text(item.title.temp_name).end()[0].outerHTML);
        });
        // 获取多行文本框的标签
        let $mulTextBox = $('<div class="mulTextBox"></div>');
        if (!type9MulText.contents) {
            return
        }
        console.log('~~type9MulText.contents',type9MulText.contents);
        type9MulText.contents.forEach(function (content, index) {// 多行文本框
            if (index < 10) {
                return
            }
            if (index % 2 === 0) { //固定顺序，从[10]开始，一个名字一个内容
                $mulTextBox.append(`<div class="item">
                                        <h4>${content.temp_name}: <span id="${content.column_name}"></span></h4>
                                        <div class="border multiText mt-2 m-3 p-2"></div><div class="pt-1"></div>
                                    </div>`)
            } else {
                $mulTextBox.children('.item:last').find('.multiText').attr('id', content.column_name)
            }
        });
        // 通过type10获取表格数据内容
        let addTableMark = [];
        // 通过企业父title获取表格头部的日期、单位和多行文本框们
        $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.title.get_source}&company_id=${this.row.company_id}&report_type=${this.row.report_type}`, (type9MulTextData) => {
            $.get(BASE_PATH + `credit/front/ReportGetData/${type10Items.title.get_source}&ficConf_id=${type9MulTextData.rows[0].id}&report_type=${this.row.report_type}`, (data) => {
                data.rows.forEach((row) => {
                    if (addTableMark.includes(row.parent_sector + '-' + row.son_sector)) {
                        //孩子顺序是固定的
                        let firstSonOrder = addTableMark.find((str) => str.slice(0, 1) === row.parent_sector + '').split('-')[1];
                        $allTable.children().eq(row.parent_sector - 1 - (bigData ? 4 : 0)).find('table').eq(row.son_sector - firstSonOrder).find('tbody')
                            .append(`<tr><td><span class="trName">${row.item_name}</span></td><td>${row.begin_date_value}</td><td>${row.end_date_value}</td></tr>`)
                    } else {
                        $allTable.children().eq(row.parent_sector - 1 - (bigData ? 4 : 0))
                            .append(`<table class="table table-hover"><tbody><tr><td><span class="trName">${row.item_name}</span></td><td>${row.begin_date_value}</td><td>${row.end_date_value}</td></tr></tbody></table>`)
                        addTableMark.push(row.parent_sector + '-' + row.son_sector);
                    }
                });
                //根据合并设置标题
                $allTable.find('h4').each(function () {
                    if (type9MulTextData.rows[0].is_merge === '1') {
                        $(this).text($(this).text().split('||')[1])
                    } else {
                        $(this).text($(this).text().split('||')[0])
                    }
                });
                //表格头部的日期和单位
                $allTable.find('.tableBox').each(function (index, item) {
                    let [dateStart, dateEnd] = [type9MulTextData.rows[0].date1, type9MulTextData.rows[0].date2];
                    if ($(this).find('h4').text().includes('利润表')) {
                        [dateStart, dateEnd] = [type9MulTextData.rows[0].date3, type9MulTextData.rows[0].date4];
                    }
                    $(this).find('table:eq(0)').prepend(`<thead>
                    <tr><th></th><th></th><th>${type9MulText.contents[(bigData ? 1 : 4)].temp_name}：<span class="currency" ></span>（<span class="currency_ubit" ></span>）</th></tr>
                    <tr><th></th><th>开始日期：${dateStart || '&emsp;'}</th><th>结束日期：${dateEnd || '&emsp;'}</th></tr>
                </thead>`);
                });
                // 通过企业父title的某个内容获取下拉框来转换单位
                $.when(
                    $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.contents[(bigData ? 1 : 4)].get_source}`),
                    $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.contents[(bigData ? 2 : 5)].get_source}`)
                ).done(function (unitData, currencyUbitData) {
                    let [currency, currency_ubit] = [type9MulTextData.rows[0].currency, type9MulTextData.rows[0].currency_ubit]
                    let $select1 = $(`<select id="currencyUnitTrans">${unitData[0].selectStr}</select>`);
                    let $select2 = $(`<select id="currencyUbitDataTrans">${currencyUbitData[0].selectStr}</select>`);
                    let currencyText = $select1.val(currency).find("option:selected").text();
                    let currencyUbitText = $select2.val(currency_ubit).find("option:selected").text();
                    $allTable.find(".currency").text(currencyText);
                    $allTable.find(".currency_ubit").text(currencyUbitText);
                });
                // 多行文本框赋值
                $.get(BASE_PATH + `credit/front/ReportGetData/getSelete?type=profitablity_sumup&selectedId=670&disPalyCol=detail_name`, (optionStr) => {
                    // let sumup = data.rows[0]
                    let $select3 = $(`<select id="sumupDataTrans">${optionStr.selectStr}</select>`);
                    $mulTextBox.children('.item').find('[id]').each(function (index, item) {
                        let id = $(this).attr('id');
                        let text = $(this).is('span') ? $select3.val(type9MulTextData.rows[0][id]).find("option:selected").text() : type9MulTextData.rows[0][id];
                        $(item).text(text);
                    });
                });
            })
        });
        $(".type10-content:eq(" + (bigData ? 1 : 0) + ")").append($allTable, bigData ? '' : $mulTextBox);
    },
    // 获取质检结果下拉框的数据
    getQualitySelectData(param) {
        let _this = this;
        let optionData = [];
        $(".select2Box select").each(function (index, item) {
            if ($(item).select2('val')) {
                let detail_id_str = $(item).select2('val').map(function (value) {
                    return value.split("_")[1]
                }).join(' ');
                optionData.push({
                    parentId: $(item).parents(".module-wrap").attr("id"),
                    quality_result: detail_id_str,
                })
            } else {
                optionData.push({
                    parentId: $(item).parents(".module-wrap").attr("id"),
                    quality_result: '',
                })
            }
        });
        // 提交下拉框数据，用于增改查(update===true ? 改:(有id ? 查:增))
        $.ajax({
            url: `${this.BASE_PATH}ReportGetData/getOrSaveResult?orderId=${_this.row.id}&quality_type=${_this.row.quality_type}&${param === 'update' ? 'update=true&id=' : ''}`,
            data: {datajson: JSON.stringify(optionData)},
            dataType: "json",
            success: (data) => {
                data.rows.forEach(function (selecte, index) {
                    let valueArrOld = selecte.quality_result.split(" ");
                    if (valueArrOld) {
                        // 赋值
                        let valueArrNew = [];
                        selecte.quality_result.split(" ").forEach(function (itemValue) {
                            valueArrNew.push(selecte.report_model_id + '_' + itemValue);
                        });
                        $(`#${selecte.report_model_id}`).find('.js-example-basic-multiple').val(valueArrNew).change()
                    }
                });
            }
        });
    },
    // 设置质检结果下拉列表
    setQualitySelect() {
        let _this = this;
        this.english = [7, 9, 11].includes(this.row.report_type - 0);
        let detailname = 'detail_name';
        $(".l-title").each(function (index, item) {
            if (!['基本信息', '流程进度', '质检评分', '质检意见', '订单附件', '核实附件', '查档附件'].includes($(this).text())) {
                switch (_this.row.quality_type) {
                    case 'entering_quality':
                        $(this).nextAll('.module-content').after(qualitySelectHtml);
                        break;
                    case 'analyze_quality':
                        // 8/9-商业中/英文报告
                        if (_this.row.report_type === "8" && $(this).text() === '行业分析'
                            || _this.row.report_type === "9" && $(this).text() === 'IndustryAnalyze') {
                            $(this).nextAll('.module-content').after(qualitySelectHtml);
                        }
                        // 10/11-信用中/英文报告
                        if (_this.row.report_type === "10" && ($(this).text() === '行业分析' || $(this).text() === '财务分析')
                            || _this.row.report_type === "11" && ($(this).text() === 'IndustryAnalyze' || $(this).text() === '财务分析')) {
                            $(this).nextAll('.module-content').after(qualitySelectHtml);
                        }
                        break;
                    // 此页面无翻译功能
                }
                if (_this.quality_deal === '3') {
                    $('.select2-container').addClass('disable');
                }
            }
        });
        let BASE_PATH = this.BASE_PATH + 'ReportGetData/';
        // 添加并获取质检结果下拉框选项的内容
        $.get(BASE_PATH + 'selectQuality?type=' + this.row.quality_type + "&disPalyCol=" + detailname, function (data) {
            // 设置option内容
            let OptHtml = '';
            data.rows.forEach((item) => {
                /** @namespace item.detail_id */
                OptHtml += `<option data-detail_id="${item.detail_id}" data-grade="${item.value}">${item.detail_name}</option>`
            });
            $(".select2Box select").html(OptHtml)
                .find('option').each(function (index, option) { //初始化唯一的value值
                $(option).attr("value", $(option).parents(".module-wrap").attr("id") + "_" + $(option).data("detail_id"))
            });
            // select2初始化并计算分数
            $('.js-example-basic-multiple').select2({placeholder: "请选择评分项"}).on('change', function () {
                let grade = 0;
                $('.js-example-basic-multiple').each(function (index, item) {
                    $(item).select2('data').forEach(function (selectOption) {
                        grade += (selectOption.element.dataset.grade - 0);
                    });
                });
                $("#grade").val(grade);
            });
            _this.getQualitySelectData();

        })
    },
    // 设置tabs标签
    setTabs() {
        let tabsHtml = '';
        this.data.tabFixed.forEach((item, index) => {
            tabsHtml += `<li><a href="#${item.anchor_id}" style="padding: 1.25rem 2.125rem;">${item.temp_name}</a></li>`
        });
        $("#tabs").html(tabsHtml).on('click', 'li', function () {
            $(this).addClass('tab-active').siblings().removeClass('tab-active')
        }).children().eq(0).addClass('tab-active')
        Public.tabFixed(".tab_bar", ".main", 120, 90)
    },
    // 设置头部信息
    setHeader() {
        $("#orderName").text(this.data.defaultModule[0].temp_name + " :");
        $("#orderNum").text(this.row.num);
        $("#status").text(this.row.statusName);
    },
    initProcess() {
        let $li = $(`<li>
                        <div class="bar-span-box d-flex align-items-center">
                            <span class="span_bar"></span><span class="span_circle"></span>
                        </div>
                        <span></span>
                        <span></span>
                        <span></span>
                    </li>`);
        // let $ul = $(".process ul");
        let $ul = $("<ul></ul>");
        // let names = ['录入新订单', '报告核实', '信息录入', '报告质检', '分析', '分析质检', '翻译', '翻译质检', '递交客户'];
        for (let i = 0; i < 12; i++) {
            $ul.append($li.clone().children('span:eq(0)').text(this.processNames[i]).end());
        }
        $ul.children('li:eq(7)').after('<br>').next().next().css("margin-left", '10.5rem');
        return $ul;
    },
    /**
     * 加载表格数据
     * @param item 当前模块数据
     * @param $wrap 当前存放DOM的jquery对象
     * @param chartType 要显示何种图表：'pie','lineBar'
     * @param otherProperty get方法中的其他字段参数
     */
    setTable(item, $wrap, chartType, otherProperty) {
        if (item.title.small_module_type !== '21') { //21类型是后来发现爬取到数据后需要优先显示表格，并设置单选项
            $wrap.append(`<div class="module-content type${item.smallModileType}-content tabelBox pt-4 pb-0"></div>`);
        }
        // 有图表的取截止时间
        if (item.smallModileType === '11') {
            $wrap.find(".module-content").append(`<h4>${this.english ? 'as of: ' : '截止时间'}：<span class="asOf"></span></h4>`);
            $.get(this.getUrl(item, 'remark'), (data) => {
                if (data.rows && data.rows.length > 0) {
                    $wrap.find('.asOf').text(`${data.rows[0] ? data.rows[0].date : ''}`);
                }
            });
        }
        let $table = $('<table class="table table-hover"><thead></thead><tbody></tbody></table>');
        let columnNameArr = [];

        item.contents.forEach((item) => {
            // 带日期的单元格加宽
            $table.children('thead').append(`<th class="${item.field_type === 'money' ? 'moneyCol' : ''}" ${['日期', '成立日期', '注册日期', '合作时间', '操作时间'].includes(item.temp_name) ? ' style=min-width:10rem' : ''}>${item.temp_name}</th>`);
            columnNameArr.push(item.column_name);
        });
        $wrap.find(".module-content").append(`${$table[0].outerHTML}`);
        if (item.title.small_module_type !== '21') {
            $(".main .table-content").append($wrap);
        }
        // 绑数
        $.post(this.getUrl(item, otherProperty), {selectInfo: this.selectInfo}, (data) => {
                if (data.rows) {
                    if (data.rows.length === 0) {
                        $wrap.find('tbody').append(`<tr><td class="text-center pt-3" colspan="${item.contents.length}">${this.english ? 'No matching records were found' : '没有找到匹配的记录'}</td></tr>`);
                        return;
                    }
                    let chartData = chartType === 'pie' ? [] : {xAxisData: [], y1Data: [], y2Data: []};
                    let arr = ['xAxis', 'y1', 'y2'];
                    data.rows.forEach((row) => {
                        // 封装图表数据
                        switch (item.smallModileType) {
                            //饼图
                            case '11':
                                let money = row[item.contents[2].column_name];
                                chartData.push({
                                    name: row[item.contents[0].column_name],
                                    value: money.includes('%') ? money.slice(0, -1) - 0 : money - 0
                                });
                                break;
                            //柱线组合图(行业GDP)
                            case '22':
                                item.contents.forEach((content, i) => {
                                    chartData[arr[i] + 'Name'] = content.temp_name;
                                    chartData[arr[i] + 'Data'].push(row[content.column_name])
                                });
                                //猜测数据结构
                               /* let contents = [
                                    {
                                        temp_name:'x轴',
                                        column_name:'a',
                                    },{
                                        temp_name:'y1轴',
                                        column_name:'b',
                                    },{
                                        temp_name:'y2轴',
                                        column_name:'c',
                                    },
                                ];
                                let rows =[
                                    {a:[2013,2014,2015,2016,2017]},
                                    {b:[4800, 4700, 4800, 5000, 4500]},
                                    {c:[0.06, 0.062, 0.068, 0.064, 0.062]},
                                ];*/
                                // 这里遍历组合图表数据
                                break;
                        }
                        let $tr = $('<tr></tr>');
                        columnNameArr.forEach((columnName, index) => {
                            //商标和专利部分需要显示缩略图
                            let [isBrand, aHref] = [false, ''];
                            if (item.title.temp_name === '商标和专利' && index === 2) {
                                isBrand = true;
                                aHref = row[columnName].includes('http') ? row[columnName] : 'http://' + row[columnName];
                            }
                            let tdData = isBrand ? `<a href= ${aHref} target="_blank"><img src=${aHref} alt="商标"></a>` : row[columnName];
                            $tr.append(`<td>${Public.textFilter(tdData, 'null', '-')}</td>`);// 没数据的显示 “-”
                        });
                        $wrap.find('tbody').append($tr);
                    });
                    //股东信息表格加合计功能
                    if (['股东信息', 'Shareholding'].includes(item.title.temp_name)) {
                        let $sumTr = $('<tr class="tableSum font-weight-bold" style="background-color: var(--bg-thead);"></tr>');
                        $wrap.find('thead th').each(function (index, elem) {
                            let tdHtml = '';
                            if (index === 0) {
                                tdHtml = item.title.temp_name === '股东信息' ? '<td>合计</td>' : '<td>total</td>';
                            } else if (['出资金额', 'Amount', '出资比例（%）', '% of Shareholding'].includes($(elem).text())) {
                                let sum = 0;
                                $(this).parents('table').find('tbody>tr').each(function (i, tr) {
                                    sum += ($(tr).find("td").eq(index).text().replace(/[,]/g, "") - 0.0)
                                });
                                if (['出资比例（%）', '% of Shareholding'].includes($(elem).text())) {
                                    sum = sum.toFixed(2);
                                    if (sum === '99.99') {
                                        sum = '100.00'
                                    }
                                }
                                tdHtml = `<td>${sum}</td>`;
                            } else {
                                tdHtml = '<td></td>';
                            }
                            $sumTr.append(tdHtml)
                        });
                        $wrap.find('tbody').append($sumTr);
                    }
                    // 设置千分位
                    let index = $wrap.find('table th.moneyCol').index();
                    $wrap.find('table td:nth-child(' + (index + 1) + ')').text(function () {
                        return  (Number($(this).text().replace(/,/g, ""))||'-').toLocaleString('en-US');
                    });
                    // 质检意见下面的质检类型全部转换成中文
                    if (item.title.temp_name === '质检意见') {
                        $wrap.find('table tr>td:nth-child(1)').text(function () {
                            return qualityTypeToChinese[$(this).text()];
                        })
                    }
                    if (chartType === 'pie') {//饼图中如果参股人的百分比之和小于100，则补上“未知”
                        let sum = chartData.reduce(function (prev, cur) {
                            return cur.value + prev
                        }, 0);
                        if (sum < 100) {
                            chartData.push({
                                name: '未知',
                                value: 100 - sum
                            })
                        }
                    }

                    if (['11', '22'].includes(item.smallModileType)) {
                        this.drawChart($wrap, chartData)[chartType]();// 绘制图表
                    }
                } else {
                    if (item.smallModileType === '22') {
                        // this.drawChart($wrap)['lineBar']();// 绘制测试图表
                    }
                    console.warn(item.title.temp_name + `-表格${chartType ? '&柱线图表' : ''}-没有返回数据！`);
                }
            }
        );
    },
    drawChart($wrap, chartData) {
        return {
            pie: () => {
                $wrap.find(".module-content").append(`<h4 class="guDongSubTitle">出资比例(%)</h4><div class="chartBox" id="ec01_pie" style="height: 32rem;"></div>`)
                let chart = echarts.init($wrap.find(".module-content .chartBox")[0]);
                chart.setOption(opt_pie);
                chart.setOption({
                    color: this.chartColors,
                    series: [{
                        data: chartData,
                    }].map(function (item) {
                        return $.extend(true, item, {
                            type: 'pie',
                            label: {
                                formatter: '{b}: {d}%'
                            },
                            radius: '28%',
                            center: ['50%', '52%'],
                            startAngle: '45'
                        })
                    })
                }, true);
            },
            /**
             * 画柱线组合图
             * @param elem 传入的dom选择器，用于测试
             */
            lineBar: (elem) => {
                let chart;
                if (elem) {
                    $(elem).css('height', '32rem');
                    chart = echarts.init($(elem)[0]);
                } else {
                    $wrap.find(".module-content").append(`<div class="chartBox" id="ec_lineBar" style="height: 32rem;"></div>`);
                    chart = echarts.init($wrap.find(".module-content .chartBox")[0]);
                }
                chartData = chartData || {
                    xAxisName: 'x轴',
                    y1Name: 'y1轴',
                    y2Name: 'y2轴',
                    xAxisData: ['这是', '测试', '数据', 2016, 2017],
                    y1Data: [4800, 4700, 4800, 5000, 4500], //bar
                    y2Data: [0.06, 0.062, 0.068, 0.064, 0.062], //line
                };
                chart.setOption({
                    color: ['#1890ff', '#facc15'],
                    legend: {show: true},
                    xAxis: {
                        name: '\n' + chartData.xAxisName,
                        nameGap: bodyScale * 20,
                        axisLine: {show: false},
                        axisTick: {show: false},
                        data: chartData.xAxisData
                    },
                    grid: {top: '10%'},
                    yAxis: [{
                        name: chartData.y1Name,
                        axisLine: {show: false},
                        axisTick: {show: false},
                    }, {
                        name: chartData.y2Name,
                        axisLine: {show: false},
                        axisTick: {show: false},
                        axisLabel: {interval: 2},
                        splitLine: {lineStyle: {type: 'dashed'}}
                    }],
                    series: [{
                        type: 'bar',
                        name: chartData.y1Name,
                        yAxisIndex: 0,
                        data: chartData.y1Data,
                    }, {
                        type: 'line',
                        name: chartData.y2Name,
                        yAxisIndex: 1,
                        data: chartData.y2Data,
                    }].map(function (item) {
                        return $.extend(true, item, {
                            label: {
                                formatter: '{b}: {d}%'
                            },
                            barWidth: 36 * bodyScale
                        })
                    })
                }, true);
            }
        }
    },
};

OrderDetail.init();