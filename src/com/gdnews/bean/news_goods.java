package com.gdnews.bean;

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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class news_goods extends Activity {

	String result = null;
	MyHandler myhandler = new MyHandler();
	
	public String goods() {
		/*String GoodsURL = "http://1.mygdmecapp.sinaapp.com/myapp_goods.php";
		// 启动一个自定义的线程访问网络
		MyThread mythread = new MyThread(GoodsURL);
		// 线程启动
		mythread.start();*/

		return result;
	}

	String getWebInfo(String url) {
		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		/*params.add(new BasicNameValuePair("n",ui.getid()));
		System.out.println("aaa"+ui.getid());*/
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

}