/**
 * 模块
 * 0 表单/1 表格/2 附件 / 5 固定底部的按钮/6 信用等级 /
 * 7 单独小模块的多行输入框 / 8 radio类型 / 9 浮动类型 /10 财务模块 /11 对于填报页面是表格
 */
let ReportConfig = {
	cwConfigAlterSource:'',
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
        this.tableDataArr = []
        this.tableDataArrEn = []
        this.idArr.forEach((item,index)=>{
        	const $table = $("#table"+item);
        	const $tableEn = $("#table"+item+"En");
        	let contents = this.contentsArr[index]
        	let contentsEn = this.contentsArrEn[index]
        	let titles = this.title
        	let urlTemp = titles[index].get_source;
        	let conf_id = titles[index].id;
        	if(!urlTemp){return}
        	let urlCH = BASE_PATH  + 'credit/front/ReportGetData/'+ urlTemp.split("*")[0] + `&conf_id=${conf_id}`
        	let urlEN = BASE_PATH  + 'credit/front/ReportGetData/'+ urlTemp.split("*")[0] + `&conf_id=${conf_id}`
        	if(urlTemp.split("*")[1]){
        		let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
        		tempParam.forEach((item,index)=>{
        			if(item === 'company_id') {
        				let val = this.rows["company_id_en"]
        				urlEN += `&${item}=${val}`
        			}else {
        				urlEN += `&${item}=${this.rows[item]}`
        			}
        			urlCH += `&${item}=${this.rows[item]}`
        		})
        	}
        	let selectInfo = []
        	selectInfo.push(_this.selectInfoObj)
        	
        	let tempRows = []
        	$table.bootstrapTable({
        		columns: _this.tableColumns(contents,'ch'),
    			url:urlCH, // 请求后台的URL（*）
			    method : 'post', // 请求方式（*）post/get
			    ajaxOptions:{
			    	async:false
			    },
			    queryParams:function(param){
			    	param.selectInfo = JSON.stringify(selectInfo)
			    	return param
			    },
			    sidePagination: 'server',
			    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    			pagination: false, //分页
    			smartDisplay:true,
    			locales:'zh-CN',
    			onLoadSuccess:(data)=>{
    				_this.tableDataArr.push(data)
    			}
        	});
        	$tableEn.bootstrapTable({
        		columns: _this.tableColumns(contentsEn,'en',index,_this.idArrEn[index]),
        		url:urlEN, // 请求后台的URL（*）
        		method : 'post', // 请求方式（*）post/get
        		queryParams:function(param){
        			param.selectInfo = JSON.stringify(selectInfo)
        			return param
        		},
        		 ajaxOptions:{
 			    	async:false
 			    },
        		sidePagination: 'server',
        		contentType:'application/x-www-form-urlencoded;charset=UTF-8',
        		pagination: false, //分页
        		smartDisplay:true,
        		locales:'zh-CN',
        		onLoadSuccess:(data)=>{
//        			console.log(data)
        			_this.tableDataArrEn.push(data)
        		}
        	});
        	
        	
        })
      
    },
    tableColumns(a,lang,tempI,tempId){
    	let _this = this
    	let arr = []
		a.forEach((ele,index)=>{
			if(ele.temp_name !== '操作' && ele.temp_name !== 'Operation'){
				arr.push({
					title:ele.temp_name,
					field: ele.column_name,
					width:(1/a.length)*100+'%'
				})
				
			}
			if(lang === 'en' && (ele.temp_name === '操作' || ele.temp_name === 'Operation')){
				arr.push({
					title:'Operation',
					field: 'operate',
					width: 1/a.length,
					events: {
    					"click .edit":(e,value,row,index)=>{
    						_this.isAdd = false
    						_this.rowId = row.id
    						//回显
    						let formArr = Array.from($("#modalEn"+tempId).find(".form-inline"))
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
    					}
    				},
    				formatter: function(){return _this.formatBtnArr[tempI]}
				})
			}
		})
		
		return arr
    },
    initmodal(){
    	/**
    	 * 初始化模态窗
    	 */
    	let _this = this
    	let modalHtml = ''
    	let ids = this.idArrEn
    	let contents = this.contentsArrEn;
    	let titles = this.titleEn
    	//格式化按钮数组
    	this.formatBtnArr = []
    	ids.forEach((item,index)=>{
    		let modalBody = ''
			let myIndex = index;
    		contents[index].forEach((ele,index)=>{
    			if(ele.temp_name === '操作' || ele.temp_name === 'Operation') {
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
    		this.formatBtnArr.push(`<div class="operate"><a href="javascript:;" class="edit" data-toggle="modal" data-target="#modalEn${item}">编辑</a></div>`)
    		modalHtml += `<div class="modal fade" id="modalEn${item}" tabindex="-1" role="dialog" aria-labelledby="examplemodalCenterTitle" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered" role="document">
					        <div class="modal-content">
					            <div class="modal-header">
					              	${titles[index].temp_name}
					            </div>
					            <div class="modal-body">
					                ${modalBody}
					            </div>
					            <div class="modal-footer">
					                <button type="button" class="btn btn-primary" id="modalEn_save${item}" data-dismiss="modal">保存</button>
					                <button type="button" class="btn btn-default" id="modalEn_cancel${item}" data-dismiss="modal">取消</button>
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
    	this.formDataArr = []
    	let titles = this.formTitle;
    	let formIndex = this.formIndex;
    	let _this = this
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
	    				_this.formDataArr.push(data.rows[0])
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
    bindFormDataEn(tempData,i){
    	//英文绑定表单数据
    	let titlesEn = this.formTitleEn;
    	let formIndexEn = this.formIndexEn;
    	let _this = this
    	if(tempData){
    		let arr = Array.from($("#titleEn"+formIndexEn[i]))
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
    			//实体id
    			let obid = tempData.id;
    			formArr.forEach((item,index)=>{
    				let obj = tempData;
    				let id = $(item).attr("id");
    				let anotherIdArr = id.split("_")
    				anotherIdArr.pop();
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
    		return;
    	}
    	
    	
    	
    	formIndexEn.forEach((item,index)=>{
    		let conf_id = titlesEn[index].id;
    		let getFormUrl = titlesEn[index].get_source;
    		if(getFormUrl === null || getFormUrl === ''){return}
    		let url = BASE_PATH  + 'credit/front/ReportGetData/' + getFormUrl.split("*")[0] 
    		let paramObj = {}
    		if(getFormUrl.split("*")[1]){
    			let tempParam = getFormUrl.split("*")[1].split("$");//必要参数数组
    			tempParam.forEach((item,index)=>{
    				if(item === 'company_id') {
    					paramObj[item] = this.rows[item+'_en']
    				}else {
    					paramObj[item] = this.rows[item]
    				}
    			})
    		}
    		if(!paramObj["company_id"] ){return}
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
    		let arr = Array.from($("#titleEn"+item))
    		if(temp.rows === null || temp.rows.length === 0){return}
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
    	if(floatIndex.length === 0){return}
    	let cw_title = []
    	let cw_contents = []
    	let cw_dom;
    	_this.tableTitle = []
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
//    					console.log(this.floatTitle[index])
    					cw_title.push(this.floatTitle[index])
    					cw_contents.push(this.floatContents[index])
    					cw_dom = $("#titleCw"+i)
    				}
    				
    			}
    		})
    	})
//    	console.log(cw_title,cw_contents)
    	this.cwConfigAlterSource = cw_title[0]['alter_source'];
    	this.cwConfigGetSource = cw_title[0]['get_source'];
    	let cw_top_html = ''
    	let cw_table_html = ''
    	let cw_bottom_html = ''
    	let tableCwId = []
    	cw_title.forEach((item,index)=>{
    		//初始化财务模块
    		let this_content = cw_contents[index];
    		let moneySource = cw_contents[0][4].get_source;
    		let moneyStr = ''
			let unitSource = cw_contents[0][5].get_source;
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
    		if(item.sort === 1) {
    			//财务模块杂七腊八的配置
    			let radioArr = this_content[1]["get_source"].split("&");
    			cw_top_html += `<div class="top-html mx-4">
    								<div class="cw-box d-flex justify-content-between align-items-center mt-4">
    									<div class="firm-name form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[0].temp_name}</label>
    										<input type="text" style="width:14rem" class="form-control" id="${this_content[0].column_name}cw" placeholder=${this_content[0].place_hold} name=${this_content[0].column_name}>
    									</div>
    									<div class="is-merge form-inline">
    										<label class="mr-3" style="font-weight:600">${this_content[1].temp_name}</label>
    										<div class="form-check form-check-inline">
	    										<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[0].split("-")[0]}>
				                                <label class="form-check-label mx-0" for="">${radioArr[0].split("-")[1]}</label>
			                                </div>
						    				<div class="form-check form-check-inline">
							    				<input class="form-check-input" type="radio" name=${this_content[1].column_name} id="${this_content[1].column_name}cw" value=${radioArr[1].split("-")[0]}>
							    				<label class="form-check-label mx-0" for="">${radioArr[1].split("-")[1]}</label>
						    				</div>
    									</div>
    									<!-- 上传下载按钮 -->
    									<div class="btn-group">
    										<button class="btn btn-default mr-3 cwDown" >${this_content[2].temp_name}</button>
						    				<button class="aa-btn btn btn-primary cwUp" style="position:relative">
						    					<form class="uploadForm" enctype="multipart/form-data" action="" method="POST" >
						    						<input style="opacity:0;cursor:pointer;width:100%;height:100%;position:absolute;left:0;top:0" type="file" name="file" class="fileInp">
						    						<input name="report_type" type="hidden" class="report_type"/>
								    				<input name="ficConf_id" type="hidden" class="ficConf_id"/>
						    					</form>
						    					${this_content[3].temp_name}
						    				</button>
    									</div>
    								</div>
    									<!-- 单位 -->
    								<div class="cw-unit">
    									<div class="form-inline my-3">
    										<label style="font-weight:600;margin-left:60%" class="mr-3">${this_content[4].temp_name}</label>
    										<select class="form-control mr-3 moneySel" id="${this_content[4].column_name}cw" style="width:10rem" name=${this_content[4].column_name}>${moneyStr}</select>
    										<select class="form-control mr-3 unitSel" id="${this_content[5].column_name}cw" style="width:10rem" name=${this_content[5].column_name}>${unitStr}</select>
    									</div>
    								</div>
    								<!-- 日期 -->
    								<div class="cw-date form-inline">
    									<input class="form-control my-3 dateInp1" id="${this_content[6].column_name}cw" style="margin-left:32%;margin-right:11%" type="text" name=${this_content[6].column_name}  placeholder=${this_content[6].place_hold} />
    									<input class="form-control dateInp2"  id="${this_content[7].column_name}cw" type="text" name=${this_content[7].column_name}  placeholder=${this_content[7].place_hold} />
    								</div>
    							</div>`
    			let tempUrl = BASE_PATH+ 'credit/front/ReportGetData/' +this_content[10]['get_source'];
    			let options = ''
    			$.ajax({
    				url:tempUrl,
    				async:false,
    				success:(data)=>{
    					options = data.selectStr
    				}
    			})
    			cw_bottom_html +=`<div class="bottom-html"><div class="cw-bottom p-4">
    								<label class="control-label">${this_content[10].temp_name}</label>
    								<select class="form-control my-3 ${this_content[10].column_name}" id="${this_content[10].column_name}cw" name="${this_content[10].column_name}">${options}</select>
    								<textarea class="form-control ${this_content[11].column_name}" id="${this_content[11].column_name}cw" name="${this_content[11].column_name}" placeholder="${this_content[11].place_hold}"></textarea>
    							 </div>
    							 <div class="cw-bottom p-4">
    								<label class="control-label">${this_content[12].temp_name}</label>
    								<input class="form-control my-3 ${this_content[12].column_name}" id="${this_content[12].column_name}cw" name="${this_content[12].column_name}" />
    								<textarea class="form-control ${this_content[13].column_name}" id="${this_content[13].column_name}cw" name="${this_content[13].column_name}" placeholder="${this_content[13].place_hold}"></textarea>
    							 </div>
    							  <div class="cw-bottom p-4">
    								<label class="control-label">${this_content[14].temp_name}</label>
    								<input class="form-control my-3 ${this_content[14].column_name}" id="${this_content[14].column_name}cw" name="${this_content[14].column_name}" />
    								<textarea class="form-control ${this_content[15].column_name}" id="${this_content[15].column_name}cw" name="${this_content[15].column_name}" placeholder="${this_content[15].place_hold}"></textarea>
    							 </div>
    							 <div class="cw-bottom p-4">
    								<label class="control-label">${this_content[16].temp_name}</label>
    								<input class="form-control my-3 ${this_content[16].column_name}" id="${this_content[16].column_name}cw" name="${this_content[16].column_name}" />
    								<textarea class="form-control ${this_content[17].column_name}" id="${this_content[17].column_name}cw" name="${this_content[17].column_name}" placeholder="${this_content[17].place_hold}"></textarea>
    							 </div></div>`
    		}else {
    			let addtext = cw_title[1].place_hold
    			let conf_id = cw_title[0].id
//    			console.log(item,this_content,conf_id)
    			if(item.temp_name !== null && item.temp_name !== '') {
    				_this.tableTitle.push(item.temp_name.split("||"));
    				cw_table_html += `<div class="table-title">${item.temp_name.split("||")}</div>`
    				if(index !== 1){
    					//如果不是合计表，
    					cw_table_html += `<div class="cw-unit">
	    						<div class="form-inline my-3">
	    						<label style="font-weight:600;margin-left:60%" class="mr-3">${cw_contents[0][4].temp_name}</label>
	    						<select disabled="disabled" class="form-control mr-3 moneySel" style="width:10rem" name=${cw_contents[0][4].column_name}>${moneyStr}</select>
	    						<select disabled="disabled" class="form-control mr-3 unitSel" style="width:10rem" name=${cw_contents[0][5].column_name}>${unitStr}</select>
	    						</div>
    						</div>`
    					if(index === 3) {
    						//利润表
    						cw_table_html += `<div class="cw-date form-inline cw-range">
    							<input class="form-control my-3" id="${cw_contents[0][8].column_name}cw" style="margin-left:32%;margin-right:11%" type="text" name=${cw_contents[0][8].column_name}  placeholder=${cw_contents[0][6].place_hold} />
    							<input class="form-control" id="${cw_contents[0][9].column_name}cw" type="text" name=${cw_contents[0][9].column_name} placeholder=${cw_contents[0][7].place_hold} />
    							</div>`
    					}else {
    						cw_table_html += `<div class="cw-date form-inline">
    							<input class="form-control my-3 dateInp1" disabled="disabled" style="margin-left:32%;margin-right:11%" type="text" name=${cw_contents[0][6].column_name}  placeholder=${cw_contents[0][6].place_hold} />
    							<input class="form-control dateInp2" disabled="disabled" type="text" name=${cw_contents[0][7].column_name} placeholder=${cw_contents[0][7].place_hold} />
    							</div>`
    					}
    				}
    			}
    			
    			switch(item.sort) {
    				case 2:
    					//合计表
    					cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
					            				<table id="tableCwHj"
					            				data-toggle="table"
					            				style="position: relative"
					            				>
					            				</table>
											</div>`
    						tableCwId.push('tableCwHj')
    					
    				break;
    				case 3:
    					//资产负债表
    					for(let i=0;i<5;i++){
    						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
    							<table id="tableCwFz${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4 addBtn" type="button" >+ ${addtext}</button>
    							</div>`
    						tableCwId.push(`tableCwFz${i}`)
    					}
    				break;	
    				case 4:
    					//利润表
    					for(let i=0;i<4;i++){
    						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
    							<table id="tableCwLr${i}"
    							data-toggle="table"
    							style="position: relative"
    							>
    							</table>
    							<button class="btn btn-lg btn-block mb-3 mt-4 addBtn" type="button"  >+ ${addtext}</button>
    							</div>`
    							tableCwId.push(`tableCwLr${i}`)
    					}
    					break;	
    				case 5:
    					//比率表
						cw_table_html += `<div class="table-content1 cw-table" style="background:#fff">
							<table id="tableCwBl"
							data-toggle="table"
							style="position: relative"
							>
							</table>
							</div>`
							tableCwId.push(`tableCwBl`)
    					break;	
    				default:
    				break;
    			}
     		}
    	})
    	$(cw_dom).after(cw_bottom_html)
    	$(cw_dom).after(cw_table_html)
    	$(cw_dom).after(cw_top_html)
    	setTimeout(()=>{
    		InitObjTrans.bindCwConfig(_this.cwConfigGetSource,cw_contents[0][1].column_name,_this.rows,_this.tableTitle)
    		InitObjTrans.initCwTable(tableCwId,cw_contents[1],_this.cwGetSource,_this.cwAlterSource,_this.cwDeleteSource)
    		InitObjTrans.cwModalCompute(_this.cwAlterSource)
    		InitObjTrans.downLoadCw(cw_contents[0][2].alter_source,_this.rows);
			InitObjTrans.upLoadCw(cw_contents[0][3].alter_source,_this.rows,_this.cwGetSource,_this.cwAlterSource,tableCwId);
			InitObjTrans.addNewCwModal(_this.cwConfigAlterSource,_this.rows);
    	},0)
    },
    initContent(){
        /**初始化内容 */
    	this.entityTitle = [] //存放小模块的实体title
    	this.idArr = []    //存放table类型模块对应的index
    	this.idArrEn = []    //存放table类型模块对应的index
    	this.contentsArr = [] //存放table类型模块的contents
    	this.title = [] //存放table类型模块的title
    	this.contentsArrEn = [] //存放table类型模块的contents
    	this.titleEn = [] //存放table类型模块的title
    	this.formIndex = [] //存放form类型模块对应的index
    	this.formTitle = [] //存放form类型模块的title
    	this.formIndexEn = [] //存放form类型模块对应的index
    	this.formTitleEn = [] //存放form类型模块的title
    	this.floatIndex = [] //存放float类型模块对应的index
    	this.floatTitle = [] //存放float类型模块的title
    	this.floatContents = [] //存放float类型模块的Contents
    	this.selectInfoObj = {} //存放选择框信息传给后台
    	this.notMoneyFloatHtml = {} //存放非财务模块的浮动html
    	this.cwGetSource = '' //存放获取财务url
    	this.cwAlterSource = '' //存放修改财务url
    	this.cwDeleteSource = '' //删除财务url
    	this.saveStatusUrl = ''
		this.submitStatusUrl = ''
    	let row = localStorage.getItem("row");
    	let _this = this
    	let id = JSON.parse(row).id;
    	let reportType = JSON.parse(row).report_type
    	let istranslate = true
        $.ajax({
        	type:"get",
        	url:BASE_PATH + "credit/front/getmodule/list",
        	data:{id,reportType,istranslate},
        	success:(data)=>{
                setTimeout(()=>{
                	_this.initmodal();
                	InitObjTrans.addressInit();
                	_this.initTable();
                	_this.initFloat();
                	InitObjTrans.dateInit();
                	_this.bindFormData();
                	_this.bindFormDataEn();
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
                let modulesToEn = data.modulesToEn;    
                let contentHtml = '' 
                let bottomBtn = ''
                modules.forEach((item,index)=>{
                	/**
                	 * 循环模块
                	 */
                	_this.entityTitle.push(item.title)
                	let smallModileType = item.smallModileType
                	if(item.title.temp_name === null || item.title.temp_name === "" || item.title.float_parent) {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3" style="display:none"><a class="l-title" name="anchor${item.title.id}" id="title${index}">${item.title.temp_name}</a>`
                	}else if(smallModileType === '10'){
                		//财务模块
                		_this.cwGetSource = item.title.get_source;
                		_this.cwAlterSource = item.title.alter_source;
                		_this.cwDeleteSource = item.title.remove_source;
                		contentHtml +=  `<div class="bg-f pb-4 mb-3 gjcw"><a class="l-title cwmodal" name="anchor${item.title.id}" id="titleCw${index}">${item.title.temp_name}</a>`
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
		        						            		<input type="text" disabled="disabled" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
		        						            		<p class="errorInfo">${item.error_msg}</p>
		        					            		</div>`
		                        		}else {
		                        			
		                        			switch(field_type) {
		                        				case 'text':
		                        					formGroup += `<div class="form-group">
	        						            		<label for="" class="mb-2">${item.temp_name}</label>
	        						            		<input type="text" disabled="disabled" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
	        						            		<p class="errorInfo">${item.error_msg}</p>
	        					            		</div>`
	                        						break;
		                        				case 'number':
			                    						formGroup += `<div class="form-group">
					                        							<label for="" class="mb-2">${item.temp_name}</label>
					                        							<input disabled="disabled" type="number" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name} reg=${item.reg_validation}>
					                        							<p class="errorInfo">${item.error_msg}</p>
				                        							</div>`
		                        					
		                        					break;
		                        				case 'date':
		                        					formGroup += `<div class="form-group date-form">
												            		<label for="" class="mb-2">${item.temp_name}</label>
												            		<input disabled="disabled" type="text" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
												            		<p class="errorInfo">${item.error_msg}</p>
											            		</div>`
		                        					break;
		                        				case 'date_scope':
		                        					formGroup += `<div class="form-group date-scope-form">
									            		<label for="" class="mb-2">${item.temp_name}</label>
									            		<input type="text" disabled="disabled" class="form-control" id="${item.column_name}_${ind}" placeholder="" name=${item.column_name}>
									            		<p class="errorInfo">${item.error_msg}</p>
								            		</div>`
		                        					break;
							            		case 'address':
							            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <input disabled="disabled"  type="text" class="form-control"  style="width: 100%" name=${item.column_name} id="${item.column_name}_${ind}">
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
										            					<select disabled="disabled" name=${item.column_name} id="${item.column_name}_${ind}" class="form-control">
										            						${data.selectStr}
										            					</select>
							            							</div>`
							            				}
							            			})
							            			
							            			break;
							            		case 'textarea':
							            			formGroup += `  <div class="form-group"  style="width: 100%">
									                                    <label  class="mb-2">${item.temp_name}</label>
									                                    <textarea disabled="disabled" class="form-control"  style="width: 100%;height: 6rem" name=${item.column_name} id="${item.column_name}_${ind}"></textarea>
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
                				</div>`
                		
                			break;
                		case '2':
                			//附件类型
                			contentHtml += Public.fileConfig(item,_this.rows)
                			break;
                		case '5':
                			//固定底部的按钮组
                		/*	item.title.column_name === 'save'?_this.saveStatusUrl = item.title.alter_source:_this.submitStatusUrl = item.title.alter_source
                			let className = item.title.column_name === 'save'?'btn btn-default ml-4':'btn btn-primary ml-4'
                			bottomBtn += `<button id=${item.title.column_name} class="${className}">${item.title.temp_name}</button>`*/
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
                	
                	if(smallModileType === '10') {
                		//财务
                		contentHtml += `</div><button class="btn mb-3 btn-lg btn-block" id="addCwMdal" style="display:none">增加一个财务模版</button>`
                	}else {
                		contentHtml += `</div>`
                	}
                	
                	
                	let item_en = modulesToEn[index]
                	if(!item_en){return}
                	let smallModileTypeEn = item_en.smallModileType
                	if(item_en.title.temp_name === null || item_en.title.temp_name === "" || item_en.title.float_parent) {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3" style="display:none"><a class="l-title" name="anchor${item_en.title.id}" id="titleEn${index}">${item_en.title.temp_name}</a>`
                	}else if(smallModileTypeEn === '10'){
                		//财务模块
//                		_this.cwGetSource = item.title.get_source;
//                		_this.cwAlterSource = item.title.alter_source;
//                		_this.cwDeleteSource = item.title.remove_source;
                		contentHtml +=  `<div class="bg-f pb-4 mb-3 gjcw"><a class="l-title cwmodal" name="anchor${item.title.id}" id="titleCwEn${index}">${item_en.title.temp_name}</a>`
                	}else if(smallModileTypeEn !== '-2' && smallModileTypeEn !== '5'  && smallModileTypeEn !== '2') {
                		contentHtml +=  `<div class="bg-f pb-4 mb-3"><a class="l-title" name="anchor${item.title.id}" id="titleEn${index}">${item_en.title.temp_name}</a>`
                	}
                	let btnTextEn = item_en.title.place_hold;
                	let formArrEn = item_en.contents; 
                	
                	//英文循环
                	switch(smallModileTypeEn) {
            		case '0':
            			//表单类型
            			_this.formTitleEn.push(item_en.title)
            			_this.formIndexEn.push(index)
            			let ind = index
            			let rowNum = 0;//代表独占一行的input数量
            			formArrEn.forEach((item_en,index)=>{
            				let formGroup = ''
                    		//判断input的类型
                    		let field_type = item_en.field_type
                    		if(!field_type) {
                    			formGroup += `<div class="form-group">
    						            		<label for="" class="mb-2">${item_en.temp_name}</label>
    						            		<input type="text"  class="form-control" id="${item_en.column_name}_${ind}_En" placeholder="" name=${item_en.column_name} reg=${item.reg_validation}>
    					            		</div>`
                    		}else {
                    			
                    			switch(field_type) {
                    				case 'text':
                    					formGroup += `<div class="form-group">
						            		<label for="" class="mb-2">${item_en.temp_name}</label>
						            		<input type="text" class="form-control" id="${item_en.column_name}_${ind}_En" placeholder="" name=${item_en.column_name} reg=${item.reg_validation}>
					            		</div>`
                						break;
                    				case 'number':
                    						formGroup += `<div class="form-group">
		                        							<label for="" class="mb-2">${item_en.temp_name}</label>
		                        							<input type="number" class="form-control" id="${item_en.column_name}_${ind}_En" placeholder="" name=${item_en.column_name} reg=${item.reg_validation}>
	                        							</div>`
                    					
                    					break;
                    				case 'date':
                    					formGroup += `<div class="form-group date-form">
									            		<label for="" class="mb-2">${item_en.temp_name}</label>
									            		<input  type="text" class="form-control" id="${item_en.column_name}_${ind}_En" placeholder="" name=${item_en.column_name}>
								            		</div>`
                    					break;
                    				case 'date_scope':
                    					formGroup += `<div class="form-group date-scope-form">
						            		<label for="" class="mb-2">${item_en.temp_name}</label>
						            		<input type="text"  class="form-control" id="${item_en.column_name}_${ind}_En" placeholder="" name=${item.column_name}>
					            		</div>`
                    					break;
				            		case 'address':
				            			formGroup += ` <div class="form-group address-form"  style="width: 100%">
						                                    <label  class="mb-2">${item_en.temp_name}</label>
						                                    <input  type="text" class="form-control"  style="width: 100%" name=${item_en.column_name} id="${item_en.column_name}_${ind}_En">
						                                </div>`
				            			break;
				            		case 'select':
				            			if(item_en.get_source === null){return}
				            			let url = BASE_PATH + 'credit/front/ReportGetData/' + item_en.get_source
				            			$.ajax({
				            				type:'get',
				            				url,
				            				async:false,
				            				dataType:'json',
				            				success:(data)=>{
				            				formGroup += `<div class="form-group">
							            					<label for="" class="mb-2">${item_en.temp_name}</label>
							            					<select name=${item_en.column_name} id="${item.column_name}_${ind}_En" class="form-control">
							            						${data.selectStr}
							            					</select>
				            							</div>`
				            				}
				            			})
				            			
				            			break;
				            		case 'textarea':
				            			formGroup += `  <div class="form-group"  style="width: 100%">
						                                    <label  class="mb-2">${item_en.temp_name}</label>
						                                    <textarea class="form-control"  style="width: 100%;height: 6rem" name=${item_en.column_name} id="${item_en.column_name}_${ind}_En"></textarea>
						                                </div>`
						                break;
    							    default :
    							    	break;
                    			}
                    		}
            				if(formArr[index-1] && formArr[index-1]['field_type'] === 'address')  {
            					rowNum += 1
            				}
                    		
                    		if((index - rowNum)%3 === 0  || formArrEn[index-1]['field_type'] === 'address' || formArrEn[index]['field_type'] === 'address' || formArrEn[index]['field_type'] === 'textarea'){
                    			contentHtml += `<div class="firm-info mt-4 px-5 d-flex justify-content-between">`;
                    		}
                    		contentHtml += formGroup;
                    		
                    		if(((index+1 - rowNum)%3 === 0 && index !==0) || formArrEn[index]['field_type'] === 'address' || (formArrEn[index+1]&&formArrEn[index+1]['field_type'] === 'textarea') || (formArrEn[index+1]&&formArrEn[index+1]['field_type'] === 'address') ||((formArrEn[index+1]&&formArrEn[index+1]['field_type'] === 'textarea') && formArrEn[index+2]&&formArrEn[index+2]['field_type'] === 'textarea')){
                    			contentHtml += `</div>`    
                    		}
                    		
	                	}) 
	                	contentHtml += `</div>`   
	                	break;
            		case '1':
            			//table类型
            			_this.idArrEn.push(index)
            			_this.contentsArrEn.push(item_en.contents)
            			_this.titleEn.push(item_en.title)
            			contentHtml += `<div class="table-content1" style="background:#fff">
			                				<table id="table${index}En"
			                				data-toggle="table"
			                				style="position: relative"
			                				>
			                				</table>
            				</div>`
            		
            			break;
            		case '11':
            			//table类型
            			_this.idArrEn.push(index)
            			_this.contentsArrEn.push(item_en.contents)
            			_this.titleEn.push(item_en.title)
            			contentHtml += `<div class="table-content1" style="background:#fff">
			                				<table id="table${index}En"
			                				data-toggle="table"
			                				style="position: relative"
			                				>
			                				</table>
            				</div>`
            		
            			break;
            		case '2':
            			//附件类型
//            			contentHtml += Public.fileConfig(item,_this.rows)
            			break;
            		case '5':
            			//固定底部的按钮组
            			item_en.title.column_name === 'save'?_this.saveStatusUrl = item_en.title.alter_source:_this.submitStatusUrl = item_en.title.alter_source
            			let className = item_en.title.column_name === 'save'?'btn btn-default ml-4':'btn btn-primary ml-4'
            			if(item_en.title.column_name === 'save'){
            				bottomBtn += `<button id="translateBtn" class="btn btn-primary ml-4">翻译</button><button id=${item_en.title.column_name} class="${className}">${item_en.title.temp_name}</button>`
            			}else {
            				bottomBtn += `<button id=${item_en.title.column_name} class="${className}">${item_en.title.temp_name}</button>`
            			}
            			break;
            		case '6':
            			//信用等级
            			let inputObj = item.contents[0]
            			_this.formTitleEn.push(inputObj)
            			_this.formIndexEn.push(index)
            			contentHtml += `<div class="form-group form-inline p-4 mx-3" id="xydj">
				                          <label >${inputObj.temp_name}</label>
				                          <input type="text" id=${inputObj.column_name} name=${inputObj.column_name} class="form-control mx-3" placeholder="" aria-describedby="helpId" style="border-color:blue">
				                          <span id="helpId" class="text-muted">${inputObj.suffix}</span>
				                        </div>`
            			
            			let tableContents = []
            			item_en.contents.forEach((item,index)=>{
            				if(index > 0 && index < 5) {
            					tableContents.push(item)
            				}
            			})
            			_this.idArrEn.push(index)
            			_this.contentsArrEn.push(tableContents)
            			_this.titleEn.push(item_en.title)
        				contentHtml += `<div class="table-content1" style="background:#fff">
			                				<table id="table${index}"
			                				style="position: relative"
			                				>
			                				</table>
            							</div>`
        				let explainObj = item.contents[5];
            			let explainUrl = explainObj.get_source;
            			if(explainUrl === null) {return;}
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
            					returnData = data.rows
            				}
            			})
            			returnData.forEach((item,index)=>{
            				contentHtml += `<p class="m-3 ml-4">${item.describe}</p>`
            			})
            			break;
            		case '7':
            			//单独小模块的多行输入框 保存回显同表单模块
            			_this.formTitleEn.push(item.title)
            			_this.formIndexEn.push(index)
            			let ot_item = item_en
            			item_en.contents.forEach((item,index)=>{
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
            			_this.formTitleEn.push(item.title)
            			_this.formIndexEn.push(index)
            			
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
                
                $(".main-content").html(contentHtml)
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
                $("#modalEn_logo_icon span").text(filename)
            }else {
                Public.message("info","上传文件格式错误！")
            }
        })
    	
    	this.idArr.forEach((item,index)=>{
    		
    		//点击模态框保存按钮，新增一条数据
    		$("#modalEn_save"+item).unbind().click(()=>{
    			let dataJson = []
    			let dataJsonObj = {}
    			let formArr = Array.from($("#modalEn"+item).find(".form-inline"))
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
				dataJsonObj["id"] = this.rowId
            	let tempParam = urlTemp.split("*")[1].split("$");//必要参数数组
            	let paramObj = {}
            	tempParam.forEach((item,index)=>{
    				if(item === 'company_id') {
    					dataJsonObj[item] = this.rows[item+'_en']
    				}else {
    					dataJsonObj[item] = this.rows[item]
    				}
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
            				Public.message("success",'修改成功！')
            				//刷新数据
            				this.refreshTable($("#table"+item + 'En'));
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
    	let _this = this
    	let tableTitlesEn = this.titleEn 
    	let tableDataArr = this.tableDataArr
    	let tableDataArrEn = this.tableDataArrEn
    	let idArrEn = this.idArrEn
    	console.log(this.tableDataArrEn)
    	let dataEn  = []
    	tableTitlesEn.forEach((item,index)=>{
    		let alterSource = item["alter_source"];
    		let url = BASE_PATH +'credit/front/ReportGetData/'+ alterSource.split("*")[0] ;
    		let dataJson = []
    		//点击翻译按钮
    		$(".position-fixed").on("click","#translateBtn",(e)=>{
    			 //表格翻译
    			
	   			 let temp = []
    			
	   			 if(!_this.tableDataArr[index]){
	   				 //此表格无数据，返回
	   				 return
	   			 }
	   			 console.log(tableDataArrEn[index]['rows'])
	   			_this.tableDataArr[index]['rows'].forEach((ele,i)=>{
	   				if(!tableDataArrEn[index]){return}
	   				ele["id"] = tableDataArrEn[index]['rows'].length!==0 && tableDataArrEn[index]['rows'][i]?tableDataArrEn[index]['rows'][i]["id"]:null;
	   				$.ajax({
	   					url:BASE_PATH + `credit/ordertranslate/translate`,
	   					type:'post',
	   					async:false,
	   					data:{
	   						dataJson:JSON.stringify(ele)
	   					},
	   					success:(data)=>{
	   						temp.push(data)
	   					}
	   				})
	   			}) 
	   			 $("#table"+idArrEn[index] + 'En').bootstrapTable("removeAll");
	   			 $("#table"+idArrEn[index] + 'En').bootstrapTable("append",temp);
	   			 
    		})
    		
    		 //点击保存按钮
    		
    		$(".position-fixed").on("click","#save",(e)=>{
    			 let data = $("#table"+idArrEn[index] + 'En').bootstrapTable("getData");
    			 console.log(data)
    			 if(data.length === 0 || !Array.isArray(data)){return}
    			 data.forEach((ele,i)=>{
    				 if(alterSource.split("*")[1]) {
		    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
		    			tempParam.forEach((item,index)=>{
		    				if(item === 'company_id') {
		    					console.log(ele)
		    					ele[item] = _this.rows['company_id_en']
		    					console.log(ele)
		    				}else {
		    					ele[item] =_this.rows[item]
		    				}
		    			})
		    		}
					
					let arr = Object.keys(ele) 
					arr.forEach((item,index)=>{
						ele[item] = ele[item]!==null && typeof ele[item] === 'string'?ele[item].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3"):ele[item]
					})
    			 })
    			 let $modals = $("#modalEn"+idArrEn[index])
    			 let $selects = $modals.find(".modal-body").find("select")
    			 $selects.each((index,item)=>{
    				 let name = $(item).attr("name")
    				 let val = $("#"+$(item).attr("id")+' option:selected').val()
    				 data.forEach((ele)=>{ 
    					 if(ele[name]){
    						 ele[name] = val
    					 }
    				 })
    			 })
    			 $.ajax({
    				 url:url,
    				 data:{
    					 dataJson:JSON.stringify(data)
    				 },
    				 type:'post',
    				 success:(data)=>{
    					 console.log(data)
    				 }
    			 })
    		})
    	//点击提交按钮
		
		$(".position-fixed").on("click","#submit",(e)=>{
			 let data = $("#table"+idArrEn[index] + 'En').bootstrapTable("getData");
			 console.log(data)
			 if(data.length === 0){return}
			 data.forEach((ele,i)=>{
				 if(alterSource.split("*")[1]) {
	    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
	    			tempParam.forEach((item,index)=>{
	    				if(item === 'company_id') {
	    					console.log(ele)
	    					ele[item] = _this.rows['company_id_en']
	    					console.log(ele)
	    				}else {
	    					ele[item] =_this.rows[item]
	    				}
	    			})
	    		}
				
				let arr = Object.keys(ele) 
				arr.forEach((item,index)=>{
					ele[item] = ele[item]!==null && typeof ele[item] === 'string'?ele[item].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1').replace(/{/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷2").replace(/]/g, "锟э窖拷锟锟斤拷锟斤拷*锟斤拷3"):ele[item]
				})
			 })
			 let $modals = $("#modalEn"+idArrEn[index])
			 let $selects = $modals.find(".modal-body").find("select")
			 $selects.each((index,item)=>{
				 let name = $(item).attr("name")
				 let val = $("#"+$(item).attr("id")+' option:selected').val()
				 data.forEach((ele)=>{ 
					 if(ele[name]){
						 ele[name] = val
					 }
				 })
			 })
			 $.ajax({
				 url:url,
				 data:{
					 dataJson:JSON.stringify(data)
				 },
				 type:'post',
				 success:(data)=>{
					 console.log(data)
				 }
			 })
		})
	})
			
    	
    	let formTitlesEn = this.formTitleEn;
    	let formIndexEn = this.formIndexEn;
//    	console.log(formTitles,formIndex)
    
    	//_this.formDataArr
    	formIndexEn.forEach((item,index)=>{
    		let alterSource = formTitlesEn[index]["alter_source"];
    		if(alterSource === null || alterSource === ''){return}
    		let url = BASE_PATH +'credit/front/ReportGetData/'+ alterSource.split("*")[0] ;
    		let dataJson = []
    		let dataJsonObj = {} 
    		if(alterSource.split("*")[1]) {
    			
    			let tempParam = alterSource.split("*")[1].split("$");//必要参数数组
    			tempParam.forEach((item,index)=>{
    				if(item === 'company_id') {
    					dataJsonObj[item] = this.rows[item+'_en']
    				}else {
    					dataJsonObj[item] = this.rows[item]
    				}
    			})
    		}
			if(dataJsonObj["company_id"] && !dataJsonObj["company_id"]){return}
    		//点击翻译按钮
    		$(".position-fixed").on("click","#translateBtn",(e)=>{
    			 //表单翻译
    			 $.ajax({
    				 url:BASE_PATH + `credit/ordertranslate/translate`,
    				 type:'post',
    				 data:{
    					 dataJson:JSON.stringify(_this.formDataArr[index])
    				 },
    				 success:(data)=>{
    					 _this.bindFormDataEn(data,index)
    				 }
    			 });
    			 
    		})
			 //点击保存按钮
    		
    		$(".position-fixed").on("click","#save",(e)=>{
    			InitObjTrans.saveCwConfigInfo(_this.cwConfigAlterSource,_this.rows);
    			$("#save").addClass("disabled")
    			 let arr = Array.from($("#titleEn"+item))
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
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					  let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
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
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
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
    					 if(data.statusCode === 1 && !formIndexEn[index+1]) {
    						 let url = BASE_PATH + 'credit/front/orderProcess/' + _this.saveStatusUrl + `&model.id=${_this.rows["id"]}`;
    						 $.ajax({
    							 url,
    							 type:'post',
    							 success:(data)=>{
    								 if(data.statusCode === 1) {
    									 Public.message("success",data.message)
    									 
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
    			InitObjTrans.saveCwConfigInfo(_this.cwConfigAlterSource,_this.rows);
    			$("#commit").addClass("disabled")
    			 let arr = Array.from($("#titleEn"+item))
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
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
    				 }else if($(item).next().hasClass("textarea-module")) {
    					 //无标题多行文本输入框
    					 let name =$(item).next().find("textarea").attr("name")
    					 let val =$(item).next().find("textarea").val()
    					 let id = $(item).next().find("textarea").attr("entityid")
    					  dataJsonObj["id"] = id
    					 dataJsonObj[name] = val.replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
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
    						 anotherIdArr.pop();
    						 let anotherId = anotherIdArr.join('_')
    						 let tempObj = _this.getFormData($('#'+id))
    						 let entryid = $(item).attr("entryid")
    						 dataJsonObj["id"] = entryid
    						 for(let i in tempObj){
    							 if(tempObj.hasOwnProperty(i))
    								 dataJsonObj[i] = tempObj[i].replace(/:/g,'锟斤拷锟斤拷之锟斤拷锟窖э拷锟').replace(/,/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷').replace(/}/g,'锟э窖拷锟锟斤拷锟斤拷*锟斤拷1')
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
    					 if(data.statusCode === 1 && !formIndexEn[index+1]) {
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