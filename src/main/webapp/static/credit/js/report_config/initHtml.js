let InitObj = {
	rows : JSON.parse(localStorage.getItem("row")),
	bindCwConfig(conf_id,url){
		/**
		 * 绑定财务模块表格配置信息
		 */
		let paramObj = {}
		paramObj["conf_id"] = conf_id
		if(url.split("*")[1]) {
			let tempParam = url.split("*")[1].split("$");//必要参数数组
			tempParam.forEach((item,index)=>{
				paramObj[item] = this.rows[item]
			})
		}
		$.ajax({
			url:BASE_PATH + 'credit/front/ReportGetData/' + url.split("*")[0],
			type:'post',
			data:paramObj,
			success:(data)=>{
				console.log(data)
				data = data.rows
				let cwModalArr = Array.from($(".cwModal"))
				data.forEach((item,index)=>{
					let inputs = Array.from($(cwModalArr[index]).siblings(".top-html").find("input[type='text']"))
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
					})
				})
			}
		})
	},
	initCwTable(isEdit,tableCwIds,title,contents){
		//财务模块表格初始化
		/**
		 * tableCwIds:表格id数组 
		 * isEdit:是否可以编辑 0 不可编辑 1可编辑
		 * title ：表格的配置信息
		 * contents :表格的表头信息
		 */
		
		
		
		tableCwIds.forEach((item,index)=>{
			const $table = $('#'+item);
			console.log($table)
			$table.bootstrapTable({
        		columns: columns(),
    			//url:url, // 请求后台的URL（*）
			    method : 'post', // 请求方式（*）post/get
			    sidePagination: 'server',
			    contentType:'application/x-www-form-urlencoded;charset=UTF-8',
    			pagination: false, //分页
    			smartDisplay:true,
    			locales:'zh-CN',
        	});
			
			
			function columns(){
        		let arr = []
        		contents.forEach((ele,index)=>{
        			if(ele.temp_name !== '操作|编辑|删除'){
        				arr.push({
        					title:ele.temp_name,
        					field: ele.column_name,
        					width:(1/contents.length)*100+'%'
        				})
        				
        			}else {
        				arr.push({
        					title:'',
        					field: 'operate',
        					width:1/contents.length,
        					events: {
            					"click .edit":(e,value,row,index)=>{
            						
            					},
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
		    		let cw_date = Array.from($(item).children("input"))
		    		cw_date.forEach((item,index)=>{
		    			laydate.render({
			    			elem: item,
			    			format: 'yyyy年MM月dd日'
			    		});
		    		})
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

