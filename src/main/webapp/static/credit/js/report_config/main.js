/**
 * 模块
 * 0 表单/1 表格/2 附件 / 5 固定底部的按钮/6 信用等级 /
 * 7 单独小模块的多行输入框 / 8 radio类型 / 9 浮动类型 /10 财务模块 /11 对于填报页面是表格
 */
let ReportConfig = {
    init(){
    	this.rows = JSON.parse(localStorage.getItem("row"));
        this.initContent();
        // this.transition()
    },
    transitionStyle(obj){
        let newObj = {}
        for (var key in obj) {
            let key1 = key.replace(/([A-Z])/g,"-$1").toLowerCase();
            newObj[key1] = obj[key]    
        }
       return JSON.stringify(newObj).replace(/\"/g,"").replace("{","").replace("}","").replace(/,/g,";")
    },
    initTable(){
    	/**
    	 * 表格初始化
    	 */
        let _this = this
        
        this.idArr.forEach((item,index)=>{
        	const $table = $("#table"+item);
        	let contents = this.contentsArr[index]
        	let titles = this.title
        	let urlTemp = titles[index].get_source;
        	let conf_id = titles[index].id;
        	if(!urlTemp){return}
        	let url = BASE_PATH  + 'credit/front/ReportGetData/'+ urlTemp.split("*")[0] + `&conf_id=${conf_id}`
        	if(urlTemp.split("*")[1]){
        		let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
        		tempParam.forEach((item,index)=>{
        			url += `&${item}=${this.rows[item]}`
        		})
        	}
			 
			 let selectInfo = []
        	selectInfo.push(_this.selectInfoObj)
        	
        	$table.bootstrapTable({
        		columns: columns(index,item),
    			url:url, // 请求后台的URL（*）
			    method : 'post', // 请求方式（*）post/get
			    queryParams:function(param){
			    	param.selectInfo = JSON.stringify(selectInfo)
			    	return param
			    },
			    sidePagination: 'server',
			    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    			pagination: false, //分页
    			smartDisplay:true,
    			locales:'zh-CN',
        	});
        	
        	
        	function columns(tempI,tempId){
        		
        		let arr = []
        		contents.forEach((ele,index)=>{
        			if(ele.temp_name !== '操作'){
        				arr.push({
        					title:ele.temp_name,
        					field: ele.column_name,
        					width:(1/contents.length)*100+'%'
        				})
        				
        			}else {
        				arr.push({
        					title:ele.temp_name,
        					field: 'operate',
        					width:1/contents.length,
        					events: {
            					"click .edit":(e,value,row,index)=>{
            						_this.isAdd = false
            						_this.rowId = row.id
            						//回显
            						let formArr = Array.from($("#modal"+tempId).find(".form-inline"))
            						formArr.forEach((item,index)=>{
            							let id = $(item).children("label").siblings().attr("id");
            							let anotherIdArr = id.split("_")
            							anotherIdArr.pop();
            							let anotherId = anotherIdArr.join('_')
            							if($("#"+id).is('select')) {
            								//如果是select
            								$("#"+id).find("option[text='"+row[anotherId]+"']").attr("selected",true);
            							}else {
            								
            								$("#"+id).val(row[anotherId])
            							}
            						})
            					},
            					"click .delete":(e,value,row,index)=>{
            						$("#popEnter").on('click', function(){
            							//确定删除
            							let urlTemp = _this.title[tempI]["remove_source"];
            							let url = BASE_PATH + 'credit/front/ReportGetData/' + urlTemp;
            							$.ajax({
            								url,
            								type:'post',
            								data:{
            									id:row.id
            								},
            								success:(data)=>{
            									if(data.statusCode === 1) {
            										Public.message('success',data.message)
            										//刷新数据
            										_this.refreshTable($("#table"+tempId));
            									}else {
            										Public.message('error',data.message)
            									}
            								}
            							})
            						})
            						
            					}
            				},
            				formatter: function(){return _this.formatBtnArr[tempI]}
        				})
        			}
        		})
        		
        		return arr
        	}
        })
    },
    initModal(){
    	/**
    	 * 初始化模态窗
    	 */
    	let _this = this
    	let modalHtml = ''
    	let ids = this.idArr
    	let contents = this.contentsArr;
    	let titles = this.title
    	//格式化按钮数组
    	this.formatBtnArr = []
    	ids.forEach((item,index)=>{
    		let modalBody = ''
			let myIndex = index;
    		contents[index].forEach((ele,index)=>{
    			if(ele.temp_name === '操作') {
    				return;
    			}
    			
    			if(!ele.field_type || ele.field_type === 'text') {
    				modalBody += ` <div class="form-inline justify-content-center my-3">
									<label for="" class="control-label" >${ele.temp_name}：</label>
									<input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
	    						</div>`
    			}
    			switch(ele.field_type) {
    				case 'date':
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-date">
						                    <label for="" class="control-label" >${ele.temp_name}：</label>
						                    <input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
						                </div>`
    					break;
    				case 'number':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
				    						<label for="" class="control-label" >${ele.temp_name}：</label>
				    						<input type="number" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
    							</div>`
    					break;
    				case 'select':
    					if(!ele.get_source) {return}
    					let url = BASE_PATH + 'credit/front/ReportGetData/' + ele.get_source
    					ele.get_source = ele.get_source.replace(new RegExp(/&/g),"$")
    					_this.selectInfoObj[ele.get_source] = ele.column_name
            			$.ajax({
            				type:'get',
            				url,
            				async:false,
            				dataType:'json',
            				success:(data)=>{
            					modalBody += ` <div class="form-inline justify-content-center my-3">
            						<label for="" class="control-label" >${ele.temp_name}：</label>
            						<select  class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
            							${data.selectStr}
            						</select>
            						</div>`
            				}
            			})
    					break;
    				case 'file':
    					modalBody += ` <div class="form-inline justify-content-center my-3">
						                    <label for="" class="control-label">${ele.temp_name}：</label>
						                    <button type="button" class="form-control" id="modal_logo_icon">
						                        <span style="display:block;height:1.5rem">${ele.place_hold}</span>
						                        <input type="file" class="file-input" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
						                    </button>
						                </div>`
    					break;
    				case 'address' :
    					modalBody += ` <div class="form-inline justify-content-center my-3 modal-address">
		                    <label for="" class="control-label" >${ele.temp_name}：</label>
		                    <input type="text" class="form-control" id="${ele.column_name + '_' + myIndex}" name="${ele.column_name}" >
		                </div>`
    					break;
    				default:
    					break;
    			}
    			
    			
    		})
    		this.formatBtnArr.push(`<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#modal${item}">编辑</a><a href="javascript:;"  class="delete" data-toggle="modal" data-target="#modal_delete">删除</a></div>`)
    		modalHtml += `<div class="modal fade" id="modal${item}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered" role="document">
					        <div class="modal-content">
					            <div class="modal-header">
					              	${titles[index].temp_name}
					            </div>
					            <div class="modal-body">
					                ${modalBody}
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-primary" id="modal_save${item}" data-dismiss="modal">保存</button>
					                <button type="button" class="btn btn-default" id="modal_cancel${item}" data-dismiss="modal">取消</button>
					            </div>
					        </div>
					    </div>
					</div>`
    	})
    	
    	$("#container").append(modalHtml)
    },
    bindFormData(){
    	/**
    	 * 绑定表单数据
    	 */
    	let titles = this.formTitle;
    	let formIndex = this.formIndex;
    	formIndex.forEach((item,index)=>{
    		let conf_id = titles[index].id;
    		let getFormUrl = titles[index].get_source;
    		if(getFormUrl === null || getFormUrl === ''){return}
			let url = BASE_PATH  + 'credit/front/ReportGetData/' + getFormUrl.split("*")[0] 
			let paramObj = {}
			if(getFormUrl.split("*")[1]){
				let tempParam = getFormUrl.split("*")[1].split("$");//必要参数数组
				tempParam.forEach((item,index)=>{
					paramObj[item] = this.rows[item]
				})
			}
			 paramObj["conf_id"] = conf_id
			 let temp;
			 $.ajax({
	    			url,
	    			type:'post',
	    			async:false,
	    			data:paramObj,
	    			success:(data)=>{
	    				temp = data
	    			}
	    			
	    		})
			 let arr = Array.from($("#title"+item))
			 if(temp.rows === null){return}
			 arr.forEach((item,index)=>{
				 if($(item).siblings(".radio-con").length !== 0) {
					 //radio类型绑数
					 if(temp.rows.length === 0){return}
					 let obid = temp.rows[0].id;
					 $(item).siblings(".radio-con").find(".radio-box").find("input").attr("entityid",obid)
					 let overall_rating =  temp.rows[0].overall_rating;
					 let name = $(item).siblings(".radio-con").find(".radio-box").find("input").attr("name")
					 
					 $("input:radio[name="+name+"][value="+overall_rating+"]").attr("checked",true);  
					 return
				 }
				 if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
					 //信用等级
					 let name =$(item).next().find("input").attr("name")
					 $(item).next().find("input").val(temp.rows[0][name])
					  return;
				 }
				 if($(item).next().hasClass("textarea-module")) {
					 //无标题多行文本输入框
					 if(temp.rows.length === 0){return}
					 let obid = temp.rows[0].id;
					 $(item).next().find("textarea").attr("entityid",obid)
					 let name =$(item).next().find("textarea").attr("name")
					 $(item).next().find("textarea").val(temp.rows[0][name])
					 return;
				 }
				 if(($(item).next().find("input").hasClass("float-date"))) {
					 //浮动非财务
					 if(temp.rows.length === 0){return}
					 let obid = temp.rows[0].id;
					 $(item).next().find("input").attr("entityid",obid)
					  let name =$(item).next().find("input").attr("name")
					 $(item).next().find("input").val(temp.rows[0][name])
					 return;
				 }
				 let formArr = Array.from($(item).siblings().find(".form-control"))
				 if(temp.rows.length === 0){return}
				 //实体id
				 let obid = temp.rows[0].id;
				 formArr.forEach((item,index)=>{
					 let obj = temp.rows[0];
    				let id = $(item).attr("id");
    				let anotherIdArr = id.split("_")
    				anotherIdArr.pop();
    				let anotherId = anotherIdArr.join('_')
    				$("#"+id).attr("entryid",obid)
    				if($(item).is('select')){
    					//如果是select
    					$("#"+id).find("option[value='"+obj[anotherId]+"']").attr("selected",true);
    				}else {
    					$("#"+id).val(obj[anotherId])
    				}
				 })
			 })
    		
    	})
    	
    },
    tabChange(){
        /**tab切换事件 */
    	$(".tab-bar li:eq(0) a").addClass("tab-active")
        $(".tab-bar li").click((e)=>{
            $(e.target).addClass("tab-active").parents("li").siblings().children('a').removeClass("tab-active")

            /* 解决锚链接的偏移问题*/
            $("#container ").css('height',"calc(100% - 5.6rem)");
            $(".main ").css('marginBottom',"-.6rem");
        })
    },
    initFloat(){
    	/**
    	 * 初始化浮动模块
    	 */
    	let _this = this
    	let floatIndex = this.floatIndex;
    	let cw_title = []
    	let cw_contents = []
    	let cw_dom;
    	floatIndex.forEach((item,index)=>{
    		let floatParentId = this.floatTitle[index]['float_parent'];//浮动的父节点id
    		let titleId;
    		this.entityTitle.forEach((item,i)=>{
    			if(item.id === floatParentId ) {
    				if(floatParentId !== 853) {
    					//非财务模块浮动
    					let html = this.notMoneyFloatHtml[index]
    					$("#title"+i).after(html)
    					this.formIndex.push(i)
    					this.formTitle.push(this.floatTitle[index])
    				}else {
    					//财务模块浮动
    					cw_title.push(this.floatTitle[index])
    					cw_contents.push(this.floatContents[index])
    					cw_dom = $("#titleCw"+i)
    				}
    				
    			}
    		})
    	})
//    	console.log(cw_title,cw_contents)
    	let cw_top_html = ''
    	let cw_table_html = ''
    	let tableCwId = []
    	cw_title.forEach((item,index)=>{
    		//初始化财务模块
    		let this_content = cw_contents[index];
    		if(item.sort === 1) {
    			//财务模块杂七腊八的配置
    			let radioArr = this_content[1]["get_source"].split("&");
    			let moneySource = this_content[4].get_source;
    			let moneyStr = ''
				let unitSource = this_content[5].get_source;
    			let unitStr = ''
    			$.ajax({
    				url:BASE_PATH + 'credit/front/ReportGetData/' + moneySource,
    				async:false,
    				type:'post',
    				success:(data)=>{
    					moneyStr = data.selectStr
    				}
    			})
    			$.ajax({
    				url:BASE_PATH + 'credit/front/ReportGetData/' + unitSource,
    				async:false,
    				type:'post',
    				success:(data)=>{
    					unitStr = data.selectStr
    				}
    			})
    			cw_top_html += `<div class="top-html mx-4">
    								<div class="cw-box d-flex justify-content-between align-items-center mt-4">
    									<div class="firm-name form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[0].temp_name}</label>
    										<input type="text" style="width:14rem" class="form-control" id="${this_content[0].column_name}cw" placeholder=${this_content[0].place_hold} name=${this_content[0].column_name}>
    									</div>
    									<div class="is-merge form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[1].temp_name}</label>
    										<div class="form-check form-check-inline">
	    										<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[0].split(":")[0]}>
				                                <label class="form-check-label mx-0" for="">${radioArr[0].split(":")[1]}</label>
			                                </div>
						    				<div class="form-check form-check-inline">
							    				<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[1].split(":")[0]}>
							    				<label class="form-check-label mx-0" for="">${radioArr[1].split(":")[1]}</label>
						    				</div>
    									</div>
    									<div class="btn-group">
    										<button class="btn btn-default mr-3 cwDown">${this_content[2].temp_name}</button>
						    				<button class="btn btn-primary cwUp">${this_content[3].temp_name}</button>
    									</div>
    								</div>
    								<div class="cw-unit">
    									<div class="form-inline my-3">
    										<label style="font-weight:600;margin-left:60%" class="mr-3">${this_content[4].temp_name}</label>
    										<select class="form-control mr-3" style="width:10rem" name=${this_content[4].column_name}>${moneyStr}</select>
    										<select class="form-control mr-3" style="width:10rem" name=${this_content[5].column_name}>${unitStr}</select>
    									</div>
    								</div>
    								<div class="cw-date form-inline">
    									<input class="form-control mr-5 my-3" style="margin-left:45%" type="text" name=${this_content[6].column_name} id=${this_content[6].column_name} placeholder=${this_content[6].place_hold} />
    									<input class="form-control" type="text" name=${this_content[7].column_name} id=${this_content[7].column_name} placeholder=${this_content[7].place_hold} />
    								</div>
    							</div>`
    		}else {
    			console.log(item,this_content)
    			let addtext = cw_title[1].place_hold
    			let conf_id = cw_title[0].id
    			let url = cw_title[0].get_source
    			switch(item.sort) {
    				case 2:
    					//合计表
    					cw_table_html += `<div class="table-content1" style="background:#fff">
					            				<table id="tableCwHj"
					            				data-toggle="table"
					            				style="position: relative"
					            				>
					            				</table>
											</div>`
    						tableCwId.push('tableCwHj')
    					setTimeout(()=>{
    						let id = InitObj.bindCwConfig(conf_id,url)
    						InitObj.initCwTable(0,tableCwId,item,this_content,_this.cwGetSource,id)
    						
    					},0)
    				break;
    				case 3:
    					//资产负债表
    					for(let i=0;i<5;i++){
    						cw_table_html += `<div class="table-content1" style="background:#fff">
    							<table id="tableCwFz${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn" >+ ${addtext}</button>
    							</div>`
    						tableCwId.push(`tableCwFz${i}`)
    					}
    				break;	
    				case 4:
    					//利润表
    					for(let i=0;i<4;i++){
    						cw_table_html += `<div class="table-content1" style="background:#fff">
    							<table id="tableCwLr${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn" >+ ${addtext}</button>
    							</div>`
    							tableCwId.push(`tableCwLr${i}`)
    					}
    					break;	
    				case 5:
    					//比率表
						cw_table_html += `<div class="table-content1" style="background:#fff">
							<table id="tableCwBl"
							data-toggle="table"
							style="position: relative"
							>
							</table>
							<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn" >+ ${addtext}</button>
							</div>`
							tableCwId.push(`tableCwBl`)
    					break;	
    				default:
    				break;
    			}
     		}
    	})
    	$(cw_dom).after(cw_table_html)
    	$(cw_dom).after(cw_top_html)
    	
    },
    initContent(){
        /**初始化内容 */
    	this.entityTitle = [] //存放小模块的实体title
    	this.idArr = []    //存放table类型模块对应的index
    	this.contentsArr = [] //存放table类型模块的contents
    	this.title = [] //存放table类型模块的title
    	this.formIndex = [] //存放form类型模块对应的index
    	this.formTitle = [] //存放form类型模块的title
    	this.floatIndex = [] //存放float类型模块对应的index
    	this.floatTitle = [] //存放float类型模块的title
    	this.floatContents = [] //存放float类型模块的Contents
    	this.selectInfoObj = {} //存放选择框信息传给后台
    	this.notMoneyFloatHtml = {} //存放非财务模块的浮动html
    	this.cwGetSource = '' //存放财务url
    	this.saveStatusUrl = ''
		this.submitStatusUrl = ''
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
    	let reportType = JSON.parse(row).report_type
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id,reportType},
        	success:(data)=>{
                setTimeout(()=>{
                	_this.initModal();
                	InitObj.addressInit();
                	InitObj.regChecked();
                	_this.initTable();
                	_this.initFloat();
                	InitObj.dateInit();
                	_this.bindFormData();
                	_this.tabChange();
                	_this.modalClean();
                	_this.bottomBtnEvent();
            	    Public.tabFixed(".tab-bar",".main",120,90)
            	    
            	    let firmArr = Array.from($(".firm-info"));
            	    firmArr.forEach((item,index)=>{
            	    	if($(item).children().length === 2) {
            	    		$(item).addClass("abc")
            	    	}
            	    })
                },0)
                /**
                 * 头部
                 */
                let header = data.defaultModule;
                let headerHtml = ''
            	let temp = ''
                header.forEach((item,index)=>{
                	if(item.node_level === '1') {
                		headerHtml += `<div class="order-num main-title">${item.temp_name}：<span id="num"></span></div>`
                	}else {
                		if(index < 4){
                			temp += `<div class="order-item col-md-4">
		                                <span class="fw"  >${item.temp_name}：</span>
		                                <span id=""></span>
		                            </div>`
                		}else {
                			temp += `<div class="order-item col-md-4 mt-3">
		                                <span class="fw"  >${item.temp_name}：</span>
		                                <span id=""></span>
		                            </div>`
                		}
                	}
                })
                headerHtml += `<div class="order-item-box d-flex flex-wrap justify-content-start row pr-4">`
            	headerHtml += 	temp;
                headerHtml += `</div>`
                $(".order-box").html(headerHtml)
                //头部绑数
                let row = JSON.parse(localStorage.getItem("row"))
                $("#num").html(row.num)
                let tempArr = [row.companyNames,row.companyZHNames,row.reportType,row.receiver_date,row.end_date]
                let headItem = Array.from($(".fw"))
                headItem.forEach((item,index)=>{
                	$(item).siblings("span").html(tempArr[index])
                })
                /**
                 * tabFixed
                 */
                let tabFixed = data.tabFixed;
                let tabFixedHtml = ''
                tabFixed.forEach((item,index)=>{
                	tabFixedHtml += `<li class="tab-info"><a href="#anchor${item.anchor_id}">${item.temp_name}</a></li>`
                })
                
                $(".tab-bar").html(tabFixedHtml)
                /**
                 * 内容模块部分
                 */
                let modules = data.modules;    
                let contentHtml = '' 
                let bottomBtn = ''
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	_this.entityTitle.push(item.title)
                	let smallModileType = item.smallModileType
                	if(item.title.temp_name === null || item.title.temp_name === "" || item.title.float_parent) {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3" ><a style="display:none" class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                	}else if(smallModileType === '10'){
                		//财务模块
                		_this.cwGetSource = item.title.get_source
                		contentHtml +=  `<div class="bg-f pb-4 mb-3"><a class="l-title cwModal" name="anchor${item.title.id}" id="titleCw${index}">${item.title.temp_name}</a>`
                	}else if(smallModileType !== '-2' && smallModileType !== '5' ) {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3"><a class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                	}
                	let btnText = item.title.place_hold;
                	let formArr = item.contents; 
                	//模块的类型
                	
                	switch(smallModileType) {
                		case '0':
                			//表单类型
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ind = index
                			let rowNum = 0;//代表独占一行的input数量
		                	formArr.forEach((item,index)=>{
		                		
		                				let formGroup = ''
		                        		//判断input的类型
		                        		let field_type = item.field_type
		                        		if(!field_type) {
		                        			formGroup += `<div class="form-group">
		        						            		<label for="" class="mb-2">${item.temp_name}</label>
		        						            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'text':
		                        					formGroup += `<div class="form-group">
	        						            		<label for="" class="mb-2">${item.temp_name}</label>
	        						            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
	        						            		<p class="errorInfo">${item.error_msg}</p>
	        					            		</div>`
	                        						break;
		                        				case 'number':
			                    						formGroup += `<div class="form-group">
					                        							<label for="" class="mb-2">${item.temp_name}</label>
					                        							<input type="number" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
					                        							<p class="errorInfo">${item.error_msg}</p>
				                        							</div>`
		                        					
		                        					break;
		                        				case 'date':
		                        					formGroup += `<div class="form-group date-form">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
		                        					break;
		                        				case 'date_scope':
		                        					formGroup += `<div class="form-group date-scope-form">
									            		<label for="" class="mb-2">${item.temp_name}</label>
									            		<input type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
									            		<p class="errorInfo">${item.error_msg}</p>
								            		</div>`
		                        					break;
							            		case 'address':
							            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <input type="text" class="form-control"  style="width: 100%" name=${item.column_name} id="${item.column_name}_${ind}">
									                                </div>`
							            			break;
							            		case 'select':
							            			if(item.get_source === null){return}
							            			let url = BASE_PATH + 'credit/front/ReportGetData/' + item.get_source
							            			$.ajax({
							            				type:'get',
							            				url,
							            				async:false,
							            				dataType:'json',
							            				success:(data)=>{
							            				formGroup += `<div class="form-group">
										            					<label for="" class="mb-2">${item.temp_name}</label>
										            					<select name=${item.column_name} id="${item.column_name}_${ind}" class="form-control">
										            						${data.selectStr}
										            					</select>
							            							</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'textarea':
							            			formGroup += `  <div class="form-group"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <textarea class="form-control"  style="width: 100%;height: 6rem" name=${item.column_name} id="${item.column_name}_${ind}"></textarea>
									                                </div>`
									                break;
		        							    default :
		        							    	break;
		                        			}
		                        		}
		                				if(formArr[index-1] && formArr[index-1]['field_type'] === 'address')  {
		                					rowNum += 1
		                				}
		                        		
		                        		if((index - rowNum)%3 === 0  || formArr[index-1]['field_type'] === 'address' || formArr[index]['field_type'] === 'address' || formArr[index]['field_type'] === 'textarea'){
		                        			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
		                        		}
		                        		contentHtml += formGroup;
		                        		
		                        		if(((index+1 - rowNum)%3 === 0 && index !==0) || formArr[index]['field_type'] === 'address' || (formArr[index+1]&&formArr[index+1]['field_type'] === 'textarea') || (formArr[index+1]&&formArr[index+1]['field_type'] === 'address') ||((formArr[index+1]&&formArr[index+1]['field_type'] === 'textarea') && formArr[index+2]&&formArr[index+2]['field_type'] === 'textarea')){
		                        			contentHtml += `</div>`    
		                        		}
		                        		
		                	}) 
		                	contentHtml += `</div>`   
		                	break;
                		case '1':
                			//table类型
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                				</div>`
                		
                			break;
                		case '11':
                			//table类型
                			_this.idArr.push(index)
                			_this.contentsArr.push(item.contents)
                			_this.title.push(item.title)
                			contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				data-toggle="table"
				                				style="position: relative"
				                				>
				                				</table>
				                				<button class="btn btn-lg btn-block mb-3 mt-4" type="button" id="addBtn${index}" data-toggle="modal" data-target="#modal${index}" >+ ${btnText}</button>
                				</div>`
                		
                			break;
                		case '2':
                			//附件类型
                			contentHtml += Public.fileConfig(item,_this.rows)
                			break;
                		case '5':
                			//固定底部的按钮组
                			item.title.column_name === 'save'?_this.saveStatusUrl = item.title.alter_source:_this.submitStatusUrl = item.title.alter_source
                			let className = item.title.column_name === 'save'?'btn btn-default ml-4':'btn btn-primary ml-4'
                			bottomBtn += `<button id=${item.title.column_name} class="${className}">${item.title.temp_name}</button>`
                			break;
                		case '6':
                			//信用等级
                			let inputObj = item.contents[0]
                			_this.formTitle.push(inputObj)
                			_this.formIndex.push(index)
                			contentHtml += `<div class="form-group form-inline p-4 mx-3" id="xydj">
					                          <label >${inputObj.temp_name}</label>
					                          <input type="text" id=${inputObj.column_name} name=${inputObj.column_name} class="form-control mx-3" placeholder="" aria-describedby="helpId" style="border-color:blue">
					                          <span id="helpId" class="text-muted">${inputObj.suffix}</span>
					                        </div>`
                			
                			let tableContents = []
                			item.contents.forEach((item,index)=>{
                				if(index > 0 && index < 5) {
                					tableContents.push(item)
                				}
                			})
                			_this.idArr.push(index)
                			_this.contentsArr.push(tableContents)
                			_this.title.push(item.title)
            				contentHtml += `<div class="table-content1" style="background:#fff">
				                				<table id="table${index}"
				                				style="position: relative"
				                				>
				                				</table>
                							</div>`
            				let explainObj = item.contents[5];
                			let explainUrl = explainObj.get_source;
                			let paramObj = {}
                			if(explainUrl.split("*")[1]) {
                				let tempParam = explainUrl.split("*")[1].split("$");//必要参数数组
                				tempParam.forEach((item,index)=>{
                					paramObj[item] = this.rows[item]
                				})
                			}
                			let conf_id = item.title.id;
                        	paramObj["conf_id"] = conf_id
                			let returnData;
                			$.ajax({
                				url:BASE_PATH + 'credit/front/ReportGetData/' + explainUrl.split("*")[0],
                				data:paramObj,
                				async:false,
                				type:'post',
                				success:(data)=>{
                					console.log(data)
                					returnData = data.rows
                				}
                			})
                			returnData.forEach((item,index)=>{
                				contentHtml += `<p class="m-3 ml-4">${item.describe}</p>`
                			})
                			break;
                		case '7':
                			//单独小模块的多行输入框 保存回显同表单模块
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			let ot_item = item
                			item.contents.forEach((item,index)=>{
                				if(ot_item.title.temp_name === '' || ot_item.title.temp_name === null) {
                					contentHtml += ` <div class="textarea-module form-group mb-3 p-4" style="background:#fff;margin-top:-2rem">
                						<label for="" class="thead-label">${item.temp_name}</label>
                						<textarea name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
            						</div>`
                				}else{
                					contentHtml += ` <div class="textarea-module form-group mb-3 p-4" style="background:#fff">
	                						<label for="" class="thead-label">${item.temp_name}</label>
	                						<textarea name=${item.column_name} id=${item.column_name} rows="2" class="form-control" placeholder=""></textarea>
                						</div>`
                				}
                			})
                			break;
                		case '8':
                			//radio类型 总体评价模块    保存回显同表单模块
                			_this.formTitle.push(item.title)
                			_this.formIndex.push(index)
                			
                			let str = item.contents[0].get_source;
                			let this_item = item
                			let strItem = str.split("&")
                			contentHtml += `<div class="table-content1 form-group radio-con" style="background:#fff">
					                        	<div class="radio-box">`
                				strItem.forEach((item,index)=>{
                				contentHtml += ` <div class="form-check form-check-inline mr-5">
					                                <input class="form-check-input" type="radio" name=${this_item.contents[0].column_name} id="inlineRadio${index}" value=${item.split("-")[0]}>
					                                <label class="form-check-label mx-0" for="inlineRadio${index}">${item.split("-")[1]}</label>
					                            </div>`
                			})
                				
                				
            				contentHtml += `</div></div>`
                			break;
                		case '9':
                			//浮动类型
                			_this.floatIndex.push(index)
                			_this.floatTitle.push(item.title)
                			_this.floatContents.push(item.contents)
                			if(item.contents.length === 0) {
                				//非财务模块的浮动
                				_this.notMoneyFloatHtml[index] = `<div class="form-group form-inline p-4">
					                          <label >${item.title.temp_name === null?'':item.title.temp_name}</label>
					                          <input type="text" placeholder=${item.title.place_hold} name=${item.title.column_name} class="form-control mx-3 float-date" >
					                        </div>`
                			}
                			break;
                		case '10':
                			//财务模块
                			
                			break;
            			default:
            				break;
            		}
                	contentHtml += `</div>`
                })
                
                $(".table-content").html(contentHtml)
                $(".position-fixed").html(bottomBtn)
            }
        })
    },
    modalClean(){
    	/**上传图标 */
        $(".file-input").change(function(){
            let filename = $(this).val().replace("C:\\fakepath\\","");
            let num = filename.split(".").length;
            let filetype = filename.split(".")[num-1];
            if(filetype === 'jpg' || filetype === 'png' || filetype === 'pdf') {
                $("#modal_logo_icon span").text(filename)
            }else {
                Public.message("info","上传文件格式错误！")
            }
        })
    	
    	this.idArr.forEach((item,index)=>{
    		//点击新增一条清空模态框中内容
    		$("#addBtn"+item).click(()=>{
    			this.isAdd = true
    			$("#modal"+item+" input").val("");
    			$("#modal"+item+" input").siblings("span").html("");
		    	$("#modal"+item+" textarea").val("");
	    	    $("#modal"+item+" select").find("option:selected").attr("selected", false);
			    $("#modal"+item+" select").find("option").first().attr("selected", true);
    
    		})
    		//点击模态框保存按钮，新增一条数据
    		$("#modal_save"+item).unbind().click(()=>{
    			let dataJson = []
    			let dataJsonObj = {}
    			let formArr = Array.from($("#modal"+item).find(".form-inline"))
				formArr.forEach((item,index)=>{
					let id = $(item).children("label").siblings().attr("id");
					if($("#"+id).is("button")) {
						let name = $('#'+$('#'+id).children("input").attr("id")).attr("name")
						let val = $('#'+id).children("span").html()
						dataJsonObj[name] = val
					}
					
					//调用form格式化数据函数
					let tempObj = this.getFormData($('#'+id));
					for(let i in tempObj){
						if(tempObj.hasOwnProperty(i))
						dataJsonObj[i] = tempObj[i]
					}
				})
    			
    			
    			let urlTemp = this.title[index].alter_source
    			if(!urlTemp){return}
    			let url = BASE_PATH  + 'credit/front/ReportGetData/' + urlTemp.split("*")[0] 
    			if(!this.isAdd){
    				//是修改保存
    				dataJsonObj["id"] = this.rowId
    			}
            	let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
            	let paramObj = {}
            	tempParam.forEach((item,index)=>{
            		dataJsonObj[item] = this.rows[item]
    			 })
    			 dataJson.push(dataJsonObj)
            	paramObj["dataJson"] = JSON.stringify(dataJson)
            	//调用新增修改接口
            	$.ajax({
            		url,
            		type:'post',
            		dataType:'json',
            		data:paramObj,
            		contentType:'application/x-www-form-urlencoded;charset=UTF-8',
            		success:(data)=>{
            			if(data.statusCode === 1) {
            				Public.message("success",this.isAdd?'新增成功！':'修改成功！')
            				//刷新数据
            				this.refreshTable($("#table"+item));
            			}else {
            				Public.message("error",data.message)
            			}
            		}
            	})
    		})
    	})
    },
    refreshTable(ele){
    	//刷新表格中的数据
    	$(ele).bootstrapTable("refresh")
    },
    getFormData(form) {
    	//序列化
        var unindexed_array = form.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function (n, i) {
          indexed_array[n['name']] = n['value'];
        });

        return indexed_array;
    },
    bottomBtnEvent(){
    	/**
    	 * 底部按钮点击事件
    	 */
    	let formTitles = this.formTitle;
    	let formIndex = this.formIndex;
    	console.log(formTitles,formIndex)
    	let _this = this
    	formIndex.forEach((item,index)=>{
    		let alterSource = formTitles[index]["alter_source"];
    		if(alterSource === null || alterSource === ''){return}
    		let url = BASE_PATH +'credit/front/ReportGetData/'+ alterSource.split("*")[0] ;
    		let dataJson = []
    		let dataJsonObj = {} 
    		if(alterSource.split("*")[1]) {
    			
    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
    			tempParam.forEach((item,index)=>{
    				dataJsonObj[item] = this.rows[item]
    			})
    		}
			 //点击保存按钮
    		$(".position-fixed").on("click","#save",(e)=>{
    			$("#save").addClass("disabled")
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }else if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
    					 //信用等级
    					 let name =$(item).next().find("input").attr("name")
    					 let val =$(item).next().find("input").val()
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					  let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    				 }else if($(item).next().find("input").hasClass("float-date")){
    					 //浮动非财务
    					  let name =$(item).next().find("input").attr("name")
    					  let val =$(item).next().find("input").val()
    					  let id = $(item).next().find("input").attr("entityid")
    					  dataJsonObj["id"] = id
    					  dataJsonObj[name] = val
    				 }else {
    					 let formArr = Array.from($(item).siblings().find(".form-control"))
    					 formArr.forEach((item,index)=>{
    						 let id = $(item).attr("id");
    						 let anotherIdArr = id.split("_")
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    						 }
    					 })
    				 }
    				 
    			 })
    			 dataJson.push(dataJsonObj)
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("#save").removeClass("disabled")
    					 if(data.statusCode === 1 && !formIndex[index+1]) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.saveStatusUrl + `&model.id=${_this.rows["id"]}`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 Public.goToInfoImportPage();
    									 
    								 }else {
    									 Public.message("error",data.message)
    								 }
    							 }
    						 })
    					 }else if(data.statusCode !== 1){
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    			 //点击提交按钮
    		$(".position-fixed").on("click","#commit",(e)=>{
    			$("#commit").addClass("disabled")
    			 let arr = Array.from($("#title"+item))
    			 arr.forEach((item,index)=>{
    				 if($(item).siblings(".radio-con").length !== 0) {
    					 //radio类型绑数
    					 let radioName = $(item).siblings().find(".radio-box").find("input").attr("name")
    					 let id = $(item).siblings().find(".radio-box").find("input").attr("entityid")
    					 let val = $('input[name='+radioName+']:checked').val();
    					 dataJsonObj[radioName] = val
    					 dataJsonObj["id"] = id
    				 }else if($(item).next().attr("id") && $(item).next().attr("id") === 'xydj') {
    					 //信用等级
    					 let name =$(item).next().find("input").attr("name")
    					 let val =$(item).next().find("input").val()
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					 let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    				 }else if($(item).next().find("input").hasClass("float-date")){
    					 //浮动非财务
	   					  let name =$(item).next().find("input").attr("name")
	   					  let val =$(item).next().find("input").val()
	   					  let id = $(item).next().find("input").attr("entityid")
	   					  dataJsonObj["id"] = id
	   					  dataJsonObj[name] = val
	   				 }else {
    					 let formArr = Array.from($(item).siblings().find(".form-control"))
    					 formArr.forEach((item,index)=>{
    						 let id = $(item).attr("id");
    						 let anotherIdArr = id.split("_")
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷')
    						 }
    					 })
    				 }
    			 })
    			 dataJson.push(dataJsonObj)
    			 $.ajax({
    				 url,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(dataJson)
    				 },
    				 contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    				 success:(data)=>{
    					 $("#commit").removeClass("disabled")
    					 if(data.statusCode === 1 && !formIndex[index+1]) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.submitStatusUrl + `&model.id=${_this.rows["id"]}`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 Public.goToInfoImportPage();
    									 
    								 }else if(data.statusCode !== 1){
    									 Public.message("error",data.message)
    								 }
    							 }
    						 })
    					 }else if(data.statusCode !== 1 ){
    						 Public.message("error",data.message)
    					 }
    				 }
    			 })
    		})
    	})
    }
}

ReportConfig.init();