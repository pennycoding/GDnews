package com.gdnews.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdnews.bean.User_imformation;
import com.gdnews.news.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyConformation extends Activity {

	ImageView titlereturn, user_image;
	TextView user_name, user_sex, user_old, user_email, user_address;
	private User_imformation ui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_information_sitting);

		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);
		user_image = (ImageView) this.findViewById(R.id.user_image);

		user_name = (TextView) this.findViewById(R.id.user_name);
		user_sex = (TextView) this.findViewById(R.id.user_sex);
		user_old = (TextView) this.findViewById(R.id.user_old);
		user_email = (TextView) this.findViewById(R.id.user_email);
		user_address = (TextView) this.findViewById(R.id.user_address);
		ui = (User_imformation) getApplication();

		new MyWebResquestTask()
				.execute("http://1.mygdmecapp.sinaapp.com/myapp_user.php");

		titlereturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// System.out.println(land.getid());

				finish();

			}
		});

	}

	// 从网站上获取HTML返回字符串放置在EditText里面
	String getWebInfo(String url) {

		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", ui.getuserid()));

		// 发送post请求
		HttpPost httpRequest = new HttpPost(url);

		try {
			// 发出HTTP请求
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpClient httpClient = new DefaultHttpClient();

			// 取得HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

				return result;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "网络访问异常";

	}

	public class MyWebResquestTask extends AsyncTask<String, String, String> {

		// 主线程执行
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			try {
				JSONObject jsonObject = new JSONObject(result);
				user_name.setText(jsonObject.getString("user_name"));
				user_sex.setText(jsonObject.getString("user_sex"));
				user_old.setText(jsonObject.getString("user_old") + "岁");
				user_email.setText(jsonObject.getString("user_email"));
				user_address.setText(jsonObject.getString("user_address"));
				System.out.println(result);
				new MyDownloadTask(user_image).execute(jsonObject
						.getString("user_image"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 主线程执行
		// 子线程，这里不要访问控件，但是可以访问网络
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String webresponse = getWebInfo(params[0]);
			return webresponse;
		}

	}

	// 图片下载函数
	public Bitmap downloadImage(String url) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);

		Bitmap bitmap = null;

		try {
			HttpResponse resp = httpclient.execute(httpget);
			// 判断是否正确执行
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				// 将返回内容转换为bitmap
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				bitmap = BitmapFactory.decodeStream(in);

				return bitmap;

			}

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	// 异步任务类
	public class MyDownloadTask extends AsyncTask<String, String, String> {

		// 下载下来的图片，存放在这里
		private Bitmap bmp;

		ImageView imageView;

		int width;
		int height;

		// 获得一个外部ImageView的引用
		public MyDownloadTask(ImageView imageView) {
			this.imageView = imageView;
			height = imageView.getHeight();
			width = imageView.getWidth();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("OK")) {
				imageView.setImageBitmap(bmp);
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			imageView.setImageResource(R.drawable.titleimage);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			bmp = downloadImage(arg0[0]);

			if (bmp != null)
				return "OK";
			else
				return "FAIL";
		}

	}

}
