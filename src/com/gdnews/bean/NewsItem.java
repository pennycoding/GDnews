package com.gdnews.bean;

public class NewsItem {
	public String news_title = "";
	public String news_summary = "";
	public int news_time = 0;
	public int news_comment = 0;
	public String news_images = "";
	public String news_url = "";

	public NewsItem(String news_title, String news_summary, int news_time,
			int news_comment, String news_images, String news_url) {
		super();
		this.news_title = news_title;
		this.news_summary = news_summary;
		this.news_time = news_time;
		this.news_comment = news_comment;
		this.news_images = news_images;
		this.news_url = news_url;
	}

}
