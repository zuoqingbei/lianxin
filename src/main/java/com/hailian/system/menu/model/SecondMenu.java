package com.hailian.system.menu.model;

import java.util.List;
public class SecondMenu {
	//二级菜单
	private String text;
	private Integer id;
	private List<ThirdMenu> nodes;
	
	public SecondMenu(String text, int id,List<ThirdMenu> nodes) {
		this.text=text;
		this.id=id;
		this.nodes=nodes;
	}
	public SecondMenu(String text, int id) {
		this.text=text;
		this.id=id;
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
	public List<ThirdMenu> getNodes() {
		return nodes;
	}
	public void setNodes(List<ThirdMenu> nodes) {
		this.nodes = nodes;
	}
	

}
