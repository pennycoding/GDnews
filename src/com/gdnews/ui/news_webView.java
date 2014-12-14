package com.gdnews.ui;

import com.gdnews.news.R;
import com.gdnews.ui.MyFragmentFour.MyPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class news_webView extends Activity {

	WebView news_content;

	// private PagerAdapter
	MyPagerAdapter pagerAdapter;

	DemoCommentData democommentdata = new DemoCommentData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_content_layout);

		news_content = (WebView) this.findViewById(R.id.news_content);

		// 获取参数
		Intent intent = getIntent();
		String newsUrl = intent.getStringExtra("newsURL");

		news_content.loadUrl(newsUrl);

	}

}
