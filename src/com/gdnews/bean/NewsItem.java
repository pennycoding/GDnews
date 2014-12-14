package com.gdnews.bean;

public class NewsItem {
	public String title = "";
	public String desc = "";
	public int catid = 0;
	public String thumb = "";
	public String url = "";
	public String username = "";
	public int inputtime = 0;

	public NewsItem(String title, String desc, int catid, String thumb,
			String url,String username,int inputtime) {
		super();
		this.title = title;
		this.desc = desc;
		this.catid = catid;
		this.thumb = thumb;
		this.url = url;
		this.username = username;
		this.inputtime = inputtime;
	}

}
