let OrderDetail = {
    init: function () {
        this.row = JSON.parse(localStorage.getItem("row"));
        console.log('--row', this.row);
        // alert(this.row.qualityCheck);
        this.BASE_PATH = BASE_PATH + 'credit/front/';
        this.getUrl = (item, otherProperty) => {
            let urlArr = (otherProperty ? item.title[otherProperty] : item.title.data_source).split("*");
            if (urlArr[0] === '') {
                return
            }
            let url = '';
            urlArr.forEach((param, index) => {
                if (index === 0) {
                    url = param;
                } else {
                    if (param === 'orderId') {//这里的orderId对应rows的id，和填报配置中不一样
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
        this.chartColors = ['#1890ff', '#13c2c2', '#2fc25c', '#facc15', '#ef4763', '#8543e0', '#40a9ff', '#36cfc9', '#73d13d', '#ffec3d', '#ff4d4f', '#9254de']
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
        let type = this.row.qualityCheck ? '' : 0;
        $.get(`${this.BASE_PATH}getmodule/detail/`,
            {id, reportType, type},
            (data) => {
                setTimeout(() => {
                    console.log('--data', data);
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
            }
        )
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
            let $wrap = $moduleWrap.clone().attr('id', itemId)
                .append($moduleTitle.clone().text(item.title.temp_name));
            switch (smallModuleType) {
                // 0-表单
                case '0':
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
                        <div class=" order-content">
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
                            if (data.rows) {
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
                    $wrap.append(`<div class="tabelBox p-4">${html}</div></div>`);
                    break;
                // 4-流程进度
                case '4':
                    let $ul = this.initProcess();
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows) {
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
                            <div class="bar_box py-3 process">${$ul[0].outerHTML}</div></div>`);
                    });
                    break;
                // 6-信用等级
                case '6':
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            $wrap.append(`${this.creditLevel}`)
                                .find("#creditLevel").text(data.rows[0][item.title.column_name])
                        } else {
                            console.warn(item.title.temp_name + '-信用等级-没有返回数据！')
                        }
                    });
                    break;
                // 7-多行文本框
                case '7':
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows) {
                            $wrap.append(`<div class="border multiText m-4 p-2">${data.rows[0] ?
                                data.rows[0][item.title.column_name] ? data.rows[0][item.title.column_name] : ''
                                : ''}</div><div class="pt-1"></div>`);
                        } else {
                            console.warn(item.title.temp_name + '-总结-没有返回数据！')
                        }

                    });
                    break;
                // 8-总体评价，一个对勾列表和两个多行文本框
                case '8':
                    let $type8_ul = $('<ul class=""></ul>').append(function () {
                        return ['', '极好', '好', '一般', '较差', '差', '尚无法评估'].reduce(function (prev, cur) {
                            return `${prev} <li>（<span></span>）${cur}</li>`
                        });
                    });
                    $wrap.append(`
                            <div class="type8-content">
                                <h4>${item.contents[0].temp_name}</h4>
                                ${$type8_ul[0].outerHTML}
                                <h4>${item.contents[1].temp_name}</h4>
                                <div class="border multiText m-4 p-2"></div>
                                <h4>${item.contents[2].temp_name}</h4>
                                <div class="border multiText m-4 p-2"></div>
                            </div>`);
                    $.get(`${this.getUrl(item)}&order_num=${this.row.num}`, (data) => {
                        if (data.rows) {
                            $wrap.find('ul>li').eq(1 + data.rows[0][item.contents[0].column_name]).find('span').text('√')
                                .parents('ul').siblings('.multiText:eq(0)').text(data.rows[0][item.contents[1].column_name])
                                .siblings('.multiText').text(data.rows[0][item.contents[2].column_name]);
                        } else {
                            console.warn(item.title.temp_name + '-总体评价-没有返回数据！', `${this.getUrl(item)}&order_num=${this.row.num}`)
                        }
                    });
                    break;
                // 20-单选框判断后加一个多行文本框
                case '20':
                    const $type20_div = $('<div class="radioBox p-3" ></div>').append(['', '有，详情如下。', '无，根据企业登记机关所示数据，该企业并未设立任何分支机构。', '企业登记机关并未提供该企业分支机构数据。']
                        .reduce(function (prev, cur) {
                            return `${prev}<input class="my-2" type="radio" name="embranchment" >${cur}<br>`
                        }));
                    $wrap.append(`<div class='type20-content'>${$type20_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            let column_name_radio = item.title.column_name.split(',')[0];
                            let column_name_text = item.title.column_name.split(',')[1];
                            let radioSelect = data.rows[0][column_name_radio];
                            $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                            if (radioSelect === '1') {
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
                    $wrap.append(`<div class='type20-content'>${$type21_div[0].outerHTML}</div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            let radioSelect = data.rows[0][item.title.column_name];
                            $wrap.find('.radioBox>[type=radio]').eq(radioSelect - 1).prop('checked', true);
                            if (radioSelect === '1') {
                                this.setTable(item, $wrap, '', 'save_source');
                            }
                        } else {
                            console.warn(item.title.temp_name + '-102单选&表格-没有返回数据！')
                        }
                    });
                    break;
            }
            $(".main .table-content").append($wrap);
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
        // 绑数
        $.post(this.getUrl(item, otherProperty), {selectInfo: type1_extraUrl}, (data) => {
                if (data.rows) {
                    if (data.rows.length === 0) {
                        $table.children('tbody').append(`<tr><td class="text-center pt-3" colspan="${item.contents.length}">${this.english ? 'No matching records were found' : '没有找到匹配的记录'}</tr></td>`);
                        $wrap.append(`<div class="tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`);
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
                        $table.children('tbody').append($tr);
                    });
                    $wrap.append(`<div class="tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`);
                    if (Array.isArray(chartData) && chartData.length > 0 || typeof chartData === 'object' && Object.keys(chartData).length > 0) { // 绘制饼图
                        this.drawChart(item, chartData)[chartType]();// 绘制图表
                    }
                } else {
                    console.warn(item.title.temp_name + `-表格${chartType ? '&图表' : ''}-没有返回数据！`);
                    $wrap.append(`<div class="tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`);
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
                // console.log(chart)
            }
        }
    }
};

OrderDetail.init();