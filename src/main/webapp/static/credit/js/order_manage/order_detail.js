let OrderDetail = {
    init() {
        this.rows = JSON.parse(localStorage.getItem("row"));
        console.log('--rows', this.rows);
        this.BASE_PATH = BASE_PATH + 'credit/front/';
        this.getUrl = (item) => {
            let urlArr = item.title.data_source.split("*");
            if (urlArr[0] === '') {
                return
            }
            let url = '';
            urlArr.forEach((param, index) => {
                if (index === 0) {
                    url = param;
                } else {
                    if (param === 'orderId') {//这里的orderId对应rows的id，和填报配置中不一样
                        url += `&${param}=${this.rows.id}`
                    } else {
                        url += `&${param}=${this.rows[param]}`
                    }
                }
            });
            url = `${this.BASE_PATH}ReportGetData/${url}&conf_id=${item.title.id}`;
            return url;
        };
        this.processNames = this.rows.country === '中国大陆' ?
            ['订单分配', '信息录入', '订单核实', '订单查档', '信息质检', '分析录入', '分析质检', '翻译录入', '翻译质检', '报告完成', '客户内容已更新', '订单完成']
            : ['订单查档', '订单分配', '信息录入', '订单核实', '信息质检', '分析录入', '分析质检', '翻译录入', '翻译质检', '报告完成', '客户内容已更新', '订单完成'];
        this.creditLevel = `
                <div class="credit-level-title pt-3">
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
                <p class="m-3 ml-4">以上级别可用来评估该公司的信用风险和确定给予该公司的信用额度。它是经过计算本报告每部分内容综合进行评估的因素和它们在评估中所占的比重（%）</p>
                <p class="m-3 ml-4">财务状况（40%） 股东背景（10%） 付款记录（10%）</p>
                <p class="m-3 ml-4">信用历史（15%） 市场趋势（10%） 经营规模（15%）</p>
                <p class="m-3 ml-4">如果是个体户或无限责任性质的公司，新建立的或缺少财务资料的公司，评估的比重会增加到“股东背景”和“付款记录”两项。</p>
                <p class="m-3 ml-4 pb-4">使用缩写： N/A-不详 CNY-人民币 SC-目标公司</p>`
        this.initContent();

        /*附件*/
        // this.fileJudge();
        // /*出资比例环形图*/
        // this.initEchartsPie();
    },
    initContent() {
        let _this = this;
        let id = this.rows.id;
        let reportType = this.rows.report_type;
        $.get(`${this.BASE_PATH}getmodule/detail/`,
            {id, reportType, type: 0},
            (data) => {
                setTimeout(() => {
                    console.log('--data', data);
                    _this.data = data;
                    _this.setHeader();
                    _this.setTabs();
                    _this.setContent();
                }, 0)
            }
        )
    },
    // 设置内容
    setContent() {
        let $moduleWrap = $('<div class="module-wrap bg-f company-info mb-4"></div>');
        let $moduleTitle = $('<div class="l-title"></div>');
        this.data.modules.forEach((item) => {
            // smallModileType数据类型：0-表单，1-表格，11-带饼图的表格，2-附件，4-流程进度，6-信用等级，7-多行文本框
            let smallModuleType = item.smallModileType;
            let itemId = item.title.id;
            let $wrap = $moduleWrap.clone().attr('id', itemId)
                .append($moduleTitle.clone().text(item.title.temp_name));
            switch (smallModuleType) {
                // 0-表单
                case '0':
                    let formHtml = '';
                    item.contents.forEach((item) => {
                        formHtml += `
                            <div class="col-md-4 mt-2 mb-2">
                                <span>${item.temp_name}：</span>
                                <span id="${item.column_name}"></span>
                            </div>`
                    });
                    $wrap.append(`
                        <div class=" mb-4 order-content">
                            <div class="row mt-2 mb-2">${formHtml}</div>
                        </div>`);
                    //绑数
                    $.get(this.getUrl(item), (data) => {
                        if (data.rows) {
                            $wrap.find('[id]').each(function (index, item) {
                                let id = $(this).attr('id');
                                $(this).text(data.rows[0][id])
                            })
                        } else {
                            console.warn(item.title.temp_name + '-表单-没有返回数据！')
                        }
                    });
                    break;
                // 1-表格
                case '1':
                    this.setTable(item, $wrap);
                    break;
                // 11-带饼图的表格
                case '11':
                    this.setTable(item, $wrap, true);
                    break;
                // 2-附件
                case '2':
                    let html = Public.fileConfig(item, this.rows);
                    $wrap.append(`<div class="tabelBox p-4">${html}</div></div>`);
                    break;
                // 4-流程进度
                case '4':
                    let $ul = this.initProcess();
                    $.get(`${this.getUrl(item)}&order_num=${this.rows.num}`, (data) => {
                        if (data) {
                            this.processNames.forEach((name, index) => {
                                data.forEach((item) => {
                                    if (name === item.order_state) {
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
                        $ul.find('.span_active:last').addClass('circle_active');
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
                    $.get(`${this.getUrl(item)}&order_num=${this.rows.num}`, (data) => {
                        if (data) {
                            $wrap.append(`<div class="border multiText m-4 p-2">${data.rows ? data.rows[0][item.title.column_name] : ''}</div><div class="pt-1"></div>`)
                            console.warn(data)
                        } else {
                            console.warn(item.title.temp_name + '-总结-没有返回数据！')
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
        $("#orderNum").text(this.rows.num);
        $("#status").text(this.rows.statusName);
    },
    initProcess() {
        let $li = $(`<li>
                        <div class="bar-span-box d-flex align-items-center">
                            <span class="span_bar"></span><span class="span_circle"></span>
                        </div>
                        <span>录入新订单</span>
                        <span>&nbsp;</span>
                        <span>&nbsp;</span>
                    </li>`);
        // let $ul = $(".process ul");
        let $ul = $("<ul></ul>");
        // let names = ['录入新订单', '报告核实', '信息录入', '报告质检', '分析', '分析质检', '翻译', '翻译质检', '递交客户'];
        for (let i = 0; i < 9; i++) {
            $ul.append($li.clone().children('span:eq(0)').text(this.processNames[i]).end());
        }
        return $ul;
    },
    setTable(item, $wrap, hasChart = false) {
        let $table = $('<table class="table"><thead></thead><tbody></tbody></table>');
        let columnNameArr = [];
        let chartData = [];
        if (hasChart) {
            let urlArr = item.title.save_source.split("*");
            if (urlArr[0] === '') {
                return
            }
            let url = '';
            urlArr.forEach((param, index) => {
                if (index === 0) {
                    url = param;
                } else {
                    if (param === 'orderId') {//这里的orderId对应rows的id，和填报配置中不一样
                        url += `&${param}=${this.rows.id}`
                    } else {
                        url += `&${param}=${this.rows[param]}`
                    }
                }
            });
            url = `${this.BASE_PATH}ReportGetData/${url}&conf_id=${item.title.id}`;
            $.get(url, (data) => {
                console.log("~~~~~", data)
                $wrap.append(`<h3 class="guDongSubTitle">截止时间：${data.rows ? data.rows[0].date : ''}</h3>`)
            })
        }
        item.contents.forEach((item) => {
            $table.children('thead').append(`<th>${item.temp_name}</th>`);
            columnNameArr.push(item.column_name);
        });
        // 绑数
        $.get(this.getUrl(item), (data) => {
            if (data.rows) {
                data.rows.forEach((row) => {
                    let $tr = $('<tr></tr>');
                    if (hasChart) {
                        let money = row.money;
                        chartData.push({
                            name: row.name,
                            value: money.includes('%') ? money.slice(0, -1) - 0 : money - 0
                        })
                    }
                    columnNameArr.forEach((columnName) => {
                        $tr.append(`<td>${row[columnName] ? row[columnName] : '-'}</td>`);

                    });
                    $table.children('tbody').append($tr);
                });
                $wrap.append(`<div class="tabelBox px-4 pt-4 pb-0">${$table[0].outerHTML}</div>`);
                if (hasChart) { // 绘制饼图
                    let itemId = item.title.id;
                    $("#" + itemId).append(`
                        <h3 class="guDongSubTitle">出资比例(%)</h3>
                        <div class="chartBox" style="height: 20rem;"></div>`)
                    let chart = echarts.init($(`#${itemId} .chartBox`)[0]);
                    chart.setOption(opt_pie);
                    chart.setOption({
                        color: [
                            '#1890ff',
                            '#13c2c2',
                            '#2fc25c',
                            '#facc15',
                            '#ef4763',
                            '#8543e0',
                            '#40a9ff',
                            '#36cfc9',
                            '#73d13d',
                            '#ffec3d',
                            '#ff4d4f',
                            '#9254de',
                        ],
                        series: [{
                            data: chartData,
                        }
                        ].map(function (item) {
                            return $.extend(true, item, {
                                type: 'pie',
                                label: {
                                    formatter: '{b}: {d}%'
                                },
                                radius: '60%',
                                center: ['50%', '50%'],
                                startAngle: '0'
                            })
                        })
                    }, true);
                }
            } else {
                console.warn(item.title.temp_name + `-表格${hasChart ? '&图表' : ''}-没有返回数据！`)
            }
        });
    },

}

OrderDetail.init();