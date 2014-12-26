package com.gdnews.bean;

public class CommentItem {
	public String comment_content = "";
	public String user_name = "";
	public String user_image = "";
	public String comment_date = "";

	public CommentItem(String comment_content, String user_name,
			String user_image, String comment_date) {
		super();
		this.comment_content = comment_content;
		this.user_name = user_name;
		this.user_image = user_image;
		this.comment_date = comment_date;
	}

}
