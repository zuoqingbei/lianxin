package com.ulab.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.impl.CalendarCache;
import com.ibm.icu.math.BigDecimal;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.ulab.core.BaseController;
import com.ulab.model.FaultModel;
@ControllerBind(controllerKey="/health",viewPath="/phm")
public class HealthController extends BaseController{
	//转发到health.html
	public void health() {
		render("health.html");
		
	}
	
}
