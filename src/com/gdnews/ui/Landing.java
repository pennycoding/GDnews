package com.gdnews.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import com.gdnews.bean.User_imformation;
import com.gdnews.news.MainActivity;
import com.gdnews.news.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Landing extends Activity {

	ImageView titlereturn, my_image;
	Button cancellation;
	User_imformation ui;
	Intent intent = new Intent();
	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landsuccess);

		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);
		my_image = (ImageView) this.findViewById(R.id.my_image);
		cancellation = (Button) this.findViewById(R.id.Cancellation);
		ui = (User_imformation) getApplication();
		userid = ui.getuserid();
		new MyDownloadTask(my_image).execute("http://1.mygdmecapp.sinaapp.com/myapp_landing.php");

		titlereturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.setClass(Landing.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

		cancellation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ui.setuserid("");
				intent.setClass(Landing.this, User_land.class);
				startActivity(intent);
				finish();
			}
		});

	}

	// 图片下载函数
	public Bitmap downloadImage(String url) {
		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", userid));

		Bitmap bitmap = null;

		// 发送post请求
		HttpPost httpRequest = new HttpPost(url);

		HttpClient httpClient = new DefaultHttpClient();
		try {
			// 发出HTTP请求
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 取得HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			// 判断是否正确执行
			if (HttpStatus.SC_OK == httpResponse.getStatusLine()
					.getStatusCode()) {
				// 将返回内容转换为bitmap
				HttpEntity entity = httpResponse.getEntity();
				InputStream in = entity.getContent();
				bitmap = BitmapFactory.decodeStream(in);

				return bitmap;

			}

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
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
