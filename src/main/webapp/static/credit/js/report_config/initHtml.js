let InitObj = {
	hjArr:[],
	bindCwConfig(conf_id,url,tableTitle,i,radioName,rows){
		
		/**
		 * 绑定财务模块表格配置信息
		 * tableTitle:表格标题
		 * i:代表第几次调用此方法 1-4 共四次
		 */
		let paramObj = {}
		let _this = this
		let cwModalId = ''
		paramObj["conf_id"] = conf_id
		if(url.split("*")[1]) {
			let tempParam = url.split("*")[1].split("$");//必要参数数组
			tempParam.forEach((item,index)=>{
				paramObj[item] = rows[item]
				console.log(paramObj[item],rows[item])
			})
		}
		console.log(tableTitle)
		let radioVal = ''
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + url.split("*")[0],
			type:'post',
			async:false,
			data:paramObj,
			success:(data)=>{
				console.log(data)
				data = data.rows
				if(data.length === 0){return}
				//获取财务模板id   ////下面 0代表页面自上而下的财务模块
				cwModalId = data[0]["id"]
				let cwModalArr = Array.from($(".cwModal"))
				data.forEach((item,index)=>{
					let inputs = Array.from($(cwModalArr[index]).siblings(".top-html").find("input[type='text']"))
					let inputss =  Array.from($(".cw-date"));
					inputss.forEach((item,index)=>{
						if(!$(item).hasClass("cw-range")){
							inputs.push($(item).find("input"))
						}
					})
					let selects = Array.from($(cwModalArr[index]).siblings(".top-html").find("select"))
					let radios = Array.from($(cwModalArr[index]).siblings(".top-html").find("input[type='radio']"))
					inputs.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						$(ele).val(item[name])
					})
					selects.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						let val = item[name]
						$(ele).find("option[value="+val+"]").attr("selected","selected")
					})
					radios.forEach((ele,i)=>{
						let name = $(ele).attr("name")
						let val = item[name]
						$("input[name="+name+"][value="+val+"]").attr("checked","checked")
						radioVal = val
					})
				})
			}
		})
		let tables = Array.from($(".table-title"))
		$(tables[i-1]).html(radioVal==="1"?tableTitle[1]:tableTitle[0])
		$(document).on('change','input[type=radio][name='+radioName+']',()=>{
			let val = $('input[type=radio][name='+radioName+']:checked').val()
			console.log(i-1,tableTitle,val)
			$(tables[i-1]).html(val==="1"?tableTitle[1]:tableTitle[0])
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
	cwModalCompute(i=1){
		//财务计算
		$(".gjcw"+i+" table").on("blur","input",(e)=>{
			let className = $(e.target).attr("class")
			//配数据的时候加减法的calssname一定要配置在最后面
			className = className.split(" ")[className.split(" ").length-1]
			if(className.split("-")[1] || className === 'amount1'|| className === 'amount2'){
				//减法
				this.hjArr.forEach((item,index)=>{
					if(item !== null && item.split("-")[item.split("-").length-1] === 'sub'){
						//减法的合计
						let total1 = $(".gjcw"+i+" .amount1").val()
						let total2 = $(".gjcw"+i+" .amount2").val()
						let arr = item.split("-");
						if(arr.length === 2) {
							//毛利润
							let temp1 = 0
							let doms1 = Array.from($(".gjcw"+i+" ."+arr[0]+"-son"));
							doms1.forEach((item,index)=>{
								temp1 += +$(item).val()
							})
							total1 -= temp1;
							let temp2 = 0
							let doms2 = Array.from($(".gjcw"+i+" ."+arr[0].replace("1","2")+"-son"));
							doms2.forEach((item,index)=>{
								temp2 += +$(item).val()
							})
							total2 -= temp2;
						}else if(arr.length === 3) {
							//非毛利润表
							let sub1 = $(".gjcw"+i+" ."+arr[0]+"-sub").length===0?$(".gjcw"+i+" ."+this.hjArr[index-1].split("-")[0]+'-'+arr[0]+"-sub").val():$(".gjcw"+i+" ."+arr[0]+"-sub").val();
							let sub2 = $(".gjcw"+i+" ."+arr[0].replace("1","2")+"-sub").length===0?$(".gjcw"+i+" ."+this.hjArr[index-1].split("-")[0].replace("1","2")+'-'+arr[0].replace("1","2")+"-sub").val():$(".gjcw"+i+" ."+arr[0].replace("1","2")+"-sub").val();
							let temp1 = 0;
							let temp2 = 0;
							let doms1 = Array.from($(`.gjcw${i} .${arr[1]}-son`));
							doms1.forEach((item,index)=>{
								temp1 += +$(item).val()
							})
							total1 -= (total1-sub1);
							total1 -= temp1;
							let doms2 = Array.from($(`.gjcw${i} .${arr[1].replace("1","2")}-son`));
							doms2.forEach((item,index)=>{
								temp2 += +$(item).val()
							})
							total2 -= (total2-sub2);
							total2 -= temp2;
						}
						
						$(".gjcw"+i+" ."+item).val(total1)
						$(".gjcw"+i+" ."+item.replace(/1/g,'2')).val(total2)
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
								let doms1 = Array.from($(".gjcw"+i+" ."+item))
								doms1.forEach((item,index)=>{
									num1 += +$(item).val()
								})
								let doms2 = Array.from($(".gjcw"+i+" ."+item.replace("1","2")))
								doms2.forEach((item,index)=>{
									num2 += +$(item).val()
								})
							}
						})
						$(".gjcw"+i+" ."+item).val(num1)
						$(".gjcw"+i+" ."+item.replace(/1/g,'2')).val(num2)
					}
				})
			}
			//固定资产
			$(".gjcw"+i+" .gdzcje1-copy").val($(".gjcw"+i+" .gdzcje1").val())
			$(".gjcw"+i+" .gdzcje2-copy").val($(".gjcw"+i+" .gdzcje2").val())
			
			//重要比率表
			let ldzc1 = $(`.gjcw${i} .ldzc1-add`).val()//流动资产 
			let ldzc2 = $(`.gjcw${i} .ldzc2-add`).val()// 
			let ldfz1 = $(`.gjcw${i} .ldfz1-add`).val()//流动负债
			let ldfz2 = $(`.gjcw${i} .ldfz2-add`).val()//
			let ch1 = $(`.gjcw${i} .ch1`).val()//存货
			let ch2 = $(`.gjcw${i} .ch2`).val()//
			let fzze1 = $(`.gjcw${i} .ldfz1-qtfz1-total`).val()//负债总额
			let fzze2 = $(`.gjcw${i} .ldfz2-qtfz2-total`).val()//
			let zcze1 = $(`.gjcw${i} .ldzc1-qtzc1-total`).val()//资产总额
			let zcze2 = $(`.gjcw${i} .ldzc2-qtzc2-total`).val()//
			let yysr1 = $(`.gjcw${i} .amount1`).val()//营业收入
			let yysr2 = $(`.gjcw${i} .amount2`).val()//
			let jlr1 = $(`.gjcw${i} .sq1-jlr1-sub`).val()//净利润
			let jlr2 = $(`.gjcw${i} .sq2-jlr2-sub`).val()//
			let yycb1 = $(`.gjcw${i} .yycb1`).val()//营业成本
			let yycb2 = $(`.gjcw${i} .yycb2`).val()//
			let yszk1 = $(`.gjcw${i} .yszk1`).val()//应收账款
			let yszk2 = $(`.gjcw${i} .yszk2`).val()//
			let yfzk1 = $(`.gjcw${i} .yfzk1`).val()//应付账款
			let yfzk2 = $(`.gjcw${i} .yfzk2`).val()//
			
			//流动比率
			$(`.gjcw${i} .ldbl1`).val(+ldfz1 === 0?0:ldzc1/ldfz1)
			$(`.gjcw${i} .ldbl2`).val(+ldfz2 === 0?0:ldzc2/ldfz2)
			//速动比率
			$(`.gjcw${i} .sdbl1`).val(+ldfz1 === 0?0:ldzc1-ch1/ldfz1)
			$(`.gjcw${i} .sdbl2`).val(+ldfz2 === 0?0:ldzc2-ch2/ldfz2)
			//资产负债率
			$(`.gjcw${i} .zcfzl1`).val(+zcze1 === 0?0:fzze1/zcze1)
			$(`.gjcw${i} .zcfzl2`).val(+zcze2 === 0?0:fzze2/zcze2)
			//净利润率
			$(`.gjcw${i} .jlrl1`).val(+yysr1 === 0?0:jlr1/yysr1)
			$(`.gjcw${i} .jlrl2`).val(+yysr2 === 0?0:jlr2/yysr2)
			//资产回报率
			$(`.gjcw${i} .zchb1`).val(+zcze1 === 0?0:jlr1/zcze1)
			$(`.gjcw${i} .zchb2`).val(+zcze2 === 0?0:jlr2/zcze2)
			//营业总额/资产总额
			$(`.gjcw${i} .yy-zc1`).val(+zcze1 === 0?0:yysr1/zcze1)
			$(`.gjcw${i} .yy-zc2`).val(+zcze2 === 0?0:yysr2/zcze2)
			//营业成本/营业总额
			$(`.gjcw${i} .yy-yy1`).val(+yysr1 === 0?0:yycb1/yysr1)
			$(`.gjcw${i} .yy-yy2`).val(+yysr2 === 0?0:yycb2/yysr2)
			
			let cw_range1 = $($(`.gjcw${i} .cw-range`).find("input")[0]).val()
			let cw_range2 = $($(`.gjcw${i} .cw-range`).find("input")[1]).val()
			if(cw_range1.split("-")[1] && cw_range1.split("-")[0].trim() === cw_range1.split("-")[1].trim()) {
				//存货周转天数
				$(`.gjcw${i} .chzzts1`).val(+yysr1 === 0?0:ch1/yysr1*365)
				//应收账款周转天数
				$(`.gjcw${i} .yszkzzts1`).val(+yysr1 === 0?0:yszk1/yysr1*365)
				//应付账款周转天数
				$(`.gjcw${i} .yfzkzzts1`).val(+yycb1 === 0?0:yfzk1/yycb1*365)
			}
			if(cw_range2.split("-")[1] && cw_range2.split("-")[0].trim() === cw_range2.split("-")[1].trim()) {
				//存货周转天数
				$(`.gjcw${i} .chzzts2`).val(+yysr2 === 0?0:ch2/yysr2*365)
				//应收账款周转天数
				$(`.gjcw${i} .yszkzzts2`).val(+yysr2 === 0?0:yszk2/yysr2*365)
				//应付账款周转天数
				$(`.gjcw${i} .yfzkzzts2`).val(+yycb2 === 0?0:yfzk2/yycb2*365)
			}
		})
	},
	
	initCwTable(tableCwIds,title,contents,getSource,alterSource,id){
		//财务模块表格初始化
		/**
		 * tableCwIds:表格id数组 
		 * 
		 * title ：表格的配置信息
		 * contents :表格的表头信息
		 * getSource:财务getsource
		 * alterSource:财务alterSource
		 * id:财务模块id
		 */
		let returnData;
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + getSource + '?ficConf_id='+id,
			type:'post',
			async:false,
			success:(data)=>{
				returnData = data
			}
		})
		let tempRows =  []
		let tempArr = [];
		
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
        			tempObj[ele.column_name] = ''
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
	        						
	        					}
	        				},
	//            				formatter: function(){return _this.formatBtnArr[tempI]}
	    				})
	    			}
        		})
        		
        		return arr
        	}
		})
		
		setTimeout(()=>{
			$(".bg-gray").parent("td").parent("tr").css("background","#fafafa")
			
			$(".addBtn").click((e)=>{
				console.log($(e.target).siblings(".bootstrap-table").find("input"))
				let $input = $($(e.target).siblings(".bootstrap-table").find("input")[0])
				let son_sector = $input.attr("sonsector")
				let parent_sector = $input.attr("parentsector")
				let dataJson = []
				
				tempObj["son_sector"] = son_sector
				tempObj["parent_sector"] = parent_sector
				dataJson.push(tempObj)
				$.ajax({
					url:BASE_PATH + 'credit/front/ReportGetData/' + alterSource,
					type:'post',
					data:{
						dataJson:JSON.stringify(dataJson),
						ficConf_id:id
					},
					success:(data)=>{
						console.log(data)
					}
				})
			})
		},0)
		
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
}

