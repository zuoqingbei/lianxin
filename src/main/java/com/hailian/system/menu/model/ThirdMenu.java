package com.hailian.system.menu.model;

public class ThirdMenu {
	private String text;
	private Integer id;
	public ThirdMenu(String text, int id) {
		super();
		this.text = text;
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
