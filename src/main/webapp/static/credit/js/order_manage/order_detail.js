let OrderDetail = {
    init() {
        this.rows = JSON.parse(localStorage.getItem("row"));
        console.log('--rows', this.rows);
        this.BASE_PATH = BASE_PATH + 'credit/front/';
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
        let getUrl = (item) => {

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
        this.data.modules.forEach((item) => {
            // smallModileType数据类型：0-表单，1-表格，11-带饼图的表格，2-附件，4-流程进度
            let smallModuleType = item.smallModileType;
            let $wrap = $moduleWrap.clone().append($moduleTitle.clone()
                .attr('id', item.title.id).text(item.title.temp_name));
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
                    $.get(getUrl(item), (data) => {
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
                    let $table = $('<table class="table"><thead></thead><tbody></tbody></table>');
                    let columnNameArr = [];
                    item.contents.forEach((item) => {
                        $table.children('thead').append(`<th>${item.temp_name}</th>`);
                        columnNameArr.push(item.column_name);
                    });
                    // 绑数
                    $.get(getUrl(item), (data) => {
                        if (data.rows) {
                            data.rows.forEach((row) => {
                                let $tr = $('<tr></tr>');
                                columnNameArr.forEach((columnName) => {
                                    $tr.append(`<td>${row[columnName]}</td>`)
                                });
                                $table.children('tbody').append($tr);
                            });
                            $wrap.append(`<div class="tabelBox p-4">${$table[0].outerHTML}</div>`);
                        } else {
                            console.warn(item.title.temp_name + '-表格-没有返回数据！')
                        }
                    });
                    break;
                case '2':
                    let html = Public.fileConfig(item, this.rows);
                    $wrap.append(`<div class="tabelBox p-4">${html}</div></div>`);
                    // $wrap.append(`<div class="tabelBox p-4">1111111111111</div></div>`);
                    console.log(html)
                    break;
                case '4':
                    let $ul = this.initProcess();
                    $.get(`${getUrl(item)}&order_num=${this.rows.num}`, (data) => {
                        if (data) {
                            console.log('流程进度', getUrl(item), data);
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
        $("#orderName").text(this.data.defaultModule[0].temp_name + " :")
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

    /*附件*/
    fileJudge() {
        let filename = "";
        let filetype = "";
        //let file_content=[];
        let file_content = [{name: "出资比例环形图", type: "doc", url: ""}, {
            name: "出资比例环形图",
            type: "png",
            url: ""
        }, {name: "出资比例环形图", type: "pdf", url: ""}];
        /* 循环遍历文件数组
           取出文件名称
           对文件名称进行判断*/
        for (var i = 0; i < file_content.length; i++) {
            filename = file_content[i].name;
            filetype = file_content[i].type;
            let num = filename.split("").length; //转化成数组后求文件名称的长度
            /*如果文件名称大于四个字 截取前两个字和后两个字*/
            if (num > 4) {
                filename = filename.substr(0, 2) + '..' + filename.substr(filename.length - 2, 2) + '.' + filetype;
            }
            //根据文件类型分配图片
            let fileicon = '../../../static/credit/imgs/order/';
            if (filetype === 'doc' || filetype === 'docx') {
                fileicon += 'word.png'
            } else if (filetype === 'xlsx' || filetype === 'xls') {
                fileicon += 'Excel.png'
            } else if (filetype === 'png') {
                fileicon += 'PNG.png'
            } else if (filetype === 'jpg') {
                fileicon += 'JPG.png'
            } else if (filetype === 'pdf') {
                fileicon += 'PDF.png'
            } else {
                Public.message("info", "不支持上传此种类型文件！");
                return
            }
            /*写入新的文件名称*/
            $(".accessory_box").append(
                ` <div>
    <div class="uploadFile mt-3 mr-3 ml-3">
    <img src=${fileicon} />
    <p class="filename">${filename}</p>
    </div>
    <div class="order-yulan  mt-3 ml-3"><a href="#">预览</a></div></div>`);
        }
        if (file_content.length == 0) {
            $(".accessory_box").append(`
  <div>
                            <div class="uploadFile mt-3 mr-3 ml-3">
                                <div class="over-box">
                                    <img src="../imgs/order/！.png" class="m-auto"/>
                                    <p class="mt-2">暂无附件</p>
                                </div>
                            </div>
                            <div class="order-yulan  mt-3 ml-3 "><a href="#">预览</a></div>
                        </div>
`);
        }
    },

}

OrderDetail.init();