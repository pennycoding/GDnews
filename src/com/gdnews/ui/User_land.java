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
import com.gdnews.bean.User_imformation;
import com.gdnews.news.MainActivity;
import com.gdnews.news.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class User_land extends Activity {

	Button btn_confirm;
	EditText et_user, et_password;
	String user_name, user_password;
	ImageView iv_close;
	Intent intent = new Intent();
	MyHandler myhandler = new MyHandler();
	User_imformation ui;
	String LandURL = "http://1.mygdmecapp.sinaapp.com/myapp_land.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_land);

		btn_confirm = (Button) this.findViewById(R.id.btn_confirm);
		et_user = (EditText) this.findViewById(R.id.et_user);
		et_password = (EditText) this.findViewById(R.id.et_password);
		iv_close = (ImageView) this.findViewById(R.id.titlereturn);
		ui = (User_imformation) getApplication();

		btn_confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				user_name = et_user.getText().toString().trim();
				user_password = et_password.getText().toString().trim();

				if (user_name.equals("") || user_name == null) {
					Toast.makeText(User_land.this, "请输入用户名", 3000).show();
				} else if (user_password.equals("") || user_password == null) {
					Toast.makeText(User_land.this, "请输入用密码 ", 3000).show();
				} else {
					// 启动一个自定义的线程访问网络
					MyThread mythread = new MyThread(LandURL);
					// 线程启动
					mythread.start();
				}
			}
		});

		iv_close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.setClass(User_land.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	String getWebInfo(String url) {
		String result = "";
		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", user_name));
		params.add(new BasicNameValuePair("pwd", user_password));

		// 发送post请求
		HttpPost httpRequest = new HttpPost(url);

		try {
			// 发出HTTP请求
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			System.out.println("username:" + user_name);
			System.out.println("password:" + user_password);

			// 取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);

			// 若状态码为200则请求成功，取到返回数据
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// 取出字符串
				result = EntityUtils
						.toString(httpResponse.getEntity(), "UTF-8");

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
		return result;

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
			// 返回用户id
			if (result.equals("error")) {
				Toast.makeText(User_land.this, "用户名或密码错误！", 1000).show();
			} else {
				ui.setuserid(result);
				intent.setClass(User_land.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
			System.out.println(result);
		}

	}

}
