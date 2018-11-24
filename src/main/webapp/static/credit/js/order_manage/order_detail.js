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
                    console.log('--data', data)
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
            // smallModileType数据类型：0-表单，1-表格，11-带饼图的表格，2-附件，4-流程进度
            let smallModuleType = item.smallModileType;
            let itemId = item.title.id;
            let $wrap = $moduleWrap.clone().attr('id', itemId)
                .append($moduleTitle.clone().text(item.title.temp_name));
            switch (smallModuleType) {
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
                case '1':
                    this.setTable(item,$wrap);
                    break;
                case '11':
                    let data0 = this.setTable(item,$wrap,true);
                    // let data = data0.chartData;

                    break;
                case '2':
                    let html = Public.fileConfig(item, this.rows);
                    $wrap.append(`<div class="tabelBox p-4">${html}</div></div>`);
                    break;
                case '4':
                    let $ul = this.initProcess();
                    $.get(`${this.getUrl(item)}&order_num=${this.rows.num}`, (data) => {
                        if (data) {
                            console.log('流程进度', this.getUrl(item), data);
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
        $("#tabs").html(tabsHtml).children().eq(0).addClass('tab-active');
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
    setTable(item,$wrap,hasChart=false){
        let $table = $('<table class="table"><thead></thead><tbody></tbody></table>');
        let columnNameArr = [];
        let chartData = [];
        item.contents.forEach((item) => {
            $table.children('thead').append(`<th>${item.temp_name}</th>`);
            columnNameArr.push(item.column_name);
        });
        // 绑数
        $.get(this.getUrl(item), (data) => {

            if (data.rows) {
                data.rows.forEach((row) => {
                    let $tr = $('<tr></tr>');
                    if(hasChart){
                        chartData.push({
                            name:row.name,
                            value:row.money.slice(0,-1)-0
                        })
                    }
                    columnNameArr.forEach((columnName) => {
                        $tr.append(`<td>${row[columnName]}</td>`);

                    });
                    $table.children('tbody').append($tr);
                });
                $wrap.append(`<div class="tabelBox p-4">${$table[0].outerHTML}</div>`);
                if(hasChart){
                    let itemId = item.title.id;
                    $("#"+itemId).append(`<div class="chartBox" style="height: 20rem;"></div>`)
                    let chart = echarts.init($(`#${itemId} .chartBox`)[0]);
                    chart.setOption(opt_pie);
                    chart.setOption({
                        series: [
                            {
                                data: chartData,
                            }
                        ].map(function (item) {
                            return $.extend(true,item,{
                                type:'pie',
                                radius : '50%',
                                startAngle:'0'
                            })

                        })
                    },true);
                    console.log(chart.getOption())
                }
            } else {
                console.warn(item.title.temp_name + `-表格${hasChart?'&图表':''}-没有返回数据！`)
            }
        });
    },
    /*出资比例环形图*/
    initEchartsPie() {
        let ec001_pie = echarts.init($("#ec001_pie")[0]);
        ec001_pie.clear();
        ec001_pie.setOption(opt_pie);
        ec001_pie.setOption({
            series: [
                {
                    data: [
                        {value: 335, name: '直接访问'},
                        {value: 310, name: '邮件营销'}
                    ],
                }
            ]
        });
    },

}

OrderDetail.init();