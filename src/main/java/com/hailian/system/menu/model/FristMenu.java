package com.hailian.system.menu.model;

import java.util.List;


public class FristMenu {
	//一级菜单
	private String  text;
	private Integer id;
	//二级菜单
	
	private List<SecondMenu> nodes;
	
	public FristMenu(String text,int id,List<SecondMenu> nodes) {
		this.text=text;
		this.id=id;
		this.nodes=nodes;
	}
	public FristMenu(String text,int id) {
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
	public List<SecondMenu> getNodes() {
		return nodes;
	}
	public void setNodes(List<SecondMenu> nodes) {
		this.nodes = nodes;
	}
	
	
	
}
