// import $ from "../../assets/wangEditor-3.1.1/src/js/util/dom-core";

let OrderDetail = {
    init: function () {
        // ljl为此对象之外的页面全局变量
        ljl.row = this.row = JSON.parse(localStorage.getItem("row"));
        console.log('~~row：', this.row);
        this.isQuality = !!this.row.quality_type;
        this.quality_deal = '';
        this.qualityOpinionId = '';
        this.cwModules = [];
        [this.type9MulText, this.type9TableHead, this.type10Items] = [{}, [], {}];
        this.BASE_PATH = BASE_PATH + 'credit/front/';
        this.getUrl = (item, otherProperty, paramObj) => {
            let urlArr = (otherProperty ? item.title[otherProperty] : item.title.data_source).split("*");
            if (urlArr[0] === '') {
                return
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
                                if (paramObj[item]) {
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
            url = `${this.BASE_PATH}ReportGetData/${url}&conf_id=${item.title.id}`;
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
    },
    // 页面结构
    initContent() {
        let _this = this;
        let id = this.row.id;
        let reportType = this.row.report_type;
        let type = this.row.quality_type ? '' : 0;// 质检页面type传'',详情页面type传0
        // console.log(this.row.quality_type+'=======================')
        $.get(`${this.BASE_PATH}getmodule/detail/`, {id, reportType, type}, (data) => {
            setTimeout(() => {
                console.log('~~~data：', data);
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
    setContent() {
        let $moduleWrap = $('<div class="module-wrap bg-f company-info mb-4"></div>');
        let $moduleTitle = $('<div class="l-title"></div>');
        this.data.modules.forEach((item) => {
            // smallModileType数据类型：0-表单，1-表格，11-带饼图的表格，2-附件，4-流程进度，6-信用等级，7-多行文本框
            let smallModuleType = item.smallModileType;
            let itemId = item.title.id;
            let _this = this;
            let $wrap;
            if (smallModuleType !== '9') {
                $wrap = $moduleWrap.clone().attr('id', itemId)
                    .append($moduleTitle.clone().text(item.title.temp_name));
            }

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
                            <div class="col-md-4 mt-2 mb-2">
                                <span>${item.temp_name}：</span>
                                <span data-column_name="${item.column_name}"></span>
                            </div>`
                        }
                    });
                    $wrap.append(`
                        <div class="type1-content module-content order-content123">
                            <div class="row mt-2 mb-2">${formHtml}</div>
                        </div>`);
                    //绑数
                    if (item.title.temp_name === '基本信息') { //表单头部取数于本地存储
                        $wrap.find(`span[data-column_name]`).each(function (index, item) {
                            let column_name = $(this).data('column_name');
                            $(this).text(_this.row[column_name])
                        });
                    } else {
                        $.post(this.getUrl(item), {selectInfo: type0_extraUrl}, (data) => {
                            if (data.rows && data.rows.length > 0) {
                                $wrap.find('span[data-column_name]').each(function (index, dom) {
                                    // console.log($(this).data('column_name')+$(this).hasClass('radioBox') )
                                    let column_name = $(this).data('column_name');
                                    if ($(this).hasClass('radioBox')) {
                                        $(this).children().eq(data.rows[0][column_name] - 1).prop('checked', true);
                                    } else {
                                        $(this).text(data.rows[0][column_name])
                                    }
                                })
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
                        $wrap.append(`<div class="module-wrap bg-f company-info">
                            <div class="type4-content module-content bar_box py-3 process">${$ul[0].outerHTML}</div></div>`);
                    });
                    break;
                // 6-信用等级
                case '6':
                    $wrap.append(`<div class="type6-content module-content">${this.creditLevel}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows && data.rows.length > 0) {
                            $wrap.find("#creditLevel").text(data.rows[0][item.title.column_name])
                        } else {
                            console.warn(item.title.temp_name + '-信用等级-没有返回数据！')
                        }
                    });
                    break;
                // 7-多行文本框
                case '7':
                    $wrap.append(`<div class="type7-content module-content"><div class="border multiText m-4 p-2"></div><div class="pt-1"></div></div>`);
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
                            <div class="module-content type8-content">
                                <h4>${item.contents[0].temp_name}</h4>
                                ${$type8_ul[0].outerHTML}
                                <h4>${item.contents[1].temp_name}</h4>
                                <div class="border multiText m-4 p-2"></div>
                                <h4>${item.contents[2].temp_name}</h4>
                                <div class="border multiText m-4 p-2"></div>
                            </div>`);
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows && data.rows.length > 0) {
                            // $wrap.find('ul>li').eq(1 + data.rows[0][item.contents[0].column_name]).find('span').text('√')
                            $wrap.find('ul>li').eq(data.rows[0][item.contents[0].column_name] - 1).find('[type=radio]').prop('checked', true)
                                .parents('ul').siblings('.multiText:eq(0)').text(data.rows[0][item.contents[1].column_name])
                                .siblings('.multiText').text(data.rows[0][item.contents[2].column_name]);
                        } else {
                            console.warn(item.title.temp_name + '-总体评价-没有返回数据！', `${this.getUrl(item)}&order_num=${this.row.num}`)
                        }
                    });
                    break;
                // 9-财务模块结构和多行文本框数据
                case '9':
                    if (item.title.sort === '1') {
                        _this.type9MulText = item;
                    } else {
                        _this.type9TableHead.push(item)
                    }
                    break;
                // 10-财务模块表格数据
                case '10':
                    $wrap.append(`<div class="module-content type10-content"></div>`);
                    _this.type10Items = item;
                    break;
                // 20-单选框判断后加一个多行文本框
                case '20':
                    const $type20_div = $('<div class="radioBox p-3" ></div>').append(['', '有，详情如下。', '无，根据企业登记机关所示数据，该企业并未设立任何分支机构。', '企业登记机关并未提供该企业分支机构数据。']
                        .reduce(function (prev, cur) {
                            return `${prev}<input class="my-2" type="radio" name="embranchment" >${cur}<br>`
                        }));
                    $wrap.append(`<div class='module-content type20-content'>${$type20_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            let column_name_radio = item.title.column_name.split(',')[0];
                            let column_name_text = item.title.column_name.split(',')[1];
                            let radioSelect = data.rows[0][column_name_radio];
                            $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                            if (radioSelect === 1) {
                                $wrap.find('.type20-content').append(`<div class="border multiText m-4 p-2">${data.rows[0][column_name_text]}</div>`)
                            }
                        } else {
                            console.warn(item.title.temp_name + '-102单选&文本框-没有返回数据！')
                        }
                    });
                    break;
                // 21-单选框判断后加一个表格
                case '21':
                    const $type21_div = $('<div class="radioBox p-3" ></div>').append(['', '有，详情如下。', '无，根据企业登记机关所示数据，无变更记录。', '企业登记机关并未提供该企业变更记录。']
                        .reduce(function (prev, cur) {
                            return `${prev}<input class="my-2" type="radio" name="registration_change" >${cur}<br>`
                        }));
                    $wrap.append(`<div class='module-content type20-content'>${$type21_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            let radioSelect = data.rows[0][item.title.column_name];
                            $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                            if (radioSelect === 1) {
                                this.setTable(item, $wrap, '', 'save_source');
                            }
                        } else {
                            console.warn(item.title.temp_name + '-102单选&表格-没有返回数据！')
                        }
                    });
                    break;
                // 质检表单
                case '23':
                    $wrap.append(type23_html);
                    $wrap.find("[for=grade]").text(item.contents[0].temp_name + ' : ')
                        .end().find("[for=quality_opinion]").text(item.contents[1].temp_name + ' : ');
                    let dealQualityData = (param, param2) => {
                        let checkedIndex = $(".type23-content").find('.radio-box [type=radio]:checked').parent().index() + 1;
                        console.log('$wrap.find("#quality_opinion").val()', $wrap.find("#quality_opinion").val())
                        $.get(this.getUrl(item, '', {
                                id: this.qualityOpinionId,
                                quality_opinion: $wrap.find("#quality_opinion").val() ? $wrap.find("#quality_opinion").val() : '',
                                quality_deal: checkedIndex,
                                quality_type: _this.row.quality_type,
                                report_type: _this.row.report_type,
                                report_module_id: item.title.id,
                                grade: $wrap.find("#grade").val()
                            }) + (param === 'update' ? '&update=true' : '')
                            + (param2 === 'submit' ? '&submit=true' : ''),
                            (data) => {
                                console.log('$wrap.find("#quality_opinion").val()2', $wrap.find("#quality_opinion").val());
                                this.qualityOpinionId = data.rows[0].id ? data.rows[0].id : '';
                                let quality_type = this.row.quality_type;
                                let status = this.row.status;
                                switch (quality_type) {
                                    case 'entering_quality':
                                        if ($('.select2-container'.length === 0)) {
                                            this.quality_deal = data.rows.quality_deal;
                                        }
                                        if (!(status === '298' || status === '294')) {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
                                        } else if (data.rows.quality_deal === '2') {// 还可以修改质检意见
                                            $('.select2-container').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
                                        }
                                        break;
                                    case 'analyze_quality':
                                        if (status !== '303') {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
                                        } else if (data.rows.quality_deal === '2') {
                                            $('.select2-container').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
                                        }
                                        break;
                                    case 'translate_quality':
                                        if (status !== '308') {
                                            $('.select2-container,.radio-box,#quality_opinion,#save,#submit').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
                                        } else if (data.rows.quality_deal === '2') {
                                            $('.select2-container').addClass('disable');
                                            this.quality_deal = data.rows.quality_deal;
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
                    $wrap.find("#save").click(function (e, param) {
                        _this.getQualitySelectData('update'); //质检结果
                        dealQualityData('update', param); //质检意见、分数等
                        setTimeout(function () {
                            $('#reportbusiness').click();
                        }, 1500);
                        Public.message('success', '保存成功！')
                    });
                    $wrap.find("#submit").click(function () {
                        $("#save").trigger('click', 'submit');
                    });
                    break;
                // 企业结构树形图
                case '25':
                    /*$("#" + itemId).append(`<div class="ec03_tree" style="height: 32rem;"></div>`);
                    let data = [
                        '人/男人/老头,中年男子,小男孩',
                        '人/女人/老太太,中年妇女,小姑娘',
                        '人/机器人',
                        '神仙'
                    ];
                    let [obj,currentObj] = [{},obj];
                    data.forEach((item,index)=>{
                        item.split("/").forEach((leverData,index)=>{
                            if(leverData.includes(',')){
                                leverData.split(",").forEach((child)=>{
                                    obj.children.push({name:leverData});
                                })
                            }
                            currentObj.children.push({name:leverData});
                            obj = currentObj;
                            currentObj = currentObj[0].children;

                        })
                    })
                    console.log(obj)
                    let chart = echarts.init($(`#${itemId} #ec03_tree`)[0]);
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

                    break;*/
                default:
                    console.warn(item.title.temp_name + '没有找到模块类型！');
            }
            $(".main .table-content").append($wrap);
        });
        this.setCwData();
        // 质检结果下拉列表
        if (this.isQuality) {
            this.setQualitySelect();
            $(".main-header .tab_bar>li:lt(2)").hide();
        }
    },
    //财务部分
    setCwData() {
        /*
        * type9MulText：包含单位、多行文本框部分的标签，其data_source可获取对应的值、表格头部的日期；
        *               其中的contents的[4,5]是单位的标签，其中的data_source可获取下拉框来将数值转换为对应文本，
        *               contents的[10]开始往后是多行文本框的标签
        * type9TableHead：表格4个部分的标题
        * type10Items：其data_source可获取表格数据内容，其中parent_sector、son_sector和表格顺序对应
        * */
        let [type9MulText, type9TableHead, type10Items] = [this.type9MulText, this.type9TableHead, this.type10Items];
        console.info('type9MulText', type9MulText, '\ntype9TableHead', type9TableHead, '\ntype10Items', type10Items);
        let $tableBox = $('<div class="tableBox m-4"><h4 class="text-center p-3"></h4></div>');
        let $allTable = $('<div class="tableAll"></div>');
        type9TableHead.forEach(function (item) {
            // $allTable.append($tableBox.find('h4').text(item.title.temp_name).end());
            $allTable.append($tableBox.find('h4').text(item.title.temp_name).end()[0].outerHTML);
        });
        // 获取多行文本框的标签
        let $mulTextBox = $('<div class="mulTextBox"></div>');
        if(!type9MulText.contents){return}
        type9MulText.contents.forEach(function (content, index) {
            if (index < 9) {
                return
            }
            if (index % 2 === 0) { //固定顺序，从第10个开始
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
        $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.title.data_source}?company_id=${this.row.company_id}&report_type=${this.row.report_type}`, (type9MulTextData) => {
            $.get(BASE_PATH + `credit/front/ReportGetData/${type10Items.title.data_source}?ficConf_id=${type9MulTextData.rows[0].id}&report_type=${this.row.report_type}`, (data) => {
                data.rows.forEach((row) => {
                    if (addTableMark.includes(row.parent_sector + '-' + row.son_sector)) {
                        //孩子顺序是固定的
                        let firstSonOrder = addTableMark.find((str) => str.slice(0, 1) === row.parent_sector + '').split('-')[1];
                        $allTable.children().eq(row.parent_sector - 1).find('table').eq(row.son_sector - firstSonOrder).find('tbody')
                            .append(`<tr><td><span class="trName">${row.item_name}</span></td><td>${row.begin_date_value}</td><td>${row.end_date_value}</td></tr>`)
                    } else {
                        $allTable.children().eq(row.parent_sector - 1)
                            .append(`<table class="table table-hover"><tbody><tr><td><span class="trName">${row.item_name}</span></td><td>${row.begin_date_value}</td><td>${row.end_date_value}</td></tr></tbody></table>`)
                        addTableMark.push(row.parent_sector + '-' + row.son_sector);
                    }
                });
                //表格头部的日期
                $allTable.find('.tableBox').each(function (index, item) {
                    let [dateStart, dateEnd] = [type9MulTextData.rows[0].date1, type9MulTextData.rows[0].date2];
                    if ($(this).find('h4').text().includes('利润表')) {
                        [dateStart, dateEnd] = [type9MulTextData.rows[0].date3, type9MulTextData.rows[0].date4];
                    }
                    $(this).find('table:eq(0)').prepend(`<thead>
                    <tr><th></th><th></th><th>${type9MulText.contents[4].temp_name}：<span class="currency" ></span>（<span class="currency_ubit" ></span>）</th></tr>
                    <tr><th></th><th>${dateStart || '&emsp;'}</th><th>${dateEnd || '&emsp;'}</th></tr>
                </thead>`);
                });
                // 通过企业父title的某个内容获取下拉框来转换单位
                $.when(
                    $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.contents[4].data_source}`),
                    $.get(BASE_PATH + `credit/front/ReportGetData/${type9MulText.contents[5].data_source}`)
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
        $(".type10-content").append($allTable, $mulTextBox);
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
        let detailname = this.english ? 'detail_name_en' : 'detail_name';
        $(".l-title").each(function (index, item) {
            if (!['基本信息', '流程进度', '质检评分', '质检意见', '附件'].includes($(this).text())) {
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
                if (_this.quality_deal === '2') {
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
            tabsHtml += `<li><a href="#${item.anchor_id}">${item.temp_name}</a></li>`
        });
        $("#tabs").html(tabsHtml).on('click', 'li', function () {
            $(this).addClass('tab-active').siblings().removeClass('tab-active')
        }).children().eq(0).addClass('tab-active')
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
        $ul.children('li:eq(9)').after($li.clone().css("visibility", 'hidden')).after('<br>');
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
        // 有图表的取截止时间
        if (chartType === 'pie') {
            $wrap.append(`<h3 class="guDongSubTitle">${this.english ? 'as of: ' : '截止时间'}：<span class="asOf"></span> </h3>`);
            $.get(this.getUrl(item, 'save_source'), (data) => {
                if (data.rows) {
                    $wrap.find('.asOf').text(`${data.rows[0] ? data.rows[0].date : ''}`);
                }
            });
        }
        let $table = $('<table class="table"><thead></thead><tbody></tbody></table>');
        let columnNameArr = [];
        item.contents.forEach((item) => {
            $table.children('thead').append(`<th>${item.temp_name}</th>`);
            columnNameArr.push(item.column_name);
        });
        $wrap.append(`<div class="module-content tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`)
        // 绑数
        $.post(this.getUrl(item, otherProperty), {selectInfo: type1_extraUrl}, (data) => {
                if (data.rows) {
                    if (data.rows.length === 0) {
                        $wrap.find('tbody').append(`<tr><td class="text-center pt-3" colspan="${item.contents.length}">${this.english ? 'No matching records were found' : '没有找到匹配的记录'}</tr></td>`);
                        return;
                    }
                    let chartData = [];
                    data.rows.forEach((row) => {
                        let $tr = $('<tr></tr>');
                        switch (chartType) {
                            case 'pie':
                                let money = row.money;
                                chartData.push({
                                    name: row.name,
                                    value: money.includes('%') ? money.slice(0, -1) - 0 : money - 0
                                });
                                break;
                            case 'lineBar':
                                chartData = {
                                    line: [0.06, 0.062, 0.068, 0.064, 0.062],
                                    bar: [4800, 4700, 4800, 5000, 4500]
                                };
                                break;
                        }
                        columnNameArr.forEach((columnName) => {
                            $tr.append(`<td>${row[columnName] ? row[columnName] : '-'}</td>`);
                        });
                        $wrap.find('tbody').append($tr);
                    });
                    $wrap.append(`<!--<div class="module-content tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>-->`);
                    if (Array.isArray(chartData) && chartData.length > 0 || typeof chartData === 'object' && Object.keys(chartData).length > 0) { // 绘制饼图
                        this.drawChart(item, chartData)[chartType]();// 绘制图表
                    }
                } else {
                    console.warn(item.title.temp_name + `-表格${chartType ? '&图表' : ''}-没有返回数据！`);
                    // $wrap.append(`<div class="module-content tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`);
                }
            }
        );
    },
    drawChart(item, chartData) {
        let itemId = item.title.id;
        return {
            pie: () => {
                $("#" + itemId).append(`
                        <h3 class="guDongSubTitle">出资比例(%)</h3>
                        <div class="chartBox" style="height: 32rem;"></div>`);
                let chart = echarts.init($(`#${itemId} .chartBox`)[0]);
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
            lineBar: () => {
                $("#" + itemId).append(`
                        <h3 class="guDongSubTitle">出资比例(%)</h3>
                        <div class="chartBox" style="height: 28rem;"></div>`);
                let chart = echarts.init($(`#${itemId} .chartBox`)[0]);
                // chart.setOption(opt_lineBar);

                chart.setOption({
                    color: ['#1890ff', '#facc15'],

                    legend: {show: true},
                    xAxis: {
                        axisLine: {show: false},
                        axisTick: {show: false},
                        data: [2014, 2015, 2016, 2017, 2018]
                    },
                    // grid:{top:'15%'},
                    yAxis: [{
                        name: 'y1',
                        axisLine: {show: false},
                        axisTick: {show: false},
                        // splitNumber:6,
                        // interval:5
                    }, {
                        name: 'y2',
                        axisLine: {show: false},
                        axisTick: {show: false},
                        axisLabel: {interval: 2},
                        splitLine: {lineStyle: {type: 'dashed'}}
                        // splitNumber:6,
                        // interval:5
                    }],
                    series: [{
                        type: 'bar',
                        name: 'y1',
                        yAxisIndex: 0,
                        data: chartData.bar,
                    }, {
                        type: 'line',
                        name: 'y2',
                        yAxisIndex: 1,
                        data: chartData.line,
                    }].map(function (item) {
                        return $.extend(true, item, {
                            // type: 'pie',
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