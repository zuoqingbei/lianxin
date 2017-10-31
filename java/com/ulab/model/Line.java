package com.ulab.model;

import java.util.List;

public class Line {
	private String name;
	private List<Value> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Value> getData() {
		return data;
	}
	public void setData(List<Value> data) {
		this.data = data;
	}
}
