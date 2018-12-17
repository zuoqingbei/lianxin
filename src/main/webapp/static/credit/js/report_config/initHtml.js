let InitObj = {
	hjArr:[],
	cwAlterSource:'',
	cwType:'',
	tableColumnNameArr:[],
	saveCwConfigInfo(url,rows){
		/**
		 * 保存财务配置信息
		 * url:保存接口url
		 */
		let cwModals = Array.from($(".gjcw"));
		cwModals.forEach((item,index)=>{
			let dataJsonObj = {}
			let dataJson = []
			let radioName = $(item).find('input[type="radio"]').attr("name")
			let radioVal = $("input[name="+radioName+"]:checked").val()
			dataJsonObj[radioName] = radioVal
			
			let inputs_top = Array.from($(item).find(".top-html").find("input"))
			let selects_top = Array.from($(item).find(".top-html").find("select"))
			let inputs_bottom = Array.from($(item).find(".bottom-html").find("input"))
			let selects_bottom = Array.from($(item).find(".bottom-html").find("select"))
			let textareas_bottom = Array.from($(item).find(".bottom-html").find("textarea"))
			let inputs_all = inputs_top.concat(inputs_bottom)
			let selects_all = selects_top.concat(selects_bottom)
			inputs_all.forEach((item,index)=>{
				let id = $(item).attr("id");
				let tempObj = this.getFormData($('#'+id));
				for(let i in tempObj){
					if(tempObj.hasOwnProperty(i))
					dataJsonObj[i] = tempObj[i]
				}
			})
			selects_all.forEach((item,index)=>{
				let id = $(item).attr("id");
				let tempObj = this.getFormData($('#'+id));
				for(let i in tempObj){
					if(tempObj.hasOwnProperty(i))
						dataJsonObj[i] = tempObj[i]
				}
			})
			textareas_bottom.forEach((item,index)=>{
				let id = $(item).attr("id");
				let tempObj = this.getFormData($('#'+id));
				for(let i in tempObj){
					if(tempObj.hasOwnProperty(i))
						dataJsonObj[i] = tempObj[i]
				}
			})
			let id = $(item).attr("cwConfigid")
			if(id){
				dataJsonObj["id"] = id
			}
			dataJson.push(dataJsonObj)
			$.ajax({
				url:BASE_PATH + 'credit/front/ReportGetData/' + url,
				type:'post',
				data:{dataJson:JSON.stringify(dataJson),"report_type":rows["report_type"]},
				success:(data)=>{
					console.log(data)
				}
			})
		})
	},
	bindCwConfig(cwConfigGetSource,radioName,rows,tableTitles){
		
		/**
		 * 绑定财务模块表格配置信息
		 * tableTitle:表格标题,tableTitle,i
		 * 
		 */
		let paramObj = {}
		let _this = this
		let cwModalId = ''
		paramObj["company_id"] = rows["company_id"]
		paramObj["report_type"] = rows["report_type"]
		if(cwConfigGetSource.split("*")[1]) {
			let tempParam = cwConfigGetSource.split("*")[1].split("$");//必要参数数组
			tempParam.forEach((item,index)=>{
				paramObj[item] = rows[item]
				console.log(paramObj[item],rows[item])
			})
		}
		let radioVal = ''
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + cwConfigGetSource.split("*")[0],
			type:'post',
			async:false,
			data:paramObj,
			success:(data)=>{
				console.log(data)
				data = data.rows
				_this.cwType = data[0]["type"]
				if(data.length === 0){return}
				//获取财务模板id   ////下面 0代表页面自上而下的财务模块
				let cwModals = Array.from($(".gjcw"))
				cwModals.forEach((self,ind)=>{
					$(self).attr("cwConfigid",data[ind]["id"])
					cwModalId = data[ind]["id"]
				})
				let cwModalArr = Array.from($(".cwModal"))
				data.forEach((item,index)=>{
					//每个item代表每个财务模块的配置数据
					let inputs = Array.from($(cwModalArr[index]).siblings(".top-html").find("input[type='text']"))
					let inputss =  Array.from($(".cw-date"));
					inputss.forEach((item,index)=>{
						if(!$(item).hasClass("cw-range")){
							inputs = inputs.concat(Array.from($(item).find("input")))
						}
					})
					let selects = Array.from($(cwModalArr[index]).siblings(".top-html").find("select"))
					selects = selects.concat(Array.from($(cwModalArr[index]).siblings(".cw-unit").find("select")))
					let radios = Array.from($(cwModalArr[index]).siblings(".top-html").find("input[type='radio']"))
					inputs.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						$(cwModals[index]).find(ele).val(item[name])
					})
					selects.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						let val = item[name]
						$(cwModals[index]).find(ele).find("option[value="+val+"]").attr("selected","selected")
					})
					radios.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						let val = item[name]
						$(cwModals[index]).find("input[name="+name+"][value="+val+"]").attr("checked","checked")
						radioVal = val
					})
					let ot_item = item
					let ot_index = index
					let cw_bottoms = Array.from($(cwModals[index]).find(".cw-bottom"))
					cw_bottoms.forEach((item,index)=>{
						let b_inputs = Array.from($(item).find("input"))
						let b_selects = Array.from($(item).find("select"))
						let b_textAreas = Array.from($(item).find("textarea"))
						b_inputs.forEach((ele,i)=>{
							let name = $(ele).attr("name")
							$(cwModals[ot_index]).find(ele).val(ot_item[name])
						})
						b_selects.forEach((ele,i)=>{
							let name = $(ele).attr("name")
							let val = ot_item[name]
							$(cwModals[ot_index]).find(ele).find("option[value="+val+"]").attr("selected","selected")
						})
						b_textAreas.forEach((ele,i)=>{
							let name = $(ele).attr("name")
							$(cwModals[ot_index]).find(ele).val(ot_item[name])
						})
					})
				})
			}
		})
		console.log(tableTitles)
		let tables = Array.from($(".table-title"))
		tables.forEach((item,i)=>{
			$(tables[i]).html(radioVal==="1"?tableTitles[i][1]:tableTitles[i][0])
			$(document).on('change','input[type=radio][name='+radioName+']',()=>{
				let val = $('input[type=radio][name='+radioName+']:checked').val()
				$(tables[i]).html(val==="1"?tableTitles[i][1]:tableTitles[i][0])
			})
		})
		$(document).on('change','.moneySel',(e)=>{
			let val = $('.moneySel option:selected').val()
			let selects = Array.from($(".moneySel"))
			selects.forEach((item,index)=>{
				$(item).val(val);
			})
		})
		$(document).on('change','.unitSel',(e)=>{
			let val = $('.unitSel option:selected').val()
			let selects = Array.from($(".unitSel"))
			selects.forEach((item,index)=>{
				$(item).val(val);
			})
		})
		
		return cwModalId
	},
	cwModalCompute(alterSource){
		/**
		 * 财务计算函数
		 */
		let cwModals = Array.from($(".gjcw"));
		//财务计算
		let _this = this
		cwModals.forEach((seft,i)=>{
			$(seft).find("table").on("blur","input",(e)=>{
				let className = $(e.target).attr("class")
				let entityid = $(e.target).attr("entityid")
				let val = $(e.target).val()
				//配数据的时候加减法的calssname一定要配置在最后面
				className = className.split(" ")[className.split(" ").length-1]
				//调用修改接口
				let $oT_td = $(e.target).parent("td")
				let $oT_tr = $(e.target).parents("tr")
				let $trs = $oT_tr.children("td")
				let td_index = $.inArray($oT_td[0],Array.from($trs))
				let str = _this.tableColumnNameArr[td_index]
				let url = BASE_PATH + 'credit/front/ReportGetData/' + alterSource;
				let dataJson = []
				let dataObj = {}
				dataObj[str] = val;
				dataObj["id"] = entityid;
				dataJson.push(dataObj)
				$.ajax({
					url,
					data:{dataJson:JSON.stringify(dataJson)},
					type:'post',
					success:(data)=>{
						if(data.statusCode === 1) {
							//保存合计项
							_this.saveCwSummation(alterSource)
						}
					}
				})
				
				if(className.split("-")[1] || className === 'amount1'|| className === 'amount2'){
					//减法
					this.hjArr.forEach((item,index)=>{
						if(item !== null && item.split("-")[item.split("-").length-1] === 'sub'){
							//减法的合计
							let total1 = $(seft).find(".amount1").val()
							let total2 = $(seft).find(".amount2").val()
							let arr = item.split("-");
							if(arr.length === 2) {
								//毛利润
								let temp1 = 0
								let doms1 = Array.from($(seft).find("."+arr[0]+"-son"));
								doms1.forEach((item,index)=>{
									temp1 += +$(item).val()
								})
								total1 -= temp1;
								let temp2 = 0
								let doms2 = Array.from($(seft).find("."+arr[0].replace("1","2")+"-son"));
								doms2.forEach((item,index)=>{
									temp2 += +$(item).val()
								})
								total2 -= temp2;
							}else if(arr.length === 3) {
								//非毛利润表
								let sub1 = $(seft).find("."+arr[0]+"-sub").length===0?$(seft).find("."+this.hjArr[index-1].split("-")[0]+'-'+arr[0]+"-sub").val():$(seft).find("."+arr[0]+"-sub").val();
								let sub2 = $(seft).find("."+arr[0].replace("1","2")+"-sub").length===0?$(seft).find("."+this.hjArr[index-1].split("-")[0].replace("1","2")+'-'+arr[0].replace("1","2")+"-sub").val():$(seft).find("."+arr[0].replace("1","2")+"-sub").val();
								let temp1 = 0;
								let temp2 = 0;
								let doms1 = Array.from($(seft).find(`.${arr[1]}-son`));
								doms1.forEach((item,index)=>{
									temp1 += +$(item).val()
								})
								total1 -= (total1-sub1);
								total1 -= temp1;
								let doms2 = Array.from($(seft).find(`.${arr[1].replace("1","2")}-son`));
								doms2.forEach((item,index)=>{
									temp2 += +$(item).val()
								})
								total2 -= (total2-sub2);
								total2 -= temp2;
							}
							
							$(seft).find("."+item).val(total1)
							$(seft).find("."+item.replace(/1/g,'2')).val(total2)
						}
					})
				}else {
					//加法
					this.hjArr.forEach((item,index)=>{
						if(item !== null && item.split("-")[item.split("-").length-1] !== 'sub'){
							//加法的合计
							let num1 = 0
							let num2 = 0
							let arr = item.split("-");
							arr.forEach((item,index)=>{
								let str = arr[arr.length-1];
								if(index<arr.length-1){
									let doms1 = Array.from($(seft).find("."+item))
									doms1.forEach((item,index)=>{
										num1 += +$(item).val()
									})
									let doms2 = Array.from($(seft).find("."+item.replace("1","2")))
									doms2.forEach((item,index)=>{
										num2 += +$(item).val()
									})
								}
							})
							$(seft).find("."+item).val(num1)
							$(seft).find("."+item.replace(/1/g,'2')).val(num2)
						}
					})
				}
				//固定资产
				$(seft).find(".gdzcje1-copy").val($(seft).find(".gdzcje1").val())
				$(seft).find(".gdzcje2-copy").val($(seft).find(".gdzcje2").val())
				
				//重要比率表
				let ldzc1 =$(seft).find(`.ldzc1-add`).val()//流动资产 
				let ldzc2 = $(seft).find(`.ldzc2-add`).val()// 
				let ldfz1 = $(seft).find(`.ldfz1-add`).val()//流动负债
				let ldfz2 = $(seft).find(`.ldfz2-add`).val()//
				let ch1 = $(seft).find(`.ch1`).val()//存货
				let ch2 = $(seft).find(`.ch2`).val()//
				let fzze1 = $(seft).find(`.ldfz1-qtfz1-total`).val()//负债总额
				let fzze2 = $(seft).find(`.ldfz2-qtfz2-total`).val()//
				let zcze1 = $(seft).find(`.ldzc1-qtzc1-total`).val()//资产总额
				let zcze2 = $(seft).find(`.ldzc2-qtzc2-total`).val()//
				let yysr1 = $(seft).find(`.amount1`).val()//营业收入
				let yysr2 = $(seft).find(`.amount2`).val()//
				let jlr1 = $(seft).find(`.sq1-jlr1-sub`).val()//净利润
				let jlr2 = $(seft).find(`.sq2-jlr2-sub`).val()//
				let yycb1 = $(seft).find(`.yycb1`).val()//营业成本
				let yycb2 = $(seft).find(`.yycb2`).val()//
				let yszk1 = $(seft).find(`.yszk1`).val()//应收账款
				let yszk2 = $(seft).find(`.yszk2`).val()//
				let yfzk1 = $(seft).find(`.yfzk1`).val()//应付账款
				let yfzk2 = $(seft).find(`.yfzk2`).val()//
				
				//流动比率
				$(seft).find(`.ldbl1`).val(+ldfz1 === 0?0:ldzc1/ldfz1)
				$(seft).find(`.ldbl2`).val(+ldfz2 === 0?0:ldzc2/ldfz2)
				//速动比率
				$(seft).find(`.sdbl1`).val(+ldfz1 === 0?0:ldzc1-ch1/ldfz1)
				$(seft).find(`.sdbl2`).val(+ldfz2 === 0?0:ldzc2-ch2/ldfz2)
				//资产负债率
				$(seft).find(`.zcfzl1`).val(+zcze1 === 0?0:fzze1/zcze1)
				$(seft).find(`.zcfzl2`).val(+zcze2 === 0?0:fzze2/zcze2)
				//净利润率
				$(seft).find(`.jlrl1`).val(+yysr1 === 0?0:jlr1/yysr1)
				$(seft).find(`.jlrl2`).val(+yysr2 === 0?0:jlr2/yysr2)
				//资产回报率
				$(seft).find(`.zchb1`).val(+zcze1 === 0?0:jlr1/zcze1)
				$(seft).find(`.zchb2`).val(+zcze2 === 0?0:jlr2/zcze2)
				//营业总额/资产总额
				$(seft).find(`.yy-zc1`).val(+zcze1 === 0?0:yysr1/zcze1)
				$(seft).find(`.yy-zc2`).val(+zcze2 === 0?0:yysr2/zcze2)
				//营业成本/营业总额
				$(seft).find(`.yy-yy1`).val(+yysr1 === 0?0:yycb1/yysr1)
				$(seft).find(`.yy-yy2`).val(+yysr2 === 0?0:yycb2/yysr2)
				
				let cw_range1 = $($(seft).find(`.cw-range`).find("input")[0]).val()
				let cw_range2 = $($(seft).find(`.cw-range`).find("input")[1]).val()
				if(cw_range1.split("-")[1] && cw_range1.split("-")[0].trim() === cw_range1.split("-")[1].trim()) {
					//存货周转天数
					$(seft).find(`.chzzts1`).val(+yysr1 === 0?0:ch1/yysr1*365)
					//应收账款周转天数
					$(seft).find(`.yszkzzts1`).val(+yysr1 === 0?0:yszk1/yysr1*365)
					//应付账款周转天数
					$(seft).find(`.yfzkzzts1`).val(+yycb1 === 0?0:yfzk1/yycb1*365)
				}
				if(cw_range2.split("-")[1] && cw_range2.split("-")[0].trim() === cw_range2.split("-")[1].trim()) {
					//存货周转天数
					$(seft).find(`.chzzts2`).val(+yysr2 === 0?0:ch2/yysr2*365)
					//应收账款周转天数
					$(seft).find(`.yszkzzts2`).val(+yysr2 === 0?0:yszk2/yysr2*365)
					//应付账款周转天数
					$(seft).find(`.yfzkzzts2`).val(+yycb2 === 0?0:yfzk2/yycb2*365)
				}
			})
		})
	},
	
	initCwTable(tableCwIds,contents,getSource,alterSource,deleteSource,rows){
		//财务模块表格初始化  掉了4次
		/**
		 * tableCwIds:表格id数组 
		 * contents :表格的表头信息
		 * getSource:财务getsource
		 * alterSource:财务alterSource
		 * deleteSource: 财务 deleteSource
		 * id:财务模块id
		 */
		let id = $(".gjcw").attr("cwconfigid")
		let returnData;
		let _this = this
		this.cwAlterSource = alterSource
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + getSource + '?ficConf_id='+id+'&report_type='+rows["report_type"],
			type:'post',
			async:false,
			success:(data)=>{
				returnData = data
			}
		})
		let tempRows =  []
		let tempArr = [];
		returnData['rows'].sort((a,b)=>{
			return a["son_sector"]-b["son_sector"]
		});
		returnData['rows'].forEach((item,index)=>{
			if(item.is_sum_option) {
				//合计项
				this.hjArr.push(item.class_name1)
				if(index>8){
					//非合计表合计项 给个class背景变色
					item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["begin_date_value"]} class="form-control bg-gray ${item.class_name1}" style="width:13.5rem"/>`
					item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
				}else {
					item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["begin_date_value"]} class="form-control ${item.class_name1}" style="width:13.5rem"/>`
					item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
				}
			}else {
				if(!item.is_default){
					item["item_name"] = `<input type="text" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value="${item['item_name'] === null?'':item['item_name']}" class="form-control" style="width:13.5rem"/>`
				}
				item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value=${item["begin_date_value"]} class="form-control ${item.class_name1}" style="width:13.5rem"/>`
				item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
			}
			if(!returnData['rows'][index-1] || item.son_sector !== returnData['rows'][index-1]["son_sector"] || (index+1) === returnData['rows'].length) {
				if(tempRows.length !== 0){
					tempArr.push(tempRows)
					tempRows = []
				}
			}
			tempRows.push(item)
		})
		//合计项放在最后
		tempArr.forEach((item,index)=>{
			item.forEach((ele,index)=> {
				if(ele.is_sum_option === 1) {
					item.splice($.inArray(ele,item),1)
					item.push(ele)
				}
			})
		})
		let tempObj ={}
		tableCwIds.forEach((item,index)=>{
			const $table = $('#'+item);
			$table.bootstrapTable({
        		columns: columns(),
        		data:tempArr[index],
        		showHeader:false,
    			pagination: false, //分页
    			smartDisplay:true,
    			locales:'zh-CN',
        	});
			
			function columns(){
        		let arr = []
        		contents.forEach((ele,index)=>{
        			_this.tableColumnNameArr.push(ele.column_name);
        			if(ele.column_name !== null){
        				index === 0?tempObj[ele.column_name] = '':tempObj[ele.column_name] = 0
        			}
	    			if(ele.temp_name !== '删除'){
	    				arr.push({
	    					title:ele.temp_name,
	    					field: ele.column_name,
	    					width:(1/contents.length)*100+'%',
	    					class:'table-margin',
	    					align:'center',
	    				})
	    				
	    			}else {
	    				arr.push({
	    					title:'',
	    					field: 'operate',
	    					width:1/contents.length,
	    					align:'center',
	    					events: {
	        					"click .delete":(e,value,row,index)=>{
//	        						console.log(value,row)
	        						let entityId = row.id
	        						$("#popEnter").unbind().on('click', function(){
            							//确定删除
            							let url = BASE_PATH + 'credit/front/ReportGetData/' + deleteSource;
            							$.ajax({
            								url,
            								type:'post',
            								data:{
            									id:entityId
            								},
            								success:(data)=>{
            									
            									if(data.statusCode === 1) {
            										Public.message('success',data.message)
            										//刷新数据
            										_this.refreshCwModal(tableCwIds,getSource,id,rows)
            									}else {
            										Public.message('error',data.message)
            									}
            								}
            							})
            						})
	        					}
	        				},
            				formatter: function(){return `<a href="javascript:;" class="delete" data-toggle="modal" data-target="#modal_delete">${ele.temp_name}</a>`}
	    				})
	    			}
        		})
        		
        		return arr
        	}
		})
		setTimeout(()=>{
			//初始化完表格之后自动计算
			//自动计算合计
			const $table_a = $('#'+tableCwIds[1]);
//			console.log($table_a.find("input")[0])
			$($table_a.find("input")[0]).trigger("blur")
			$(".bg-gray").parent("td").parent("tr").css("background","#fafafa")
			
			//点击新增一行按钮
			$(".addBtn").unbind().click((e)=>{
				let $input = $($(e.target).siblings(".bootstrap-table").find("input")[0])
				let $input2 = $($(e.target).siblings(".bootstrap-table").find("input")[2])
				let class_name1 = $input.attr("class").split(" ")[$input.attr("class").split(" ").length-1];
				let class_name2 = $input.attr("class").split(" ")[$input.attr("class").split(" ").length-1].replace("1","2");
				if(class_name1 === '.amount1'){
					class_name1 = $input2.attr("class").split(" ")[$input2.attr("class").split(" ").length-1];
					class_name2 = $input2.attr("class").split(" ")[$input2.attr("class").split(" ").length-1].replace("1","2");
				}
				let son_sector = $input.attr("sonsector")
				let parent_sector = $input.attr("parentsector")
				let dataJson = []
				
				tempObj["son_sector"] = son_sector
				tempObj["parent_sector"] = parent_sector
				tempObj["class_name1"] = class_name1
				tempObj["class_name2"] = class_name2
				tempObj["type"] = _this.cwType
				tempObj["conf_id"] = id
				dataJson.push(tempObj)
				$.ajax({
					url:BASE_PATH + 'credit/front/ReportGetData/' + alterSource,
					type:'post',
					data:{
						dataJson:JSON.stringify(dataJson),
					},
					success:(data)=>{
						///新增一行成功
						console.log(data)
						if(data.statusCode === 1) {
							_this.refreshCwModal(tableCwIds,getSource,id)
						}else {
							Public.message("error",data.message)
						}
					}
				})
			})
			
			//处理默认项的删除A标签
			let tables = Array.from($(".cw-table").find(".table.table-hover"));
			tables.forEach((item,index)=>{
				let trs = Array.from($(item).find("tbody tr"));
				trs.forEach(ele=>{
					if($($(ele).find("td")[0]).find("input").length === 0){
						//默认项
						$($(ele).find("td")[3]).find("a").hide()
					}
				})
			})
		},0)
		
	},
	downLoadCw(url,rows){
		/**
		 * 财务下载模板
		 * url:下载模板url
		 */
		$(document).on("click",'.cwDown',()=>{
			$.ajax({
				url:BASE_PATH + `credit/front/ReportGetData/` +url,
				type:'post',
				data:{
					"report_type":rows["report_type"]
				},
				success:(data)=>{
					Public.message("success","下载成功！")
					let $eleForm = $("<form method='get' style='visibility:none'><input name='report_type' style='visibility:none' value="+rows["report_type"]+"></form>");
					$eleForm.attr("action", BASE_PATH + `credit/front/ReportGetData/` +url);
					$(".main").append($eleForm);
					//提交表单，实现下载
					$eleForm.submit();
				}
			})
		})
	},
	upLoadCw(url,rows,getSource,alterSource,tableCwId){
		/**
		 * 财务上传模板
		 * url:下载模板url
		 * getSource:财务getSource
		 */
		let _this = this
		$(document).on("change",'.fileInp',(e)=>{
			//$(e.target).parents(".aa-btn").addClass("loading")
			$("body").mLoading("show");//显示loading组件
			let file = $(e.target).prop('files')[0];
			let id = $(e.target).parents(".gjcw").attr("cwconfigid")
			$(e.target).siblings(".report_type").val(rows["report_type"])
			$(e.target).siblings(".ficConf_id").val(id)
			$(e.target).parent("form").attr("action",BASE_PATH + `credit/front/ReportGetData/` +url)
			$(e.target).parent("form").ajaxSubmit({
				success:(data)=>{
					$("body").mLoading("hide");//隐藏loading组件
					if(data.statusCode === 0){
						Public.message("error",data.message)
					}else if(data.statusCode === 1){
						Public.message("success",data.message)
						_this.refreshCwModal(tableCwId,getSource,id,rows)
						$(e.target).val("")
						_this.saveCwSummation(alterSource)
					}
				}
			})
			
		})
	},
	saveCwSummation(alterSource){
		/**
		 * 保存财务合计项
		 */
		let _this = this
		let dataJson = [];
		let $tableHj = $("#tableCwHj");
		let HjTrs = Array.from($tableHj.find("tbody").find("tr"))
		HjTrs.forEach((item,index)=>{
			let tempObj = {};
			let id = $(item).find("td:eq(1)").find("input").attr("entityid")
			let tds = $(item).find("td")
			let val1 = $(item).find("td:eq(1)").find("input").val()
			let val2 = $(item).find("td:eq(2)").find("input").val()
			let column_name1 = _this.tableColumnNameArr[1]
			let column_name2 = _this.tableColumnNameArr[2]
			tempObj[column_name1] = val1
			tempObj[column_name2] = val2
			tempObj["id"] = id
			dataJson.push(tempObj)
		})
		let $tableBj = $("#tableCwBl");
		let BlTrs = Array.from($tableBj.find("tbody").find("tr"))
		BlTrs.forEach((item,index)=>{
			let tempObj = {};
			let id = $(item).find("td:eq(1)").find("input").attr("entityid")
			let tds = $(item).find("td")
			let val1 = $(item).find("td:eq(1)").find("input").val()
			let val2 = $(item).find("td:eq(2)").find("input").val()
			let column_name1 = _this.tableColumnNameArr[1]
			let column_name2 = _this.tableColumnNameArr[2]
			tempObj[column_name1] = val1
			tempObj[column_name2] = val2
			tempObj["id"] = id
			dataJson.push(tempObj)
		})
		$.ajax({
			url:BASE_PATH + `credit/front/ReportGetData/` + alterSource,
			type:'post',
			data:{
				dataJson:JSON.stringify(dataJson)
			},
			success:(data)=>{
				
			}
		})
	},
	addNewCwModal(cwConfigAlterSource,rows){
		/**
		 * 新增一个财务模块
		 */
		$(document).on("click","#addCwMdal",(e)=>{
			let dataJson = []
			let obj = {}
			obj["company_id"] = rows["company_id"]
			dataJson.push(obj)
			$.ajax({
				url:BASE_PATH + 'credit/front/ReportGetData/' + cwConfigAlterSource,
				type:'post',
				data:{
					dataJson:JSON.stringify(dataJson),
					report_type:rows["report_type"]
				},
				success:(data)=>{
					console.log(data)
				}
			})
		})
	},
	refreshCwModal(tableCwIds,getSource,id,rows){
		/**
		 * 刷新表格数据
		 * id:财务模板id
		 */
//		console.log(tableCwIds)
		let returnData;
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + getSource + '?ficConf_id='+id+'&report_type='+rows["report_type"],
			type:'post',
			async:false,
			success:(data)=>{
				returnData = data
			}
		})
		let tempRows =  []
		let tempArr = [];
		returnData['rows'].sort((a,b)=>{
			return a["son_sector"]-b["son_sector"]
		});
		returnData['rows'].forEach((item,index)=>{
			if(item.is_sum_option) {
				//合计项
				if(index>8){
					//非合计表合计项 给个class背景变色
					item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["begin_date_value"]} class="form-control bg-gray ${item.class_name1}" style="width:13.5rem"/>`
					item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
				}else {
					item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["begin_date_value"]} class="form-control ${item.class_name1}" style="width:13.5rem"/>`
					item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} disabled="disabled" value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
				}
			}else {
				if(!item.is_default){
					item["item_name"] = `<input type="text" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value="${item['item_name'] === null?'':item['item_name']}" class="form-control" style="width:13.5rem"/>`
				}
				item["begin_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value=${item["begin_date_value"]} class="form-control ${item.class_name1}" style="width:13.5rem"/>`
				item["end_date_value"] = `<input type="number" entityid=${item.id} sonsector=${item.son_sector} parentsector=${item.parent_sector} value=${item["end_date_value"]} class="form-control ${item.class_name2}" style="width:13.5rem"/>`
			}
			if(!returnData['rows'][index-1] || item.son_sector !== returnData['rows'][index-1]["son_sector"] || (index+1) === returnData['rows'].length) {
				if(tempRows.length !== 0){
					tempArr.push(tempRows)
					tempRows = []
				}
			}
			tempRows.push(item)
		})
			//合计项放在最后
		tempArr.forEach((item,index)=>{
			item.forEach((ele,index)=> {
				if(ele.is_sum_option === 1) {
					item.splice($.inArray(ele,item),1)
					item.push(ele)
				}
			})
		})
		tableCwIds.forEach((item,index)=>{
			const $table = $('#'+item);
			$table.bootstrapTable("load",tempArr[index])
			//处理默认项的删除A标签
			let tables = Array.from($(".cw-table").find(".table.table-hover"));
			tables.forEach((item,index)=>{
				let trs = Array.from($(item).find("tbody tr"));
				trs.forEach(ele=>{
					if($($(ele).find("td")[0]).find("input").length === 0){
						//默认项
						$($(ele).find("td")[3]).find("a").hide()
					}
				})
			})
			
		})
		//自动计算合计
		const $table = $('#'+tableCwIds[1]);
		$($table.find("input")[0]).trigger("blur")
	},
	dateInit(){
    	/**
    	 * 日期初始化
    	 */
    	layui.use('laydate', function(){
    		  	const laydate = layui.laydate;
		    	let dateArr = Array.from($('.date-form input'))
		    	dateArr.forEach((item,index)=>{
		    		laydate.render({
		    			elem: item,
		    			format: 'yyyy年MM月dd日'
		    		});
		    	})
		    	let dateScopeArr = Array.from($('.date-scope-form input'))
		    	dateScopeArr.forEach((item,index)=>{
		    		laydate.render({
		    			elem: item,
		    			format: 'yyyy年MM月dd日',
		    			range:true
		    		});
		    	})
		    	let floatDateArr = Array.from($('.float-date'))
		    	floatDateArr.forEach((item,index)=>{
		    		laydate.render({
		    			elem: item,
		    			format: 'yyyy年MM月dd日'
		    		});
		    	})
		    	
		    	//模态窗里面的时间控件初始化
		    	let modals = Array.from($(".modal"))
		    	let modalDates = Array.from($(".modal .modal-date input"))
		    	modals.forEach((item,index)=>{
		    		modalDates.forEach((item,index)=>{
		    			laydate.render({
			    			elem: item,
			    			format: 'yyyy年MM月dd日'
			    		});
		    		})
		    	})
		    	//财务时间控件
		    	let  cwDate= Array.from($(".cw-date"))
		    	cwDate.forEach((item,index)=>{
		    		if($(item).hasClass("cw-range")){
		    			let cw_date = Array.from($(item).children("input"))
			    		cw_date.forEach((item,index)=>{
			    			laydate.render({
				    			elem: item,
				    			format: 'yyyy年MM月dd日',
				    			range:true
				    		});
			    		})
		    		}else {
		    			let cw_date = Array.from($(item).children("input"))
		    			cw_date.forEach((item,index)=>{
		    				laydate.render({
		    					elem: item,
		    					format: 'yyyy年MM月dd日',
		    					done:function(value){
		    						if($(item).hasClass("dateInp1")){
		    							let dateInps1 = Array.from($(".dateInp1"))
		    							dateInps1.forEach((item,index)=>{
		    								$(item).val(value);
		    							})
		    						}else if($(item).hasClass("dateInp2")){
		    							let dateInps2 = Array.from($(".dateInp2"))
		    							dateInps2.forEach((item,index)=>{
		    								$(item).val(value);
		    							})
		    						}
		    					}
		    				});
		    			})
		    		}
		    	})
    	});
    },
    addressInit(){
    	/**
    	 * 地址初始化
    	 */
    	let addArr = Array.from($('.address-form input'))
    	addArr.forEach((item,index)=>{
    		$(item).focus(function (e) {
    			SelCity(this,e);
    			let top = $(item).offset().top
    			$("#PoPy").css("top",top+30+"px")
    			$(".main").scroll(()=>{
    				let top = $(item).offset().top
    				$("#PoPy").css("top",top+30+"px")
    				if(top < 90) {
    					$("#cColse").trigger("click")
    				}
    			})
    		});
    	})
    	
    	//模态窗里面的地址控件初始化
    	let modals = Array.from($(".modal"))
    	let modalAdd = Array.from($(".modal .modal-address input"))
    	modals.forEach((item,index)=>{
    		modalAdd.forEach((item,index)=>{
    			$(item).focus(function (e) {
        			SelCity(this,e);
        			let top = $(item).offset().top
        			$("#PoPy").css("top",top+30+"px")
        			$(".main").scroll(()=>{
        				let top = $(item).offset().top
        				$("#PoPy").css("top",top+30+"px")
        				if(top < 90) {
        					$("#cColse").trigger("click")
        				}
        			})
        		});
    		})
    	})
    },
    regChecked(){
    	/**
    	 * 正则验证
    	 */
    	$(".firm-info .form-control").blur((e)=>{
    		let val = $(e.target).val();
    		let reg = $(e.target).attr("reg")
    		if(reg === 'null' || !reg) {
    			return;
    		}else {
    			
    			if(!eval("("+reg+")").test(val)){
    				$(e.target).siblings(".errorInfo").show();
    				$(e.target).val("")
    				$(e.target).addClass("active")
    			
    			}else {
    			
    				$(e.target).siblings(".errorInfo").hide();
    				$(e.target).removeClass("active")
    			}
    		}
    	})
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
    initSelect2(){
    	$("select.select2.form-control").select2({
    		 multiple: true,
    	})
    }
}

