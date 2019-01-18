/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.el;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.util.ObjectUtils;

/**
 * 点缀对象
 * 
 * @author Sayi
 *
 */
public class Dot {

	private static Logger logger = LoggerFactory.getLogger(Dot.class);
	private String el;
	private Dot target;
	private String key;

	final static String EL_REGEX;
	final static Pattern EL_PATTERN;

	static {
		EL_REGEX = "^\\w+(\\.\\w+)*$";
		EL_PATTERN = Pattern.compile(EL_REGEX);
	}

	public Dot(String el) {
		ObjectUtils.requireNonNull(el, "EL cannot be null.");
		if (!EL_PATTERN.matcher(el).matches()) {
			throw new ExpressionEvalException("Error EL fomart: " + el);
		}

		this.el = el;
		int dotIndex = el.lastIndexOf(".");
		if (-1 == dotIndex) {
			this.key = el;
		} else {
			this.key = el.substring(dotIndex + 1);
			this.target = new Dot(el.substring(0, dotIndex));
		}
	}

	public Object eval(ELObject elObject) {
		if (elObject.cache.containsKey(el))
			return elObject.cache.get(el);
		Object result = null != target ? result = evalKey(target.eval(elObject)) : evalKey(elObject.model);
		if (null != result)
			elObject.cache.put(el, result);
		return result;
	}

	private Object evalKey(Object obj) {
		ObjectUtils.requireNonNull(obj, "Cannot read value from null Prefix-Model, Prefix-Model EL: " + target);
		final Class<?> objClass = obj.getClass();
		if (obj instanceof String || obj instanceof Number || obj instanceof java.util.Date || obj instanceof Collection
				|| objClass.isArray() || objClass.isPrimitive()) {
			throw new ExpressionEvalException("Prefix-Model must be JavaBean or Map, Prefix-Model EL: " + target
					+ ", Prefix-Model type: " + objClass);
		}

		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).get(key);
		}

		Field field = FieldFinder.find(objClass, key);
		if (null == field) {
			logger.error("Connot find the key:" + key + " from Prefix-Model EL:" + target);
		} else {
			try {
				return field.get(obj);
			} catch (Exception e) {
				logger.error("Error read the property:" + key + " from " + objClass);
			}
		}
		return null;

	}

	public String getEl() {
		return el;
	}

	public Dot getTarget() {
		return target;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return null == el ? "[root el]" : el;
	}

}
