package com.gdnews.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import com.gdnews.bean.ActionItem;
import com.gdnews.bean.User_imformation;
import com.gdnews.model.TitlePopup;
import com.gdnews.news.MainActivity;
import com.gdnews.news.R;
import com.gdnews.ui.MyFragmentOne.MyAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ImageView;

public class news_webView extends Activity {

	WebView news_content;
	TitlePopup titlePopup;
	ImageView imageView,titlereturn;
	MyAdapter pagerAdapter;
	User_imformation ui;
	MyHandler myhandler = new MyHandler();
	String n;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_content_layout);
		init();
		news_content = (WebView) this.findViewById(R.id.news_content);
		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);
		imageView = (ImageView) this.findViewById(R.id.imageView1);
		ui = (User_imformation) getApplication();

		// 获取参数
		Intent intent = getIntent();		
		String newsUrl = intent.getStringExtra("newsURL");
		String newsId = intent.getStringExtra("newsid");
		ui.setnewid(newsId);
		n = newsId;
		
		news_content.loadUrl(newsUrl);
		
		// 启动一个自定义的线程访问网络
		MyThread mythread = new MyThread("http://1.mygdmecapp.sinaapp.com/myapp_goods.php");
		// 线程启动
		mythread.start();
		
		titlereturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(news_webView.this, MainActivity.class);
				startActivity(intent);
				finish();
				
			}
		});

		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				titlePopup.show(v);
			}
		});

	}
	
	String getWebInfo(String url) {
		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("n",n));
		System.out.println(n);
		// 发送post请求
		HttpPost httpRequest = new HttpPost(url);

		try {
			// 发出HTTP请求
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						"UTF-8");

				return result;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}

	public class MyThread extends Thread {

		public String url;

		public MyThread(String Url) {
			url = Url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = getWebInfo(url);
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("RESULT", result);
			msg.setData(bundle);

			myhandler.sendMessage(msg);

		}

	}// end of class MyThread

	public class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			String result = msg.getData().getString("RESULT");
			System.out.println(result);
		}

	}
	

	private void init() {
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		titlePopup.addAction(new ActionItem(this, "评论", R.drawable.comment));
	}

}
