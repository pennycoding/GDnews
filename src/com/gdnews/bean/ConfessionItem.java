package com.gdnews.bean;

public class ConfessionItem {

	public String confession_to = "";
	public String confession_content = "";
	public String confession_from = "";
	public String confession_date = "";

	public ConfessionItem(String confession_to, String confession_content,  
			String confession_from,  String confession_date) {
		super();
		this.confession_to = confession_to;
		this.confession_content = confession_content;
		this.confession_from = confession_from;
		this.confession_date = confession_date;
	}
}
