package com.gdnews.bean;

import android.app.Application;

public class User_imformation extends Application {

	private String userid = "";
	private String newsid = "";

	@Override
	public void onCreate() {
		super.onCreate();
		setuserid(USERID); // ��ʼ��ȫ�ֱ���
		setnewid(NEWSID); // ��ʼ��ȫ�ֱ���
	}

	public String getuserid() {
		return this.userid;
	}

	public void setuserid(String userid) {
		this.userid = userid;
	}
	
	public String getnewid() {
		return this.newsid;
	}

	public void setnewid(String newsid) {
		this.newsid = newsid;
	}

	private static final String USERID = "";
	private static final String NEWSID = "";
}
