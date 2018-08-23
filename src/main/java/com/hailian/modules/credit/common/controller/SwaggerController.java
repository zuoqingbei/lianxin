package com.hailian.modules.credit.common.controller;

import com.alibaba.fastjson.JSON;
import com.feizhou.swagger.annotation.Api;
import com.feizhou.swagger.annotation.ApiOperation;
import com.feizhou.swagger.annotation.Param;
import com.feizhou.swagger.annotation.Params;
import com.feizhou.swagger.model.SwaggerDoc;
import com.feizhou.swagger.model.SwaggerGlobalPara;
import com.feizhou.swagger.model.SwaggerPath;
import com.feizhou.swagger.utils.ClassHelper;
import com.google.common.collect.Maps;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.jfinal.core.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * swagger
 *
 * @author lee
 * @version V1.0.0
 * @date 2017/7/7
 */
@ControllerBind(controllerKey = "/creditSwagger")
public class SwaggerController extends Controller {


    public void api() {
        SwaggerDoc doc = new SwaggerDoc();
        Map<String, Map<String, SwaggerPath.ApiMethod>> paths = new HashMap<>();
        Map<String, String> classMap = Maps.newHashMap();
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(Api.class)) {
                Api api = cls.getAnnotation(Api.class);

                if (!classMap.containsKey(api.tag())) {
                    classMap.put(api.tag(), api.description());
                }

                Method[] methods = cls.getMethods();

                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    SwaggerPath.ApiMethod apiMethod = new SwaggerPath.ApiMethod();
                    apiMethod.setOperationId("");
                    apiMethod.addProduce("application/json");

                    List<SwaggerPath.Parameter> parameterList = SwaggerGlobalPara.getParameterList();
                    if (parameterList != null && parameterList.size() > 0) {
                        for (SwaggerPath.Parameter parameter : parameterList) {
                            apiMethod.addParameter(parameter);
                        }
                    }

                    for (Annotation annotation : annotations) {
                        Class<? extends Annotation> annotationType = annotation.annotationType();
                        if (ApiOperation.class == annotationType) {
                            ApiOperation apiOperation = (ApiOperation) annotation;
                            Map<String, SwaggerPath.ApiMethod> methodMap = new HashMap<>();
                            apiMethod.setSummary(apiOperation.description());
                            apiMethod.setDescription(apiOperation.description());
                            apiMethod.addTag(apiOperation.tag());
                            apiMethod.addConsume(apiOperation.consumes());
                            methodMap.put(apiOperation.httpMethod(), apiMethod);
                            paths.put(apiOperation.url(), methodMap);
                        } else if (Params.class == annotationType) {
                            Params apiOperation = (Params) annotation;
                            Param[] params = apiOperation.value();
                            for (Param apiParam : params) {
                                if (apiParam.dataType().equals("file")) {
                                    apiMethod.addParameter(new SwaggerPath.Parameter(apiParam.name(), "formData", apiParam.description(), apiParam.required(), apiParam.dataType(), apiParam.format(), apiParam.defaultValue()));
                                } else {
                                    apiMethod.addParameter(new SwaggerPath.Parameter(apiParam.name(), apiParam.description(), apiParam.required(), apiParam.dataType(), apiParam.format(), apiParam.defaultValue()));
                                }
                            }
                        } else if (Param.class == annotationType) {
                            Param apiParam = (Param) annotation;
                            apiMethod.addParameter(new SwaggerPath.Parameter(apiParam.name(), apiParam.description(), apiParam.required(), apiParam.dataType(), apiParam.format(), apiParam.defaultValue()));
                        }
                    }
                }
            }
        }

        if (classMap.size() > 0) {
            for (String key : classMap.keySet()) {
                doc.addTags(new SwaggerDoc.TagBean(key, classMap.get(key)));
            }
        }
        doc.setPaths(paths);

        // swaggerUI 使用Java的关键字default作为默认值,此处将生成的JSON替换
        renderText(JSON.toJSONString(doc).replaceAll("\"defaultValue\"", "\"default\""));
    }
}
